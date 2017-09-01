@MATCH-654
Feature: Community Admin - Manually Remove User from Group
  As a Community Administrator I need to be able to manually remove a Community user from a group so I can
  moderate and protect group members.

  Scenario: As a Community Administrator I can manually remove a user from a group they have previously joined.
    Given I am logged in to Purple Community through the HE App
    And I search for "**Test Automation** HE Community PUBLIC Group" group
    And I am sure that user is a member of the group
    And I sign out from the HE app
    Then I am logged in to Purple Community through the Support App
    And I navigate to Manage Group Members page
    And I remove the user from the group
    Then I check if the user is removed
    And I sign out from the Support app