@SM
Feature: SM - StudentLife - StudentLife - As a HS student, I need to be able to search for colleges based on the 'Student Life' fit criteria

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

  @MATCH-4234
  Scenario: The clubs and organizations fit criteria in the Student Life dropdown should display the number of clubs/orgs
  selected by the student in the pill like our other multiselect fit criteria are doing.
    Given SM I am logged in to SuperMatch through Family Connection
    When SM I clear all pills from Must have  and Nice to have boxes
    And SM I select the "Student Life" fit criteria
    And SM I pick "Business" from the "ORGANIZATIONS AND CLUBS" dropdown in Student Life fit criteria
    Then SM I verify that the Must Have box contains "Organizations and Clubs [1]"
    And SM I press Why button for the first college in results with score 100%
    And SM I verify that "Organizations and Clubs [1]" is displayed in the "Must Have" box in the Why Drawer
