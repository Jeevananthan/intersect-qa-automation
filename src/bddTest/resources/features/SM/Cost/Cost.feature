@SM
Feature: SM - Cost - As a HS student, I need to be able to search for colleges based on the 'Admission' fit criteria

  @MATCH-3391
  Scenario: As a HS student, I want to filter colleges I am searching for by 'Meets 100% of Need' within the Cost
  category so I can see relevant colleges that match my Meets 100% of Need requirement.
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I select the "Meets 100% of Need" checkbox from the Cost fit criteria
    Then SM I verify "Meets 100% of Need" checkbox in Cost fit criteria
    And SM I verify that the Must Have box contains "Meets 100% of Need"
    Then SM I unselect the "Meets 100% of Need" checkbox from the "Cost" fit criteria
    And SM I verify that the Must Have box does not contain "Meets 100% of Need"
    Then SM I select the "Meets 100% of Need" checkbox from the Cost fit criteria
    And SM I move "Meets 100% of Need" from the Must Have box to the Nice to Have box
    Then SM I unselect the "Meets 100% of Need" checkbox from the "Cost" fit criteria
    And SM I verify that the Must Have box does not contain "Meets 100% of Need"
    And SM I verify that Nice to Have box does not contain "Meets 100% of Need"
    Then SM I select the "Meets 100% of Need" checkbox from the Cost fit criteria
    And SM I verify that the Must Have box contains "Meets 100% of Need"

  @MATCH-4194
  Scenario: We need to incorporate the word 'annual' or the words 'per year' into the dropdown UI
    Given SM I am logged in to SuperMatch through Family Connection
    When SM I open the "Cost" tab
    Then SM I verify that the appropriate wording is used for dropdowns of the following options:
    | Maximum Tuition and Fees                         | Select Max | per year |
    | Maximum Total Cost (Tuition, Fees, Room & Board) | Select Max | per year |