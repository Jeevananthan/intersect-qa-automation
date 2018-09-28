@HUBS @HUBS-945 @HUBS-1060
Feature: HBS - International - As a community user viewing College Hubs, I want to be able to view Hubs International Tab content so I can
  understand what Hubs offers students.

  Background:
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HUBS I access HUBS Edit Mode
    Then HUBS I open the "International" tab in the preview

  @HUBS-946 @HUBS-992 @HUBS-803
  Scenario: All the elements of the page are displayed for HE users in International
    Then HUBS All the elements of the international tab should be displayed


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

  @HUBS-803 @HUBS-1063 @HUBS-1024 @HUBS-1060 @HUBS-1024
  Scenario: Changes done in HEM are successfully published to HUBS (MATCH-4651)
    When HUBS I open "International" in the edit menu
    And HUBS I take note of the values from the following fields in International:
      | Application Deadline | Day |
      | Fees | Application Fee     |
      | Test Requirements | SAT |
      | Applications | Received |
      | Test Scores  | Michigan (MELAB);Low |
      | Qualifications | Ability to Finance |
      | Accepted English Tests | TOEFL      |
    And HUBS I edit all the fields in International based on the gathered values, with the following details:
      | Application Deadline | Day |
      | Fees | Application Fee     |
      | Test Requirements | SAT |
      | Applications | Received |
      | Test Scores  | Michigan (MELAB);Low |
      | Qualifications | Ability to Finance |
      | Accepted English Tests | TOEFL      |
      | Publishing reason      | test       |
    And HE I successfully sign out
    And HUBS I approve the changes in CMS with the user email "purpleheautomation@gmail.com" and the following details:
      | admin | hbcmsxx | The University of Alabama | Published |
    And HUBS I successfully sign out from CMS
    Then HUBS I should be able to verify the changes for International published in HUBS, with username "samstudent", password "Hobsons!23" and college "The University of Alabama", in the following sections
      | Application Deadline | Day |
      | Fees | Application Fee     |
      | Test Requirements | SAT |
      | Applications | Received |
      | Test Scores  | TOEFL (Paper);Low |
      | Qualifications | Ability to Finance |
      | Accepted English Tests | TOEFL      |


