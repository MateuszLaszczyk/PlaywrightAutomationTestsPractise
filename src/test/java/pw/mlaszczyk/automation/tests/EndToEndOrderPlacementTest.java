package pw.mlaszczyk.automation.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import pw.mlaszczyk.automation.config.ConfigLoader;
import pw.mlaszczyk.automation.helpers.ProductAssertions;
import pw.mlaszczyk.automation.pages.pages.LoginPage;
import pw.mlaszczyk.automation.pages.pages.ProductPage;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EndToEndOrderPlacementTest extends BaseTest {

    private static String username;
    private static String password;
    private static String baseUrl;
    private static String mainPageUrl;
    private static ProductAssertions productAssertions;
    private LoginPage loginPage;
    private ProductPage productPage;

    @BeforeAll
    static void loadConfig() {
        Locale.setDefault(Locale.US); // predictable decimals (10.99)
        username    = ConfigLoader.get("saucedemo.username");
        password    = ConfigLoader.get("saucedemo.password");
        baseUrl     = ConfigLoader.get("saucedemo.baseUrl");
        mainPageUrl = ConfigLoader.get("saucedemo.mainPageUrl");
    }

    @BeforeEach
    void setUp() {
        loginPage   = new LoginPage(page);
        productPage = new ProductPage(page);
    }

    @Epic("Order Placement")
    @Feature("End-to-End Purchase Flow")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Complete order for Sauce Labs Fleece Jacket")
    @Timeout(120)
    @Test
    void orderFlowTest() {
        // 1) Login
        page.navigate(baseUrl);
        loginPage.loginToTheWebsite(username, password);
        assertThat(page).hasURL(mainPageUrl);

        // 2) Sort high→low and assert prices are sorted descending
        productPage.selectHighestFilter();
        ProductAssertions.assertPricesSortedDescending(productPage.allPrices());

        // 3) Add product and assert cart badge
        productPage.addSauceLabsFleeceJacketToCart();
        assertThat(productPage.cartBadge()).hasText("1");
        assertThat(productPage.sauceLabsFleeceRemoveButton()).isVisible();

        // 4) Go to cart and assert correct product is in the cart
        productPage.navigateToCart();
        assertThat(productPage.productNameInCart()).hasText("Sauce Labs Fleece Jacket");

        // 5) Checkout → fill data → Continue
        productPage.clickCheckoutButton();
        productPage.fillShippingData("John", "Doe", "80-000");
        productPage.clickContinueButton();

        // 6) Assert pricing (tax = 8% of item total, total = item total + tax)
        assertThat(productPage.itemTotalLabel()).isVisible();
        assertThat(productPage.taxLabel()).isVisible();
        assertThat(productPage.totalLabel()).isVisible();

        // 7) Assert correctness for prices
        ProductAssertions.assertTaxIsEightPercent(productPage.itemTotalLabel(), productPage.taxLabel());
        ProductAssertions.assertTotalPriceIsCorrect(productPage.itemTotalLabel(), productPage.taxLabel(), productPage.totalLabel());

        // 8) Finish and assert completion
        productPage.clickFinishButton();
        assertThat(productPage.orderCompletedHeader()).isVisible();

        Allure.addAttachment("Page screenshot", new ByteArrayInputStream(page.screenshot()));
    }
}
