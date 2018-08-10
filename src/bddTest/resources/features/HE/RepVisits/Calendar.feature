@HE
Feature: HE - RepVisits - Calendar - As an HE user I want to use the RepVisits Calendar features.

  @MATCH-4222
  Scenario: As an RepVisits HE admin premium/paid Presence subscription user, I want the ability to see and select which
  visits and college fair registrations I'd like to transfer at the "Re-assign appointments" modal for the selected HE rep,
  so that I can support appropriate appointment transfer as necessary at a single appointment level or in bulk from one
  user to another user.
    Given HE I want to login to the HE app using "purpleheautomation+administrator@gmail.com" as username and "Password!1" as password
    When HE I go to re assign appointments
    Then HS I verify UI components with the option "Publishing, PurpleHE" in the drop down action
    Then HS I verify UI components with the option "Community, PurpleHE" in the drop down action
    And HE I successfully sign out
    
  @MATCH-4450
  Scenario Outline: As Hobsons product manager managing value adds to getting HE users to upgrade to RV Presence premium subscription,
  I want the RV>Calendar, Agenda view to be premium level access on the HE side while remaining accessible for HS users,
  So that further value can be provided to upgrade to an RV Presence premium subscription.

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

  @MATCH-4622 @MATCH-4550
  Scenario Outline : As a HE admin trying to transfer appointments from one HE user to another at my institution,
                     I want to be reminded that the system does not validate whether there's a conflict of day/time for the appointments being moved with the target new assignees calendars,
                     So that I can decide if I want to continue with the transfer or hold off on it in case I still need to confirm.

    Given HE I am logged in to Intersect HE as user type "<user>"
    When HE I go to re assign appointments
    And HE I verify the the blue Note alert "<alertMessage>" is displaying when changing the Select staff member dropdown for the user "Community, PurpleHE","Publishing, PurpleHE"
    Then HE I verify the the blue Note alert "<alertMessage>" is displaying when changing the Select staff member dropdown for the user "Community, PurpleHE","Fresh, PurpleHE" with no appointments in Select new assignee
    And HE I verify the users are displaying including "Automation, PurpleHE" in re assign appointments dropdown using "Community, PurpleHE"
    Then HE I verify the user "Community, PurpleHE" selected from 'select staff member' drop-down, excluded in 'Select new assignee' dropdown
    And HE I successfully sign out

    Examples:
    |user         |alertMessage                                                                                                               |
    |administrator|RepVisits does not prevent scheduling conflicts. Please confirm availability with the newly assigned rep before proceeding.|