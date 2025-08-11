package pw.mlaszczyk.automation.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import pw.mlaszczyk.automation.config.configLoader;
import pw.mlaszczyk.automation.pages.pages.loginPage;

public class LogOutTest extends BaseTest {
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

    @Epic("Logging out")
    @Feature("Logging out")
    @Story("User is able to log out from the application")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Logging out")
    @Test
    void shouldLogOut() {
        page.navigate(baseUrl);
        loginPage login = new loginPage(page);

        login.loginAndVerify(username, password, mainPageUrl);
        login.logOutFromTheWebSiteAndVerify(baseUrl);
    }
}
