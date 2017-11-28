@HS
Feature:  As an HS user, I want to be able to access the features of the RepVisits module.


  @MATCH-1779 @MATCH-1735 @NotInQA
  Scenario: As a HS RepVisits user I need to be able to navigate to a page for availability settings
            So that I can manage my college visits within the times that I am typically available.
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I verify the following tabs exist on the RepVisits page
      |Overview |Calendar |Availability & Settings |College Fairs |Contacts |Notifications & Tasks |
    And HS I verify the Availability & Settings tab of the RepVisits page
    And HS I successfully sign out

  @MATCH-1579
  Scenario: As a HS RepVisits user I can able to Scheduling the visits in the Availability Settings page
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    And HS I set the Accept option of RepVisits Visit Scheduling to "a maximum of..." "5" visits per day
    Then HS I go to the Counselor Community
    Then HS I verify the Availability Settings section of the Availability subtab in the Availability & Settings page in RepVisits has the following data
      |Accept           |visits per day |
      |a maximum of...  |5              |
    Then HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
    Then HS I go to the Counselor Community
    Then HS I verify the Availability Settings section of the Availability subtab in the Availability & Settings page in RepVisits has the following data
      |Accept                           |
      |visits until I am fully booked.  |
    And HS I successfully sign out

  @MATCH-1586
  Scenario: As an HS User I want to be able to use the Availability and Settings tab of RepVisits to Set Time Zone
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    And HS I verify the Availability & Settings tab of the RepVisits page
    And HS I set the RepVisits Availability & Settings time zone to "US/Central"
    And HS I click on Availability on the Availability & Settings tab in RepVisits
    Then HS I verify the time zone in Repvisits Availability & Settings is "US/Central"
    And HS I set the RepVisits Availability & Settings time zone to "US/Eastern"
    And HS I successfully sign out

  @MATCH-1625 @MATCH-1958
  Scenario: As a high school counselor using Naviance and RepVisits,
            I want to integrate my RepVisits account with Naviance college visits
            So that I do not have to manually enter appointments.
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I verify the Naviance Settings section of the Availability & Settings tab of the RepVisits page
    And HS I verify the Coming Soon message on the RepVisits Overview page
    And HS I successfully sign out

  @MATCH-1574
  Scenario: As a high school staff member,
            I want to be able to view the weekly recurring time slots that my school is available for visits
            so that colleges can manage those availabilities.
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I verify the Regular Weekly Hours section of the Availability subtab of the Availability & Settings tab in RepVisits
    And HS I successfully sign out

  @MATCH-1595
  Scenario: As a HS RepVisits user I can able to access the Visit Confirmation in the Availability Settings page
            So that i can able to fix the appointment for the High school
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I set the RepVisits Visits Confirmations option to "Yes, accept all incoming requests"
    Then HS I go to the Counselor Community
    Then HS I verify the Availability Settings section of the Availability subtab in the Availability & Settings page in RepVisits has the following data
      |Automatically confirm all visit requests? |
      |Yes, accept all incoming requests.        |
    Then HS I set the RepVisits Visits Confirmations option to "No, I want to manually review all incoming requests."
    Then HS I go to the Counselor Community
    Then HS I verify the Availability Settings section of the Availability subtab in the Availability & Settings page in RepVisits has the following data
      |Automatically confirm all visit requests?            |
      |No, I want to manually review all incoming requests. |
    Then HS I successfully sign out

  @MATCH-1803
  Scenario Outline: As a high school staff member ,
            I want to be able to define the weekly recurring appointment times that my school is available
            so that colleges can schedule appointments to visit during those times.
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I set a date using "<StartDate>" and "<EndDate>"
    When HS I add new time slot with "<Day>", "<HourStartTime>", "<HourEndTime>", "<MinuteStartTime>", "<MinuteEndTime>", "<MeridianStartTime>", "<MeridianEndTime>" and "<NumVisits>"
    Then HS I remove the Time Slot created with "<HourStartTime>", "<MinuteStartTime>" and "<MeridianStartTime>"
    Then HS I verify the Time Slot time were removed with "<HourStartTime>", "<MinuteStartTime>" and "<MeridianStartTime>"
    And HS I successfully sign out

    Examples:
      |Day              | HourStartTime | HourEndTime| MinuteStartTime| MinuteEndTime | MeridianStartTime | MeridianEndTime | NumVisits  | StartDate            |EndDate           |
      |Monday           | 5             |06          | 11             | 07             | am                | pm              | 3          | August 29 2017         |August 30 2017      |
      |Tuesday          | 5             |07          | 12             | 08             | am                | pm              | 99         | August 15 2017       |September 23 2017 |