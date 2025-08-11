package pw.mlaszczyk.automation.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import pw.mlaszczyk.automation.config.configLoader;
import pw.mlaszczyk.automation.pages.pages.loginPage;

import java.io.ByteArrayInputStream;

public class LogInWithInvalidUserNameTest extends BaseTest {
    private static String invalidUserName;
    private static String password;
    private static String baseUrl;

    @BeforeAll
    static void loadConfig() {
        invalidUserName = configLoader.get("saucedemo.invalidusername");
        password = configLoader.get("saucedemo.password");
        baseUrl = configLoader.get("saucedemo.baseUrl");
    }

    @Epic("Invalid user name")
    @Feature("Logging with invalid user name")
    @Story("User tries to log in with invalid user name")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Login with invalid user name")
    @Test
    void loginWithInvalidUserNameAndVerify() {
        page.navigate(baseUrl);
        loginPage login = new loginPage(page);
        login.loginWithInvalidUserNameAndVerify(invalidUserName, password);

        Allure.addAttachment("Page screenshot", new ByteArrayInputStream(page.screenshot()));
    }
}
