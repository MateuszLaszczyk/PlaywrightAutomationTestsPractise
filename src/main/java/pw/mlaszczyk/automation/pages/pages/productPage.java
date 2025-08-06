package pw.mlaszczyk.automation.pages.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class productPage {
    private final Page page;

    public productPage(Page page) {
        this.page = page;
    }

    /**
     * Logs in using provided credentials and verifies successful navigation to inventory page.
     *
     * @param username the username to log in
     * @param password the password to log in
     * @param mainPageUrl to validate URL after login
     */
    public void loginAndVerify(String username, String password,String mainPageUrl) {
        page.locator("input[data-test='username']").fill(username);
        page.locator("input[data-test='password']").fill(password);
        page.locator("input[data-test='login-button']").click();
        assertThat(page).hasURL(mainPageUrl);
    }

    /**
     * Executes the entire flow: sort, add item to cart, checkout, verify prices, complete order.
     *
     * @param expectedProductName product name expected in cart
     * @param firstName           first name for shipping
     * @param lastName            last name for shipping
     * @param zip                 postal code for shipping
     */
    public void endToEndOrderPlacement(String expectedProductName, String firstName, String lastName, String zip) {
        selectHighestFilter();
        assertPricesSortedDescending();
        addItemToCart();
        assertQtyProductAddedToCart(1);
        navigateToCart();
        assertProductNameAddedToCart(expectedProductName);
        clickCheckoutButton();
        fillShippingData(firstName, lastName, zip);
        clickContinueButton();
        assertTaxIsEightPercent();
        assertTotalPriceIsCorrect();
        clickFinishButton();
        assertOrderPlacement();
    }

    /**
     * Selects the product filter to show highest price first.
     */
    public void selectHighestFilter() {
        Locator dropDownList = page.locator("select[data-test='product-sort-container']");
        dropDownList.selectOption("hilo");
    }

    /**
     * Adds a specific item to the cart.
     */
    public void addItemToCart() {
        page.locator("button[data-test='add-to-cart-sauce-labs-fleece-jacket']").click();
    }

    /**
     * Navigates to the shopping cart page.
     */
    public void navigateToCart() {
        page.locator("a[data-test='shopping-cart-link']").click();
    }

    /**
     * Clicks the checkout button in the cart.
     */
    public void clickCheckoutButton() {
        page.locator("button[data-test='checkout']").click();
    }

    /**
     * Clicks the continue button after entering shipping data.
     */
    public void clickContinueButton() {
        page.locator("input[data-test='continue']").click();
    }

    /**
     * Fills the shipping data form during checkout.
     *
     * @param firstName first name
     * @param lastName  last name
     * @param zip       postal code
     */
    public void fillShippingData(String firstName, String lastName, String zip) {
        page.locator("input[data-test='firstName']").fill(firstName);
        page.locator("input[data-test='lastName']").fill(lastName);
        page.locator("input[data-test='postalCode']").fill(zip);
    }

    /**
     * Asserts that the product prices are sorted in descending order.
     */
    public void assertPricesSortedDescending() {
        List<String> priceTexts = page.locator(".inventory_item_price").allInnerTexts();
        List<Double> prices = priceTexts.stream()
                .map(text -> Double.parseDouble(text.replace("$", "").trim()))
                .collect(Collectors.toList());

        List<Double> sortedPrices = new ArrayList<>(prices);
        sortedPrices.sort(Comparator.reverseOrder());

        if (!sortedPrices.equals(prices)) {
            throw new AssertionError("Prices are not sorted descending");
        }
    }

    /**
     * Clicks the final finish button to complete the order.
     */
    public void clickFinishButton() {
        page.locator("button[data-test='finish']").click();
    }

    /**
     * Asserts that a specific number of items were added to the cart.
     *
     * @param expectedCount expected number of items
     */
    public void assertQtyProductAddedToCart(int expectedCount) {
        Locator cartBadge = page.locator("span[data-test='shopping-cart-badge']");
        assertThat(cartBadge).hasText(String.valueOf(expectedCount));
    }

    /**
     * Asserts that a specific product name is visible in the cart.
     *
     * @param expectedName expected product name
     */
    public void assertProductNameAddedToCart(String expectedName) {
        Locator productNameInCart = page.locator("div[data-test='inventory-item-name']");
        assertThat(productNameInCart).hasText(expectedName);
    }

    /**
     * Asserts that tax is calculated correctly (8% of item total).
     */
    public void assertTaxIsEightPercent() {
        Locator itemTotalLabel = page.locator(".summary_subtotal_label");
        Locator taxLabel = page.locator(".summary_tax_label");

        String itemTotalText = itemTotalLabel.innerText().replace("Item total: $", "").trim();
        String taxText = taxLabel.innerText().replace("Tax: $", "").trim();

        double itemTotal = Double.parseDouble(itemTotalText);
        double tax = Double.parseDouble(taxText);

        double expectedTax = Math.round(itemTotal * 0.08 * 100.0) / 100.0;
        String expectedText = String.format("Tax: $%.2f", expectedTax);

        assertThat(taxLabel).hasText(expectedText);
    }

    /**
     * Asserts that total price = item total + tax.
     */
    public void assertTotalPriceIsCorrect() {
        Locator itemTotalLabel = page.locator(".summary_subtotal_label");
        Locator taxLabel = page.locator(".summary_tax_label");
        Locator totalLabel = page.locator(".summary_total_label");

        double itemTotal = Double.parseDouble(itemTotalLabel.innerText().replace("Item total: $", "").trim());
        double tax = Double.parseDouble(taxLabel.innerText().replace("Tax: $", "").trim());

        double expectedTotal = Math.round((itemTotal + tax) * 100.0) / 100.0;

        assertThat(totalLabel).hasText(String.format("Total: $%.2f", expectedTotal));
    }

    /**
     * Asserts that the order confirmation message is visible.
     */
    public void assertOrderPlacement() {
        assertThat(page.locator("h2[data-test='complete-header']")).isVisible();
    }
}
