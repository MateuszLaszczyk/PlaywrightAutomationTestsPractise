package pw.mlaszczyk.automation.tests;


import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pw.mlaszczyk.automation.config.ConfigLoader;
import pw.mlaszczyk.automation.pages.pages.LoginPage;
import pw.mlaszczyk.automation.tests.Setup.BaseTest;


import java.io.ByteArrayInputStream;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Epic("Authentication")
@Feature("Login")
public class LogInWithInvalidPasswordTest extends BaseTest {
    private static String userName;
    private static String baseUrl;
    private static String invalidPassword;
    private LoginPage loginPage;

    @BeforeAll
    static void cfg() {
        invalidPassword = ConfigLoader.get("saucedemo.invalidPassword");
        userName = ConfigLoader.get("saucedemo.username");
        baseUrl = ConfigLoader.get("saucedemo.baseUrl");
    }

    @BeforeEach
    void setUpPages() {
        loginPage = new LoginPage(page);
    }


    @Epic("Login")
    @Feature("Login feature")
    @Story("User provides INVALID password during login")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Invalid Password login")
    @Test
    void testLogInWithInvalidPassword() {
        // 1. Navigate to login page
        page.navigate(baseUrl);
        // 2. Login with provided credentials
        loginPage.login(userName, invalidPassword);
        // 3. Assert that error popup is visible
        assertThat(loginPage.errorMessage()).isVisible();
        // 4. Assert that error popup has text
        assertThat(loginPage.errorMessage()).hasText("Epic sadface: Username and password do not match any user in this service");

        Allure.addAttachment("Page screenshot", new ByteArrayInputStream(page.screenshot()));
    }

}
