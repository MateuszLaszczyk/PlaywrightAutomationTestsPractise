package pw.mlaszczyk.automation.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import pw.mlaszczyk.automation.assertions.CartAssertions;
import pw.mlaszczyk.automation.assertions.OrderAssertions;
import pw.mlaszczyk.automation.assertions.PricingAssertions;
import pw.mlaszczyk.automation.assertions.ProductAssertions;
import pw.mlaszczyk.automation.config.ConfigLoader;
import pw.mlaszczyk.automation.pages.pages.LoginPage;
import pw.mlaszczyk.automation.pages.pages.ProductPage;

import java.io.ByteArrayInputStream;
import java.util.Locale;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class EndToEndOrderPlacementTest extends BaseTest {

    private static String username;
    private static String password;
    private static String baseUrl;
    private static String mainPageUrl;

    private LoginPage loginPage;
    private ProductPage productPage;

    @BeforeAll
    static void loadConfig() {
        // Force US locale to make decimal formatting predictable (e.g., 10.99 instead of 10,99)
        Locale.setDefault(Locale.US);
        username    = ConfigLoader.get("saucedemo.username");
        password    = ConfigLoader.get("saucedemo.password");
        baseUrl     = ConfigLoader.get("saucedemo.baseUrl");
        mainPageUrl = ConfigLoader.get("saucedemo.mainPageUrl");
    }

    @BeforeEach
    void setUpPages() {
        loginPage   = new LoginPage(page);
        productPage = new ProductPage(page);
    }

    @Epic("Order Placement")
    @Feature("End-to-End Purchase Flow")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Complete order for Sauce Labs Fleece Jacket")
    @Test
    void orderFlowTest() {
        // 1) Navigate to login page and sign in
        page.navigate(baseUrl);
        loginPage.loginToTheWebsite(username, password);
        assertThat(page).hasURL(mainPageUrl);

        // 2) Sort products by highest price and verify correct sorting
        productPage.selectHighestFilter();
        ProductAssertions.assertPricesSortedDescending(productPage.allPrices());

        // 3) Add "Sauce Labs Fleece Jacket" to the cart and verify cart badge count
        productPage.addSauceLabsFleeceJacketToCart();
        CartAssertions.assertCartItemCount(productPage.cartBadge(), 1);

        // 4) Open the cart and verify that the correct product is inside
        productPage.navigateToCart();
        CartAssertions.assertProductNameInCart(productPage.productNameInCart(), "Sauce Labs Fleece Jacket");

        // 5) Proceed to checkout and fill in shipping information
        productPage.clickCheckoutButton();
        productPage.fillShippingData("John", "Doe", "80-000");
        productPage.clickContinueButton();

        // 6) Verify that tax is 8% of item total and that total is correct
        PricingAssertions.assertTaxIsEightPercent(productPage.itemTotalLabel(), productPage.taxLabel());
        PricingAssertions.assertTotalPriceIsCorrect(productPage.itemTotalLabel(), productPage.taxLabel(), productPage.totalLabel());

        // 7) Finish the order and verify confirmation message
        productPage.clickFinishButton();
        OrderAssertions.assertOrderIsCompleted(productPage.orderCompletedHeader());

        // 8) Attach final screenshot to Allure report
        Allure.addAttachment("Page screenshot", new ByteArrayInputStream(page.screenshot()));
    }
}
