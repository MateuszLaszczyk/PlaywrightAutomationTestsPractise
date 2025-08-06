package pw.mlaszczyk.automation.tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import jdk.jfr.Description;
import org.junit.jupiter.api.*;
import pw.mlaszczyk.automation.pages.pages.LoginPage;
import pw.mlaszczyk.automation.config.ConfigLoader;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/**
 * This test class verifies the login functionality on the Saucedemo website
 * using Microsoft Playwright and JUnit 5.
 *
 * <p>It includes setup and teardown logic for the Playwright browser,
 * loads credentials from an external properties file,
 * and performs a successful login validation test.
 *
 * Test URL: https://www.saucedemo.com
 */
public class LoginTest {
    static Playwright playwright;
    static LoginPage loginPage;
    static Browser browser;
    Page page;

    // Credentials loaded from config.properties
    private static String username;
    private static String password;

    /**
     * Initializes the Playwright engine and browser before any tests run.
     * Also loads login credentials from the config.properties file using ConfigLoader.
     */
    @BeforeAll
    static void setupAll() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
        );

        username = ConfigLoader.get("saucedemo.username");
        password = ConfigLoader.get("saucedemo.password");
        System.out.println("Loaded username: " + username);
    }

    /**
     * Opens a new browser page and navigates to the login page before each test.
     * Initializes the LoginPage object for further interaction.
     */
    @BeforeEach
    void setup() {
        page = browser.newPage();
        page.navigate("https://www.saucedemo.com");
        loginPage = new LoginPage(page);
    }

    /**
     * This test verifies that a user can successfully log in using valid credentials.
     * It checks whether the browser is redirected to the inventory page after login.
     */
    @Test
    @Description("This test verifies a successful login with valid credentials")
    void shouldLoginWithValidCredentials() {
        loginPage.login(username, password);
        assertThat(page).hasURL("https://www.saucedemo.com/inventory.html");
    }

    /**
     * Closes the browser page after each test to clean up resources.
     */
    @AfterEach
    void tearDown() {
        if (page != null) {
            page.close();
        }
    }

    /**
     * Closes the browser and Playwright engine after all tests have completed.
     */
    @AfterAll
    static void tearDownAll() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}
