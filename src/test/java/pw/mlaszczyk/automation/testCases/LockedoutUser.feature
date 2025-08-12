Feature: Lockedout users

  Scenario: User is not able to login into the application if his account is lockedout
    Given User account is lockedout
    When User triest to login into lockedout account
    Then He will not be able to and following error message will be displayed:
      | Epic sadface: Sorry, this user has been locked out. |
