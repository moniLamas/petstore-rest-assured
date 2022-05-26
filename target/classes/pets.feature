Feature: (e2e) Validate pets

  @pets
  Scenario: (e2e) Validate that the response of the pets request is 200
    Given the following get request that brings us the pets list
    Then the response is 200
