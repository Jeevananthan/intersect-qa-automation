@HS
Feature: HS - RepVisits - Blocked Days -  As an HS user, I can set up block days for visit availability


  @MATCH-2589
  Scenario Outline: In HS RepVisits, The Visit should not be displayed on the blocked days
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I Remove the created blocked days
    Then HS I set a date using "<BlockedDate>" and "<EndDate>"
    Then HS I set Blocked date as "<Reason>" and select the reason as "<BlockedDate>" in the Holiday tab
    Then HS I go the Exception tab in RepVisits
    And HS I verify the blocked day in Exception tab using "<BlockedDate>"

    And HS I verify the calendar page
    Then HS I verify the visit schedule popup
    Then HS I schedule a new visit for "<BlockedDate>","<StartTime>","<EndTime>","<Attendee>","<visitLocation>"
    And HS I successfully sign out

    Given HE I want to login to the HE app using "purpleheautomation+publishing@gmail.com" as username and "Password!1" as password
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to verify the appointment is not present for "<School>" using "<BlockedDate>" and "<StartTime>"
    Then HE I type into the global search box and select the result using "<School>"
    Then HE I select Check RepVisits Availability in the community page
    Then HE I select Visits to verify the appointment is not present using "<BlockedDate>" and "<StartTime>" in the RepvisitsAvailability
    Then HE I successfully sign out

    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I click the Remove option for the "<BlockedDate>" and "<EndDate>" in blocked days
    And HS I successfully sign out

    Examples:
      |BlockedDate|EndDate|StartTime|EndTime  |Attendee           |visitLocation|Reason |School              |
      |14         |21     |10:25 am |11:25 pm |PurpleHE Publishing|USA          |Holiday|Int Qa High School 4|



  @MATCH-1575 @MATCH-2124
  Scenario Outline: As a high school community member,
  I want to be able to automatically block off U.S. Holidays
  so that I do not have to manually block each holiday.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
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

