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
