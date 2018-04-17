@MATCH-1675
Feature: As a Community user I want to view the actual users who have following my institution
  when looking at my institution's profile so I can connect with them.

  Scenario: As a Community user I want to view the actual users who have following my institution
    Given HE I am logged in to Intersect HE as user type "administrator"
    And I go to institution page
    And I click on Followers tab
    Then I check if I see followers of my institution
    And HE I successfully sign out