package pw.mlaszczyk.automation.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import pw.mlaszczyk.automation.config.configLoader;
import pw.mlaszczyk.automation.pages.pages.loginPage;
import pw.mlaszczyk.automation.pages.pages.productPage;


import java.io.ByteArrayInputStream;
import java.util.Locale;


public class endToEndOrderPlacementTest {
    static Playwright playwright;
    static loginPage loginPage;
    static Browser browser;
    static Page page;
    static productPage productPage;

    // Credentials loaded from config.properties file
    private static String username;
    private static String password;
    private static String baseUrl;
    private static String mainPageUrl;

    /**
     * Runs once before all tests.
     * Initializes Playwright, browser and loads credentials from config.
     */
    @BeforeAll
    static void setupAll() {
        playwright = Playwright.create();
        Locale.setDefault(Locale.US); // set locale for consistent parsing.
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(true) // show browser window
        );

        username = configLoader.get("saucedemo.username");
        password = configLoader.get("saucedemo.password");
        baseUrl = configLoader.get("saucedemo.baseUrl");
        mainPageUrl = configLoader.get("saucedemo.mainPageUrl");
        System.out.println("Loaded username: " + username);
    }

    /**
     * Runs before each test.
     * Creates a new browser page and initializes page objects.
     */
    @BeforeEach
    void setup() {
        page = browser.newPage();
        page.navigate(baseUrl);
        loginPage = new loginPage(page);
        productPage = new productPage(page);
    }

    @Epic("Order Placement")
    @Feature("End-to-End Order Flow")
    @Story("User places an order after successful login")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("E2E Order Placement Test")

    @Test
    void endToEndOrderPlacement() {
        loginPage.loginAndVerify(username, password, mainPageUrl);
        productPage.endToEndOrderPlacement("Sauce Labs Fleece Jacket", "Mateusz", "Laszczyk", "80-126");
        Allure.addAttachment("Page screenshot", new ByteArrayInputStream(page.screenshot()));
    }

    /**
     * Runs after each test.
     * Closes the current browser page.
     */
    @AfterEach
    void tearDown() {
        if (page != null) {
            page.close();
        }
    }

    /**
     * Runs once after all tests.
     * Closes the browser and Playwright engine.
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
