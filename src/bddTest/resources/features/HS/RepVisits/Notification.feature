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
#  Cancelling visits
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
    Then HS I remove the Time Slot created with "<StartDate>","<StartTime>" in Regular Weekly Hours Tab
    And HS I successfully sign out

    Examples:
      |Day |StartTime|EndTime |NumVisits|StartDate|EndDate |Option                                               |School                  |heStartTime |Date|option|
      |35  |11:34am  |10:25pm |3        |35       |49      |No, I want to manually review all incoming requests. |Standalone High School 2|11:34am     |35  |1     |

  @MATCH-2094 @MATCH-2124
  Scenario Outline: As an HE user I want to see RepVisit notifications organized intuitively within my Notifications
  page REQUESTS subtab so I can efficiently find the updates I am looking for within RepVisits.
#FOR FAIRS
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
    Then HS I cancel the college fairs created
    Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"

    Then HE I am logged in to Intersect HE as user type "alpenaAdmin"
    And HE I search for "<School>" in RepVisits page
    Then HE I register for the "<College Fair Name>" college fair at "<School>"

    Then HE I verify default the HE user to see the REQUESTS subtab when they arrive on the Notifications page
    Then HE I verify the message "You currently have no notifications" is displayed in the Request subTab
    Then HE I verify the Paginate the REQUESTS subtab via 25 entries with a "Show More" action to display the next 25 entries
    Then HE I verify the Sorting notification entries in the REQUESTS subtab by newest to oldest
    And HE I verify the Notifications & Tasks using "<School>","<Date>","<fairTime>" for fairs
    Then HE I click the View full details option in the Request subTab using "<School>","<Date>","<fairTime>" for fairs

#Cancelling fairs
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
    Then HS I Click on the View Details button for the College Fair Event "<College Fair Name>"
    Then HS I select Edit button to cancel the college Fair "<College Fair Name>"

    Examples:
      |School                   |College Fair Name         |Date|Start Time|End Time|RSVP Deadline|Cost|Max Number of Colleges|Number of Students Expected| ButtonToClick |fairTime|
      |Standalone High School 2 |QA4 Fairs for testing     |35  |1123AM    |1000AM  |28           |$25 |25                    |100                        | Save          |11:23am  |

  @MATCH-2565
  Scenario Outline: As a Repvisits admin users, I want to view the all requests should be available for all Admin user until one of the user approves/denies the request.
#precondition
    Given HS I am logged in to Intersect HS as user type "HSadmin2"
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
    Given HS I am logged in to Intersect HS as user type "HSadmin3"
    Then HS I verify the Notification "<user>","<institution>","<heStartTime>","<StartDate>" in the Request Notification Tab
    And HS I successfully sign out
#verify Notification in admin user
    Given HS I am logged in to Intersect HS as user type "HSadmin2"
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
    Given HS I am logged in to Intersect HS as user type "HSadmin3"
    Then HS I verify the Notification is not displayed after "Confirm" the visit in the Request Notification Tab for "<user>","<institution>","<heStartTime>","<StartDate>"
#FOR DECLINE
    Then HS I verify the Notification is not displayed after "Decline" the visit in the Request Notification Tab for "<user>","<institution>","<heStartTime>","<StartDate>"
    And HS I successfully sign out

    Examples:
      |Date  |Day|StartTime|EndTime|NumVisits|StartDate|EndDate|hsEndTime |Option                                              |School                     |heStartTime|heTime |user    |institution              |option|
      |7     |7  |10:56am  |12:11pm|3        |7        |49     |12:11pm   |No, I want to manually review all incoming requests.|Trimble County High School |10:56am    |10:56am|PurpleHE|The University of Alabama|1     |

  @MATCH-2093 @MATCH-2828
  Scenario Outline: As a HS user I want to see RepVisit notifications organized intuitively within my Notifications page ACTIVITY subtab
  so I can efficiently find the updates I am looking for within RepVisits.
#FOR VISITS
#precondition
    Given HS I am logged in to Intersect HS as user type "Notification1"
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
    And HS I successfully sign out

    Given HE I want to login to the HE app using "purpleheautomation+HEAlpena@gmail.com" as username and "Password!1" as password
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<Date>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"

    Given HE I want to login to the HE app using "purplehsautomations+alpena@gmail.com" as username and "Password!1" as password
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<Date>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"

    Given HS I am logged in to Intersect HS as user type "Notification1"
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
    And HS I successfully sign out

    Examples:
      |activityDate |calendarST    |HEUser   |HSuser                 |institution               |Day |StartTime|EndTime |NumVisits|StartDate|EndDate |hsEndTime    |Option                                               |School               |heStartTime |heTime  |Date|StartDateforNewVisit|newVisitSTime|RescheduleStartTime|RescheduleAvailabilityStartTime|RescheduleAvailabilityEndTime|reason|FName    |LName |EMail                           |Phone       |Position|option|
      |14           |12:19AM       |purple   |PurpleHS Notification1 |Alpena Community College  |14  |12:19am  |10:59pm |3        |14       |35      |10:59pm      |No, I want to manually review all incoming requests. |Geneva Co High School|12:19am     |12:19am |14  |21                  |12:31am      |12:29 AM           |12:29am                        |10:59pm                      |by QA |purple   |HE    |purpleheautomation@gmail.com    |999999999999|QA      |1     |

  @MATCH-2093 @MATCH-2828
  Scenario Outline: As a HS user I want to see RepVisit notifications organized intuitively within my Notifications page ACTIVITY subtab
  so I can efficiently find the updates I am looking for within RepVisits.
#FOR FAIRS
#FOR CONFIRM
    Given HS I am logged in to Intersect HS as user type "Notification1"
    Then HS I cancel the college fairs created
    Then HS I set the following data to On the College Fair page "<newFairName>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
    And HS I successfully sign out

    Given HE I want to login to the HE app using "purpleheautomation+HEAlpena@gmail.com" as username and "Password!1" as password
    And HE I search for "<School>" in RepVisits page
    Then HE I register for the "<College Fair Name>" college fair at "<School>"

    Given HS I am logged in to Intersect HS as user type "Notification1"
    Then HS I verify the Notification "<HEUser>","<institution>","<FairsSTime>","<Date>" in the Request Notification Tab for Fairs
    And HS I select "Confirm" option for the Notification using "<HEUser>","<Date>","<FairsSTime>","<institution>" for Fairs
#VERIFY ACTIVITY
    And HS I select Activity in RepVisits to verify "confirmed" notification for "<HSuser>","<institution>","<activityDate>","<AcitivityFairTime>" for Fairs
#FOR RESCHEDULE
    Then HS I Click on the View Details button for the College Fair "<College Fair Name>"
    Then HS I verify the edit fair popup "<College Fair Name>","<FairSTimeforReschedule>","<Date>"
    And HS I reschedule the fair using "<newFairsSTime>"
#VERIFY ACTIVITY
    And HS I select Activity in RepVisits to verify "rescheduled" notification for "<HSuser>","<institution>","<activityDate>","<newFairsSTime>" for Fairs
    And HS I successfully sign out

    Given HE I want to login to the HE app using "purplehsautomations+alpena@gmail.com" as username and "Password!1" as password
    And HE I search for "<School>" in RepVisits page
    Then HE I register for the "<College Fair Name>" college fair at "<School>"
#FOR DECLINE
    Given HS I am logged in to Intersect HS as user type "Notification1"
    Then HS I verify the Notification "<HEUser>","<institution>","<newFairsSTime>","<Date>" in the Request Notification Tab for Fairs
    And HS I select "Decline" option for the Notification using "<HEUser>","<Date>","<newFairsSTime>","<institution>" for Fairs
    Then HS I verify the Decline Pop-up in the Notification Tab "<HEUser>","<institution>","<newFairsSTime>","<Date>" for Fairs
    Then HS I select the "Yes, Decline" button by entering the message "QA Declined" for "<HEUser>"
#VERIFY ACTIVITY
    And HS I select Activity in RepVisits to verify "declined" notification for "<HSuser>","<institution>","<activityDate>","<newFairsSTime>" for Fairs
#FOR CANCEL
    Then HS I Click on the View Details button for the College Fair Event "<College Fair Name>"
    Then HS I select Edit button to cancel the college Fair "<College Fair Name>"
#VERIFY ACTIVITY
    And HS I select Activity in RepVisits to verify "cancelled" notification for "<HSuser>","<institution>","<activityDate>","<newFairsSTime>" for Fairs
    Then HS I verify the Paginate the ACTIVITY subtab via 25 entries with a "Show More" action to display the next 25 entries
    Then HS I verify the message "You currently have no notifications" is displayed in the ACTIVITY subtab
    And HS I successfully sign out

    Examples:
      |activityDate |HEUser   |HSuser                 |institution               |School               |College Fair Name     |Date|Start Time|End Time|RSVP Deadline|Cost|Max Number of Colleges|Number of Students Expected|ButtonToClick |FairsSTime|newFairsSTime|AcitivityFairTime|newFairName|FairSTimeforReschedule|
      |14           |purple   |PurpleHS Notification1 |Alpena Community College  |Geneva Co High School|Qa Fair for testng    |14  |1000AM    |1200PM  |5            |$25 |25                    |100                        |Save          |10:00am   |11:00am      |10:00am          |fairNewqa  |11:00 AM              |

  @MATCH-2168
  Scenario Outline: As a Naviance RepVisits user who is looking at Notification entries in the RepVisits notifications page,
  I want to see the City and State of each institution within each notification entry
  so I can more easily identify the institution the notification entry is updating me about.
#FOR VISITS
#Naviance
#precondition
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
    And HS I set the Visit Availability of RepVisits Availability Settings to "All RepVisits Users"
    Then HS I set the RepVisits Visits Confirmations option to "<Option>"
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
#Create a visits
    Then HS I set the date using "<StartDate>" and "<EndDate>"
    And HS I verify the update button appears and I click update button
    Then HS I clear the time slot for the particular day "<StartDate>" in Regular Weekly Hours Tab
    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>" with "<option>"
    And HS I successfully sign out
#Register Visits
    Then HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<StartDate>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"

    Then HE I am logged in to Intersect HE as user type "publishing2"
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<StartDate>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"

#verify the City and State displayed in the HE Request Notification tab
    Then HE I verify the city and state "<cityAndStateofSchool>" are present in the underneath of School Name "<School>" in the Request Notification Tab

#Verify the City and State displayed in the HS Request and Activity Notification Tab
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
    Then HS I verify the Notification "<user>","<institution>","<heStartTime>","<StartDate>" in the Request Notification Tab
    Then HS I verify the city and state "<cityAndStateofInstitution>" are present in the underneath of Institiution Name "<institution>" in the Request Notification Tab
    And HS I select "Confirm" option for the Notification using "<user>","<heStartTime>","<institution>"

    Then HS I verify the Notification "<user>","<institution>","<heStartTime>","<StartDate>" in the Request Notification Tab
    And HS I select "Decline" option for the Notification using "<user>","<heStartTime>","<institution>"
    Then HS I verify the Decline Pop-up in the Notification Tab "<user>","<institution>","<heStartTime>","<StartDate>"
    Then HS I select the "Yes, Decline" button by entering the message "QA Declined" for "<user>"

    Then HS I verify the city and state "<cityAndStateofInstitution>" are present in the underneath of Institiution Name "<institution>" in the Activity Tab
    Then HS I remove the Time Slot created with "<StartDate>","<StartTime>" in Regular Weekly Hours Tab
    And HS I successfully sign out

#Verify the City and State displayed in the HE Activity Activity Tab
    Then HE I am logged in to Intersect HE as user type "publishing2"
    Then HE I verify the city and state "<cityAndStateofSchool>" are present in the underneath of School Name "<schoolForHE>" in the Activity Tab

    Examples:
      |user    |institution              |Day|StartTime|EndTime |NumVisits|StartDate|EndDate |hsEndTime|Option                                               |School                  |heStartTime|heTime |cityAndStateofInstitution|cityAndStateofSchool |schoolForHE                     | option|
      |PurpleHE|The University of Alabama|7  |10:25am  |11:25pm |3        |7        |42      |11:25pm  |No, I want to manually review all incoming requests. |Standalone High School 2|10:25am    |10:25am|Tuscaloosa, AL           |Milford, Ohio        |Standalone High School 2 - Ohio |1      |

  @MATCH-2168
  Scenario Outline: As a Naviance RepVisits user who is looking at Notification entries in the RepVisits notifications page,
  I want to see the City and State of each institution within each notification entry
  so I can more easily identify the institution the notification entry is updating me about.
#FOR FAIRS
#Naviance
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
    Then HS I set the date using "<StartDate>" and "<EndDate>"
#Create a Fair
    Then HS I cancel the college fairs created
    Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
    And HS I successfully sign out
#Register Visits and Fairs
    Then HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "<School>" in RepVisits page
    Then HE I register for the "<College Fair Name>" college fair at "<School>"

    Then HE I am logged in to Intersect HE as user type "publishing2"
    And HE I search for "<School>" in RepVisits page
    Then HE I register for the "<College Fair Name>" college fair at "<School>"
#verify the City and State displayed in the HE Request Notification tab
    Then HE I verify the city and state "<cityAndStateofSchool>" are present in the underneath of School Name "<School>" in the Request Notification Tab for Fairs

#Verify the City and State displayed in the HS Request and Activity Notification Tab for Fairs
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
    Then HS I verify the Notification "<user>","<institution>","<fairStartTime>","<Date>" in the Request Notification Tab for Fairs
    Then HS I verify the city and state "<cityAndStateofInstitution>" are present in the underneath of Institiution Name "<institution>" in the Request Notification Tab for Fairs
    And HS I select "Confirm" option for the Notification using "<user>","<Date>","<fairStartTime>","<institution>" for Fairs

    Then HS I verify the Notification "<user>","<institution>","<fairStartTime>","<Date>" in the Request Notification Tab for Fairs
    And HS I select "Decline" option for the Notification using "<user>","<Date>","<fairStartTime>","<institution>" for Fairs
    Then HS I verify the Decline Pop-up in the Notification Tab "<user>","<institution>","<fairStartTime>","<Date>" for Fairs
    Then HS I select the "Yes, Decline" button by entering the message "QA Declined" for "<user>"

    Then HS I verify the city and state "<cityAndStateofInstitution>" are present in the underneath of Institiution Name "<institution>" in the Activity Tab for Fairs
    And HS I successfully sign out
#Verify the City and State displayed in the HE Activity Activity Tab
    Then HE I am logged in to Intersect HE as user type "publishing2"
    Then HE I verify the city and state "<cityAndStateofSchool>" are present in the underneath of School Name "<schoolForHE>" in the Activity Tab for Fairs

    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
    Then HS I Click on the View Details button for the College Fair Event "<College Fair Name>"
    Then HS I select Edit button to cancel the college Fair "<College Fair Name>"
    And HS I successfully sign out

    Examples:
      |user    |institution              |fairStartTime|School                  |College Fair Name          |Date|Start Time|End Time|RSVP Deadline|Cost|Max Number of Colleges|Number of Students Expected|ButtonToClick|cityAndStateofInstitution|cityAndStateofSchool |schoolForHE                     |StartDate|EndDate |
      |PurpleHE|The University of Alabama|9:00am       |Standalone High School 2|QA Fairs for City and State|12  |0900AM    |1000AM  |11           |$25 |25                    |100                        |Save         |Tuscaloosa, AL           |Milford, Ohio        |Standalone High School 2 - Ohio |1       |92      |


  @MATCH-2168
  Scenario Outline: As a Non-Naviance RepVisits user who is looking at Notification entries in the RepVisits notifications page,
  I want to see the City and State of each institution within each notification entry
  so I can more easily identify the institution the notification entry is updating me about.
#FOR VISITS
#Non-Naviance
#precondition
    Given HS I am logged in to Intersect HS as user type "HSadmin2"
    And HS I set the Visit Availability of RepVisits Availability Settings to "All RepVisits Users"
    Then HS I set the RepVisits Visits Confirmations option to "<Option>"
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
#Create a visits and Fairs
    Then HS I set the date using "<StartDate>" and "<EndDate>"
    And HS I verify the update button appears and I click update button
    Then HS I clear the time slot for the particular day "<StartDate>" in Regular Weekly Hours Tab
    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>" with "<option>"
    And HS I successfully sign out
#Register Visits and Fairs
    Then HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "<Non-NavSchool>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<Non-NavSchool>" using "<StartDate>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<Non-NavSchool>" using "<heTime>" and "<hsEndTime>"

    Then HE I am logged in to Intersect HE as user type "publishing"
    And HE I search for "<Non-NavSchool>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<Non-NavSchool>" using "<StartDate>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<Non-NavSchool>" using "<heTime>" and "<hsEndTime>"

#verify the City and State displayed in the HE Request Notification tab
    Then HE I verify the city and state "<cityAndStateofNon-NavianceSchool>" are present in the underneath of School Name "<Non-NavSchool>" in the Request Notification Tab

#Verify the City and State displayed in the HS Request and Activity Notification Tab
    Given HS I am logged in to Intersect HS as user type "HSadmin2"
    Then HS I verify the Notification "<user>","<institution>","<heStartTime>","<StartDate>" in the Request Notification Tab
    Then HS I verify the city and state "<cityAndStateofInstitution>" are present in the underneath of Institiution Name "<institution>" in the Request Notification Tab
    And HS I select "Confirm" option for the Notification using "<user>","<heStartTime>","<institution>"

    Then HS I verify the Notification "<user>","<institution>","<heStartTime>","<StartDate>" in the Request Notification Tab
    And HS I select "Decline" option for the Notification using "<user>","<heStartTime>","<institution>"
    Then HS I verify the Decline Pop-up in the Notification Tab "<user>","<institution>","<heStartTime>","<StartDate>"
    Then HS I select the "Yes, Decline" button by entering the message "QA Declined" for "<user>"

    Then HS I verify the city and state "<cityAndStateofInstitution>" are present in the underneath of Institiution Name "<institution>" in the Activity Tab
    Then HS I remove the Time Slot created with "<StartDate>","<StartTime>" in Regular Weekly Hours Tab
    And HS I successfully sign out

#Verify the City and State displayed in the HE Activity Notification Tab
    Then HE I am logged in to Intersect HE as user type "publishing"
    Then HE I verify the city and state "<cityAndStateofNon-NavianceSchool>" are present in the underneath of School Name "<Non-NavschoolForHE>" in the Activity Tab

    Examples:
      |user    |institution              |Day|StartTime|EndTime |NumVisits|StartDate|EndDate |hsEndTime|Option                                              |heStartTime|heTime |Non-NavSchool             |cityAndStateofInstitution|cityAndStateofNon-NavianceSchool|Non-NavschoolForHE                   |option|
      |PurpleHE|The University of Alabama|14 |10:25am  |11:25pm |3        |14       |42      |11:25pm  |No, I want to manually review all incoming requests.|10:25am    |10:25am|Trimble County High School|Tuscaloosa, AL           |Bedford, Kentucky               |Trimble County High School - Kentucky|1     |

  @MATCH-2168
  Scenario Outline: As a Non-Naviance RepVisits user who is looking at Notification entries in the RepVisits notifications page,
  I want to see the City and State of each institution within each notification entry
  so I can more easily identify the institution the notification entry is updating me about.
#FOR FAIRS
#Non-Naviance
#precondition
    Given HS I am logged in to Intersect HS as user type "HSadmin2"
#Create a Fair
    Then HS I cancel the college fairs created
    Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
    And HS I successfully sign out
#Register Fair
    Then HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "<Non-NavSchool>" in RepVisits page
    Then HE I register for the "<College Fair Name>" college fair at "<Non-NavSchool>"

    Then HE I am logged in to Intersect HE as user type "publishing"
    And HE I search for "<Non-NavSchool>" in RepVisits page
    Then HE I register for the "<College Fair Name>" college fair at "<Non-NavSchool>"
#verify the City and State displayed in the HE Request Notification tab
    Then HE I verify the city and state "<cityAndStateofNon-NavianceSchool>" are present in the underneath of School Name "<Non-NavSchool>" in the Request Notification Tab for Fairs

#Verify the City and State displayed in the HS Request and Activity Notification Tab
    Given HS I am logged in to Intersect HS as user type "HSadmin2"
    Then HS I verify the Notification "<user>","<institution>","<fairStartTime>","<Date>" in the Request Notification Tab for Fairs
    Then HS I verify the city and state "<cityAndStateofInstitution>" are present in the underneath of Institiution Name "<institution>" in the Request Notification Tab for Fairs
    And HS I select "Confirm" option for the Notification using "<user>","<Date>","<fairStartTime>","<institution>" for Fairs

    Then HS I verify the Notification "<user>","<institution>","<fairStartTime>","<Date>" in the Request Notification Tab for Fairs
    And HS I select "Decline" option for the Notification using "<user>","<Date>","<fairStartTime>","<institution>" for Fairs
    Then HS I verify the Decline Pop-up in the Notification Tab "<user>","<institution>","<fairStartTime>","<Date>" for Fairs
    Then HS I select the "Yes, Decline" button by entering the message "QA Declined" for "<user>"

    Then HS I verify the city and state "<cityAndStateofInstitution>" are present in the underneath of Institiution Name "<institution>" in the Activity Tab for Fairs
    And HS I successfully sign out
#Verify the City and State displayed in the HE Activity Notification Tab
    Then HE I am logged in to Intersect HE as user type "publishing"
    Then HE I verify the city and state "<cityAndStateofNon-NavianceSchool>" are present in the underneath of School Name "<Non-NavschoolForHE>" in the Activity Tab for Fairs

    Given HS I am logged in to Intersect HS as user type "HSadmin2"
    Then HS I Click on the View Details button for the College Fair Event "<College Fair Name>"
    Then HS I select Edit button to cancel the college Fair "<College Fair Name>"
    And HS I successfully sign out

    Examples:
      |user    |institution              |fairStartTime|College Fair Name          |Date|Start Time|End Time|RSVP Deadline|Cost|Max Number of Colleges|Number of Students Expected|ButtonToClick|Non-NavSchool             |cityAndStateofInstitution|cityAndStateofNon-NavianceSchool|Non-NavschoolForHE                   |
      |PurpleHE|The University of Alabama|9:01am       |QA Fairs for City and State|12  |0901AM    |1001AM  |11           |$25 |25                    |100                        |Save         |Trimble County High School|Tuscaloosa, AL           |Bedford, Kentucky               |Trimble County High School - Kentucky|

  @MATCH-2091
  Scenario: As a HS user I want to see RepVisit notifications organized intuitively within my Notifications page REQUESTS subtab
  so I can efficiently find the updates I am looking for within RepVisits
  #Activity subtab covered on MATCH-2093
  #FOR VISITS
    #precondition
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
    And HS I set the Visit Availability of RepVisits Availability Settings to "All RepVisits Users"
    Then HS I set the RepVisits Visits Confirmations option to "No, I want to manually review all incoming requests."
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
    Then HS I verify the Message "You currently have no notifications." is displayed in the Request Notification Tab

    Then HS I set the date using "14" and "42"
    And HS I verify the update button appears and I click update button
    Then HS I clear the time slot for the particular day "14" in Regular Weekly Hours Tab
    Then HS I add the new time slot with "14","10:32am","11:25pm" and "3" with "1"
    And HS I successfully sign out

    Then HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "Standalone High School 2" in RepVisits page
    Then HE I select Visits to schedule the appointment for "Standalone High School 2" using "14" and "10:32am"
    And HE I verify the schedule pop_up for "Standalone High School 2" using "10:32am" and "11:25pm"

    Then HE I am logged in to Intersect HE as user type "publishing"
    And HE I search for "Standalone High School 2" in RepVisits page
    Then HE I select Visits to schedule the appointment for "Standalone High School 2" using "14" and "10:32am"
    And HE I verify the schedule pop_up for "Standalone High School 2" using "10:32am" and "11:25pm"

    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
    Then HS I verify the Default the HS user to see the Request subtab when they arrive on the Notifications page
    Then HS I verify the Notification "PurpleHE","The University of Alabama","10:32am","14" in the Request Notification Tab
    And HS I select "Confirm" option for the Notification using "PurpleHE","10:32am","The University of Alabama"

    Then HS I verify the Notification "PurpleHE","The University of Alabama","10:32am","14" in the Request Notification Tab
    And HS I select "Decline" option for the Notification using "PurpleHE","10:32am","The University of Alabama"
    Then HS I verify the Decline Pop-up in the Notification Tab "PurpleHE","The University of Alabama","10:32am","14"
    Then HS I select the "No, go back" button by entering the message "" for "PurpleHE"
    Then HS I verify the Notification "PurpleHE","The University of Alabama","10:32am","14" in the Request Notification Tab
    And HS I select "Decline" option for the Notification using "PurpleHE","10:32am","The University of Alabama"
    Then HS I verify the Decline Pop-up in the Notification Tab "PurpleHE","The University of Alabama","10:32am","14"
    Then HS I select the "Yes, Decline" button by entering the message "QA Declined" for "PurpleHE"
    Then HS I remove the Time Slot created with "14","10:32am" in Regular Weekly Hours Tab

  #FOR FAIRS
    Then HS I set the following data to On the College Fair page "QAs Fairs tests", "14", "0900AM", "1000AM", "12", "$25", "25", "100", "Save"
    And HS I successfully sign out

    Then HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "Standalone High School 2" in RepVisits page
    Then HE I register for the "QAs Fairs tests" college fair at "Standalone High School 2"
    And HE I successfully sign out

    Then HE I am logged in to Intersect HE as user type "publishing"
    And HE I search for "Standalone High School 2" in RepVisits page
    Then HE I register for the "QAs Fairs tests" college fair at "Standalone High School 2"
    And HE I successfully sign out

    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
    Then HS I verify the Default the HS user to see the Request subtab when they arrive on the Notifications page
    Then HS I verify the Notification "PurpleHE","The University of Alabama","9:00am","14" in the Request Notification Tab for Fairs
    And HS I select "Confirm" option for the Notification using "PurpleHE","14","9:00am","The University of Alabama" for Fairs

    Then HS I verify the Notification "PurpleHE","The University of Alabama","9:00am","14" in the Request Notification Tab for Fairs
    And HS I select "Decline" option for the Notification using "PurpleHE","14","9:00am","The University of Alabama" for Fairs
    Then HS I verify the Decline Pop-up in the Notification Tab "PurpleHE","The University of Alabama","9:00am","14" for Fairs
    Then HS I select the "No, go back" button by entering the message "" for "PurpleHE"
    Then HS I verify the Notification "PurpleHE","The University of Alabama","9:00am","14" in the Request Notification Tab for Fairs
    And HS I select "Decline" option for the Notification using "PurpleHE","14","9:00am","The University of Alabama" for Fairs
    Then HS I verify the Decline Pop-up in the Notification Tab "PurpleHE","The University of Alabama","9:00am","14" for Fairs
    Then HS I select the "Yes, Decline" button by entering the message "QA Declined" for "PurpleHE"
    Then HS I set the RepVisits Visits Confirmations option to "Yes, accept all incoming requests."
    And HS I successfully sign out
