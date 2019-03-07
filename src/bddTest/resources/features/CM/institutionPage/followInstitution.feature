@CM @MATCH-454
Feature: Community User - Follow Institution
  As a Purple Community user I can follow an institution so I can receive content from them as it is created.


  @MATCH-456
  Scenario: As a premium HE, HS, or Hobsons staff Community user when viewing an institution's profile, I have a follow action available.
    Given HE I am logged in to Intersect HE as user type "administrator"
    And I go to institution page
    And I am not following institution with id "703"
    Then I go to institution page with institution id "703"
    And I check if follow institution button is visible



  @MATCH-457
  Scenario: As a premium HE, HS, or Hobsons staff Community user when I take the follow action for an institution, that institution is automatically added to the list of institutions I am following in the Connections tab - Institutions I'm Following page.
    Given HE I am logged in to Intersect HE as user type "administrator"
    And I go to institution page
    And I am not following institution with id "703"
    Then I go to institution page with institution id "703"
    And I click on Follow institution button
    And I go to connections page
    And I go to Institutions that I'm following page
    Then I check if institution with id "703" is in the list


  #This scenario cannot be done, missing credentials for freemium user
#  @MATCH-1373
#  Scenario: As a freemium HE user, I cannot follow institutions.

