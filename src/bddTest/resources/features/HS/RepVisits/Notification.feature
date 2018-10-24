@HS @HS1
Feature: HS - RepVisits - Notification - As an HS user, I should be able to see notifications when there are new appointment requests or changes to existing appointments

  @MATCH-2168
  Scenario Outline: As a Naviance RepVisits user who is looking at Notification entries in the RepVisits notifications page,
                    I want to see the City and State of each institution within each notification entry
                    so I can more easily identify the institution the notification entry is updating me about.
#Naviance
#precondition
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
    And HS I set the Visit Availability of RepVisits Availability Settings to "All RepVisits Users"
    Then HS I set the RepVisits Visits Confirmations option to "<Option>"
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
#Create a visits and Fairs
    Then HS I set the date using "<StartDate>" and "<EndDate>"
    And HS I verify the update button appears and I click update button
    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>" with "<option>"
    Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
    And HS I successfully sign out
#Register Visits and Fairs
    Then HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<StartDate>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"

    And HE I search for "<School>" in RepVisits page
    Then HE I register for the "<College Fair Name>" college fair at "<School>"

    Then HE I am logged in to Intersect HE as user type "publishing"
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<StartDate>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"

    And HE I search for "<School>" in RepVisits page
    Then HE I register for the "<College Fair Name>" college fair at "<School>"
#verify the City and State displayed in the HE Request Notification tab
    Then HE I verify the city and state "<cityAndStateofSchool>" are present in the underneath of School Name "<School>" in the Request Notification Tab
    Then HE I verify the city and state "<cityAndStateofSchool>" are present in the underneath of School Name "<School>" in the Request Notification Tab for Fairs

#Verify the City and State displayed in the HS Request and Activity Notification Tab for Visits
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

#Verify the City and State displayed in the HE Activity Notification Tab
    Then HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify the city and state "<cityAndStateofSchool>" are present in the underneath of School Name "<schoolForHE>" in the Activity Tab

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
#Verify the City and State displayed in the HE Activity Notification Tab
    Then HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify the city and state "<cityAndStateofSchool>" are present in the underneath of School Name "<schoolForHE>" in the Activity Tab for Fairs
    
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
    Then HS I Click on the View Details button for the College Fair Event "<College Fair Name>"
    Then HS I select Edit button to cancel the college Fair "<College Fair Name>"
    And HS I set the Visit Availability of RepVisits Availability Settings to "All RepVisits Users"
    Then HS I set the RepVisits Visits Confirmations option to "<Option2>"
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
    And HS I successfully sign out

    Examples:
      |user    |institution              |fairStartTime|Day|StartTime|EndTime |NumVisits|StartDate|EndDate |hsEndTime|Option                                              |Option2                           |School                  |heStartTime|heTime |College Fair Name          |Date|Start Time|End Time|RSVP Deadline|Cost|Max Number of Colleges|Number of Students Expected|ButtonToClick|cityAndStateofInstitution|cityAndStateofSchool |schoolForHE                     | option|
      |PurpleHE|The University of Alabama|9:00am       |7  |10:25am  |11:25pm |3        |7        |42      |11:25pm  |No, I want to manually review all incoming requests.|Yes, accept all incoming requests.|Standalone High School 2|10:25am    |10:25am|QA Fairs for City and State|14  |0900AM    |1000AM  |12           |$25 |25                    |100                        |Save         |Tuscaloosa, AL           |Milford, Ohio        |Standalone High School 2 - Ohio |1      |

  @MATCH-2168
  Scenario Outline: As a Non-Naviance RepVisits user who is looking at Notification entries in the RepVisits notifications page,
                    I want to see the City and State of each institution within each notification entry
                    so I can more easily identify the institution the notification entry is updating me about.
#Non-Naviance
#precondition
    Given HS I am logged in to Intersect HS as user type "admin"
    And HS I set the Visit Availability of RepVisits Availability Settings to "All RepVisits Users"
    Then HS I set the RepVisits Visits Confirmations option to "<Option>"
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
#Create a visits and Fairs
    Then HS I set the date using "<StartDate>" and "<EndDate>"
    And HS I verify the update button appears and I click update button
    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>" with "<option>"
    Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
    And HS I successfully sign out
#Register Visits and Fairs
    Then HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "<Non-NavSchool>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<Non-NavSchool>" using "<StartDate>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<Non-NavSchool>" using "<heTime>" and "<hsEndTime>"

    And HE I search for "<Non-NavSchool>" in RepVisits page
    Then HE I register for the "<College Fair Name>" college fair at "<Non-NavSchool>"

    Then HE I am logged in to Intersect HE as user type "publishing"
    And HE I search for "<Non-NavSchool>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<Non-NavSchool>" using "<StartDate>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<Non-NavSchool>" using "<heTime>" and "<hsEndTime>"

    And HE I search for "<Non-NavSchool>" in RepVisits page
    Then HE I register for the "<College Fair Name>" college fair at "<Non-NavSchool>"
#verify the City and State displayed in the HE Request Notification tab
    Then HE I verify the city and state "<cityAndStateofNon-NavianceSchool>" are present in the underneath of School Name "<Non-NavSchool>" in the Request Notification Tab
    Then HE I verify the city and state "<cityAndStateofNon-NavianceSchool>" are present in the underneath of School Name "<Non-NavSchool>" in the Request Notification Tab for Fairs

#Verify the City and State displayed in the HS Request and Activity Notification Tab for Visits
    Given HS I am logged in to Intersect HS as user type "admin"
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
    Then HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify the city and state "<cityAndStateofNon-NavianceSchool>" are present in the underneath of School Name "<Non-NavschoolForHE>" in the Activity Tab

#Verify the City and State displayed in the HS Request and Activity Notification Tab for Fairs
    Given HS I am logged in to Intersect HS as user type "admin"
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
    Then HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify the city and state "<cityAndStateofNon-NavianceSchool>" are present in the underneath of School Name "<Non-NavschoolForHE>" in the Activity Tab for Fairs

    Given HS I am logged in to Intersect HS as user type "admin"
    Then HS I Click on the View Details button for the College Fair Event "<College Fair Name>"
    Then HS I select Edit button to cancel the college Fair "<College Fair Name>"
    And HS I successfully sign out

    Examples:
      |user    |institution              |fairStartTime|Day|StartTime|EndTime |NumVisits|StartDate|EndDate |hsEndTime|Option                                              |Option2                           |heStartTime|heTime |College Fair Name          |Date|Start Time|End Time|RSVP Deadline|Cost|Max Number of Colleges|Number of Students Expected|ButtonToClick|Non-NavSchool |cityAndStateofInstitution|cityAndStateofNon-NavianceSchool|Non-NavschoolForHE         |option|
      |PurpleHE|The University of Alabama|9:00am       |14 |9:00am  |11:25pm |3        |14       |42      |11:25pm  |No, I want to manually review all incoming requests.|Yes, accept all incoming requests. |9:        |9:    |QA Fairs for City and State|14  |0900AM    |1000AM  |12           |$25 |25                    |100                        |Save         |Homeconnection|Tuscaloosa, AL           |Oak Harbor, Washington          |Homeconnection - Washington|1        |

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
    Then HS I verify the Notification is not displayed after "Confirm" the visit in the Request Notification Tab for "<user>","<institution>","<heStartTime>"
#FOR DECLINE
    Then HS I verify the Notification "<user>","<institution>","<heStartTime>","<StartDate>" in the Request Notification Tab
    And HS I select "Decline" option for the Notification using "<user>","<heStartTime>","<institution>"
    Then HS I verify the Decline Pop-up in the Notification Tab "<user>","<institution>","<heStartTime>","<StartDate>"
    Then HS I select the "Yes, Decline" button by entering the message "QA Declined" for "<user>"
#verify notification is not displayed
    Then HS I verify the Notification is not displayed after "Decline" the visit in the Request Notification Tab for "<user>","<institution>","<heStartTime>"
    Then HS I remove the Time Slot created with "<StartDate>","<StartTime>" in Regular Weekly Hours Tab
    And HS I successfully sign out
#verify Notification is not displayed in other admin user with same school
#FOR CONFIRM
    Given HS I am logged in to Intersect HS as user type "admin"
    Then HS I verify the Notification is not displayed after "Confirm" the visit in the Request Notification Tab for "<user>","<institution>","<heStartTime>"
#FOR DECLINE
    Then HS I verify the Notification is not displayed after "Decline" the visit in the Request Notification Tab for "<user>","<institution>","<heStartTime>"
    And HS I successfully sign out

    Examples:
      |Date  |Day|StartTime|EndTime|NumVisits|StartDate|EndDate|hsEndTime |Option                                              |School            |heStartTime|heTime |user    |institution              |option|
      |7     |7  |10:56am  |12:11pm|3        |7        |49     |12:11pm   |No, I want to manually review all incoming requests.|Homeconnection    |10:56am    |10:56am|PurpleHE|The University of Alabama|1     |

  @MATCH-2881
  Scenario Outline: As a Repvisits Naviance user, i cannot view the notification while Rescheduling an appointment that has already been pushed to Naviance
#Rescheduling an appointment that has already been pushed to Naviance
#verify settings(select Manually choose which visits to publish)
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone6"
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
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone6"
    Then HS I verify the Notification is "displaying" in the Naviance Sync Tab for the following details "<University>","<heTime>","<Date>"
#verify settings(select Automatically choose which visits to publish)
    And HS I go to the Naviance Settings to select the option "Automatically publish confirmed visits."
#verify Naviance Tab
    Then HS I verify the Notification is "not displaying" in the Naviance Sync Tab for the following details "<University>","<heTime>","<Date>"
#verify settings(select Manually choose which visits to publish)
    And HS I go to the Naviance Settings to select the option "Manually choose which visits to publish. (If any)"
#reschedule visit
    Then HS I reschedule the visit for the following data "<University>","<heCalendarTime>","<Date>"
    Then HS I verify reschedule pop-up for the following data "<User>","<University>","<heCalendarTime>","<Date>"
    Then HS I reschedule a visit for the following details "<heTimefornewVisit>","<reason>","<Date>"
#verify Naviance
    Then HS I verify the Notification is "not displaying" in the Naviance Sync Tab for the following details "<University>","<heTime>","<Date>"
#Remove the time slot in Regular Weekly Hours Tab
    Then HS I remove the Time Slot created with "<StartDate>","<heStartTime>" in Regular Weekly Hours Tab
#Remove the appointment from Calendar
    And HS I select calendar in RepVisits
    Then HS I verify the calendar page using "<University>","<heCalendarTime>","<Date>","ReScheduled"
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
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone6"
    Then HS I verify the Notification is "displaying" in the Naviance Sync Tab for the following details "<University>","<heTime>","<Date>"
#reschedule visit
    Then HS I reschedule the visit for the following data "<University>","<heCalendarTime>","<Date>"
    Then HS I verify reschedule pop-up for the following data "<User>","<University>","<heCalendarTime>","<Date>"
    Then HS I reschedule a visit for the following details "<heTimefornewVisit>","<reason>","<Date>"
#verify Naviance
    Then HS I verify the Rescheduled Notification is "displaying" in the Naviance Sync Tab for an appointment that has not been pushed to Naviance using "<University>","<StartTimefornewVisit>","<Date>"
#Remove the time slot in Regular Weekly Hours Tab
    Then HS I remove the Time Slot created with "<StartDate>","<StartTimefornewVisit>" in Regular Weekly Hours Tab
#Remove the appointment from Calendar
    And HS I select calendar in RepVisits
    Then HS I verify the calendar page using "<University>","<heCalendarTime>","<Date>","ReScheduled"
    Then HS I remove the appointment from the calendar
    And HS I successfully sign out

    Examples:
      |StartTime|EndTime |NumVisits|Option                            |hsEndTime|School                   |University                |heStartTime   |heTime   |Day|Date|StartDate|EndDate|StartTimefornewVisit|User     |reason   |heTimefornewVisit|heCalendarTime|option|
      |12:34am  |12:59pm |3        |Yes, accept all incoming requests.|12:59pm  |Standalone High School 6 |The University of Alabama |12:34am       |12:34am  |14 |14  |14       |35     |12:28am             |PurpleHE |by QA    |12:28am          |12:33AM       |1     |

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
