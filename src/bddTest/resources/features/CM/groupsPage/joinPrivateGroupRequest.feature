@MATCH-695
Feature: Community User - Approve/Reject Request to Join Private Group
  As a Community user and owner/manager of a Private group I am able to approve or deny another Community member's
  request to join my Private group so I can control my group members.


  @MATCH-696
  Scenario: As a Community user and Private group owner/manager I can approve another Community member's request to join my private group.
    Given I am logged in to Purple Community through the HE App
    And I search for "**Test Automation** HE Community PRIVATE Group" group
    And I am sure that user is not a member of the group
    Then I request to join the group
    And I sign out from the HE app
    And I approve request to join the group
    And I check if user is a member of the group
    And I sign out from the HE app

  #This scenario could fail on ste when denying join group request due to session issue (not possible to go to /groups page)
  @MATCH-697
  Scenario: As a Community user and Private group owner/manager I can deny another Community member's request to join my private group.
    Given I am logged in to Purple Community through the HE App
    And I search for "**Test Automation** HE Community PRIVATE Group" group
    And I am sure that user is not a member of the group
    Then I request to join the group
    And I sign out from the HE app
    And I deny request to join the group
    Then I am logged in to Purple Community through the HE App
    And I search for "**Test Automation** HE Community PRIVATE Group" group
    Then I check if user is not a member of the group
    And I sign out from the HE app


  @MATCH-698
  Scenario: As a Community user that has had their request to join a Private group approved, I receive an approval notification within the Community.
    Given I am logged in to Purple Community through the HE App
    And I search for "**Test Automation** HE Community PRIVATE Group" group
    And I am sure that user is not a member of the group
    Then I request to join the group
    And I sign out from the HE app
    And I approve request to join the group
    And I open Notifications list
    And I check if user has new notification that my request to join private group is approved
    Then I sign out from the HE app
