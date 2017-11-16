@CM @MATCH-408
Feature: Community User - New Connection Request Notification
  As a Community user I need to receive a notification when another Community user makes a connection request
  with me so I can quickly manage these requests to build my Community network.


  @MATCH-409
  Scenario: As a Community user I see a notification within the Community that I have pending connection requests.
    Given I am logged in to Purple Community through the HE App
    And I am not connected to "PurpleHS User" user
    And I clear all the notifications
    And I am connected to HS user
    Then I open Notifications list
    And I check if user have new notification
    And I sign out from the HE app


  #This scenario cannot be covered because of gmail security system
#  @MATCH-410
#  Scenario: As a Community user I receive an email notification for each connection request from other Community users.


  #This scenario cannot be covered since we cannot read notification messages written by ajax using selenium
#  @MATCH-411
#  Scenario: As a Community user the notification I see within the Community displays the number of pending connection requests I have to review.
