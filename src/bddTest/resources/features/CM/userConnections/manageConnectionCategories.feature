@MATCH-438
Feature: Community User - Manage Connection Categories
  As a Community user I need to manage my custom categories for connections so I can efficiently filter my
  connections.


  @MATCH-439
  Scenario: As a Community user I can edit a custom category for my connections.
    Given I am logged in to Purple Community through the HE App
    And I go to connections page
    And I create new connections category using name "AutogeneratedTestGroup"
    Then I edit category name from "AutogeneratedTestGroup" to "NEW_AutogeneratedTestGroup"
    And I go to connections page
    And I check if category with name "NEW_AutogeneratedTestGroup" is created
    Then I delete "NEW_AutogeneratedTestGroup" category
    And I sign out from the HE app


  @MATCH-440
  Scenario: As a Community user I can delete a custom category for my connections if it is not assigned to any connections.
    Given I am logged in to Purple Community through the HE App
    And I go to connections page
    Then I create new connections category using name "AutogeneratedTestGroup"
    And I go to the "AutogeneratedTestGroup" category
    Then I remove all connections from "AutogeneratedTestGroup" category
    Then I delete "AutogeneratedTestGroup" category
    And I sign out from the HE app


    #This scenario cannot be covered as the feature is not implemented yet
#  @MATCH-441
#  Scenario: As a Community user I am presented with a confirmation message when I attempt to delete a category that is no longer assigned to any of my connections.


  @MATCH-442
  Scenario: As a Community user I am presented with an error message when I attempt to delete a category that is still assigned to connections.
    Given I am logged in to Purple Community through the HE App
    And I am connected to HS user
    And I go to connections page
    Then I create new connections category using name "AutogeneratedTestGroup"
    And I add "PurpleHS User" to the "AutogeneratedTestGroup" category
    And I delete "AutogeneratedTestGroup" category
    Then I check if I can see "To delete category, You must first remove all connections from category." on the page
    And I go to the "AutogeneratedTestGroup" category
    Then I remove all connections from "AutogeneratedTestGroup" category
    And I delete "AutogeneratedTestGroup" category
    And I sign out from the HE app