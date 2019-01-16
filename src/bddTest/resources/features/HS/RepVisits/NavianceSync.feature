@HS @HS1
Feature: HS - RepVisits - NavianceSync - As an HS user, I want to be able to access the features of the Sync Opt in features.
  @MATCH-3237
  Scenario: As an RepVisits HS user that had previously connected my RepVisits with my Naviance account to publish
  events into Naviance, I want the ability to opt out/disconnect my RepVisits events from publishing to Naviance,
  so that I can manage events separately in Naviance and RepVisits.
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
#Precondition
    Then HS I complete the setupWizard
    Then HS I clean the visits for particular Month "14"
    Then HS I create a new visit to verify the details in naviance with "14","10:30am","12:30pm","2","PurpleHE Automation" and "Cbba"
    Then HS I navigate to naviance settings page
    And HS I click on Disconnect RepVisits from Naviance button
    And HS I verify the Cancel on the disconnect confirmation popup
    And HS I click on Disconnect RepVisits from Naviance button
    And HS I verify the Yes on the disconnect confirmation popup with "14","10:30am","12:30pm","2","PurpleHE Automation","The University of Alabama" and "Cbba"
    Then HS I verify and select an appointment in calendar page using "The University of Alabama","10:30am","14","Scheduled"
    Then HS I remove the appointment from the calendar
    Then HS I remove the Time Slot created with "14","10:30am" in Regular Weekly Hours Tab

  @MATCH-4895
  Scenario: When a Naviance High School has their settings set to:
  "Visits Confirmations" set to "No, I want to manually review all incoming requests."
  Naviance Sync settings set to "Automatically publish confirmed visits." OR "Manually choose which visits to publish"
  and a College Rep reschedules a visit after the HS previously confirmed the visit (resulting in it previously syncing to Naviance)
  They will not get synced back into Naviance with the updated date/time..
    #Setup environment
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone6"
    Then HS I complete the setupWizard
    Then HS I clean the visits for particular Month "21"
    Then HS I clean the college fairs created
    Then HS I navigate to naviance settings page
    Then HS I set the date using "21" and "28"
    Then HS I add the new time slot with "21","11:30am","12:40pm" and "2" with "1"
    Then HS I set the value for Reschedule the visit
    Then HS I add the new time slot with "21","10:31am","12:40pm" and "2" with "1"
    #Configure
    And HS I go to the Naviance Settings to select the option "Automatically publish confirmed visits."
    Then HS I set the RepVisits Visits Confirmations option to "No, I want to manually review all incoming requests."

    #Request and verifications
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "Standalone High School 6" in RepVisits page
    Then HE I select Visits to schedule the appointment for "Standalone High School 6" using "21" and "10:31am"
    And HE I verify the schedule pop_up for "Standalone High School 6" using "10:31am" and "12:40pm"

    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone6"
    Then HS I verify the Notification "PurpleHE","The University of Alabama","10:31am","21" in the Request Notification Tab
    And HS I select "Confirm" option for the Notification using "PurpleHE","10:31am","The University of Alabama"

    And HS I verify the visit in Naviance with "PurpleHE Automation","The University of Alabama","10:31 AM	"

    #Reschedule visit and verifications
    Then HS I reschedule the visit for the following data "The University of Alabama","10:31AM","21" in calendar
    Then HS I verify reschedule pop-up for the following data "PurpleHE","The University of Alabama","10:31AM","21"
    Then HS I reschedule a visit for the following details "11:51AM","Test","21"

    And HS I verify the reschedule in Naviance with "PurpleHE Automation","The University of Alabama","11:51 AM"

    #Cancel visit and verifications
    Then HS I verify and select an appointment in calendar page using "The University of Alabama","11:30am","21","ReScheduled"
    Then HS I remove the appointment from the calendar
    And HS I verify the cancel in Naviance with "PurpleHE Automation","The University of Alabama","11:51 AM"

    #Clean environment
    Then HS I remove the Time Slot created with "21","11:31am" in Regular Weekly Hours Tab


  @MATCH-4071
  Scenario: A persistent message appears in RepVisits (i.e. no other locations in Intersect such as Counselor Community,
  etc) alerting them that they did not complete the Naviance Settings wizard in the aforementioned scenario indicating,
  "Naviance Sync Incomplete Looks like you didn't finish connecting RepVisits to Naviance. Please select an option below
  to proceed." - specs per mock and related assets


  @MATCH-4071 @MATCH-4205 @MATCH3955
  Scenario: A persistent message appears in RepVisits (i.e. no other locations in Intersect such as Counselor Community,
  etc) alerting them that they did not complete the Naviance Settings wizard in the aforementioned scenario indicating,
  "Naviance Sync Incomplete Looks like you didn't finish connecting RepVisits to Naviance. Please select an option below
  to proceed." - specs per mock and related assets. When in this partial saved state, message disappears when:
  2.1 The user selects the "no" radio option and clicks "next" on the wizard (i.e. performs a disconnect per --MATCH-3955--)
  QA/UAT: The timing for which this is disappearing is incorrect as with current implementation it's only disappearing
  when you complete the entire wizard with this tickets scenario and not when you complete the Naviance Settings section
  of the wizard. Instead of correcting with this ticket, will adjust the timing of this alert disappearing with MATCH-4205
  2.2PASSED the user selects the "yes" radio  option and completes the full Naviance Settings wizard
    #Precondition
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone3"
    Then HS I complete the setupWizard
    Then HS I navigate to naviance settings page
    Then HS I click on Disconnect RepVisits from Naviance button
    Then HS I navigate to the Naviance Settings page through the setup Wizard
    Then HS I select "Yes" to connect RepVisits with Naviance in the Wizard
    Then HS I navigate to the Naviance Settings page through the setup Wizard
    Then HS I complete the setupWizard

    #Verify with No option
    Then HS I navigate to the Naviance Settings page through the setup Wizard
    Then HS I select "No" to connect RepVisits with Naviance in the Wizard
    Then HS I verify the toast "not" displayed when setup Wizard is incomplete
    #Verify with Yes option
    Then HS I navigate to the Naviance Settings page through the setup Wizard
    Then HS I select "Yes" to connect RepVisits with Naviance in the Wizard
    Then HS I verify the toast "is" displayed when setup Wizard is incomplete
    Then HS I select "Yes" to connect RepVisits with Naviance in the Wizard
    Then HS I complete the setupWizard

  @MATCH-2881
  Scenario Outline: As a Repvisits Naviance user, i cannot view the notification while Rescheduling an appointment that has already been pushed to Naviance
#Rescheduling an appointment that has already been pushed to Naviance
#verify settings(select Manually choose which visits to publish)
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone7"
    Then HS I clean the visits for particular Month "<Date>"
    And HS I go to the Naviance Settings to select the option "Manually choose which visits to publish. (If any)"
#create Visit for Reschedule
    Then HS I set the date using "<StartDate>" and "<EndDate>"
    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>" with "<option>"
    Then HS I set the value for Reschedule the visit
    Then HS I set the RepVisits Visits Confirmations option to "<Option>"
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
#create new visit
    Then HS I set the date using "<StartDate>" and "<EndDate>"
    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>" with "<option>"
    And HS I successfully sign out
#schedule Visit
    Then HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<Date>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"
#verify Naviance Tab
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone7"
    Then HS I verify the Notification is "displaying" in the Naviance Sync Tab for the following details "<University>","<heTime>","<Date>"
#verify settings(select Automatically choose which visits to publish)
    And HS I go to the Naviance Settings to select the option "Automatically publish confirmed visits."
#verify Naviance Tab
    Then HS I verify the Notification is "not displaying" in the Naviance Sync Tab for the following details "<University>","<heTime>","<Date>"
#verify settings(select Manually choose which visits to publish)
    And HS I go to the Naviance Settings to select the option "Manually choose which visits to publish. (If any)"
#reschedule visit
    Then HS I reschedule the visit for the following data "<University>","<heCalendarTime>","<Date>" in calendar
    Then HS I verify reschedule pop-up for the following data "<User>","<University>","<heCalendarTime>","<Date>"
    Then HS I reschedule a visit for the following details "<heTimefornewVisit>","<reason>","<Date>"
#verify Naviance
    Then HS I verify the Notification is "not displaying" in the Naviance Sync Tab for the following details "<University>","<heTime>","<Date>"
#Remove the time slot in Regular Weekly Hours Tab
    Then HS I remove the Time Slot created with "<StartDate>","<heStartTime>" in Regular Weekly Hours Tab
#Remove the appointment from Calendar
    And HS I select calendar in RepVisits
    Then HS I verify and select an appointment in calendar page using "<University>","<heCalendarTime>","<Date>","ReScheduled"
    Then HS I remove the appointment from the calendar

#Rescheduling an appointment that has not been pushed to Naviance
#verify settings(select Manually choose which visits to publish)
    And HS I go to the Naviance Settings to select the option "Manually choose which visits to publish. (If any)"
#create Visit for Reschedule
    Then HS I set the date using "<StartDate>" and "<EndDate>"
    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>" with "<option>"
    Then HS I set the value for Reschedule the visit
#create new visit
    Then HS I set the date using "<StartDate>" and "<EndDate>"
    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>" with "<option>"
    And HS I successfully sign out
#schedule Visit
    Then HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<Date>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"
#verify Naviance Tab
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone7"
    Then HS I verify the Notification is "displaying" in the Naviance Sync Tab for the following details "<University>","<heTime>","<Date>"
#reschedule visit
    Then HS I reschedule the visit for the following data "<University>","<heCalendarTime>","<Date>" in calendar
    Then HS I verify reschedule pop-up for the following data "<User>","<University>","<heCalendarTime>","<Date>"
    Then HS I reschedule a visit for the following details "<heTimefornewVisit>","<reason>","<Date>"
#verify Naviance
    Then HS I verify the Rescheduled Notification is "displaying" in the Naviance Sync Tab for an appointment that has not been pushed to Naviance using "<University>","<StartTimefornewVisit>","<Date>"
#Remove the time slot in Regular Weekly Hours Tab
    Then HS I remove the Time Slot created with "<StartDate>","<StartTimefornewVisit>" in Regular Weekly Hours Tab
#Remove the appointment from Calendar
    And HS I select calendar in RepVisits
    Then HS I verify and select an appointment in calendar page using "<University>","<heCalendarTime>","<Date>","ReScheduled"
    Then HS I remove the appointment from the calendar
    And HS I successfully sign out
    And HE I successfully sign out

    Examples:
      |StartTime|EndTime |NumVisits|Option                            |hsEndTime|School                   |University                |heStartTime   |heTime   |Day|Date|StartDate|EndDate|StartTimefornewVisit|User     |reason   |heTimefornewVisit|heCalendarTime|option|
      |10:34am  |12:59pm |3        |Yes, accept all incoming requests.|12:59pm  |Standalone High School 7 |The University of Alabama |10:34am       |10:34am  |21 |21  |21       |35     |10:28am             |PurpleHE |by QA    |10:28am          |10:33AM       |1     |

  @MATCH-1639
  Scenario: As a high school RepVisits user, I want Naviance to send visit information to Naviance when I create or modify a visit
  So that Naviance accurately reflects my visits.
  #pre-condition
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    And HS I go to the Naviance Settings and submit the following details
      |option                 |Automatically publish confirmed visits.|
      |NumVisits              |3                                      |
      |Location               |Cbba                                   |
      |studentsCount          |30                                     |
      |DeadlineValue          |2                                      |
      |Notes                  |sss-testing from HS                    |
      |deadlineOption         |hours                                  |
  #create new visit for Reschedule
    Then HS I set a date using "21" and "42"
    Then HS I clear the time slot for the particular day "21" in Regular Weekly Hours Tab
    Then HS I add the new time slot with "21","10:34am","12:33pm" and "3" with "1"
    Then HS I set the value for Reschedule the visit
  #create Visit
    Then HS I set a date using "14" and "35"
    And HS I verify the update button appears and I click update button
    Then HS I add the new time slot with "14","10:58am","12:59pm" and "3" with "1"
    Then HS I set the RepVisits Visits Confirmations option to "Yes, accept all incoming requests."
    And HS I set the Visit Availability of RepVisits Availability Settings to "All RepVisits Users"
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
    And HS I successfully sign out
  #schedule Visit
    Then HE I am logged in to Intersect HE as user type "alpenaAdmin"
    And HE I search for "Int Qa High School 4" in RepVisits page
    Then HE I select Visits to schedule the appointment for "Int Qa High School 4" using "14" and "10:58am"
    And HE I verify the schedule pop_up for "Int Qa High School 4" using "10:58am" and "12:59pm"
  #verify Newly created visit is present In Naviance
    Given HS I go to the Naviance Page with user type "navianceAdmin"
    Then HS I select "view" In Naviance college visit Page using "14","10:58 AM"
    And HS I verify the naviance college visit page using the following details "14"
      |Alpena Community College|N/A|Purple HE|Cbba|sss-testing from HS|30|no later than 2 hours prior|N/A|
    And HS I successfully sign out from the Naviance
  #reschedule visit
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I reschedule the visit for the following data "Alpena Community College","10:58AM","14" in calendar
    Then HS I verify reschedule pop-up for the following data "Purple HE","Alpena Community College","10:58AM","14"
    Then HS I reschedule a visit for the following details "10:34am","by QA","21"
    Then HS I remove the Time Slot created with "14","10:58am" in Regular Weekly Hours Tab
    And HS I successfully sign out
  #verify updated visit Details In Naviance
    Given HS I go to the Naviance Page with user type "navianceAdmin"
    Then HS I select "view" In Naviance college visit Page After Reschedule the visit using "21","10:34 AM"
    And HS I verify the naviance college visit page using the following details after Reschedule the Visits "21"
      |Alpena Community College|N/A|Purple HE|Cbba|sss-testing from HS|30|no later than 2 hours prior|N/A|
    And HS I successfully sign out from the Naviance
  #Remove the appointment from Calendar
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    And HS I select calendar in RepVisits
    Then HS I verify the Appointment in calendar page using "Alpena Community College","10:58AM","14","ReScheduled"
    Then HS I remove the appointment from the calendar
    And HS I successfully sign out



