@SM
Feature: SM - Admission - Admission - As a HS student, I need to be able to search for colleges based on the 'Admission' fit criteria

  @MATCH-3379
  Scenario: As a HS student, I want to filter colleges I am searching for by my specific GPA within the Admission
            category so I can see relevant colleges that accept students similar to me based on my GPA in my search
            results.
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I skip the onboarding modals
    Then SM I verify the system response when the GPA entered by the user is valid
      |0.1|
      |2  |
      |4  |
    Then SM I verify the system response when the GPA entered by the user is invalid
      |0  |
      |4.1|
      |5  |
    Then SM I verify that entered GPA data persists
      |3|
    Then SM I verify that the Must Have box does not contain "GPA"

  @MATCH-3382
  Scenario: As a HS student, I want to filter colleges I am searching for by my specific ACT Scores within the Admission
            category so I can see relevant colleges that accept students similar to me based on my ACT Scores in my
            search results.
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I skip the onboarding modals
    Then SM I verify the system response when the ACT score entered by the user is valid
     |1 |
     |18|
     |36|
    Then SM I verify the system response when the ACT score entered by the user is invalid
     |0|
     |37|
    Then SM I verify that entered ACT score data persists
     |35|
    Then SM I verify that the Must Have box does not contain "ACT"

  @MATCH-3381
  Scenario: As a HS student, I want to filter colleges I am searching for by my specific SAT Scores within the Admission
  category so I can see relevant colleges that accept students similar to me based on my SAT Scores in my search
  results.
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I skip the onboarding modals
    Then SM I verify the system response when the SAT score entered by the user is valid
    Then SM I verify the system response when the SAT score entered by the user is invalid
    Then SM I verify that SAT score persists when changing fit criteria
    Then SM I verify that the Must Have box does not contain "SAT"

  @MATCH-3386
  Scenario Outline: As a HS student, I want to filter colleges I am searching for by miscellaneous admission details
  within the Admission category so I can see relevant colleges that match my miscellaneous admission
  details requirements
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I skip the onboarding modals
    Then SM I select the "<AdmissionCheckbox>" checkbox from the "Admission" fit criteria
    And SM I verify the "<AdmissionCheckbox>" checkbox from the "Admission" fit criteria
    And SM I verify that the Must Have box contains "<AdmissionCheckbox>"
    Then SM I unselect the "<AdmissionCheckbox>" checkbox from the "Admission" fit criteria
    And SM I verify that the Must Have box does not contain "<AdmissionCheckbox>"
    Then SM I select the "<AdmissionCheckbox>" checkbox from the "Admission" fit criteria
    And SM I move "<AdmissionCheckbox>" from the Must Have box to the Nice to Have box
    Then SM I verify that the Nice to Have box contains "<AdmissionCheckbox>"
    Then SM I unselect the "<AdmissionCheckbox>" checkbox from the "Admission" fit criteria
    And SM I verify that the Must Have box does not contain "<AdmissionCheckbox>"
    And SM I verify that Nice to Have box does not contain "<AdmissionCheckbox>"
    Then SM I select the "<AdmissionCheckbox>" checkbox from the "Admission" fit criteria
    And SM I verify that the Must Have box contains "<AdmissionCheckbox>"
    Examples: Each of the available options for the Admission
      | AdmissionCheckbox    |
      | Accepts AP Credits   |
      | Accepts IB Credits   |
      | Test Optional        |
      | Common App Member    |
      | Coalition App Member |
      | No Application Fee   |

  @MATCH-3385
  Scenario: As a HS student, I want to  see `Application deadline on/after:`  and be able to pick date
    Given SM I am logged in to SuperMatch through Family Connection
    And SM I skip the onboarding modals
    And SM I clear all pills from Must have  and Nice to have boxes
    When SM I click "Admission" filter criteria tab
    Then I check if I can see "Regular Application Deadline" on the page
    Then SM I press button "Select date"
    And SM I pick the date "11/13/2018" from the date picker
    Then SM I verify that the Must Have box contains "Application Deadline is on/after Nov 13"

  @MATCH-3385
  Scenario: As a HS student, I want to  pick\wipe\reselect `Application deadline on/after:`
    Given SM I am logged in to SuperMatch through Family Connection
    And SM I skip the onboarding modals
    And SM I clear all pills from Must have  and Nice to have boxes
    When SM I click "Admission" filter criteria tab
    Then I check if I can see "Regular Application Deadline" on the page
    Then SM I press button "Select date"
    And SM I pick the date "01/13/2018" from the date picker
    Then SM I click clear calendar icon
    Then I check if I can see "Select date" on the page
    Then SM I press button "Select date"
    And SM I pick the date "03/13/2018" from the date picker
    Then SM I verify that the Must Have box contains "Application Deadline is on/after Mar 13"


  @MATCH-3385
  Scenario: As a HS student, I want to  pick `Application deadline on/after:` move it to Nice to have box, remove and select
  again and see in Must to have box
    Given SM I am logged in to SuperMatch through Family Connection
    And SM I skip the onboarding modals
    And SM I clear all pills from Must have  and Nice to have boxes
    When SM I click "Admission" filter criteria tab
    Then I check if I can see "Regular Application Deadline" on the page
    Then SM I press button "Select date"
    And SM I pick the date "01/13/2018" from the date picker
    Then SM I move "Application Deadline is on/after Jan 13" from the Must Have box to the Nice to Have box
    And SM I clear all pills from Must have  and Nice to have boxes
    And SM I click "Admission" filter criteria tab
    And I check if I can see "Regular Application Deadline" on the page
    And SM I press button "Select date"
    And SM I pick the date "01/13/2018" from the date picker
    Then SM I verify that the Must Have box contains "Application Deadline is on/after Jan 13"

  @MATCH-4241
  Scenario: As a HS student viewing the Why drawer of a particular college in my search results,
  I want to see the actual admission data for the college so I can clearly see what
  matched/did not match/partially matched Acceptance rate.
    Given SM I am logged in to SuperMatch through Family Connection
    And SM I skip the onboarding modals
    And SM I clear all pills from Must have  and Nice to have boxes
    And SM I click "Admission" filter criteria tab
    And SM I clean GPA/SAT/ACT scores
    And HS I Click on close button
    And SM I select the "Small City" checkbox from "Location" fit criteria
    And SM I select the "Historically Black Institutions" checkbox from "Diversity" fit criteria
    And SM I pin "Bennett College" if it is not pinned already
    And SM I pin "Texas College" if it is not pinned already
    And SM I select the "25% or Lower" checkbox from "Admission" fit criteria
    Then SM I press Why button for "Bennett College" college
    Then I check if I can see "Acceptance Rate [1]" on the page
    Then I check if I can see "Acceptance rate is 98%" on the page
    Then SM I press Why button for "Texas College" college
    Then I check if I can see "Acceptance Rate [1]" on the page
    Then I check if I can see "Open Admissions" on the page
    Then SM I press Why button for "Paine College" college
    Then I check if I can see "Acceptance Rate [1]" on the page
    Then I check if I can see "Acceptance rate is 25%" on the page



  @MATCH-4241
  Scenario: As a HS student viewing the Why drawer of a particular college in my search results,
  I want to see the actual admission data for the college so I can clearly see what
  matched/did not match/partially matched Application Deadline.
    Given SM I am logged in to SuperMatch through Family Connection
    And SM I skip the onboarding modals
    And SM I clear all pills from Must have  and Nice to have boxes
    And SM I click "Admission" filter criteria tab
    And SM I clean GPA/SAT/ACT scores
    And HS I Click on close button
    And SM I select the "Small City" checkbox from "Location" fit criteria
    And SM I select the "Historically Black Institutions" checkbox from "Diversity" fit criteria
    And SM I pin "Bennett College" if it is not pinned already
    When SM I click "Admission" filter criteria tab
    Then SM I press button "Select date"
    And SM I pick the date "01/09/2018" from the date picker
    And HS I Click on close button
    Then SM I press Why button for "Bennett College" college
    Then I check if I can see "Application Deadline is on/after Jan 9" on the page
    Then I check if I can see "Data unknown" on the page
    Then SM I press Why button for "Alabama State University" college
    Then I check if I can see "Application Deadline is on/after Jan 9" on the page
    Then I check if I can see "Regular application deadline is on Jul 30" on the page

  @MATCH-3787
  Scenario: As a HS student, I want to filter colleges I am searching for by Acceptance Rate within the Admission
  category so I can see relevant colleges that match my Acceptance Rate requirements.
    Given SM I am logged in to SuperMatch through Family Connection
    And SM I skip the onboarding modals
    And SM I clear all pills from Must have  and Nice to have boxes
    And SM I click "Admission" filter criteria tab
    And SM I select the "25% or Lower" checkbox from "Admission" fit criteria
    And SM I select the "26%-50%" checkbox from "Admission" fit criteria
    And SM I select the "51%-75%" checkbox from "Admission" fit criteria
    And SM I select the "76% or more" checkbox from "Admission" fit criteria
    And SM I select the "Open Admissions" checkbox from "Admission" fit criteria
    And SM I click "Admission" filter criteria tab
    Then SM I verify that the Must Have box contains "Acceptance Rate [5]"
    And SM I unselect the "25% or Lower" checkbox from the "Admission" fit criteria
    And SM I unselect the "26%-50%" checkbox from the "Admission" fit criteria
    And SM I unselect the "51%-75%" checkbox from the "Admission" fit criteria
    And SM I click "Admission" filter criteria tab
    Then SM I verify that the Must Have box contains "Acceptance Rate [2]"
