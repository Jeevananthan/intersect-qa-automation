@SP
Feature: Hobsons Staff - View Individual Institution Accounts
  As a Hobsons staff user I need to view individual institutional accounts.

  Scenario: As a Hobsons Sales Ops user I can view an institutional account
    Given SP I am logged in to the Admin page as a Sales Ops user
    When SP I click on an institution name
    Then SP I am able to view the individual account page
    And SP I successfully sign out

  Scenario: As a Hobsons Admin user I can view an institutional account
    Given SP I am logged in to the Admin page as a Admin user
    When SP I click on an institution name
    Then SP I am able to view the individual account page
    And SP I successfully sign out

  Scenario: As a Hobsons QA user I can view an institutional account
    Given SP I am logged in to the Admin page as a Support user
    When SP I click on an institution name
    Then SP I am able to view the individual account page
    And SP I successfully sign out

  @MATCH-958
  Scenario: As a Hobsons Support user I should not activate any module where the end date is earlier than the start date and vise-versa.
    Given SP I am logged in to the Admin page as a Admin user
    When SP I click on an institution name
    Then SP I am able to view the individual account page
    Then SP I verify the end date Feasibility
    Then SP I verify the start date Feasibility
