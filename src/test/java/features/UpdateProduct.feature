Feature: Updating product

  Scenario Outline: Verify that product is updated correctly
    Given I hit the URL of the put products api endpoint
    When I pass the URL of products in the request with "<ProductNum>"
    Then I receive the response code as 200
    And I receive the response body with id as "<ProductNum>"

    Examples:
    | ProductNum |
    | 6          |

