@store
Feature: (e2e) Validate store

  @inventory
  Scenario: (e2e) Validate that the response has the inventory
    Given the following breeding application that brings us the inventory
    Then the response is 200 for inventory