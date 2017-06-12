@HS
Feature:  As an HS user, I want to be able to access the features of the RepVisits module.
  @MATCH-1904
  Scenario: HS user cannot access Hubs View mode (bug)
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    When SP I search for "Adelphi University" as an Institution in the global search box
    And SP I select the following institution "Adelphi University" from the results
    Then SP I verify Hubs view mode for "Adelphi University"
    And HS I successfully sign out
  @MATCH-1907
  Scenario: Support user cannot access Hubs View mode (bug)
    Given SP I am logged in to the Admin page as a Support user
    When SP I search for "Adelphi University" as an Institution in the global search box
    And SP I select the following institution "Adelphi University" from the results
    Then SP I verify Hubs view mode for "Adelphi University"
    And SP I successfully sign out
