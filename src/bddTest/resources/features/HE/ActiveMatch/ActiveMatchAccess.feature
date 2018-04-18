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
    And HE I successfully sign out


  @MATCH-3016
  Scenario:  As an HE intersect user, I need the ability to view my student connections from AM so I can see who has made a connection
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I open the Active Match section
    And HE I verify the ActiveMatch page
    Then HE I verify the Headers are present in the Active Match Connections
      |Name|Gender / Ethnicity|Birth Date|Grad Year / GPA|School Name / CEEB|Contact|Address|Created / Modified|
    And HE I verify the following details are present under the new header of "Historical" in the ActiveMatch export connections dropdown Menu
      |Last 7 days|Last 14 days|Last 30 days|Last 60 days|Last 90 days|
    And HE I verify the following details are present under the header of "By School Year" in the ActiveMatch export connections dropdown Menu
      |2017-2018|2016-2017|2015-2016|2014-2015|2013-2014|
    And HE I verify the following headers are present in the ActiveMatch export connections dropdown Menu in the following order
      |Since Last Export|Historical|By School Year|
    And HE I verify the Default drop-down Menu selection to remain "Since Last Export" after all connections are modified
      |Last 7 days|Last 14 days|Last 30 days|Last 60 days|Last 90 days|
    And HE I successfully sign out

    @MATCH-3012
    Scenario: As an HE intersect user, I need the ability to export my student connections from AM so I can import them
              into my own system.
      Given HE I am logged in to Intersect HE as user type "administrator"
      When HE I navigate to the ActiveMatch Tab
      Then HE I verify the following details are present under the new header of "Historical" in the ActiveMatch export connections dropdown Menu
        |Last 7 days|Last 14 days|Last 30 days|Last 60 days|Last 90 days|
      And HE I verify the following details are present under the new header of "By School Year" in the ActiveMatch export connections dropdown Menu
        |2017-2018|2016-2017|2015-2016|2014-2015|2013-2014|
      And HE I verify the following details are present under the new header of "Since Last Export" in the ActiveMatch export connections dropdown Menu
        |connections since last export on|
      When HE I export the ActiveMatchConnections for the current year
      Then HE I verify the downloaded ActiveMatch Cvs file "student-connections.csv" contains the following headers
        |First Name|Last Name|Email|Phone|Address|City|State|Zip|Ethnicity|Gender|BirthDate|SchoolName|Ceeb|Graduation Year|Gpa|Created/Modified Date|Connection Status|
      Then HE I delete the downloaded ActiveMatch Cvs file "student-connections.csv"
      Then HE I successfully sign out
