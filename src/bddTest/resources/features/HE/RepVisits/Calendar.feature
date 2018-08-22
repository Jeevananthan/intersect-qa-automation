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

  @MATCH-4622 @MATCH-4550
  Scenario Outline: As a HE admin trying to transfer appointments from one HE user to another at my institution,
                     I want to be reminded that the system does not validate whether there's a conflict of day/time for the appointments being moved with the target new assignees calendars,
                     So that I can decide if I want to continue with the transfer or hold off on it in case I still need to confirm.
   #pre-condition
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone6"
    Then HS I set a date using "<StartDate>" and "<EndDate>"
    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>"
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
    And HS I successfully sign out

    Given HE I am logged in to Intersect HE as user type "<user>"
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<Date>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"

    And HE I verify the blue Note alert "<alertMessage>" is displaying when changing the Select staff member dropdown for the users "Automation, PurpleHE","Community, PurpleHE"
    Then HE I verify the blue Note alert "<alertMessage>" is displaying when changing the Select staff member dropdown for the users "Automation, PurpleHE","Fresh, PurpleHE" with no appointments in Select new assignee
    And HE I verify the users are displaying including "Automation, PurpleHE" in re assign appointments dropdown using "Community, PurpleHE"
    Then HE I verify the user "Automation, PurpleHE" selected from 'select staff member' drop-down, excluded in 'Select new assignee' dropdown
    And HE I successfully sign out

    Examples:
    |user         |alertMessage                                                                                                               |StartTime|EndTime |NumVisits|hsEndTime|School                   |heStartTime   |heTime   |Day|Date|StartDate|EndDate|
    |administrator|RepVisits does not prevent scheduling conflicts. Please confirm availability with the newly assigned rep before proceeding.|12:34am  |12:59pm |3        |12:59pm  |Standalone High School 6 |12:34am       |12:34am  |14 |14  |14       |35     |
