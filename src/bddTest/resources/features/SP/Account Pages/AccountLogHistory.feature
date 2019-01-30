@SP
Feature: SP - Account Pages - AccountLogHistory - View Account Audit Log History
         As a Hobsons Staff Administrator or Support user I need to be able to view and filter by date an audit log of all other
         Hobsons Staff activity per institutional account in the admin page for system security, auditing, and troubleshooting.

  Scenario: As a Hobsons Sales Ops user I cannot view an Institution's Log History
    Given SP I am logged in to the Admin page as a Sales Ops user
    When SP I select "The University of Alabama" from the institution dashboard
    And SP I am able to view the individual account page
    Then SP I do not have access to View Log History

  Scenario: As a Hobsons Support user I can view an Institution's Log History
    Given SP I am logged in to the Admin page as a Support user
    When SP I select "The University of Alabama" from the institution dashboard
    And SP I am able to view the individual account page
    Then SP I do have access to View Log History

  Scenario: As a Hobsons Admin user I can view an Institution's Log History
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I go to the Log History for "Bowling Green State University-Main Campus" from the institution dashboard

@MATCH-3007
  Scenario: As a Hobsons Super Admin user I can view an Institution's Log History
    Given SP I am logged in to the Admin page as a Super Admin user
    Then SP I go to the Log History for "Bowling Green State University-Main Campus" from the institution dashboard

  @MATCH-1682 @MATCH-2124 @MATCH-5014
  Scenario Outline: As a Support App I need to add log entries to HE Accounts View Log History page when Support users are using the Login As feature
                    so the appropriate information is available for auditing.
  #precondition
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone6"
    And HS I set the Visit Availability of RepVisits Availability Settings to "All RepVisits Users"
    Then HS I set the RepVisits Visits Confirmations option to "<Option1>"
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
    Then HS I verify the success Message "Great!You've updated your settings." in Availability Settings page
  #visit
    Then HS I set the date using "<StartDate>" and "<EndDate>"
    Then HS I verify the success Message "Great!You've updated your settings." in Availability Settings page
    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>" with "<option>"
  #Fairs
    Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
    And HS I successfully sign out

    Given SP I am logged in to the Admin page as an Admin user
    Then SP I go to the users list for "<institution>" from the institution dashboard
    And SP I "Login As" the user account for "<user>"
#verify impersonator banner in HE
    Then SP I verify the "You're currently logged in as <profileName> from <institution>. Changes you make will reflect in their account." message in the homepage
    Then HE I post a "TestQA" Message in the homepage
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<Date>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"

    And HE I search for "<School>" in RepVisits page
    Then HE I register for the "<College Fair Name>" college fair at "<School>"
    Then HE I switch to the Support App
  #verify Log history
    Then SP I go to the Log History for "<institution>" from the institution dashboard
    Then SP I verify the visit details are present in the Log History page using "<supportUser>","<profileName>","Today","<StartDate>"
    Then SP I verify the Fairs details are present in the Log History page using "<supportUser>","<profileName>","Today"
    Then SP I verify the Logged in details are present in the Log History page using "<supportUser>","<profileName>","Today"
    Then SP I verify the created post details are present in the Log History page using "<profileName>","Today"
    And SP I successfully sign out

  #postcondition
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone6"
    Then HS I remove the Time Slot created with "<StartDate>","<StartTime>" in Regular Weekly Hours Tab
    And HS I set the Visit Availability of RepVisits Availability Settings to "All RepVisits Users"
    Then HS I set the RepVisits Visits Confirmations option to "<Option2>"
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
    Then HS I verify the success Message "Great!You've updated your settings." in Availability Settings page

    Examples:
      |user                                   |supportUser         |profileName |institution              |Day |StartTime|EndTime |NumVisits|StartDate|EndDate |hsEndTime |Option1                                              |Option2                           |School                  |heStartTime |heTime  |College Fair Name     |Date|Start Time|End Time|RSVP Deadline|Cost|Max Number of Colleges|Number of Students Expected| ButtonToClick |option|
      |purpleheautomation+LogHistory@gmail.com|Match Support UI QA4|purple he   |Alpena Community College |7   |11:20am  |12:25pm |3        |7        |42      |12:25pm   |No, I want to manually review all incoming requests. |Yes, accept all incoming requests.|Standalone High School 6|11:20am     |11:20am |QAs Fairs tests       |7   |0900AM    |1000AM  |5            |$25 |25                    |100                        | Save          |1     |

  @MATCH-4305
  Scenario: We should add a message into the audit log history whenever we update a user in community.
    Given HE I am logged in to Intersect HE as user type "profiletest"
    When HE I access the Account Settings page
    And HE I add a random suffix to the First Name value
    And HE I save the changes

    Given SP I am logged in to the Admin page as an Admin user
    When SP I go to the Log History for "The University of Alabama" from the institution dashboard
    Then SP I verify the user update details are present in the Log History page using "Mark","Today"

    #Set the user name back to the original value
    When HE I am logged in to Intersect HE as user type "profiletest"
    And HE I access the Account Settings page
    And HE I set the First Name field to the original value "Mark"
    And HE I save the changes

  @MATCH-5269
  Scenario: As a Support App I need to add log entries and verify log entry details
  #precondition
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
#    And HS I set the Visit Availability of RepVisits Availability Settings to "All RepVisits Users"
#    Then HS I set the RepVisits Visits Confirmations option to "No, I want to manually review all incoming requests."
#    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
#    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
#    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."

#    Then HS I set the date using "14" and "21"
    Then HS I clear the time slot for the particular day "14" in Regular Weekly Hours Tab
    Then HS I add the new time slot with "14","10:20am","11:20am" and "5" with "1"
    And HS I successfully sign out

    Then HE I am logged in to Intersect HE as user type "alpenaAdmin"
    And HE I search for "Standalone High School 2" in RepVisits page
    Then HE I select Visits to schedule the appointment for "Standalone High School 2" using "14" and "10:20am"
    And HE I verify the schedule pop_up for "Standalone High School 2" using "10:20am" and "11:20am"
    And HE I successfully sign out

    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
    Then HS I verify the Notification "Purple","Alpena Community College","10:20am","14" in the Request Notification Tab
    And HS I select "Decline" option for the Notification using "Purple","10:20am","Alpena Community College"
    Then HS I verify the Decline Pop-up in the Notification Tab "Purple","Alpena Community College","10:20am","14"
    Then HS I select the "Yes, Decline" button by entering the message "QA Declined" for "Purple"
    And HS I successfully sign out
#Declined
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I go to the Log History for "Alpena Community College" from the institution dashboard
    Then SP I verify visit details in Log History for "Declined" using "Today","School Manager"

    Then HE I am logged in to Intersect HE as user type "alpenaAdmin"
    And HE I search for "Standalone High School 2" in RepVisits page
    Then HE I select Visits to schedule the appointment for "Standalone High School 2" using "14" and "10:20am"
    And HE I verify the schedule pop_up for "Standalone High School 2" using "10:20am" and "11:20am"
#    And HE I successfully sign out

    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
    Then HS I verify the Notification "Purple","Alpena Community College","10:20am","14" in the Request Notification Tab
    And HS I select "Confirm" option for the Notification using "Purple","10:20am","Alpena Community College"
    And HS I successfully sign out
#Approved
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I go to the Log History for "Alpena Community College" from the institution dashboard
    Then SP I verify visit details in Log History for "Approved" using "Today","School Manager"

    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
    Then HS I add the new time slot with "14","10:25am","11:25am" and "5" with "1"
    Then HS I set the value for Reschedule the visit

    Then HS I reschedule the visit for the following data "Alpena Community College","10:25am","14" in calendar
    Then HS I verify reschedule pop-up for the following data "Purple HE","Alpena Community College","10:25am","14"
    And HS I successfully sign out
#Reschedule
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I go to the Log History for "Alpena Community College" from the institution dashboard
    Then SP I verify visit details in Log History for "Reschedule" using "Today","School Manager"

    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
    And HS I verify the calendar page in RepVisits using "Alpena Community College","10:25am","14" for cancel the Visit
    Then HS I verify "Cancel This Visit" notification for "Purple HE" using "10:25AM","Alpena Community College","14","10:25am"
    Then HS I select cancel the Visit
    And HS I successfully sign out
#Cancel
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I go to the Log History for "Alpena Community College" from the institution dashboard
    Then SP I verify visit details in Log History for "Cancel" using "Today","School Manager"
