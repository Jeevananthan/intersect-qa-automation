@SM
Feature: SM - SuperMatchSearch - As a HS student accessing SuperMatch through Family Connection I need to be able to search college based on
  certain fit criteria

  @MATCH-3592
  Scenario: As a HS student accessing SuperMatch through Family Connection I need to be presented with an Student Body
            Size fit criteria
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I verify the Student Body UI in Resources Dropdown

  @MATCH-3246 @MATCH-3471
  Scenario: As a HS student accessing College Search through Family Connection I need to be presented with an
            'empty state' page (no filters selected yet) so I can perform a search when ready.
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I verify if dark blue header is present
    Then SM I verify if Your Fit Criteria text is present
    Then SM I verify the Choose Fit Criteria bar
    Then SM I verify Select Criteria to Start button and instructional text
    Then SM I verify Must Have and Nice to Have boxes
    Then SM I verify the empty results table
    Then SM I verify the dark blue footer

  @MATCH-3208
  Scenario: As a HS student accessing SuperMatch through Family Connection I need to be able to
            add or remove filter criteria from the Must Have box
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I select the "Counseling Services" checkbox from the Resources fit criteria
    Then SM I remove the "Counseling Services" fit criteria from the Must Have box or Nice to Have box
    And SM I verify that the Must Have box does not contain "Counseling Services"
    And SM I verify that the "Counseling Services" checkbox from the Resources fit criteria is "unchecked"

  @MATCH-3911
  Scenario: As a HS student who is interacting with the 'Academics' fit category dropdown, selected Bachelor's for the
            degree fit criteria, and has searched and selected at least one Major and one Minor, I want SuperMatch to
            treat those two entities as separate fit criteria so I can moved Majors and Minors around independently
            between the Must Have and Nice to Have boxes.
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I select the "Bachelor's" radio button from the Academics fit criteria
    Then SM I select the following majors in the SEARCH MAJORS multi-select combobox for Bachelor's degree type
    |Accounting|
    And SM I verify that the Must Have box contains "Major [1]"
    Then SM I select the following minors in the SEARCH MINORS multi-select combobox for Bachelor's degree type
    |Drawing|
    And SM I verify that the Must Have box contains "Minor [1]"
    Then SM I move "Minor [1]" from the Must Have box to the Nice to Have box
    And SM I verify that the Must Have box contains "Major [1]"
    And SM I verify that the Nice to Have box contains "Minor [1]"
    Then SM I move "Major [1]" from the Must Have box to the Nice to Have box
    And SM I verify that the Nice to Have box contains "Major [1]"
    And SM I verify that the Nice to Have box contains "Minor [1]"
    Then SM I unselect the following majors in the SEARCH MAJORS multi-select combobox for Bachelor's degree type
    |Accounting|
    Then SM I unselect the following minors in the SEARCH MINORS multi-select combobox for Bachelor's degree type
    |Drawing|
    And SM I verify that the Must Have box does not contain "Major [1]"
    And SM I verify that the Must Have box does not contain "Minor [1]"
    And SM I verify that Nice to Have box does not contain "Major [1]"
    And SM I verify that Nice to Have box does not contain "Minor [1]"
    Then SM I select the following majors in the SEARCH MAJORS multi-select combobox for Bachelor's degree type
    |Accounting|
    Then SM I select the following minors in the SEARCH MINORS multi-select combobox for Bachelor's degree type
    |Drawing|
    And SM I verify that the Must Have box contains "Major [1]"
    And SM I verify that the Must Have box contains "Minor [1]"
    And SM I verify that Nice to Have box does not contain "Major [1]"
    And SM I verify that Nice to Have box does not contain "Minor [1]"
    Then SM I select the "Associate's" radio button from the Academics fit criteria
    And SM I verify that the Must Have box does not contain "Major [1]"
    And SM I verify that the Must Have box does not contain "Minor [1]"

  @MATCH-3667
  Scenario: Verify that the box containing instructional text has a width of 25% and the Must Have
  and Nice to Have boxes split the rest
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I verify the widths of the three boxes

  @MATCH-3339
  Scenario: As a HS student looking to search for colleges, I want each fit category that I see in the 'Choose Fit Criteria'
  header bar to be clickable so I can select specific fit criteria.
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I verify each fit category in the Choose Fit Criteria header bar is clickable and match the color
    Then SM I verify clicking outside of the box will also close the box
    And SM I check both Select Criteria To Start buttons take the user to the Location dropdown


  @MATCH-3371
  Scenario Outline: As a HS student, I want to filter colleges I am searching for by Diversity checkboxes within the Diversity
  category so I can see relevant colleges that match my Diversity checkboxes requirements.
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I "select" the "<DiversityCheckboxOption>" checkbox from the Diversity
    And SM I verify that the Must Have box contains "<DiversityCheckboxOption>"
    Then SM I "unselect" the "<DiversityCheckboxOption>" checkbox from the Diversity
    And SM I verify that the Must Have box does not contain "<DiversityCheckboxOption>"
    Then SM I "select" the "<DiversityCheckboxOption>" checkbox from the Diversity
    And SM I move "<DiversityCheckboxOption>" from the Must Have box to the Nice to Have box
    Then SM I "unselect" the "<DiversityCheckboxOption>" checkbox from the Diversity
    And SM I verify that the Must Have box does not contain "<DiversityCheckboxOption>"
    And SM I verify that Nice to Have box does not contain "<DiversityCheckboxOption>"
    Then SM I "select" the "<DiversityCheckboxOption>" checkbox from the Diversity
    And SM I verify that the Must Have box contains "<DiversityCheckboxOption>"
    Examples: Each of the available options for the Student Life fit criteria
    |DiversityCheckboxOption         |
    |Historically Black Institutions |
    |Tribal Colleges and Universities|

  @MATCH-3432 @MATCH-4317
  Scenario: As a HS student reviewing results in SuperMatch, I want to be able to select what details I see on each
            college in my search results so the information I care most about is visible to review.
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I select the "Counseling Services" checkbox from the Resources fit criteria
    Then SM I verify the default column headers displayed in the results table
    |Admission Info   |
    |Cost             |
    |Pick what to show|
    Then SM I verify if the option selected or defaulted in column header can be changed to "Athletics"

  @MATCH-3506
  Scenario: As a HS student, I want to be able to close the Save Search popup
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I verify that the Save Search button is disabled
    And I clear the onboarding popups if present
    When I select the following data from the Admission Fit Criteria
      | GPA (4.0 scale) | 4 |
      | SAT Composite   | 400 |
      | ACT Composite   | 3   |
      | Acceptance Rate | 25% or Lower |
    And SM I open the Save Search popup
    Then SM I verify that the Save Search popup is closed when I use the Cancel action

  @MATCH-3506
  Scenario: As a HS student, I want to verify that all the text displayed in the Save Search popup is correct
    Given SM I am logged in to SuperMatch through Family Connection
    And I clear the onboarding popups if present
    When I select the following data from the Admission Fit Criteria
      | GPA (4.0 scale) | 4 |
      | SAT Composite   | 400 |
      | ACT Composite   | 3   |
      | Acceptance Rate | 25% or Lower |
    And SM I open the Save Search popup
    Then SM I verify that the text inside the Save Search popup is correct

  @MATCH-3506
  Scenario: As a HS student, I want to verify the error messages in the Save Search popup
    Given SM I am logged in to SuperMatch through Family Connection
    And I clear the onboarding popups if present
    When I select the following data from the Admission Fit Criteria
      | GPA (4.0 scale) | 4 |
      | SAT Composite   | 400 |
      | ACT Composite   | 3   |
      | Acceptance Rate | 25% or Lower |
    And SM I open the Save Search popup
    Then SM I verify the error message for more than "50" characters
    And SM I verify the error message for less than "3" characters

  @MATCH-3506 @MATCH-3508
  Scenario: As a HS student, I want to verify that the save/load search functionality (MATCH-4703)
    Given SM I am logged in to SuperMatch through Family Connection
    And I clear the onboarding popups if present
    When I select the following data from the Admission Fit Criteria
      | GPA (4.0 scale) | 4 |
      | SAT Composite   | 400 |
      | ACT Composite   | 3   |
      | Acceptance Rate | 25% or Lower |
    And SM I open the Save Search popup
    And SM I save the search with the name "SavedTestSearch"
    Then SM I verify the confirmation message
    Then SM I verify the saved search of name "SavedTestSearch" is displayed in the Saved Searches dropdown
    And SM I select "SavedTestSearch" in the Saved Searches dropdown
    Then SM I verify that "SavedTestSearch" is displayed as selected option in the Saved Searches dropdown

  @MATCH-4276
  Scenario: As a HS student, I want to see specific footnotes when SuperMatch does not know my GPA and does not know my test scores
    Given SM I am logged in to SuperMatch through Family Connection
    And I clear the onboarding popups if present
    When I select the following data from the Admission Fit Criteria
      | Acceptance Rate | 25% or Lower |
    Then SM I verify the footnote for no GPA and no other scores, with the text:
      | To determine if you're an academic match for this institution, enter your GPA and/or standardized test scores. |

  @MATCH-4276
  Scenario: As a HS student, I want to see specific footnotes when SuperMatch does know my GPA, but not my test scores
    Given SM I am logged in to SuperMatch through Family Connection
    And I clear the onboarding popups if present
    When I select the following data from the Admission Fit Criteria
      | GPA (4.0 scale) | 3 |
      | Acceptance Rate | 76% or more |
    And SM I select the "Coed" checkbox from "Diversity" fit criteria
    Then SM I verify the footnote for known GPA but unknown test scores for "Virginia Wesleyan University", with the text:
    | To best determine if you're an academic match for this institution, enter both your GPA and standardized test scores. |

   @MATCH-4276
   Scenario: As a HS student, I want to see specific footnotes when SuperMatch does know my test scores, but not my GPA
     Given SM I am logged in to SuperMatch through Family Connection
     And I clear the onboarding popups if present
     When I select the following data from the Admission Fit Criteria
       | SAT Composite   | 1500 |
       | ACT Composite   | 30   |
       | Acceptance Rate | 76% or more |
     And SM I select the "Coed" checkbox from "Diversity" fit criteria
     Then SM I verify the footnote for known GPA but unknown test scores for "Utica College", with the text:
       | To best determine if you're an academic match for this institution, enter both your GPA and standardized test scores. |


