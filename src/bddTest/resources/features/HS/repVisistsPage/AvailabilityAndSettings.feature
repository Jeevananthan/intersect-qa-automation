@HS
Feature:  As an HS user, I want to be able to access the features of Availability and Settings in RepVisits module.

  @MATCH-2682
  Scenario Outline: As a high school staff member, I want to be able to edit my regular hours in RepVisits,
                    so that I can easily change the number of colleges I will allow during a certain time slot.
#precondition
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    And HS I set the Visit Availability of RepVisits Availability Settings to "All RepVisits Users"
    Then HS I set the RepVisits Visits Confirmations option to "<Option>"
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."

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
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
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
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
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
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I remove the Time Slot created with "<StartDate>","<StartTime>" in Regular Weekly Hours Tab
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
    And HS I successfully sign out

    Examples:
      |Day |Date|StartTime|EndTime|NumVisits|StartDate|EndDate|hsEndTime|Option                            |School              |heStartTime|heTime |
      |14  |14  |10:55am  |12:11pm|2        |14       |21     |12:11pm  |Yes, accept all incoming requests.|Int Qa High School 4|10:55am    |10:55am|


