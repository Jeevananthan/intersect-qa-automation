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

  @MATCH-1581
  Scenario Outline:As a high school user, I need to be able to view my Unscheduled availability
  so that I can visually see the status of each appointment window.
#precondition
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I set the RepVisits Visits Confirmations option to "Yes, accept all incoming requests"
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."

#Unscheduled but Available Day
    Then HS I set the date using "<StartDate>" and "<EndDate>"
    And HS I verify the update button appears and I click update button
    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>"
    Then HS I verify the Unscheduled Appointments using "<heStartTime>" in Exception subtab using "<StartDate>"
    Then HS I verify the color of selected date picker outline using "<StartDate>","<OutlineColor>" in Exception Tab
    Then HS I verify the color of the Appointments using "<heStartTime>","<StartDate>","<SlotColor>","<StartTimeColor>" in Exception Tab
    Then HS I verify the Trash icon is present in the Exception Tab to remove the slot
    Then HS I verify the "<NumVisits>" Maximum colleges are present in the Availability slot for the following details "<StartDate>","<StartTime>"
    Then HS I verify the Availability slot color after select the slot "<StartDate>","<heStartTime>" in the Exception Tab
    Then HS I remove the Time Slot created with "<StartDate>","<StartTime>" in Regular Weekly Hours Tab
    Then HS I successfully sign out

    Examples:
      |Day|StartDate|EndDate |StartTime|EndTime |heStartTime|NumVisits|SlotColor             |StartTimeColor        |OutlineColor      |
      |28 |28       |49      |10:41am  |12:59pm |10:41am    |4        |rgba(124, 174, 112, 1)|rgba(255, 255, 255, 1)|rgb(127, 143, 162)|


  @MATCH-1581
  Scenario Outline:As a high school user, I need to be able to view my Blocked Days availability
  so that I can visually see the status of each appointment window.
#Blocked Days
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I set the date using "<BlockedDate>" and "<EndDate>"
    And HS I verify the update button appears and I click update button
    Then HS I set the Blocked date and select the reason as "School Event" in the Holiday tab using "<BlockedDate>"
    Then HS I verify the Blocked days with reason "Blocked - School Event" in Exception subtab using "<BlockedDate>"
    Then HS I click the Remove option for the "<BlockedDate>" and "<EndDate>" in blocked days

    Then HS I set the Blocked date and select the reason as "No School" in the Holiday tab using "<BlockedDate>"
    Then HS I verify the Blocked days with reason "Blocked - No School" in Exception subtab using "<BlockedDate>"
    Then HS I click the Remove option for the "<BlockedDate>" and "<EndDate>" in blocked days

    Then HS I set the Blocked date and select the reason as "Holiday" in the Holiday tab using "<BlockedDate>"
    Then HS I verify the Blocked days with reason "Blocked - Holiday" in Exception subtab using "<BlockedDate>"
    Then HS I click the Remove option for the "<BlockedDate>" and "<EndDate>" in blocked days

    Then HS I set the Blocked date and select the reason as "Other" in the Holiday tab using "<BlockedDate>"
    Then HS I verify the Blocked days with reason "Blocked - Other" in Exception subtab using "<BlockedDate>"
    Then HS I verify the diagonal HashLines present in the Blocked date "<BlockedDate>","<back-ground color>"
    Then HS I click the Remove option for the "<BlockedDate>" and "<EndDate>" in blocked days
    Then HS I successfully sign out

    Examples:
      |BlockedDate|EndDate |back-ground color|
      |42         |49      |rgba(0, 0, 0, 0) |


  @MATCH-1581
  Scenario Outline:As a high school user, I need to be able to view my Max Appointments availability
  so that I can visually see the status of each appointment window.
#Max Appointments Met Day
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I set the date using "<StartDate>" and "<EndDate>"
    And HS I verify the update button appears and I click update button
    Then HS I add the new time slot with "<Day>","<MaxstartTime>","<MaxEndTime>" and "<MaxNumVisits>"
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
    Then HS I successfully sign out

    Given HE I want to login to the HE app using "purpleheautomation@gmail.com" as username and "Password!1" as password
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<StartDate>" and "<MaxhestartTime>"
    And HE I verify the schedule pop_up for "<School>" using "<MaxstartTime>" and "<MaxEndTime>"
    Then HE I successfully sign out

    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    And HS I set the Accept option of RepVisits Visit Scheduling to "a maximum of..." "1" visits per day
    Then HS I go to the Exception tab to verify the visits using "Max visits met","<MaxhestartTime>","<StartDate>",""
    Then HS I verify the diagonal HashLines present in the Max Appointments Met date "<BlockedDate>","<back-ground color>"
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
    Then HS I remove the Time Slot created with "<StartDate>","<StartTime>" in Regular Weekly Hours Tab
    Then HS I successfully sign out

    Examples:
      |Day|StartDate|EndDate |StartTime|School              |MaxNumVisits|MaxstartTime|MaxhestartTime|MaxEndTime|back-ground color|
      |28 |28       |49      |10:41am  |Int Qa High School 4|4           |12:         |12:           |12:59pm   |rgba(0, 0, 0, 0) |


  @MATCH-1581
  Scenario Outline:As a high school user, I need to be able to view my Partially Scheduled availability
  so that I can visually see the status of each appointment window.
 #Partially Scheduled Day
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I set the date using "<StartDate>" and "<EndDate>"
    And HS I verify the update button appears and I click update button
    Then HS I add the new time slot with "<Day>","<PartiallystartTime>","<PartiallyEndTime>" and "<PartiallyNumVisits>"
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
    Then HS I successfully sign out

    Given HE I want to login to the HE app using "purpleheautomation@gmail.com" as username and "Password!1" as password
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<StartDate>" and "<PartiallyhestartTime>"
    And HE I verify the schedule pop_up for "<School>" using "<PartiallystartTime>" and "<PartiallyEndTime>"
    Then HE I successfully sign out

    Given HE I want to login to the HE app using "purpleheautomation+publishing@gmail.com" as username and "Password!1" as password
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<StartDate>" and "<PartiallyhestartTime>"
    And HE I verify the schedule pop_up for "<School>" using "<PartiallystartTime>" and "<PartiallyEndTime>"
    Then HE I successfully sign out

    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    And HS I set the Accept option of RepVisits Visit Scheduling to "a maximum of..." "5" visits per day
    Then HS I verify the Partially scheduled Appointments With Message "2 Appointments scheduled" in Exception subtab using "<StartDate>"
    Then HS I verify the light blue background color present in the Partially Scheduled availability using "<PartiallystartTime>","<StartDate>","<back-ground color>" in Exception Tab
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
    Then HS I remove the Time Slot created with "<StartDate>","<StartTime>" in Regular Weekly Hours Tab
    Then HS I successfully sign out

    Examples:
      |Day|StartDate|EndDate |StartTime|School              |PartiallyNumVisits|PartiallystartTime|PartiallyhestartTime|PartiallyEndTime |back-ground color     |
      |28 |28       |49      |10:41am  |Int Qa High School 4|5                 |12:               |12:                 |12:59pm          |rgba(255, 255, 255, 1)|


  @MATCH-1581
  Scenario Outline:As a high school user, I need to be able to view my Fully Booked availability
  so that I can visually see the status of each appointment window.
 #Fully Booked Day
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I set the date using "<FullyBookedStartDate>" and "<FullyBookedEndDate>"
    And HS I verify the update button appears and I click update button
    Then HS I add the new time slot with "<Day>","<FullystartTime>","<FullyEndTime>" and "<FullyNumVisits>"
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
    Then HS I successfully sign out

    Given HE I want to login to the HE app using "purpleheautomation@gmail.com" as username and "Password!1" as password
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<FullyBookedStartDate>" and "<FullyhestartTime>"
    And HE I verify the schedule pop_up for "<School>" using "<FullystartTime>" and "<FullyEndTime>"
    Then HE I successfully sign out

    Given HE I want to login to the HE app using "purpleheautomation+publishing@gmail.com" as username and "Password!1" as password
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<FullyBookedStartDate>" and "<FullyhestartTime>"
    And HE I verify the schedule pop_up for "<School>" using "<FullystartTime>" and "<FullyEndTime>"
    Then HE I successfully sign out

    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I go to the Exception tab to verify the visits using "Fully booked","<FullyhestartTime>","<FullyBookedStartDate>","2"
    Then HS I verify the diagonal HashLines present in the Fully booked date "<FullyBookedStartDate>","<back-ground color>"
    Then HS I remove the Time Slot created with "<StartDate>","<StartTime>" in Regular Weekly Hours Tab
    Then HS I successfully sign out

    Examples:
      |Day|StartDate |StartTime|School              |FullyNumVisits|FullystartTime|FullyhestartTime|FullyEndTime|FullyBookedStartDate|FullyBookedEndDate|back-ground color|
      |28 |28        |10:41am  |Int Qa High School 4|2             |12:           |12:             |12:59pm     |14                  |35                |rgba(0, 0, 0, 0) |
