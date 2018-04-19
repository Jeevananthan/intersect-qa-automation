@SM
Feature: SM - Feature - As a HS student, I need to be able to search for colleges based on the 'Diversity' fit criteria

  @MATCH-3848
  Scenario: As a HS student I want to see validation message when only the specific percentage was set
    Given SM I am logged in to SuperMatch through Family Connection
    When I select the following data from the Diversity Fit Criteria
      | Diversity          | Percentage |
      | Search by distance | 10%        |
    Then SM I see validation message "Select race or ethnicity to finish adding this criteria"

  @MATCH-3848
  Scenario: As a HS student I want to see validation message when only the specific race was set
    Given SM I am logged in to SuperMatch through Family Connection
    When I select the following data from the Diversity Fit Criteria
      | Diversity          | Select race or ethnicity                  |
      | Search by distance | Native Hawaiian or other Pacific Islander |
    Then SM I see validation message "Select percentage to finish adding this criteria"
