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

  @MATCH-1579 @MATCH-2124
  Scenario: As a HS RepVisits user I can able to Scheduling the visits in the Availability Settings page
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    And HS I set the Accept option of RepVisits Visit Scheduling to "a maximum of..." "5" visits per day
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
    Then HS I go to the Counselor Community
    Then HS I verify the Availability Settings section of the Availability subtab in the Availability & Settings page in RepVisits has the following data
      |Accept           |visits per day |
      |a maximum of...  |5              |
    Then HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
    Then HS I go to the Counselor Community
    Then HS I verify the Availability Settings section of the Availability subtab in the Availability & Settings page in RepVisits has the following data
      |Accept                           |
      |visits until I am fully booked.  |
    And HS I successfully sign out

  @MATCH-1586 @MATCH-1945 @MATCH-2124
  Scenario: As an HS User I want to be able to use the Availability and Settings tab of RepVisits to Set Time Zone
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    And HS I verify the Availability & Settings tab of the RepVisits page
    And HS I set the RepVisits Availability & Settings time zone to "US/Central"
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
    And HS I click on Availability on the Availability & Settings tab in RepVisits
    Then HS I verify the time zone in Repvisits Availability & Settings is "US/Central"
    And HS I set the RepVisits Availability & Settings time zone to "US/Eastern"
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
    And HS I successfully sign out

  @MATCH-1625 @MATCH-1958 @MATCH-1943
  Scenario: As a high school counselor using Naviance and RepVisits,
  I want to integrate my RepVisits account with Naviance college visits
  So that I do not have to manually enter appointments.
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I verify the Naviance Settings section of the Availability & Settings tab of the RepVisits page
    Then HS I verify the success message after save the changes
#Comming soon message is removed
#   And HS I verify the Coming Soon message on the RepVisits Overview page
    And HS I successfully sign out

  @MATCH-1574 @MATCH-1802 @MATCH-2124
  Scenario Outline: As a high school staff member,
  I want to be able to view the weekly recurring time slots that my school is available for visits
  so that colleges can manage those availabilities.
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I set the visit availability dates to "<StartDate>" through "<EndDate>"
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
    When HS I add new time slot with "<Day>", "<HourStartTime>", "<HourEndTime>", "<MinuteStartTime>", "<MinuteEndTime>", "<MeridianStartTime>", "<MeridianEndTime>" and "<NumVisits>"
    Then HS I verify the Time Slot time were added with "<HourStartTime>", "<MinuteStartTime>" and "<MeridianStartTime>"
    And HS I successfully sign out

    Examples:
      |Day              | HourStartTime | HourEndTime| MinuteStartTime| MinuteEndTime | MeridianStartTime | MeridianEndTime | NumVisits  | StartDate            |EndDate           |
      |Monday           | 1             |02          | 11             | 07             | am                | am              | 3          | August 29 2018         |August 30 2018      |
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

  @MATCH-1595 @MATCH-2124
  Scenario: As a HS RepVisits user I can able to access the Visit Confirmation in the Availability Settings page
  So that i can able to fix the appointment for the High school
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I set the RepVisits Visits Confirmations option to "Yes, accept all incoming requests"
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
    Then HS I go to the Counselor Community
    Then HS I verify the Availability Settings section of the Availability subtab in the Availability & Settings page in RepVisits has the following data
      |Automatically confirm all visit requests? |
      |Yes, accept all incoming requests.        |
    Then HS I set the RepVisits Visits Confirmations option to "No, I want to manually review all incoming requests."
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
    Then HS I go to the Counselor Community
    Then HS I verify the Availability Settings section of the Availability subtab in the Availability & Settings page in RepVisits has the following data
      |Automatically confirm all visit requests?            |
      |No, I want to manually review all incoming requests. |
    Then HS I successfully sign out

  @MATCH-1803 @MATCH-2124
  Scenario Outline: As a high school staff member ,
  I want to be able to define the weekly recurring appointment times that my school is available
  so that colleges can schedule appointments to visit during those times.
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I set a date using "<StartDate>" and "<EndDate>"
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
    When HS I add new time slot with "<Day>", "<HourStartTime>", "<HourEndTime>", "<MinuteStartTime>", "<MinuteEndTime>", "<MeridianStartTime>", "<MeridianEndTime>" and "<NumVisits>"
    Then HS I remove the Time Slot created with "<HourStartTime>", "<MinuteStartTime>" and "<MeridianStartTime>"
    Then HS I verify the Time Slot time were removed with "<HourStartTime>", "<MinuteStartTime>" and "<MeridianStartTime>"
    And HS I successfully sign out

    Examples:
      |Day              | HourStartTime | HourEndTime| MinuteStartTime| MinuteEndTime | MeridianStartTime | MeridianEndTime | NumVisits  | StartDate            |EndDate           |
      |Monday           | 5             |06          | 11             | 07             | am                | pm              | 3          | August 29 2018         |August 30 2018      |
#      |Tuesday          | 5             |07          | 12             | 08             | am                | pm              | 99         | August 15 2018       |September 23 2018 |

  @MATCH-1575 @MATCH-2124
  Scenario Outline: As a high school community member,
  I want to be able to automatically block off U.S. Holidays
  so that I do not have to manually block each holiday.
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I set a date using "<StartDate>" and "<EndDate>"
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
    Then HE I set and verify that "<Holiday>" is blocked on the Blocked Days page
    And HS I successfully sign out
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I search for school "Int Qa High School 4" in RepVisits page using "Liberty Township, OH" and verify that "<Date>" is blocked
    Examples:
      |Holiday               | Date                | StartDate  | EndDate     |
      |LABOR_DAY             | September 04 2018   |July 23 2018|July 14 2019 |
      |COLUMBUS_DAY          | October 9 2018      |July 23 2018|July 14 2019 |
      #Ommited by old dates cannot be setup blocked days calrified by Gayathri
      #|VETERANS_DAY          | November 10 2017    |July 23 2017|July 15 2018 |
      #|THANKSGIVING_DAY      | November 23 2017    |July 23 2017|July 15 2018 |
      #|DAY_AFTER_THANKSGIVING| November 24 2017    |July 23 2017|July 15 2018 |
      |CHRISTMAS_EVE         | December 24 2018    |July 23 2018|July 14 2019 |
      |CHRISTMAS_DAY         | December 25 2018    |July 23 2018|July 14 2019 |
      |NEW_YEAR_EVE          | December 31 2018    |July 23 2018|July 14 2019 |
      |NEW_YEAR_DAY          | January 01 2019     |July 23 2018|July 14 2019 |
      |MARTIN_LUTHER_DAY     | January 21 2019     |July 23 2018|July 14 2019 |
      |PRESIDENTS_DAY        | February 19 2019    |July 23 2018|July 14 2019 |
      |MEMORIAL_DAY          | May 28 2019         |July 23 2018|July 14 2019 |
      |INDEPENDENCE_DAY      | July 04 2019        |July 23 2018|July 14 2019 |



  @MATCH-1577 @MATCH-2124
  Scenario Outline: As a high school community member, I want to be able to indicate the date ranges for which I am
  available for college visits, so that colleges know when to visit my high school.
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I set the visit availability dates to "<StartDate>" through "<EndDate>"
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
    Then HS I verify the "<StartDate>" and "<EndDate>" date was set in the calendar
    And HS I successfully sign out

    Examples:
      |StartDate            |EndDate      |
      |July 16 2018         |July 14 2019 |
      |August 15 2018       |June 11 2019 |
      |July 16 2018         |July 14 2019 |

  @MATCH-1578 @MATCH-2124
  Scenario: As a HS RepVisits user I want to be able to use the Availability and Settings tab of RepVisits to Set Visit Scheduling
  I want to able to set the scheduling new visits in advance and set the cancelling or rescheduling visits in advance
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "56"
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
    Then HS I go to the Counselor Community
    Then HS I verify the Availability Settings section of the Availability subtab in the Availability & Settings page in RepVisits has the following data
      |Prevent colleges from scheduling new visits less than |
      |56|
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "45"
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
    Then HS I go to the Counselor Community
    Then HS I verify the Availability Settings section of the Availability subtab in the Availability & Settings page in RepVisits has the following data
      |Prevent colleges from cancelling or rescheduling less than |
      |45|
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "5"
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
    And HS I successfully sign out

  @MATCH-1585 @MATCH-2124
  Scenario: As a high school community member, I want to publish or hide my college visit availability,
  so that I can control when colleges can only schedule college visits.
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I set the Visit Availability of RepVisits Availability Settings to "Only Me"
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
    Then HS I go to the Counselor Community
    Then HS I verify the Visit Availability Section for the Availability & Settings tab of the RepVisits with "Only Me"
    And HS I set the Visit Availability of RepVisits Availability Settings to "All RepVisits Users"
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
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
    And HE I search for "Int Qa High School 4" in RepVisits
    And HE I select "Int Qa High School 4" in "Liberty Township, OH" from the RepVisits intermediate search results
    And HE I verify Repvisits Special Instructions for School are "AUTOMATION Welcome message. This message is to test the maximum limit of characters in messages. As a HS Repvisits user We will add this message. Ans same message will be displayed in HE for Repvisits to schedule their visits. Maximum characters allo"
    And HE I successfully sign out

  @MATCH-1776
  Scenario Outline: As a HS RepVisits user I want to able to create a new fair in the college fair
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
    Then HS I verify the Success Message for the College Fair "<College Fair Name>"
    Then HS I Click on the "Close" button in the success page of the college fair
    Then HS I Click the View Details button for the College Fair Event for "PreviouslySetFair"
    Then HS I Click on the "Edit" button in the College Fair Details Page
    Then HS I set the data to the Edit a college Fair "<College Fair Name>","<VerifyDateEdit>","<Cost>","<NumberofStudentsExpected>","<MaxNumberofColleges>","<verifyStartTime>","<verifyEndTime>","<VerifyRSVPDateEdit>","<ButtonToClick>"
    And HS I successfully sign out
    Examples:
      |College Fair Name    |Date            |Start Time|End Time|RSVP Deadline    |Cost|Max Number of Colleges|Number of Students Expected| ButtonToClick |Cost|MaxNumberofColleges|NumberofStudentsExpected|ButtonToClick|VerifyDateEdit       |VerifyRSVPDateEdit     |verifyStartTime|verifyEndTime|
      |QA Test Fair New/Edit|35              |0900AM    |1000AM  |7                |$25 |25                    |100                        | Save          |$25 |25                 |100                     |Save         |Tuesday, Dec 12, 2018|Wednesday, Nov 15, 2018|09:00          |10:00        |
    #    |QA Tests for Fair |December 12 2017|0900AM    |1000AM  |November 16 2017 |$25 |25                    |100                        | Save          |$25 |25                 |100                     |Save         |Tuesday, Dec 12, 2017|Wednesday, Nov 15, 2017|09:00          |10:00        |
    #    |QA Tests for Fairs|December 12 2017|0900AM    |1000AM  |November 16 2017 |$25 |25                    |100                        | Save          |$25 |25                 |100                     |Save         |Tuesday, Dec 12, 2017|Wednesday, Nov 15, 2017|09:00          |10:00        |


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
      |23                   |No School    |23         | 23        |

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

  @MATCH-2171 @MATCH-2124
  Scenario Outline: when we initially created the first and last days for availability, they were not developed to persist.
  Instead they're changed / set each time that availability is set. This ticket is to persist the first and last dates
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I set the visit availability dates to "<StartDate>" through "<EndDate>"
#    And HS I verify the update button appears and I click update button
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
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


  @MATCH-1584
  Scenario Outline: As a high school user, when I confirm an appointment I need to email colleges with specific details
  about visiting my high school  so that they are prepared to visit my high school.
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I set the RepVisits Confirmation message to "<Message>"
    And HS I verify the messaging updated confirmation toast message
    And HS I verify the RepVisits Confirmation message is set to "<Message>"
    Then HS I successfully sign out

    Examples:
      |Message                 |
      |Test update New Message |

  @MATCH-2833
  Scenario: As an HS RepVisits user I want to see a message on the RepVisits Overview page that informs me I have no
  upcoming appointments (visits OR fairs) for the next week so I can quickly know I don't have any colleges
  visiting my high school over the next 7 days.
    Given HS I want to login to the HS app using "purpleheautomation+hstest@gmail.com" as username and "Password!1" as password
    Then HS I navigate to the "Calendar" page in RepVisits
    Then HS I cancel all events for the next 7 days
    Then HS I navigate to the "Overview" page in RepVisits
    Then HS I verify the RepVisits Overview page when no events are scheduled for the next 7 days
    Then HS I successfully sign out

  @MATCH-2094 @MATCH-2124
  Scenario Outline: As an HE user I want to see RepVisit notifications organized intuitively within my Notifications
  page REQUESTS subtab so I can efficiently find the updates I am looking for within RepVisits.
    # FOR VISITS
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
    Then HS I set the RepVisits Visits Confirmations option to "<Option>"
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page

    Then HS I set the date using "<StartDate>" and "<EndDate>"
    And HS I verify the update button appears and I click update button
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>"
    And HS I successfully sign out

    Given HE I want to login to the HE app using "purpleheautomation@gmail.com" as username and "Password!1" as password
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<Date>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"
    Then HE I verify the message "You currently have no notifications" is displayed in the Request subTab
    Then HE I verify the Paginate the REQUESTS subtab via 25 entries with a "Show More" action to display the next 25 entries
    And HE I verify the Notifications & Tasks using "<School>","<StartDate>","<heStartTime>"
    Then HE I click the View full details option in the Request subTab using "<School>","<StartDate>","<heStartTime>"
    Then HE I successfully sign out

    #FOR FAIRS
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
    And HS I successfully sign out

    Given HE I want to login to the HE app using "purpleheautomation@gmail.com" as username and "Password!1" as password
    And HE I search for "<School>" in RepVisits page
    #TC may fail on the next step due to MATCH-3877
    Then HE I register for the "<College Fair Name>" college fair at "<School>"
    Then HE I verify the message "You currently have no notifications" is displayed in the Request subTab
    Then HE I verify the Paginate the REQUESTS subtab via 25 entries with a "Show More" action to display the next 25 entries
    And HE I verify the Notifications & Tasks using "<School>","<Date>","<fairTime>" for fairs
    Then HE I click the View full details option in the Request subTab using "<School>","<Date>","<fairTime>" for fairs
    Then HE I successfully sign out

    Examples:
      |Day |StartTime|EndTime |NumVisits|StartDate|EndDate |hsEndTime    |Option                                               |School              |heStartTime |heTime  |College Fair Name         |Date|Start Time|End Time|RSVP Deadline|Cost|Max Number of Colleges|Number of Students Expected| ButtonToClick |fairTime|
      |35  |10:      |11:25pm |3        |35       |49      |11:25pm      |No, I want to manually review all incoming requests. |Int Qa High School 4|10:         |10:     |QA4 Fairs for testing     |35  |0900AM    |1000AM  |28           |$25 |25                    |100                        | Save          |9:00am  |

  @MATCH-1947
  Scenario Outline: As a HS RepVisits user I want to be able to use the rep-visits setup page to set the Messaging options
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I verify the Messaging Options Page in the repvists setup wizard "<verify Confirmation Message>","<verify Special Instruction for RepVisits>"
    Then HS I enter the messages in the Message Option page for Repvists Setup wizard "<Confirmation Message_Next>","<Special Instruction for RepVisits_Next>","<Button to Click_Next>"
    Then HS I verify the Primary Contact for Visits page and then click the "Back" button
    Then HS I verify the Messaging Options Page in the repvists setup wizard "<verify Confirmation Message_Next>","<verify Special Instruction for RepVisits_Next>"
    Then HS I enter the messages in the Message Option page for Repvists Setup wizard "<Confirmation Message_Back>","<Special Instruction for RepVisits_Back>","<Button to Click_Back>"
    Then HS I verify the Availability Settings page and then click the "Next" button
    Then HS I verify the Messaging Options Page in the repvists setup wizard "<verify Confirmation Message_Next>","<verify Special Instruction for RepVisits_Next>"

    Examples:
      |verify Confirmation Message|verify Special Instruction for RepVisits|Confirmation Message_Next                |Special Instruction for RepVisits_Next     |Button to Click_Next|verify Confirmation Message_Next         |verify Special Instruction for RepVisits_Next|Confirmation Message_Back                     |Special Instruction for RepVisits_Back          |Button to Click_Back|
      |                           |                                        |Confirmation Message for visits and Fairs|Special Instructions for the repvisits User|Next                |Confirmation Message for visits and Fairs|Special Instructions for the repvisits User  |Confirmation Message for visits and Fairs Back|Special Instructions for the repvisits User Back|Back                |
  @MATCH-2111 @MATCH-2124
  Scenario: As a RepVisits High School user who works in multiple schools,
  I want to be able to enter a primary contact number for my school,
  So that Colleges trying to schedule visits have the correct contact number for me.
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    And HS I go to the Availability & Settings
    Then HS I verify the Primary Contact Phone Number is required in Availability & Settings
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
    And HS I go to the College Fair Settings page
    Then HS I verify the Primary Contact Phone Number is required in College Fair Settings
    Then HS I verify the success Message "Great! You've updated College Fair settings." in Fair Settings page
    And HS I go to the Notifications & Primary Contact Tab in HS Setup Wizard page
    Then HS I verify the Primary Contact Phone Number is required in the Visits and Fairs setup wizard
    And HS I successfully sign out

  @MATCH-1881 @MATCH-1872
  Scenario: As an HS user I need be able to search through my RepVisits Contacts
  so I can quickly find the contact I am looking to view.
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    And HS I validating the pagination of 25 contacts in Contacts Page
    And HS I verify the contacts list is sorted or not
    And HS I verify empty contacts page in Contacts
    And HS I verify full contacts page in Contacts
    And HS I verify the contacts page is full or empty
#    And HS I verify contacts details  in Contacts
#      |Overview |Calendar |Availability & Settings |College Fairs |Contacts |Notifications & Tasks|
    And HS I search for "The University of Alabama" in Contacts
    And HS I search for invalid data of "invalid data" in Contacts
    #Page layout is the same for HE/HS, so use the existing HE code for this.
    And HE I search for partial data of "The Univer" in Contacts
    And HS I successfully sign out

    @MATCH-1631 @MATCH-1463
  Scenario Outline: As a high school community member, I want to be able to view a list colleges that have requested to attend my college fair,
  so I can keep track of who is attending.
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
    Then HS I Click the View Details button for the College Fair Event for "PreviouslySetFair"
    Then HS I verify the Fair Details Page "PreviouslySetFair","<VerifyDate>","<instructionsforCollegeRepresentatives>"
    Then HS I Click on the "Add Attendee" button in the College Fair Details Page
    Then HS I Add the following Attendee "<Attendees>" from the results in the Add Attendee pop-up page
    Then HS I Click on the "No, I'm Done" button in the success page of the Add Attendees page
    Then HS I verify the list of registered college fair attendees for the "<Name>","<Contact>","<Notes>","<Status>","<Action>"
    Then HS I Click the "Cancel" button for the attendee named "<Name>"
    Then HS I set the following data in the confirm cancel pop-up "<cancellationMessage>","<buttonToClickNo,go back>"
    Then HS I verify the list of registered college fair attendees for the "<Name>","<Contact>","<Notes>","<Status>","<Action>"
    Then HS I Click the "Cancel" button for the attendee named "<Name>"
    Then HS I set the following data in the confirm cancel pop-up "<cancellationMessage>","<buttonToClickYes, cancel visit>"
    Then HS I verify the list of registered college fair attendees for the "<Name>","<Contact>","<Notes>","<StatusCanceled>","<ActionCanceled>"
    Then HS I cancel the fair of name "PreviouslySetFair" with the reason "TestCase Cleanup"
    And HS I successfully sign out


    Examples:
      |College Fair Name           |Date            |RSVP Deadline   |Start Time |End Time |Cost|Max Number of Colleges|Number of Students Expected|ButtonToClick|Attendees          |VerifyDate       |instructionsforCollegeRepresentatives|Name                     |Contact                                                    |Notes|Status   |Action|cancellationMessage             |buttonToClickNo,go back|buttonToClickYes, cancel visit|StatusCanceled  |ActionCanceled |
      |QA Fair Cancel Fair Attendee|3               |2               |0500AM     |0600AM   |$25 |25                    |100                        |Save         |PurpleHE Automation|3                |                                     |The University of Alabama|PurpleHE Automation,QA,purpleheautomation@gmail.com        |     |Attending|yes   |QA Test for canceling Attendees |No, go back            |Yes, cancel visit             |Canceled        |               |

  @MATCH-1762 @MATCH-2124
  Scenario Outline: As an HE Community member,
  I need to be able to view appointment details in my calendar of my appointments
  so that I can easily get address/contact/additional info on the scheduled visit.

    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I set the RepVisits Visits Confirmations option to "<Option>"
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
    Then HS I set the date using "<StartDate>" and "<EndDate>"
    And HS I verify the update button appears and I click update button
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>"
    And HS I successfully sign out

#premium
    Given HE I want to login to the HE app using "purpleheautomation+HEadmin@gmail.com" as username and "Password!1" as password
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<Date>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"

    And HE I select calendar in RepVisits
    Then HE I verify the calendar page using "<School>","<heCT>","<Date>"
    Then HE I verify the popup for "<School>" using "<Date>","<heCST>","<heCET>","<hsAddress>","<contactPhNo>","<user>","<eMail>"
    Then HE I verify the calendar page using "<School>","<heCT>","<Date>"
    Then HE I remove the appointment from the calendar
    Then HE I successfully sign out

#community
    Given HE I want to login to the HE app using "purpleheautomation+community@gmail.com" as username and "Password!1" as password
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<Date>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"

    Then HE I verify the calendar page using "<School>","<heCT>","<Date>"
    Then HE I verify the popup for "<School>" using "<Date>","<heCST>","<heCET>","<hsAddress>","<contactPhNo>","<user>","<eMail>"
    Then HE I successfully sign out

#freemium
    Given HE I want to login to the HE app using "purpleheautomation+limited@gmail.com" as username and "Password!1" as password
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<Date>" and "<heStartTime>" in freemium
    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"

    Then HE I verify the calendar page using "<School>","<heCT>","<Date>"
    Then HE I verify the popup for "<School>" using "<Date>","<heCST>","<heCET>","<hsAddress>","<contactPhNo>","<user>","<eMail>" for freemium
    Then HE I successfully sign out

#Remove the time slot in Regular Weekly Hours Tab
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I set the RepVisits Visits Confirmations option to "Yes, accept all incoming requests"
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
    Then HS I remove the Time Slot created with "<StartDate>","<StartTime>" in Regular Weekly Hours Tab
    And HS I successfully sign out

    Examples:
      |Day |Date|StartTime|EndTime|NumVisits|StartDate|EndDate|hsEndTime|Option                                              |School              |heStartTime|heTime   |heCT     |heCST   |heCET   |hsAddress                                |contactPhNo|user      |eMail                        |
      |7   |21  |11:50am  |12:11pm|10       |21       |49     |12:11pm  |No, I want to manually review all incoming requests.|Int Qa High School 4|11:50am    |11:50am  |11:50AM  |11:50 AM|12:11 PM|6840 LAKOTA LN Liberty Township, OH 45044|1234567890 |IAM Purple|naviance-email@mock.com|


  @MATCH-2444
  Scenario Outline: Verify that email is sent to HS users after cancelling a fair as an HE user
    Given HS I want to login to the HS app using "purpleheautomation+admin@gmail.com" as username and "Password!1" as password
    Then HS I add the email "<EMail>" in the primary contact in Notifications & Primary Contact page
    Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
    And HS I successfully sign out

    Given HE I want to login to the HE app using "purplehsautomations@gmail.com" as username and "Password!1" as password
    And HE I search for "<School>" in RepVisits page
    Then HE I register for the "<College Fair Name>" college fair at "<School>"
    Then HE I verify the calendar page using "<School>","<heCT>","<Date>" for Fairs
    Then HE I remove the Fair appointment from the calendar
    And HE I successfully sign out
    Then HE I verify the Email Notification Message for "<School>" using "<Date>","<EmailTimeForFair>"
      |Subject                                                             |To       |Messages |
      |College fair registration cancelled for <School for Notification>   |<EMail>  |1        |

    Examples:
      |School for Notification|School        |EMail                           |College Fair Name     |Date|Start Time|End Time|RSVP Deadline|Cost|Max Number of Colleges|Number of Students Expected| ButtonToClick |heCT   |EmailTimeForFair|
      |Homeconnection (WA)    |Homeconnection|purpleheautomation@gmail.com    |QAs Fairs tests       |4   |1000AM    |1100AM  |2            |$25 |25                    |100                        | Save          |10AM   |10:00am         |

  @MATCH-3462
  Scenario: As a RepVisits HS user that is interested in opting in to connect events with Naviance, I want the copy on
            the screen to clearly provide me with information on my ability to opt in/out of the publish connection,
            so that I know what the implications are for connecting and whether I can disconnect the sync.
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I navigate to the Naviance Settings page through the setup Wizard
    And HS I verify the UI of the Naviance Settings Page in setup wizard
    And HS I successfully sign out

  @MATCH-2691
  Scenario Outline: As a High School RepVisits User who is viewing my exceptions (/rep-visits/settings/availability/exceptions)
                    I want to see availability pills during times when appointments are scheduled
                    So that I can edit remaining availabilities.
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I verify in exceptions the appointments color and status for "<AppointmentStatus>" with color "<Color>"
    Then HS I successfully sign out
    Examples:
      |AppointmentStatus       |Color                   |
      |Max visits met          | rgba(233, 233, 245, 1) |
      |Fully booked            | rgba(233, 238, 245, 1) |
      |Appointment scheduled   | rgba(233, 238, 245, 1) |

  @MATCH-2381 @test
  Scenario: As a HS RepVisits user verify note to let users know their contact info will be visible
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    And HS I Navigate to College Fairs tab of the Repvisits Page
    And HS I Click button Add a College Fair to Add a fair
    And HS I verify Note on Add Edit Fair screen "Please note: Your high school name, address, email, and primary contact phone number will be displayed to admission representatives."
    And HS I click on close icon on Add Edit College Fair pop-up
    And HS I click View Details against fair
    And HS I click on Edit button to edit fair
    And HS I verify Note on Add Edit Fair screen "Please note: Your high school name, address, email, and primary contact phone number will be displayed to admission representatives."
    And HS I successfully sign out

  @MATCH-2381 @test
  Scenario: As a Non Naviance HS RepVisits user verify note to let users know their contact info will be visible
    Given HS I want to login to the HS app using "hobsonstest11@mailinator.com" as username and "Control!23" as password
    And HS I Navigate to College Fairs tab of the Repvisits Page
    And HS I Click button Add a College Fair to Add a fair
    And HS I verify Note on Add Edit Fair screen "Please note: Your high school name, address, email, and primary contact phone number will be displayed to admission representatives."
    And HS I click on close icon on Add Edit College Fair pop-up
    And HS I click View Details against fair
    And HS I click on Edit button to edit fair
    And HS I verify Note on Add Edit Fair screen "Please note: Your high school name, address, email, and primary contact phone number will be displayed to admission representatives."
    And HS I successfully sign out

  @MATCH-1484 @MATCH-2124
  Scenario Outline: A RepVisits user, I want to be able to export my visit data,
            So that I can easily show and sort the data to students/parents/my boss.
#Verify unpaid HE users are blocked from exporting
    Given HE I want to login to the HE app using "purpleheautomation+limited@gmail.com" as username and "Password!1" as password
    Then HE I verify the unpaid users are blocked from exporting in Calendar page
    Then HE I successfully sign out

#CREATE VISITS AND FAIRS
#precondition
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    And HS I set the Visit Availability of RepVisits Availability Settings to "All RepVisits Users"
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
    Then HS I set the RepVisits Visits Confirmations option to "<Option>"
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."

    Then HS I set the date using "<StartDate>" and "<EndDate>"
    And HS I verify the update button appears and I click update button
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>"
    Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
    And HS I successfully sign out

    Given HE I want to login to the HE app using "purpleheautomation@gmail.com" as username and "Password!1" as password
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<Date>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"

    And HE I search for "<School>" in RepVisits page
    Then HE I register for the "<College Fair Name>" college fair at "<School>"

#Exporting appointments
    Then HE I verify the Export button is Enabled in Calendar page
    Then HE I export the appointments for the following details "<StartDate>","<EndDate>"
    Then HE I verify the downloaded Appointments csv file "RepVisitsEvents.csv" contains following details
      |Appt Type/Fair Name|High School|Appt Date|Appt Time Zone|Appt Start|Appt Finish|Status|Address|City|State|Zip|Contact|Title|Email|Phone|
    Then HE I delete the downloaded Appointments Cvs file "RepVisitsEvents.csv"
    Then HE I successfully sign out

    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I verify the Export button is Enabled in Calendar page
    Then HS I export the appointments for the following details "<StartDate>","<EndDate>"
    Then HS I verify the downloaded Appointments csv file "RepVisitsEvents.csv" contains following details
    |Appt Type/Fair Name|Number Attending|Appt Date|Appt Start|Appt Finish|Appt Location|Status|Rep Name|Rep Title|College|City|State|email|phone|
    Then HS I delete the downloaded Appointments Cvs file "RepVisitsEvents.csv"
    Then HS I remove the Time Slot created with "<StartDate>","<StartTime>" in Regular Weekly Hours Tab
    And HS I successfully sign out

  Examples:
  |Day |StartTime|EndTime |NumVisits|StartDate|EndDate |hsEndTime    |Option                             |School                  |heStartTime |heTime  |College Fair Name     |Date|Start Time|End Time|RSVP Deadline|Cost|Max Number of Colleges|Number of Students Expected| ButtonToClick |
  |7   |10:      |11:25pm |3        |7        |14      |11:25pm      |Yes, accept all incoming requests. |Int Qa High School 4    |10:         |10:     |QAs Fairs tests       |14   |0900AM    |1000AM |7            |$25 |25                    |100                        | Save          |

 @MATCH-1812 @MATCH-2124
  Scenario Outline: As a RepVisits product I want to limit the high schools returned in RepVisits searches to only include those high schools who have made their RepVisits availability publicly available
                    so HE users are not presented with high schools in the search results that don't use RepVisits.
#Pre-condition
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    And HS I set the Visit Availability of RepVisits Availability Settings to "All RepVisits Users"
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
    Then HS I set the RepVisits Visits Confirmations option to "<Option>"
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page

    Then HS I set the date using "<StartDate>" and "<EndDate>"
    And HS I verify the update button appears and I click update button
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>"
    And HS I successfully sign out

    Given HE I want to login to the HE app using "purpleheautomation@gmail.com" as username and "Password!1" as password
#by SchoolLocation
    Then HE I search the "<School>" by "<location>"
    Then HE I verify the default toggle "Visits" is "Enabled" in search and schedule Tab
    Then HE I verify the default toggle "Fairs" is "Disabled" in search and schedule Tab
    Then HE I verify the Availability slot "<heStartTime>" is displaying in the visit toggle "<Date>","<School>" in search and schedule Tab
#by SchoolName
    And HE I search for "<School>" in RepVisits page
    Then HE I verify the default toggle "Visits" is "Enabled" in search and schedule Tab
    Then HE I verify the default toggle "Fairs" is "Disabled" in search and schedule Tab
    Then HE I verify the Availability slot "<heStartTime>" is displaying in the visit toggle "<Date>","<School>" in search and schedule Tab
    Then HE I successfully sign out

    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    And HS I set the Visit Availability of RepVisits Availability Settings to "Only Me"
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
    And HS I successfully sign out

    Given HE I want to login to the HE app using "purpleheautomation@gmail.com" as username and "Password!1" as password
#by SchoolLocation
    Then HE I search the "<School>" by "<location>"
    Then HE I verify the default toggle "Visits" is "Disabled" in search and schedule Tab
    Then HE I verify the default toggle "Fairs" is "Enabled" in search and schedule Tab
    Then HE I verify the Availability slot "<heStartTime>" is not displaying in the visit toggle "<Date>","<School>" in search and schedule Tab
#by SchoolName
    And HE I search for "<School>" in RepVisits page
    Then HE I verify the default toggle "Visits" is "Disabled" in search and schedule Tab
    Then HE I verify the default toggle "Fairs" is "Enabled" in search and schedule Tab
    Then HE I verify the Availability slot "<heStartTime>" is not displaying in the visit toggle "<Date>","<School>" in search and schedule Tab
    Then HE I successfully sign out
#Post-Condition
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    And HS I set the Visit Availability of RepVisits Availability Settings to "All RepVisits Users"
    Then HS I verify the success Message "Great! You've updated your settings." in Availability Settings page
    And HS I successfully sign out

  Examples:
    |Day |StartTime|EndTime |NumVisits|StartDate|EndDate |Option                                               |School                  |heStartTime |Date|location         |
    |14  |10:32am  |11:25pm |3        |14       |42      |No, I want to manually review all incoming requests. |Int Qa High School 4    |10:32am     |14  |Liberty Township |

 @MATCH-2436
  Scenario: As a HS user, I want to be taken to the Calendar page (month view) after my school's RepVisits' wizard has been completed
            so I don't see the Overview page as my first experience with RepVisits.
   #Naviance
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    And HS I navigate to the college visits page
    Then HS I verify the default calendar page present after the Wizard completion
    And HS I successfully sign out
   #Non-Naviance
    Given HS I want to login to the HS app using "purpleheautomation+admin@gmail.com" as username and "Password!1" as password
    And HS I navigate to the college visits page
    Then HS I verify the default calendar page present after the Wizard completion
    And HS I successfully sign out

  @MATCH-2728
  Scenario Outline: As an HS RepVisists user who I click on a College Fair in the calendar
  I want to be able to edit fairs in the summary drawer
  So that calendar appointments all have a consistent interface

    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
    Then HS I Click on the "Close" button in the success page of the college fair
    And HS I verify the fairs are clickable "<College Fair Name>","<VerifyDateEdit>","<verifyStartTime>","<verifyEndTime>","<VerifyRSVPDateEdit>","<Cost>","<MaxNumberofColleges>","<NumberofStudentsExpected>"
    Then HS I cancel college fair created "<College Fair Name>"
    Examples:
      |College Fair Name |Date            |Start Time|End Time|RSVP Deadline    |Cost|Max Number of Colleges|Number of Students Expected| ButtonToClick |Cost|MaxNumberofColleges|NumberofStudentsExpected|ButtonToClick|VerifyDateEdit       |VerifyRSVPDateEdit     |verifyStartTime|verifyEndTime|
      |Fair#778         |December 12 2017|0900AM    |1000AM  |April 16 2017 |$25 |25                    |100                        | Save          |$25 |25                 |100                     |Save         |Tuesday, Dec 12, 2017|Wednesday, Nov 15, 2017|09:00          |10:00        |

  @MATCH-3101
  Scenario: As a HS Repvisits user, I should be able to see the text '#HE User# has asked for feedback on their recent visit.' in every entry present in
            Visit Feedback Pending tab
    Given HS I want to login to the HS app using "purpleheautomation+hstest@gmail.com" as username and "Password!1" as password
    Then HS I Navigate to Notifications & Tasks tab of the Repvisits Page
    Then HS I click the Visit Feedback sub tab
    Then HS I should be able to see the text - #HE User# has asked for feedback on their recent visit.- in every entry present in Visit Feedback Pending tab

  @MATCH-2692
  Scenario: As a high school staff member, I want to be able to toggle blocking of specific availabilities in RepVisits,
  so that I can effectively close a time slot for further visits and re-open it later, if I choose.
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    And HS I set a date using "0" and "90"
    And HS I add new time slot with "Friday", "2", "3", "15", "15", "AM", "AM" and "2"
    And HS I schedule a new visit with day "Fri" time "2:15am" representative name "Test Person name" representative last name "Test Last N" representative institution "RepresentativeTest" location "Cbba" NumberOfStudents "7" registrationWillClose "7 days"
    Then HS I verify that Block this time slot button is displayed for time slot with day "Fri" and time "2:15am"
    And HS I verify that Block this time slot ToolTip is displayed for time slot with day "Fri" and time "2:15am"
    When HS I block the time slot with day "Fri" and time "2:15am"
    Then HS I verify that Unblock this time slot button is displayed for time slot with day "Fri" and time "2:15am"
    And HS I verify that Block this time slot ToolTip is displayed for time slot with day "Fri" and time "2:15am"
    And HS I verify that Blocked label is displayed in the slot time with day "Fri" and time "2:15am"
    And HS I verify that a new visit with day "Fri" and time "2:15am" cannot be set
    When HS I unblock the time slot with day "Fri" and time "2:15am"
    Then HS I verify that the blocked label is not displayed for the time slot with day "Fri" and time "2:15am"
    And HS I verify that the number of visits for the time slot with day "Fri" and time "2:15am" is "2"
    And HS I schedule a new visit with day "Fri" time "2:15am" representative name "Test Person name" representative last name "Test Last N" representative institution "RepresentativeTest2" location "Cbba" NumberOfStudents "7" registrationWillClose "7 days"
    And HS I cancel a visit with time "2:15AM" college "RepresentativeTest" and note "Cancel"
    And HS I cancel a visit with time "2:15AM" college "RepresentativeTest2" and note "Cancel"
    And HS I remove the time slot with day "Fri" and time "2:15am"
    And HS I successfully sign out

  @MATCH-2652
  Scenario: As a nonNaviance HS, Freemium HE, or Premium HE user who is receiving their 'Welcome to Counselor Community/Intersect'
            email with their user credentials to access the system for the first time I want to see more accurate information in
            the email so I can appropriately contact Support, if needed, and, ideally, log in on my own.
    Then SP I am logged in to Support for Intersect
    Then SP I search for "Alabama" in Support
    Then SP I click in "See All Users" link
    And  SP I "Re-invite" to "purpleheautomation+Match2652@gmail.com"
    Then HE I verify that the Email Notification Message says: "(.*)https://counselorcommunity.com(.*)purpleheautomation(.*)Match2652@gmail.com(.*)Hobsons Support(.*)counselorcommunity@purpledev.hobsonspobox.net(.*)"
      |Subject                                                        |To                            |Messages |
      |Your Intersect Invitation | purpleheautomation+Match2652@gmail.com|1        |

  @MATCH-1765
  Scenario Outline: As a high school user, I want to be able to manually add appointments including custom contact info/custom time slots,
  so that I can create appointments that are custom to my high school's needs.
    Given HS I am logged in to Intersect HS through Naviance with account "stndalonehs7" and username "school-user" and password "password"
  #verify AddVisit Button is Disabled in Calendar page
    Then HS I select the "Fairs" option on the welcome page in the RepVisits setup wizard
    Then HS I navigate to the calendar page to verify AddVisit Button is "Disabled"
  #verify AddVisit Button is Enabled in Calendar page
    Then HS I select the "Visits and Fairs" option on the welcome page in the RepVisits setup wizard
    Then HS I navigate to the calendar page to verify AddVisit Button is "Enabled"

#verify the UI of the visit schedule popup
    And HS I verify the calendar page is displayed
    Then HS I verify the close drawer is displaying in the visit Schedule popup
    Then HS I verify the link "Want a custom time? Add it manually" is displaying in the visit Schedule popup
    Then HS I verify the link "Go back to select from list" is displaying in the visit Schedule popup
    Then HS I verify the text "Start Typing ..." is present in the Attendee text box
    Then HS I verify the link "Not in the list? Add them manually" is displaying in the visit Schedule popup
    Then HS I verify the link "Go back to list" is displayed in the visit Schedule popup
    Then HS I verify the text box is displaying in the visit Schedule popup for "add-rep-first-name","add-rep-last-name","add-rep-email","add-rep-phone","add-rep-position"
    Then HS I verify the "Institution" only required field in the visit Schedule popup
    Then HS I verify the "If their school isn't in the list, you can simply type it in" text is present under "add-rep-institution" in the visit Schedule popup
    Then HS I verify the button "add visit" is displaying in the visit Schedule popup

#Manually adding appointment based on pre-determined time slots:
    Then HS I set the date using "<StartDate>" and "<EndDate>"
    And HS I verify the update button appears and I click update button
    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>"
    Then HS I set the RepVisits Visits Confirmations option to "<Option>"
    Then HS I add the appointment based on pre-determined time slots using "<StartDate>","<StartTime>","<Attendees>","<visitLocation>"

  #Manually adding a contact to an appointment:
    Then HS I manually add the contact to an appointment using "<StartDate>","<StartTime>","<FName>","<LName>","<EMail>","<Phone>","<Position>","<institution>"
    Then HS verify the created Appointment is present in the calendar "<StartDate>","<StartTime>","<institution>"

  #Adding a manual appointment based on a custom time:
    Then HS I schedule a new visit for "<Date>","<newVisitSTime>","<newVisitETime>","<Attendees>","<visitLocation>"

  #Confirmation message:
    Then HS I verify the confirmation message "Appointment Scheduled!,We have emailed the college with the appointment details." for the created visit
    Then HS I remove the Time Slot created with "<StartDate>","<StartTime>" in Regular Weekly Hours Tab
    And HS I successfully sign out

    Examples:
      |Date |StartTime|EndTime |NumVisits|StartDate |EndDate |Option                                               |newVisitSTime|newVisitETime|visitLocation|Attendees           |institution               |Day |FName    |LName |EMail                           |Phone       |Position|
      |35   |10:09am  |12:25pm |3        |14        |42      |No, I want to manually review all incoming requests. |11:02am      |10:58pm      |Cbba         |PurpleHE Automation |The University of Alabama |14  |Intersect|QA    |purpleheautomation@gmail.com    |999999999999|QA      |




#  @MATCH-2061
#      Scenario: : This scenario is to verify Internal Notes
#      Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
#      And HS I am Navigating to Calendar Home Screen
#      And HS I click on button Add Visit
#      And HS I select custom time manually
#      And HS I select a date "12" days ahead from now
#      And HS I select Visit StartTime "9:40am" and End Time "10:00am"
#      And HS I select representative from drop down "AlmauserFirstName"
#      And HS I Enter Internal Notes "Visit Notes Added for Automation Purpose"
#      And HS I click on Add Visit button
#      And HS I click on Agenda on Calendar
#      And Hs I open the date picker on Agenda View
#      And HS I select a date "12" days ahead from now from the standard date picker
#      And HS I click on Day on Calendar
#      And HS I click on Visit with "Alma College" from "9:40 AM" to "10:00 AM" on Day Calendar
#      And HS I verify Internal Notes on Visit Details screen "Visit Notes Added for Automation Purpose"
#      And HS I Cancel visit to create again add Notes to Cancel "canceled for automation"
#      And HS I successfully sign out

#  @MATCH-1469
#  Scenario: As a HS user Manually Add a Contact to Appointment
#    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
#    And HS I am Navigating to Calendar Home Screen
#    And HS I click on button Add Visit
#    And HS I select custom time manually
#    And HS I select a date "12" days ahead from now
#    And HS I select Visit StartTime "9:40am" and End Time "10:00am"
#    And HS I click on link Add School User Manually
#    And HS I Enter Following Data to Add a School User Manually
#      |FirstName|Amanda|
#      |LastName |Hubs  |
#      |E-mail|amanda@hobsons.com  |
#      |Phone    |5137462317         |
#      |Position |QA Tester         |
#      |Institution|Alma College    |
#    And HS I Enter Internal Notes "Visit Notes Added for Automation Purpose"
#    And HS I click on Add Visit button
#    And HS I click on Agenda on Calendar
#    And Hs I open the date picker on Agenda View
#    And HS I select a date "12" days ahead from now from the standard date picker
#    And HS I click on Day on Calendar
#    And HS I click on Visit with "Alma College" from "9:40 AM" to "10:00 AM" on Day Calendar
#    And HS I verify Representative details on Visit Details screen "amanda@hobsons.com"
#    And HS I Cancel visit to create again add Notes to Cancel "canceled for automation"
#    And HS I successfully sign out
