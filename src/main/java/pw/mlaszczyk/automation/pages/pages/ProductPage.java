package pw.mlaszczyk.automation.pages.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ProductPage {
    private final Page page;

    public ProductPage(Page page) {
        this.page = page;
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
        addItemToCartAndVerify(1);
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

    /*
    Adding SauceLabFleeceJacket item to cart, veryfing that qty is changed and remove button is visible
    * */
    public void addItemToCartAndVerify(int expectedCount) {
        page.locator("button[data-test='add-to-cart-sauce-labs-fleece-jacket']").click();
        Locator cartBadge = page.locator("span[data-test='shopping-cart-badge']");
        assertThat(cartBadge).hasText(String.valueOf(expectedCount));
        Locator removeButton = page.locator("button[data-test='remove-sauce-labs-fleece-jacket']");
        assertThat(removeButton).isVisible();
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
