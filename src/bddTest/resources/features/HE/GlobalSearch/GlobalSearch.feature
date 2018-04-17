@HE
Feature: HE - Global Search - As a HE user, I want to be able to use the Global search to help me find records.

  @MATCH-1069
  Scenario: As a HE user, I need to be able to access Advanced Search for community entities.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I go to the advanced search page for "Institutions"
    Then HE I go to the advanced search page for "People"
    Then HE I go to the advanced search page for "Groups"
    And HE I successfully sign out


  @MATCH-590 @MATCH-592 @MATCH-593 @MATCH-594 @MATCH-596 @MATCH-1051 @MATCH-1052 @MATCH-1053 @MATCH-1110
  Scenario: As a HE user I want real-time results displayed to me while performing a global search.
            So I can quickly find the person/institution/group/content/filter/event I want to view.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I type into the global search box to show results are returned below the search box in real-time "admin"
    Then HE I verify real-time search results were categorized by entity
      | People | Institutions | Groups |
    Then HE I verify that only five or less results are listed in real-time results displayed
      | User | Institutions | Groups |
    Then HE I verify real-time search results are clickable and actionable "admin"
    Then HE I verify real-time search layouts are displayed correctly "admin"
      | People | Institutions | Groups |
    And HE I successfully sign out

  @MATCH-1394
  Scenario: As an HE freemium user for a Premium Legacy Hubs only user I should NOT be able to see the global search box in Intersect
            so I cannot find too much value in my limited access to Community.
    Given SP I am logged in to the Admin page as a Support user
    And SP I set HE Account Subscriptions "Alma"
      | Legacy: Hub page management       | active   |
      | Legacy: Community                 | inactive |
      | Intersect Awareness Subscription  | inactive |
      | Intersect Presence Subscription   | inactive |
      | Legacy: ActiveMatch Events        | inactive |
      | ActiveMatch Plus                  | inactive |

    And SP I successfully sign out
    Given HE I want to login to the HE app using "daniel.kirtman@hobsons.com" as username and "internHOBS25%" as password
    Then HE I verify there is no global search options available
    And HE I successfully sign out
    Given SP I am logged in to the Admin page as a Support user
    And SP I set HE Account Subscriptions "Alma"
      | Legacy: Hub page management       | active |
      | Legacy: Community                 | active |
      | Intersect Awareness Subscription  | active |
      | Intersect Presence Subscription   | active |
      | Legacy: ActiveMatch Events        | active |
      | ActiveMatch Plus                  | active |
    And SP I successfully sign out

  @MATCH-1063 @MATCH-1064 @MATCH-1065 @MATCH-1066 @MATCH-1067 @MATCH-1073 @MATCH-1074 @MATCH-1075
  Scenario: As a HE user I want to be taken to a search results page after performing a "hard" global search.
            So I can see all of my results not just the first few results within the real-time drop down.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I type into the global search box and hit return/enter key to display advanced search results "admin"
    Then HE I verify the search results page defaults me to the users tab after performing a global search
    Then HE I type into the global search box and click search icon to display advanced search results "admin"
    Then HE I verify advanced search results were categorized by entity
      | People       | searchResultsTabpeople       |
      | Institutions | searchResultsTabinstitutions |
      | Groups       | searchResultsTabgroups       |
    Then HE I verify that only five or less results are listed for advanced search results displayed by category
      | People | Institutions | Groups |
    Then HE I verify advanced search tab layouts are displayed correctly "admin"
      | People | Institutions | Groups |
    And HE I successfully sign out


  @MATCH-1394
  Scenario: As an HE freemium user or a Premium Legacy Hubs only user I should NOT be able to see the global search box in Intersect
            so I cannot find too much value in my limited access to Community.
    Given HE I am logged in to Intersect HE as user type "limited"
    Then HE I verify there is no global search options available
    And HE I successfully sign out

  @MATCH-1545
  Scenario: As a HE user I want to see general recruitment territory details on all users returned to me when during advanced searches for people.
            So I can find the other community user I want to network with more efficiently.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify advanced search returns the HS user's general description field below the title and institution fields "MatchSupportUIQA4"
    And HE I successfully sign out

  @MATCH-932 @MATCH-934 @MATCH-1076 @MATCH-1077 @MATCH-1078
  Scenario: As a Community user I want to perform an advanced search for other Community users using any combination of the fields below.
            So I can more accurately find the users I want to network with in the Community.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify I can perform an advanced search utilizing any combination of fields for "People"
      | Keyword                                | Admin                                |
      | First Name                             | Admin                                |
      | Last Name                              | Administrator                        |
      | Email                                  | purpleheautomation+Hobsons@gmail.com |
      | Alma Mater                             | UNC Capel Hill                       |
      | Institution                            | Hobsons                              |
      | Institution Type                       | All                                  |
      | Position                               | Manager                              |
      | Institution State                      | Ohio                                 |
      | Zipcode                                | 45241                                |
      | State Served                           | Alabama                              |
      | County Served                          | Autauga                              |
      | Advises Students on Admissions Process | No                                   |
      | Schedules College Visits               | No                                   |
    And HE I successfully sign out

  @MATCH-933 @MATCH-1103 @MATCH-1105 @MATCH-1107
  Scenario: As a Community user I want to perform an advanced search for groups using any combination of the fields below.
            So I can more accurately find the groups I want to join with in the Community.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify I can perform an advanced search utilizing any combination of fields for "Groups"
      | Keyword     | Hobsons                                    |
      | Name        | Hobsons                                    |
      | Description |  |
      | Type        | Public                                     |
    And HE I successfully sign out

  @MATCH-934 @MATCH-1104 @MATCH-1106 @MATCH-1108
  Scenario: As a Community user I want to perform an advanced search for institutions using any combination of the fields below.
            So I can more accurately find the institutions I want to follow with in the Community.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify I can perform an advanced search utilizing any combination of fields for "Higher Education"
      | Keyword                       | Arkansas                   |
      | Institution Type              | Higher Education           |
      | Name                          | Arkansas State             |
      | College Type                  | Standard (4 Year)          |
      | School Type                   | Public                     |
      | Degree                        | 4                          |
      | City                          | Jonesboro                  |
      #Search Institution by state is not working ATM --- MATCH-2219 created 6/26/17
      #| State                         | Arkansas                   |
      | Postal Code                   | 72401                      |

    Then HE I verify I can perform an advanced search utilizing any combination of fields for "High School"
      | Keyword                       | Southern                   |
      | Institution Type              | High School                |
      | Name                          | Southern High School       |
      | Type                          | Public                     |
      | City                          | Wymore                     |
      | State                         | Nebraska                   |
      | Postal Code                   | 68466                      |
      | Charter School                | Unknown                    |
      | Title I Eligible              | No                         |
      | College Going Rate            | 59-100                     |
    And HE I successfully sign out

  @MATCH-1400
  Scenario: As a HE user I want to preform a global and advanced search for groups that do not return HS results.
  So I can ensure only HS groups are returned.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify real-time search results do not return any results for HS groups "New Test HS Group"
    Then HE I verify advanced search results do not return any results for HS groups "New Test HS Group"
      | Groups |
    And HE I successfully sign out