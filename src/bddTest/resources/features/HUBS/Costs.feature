@HUBS @HUBS-918 @HUBSCosts @HUBS-944
Feature: HUBS - Costs - As a community user viewing College Hubs, I want to be able to view Hubs Cost Tab content so I can understand
  what Hubs offers students.

  Background:
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HUBS I access HUBS Edit Mode
    Then HUBS I open the "Costs" tab in the preview

  Scenario: All the elements of the page are displayed for HE users in Costs
    Then HUBS All the elements of the costs tab should be displayed
    And HE I successfully sign out

  Scenario: All the types of fields are editable in real time
    And HUBS I open "Costs" in the edit menu
    Then HUBS I should be able to edit the following fields for Costs in real time:
      | Average Net Prices | $48,001 - $75,000 | 42300 |
      | % Receiving Aid    | Pell Grant        | 64    |
      | Average Amount of Aid | Grant          | 1111  |

  @HUBS-1080 @Unstable
  Scenario: Changes done in HEM are successfully published to HUBS
    When HUBS I open "Costs" in the edit menu
    And HUBS I take note of the values from the following fields in Costs:
      | Average Net Prices    | $48,001 - $75,000 |
      | % Receiving Aid       | Pell Grant        |
      | Average Amount of Aid | Grant             |
    And HUBS I edit all the fields in Costs based on the gathered values, with the following details:
      | Average Net Prices    | $48,001 - $75,000 |
      | % Receiving Aid       | Pell Grant        |
      | Average Amount of Aid | Grant             |
      | Publish Reason        | test              |
    And HUBS I approve the changes in CMS with the user email "purpleheautomation@gmail.com" and the following details:
      | admin | hbcmsxx | The University of Alabama | Published | Undergraduate Financial Aid |
    And HUBS I successfully sign out from CMS
    Then HUBS I should be able to verify the changes for costs published in HUBS, with username "benhubs", password "Hobsons!23" and college "The University of Alabama", in the following sections
      | Average Net Prices    | $48,001 - $75,000 |
      | % Receiving Aid       | Pell Grant        |
      | Average Amount of Aid | Grant             |
    #Sometimes, the Average Net Prices take a lot of time to be updated. So, the next
    #setting is the number of times to reload the HUBS page to verify the value.
      | Number of tries       | 10                |
