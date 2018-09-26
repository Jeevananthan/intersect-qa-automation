@CM
Feature: Community User - Groups Tab to Show Community User Their Joined Groups
  As a Community user I want a groups tab/page that allows me to quickly view all my current groups I've joined
  so I can view all the groups I've joined and navigate to an individual group when needed.

  @MATCH-603
  Scenario: As a Community user I want a groups tab/page that allows me to quickly view all my current groups
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then I navigate to Groups page
    And I check if my groups are displayed
    And I can go to Hobsons default group page