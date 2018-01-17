@HUBS
Feature: As a community user viewing College Hubs, I want to be able to view Hubs Cost Tab content so I can understand
  what Hubs offers students.

  Background:
    Given HE I want to login to the HE app using "jorgetesthobsons@gmail.com" as username and "Hobsons2016!" as password
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
    And HE I successfully sign out

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
    And HUBS I approve the changes in CMS with the user email "jorgetesthobsons@gmail.com" and the following details:
      | admin | hbcmsxx | Adelphi University | Published | Undergraduate Financial Aid |
    Then HUBS I should be able to verify the changes for costs published in HUBS, with username "benhubs", password "Hobsons!23" and college "Adelphi", in the following sections
      | Average Net Prices    | $48,001 - $75,000 |
      | % Receiving Aid       | Pell Grant        |
      | Average Amount of Aid | Grant             |
    And HUBS I successfully sign out
