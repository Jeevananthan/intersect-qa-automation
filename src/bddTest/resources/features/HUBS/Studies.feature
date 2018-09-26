@HUBS @HUBS-915 @HUBSStudies
Feature: HUBS - Studies - As a community user viewing College Hubs, I want to be able to view Studies Tab content so I can
  understand what Hubs offers students.

  Background:
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HUBS I access HUBS Edit Mode
    Then HUBS I open the "Studies" tab in the preview

  Scenario: All the elements of the page are displayed for HE users in Studies
    Then HUBS All the elements of the studies tab should be displayed

  Scenario: All the types of fields are editable in real time
    And HUBS I open "Studies" in the edit menu
    Then HUBS I should be able to edit the following fields in real time:
      | Student/Faculty Ratio|13 |
      | Student Retention (%)|82 |
      | Graduation Rate (%)  |64   |
      | Top Areas of Study   |1;test |
      | Study Options        |Study Abroad Credit;disabled |

  @HUBS-1043
  Scenario: Changes done in HEM are successfully published to HUBS
    When HUBS I open "Studies" in the edit menu
    And HUBS I take note of the values from the following fields:
      | Student/Faculty Ratio |
      | Student Retention (%) |
      | Graduation Rate (%)   |
      | Top Areas of Study    |
      | Study Options;Study Abroad Credit |
    And HUBS I edit all the fields based on the gathered values, with the following details:
      | Top Areas of Study | 1                   |
      | Study Options      | Study Abroad Credit |
      | Publish Reason     | test                |
    And HE I successfully sign out
    And HUBS I approve the changes in CMS with the user email "purpleheautomation@gmail.com" and the following details:
      | admin | hbcmsxx | The University of Alabama |
    And HUBS I successfully sign out from CMS
    Then HUBS I should be able to verify the changes published in HUBS, with the following credentials:
    #  The fourth parameter is the Study Option that will be evaluated.
    | benhubs | Hobsons!23 | The University of Alabama | Study Abroad Credit |

