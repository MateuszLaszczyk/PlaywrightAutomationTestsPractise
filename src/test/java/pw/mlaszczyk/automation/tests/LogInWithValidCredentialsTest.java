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
    private LoginPage loginPage;

    @BeforeAll
    static void loadConfig() {
        username = ConfigLoader.get("saucedemo.username");
        password = ConfigLoader.get("saucedemo.password");
        baseUrl = ConfigLoader.get("saucedemo.baseUrl");
        mainPageUrl = ConfigLoader.get("saucedemo.mainPageUrl");
    }

    @BeforeEach
    void setUpPages() {
        loginPage = new LoginPage(page);

    }

    @Epic("Login")
    @Feature("Login")
    @Story("User is able to login with valid credentials")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Logging in")
    @Test
    void shouldLoginWithValidCredentials() {
        page.navigate(baseUrl);


        loginPage.loginToTheWebsite(username, password);
        loginPage.assertThatUserIsLoggedIn(mainPageUrl);

        Allure.addAttachment("Page screenshot", new ByteArrayInputStream(page.screenshot()));
    }
}
