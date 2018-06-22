@HUBS @HUBSDataValidation
Feature: As a community user viewing College Hubs, I want data to be validated properly in the edition mode fields.

  Background:
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HUBS I access HUBS Edit Mode
  @HUBS-1040
  Scenario: HEM - UI - It is possible to enter unlimited characters in some fields in HEM (bug-fixed)
    When HUBS I open the "Costs" tab in the preview
    And HUBS I open "Costs" in the edit menu
    Then HUBS I verify that the following fields for Costs accept only valid values:
      | Average Net Prices | $0 - $30,000 | 777777777 | 777777 |
    And HE I successfully sign out
  @HUBS-1042
  Scenario: HEM - A specific message should be displayed when an incorrect data type is entered in an editable field
    When HUBS I open "Costs" in the edit menu
    Then HUBS I verify an error message when non valid data is used in Costs:
      | Average Net Prices | $0 - $30,000 | abcd |
    And HE I successfully sign out
  @HUBS-1051 @HUBS-1059
  Scenario: HEM - It is possible to publish letters in Admissions - Fees (bug-fixed)
    When HUBS I open "Admissions" in the edit menu
    Then HUBS I verify an error message when non valid data is used in Admissions:
      | Recommended Courses | Science | Years Required | abc |
      | Fees                | Transfer Deposit Fee | abc | - |
    And HE I successfully sign out
  @HUBS-1062
  Scenario: HEM - It is possible to publish letters in International test Scores (bug-fixed)
    When HUBS I open "International" in the edit menu
    Then HUBS I verify an error message when non valid data is used in International:
      | Test Scores  | Michigan (MELAB) | Low | abc |
    And HE I successfully sign out
  @HUBS-1046 @HUBS-1045
  Scenario: HEM - It is possible to publish letters in the fields for Number of PCs and MACs in Student Life - Computing Resources (bug-fixed)
    When HUBS I open "Student Life" in the edit menu
    Then HUBS I verify an error message when non valid data is used in Student Life:
      | Computing Resources | # of PCs in Computer Centers | abc |
      | Age Data     | % Students 24 Years Old | abc |
    And HE I successfully sign out

