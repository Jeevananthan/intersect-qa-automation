@HE
Feature: HE - Settings - SFTP Data Transfer - As an HE admin user, I should be able to manage SFTP Data Transfer connections

  @MATCH-1100 @MATCH-1258
  Scenario: As an HE admin associated with an HE account that has an active AMPLUS subscription. I want the ability to
  access SFTP set-up for my HE account from my broader Intersect Account Settings, so that global account settings such
  as these are housed in an anticipated and centralized location.
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I delete the SFTP Data Transfer connection
