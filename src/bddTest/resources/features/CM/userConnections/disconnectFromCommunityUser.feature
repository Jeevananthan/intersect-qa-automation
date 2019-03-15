@CM @MATCH-420
Feature: Community User - Disconnect from Community User
  As a Community user I need to be able to disconnect ('unfriend') from previously connected Community users so
  my network is always up to date.


  @MATCH-421
  Scenario: As a Community User I can disconnect from another Community user when viewing that user's profile.
    Given HE I am logged in to Intersect HE as user type "community"
    And I am connected to HS user
    Then I search for "PurpleHS User" and open profile page of this user
    And I click disconnect button
    And I check if user is not connected to "PurpleHSUser" user


  #There is no option to disconnect from another Community user in search results
#  @MATCH-422
#  Scenario: As a Community user I can disconnect from another Community user when that user appears in search results.



  @MATCH-423
  Scenario: As a Community user I can disconnect from another Community user when on the Manage Connections screen.
    Given HE I am logged in to Intersect HE as user type "community"
    And I am connected to HS user
    Then I go to connections page
    And I disconnect from the "PurpleHS User" user
    And I check if user is not connected to "PurpleHSUser" user


  #Message to confirm disconnect action feature is not implemented
#  @MATCH-1148
#  Scenario: When I take the disconnect action on a user I am already connected with, I am presented with a message asking me to confirm this action before it is taken.

