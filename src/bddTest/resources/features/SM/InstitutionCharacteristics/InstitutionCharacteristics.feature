@SM
Feature: SM - Institution Characteristics - As a HS student, I need to be able to search for colleges based on the 'Institution Characteristics' fit criteria

  @MATCH-3342 @MATCH-3747
    Scenario Outline: As a HS student, I want to filter colleges I am searching for by Student Success fit criteria so
                      I can see relevant colleges that match my requirements
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I select the "<StudentSuccessCheckbox>" checkbox from the Institution Characteristics fit criteria
    And SM I verify "<StudentSuccessCheckbox>" checkbox from the Institution Characteristics fit criteria
    And SM I verify that the Must Have box contains "<StudentSuccessCheckbox>"
    And SM I move "<StudentSuccessCheckbox>" from the Must Have box to the Nice to Have box
    Then SM I unselect the "<StudentSuccessCheckbox>" checkbox from the Institution Characteristics fit criteria
    And SM I verify that the Must Have box does not contain "<StudentSuccessCheckbox>"
    And SM I verify that Nice to Have box does not contain "<StudentSuccessCheckbox>"
    Then SM I select the "<StudentSuccessCheckbox>" checkbox from the Institution Characteristics fit criteria
    And SM I verify that the Must Have box contains "<StudentSuccessCheckbox>"
    Examples: Each of the available options for the Student Success fit criteria
      | StudentSuccessCheckbox  |
      | High Graduation Rate    |
      | High Retention Rate     |
      | High Job Placement Rate |

  @MATCH-3343 @MATCH-4149
  Scenario: As a HS student, I want to filter colleges I am searching for by Average Class Size within the Institution Characteristics category so I can see relevant colleges that match my Average Class Size requirements.
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I click on Institution Characteristics fit criteria
    Then SM I check the selection and deselection and Must Have box functionality for Average Class Size drop down list
    Then SM I check when Average Class Size filter is selected, moved to Nice To Have, unselected, and then selected again it should be defaulted back to the Must Have box
    Then SM I verify the Average Class Size text under Institution Characteristics in the results list is correct




