@SP
Feature: SP - Global Search - Global Search - As a support user, I want to be able to use the Global search to help me find records.

  @MATCH-1069
  Scenario: As a support user, I need to be able to access Advanced Search for community entities.
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I go to the advanced search page for "Institutions"
    Then SP I go to the advanced search page for "People"
    Then SP I go to the advanced search page for "Groups"
    And SP I successfully sign out

  @MATCH-590 @MATCH-592 @MATCH-593 @MATCH-594 @MATCH-596 @MATCH-1051 @MATCH-1052 @MATCH-1053 @MATCH-1110
  Scenario: As a SP user I want real-time results displayed to me while performing a global search.
            So I can quickly find the person/institution/group/content/filter/event I want to view.
    Given SP I am logged in to the Admin page as a Support user
    Then SP I type into the global search box and hit return/enter key to display advanced search results "Match"
    Then SP I verify real-time search results were categorized by entity
      | People | Institutions | Groups |
    Then SP I verify that only five or less results are listed in real-time results displayed
      | User | Institutions | Groups |
    Then SP I verify real-time search results are clickable and actionable "Match"
    Then SP I verify real-time search layouts are displayed correctly "Match"
      | People | Institutions | Groups |
    And SP I successfully sign out

  @MATCH-1063 @MATCH-1064 @MATCH-1065 @MATCH-1066 @MATCH-1067 @MATCH-1073 @MATCH-1074 @MATCH-1075
  Scenario: As a SP user I want to be taken to a search results page after performing a "hard" global search.
            So I can see all of my results not just the first few results within the real-time drop down.
    Given SP I am logged in to the Admin page as a Support user
    Then SP I type into the global search box and hit return/enter key to display advanced search results "admin"
    Then SP I verify the search results page defaults me to the users tab after performing a global search
    Then SP I type into the global search box and click search icon to display advanced search results "admin"
    Then SP I verify advanced search results were categorized by entity
      | People       | searchResultsTabpeople       |
      | Institutions | searchResultsTabinstitutions |
      | Groups       | searchResultsTabgroups       |
    Then SP I verify that only five or less results are listed for advanced search results displayed by category
      | People | Institutions | Groups |
    Then SP I verify advanced search tab layouts are displayed correctly "admin"
      | People | Institutions | Groups |
    And SP I successfully sign out

  @MATCH-932 @MATCH-934 @MATCH-1076 @MATCH-1077 @MATCH-1078 @MATCH-1723
  Scenario: As a Community user I want to perform an advanced search for other Community users using any combination of the fields below.
  So I can more accurately find the users I want to network with in the Community.
    Given SP I am logged in to the Admin page as a Support user
    Then SP I verify I can perform an advanced search utilizing any combination of fields for "People"
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
    And SP I successfully sign out

  @MATCH-933 @MATCH-1103 @MATCH-1105 @MATCH-1107
  Scenario: As a Community user I want to perform an advanced search for groups using any combination of the fields below.
  So I can more accurately find the groups I want to join with in the Community.
    Given SP I am logged in to the Admin page as a Support user
    Then SP I verify I can perform an advanced search utilizing any combination of fields for "Groups"
      | Keyword     | Hobsons                                    |
      | Name        | Hobsons                                    |
      | Description | Stay up to date on what's new with Hobsons |
      | Type        | Public                                     |
    And SP I successfully sign out

  @MATCH-934 @MATCH-1104 @MATCH-1106 @MATCH-1108
  Scenario: As a Community user I want to perform an advanced search for institutions using any combination of the fields below.
            So I can more accurately find the institutions I want to follow with in the Community.
    Given SP I am logged in to the Admin page as a Support user
    Then SP I verify I can perform an advanced search utilizing any combination of fields for "Higher Education"
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
    Then SP I verify I can perform an advanced search utilizing any combination of fields for "High School"
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
    And SP I successfully sign out