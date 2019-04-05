@HE
Feature: HE - RepVisits - RepVisitsAccessII - As an HE user, I want to be able to access the RepVisits features based on my role,subscription

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
    Given HE I am logged in to Intersect HE as user type "limited"
    And HE I search for "<School>" in RepVisits page
    Then HE I verify the Intersect Presence Subscription module is Inactive for "<School>"
  #Log in to HS and set an appointment slot
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I set the date using "<StartDate>" and "<EndDate>"
    And HS I verify the update button appears and I click update button
    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>" with "<option>"
    Then HS I set the RepVisits Visits Confirmations option to "<Option>"
  #Log back into HE and make sure that the visit popups work as expected
    Given HE I am logged in to Intersect HE as user type "administrator"
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
    Then HS I select Start date "<Date>" and End date "<Date>" in calendar Agenda view
    Then HS I verify that "1" visits are displayed in Agenda view
    Then HS I verify that it should not be possible to select an End date "4" which is less than the Start date "5" in Agenda view

    Examples:
      |Date|newVisitSTime|newVisitETime|Attendees           |visitLocation|
      |14  |11:02am      |10:58pm      |PurpleHE Automation |Cbba         |

  @MATCH-2075
  Scenario: As an HE user of the Counselor Community who is viewing a HS profile, I want to quickly find an action to view
  that high school's visit and fair availability if they are using RepVisits so I can plan my seasonal travel plan.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then I navigate to Counselor Community page
    Then HE I select the search category as "Int QA Combined School 2"
    Then HE I select the search result "Int QA Combined School 2"
    Then HE I verify the "Add to Travel Plan" button

  @MATCH-2051
  Scenario: As an HE user I want the quick view calendar on the Search and Schedule RepVisit page to be a premium feature so non premium HE accounts see more value in upgrading.
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I select "Anne Arundel Community College" from the institution dashboard
    Then SP I set the "Intersect Presence Subscription" module to "inactive" in the institution page
    And SP I successfully sign out
#verify premium feature details  in search and schedule page for limited account
    Given HE I am logged in to Intersect HE as user type "Module"
    Then HE I navigate to "Search and Schedule" page
    Then HE I verify the premium feature header is displaying in search and schedule page using "Premium Feature"
    Then HE I verify the lock icon is displaying in search and schedule page using "locked"
    Then HE I verify learn more hyper link is displaying in search and schedule page using "Learn more"
    Then HE I successfully sign out

    Given SP I am logged in to the Admin page as an Admin user
    Then SP I select "Anne Arundel Community College" from the institution dashboard
    Then SP I set the "Intersect Presence Subscription" module to "active" in the institution page
    And SP I successfully sign out
#verify premium feature details in search and schedule page for premium account
    Given HE I am logged in to Intersect HE as user type "Module"
    Then HE I navigate to "Search and Schedule" page
    Then HE I verify the premium feature header is not displaying in search and schedule page
    Then HE I verify the lock icon is not displaying in search and schedule page
    Then HE I verify learn more hyper link is not displaying in search and schedule page
    Then HE I successfully sign out

  @MATCH-1606
  Scenario: As an HE user I want to submit a visit request to a high school for approval so I can build out my travel plan.
#High school without auto approvals enabled
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I clean the visits for particular Month "14"
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    Then HS I set the RepVisits Visits Confirmations option to "No, I want to manually review all incoming requests."

    Then HS I set the date using "14" and "28"
    And HS I verify the update button appears and I click update button
    Then HS I clear the time slot for the particular day "14" in Regular Weekly Hours Tab
    Then HS I add the new time slot with "14","10:22am","12:22pm" and "2" with "1"
    And HS I successfully sign out

    Then HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "Int QA High School 4" in "Institutions"
    And HE I select "Int QA High School 4" from the results
    Then HE I verify the Check RepVisits Availability button
    Then HE I verify the availability pill is displaying in community availability side bar "14"
    Then HE I verify the availability pill is clickable in community availability side bar
    Then HE I verify that i can close the schedule popup in community availability side bar by clicking "CANCEL" button if i do not want to submit request
    Then HE I close community availability side bar
    And HE I search for "Int Qa High School 4" in RepVisits page
    Then HE I verify the Availability slot "10:22am" is displaying in the visit toggle "14","Int Qa High School 4" in search and schedule Tab
    Then HE I verify the pill is clickable in search and schedule page
    Then HE I verify the appointment date is displaying in the schedule popup "14"
    Then HE I verify the start and end time is displaying in the schedule popup "10:22am","12:22pm"
    Then HE I verify the time zone is displaying in the schedule popup
    Then HE I verify the High school name is displaying in the schedule popup "Int Qa High School 4"
    Then HE I verify the "Yes, Request this time" button is displaying in the schedule popup
    Then HE I verify that i can close the schedule popup by clicking "CANCEL" button if i do not want to submit request
    Then HE I verify the pill is clickable in search and schedule page
    Then HE I verify "Yes, Request this time" button to submit request to the high school
    Then HE I verify the success message "Visit requested! You will receive an email notification when your request has been confirmed." is displaying in search and schedule page
    Then HE I successfully sign out

    Then HE I am logged in to Intersect HE as user type "publishing"
    And HE I search for "Int Qa High School 4" in RepVisits page
    Then HE I verify the Availability slot "10:22am" is displaying in the visit toggle "14","Int Qa High School 4" in search and schedule Tab
    Then HE I verify the pill is clickable in search and schedule page
    Then HE I verify the appointment date is displaying in the schedule popup "14"
    Then HE I verify the start and end time is displaying in the schedule popup "10:22am","12:22pm"
    Then HE I verify the time zone is displaying in the schedule popup
    Then HE I verify the High school name is displaying in the schedule popup "Int Qa High School 4"
    Then HE I verify the "Yes, Request this time" button is displaying in the schedule popup
    Then HE I verify that i can close the schedule popup by clicking "CANCEL" button if i do not want to submit request

    Then HE I logged in to Intersect HE as another HE user type "community"
    And HE I search for "Int Qa High School 4" in RepVisits page
    Then HE I select Visits to schedule the appointment for "Int Qa High School 4" using "14" and "10:22am"
    And HE I verify the schedule pop_up for "Int Qa High School 4" using "10:22am" and "12:22pm"

    Then HE I switch to another HE User Tab
    Then HE I verify the pill is clickable in search and schedule page
    Then HE I verify "Yes, Request this time" button to submit request to the high school
    Then HE I verify the negative message "Sorry, this appointment is no longer available. Please select another appointment." is displaying in search and schedule page

    Then HE I am logged in to Intersect HE as user type "administrator"
    Then HE I go to the calendar page and verify the visit appointment is displaying with gray color "Int Qa High School 4","14","Scheduled"
    Then HE I successfully sign out

    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I go to the calendar page and verify the visit appointment is displaying with gray color "The University of Alabama","14","Scheduled"
    Then HS I remove the Time Slot created with "14","10:22am" in Regular Weekly Hours Tab
    And HS I successfully sign out

  @MATCH-1606
  Scenario: As an HE user I want to submit a visit request to a high school for approval so I can build out my travel plan.
#High school with auto approvals enabled
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I clean the visits for particular Month "14"
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    Then HS I set the RepVisits Visits Confirmations option to "Yes, accept all incoming requests."

    Then HS I set the date using "14" and "28"
    And HS I verify the update button appears and I click update button
    Then HS I clear the time slot for the particular day "14" in Regular Weekly Hours Tab
    Then HS I add the new time slot with "14","10:22am","12:22pm" and "2" with "1"
    And HS I successfully sign out

    Then HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "Int QA High School 4" in "Institutions"
    And HE I select "Int QA High School 4" from the results
    Then HE I verify the Check RepVisits Availability button
    Then HE I verify the availability pill is displaying in community availability side bar "14"
    Then HE I verify the availability pill is clickable in community availability side bar
    Then HE I verify that i can close the schedule popup in community availability side bar by clicking "CANCEL" button if i do not want to submit request
    Then HE I close community availability side bar
    And HE I search for "Int Qa High School 4" in RepVisits page
    Then HE I verify the Availability slot "10:22am" is displaying in the visit toggle "14","Int Qa High School 4" in search and schedule Tab
    Then HE I verify the pill is clickable in search and schedule page
    Then HE I verify the appointment date is displaying in the schedule popup "14"
    Then HE I verify the start and end time is displaying in the schedule popup "10:22am","12:22pm"
    Then HE I verify the time zone is displaying in the schedule popup
    Then HE I verify the High school name is displaying in the schedule popup "Int Qa High School 4"
    Then HE I verify the "Yes, Request this time" button is displaying in the schedule popup
    Then HE I verify that i can close the schedule popup by clicking "CANCEL" button if i do not want to submit request
    Then HE I verify the pill is clickable in search and schedule page
    Then HE I verify "Yes, Request this time" button to submit request to the high school
    Then HE I verify the success message "Visit confirmed! Your request has been automatically confirmed by the high school." is displaying in search and schedule page
    Then HE I successfully sign out

    Then HE I am logged in to Intersect HE as user type "publishing"
    And HE I search for "Int Qa High School 4" in RepVisits page
    Then HE I verify the Availability slot "10:22am" is displaying in the visit toggle "14","Int Qa High School 4" in search and schedule Tab
    Then HE I verify the pill is clickable in search and schedule page
    Then HE I verify the appointment date is displaying in the schedule popup "14"
    Then HE I verify the start and end time is displaying in the schedule popup "10:22am","12:22pm"
    Then HE I verify the time zone is displaying in the schedule popup
    Then HE I verify the High school name is displaying in the schedule popup "Int Qa High School 4"
    Then HE I verify the "Yes, Request this time" button is displaying in the schedule popup
    Then HE I verify that i can close the schedule popup by clicking "CANCEL" button if i do not want to submit request

    Then HE I logged in to Intersect HE as another HE user type "community"
    And HE I search for "Int Qa High School 4" in RepVisits page
    Then HE I select Visits to schedule the appointment for "Int Qa High School 4" using "14" and "10:22am"
    And HE I verify the schedule pop_up for "Int Qa High School 4" using "10:22am" and "12:22pm"

    Then HE I switch to another HE User Tab
    Then HE I verify the pill is clickable in search and schedule page
    Then HE I verify "Yes, Request this time" button to submit request to the high school
    Then HE I verify the negative message "Sorry, this appointment is no longer available. Please select another appointment." is displaying in search and schedule page

    Then HE I am logged in to Intersect HE as user type "administrator"
    Then HE I go to the calendar page and verify the visit appointment is displaying with blue color "Int Qa High School 4","14","Scheduled"
#verify school in travel plan list
    Then HE I navigate to "Travel Plan"
    Then HE I verify "Int Qa High School 4" is displayed in the Travel Plan list
    Then HE I successfully sign out

    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I go to the calendar page and verify the visit appointment is displaying with blue color "The University of Alabama","14","Scheduled"

#Clean environment
    Then HS I remove the Time Slot created with "14","10:22am" in Regular Weekly Hours Tab
    And HS I successfully sign out

