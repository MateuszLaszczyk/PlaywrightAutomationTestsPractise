package pw.mlaszczyk.automation.tests.Setup;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import pw.mlaszczyk.automation.config.ConfigLoader;


public abstract class BaseTest {
    protected static Playwright playwright;
    protected static Browser browser;
    protected Page page;


    @BeforeAll
    static void globalSetup() {
        playwright = Playwright.create();

        String headlessStr = safeGet("browser.headless", "true" +
                "");
        boolean headless = Boolean.parseBoolean(headlessStr);

        String browserName = safeGet("browser.name", "chromium"); // "chrome" | "chromium" | "firefox" | "webkit"

        BrowserType type = switch (browserName.toLowerCase()) {
            case "firefox" -> playwright.firefox();
            case "webkit" -> playwright.webkit();
            default -> playwright.chromium();
        };

        BrowserType.LaunchOptions opts = new BrowserType.LaunchOptions().setHeadless(headless);
        if ("chrome".equalsIgnoreCase(browserName)) {
            opts.setChannel("chrome");
        }

        browser = type.launch(opts);

    }

    @BeforeEach
    void setupPage() {
        BrowserContext context = browser.newContext();
        page = context.newPage();
        page.setDefaultTimeout(10_000);
        page.setDefaultNavigationTimeout(10_000);


    }

    @AfterAll
    static void globalTeardown() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    private static String safeGet(String key, String def) {
        try {
            String v = ConfigLoader.get(key);
            return v.isBlank() ? def : v;
        } catch (Exception e) {
            return def;
        }
    }
}
