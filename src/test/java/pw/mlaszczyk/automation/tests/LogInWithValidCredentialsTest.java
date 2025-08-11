package pw.mlaszczyk.automation.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import pw.mlaszczyk.automation.config.configLoader;
import pw.mlaszczyk.automation.pages.pages.loginPage;

import java.io.ByteArrayInputStream;

public class LogInWithValidCredentialsTest extends BaseTest {
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

    @Epic("Logging in")
    @Feature("Logging in")
    @Story("User is able to log in")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Logging in")
    @Test
    void shouldLoginWithValidCredentials() {
        page.navigate(baseUrl);
        loginPage login = new loginPage(page);

        login.loginAndVerify(username, password, mainPageUrl);
        Allure.addAttachment("Page screenshot", new ByteArrayInputStream(page.screenshot()));
    }
}
