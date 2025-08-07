Feature: Login with valid credentials

  Scenario: Login with valid credentials
    Given User has valid credentials
    When User navigates to initial page
      | https://www.saucedemo.com/ |
    Then Following fields should be visible:
      | UsernameField  |
      | PasswordField  |
      | 'Login' button |
    When User provides valid credentials into fields and clicks on 'Login' button
    Then User should get logged in and product page should be displayed