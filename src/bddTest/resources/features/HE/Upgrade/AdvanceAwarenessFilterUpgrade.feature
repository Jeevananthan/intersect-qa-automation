@HE
Feature: HE - Upgrade - Upgrade - As an HE user in Intersect, I need to be engaged to upgrade my subscription to Advanced Awareness
         so that I can purchase Advanced Awareness from Hobsons.
  @MATCH-4919
  Scenario: As an HE user, I want to verify that user is able to upgrade subscription
    Given HE I am logged in to Intersect HE as user type "upgradeSubscriptions"
    And HE I Verify Upgrade Subscription Ribbon and Button for "Advanced Awareness"
    And HE I click on Upgrade button subscription "Advanced Awareness"
    And HE I Request Information to Upgrade Subscription

  @MATCH-4393
  Scenario: As a HE User, I want to verify that User is able to upgrade Filter "Competitors"
    Given  HE I am logged in to Intersect HE as user type "UpgradeFilters"
    And HE I click on button Configure for subscription "Advanced Awareness"
    And HE I click on Advance Awareness menu option "Competitors"
    And HE I Verify Upgrade Filter Ribbon and Button for "Unlock Competitors"
    And HE I click on Upgrade button filter "Competitors"
    And HE I select check for field Receive Hobsons Communications
    And HE I enter message on request information page "message.sent.to.Hobsons"
    And HE I Request Information to Upgrade Subscription

  @MATCH-4393
  Scenario: As a HE User, I want to verify that User is able to upgrade Filter
    Given  HE I am logged in to Intersect HE as user type "UpgradeFilters"
    And HE I click on button Configure for subscription "Advanced Awareness"
    And HE I click on Advance Awareness menu option "Majors"
    And HE I Verify Upgrade Filter Ribbon and Button for "Unlock Majors"
    And HE I click on Upgrade button filter "Majors"
    And HE I select check for field Receive Hobsons Communications
    And HE I enter message on request information page "message.sent.to.Hobsons"
    And HE I Request Information to Upgrade Subscription






    




