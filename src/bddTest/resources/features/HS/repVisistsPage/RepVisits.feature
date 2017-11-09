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

  @MATCH-1577
  Scenario Outline: As a high school community member,
            I want to be able to indicate the date ranges for which I am available for college visits,
            so that colleges know when to visit my high school.
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I set a date using "<StartDate>" and "<EndDate>"
    Then HS I verify the "<StartDate>" and "<EndDate>" date was set in the calendar
    And HS I successfully sign out

    Examples:
      |StartDate            |EndDate           |
      |July 23 2017         |June 23 2018      |
      |August 15 2017       |September 23 2017 |

  @MATCH-1578
  Scenario: As a HS RepVisits user I want to be able to use the Availability and Settings tab of RepVisits to Set Visit Scheduling
  I want to able to set the scheduling new visits in advance and set the cancelling or rescheduling visits in advance
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "56"
    Then HS I go to the Counselor Community
    Then HS I verify the Availability Settings section of the Availability subtab in the Availability & Settings page in RepVisits has the following data
      |Prevent colleges from scheduling new visits less than |
      |56|
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "45"
    Then HS I go to the Counselor Community
    Then HS I verify the Availability Settings section of the Availability subtab in the Availability & Settings page in RepVisits has the following data
      |Prevent colleges from cancelling or rescheduling less than |
      |45|
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "5"
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    And HS I successfully sign out

  @MATCH-1585
  Scenario: As a high school community member, I want to publish or hide my college visit availability,
            so that I can control when colleges can only schedule college visits.
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I set the Visit Availability of RepVisits Availability Settings to "Only Me"
    Then HS I go to the Counselor Community
    Then HS I verify the Visit Availability Section for the Availability & Settings tab of the RepVisits with "Only Me"
    And HS I set the Visit Availability of RepVisits Availability Settings to "All RepVisits Users"
    Then HS I go to the Counselor Community
    Then HS I verify the Visit Availability Section for the Availability & Settings tab of the RepVisits with "All RepVisits Users"
    And HS I successfully sign out

   @MATCH-1944
   Scenario: As a new RepVisits user,I want a setup wizard with an introduction that describes what the system does
             so that I can be encouraged to set up my RepVisits account.
      Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
      Then HS I am verifying the welcome milestone in setup wizard
      And HS I click the Get Started button in the welcome milestone page
      And HS I navigate to college fairs,visits through availability option
      And HS I successfully sign out

   @MATCH-1945
   Scenario: As a new RepVisits user,I want the setup wizard to confirm my school's timezone
             So that I can be sure my appointments will be scheduled at the right time.
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    And HS I verify the repvisit setup wizard displayed for high school information
    Then HS I check the time zone is selected as "America/Mexico_City" and change it to "America/New_York"
    And HS I navigate to college fairs,visits through availability option
    And HS I successfully sign out

  @MATCH-1776
  Scenario Outline: As a HS RepVisits user I want to able to create a new fair in the college fair
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
    Then HS I verify the Success Message for the College Fair "<College Fair Name>"
    Then HS I Click on the "Close" button in the success page of the college fair
    Then HS I Click the View Details button for the College Fair Event for "<College Fair Name>"
    Then HS I Click on the "Edit" button in the College Fair Details Page
    Then HS I HS I set the data to the Edit a college Fair "<College Fair Name>","<VerifyDateEdit>","<verifyStartTime>","<verifyEndTime>","<VerifyRSVPDateEdit>","<Cost>","<MaxNumberofColleges>","<NumberofStudentsExpected>"
    And HS I successfully sign out

    Examples:
      |College Fair Name    |Date            |Start Time|End Time|RSVP Deadline    |Cost|Max Number of Colleges|Number of Students Expected| ButtonToClick |Cost|MaxNumberofColleges|NumberofStudentsExpected|ButtonToClick|VerifyDateEdit       |VerifyRSVPDateEdit     |verifyStartTime|verifyEndTime|
      |QA Test Fair publish |December 12 2017|0900AM    |1000AM  |November 16 2017 |$25 |25                    |100                        | Save          |$25 |25                 |100                     |Save         |Tuesday, Dec 12, 2017|Wednesday, Nov 15, 2017|09:00          |10:00        |
#        |QA Tests for Fair |December 12 2017|0900AM    |1000AM  |November 16 2017 |$25 |25                    |100                        | Save          |$25 |25                 |100                     |Save         |Tuesday, Dec 12, 2017|Wednesday, Nov 15, 2017|09:00          |10:00        |
#        |QA Tests for Fairs|December 12 2017|0900AM    |1000AM  |November 16 2017 |$25 |25                    |100                        | Save          |$25 |25                 |100                     |Save         |Tuesday, Dec 12, 2017|Wednesday, Nov 15, 2017|09:00          |10:00        |
