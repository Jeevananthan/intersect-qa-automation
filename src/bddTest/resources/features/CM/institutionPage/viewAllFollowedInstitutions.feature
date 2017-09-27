@MATCH-460
Feature: Community User - View All Followed Institutions
  As a Community user I need to be able to view all my followed institutions so I can manage my network within the
  Community.


  @MATCH-461
  Scenario: As a Community user I am presented with a Connections tab - Institutions I'm Following page that displays all my followed institutions.
    Given I am logged in to Purple Community through the HE App
    Then I go to connections page
    And I go to Institutions that I'm following page
    And I check if institutions list is displayed
    Then I sign out from the HE app