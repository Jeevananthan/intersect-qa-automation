@MATCH-710
Feature: Community Group - Aggregate Feed on Group Page
  As a Community group I want an aggregate feed of recent posts, content, replies, and activity within the group
  compiled and displayed on the group page so Community members can quickly see the activity within my group.

  Scenario: As a Community group I want an aggregate feed on group page
    Given HE I am logged in to Intersect HE as user type "administrator"
    And I search for "Hobsons News & Events" group
    Then I check if I can see aggregate feed on group page
    And HE I successfully sign out