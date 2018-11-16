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