package pw.mlaszczyk.automation.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import pw.mlaszczyk.automation.config.ConfigLoader;
import pw.mlaszczyk.automation.pages.pages.LoginPage;
import pw.mlaszczyk.automation.pages.pages.ProductPage;
import pw.mlaszczyk.automation.tests.Setup.BaseTest;

import java.io.ByteArrayInputStream;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Epic("Shopping Journey")
@Feature("Cart")
public class AddItemToCartTest extends BaseTest {
    private static String username;
    private static String password;
    private static String baseUrl;
    private static String mainPageUrl;

    private LoginPage loginPage;
    private ProductPage productPage;

    @BeforeAll
    static void cfg() {
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

    @Epic("Cart")
    @Feature("Add item to cart")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Add item increases cart badge and shows remove button")
    @Test
    void addItemToCartAndVerifyCartQty() {
        // 1. Navigate to the login page
        page.navigate(baseUrl);
        // 2. Login with valid credentials
        loginPage.login(username, password);
        // 3. Assert that user is logged in
        assertThat(page).hasURL(mainPageUrl);
        // 4. Add Sauce Labs Fleece Jacket To Cart
        productPage.addSauceLabsFleeceJacketToCart();
        // 5. Assert that cart count changed to 1
        assertThat(productPage.cartBadge()).hasText("1");
        // 6. Assert that button near item changed to remove
        assertThat(productPage.sauceLabsFleeceRemoveButton()).isVisible();

        Allure.addAttachment("Page screenshot", new ByteArrayInputStream(page.screenshot()));
    }
}
