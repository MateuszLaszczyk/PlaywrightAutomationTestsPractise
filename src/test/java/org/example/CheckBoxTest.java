package org.example;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;



public class CheckBoxTest {
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
        page.navigate("https://demoqa.com/checkbox");
    }

    @Test
    public void selectDownloadsAndFiles() {
        Checkbox checkbox = new Checkbox(page);
        ListButton listButton = new ListButton(page);
        checkbox.selectHomeCheckbox();
        listButton.expandList();
        checkbox.selectDownloadCheckbox();
        checkbox.selectFilesToDownload();
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

