@HS @HS1
Feature: HS - RepVisits - NavianceSync - As an HS user, I want to be able to access the features of the Sync Opt in features.
  @MATCH-3237
  Scenario: As an RepVisits HS user that had previously connected my RepVisits with my Naviance account to publish
  events into Naviance, I want the ability to opt out/disconnect my RepVisits events from publishing to Naviance,
  so that I can manage events separately in Naviance and RepVisits.
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
    Then HS I navigate to naviance settings page
    And HS I click on Disconnect RepVisits from Naviance button
    And HS I verify the Cancel on the disconnect confirmation popup
    And HS I click on Disconnect RepVisits from Naviance button
    And HS I verify the Yes on the disconnect confirmation popup with "7","11:30am","12:30pm","2","PurpleHE Automation","Bowling Green State University-Main Campus" and "Cbba"
    Then HS I verify and select an appointment in calendar page using "Bowling Green State University-Main Campus","11:30am","7","Scheduled"
    Then HS I remove the appointment from the calendar
    Then HS I remove the Time Slot created with "7","11:30am" in Regular Weekly Hours Tab

  @MATCH-4895
  Scenario: When a Naviance High School has their settings set to:
  "Visits Confirmations" set to "No, I want to manually review all incoming requests."
  Naviance Sync settings set to "Automatically publish confirmed visits." OR "Manually choose which visits to publish"
  and a College Rep reschedules a visit after the HS previously confirmed the visit (resulting in it previously syncing to Naviance)
  They will not get synced back into Naviance with the updated date/time..
    #Setup environment
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone3"
    Then HS I navigate to naviance settings page
    Then HS I set the date using "14" and "21"
    Then HS I add the new time slot with "14","11:30am","12:40pm" and "2" with "1"
    Then HS I set the value for Reschedule the visit
    Then HS I add the new time slot with "14","10:31am","12:40pm" and "2" with "1"
    #Configure
    And HS I go to the Naviance Settings to select the option "Automatically publish confirmed visits."
    Then HS I set the RepVisits Visits Confirmations option to "No, I want to manually review all incoming requests."

    #Request and verifications
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "Standalone High School 3" in RepVisits page
    Then HE I select Visits to schedule the appointment for "Standalone High School 3" using "14" and "10:31am"
    And HE I verify the schedule pop_up for "Standalone High School 3" using "10:31am" and "12:40pm"

    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone3"
    Then HS I verify the Notification "PurpleHE","The University of Alabama","10:31am","14" in the Request Notification Tab
    And HS I select "Confirm" option for the Notification using "PurpleHE","10:31am","The University of Alabama"
    And HS I verify the visit in Naviance with "PurpleHE Automation","The University of Alabama","10:31 AM	"

    #Reschedule visit and verifications
    Then HS I reschedule the visit in HS for the following data "The University of Alabama","10:31AM","10"
    Then HS I verify reschedule pop-up for HS with the following data "PurpleHE","The University of Alabama","10:31AM","10"
    Then HS I reschedule a visit for HS with the following details "11:51AM","Test","10"
    And HS I verify the reschedule in Naviance with "PurpleHE Automation","The University of Alabama","11:51 AM"

    #Cancel visit and verifications
    Then HS I verify and select an appointment in calendar page using "Bowling Green State University-Main Campus","11:30am","14","ReScheduled"
    Then HS I remove the appointment from the calendar
    And HS I verify the cancel in Naviance with "PurpleHE Automation","The University of Alabama","11:51 AM"

    #Clean environment
    Then HS I remove the Time Slot created with "14","11:31am" in Regular Weekly Hours Tab


  @MATCH-4071
  Scenario: A persistent message appears in RepVisits (i.e. no other locations in Intersect such as Counselor Community,
  etc) alerting them that they did not complete the Naviance Settings wizard in the aforementioned scenario indicating,
  "Naviance Sync Incomplete Looks like you didn't finish connecting RepVisits to Naviance. Please select an option below
  to proceed." - specs per mock and related assets


  @MATCH-4071 @MATCH-4205 @MATCH3955
  Scenario: A persistent message appears in RepVisits (i.e. no other locations in Intersect such as Counselor Community,
  etc) alerting them that they did not complete the Naviance Settings wizard in the aforementioned scenario indicating,
  "Naviance Sync Incomplete Looks like you didn't finish connecting RepVisits to Naviance. Please select an option below
  to proceed." - specs per mock and related assets. When in this partial saved state, message disappears when:
  2.1 The user selects the "no" radio option and clicks "next" on the wizard (i.e. performs a disconnect per --MATCH-3955--)
  QA/UAT: The timing for which this is disappearing is incorrect as with current implementation it's only disappearing
  when you complete the entire wizard with this tickets scenario and not when you complete the Naviance Settings section
  of the wizard. Instead of correcting with this ticket, will adjust the timing of this alert disappearing with MATCH-4205
  2.2PASSED the user selects the "yes" radio  option and completes the full Naviance Settings wizard
    #Precondition
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone10"
    Then HS I navigate to naviance settings page
    Then HS I click on Disconnect RepVisits from Naviance button
    Then HS I navigate to the Naviance Settings page through the setup Wizard
    Then HS I select "Yes" to connect RepVisits with Naviance in the Wizard
    Then HS I navigate to the Naviance Settings page through the setup Wizard
    Then HS I verify the toast "is" displayed when setup Wizard is incomplete
    Then HS I complete the set up wizard from any location

    #Verify with No option
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone10"
    Then HS I navigate to the Naviance Settings page through the setup Wizard
    Then HS I select "No" to connect RepVisits with Naviance in the Wizard
    Then HS I verify the toast "not" displayed when setup Wizard is incomplete
    #Verify with Yes option
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone10"
    Then HS I navigate to the Naviance Settings page through the setup Wizard
    Then HS I select "Yes" to connect RepVisits with Naviance in the Wizard
    Then HS I complete the set up wizard from any location
    Then HS I verify the toast "not" displayed when setup Wizard is incomplete
#    Then HS I complete the set up wizard from any location



