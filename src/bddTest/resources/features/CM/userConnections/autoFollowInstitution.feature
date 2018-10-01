@CM @MATCH-416
Feature: Community User - Automatically Follow Institution of Community User Connection
  As a Community user I need to automatically begin following the institution associated with any Community user
  I connect with so I can receive and view any content created by the institution as well.

  @MATCH-417
  Scenario: As a Community user after I accept another Community user's connection request, I also now follow the user's institution.
    Given HE I am logged in to Intersect HE as user type "administrator"
    And I am not connected to "PurpleHS User" user
    Then I open connections tab
    And I go to Institutions that I'm following page
    And I check if I am not following HS user's institution
    Then I am connected to HS user
    Then I go to connections page
    And I go to Institutions that I'm following page
    And I check if I am following HS user's institution