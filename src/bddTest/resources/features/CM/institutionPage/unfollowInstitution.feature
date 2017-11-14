@MATCH-462
Feature: Community User - Unfollow Institution
  As a Community user I need to be able to unfollow previously followed institutions so my network is always up
  to date.


  @MATCH-463
  Scenario: As a Community User I can unfollow a previously followed institution when viewing that institution's profile.
    Given I am logged in to Purple Community through the HE App
    And I go to institution page
    And I am following institution with id "10226"
    Then I go to institution page with institution id "10226"
    And I click on Unfollow institution button
    And I check if follow institution button is visible
    Then I sign out from the HE app


  @MATCH-465
  Scenario: As a Community user I can unfollow a previously followed institution when on the Connections tab - Institutions I'm Following.
    Given I am logged in to Purple Community through the HE App
    And I go to institution page
    And I am following institution with id "10226"
    Then I go to connections page
    And I go to Institutions that I'm following page
    Then I unfollow institution with id "10226"
    And I go to institution page with institution id "10226"
    Then I check if follow institution button is visible
    And I sign out from the HE app
