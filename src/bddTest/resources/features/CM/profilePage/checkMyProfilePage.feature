@CM
Feature: Community User - Profile Tab to Show User Profile
  As a Community user I want a Profile tab that allows me to view my own Community user profile so I can preview
  what other Community users see when viewing my profile.

  @MATCH-604
  Scenario: As a Community user I want a Profile tab that allows me to view my own Community user profile
    Given I am logged in to Purple Community through the HE App
    Then I go to user profile page
    And I check if my user profile page is opened
    Then I sign out from the HE app