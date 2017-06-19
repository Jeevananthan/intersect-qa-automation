@HE
Feature: As a HE user, I want to be able to use the Global search to help me find records.

  @MATCH-1069
  Scenario: As a HE user, I need to be able to access Advanced Search for community entities.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I go to the advanced search page for "Institutions"
    Then HE I go to the advanced search page for "People"
    Then HE I go to the advanced search page for "Groups"
    And HE I successfully sign out

  @MATCH-590 @MATCH-592 @MATCH-593 @MATCH-594 @MATCH-596 @MATCH-1051 @MATCH-1052 @MATCH-1053 @MATCH-1110
  Scenario: As a HE user I want real-time results displayed to me while performing a global search.
            so I can quickly find the person/institution/group/content/filter/event I want to view.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I type into the global search box to show results are returned below the search box in real-time "int"
    Then HE I verify search results were categorized by entity
      | People       | searchResultsTabpeople       |
      | Institutions | searchResultsTabinstitutions |
      | Groups       | searchResultsTabgroups       |
    Then HE I verify that only five or less results are listed in real-time results displayed
      | People       | searchResultsTabpeople       |
      | Institutions | searchResultsTabinstitutions |
      | Groups       | searchResultsTabgroups       |
    Then HE I verify real-time search results are clickable and actionable "int"
    Then HE I verify real-time search results are displayed correctly "int"
      | People       | searchResultsTabpeople       |
      | Institutions | searchResultsTabinstitutions |
      | Groups       | searchResultsTabgroups       |
    And HE I successfully sign out