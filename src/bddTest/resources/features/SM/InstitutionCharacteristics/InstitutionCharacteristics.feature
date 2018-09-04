@SM
Feature: SM - Institution Characteristics - Institution Characteristics - As a HS student, I need to be able to search
         for colleges based on the 'Institution Characteristics' fit criteria

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
    And SM I clear all pills from Must have  and Nice to have boxes
    Then SM I click "Institution Characteristics" filter criteria tab
    Then SM I check the selection and deselection and Must Have box functionality for Average Class Size drop down list
    Then SM I check when Average Class Size filter is selected, moved to Nice To Have, unselected, and then selected again it should be defaulted back to the Must Have box
    Then SM I verify the Average Class Size text under Institution Characteristics in the results list is correct

  @MATCH-4243
  Scenario: As a HS student viewing the Why drawer of a particular college in my search results, I want clearly see
  what matched/did not match/partially matched Institution Type
    Given SM I am logged in to SuperMatch through Family Connection
    And SM I skip the onboarding modals
    And SM I clear all pills from Must have  and Nice to have boxes
    And SM I click "Admission" filter criteria tab
    And SM I clean GPA/SAT/ACT scores
    And HS I Click on close button
    And SM I select the "Small City" checkbox from "Location" fit criteria
    And SM I select the "Historically Black Institutions" checkbox from "Diversity" fit criteria
    And SM I pin "Bennett College" if it is not pinned already
    And SM I unselect the "4-year" checkbox from the "Institution Characteristics" fit criteria
    Then SM I press Why button for "Bennett College" college
    Then I check if I can see "Institution Type" on the page
    Then SM I press Why button for "J. F. Drake State Community and Technical College" college
    Then I check if I can see "Institution Type" on the page

  @MATCH-4243
  Scenario: As a HS student viewing the Why drawer of a particular college in my search results, I want clearly see
  what matched/did not match/partially matched High Graduate Rate/High Retention Rate/High Job Placement Rate
    Given SM I am logged in to SuperMatch through Family Connection
    And SM I skip the onboarding modals
    And SM I clear all pills from Must have  and Nice to have boxes
    And SM I click "Admission" filter criteria tab
    And SM I clean GPA/SAT/ACT scores
    And HS I Click on close button
    And SM I select the "Small City" checkbox from "Location" fit criteria
    And SM I select the "Historically Black Institutions" checkbox from "Diversity" fit criteria
    And SM I pin "Bennett College" if it is not pinned already
    And SM I select the "High Graduation Rate" checkbox from the "Institution Characteristics" fit criteria
    And SM I select the "High Retention Rate" checkbox from the "Institution Characteristics" fit criteria
    And SM I select the "High Job Placement Rate" checkbox from the "Institution Characteristics" fit criteria
    Then SM I press Why button for "Bennett College" college
    Then I check if I can see "High Graduation Rate" on the page
    Then I check if I can see "Graduation rate is 26%" on the page
    Then I check if I can see "High Retention Rate" on the page
    Then I check if I can see "Retention rate is 54%" on the page
    Then I check if I can see "High Job Placement Rate" on the page
    Then I check if I can see "Data unknown" on the page
    Then SM I press Why button for "Soka University of America" college
    Then I check if I can see "High Graduation Rate" on the page
    Then I check if I can see "Graduation rate is 85%" on the page
    Then I check if I can see "High Retention Rate" on the page
    Then I check if I can see "Retention rate is 93%" on the page
    Then I check if I can see "High Job Placement Rate" on the page
    Then I check if I can see "Job placement rate is 96% within a year and 87% within 6 months" on the page

  @MATCH-4243
  Scenario: As a HS student viewing the Why drawer of a particular college in my search results, I want clearly see
  what matched/did not match/partially matched Average class size
    Given SM I am logged in to SuperMatch through Family Connection
    And SM I skip the onboarding modals
    And SM I clear all pills from Must have  and Nice to have boxes
    And SM I clear pinned schools list
    And SM I click "Admission" filter criteria tab
    And SM I clean GPA/SAT/ACT scores
    And HS I Click on close button
    And SM I select the "Small City" checkbox from "Location" fit criteria
    And SM I select the "Historically Black Institutions" checkbox from "Diversity" fit criteria
    And SM I click "Institution Characteristics" filter criteria tab
    And SM I pick "10" from the dropdown "classsize-dropdown"
    Then SM I press Why button for "Paine College" college
    Then I check if I can see "Class size < 10" on the page
    Then I check if I can see "Average class size of 10 students" on the page
    Then SM I press Why button for "Bennett College" college
    Then I check if I can see "Class size < 10" on the page
    Then I check if I can see "Average class size of 14 students" on the page
    Then SM I press Why button for "North Carolina Central University" college
    Then I check if I can see "Class size < 10" on the page
    Then I check if I can see "Average class size of 24 students" on the page
    Then SM I press Why button for "Alabama State University" college
    Then I check if I can see "Class size < 10" on the page
    Then I check if I can see "Data unknown" on the page

  @MATCH-4243
  Scenario Outline: As a HS student viewing the Why drawer of a particular college in my search results, I want clearly see
  what matched/did not match/partially matched Student Body size
    Given SM I am logged in to SuperMatch through Family Connection
    And SM I skip the onboarding modals
    And SM I clear all pills from Must have  and Nice to have boxes
    And SM I click "Admission" filter criteria tab
    And SM I clean GPA/SAT/ACT scores
    And HS I Click on close button
    And SM I select the "Small City" checkbox from "Location" fit criteria
    And SM I select the "Historically Black Institutions" checkbox from "Diversity" fit criteria
    Then SM I pin "Bennett College" if it is not pinned already
    Then SM I pin "Florida Agricultural and Mechanical University" if it is not pinned already
    Then SM I select the "<Include>" checkbox from the "Institution Characteristics" fit criteria
    And SM I clear all pills from Must have  and Nice to have boxes
    Then SM I select the "Large (13,001 to 20,000 students)" checkbox from the "Institution Characteristics" fit criteria
    Then SM I press Why button for "Florida Agricultural and Mechanical University" college
    Then I check if I can see "Student Body Size [1]" on the page
    Then I check if I can see "<text>" on the page
    Then SM I press Why button for "Bennett College" college
    Then I check if I can see "Student Body Size [1]" on the page
    Then I check if I can see "<text>" on the page
    Then SM I press Why button for the first college in results with score 100%
    Then I check if I can see "Student Body Size [1]" on the page
    Then I check if I can see "<text>" on the page
    Examples:
      | Include                     | text                   |
      | All students                | total students         |
      | Undergraduate students only | undergraduate students |


  @MATCH-4243
  Scenario: As a HS student viewing the Why drawer of a particular college in my search results, I want clearly see
  what matched/did not match/partially matched On-Campus Housing
    Given SM I am logged in to SuperMatch through Family Connection
    And SM I skip the onboarding modals
    And SM I clear all pills from Must have  and Nice to have boxes
    And SM I clear pinned schools list
    And SM I click "Admission" filter criteria tab
    And SM I clean GPA/SAT/ACT scores
    And HS I Click on close button
    And SM I select the "Small City" checkbox from "Location" fit criteria
    And SM I select the "Historically Black Institutions" checkbox from "Diversity" fit criteria
    Then SM I pin "Bennett College" if it is not pinned already
    Then SM I pin "Florida Agricultural and Mechanical University" if it is not pinned already
    Then SM I select the "On-Campus Housing" checkbox from the "Institution Characteristics" fit criteria
    And SM I click "Institution Characteristics" filter criteria tab
    And SM I pick "66%" from the dropdown "on-campus-housing-dropdown"
    Then HS I Click on close button
    Then SM I press Why button for "Bennett College" college
    Then I check if I can see "On-campus Housing > 66%" on the page
    Then I check if I can see "61% of students live on-campus" on the page
    Then SM I press Why button for "Florida Agricultural and Mechanical University" college
    Then I check if I can see "On-campus Housing > 66%" on the page
    Then I check if I can see "22% of students live on-campus" on the page
    Then SM I press Why button for "Grambling State University" college
    Then I check if I can see "On-campus Housing > 66%" on the page
    Then I check if I can see "79% of students live on-campus" on the page

  @MATCH-4781
  Scenario: Adding tooltip to 'Average Class Size' fit criteria.
    Given SM I am logged in to SuperMatch through Family Connection
    And SM I skip the onboarding modals
    And SM I verify the tootip for "Average Class Size"