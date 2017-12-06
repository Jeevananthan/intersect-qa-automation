@CM
Feature: As an HE or Support user I want to login to the Community

  Scenario: As an HE user I'm able to log in to the Community
    Given I am logged in to Purple Community through the HE App
    Then I am able to successfully login
    And I successfully sign out from the HE app

  @MATCH-1179
  Scenario: As a Support user I'm able to log in to the Community
    Given I am logged in to Purple Community through the Support App
    Then I am able to successfully login
    And I successfully sign out from the Support app
