Feature: Getting products from the API

  Background:
    Given I hit the URL of the get products api endpoint
    When  I pass the URL of products in the request

  Scenario: Verify the GET api for the products
    Then I receive the response code as 200

  Scenario: Verify the rate of the first product is correct
    Then I verify that the rate of the first product is "3.9"


