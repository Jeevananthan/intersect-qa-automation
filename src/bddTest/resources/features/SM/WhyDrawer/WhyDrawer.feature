@SM
Feature: SM - SuperMatchSearch - In order for the Why? drawer fit score breakdown section to be more intuitive for the
  HS student, we want to add a legend that provides an explanation of what each icon in the fit score breakdown means.

  @MATCH-4266
  Scenario: As a HS student want to see a legend that provides an explanation of what each icon in the fit score breakdown means.
    Given SM I am logged in to SuperMatch through the SuperMatch link
    And SM I skip the onboarding modals
    When I select the following data from the Admission Fit Criteria
      | GPA (4.0 scale) | 4 |
      | SAT Composite   | 400 |
      | ACT Composite   | 3   |
      | Acceptance Rate | 25% or Lower |
    Then SM I verify that the appropriate legend is displayed in the Why Drawer in position "1", according to the following data:
    | Match |
    | Close Match |
    | Data Unknown |
    | Doesn't Match |
