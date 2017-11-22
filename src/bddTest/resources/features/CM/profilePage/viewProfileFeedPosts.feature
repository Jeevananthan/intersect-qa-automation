@CM
Feature: Community User - Aggregate Feed on Profile Page
  As a Community user I want an aggregate feed MY POSTS, replies, tags and community activity related to me to
  display on my profile page so others can determine my recent activities within the Community.

  @MATCH-819
  Scenario: As a Community user I can see Aggregate Feed on Profile Page.
    Given I am logged in to Purple Community through the HE App
    Then I go to user profile page
    And I check if Profile posts are visible