@MATCH-1706
Feature: As a community user I want to see pagination on Institution's Followers page in case when there are a lot of followers

  Scenario: As a community user I want to see pagination on Institution's Followers page
    Given HE I am logged in to Intersect HE as user type "administrator"
    When  I go to institution page
    And I click on Followers tab
    Then I see pagination on that page
    And HE I successfully sign out