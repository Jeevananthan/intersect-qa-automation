@MATCH-2918
Feature: As a HE Intersect ADMIN or PUBLISHER with Intersect Presence or Legacy ActiveMatch Events product, I need the
  ability to access the ActiveMatch product in Intersect.

  Scenario: Active Match section is displayed for Admin users in Intersect HE (MATCH-3109)
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I open the Active Match section
    Then HE The Active Match page is displayed
    And HE I successfully sign out

  Scenario: Active Match section is not displayed for Publishing users in Intersect HE
    Given HE I am logged in to Intersect HE as user type "publishing"
    Then HE I verify the "ActiveMatch" nav link is not displaying for this user
    And HE I successfully sign out

  Scenario: Active Match section is not displayed for Community users in Intersect HE
    Given HE I am logged in to Intersect HE as user type "community"
    Then HE I verify the "ActiveMatch" nav link is not displaying for this user
    And HE I successfully sign out


