@MATCH-627
Feature: Community Admin - Manage Groups
  As a Community Administrator I want to manage already created groups so I can continue to control what Community
  users can join them.


  @MATCH-628
  Scenario: As a Community Administrator I can update a group's setting from Public to Private.
    Given I am logged in to Purple Community through the Support App
    Then I navigate to Groups page
    And I click on Add Group button
    Then I populate fields for new Group with name "**AutoGenerated** Manage group fields"
    And I set group visibility to "Public"
    And I click on Create button
    Then I open "**AutoGenerated** Manage group fields" group page
    And I click on Edit group button
    And I set group visibility to "Private"
    And I Update changes
    Then I check if the group visibility is "Private Group"
    And I delete group "**AutoGenerated** Manage group fields"
    And I sign out from the Support app



  @MATCH-629
  Scenario: As a Community Administrator I can update a group's setting from Private to Public.
    Given I am logged in to Purple Community through the Support App
    Then I navigate to Groups page
    And I click on Add Group button
    Then I populate fields for new Group with name "**AutoGenerated** Manage group fields"
    And I set group visibility to "Private"
    And I click on Create button
    Then I open "**AutoGenerated** Manage group fields" group page
    And I click on Edit group button
    And I set group visibility to "Public"
    And I Update changes
    Then I check if the group visibility is "Public group"
    And I delete group "**AutoGenerated** Manage group fields"
    And I sign out from the Support app



  @MATCH-653
  Scenario: As a Community Administrator I can modify a group's name.
    Given I am logged in to Purple Community through the Support App
    Then I navigate to Groups page
    And I click on Add Group button
    Then I populate fields for new Group with name "**AutoGenerated** Manage group fields"
    And I click on Create button
    Then I open "**AutoGenerated** Manage group fields" group page
    And I click on Edit group button
    Then I change name of the group with name "**AutoGenerated** Manage group fields--EDITED"
    And I Update changes
    Then I check if the group name is changed to "**AutoGenerated** Manage group fields--EDITED"
    And I delete group "**AutoGenerated** Manage group fields--EDITED"
    And I sign out from the Support app