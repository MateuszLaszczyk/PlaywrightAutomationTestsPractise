package pw.mlaszczyk.automation.pages.pages;

import com.microsoft.playwright.Page;
import io.cucumber.java.en.When;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/**
 * Page Object Model (POM) class that encapsulates interactions with the login page
 * of the Saucedemo application using Microsoft Playwright.
 *
 * <p>This class is responsible for performing actions related to the login process,
 * such as entering the username, password, and clicking the login button.
 */
public class loginPage {
    private final Page page;

    /**
     * Constructor that initializes the Playwright {@link Page} instance
     * to interact with the DOM elements.
     *
     * @param page the Playwright page representing the current browser tab
     */
    public loginPage(Page page) {
        this.page = page;
    }

    /**
     * Logs in to the application by filling in the username and password fields,
     * and clicking the login button.
     *
     * @param username the username to input
     * @param password the password to input
     * @param baseUrl the baseurl to input
     */
    public void loginAndVerify(String username, String password,String mainPageUrl) {
        page.locator("input[data-test='username']").fill(username);
        page.locator("input[data-test='password']").fill(password);
        page.locator("input[data-test='login-button']").click();
        assertThat(page).hasURL(mainPageUrl);
    }
}
