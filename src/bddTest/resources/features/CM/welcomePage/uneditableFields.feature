@CM
Feature: Community User - Core User Profile Fields Uneditable
  As an HE Community user I need my Community user profile fields of name and email to be read only so I do not have
  mismatched information between my Community user profile and my Purple user account.

  @MATCH-523
  Scenario: As an Community user I cannot modify the name, institution and email profile fields within the Community.
    Given I am logged in to Purple Community through the HE App
    And I am sure that HE user will be logged in for the first time and Welcome page will be opened
    And I go to Counselor Community page
    Then I check if fields name, institution and email address are uneditable
