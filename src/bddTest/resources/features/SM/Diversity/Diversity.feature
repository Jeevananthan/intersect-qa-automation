@SM
Feature: SM - Feature - As a HS student, I need to be able to search for colleges based on the 'Diversity' fit criteria

  @MATCH-3375
  Scenario: As a HS student, I want to filter colleges I am searching for by International Students within the Diversity
  category so I can see relevant colleges that match my International Students requirements.
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I select the "High International Population" checkbox from the Diversity fit criteria
    And SM I verify that the Must Have box contains "High International Population"
    Then SM I unselect the "High International Population" checkbox from the "Diversity" fit criteria
    Then SM I verify "High International Population" checkbox in Diversity fit criteria
    And SM I verify that the Must Have box does not contain "High International Population"
    Then SM I select the "High International Population" checkbox from the Diversity fit criteria
    And SM I move "High International Population" from the Must Have box to the Nice to Have box
    Then SM I unselect the "High International Population" checkbox from the "Diversity" fit criteria
    And SM I verify that the Must Have box does not contain "High International Population"
    And SM I verify that Nice to Have box does not contain "High International Population"
    Then SM I select the "High International Population" checkbox from the Diversity fit criteria
    And SM I verify that the Must Have box contains "High International Population"


