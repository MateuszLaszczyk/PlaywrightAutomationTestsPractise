package pw.mlaszczyk.automation.tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import pw.mlaszczyk.automation.config.configLoader;
import pw.mlaszczyk.automation.pages.pages.loginPage;

import java.io.ByteArrayInputStream;

public class logOutTest {
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
                new BrowserType.LaunchOptions().setHeadless(true)
        );

        username = configLoader.get("saucedemo.username");
        password = configLoader.get("saucedemo.password");
        baseUrl = configLoader.get("saucedemo.baseUrl");
        mainPageUrl = configLoader.get("saucedemo.mainPageUrl");
        System.out.println("Loaded username: " + username);
    }

    @BeforeEach
    void setup() {
        page = browser.newPage();
        page.navigate(baseUrl);
        loginPage = new loginPage(page);
    }

    @Epic("Logging out")
    @Feature("Logging out")
    @Story("User is able to logg out from the application")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Logging out ")

    @Test
    void shouldLoginWithValidCredentials() {
        loginPage.loginAndVerify(username, password,mainPageUrl);
        loginPage.logOutFromTheWebSiteAndVerify(baseUrl);

    }
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


