package pw.mlaszczyk.automation.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import pw.mlaszczyk.automation.config.ConfigLoader;
import pw.mlaszczyk.automation.pages.pages.LoginPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LogOutTest extends BaseTest {
    private static String username;
    private static String password;
    private static String baseUrl;
    private static String mainPageUrl;
    private LoginPage loginPage;


    @BeforeAll
    static void cfg() {
        username = ConfigLoader.get("saucedemo.username");
        password = ConfigLoader.get("saucedemo.password");
        baseUrl = ConfigLoader.get("saucedemo.baseUrl");
        mainPageUrl = ConfigLoader.get("saucedemo.mainPageUrl");
    }

    @BeforeEach
    void setUp() {
        loginPage = new LoginPage(page);
    }

    @Epic("Auth")
    @Feature("Logout")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("User can log out and sees login button")
    @Test
    void userCanLogOut() {
        // 1. Navigate to the login page
        page.navigate(baseUrl);
        // 2. Login with valid credentials
        loginPage.login(username, password);
        // 3. Assert that user is logged in
        assertThat(page).hasURL(mainPageUrl);
        // 4. LogOut from the website and verify
        loginPage.logout();
        // 5. Assert that user is logged out
        assertThat(page).hasURL(baseUrl + "/");
        assertThat(loginPage.loginButton()).isVisible();
    }
}
