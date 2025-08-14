Feature: Logout
  Scenario: User logs out successfully
    Given I am logged in as "standard_user"
    When I log out
    Then I see the login page
