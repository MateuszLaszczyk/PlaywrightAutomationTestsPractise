Feature: Adding item to cart

  Scenario: Add item to cart
    Given User is logged in
    When User adds item to cart
    Then Quantity on cart icon should change to 1
    And Button near item that has been added should change from
      | Add to Cart | -> | Remove |
