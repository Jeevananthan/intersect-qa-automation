@SM
Feature: SM - Feature - As a HS student, I need to be able to search for colleges based on the 'Diversity' fit criteria

  @MATCH-3848
  Scenario: As a HS student I want to see validation message when only the specific percentage was set
    Given SM I am logged in to SuperMatch through Family Connection
    When I select the following data from the Diversity Fit Criteria
      | Diversity          | Percentage |
      | Search by distance | 10%        |
    Then SM I see validation message "Select race or ethnicity to finish adding this criteria"

  @MATCH-3848
  Scenario: As a HS student I want to see validation message when only the specific race was set
    Given SM I am logged in to SuperMatch through Family Connection
    When I select the following data from the Diversity Fit Criteria
      | Diversity          | Select race or ethnicity                  |
      | Search by distance | Native Hawaiian or other Pacific Islander |
    Then SM I see validation message "Select percentage to finish adding this criteria"

  @MATCH-3848
  Scenario: As a HS student I want to see validation message when only the specific gender was set
    Given SM I am logged in to SuperMatch through Family Connection
    When I select the following data from the Diversity Fit Criteria
      | Gender |
      | Male   |
    Then SM I see validation message "Select percentage to finish adding this criteria"

  @MATCH-3848
  Scenario: As a HS student I want to see validation message when only the specific male\female % was set
    Given SM I am logged in to SuperMatch through Family Connection
    When I select the following data from the Diversity Fit Criteria
      | % MALE VS. FEMALE |
      | 10              |
    Then SM I see validation message "Select gender to finish adding this criteria"

  @MATCH-3375 @MATCH-3371 @MATCH-3817
  Scenario Outline: As a HS student, I want to filter colleges I am searching for by International Students within the Diversity
            category so I can see relevant colleges that match my International Students requirements.
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I select the "<diversityOption>" checkbox from the "Diversity" fit criteria
    And SM I verify that the Must Have box contains "<diversityOption>"
    Then SM I unselect the "<diversityOption>" checkbox from the "Diversity" fit criteria
    And SM I verify that the Must Have box does not contain "<diversityOption>"
    Then SM I select the "<diversityOption>" checkbox from the "Diversity" fit criteria
    And SM I move "<diversityOption>" from the Must Have box to the Nice to Have box
    Then SM I unselect the "<diversityOption>" checkbox from the "Diversity" fit criteria
    And SM I verify that the Must Have box does not contain "<diversityOption>"
    And SM I verify that Nice to Have box does not contain "<diversityOption>"
    Then SM I select the "<diversityOption>" checkbox from the "Diversity" fit criteria
    And SM I verify that the Must Have box contains "<diversityOption>"
    Examples:
    |diversityOption                  |
    |High International Population    |
    |Historically Black Institutions  |
    |Tribal Colleges and Universities |
    |Hispanic Serving Institutions    |

  @MATCH-3439
  Scenario Outline: As a HS student reviewing results in SuperMatch, I want to be able to see Diversity details
  about each college in my results table so I can quickly see information about the college's student body.
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I select the "<GenderConcentration>" checkbox from the Diversity fit criteria
    Then SM I check Diversity column in result colleges for "<GenderConcentration>"
    Examples: Each of the available options for the Diversity fit criteria
      | GenderConcentration |
      | Coed                |
      | Women               |
      | Men                 |


