@CM @MATCH-906
Feature: Community User - Send Message Form
  As a Community User after I take the 'Message' action on another Community user I want to be presented with
  a form that allows me to compose my message so the other Community user can receive it.


  Scenario: As a Community User after I take the 'Message' action on another Community user I want to be presented with a form.
    Given I am logged in to Purple Community through the HE App
    And I am connected to HS user
    Then I search for "PurpleHS User" and open profile page of this user
    And I click on connect button
    And I click on Message link
    Then I check if new message form elements are present
    And I sign out from the HE app
