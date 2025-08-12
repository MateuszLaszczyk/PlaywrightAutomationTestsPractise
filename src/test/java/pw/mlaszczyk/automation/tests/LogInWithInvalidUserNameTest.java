package pw.mlaszczyk.automation.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import pw.mlaszczyk.automation.config.ConfigLoader;
import pw.mlaszczyk.automation.pages.pages.LoginPage;

import java.io.ByteArrayInputStream;

public class LogInWithInvalidUserNameTest extends BaseTest {
    private static String invalidUserName;
    private static String password;
    private static String baseUrl;

    @BeforeAll
    static void loadConfig() {
        invalidUserName = ConfigLoader.get("saucedemo.invalidusername");
        password = ConfigLoader.get("saucedemo.password");
        baseUrl = ConfigLoader.get("saucedemo.baseUrl");
    }

    @Epic("Login")
    @Feature("Login feature ")
    @Story("User tries to login with INVALID user name")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Login with invalid user name")
    @Test
    void loginWithInvalidUserNameAndVerify() {
        page.navigate(baseUrl);
        LoginPage login = new LoginPage(page);
        login.loginWithInvalidUserNameAndVerify(invalidUserName, password);

        Allure.addAttachment("Page screenshot", new ByteArrayInputStream(page.screenshot()));
    }
}
