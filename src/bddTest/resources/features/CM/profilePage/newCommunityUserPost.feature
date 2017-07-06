Feature: Community User - Create Community Post from My Profile Page
  As a Community user or institution I want to create posts from my Profile page so other Community users can
  read about the content I shared within the Community.

  @MATCH-719
  Scenario: As a Community user I can create a new post from my Profile page.
    Given I am logged in to Purple Community through the HE App
    Then I go to user profile page
    And I create new user post
    Then I check if user post is created
