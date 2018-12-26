@HE
Feature: HE - RepVisits - RepVisitsAccess - As an HE user, I want to be able to access the RepVisits features based on my role,subscription

  @MATCH-1697
  Scenario: As an HE user I want to be able to access RepVisit functionality within Intersect so I can find value from this new module and its features
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify the following tabs exist on the RepVisits page
      |Overview |Search and Schedule |Calendar |Travel Plan |Contacts |Recommendations |Notifications|

  @MATCH-1667
  Scenario: As an HE user, I should be able to see Check RepVisits Availability button and Availablity sidebar from HS instituion profile
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "Int QA High School 4" in "Institutions"
    And HE I select "Int QA High School 4" from the results
    Then HE I verify the Check RepVisits Availability button

  @MATCH-1610
  Scenario: As an HE Community member,I need to view a calendar of my appointments
            so that I can easily see what my day/week/month schedule looks like.
    Given HE I am logged in to Intersect HE as user type "community"
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
    When SP I search for "2100209"
    And SP I select the following institution "Bowling Green State University-Main Campus" from the results
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
    When SP I search for "2400006"
    And SP I select "The University of Alabama" from the global search results
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
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I navigate to the Institution Notification page
    Then HE I verify the Institution Notification page
    Then HE I validate the Checkbox in the Institution Notification page using "purple HEadmin"
    Then HE I validate the Email in the Institution Notification page using "purpleheautomation@gmail.com","purpleheautomation+admin@gmail.com",",purpleheautomation+admin@gmail.com"
    Then HE I successfully sign out
    #Check access for non-admins
    Given HE I am logged in to Intersect HE as user type "community"
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
      When SP I search for "2400006"
      And SP I select "The University of Alabama" from the global search results
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
    Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
    And HS I successfully sign out
#Register a Fair
    Given HE I want to login to the HE app using "purpleheautomation@gmail.com" as username and "Password!1" as password
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

  @MATCH-1603
  Scenario Outline: As an HE user I need to be able to view the scheduling results of my Visits search AFTER I have
                    selected an individual high school from the intermediate results list OR my original search matched
                    one specific high school so I can optimize my high school visit and fair travel schedule.
  #precondition
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    And HS I set the Visit Availability of RepVisits Availability Settings to "All RepVisits Users"
    Then HS I set the RepVisits Visits Confirmations option to "<Option>"
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
    And HS I successfully sign out

    Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "<School>" in RepVisits page
    Then HE I verify default page as show visits tab and toggle between tabs
    Then HE I verify the high school information contains the following data
      |Int Qa High School 4|Liberty Township, |
    Then HE I verify the Intersect Presence Subscription module is active for "<School>"
    Then HE I verify the high school information popup contains the following details
      |Int Qa High School 4|Liberty Township, |
    Then HE I verify No Appointments Available and blocked text for "<School>"
    Then HE I select high school's Counselor Community institution profile link for "<School>"
  #Check school with a Limited HE account
    Given HE I want to login to the HE app using "purpleheautomation+limited@gmail.com" as username and "Password!1" as password
    And HE I search for "<School>" in RepVisits page
    Then HE I verify the Intersect Presence Subscription module is Inactive for "<School>"
  #Log in to HS and set an appointment slot
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I set the date using "<StartDate>" and "<EndDate>"
    And HS I verify the update button appears and I click update button
    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>" with "<option>"
    Then HS I set the RepVisits Visits Confirmations option to "<Option>"
  #Log back into HE and make sure that the visit popups work as expected
    Given HE I want to login to the HE app using "purpleheautomation@gmail.com" as username and "Password!1" as password
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<StartDate>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"

    Examples:
     |Day|StartTime|EndTime |NumVisits|StartDate|EndDate |Option                                              |hsEndTime|Option                             |School               |heStartTime|heTime | option |
     |14 |10:25am  |11:25pm |3        |14       |42      |No, I want to manually review all incoming requests.|11:25pm  |Yes, accept all incoming requests. |Int Qa High School 4 |10:25am    |10:25am|1       |

 @MATCH-4260 @MATCH-3730
  Scenario Outline: As an HE Freemium user I can not be able to view Your Notifications stub menu in Account settings page
    Given HE I am logged in to Intersect HE as user type "<hePremiumUser>"
    Then HE I verify "<yourNotifications>" stub menu is present in Account settings page for Premium
    Then HE I set the value Alert me when high schools become available in RepVisits to selected
    Then HE I verify the following details are present in Your Notifications subtab
      |Your Notifications|REPVISITS|Alert me when high schools become available in RepVisits|
    Then HE I verify the success message "Your notifications settings were updated." after click Save button
    Then HE I verify the saved changes after navigate away from Your Notifications subtab
    Then HE I set the value Alert me when high schools become available in RepVisits to selected
    Then HE I successfully sign out

   #precondition
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I select "<university>" from the institution dashboard
    And SP I set the "<module>" module to "<activeOrInactive>" in the institution page
    And SP I Click the Save Changes button
    Then SP I successfully sign out

    Given HE I am logged in to Intersect HE as user type "<heFreemiumUser>"
    Then HE I verify "<yourNotifications>" stub menu is not present in Account settings page for Freemium

    Examples:
      |hePremiumUser|heFreemiumUser    |yourNotifications |university                                |module                          |activeOrInactive|
      |administrator|limited           |Your Notifications|Bowling Green State University-Main Campus|Intersect Presence Subscription |inactive        |
      |publishing   |limitedPublishing |Your Notifications|Bowling Green State University-Main Campus|Intersect Presence Subscription |inactive        |
      |community    |limitedCommunity  |Your Notifications|Bowling Green State University-Main Campus|Intersect Presence Subscription |inactive        |

  @MATCH-4260 @MATCH-3730
  Scenario Outline: As an HS user I can not be able to view Your Notifications stub menu in Account settings page
#Naviance
    Given HS I am logged in to Intersect HS through Naviance with user type "<navianceUser>"
    Then HS I verify "<yourNotifications>" stub menu is not present in Account settings page for "Naviance"
    And HS I successfully sign out

  #Non-Naviance
    Given HS I am logged in to Intersect HS as user type "<non-NavianceUser>"
    Then HS I verify "<yourNotifications>" stub menu is not present in Account settings page for "non-Naviance"

    Examples:
      |non-NavianceUser |navianceUser  |yourNotifications |
      |administrator    |navianceAdmin |Your Notifications|
      |member           |navianceMember|Your Notifications|

  @MATCH-4224
  Scenario Outline: As an RepVisits HE admin premium/paid Presence subscription user, I want to understand why I can't submit the "Re-assign Appointments" modal form,
                    so that I can correct my entries as necessary and successfully submit.
#Pre-Conditions
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    And HS I set the Visit Availability of RepVisits Availability Settings to "All RepVisits Users"
    Then HS I set the RepVisits Visits Confirmations option to "<Option>"
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
#Create a Visit
    Then HS I set the date using "<StartDate>" and "<EndDate>"
    And HS I verify the update button appears and I click update button
    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>" with "<option>"
    And HS I successfully sign out
#Register a Visit
    Given HE I want to login to the HE app using "purpleheautomation+4224@gmail.com" as username and "Password!1" as password
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<Date>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"
    And HE I successfully sign out

    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify the error Message "Please select a Staff Member" is displaying when Select staff member is not selected
    Then HE I verify the error Message "Please select a New Assignee" is displaying when Select new assignee is not selected using "4224, Automation"
    Then HE I verify the error Message "Please select at least one appointment" is displaying when appointments is not selected using "4224, Automation","Community, PurpleHE"
    Then HE I verify the error Message "have any appointments scheduled." is displaying when the user "Fresh, PurpleHE" is selected
    Then HE I verify the error Message "Please select a Staff Member" is disappearing when the error message "doesn't have any appointments scheduled." is displayed for "Fresh, PurpleHE"

  Examples:
  |School               |Day |StartTime|EndTime |NumVisits|StartDate|EndDate |hsEndTime    |Option                                                |heStartTime |heTime  |Date| option |
  |Int Qa High School 4 |14  |10:25am  |11:25pm |3        |14       |42      |11:25pm      |No, I want to manually review all incoming requests.  |10:25am     |10:25am |14  |1       |

  @MATCH-3631
  Scenario Outline: As a HE and HS user in RVs viewing my calendar in Agenda view,
             I want the dates that are grayed out (i.e. past dates) to not be selectable,
             so that I don't accidentally select a date earlier than the start date and potentially get confused as to why no data is showing.

    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I set the date using "<StartDate>" and "<EndDate>" in calendar "Agenda" view
    Then HS I verify the disabled date "<disabledDate>" is not clickable in calendar Agenda view
    And HS I successfully sign out

    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I set the date using "<StartDate>" and "<EndDate>" in calendar "Agenda" view
    Then HE I verify the disabled date "<disabledDate>" is not clickable in calendar Agenda view

    Examples:
       |StartDate|EndDate|disabledDate|
       |7        |14     |5           |

  @MATCH-2746
  Scenario Outline: As a HE user, I should see an Agenda view in calendar
    Given HS I am logged in to Intersect HS as user type "default"
    Then HS I navigate to the "Calendar" page in RepVisits
    Then HS I schedule a new visit for "<Date>","<newVisitSTime>","<newVisitETime>","<Attendees>","<visitLocation>"
    And HS I click on Agenda on Calendar
    Then HS I select Start date "<Date>" and End date "<Date>" in Agenda view
    Then HS I verify that "1" visits are displayed in Agenda view
    Then HS I verify that it should not be possible to select an End date "4" which is less than the Start date "5" in Agenda view

    Examples:
      |Date|newVisitSTime|newVisitETime|Attendees           |visitLocation|
      |14  |11:02am      |10:58pm      |PurpleHE Automation |Cbba         |
