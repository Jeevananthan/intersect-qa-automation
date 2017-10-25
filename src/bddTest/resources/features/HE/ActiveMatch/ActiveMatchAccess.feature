@HE @ActiveMatch
Feature: HE - ActiveMatch - ActiveMatchAccess - As a HE Intersect ADMIN user with an ActiveMatch product, I need the ability to access the ActiveMatch+ product in Intersect.

  @MATCH-3010
  Scenario: Active Match section is displayed for Admin users in Intersect HE (MATCH-3109)
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify the "ActiveMatch" nav link is displaying for this user
    And HE I open the Active Match section
    Then HE The Active Match page is displayed
    And HE I successfully sign out

  @MATCH-3010
  Scenario: Active Match section is not displayed for Publishing users in Intersect HE
    Given HE I am logged in to Intersect HE as user type "publishing"
    Then HE I verify the "ActiveMatch" nav link is not displaying for this user
    And HE I successfully sign out

  @MATCH-3010
  Scenario: Active Match section is not displayed for Community users in Intersect HE
    Given HE I am logged in to Intersect HE as user type "community"
    Then HE I verify the "ActiveMatch" nav link is not displaying for this user
    And HE I successfully sign out

  @MATCH-3010
  Scenario: As a HE User with Administrator role with no Legacy: ActiveMatch Events subscription, I can not access Active Match module
    Given SP I am logged in to the Admin page as a Support user
    When SP I search for "2100209" in "HE Accounts"
    And SP I select "Bowling Green State University-Main Campus" from the global search results
    And SP I set the "ActiveMatch Plus" module to "inactive" in the institution page
    And SP I Click the Save Changes button
    Then SP I successfully sign out
    Given HE I am logged in to Intersect HE as user type "limited"
    Then HE I verify the "ActiveMatch" nav link is not displaying for this user
    And HE I successfully sign out
    #cleanup
    Given SP I am logged in to the Admin page as an Admin user
    When SP I search for "2100209" in "HE Accounts"
    And SP I select "Bowling Green State University-Main Campus" from the global search results
    And SP I set the "ActiveMatch Plus" module to "inactive" in the institution page
    And SP I Click the Save Changes button
    And SP I successfully sign out



