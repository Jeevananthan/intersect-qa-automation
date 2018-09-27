@CM @MATCH-532
Feature: Community User - Send Message to Community User
  As a Community User I want to send a private message to another Community user or institution so I can have a
  private conversation with them that isn't visible to other Community users.



  @MATCH-533
  Scenario: As a Community user I want to send a private message to another Community user when viewing that user's profile.
    Given HE I am logged in to Intersect HE as user type "administrator"
    And I am connected to HS user
    Then I search for "PurpleHS User" and open profile page of this user
    And I click on connect button
    And I click on Message link
    Then I fill in Subject "Test message - no reply" and message body "This message is sent automatically by Test Automation Script!"
    And I click on Send button

