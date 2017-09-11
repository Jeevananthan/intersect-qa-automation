@HE
Feature: As a HE user, I want to be able to access HUBS View Mode
  @MATCH-1815
  Scenario: HE user cannot access Hubs View mode (bug)
    Given HE I want to login to the HE app using "jorgetesthobsons@gmail.com" as username and "Hobsons2016!" as password
    When HS I go to the Counselor Community
    And HS I access the INSTITUTION page
    Then SP I verify Hubs view mode for "Adelphi University"
    And HE I successfully sign out
