@SM
Feature: SM - Institution Characteristics - As a HS student, I need to be able to search for colleges based on the 'Institution Characteristics' fit criteria

  @MATCH-3747 @MATCH-3342
  Scenario Outline: As a HS student, I want to filter colleges I am searching for by Student Success fit criteria so I can see relevant colleges that match my requirements.
    Then SM I select the "<StudentSuccessCheckbox>" checkbox from the Institution Characteristics fit criteria
    And SM I verify "<StudentSuccessCheckbox>" checkbox from the Institution Characteristics fit criteria
    And SM I verify that the Must Have box contains "<StudentSuccessCheckbox>"
    Examples: Each of the available options for the Student Success fit criteria
      | StudentSuccessCheckbox  |
      | High Graduation Rate    |
      | High Retention Rate     |
      | High Job Placement Rate |



Scenario: asdfasfsdf

    Then SM I unselect the "<StudentSuccessCheckbox>" checkbox from the Resources fit criteria
    And SM I verify that the Must Have box does not contain "<StudentSuccessCheckbox>"
    Then SM I select the "<StudentSuccessCheckbox>" checkbox from the Resources fit criteria
    And SM I move "<StudentSuccessCheckbox>" from the Must Have box to the Nice to Have box
    Then SM I unselect the "<StudentSuccessCheckbox>" checkbox from the Resources fit criteria
    Then SM I select the "<StudentSuccessCheckbox>" checkbox from the Resources fit criteria
    And SM I verify that the Must Have box contains "<StudentSuccessCheckbox>"
    Examples: Each of the available options for the Student Success fit criteria
      | StudentSuccessCheckbox  |
      | High Graduation Rate    |
      | High Retention Rate     |
      | High Job Placement Rate |
