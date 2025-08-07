package pw.mlaszczyk.automation.tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.*;
import pw.mlaszczyk.automation.pages.pages.loginPage;
import pw.mlaszczyk.automation.config.configLoader;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/**
 * This test class verifies the login functionality on the Saucedemo website
 * using Microsoft Playwright and JUnit 5.
 *
 * <p>It includes setup and teardown logic for the Playwright browser,
 * loads credentials from an external properties file,
 * and performs a successful login validation test.
 */
public class loginTestWithValidCredentials {
    static Playwright playwright;
    static loginPage loginPage;
    static Browser browser;
    Page page;

    // Credentials loaded from config.properties
    private static String username;
    private static String password;
    private static String baseUrl;
    private static String mainPageUrl;

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

        username = configLoader.get("saucedemo.username");
        password = configLoader.get("saucedemo.password");
        baseUrl = configLoader.get("saucedemo.baseUrl");
        mainPageUrl = configLoader.get("saucedemo.mainPageUrl");
        System.out.println("Loaded username: " + username);
    }

    /**
     * Opens a new browser page and navigates to the login page before each test.
     * Initializes the LoginPage object for further interaction.
     */
    @BeforeEach
    void setup() {
        page = browser.newPage();
        page.navigate(baseUrl);
        loginPage = new loginPage(page);
    }

    @Test
    void shouldLoginWithValidCredentials() {
        loginPage.loginAndVerify(username, password, mainPageUrl);
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
