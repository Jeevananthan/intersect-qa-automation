@SP
Feature: As a Support User, I want to be able to access HUBS Edit Mode

  @MATCH-1907
  Scenario: Support user cannot access Hubs View mode (bug)
    Given SP I am logged in to the Admin page as a Support user
    When SP I search for "Bowling Green State University-Main Campus" as an Institution in the global search box
    And SP I select "Bowling Green State University-Main Campus" from the global search results
    Then SP I verify Hubs view mode for "Bowling Green State University-Main Campus"
    And SP I successfully sign out
