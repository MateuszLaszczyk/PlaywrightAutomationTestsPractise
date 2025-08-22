package pw.mlaszczyk.automation.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import pw.mlaszczyk.automation.config.ConfigLoader;
import pw.mlaszczyk.automation.helpers.ProductAssertions;
import pw.mlaszczyk.automation.pages.pages.LoginPage;
import pw.mlaszczyk.automation.pages.pages.ProductPage;
import pw.mlaszczyk.automation.tests.Setup.BaseTest;

import java.io.ByteArrayInputStream;

import java.util.*;


import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Epic("E2E Shopping Journey")
@Feature("Checkout")
public class EndToEndOrderPlacementTest extends BaseTest {

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
    @DisplayName("Place an order end-to-end")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Validates the scenario: Place an order end-to-end. Covers login, cart, checkout steps, pricing assertions, and confirmation.")
    @Owner("QA Automation")
    void orderFlowTest() {
        // 1. Navigate to the login page
        page.navigate(baseUrl);
        // 2. Login with valid credentials
        loginPage.login(username, password);
        // 3. Assert that user is logged in
        assertThat(page).hasURL(mainPageUrl);
        // 4. Sort high→low and assert prices are sorted descending
        productPage.selectHighestFilter();
        // 5. Assert that items are sorted
        ProductAssertions.assertPricesSortedDescending(productPage.allPrices());
        // 6. Add product
        productPage.addSauceLabsFleeceJacketToCart();
        // 7. Assert that cart quantity is changed to one
        assertThat(productPage.cartBadge()).hasText("1");
        // 8. Assert that button near item changed to remove
        assertThat(productPage.sauceLabsFleeceRemoveButton()).isVisible();
        // 9. Navigate to cart
        productPage.navigateToCart();
        // 10. Assert that product in the cart is correct
        assertThat(productPage.productNameInCart()).hasText("Sauce Labs Fleece Jacket");
        // 11. Navigate to Checkout
        productPage.clickCheckoutButton();
        // 12. Assert that page has correct URL
        assertThat(page).hasURL(checkoutStepOneUrl);
        // 13. Fill out the shipping info with credentials
        productPage.fillShippingData("John", "Doe", "80-000");
        // 14. Assert that all fields are fulfilled
        assertThat(productPage.firstNameInput()).hasValue("John");
        assertThat(productPage.lastNameInput()).hasValue("Doe");
        assertThat(productPage.zipInput()).hasValue("80-000");
        // 15. Click continue button
        productPage.clickContinueButton();
        // 16. Assert that page has correct URL
        assertThat(page).hasURL(checkoutStepTwoUrl);
        // 18. Assert that pricing tags are visible
        assertThat(productPage.itemTotalLabel()).isVisible();
        assertThat(productPage.taxLabel()).isVisible();
        assertThat(productPage.totalLabel()).isVisible();
        // 19. Assert correctness for prices
        ProductAssertions.assertTaxIsEightPercent(productPage.itemTotalLabel(), productPage.taxLabel());
        ProductAssertions.assertTotalPriceIsCorrect(productPage.itemTotalLabel(), productPage.taxLabel(), productPage.totalLabel());
        // 20. Click finish button
        productPage.clickFinishButton();
        // 21. Assert that order is placed
        assertThat(productPage.orderCompletedHeader()).isVisible();

        Allure.addAttachment("Page screenshot", new ByteArrayInputStream(page.screenshot()));
    }
}
