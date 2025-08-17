package pw.mlaszczyk.automation.pages.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * Page Object Model (POM) for the Saucedemo login page.
 * Unified with ProductPage: locators first, then actions.
 */
public class LoginPage {
    private final Page page;

    public LoginPage(Page page) {
        this.page = page;
    }

    // --- Locators ---
    public Locator usernameInput() { return page.locator("input[data-test='username']"); }
    public Locator passwordInput() { return page.locator("input[data-test='password']"); }
    public Locator loginButton()   { return page.locator("input[data-test='login-button']"); }
    public Locator menuButton()    { return page.locator("#react-burger-menu-btn"); }
    public Locator logoutLink()    { return page.locator("a[data-test='logout-sidebar-link']"); }
    public Locator errorMessage()  { return page.locator("h3[data-test='error']"); }

    // --- Actions ---
    /** Performs a standard login. */
    public void login(String username, String password) {
        usernameInput().clear();
        usernameInput().fill(username);
        passwordInput().clear();
        passwordInput().fill(password);
        loginButton().click();
    }

    /** Logs out from the application. */
    public void logout() {
        menuButton().click();
        logoutLink().click();
    }
}
