@MATCH-551
Feature: Community User - Send Message to Multiple Community Users
  As a Community User I want to send a private message to multiple Community users and/or institutions at the same
  time so I can communicate efficiently with my Community network in a manner that isn't public.


  @MATCH-552
  Scenario: As a Community user I want to send a private message to multiple Community users when viewing my connections.
    Given HE I am logged in to Intersect HE as user type "community"
    And I go to connections page
    And I am connected to MatchSupportQA3 user
    And I am connected to HS user
    Then I go to connections page
    And I click on Message button
    Then I fill in Subject "Test message" and message body "Sending message to multiple connections."
    And I add "MatchSupportUIQA3" user as selected connection
    And I add "PurpleHS User" user as selected connection
    And I click on Send button
    And I check if there is a notification about message action


