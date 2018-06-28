@CM
Feature: HS Community User - Prepopulate Community User Profile Fields Upon Activation

  As an HS Community user I want my Naviance Succeed user account data to prepopulate my Community user profile upon
  initial activation so I don't have to re-enter information Naviance already knows about me.

  @MATCH-486
  Scenario: As an HS Community user I want my Naviance Succeed user account details to autopopulate my Community user profile when initially activated.
    Given HS I am logged in to Intersect HS as user type "default"
    And I am sure that HS user will be logged in for the first time and HS Welcome page will be opened
    And I go to HS Counselor Community page
    #Then I upload Profile and Banner pictures
    And I populate all the fields on Welcome page
    Then I set work email and office phone privacy to 'Connections Only'
    And I set personal email and mobile phone privacy to 'Visible to Only Me'
    And I set the EU citizen to "Yes"
    And I accept Terms and conditions
    And I consent to create and maintain my Intersect account
    And I Save changes
    Then I click on Edit profile button
    And I check if privacy settings are saved properly
    Then HS I successfully sign out