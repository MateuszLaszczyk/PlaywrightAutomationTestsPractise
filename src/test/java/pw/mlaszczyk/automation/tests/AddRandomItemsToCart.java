package pw.mlaszczyk.automation.tests;

import io.qameta.allure.*;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pw.mlaszczyk.automation.config.ConfigLoader;
import pw.mlaszczyk.automation.helpers.ProductAssertions;
import pw.mlaszczyk.automation.pages.pages.LoginPage;
import pw.mlaszczyk.automation.pages.pages.ProductPage;
import pw.mlaszczyk.automation.tests.Setup.BaseTest;

import java.io.ByteArrayInputStream;
import java.util.Locale;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Epic("E2E Shopping Journey")
@Feature("Cart & Checkout")
public class AddRandomItemsToCart extends BaseTest {
    private static String username;
    private static String password;
    private static String baseUrl;
    private static String mainPageUrl;
    private LoginPage loginPage;
    private ProductPage productPage;
    private static String checkoutStepOneUrl;
    private static String checkoutStepTwoUrl;



    @BeforeAll
    static void cfg() {
        Locale.setDefault(Locale.US); // predictable decimals (10.99)
        username = ConfigLoader.get("saucedemo.username");
        password = ConfigLoader.get("saucedemo.password");
        baseUrl = ConfigLoader.get("saucedemo.baseUrl");
        mainPageUrl = ConfigLoader.get("saucedemo.mainPageUrl");
        checkoutStepOneUrl = ConfigLoader.get("saucedemo.checkOutStepOneUrl");
        checkoutStepTwoUrl = ConfigLoader.get("saucedemo.checkOutStepTwoUrl");
    }

    @BeforeEach
    void setUp() {
        loginPage = new LoginPage(page);
        productPage = new ProductPage(page);
    }

    @Test
    @DisplayName("Add multiple random items and complete checkout")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Validates the scenario: Add multiple random items and complete checkout. Includes navigation, actions, and assertions across the page flow.")
    @Owner("QA Automation")
    void addRandomItemsToCartAndCheckout()
    {
        // 1. Navigate to base URL
        page.navigate(baseUrl);

        // 2. Log in with provided username and password
        loginPage.login(username, password);

        // 3. Assert that the user is redirected to the main page
        assertThat(page).hasURL(mainPageUrl);

        // 4. Add 6 random items to the cart
        productPage.addRandomItemsToCart(6);

        // 5. Assert that the cart badge shows "5" items
        assertThat(productPage.cartBadge()).hasText("6");

        // 6. Navigate to the cart page
        productPage.navigateToCart();

        // 7. Click on the checkout button
        productPage.clickCheckoutButton();

        // 8. Assert that the page has the correct Checkout Step One URL
        assertThat(page).hasURL(checkoutStepOneUrl);

        // 9. Fill out the shipping information with test data
        productPage.fillShippingData("John", "Doe", "80-000");

        // 10. Assert that the shipping form fields are correctly filled
        assertThat(productPage.firstNameInput()).hasValue("John");
        assertThat(productPage.lastNameInput()).hasValue("Doe");
        assertThat(productPage.zipInput()).hasValue("80-000");

        // 11. Click on the continue button
        productPage.clickContinueButton();

        // 12. Assert that the page has the correct Checkout Step Two URL
        assertThat(page).hasURL(checkoutStepTwoUrl);

        // 13. Assert that pricing labels (Item total, Tax, Total) are visible
        assertThat(productPage.itemTotalLabel()).isVisible();
        assertThat(productPage.taxLabel()).isVisible();
        assertThat(productPage.totalLabel()).isVisible();

        // 14. Assert correctness of tax (8%) and total price calculation
        ProductAssertions.assertTaxIsEightPercent(productPage.itemTotalLabel(), productPage.taxLabel());
        ProductAssertions.assertTotalPriceIsCorrect(productPage.itemTotalLabel(), productPage.taxLabel(), productPage.totalLabel());

        // 15. Click on the finish button to place the order
        productPage.clickFinishButton();

        // 16. Assert that the order completion header is visible (order placed successfully)
        assertThat(productPage.orderCompletedHeader()).isVisible();

        // 17. Attach a screenshot to Allure report
        Allure.addAttachment("Page screenshot", new ByteArrayInputStream(page.screenshot()));
    }


}




