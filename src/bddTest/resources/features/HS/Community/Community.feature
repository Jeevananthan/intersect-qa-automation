@HS
Feature: As a HS user, I want to be able to access HUBS Edit Mode

  @MATCH-1904
  Scenario: HS user cannot access Hubs View mode (bug)
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    When SP I search for "The University of Alabama" as an Institution in the global search box
    And SP I select "The University of Alabama" from the global search results
    Then SP I verify Hubs view mode for "The University of Alabama"
    And HS I successfully sign out
