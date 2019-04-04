@CM
Feature: Community User - Core User Profile Fields Uneditable
  As an HE Community user I need my Community user profile fields of name and email to be read only so I do not have
  mismatched information between my Community user profile and my Purple user account.

  @MATCH-523
  Scenario: As an Community user I cannot modify the name, institution and email profile fields within the Community.
    Given HE I am logged in to Intersect HE as user type "resetAccount"
    And I am sure that HE user will be logged in for the first time and Welcome page will be opened
    Then I check if fields name, institution and email address are uneditable
    #Post conditions
    And I populate all the fields on Welcome page
    Then I set work email and office phone privacy to 'Connections Only'
    And I set personal email and mobile phone privacy to 'Visible to Only Me'
    And I set the EU citizen to "Yes"
    And I accept Terms and conditions
    And I consent to create and maintain my Intersect account
    And I Save changes
    Then I click on Edit profile button
    And I check if privacy settings are saved properly
