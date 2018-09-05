@HUBS @HUBSStudentLife @HUBS-942
Feature: HUBS - StudentLife - As a community user viewing College Hubs, I want to be able to view Student Life Tab content so I can
  understand what Hubs offers students.

  Background:
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HUBS I access HUBS Edit Mode
    Then HUBS I open the "Student Life" tab in the preview

  Scenario: All the elements of the page are displayed for HE users in Student Life
    Then HUBS All the elements of the student life tab should be displayed
    And HE I successfully sign out

  Scenario: All the types of fields are editable in real time
    And HUBS I open "Student Life" in the edit menu
    Then HUBS I should be able to edit the following fields for Student Life in real time:
      | School Size  | Undergraduate Women | 777  |
      | School Size  | Undergraduate Men | 777  |
      | School Size  | Graduate Women | 777  |
      | School Size  | Graduate Men | 777  |
      | Nearest City | Nearest City        | test |
      | Ethnicity    | % African American  | 20  |
      | Gender Data  | Undergraduate Men   | 666  |
      | Age Data     | % Students 24 Years Old | 15 |
      | Housing Data | Campus Housing Capacity | 666 |
      | Greek Life   | # of Fraternaties       | 88  |
      | Services     | Assistance for Mobility Impaired | null |
      | Computing Resources | # of PCs in Computer Centers | 111 |
      | Organizations       | Academic Clubs               | yes |
      | Athletics           | Men                     | Track And Field;Association;NWAACC |
    And HE I successfully sign out

  @HUBS-1044 @HUBS-1023
  Scenario: Changes done in HEM are successfully published to HUBS (MATCH-4055)
    When HUBS I open "Student Life" in the edit menu
    And HUBS I take note of the values from the following fields in Student Life:
      | School Size  | Total Students  |
      | Nearest City | Nearest City        |
      | Ethnicity    | % African American  |
      | Gender Data  | Female   |
      | Age Data     | % Students 24 Years Old |
      | Housing Data | Campus Housing Capacity |
      | Greek Life   | # of Fraternaties       |
      | Services     | Academic/Career Counseling |
      | Computing Resources | Library;pc |
      | Organizations       | Academic Clubs |
      | Athletics           | Men;Track And Field;Division|
    And HUBS I edit all the fields in Student Life based on the gathered values, with the following details:
      | School Size  | Total Students  |
      | Nearest City | Nearest City        |
      | Ethnicity    | % African American  |
      | Gender Data  | Female   |
      | Age Data     | % Students 24 Years Old |
      | Housing Data | Campus Housing Capacity |
      | Greek Life   | # of Fraternaties       |
      | Services     | Academic/Career Counseling |
      | Computing Resources | # of PCs in Libraries |
      | Organizations       | Academic Clubs |
      | Athletics           | Men;Track And Field;Division|
      | Publish Reason      | test |
    And HE I successfully sign out
    And HUBS I approve the changes in CMS with the user email "purpleheautomation@gmail.com" and the following details:
      | admin | hbcmsxx | The University of Alabama  |
    And HUBS I successfully sign out from CMS
    Then HUBS I should be able to verify the changes for Student Life published in HUBS, with username "benhubs", password "Hobsons!23" and college "The University of Alabama", in the following sections
      | School Size  | null |
      | Nearest City | null |
      | Ethnicity    | % African American |
      | Gender Data  | Male |
      | Age Data     | % Students 24 Years Old |
      | Housing Data | Campus Housing Capacity |
      | Greek Life   | # of Fraternaties |
      | Services     | Academic/Career Counseling |
      | Computing Resources | Library;pc |
      | Organizations       | Academic Clubs |
      | Athletics           | Men;Track And Field;Division |
    And HUBS I successfully sign out

