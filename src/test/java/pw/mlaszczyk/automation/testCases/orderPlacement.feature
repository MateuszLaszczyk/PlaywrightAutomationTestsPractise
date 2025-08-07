Feature: Place order

  Scenario: Login with valid credentials and place order
    Given User navigates to login page
    When User logs in with valid credentials
    Then User should be redirected to the product page
    When User adds any item to the cart
    Then Cart quantity should be udpated
    When User navigates to cart
    Then User is able to see following information
      | Quantity | ProductName | ProductDescription | ProductPrice | RemoveButton | ContinueShoppingButton | Checkout |
    When User click on 'Checkout' button
    Then 'Checkout: Your Information' page should be displayed containing three fields like:
      | First Name     |
      | Last Name      |
      | Zip/PostalCode |
    When User provides valid credentials to all the fields and click 'Continue'
    Then 'Checkout: Overview' page should be displayed with following information:
      | Quantity             | ProductName           |
      | ProductDescription   | ProductPrice          |
      | Payment Information: | Shipping Information: |
      | Price Total          | Item total            |
      | Tax                  | Total:                |
    When User click on 'Finish' button
    Then 'Checkout: Complete!' page should be displayed with following information:
      | Green checkmark logo           |
      | OrderPlacedInformation         |
      | OrderPlacedShippingDescription |
      | 'Back Home' button             |