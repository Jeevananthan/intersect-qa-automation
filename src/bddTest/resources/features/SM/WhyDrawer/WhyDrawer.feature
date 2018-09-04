@SM
Feature: SM - WhyDrawer - WhyDrawer - In order for the Why? drawer fit score breakdown section to be more intuitive for the
         HS student, we want to add a legend that provides an explanation of what each icon in the fit score breakdown means.

  Background:
    Given SM I am logged in to SuperMatch through Family Connection
    And SM I skip the onboarding modals
    And SM I clear pinned schools list
    And SM I clear all pills from Must have  and Nice to have boxes

  @MATCH-4266 @MATCH-4269
  Scenario: As a HS student want to see a legend that provides an explanation of what each icon in the fit score breakdown means.
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

@MATCH-4249
  Scenario: As a HS student viewing the Why drawer of a particular college in my search results,
  I want to see the actual athletics data for the college so I can clearly see what matched
 When SM I click "Athletics" filter criteria tab
    And SM I press button "ADD SPORT"
    And SM I pick "Archery" from the dropdown "supermatch-athletics-search"
    And SM I press button "ADD"
    And SM I press Why button for the first college in results with score 100%
    Then I check if I can see "Athletics [1]" on the page
    And  I check if I can see "The following athletics are offered:" on the page
    And  I check if I can see "Archery" on the page

  @MATCH-4249
  Scenario: As a HS student viewing the Why drawer of a particular college in my search results,
  I want to see the actual athletics data for the college so I can clearly see what matched (multiple sports)
    When SM I click "Athletics" filter criteria tab
    And SM I press button "ADD SPORT"
    And SM I pick "Archery" from the dropdown "supermatch-athletics-search"
    And SM I press button "ADD"
    And SM I press button "ADD SPORT"
    And SM I pick "Badminton" from the dropdown "supermatch-athletics-search"
    And SM I press button "ADD"
    And SM I press button "ADD SPORT"
    And SM I pick "Aerobics" from the dropdown "supermatch-athletics-search"
    And SM I press button "ADD"
    And SM I press Why button for the first college in results with score 100%
    Then I check if I can see "Athletics [3]" on the page
    And  I check if I can see "The following athletics are offered:" on the page

  @MATCH-4249
  Scenario: As a HS student viewing the Why drawer of a particular college in my search results,
  I want to see the actual athletics data for the college so I can clearly see what doesn't match
    And SM I select the "Central" checkbox from "Location" fit criteria
    And SM I unselect the "Privat" checkbox from the "Institution Characteristics" fit criteria
    And SM I unselect the "4-year" checkbox from the "Institution Characteristics" fit criteria
    When SM I click "Athletics" filter criteria tab
    And SM I press button "ADD SPORT"
    And SM I pick "Archery" from the dropdown "supermatch-athletics-search"
    And SM I press button "ADD"
    And SM I press Why button for "Metropolitan Community College - Kansas City" college
    Then I check if I can see "Athletics [1]" on the page
    And  I check if I can see "Doesn't offer any of your athletics selection(s)" on the page

  @MATCH-4249
  Scenario: As a HS student viewing the Why drawer of a particular college in my search results,
  I want to see the actual athletics data for the college so I can clearly see that there is no information about sports
    And SM I select the "Central" checkbox from "Location" fit criteria
    And SM I unselect the "Privat" checkbox from the "Institution Characteristics" fit criteria
    And SM I unselect the "4-year" checkbox from the "Institution Characteristics" fit criteria
    When SM I click "Athletics" filter criteria tab
    And SM I press button "ADD SPORT"
    And SM I pick "Archery" from the dropdown "supermatch-athletics-search"
    And SM I press button "ADD"
    And SM I press Why button for "Rolla Technical Institute/Center" college
    Then I check if I can see "Athletics [1]" on the page
    And  I check if I can see "Data unknown" on the page
#
  @MATCH-4248
  Scenario: As a HS student viewing the Why drawer of a particular college in my search results,
  I want to see the actual student life for the college so I can clearly see what matched
    When SM I click "Student Life" filter criteria tab
    And SM I pick "Business" from the dropdown "sm-filter-search-dropdown"
    And SM I press Why button for the first college in results with score 100%
    Then I check if I can see "Organizations and Clubs [1]" on the page
    And  I check if I can see "The following organizations and clubs are offered:" on the page
    And  I check if I can see "Business" on the page

  @MATCH-4248
  Scenario: As a HS student viewing the Why drawer of a particular college in my search results,
  I want to see the actual student life for the college so I can clearly see what desn't match
    When SM I click "Location" filter criteria tab
    And SM I pick "Minnesota" from the dropdown "div-state-or-region-search"
    And SM I select the "Suburb near Small City" checkbox from "Location" fit criteria
    And SM I unselect the "Public" checkbox from the "Institution Characteristics" fit criteria
    And SM I unselect the "2-year or less (Community Colleges and Vocational/Technical schools)" checkbox from the "Institution Characteristics" fit criteria
    When SM I click "Diversity" filter criteria tab
    And SM I pick "Christ and Missionary Alliance Church" from the dropdown "sm-filter-search-dropdown"
    When SM I click "Student Life" filter criteria tab
    And SM I pick "Gay and Lesbian" from the dropdown "sm-filter-search-dropdown"
    And SM I press Why button for "Crown College" college
    Then I check if I can see "Organizations and Clubs [1]" on the page
    And  I check if I can see "Doesn't offer any of your organizations and clubs selection(s)." on the page

  @MATCH-4248
  Scenario: As a HS student viewing the Why drawer of a particular college in my search results,
  I want to see the actual student life data for the college so I can clearly see what matched (multiple sports)
    When SM I click "Student Life" filter criteria tab
    And SM I pick "Business" from the dropdown "sm-filter-search-dropdown"
    And SM I pick "Choir" from the dropdown "sm-filter-search-dropdown"
    And SM I pick "Gay and Lesbian" from the dropdown "sm-filter-search-dropdown"
    And SM I press Why button for the first college in results with score 100%
    Then I check if I can see "Organizations and Clubs [3]" on the page
    And  I check if I can see "The following organizations and clubs are offered:" on the page

#    To add one more scenario w\o data a college with appropriate data is needed. TODO

  @MATCH-3427
  Scenario: As a HS student viewing the Why drawer of a particular college in my search results,
  I want to see the actual out of States Students for the college so I can clearly see
  what matched/did not match/partially matched my search/fit criteria requirements
    When SM I click "Admission" filter criteria tab
    And SM I clean GPA/SAT/ACT scores
    And SM I select the "Small City" checkbox from "Location" fit criteria
    And SM I select the "Historically Black Institutions" checkbox from "Diversity" fit criteria
    And SM I pin "Bennett College" if it is not pinned already
    And SM I remove the "Campus Surroundings [1]" fit criteria from the Must Have box or Nice to Have box
    And SM I select the "Large City" checkbox from "Location" fit criteria
    Then SM I press Why button for "Bennett College" college
    Then I check there are 2 icons ".green.check.icon" are displayed
    Then I check there are 2 icons ".red.cancel.icon" are displayed