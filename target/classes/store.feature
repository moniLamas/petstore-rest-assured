@store
Feature: (e2e) Validate store

  @store-inventory
  Scenario: (e2e) Validate that the response has the inventory
    Given the following breeding application that brings us the inventory
    Then the response is 200 for inventory

  @post-order
  Scenario Outline: (e2e) Validate that the response has a order
    Given the following post request that add order
    And the response is 200 for the post order
    Then the body response contains update order "<complete>"

    Examples:
      | complete |
      | true   |