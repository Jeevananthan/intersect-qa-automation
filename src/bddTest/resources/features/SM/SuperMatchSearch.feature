@SM
Feature: As a HS student accessing SuperMatch through Family Connection I need to be able to search college based on
  certain fit criteria

  @MATCH-3592
  Scenario: As a HS student accessing SuperMatch through Family Connection I need to be presented with an Student Body
            Size fit criteria
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I verify the Student Body UI in Resources Dropdown

  @MATCH-3246
  Scenario: As a HS student accessing College Search through Family Connection I need to be presented with an
            'empty state' page (no filters selected yet) so I can perform a search when ready.
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I verify if dark blue header is present
    Then SM I verify if Your Fit Criteria text is present
    Then SM I verify the Choose Fit Criteria bar
    Then SM I verify Select Criteria to Start button and instructional text
    Then SM I verify Must Have and Nice to Have boxes
    Then SM I verify the empty results table
    Then SM I verify the dark blue footer