@HE
Feature: HE- RepVisits - RepVisitsScheduleVisits - As an HE user, I want to be able to search and schedule visits with HS

  @MATCH-1602 @MATCH-1958 @MATCH-1729
  Scenario: As an HE user I want to be able to use the Search and Schedule tab of RepVisits to browse HS availability.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify the Search and Schedule tab of the RepVisits page
    Then HE I search for High Schools with the following location data in RepVisits
      | City     | State    | State Abbreviation | County | Postal Code |
      | new york | Kentucky | KY                 | Bronx  | 45044       |
    #And HE I verify the Coming Soon message on the RepVisits Overview page
    And HE I successfully sign out

  @MATCH-1476 @MATCH-1902 @MATCH-1903 @MATCH-1774
  Scenario: As an HE user with the Intersect Presence Subscription active I want to see
  10 high schools returned in the scheduling results on a map so I can plan my high school visits
  travel efficiently by location.
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "Lebanon High School" in RepVisits
    Then HE I select "Lebanon High School" in "Lebanon, OH" from the RepVisits intermediate search results
    Then HE I view the map plugin on RepVisits Search & Schedule subtab
    And HE I select "Lebanon High School" from the RepVisists map plugin
    Then HE I verify the high school information popup contains the following data
      |School Name          |High School Contact:   |Address                           |Phone          |District      |Type   |Senior Class Size |College Going Rate |
      |Lebanon High School  |PurpleHS User          |1916 Drake Rd Lebanon, Ohio 45036 |(513) 934-5105 |Lebanon City  |PUBLIC |335               |65                 |

  @MATCH-2169
  Scenario Outline: HE Users - RepVisits - Availability Pills Updates
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I set a date using "<StartDate>" and "<EndDate>"
    And HS I verify the update button appears and I click update button
    When HS I add new time slot with "Monday", "<HourStartTime>", "<HourEndTime>", "<MinuteStartTime>", "<MinuteEndTime>", "<MeridianStartTime>", "<MeridianEndTime>" and "<NumVisits>"
    When HS I add new time slot with "Tuesday", "<HourStartTime>", "<HourEndTime>", "<MinuteStartTime>", "<MinuteEndTime>", "<MeridianStartTime>", "<MeridianEndTime>" and "<NumVisits>"
    When HS I add new time slot with "Wednesday", "<HourStartTime>", "<HourEndTime>", "<MinuteStartTime>", "<MinuteEndTime>", "<MeridianStartTime>", "<MeridianEndTime>" and "<NumVisits>"
    When HS I add new time slot with "Thursday", "<HourStartTime>", "<HourEndTime>", "<MinuteStartTime>", "<MinuteEndTime>", "<MeridianStartTime>", "<MeridianEndTime>" and "<NumVisits>"
    When HS I add new time slot with "Friday", "<HourStartTime>", "<HourEndTime>", "<MinuteStartTime>", "<MinuteEndTime>", "<MeridianStartTime>", "<MeridianEndTime>" and "<NumVisits>"
    Then HS I set the RepVisits Visits Confirmations option to "<Option>"
    And HS I successfully sign out

    Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for school in RepVisits using "<SchoolName>"
    Then HE I select Visits to schedule the appointment for "<SchoolName>" using "<Date>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<SchoolName>" using "<heTime>" and "<hsEndTime>"
    And HE verify the Pills got disappear for "<heStartTime>","<SchoolName>"
    And HE I successfully sign out
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    And HS I remove the time slot with day "Mon" and time "<heStartTime>"
    And HS I remove the time slot with day "Tue" and time "<heStartTime>"
    And HS I remove the time slot with day "Wed" and time "<heStartTime>"
    And HS I remove the time slot with day "Thu" and time "<heStartTime>"
    And HS I remove the time slot with day "Fri" and time "<heStartTime>"
    And HS I successfully sign out

    Examples:
      |hsEndTime|heStartTime  |heTime  |SchoolName             |Date    |Day   |HourStartTime|HourEndTime|MinuteStartTime|MinuteEndTime|MeridianStartTime|MeridianEndTime|NumVisits|StartDate  |EndDate  |Option                                              |
      |01:30am  |1:29am       |01:29   |Int Qa High School 4   |1       |Friday|1            |1          |29             |30           |am               |am             |3        |0          |10       |No, I want to manually review all incoming requests.|
