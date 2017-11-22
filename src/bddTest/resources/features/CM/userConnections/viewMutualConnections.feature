@CM @MATCH-529
Feature: Community User - View Mutual Connections When Viewing a User Profile
  As a Community user I need to view 'mutual' connections I have with another, unconnected Community user when
  viewing their profile so I can more easily determine if I want to connect with the Community user.



  @MATCH-530
  Scenario: As a Community user when viewing another user's profile I am not connect with I am able to see mutual connections.
    Given I am logged in to Purple Community through the HE App
    And I am connected to HS user
    Then I search for "PurpleHS User" and open profile page of this user
    And I open a list of user's connections
    And I check if Mutual connections are displayed
    And I sign out from the HE app
