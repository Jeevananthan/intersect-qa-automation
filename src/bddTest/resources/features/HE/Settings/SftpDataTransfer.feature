@HE
Feature: HE - Settings - SFTP Data Transfer - As an HE admin user, I should be able to manage SFTP Data Transfer connections

  @MATCH-4793
  Scenario: As an HE admin associated with an HE account that has an active AMPLUS subscription. I want the ability to
  access SFTP set-up for my HE account from my broader Intersect Account Settings, so that global account settings such
  as these are housed in an anticipated and centralized location.
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I delete the SFTP Data Transfer connection
    Then HE I verify the text "SFTP Data Transfer" is displayed
    And HE I verify the text "Establish an automated secure file transfer using SFTP for the data listed below." is displayed
    And HE I verify the text "NAVIANCE ACTIVEMATCH" is displayed
    And HE I verify the text "Student Connections" is displayed
    And HE I verify the text "Transfer new ActiveMatch student connections to a location of your choice." is displayed
    And HE I verify the SET UP CONNECTION button is displayed
    And HE I successfully sign out
