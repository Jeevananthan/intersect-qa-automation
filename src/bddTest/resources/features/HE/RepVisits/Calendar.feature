@HE
Feature: HE - RepVisits - Calendar - As an HE user, I want to use the RepVisits Calendar features.

  @MATCH-4222
  Scenario: As an RepVisits HE admin premium/paid Presence subscription user, I should be able to view visit/fair appointments that I can reassign
    Given HE I want to login to the HE app using "purpleheautomation+administrator@gmail.com" as username and "Password!1" as password
    When HS I go to re assign appointments
    Then HS I verify UI components with the option "Publishing, PurpleHE" in the drop down action
    Then HS I verify UI components with the option "Community, PurpleHE" in the drop down action
    And HE I successfully sign out
    
  @MATCH-4450
  Scenario Outline: As a HE user with active Prescence subscription, I can access Agenda view of my appointments
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I select "The University of Alabama" from the institution dashboard
    And SP I set the "Intersect Presence Subscription" module to "active" in the institution page
    Then SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    And SP I set the "Intersect Presence Subscription" module to "inactive" in the institution page
    And SP I Click the Save Changes button
    Then SP I successfully sign out

    Given HE I am logged in to Intersect HE as user type "<hePremium>"
    Then HE I verify the user can access "Agenda" view
    And HE I successfully sign out

    Given HE I am logged in to Intersect HE as user type "<heFreemium>"
    Then HE I verify the message "Unlock Agenda View" is displaying in the "Agenda" page
    Then HE I verify "UPGRADE" button is displaying in the "Agenda" page
    Then HE I verify the upgrade model page after clicking the UPGRADE button in Agenda view
    And HE I successfully sign out

    Examples:
      |hePremium    |heFreemium       |
      |administrator|limited          |
      |publishing   |limitedPublishing|
      |community    |limitedCommunity |

