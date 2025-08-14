Feature: Login
  As a user
  I want to login
  So that I can access the store

  @smoke @regression
  Scenario Outline: Login with various credentials
    Given I am on the login page
    When I login with username "<username>" and password "<password>"
    Then I <result>

    Examples:
      | username        | password       | result                                |
      | standard_user   | secret_sauce   | see the products page                 |
      | locked_out_user | secret_sauce   | see error "Sorry, this user has been locked out." |
      | invalid_user    | secret_sauce   | see error "Username and password do not match"    |
      | standard_user   | wrong_pass     | see error "Username and password do not match"    |
