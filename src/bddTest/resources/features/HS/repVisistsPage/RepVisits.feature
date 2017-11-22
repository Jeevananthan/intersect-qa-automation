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

  @MATCH-1586 @MATCH-1945
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

  @MATCH-1574 @MATCH-1802
  Scenario Outline: As a high school staff member,
  I want to be able to view the weekly recurring time slots that my school is available for visits
  so that colleges can manage those availabilities.
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I set the visit availability dates to "<StartDate>" through "<EndDate>"
    When HS I add new time slot with "<Day>", "<HourStartTime>", "<HourEndTime>", "<MinuteStartTime>", "<MinuteEndTime>", "<MeridianStartTime>", "<MeridianEndTime>" and "<NumVisits>"
    Then HS I verify the Time Slot time were added with "<HourStartTime>", "<MinuteStartTime>" and "<MeridianStartTime>"
    And HS I successfully sign out

    Examples:
      |Day              | HourStartTime | HourEndTime| MinuteStartTime| MinuteEndTime | MeridianStartTime | MeridianEndTime | NumVisits  | StartDate            |EndDate           |
      |Monday           | 1             |02          | 11             | 7             | am                | am              | 3          | August 29 2017         |August 30 2017      |
#      |Monday           | 2             |03          | 11             | 7             | am                | am              | 3          | July 23 2017         |June 23 2018      |
#      |Monday           | 3             |04          | 11             | 7             | am                | am              | 3          | July 23 2017         |June 23 2018      |
#      |Monday           | 4             |05          | 11             | 7             | am                | am              | 3          | July 23 2017         |June 23 2018      |
#      |Monday           | 5             |06          | 11             | 7             | am                | am              | 3          | July 23 2017         |June 23 2018      |
#      |Monday           | 6             |07          | 11             | 7             | am                | am              | 3          | July 23 2017         |June 23 2018      |
#      |Monday           | 7             |08          | 11             | 7             | am                | am              | 3          | July 23 2017         |June 23 2018      |
#      |Monday           | 8             |09          | 11             | 7             | am                | am              | 3          | July 23 2017         |June 23 2018      |
#      |Monday           | 9             |10          | 11             | 7             | am                | am              | 3          | July 23 2017         |June 23 2018      |
#      |Monday           | 10             |11          | 11             | 7             | am                |am              | 3          | July 23 2017         |June 23 2018      |
#      |Monday           | 11             |12          | 11             | 7             | am                | am              | 3          | July 23 2017         |June 23 2018      |
#      |Monday           | 12             |06          | 11             | 7             | am                | pm              | 3          | July 23 2017         |June 23 2018      |
#      |Monday           | 1             |02          | 11             | 7             | pm                | pm              | 3          | July 23 2017         |June 23 2018      |
#      |Monday           | 2             |03          | 11             | 7             | pm                | pm              | 3          | July 23 2017         |June 23 2018      |
#      |Monday           | 3             |04          | 11             | 7             | pm                | pm              | 3          | July 23 2017         |June 23 2018      |
#      |Monday           | 4             |05          | 11             | 7             | pm                | pm              | 3          | July 23 2017         |June 23 2018      |
#      |Monday           | 5             |06          | 11             | 7             | pm                | pm              | 3          | July 23 2017         |June 23 2018      |
#      |Monday           | 6             |07          | 11             | 7             | pm                | pm              | 3          | July 23 2017         |June 23 2018      |
#      |Monday           | 7             |08          | 11             | 7             | pm                | pm              | 3          | July 23 2017         |June 23 2018      |
#      |Monday           | 8             |09          | 11             | 7             | pm                | pm              | 3          | July 23 2017         |June 23 2018      |
#      |Monday           | 9             |10          | 11             | 7             | pm                | pm              | 3          | July 23 2017         |June 23 2018      |
#      |Monday           | 10             |11          | 11             | 7             | pm                | pm              | 3          | July 23 2017         |June 23 2018      |
#      |Monday           | 11             |12          | 11             | 7             | pm                | pm              | 3          | July 23 2017         |June 23 2018      |
#      |Monday           | 12             |06          | 11             | 7             | pm                | pm              | 3          | July 23 2017         |June 23 2018      |
#      |Tuesday          | 5             |07          | 12             | 8             | pm                | pm              | 99         | August 15 2017       |September 23 2017 |


  @MATCH-1574
  Scenario: As a high school staff member,
            I want to be able to view the weekly recurring time slots that my school is available for visits
            so that colleges can manage those availabilities.
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I verify the Regular Weekly Hours section of the Availability subtab of the Availability & Settings tab in RepVisits
    And HS I successfully sign out

  @MATCH-1962
  Scenario: As a HIGH School User, I want to verify College Fair Blank DashBoard
    Given HS I am logged in to Intersect HS through Naviance with account "stndalonehs2" and username "school-user" and password "password"
    And HS I Navigate to College Fairs tab of the Repvisits Page
    And HS I verify the College Fair Blank DashBoard Message
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

  @MATCH-1575
  Scenario Outline: As a high school community member,
  I want to be able to automatically block off U.S. Holidays
  so that I do not have to manually block each holiday.
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HE I set and verify that "<Holiday>" is blocked on the Blocked Days page
    And HS I successfully sign out
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I search for "Int QA High School 4" in RepVisits page using "Arlington, VA" and verify that "<Date>" is blocked
    Examples:
      |Holiday               | Date |
      |LABOR_DAY             | September 04 2017   |
      |COLUMBUS_DAY          | October 9 2017      |
      |VETERANS_DAY          | November 10 2017    |
      |THANKSGIVING_DAY      | November 23 2017    |
      |DAY_AFTER_THANKSGIVING| November 24 2017    |
      |CHRISTMAS_EVE         | December 24 2017    |
      |CHRISTMAS_DAY         | December 25 2017    |
      |NEW_YEAR_EVE          | December 31 2017    |
      |NEW_YEAR_DAY          | January  01 2018    |
      |MARTIN_LUTHER_DAY     | January 15 2018     |
      |PRESIDENTS_DAY        | February 19 2018    |
      |MEMORIAL_DAY          | May 28 2018         |
      |INDEPENDENCE_DAY      | July 04 2018        |



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

  @MATCH-1580
  Scenario: As a HS Repvisit user Set Repvist availability Messages Instructions for HE on scheduling page
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then  HS I verify the Availability & Settings tab of the RepVisits page
    And HS I verify the UI of the Messaging Options Page
    And HS I set the Special Instructions Text as "AUTOMATION Welcome message. This message is to test the maximum limit of characters in messages. As a HS Repvisits user We will add this message. Ans same message will be displayed in HE for Repvisits to schedule their visits. Maximum characters allowed are 250 . This text contains more than 250 characters";
    And HS I verify the Special Instructions are "AUTOMATION Welcome message. This message is to test the maximum limit of characters in messages. As a HS Repvisits user We will add this message. Ans same message will be displayed in HE for Repvisits to schedule their visits. Maximum characters allo"
    And HS I successfully sign out
    And HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "Int QA High School 4" in RepVisits
    And HE I select "Int QA High School 4" in "Arlington, VA" from the RepVisits intermediate search results
    And HE I verify Repvisits Special Instructions for School are "AUTOMATION Welcome message. This message is to test the maximum limit of characters in messages. As a HS Repvisits user We will add this message. Ans same message will be displayed in HE for Repvisits to schedule their visits. Maximum characters allo"
    And HE I successfully sign out

  @MATCH-1496
  Scenario: As an HS user I want the Intersect left navigation bar to be better organized and labeled.
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I verify the left navigation bar and section breadcrumbs are as follows
      | Awareness | Counselor Community |
    Then HS I verify the left navigation bar and section breadcrumbs are as follows
      |Presence |RepVisits |
    And HS I successfully sign out

  @MATCH-1576
  Scenario Outline: As a HS RepVisits user, I want to be able to Block specific days and date ranges in the Holidays tab of the Availability and Settings page
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I set the Blocked date as "<BlockedDate>" and select the reason as "<Reason>" in the Holiday tab
    Then HS I go to the Counselor Community
    Then HS I verify the "<StartDate>" and "<EndDate>" date with "<Reason>" was present in the Holidays tab in the Availability & Settings page in RepVisits
    Then HS I click the Remove option for the "<StartDate>" and "<EndDate>" in the Holiday tab
    Then HS I go to the Counselor Community
    Then HS I verify the "<StartDate>" and "<EndDate>" date with "<Reason>" was not present in the Holidays tab in the Availability & Settings page in RepVisits
    And HS I successfully sign out
      Examples:
      |BlockedDate          |Reason       |StartDate  | EndDate   |
      |July 14 2018         |School Event |2018-07-14 | 2018-07-14|

  @MATCH-1756
  Scenario:As an HS Community member,I need to view a calendar of my appointments
           so that I can easily see what my day/week/month schedule looks like.
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    And HS I verify the calendar view in RepVisits
    And HS I successfully sign out

  @MATCH-1949
  Scenario: As a new RepVisits user,I want the setup wizard to help me configure my calendars
            so that I can see all my calendar information in one place.
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    And HS I verify the Calendar Sync Milestone in the setup wizard of repvisits
    And HS I successfully sign out

  @MATCH-1948
  Scenario: As a new RepVisits user,I want the setup wizard to help me configure my school's contacts.
            so that I can be sure internal notifications will be routed to the people who need the information.
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    And HS I change the primary contact from "IAM Purple" to "Jennifer TestAdmin" and verify that the save option is working
    And HS I successfully sign out

  @MATCH-1946
  Scenario Outline: As a new RepVisits user,I want the setup wizard to walk me through my availability settings
  so that I can be sure my RepVisits account is properly set up.
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I go to welcome wizard of the repvisits
    And HS I navigate to "Availability" wizard in repvisits
    Then HS I add the time slot in "Monday" with start time as "05:00AM" and end time as "02:00PM" and "5" vistis
    And HS I navigate to sub tab "Blocked Days" in availability wizard
    Then HS I select "LABOR_DAY" in blocked days tab and verify saving option works successfully
    And HS I navigate to sub tab "Exceptions" in availability wizard
    Then HS I change to "next week" in exception and verify saving option works successfully
    And HS I navigate to sub tab "Availability Settings" in availability wizard
    Then HS I set the RepVisits Visits Confirmations option to "<Visits Confirmation>","<Prevent colleges scheduling new visits>","<Prevent colleges cancelling or rescheduling>"
    And HS I successfully sign out

    Examples:
      |Visits Confirmation                                 |Prevent colleges scheduling new visits|Prevent colleges cancelling or rescheduling|
      |No, I want to manually review all incoming requests.|5                                     |5                                          |

  @MATCH-2171
  Scenario Outline: when we initially created the first and last days for availability, they were not developed to persist.
                    Instead they're changed / set each time that availability is set. This ticket is to persist the first and last dates
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I set the visit availability dates to "<StartDate>" through "<EndDate>"
    And HS I verify the update button appears and I click update button
    Then HS I go to the Counselor Community
    Then HS I verify the StartDate is set to "<verifyStartDate>" and EndDate is set to "<verifyEndDate>"
    And HS I successfully sign out

   Examples:
     |StartDate     |EndDate        |verifyStartDate  |verifyEndDate   |
     |June 14 2018  |July 14 2018   |06/14/2018       |07/14/2018      |

  @MATCH-1950
  Scenario: As a new RepVisits user,
            I want the setup wizard to guide me through final steps in the new user experience
            so that I can decide on my appointments' visibility and then continue into the system.
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    #FAIRS
    Then HS I select the "Fairs" option on the welcome page in the RepVisits setup wizard
    Then HS I select the "Only Me" option for Visit Availability on the 'One Last Step' page
    Then HS I verify the 'You're All Set' page is correct when Visit Availability is set to "Only Me"
    Then HS I select the "All RepVisits Users" option for Visit Availability on the 'One Last Step' page
    Then HS I verify the 'You're All Set' page is correct when Visit Availability is set to "All RepVisits Users"
    #VISITS
    Then HS I select the "Visits" option on the welcome page in the RepVisits setup wizard
    Then HS I select the "Only Me" option for Visit Availability on the 'One Last Step' page
    Then HS I verify the 'You're All Set' page is correct when Visit Availability is set to "Only Me"
    Then HS I select the "All RepVisits Users" option for Visit Availability on the 'One Last Step' page
    Then HS I verify the 'You're All Set' page is correct when Visit Availability is set to "All RepVisits Users"
    #VISITS AND FAIRS
    Then HS I select the "Visits and Fairs" option on the welcome page in the RepVisits setup wizard
    Then HS I select the "Only Me" option for Visit Availability on the 'One Last Step' page
    Then HS I verify the 'You're All Set' page is correct when Visit Availability is set to "Only Me"
    Then HS I select the "All RepVisits Users" option for Visit Availability on the 'One Last Step' page
    Then HS I verify the 'You're All Set' page is correct when Visit Availability is set to "All RepVisits Users"
    Then HS I successfully sign out

  @MATCH-2391
  Scenario: As a RepVisits user,I cannot able to add the visits for the past days
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS verify pills are not available for the past dates in schedule new visit page
    Then HS verify the past dates are disabled in the select custom date section
    Then HS verify pills are not available for the past dates in Re-schedule visit page
    Then HS verify the past dates are disabled in the select custom date section for Re-schedule visit page
    And HS I successfully sign out