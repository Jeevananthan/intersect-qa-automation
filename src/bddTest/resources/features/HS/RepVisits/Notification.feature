@HS @HS1
Feature: HS - RepVisits - Notification - As an HS user, I should be able to see notifications when there are new appointment requests or changes to existing appointments
  @MATCH-2094 @MATCH-2124
  Scenario Outline: As an HE user I want to see RepVisit notifications organized intuitively within my Notifications
  page REQUESTS subtab so I can efficiently find the updates I am looking for within RepVisits.
#FOR VISITS
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    Then HS I set the RepVisits Visits Confirmations option to "<Option>"

    Then HS I set the date using "<StartDate>" and "<EndDate>"
    And HS I verify the update button appears and I click update button
    Then HS I clear the time slot for the particular day "<StartDate>" in Regular Weekly Hours Tab
    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>" with "<option>"
    And HS I successfully sign out

    Then HE I am logged in to Intersect HE as user type "alpenaAdmin"
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<Date>" and "<heStartTime>"
    And HE I click Request button in visit schedule popup

    Then HE I verify default the HE user to see the REQUESTS subtab when they arrive on the Notifications page
    Then HE I verify the message "You currently have no notifications" is displayed in the Request subTab
    Then HE I verify the Paginate the REQUESTS subtab via 25 entries with a "Show More" action to display the next 25 entries
    Then HE I verify the Sorting notification entries in the REQUESTS subtab by newest to oldest
    And HE I verify the Notifications & Tasks using "<School>","<StartDate>","<heStartTime>"
    Then HE I click the View full details option in the Request subTab using "<School>","<StartDate>","<heStartTime>"

#FOR FAIRS
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
    Then HS I cancel the college fairs created
    Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
    And HS I successfully sign out

    Then HE I am logged in to Intersect HE as user type "alpenaAdmin"
    And HE I search for "<School>" in RepVisits page
    Then HE I register for the "<College Fair Name>" college fair at "<School>"

    Then HE I verify default the HE user to see the REQUESTS subtab when they arrive on the Notifications page
    Then HE I verify the message "You currently have no notifications" is displayed in the Request subTab
    Then HE I verify the Paginate the REQUESTS subtab via 25 entries with a "Show More" action to display the next 25 entries
    Then HE I verify the Sorting notification entries in the REQUESTS subtab by newest to oldest
    And HE I verify the Notifications & Tasks using "<School>","<Date>","<fairTime>" for fairs
    Then HE I click the View full details option in the Request subTab using "<School>","<Date>","<fairTime>" for fairs

#Cancelling visits and fairs
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
    Then HS I remove the Time Slot created with "<StartDate>","<StartTime>" in Regular Weekly Hours Tab
    Then HS I Click on the View Details button for the College Fair Event "<College Fair Name>"
    Then HS I select Edit button to cancel the college Fair "<College Fair Name>"
    And HS I successfully sign out

    Examples:
      |Day |StartTime|EndTime |NumVisits|StartDate|EndDate |Option                                               |School                  |heStartTime |College Fair Name         |Date|Start Time|End Time|RSVP Deadline|Cost|Max Number of Colleges|Number of Students Expected| ButtonToClick |fairTime|option|
      |35  |11:34am  |10:25pm |3        |35       |49      |No, I want to manually review all incoming requests. |Standalone High School 2|11:34am     |QA4 Fairs for testing     |35  |0900AM    |1000AM  |28           |$25 |25                    |100                        | Save          |9:00am  |1     |

  @MATCH-2565
  Scenario Outline: As a Repvisits admin users, I want to view the all requests should be available for all Admin user until one of the user approves/denies the request.
#precondition
    Given HS I am logged in to Intersect HS as user type "adminHS"
    Then HS I set the RepVisits Visits Confirmations option to "<Option>"
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."

    Then HS I set the date using "<StartDate>" and "<EndDate>"
    And HS I verify the update button appears and I click update button
    Then HS I clear the time slot for the particular day "<StartDate>" in Regular Weekly Hours Tab
    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>" with "<option>"
    And HS I successfully sign out

    Then HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<Date>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"

    Then HE I am logged in to Intersect HE as user type "publishing"
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<Date>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"

#verify notification is displayed
#verify Notification in other admin user with same school
    Given HS I am logged in to Intersect HS as user type "admin"
    Then HS I verify the Notification "<user>","<institution>","<heStartTime>","<StartDate>" in the Request Notification Tab
    And HS I successfully sign out
#verify Notification in admin user
    Given HS I am logged in to Intersect HS as user type "adminHS"
#FOR CONFIRM
    Then HS I verify the Notification "<user>","<institution>","<heStartTime>","<StartDate>" in the Request Notification Tab
    And HS I select "Confirm" option for the Notification using "<user>","<heStartTime>","<institution>"
#verify notification is not displayed
    Then HS I verify the Notification is not displayed after "Confirm" the visit in the Request Notification Tab for "<user>","<institution>","<heStartTime>","<StartDate>"
#FOR DECLINE
    Then HS I verify the Notification "<user>","<institution>","<heStartTime>","<StartDate>" in the Request Notification Tab
    And HS I select "Decline" option for the Notification using "<user>","<heStartTime>","<institution>"
    Then HS I verify the Decline Pop-up in the Notification Tab "<user>","<institution>","<heStartTime>","<StartDate>"
    Then HS I select the "Yes, Decline" button by entering the message "QA Declined" for "<user>"
#verify notification is not displayed
    Then HS I verify the Notification is not displayed after "Decline" the visit in the Request Notification Tab for "<user>","<institution>","<heStartTime>","<StartDate>"
    Then HS I remove the Time Slot created with "<StartDate>","<StartTime>" in Regular Weekly Hours Tab
    And HS I successfully sign out
#verify Notification is not displayed in other admin user with same school
#FOR CONFIRM
    Given HS I am logged in to Intersect HS as user type "admin"
    Then HS I verify the Notification is not displayed after "Confirm" the visit in the Request Notification Tab for "<user>","<institution>","<heStartTime>","<StartDate>"
#FOR DECLINE
    Then HS I verify the Notification is not displayed after "Decline" the visit in the Request Notification Tab for "<user>","<institution>","<heStartTime>","<StartDate>"
    And HS I successfully sign out

    Examples:
      |Date  |Day|StartTime|EndTime|NumVisits|StartDate|EndDate|hsEndTime |Option                                              |School            |heStartTime|heTime |user    |institution              |option|
      |7     |7  |10:56am  |12:11pm|3        |7        |49     |12:11pm   |No, I want to manually review all incoming requests.|Homeconnection    |10:56am    |10:56am|PurpleHE|The University of Alabama|1     |

  @MATCH-2093 @MATCH-2828
  Scenario Outline: As a HS user I want to see RepVisit notifications organized intuitively within my Notifications page ACTIVITY subtab
  so I can efficiently find the updates I am looking for within RepVisits.
#FOR VISITS
#precondition
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone6"
    Then HS I set the RepVisits Visits Confirmations option to "<Option>"
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
#FOR CONFIRM
    Then HS I clear the time slot for the particular day "<StartDateforNewVisit>" in Regular Weekly Hours Tab
    Then HS I set the date using "<StartDateforNewVisit>" and "<EndDate>"
    And HS I verify the update button appears and I click update button
    Then HS I add the new time slot with "<Day>","<RescheduleAvailabilityStartTime>","<RescheduleAvailabilityEndTime>" and "<NumVisits>" with "<option>"
    Then HS I set the value for Reschedule the visit

    Then HS I set the date using "<StartDate>" and "<EndDate>"
    And HS I verify the update button appears and I click update button
    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>" with "<option>"

    Given HE I want to login to the HE app using "purpleheautomation+HEAlpena@gmail.com" as username and "Password!1" as password
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<Date>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"

    Given HE I want to login to the HE app using "purplehsautomations+alpena@gmail.com" as username and "Password!1" as password
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<Date>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"

    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone6"
    Then HS I verify the Notification "<HEUser>","<institution>","<heStartTime>","<StartDate>" in the Request Notification Tab
    And HS I select "Confirm" option for the Notification using "<HEUser>","<heStartTime>","<institution>"
#VERIFY ACTIVITY
    And HS I select Activity in RepVisits to verify "confirmed" notification for "<HSuser>","<institution>","<activityDate>","<heStartTime>"

#FOR DECLINE
    Then HS I verify the Notification "<HEUser>","<institution>","<heStartTime>","<StartDate>" in the Request Notification Tab
    And HS I select "Decline" option for the Notification using "<HEUser>","<heStartTime>","<institution>"
    Then HS I verify the Decline Pop-up in the Notification Tab "<HEUser>","<institution>","<heStartTime>","<StartDate>"
    Then HS I select the "Yes, Decline" button by entering the message "QA Declined" for "<HEUser>"
#VERIFY ACTIVITY
    And HS I select Activity in RepVisits to verify "declined" notification for "<HSuser>","<institution>","<activityDate>","<heStartTime>"

#FOR RESCHEDULE
    Then HS I reschedule the visit for the following data "<institution>","<RescheduleStartTime>","<Date>" in calendar
    Then HS I verify reschedule pop-up for the following data "<HEUser>","<institution>","<RescheduleStartTime>","<Date>"
#VERIFY RESCHEDULE PAGE FOR MATCH-2828
    Then HS I verify the "Rescheduled visits aren't automatically confirmed" in reschedule page
    Then HS I verify the university "<institution>" in reschedule page
    Then HS I verify the date "<Date>" in reschedule page
    Then HS I verify the time "<RescheduleStartTime>" in reschedule page

    Then HS I reschedule a visit for the following details "<newVisitSTime>","<reason>","<StartDateforNewVisit>"
#VERIFY ACTIVITY
    And HS I select Activity in RepVisits to verify "rescheduled" notification for "<HSuser>","<institution>","<StartDateforNewVisit>","<RescheduleAvailabilityStartTime>" after Rescheduled the visit

#FOR CANCEL
    And HS I verify the calendar page in RepVisits using "<institution>","<StartTime>","<StartDateforNewVisit>" for cancel the Visit
    Then HS I verify "Cancel This Visit" notification for "<HEUser>" using "<calendarST>","<institution>","<StartDateforNewVisit>","<StartTime>"
    Then HS I select cancel the Visit
#VERIFY ACTIVITY
    And HS I select Activity in RepVisits to verify "cancelled" notification for "<HSuser>","<institution>","<StartDateforNewVisit>","<heStartTime>" after cancelled the visit

#FOR MANUALLY CREATE A NEW VISIT APPOINTMENT
    Then HS I manually add the contact to an appointment using "<StartDate>","<StartTime>","<FName>","<LName>","<EMail>","<Phone>","<Position>","<institution>"
#VERIFY ACTIVITY
    And HS I select Activity in RepVisits to verify "scheduled" notification for "<HSuser>","<institution>","<activityDate>","<newVisitSTime>"

#Remove the time slot in Regular Weekly Hours Tab and Calendar
    Then HS I remove the Time Slot created with "<StartDate>","<StartTime>" in Regular Weekly Hours Tab
#FOR FAIRS
#FOR CONFIRM
    Then HS I cancel the college fairs created
    Then HS I set the following data to On the College Fair page "<newFairName>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"

    Given HE I want to login to the HE app using "purpleheautomation+HEAlpena@gmail.com" as username and "Password!1" as password
    And HE I search for "<School>" in RepVisits page
    Then HE I register for the "<College Fair Name>" college fair at "<School>"

    Given HE I want to login to the HE app using "purplehsautomations+alpena@gmail.com" as username and "Password!1" as password
    And HE I search for "<School>" in RepVisits page
    Then HE I register for the "<College Fair Name>" college fair at "<School>"

    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone6"
    Then HS I verify the Notification "<HEUser>","<institution>","<FairsSTime>","<Date>" in the Request Notification Tab for Fairs
    And HS I select "Confirm" option for the Notification using "<HEUser>","<Date>","<FairsSTime>","<institution>" for Fairs
#VERIFY ACTIVITY
    And HS I select Activity in RepVisits to verify "confirmed" notification for "<HSuser>","<institution>","<activityDate>","<AcitivityFairTime>" for Fairs
#FOR DECLINE
    Then HS I verify the Notification "<HEUser>","<institution>","<FairsSTime>","<Date>" in the Request Notification Tab for Fairs
    And HS I select "Decline" option for the Notification using "<HEUser>","<Date>","<FairsSTime>","<institution>" for Fairs
    Then HS I verify the Decline Pop-up in the Notification Tab "<HEUser>","<institution>","<FairsSTime>","<Date>" for Fairs
    Then HS I select the "Yes, Decline" button by entering the message "QA Declined" for "<HEUser>"
#VERIFY ACTIVITY
    And HS I select Activity in RepVisits to verify "declined" notification for "<HSuser>","<institution>","<activityDate>","<AcitivityFairTime>" for Fairs
#FOR RESCHEDULE
    Then HS I Click on the View Details button for the College Fair "<College Fair Name>"
    Then HS I verify the edit fair popup "<College Fair Name>","<FairSTimeforReschedule>","<Date>"
    And HS I reschedule the fair using "<newFairsSTime>"
#VERIFY ACTIVITY
    And HS I select Activity in RepVisits to verify "rescheduled" notification for "<HSuser>","<institution>","<activityDate>","<newFairsSTime>" for Fairs
#FOR CANCEL
    Then HS I Click on the View Details button for the College Fair Event "<College Fair Name>"
    Then HS I select Edit button to cancel the college Fair "<College Fair Name>"
#VERIFY ACTIVITY
    And HS I select Activity in RepVisits to verify "cancelled" notification for "<HSuser>","<institution>","<activityDate>","<newFairsSTime>" for Fairs
    Then HS I verify the message "You currently have no notifications" is displayed in the ACTIVITY subtab
    Then HS I verify the Paginate the ACTIVITY subtab via 25 entries with a "Show More" action to display the next 25 entries

    Examples:
      |activityDate |calendarST    |HEUser   |HSuser         |institution               |Day |StartTime|EndTime |NumVisits|StartDate|EndDate |hsEndTime    |Option                                               |School                  |heStartTime |heTime  |College Fair Name     |Date|Start Time|End Time|RSVP Deadline|Cost|Max Number of Colleges|Number of Students Expected|ButtonToClick |StartDateforNewVisit|newVisitSTime|RescheduleStartTime|RescheduleAvailabilityStartTime|RescheduleAvailabilityEndTime|FairsSTime|newFairsSTime|AcitivityFairTime|newFairName|reason|FName    |LName |EMail                           |Phone       |Position|FairSTimeforReschedule|option|
      |14           |12:19AM       |purple   |School Manager |Alpena Community College  |14  |12:19am  |10:59pm |3        |14       |35      |10:59pm      |No, I want to manually review all incoming requests. |Standalone High School 6|12:19am     |12:19am |Qa Fair for testng    |14  |1200AM    |0100AM  |5            |$25 |25                    |100                        |Save          |21                  |12:31am      |12:29 AM           |12:29am                        |10:59pm                      |12:00am   |12:00am      |12:00am          |fairNewqa  |by QA |purple   |HE    |purpleheautomation@gmail.com    |999999999999|QA      |12:00 AM              |1     |
