@SM
Feature: SM - FavoriteSchools - As a HS student, I want to be able to Favorite schools and have them sync with the
         "Schools I'm Thinking About" list in Naviance.

  @MATCH-3429 @MATCH-3574
  Scenario: As a HS student accessing SuperMatch, I want to be able to favorite a school and have it appear in my
            "Schools I'm Thinking About" list inside Naviance Student.
    Given SM I am logged in to SuperMatch through Family Connection as user type "3429-3574"
    Then SM I clear all pills from Must have  and Nice to have boxes
    Then SM I select the "25% or Lower" checkbox from "Admission" fit criteria
    Then SM I select the "Northeast" checkbox from "Location" fit criteria
    Then SM I un-favorite the school "Cornell University"
    And SM I store the "THINKING ABOUT" value from the footer
    Then SM I favorite the school "Cornell University"
    And SM I verify that the "THINKING ABOUT" value from the footer is "greater than" the value stored earlier
    Then SM I press Why button for "Cornell University" college
    Then SM I un-favorite the school "Cornell University" from the why drawer
    And SM I verify that the "THINKING ABOUT" value from the footer is "equal to" the value stored earlier
    Then SM I favorite the school "Cornell University" from the why drawer
    And SM I close the why drawer
    And SM I verify that the "THINKING ABOUT" value from the footer is "greater than" the value stored earlier
    Then SM I un-favorite the school "Cornell University"
    And SM I verify that the "THINKING ABOUT" value from the footer is "equal to" the value stored earlier