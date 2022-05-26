Feature: (e2e) Validate pets

  @petsAll
  Scenario: (e2e) Validate that the response of the pets request is 200
    Given the following get request that brings us the pets list
    Then the response is 200

  @petsAvailable
  Scenario: (e2e) Validate that pets are in available status
    Given the following get request that brings us the pets availables
#    And response message
    Then the response is 200 for avalaibles


