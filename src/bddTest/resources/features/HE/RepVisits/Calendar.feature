@HE
Feature: HE - RepVisits - Calendar - As an HE user I want to use the RepVisits Calendar features.

  @MATCH-4222
  Scenario: As an RepVisits HE admin premium/paid Presence subscription user, I want the ability to see and select which
  visits and college fair registrations I'd like to transfer at the "Re-assign appointments" modal for the selected HE rep,
  so that I can support appropriate appointment transfer as necessary at a single appointment level or in bulk from one
  user to another user.
    Given HE I want to login to the HE app using "purpleheautomation+administrator@gmail.com" as username and "Password!1" as password
    When HE I go to re assign appointments
    Then HE I verify UI components with the option "Publishing, PurpleHE" in the drop down action
    Then HE I verify UI components with the option "Community, PurpleHE" in the drop down action
    And HE I successfully sign out
    
  @MATCH-4450
  Scenario Outline: As Hobsons product manager managing value adds to getting HE users to upgrade to RV Presence premium subscription,
  I want the RV>Calendar, Agenda view to be premium level access on the HE side while remaining accessible for HS users,
  So that further value can be provided to upgrade to an RV Presence premium subscription.

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

  @MATCH-4450
  Scenario Outline: As Hobsons product manager managing value adds to getting HS users to upgrade to RV Presence premium subscription,
  I want the RV>Calendar, Agenda view to be premium level access on the HS side while remaining accessible for HE users,
  So that further value can be provided to upgrade to an RV Presence premium subscription.

    Given HS I am logged in to Intersect HS through Naviance with user type "<hsNavianceAdmin>"
    Then HS I verify the user can access "Agenda" view
    And HS I successfully sign out

    Given HS I am logged in to Intersect HS through Naviance with user type "<hsNavianceMember>"
    Then HS I verify the user cannot access Agenda view
    And HS I successfully sign out

    Given HS I am logged in to Intersect HS as user type "<hsNon-NavianceAdmin>"
    Then HS I verify the user can access "Agenda" view
    And HS I successfully sign out

    Given HS I am logged in to Intersect HS as user type "<hsNon-NavianceMember>"
    Then HS I verify the user cannot access Agenda view
    And HS I successfully sign out

    Examples:
      |hsNavianceAdmin|hsNavianceMember|hsNon-NavianceAdmin|hsNon-NavianceMember|
      |navianceAdmin  |navianceMember  |administrator      |member              |

   @MATCH-4798
   Scenario: Limit access to the "Re-assign appointments" link to JUST HE admins associated with an HE
   institution that has an active Presence subscription
     #Administrator
     Given HE I am logged in to Intersect HE as user type "administrator"
     Then HE I verify that Re-assign link is "visible"
     #Community
     Given HE I am logged in to Intersect HE as user type "publishing"
     Then HE I verify that Re-assign link is "not visible"
     #Publishing
     Given HE I am logged in to Intersect HE as user type "community"
     Then HE I verify that Re-assign link is "not visible"
     #Limited
     Given HE I am logged in to Intersect HE as user type "limited"
     Then HE I verify that Re-assign link is "not visible"
     #Limited publishing
     Given HE I am logged in to Intersect HE as user type "limitedPublishing"
     Then HE I verify that Re-assign link is "not visible"
     #Limited community
     Given HE I am logged in to Intersect HE as user type "limitedCommunity"
     Then HE I verify that Re-assign link is "not visible"
     #HS User
     Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
     Then HE I verify that Re-assign link is "not visible"
