Feature: Community User - Connections Tab to Show Community User Their Connections
  As a Community user I want a Connections tab/page that allows me to quickly view all my current user connections
  and institution follows I've made so I can view all of them in one place and navigate to an individual user or
  institution when needed.

  @MATCH-602
  Scenario: As a Community user I want a Connections tab/page that allows me to quickly view all my current user connections
    Given I am logged in to Purple Community through the HE App
    Then I go to connections page
    And I check if my connections page is opened
    And I check if I can navigate to an individual user
    Then I sign out from the HE app