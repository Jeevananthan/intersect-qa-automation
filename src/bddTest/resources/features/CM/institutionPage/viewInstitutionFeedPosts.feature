@CM
Feature: Community User - View Aggregate Feed on HE/HS Institutions
  As a Community user or institution while viewing an HE/HS institution within the Community I want to see an
  aggregate feed of that institution's recent activity so I can learn more about that insitution and catch
  up on their Community interactions.

  @MATCH-915 @MATCH-916
  Scenario: As a Community user or institution i can see Aggregate Feed on HE/HS Institutions.
    Given HE I am logged in to Intersect HE as user type "administrator"
    And I go to institution page
    And I go to Hobsons institution page
    Then I check if Institution posts are visible