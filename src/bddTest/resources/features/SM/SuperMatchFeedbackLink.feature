@SM
Feature: SM - SuperMatchFeedbackLink - As a HS student interacting with SuperMatch Beta, I need a way to submit feedback on and ideas for the tool

  @MATCH-3433
  Scenario: As a HS student interacting with SuperMatch Beta, I need a way to submit feedback on and ideas for the tool
    Given SM I am logged in to SuperMatch through Family Connection in Staging with the following details
    | benhubs | Hobsons!23 | rtsa |
    Then SM I verify that a survey is opened after clicking the "Provide Feedback" button
    And SM I sign out of SuperMatch through Family Connection