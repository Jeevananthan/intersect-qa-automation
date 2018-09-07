@HE
Feature: As an HE user in Intersect, I need to be engaged to upgrade my subscription to Advanced Awareness
  so that I can purchase Advanced Awareness from Hobsons.
  @MATCH-4919
  Scenario: As an HE user, I want to verify that user is able to upgrade subscription
    Given HE I am logged in to Intersect HE as user type "upgradeSubscriptions"
    And HE I Verify Upgrade Subscription Ribbon and Button for "Advanced Awareness"
    And HE I click on Upgrade button subscription "Advanced Awareness"
    And HE I Request Information to Upgrade Subscription

  @MATCH-4687
  Scenario: As a HE user, I want to add special message to upgrade susbcription
    Given HE I am logged in to Intersect HE as user type "upgradeSubscriptions"
    And HE I Verify Upgrade Subscription Ribbon and Button for "Advanced Awareness"
    And HE I click on Upgrade button subscription "Advanced Awareness"
    And HE I select check for field Receive Hobsons Communications
    And HE I enter message on request information page "message.sent.to.Hobsons"
    And HE I Request Information to Upgrade Subscription

  @MATCH-4687
  Scenario: As a HE user, I want to add special message to upgrade susbcription ; verify 500 character limit for message box
    Given HE I am logged in to Intersect HE as user type "upgradeSubscriptions"
    And HE I Verify Upgrade Subscription Ribbon and Button for "Advanced Awareness"
    And HE I click on Upgrade button subscription "Advanced Awareness"
    And HE I select check for field Receive Hobsons Communications
    And HE I enter message on request information page "message.sent.to.Hobsons"
    And HE I verify message is not more than "500" characters
    And HE I Request Information to Upgrade Subscription

  @MATCH-4687
   Scenario: As a HE User, I want to Edit/Update my Contact details and Request Upgrade Information
      Given HE I am logged in to Intersect HE as user type "upgradeSubscriptions"
      And HE I click on Upgrade button subscription "Advanced Awareness"
      And HE I Edit The Contact Details with the following data
      | First Name | Automation4919-Edited |
      | Last Name  | MATCH4919-Edited      |
      | Email      | purpleheautomation+Match4919+Updated@gmail.com |
      | Phone      | 513-746-2317                                   |
      | Message    | Edited Contact detaiils to request information |
      And HE I Request Information to Upgrade Subscription

  @MATCH-4631
  Scenario: As an HE user, I want to verify that user is able to upgrade subscription for CONNECTIONS Module
    Given HE I am logged in to Intersect HE as user type "upgradeSubscriptions"
    And HE I Verify Upgrade Subscription Ribbon and Button for "Connection"
    And HE I click on Upgrade button subscription "Connection"
    And HE I select check for field Receive Hobsons Communications
    And HE I enter message on request information page "message.sent.to.Hobsons"
    And HE I Request Information to Upgrade Subscription

  @MATCH-4631
  Scenario: As an HE user, I want to verify that user is able to upgrade subscription for Events Module
    Given HE I am logged in to Intersect HE as user type "upgradeSubscriptions"
    And HE I Verify Upgrade Subscription Ribbon and Button for "Events"
    And HE I click on Upgrade button subscription "Events"
    And HE I select check for field Receive Hobsons Communications
    And HE I enter message on request information page "message.sent.to.Hobsons"
    And HE I Request Information to Upgrade Subscription




