package pw.mlaszczyk.automation.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import pw.mlaszczyk.automation.config.configLoader;
import pw.mlaszczyk.automation.pages.pages.loginPage;
import pw.mlaszczyk.automation.pages.pages.productPage;

import java.io.ByteArrayInputStream;

public class AddItemToCartTest extends BaseTest {
    private static String username;
    private static String password;
    private static String baseUrl;
    private static String mainPageUrl;

    @BeforeAll
    static void loadConfig() {
        username = configLoader.get("saucedemo.username");
        password = configLoader.get("saucedemo.password");
        baseUrl = configLoader.get("saucedemo.baseUrl");
        mainPageUrl = configLoader.get("saucedemo.mainPageUrl");
    }

    @Epic("Adding item to cart")
    @Feature("Add item to cart")
    @Story("User adds item to cart and verifying")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Add item to cart")
    @Test
    void addItemToCartAndVerifyCartQty() {
        page.navigate(baseUrl);
        loginPage login = new loginPage(page);
        productPage products = new productPage(page);

        login.loginAndVerify(username, password, mainPageUrl);
        products.addItemToCartAndVerify(1);

        Allure.addAttachment("Page screenshot", new ByteArrayInputStream(page.screenshot()));
    }
}
