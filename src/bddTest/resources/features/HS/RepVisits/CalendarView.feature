@HS @HS1
Feature:  HS - RepVisits - CalendarView - As an HS user, I should be able to view, manage and export my appointments from Calendar view

  @MATCH-1756
  Scenario:As an HS Community member,I need to view a calendar of my appointments
  so that I can easily see what my day/week/month schedule looks like.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    And HS I verify the calendar view in RepVisits

  @MATCH-2728
  Scenario Outline: As an HS RepVisists user who I click on a College Fair in the calendar
  I want to be able to edit fairs in the summary drawer
  So that calendar appointments all have a consistent interface

    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I clean the visits created
    Then HS I clean the college fairs created
    Then HS I set the data to create the College Fair "<College Fair Name>","<Date>","<Start Time>","<End Time>","<RSVP Deadline>","<Cost>","<Max Number of Colleges>","<Number of Students Expected>","<ButtonToClick>"
    Then HS I Click on the "Close" button in the success page of the college fair
    And HS I verify the fairs are clickable "<College Fair Name>","<VerifyDateEdit>","<verifyStartTime>","<verifyEndTime>","<VerifyRSVPDateEdit>","<Cost>","<MaxNumberofColleges>","<NumberofStudentsExpected>"
    Then HS I clean the college fairs created
    Examples:
      |College Fair Name |Date  |Start Time|End Time|RSVP Deadline |Cost|Max Number of Colleges|Number of Students Expected| ButtonToClick |Cost|MaxNumberofColleges|NumberofStudentsExpected|ButtonToClick|VerifyDateEdit |VerifyRSVPDateEdit |verifyStartTime|verifyEndTime|
      |Fair#778          |3     |0900AM    |1000AM  |2             |$25 |25                    |100                        | Save          |$25 |25                 |100                     |Save         |3              |2                  |09:00          |10:00        |


  @MATCH-2728
  Scenario: Verify ability to view and edit Fair appointments from Calendar view
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I clean the visits created
    Then HS I clean the college fairs created
    Then HS I create a dynamic College Fair with the following data
      | College Fair Name                                         | MATCH-2082 Fair         |
      | Automatically Confirm Incoming Requestions From Colleges? | no                      |
      | Cost                                                      | 10                      |
      | Start Time                                                | 0810AM                  |
      | Date                                                      | 3                       |
      | RSVP Deadline                                             | 2                       |
      | End Time                                                  | 0820AM                  |
      | Max Number of Colleges                                    | 10                      |
      | Number of Students Expected                               | 10                      |
      | Instructions for College Representatives                  | Submit request by Email |
      | Email Message to Colleges After Confirmation              | why not                 |
    And HS I successfully sign out
  # Log into HE app and verify that the fair is visible
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify that the previously created fair appears for "Int QA High School 4"
  # Log into HS app and unpublish the previous College Fair
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I Click the View Details button for the College Fair Event for "PreviouslySetFair"
    #And HS I click on Edit button to edit fair
    Then HS I edit a dynamic College Fair with the following data
      | College Fair Name                                         | MATCH-2082 Fair Edited  |
      | Automatically Confirm Incoming Requestions From Colleges? | no                      |
      | Cost                                                      | 11                     |
      | Start Time                                                | 0815AM                  |
      | Date                                                      |90                      |
      | RSVP Deadline                                             |3                      |
      | End Time                                                  | 0825AM                  |
      | Max Number of Colleges                                    | 11                     |
      | Number of Students Expected                               | 11                      |
      | Instructions for College Representatives                  | Submit request by Email |
      | Email Message to Colleges After Confirmation              | why not                 |
    Then HS I verify edit a dynamic College Fair with the following data
      | College Fair Name                                         | MATCH-2082 Fair Edited  |
      | Automatically Confirm Incoming Requestions From Colleges? | no                      |
      | Cost                                                      | 11                     |
      | Start Time                                                | 0815AM                  |
      | Date                                                      |90                       |
      | RSVP Deadline                                             |3                       |
      | End Time                                                  | 0825AM                  |
      | Max Number of Colleges                                    | 11                     |
      | Number of Students Expected                               | 11                      |
      | Instructions for College Representatives                  | Submit request by Email |
      | Email Message to Colleges After Confirmation              | why not                 |
    Then HS I cancel college fair created "MATCH-2082 Fair Edited"

  @MATCH-1765
  Scenario Outline: As a high school user, I want to be able to manually add appointments including custom contact info/custom time slots,
  so that I can create appointments that are custom to my high school's needs.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Given HS I clean the visits created
  #verify AddVisit Button is Disabled in Calendar page
    Then HS I select the "Fairs" option on the welcome page in the RepVisits setup wizard
    Then HS I navigate to the calendar page to verify AddVisit Button is "Disabled"
  #verify AddVisit Button is Enabled in Calendar page
    Then HS I select the "Visits and Fairs" option on the welcome page in the RepVisits setup wizard
    Then HS I navigate to the calendar page to verify AddVisit Button is "Enabled"

#verify the UI of the visit schedule popup
    And HS I verify the calendar page is displayed
    Then HS I verify the close drawer is displaying in the visit Schedule popup
    Then HS I verify the link "Want a custom time? Add it manually" is displaying in the visit Schedule popup
    Then HS I verify the link "Go back to select from list" is displaying in the visit Schedule popup
    Then HS I verify the text "Start Typing ..." is present in the Attendee text box
    Then HS I verify the link "Not in the list? Add them manually" is displaying in the visit Schedule popup
    Then HS I verify the link "Go back to list" is displayed in the visit Schedule popup
    Then HS I verify the text box is displaying in the visit Schedule popup for "add-rep-first-name","add-rep-last-name","add-rep-email","add-rep-phone","add-rep-position"
    Then HS I verify the "Institution" only required field in the visit Schedule popup
    Then HS I verify the "If their school isn't in the list, you can simply type it in" text is present under "add-rep-institution" in the visit Schedule popup
    Then HS I verify the button "add visit" is displaying in the visit Schedule popup

#Manually adding appointment based on pre-determined time slots:
    Then HS I set the date using "<StartDate>" and "<EndDate>"
    And HS I verify the update button appears and I click update button
    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>" with "<option>"
    Then HS I set the RepVisits Visits Confirmations option to "<Option>"

    Then HS I add the appointment based on pre-determined time slots using "<StartDate>","<StartTime>","<Attendees>","<visitLocation>"

  #Manually adding a contact to an appointment:
    Then HS I manually add the contact to an appointment using "<StartDate>","<StartTime>","<FName>","<LName>","<EMail>","<Phone>","<Position>","<institution>"
    Then HS verify the created Appointment is present in the calendar "<StartDate>","<StartTime>","<institution>"

  #Adding a manual appointment based on a custom time:
    Then HS I schedule a new visit for "<Date>","<newVisitSTime>","<newVisitETime>","<Attendees>","<visitLocation>"

  #Confirmation message:
    Then HS I verify the confirmation message "Appointment Scheduled!,We have emailed the college with the appointment details." for the created visit
    Then HS I remove the Time Slot created with "<StartDate>","<StartTime>" in Regular Weekly Hours Tab

    Examples:
      |Date |StartTime|EndTime |NumVisits|StartDate |EndDate |Option                                               |newVisitSTime|newVisitETime|visitLocation|Attendees           |institution               |Day |FName    |LName |EMail                           |Phone       |Position|option|
      |85   |10:09am  |12:25pm |3        |29      |42      |No, I want to manually review all incoming requests. |11:02am      |10:58pm      |Cbba         |PurpleHE Automation |The University of Alabama |29 |Intersect|QA    |purpleheautomation@gmail.com    |999999999999|QA      |1      |

  @MATCH-2391
  Scenario: As a HS user, I should not be able to add visits in the past
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
    Given HS I clean the visits created
    Then HS I set the date using "3" and "42"
    When HS I navigate to the "Calendar" page in RepVisits
    And HS I click on button Add Visit
    Given HS I create a visit "3" days ahead from now with the following details
      | Start Time | 09:40am |
      | End Time   | 10:39am |
      | Representative | PurpleHE Publishing|
    Then HS verify pills are not available for the past dates in schedule new visit page
    Then HS verify the past dates are disabled in the select custom date section
    Then HS verify pills are not available for the past dates in Re-schedule visit page
    Then HS verify the past dates are disabled in the select custom date section for Re-schedule visit page
    And HS I open the visit with generated time in the Calendar
    And HS I clean the visits created

  @MATCH-2061 @MATCH3954
  Scenario: : As a HS user, I should be able to add internal notes to my visits
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
    Given HS I clean the visits created
    Then HS I set the date using "-15" and "42"
    And HS I am Navigating to Calendar Home Screen
    And HS I click on button Add Visit
    And HS I select custom time manually
    And HS I select a date "28" days ahead from now
    And HS I select Visit StartTime "5:40am" and End Time "6:00am"
    And HS I select representative from drop down "PurpleHE Publishing"
    And HS I Enter Internal Notes "Visit Notes Added for Automation Purpose"
    And HS I click on Add Visit button
    And HS I click on Agenda on Calendar
    And Hs I open the date picker on Agenda View
    And HS I select a date "0" days ahead from now from the standard date picker
    And HS I click on Day on Calendar
    And HS I click on Visit with "The University of Alabama" from "5:40 AM" to "6:00 AM" on Day Calendar
    And HS I verify Internal Notes on Visit Details screen "Visit Notes Added for Automation Purpose"
    And HS I Cancel visit to create again add Notes to Cancel "canceled for automation"

  @MATCH-4472
  Scenario: As an RepVisits RepVisits HS admin user,I want the ability to more easily access the
  "Share calendars" link from the Calendars>Your Calendars section of RV, so that I don't miss seeing that as a
  feature of RV and can actually leverage that functionality.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HE I verify that Share Calendars Link is displayed in Calendar page
    Then HE I verify that Share your calendar modal is opened when clicking the Share Calendars Link

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

    Examples:
      |hsNavianceAdmin|hsNavianceMember|hsNon-NavianceAdmin|hsNon-NavianceMember|
      |navianceAdmin  |navianceMember1 |administrator      |member              |

  @MATCH-1484 @ignore
  Scenario Outline: A RepVisits user, I want to be able to export my visit data,
  So that I can easily show and sort the data to students/parents/my boss.
#Verify unpaid HE users are blocked from exporting
    Then HE I am logged in to Intersect HE as user type "limited"
    Then HE I verify the unpaid users are blocked from exporting in Calendar page
    Then HE I successfully sign out
#CREATE VISITS AND FAIRS
#precondition
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    And HS I set the Visit Availability of RepVisits Availability Settings to "All RepVisits Users"
    Then HS I set the RepVisits Visits Confirmations option to "<Option>"
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."

    Then HS I set the date using "<StartDate>" and "<EndDate>"
    And HS I verify the update button appears and I click update button
    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>" with "<option>"
    Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
    And HS I successfully sign out

    Then HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<Date>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"
    And HE I search for "<School>" in RepVisits page
    Then HE I register for the "<College Fair Name>" college fair at "<School>"
#Exporting appointments
    Then HE I verify the Export button is Enabled in Calendar page
    Then HE I export the appointments for the following details "<StartDate>","<EndDate>"
    Then HE I verify the downloaded Appointments csv file "RepVisitsEvents.csv" contains following details
      |Appt Type/Fair Name|High School|Appt Date|Appt Time Zone|Appt Start|Appt Finish|Status|Address|City|State|Zip|Contact|Title|Email|Phone|
    Then HE I delete the downloaded Appointments Cvs file "RepVisitsEvents.csv"
    Then HE I successfully sign out

    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I verify the Export button is Enabled in Calendar page
    Then HS I export the appointments for the following details "<StartDate>","<EndDate>"
    Then HS I verify the downloaded Appointments csv file "RepVisitsEvents.csv" contains following details
      |Appt Type/Fair Name|Number Attending|Appt Date|Appt Start|Appt Finish|Appt Location|Status|Rep Name|Rep Title|College|City|State|email|phone|
    Then HS I delete the downloaded Appointments Cvs file "RepVisitsEvents.csv"
    Then HS I remove the Time Slot created with "<StartDate>","<StartTime>" in Regular Weekly Hours Tab
    And HS I successfully sign out

    Examples:
      |Day |StartTime|EndTime |NumVisits|StartDate|EndDate |hsEndTime    |Option                             |School                  |heStartTime |heTime  |College Fair Name     |Date|Start Time|End Time|RSVP Deadline|Cost|Max Number of Colleges|Number of Students Expected| ButtonToClick |option|
      |14  |10:20am  |11:25pm |3        |14       |21      |11:25pm      |Yes, accept all incoming requests. |Int Qa High School 4    |10:20am     |10:20am |QAs Fairs tests       |14  |0900AM    |1000AM  |7            |$25 |25                    |100                        | Save          |1     |


