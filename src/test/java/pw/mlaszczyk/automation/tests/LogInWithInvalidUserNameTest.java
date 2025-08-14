package pw.mlaszczyk.automation.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import pw.mlaszczyk.automation.config.ConfigLoader;
import pw.mlaszczyk.automation.pages.pages.LoginPage;


import java.io.ByteArrayInputStream;

public class LogInWithInvalidUserNameTest extends BaseTest {
    private static String invalidUserName;
    private static String password;
    private static String baseUrl;
    private LoginPage loginPage;

    @BeforeAll
    static void loadConfig() {
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
        page.navigate(baseUrl);

        loginPage.loginWithInvalidUserName(invalidUserName, password);
        loginPage.asserThatInvalidUserNameDoesntWork();

        Allure.addAttachment("Page screenshot", new ByteArrayInputStream(page.screenshot()));
    }
}
