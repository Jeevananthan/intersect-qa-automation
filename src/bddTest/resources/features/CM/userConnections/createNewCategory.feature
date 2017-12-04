Feature: Community User - Create Custom Connection Categories
  As a Community user I need to create custom connection categories so I can more quickly filter and find
  particular connections on the Manage Connections page.

  @MATCH-429
  Scenario: As a community user I am able to create custom connections category.
    Given I am logged in to Purple Community through the HE App
    And I go to connections page
    Then I create new connections category using name "AutogeneratedTestGroup"
    And I check if category with name "AutogeneratedTestGroup" is created
    Then I delete "AutogeneratedTestGroup" category
    And I sign out from the HE app