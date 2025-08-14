package pw.mlaszczyk.automation.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import pw.mlaszczyk.automation.config.ConfigLoader;
import pw.mlaszczyk.automation.pages.pages.LoginPage;
import pw.mlaszczyk.automation.pages.pages.ProductPage;

import java.io.ByteArrayInputStream;

public class AddItemToCartTest extends BaseTest {
    private static String username;
    private static String password;
    private static String baseUrl;
    private static String mainPageUrl;

    @BeforeAll
    static void loadConfig() {
        username = ConfigLoader.get("saucedemo.username");
        password = ConfigLoader.get("saucedemo.password");
        baseUrl = ConfigLoader.get("saucedemo.baseUrl");
        mainPageUrl = ConfigLoader.get("saucedemo.mainPageUrl");
    }

    @Epic("Adding item to cart")
    @Feature("Add item to cart feature")
    @Story("User is able to add item to a cart")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Add item to cart")
    @Test
    void addItemToCartAndVerifyCartQty() {
        page.navigate(baseUrl);
        LoginPage login = new LoginPage(page);
        ProductPage products = new ProductPage(page);

        login.loginAndVerify(username, password, mainPageUrl);
        products.addItemToCart();
        products.verifyThatItemIsAddedToCart(1);

        Allure.addAttachment("Page screenshot", new ByteArrayInputStream(page.screenshot()));
    }
}
