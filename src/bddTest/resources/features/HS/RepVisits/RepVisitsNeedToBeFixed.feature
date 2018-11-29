@HS @ignore
Feature:  As an HS user, I want to be able to access the features of the RepVisits module.


 #  This ticket is commented by missing fix because is changing the password and not recovering the initial password, Jeeva
#  comment that are some issue related to this ticket
#  @MATCH-3062
#  Scenario Outline: As a HS RepVisits User,I need to be able to update my contact information and reset my password
#                    So I can effectively manage my RepVisits Account.
#
#    Given HS I want to login to the HS app using "<usertype>" as username and "<oldPassword>" as password
#    Then HS I navigate to the "Account Settings" Page
#    Then HS I reset the password for "<oldPassword>","<newPassword>"
#    And HS I verify the success message "Success! You've updated your account information." in Account settings page
#    And HS I successfully sign out
#
#    Given HS I want to login to the HS app using "<usertype>" as username and "<newPassword>" as password
#    Then HS I navigate to the "Account Settings" Page
#    And HS I verify the left-sub menu "Account Information" is present in the Account Settings page
#    And HS I verify the non-password fields "Account Information,Your Name,First Name,Last Name,Contact Information,Email,Change Password,Current Password,New Password,Confirm New Password" are pre-populated with current data "<FirstName>","<LastName>","<Email>"
#      |contain a lowercase letter|contain an uppercase letter|contain a number|
#    And HS I validate the password field "<user>","<newPassword>","<minimum8character>","<lowercaseletter>","<uppercaseletter>","<withoutNumber>","<withoutspecialcharacter>"
#    And HS I verify the success message "Success! You've updated your account information." in Account settings page
#    And HS I successfully sign out
#  Examples:
#   |usertype                                  |oldPassword|newPassword|minimum8character|lowercaseletter|uppercaseletter|withoutNumber|withoutspecialcharacter|user          |FirstName|LastName|Email                                     |
#   |purpleheautomation+administrator@gmail.com|Password!1 |Password#1 |word!1           |password#1     |PASSWORD#1     |Password#*   |Password1              |administrator |Test     |qa      |purpleheautomation+administrator@gmail.com|
#   |purpleheautomation+member@gmail.com       |Password!1 |Password#1 |word!1           |password#1     |PASSWORD#1     |Password#*   |Password1              |member        |QA       |Test    |purpleheautomation+member@gmail.com       |

      #Scenario is failing and need to be fixed
#  Fixed:
#  Scenario: As an HS user, I want to be able to add/remove time slots
#    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
#    When HS I open the Exceptions page
#    And HS I select a date "10" days ahead from now
#    And HS I add a new time slot with the following data:
#      | Start time | 07:03 am |
#      | End time   | 08:00 am |
#      | Visits     | 3        |
#    Then HS I verify that the time slot was added in a generated date, with the start time "7:03am"
#    And HS I delete the time slot in a generated date, with start time "7:03am"
#    And HS I verify that the time slot was removed from the generated date, with the start time "7:03am"
#    And HS I successfully sign out

#  Fixed:
#  Scenario: As an HS User, I want to be able to clear a day
#    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
#    When HS I open the Exceptions page
#    And HS I select a date "11" days ahead from now
#    And HS I add a new time slot with the following data:
#      | Start time | 07:04 am |
#      | End time   | 08:00 am |
#      | Max Visits | 3        |
#    And HS I select a date "11" days ahead from now
#    Then HS I clear the day
#    And HS I verify that the time slot was removed from the generated date, with the start time "7:04am"
#    And HS I successfully sign out

  #  @MATCH-1469
#  Scenario: As a HS user Manually Add a Contact to Appointment
#    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
#    And HS I am Navigating to Calendar Home Screen
#    And HS I click on button Add Visit
#    And HS I select custom time manually
#    And HS I select a date "12" days ahead from now
#    And HS I select Visit StartTime "9:40am" and End Time "10:00am"
#    And HS I click on link Add School User Manually
#    And HS I Enter Following Data to Add a School User Manually
#      |FirstName|Amanda|
#      |LastName |Hubs  |
#      |E-mail|amanda@hobsons.com  |
#      |Phone    |5137462317         |
#      |Position |QA Tester         |
#      |Institution|Alma College    |
#    And HS I Enter Internal Notes "Visit Notes Added for Automation Purpose"
#    And HS I click on Add Visit button
#    And HS I click on Agenda on Calendar
#    And Hs I open the date picker on Agenda View
#    And HS I select a date "12" days ahead from now from the standard date picker
#    And HS I click on Day on Calendar
#    And HS I click on Visit with "Alma College" from "9:40 AM" to "10:00 AM" on Day Calendar
#    And HS I verify Representative details on Visit Details screen "amanda@hobsons.com"
#    And HS I Cancel visit to create again add Notes to Cancel "canceled for automation"
#    And HS I successfully sign out

#  @MATCH-2094 @MATCH-2124
#  Scenario Outline: As an HE user I want to see RepVisit notifications organized intuitively within my Notifications
#  page REQUESTS subtab so I can efficiently find the updates I am looking for within RepVisits.
#    # FOR VISITS
#    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
#    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
#    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
#    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
#    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
#    Then HS I set the RepVisits Visits Confirmations option to "<Option>"
#    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
#
#    Then HS I set the date using "<StartDate>" and "<EndDate>"
#    And HS I verify the update button appears and I click update button
#    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
#    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>"
#    And HS I successfully sign out
#
#    Given HE I want to login to the HE app using "purpleheautomation@gmail.com" as username and "Password!1" as password
#    And HE I search for "<School>" in RepVisits page
#    Then HE I select Visits to schedule the appointment for "<School>" using "<Date>" and "<heStartTime>"
#    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"
#    Then HE I verify the message "You currently have no notifications" is displayed in the Request subTab
#    Then HE I verify the Paginate the REQUESTS subtab via 25 entries with a "Show More" action to display the next 25 entries
#    And HE I verify the Notifications & Tasks using "<School>","<StartDate>","<heStartTime>"
#    Then HE I click the View full details option in the Request subTab using "<School>","<StartDate>","<heStartTime>"
#    Then HE I successfully sign out
#
#    #FOR FAIRS
#    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
#    Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
#    And HS I successfully sign out
#
#    Given HE I want to login to the HE app using "purpleheautomation@gmail.com" as username and "Password!1" as password
#    And HE I search for "<School>" in RepVisits page
#    #TC may fail on the next step due to MATCH-3877
#    Then HE I register for the "<College Fair Name>" college fair at "<School>"
#    Then HE I verify the message "You currently have no notifications" is displayed in the Request subTab
#    Then HE I verify the Paginate the REQUESTS subtab via 25 entries with a "Show More" action to display the next 25 entries
#    And HE I verify the Notifications & Tasks using "<School>","<Date>","<fairTime>" for fairs
#    Then HE I click the View full details option in the Request subTab using "<School>","<Date>","<fairTime>" for fairs
#    Then HE I successfully sign out
#
#    Examples:
#      |Day |StartTime|EndTime |NumVisits|StartDate|EndDate |hsEndTime    |Option                                               |School              |heStartTime |heTime  |College Fair Name         |Date|Start Time|End Time|RSVP Deadline|Cost|Max Number of Colleges|Number of Students Expected| ButtonToClick |fairTime|
#      |35  |10:      |11:25pm |3        |35       |49      |11:25pm      |No, I want to manually review all incoming requests. |Int Qa High School 4|10:         |10:     |QA4 Fairs for testing     |35  |0900AM    |1000AM  |28           |$25 |25                    |100                        | Save          |9:00am  |

#  @MATCH-1762 @MATCH-2124
#  Scenario Outline: As an HE Community member,
#  I need to be able to view appointment details in my calendar of my appointments
#  so that I can easily get address/contact/additional info on the scheduled visit.
#
#    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
#    Then HS I set the RepVisits Visits Confirmations option to "<Option>"
#    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
#    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
#    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
#    Then HS I set the date using "<StartDate>" and "<EndDate>"
#    And HS I verify the update button appears and I click update button
#    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
#    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>"
#    And HS I successfully sign out
#
##premium
#    Given HE I want to login to the HE app using "purpleheautomation+HEadmin@gmail.com" as username and "Password!1" as password
#    And HE I search for "<School>" in RepVisits page
#    Then HE I select Visits to schedule the appointment for "<School>" using "<Date>" and "<heStartTime>"
#    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"
#
#    And HE I select calendar in RepVisits
#    Then HE I verify the calendar page using "<School>","<heCT>","<Date>"
#    Then HE I verify the popup for "<School>" using "<Date>","<heCST>","<heCET>","<hsAddress>","<contactPhNo>","<user>","<eMail>"
#    Then HE I verify the calendar page using "<School>","<heCT>","<Date>"
#    Then HE I remove the appointment from the calendar
#    Then HE I successfully sign out
#
##community
#    Given HE I want to login to the HE app using "purpleheautomation+community@gmail.com" as username and "Password!1" as password
#    And HE I search for "<School>" in RepVisits page
#    Then HE I select Visits to schedule the appointment for "<School>" using "<Date>" and "<heStartTime>"
#    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"
#
#    Then HE I verify the calendar page using "<School>","<heCT>","<Date>"
#    Then HE I verify the popup for "<School>" using "<Date>","<heCST>","<heCET>","<hsAddress>","<contactPhNo>","<user>","<eMail>"
#    Then HE I successfully sign out
#
##freemium
#    Given HE I want to login to the HE app using "purpleheautomation+limited@gmail.com" as username and "Password!1" as password
#    And HE I search for "<School>" in RepVisits page
#    Then HE I select Visits to schedule the appointment for "<School>" using "<Date>" and "<heStartTime>" in freemium
#    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"
#
#    Then HE I verify the calendar page using "<School>","<heCT>","<Date>"
#    Then HE I verify the popup for "<School>" using "<Date>","<heCST>","<heCET>","<hsAddress>","<contactPhNo>","<user>","<eMail>" for freemium
#    Then HE I successfully sign out
#
##Remove the time slot in Regular Weekly Hours Tab
#    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
#    Then HS I set the RepVisits Visits Confirmations option to "Yes, accept all incoming requests"
#    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
#    Then HS I remove the Time Slot created with "<StartDate>","<StartTime>" in Regular Weekly Hours Tab
#    And HS I successfully sign out
#
#    Examples:
#      |Day |Date|StartTime|EndTime|NumVisits|StartDate|EndDate|hsEndTime|Option                                              |School              |heStartTime|heTime   |heCT     |heCST   |heCET   |hsAddress                                |contactPhNo|user      |eMail                        |
#      |7   |21  |11:50am  |12:11pm|10       |21       |49     |12:11pm  |No, I want to manually review all incoming requests.|Int Qa High School 4|11:50am    |11:50am  |11:50AM  |11:50 AM|12:11 PM|6840 LAKOTA LN Liberty Township, OH 45044|1234567890 |IAM Purple|naviance-email@mock.com|

#  @MATCH-2381 @test
#  Scenario: As a Non Naviance HS RepVisits user verify note to let users know their contact info will be visible
#    Given HS I want to login to the HS app using "hobsonstest11@mailinator.com" as username and "Control!23" as password
#    And HS I Navigate to College Fairs tab of the Repvisits Page
#    And HS I Click button Add a College Fair to Add a fair
#    And HS I verify Note on Add Edit Fair screen "Please note: Your high school name, address, email, and primary contact phone number will be displayed to admission representatives."
#    And HS I click on close icon on Add Edit College Fair pop-up
#    And HS I click View Details against fair
#    And HS I click on Edit button to edit fair
#    And HS I verify Note on Add Edit Fair screen "Please note: Your high school name, address, email, and primary contact phone number will be displayed to admission representatives."
#    And HS I successfully sign out

#  @MATCH-1781
#  Scenario: As a HS user, I need to be able to search for high schools during the registration process.
#  so I can associate myself with45 the high school I work at
#    Given HS Iam navigating to Intersect HS through Non naviance Url
#    Then HS I click the new user link in the login page
#    When HS I click on HIGHER EDUCATION STAFF MEMBER
#    And HS I search for "Acs Abu Dhabi" in "High school" and verify the results

#  @MATCH-1848
#  Scenario: As a HS user, I need to see particular information and instructions on a HS Reg Institution Page.
#  So I can verify the institution is my high school and request a user account.
#    Given HS I navigate to Registration Intersect url
#    Then HS I verify the Institution page
#    And HS I search for "Homeconnection" in High School Staff Member registration page
#    Then HS I verify the address page of "Homeconnection" which is a "non-naviance" school in "Washington"
#    And HS I verify the link "please complete this form."
#    Given HS I navigate to Registration Intersect url
#    And HS I search for "Int QA High School 3" in High School Staff Member registration page
#    Then HS I verify the address page of "Int QA High School 3" which is a "naviance" school in "Arlington"
#    And HS I verify the link "Naviance"
  #Test case is not running accorded to the expected, need to be fixed.
#  @MATCH-1617 @MATCH-1997
#  Scenario: As a high school community user, I want to be able to accept or deny a college that requests to attend my fair.
#            So that I can ensure the colleges attending are a good match for my students. 
#    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
#    Then HS I create a College Fair with the following data
#      | College Fair Name                                         | Fair QA Test#03         |
#      | Automatically Confirm Incoming Requestions From Colleges? | no                      |
#      | Cost                                                      | 10                      |
#      | Start Time                                                | 0800AM                  |
#      | Date                                                      | 5                       |
#      | RSVP Deadline                                             | 4                       |
#      | End Time                                                  | 0800PM                  |
#      | Max Number of Colleges                                    | 10                      |
#      | Number of Students Expected                               | 10                      |
#      | Instructions for College Representatives                  | Submit request by Email |
#      | Email Message to Colleges After Confirmation              | why not                 |
#    And HS I successfully sign out
#
#    # Log into HE app to request attendance to college fair created in HS app above
#    Given HE I am logged in to Intersect HE as user type "administrator"
#    Then HE I request an appointment with "Int QA High School 4" for College Fair "Fair QA Test#03"
#    And HE I successfully sign out
#    Given HE I am logged in to Intersect HE as user type "publishing"
#    Then HE I request an appointment with "Int QA High School 4" for College Fair "Fair QA Test#03"
#    And HE I successfully sign out
#
#    # Log back into the HS app to accept and decline the attendance requests from above
#    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
#    Then HS I make sure the "Confirm" button works properly for college fair attendee requests for "Fair QA Test#03"
#    Then HS I make sure the "Decline" button works properly for college fair attendee requests for "Fair QA Test#03"
#    Then HS I cancel the "Fair QA Test#03" College Fair
#    And HS I successfully sign out

#  @MATCH-4450
#  Scenario Outline: As a HS user with access to RepVisits, I can access Agenda view of my visits
#    Given HS I am logged in to Intersect HS through Naviance with user type "<hsNavianceAdmin>"
#    Then HS I verify the user can access "Agenda" view
#    And HS I successfully sign out
#
#    Given HS I am logged in to Intersect HS through Naviance with user type "<hsNavianceMember>"
#    Then HS I verify the user cannot access Agenda view
#    And HS I successfully sign out
#
#    Given HS I am logged in to Intersect HS as user type "<hsNon-NavianceAdmin>"
#    Then HS I verify the user can access "Agenda" view
#    And HS I successfully sign out
#
#    Given HS I am logged in to Intersect HS as user type "<hsNon-NavianceMember>"
#    Then HS I verify the user cannot access Agenda view
#    And HS I successfully sign out
#
#    Examples:
#      |hsNavianceAdmin|hsNavianceMember|hsNon-NavianceAdmin|hsNon-NavianceMember|
#      |navianceAdmin  |navianceMember  |administrator      |member              |

#Complex to fix missunderstand logic to verify Export with a limmited account
#  @MATCH-1484 @MATCH-2124
#  Scenario Outline: A RepVisits user, I want to be able to export my visit data,
#  So that I can easily show and sort the data to students/parents/my boss.
##Verify unpaid HE users are blocked from exporting
#    Given HE I want to login to the HE app using "purpleheautomation+limited@gmail.com" as username and "Password!1" as password
#    Then HE I verify the unpaid users are blocked from exporting in Calendar page
#    Then HE I successfully sign out
#
#    #CREATE VISITS AND FAIRS
##precondition
#    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
#    And HS I set the Visit Availability of RepVisits Availability Settings to "All RepVisits Users"
#    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
#    Then HS I set the RepVisits Visits Confirmations option to "<Option>"
#    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
#    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
#    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
#    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
#    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
#    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
#
#    Then HS I set the date using "<StartDate>" and "<EndDate>"
#    And HS I verify the update button appears and I click update button
#    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
#    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>"
#    Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
#    And HS I successfully sign out
#
#    Given HE I want to login to the HE app using "purpleheautomation@gmail.com" as username and "Password!1" as password
#    And HE I search for "<School>" in RepVisits page
#    Then HE I select Visits to schedule the appointment for "<School>" using "<Date>" and "<heStartTime>"
#    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"
#    And HE I search for "<School>" in RepVisits page
#    Then HE I register for the "<College Fair Name>" college fair at "<School>"
#
##Exporting appointments
#    Then HE I verify the Export button is Enabled in Calendar page
#    Then HE I export the appointments for the following details "<StartDate>","<EndDate>"
#    Then HE I verify the downloaded Appointments csv file "RepVisitsEvents.csv" contains following details
#      |Appt Type/Fair Name|High School|Appt Date|Appt Time Zone|Appt Start|Appt Finish|Status|Address|City|State|Zip|Contact|Title|Email|Phone|
#    Then HE I delete the downloaded Appointments Cvs file "RepVisitsEvents.csv"
#    Then HE I successfully sign out
#
#    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
#    Then HS I verify the Export button is Enabled in Calendar page
#    Then HS I export the appointments for the following details "<StartDate>","<EndDate>"
#    Then HS I verify the downloaded Appointments csv file "RepVisitsEvents.csv" contains following details
#      |Appt Type/Fair Name|Number Attending|Appt Date|Appt Start|Appt Finish|Appt Location|Status|Rep Name|Rep Title|College|City|State|email|phone|
#    Then HS I delete the downloaded Appointments Cvs file "RepVisitsEvents.csv"
#    Then HS I remove the Time Slot created with "<StartDate>","<StartTime>" in Regular Weekly Hours Tab
#    And HS I successfully sign out
#
#    Examples:
#      |Day |StartTime|EndTime |NumVisits|StartDate|EndDate |hsEndTime    |Option                             |School                  |heStartTime |heTime  |College Fair Name     |Date|Start Time|End Time|RSVP Deadline|Cost|Max Number of Colleges|Number of Students Expected| ButtonToClick |
#      |7   |10:am    |11:25pm |3        |7        |14      |11:25pm      |Yes, accept all incoming requests. |Int Qa High School 4    |10:       |10:   |QAs Fairs tests       |14   |0900AM    |1000AM |7            |$25 |25                    |100                        | Save          |

#  @MATCH-2444
#  Scenario Outline: Verify that email is sent to HS users after cancelling a fair as an HE user
#    Given HS I want to login to the HS app using "purpleheautomation+admin@gmail.com" as username and "Password!1" as password
#    Then HS I add the email "<EMail>" in the primary contact in Notifications & Primary Contact page
#    Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
#    And HS I successfully sign out
#
#    Given HE I want to login to the HE app using "purpleheautomation@gmail.com" as username and "Password!1" as password
#    And HE I search for "<School>" in RepVisits page
#    Then HE I register for the "<College Fair Name>" college fair at "<School>"
#    Then HE I verify the calendar page using "<School>","<heCT>","<Date>" for Fairs
#    Then HE I remove the Fair appointment from the calendar
#    And HE I successfully sign out
#    Then HE I verify the Email Notification Message for "<School>" using "<Date>","<EmailTimeForFair>"
#      |Subject                                                             |To       |Messages |
#      |College fair registration cancelled for <School for Notification>   |<EMail>  |1        |
#
#    Examples:
#      |School for Notification|School        |EMail                           |College Fair Name     |Date|Start Time|End Time|RSVP Deadline|Cost|Max Number of Colleges|Number of Students Expected| ButtonToClick |heCT   |EmailTimeForFair|
#      |Homeconnection (WA)    |Homeconnection|purpleheautomation@gmail.com    |QAs Fairs tests       |4   |900AM    |1100AM  |2            |$25 |25                    |100                        | Save          |9AM   |9:00am.         |

#  @MATCH-2093 @MATCH-2828
#  Scenario Outline: As a HS user I want to see RepVisit notifications organized intuitively within my Notifications page ACTIVITY subtab
#  so I can efficiently find the updates I am looking for within RepVisits.
##FOR VISITS
##precondition
#    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
#    Then HS I set the RepVisits Visits Confirmations option to "<Option>"
#    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
#    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
#    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
##FOR CONFIRM
#    Then HS I set the date using "<StartDateforNewVisit>" and "<EndDate>"
#    And HS I verify the update button appears and I click update button
#    Then HS I add the new time slot with "<Day>","<RescheduleAvailabilityStartTime>","<RescheduleAvailabilityEndTime>" and "<NumVisits>"
#    Then HS I set the value for Reschedule the visit
#
#    Then HS I set the date using "<StartDate>" and "<EndDate>"
#    And HS I verify the update button appears and I click update button
#    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>"
##    And HS I successfully sign out
#
#    Given HE I want to login to the HE app using "purpleheautomation+HEAlpena@gmail.com" as username and "Password!1" as password
#    And HE I search for "<School>" in RepVisits page
#    Then HE I select Visits to schedule the appointment for "<School>" using "<Date>" and "<heStartTime>"
#    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"
##    Then HE I successfully sign out
#
#    Given HE I want to login to the HE app using "purplehsautomations+alpena@gmail.com" as username and "Password!1" as password
#    And HE I search for "<School>" in RepVisits page
#    Then HE I select Visits to schedule the appointment for "<School>" using "<Date>" and "<heStartTime>"
#    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"
##    Then HE I successfully sign out
#
#    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
#    Then HS I verify the Notification "<HEUser>","<institution>","<heStartTime>","<StartDate>" in the Request Notification Tab
#    And HS I select "Confirm" option for the Notification using "<HEUser>","<heStartTime>","<institution>"
##VERIFY ACTIVITY
#    And HS I select Activity in RepVisits to verify "confirmed" notification for "<HSuser>","<institution>","<activityDate>","<heStartTime>"
#
##FOR DECLINE
#    Then HS I verify the Notification "<HEUser>","<institution>","<heStartTime>","<StartDate>" in the Request Notification Tab
#    And HS I select "Decline" option for the Notification using "<HEUser>","<heStartTime>","<institution>"
#    Then HS I verify the Decline Pop-up in the Notification Tab "<HEUser>","<institution>","<heStartTime>","<StartDate>"
#    Then HS I select the "Yes, Decline" button by entering the message "QA Declined" for "<HEUser>"
##VERIFY ACTIVITY
#    And HS I select Activity in RepVisits to verify "declined" notification for "<HSuser>","<institution>","<activityDate>","<heStartTime>"
#
##FOR RESCHEDULE
#    Then HS I reschedule the visit for the following data "<institution>","<RescheduleStartTime>","<Date>"
#    Then HS I verify reschedule pop-up for the following data "<HEUser>","<institution>","<RescheduleStartTime>","<Date>"
##VERIFY RESCHEDULE PAGE FOR MATCH-2828
#    Then HS I verify the "Rescheduled visits aren't automatically confirmed" in reschedule page
#    Then HS I verify the university "<institution>" in reschedule page
#    Then HS I verify the date "<Date>" in reschedule page
#    Then HS I verify the time "<RescheduleStartTime>" in reschedule page
#
#    Then HS I reschedule a visit for the following details "<newVisitSTime>","<reason>","<StartDateforNewVisit>"
##VERIFY ACTIVITY
#    And HS I select Activity in RepVisits to verify "rescheduled" notification for "<HSuser>","<institution>","<StartDateforNewVisit>","<RescheduleAvailabilityStartTime>" after Rescheduled the visit
#
##FOR CANCEL
#    And HS I verify the calendar page in RepVisits using "<institution>","<StartTime>","<StartDateforNewVisit>" for cancel the Visit
#    Then HS I verify "Cancel This Visit" notification for "<HEUser>" using "<calendarST>","<institution>","<StartDateforNewVisit>","<StartTime>"
#    Then HS I select cancel the Visit
##VERIFY ACTIVITY
#    And HS I select Activity in RepVisits to verify "cancelled" notification for "<HSuser>","<institution>","<StartDateforNewVisit>","<heStartTime>" after cancelled the visit
#
##FOR MANUALLY CREATE A NEW VISIT APPOINTMENT
#    Then HS I manually add the contact to an appointment using "<StartDate>","<StartTime>","<FName>","<LName>","<EMail>","<Phone>","<Position>","<institution>"
##VERIFY ACTIVITY
#    And HS I select Activity in RepVisits to verify "scheduled" notification for "<HSuser>","<institution>","<activityDate>","<newVisitSTime>"
#
##Remove the time slot in Regular Weekly Hours Tab and Calendar
#    Then HS I remove the Time Slot created with "<StartDate>","<StartTime>" in Regular Weekly Hours Tab
#    And HS I verify the calendar page using "<institution>","<StartTime>","<Date>" for cancel the Visit
#    Then HS I verify the "Cancel This Visit" notification for "<HEUser>" using "<calendarST>","<institution>","<activityDate>","<StartTime>"
#    Then HS I select cancel the Visit
#
##FOR FAIRS
##FOR CONFIRM
#    Then HS I set the following data to On the College Fair page "<newFairName>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
##    And HS I successfully sign out
#
#    Given HE I want to login to the HE app using "purpleheautomation+HEAlpena@gmail.com" as username and "Password!1" as password
#    And HE I search for "<School>" in RepVisits page
#    Then HE I register for the "<College Fair Name>" college fair at "<School>"
##    And HE I successfully sign out
#
#    Given HE I want to login to the HE app using "purplehsautomations+alpena@gmail.com" as username and "Password!1" as password
#    And HE I search for "<School>" in RepVisits page
#    Then HE I register for the "<College Fair Name>" college fair at "<School>"
##    And HE I successfully sign out
#
#    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
#    Then HS I verify the Notification "<HEUser>","<institution>","<FairsSTime>","<Date>" in the Request Notification Tab for Fairs
#    And HS I select "Confirm" option for the Notification using "<HEUser>","<Date>","<FairsSTime>","<institution>" for Fairs
##VERIFY ACTIVITY
#    And HS I select Activity in RepVisits to verify "confirmed" notification for "<HSuser>","<institution>","<activityDate>","<AcitivityFairTime>" for Fairs
#
##FOR DECLINE
#    Then HS I verify the Notification "<HEUser>","<institution>","<FairsSTime>","<Date>" in the Request Notification Tab for Fairs
#    And HS I select "Decline" option for the Notification using "<HEUser>","<Date>","<FairsSTime>","<institution>" for Fairs
#    Then HS I verify the Decline Pop-up in the Notification Tab "<HEUser>","<institution>","<FairsSTime>","<Date>" for Fairs
#    Then HS I select the "Yes, Decline" button by entering the message "QA Declined" for "<HEUser>"
##VERIFY ACTIVITY
#    And HS I select Activity in RepVisits to verify "declined" notification for "<HSuser>","<institution>","<activityDate>","<AcitivityFairTime>" for Fairs
#
##FOR RESCHEDULE
#    Then HS I Click on the View Details button for the College Fair "<College Fair Name>"
#    Then HS I verify the edit fair popup "<College Fair Name>","<FairSTimeforReschedule>","<Date>"
#    And HS I reschedule the fair using "<newFairsSTime>"
##VERIFY ACTIVITY
#    And HS I select Activity in RepVisits to verify "rescheduled" notification for "<HSuser>","<institution>","<activityDate>","<newFairsSTime>" for Fairs
#
##FOR CANCEL
#    Then HS I Click on the View Details button for the College Fair Event "<College Fair Name>"
#    Then HS I select Edit button to cancel the college Fair "<College Fair Name>"
##VERIFY ACTIVITY
#    And HS I select Activity in RepVisits to verify "cancelled" notification for "<HSuser>","<institution>","<activityDate>","<newFairsSTime>" for Fairs
#    Then HS I verify the message "You currently have no notifications" is displayed in the ACTIVITY subtab
#    Then HS I verify the Paginate the ACTIVITY subtab via 25 entries with a "Show More" action to display the next 25 entries
#
#    Examples:
#      |activityDate |calendarST    |HEUser   |HSuser         |institution               |Day |StartTime|EndTime |NumVisits|StartDate|EndDate |hsEndTime    |Option                                               |School                  |heStartTime |heTime  |College Fair Name     |Date|Start Time|End Time|RSVP Deadline|Cost|Max Number of Colleges|Number of Students Expected|ButtonToClick |StartDateforNewVisit|newVisitSTime|RescheduleStartTime|RescheduleAvailabilityStartTime|RescheduleAvailabilityEndTime|FairsSTime|newFairsSTime|AcitivityFairTime|newFairName|reason|FName    |LName |EMail                           |Phone       |Position|FairSTimeforReschedule|
#      |14           |12:19AM       |purple   |School Manager |Alpena Community College  |14  |12:19am  |10:59pm |3        |14       |35      |10:59pm      |No, I want to manually review all incoming requests. |Standalone High School 6|12:19am         |12:     |Qa Fair for testng    |14  |1200AM    |0100AM  |5            |$25 |25                    |100                        |Save          |21                  |12:31am      |12:29 AM           |12:29am                        |10:59pm                      |12:00am   |12:00am      |12:00am          |fairNewqa  |by QA |purple   |HE    |purpleheautomation@gmail.com    |999999999999|QA      |12:00 AM              |
#
#
#
#  @MATCH-2833
#  Scenario: As an HS RepVisits user I want to see a message on the RepVisits Overview page that informs me I have no
#  upcoming appointments (visits OR fairs) for the next week so I can quickly know I don't have any colleges
#  visiting my high school over the next 7 days.
#    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
#    Then HS I navigate to the "Calendar" page in RepVisits
#    Then HS I cancel all events for the next 7 days
#    Then HS I navigate to the "Overview" page in RepVisits
#    Then HS I verify the RepVisits Overview page when no events are scheduled for the next 7 days

#  #Notifications Needs a deep Refactor
#  @MATCH-2168
#  Scenario Outline: As a Naviance RepVisits user who is looking at Notification entries in the RepVisits notifications page,
#  I want to see the City and State of each institution within each notification entry
#  so I can more easily identify the institution the notification entry is updating me about.
##Naviance
##precondition
#    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
#    And HS I set the Visit Availability of RepVisits Availability Settings to "All RepVisits Users"
#    Then HS I set the RepVisits Visits Confirmations option to "<Option>"
#    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
#    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
#    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
##Create a visits and Fairs
#    Then HS I set the date using "<StartDate>" and "<EndDate>"
#    And HS I verify the update button appears and I click update button
#    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>" with "<option>"
#    Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
#    And HS I successfully sign out
##Register Visits and Fairs
#    Then HE I am logged in to Intersect HE as user type "administrator"
#    And HE I search for "<School>" in RepVisits page
#    Then HE I select Visits to schedule the appointment for "<School>" using "<StartDate>" and "<heStartTime>"
#    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"
#
#    And HE I search for "<School>" in RepVisits page
#    Then HE I register for the "<College Fair Name>" college fair at "<School>"
#
#    Then HE I am logged in to Intersect HE as user type "publishing"
#    And HE I search for "<School>" in RepVisits page
#    Then HE I select Visits to schedule the appointment for "<School>" using "<StartDate>" and "<heStartTime>"
#    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"
#
#    And HE I search for "<School>" in RepVisits page
#    Then HE I register for the "<College Fair Name>" college fair at "<School>"
##verify the City and State displayed in the HE Request Notification tab
#    Then HE I verify the city and state "<cityAndStateofSchool>" are present in the underneath of School Name "<School>" in the Request Notification Tab
#    Then HE I verify the city and state "<cityAndStateofSchool>" are present in the underneath of School Name "<School>" in the Request Notification Tab for Fairs
#
##Verify the City and State displayed in the HS Request and Activity Notification Tab for Visits
#    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
#    Then HS I verify the Notification "<user>","<institution>","<heStartTime>","<StartDate>" in the Request Notification Tab
#    Then HS I verify the city and state "<cityAndStateofInstitution>" are present in the underneath of Institiution Name "<institution>" in the Request Notification Tab
#    And HS I select "Confirm" option for the Notification using "<user>","<heStartTime>","<institution>"
#
#    Then HS I verify the Notification "<user>","<institution>","<heStartTime>","<StartDate>" in the Request Notification Tab
#    And HS I select "Decline" option for the Notification using "<user>","<heStartTime>","<institution>"
#    Then HS I verify the Decline Pop-up in the Notification Tab "<user>","<institution>","<heStartTime>","<StartDate>"
#    Then HS I select the "Yes, Decline" button by entering the message "QA Declined" for "<user>"
#
#    Then HS I verify the city and state "<cityAndStateofInstitution>" are present in the underneath of Institiution Name "<institution>" in the Activity Tab
#    Then HS I remove the Time Slot created with "<StartDate>","<StartTime>" in Regular Weekly Hours Tab
#    And HS I successfully sign out
#
##Verify the City and State displayed in the HE Activity Notification Tab
#    Then HE I am logged in to Intersect HE as user type "administrator"
#    Then HE I verify the city and state "<cityAndStateofSchool>" are present in the underneath of School Name "<schoolForHE>" in the Activity Tab
#
##Verify the City and State displayed in the HS Request and Activity Notification Tab for Fairs
#    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
#    Then HS I verify the Notification "<user>","<institution>","<fairStartTime>","<Date>" in the Request Notification Tab for Fairs
#    Then HS I verify the city and state "<cityAndStateofInstitution>" are present in the underneath of Institiution Name "<institution>" in the Request Notification Tab for Fairs
#    And HS I select "Confirm" option for the Notification using "<user>","<Date>","<fairStartTime>","<institution>" for Fairs
#
#    Then HS I verify the Notification "<user>","<institution>","<fairStartTime>","<Date>" in the Request Notification Tab for Fairs
#    And HS I select "Decline" option for the Notification using "<user>","<Date>","<fairStartTime>","<institution>" for Fairs
#    Then HS I verify the Decline Pop-up in the Notification Tab "<user>","<institution>","<fairStartTime>","<Date>" for Fairs
#    Then HS I select the "Yes, Decline" button by entering the message "QA Declined" for "<user>"
#
#    Then HS I verify the city and state "<cityAndStateofInstitution>" are present in the underneath of Institiution Name "<institution>" in the Activity Tab for Fairs
#    And HS I successfully sign out
##Verify the City and State displayed in the HE Activity Notification Tab
#    Then HE I am logged in to Intersect HE as user type "administrator"
#    Then HE I verify the city and state "<cityAndStateofSchool>" are present in the underneath of School Name "<schoolForHE>" in the Activity Tab for Fairs
#
#    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
#    Then HS I Click on the View Details button for the College Fair Event "<College Fair Name>"
#    Then HS I select Edit button to cancel the college Fair "<College Fair Name>"
#    And HS I set the Visit Availability of RepVisits Availability Settings to "All RepVisits Users"
#    Then HS I set the RepVisits Visits Confirmations option to "<Option2>"
#    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
#    And HS I successfully sign out
#
#    Examples:
#      |user    |institution              |fairStartTime|Day|StartTime|EndTime |NumVisits|StartDate|EndDate |hsEndTime|Option                                              |Option2                           |School                  |heStartTime|heTime |College Fair Name          |Date|Start Time|End Time|RSVP Deadline|Cost|Max Number of Colleges|Number of Students Expected|ButtonToClick|cityAndStateofInstitution|cityAndStateofSchool |schoolForHE                     | option|
#      |PurpleHE|The University of Alabama|9:00am       |7  |10:25am  |11:25pm |3        |7        |42      |11:25pm  |No, I want to manually review all incoming requests.|Yes, accept all incoming requests.|Standalone High School 2|10:25am    |10:25am|QA Fairs for City and State|14  |0900AM    |1000AM  |12           |$25 |25                    |100                        |Save         |Tuscaloosa, AL           |Milford, Ohio        |Standalone High School 2 - Ohio |1      |
#
#  @MATCH-2168
#  Scenario Outline: As a Non-Naviance RepVisits user who is looking at Notification entries in the RepVisits notifications page,
#  I want to see the City and State of each institution within each notification entry
#  so I can more easily identify the institution the notification entry is updating me about.
##Non-Naviance
##precondition
#    Given HS I am logged in to Intersect HS as user type "admin"
#    And HS I set the Visit Availability of RepVisits Availability Settings to "All RepVisits Users"
#    Then HS I set the RepVisits Visits Confirmations option to "<Option>"
#    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
#    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
#    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
##Create a visits and Fairs
#    Then HS I set the date using "<StartDate>" and "<EndDate>"
#    And HS I verify the update button appears and I click update button
#    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>" with "<option>"
#    Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
#    And HS I successfully sign out
##Register Visits and Fairs
#    Then HE I am logged in to Intersect HE as user type "administrator"
#    And HE I search for "<Non-NavSchool>" in RepVisits page
#    Then HE I select Visits to schedule the appointment for "<Non-NavSchool>" using "<StartDate>" and "<heStartTime>"
#    And HE I verify the schedule pop_up for "<Non-NavSchool>" using "<heTime>" and "<hsEndTime>"
#
#    And HE I search for "<Non-NavSchool>" in RepVisits page
#    Then HE I register for the "<College Fair Name>" college fair at "<Non-NavSchool>"
#
#    Then HE I am logged in to Intersect HE as user type "publishing"
#    And HE I search for "<Non-NavSchool>" in RepVisits page
#    Then HE I select Visits to schedule the appointment for "<Non-NavSchool>" using "<StartDate>" and "<heStartTime>"
#    And HE I verify the schedule pop_up for "<Non-NavSchool>" using "<heTime>" and "<hsEndTime>"
#
#    And HE I search for "<Non-NavSchool>" in RepVisits page
#    Then HE I register for the "<College Fair Name>" college fair at "<Non-NavSchool>"
##verify the City and State displayed in the HE Request Notification tab
#    Then HE I verify the city and state "<cityAndStateofNon-NavianceSchool>" are present in the underneath of School Name "<Non-NavSchool>" in the Request Notification Tab
#    Then HE I verify the city and state "<cityAndStateofNon-NavianceSchool>" are present in the underneath of School Name "<Non-NavSchool>" in the Request Notification Tab for Fairs
#
##Verify the City and State displayed in the HS Request and Activity Notification Tab for Visits
#    Given HS I am logged in to Intersect HS as user type "admin"
#    Then HS I verify the Notification "<user>","<institution>","<heStartTime>","<StartDate>" in the Request Notification Tab
#    Then HS I verify the city and state "<cityAndStateofInstitution>" are present in the underneath of Institiution Name "<institution>" in the Request Notification Tab
#    And HS I select "Confirm" option for the Notification using "<user>","<heStartTime>","<institution>"
#
#    Then HS I verify the Notification "<user>","<institution>","<heStartTime>","<StartDate>" in the Request Notification Tab
#    And HS I select "Decline" option for the Notification using "<user>","<heStartTime>","<institution>"
#    Then HS I verify the Decline Pop-up in the Notification Tab "<user>","<institution>","<heStartTime>","<StartDate>"
#    Then HS I select the "Yes, Decline" button by entering the message "QA Declined" for "<user>"
#
#    Then HS I verify the city and state "<cityAndStateofInstitution>" are present in the underneath of Institiution Name "<institution>" in the Activity Tab
#    Then HS I remove the Time Slot created with "<StartDate>","<StartTime>" in Regular Weekly Hours Tab
#    And HS I successfully sign out
#
##Verify the City and State displayed in the HE Activity Notification Tab
#    Then HE I am logged in to Intersect HE as user type "administrator"
#    Then HE I verify the city and state "<cityAndStateofNon-NavianceSchool>" are present in the underneath of School Name "<Non-NavschoolForHE>" in the Activity Tab
#
##Verify the City and State displayed in the HS Request and Activity Notification Tab for Fairs
#    Given HS I am logged in to Intersect HS as user type "admin"
#    Then HS I verify the Notification "<user>","<institution>","<fairStartTime>","<Date>" in the Request Notification Tab for Fairs
#    Then HS I verify the city and state "<cityAndStateofInstitution>" are present in the underneath of Institiution Name "<institution>" in the Request Notification Tab for Fairs
#    And HS I select "Confirm" option for the Notification using "<user>","<Date>","<fairStartTime>","<institution>" for Fairs
#
#    Then HS I verify the Notification "<user>","<institution>","<fairStartTime>","<Date>" in the Request Notification Tab for Fairs
#    And HS I select "Decline" option for the Notification using "<user>","<Date>","<fairStartTime>","<institution>" for Fairs
#    Then HS I verify the Decline Pop-up in the Notification Tab "<user>","<institution>","<fairStartTime>","<Date>" for Fairs
#    Then HS I select the "Yes, Decline" button by entering the message "QA Declined" for "<user>"
#
#    Then HS I verify the city and state "<cityAndStateofInstitution>" are present in the underneath of Institiution Name "<institution>" in the Activity Tab for Fairs
#    And HS I successfully sign out
##Verify the City and State displayed in the HE Activity Notification Tab
#    Then HE I am logged in to Intersect HE as user type "administrator"
#    Then HE I verify the city and state "<cityAndStateofNon-NavianceSchool>" are present in the underneath of School Name "<Non-NavschoolForHE>" in the Activity Tab for Fairs
#
#    Given HS I am logged in to Intersect HS as user type "admin"
#    Then HS I Click on the View Details button for the College Fair Event "<College Fair Name>"
#    Then HS I select Edit button to cancel the college Fair "<College Fair Name>"
#    And HS I successfully sign out
#
#    Examples:
#      |user    |institution              |fairStartTime|Day|StartTime|EndTime |NumVisits|StartDate|EndDate |hsEndTime|Option                                              |Option2                           |heStartTime|heTime |College Fair Name          |Date|Start Time|End Time|RSVP Deadline|Cost|Max Number of Colleges|Number of Students Expected|ButtonToClick|Non-NavSchool |cityAndStateofInstitution|cityAndStateofNon-NavianceSchool|Non-NavschoolForHE         |option|
#      |PurpleHE|The University of Alabama|9:00am       |14 |9:00am  |11:25pm |3        |14       |42      |11:25pm  |No, I want to manually review all incoming requests.|Yes, accept all incoming requests. |9:        |9:    |QA Fairs for City and State|14  |0900AM    |1000AM  |12           |$25 |25                    |100                        |Save         |Homeconnection|Tuscaloosa, AL           |Oak Harbor, Washington          |Homeconnection - Washington|1        |
