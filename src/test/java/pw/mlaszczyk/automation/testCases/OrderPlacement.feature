Feature: Place order
  As a shopper
  I want to purchase products

  @smoke
  Scenario: Place order with one item
    Given I am logged in as "standard_user"
    And I add product "Sauce Labs Backpack" to the cart
    When I go to the cart
    Then the cart shows:
      | Product             | Quantity |
      | Sauce Labs Backpack | 1        |
    When I proceed to checkout
    And I provide shipping information:
      | FirstName | LastName | ZipCode |
      | Jan       | Kowalski | 80-001  |
    And I continue to overview
    Then I see order summary with item total, tax and total
    When I finish the order
    Then I see order confirmation
    And I see a "Back Home" button
