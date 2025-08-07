Feature: Login with invalid User Name

  Scenario: Login with invalid User Name
    Given User has invalid user name and valid password
    When User navigates to initial page
      | https://www.saucedemo.com/ |
    Then Following fields should be visible:
      | UsernameField  |
      | PasswordField  |
      | 'Login' button |
    When User provides invalid username and valid password into field and clicks on 'Login' button
    Then User should get logged in and following error pop-up should be displayed:
      | Epic sadface: Username and password do not match any user in this service |