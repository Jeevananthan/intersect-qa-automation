Feature: Community User - Create Community Post for my Institution
  As a Community user with the appropriate permissions I want to create posts for my Institution so other
  Community users can read about updates I share about my institution within the Community.

  @MATCH-908
  Scenario: As a Community user I can create a new institution post from my Institution page.
    Given I am logged in to Purple Community through the HE App
    And I go to institution page
    And I create new institution post
    Then I check if institution post is created
