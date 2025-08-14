Feature: Locked out users
  @negative
  Scenario: Locked out user cannot login
    Given I am on the login page
    When I login with username "locked_out_user" and password "secret_sauce"
    Then I see error "Sorry, this user has been locked out."
