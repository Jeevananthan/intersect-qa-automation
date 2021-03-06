@MATCH-515
Feature: Community User - Manage Community User Profile Field Permissions
  As a Community user I need to be able to manage my profile fields permissions (excluding name, institution name,
  and title, bio, headline, population served and territory fields) so I can continuously control what information
  I am displaying about myself within the Community.



  @MATCH-516
  Scenario: As a Community user I need to update my Community user profile fields permission from 'visible to all users' to 'visible to only me'.
    Given HE I am logged in to Intersect HE as user type "administrator"
    And I go to user profile page
    Then I click on Edit profile button
    And I set all fields permissions to "public"
    And I Save changes
    Then I click on Edit profile button
    And I set all fields permissions to "private"
    And I Save changes
    Then I click on Edit profile button
    And I check if permissions settings are saved to "private"


  @MATCH-517
  Scenario: As a Community user I need to update my Community user profile fields permission from 'visible to all users' to 'connections only'.
    Given HE I am logged in to Intersect HE as user type "administrator"
    And I go to user profile page
    Then I click on Edit profile button
    And I set all fields permissions to "public"
    And I Save changes
    Then I click on Edit profile button
    And I set all fields permissions to "connections"
    And I Save changes
    Then I click on Edit profile button
    And I check if permissions settings are saved to "connections"


  @MATCH-518
  Scenario: As a Community user I need to update my Community user profile fields permission from 'hidden' to 'public'.
    Given HE I am logged in to Intersect HE as user type "administrator"
    And I go to user profile page
    Then I click on Edit profile button
    And I set all fields permissions to "private"
    And I Save changes
    Then I click on Edit profile button
    And I set all fields permissions to "public"
    And I Save changes
    Then I click on Edit profile button
    And I check if permissions settings are saved to "public"



  @MATCH-519
  Scenario: As a Community user I need to update my Community user profile fields permission from 'hidden' to 'connections only'.
    Given HE I am logged in to Intersect HE as user type "administrator"
    And I go to user profile page
    Then I click on Edit profile button
    And I set all fields permissions to "private"
    And I Save changes
    Then I click on Edit profile button
    And I set all fields permissions to "connections"
    And I Save changes
    Then I click on Edit profile button
    And I check if permissions settings are saved to "connections"



  @MATCH-520
  Scenario: As a Community user I need to update my Community user profile fields permission from 'connections only' to 'public'.
    Given HE I am logged in to Intersect HE as user type "administrator"
    And I go to user profile page
    Then I click on Edit profile button
    And I set all fields permissions to "connections"
    And I Save changes
    Then I click on Edit profile button
    And I set all fields permissions to "public"
    And I Save changes
    Then I click on Edit profile button
    And I check if permissions settings are saved to "public"



  @MATCH-521
  Scenario: As a Community user I need to update my Community user profile fields permission from 'connections only' to 'hidden'.
    Given HE I am logged in to Intersect HE as user type "administrator"
    And I go to user profile page
    Then I click on Edit profile button
    And I set all fields permissions to "connections"
    And I Save changes
    Then I click on Edit profile button
    And I set all fields permissions to "private"
    And I Save changes
    Then I click on Edit profile button
    And I check if permissions settings are saved to "private"


  @MATCH-522
  Scenario: As a Community user with an activated profile, I am not presented with privacy settings on the following fields first name, last name, institution name, bio, headline, territory served, and job title.
    Given HE I am logged in to Intersect HE as user type "administrator"
    And I go to user profile page
    Then I click on Edit profile button
    And I check if there are no privacy settings for following fields first name, last name, institution name, bio, headline, territory served, and job title

