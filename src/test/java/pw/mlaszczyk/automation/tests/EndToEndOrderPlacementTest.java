package pw.mlaszczyk.automation.tests;

import com.microsoft.playwright.BrowserContext;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import pw.mlaszczyk.automation.config.configLoader;
import pw.mlaszczyk.automation.pages.pages.loginPage;
import pw.mlaszczyk.automation.pages.pages.productPage;

import java.io.ByteArrayInputStream;
import java.util.Locale;

public class EndToEndOrderPlacementTest extends BaseTest {
    private static String username;
    private static String password;
    private static String baseUrl;
    private static String mainPageUrl;

    @BeforeAll
    static void loadConfig() {
        Locale.setDefault(Locale.US);
        username = configLoader.get("saucedemo.username");
        password = configLoader.get("saucedemo.password");
        baseUrl = configLoader.get("saucedemo.baseUrl");
        mainPageUrl = configLoader.get("saucedemo.mainPageUrl");
    }

    @Epic("Order Placement")
    @Feature("End-to-End Order Flow")
    @Story("User is able to place an order")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("E2E Order Placement")
    @Test
    void endToEndOrderPlacement() {
        page.navigate(baseUrl);
        loginPage login = new loginPage(page);
        productPage products = new productPage(page);

        login.loginAndVerify(username, password, mainPageUrl);
        products.endToEndOrderPlacement("Sauce Labs Fleece Jacket", "Mateusz", "Laszczyk", "80-126");

        Allure.addAttachment("Page screenshot", new ByteArrayInputStream(page.screenshot()));
    }
}
