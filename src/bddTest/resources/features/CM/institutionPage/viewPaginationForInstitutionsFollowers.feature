@MATCH-1706
Feature: As a community user I want to see pagination on Institution's Followers page in case when there are a lot of followers

  Scenario: As a community user I want to see pagination on Institution's Followers page
    Given I am logged in to Purple Community through the HE App
    When  I go to institution page
    And I click on Followers tab
    Then I see pagination on that page
    And I sign out from the HE app