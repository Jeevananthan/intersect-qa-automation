@HS
Feature: Support user cannot access Hubs View mode (bug)
  @MATCH-1907
  Scenario: Support user cannot access Hubs View mode (bug)
    Given SP I am logged in to the Admin page as a Support user
    When SP I search for "Adelphi University" as an Institution in the global search box
    And SP I select the following institution "Adelphi University" from the results
    Then SP I verify Hubs view mode for "Adelphi University"
    And SP I successfully sign out
