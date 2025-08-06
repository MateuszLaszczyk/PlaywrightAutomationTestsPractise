package pw.mlaszczyk.automation.pages.pages;

import com.microsoft.playwright.Page;

/**
 * Page Object Model (POM) class that encapsulates interactions with the login page
 * of the Saucedemo application using Microsoft Playwright.
 *
 * <p>This class is responsible for performing actions related to the login process,
 * such as entering the username, password, and clicking the login button.
 */
public class LoginPage {
    private final Page page;

    /**
     * Constructor that initializes the Playwright {@link Page} instance
     * to interact with the DOM elements.
     *
     * @param page the Playwright page representing the current browser tab
     */
    public LoginPage(Page page) {
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
    public void login(String username, String password) {
        page.fill("#user-name", username);
        page.fill("#password", password);
        page.click("#login-button");
    }
}
