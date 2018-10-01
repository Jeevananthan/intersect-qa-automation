@HS
Feature: HS - RepVisits - CollegeVisits - As an HS user, I want my inputs to be validated in RVs on the Intersect side before I submit the form

  @MATCH-3600
  Scenario: As a HS RepVisits user I want my inputs to be validated in the Schedule New Visit form
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    When HS I navigate to the "Calendar" page in RepVisits
    And HS I click on button Add Visit
    And HS I verify the following input validations in the Schedule New Visit form:
    | Student Registration Deadline | days | 0 |
    | Student Registration Deadline | days | 1000 |
    | Custom Time     | Start Time/End Time  | qwerty |
    | Representative          | First Name/Last Name | 30.char.name |
    | Representative          | Institution | 120.char.text |
    And HS I successfully sign out

  @MATCH-3600
  Scenario: As a HS RepVisits user I want my inputs to be validated in the Naviance Settings form
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone6"
    And HS I go to the Naviance settings
    Then HS I verify the input validations for Student Registration Deadline with the data:
    | Registration will close | 1000 |
    | Hours or Days option | hours |

  @MATCH-3600
  Scenario: As a HS RepVisits user I want my inputs to be validated in the Visit Details form
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    When HS I navigate to the "Calendar" page in RepVisits
    And HS I click on button Add Visit
    And HS I create a visit "1" days ahead from now with the following details
      | Start Time | 09:36am |
      | End Time   | 10:30am |
      | Representative | Franky |
    And HS I open the visit with generated time in the Calendar
    And HS I open the Reschedule New Visit form
    Then HS I verify the following input validations in the Reschedule Visit form:
      | Reschedule Message | asd |
      | Start Time | asdfasdf |
      | End Time   | asdfasdd |
    And HS I close the Reschedule Visit form
    And HS I open the visit with generated time in the Calendar
    And HS I cancel the open visit