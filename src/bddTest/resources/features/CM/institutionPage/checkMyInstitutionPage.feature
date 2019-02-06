@CM
Feature: Community User - Institution Tab for User to View Their Institution's Profile
  As a Community user I want an Institution tab that shows me my Institution's profile page so I can quickly view
  and read recent activity from my institution as well as edit my institution's profile if I have the appropriate
  permissions.

  @MATCH-606
  Scenario: As a Community user I want an Institution tab that shows me my Institution's profile page
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then I go to institution page
    Then I open institution tab
    And I check if my institution info is displayed
    And I check if I am able to edit my institution info