Feature: Logout

  Scenario: User is able to logout
    Given User is logged in
    When User hover over hamburger menu icon and clicks
    Then User will see 'Logout' button
    When User click on that button
    Then User will get logged out