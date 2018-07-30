@CM @MATCH-906
Feature: Community User - Send Message Form
  As a Community User after I take the 'Message' action on another Community user I want to be presented with
  a form that allows me to compose my message so the other Community user can receive it.

  Scenario: As a Community User after I take the 'Message' action on another Community user I want to be presented with a form.
    Given HE I am logged in to Intersect HE as user type "administrator"
    And I am connected to HS user
    Then I search for "PurpleHS User" and open profile page of this user
    And I click on connect button
    # Clicks on button, there's a wait time, no action, then searches for PurpleHS User again, and conducts the step below.
    And I click on Message link
    Then I check if new message form elements are present
    And HE I successfully sign out
