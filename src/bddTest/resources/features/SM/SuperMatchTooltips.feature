@SM
Feature: SM - SuperMatchTooltips - As a HS student who is interacting with SuperMatch, I want to see tooltips so I can
         learn how to use the tool and what certain feature are/mean.

  Background:
    Given SM I am logged in to SuperMatch through Family Connection
    Then I clear the onboarding popups if present
    And SM I start the search over

  @MATCH-3526 @MATCH-3525 @MATCH-3527 @MATCH-4318 @MATCH-4320 @MATCH-4321 @MATCH-4447
  Scenario: As a HS student accessing SuperMatch through Family Connection I need to be verify that the tooltips are displayed/closed
    Then SM I verify that the tooltips are displayed in the tab "Location"
    Then SM I verify that the tooltips are displayed in the tab "Academics"
    Then SM I verify that the tooltips are displayed in the tab "Diversity"
    Then SM I verify that the tooltips are displayed in the tab "Institution Characteristics"
    Then SM I verify that the tooltips are displayed in the tab "Cost"
    Then SM I verify that the tooltips are displayed in the tab "Student Life"
    Then SM I verify that the tooltips are displayed in the tab "Athletics:Aerobics"
    Then SM I verify that the tooltips are displayed in the tab "Resources"
    Then SM I verify that the tooltips are displayed in the tab "Admission"

  @MATCH-3524 @MATCH-4618
  Scenario: As a HS student accessing SuperMatch through Family Connection I need to be verify that the tooltips are displayed/closed (2nd part)
    When I select the following data from the Admission Fit Criteria
    | GPA (4.0 scale) | 4 |
    | SAT Composite   | 400 |
    | ACT Composite   | 3   |
    | Acceptance Rate | 25% or Lower |
    #The following step is needed to avoid MATCH-4830
    #And SM I reload the page
    Then SM I verify that the tooltips are displayed in the section "sectionLabel"
    Then SM I verify that the tooltips are displayed in the section "Fit Score"
    Then SM I verify that the tooltips are displayed in the section "Academic Match"
    Then SM I verify that the tooltips are displayed in the section "GPA"
    Then SM I verify that the tooltips are displayed in the section "Scores"

  @MATCH-4294
  Scenario: As a HS student accessing SuperMatch through Family Connection I need to be verify that the tooltips are displayed/closed (3rd part)
    Then SM I clear pinned schools list
    When I select the following data from the Admission Fit Criteria
      | GPA (4.0 scale) | 4 |
      | SAT Composite   | 400 |
      | ACT Composite   | 3   |
      | Acceptance Rate | 25% or Lower |
    #The following step is needed to avoid MATCH-4830
    #And SM I reload the page
    And SM I pin "Colorado College" if it is not pinned already
    And SM I open the Pinned Schools Compare screen
    Then SM I verify that the tooltips are displayed in the Schools Compare screen "Academic Match"
    Then SM I verify that the tooltips are displayed in the Schools Compare screen "Admission Info"
    Then SM I verify that the tooltips are displayed in the Schools Compare screen "Institution Characteristics"
    Then SM I verify that the tooltips are displayed in the Schools Compare screen "Diversity"

  @MATCH-4618
  Scenario: As a HS student accessing SuperMatch through Family Connection I need to be verify that no icon is displayed
    for GPA in the results table when there is no GPA value
    When I select the following data from the Admission Fit Criteria
      | SAT Composite   | 400 |
      | ACT Composite   | 3   |
      | Acceptance Rate | 25% or Lower |
    #The following step is needed to avoid MATCH-4830
    And SM I reload the page
    Then SM I verify that no tooltip icon is displayed for GPA in the results table




