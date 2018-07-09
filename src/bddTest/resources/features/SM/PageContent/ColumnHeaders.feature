@SM
Feature: SM - As a HS student who is interacting with SuperMatch, I want to see the proper names for the column headers
  in the results table

  @MATCH-4317
  Scenario: The 'Financial Aid' column in the results table selectable dropdowns need renamed to 'Cost'
    Given SM I am logged in to SuperMatch through Family Connection
    And I clear the onboarding popups if present
    When I select the following data from the Admission Fit Criteria
      | GPA (4.0 scale) | 4 |
      | SAT Composite   | 400 |
      | ACT Composite   | 3   |
      | Acceptance Rate | 25% or Lower |
    Then SM I verify that the column headers in the results table are the following:
      | Fit Score |
      | Academic Match |
      | Admission Info |
      | Cost           |
      | Pick what to show |
