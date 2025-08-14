package pw.mlaszczyk.automation.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import pw.mlaszczyk.automation.config.ConfigLoader;
import pw.mlaszczyk.automation.pages.pages.LoginPage;
import pw.mlaszczyk.automation.pages.pages.ProductPage;

import java.io.ByteArrayInputStream;
import java.util.Locale;

public class EndToEndOrderPlacementTest extends BaseTest {
    private static String username;
    private static String password;
    private static String baseUrl;
    private static String mainPageUrl;
    private LoginPage loginPage;
    private ProductPage productPage;

    @BeforeAll
    static void loadConfig() {
        Locale.setDefault(Locale.US);
        username = ConfigLoader.get("saucedemo.username");
        password = ConfigLoader.get("saucedemo.password");
        baseUrl = ConfigLoader.get("saucedemo.baseUrl");
        mainPageUrl = ConfigLoader.get("saucedemo.mainPageUrl");
    }

    @BeforeEach
    void setUpPages() {
        loginPage = new LoginPage(page);
        productPage = new ProductPage(page);
    }

    @Epic("Order Placement")
    @Feature("End-to-End Order Flow")
    @Story("User is able to place an order")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("E2E Order Placement")
    @Test
    void endToEndOrderPlacement() {
        page.navigate(baseUrl);

        loginPage.loginToTheWebsite(username, password);
        loginPage.assertThatUserIsLoggedIn(mainPageUrl);

        productPage.endToEndOrderPlacement("Sauce Labs Fleece Jacket", "Mateusz", "Laszczyk", "80-126");
        productPage.assertOrderPlacement();

        Allure.addAttachment("Page screenshot", new ByteArrayInputStream(page.screenshot()));
    }
}
