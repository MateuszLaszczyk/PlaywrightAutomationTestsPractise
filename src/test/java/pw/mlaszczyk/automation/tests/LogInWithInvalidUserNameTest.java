package pw.mlaszczyk.automation.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import pw.mlaszczyk.automation.config.ConfigLoader;
import pw.mlaszczyk.automation.pages.pages.LoginPage;
import pw.mlaszczyk.automation.tests.Setup.BaseTest;


import java.io.ByteArrayInputStream;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Epic("Authentication")
@Feature("Login")
public class LogInWithInvalidUserNameTest extends BaseTest {
    private static String invalidUserName;
    private static String password;
    private static String baseUrl;
    private LoginPage loginPage;


    @BeforeAll
    static void cfg() {
        invalidUserName = ConfigLoader.get("saucedemo.invalidusername");
        password = ConfigLoader.get("saucedemo.password");
        baseUrl = ConfigLoader.get("saucedemo.baseUrl");
    }

    @BeforeEach
    void setUpPages() {
        loginPage = new LoginPage(page);

    }

    @Epic("Login")
    @Feature("Login feature ")
    @Story("User tries to login with INVALID user name")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Login with invalid user name")
    @Test
    void loginWithInvalidUserNameAndVerify() {
        // 1. Navigate to the website
        page.navigate(baseUrl);
        // 2. Login with provided credentials
        loginPage.login(invalidUserName, password);
        // 3. Assert that error pop up is visible
        assertThat(loginPage.errorMessage()).isVisible();
        //4 Assert that error pop up has text
        assertThat(loginPage.errorMessage()).hasText("Epic sadface: Username and password do not match any user in this service");


        Allure.addAttachment("Page screenshot", new ByteArrayInputStream(page.screenshot()));
    }
}
