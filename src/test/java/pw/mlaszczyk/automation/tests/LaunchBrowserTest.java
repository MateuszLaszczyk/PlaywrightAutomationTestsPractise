package pw.mlaszczyk.automation.tests;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import pw.mlaszczyk.automation.pages.data.UserFormData;
import pw.mlaszczyk.automation.pages.pages.TextBoxPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LaunchBrowserTest {
    static Playwright playwright;
    static Browser browser;
    Page page;

    @BeforeAll
    static void setupAll() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
        );
    }

    @BeforeEach
    void setup() {
        page = browser.newPage();
        page.navigate("https://demoqa.com/text-box");
    }

    @Test
    public void fillFormAndSubmit() {
        TextBoxPage form = new TextBoxPage(page);

        UserFormData data = new UserFormData(
                "Mateusz Laszczyk",
                "mateusz@example.com",
                "Czeslawa Milosza",
                "Czeslawa Milosza"
        );

        form.enterUserName(data.name);
        form.enterEmail(data.email);
        form.enterCurrentAddress(data.currentAddress);
        form.enterPermamentAddress(data.permanentAddress);
        form.clickSubmitButton();

        String output = form.getOutputText();

        assertTrue(output.contains(data.name));
        assertTrue(output.contains(data.email));
        assertTrue(output.contains(data.currentAddress));
        assertTrue(output.contains(data.permanentAddress));
    }

    @AfterEach
    void closePage() {
        page.close();
    }

    @AfterAll
    static void teardownAll() {
        browser.close();
        playwright.close();
    }
}
