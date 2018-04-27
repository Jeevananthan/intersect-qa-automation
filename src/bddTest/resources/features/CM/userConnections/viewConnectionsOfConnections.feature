@MATCH-445
Feature: Community User - View Connections of Connections
  As a Community user I need to be able to view the connections of my connections so I can continue to grow my
  Community network.


  @MATCH-446
  Scenario: As a Community user when viewing the user profile of a connection I am able to see their connections.
    Given HE I am logged in to Intersect HE as user type "administrator"
    And I am connected to HS user
    Then I search for "PurpleHS User" and open profile page of this user
    And I open a list of user's connections
    And I check if user's connections are displayed
    Then HE I successfully sign out


  @MATCH-447
  Scenario: As a Community user when viewing a connection's connections I am able to request to connect with them.
    Given HE I am logged in to Intersect HE as user type "administrator"
    And I am connected to HS user
    Then I search for "PurpleHS User" and open profile page of this user
    And I open a list of user's connections
    And I check when viewing a connection's connections if I am able to request to connect with them
    Then HE I successfully sign out
