@HE
Feature: HE - RepVisits - Calendar - As an HE user, I want to use the RepVisits Calendar features.

  @MATCH-4222
  Scenario: As an RepVisits HE admin premium/paid Presence subscription user, I should be able to view visit/fair appointments that I can reassign
    Given HE I want to login to the HE app using "purpleheautomation+administrator@gmail.com" as username and "Password!1" as password
    When HE I go to re assign appointments
    Then HE I verify the user "Publishing, PurpleHE" is displaying in select staff member dropdown
    Then HE I verify the text 'Showing all' is displaying in reassignAppointments Page for the user "Publishing, PurpleHE"
    Then HE I click Go Back button
    When HE I go to re assign appointments
    Then HE I verify Select all check box in reAssignAppointments page using "Publishing, PurpleHE"
    Then HE I verify the appointments count in reAssignAppointments page for the user "Publishing, PurpleHE"
    Then HE I verify the appointments displaying in agenda view
    Then HE I verify show more button displaying when 26 or more appointments are returned for the user "Publishing, PurpleHE" in reassignAppointments Page
    Then HE I verify the user "Fresh, PurpleHE" is displaying in Select new assignee dropdown
    Then HE I verify no appointment text is displaying for the user "Fresh, PurpleHE"

  @MATCH-4450
  Scenario Outline: As a HE user with active Presence subscription, I can access Agenda view of my appointments
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    And SP I set the "Intersect Presence Subscription" module to "inactive" in the institution page
    And SP I Click the Save Changes button
    Then SP I successfully sign out

    Given HE I am logged in to Intersect HE as user type "<hePremium>"
    Then HE I verify the user can access "Agenda" view
    And HE I successfully sign out

    Given HE I am logged in to Intersect HE as user type "<heFreemium>"
    Then HE I verify the message "Unlock Agenda View" is displaying in the "Agenda" page
    Then HE I verify "UPGRADE" button is displaying in the "Agenda" page
    Then HE I verify the upgrade model page after clicking the UPGRADE button in Agenda view

    Examples:
      |hePremium    |heFreemium       |
      |administrator|limited          |
      |publishing   |limitedPublishing|
      |community    |limitedCommunity |

    @MATCH-4146
    Scenario: As an RepVisits HE premium/paid Presence subscription user,I want the ability to more easily access the
        "Share calendars" link from the Calendars>Your Calendars section of RV, so that I don't miss seeing that as a
        feature of RV and can actually leverage that functionality.
      Given HE I am logged in to Intersect HE as user type "administrator"
      Then HE I verify that Share Calendars Link is displayed in Calendar page
      Then HE I verify that Share your calendar modal is opened when clicking the Share Calendars Link

   @MATCH-4798
   Scenario: Limit access to the "Re-assign appointments" link to JUST HE admins associated with an HE
   institution that has an active Presence subscription
     #Administrator
     Given HE I am logged in to Intersect HE as user type "administrator"
     Then HE I verify that Re-assign link is visible
     #Community
     Given HE I am logged in to Intersect HE as user type "publishing"
     Then HE I verify that Re-assign link is not visible
     #Publishing
     Given HE I am logged in to Intersect HE as user type "community"
     Then HE I verify that Re-assign link is not visible
     #Limited
     Given HE I am logged in to Intersect HE as user type "limited"
     Then HE I verify that Re-assign link is not visible
     #Limited publishing
     Given HE I am logged in to Intersect HE as user type "limitedPublishing"
     Then HE I verify that Re-assign link is not visible
     #Limited community
     Given HE I am logged in to Intersect HE as user type "limitedCommunity"
     Then HE I verify that Re-assign link is not visible
     #HS User
     Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
     Then HE I verify that Re-assign link is not visible

  @MATCH-4450
  Scenario Outline: As Hobsons product manager managing value adds to getting HS users to upgrade to RV Presence premium subscription,
  I want the RV>Calendar, Agenda view to be premium level access on the HS side while remaining accessible for HE users,
  So that further value can be provided to upgrade to an RV Presence premium subscription.

    Given HS I am logged in to Intersect HS through Naviance with user type "<hsNavianceAdmin>"
    Then HS I verify the user can access "Agenda" view
    And HS I successfully sign out

    Given HS I am logged in to Intersect HS through Naviance with user type "<hsNavianceMember>"
    Then HS I verify the user cannot access Agenda view
    And HS I successfully sign out

    Given HS I am logged in to Intersect HS as user type "<hsNon-NavianceAdmin>"
    Then HS I verify the user can access "Agenda" view
    And HS I successfully sign out

    Given HS I am logged in to Intersect HS as user type "<hsNon-NavianceMember>"
    Then HS I verify the user cannot access Agenda view

    Examples:
      |hsNavianceAdmin|hsNavianceMember|hsNon-NavianceAdmin|hsNon-NavianceMember|
      |navianceAdmin  |navianceMember1 |administrator      |member              |

  @MATCH-4224
  Scenario Outline: As an RepVisits HE admin premium/paid Presence subscription user, I want to understand why I can't submit the "Re-assign Appointments" modal form,
  so that I can correct my entries as necessary and successfully submit.
#Pre-Conditions
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    And HS I set the Visit Availability of RepVisits Availability Settings to "All RepVisits Users"
    Then HS I set the RepVisits Visits Confirmations option to "<Option>"
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
#Create a Visit
    Then HS I set the date using "<StartDate>" and "<EndDate>"
    And HS I verify the update button appears and I click update button
    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>" with "<option>"
    And HS I successfully sign out
#Register a Visit
    Given HE I am logged in to Intersect HE as user type "publishing"
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<Date>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"

    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify the error Message "Please select a Staff Member" is displaying when Select staff member is not selected
    Then HE I verify the error Message "Please select a New Assignee" is displaying when Select new assignee is not selected using "Publishing, PurpleHE"
    Then HE I verify the error Message "Please select at least one appointment" is displaying when appointments is not selected using "Publishing, PurpleHE","Community, PurpleHE"
    Then HE I verify the error Message "have any appointments scheduled." is displaying when the user "Fresh, PurpleHE" is selected
    Then HE I verify the error Message "Please select a Staff Member" is disappearing when the error message "doesn't have any appointments scheduled." is displayed for "Fresh, PurpleHE"

    Examples:
      |School               |Day |StartTime|EndTime |NumVisits|StartDate|EndDate |hsEndTime    |Option                                                |heStartTime |heTime  |Date|option|
      |Int Qa High School 4 |14  |10:25am  |11:25pm |3        |14       |42      |11:25pm      |No, I want to manually review all incoming requests.  |10:25am     |10:25am |14  |1     |

  @MATCH-4622 @MATCH-4550
  Scenario Outline: As a HE admin trying to transfer appointments from one HE user to another at my institution,
                     I want to be reminded that the system does not validate whether there's a conflict of day/time for the appointments being moved with the target new assignees calendars,
                     So that I can decide if I want to continue with the transfer or hold off on it in case I still need to confirm.
   #pre-condition
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone6"
    Then HS I set a date using "<StartDate>" and "<EndDate>"
    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>" with "<option>"
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
    And HS I successfully sign out

    Given HE I am logged in to Intersect HE as user type "<user>"
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<Date>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<School>" using "<heTime>" and "<hsEndTime>"

    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify the blue Note alert "<alertMessage>" is displaying when changing the Select staff member dropdown for the users "Publishing, PurpleHE","Fresh, PurpleHE" with appointments in Select new assignee dropdown
    Then HE I verify the blue Note alert "<alertMessage>" is displaying when changing the Select staff member dropdown for the users "Publishing, PurpleHE","Community, PurpleHE" with no appointments in Select new assignee dropdown
    And HE I verify the users are displaying including "Publishing, PurpleHE" in Select staff member dropdown
    And HE I verify the users are displaying including "Publishing, PurpleHE" in Select new assignee dropdown using "Community, PurpleHE"
    Then HE I verify the user "Publishing, PurpleHE" selected from 'select staff member' drop-down, excluded in 'Select new assignee' dropdown
    And HE I successfully sign out

    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone6"
    Then HS I remove the Time Slot created with "<Day>","<StartTime>" in Regular Weekly Hours Tab

    Examples:
    |user         |alertMessage                                                                                                               |StartTime|EndTime |NumVisits|hsEndTime|School                   |heStartTime   |heTime   |Day|Date|StartDate|EndDate|option|
    |publishing   |RepVisits does not prevent scheduling conflicts. Please confirm availability with the newly assigned rep before proceeding.|12:34am  |12:59pm |3        |12:59pm  |Standalone High School 6 |12:34am       |12:34am  |3  |3    |1       |20     |1     |

  @MATCH-4902
  Scenario Outline: As a HE admin with an active Presence subscription reassigns a college fair registration from one HE rep to another at their institution,
  that update is not currently being reflected for the HS admin at RV>College Fairs>View Details for the college fair in question.
#Pre-condition
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone6"
    Then HS I cancel registered college fair "<College Fair Name>"
    Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
    Then HS I Click on the "No, I'm Done" button in the success page of the Add Attendees page
    And HS I successfully sign out

    Then HE I am logged in to Intersect HE as user type "publishing"
    And HE I search for "<School>" in RepVisits page
    Then HE I register for the "<College Fair Name>" college fair at "<School>"

    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone6"
    Then HS I Click on the View Details button for the College Fair "<College Fair Name>"
    Then HS I verify the Attendee details "<Attendee>" in Edit fairs page
    And HS I successfully sign out

    Then HE I am logged in to Intersect HE as user type "administrator"
    When HE I go to re assign appointments
    Then HE I select the user "Publishing, PurpleHE" from 'Select staff member' dropdown
    Then HE I select the fair to reassign using "<RSVP Deadline>","<School>","<Number of Students Expected>"
    Then HE I select the user "Community, PurpleHE" from 'Select new assignee' dropdown
    Then HE I click Reassign Appointments button "<appointmentsCount>"

    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone6"
    Then HS I Click on the View Details button for the College Fair "<College Fair Name>"
    Then HS I verify the Attendee details "<reAssignedAttendee>" in Edit fairs page
#Post condition
#cancel the college Fair
    Then HS I Click on the View Details button for the College Fair "<College Fair Name>"
    Then HS I select Edit button to cancel the college Fair "<College Fair Name>"

    Examples:
      |College Fair Name|Date |Start Time|End Time|RSVP Deadline    |Cost|Max Number of Colleges|Number of Students Expected|ButtonToClick|School                  |Attendee              |reAssignedAttendee |appointmentsCount|
      |4902qaFairs      |24   |0800AM    |1000AM  |7                |$25 |25                    |4902                       |Save         |Standalone High School 6|PurpleHE Publishing   |PurpleHE Community |1                |

  @MATCH-1762 @MATCH-2124
  Scenario Outline: As an HE Community member,
  I need to be able to view appointment details in my calendar of my appointments
  so that I can easily get address/contact/additional info on the scheduled visit.

    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
    Then HS I set the RepVisits Visits Confirmations option to "<Option>"
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
    Then HS I set the date using "<StartDate>" and "<EndDate>"
    And HS I verify the update button appears and I click update button
    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>" with "<option>"
    And HS I successfully sign out

#premium
    Then HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<Date>" and "<heStartTime>"
    And HE I click Request button in visit schedule popup

    And HE I select calendar in RepVisits
    Then HE I verify and select an appointment in calendar page using "<School>","<heCT>","<Date>","Scheduled"
    Then HE I verify the popup for "<School>" using "<Date>","<heCST>","<heCET>","<hsAddress>","<contactPhNo>","<user>","<eMail>"
    Then HE I verify and select an appointment in calendar page using "<School>","<heCT>","<Date>","Scheduled"
    Then HE I remove the appointment from the calendar

#community
    Then HE I am logged in to Intersect HE as user type "community"
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<Date>" and "<heStartTime>"
    And HE I click Request button in visit schedule popup

    Then HE I verify and select an appointment in calendar page using "<School>","<heCT>","<Date>","Scheduled"
    Then HE I verify the popup for "<School>" using "<Date>","<heCST>","<heCET>","<hsAddress>","<contactPhNo>","<user>","<eMail>"
    Then HE I verify and select an appointment in calendar page using "<School>","<heCT>","<Date>","Scheduled"
    Then HE I remove the appointment from the calendar

#freemium
    Then HE I am logged in to Intersect HE as user type "limited"
    And HE I search for "<School>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<School>" using "<Date>" and "<heStartTime>" in freemium
    And HE I click Request button in visit schedule popup

    Then HE I verify and select an appointment in calendar page using "<School>","<heCT>","<Date>","Scheduled"
    Then HE I verify the popup for "<School>" using "<Date>","<heCST>","<heCET>","<hsAddress>","<contactPhNo>","<user>","<eMail>" for freemium
    Then HE I verify and select an appointment in calendar page using "<School>","<heCT>","<Date>","Scheduled"
    Then HE I remove the appointment from the calendar

#Remove the time slot in Regular Weekly Hours Tab
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
    Then HS I set the RepVisits Visits Confirmations option to "Yes, accept all incoming requests."
    Then HS I remove the Time Slot created with "<StartDate>","<StartTime>" in Regular Weekly Hours Tab
    And HS I successfully sign out

    Examples:
      |Day |Date|StartTime|EndTime|NumVisits|StartDate|EndDate|Option                                              |School                  |heStartTime|heCT     |heCST   |heCET   |hsAddress                                |contactPhNo       |user          |eMail                                       |option|
      |21  |21  |11:57am  |12:11pm|10       |21       |49     |No, I want to manually review all incoming requests.|Standalone High School 2|11:50am    |11:50AM  |11:50 AM|12:11 PM|1 Eagles Way Milford, OH 45150           |555555555555555555|School Manager|school_user_61024USPU@localhost.naviance.com|1     |

  @MATCH-4147 @MATCH-4700
  Scenario Outline: As an RepVisits HE admin premium/paid Presence subscription user,
                    I want the ability to easily access a path to reassign other HE users visits at my institution from RepVisits>Calendar view,
                    so that I can support rep attritition, rep absences, and so forth while maintaining scheduled appointments and do so from the location in RV that I'm used to managing my own appointments.
#Pre-condition
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I go to the users list for "<institution>" from the institution dashboard
    And SP I "inactivate" the user account for "<user>"
#verify all fields in re assign appointments page
    Then HE I am logged in to Intersect HE as user type "administrator"
    When HE I go to re assign appointments
    Then HE I verify the text 'Re-assign Appointments' is displaying in re assign appointments page
    Then HE I verify the text 'Select appointments to re-assign:' is displaying in re assign appointments page
    Then HE I verify the text 'Select a staff member above to see their appointments here' is displaying in re assign appointments page
    Then HE I verify the dropdown 'Select staff member' is displaying in re assign appointments page
    Then HE I verify the dropdown 'Select new assignee' is displaying in re assign appointments page
    Then HE I verify the button 'GO BACK' is displaying in re assign appointments page
    Then HE I verify the button 'Reassign  Appointments' is displaying in re assign appointments page
    Then HE I click Go Back button
#verify staff member dropdown
    Then HE I verify the current user "Automation, PurpleHE" is displaying in Select staff member dropdown list
    Then HE I verify the in active user "InActive, PurpleHE" is displaying with 'Inactive User' notation in Select staff member dropdown list
    Then HE I verify the in active user "AutomationWithoutCCProfile, PurpleHE" is displaying with 'Profile Incomplete' notation in Select staff member dropdown list
    Then HE I click Go Back button
    Then HE I verify the users are listed in A-Z order in 'select staff member' dropdown
#verify new assignee dropdown
    Then HE I select the user "Publishing, PurpleHE" in select staff member dropdown
    Then HE I verify the current user "Automation, PurpleHE" is displaying in Select new assignee dropdown list
    Then HE I verify the in active user "InActive, PurpleHE" is displaying with 'Inactive User' notation in new assignee dropdown list
    Then HE I verify the in active user "AutomationWithoutCCProfile, PurpleHE" is displaying with 'Profile Incomplete' notation in new assignee dropdown list
    Then HE I verify the in active user "InActive, PurpleHE" is not selectable in Select new assignee dropdown
    Then HE I click Go Back button
    Then HE I verify the users are listed in A-Z order in 'Select new assignee' dropdown using "HE, Purple"
    Then HE I verify the user "HE, Purple" selected from 'select staff member' drop-down, excluded in 'Select new assignee' dropdown
#verify 'GO BACK' button
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
    Then HS I cancel registered college fair "<College Fair Name>"
    Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
    Then HS I Click on the "Close" button in the success page of the college fair
    And HS I successfully sign out

    Then HE I am logged in to Intersect HE as user type "publishing"
    And HE I search for "<School>" in RepVisits page
    Then HE I register for the "<College Fair Name>" college fair at "<School>"

    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
    Then HS I Click on the View Details button for the College Fair "<College Fair Name>"
    Then HS I verify the Attendee details "<Attendee>" in Edit fairs page
    And HS I successfully sign out

    Then HE I am logged in to Intersect HE as user type "administrator"
    When HE I go to re assign appointments
    Then HE I select the user "Publishing, PurpleHE" from 'Select staff member' dropdown
    Then HE I select the fair to reassign using "<RSVP Deadline>","<School>","<Number of Students Expected>"
    Then HE I select the user "Community, PurpleHE" from 'Select new assignee' dropdown
    Then HE I click Go Back button
    Then HE I successfully sign out

#verify the details is not changed after clicked 'GO BACK' button
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
    Then HS I Click on the View Details button for the College Fair "<College Fair Name>"
    Then HS I verify the Attendee details "<Attendee>" in Edit fairs page
    Then HS I cancel registered college fair "<College Fair Name>"
    And HS I successfully sign out

    Examples:
      |College Fair Name|Date |Start Time|End Time|RSVP Deadline    |Cost|Max Number of Colleges|Number of Students Expected|ButtonToClick|School                  |Attendee              |institution              |user                                   |
      |4902qaFairs      |24   |0800AM    |1000AM  |7                |$25 |25                    |4902                       |Save         |Standalone High School 2|PurpleHE Publishing   |The University of Alabama|purpleheautomation+heInActive@gmail.com|

  @MATCH-4911
  Scenario: As an RepVisits HE user I should not be able to select the same name in Re-assign appointments
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I select the user "Publishing, PurpleHE" in select staff member dropdown
    Then HE I verify that the user: "Publishing, PurpleHE" is not displayed in select new assignee dropdown
