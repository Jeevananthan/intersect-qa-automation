@SM
Feature: SM - As a HS student who is interacting with SuperMatch, I want to see the proper names for the column headers
  in the results table

#  This scenario doesn't cover those checks are required for MATCH-4317
#
#  Acceptance Criteria:
#
#  Update all three dropdowns in the results table so that the 'Financial Aid' option now displays as 'Cost'

#NEED TO TE REWRITTEN
#  @MATCH-4317
#  Scenario: The 'Financial Aid' column in the results table selectable dropdowns need renamed to 'Cost'
#    Given SM I am logged in to SuperMatch through Family Connection
#    And I clear the onboarding popups if present
#    When I select the following data from the Admission Fit Criteria
#      | GPA (4.0 scale) | 4 |
#      | SAT Composite   | 400 |
#      | ACT Composite   | 3   |
#      | Acceptance Rate | 25% or Lower |
#    Then SM I verify that the column headers in the results table are the following:
#      | Fit Score |
#      | Academic Match |
#      | Admission Info |
#      | Cost           |
#      | Pick what to show |
