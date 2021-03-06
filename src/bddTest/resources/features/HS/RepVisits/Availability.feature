@HS @HS2
Feature:  HS - RepVisits - Availability - As an HS user, I should be able to set up my Visit availability


  @MATCH-5139
  Scenario Outline: As a HS RepVisits user I can be able to add a time slot in Regular Weekly Hours
    Given HS I am logged in to Intersect HS as user type "Availability1"
    And HS I set the Visit Availability of RepVisits Availability Settings to "All RepVisits Users"
    Then HS I set the RepVisits Visits Confirmations option to "<Option>"
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
#Create a visit and verify the time slot in regular weekly hours
    Then HS I set the date using "<StartDate>" and "<EndDate>"
    And HS I verify the update button appears and I click update button
    Then HS I clear the time slot for the particular day "<StartDate>" in Regular Weekly Hours Tab
    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>" with "<option>"
    Then HS I verify the time slot is added with "<StartDate>","<StartTime>","<EndTime>" in Regular Weekly Hours Tab
    And HS I successfully sign out
#Verify and register the appointment in he
    Then HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<StartDate>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"
 #Verify appointment from HE calendar
    And HE I verify the visit details are present in the calendar using "<School>","<StartDate>","<StartTime>"

#Verify and remove the appointment from HS calendar
    Given HS I am logged in to Intersect HS as user type "Availability1"
    Then HS I verify and select an appointment in calendar page using "<institution>","<StartTime>","<StartDate>","Scheduled"
    Then HS I remove the appointment from the calendar
    Then HS I remove the Time Slot created with "<StartDate>","<StartTime>" in Regular Weekly Hours Tab
    And HS I successfully sign out

    Examples:
      |institution              |Day|StartTime|EndTime |NumVisits|StartDate|EndDate |hsEndTime|Option                            |School          |heStartTime|heTime | option|
      |The University of Alabama|14 |10:25am  |11:25pm |3        |14       |28      |11:25pm  |Yes, accept all incoming requests.|Twin Oaks School|10:25am    |10:25am|1      |


  @MATCH-1779 @MATCH-1735 @NotInQA
  Scenario: As a HS RepVisits user I need to be able to navigate to a page for availability settings
  So that I can manage my college visits within the times that I am typically available.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I verify the following tabs exist on the RepVisits page
      |Overview |Calendar |Availability & Settings |College Fairs |Contacts |Notifications & Tasks |
    And HS I verify the Availability & Settings tab of the RepVisits page
    And HS I successfully sign out

  @MATCH-1579 @MATCH-2124
  Scenario: As a HS RepVisits user I can able to Scheduling the visits in the Availability Settings page
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    And HS I set the Accept option of RepVisits Visit Scheduling to "a maximum of..." "5" visits per day
    Then HS I verify the success Message "Great!You've updated your settings." in Availability Settings page
    Then HS I go to the Counselor Community
    Then HS I verify the Availability Settings section of the Availability subtab in the Availability & Settings page in RepVisits has the following data
      |Accept           |visits per day |
      |a maximum of...  |5              |
    Then HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
    Then HS I verify the success Message "Great!You've updated your settings." in Availability Settings page
    Then HS I go to the Counselor Community
    Then HS I verify the Availability Settings section of the Availability subtab in the Availability & Settings page in RepVisits has the following data
      |Accept                           |
      |visits until I am fully booked.  |
    And HS I successfully sign out

  @MATCH-1586 @MATCH-1945 @MATCH-2124
  Scenario: As an HS User I want to be able to use the Availability and Settings tab of RepVisits to Set Time Zone
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    And HS I verify the Availability & Settings tab of the RepVisits page
    And HS I set the RepVisits Availability & Settings time zone to "US/Central"
    Then HS I verify the success Message "Great!You've updated your settings." in Availability Settings page
    And HS I click on Availability on the Availability & Settings tab in RepVisits
    Then HS I verify the time zone in Repvisits Availability & Settings is "US/Central"
    And HS I set the RepVisits Availability & Settings time zone to "US/Eastern"
    Then HS I verify the success Message "Great!You've updated your settings." in Availability Settings page
    And HS I successfully sign out

  @MATCH-1574 @MATCH-1802 @MATCH-2124 @MATCH-4262
  Scenario Outline: As a high school staff member,
  I want to be able to view the weekly recurring time slots that my school is available for visits
  so that colleges can manage those availabilities.
    Given HS I am logged in to Intersect HS as user type "Availability1"
    Then HS I set the visit availability dates to "<StartDate>" through "<EndDate>"
    Then HS I verify the success Message "Great!You've updated your settings." in Availability Settings page
    When HS I add new time slot with "<Day>", "<HourStartTime>", "<HourEndTime>", "<MinuteStartTime>", "<MinuteEndTime>", "<MeridianStartTime>", "<MeridianEndTime>" and "<NumVisits>"
    Then HS I verify the Time Slot time were added with "<HourStartTime>", "<MinuteStartTime>" and "<MeridianStartTime>"
    And HS I successfully sign out

    Examples:
      |Day              | HourStartTime | HourEndTime| MinuteStartTime| MinuteEndTime | MeridianStartTime | MeridianEndTime | NumVisits  | StartDate            |EndDate           |
      |Monday           | 1             |02          | 11             | 07             | am                | am              | 3          | August 29 2019         |August 30 2019      |

  @MATCH-1574
  Scenario: As a high school staff member,
  I want to be able to view the weekly recurring time slots that my school is available for visits
  so that colleges can manage those availabilities.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I verify the Regular Weekly Hours section of the Availability subtab of the Availability & Settings tab in RepVisits
    And HS I successfully sign out

  @MATCH-1595 @MATCH-2124
  Scenario: As a HS RepVisits user I can able to access the Visit Confirmation in the Availability Settings page
  So that i can able to fix the appointment for the High school
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I set the RepVisits Visits Confirmations option to "Yes, accept all incoming requests."
    Then HS I verify the success Message "Great!You've updated your settings." in Availability Settings page
    Then HS I go to the Counselor Community
    Then HS I verify the Availability Settings section of the Availability subtab in the Availability & Settings page in RepVisits has the following data
      |Automatically confirm all visit requests? |
      |Yes, accept all incoming requests.        |
    Then HS I set the RepVisits Visits Confirmations option to "No, I want to manually review all incoming requests."
    Then HS I verify the success Message "Great!You've updated your settings." in Availability Settings page
    Then HS I go to the Counselor Community
    Then HS I verify the Availability Settings section of the Availability subtab in the Availability & Settings page in RepVisits has the following data
      |Automatically confirm all visit requests?            |
      |No, I want to manually review all incoming requests. |
    And HS I successfully sign out

  @MATCH-1803 @MATCH-2124
  Scenario Outline: As a high school staff member ,
  I want to be able to define the weekly recurring appointment times that my school is available
  so that colleges can schedule appointments to visit during those times.
    Given HS I am logged in to Intersect HS as user type "Availability1"
    Then HS I set a date using "<StartDate>" and "<EndDate>"
    Then HS I verify the success Message "Great!You've updated your settings." in Availability Settings page
    When HS I add new time slot with "<Day>", "<HourStartTime>", "<HourEndTime>", "<MinuteStartTime>", "<MinuteEndTime>", "<MeridianStartTime>", "<MeridianEndTime>" and "<NumVisits>"
    Then HS I remove the Time Slot created with "<HourStartTime>", "<MinuteStartTime>" and "<MeridianStartTime>"
    Then HS I verify the Time Slot time were removed with "<HourStartTime>", "<MinuteStartTime>" and "<MeridianStartTime>"
    And HS I successfully sign out

    Examples:
      |Day              | HourStartTime | HourEndTime| MinuteStartTime| MinuteEndTime | MeridianStartTime | MeridianEndTime | NumVisits  | StartDate  |EndDate  |
      |Monday           | 6             |06          | 11             | 07            | am                | pm              | 3          | 14         |28       |
#      |Tuesday          | 5             |07          | 12             | 08             | am                | pm              | 99         | August 15 2018       |September 23 2018 |


  @MATCH-1577 @MATCH-2124
  Scenario Outline: As a high school community member, I want to be able to indicate the date ranges for which I am
  available for college visits, so that colleges know when to visit my high school.
    Given HS I am logged in to Intersect HS as user type "Availability1"
    Then HS I set the visit availability dates to "<StartDate>" through "<EndDate>"
    Then HS I verify the success Message "Great!You've updated your settings." in Availability Settings page
    Then HS I verify the "<StartDate>" and "<EndDate>" date was set in the calendar
    And HS I successfully sign out
    Examples:
      |StartDate            |EndDate      |
      |July 19 2019         |July 14 2020 |
      |August 15 2019      |June 11 2020 |
      |July 19 2019         |July 14 2020 |

  @MATCH-1578 @MATCH-2124
  Scenario: As a HS RepVisits user I want to be able to use the Availability and Settings tab of RepVisits to Set Visit Scheduling
  I want to able to set the scheduling new visits in advance and set the cancelling or rescheduling visits in advance
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "56"
    Then HS I verify the success Message "Great!You've updated your settings." in Availability Settings page
    Then HS I go to the Counselor Community
    Then HS I verify the Availability Settings section of the Availability subtab in the Availability & Settings page in RepVisits has the following data
      |Prevent colleges from scheduling new visits less than |
      |56|
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "45"
    Then HS I verify the success Message "Great!You've updated your settings." in Availability Settings page
    Then HS I go to the Counselor Community
    Then HS I verify the Availability Settings section of the Availability subtab in the Availability & Settings page in RepVisits has the following data
      |Prevent colleges from cancelling or rescheduling less than |
      |45|
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "5"
    Then HS I verify the success Message "Great!You've updated your settings." in Availability Settings page
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    Then HS I verify the success Message "Great!You've updated your settings." in Availability Settings page
    And HS I successfully sign out

  @MATCH-1585 @MATCH-2124
  Scenario: As a high school community member, I want to publish or hide my college visit availability,
  so that I can control when colleges can only schedule college visits.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I set the Visit Availability of RepVisits Availability Settings to "Only Me"
    Then HS I verify the success Message "Great!You've updated your settings." in Availability Settings page
    Then HS I go to the Counselor Community
    Then HS I verify the Visit Availability Section for the Availability & Settings tab of the RepVisits with "Only Me"
    And HS I set the Visit Availability of RepVisits Availability Settings to "All RepVisits Users"
    Then HS I verify the success Message "Great!You've updated your settings." in Availability Settings page
    Then HS I go to the Counselor Community
    Then HS I verify the Visit Availability Section for the Availability & Settings tab of the RepVisits with "All RepVisits Users"
    And HS I successfully sign out

