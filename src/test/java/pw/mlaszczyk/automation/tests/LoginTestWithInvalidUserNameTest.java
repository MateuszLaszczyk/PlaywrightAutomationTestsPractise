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


public class loginTestWithInvalidUserNameTest {
    static Playwright playwright;
    static loginPage loginPage;
    static Browser browser;
    static Page page;


    // Credentials loaded from config.properties file

    private static String password;
    private static String baseUrl;
    private static String invalidUserName;

    @BeforeAll
    static void setupAll() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(true) // show browser window
        );

        invalidUserName = configLoader.get("saucedemo.invalidusername");
        password = configLoader.get("saucedemo.password");
        baseUrl = configLoader.get("saucedemo.baseUrl");

    }

    @BeforeEach
    void setup() {
        page = browser.newPage();
        page.navigate(baseUrl);
        loginPage = new loginPage(page);
        System.out.println("Used username: " + invalidUserName);

    }

    @Epic("Invalid user name")
    @Feature("Loggin with invalid user name ")
    @Story("User tries to log in with ivalid user name")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Loggin with invalid user name ")


    @Test
    public void loginWithInvalidUerNameAndVerify() {
        loginPage.loginWithInvalidUserNameAndVerify(invalidUserName, password);
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



