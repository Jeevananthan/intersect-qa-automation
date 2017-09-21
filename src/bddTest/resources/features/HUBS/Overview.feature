@HUBS-913
Feature: As a community user viewing College Hubs, I want to be able to view Hubs Overview Tab content so I can
  understand what Hubs offers students.

  Background:
    Given HE I want to login to the HE app using "mkaur@hobsons-us.com" as username and "Alma!234" as password
    And HUBS I access HUBS Edit Mode
    Then HUBS I open the "Overview" tab in the preview

  @HUBSStudies @HUBS-923 @HUBS-1005 @HUBS-992
  Scenario: All the elements of the page are displayed for HE users in Overview
    Then HUBS All the elements of the overview tab should be displayed
    And HE I successfully sign out

  Scenario: All the types of fields are editable in real time
    And HUBS I open "Overview" in the edit menu
    Then HUBS I should be able to edit the following fields for Overview in real time:
      | Opening Statement | test |
      | Website | test |
      | School Type  | Level;Corporation;Private / Corporate|
      | Undergraduate Enrollment | 10;10 |
      | Student / Faculty Ratio  | 777 |
      | Campus Surroundings      | City Size;Town, within 10 miles of urban area |
      | Test Scores              | SAT Critical Reading;Low;777                  |
      | Average GPA              | 7.7                                           |
      | Contact Information      | Application Mailing Address;ZIP;777           |
    And HE I successfully sign out

  Scenario: Changes done in HEM are successfully published to HUBS
    When HUBS I open "Overview" in the edit menu
    And HUBS I take note of the values from the following fields in Overview:
      | Opening Statement |
      | Website |
      | School Type  |
      | Undergraduate Enrollment |
      | Student / Faculty Ratio  |
      | Campus Surroundings      |
      | Test Scores              |
      | Average GPA              |
      | Contact Information      |
    And HUBS I edit all the fields in Overview based on the gathered values, with the following details:
      | Opening Statement |
      | Website |
      | School Type  |
      | Undergraduate Enrollment |
      | Student / Faculty Ratio  |
      | Campus Surroundings      |
      | Test Scores              |
      | Average GPA              |
      | Contact Information      |
      | test |
    And HE I successfully sign out
    And HUBS I approve the changes in CMS with the user email "mkaur@hobsons-us.com" and the following details:
      | admin | hbcmsxx | Alma College | Published |
    Then Then HUBS I should be able to verify the changes for Overview published in HUBS, with username "benhubs", password "Hobsons!23" and college "Alma", in the following sections
      | Opening Statement |
      | Website |
      | School Type  |
      | Undergraduate Enrollment |
      | Student / Faculty Ratio  |
      | Campus Surroundings      |
      | Test Scores              |
      | Average GPA              |
      | Contact Information      |
    And HUBS I successfully sign out

