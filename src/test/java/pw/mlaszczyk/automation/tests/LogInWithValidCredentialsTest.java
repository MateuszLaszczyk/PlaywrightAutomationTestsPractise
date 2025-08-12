package pw.mlaszczyk.automation.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import pw.mlaszczyk.automation.config.ConfigLoader;
import pw.mlaszczyk.automation.pages.pages.LoginPage;

import java.io.ByteArrayInputStream;

public class LogInWithValidCredentialsTest extends BaseTest {
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

    @Epic("Login")
    @Feature("Login")
    @Story("User is able to login with valid credentials")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Logging in")
    @Test
    void shouldLoginWithValidCredentials() {
        page.navigate(baseUrl);
        LoginPage login = new LoginPage(page);

        login.loginAndVerify(username, password, mainPageUrl);
        Allure.addAttachment("Page screenshot", new ByteArrayInputStream(page.screenshot()));
    }
}
