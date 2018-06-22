@SM @MATCH-3419
Feature: As a HS student accessing SuperMatch for the first time, I want to be onboarded so I can learn how to use the SuperMatch tool and its features.

  @MATCH-3419 @MATCH-3420 @MATCH-3421 @MATCH-3422 @MATCH-3423
  Scenario: As a HS student accessing SuperMatch for the first time, I want to be onboarded so I can learn how to use the SuperMatch tool and its features.
    When SM I am logged in to SuperMatch through Family Connection
    And SM I enable the onboarding modals if they are disabled
    Then SM I verify that the onboarding modal is displayed for the following sections
    | Comprehensive List of Criteria |
    | Categorize Your Criteria |
    | Customize What You See |
    | Pin & Favorite Schools |
    | Compare Pinned Schools |
