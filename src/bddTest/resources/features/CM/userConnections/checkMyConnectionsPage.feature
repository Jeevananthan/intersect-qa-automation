@CM
Feature: Community User - Connections Tab to Show Community User Their Connections
  As a Community user I want a Connections tab/page that allows me to quickly view all my current user connections
  and institution follows I've made so I can view all of them in one place and navigate to an individual user or
  institution when needed.

  @MATCH-602
  Scenario: As a Community user I want a Connections tab/page that allows me to quickly view all my current user connections
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then I go to connections page
    And I check if my connections page is opened
    And I check if I can navigate to an individual user
    Then HE I successfully sign out