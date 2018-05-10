@HS @MATCH-1582
Feature: As a high school user, I need to be able to add or delete appointment windows on a day by day basis
  so that I can indicate when we will be offering appointments that differ from our typical recurring schedule.

  Scenario Outline: As an HS user, I want to be able to add precondition
  I want to be able to view the weekly recurring time slots that my school is available for visits
  so that colleges can manage those availabilities.
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I set a date using "<StartDate>" and "<EndDate>"
    Then HS I set the visit availability dates to "<StartDate>" through "<EndDate>"
    Examples:
      | StartDate          |EndDate         |
      | July 29 2018       |July 14 2019    |

    #Scenario is failing and need to be fixed
#  Scenario: As an HS user, I want to be able to add/remove time slots
#    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
#    When HS I open the Exceptions page
#    And HS I select a date "10" days ahead from now
#    And HS I add a new time slot with the following data:
#      | Start time | 07:03 am |
#      | End time   | 08:00 am |
#      | Visits     | 3        |
#    Then HS I verify that the time slot was added in a generated date, with the start time "7:03am"
#    And HS I delete the time slot in a generated date, with start time "7:03am"
#    And HS I verify that the time slot was removed from the generated date, with the start time "7:03am"
#    And HS I successfully sign out


#  Scenario: As an HS User, I want to be able to clear a day
#    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
#    When HS I open the Exceptions page
#    And HS I select a date "11" days ahead from now
#    And HS I add a new time slot with the following data:
#      | Start time | 07:04 am |
#      | End time   | 08:00 am |
#      | Max Visits | 3        |
#    And HS I select a date "11" days ahead from now
#    Then HS I clear the day
#    And HS I verify that the time slot was removed from the generated date, with the start time "7:04am"
#    And HS I successfully sign out