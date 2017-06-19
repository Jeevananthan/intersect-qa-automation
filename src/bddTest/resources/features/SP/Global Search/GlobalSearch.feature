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
  Scenario: As a support user I want real-time results displayed to me while performing a global search.
            so I can quickly find the person/institution/group/content/filter/event I want to view.
    Given SP I am logged in to the Admin page as a Support user
    Then SP I type into the global search box to show results are returned below the search box in real-time "int"
    Then SP I verify search results were categorized by entity
      | People       | searchResultsTabpeople       |
      | Institutions | searchResultsTabinstitutions |
      | Groups       | searchResultsTabgroups       |
    Then SP I verify that only five or less results are listed in real-time results displayed
      | People       | searchResultsTabpeople       |
      | Institutions | searchResultsTabinstitutions |
      | Groups       | searchResultsTabgroups       |
    Then SP I verify real-time search results are clickable and actionable "int"
    Then SP I verify real-time search results are displayed correctly "int"
      | People       | searchResultsTabpeople       |
      | Institutions | searchResultsTabinstitutions |
      | Groups       | searchResultsTabgroups       |

    And SP I successfully sign out