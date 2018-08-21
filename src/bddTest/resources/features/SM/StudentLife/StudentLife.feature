@SM
Feature: SM - Feature - As a HS student, I need to be able to search for colleges based on the 'Student Life' fit criteria

  @MATCH-4192
  Scenario Outline: As a HS student I want to see the functionality of Greek Life under Student Life fit-criteria
    Given SM I am logged in to SuperMatch through Family Connection
    And SM I clear all pills from Must have  and Nice to have boxes
    Then SM I select the "Student Life" fit criteria
    And SM I verify Greek Life option "<GreekLife>"
    Examples: Each of the available options for the Greek Life fit criteria
      | GreekLife                         |
      | Fraternities/Sororities Available |
      | No Fraternities/Sororities        |

