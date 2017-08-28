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

  @MATCH-1625
  Scenario: As a high school counselor using Naviance and RepVisits,
            I want to integrate my RepVisits account with Naviance college visits
            So that I do not have to manually enter appointments.
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I verify the Naviance Settings section of the Availability & Settings tab of the RepVisits page
    And HS I successfully sign out

  @MATCH-1574 @MATCH-1802
  Scenario Outline: As a high school staff member,
  I want to be able to view the weekly recurring time slots that my school is available for visits
  so that colleges can manage those availabilities.
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I set a date using "<StartDate>" and "<EndDate>"
    When HS I add new time slot with "<Day>", "<HourStartTime>", "<HourEndTime>", "<MinuteStartTime>", "<MinuteEndTime>", "<MeridianStartTime>", "<MeridianEndTime>" and "<NumVisits>"
#    Then HS I verify the Time Slot time were added with "<HourStartTime>", "<MinuteStartTime>" and "<MeridianStartTime>"
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
