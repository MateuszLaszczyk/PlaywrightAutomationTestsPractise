package pw.mlaszczyk.automation.tests;


import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pw.mlaszczyk.automation.config.configLoader;
import pw.mlaszczyk.automation.pages.pages.loginPage;

import java.io.ByteArrayInputStream;

public class LogInWithInvalidPasswordTest extends BaseTest {
    private static String userName;
    private static String baseUrl;
    private static String invalidPassword;

    @BeforeAll
    static void loadConfig() {
        invalidPassword = configLoader.get("saucedemo.invalidPassword");
        userName = configLoader.get("saucedemo.username");
        baseUrl = configLoader.get("saucedemo.baseUrl");
    }


    @Epic("Logging in ")
    @Feature("Logging in feature")
    @Story("User provides in valid password during logging in")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Invalid Password login")
    @Test
    void testLogInWithInvalidPassword() {
        page.navigate(baseUrl);
        loginPage login = new loginPage(page);
        login.loginWithInvalidPasswordAndVerify(userName, invalidPassword);

        Allure.addAttachment("Page screenshot", new ByteArrayInputStream(page.screenshot()));
    }

}
