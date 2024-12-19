Feature: Getting products from the API


  Scenario: Verify the GET api for the products
    Given I hit the URL of the get products api endpoint
    When  I pass the URL of products in the request
    Then I receive the response code as 200

  Scenario : Verify the rate of the first product is correct
    Given I hit the URL of the get products api endpoint
    When  I pass the URL of products in the request
    Then I verify that the rate of the first product is "3.9"


