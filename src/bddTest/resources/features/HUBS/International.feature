@HUBS-945 @HUBS-1060
Feature: As a community user viewing College Hubs, I want to be able to view Hubs Overview Tab content so I can
  understand what Hubs offers students.

  Background:
    Given HE I want to login to the HE app using "testhobsons678@mailinator.com" as username and "Hobsons!23" as password
    And HUBS I access HUBS Edit Mode
    Then HUBS I open the "International" tab in the preview
  @HUBS-946
  Scenario: All the elements of the page are displayed for HE users in International
    Then HUBS All the elements of the international tab should be displayed
    And HE I successfully sign out

  Scenario: All the types of fields are editable in real time
    And HUBS I open "International" in the edit menu
    Then HUBS I should be able to edit the following fields for International in real time:
      | Application Deadline | Day | 1 |
      | Fees | Application Fee     | 25 |
      | Test Requirements | SAT | Recommended |
      | Applications | Received | 666 |
      | Test Scores  | Michigan (MELAB) | Low;777 |
      | Qualifications | Ability to Finance | Recommended |
      | Accepted English Tests | TOEFL      | -           |
    And HE I successfully sign out
  @HUBS-803 @HUBS-1063 @HUBS-1024
  Scenario: Changes done in HEM are successfully published to HUBS
    When HUBS I open "International" in the edit menu
    And HUBS I take note of the values from the following fields in International:
      | Application Deadline | Day |
      | Fees | Application Fee     |
      | Test Requirements | SAT |
      | Applications | Received |
      | Test Scores  | TOEFL (Computer);Low |
      | Qualifications | Ability to Finance |
      | Accepted English Tests | TOEFL      |
    And HUBS I edit all the fields in International based on the gathered values, with the following details:
      | Application Deadline | Day |
      | Fees | Application Fee     |
      | Test Requirements | SAT |
      | Applications | Received |
      | Test Scores  | TOEFL (Computer);Low |
      | Qualifications | Ability to Finance |
      | Accepted English Tests | TOEFL      |
      | Publishing reason      | test       |
    And HE I successfully sign out
    And HUBS I approve the changes in CMS with the user email "testhobsons678@mailinator.com" and the following details:
      | admin | hbcmsxx | University of Montevallo | Published |
    Then Then HUBS I should be able to verify the changes for International published in HUBS, with username "samstudent", password "Hobsons!23" and college "University of Montevallo", in the following sections
      | Application Deadline | Day |
      | Fees | Application Fee     |
      | Test Requirements | SAT |
      | Applications | Received |
      | Test Scores  | TOEFL (Computer);Low |
      | Qualifications | Ability to Finance |
      | Accepted English Tests | TOEFL      |
    And HUBS I successfully sign out

