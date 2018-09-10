@HS @MATCH-1582
Feature: HS - RepVisits - Exceptions - As an HS user, I should be able to manage exceptions to my regular visit availability schedule

  Scenario Outline: As an HS user, I want to be able to add precondition I want to be able to view the weekly recurring time slots that my school is available for visits
  so that colleges can manage those availabilities.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I set a date using "<StartDate>" and "<EndDate>"
    Then HS I set the visit availability dates to "<StartDate>" through "<EndDate>"
    Examples:
      | StartDate          |EndDate         |
      | 23      |79    |

  @MATCH-2989
  Scenario Outline: When entering an appointment that starts between 12:00 pm and 12:59 pm and ends at 1:00 pm or later,
  the appointment should not be blocked by the "Start time must be before end time" error.
  #visit
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I set my RepVisits availability to the current school year
  #Start Time:12:xx pm, EndTime:01:00pm
    Then HS I add the new time slot with "21","12:00pm","01:00pm" and "3"
  #verify Timeslot in Exception
    Then HS I verify there is a timeslot on "21" at "PreviouslySetTime" in the Exceptions tab
    Then HS I remove the Time Slot created with "21","PreviouslySetTime" in Regular Weekly Hours Tab
  #fair Start Time:12:00 pm, EndTime:01:00pm
    Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
    Examples:
      |College Fair Name        |Date  |Start Time|End Time|RSVP Deadline|Cost|Max Number of Colleges|Number of Students Expected|ButtonToClick|Day  |StartTime|EndTime |NumVisits|StartDate|EndDate|exceptionTime|
      |QA Fair NotificationsTest|21    |1200PM    |0100PM  |17           |$25 |25                    |100                        |Save         |21   |12:00pm  |01:00pm |3        |21       |35     |12:00pm      |



  @MATCH-2682 @MATCH-2689
  Scenario Outline: As a high school staff member, I want to be able to edit my regular hours in RepVisits,
  so that I can easily change the number of colleges I will allow during a certain time slot.
#precondition
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
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
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
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
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
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
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I remove the Time Slot created with "<StartDate>","<StartTime>" in Regular Weekly Hours Tab
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
    And HS I successfully sign out

    Examples:
      |Day |Date|StartTime|EndTime|NumVisits|StartDate|EndDate|hsEndTime|Option                            |School              |heStartTime|heTime |
      |43  |43  |10:55am  |12:11pm|2        |43       |49     |12:11pm  |Yes, accept all incoming requests.|Int Qa High School 4|10:        |10:    |

 @MATCH-1581
  Scenario Outline:As a high school user, I need to be able to view my Unscheduled availability
  so that I can visually see the status of each appointment window.
#precondition
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I set the RepVisits Visits Confirmations option to "Yes, accept all incoming requests"
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
    Then HS I clear the time slot for the particular day "<StartDate>" in Regular Weekly Hours Tab

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
      |28 |28       |49      |10:41am  |12:59pm |10:        |4        |rgba(124, 174, 112, 1)|rgba(255, 255, 255, 1)|rgb(127, 143, 162)|


  @MATCH-1581
  Scenario Outline:As a high school user, I need to be able to view my Blocked Days availability
  so that I can visually see the status of each appointment window.
#Blocked Days
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I clear the time slot for the particular day "<BlockedDate>" in Regular Weekly Hours Tab
    Then HS I set the date using "<StartDate>" and "<EndDate>"
    And HS I verify the update button appears and I click update button
    Then HS I Remove the created blocked days
    Then HS I set the Blocked date and select the reason as "<BlockedDate>" in the Holiday tab using "School Event"
    Then HS I verify the Blocked days with reason "Blocked - School Event" in Exception subtab using "<BlockedDate>"
    Then HS I click the Remove option for the "<BlockedDate>" and "<EndDate>" in blocked days

    Then HS I set the Blocked date and select the reason as "<BlockedDate>" in the Holiday tab using "No School"
    Then HS I verify the Blocked days with reason "Blocked - No School" in Exception subtab using "<BlockedDate>"
    Then HS I click the Remove option for the "<BlockedDate>" and "<EndDate>" in blocked days

    Then HS I set the Blocked date and select the reason as "<BlockedDate>" in the Holiday tab using "Holiday"
    Then HS I verify the Blocked days with reason "Blocked - Holiday" in Exception subtab using "<BlockedDate>"
    Then HS I click the Remove option for the "<BlockedDate>" and "<EndDate>" in blocked days

    Then HS I set the Blocked date and select the reason as "<BlockedDate>" in the Holiday tab using "Other"
    Then HS I verify the Blocked days with reason "Blocked - Other" in Exception subtab using "<BlockedDate>"
    Then HS I verify the diagonal HashLines present in the Blocked date "<BlockedDate>","<back-ground color>"
    Then HS I click the Remove option for the "<BlockedDate>" and "<EndDate>" in blocked days
    Then HS I successfully sign out

    Examples:
      |StartDate|EndDate|BlockedDate|EndDate |back-ground color|
      |1       |49     |7          |14      |rgba(0, 0, 0, 0) |


  @MATCH-1581
  Scenario Outline:As a high school user, I need to be able to view my Max Appointments availability
  so that I can visually see the status of each appointment window.
#Max Appointments Met Day
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I clear the time slot for the particular day "<StartDate>" in Regular Weekly Hours Tab
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

    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    And HS I set the Accept option of RepVisits Visit Scheduling to "a maximum of..." "1" visits per day
    Then HS I go to the Exception tab to verify the visits using "Max visits met","<MaxhestartTime>","<StartDate>",""
    Then HS I verify the diagonal HashLines present in the Max Appointments Met date "<StartDate>","<back-ground color>"
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
    Then HS I remove the Time Slot created with "<StartDate>","<MaxstartTime>" in Regular Weekly Hours Tab
    Then HS I successfully sign out

    Examples:
      |Day|StartDate|EndDate |School              |MaxNumVisits|MaxstartTime|MaxhestartTime|MaxEndTime|back-ground color|
      |28 |28       |49      |Int Qa High School 4|2           |12:am         |12:am           |12:59pm   |rgba(0, 0, 0, 0) |


  @MATCH-1581
  Scenario Outline:As a high school user, I need to be able to view my Partially Scheduled availability
  so that I can visually see the status of each appointment window.
 #Partially Scheduled Day
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I clear the time slot for the particular day "<StartDate>" in Regular Weekly Hours Tab
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

    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    And HS I set the Accept option of RepVisits Visit Scheduling to "a maximum of..." "5" visits per day
    Then HS I verify the Partially scheduled Appointments With Message "2 Appointments scheduled" in Exception subtab using "<StartDate>"
    Then HS I verify the light blue background color present in the Partially Scheduled availability using "<PartiallystartTime>","<StartDate>","<back-ground color>" in Exception Tab
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
    Then HS I remove the Time Slot created with "<StartDate>","<PartiallystartTime>" in Regular Weekly Hours Tab
    Then HS I successfully sign out

    Examples:
      |Day|StartDate|EndDate |School              |PartiallyNumVisits|PartiallystartTime|PartiallyhestartTime|PartiallyEndTime |back-ground color     |
      |28 |28       |49      |Int Qa High School 4|5                 |10:am               |12:am                 |12:59pm          |rgba(255, 255, 255, 1)|


  @MATCH-1581
  Scenario Outline:As a high school user, I need to be able to view my Fully Booked availability
  so that I can visually see the status of each appointment window.
 #Fully Booked Day
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I clear the time slot for the particular day "<FullyBookedStartDate>" in Regular Weekly Hours Tab
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

    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I go to the Exception tab to verify the visits using "Fully booked","<FullyhestartTime>","<FullyBookedStartDate>",""
    Then HS I verify the diagonal HashLines present in the Fully booked date "<FullyBookedStartDate>","<back-ground color>"
    Then HS I remove the Time Slot created with "<FullyBookedStartDate>","<FullystartTime>" in Regular Weekly Hours Tab
    Then HS I successfully sign out

    Examples:
      |Day |School              |FullyNumVisits|FullystartTime|FullyhestartTime|FullyEndTime|FullyBookedStartDate|FullyBookedEndDate|back-ground color|
      |21  |Int Qa High School 4|2             |11:am           |12:am             |12:59pm     |21                  |35                |rgba(0, 0, 0, 0) |


      @MATCH-2692
  Scenario: As a high school staff member, I want to be able to toggle blocking of specific availabilities in RepVisits,
  so that I can effectively close a time slot for further visits and re-open it later, if I choose.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I set a date using "0" and "99"
    Then HS I add the new time slot with "8","5:33am","05:30pm" and "2"
    Then HS I add the new time slot with "8","6:06am","03:30pm" and "2"
    Then HS I set a date using "0" and "99"
    And HS I schedule a new visit with day "Fri" time "5:33am" representative name "Test Person name" representative last name "Test Last N" representative institution "RepresentativeTest" location "Cbba" NumberOfStudents "7" registrationWillClose "7 days"
    Then HS I verify that Block this time slot button is displayed for time slot with day "Fri" and time "5:33am"
    And HS I verify that Block this time slot ToolTip is displayed for time slot with day "Fri" and time "5:33am"
    When HS I block the time slot with day "Fri" and time "5:33am"
    Then HS I verify that Unblock this time slot button is displayed for time slot with day "Fri" and time "5:33am"
    And HS I verify that Block this time slot ToolTip is displayed for time slot with day "Fri" and time "1:00am"
    And HS I verify that Blocked label is displayed in the slot time with day "Fri" and time "5:33am"
    And HS I verify that a new visit with day "Fri" and time "5:33am" cannot be set
    When HS I unblock the time slot with day "Fri" and time "5:33am"
    Then HS I verify that the blocked label is not displayed for the time slot with day "Fri" and time "5:33am"
    And HS I verify that the number of visits for the time slot with day "Fri" and time "1:00am" is "2"
#    And HS I schedule a new visit with day "Fri" time "6:06am" representative name "Test Person name" representative last name "Test Last N" representative institution "RepresentativeTest2" location "Cbba" NumberOfStudents "7" registrationWillClose "7 days"
#    And HS I cancel a visit with time "6:06AM" college "RepresentativeTest" and note "Cancel"
#    And HS I cancel a visit with time "6:06AM" college "RepresentativeTest2" and note "Cancel"
#    And HS I remove the time slot with day "Fri" and time "6:06am"
    And HS I successfully sign out

  @MATCH-4255
  Scenario Outline: As a high school admin, I can able to edit the availability slots in regular weekly hours, so that i can able to enable/disable the particular time slot availability.
#precondition
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I clear the time slot for the particular day "<StartDate>" in Regular Weekly Hours Tab
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."

    Then HS I set a date using "<StartDate>" and "<EndDate>"
    And HS I verify the update button appears and I click update button
    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>"
    And HS I successfully sign out

    Given HE I want to login to the HE app using "purpleheautomation@gmail.com" as username and "Password!1" as password
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<Date>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"

# NumVisits-2
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I go to the Exception tab to verify the visits using "Appointment scheduled","<heStartTime>","<StartDate>",""

#NumofVisits from 2 to 1
    Then HS I select the time slot in Regular Weekly Hours to verify the pills is highlighted using "<StartDate>","<EndDate>","<heStartTime>"
    Then HS I edit the slots in Regular Weekly Hours to "1"

#NumVisits-1
    Then HS I go to the Exception tab to verify the visits using "Fully booked","<heStartTime>","<StartDate>",""
    Then HS I verify the pills "<StartDate>","<StartTime>" is not displayed in the schedule new visit popup
    And HS I successfully sign out

#verify the pills in search and schedule page
    Given HE I want to login to the HE app using "purpleheautomation+marketing@gmail.com" as username and "Password!1" as password
    And HE I search for "<School>" in RepVisits page
    Then HE I verify the pills is not displayed in the search and schedule page using "<School>","<Date>" and "<heStartTime>"

#edit regular weekly hours
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I select the time slot in Regular Weekly Hours to verify the pills is highlighted using "<StartDate>","<EndDate>","<heStartTime>"
    Then HS I edit the slots in Regular Weekly Hours to "2"

#verify the Exception tab
    Then HS I go to the Exception tab to verify the visits using "Appointment scheduled","<heStartTime>","<StartDate>",""
    Then HS I verify the pills "<StartDate>","<StartTime>" is displayed in the schedule new visit popup
    And HS I successfully sign out

#verify the pills is present in the search and schedule page
    Given HE I want to login to the HE app using "purpleheautomation+marketing@gmail.com" as username and "Password!1" as password
    And HE I search for "<School>" in RepVisits page
    Then HE I verify the pills is displayed in the search and schedule page using "<School>","<Date>" and "<heStartTime>"

#Remove the time slot in Regular Weekly Hours Tab
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I remove the Time Slot created with "<StartDate>","<StartTime>" in Regular Weekly Hours Tab
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
    And HS I successfully sign out

    Examples:
      |Day |Date|StartTime|EndTime|NumVisits|StartDate|EndDate|hsEndTime|School              |heStartTime|heTime |
      |7   |7   |10:55am  |12:11pm|2        |7        |14     |12:11pm  |Int Qa High School 4|10:55am    |10:55am|
