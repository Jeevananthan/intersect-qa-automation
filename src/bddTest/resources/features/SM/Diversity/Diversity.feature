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

  @MATCH-4242
  Scenario: As a HS student viewing the Why drawer of a particular college in my search results,
  I want to see the actual Specific Representation for the college so I can clearly see
  what matched/did not match/partially matched my search/fit criteria requirements
    Given SM I am logged in to SuperMatch through Family Connection
    And SM I skip the onboarding modals
    And SM I clear all pills from Must have  and Nice to have boxes
    And SM I select the "Small City" checkbox from "Location" fit criteria
    And SM I select the "Historically Black Institutions" checkbox from "Diversity" fit criteria
    And SM I pin "Bennett College" if it is not pinned already
    And SM I clear all pills from Must have  and Nice to have boxes
    When SM I select the "Specific Representation" checkbox from "Diversity" fit criteria
    Then SM I pick "50%" from the dropdown "supermatch-diversity-dropdown"
    Then SM I pick "Native Hawaiian or other Pacific Islander" from the dropdown "race"
    Then SM I press Why button for "Bennett College" college
    Then I check if I can see "0% are Native Hawaiian or other Pacific Islander students" on the page
    Then SM I press Why button for "Pacific Islands University" college
    Then I check if I can see "At least 50% Native Hawaiian or other Pacific Islander" on the page
    Then I check if I can see "83% are Native Hawaiian or other Pacific Islander students" on the page
    Then SM I press Why button for "University of Guam" college
    Then I check if I can see "48% are Native Hawaiian or other Pacific Islander students" on the page

  @MATCH-4242
  Scenario: As a HS student viewing the Why drawer of a particular college in my search results,
  I want to see the actual Religious Affiliation for the college so I can clearly see
  what matched/did not match/partially matched my search/fit criteria requirements
    Given SM I am logged in to SuperMatch through Family Connection
    And SM I skip the onboarding modals
    And SM I clear all pills from Must have  and Nice to have boxes
    And SM I select the "Small City" checkbox from "Location" fit criteria
    And SM I select the "Historically Black Institutions" checkbox from "Diversity" fit criteria
    And SM I pin "Bennett College" if it is not pinned already
    And SM I clear all pills from Must have  and Nice to have boxes
    Then SM I click "Diversity" filter criteria tab
    Then SM I pick "Roman Catholic" from the dropdown "sm-filter-search-dropdown-icon"
    Then SM I press Why button for "Bennett College" college
    Then I check if I can see "Religious Affiliation" on the page
    Then SM I press Why button for the first college in results with score 100%
    Then I check if I can see "Religious Affiliation" on the page


  @MATCH-4242
  Scenario: As a HS student viewing the Why drawer of a particular college in my search results,
  I want to see the actual Religious Affiliation for the college so I can clearly see
  what matched/did not match/partially matched my search/fit criteria requirements
    Given SM I am logged in to SuperMatch through Family Connection
    And SM I skip the onboarding modals
    And SM I clear all pills from Must have  and Nice to have boxes
    And SM I select the "Small City" checkbox from "Location" fit criteria
    And SM I select the "Historically Black Institutions" checkbox from "Diversity" fit criteria
    And SM I pin "Bennett College" if it is not pinned already
    And SM I clear all pills from Must have  and Nice to have boxes
    Then SM I select the "Men's College" checkbox from "Diversity" fit criteria
    Then SM I press Why button for "Bennett College" college
    Then I check if I can see "Gender Concentration" on the page
    Then SM I press Why button for the first college in results with score 100%
    Then I check if I can see "Gender Concentration " on the page

