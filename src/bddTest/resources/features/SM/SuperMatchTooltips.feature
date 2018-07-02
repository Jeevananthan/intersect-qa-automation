@SM
Feature: SM - SuperMatchTooltips - As a HS student who is interacting with SuperMatch, I want to see tooltips so I can
  learn how to use the tool and what certain feature are/mean.

  Background:
    #Given SM I am logged in to SuperMatch through Family Connection
    Given SM I am logged in to SuperMatch through Family Connection as user "gauriparent1" with password "password" from school "blue1combo"
    Then I clear the onboarding popups if present

  @MATCH-3526 @MATCH-3525 @MATCH-3527 @MATCH-4318 @MATCH-4320
  Scenario Outline: As a HS student accessing SuperMatch through Family Connection I need to be verify that the tooltips are displayed/closed
    Then SM I verify that the tooltips are displayed in the tab "<tabLabel>"
    Examples:
    | tabLabel |
    | Location |
    | Academics |
    | Admission |
    | Diversity |
    | Institution Characteristics |
    | Cost                        |
    | Student Life                |
    | Athletics:Aerobics          |
    | Resources                   |

  @MATCH-3524
  Scenario Outline: As a HS student accessing SuperMatch through Family Connection I need to be verify that the tooltips are displayed/closed (2nd part)
    When I select the following data from the Admission Fit Criteria
    | GPA (4.0 scale) | 4 |
    | SAT Composite   | 400 |
    | ACT Composite   | 3   |
    | Acceptance Rate | 25% or Lower |
    Then SM I verify that the tooltips are displayed in the section "<sectionLabel>"
    Examples:
    | sectionLabel |
    | Fit Score    |
    | Academic Match |
    | Scores  |

  @MATCH-4294
  Scenario Outline: As a HS student accessing SuperMatch through Family Connection I need to be verify that the tooltips are displayed/closed (3rd part)
    When I select the following data from the Admission Fit Criteria
      | GPA (4.0 scale) | 4 |
      | SAT Composite   | 400 |
      | ACT Composite   | 3   |
      | Acceptance Rate | 25% or Lower |
    And SM I pin "Colorado College" if it is not pinned already
    And SM I open the Pinned Schools Compare screen
    Then SM I verify that the tooltips are displayed in the Schools Compare screen "<sectionLabel>"
    Examples:
    | sectionLabel |
    | Academic Match |
    | Admission Info |
    | Institution Characteristics |
    | Diversity                   |


