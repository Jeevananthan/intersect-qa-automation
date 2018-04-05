@SP
Feature: As a support user, I want to be able to use the Global search to help me find records.

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
    Then SP I type into the global search box to show results are returned below the search box in real-time "admin"
    Then SP I verify real-time search results were categorized by entity
      | People | Institutions | Groups |
    Then SP I verify that only five or less results are listed in real-time results displayed
      | User | Institutions | Groups |
    Then SP I verify real-time search results are clickable and actionable "admin"
    Then SP I verify real-time search layouts are displayed correctly "admin"
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
      | People       | searchResultsTabpeople       |
      | Institutions | searchResultsTabinstitutions |
      | Groups       | searchResultsTabgroups       |
    Then SP I verify advanced search tab layouts are displayed correctly "admin"
      | People       | searchResultsTabpeople       |
      | Institutions | searchResultsTabinstitutions |
      | Groups       | searchResultsTabgroups       |
    And SP I successfully sign out