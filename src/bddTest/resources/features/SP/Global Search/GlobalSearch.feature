@SP
Feature: As a support user, I want to be able to use the Global search to help me find records.

  @MATCH-1069
  Scenario: As a support user, I need to be able to access Advanced Search for community entities.
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I go to the advanced search page for "Institutions"
    Then SP I go to the advanced search page for "People"
    Then SP I go to the advanced search page for "Groups"
