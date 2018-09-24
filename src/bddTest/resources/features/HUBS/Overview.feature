@HUBS @HUBS-913
Feature: HUBS - Overview - As a community user viewing College Hubs, I want to be able to view Hubs Overview Tab content so I can
  understand what Hubs offers students.

  Background:
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HUBS I access HUBS Edit Mode
    Then HUBS I open the "Overview" tab in the preview

  @HUBS-923 @HUBS-1005 @HUBS-992
  Scenario: All the elements of the page are displayed for HE users in Overview (MATCH-3930)
    Then HUBS All the elements of the overview tab should be displayed
    And HE I successfully sign out

  @HUBS-1087
  Scenario: All the types of fields are editable in real time
    And HUBS I open "Overview" in the edit menu
    Then HUBS I should be able to edit the following fields for Overview in real time:
      | Opening Statement | test |
      | Website | test |
      | School Type  | Level;Corporation;Public / Corporate|
      | Undergraduate Enrollment | 10;10 |
      | Student / Faculty Ratio  | 777 |
      | Campus Surroundings      | City Size;Town, within 10 miles of urban area |
      | Test Scores              | SAT Critical Reading;25th Percentile;777                  |
      #MATCH-3775: Average GPA is not updated in real time
      #| Average GPA              | 7.7                                           |
      #MATCH-3780: Contact Information is not editable
      #| Contact Information      | Application Mailing Address;ZIP;777           |
    And HE I successfully sign out

  Scenario: Changes done in HEM are successfully published to HUBS (MATCH-3957 - MATCH-4652)
    When HUBS I open "Overview" in the edit menu
    And HUBS I take note of the values from the following fields in Overview:
      | Opening Statement |
      | Website |
      | School Type  |
      | Undergraduate Enrollment |
      | Student / Faculty Ratio  |
      | Campus Surroundings      |
      | Test Scores;SAT;SAT 2400 Reading;25th Percentile |
      #| Average GPA              |
      #| Contact Information      |
    And HUBS I edit all the fields in Overview based on the gathered values, with the publish reason "test"
    And HE I successfully sign out
    And HUBS I approve the changes in CMS with the user email "purpleheautomation@gmail.com" and the following details:
      | admin | hbcmsxx | The University of Alabama | Published |
    And HUBS I successfully sign out from CMS
    Then HUBS I should be able to verify the changes for Overview published in HUBS, with username "benhubs", password "Hobsons!23" and college "The University of Alabama", in the following sections
      | Opening Statement |
      | Website |
      | School Type  |
      | Undergraduate Enrollment |
      | Student / Faculty Ratio  |
      | Campus Surroundings      |
      | Test Scores;SAT;SAT 2400 Reading;25th Percentile            |
      #| Average GPA              |
      #| Contact Information      |
    And HUBS I successfully sign out

  @MATCH-4157
  Scenario: User have ability to Edit 25th and 75th GPA percentile
    When HUBS I open "Overview" in the edit menu
    Then HUBS I should be able to edit the following fields for Overview in real time:
      | 25th Percentile (visible only in SuperMatch) | 2.5 |
      | 75th Percentile (visible only in SuperMatch) | 2.9 |




