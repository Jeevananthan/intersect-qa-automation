@SM
Feature: SM - FavoriteSchools - As a HS student, I want to be able to Favorite schools and have them sync with the
         "Schools I'm Thinking About" list in Naviance.

  @MATCH-3429 @MATCH-3574
  Scenario: As a HS student accessing SuperMatch, I want to be able to favorite a school and have it appear in my
            "Schools I'm Thinking About" list inside Naviance Student.
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I clear all pills from Must have  and Nice to have boxes
    Then SM I select the "25% or Lower" checkbox from "Admission" fit criteria
    Then SM I select the "Northeast" checkbox from "Location" fit criteria
    And SM I store the "THINKING ABOUT" value from the footer
    Then SM I favorite the school "Cornell University"
    And SM I verify that the "THINKING ABOUT" value from the footer is "greater than" the value stored earlier