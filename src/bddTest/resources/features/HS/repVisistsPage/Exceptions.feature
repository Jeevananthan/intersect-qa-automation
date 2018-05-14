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

  @MATCH-2989
  Scenario Outline: When entering an appointment that starts between 12:00 pm and 12:59 pm and ends at 1:00 pm or later,
  the appointment should not be blocked by the "Start time must be before end time" error.
  #visit
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I set my RepVisits availability to the current school year
  #Start Time:12:00 pm, EndTime:01:00pm
  #TODO - This randomizes the time and uses am always...  Fix.
    Then HS I add the new time slot with "21","12:00pm","01:00pm" and "3"
  #verify Timeslot in Exception
    Then HS I verify there is a timeslot on "21" at "12:00pm" in the Exceptions tab
    Then HS I remove the Time Slot created with "21","12:00pm" in Regular Weekly Hours Tab
  #fair Start Time:12:00 pm, EndTime:01:00pm
    Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
    Examples:
      |College Fair Name        |Date  |Start Time|End Time|RSVP Deadline|Cost|Max Number of Colleges|Number of Students Expected|ButtonToClick|Day  |StartTime|EndTime |NumVisits|StartDate|EndDate|exceptionTime|
      |QA Fair NotificationsTest|21    |1200PM    |0100PM  |17           |$25 |25                    |100                        |Save         |21   |12:00pm  |01:00pm |3        |21       |35     |12:00pm      |


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