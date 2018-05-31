@SM
Feature: SM - SuperMatchFeedbackLink - As a HS student interacting with SuperMatch Beta, I need a way to submit feedback on and ideas for the tool

  @MATCH-3433
  Scenario: As a HS student interacting with SuperMatch Beta, I need a way to submit feedback on and ideas for the tool
    Given SM I am logged in to SuperMatch through Family Connection as user "sanad.student" with password "password" from school "blue1combo"
    Then SM I verify that a survey is opened after clicking the Provide Feedback button

  @MATCH-4052
  Scenario: The SuperMatch beta survey link needs to be corrected so students that access the survey link from SuperMatch are taken to the correct survey.
    Given SM I am logged in to SuperMatch through the SuperMatch link
    Then SM I open the survey using the Provide Feedback button
    Then SM I verify that the survey URL is "https://supermatchbetasurvey.questionpro.com/"
    And SM I close the survey
