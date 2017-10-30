Feature: HS Community User - Prepopulate Community User Profile Fields Upon Activation

  As an HS Community user I want my Naviance Succeed user account data to prepopulate my Community user profile upon
  initial activation so I don't have to re-enter information Naviance already knows about me.

  @MATCH-486
  Scenario: As an HS Community user I want my Naviance Succeed user account details to autopopulate my Community user profile when initially activated.
    Given I am logged in to Purple Community through the HS App
    And I am sure that HS user will be logged in for the first time and HS Welcome page will be opened
    And I go to HS Counselor Community page
    Then I upload Profile and Banner pictures
    And I populate all the fields on Welcome page
    And I accept Terms and conditions
    Then I Save changes
    And I check if changes are saved