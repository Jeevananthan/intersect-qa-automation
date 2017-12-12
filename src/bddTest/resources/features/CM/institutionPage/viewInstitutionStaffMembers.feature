@CM @MATCH-909
Feature: Community User - View Related Staff of an Institution
  As a Community user or institution while viewing an institution within the Community I want to see the
  related staff/community users of that particular institution so I can quickly connect with them.


  Scenario: As a Community user I want to see a staff tab that displays all related staff of the institution.
    Given I am logged in to Purple Community through the HE App
    And I go to institution page
    And I go to Hobsons institution page
    And I click on Staff members tab
    Then I check if Institution staff members are visible and I can connect with them
    And I sign out from the HE app