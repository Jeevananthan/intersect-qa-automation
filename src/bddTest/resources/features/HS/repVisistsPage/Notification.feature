@HS
Feature: As an HS user, I want to be able to access the features of RepVisits-Notification module.

  @MATCH-2168
  Scenario Outline: As a Naviance RepVisits user who is looking at Notification entries in the RepVisits notifications page,
                    I want to see the City and State of each institution within each notification entry
                    so I can more easily identify the institution the notification entry is updating me about.
#Naviance
#precondition
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    And HS I set the Visit Availability of RepVisits Availability Settings to "All RepVisits Users"
    Then HS I set the RepVisits Visits Confirmations option to "<Option>"
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
#Create a visits and Fairs
    Then HS I set the date using "<StartDate>" and "<EndDate>"
    And HS I verify the update button appears and I click update button
    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>"
    Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
    And HS I successfully sign out
#Register Visits and Fairs
    Given HE I want to login to the HE app using "purpleheautomation@gmail.com" as username and "Password!1" as password
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<StartDate>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"

    And HE I search for "<School>" in RepVisits page
    Then HE I register for the "<College Fair Name>" college fair at "<School>"
    Then HE I successfully sign out

    Given HE I want to login to the HE app using "purpleheautomation+publishing@gmail.com" as username and "Password!1" as password
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<StartDate>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"

    And HE I search for "<School>" in RepVisits page
    Then HE I register for the "<College Fair Name>" college fair at "<School>"
#verify the City and State displayed in the HE Request Notification tab
    Then HE I verify the city and state "<cityAndStateofSchool>" are present in the underneath of School Name "<School>" in the Request Notification Tab
    Then HE I verify the city and state "<cityAndStateofSchool>" are present in the underneath of School Name "<School>" in the Request Notification Tab for Fairs
    Then HE I successfully sign out
#Verify the City and State displayed in the HS Request and Activity Notification Tab for Visits
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
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
    Given HE I want to login to the HE app using "purpleheautomation@gmail.com" as username and "Password!1" as password
    Then HE I verify the city and state "<cityAndStateofSchool>" are present in the underneath of School Name "<schoolForHE>" in the Activity Tab
    And HE I successfully sign out
#Verify the City and State displayed in the HS Request and Activity Notification Tab for Fairs
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
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
    Given HE I want to login to the HE app using "purpleheautomation@gmail.com" as username and "Password!1" as password
    Then HE I verify the city and state "<cityAndStateofSchool>" are present in the underneath of School Name "<schoolForHE>" in the Activity Tab for Fairs
    And HE I successfully sign out

    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I Click on the View Details button for the College Fair Event "<College Fair Name>"
    Then HS I select Edit button to cancel the college Fair "<College Fair Name>"
    And HS I set the Visit Availability of RepVisits Availability Settings to "All RepVisits Users"
    Then HS I set the RepVisits Visits Confirmations option to "<Option2>"
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
    And HS I successfully sign out

    Examples:
      |user    |institution              |fairStartTime|Day|StartTime|EndTime |NumVisits|StartDate|EndDate |hsEndTime|Option                                              |Option2                           |School              |heStartTime|heTime |College Fair Name          |Date|Start Time|End Time|RSVP Deadline|Cost|Max Number of Colleges|Number of Students Expected|ButtonToClick|cityAndStateofInstitution|cityAndStateofSchool  |schoolForHE                 |
      |PurpleHE|The University of Alabama|9:00am       |7  |10:25am  |11:25pm |3        |7        |42      |11:25pm  |No, I want to manually review all incoming requests.|Yes, accept all incoming requests.|Int Qa High School 4|10:        |10:    |QA Fairs for City and State|14  |0900AM    |1000AM  |12           |$25 |25                    |100                        |Save         |Tuscaloosa, AL           |Liberty Township, Ohio|Int Qa High School 4 - Ohio |

  @MATCH-2168
  Scenario Outline: As a Non-Naviance RepVisits user who is looking at Notification entries in the RepVisits notifications page,
                    I want to see the City and State of each institution within each notification entry
                    so I can more easily identify the institution the notification entry is updating me about.
#Non-Naviance
#precondition
    Given HS I want to login to the HS app using "purpleheautomation+admin@gmail.com" as username and "Password!1" as password
    And HS I set the Visit Availability of RepVisits Availability Settings to "All RepVisits Users"
    Then HS I set the RepVisits Visits Confirmations option to "<Option>"
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
#Create a visits and Fairs
    Then HS I set the date using "<StartDate>" and "<EndDate>"
    And HS I verify the update button appears and I click update button
    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>"
    Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
    And HS I successfully sign out
#Register Visits and Fairs
    Given HE I want to login to the HE app using "purpleheautomation@gmail.com" as username and "Password!1" as password
    And HE I search for "<Non-NavSchool>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<Non-NavSchool>" using "<StartDate>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<Non-NavSchool>" using "<heTime>" and "<hsEndTime>"

    And HE I search for "<Non-NavSchool>" in RepVisits page
    Then HE I register for the "<College Fair Name>" college fair at "<Non-NavSchool>"
    Then HE I successfully sign out

    Given HE I want to login to the HE app using "purpleheautomation+publishing@gmail.com" as username and "Password!1" as password
    And HE I search for "<Non-NavSchool>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<Non-NavSchool>" using "<StartDate>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<Non-NavSchool>" using "<heTime>" and "<hsEndTime>"

    And HE I search for "<Non-NavSchool>" in RepVisits page
    Then HE I register for the "<College Fair Name>" college fair at "<Non-NavSchool>"
#verify the City and State displayed in the HE Request Notification tab
    Then HE I verify the city and state "<cityAndStateofNon-NavianceSchool>" are present in the underneath of School Name "<Non-NavSchool>" in the Request Notification Tab
    Then HE I verify the city and state "<cityAndStateofNon-NavianceSchool>" are present in the underneath of School Name "<Non-NavSchool>" in the Request Notification Tab for Fairs
    Then HE I successfully sign out
#Verify the City and State displayed in the HS Request and Activity Notification Tab for Visits
    Given HS I want to login to the HS app using "purpleheautomation+admin@gmail.com" as username and "Password!1" as password
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
    Given HE I want to login to the HE app using "purpleheautomation@gmail.com" as username and "Password!1" as password
    Then HE I verify the city and state "<cityAndStateofNon-NavianceSchool>" are present in the underneath of School Name "<Non-NavschoolForHE>" in the Activity Tab
    And HE I successfully sign out
#Verify the City and State displayed in the HS Request and Activity Notification Tab for Fairs
    Given HS I want to login to the HS app using "purpleheautomation+admin@gmail.com" as username and "Password!1" as password
    Then HS I verify the Notification "<user>","<institution>","<fairStartTime>","<Date>" in the Request Notification Tab for Fairs
    Then HS I verify the city and state "<cityAndStateofInstitution>" are present in the underneath of Institiution Name "<institution>" in the Request Notification Tab for Fairs
    And HS I select "Confirm" option for the Notification using "<user>","<fairStartTime>","<institution>" for Fairs

    Then HS I verify the Notification "<user>","<institution>","<fairStartTime>","<Date>" in the Request Notification Tab for Fairs
    And HS I select "Decline" option for the Notification using "<user>","<fairStartTime>","<institution>" for Fairs
    Then HS I verify the Decline Pop-up in the Notification Tab "<user>","<institution>","<fairStartTime>","<Date>" for Fairs
    Then HS I select the "Yes, Decline" button by entering the message "QA Declined" for "<user>"
    Then HS I verify the city and state "<cityAndStateofInstitution>" are present in the underneath of Institiution Name "<institution>" in the Activity Tab for Fairs
    Then HS I set the RepVisits Visits Confirmations option to "<Option2>"
    And HS I successfully sign out
#Verify the City and State displayed in the HE Activity Notification Tab
    Given HE I want to login to the HE app using "purpleheautomation@gmail.com" as username and "Password!1" as password
    Then HE I verify the city and state "<cityAndStateofNon-NavianceSchool>" are present in the underneath of School Name "<Non-NavschoolForHE>" in the Activity Tab for Fairs
    And HE I successfully sign out

    Given HS I want to login to the HS app using "purpleheautomation+admin@gmail.com" as username and "Password!1" as password
    Then HS I Click on the View Details button for the College Fair Event "<College Fair Name>"
    Then HS I select Edit button to cancel the college Fair "<College Fair Name>"
    And HS I set the Visit Availability of RepVisits Availability Settings to "All RepVisits Users"
    Then HS I set the RepVisits Visits Confirmations option to "<Option2>"
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
    And HS I successfully sign out

    Examples:
      |user    |institution              |fairStartTime|Day|StartTime|EndTime |NumVisits|StartDate|EndDate |hsEndTime|Option                                              |Option2                           |heStartTime|heTime |College Fair Name          |Date|Start Time|End Time|RSVP Deadline|Cost|Max Number of Colleges|Number of Students Expected|ButtonToClick|Non-NavSchool |cityAndStateofInstitution|cityAndStateofNon-NavianceSchool|Non-NavschoolForHE         |
      |PurpleHE|The University of Alabama|9:00am       |14 |10:25am  |11:25pm |3        |14       |42      |11:25pm  |No, I want to manually review all incoming requests.|Yes, accept all incoming requests.|10:        |10:    |QA Fairs for City and State|14  |0900AM    |1000AM  |12           |$25 |25                    |100                        |Save         |Homeconnection|Tuscaloosa, AL           |Oak Harbor, Washington          |Homeconnection - Washington|

  @MATCH-2565
  Scenario Outline: As a Repvisits admin users, I want to view the all requests should be available for all Admin user until one of the user approves/denies the request.
#precondition
    Given HS I want to login to the HS app using "purplehsautomations@gmail.com" as username and "Password!1" as password
    Then HS I set the RepVisits Visits Confirmations option to "<Option>"
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."

    Then HS I set the date using "<StartDate>" and "<EndDate>"
    And HS I verify the update button appears and I click update button
    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>"
    And HS I successfully sign out

    Then HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<Date>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"
    Then HE I successfully sign out

    Then HE I am logged in to Intersect HE as user type "publishing"
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<Date>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"
    Then HE I successfully sign out
#verify notification is displayed
#verify Notification in other admin user with same school
    Given HS I want to login to the HS app using "purpleheautomation+admin@gmail.com" as username and "Password!1" as password
    Then HS I verify the Notification "<user>","<institution>","<heStartTime>","<StartDate>" in the Request Notification Tab
    And HS I successfully sign out
#verify Notification in admin user
    Given HS I want to login to the HS app using "purplehsautomations@gmail.com" as username and "Password!1" as password
    Then HS I verify the Notification "<user>","<institution>","<heStartTime>","<StartDate>" in the Request Notification Tab
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
    Given HS I want to login to the HS app using "purpleheautomation+admin@gmail.com" as username and "Password!1" as password
    Then HS I verify the Notification is not displayed after "Confirm" the visit in the Request Notification Tab for "<user>","<institution>","<heStartTime>"
#FOR DECLINE
    Then HS I verify the Notification is not displayed after "Decline" the visit in the Request Notification Tab for "<user>","<institution>","<heStartTime>"
    And HS I successfully sign out

    Examples:
      |Date  |Day|StartTime|EndTime|NumVisits|StartDate|EndDate|hsEndTime |Option                                              |School            |heStartTime|heTime |user    |institution              |
      |7     |7  |10:56am  |12:11pm|3        |7        |49     |12:11pm   |No, I want to manually review all incoming requests.|Homeconnection    |10:56am    |10:56am|PurpleHE|The University of Alabama|

