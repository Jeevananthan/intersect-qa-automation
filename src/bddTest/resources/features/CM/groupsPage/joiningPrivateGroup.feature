@MATCH-685
Feature: Community User - Joining Private Groups
  As a Community user I am unable to join Private groups without an invitation from the group owner/manager so the
  group can remain accessible to only the appropriate users.



  @MATCH-686
  Scenario: As a Community user I am presented with a 'Request to Join' action when viewing a private group's page.
    Given I am logged in to Purple Community through the HE App
    And I search for "**Test Automation** HE Community PRIVATE Group" group
    And I am sure that user is not a member of the group
    And I search for "**Test Automation** HE Community PRIVATE Group" group
    Then I check if I see "Request to join" button
    And I sign out from the HE app

  #This scenario may fail on step when denying join group request due to session issue (not possible to go to /groups page)
  @MATCH-690
  Scenario: As a Community user when I take the 'Request to Join' action on a Private group the group owner/manager is presented with a notification in the Community of my request.
    Given I am logged in to Purple Community through the Support App
    And I clear all the notifications
    And I sign out from the Support app
    Then I am logged in to Purple Community through the HE App
    And I search for "**Test Automation** HE Community PRIVATE Group" group
    And I am sure that user is not a member of the group
    Then I request to join the group
    And I sign out from the HE app
    And I am logged in to Purple Community through the Support App
    Then I open Notifications list
    And I check if user has new notification
    And I sign out from the Support app
    Then I deny request to join the group

  #This scenario cannot be covered because of gmail security system
#  @MATCH-692
#  Scenario: As a Community user when I take the 'Request to Join' action on a Private group the group owner/manager is sent an email notification of my request.

