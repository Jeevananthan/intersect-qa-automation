@HE
Feature: HE - Upgrade - Upgrade - As an HE user in Intersect, I need to be engaged to upgrade my subscription to Advanced Awareness
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

  @MATCH-4631 @MATCH-5203
  Scenario: As an HE user, I want to verify that user is able to upgrade subscription for CONNECTIONS Module
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    And SP I set the "Connection" module to "inactive" in the institution page
    And SP I set the "ActiveMatch Plus" module to "inactive" in the institution page
    And SP I Click the Save Changes button
    Then SP I successfully sign out

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

  @MATCH-4946
  Scenario: The Counselor Community upgrade forms that allow HE users can to submit to indicate they are interested in paying for an
            Intersect subscription they currently don't have need a few changes.
  #Pre-conditions
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    Then SP I set the "Legacy: Hub page management" module to "inactive" in the institution page
    Then SP I set the "Legacy: Community" module to "inactive" in the institution page
    Then SP I set the "Intersect Awareness Subscription" module to "inactive" in the institution page
    Then SP I set the "Intersect Presence Subscription" module to "inactive" in the institution page
    Then SP I set the "Legacy: ActiveMatch Events" module to "inactive" in the institution page
    Then SP I set the "ActiveMatch Plus" module to "inactive" in the institution page
    Then SP I set the "Advanced Awareness" module to "inactive" in the institution page
    Then SP I set the "Connection" module to "inactive" in the institution page
    And SP I Click the Save Changes button
    Then SP I successfully sign out

    Given HE I am logged in to Intersect HE as user type "limited"
    Then HE I navigate to the community page
    Then HE I click upgrade button in community page
    Then HE I verify the following fields are required in the upgrade form
      |First Name|Last Name|Email|Institution|
    Then HE I verify the check box is changed to "Receive Hobsons communications related to product news, promotions, event updates, and company announcements." in the upgrade form
    Then HE I verify the note "Note: Upgrades are available for U.S. and Canadian based institutions only." is displaying in the upgrade form
    Then HE I verify the privacy policy link navigate to the URL "https://static.intersect.hobsons.com/privacy.html" in the upgrade form
    Then HE I close upgrade form
    Then HE I successfully sign out

  @MATCH-4946
  Scenario Outline: The RepVisits upgrade forms that allow HE users can to submit to indicate they are interested in paying for an
                    Intersect subscription they currently don't have need a few changes.
    #Pre-conditions
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    Then SP I set the "Intersect Presence Subscription" module to "inactive" in the institution page
    And SP I Click the Save Changes button
    Then SP I successfully sign out

    Given HE I am logged in to Intersect HE as user type "limited"
    Then HE I navigate to the Fields "<Fields>" in repvisits page
    Then HE I click upgrade button in repVisits
    Then HE I verify the following fields are required in the upgrade form
      |First Name|Last Name|Email|Institution|
    Then HE I verify the check box is changed to "Receive Hobsons communications related to product news, promotions, event updates, and company announcements." in the upgrade form
    Then HE I verify the note "Note: Upgrades are available for U.S. and Canadian based institutions only." is displaying in the upgrade form
    Then HE I verify the updated text "Manage your RepVisits contacts in one place with Contacts" is displaying in the upgrade form
    Then HE I verify the updated text "A representative will walk you through everything you want to know and discuss pricing." is displaying in the upgrade form
    Then HE I verify the privacy policy link navigate to the URL "https://static.intersect.hobsons.com/privacy.html" in the upgrade form
    Then HE I close upgrade form
    Then HE I successfully sign out

    Examples:
      |Fields         |
      |Agenda         |
      |Travel Plan    |
      |Contacts       |
      |Recommendations|
      |Visit Feedback |