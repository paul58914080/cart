@Cart
Feature: User would like to get carts
  Background:
    Given the following carts exists in the library
      | code | description                 |
      | 1    | Twinkle twinkle little star |
      | 2    | Johnny Johnny Yes Papa      |

  Scenario: User should be able to get all carts
    When user requests for all carts
    Then the user gets the following carts
      | code | description                 |
      | 1    | Twinkle twinkle little star |
      | 2    | Johnny Johnny Yes Papa      |

  Scenario: User should be able to get carts by code
    When user requests for carts by code "1"
    Then the user gets the following carts
      | code | description                 |
      | 1    | Twinkle twinkle little star |

  Scenario: User should get an appropriate NOT FOUND message while trying get carts by an invalid code
    When user requests for carts by id "10000" that does not exists
    Then the user gets an exception "Cart with code 10000 does not exist"