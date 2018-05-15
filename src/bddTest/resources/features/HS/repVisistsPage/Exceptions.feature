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

  @MATCH-2682
  Scenario Outline: As a high school staff member, I want to be able to edit my regular hours in RepVisits,
  so that I can easily change the number of colleges I will allow during a certain time slot.
#precondition
    Given HS I want to login to the HS app using "purplehsautomations@gmail.com" as username and "Password!1" as password
    And HS I set the Visit Availability of RepVisits Availability Settings to "All RepVisits Users"
    Then HS I set the RepVisits Visits Confirmations option to "<Option>"
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
    Then HS I clear the time slot for the particular day "<StartDate>" in Regular Weekly Hours Tab

#create a visits
    Then HS I set a date using "<StartDate>" and "<EndDate>" in Regular Weekly Hours Tab
    And HS I verify the update button appears and I click update button
    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>"
    And HS I successfully sign out

    Given HE I want to login to the HE app using "purpleheautomation@gmail.com" as username and "Password!1" as password
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<Date>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"
    Then HE I successfully sign out

#verify the Exception tab(before changing the NumofVisits : NumVisits-2)
    Given HS I want to login to the HS app using "purplehsautomations@gmail.com" as username and "Password!1" as password
    Then HS I go to the Exception tab to verify the visits using "Appointment scheduled","<heStartTime>","<StartDate>",""

#verify & edit regular weekly hours(changing NumofVisits from 2 to 1)
    Then HS I select the time slot in Regular Weekly Hours to verify the pills is highlighted using "<StartDate>","<EndDate>","<heStartTime>"
    Then HS I edit the slots in Regular Weekly Hours to "1"

#verify the Exception tab(after changing the NumofVisits : NumVisits-1)
    Then HS I go to the Exception tab to verify the visits using "Fully booked","<heStartTime>","<StartDate>",""
    Then HS I verify the pills "<StartDate>","<StartTime>" is not displayed in the schedule new visit popup
    And HS I successfully sign out

#verify the pills is not present in the search and schedule page
    Given HE I want to login to the HE app using "purpleheautomation+marketing@gmail.com" as username and "Password!1" as password
    And HE I search for "<School>" in RepVisits page
    Then HE I verify the pills is not displayed in the search and schedule page using "<School>","<Date>" and "<heStartTime>"
    Then HE I successfully sign out

#edit regular weekly hours(changing NumofVisits from 1 to 2)
    Given HS I want to login to the HS app using "purplehsautomations@gmail.com" as username and "Password!1" as password
    Then HS I select the time slot in Regular Weekly Hours to verify the pills is highlighted using "<StartDate>","<EndDate>","<heStartTime>"
    Then HS I edit the slots in Regular Weekly Hours to "2"

#verify the Exception tab(after changing the NumofVisits : NumVisits-2)
    Then HS I go to the Exception tab to verify the visits using "Appointment scheduled","<heStartTime>","<StartDate>",""
    Then HS I verify the pills "<StartDate>","<StartTime>" is displayed in the schedule new visit popup
    And HS I successfully sign out

#verify the pills is present in the search and schedule page
    Given HE I want to login to the HE app using "purpleheautomation+marketing@gmail.com" as username and "Password!1" as password
    And HE I search for "<School>" in RepVisits page
    Then HE I verify the pills is displayed in the search and schedule page using "<School>","<Date>" and "<heStartTime>"
    Then HE I successfully sign out

#Remove the time slot in Regular Weekly Hours Tab
    Given HS I want to login to the HS app using "purplehsautomations@gmail.com" as username and "Password!1" as password
    Then HS I remove the Time Slot created with "<StartDate>","<StartTime>" in Regular Weekly Hours Tab
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
    And HS I successfully sign out

    Examples:
      |Day |Date|StartTime|EndTime|NumVisits|StartDate|EndDate|hsEndTime|Option                            |School        |heStartTime|heTime |
      |42  |42  |10:55am  |12:11pm|2        |42       |49     |12:11pm  |Yes, accept all incoming requests.|Homeconnection|10:        |10:    |


