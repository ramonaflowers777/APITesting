Feature: Insert product using POST API

  Scenario Outline: Validating post product api
    Given I hit the URL of the post products api endpoint
    When I pass the URL of products in the request
    And I pass the request body of product title "<productTitle>"
    Then I receive the response code as 200
    And I receive the response body after post with id as "<id>"

    Examples:
      | productTitle | id |
      | Shoes        | 21 |