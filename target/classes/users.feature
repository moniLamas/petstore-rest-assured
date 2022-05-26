Feature: (e2e) Validate users

  @usersPost
  Scenario Outline: (e2e) Validate post one user
    Given the following post request that add one user
    And the response is 200 for the post user
    And the body response contains key id
    Then the body response contains the "<message>" of the user created

    Examples:
      | message  |
      | 101 |
