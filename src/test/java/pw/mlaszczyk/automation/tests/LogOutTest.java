package pw.mlaszczyk.automation.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import pw.mlaszczyk.automation.config.ConfigLoader;
import pw.mlaszczyk.automation.pages.pages.LoginPage;


public class LogOutTest extends BaseTest {
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

    @Epic("Logging out")
    @Feature("Logging out")
    @Story("User is able to log out from the application")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Logging out")
    @Test
    void shouldLogOut() {
        page.navigate(baseUrl);

        loginPage.loginToTheWebsite(username, password);
        loginPage.assertThatUserIsLoggedIn(mainPageUrl);

        loginPage.logOutFromTheWebSite();
        loginPage.assertThatUserIsLoggedOut();
    }
}
