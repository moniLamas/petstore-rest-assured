@users
Feature: (e2e) Validate users

  @user-post
  Scenario Outline: (e2e) Validate post one user
    Given the following post request that add one user
    And the response is 200 for the post user
    And the body response contains key "<id>"
    Then the body response contains the "<message>" of the user created

    Examples:
      | id     | message |
      | 100511 | 100511  |

  @users-get
  Scenario Outline: (e2e) Validate that the response has the new user
    Given the following get request which brings us "<username>"
    And the response is 200 for the get user
    Then the body response contains the "<email>"
    Examples:
      | username    | email              |
      | georgeLucas | glucas@petshop.com |

