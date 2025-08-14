Feature: Add item to cart
  As a shopper
  I want to add products to my cart
  So that I can purchase them

  @smoke
  Background:
    Given I am logged in as "standard_user"

  Scenario: Add single item to cart
    When I add product "Sauce Labs Backpack" to the cart
    Then the cart badge shows "1"
    And the product "Sauce Labs Backpack" action button is "Remove"
    And the cart contains:
      | Product             | Quantity | PricePresent |
      | Sauce Labs Backpack | 1        | true         |
