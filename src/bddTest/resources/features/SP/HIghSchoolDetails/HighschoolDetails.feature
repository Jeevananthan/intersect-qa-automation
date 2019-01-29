@SP
Feature: SP - Verify High School details on the Support page as a Support Hobsons user
  @MATCH-1670
  Scenario: As a Support User I can access Hubs view mode
    Given SP I am logged in to the Admin page as a Sales Ops user
    When SP I search for "55033USPU"
    Then SP I am able to see "Int Qa High School 4" institution in the results for HS school
    Then SP I verify real-time search results are clickable and actionable "Int Qa High School 4"
    And SP I verify High School client details with following data
    | NID    | 55033USPU                            |
    | NGUID  | 04d3b77e-18f5-43cd-a585-101268b1178b |
    | Client | Int Qa High School 4                 |


