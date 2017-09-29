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

  @MATCH-1575
  Scenario Outline: As a high school community member,
  I want to be able to automatically block off U.S. Holidays
  so that I do not have to manually block each holiday.
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HE I set and verify that "<Holiday>" is blocked on the Blocked Days page
    And HS I successfully sign out
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I search for "Int QA High School 4" in RepVisits page using "Arlington, VA" with "<Date>"
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

