@CM @MATCH-404
Feature: Community User - Approve/Ignore Request to Connect
  As a Community user I need to be able to approve/ignore a connection request from another Community user so I
  can control who I network with in the Community.

  @MATCH-405
  Scenario: As a HS Community user I can approve a connection request
    Given HE I am logged in to Intersect HE as user type "community"
    And I am not connected to "PurpleHS User" user
    Then I search for "PurpleHS User" and open profile page of this user
    And I click on connect button
    And I send the connection invitation
    Then As a HS user I accept the invitation
    And I check if user is connected to "PurpleHS User" user


  @MATCH-406
  Scenario: As a HS Community user I can ignore a connection request
    Given HE I am logged in to Intersect HE as user type "community"
    And I am not connected to "PurpleHS User" user
    Then I search for "PurpleHS User" and open profile page of this user
    And I click on connect button
    And I send the connection invitation
    Then As a HS user I ignore the invitation
    And I check if user is not connected to "PurpleHS User" user
#
#
##  #There is no any messages visible on connection requests page from user that requested a connection
##  @MATCH-407
##  Scenario: As a HS Community user, when viewing a connection request, I can view the personal message from the requestor if they provided one.
#

  @MATCH-1230
  Scenario: As a Community user who is able to request to connect to other users, the status I see of the user that ignored my request should revert from Invited to Connect again.
    Given HE I am logged in to Intersect HE as user type "community"
    And I am not connected to "PurpleHS User" user
    Then I search for "PurpleHS User" and open profile page of this user
    And I click on connect button
    And I send the connection invitation
    And I see "Invited" status button
    Then As a HS user I ignore the invitation
    And I check if user is not connected to "PurpleHS User" user
    Then I search for "PurpleHS User" and open profile page of this user
    And I see "Send Connect Request" status button


  @MATCH-1375
  Scenario: As a premium (Legacy Community or Matching Subscription) HE Community user I can approve a connection request
    Given HS I am logged in to Intersect HS as user type "default"
    And I am not connected to "PurpleHE Community" HE user
    Then I search for "PurpleHE Community" and open profile page of this user
    And I click on connect button
    And I send the connection invitation
    Then As a HE user I accept the invitation
    And I check if HS user is connected to "PurpleHE Automation" user
#
#
  @MATCH-1376
  Scenario: As a premium (Legacy Community or Matching Subscription) HE Community user I can ignore a connection request
    Given HS I am logged in to Intersect HS as user type "community"
    And I am not connected to "PurpleHE Automation" user
    Then I search for "PurpleHE Automation" and open profile page of this user
    And I click on connect button
    And I send the connection invitation
    Then As a HE user I ignore the invitation
    And I check if user is not connected to "PurpleHE Automation" user
###
#
##  #There is no any messages visible on connection requests page from user that requested a connection
##  @MATCH-1377
##  Scenario: As a premium (Legacy Community or Matching Subscription) HE Community user, when viewing a connection request, I can view the personal message from the requestor if they provided one.
#
#
##  #This scenario cannot be done, missing credentials for freemium user
##  @MATCH-1378
##  Scenario: As a freemium HE user or premium (Legacy Hubs) HE user, I do not receive any connection requests.
