@CM
Feature: Community Admin - Create Public HS/HE Only Groups
  As a Community Administrator I want to create Public groups for HS or HE only Community users to join so they
  can have more targeted interactions and discuss topics in a more private manner.

  @MATCH-620
  Scenario: As a Community Administrator I can create Public groups that only HS Community users can join.
    Given I am logged in to Purple Community through the Support App
    Then I navigate to Groups page
    And I click on Add Group button
    Then I populate fields for new Group with name "**AutoGenerated** HS Users Only Group"
    And I select institution type "HS"
    And I click on Create button
    Then I check if group with name "**AutoGenerated** HS Users Only Group" is created
    And I sign out from the Support app

  @MATCH-625
  Scenario: As a Community Administrator I can create Public groups that only HE Community users can join.
    Given I am logged in to Purple Community through the Support App
    Then I navigate to Groups page
    And I click on Add Group button
    Then I populate fields for new Group with name "**AutoGenerated** HE Users Only Group"
    And I select institution type "HE"
    And I click on Create button
    Then I check if group with name "**AutoGenerated** HE Users Only Group" is created
    And I sign out from the Support app

  @MATCH-626
  Scenario: As a non Community Administrator I cannot create a new group.
    Given I am logged in to Purple Community through the HE App
    Then I navigate to Groups page
    And I check if non Community Administrator can create new group
