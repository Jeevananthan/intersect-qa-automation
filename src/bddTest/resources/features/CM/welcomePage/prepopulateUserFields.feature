@CM
Feature: HE Community User - Prepopulate Community User Profile Fields Upon Activation
  As an HE Community user I want my Purple user account data to prepopulate my Community user profile upon initial
  activation so I don't have to re-enter information Purple already knows about me.

  @MATCH-480
  Scenario: As an HE Community user I want my Purple user account details to autopopulate my Community user profile when initially activated.
    Given HE I am logged in to Intersect HE as user type "resetAccount"
    And I am sure that HE user will be logged in for the first time and Welcome page will be opened
    #Then I upload Profile and Banner pictures
    And I populate all the fields on Welcome page
    And I set the EU citizen to "Yes"
    And I accept Terms and conditions
    And I consent to create and maintain my Intersect account
    Then I Save changes
    And I check if changes are saved
