package pw.mlaszczyk.automation.pages.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ProductPage {
    private final Page page;

    public ProductPage(Page page) {
        this.page = page;
    }

    //  Locators
    public Locator dropDownList() { return page.locator("select[data-test='product-sort-container']"); }
    public Locator cartIcon() { return page.locator("a[data-test='shopping-cart-link']"); }
    public Locator checkoutButton() { return page.locator("button[data-test='checkout']"); }
    public Locator continueButton() { return page.locator("input[data-test='continue']"); }
    public Locator firstNameInput() { return page.locator("input[data-test='firstName']"); }
    public Locator lastNameInput() { return page.locator("input[data-test='lastName']"); }
    public Locator zipInput() { return page.locator("input[data-test='postalCode']"); }
    public Locator finishButton() { return page.locator("button[data-test='finish']"); }
    public Locator sauceLabsFleeceJacketAddButton() { return page.locator("button[data-test='add-to-cart-sauce-labs-fleece-jacket']"); }
    public Locator cartBadge() { return page.locator("span[data-test='shopping-cart-badge']"); }
    public Locator sauceLabsFleeceRemoveButton() { return page.locator("button[data-test='remove-sauce-labs-fleece-jacket']"); }
    public Locator productNameInCart() { return page.locator("div[data-test='inventory-item-name']"); }
    public Locator itemTotalLabel() { return page.locator(".summary_subtotal_label"); }
    public Locator taxLabel() { return page.locator(".summary_tax_label"); }
    public Locator totalLabel() { return page.locator(".summary_total_label"); }
    public Locator orderCompletedHeader() { return page.locator("h2[data-test='complete-header']"); }
    public Locator allPrices() { return page.locator(".inventory_item_price"); }

    //  Actions
    public void selectHighestFilter() {
        dropDownList().selectOption("hilo");
    }

    public void navigateToCart() {
        cartIcon().click();
    }

    public void clickCheckoutButton() {
        checkoutButton().click();
    }

    public void clickContinueButton() {
        continueButton().click();
    }

    public void fillShippingData(String firstName, String lastName, String zip) {
        firstNameInput().fill(firstName);
        lastNameInput().fill(lastName);
        zipInput().fill(zip);
    }

    public void clickFinishButton() {
        finishButton().click();
    }

    public void addSauceLabsFleeceJacketToCart() {
        sauceLabsFleeceJacketAddButton().click();
    }
}
