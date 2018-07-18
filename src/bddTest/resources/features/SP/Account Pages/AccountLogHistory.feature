@SP
Feature: Hobsons Support - View Institution Log History
         As a Hobsons Staff Administrator or Support user I need to be able to view and filter by date an audit log of all other Hobsons Staff
         activity per institutional account in the admin page for system security, auditing, and troubleshooting.

  Scenario: As a Hobsons Sales Ops user I cannot view an Institution's Log History
    Given SP I am logged in to the Admin page as a Sales Ops user
    When SP I select "The University of Alabama" from the institution dashboard
    And SP I am able to view the individual account page
    Then SP I do not have access to View Log History
    And SP I successfully sign out

  Scenario: As a Hobsons Support user I can view an Institution's Log History
    Given SP I am logged in to the Admin page as a Support user
    When SP I select "The University of Alabama" from the institution dashboard
    And SP I am able to view the individual account page
    Then SP I do have access to View Log History
    And SP I successfully sign out

  Scenario: As a Hobsons Admin user I can view an Institution's Log History
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I go to the Log History for "Bowling Green State University-Main Campus" from the institution dashboard
    And SP I successfully sign out

  Scenario: As a Hobsons Super Admin user I can view an Institution's Log History
    Given SP I am logged in to the Admin page as a Super Admin user
    Then SP I go to the Log History for "Bowling Green State University-Main Campus" from the institution dashboard
    And SP I successfully sign out

  @MATCH-1682 @MATCH-2124
  Scenario Outline: As a Support App I need to add log entries to HE Accounts View Log History page when Support users are using the Login As feature
                    so the appropriate information is available for auditing.
  #precondition
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone6"
    And HS I set the Visit Availability of RepVisits Availability Settings to "All RepVisits Users"
    Then HS I set the RepVisits Visits Confirmations option to "<Option1>"
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
  #visit
    Then HS I set the date using "<StartDate>" and "<EndDate>"
    And HS I verify the update button appears and I click update button
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>"
  #Fairs
    Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
    And HS I successfully sign out

    Given SP I am logged in to the Admin page as an Admin user
    Then SP I go to the users list for "<institution>" from the institution dashboard
    And SP I "Login As" the user account for "<user>"

    Then HE I verify the "You're currently logged in as <profileName> from <institution>. Changes you make will reflect in their account." message in the homepage
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
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
    And HS I successfully sign out

    Examples:
      |user                                   |supportUser         |profileName |institution              |Day |StartTime|EndTime |NumVisits|StartDate|EndDate |hsEndTime |Option1                                              |Option2                           |School                  |heStartTime |heTime  |College Fair Name     |Date|Start Time|End Time|RSVP Deadline|Cost|Max Number of Colleges|Number of Students Expected| ButtonToClick |
      |purpleheautomation+LogHistory@gmail.com|Match Support UI QA4|purple he   |Alpena Community College |7   |11:20am  |12:25pm |3        |7        |42      |12:25pm   |No, I want to manually review all incoming requests. |Yes, accept all incoming requests.|Standalone High School 6|11:         |11:     |QAs Fairs tests       |7   |0900AM    |1000AM  |5            |$25 |25                    |100                        | Save          |
