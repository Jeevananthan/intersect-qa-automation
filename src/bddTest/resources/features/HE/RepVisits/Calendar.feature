@HE
Feature: HE - RepVisits - Calendar - As an HE user, I want to use the RepVisits Calendar features.

  @MATCH-4222
  Scenario: As an RepVisits HE admin premium/paid Presence subscription user, I should be able to view visit/fair appointments that I can reassign
    Given HE I want to login to the HE app using "purpleheautomation+administrator@gmail.com" as username and "Password!1" as password
    When HE I go to re assign appointments
    Then HS I verify UI components with the option "Publishing, PurpleHE" in the drop down action
    Then HS I verify UI components with the option "Coordinator, PurpleHE" in the drop down action
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

  @MATCH-4902
  Scenario Outline: As a HE admin with an active Presence subscription reassigns a college fair registration from one HE rep to another at their institution,
  that update is not currently being reflected for the HS admin at RV>College Fairs>View Details for the college fair in question.
#Pre-condition
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone6"
    Then HS I cancel registered college fair "<College Fair Name>"
    Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
    Then HS I Click on the "No, I'm Done" button in the success page of the Add Attendees page
    And HS I successfully sign out

    Given HE I want to login to the HE app using "purpleheautomation+publishing@gmail.com" as username and "Password!1" as password
    And HE I search for "<School>" in RepVisits page
    Then HE I register for the "<College Fair Name>" college fair at "<School>"

    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone6"
    Then HS I Click on the View Details button for the College Fair "<College Fair Name>"
    Then HS I verify the Attendee details "<Attendee>" in Edit fairs page
    And HS I successfully sign out

    Then HE I am logged in to Intersect HE as user type "administrator"
    When HE I go to re assign appointments
    Then HE I select the user "Publishing, PurpleHE" from "Select staff member" dropdown
    Then HE I select the fair to reassign using "<Date>","<School>","<Number of Students Expected>"
    Then HE I select the user "Community, PurpleHE" from "Select new assignee" dropdown
    Then HE I click Reassign Appointments button "<appointmentsCount>"

    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone6"
    Then HS I Click on the View Details button for the College Fair "<College Fair Name>"
    Then HS I verify the Attendee details "<reAssignedAttendee>" in Edit fairs page
#Post condition
#cancel the college Fair
    Then HS I Click on the View Details button for the College Fair "<College Fair Name>"
    Then HS I select Edit button to cancel the college Fair "<College Fair Name>"
    And HS I successfully sign out

    Examples:
      |College Fair Name|Date |Start Time|End Time|RSVP Deadline    |Cost|Max Number of Colleges|Number of Students Expected|ButtonToClick|School                  |Attendee              |reAssignedAttendee |appointmentsCount|
      |4902qaFairs      |24   |0800AM    |1000AM  |7                |$25 |25                    |4902                       |Save         |Standalone High School 6|PurpleHE Publishing   |PurpleHE Community |1                |

