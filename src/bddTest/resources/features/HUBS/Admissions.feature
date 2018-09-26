@HUBS
Feature: HUBS - Admissions - As a community user viewing College Hubs, I want to be able to view Hubs Admissions Tab content so I can understand
  what Hubs offers students.

  Background:
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HUBS I access HUBS Edit Mode
    Then HUBS I open the "Admissions" tab in the preview

  Scenario: All the elements of the page are displayed for HE users in Costs (MATCH-4009)
    Then HUBS All the elements of the admissions tab should be displayed
    And HE I successfully sign out

  Scenario: All the types of fields are editable in real time
    And HUBS I open "Admissions" in the edit menu
    Then HUBS I should be able to edit the following fields for Admissions in real time:
      | Acceptance Rate | 6666 | 7777 |
      | Important Policies | Admissions Policy  | Rolling admissions policy  |
      | Deadlines          | Early Decision | month;February        |
      | Fees               | Transfer Deposit Fee | 777                      |
      | Application Requirements | Freshman       | Campus Visit;Optional    |
      | Recommended Courses      | Science        | Years Required;7         |
      | Application Factors      | Ethnicity | Considered |

  @HUBS-1052
  Scenario: Changes done in HEM are successfully published to HUBS (MATCH-4022)
    When HUBS I open "Admissions" in the edit menu
    And HUBS I take note of the values from the following fields in Admissions:
      | Acceptance Rate |
      | Important Policies;Wait List |
      | Deadlines |
      | Fees               |
      | Application Requirements;Required;Campus Visit |
      | Recommended Courses;Math;Years Required |
      | Application Factors;Class Rank;Important |
    And HUBS I edit all the fields in Admissions based on the gathered values, with the following details:
      | Application Factors | Ethnicity |
      | Publish Reason      | test      |
      | Deadlines           | Early Decision |
    And HUBS I approve the changes in CMS with the user email "purpleheautomation@gmail.com" and the following details:
    | admin | hbcmsxx | The University of Alabama  |
    And HUBS I successfully sign out from CMS
    Then HUBS I should be able to verify the changes for admissions published in HUBS, with username "benhubs", password "Hobsons!23" and college "The University of Alabama", in the following sections
      | Acceptance Rate |
      | Important Policies;Wait List |
      | Deadlines          |
      | Fees               |
      | Application Requirements;Required;Campus Visit |
      | Recommended Courses;Math;Years Required |
      | Application Factors;Class Rank;Important      |
