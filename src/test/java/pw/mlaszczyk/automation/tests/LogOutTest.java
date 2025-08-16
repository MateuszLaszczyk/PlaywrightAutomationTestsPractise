package pw.mlaszczyk.automation.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import pw.mlaszczyk.automation.config.ConfigLoader;
import pw.mlaszczyk.automation.pages.pages.LoginPage;
import pw.mlaszczyk.automation.pages.pages.ProductPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LogOutTest extends BaseTest {
    private static String username;
    private static String password;
    private static String baseUrl;
    private static String mainPageUrl;

    private LoginPage loginPage;
    private ProductPage productPage;

    @BeforeAll
    static void cfg() {
        username    = ConfigLoader.get("saucedemo.username");
        password    = ConfigLoader.get("saucedemo.password");
        baseUrl     = ConfigLoader.get("saucedemo.baseUrl");
        mainPageUrl = ConfigLoader.get("saucedemo.mainPageUrl");
    }

    @BeforeEach
    void setUp() {
        loginPage   = new LoginPage(page);
        productPage = new ProductPage(page);
    }

    @Epic("Auth")
    @Feature("Logout")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("User can log out and sees login button")
    @Test
    void userCanLogOut() {
        page.navigate(baseUrl);
        loginPage.loginToTheWebsite(username, password);
        assertThat(page).hasURL(mainPageUrl);

        loginPage.logOutFromTheWebSite();

        // inline assertions:
        assertThat(page).hasURL(baseUrl + "/");
        assertThat(loginPage.loginButton()).isVisible();
    }
}
