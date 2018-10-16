@SM
Feature: SM - Diversity - Diversity - As a HS student, I need to be able to search for colleges based on the 'Diversity' fit criteria

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
    And SM I click "Admission" filter criteria tab
    And SM I clean GPA/SAT/ACT scores
    And SM I select the "Small City" checkbox from "Location" fit criteria
    And SM I select the "Historically Black Institutions" checkbox from "Diversity" fit criteria
    And SM I pin "Bennett College" if it is not pinned already
    And SM I clear all pills from Must have  and Nice to have boxes
    When SM I select the "Specific Representation" from the "Diversity" fit criteria not closing the tab
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
    And SM I click "Admission" filter criteria tab
    And SM I clean GPA/SAT/ACT scores
    And SM I select the "Small City" checkbox from "Location" fit criteria
    And SM I select the "Historically Black Institutions" checkbox from "Diversity" fit criteria
    And SM I pin "Bennett College" if it is not pinned already
    And SM I clear all pills from Must have  and Nice to have boxes
    Then SM I click "Diversity" filter criteria tab
    Then SM I pick "Roman Catholic" from the dropdown ".sm-filter-search-dropdown.multiple"
    And HS I Click on close button
    Then SM I press Why button for "Bennett College" college
    Then I check if I can see "Religious Affiliation" on the page
    Then SM I press Why button for the first college in results with score 100%
    Then I check if I can see "Religious Affiliation" on the page


  @MATCH-4242
  Scenario: As a HS student viewing the Why drawer of a particular college in my search results,
  I want to see the actual Religious Affiliation for the college so I can clearly see
  what matched/did not match matched my search/fit criteria requirements
    Given SM I am logged in to SuperMatch through Family Connection
    And SM I skip the onboarding modals
    And SM I clear all pills from Must have  and Nice to have boxes
    And SM I clear pinned schools list
    And SM I click "Admission" filter criteria tab
    And SM I clean GPA/SAT/ACT scores
    And SM I select the "Small City" checkbox from "Location" fit criteria
    And SM I select the "Historically Black Institutions" checkbox from "Diversity" fit criteria
    And SM I pin "Bennett College" if it is not pinned already
    And SM I clear all pills from Must have  and Nice to have boxes
    Then SM I select the "Men's College" checkbox from "Diversity" fit criteria
    Then SM I press Why button for "Bennett College" college
    Then I check if I can see "Gender Concentration" on the page
    Then SM I press Why button for the first college in results with score 100%
    Then I check if I can see "Gender Concentration " on the page

  @MATCH-4242
  Scenario: As a HS student viewing the Why drawer of a particular college in my search results,
  I want to see the actual International Students for the college so I can clearly see
  what matched/did not match/partially matched my search/fit criteria requirements
    Given SM I am logged in to SuperMatch through Family Connection
    And SM I skip the onboarding modals
    And SM I clear all pills from Must have  and Nice to have boxes
    And SM I clear pinned schools list
    And SM I click "Admission" filter criteria tab
    And SM I clean GPA/SAT/ACT scores
    And SM I select the "Small City" checkbox from "Location" fit criteria
    And SM I select the "Historically Black Institutions" checkbox from "Diversity" fit criteria
    And SM I pin "Bennett College" if it is not pinned already
    When SM I select the "High International Population" from the "Diversity" fit criteria not closing the tab
    And HS I Click on close button
    Then SM I press Why button for "Bennett College" college
    Then I check if I can see "High International Population" on the page
    Then I check if I can see "1% are international students" on the page
    Then SM I press Why button for "Oakwood University" college
    Then I check if I can see "High International Population" on the page
    Then I check if I can see "5% are international students" on the page


  @MATCH-4242
  Scenario: As a HS student viewing the Why drawer of a particular college in my search results,
  I want to see the actual %Make vs. Female for the college so I can clearly see
  what matched/did not match/partially matched my search/fit criteria requirements
    Given SM I am logged in to SuperMatch through Family Connection
    And SM I skip the onboarding modals
    And SM I clear all pills from Must have  and Nice to have boxes
    And SM I clear pinned schools list
    And SM I click "Admission" filter criteria tab
    And SM I clean GPA/SAT/ACT scores
    And SM I select the "Small City" checkbox from "Location" fit criteria
    And SM I select the "Historically Black Institutions" checkbox from "Diversity" fit criteria
    And SM I pin "Bennett College" if it is not pinned already
    Then SM I remove the "Historically Black Institutions" fit criteria from the Must Have box or Nice to Have box
    Then SM I click "Diversity" filter criteria tab
    And SM I pick "70%" from the dropdown "male-female-percent-dropdown"
    And SM I pick "Male" from the dropdown "male-female-gender-dropdown"
    And HS I Click on close button
    Then SM I press Why button for "Bennett College" college
    Then I check if I can see "At least 70% are Male" on the page
    Then I check if I can see "1% are male students" on the page
    Then SM I press Why button for "Florida Institute of Technology" college
    Then I check if I can see "At least 70% are Male" on the page
    Then I check if I can see "71% are male students" on the page

  @MATCH-4242
  Scenario: As a HS student viewing the Why drawer of a particular college in my search results,
  I want to see the actual out of States Students for the college so I can clearly see
  what matched/did not match/partially matched my search/fit criteria requirements
    Given SM I am logged in to SuperMatch through Family Connection
    And SM I skip the onboarding modals
    And SM I clear all pills from Must have  and Nice to have boxes
    And SM I clear pinned schools list
    And SM I click "Admission" filter criteria tab
    And SM I clean GPA/SAT/ACT scores
    And SM I select the "Small City" checkbox from "Location" fit criteria
    And SM I select the "Historically Black Institutions" checkbox from "Diversity" fit criteria
    And SM I pin "Bennett College" if it is not pinned already
    And SM I unselect the "Privat" checkbox from the "Institution Characteristics" fit criteria
    And SM I unselect the "4-year" checkbox from the "Institution Characteristics" fit criteria
    Then SM I click "Diversity" filter criteria tab
    And SM I pick "50%" from the dropdown "OutOfStateStudents-dropdown"
    Then SM I press Why button for "Bennett College" college
    Then I check if I can see "Out of State Students ≥ 50%" on the page
    Then I check if I can see "42% are out-of-state students" on the page
    Then SM I press Why button for "Delaware State University" college
    Then I check if I can see "Out of State Students ≥ 50%" on the page
    Then I check if I can see "63% are out-of-state students" on the page
  #TODO: add a scenario for Overall Diversity' once MATCH-4939 and MATCH-4938 are fixed

  @MATCH-3805
  Scenario: As a HS student, I want to filter colleges I am searching for by Diversity within the Diversity category so
  I can see relevant colleges that match my Diversity requirements.
    Given SM I am logged in to SuperMatch through Family Connection
    And SM I skip the onboarding modals
    And SM I clear all pills from Must have  and Nice to have boxes
    And SM I click "Diversity" filter criteria tab
    Then SM I verify that the "Overall Diversity" radio button is "selected"
    And I click the dropdown "div#supermatch-diversity-percent-dropdown"
    Then I verify that the options list "span.text" matches the list in "overall.diversity.percentage.list"
    And I select the option "20%" from the list "span.text"
    Then SM I verify that the Must Have box contains "At least 20% are minority students"
    And I click the dropdown "div#supermatch-diversity-percent-dropdown"
    And I select the option "10%" from the list "span.text"
    Then SM I verify that the Must Have box contains "At least 10% are minority students"

  @MATCH-3373
  Scenario: As a HS student, I want to filter colleges I am searching for by Gender Concentration within the Diversity
  category so I can see relevant colleges that match my Gender Concentration requirements.
    Given SM I am logged in to SuperMatch through Family Connection
    And SM I skip the onboarding modals
    And SM I clear all pills from Must have  and Nice to have boxes
    And SM I click "Diversity" filter criteria tab
    And SM I select the "Coed" checkbox from "Diversity" fit criteria
    Then SM I verify that the Must Have box contains "Gender Concentration [1]"
    And SM I select the "Women's College" checkbox from "Diversity" fit criteria
    Then SM I verify that the Must Have box contains "Gender Concentration [2]"
    And SM I select the "Men's College" checkbox from "Diversity" fit criteria
    Then SM I verify that the Must Have box contains "Gender Concentration [3]"
    And SM I unselect the "Coed" checkbox from the "Diversity" fit criteria
    Then SM I verify that the Must Have box contains "Gender Concentration [2]"
    And SM I unselect the "Women's College" checkbox from the "Diversity" fit criteria
    Then SM I verify that the Must Have box contains "Gender Concentration [1]"
    And SM I unselect the "Men's College" checkbox from the "Diversity" fit criteria

  @MATCH-3372
  Scenario: As a HS student, I want to filter colleges I am searching for by Religious Affiliation within the Diversity
  category so I can see relevant colleges that match my Religious Affiliation requirements.
    Given SM I am logged in to SuperMatch through Family Connection
    When SM I clear all pills from Must have  and Nice to have boxes
    And SM I select the "Diversity" fit criteria
    Then I verify that the default text in "input.search + span + div" is "Start Typing..."
    And I click the dropdown "input.search + span + div"
    Then I verify that the options list "span.text" matches the list in "religious.affiliation.options.list"
    And I select the option "Advent Christian Church" from the list "span.text"
    Then SM I verify that the option "Advent Christian Church" was added to the dropdown field
    And I select the option "American Baptist" from the list "span.text"
    Then SM I verify that 2 items are displayed in the dropdown field
    And SM I remove the option "American Baptist" from the dropdown field
    Then SM I verify that 1 items are displayed in the dropdown field
    Then SM I verify that the Must Have box contains "Religious Affiliation [1]"

  @MATCH-3948
  Scenario: As a HS student that is comparing my pinned schools, I want to see Diversity details about each college
  side by side so I can determine which pinned college is a best fit for me based on their Diversity.
    Given SM I am logged in to SuperMatch through Family Connection
    And I clear the onboarding popups if present
    And SM I clear all the pinned college
    And SM I search for "College of the North Atlantic" college in search bar
    And SM I open the Pinned Schools Compare screen
    And SM I verify all the below options available in Diversity fit criteria in Diversity expandable drawer
      |Coed| Gender Mix| Out-of-state Students |International Students|Percentage Minority |% American Indian or Alaskan Native |% Asian|% Black or African American|% Hispanic/Latin|% Native Hawaiian or other Pacific Islander|Religious Affiliation |Diversity Affiliation|
      |Yes| 0% Female\n0% Male |10%| Unknown |0%| 0%| 0%| 0%| 0%| Unknown |Unknown| Unknown|
