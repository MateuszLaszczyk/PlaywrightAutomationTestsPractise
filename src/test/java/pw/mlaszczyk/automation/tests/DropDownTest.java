package pw.mlaszczyk.automation.tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.*;
import pw.mlaszczyk.automation.pages.pages.DropDownPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DropDownTest {
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
        page.navigate("https://practice.expandtesting.com/dropdown");
    }

    @Test
    public void shouldSelectOptionsCorrectlyFromDropdowns () {
    DropDownPage dropDownPage = new DropDownPage(page);
    dropDownPage.selectOptionOne();
    assertEquals("1", dropDownPage.getSelectedDropdownValue());
    dropDownPage.selectElementsPerPage();
    assertEquals("50", dropDownPage.getSelectedElementsPerPageValue());
    dropDownPage.selectCountry();
    assertEquals("PL", dropDownPage.getSelectedCountryValue());
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


