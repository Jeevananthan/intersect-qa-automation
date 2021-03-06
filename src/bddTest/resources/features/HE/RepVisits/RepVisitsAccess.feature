@HE
Feature: HE - RepVisits - RepVisitsAccess - As an HE user, I want to be able to access the RepVisits features based on my role,subscription

  @MATCH-1697
  Scenario: As an HE user I want to be able to access RepVisit functionality within Intersect so I can find value from this new module and its features
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify the following tabs exist on the RepVisits page
      |Overview |Search and Schedule |Calendar |Travel Plan |Contacts |Recommendations |Notifications|

  @MATCH-1667 @MATCH-2075
  Scenario: As an HE user, I should be able to see Check RepVisits Availability button and Availablity sidebar from HS instituion profile
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "Int QA High School 4" in "Institutions"
    And HE I select "Int QA High School 4" from the results
    Then HE I verify the Check RepVisits Availability button

  @MATCH-1610
  Scenario: As an HE Community member,I need to view a calendar of my appointments
            so that I can easily see what my day/week/month schedule looks like.
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I verify the calendar view in repvisits

  @MATCH-1935 @MATCH-1934 @MATCH-1936 @MATCH-2274
  Scenario: As an HE user tied to an HE account that DOES NOT have the Intersect Presence Subscription activated,
  I need to see an upgrade message on the Contacts,Recommendations,Travel Plan and Visit Feedback tabs.
  #logging to support app to do pre-requisites that is inactive the ''Intersect Presence Subscription'' module
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    And SP I set the "Intersect Presence Subscription" module to "inactive" in the institution page
    And SP I Click the Save Changes button
    Then SP I successfully sign out
  #logging to HE app to verify the upgrade message for contacts, recommendations,Travel Plan and Visit Feedback tabs
    Given HE I am logged in to Intersect HE as user type "limited"
    Then HE I verify the following tabs exist on the RepVisits page
      |Overview |Search and Schedule |Calendar |Travel Plan |Contacts |Recommendations |Notifications| Visit Feedback|
    Then HE I verify the upgrade messaging on the Contacts page in RepVisits
    Then HE I verify the upgrade messaging on the Recommendations page in RepVisits
    Then HE I verify the upgrade messaging on the Travel Plan page in RepVisits
    And HE I navigate to the "Visit Feedback" page in RepVisits
    Then HE I verify the freemium messaging on the Visits Feedback page

  @MATCH-2274
  Scenario: As a HE non-Administrator user, I want to ensure the no access messaging on the Visit Feedback section of the RepVisits page.
    Given HE I am logged in to Intersect HE as user type "community"
    Then HE I navigate to the "Visit Feedback" page in RepVisits
    And HE I verify the non-administrator messaging on the Visits Feedback page

  @MATCH-1989
  Scenario: As an HE user tied to an HE account that has not paid for the Intersect Presence Subscription.
  I want to be presented with a popup/form that allows me to inquire about upgrading my HE account

    #logging to support app to do pre-requisites that is inactive the ''Intersect Presence Subscription'' module
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    And SP I set the "Legacy: Hub page management" module to "inactive" in the institution page
    And SP I set the "Legacy: Community" module to "inactive" in the institution page
    And SP I set the "Intersect Awareness Subscription" module to "inactive" in the institution page
    And SP I set the "Intersect Presence Subscription" module to "inactive" in the institution page
    And SP I Click the Save Changes button
    Then SP I successfully sign out

    #logging to HE app to verify the upgrade message for contacts and recommendations tabs
    Given HE I am logged in to Intersect HE as user type "limited"
    Then HE I verify the upgrade messaging on the Contacts page in RepVisits
    And HE I click the upgrade button
    Then HE I verify the Upgrade popup and the details displayed in the popup
      |First Name |Last Name  |Work Email Address                       |
      |PurpleHE   |Limited    |purpleheautomation+limited@gmail.com     |
    Then HE I verify the upgrade messaging on the Recommendations page in RepVisits
    And HE I click the upgrade button
    Then HE I verify the Upgrade popup and the details displayed in the popup
      |First Name |Last Name  |Work Email Address                   |
      |PurpleHE   |Limited    |purpleheautomation+limited@gmail.com |
    Then HE I verify the upgrade messaging on the Travel Plan page in RepVisits
    And HE I click the upgrade button
    Then HE I verify the Upgrade popup and the details displayed in the popup
      |First Name |Last Name  |Work Email Address                   |
      |PurpleHE   |Limited    |purpleheautomation+limited@gmail.com |

  Scenario: As an HE User, I want to be able to view the weekly recurring time slots and able to view the UI for the "Search and Schedule" Page
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify the Search heading over the search bar in Search and Schedule Tab
    And HE I search for "Int Qa High School 4" in RepVisits page
    Then HE I verify the Search heading over the search bar after search the school in Search and Schedule Tab
    Then HE I verify the Schedule heading over the availability block
    Then HE I verify the calender icon is present next to date
    Then HE I verify the date and calendar icon present over the availability table
    Then HE I verify the next and previous buttons at the top, far right of the availability table
    Then HE I verify the view type button to the left of the next/previous buttons
    Then HE I verify the color of the active view type button
    Then HE I verify "Showing All Scheduled Fairs" Text in Fairs Tab in Search and Schedule Tab
    Then HE I verify the Your Schedule Text in Search and Schedule Page
    And HE I search for "Mays High School" in RepVisits page
    Then HE I verify the Map in SearchAndSchedule Page

  @MATCH-2485
  Scenario: Issue: For HE users viewing their travel plan, the "see details" link for college fairs
  opens the HS in the visits view.ass
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    Then SP I set the "Intersect Presence Subscription" module to "active" in the institution page
    And SP I successfully sign out

    Given HS I want to login to the HS app using "purpleheautomation+hstest@gmail.com" as username and "Password!1" as password
    Then HS I go to the repvisits page
    Then HS I select "All RepVisits Users" to show view availability
    And HS I successfully sign out

    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify the see details link in RepVisits

  @MATCH-3065 @MATCH-3407
  Scenario: As a RepVisits Admin User I want to be able to configure email forwarding of my ActiveMatch and ActiveMatch
            Events Reports So that I can keep non RV Using members of my school staff informed
    Given HE I am logged in to Intersect HE as user type "AccountSettings"
    Then HE I navigate to the Institution Notification page
    Then HE I verify the Institution Notification page
    Then HE I validate the Checkbox in the Institution Notification page using "PurpleHE AccountSettings"
    Then HE I validate the Email in the Institution Notification page using "purpleheautomation@gmail.com","purpleheautomation+AccountSettings@gmail.com",",purpleheautomation+AccountSettings@gmail.com"
    Then HE I successfully sign out
    #Check access for non-admins
    Given HE I am logged in to Intersect HE as user type "AccountSettingsCommunity"
    Then HE I verify the Non-admins do not have the tab in navigation
    Then HE I verify the Non-admins cannot reach the page directly by URL

  @MATCH-2238
  Scenario: Verify Overview page when HE user DOES NOT have Intersect subscription activated
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    And SP I set the "Intersect Presence Subscription" module to "inactive" in the institution page
    And SP I Click the Save Changes button
    Then SP I successfully sign out
    Given HE I am logged in to Intersect HE as user type "limited"
    Then HE I navigate to the "Overview" page in RepVisits
    Then HE I verify the Repvisits Overview Upgrade Subscription page

    @MATCH-1604
    Scenario Outline: As an HE user of an HE account with a Presence subscription activated, I want to be able to view all the high schools I've added to my travel plan
              so that I can easily view all the high school I may want to visit on one screen.
#Pre-Conditions
    Given SP I am logged in to the Admin page as an Admin user
      Then SP I select "The University of Alabama" from the institution dashboard
      Then SP I set the "Intersect Presence Subscription" module to "active" in the institution page
      And SP I successfully sign out
    And HS I want to login to the HS app using "purplehsautomations+LakotaEast@gmail.com" as username and "Password!1" as password
    And HS I set the Visit Availability of RepVisits Availability Settings to "All RepVisits Users"
    Then HS I set the RepVisits Visits Confirmations option to "<Option>"
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
#Creating Visits and Fairs
    Then HS I set the date using "<StartDate>" and "<EndDate>"
    And HS I verify the update button appears and I click update button
    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>" with "<option>"
    Then HS I create a College Fair using the following data "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
    And HS I successfully sign out
#Register a Fair
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "<School>" in RepVisits page
    Then HE I register for the "<College Fair Name>" college fair at "<School>"
#Register a Visit
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<Date>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"
#Verify Travel Plan Tab
    Then HE I verify the instructional text in Travel Plan and verify the link to navigate to the Recommendations page
    When HE I add "<School>" high school with location "<location>" to the Travel Plan
    Then HE I verify the states of the school are present in the ABC order
    Then HE I verify the School details in Travel plan "<School>","<address>","<college going rate>","<senior class size>","<primary POC>","<stateName>"
    Then HE I verify the "Upcoming Appointments" Text is present in the Travel plan for "<School>"
    Then HE I verify upcoming fair message is displayed in the Travel plan page for "<School>"
    Then HE I verify the Visit details are displayed in the Travel plan for "<School>","<StartDate>"
    Then HE I verify the Fair details are displayed in the Travel plan for "<School>","<Date>"
    Then HE I verify the "Remove" button is present in the Travel Plan for "<School>"
    Then HE I verify the text "Previous Appointments" is present in the Travel plan page for "<School>"
    Then HE I verify the text "Nothing scheduled yet" is present in the Travel plan page for "<School>"
    Then HE I verify the "View Availability" Button is present in the Travel plan page for "<School>"
    Then HE I verify the "View Availability" button for "<School>", navigate to the search and schedule page or not
#Verify the label "This school isnt using RepVisits yet" in Travel Plan
    When HE I add "Westlake High School" high school with location "Austin" to the Travel Plan
    Then HE I verify the "This school isnt using RepVisits yet" label is displayed for "Westlake High School"
    Then HE I remove "Westlake High School" high school from the travel plan
    And HE I successfully sign out
#Post Conditions [Removing the created visits and Fairs]
    Given HS I want to login to the HS app using "purplehsautomations+LakotaEast@gmail.com" as username and "Password!1" as password
    Then HS I Click on the View Details button for the College Fair "<College Fair Name>"
    Then HS I select Edit button to cancel the college Fair "<College Fair Name>"
    Then HS I remove the Time Slot created with "<StartDate>","<StartTime>" in Regular Weekly Hours Tab

Examples:
      |School                  |address                                             |college going rate|senior class size|primary POC      |stateName |Day |StartTime|EndTime |NumVisits|StartDate|EndDate |hsEndTime    |Option                                                |heStartTime |heTime  |College Fair Name     |Date|Start Time|End Time|RSVP Deadline|Cost|Max Number of Colleges|Number of Students Expected| ButtonToClick |location   | option |
      |Lakota East High School |6840 Lakota Ln Liberty township, Ohio, Butler, 45044|83                |554              |Intersect QA     |OHIO      |2   |10:25am  |11:25pm |3        |2        |30      |11:25pm      |No, I want to manually review all incoming requests.  |10:25am     |10:25am |QAs Fairs tests       |2   |0900AM    |1000AM  |1            |$25 |25                    |100                        | Save          |Butler     |1       |

