@SM
Feature: SM - SuperMatchSearch - In order for the Why? drawer fit score breakdown section to be more intuitive for the
  HS student, we want to add a legend that provides an explanation of what each icon in the fit score breakdown means.

  @MATCH-4266 @MATCH-4269
  Scenario: As a HS student want to see a legend that provides an explanation of what each icon in the fit score breakdown means.
    Given SM I am logged in to SuperMatch through Family Connection
    And SM I skip the onboarding modals
    When I select the following data from the Admission Fit Criteria
      | GPA (4.0 scale) | 4 |
      | SAT Composite   | 400 |
      | ACT Composite   | 3   |
      | Acceptance Rate | 25% or Lower |
    And SM I select the "Accepts AP Credits" checkbox from the "Admission" fit criteria
    And SM I move "Accepts AP Credits" from the Must Have box to the Nice to Have box
    Then SM I verify that the appropriate legend is displayed in the Why Drawer in position "1", according to the following data:
    | Match |
    | Close Match |
    | Data Unknown |
    | Doesn't Match |
    | X out of X Must Have criteria are a match |
    | X out of X Nice to Have criteria are a match |


  Scenario: As a HS student viewing the Why drawer of a particular college in my search results,
  I want to see the actual athletics data for the college so I can clearly see what matched
    Given SM I am logged in to SuperMatch through Family Connection
    And SM I skip the onboarding modals
    And SM I remove the "Athletics" fit criteria from the Must Have box or Nice to Have box
    And SM I clear pinned schools list
    When SM I click "Athletics" filter criteria tab
    And SM I press button "ADD SPORT"
    And SM I pick "Archery" from the dropdown "supermatch-athletics-search"
    And SM I press button "ADD"
    And SM I press Why button for the first college in results with score 100%
    Then I check if I can see "Athletics [1]" on the page
    And  I check if I can see "The following athletics are offered:" on the page
    And  I check if I can see "Archery" on the page

  Scenario: As a HS student viewing the Why drawer of a particular college in my search results,
  I want to see the actual athletics data for the college so I can clearly see what doesn't match
    Given SM I am logged in to SuperMatch through Family Connection
    And SM I skip the onboarding modals
    And SM I remove
    And SM I clear pinned schools list
    And SM I select the "<string>" checkbox from "Location" fit criteria
    When SM I click "Athletics" filter criteria tab
    And SM I press button "ADD SPORT"
    And SM I pick "Archery" from the dropdown "supermatch-athletics-search"
    And SM I press button "ADD"
    And SM I press Why button for "The New England Conservatory of Music" college
    Then I check if I can see "Athletics [1]" on the page
    And  I check if I can see "Doesn't offer any of your athletics selection(s)" on the page
