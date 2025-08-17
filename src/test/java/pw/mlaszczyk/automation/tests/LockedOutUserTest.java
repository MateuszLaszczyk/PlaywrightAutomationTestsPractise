package pw.mlaszczyk.automation.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pw.mlaszczyk.automation.config.ConfigLoader;
import pw.mlaszczyk.automation.pages.pages.LoginPage;


import java.io.ByteArrayInputStream;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LockedOutUserTest extends BaseTest {
    private static String lockedOutUserName;
    private static String password;
    private static String baseUrl;
    private LoginPage loginPage;


    @BeforeAll
    public static void cfg() {
        lockedOutUserName = ConfigLoader.get("saucedemo.lockedOutUser");
        password = ConfigLoader.get("saucedemo.password");
        baseUrl = ConfigLoader.get("saucedemo.baseUrl");
    }

    @BeforeEach
    void setUpPages() {
        loginPage = new LoginPage(page);
    }

    @Epic("Lockedout ")
    @Feature("Lockedout user feature")
    @Story("User is not able to login if his account is lockedout")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Lockedout user test")
    @Test
    void lockedOutUserTest() {
        // 1. Navigate to login page
        page.navigate(baseUrl);
        // 2. Login with provided credentials
        loginPage.login(lockedOutUserName, password);
        // 3. Assert that error popup is visible
        assertThat(loginPage.errorMessage()).isVisible();
        // 4. Assert that error popup has text
        assertThat(loginPage.errorMessage()).hasText("Epic sadface: Sorry, this user has been locked out.");

        Allure.addAttachment("Page screenshot", new ByteArrayInputStream(page.screenshot()));
    }

}
