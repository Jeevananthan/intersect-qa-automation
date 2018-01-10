@CM @MATCH-667
Feature: Community User - Joining Public Groups
  As a Community user or institution I want to join Public groups that exist in the Community to further collaborate
  and network with other Community users regarding certain topics of interest.


  @MATCH-668
  Scenario: As a Community user I am presented with a 'Join' action when viewing a Public group's page.
    Given I am logged in to Purple Community through the HE App
    And I search for "**Test Automation** HE Community PUBLIC Group" group
    And I am sure that user is not a member of the group
    And I search for "**Test Automation** HE Community PUBLIC Group" group
    Then I check if I see "Join Group" button
    And I sign out from the HE app


  @MATCH-681
  Scenario: As a Community user I am presented with a 'Join' action next to any Public group that returns in my search results.
    Given I am logged in to Purple Community through the HE App
    Then I enter "**Test Automation** HE Community PUBLIC Group" in search box
    And I click on Search icon
    Then I click on Groups tab under search
    And I check if I am presented with a 'Join' action next to any Public group that returns in my search results
    And I sign out from the HE app


  @MATCH-683
  Scenario: As a Community user after taking the 'Join' action on a Public group the group displays on My Groups page.
    Given I am logged in to Purple Community through the HE App
    And I search for "**Test Automation** HE Community PUBLIC Group" group
    And I am sure that user is not a member of the group
    And I search for "**Test Automation** HE Community PUBLIC Group" group
    And I click on 'Join Group' button
    Then I open my groups tab
    And I check if I can see "**Test Automation** HE Community PUBLIC Group" on the page
    And I sign out from the HE app