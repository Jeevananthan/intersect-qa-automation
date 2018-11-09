@HE @ActiveMatch
Feature: HE - ActiveMatch - ActiveMatchAccess - As an HE Admin user with active ActiveMatch subscription, I can access the ActiveMatch product

  @MATCH-3010
  Scenario: Active Match section is displayed for Admin users in Intersect HE (MATCH-3109)
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And SP I set the "Connection" module to "inactive" in the institution page
    And SP I set the "ActiveMatch Plus" module to "active" in the institution page
    And SP I Click the Save Changes button
    When HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify the "Connection" nav link is displaying for this user
    And HE I open the Active Match section

  @MATCH-3010
  Scenario: Active Match section is not displayed for Publishing users in Intersect HE
    Given HE I am logged in to Intersect HE as user type "publishing"
    Then HE I verify the "ActiveMatch" nav link is not displaying for this user

  @MATCH-3010
  Scenario: Active Match section is not displayed for Community users in Intersect HE
    Given HE I am logged in to Intersect HE as user type "community"
    Then HE I verify the "ActiveMatch" nav link is not displaying for this user

  @MATCH-3010 @MATCH-3023
  Scenario: As a HE User with Administrator role with no ActiveMatch Plus subscription, I can not access Active Match module
    Given SP I am logged in to the Admin page as a Support user
    When SP I search for "2100209" in "HE Accounts"
    And SP I select "Bowling Green State University-Main Campus" from the global search results
    And SP I set the "ActiveMatch Plus" module to "inactive" in the institution page
    And SP I Click the Save Changes button
    Then SP I successfully sign out
    Given HE I am logged in to Intersect HE as user type "limited"
    Then HE I verify the "ActiveMatch" nav link is not displaying for this user

  @MATCH-3519
  Scenario: As an Active Match user,
  I want additional drop-down menu options to filter my Connections to include additional date ranges, easier access to the default view on various devices, and a cl
  so that I don't miss any new or modified connections associated with my institution and so that I have greater flexibility to view additional date ranges that are smaller than a full school year but potentially greater than the "Since Last Export" options.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I navigate to the ActiveMatch Tab
    And HE I verify the ActiveMatch page
    And HE I verify the following details are present under the new header of "Historical" in the ActiveMatch export connections dropdown Menu
    |Last 7 days|Last 14 days|Last 30 days|Last 60 days|Last 90 days|
    And HE I verify the following headers are present in the ActiveMatch export connections dropdown Menu in the following order
    |Since Last Export|Historical|By School Year|
    And HE I verify the Header after selecting "Historical" for the following selection in the ActiveMatch export connections
    |Last 7 days|Last 14 days|Last 30 days|Last 60 days|Last 90 days|
    And HE I verify the Header "Since Last Export"
    And HE I verify the Default drop-down Menu selection to remain "Since Last Export" after all connections are modified
      |Last 7 days|Last 14 days|Last 30 days|Last 60 days|Last 90 days|