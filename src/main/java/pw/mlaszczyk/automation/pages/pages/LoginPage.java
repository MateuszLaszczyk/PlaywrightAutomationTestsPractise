package pw.mlaszczyk.automation.pages.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;


import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

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
     * @param username        the username to input
     * @param password        the password to input
     * @param baseUrl         the baseurl to input
     * @param invalidUserName the InvalidUserName to input
     * @param invalidPassword the invalid password to input
     * @param lockedOutUserName the lockedout user
     */
    public void loginAndVerify(String username, String password, String mainPageUrl) {
        page.locator("input[data-test='username']").fill(username);
        page.locator("input[data-test='password']").fill(password);
        page.locator("input[data-test='login-button']").click();
        assertThat(page).hasURL(mainPageUrl);
    }

    public void loginWithInvalidUserNameAndVerify(String InvalidUserName, String password) {
        page.locator("input[data-test='username']").fill(InvalidUserName);
        page.locator("input[data-test='password']").fill(password);
        page.locator("input[data-test='login-button']").click();
        Locator ErrorPopup = page.locator("h3[data-test='error']");
        assertThat(ErrorPopup).hasText("Epic sadface: Username and password do not match any user in this service");

    }

    public void loginWithInvalidPasswordAndVerify(String username, String invalidPassword) {
        page.locator("input[data-test='username']").fill(username);
        page.locator("input[data-test='password']").fill(invalidPassword);
        page.locator("input[data-test='login-button']").click();

        Locator ErrorPopup = page.locator("h3[data-test='error']");
        assertThat(ErrorPopup).hasText("Epic sadface: Username and password do not match any user in this service");
    }

    public void logOutFromTheWebSiteAndVerify(String baseUrl) {
        Locator hamburgerIcon = page.locator("#react-burger-menu-btn");
        Locator logOutButton = page.locator("a[data-test='logout-sidebar-link']");
        Locator userName = page.locator("input[data-test='username']");
        hamburgerIcon.click();
        logOutButton.click();
        assertThat(userName).isVisible();
    }

    public void checkIfLockedOutUserCanLogin(String lockedOutUserName, String Password) {
        page.locator("input[data-test='username']").fill(lockedOutUserName);
        page.locator("input[data-test='password']").fill(Password);
        page.locator("input[data-test='login-button']").click();
        Locator errorLoginPopup = page.locator("h3[data-test='error']");
        assertThat(errorLoginPopup).hasText("Epic sadface: Sorry, this user has been locked out.");
    }
}

