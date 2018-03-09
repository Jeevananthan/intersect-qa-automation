@SM
Feature: SM - Feature - As a HS student, I need to be able to search for colleges based on the 'Admission' fit criteria

  @MATCH-3391
  Scenario: As a HS student, I want to filter colleges I am searching for by 'Meets 100% of Need' within the Cost
  category so I can see relevant colleges that match my Meets 100% of Need requirement.
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I select the "Meets 100% of Need" checkbox from the Cost fit criteria
    Then SM I verify "Meets 100% of Need" checkbox in Cost fit criteria
    And SM I verify that the Must Have box contains "Meets 100% of Need"
    Then SM I unselect the "Meets 100% of Need" checkbox
    #And SM I verify that the Must Have box does not contain "Meets 100% of Need"
    #Then SM I select the "Meets 100% of Need" checkbox from the Cost fit criteria