Feature: Login

  Scenario: User is able to login using valid LOGIN and PASSWORD
    Given User has valid credentials
    When User navigates to initial page
      | https://www.saucedemo.com/ |
    Then Following fields should be visible:
      | Username |
      | Password |
    And Following button:
      | Login |
    When User provides valid credentials into fields and clicks on 'Login' button
    Then User should get logged in and product page should be displayed

Feature: Login with invalid USER NAME

  Scenario: Login with invalid USER NAME
    Given User has invalid USER NAME and valid PASSWORD
    When User navigates to initial page:
      | https://www.saucedemo.com/ |
    Then Following fields should be visible:
      | Username |
      | Password |
    And Following button:
      | Login |
    When User provides invalid USERNAME and valid PASSWORD into field and clicks on 'Login' button
    Then User shouldnt get logged in and following error pop-up should be displayed:
      | Epic sadface: Username and password do not match any user in this service |

Feature: Login with invalid PASSWORD

  Scenario: Login with invalid PASSWORD
    Given User has invalid PASSWORD and valid USERNAME
    When User navigates to initial page:
      | https://www.saucedemo.com/ |
    Then Following fields should be visible:
      | Username |
      | Password |
    And Following button:
      | Login |
    When User provides valid USERNAME and invalid PASSWORD into fields and clicks on 'Login' button
    Then User shouldnt get logged in and following error pop-up should be displayed:
      | Epic sadface: Username and password do not match any user in this service |