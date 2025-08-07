package pw.mlaszczyk.automation.tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pw.mlaszczyk.automation.config.configLoader;
import pw.mlaszczyk.automation.pages.pages.loginPage;
import pw.mlaszczyk.automation.pages.pages.productPage;

import java.io.ByteArrayInputStream;
import java.util.Locale;

public class addItemToCartTest {
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
                new BrowserType.LaunchOptions().setHeadless(false) // show browser window
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
    @Epic("Adding item to cart")
    @Feature("Add item to cart")
    @Story("User adds item to cart and verifying")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Add item to cart ")

    @Test
    void addItemToCartAndVerifyCartQty() {
        loginPage.loginAndVerify(username, password, mainPageUrl);
        productPage.addItemToCartAndVerify(1);
        Allure.addAttachment("Page screenshot", new ByteArrayInputStream(page.screenshot()));
    }
}
