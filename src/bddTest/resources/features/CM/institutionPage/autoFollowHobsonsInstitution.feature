Feature: Community User - Auto Follow the Hobsons Institution
  As a Purple Community user I need to automatically follow the Hobsons institutional profile when I join the
  Community so I can receive important information from Hobsons when posts are made.

  @MATCH-891
  Scenario: As a Community user I automatically following the Hobsons institution when I join.
    Given I am logged in to Purple Community through the HE App
    And I go to connections page
    And I go to Institutions that I'm following page
    Then I check if I am following Hobsons instituion
    And I sign out from the HE app

  @MATCH-892
  Scenario: As a Community user I am unable to unfollow the Hobsons institution.
    Given I am logged in to Purple Community through the HE App
    And I go to institution page
    And I go to Hobsons institution page
    Then I check if I can unfollow the Hobsons institution
    And I sign out from the HE app