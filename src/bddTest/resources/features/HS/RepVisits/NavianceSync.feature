@HS
Feature: HS - RepVisits - NavianceSync - As an HS user, I want to be able to access the features of the Sync Opt in features.

@MATCH-3237
Scenario: As an RepVisits HS user that had previously connected my RepVisits with my Naviance account to publish
events into Naviance, I want the ability to opt out/disconnect my RepVisits events from publishing to Naviance,
so that I can manage events separately in Naviance and RepVisits.
Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone10"
Then HS I navigate to naviance settings page
And HS I click on Disconnect RepVisits from Naviance button
And HS I verify the Cancel on the disconnect confirmation popup
And HS I click on Disconnect RepVisits from Naviance button
And HS I verify the Yes on the disconnect confirmation popup with "7","6:30am","7:00am","2","PurpleHE Automation" and "Cbba"
And HS I cancel a visit with time "6:30AM" college "Bowling Green State University-Main Campus" and note "Cancel"
Then HS I remove the Time Slot recently created with "7","6:30am" in Regular Weekly Hours Tab
And HS I successfully sign out

  @MATCH-4895
  Scenario: When a Naviance High School has their settings set to:
  "Visits Confirmations" set to "No, I want to manually review all incoming requests."
  Naviance Sync settings set to "Automatically publish confirmed visits." OR "Manually choose which visits to publish"
  and a College Rep reschedules a visit after the HS previously confirmed the visit (resulting in it previously syncing to Naviance)
  They will not get synced back into Naviance with the updated date/time..
    #Setup environment
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone10"
    Then HS I navigate to naviance settings page
    And HS I add new Time Slot as precondition with "10","5:31am","7:40am" and "2"
    And HS I add new Time Slot as precondition with "10","5:51am","7:40am" and "2"
    #Configure
    And HS I setup Naviance Sync Settings page with "Automatically publish confirmed visits." option
    And HS I setup Availability Settings page with "No, I want to manually review all incoming requests." option

    #Request and verifications
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "Standalone High School 10" in "Institutions"
    And HE I select "Standalone High School 10" from the results
    And HE I request an appointment with "Standalone High School 10" for Visits with "10" and "5:31am"

    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone10"
    And HS I confirm Request with "PurpleHE","The University of Alabama","5:31am"
    And HS I verify the visit in Naviance with "PurpleHE Automation","The University of Alabama","5:31 AM	"

    #Reschedule visit and verifications
    Then HS I reschedule the visit in HS for the following data "The University of Alabama","5:31AM","10"
    Then HS I verify reschedule pop-up for HS with the following data "PurpleHE","The University of Alabama","5:31AM","10"
    Then HS I reschedule a visit for HS with the following details "5:51AM","Test","10"
    And HS I verify the reschedule in Naviance with "PurpleHE Automation","The University of Alabama","5:51 AM"

    #Cancel visit and verifications
    And HS I cancel a visit with time "5:51AM" college "The University of Alabama" and note "Cancel"
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone10"
    And HS I verify the cancel in Naviance with "PurpleHE Automation","The University of Alabama","5:51 AM"

    #Clean environment
    Then HS I remove the Time Slot recently created with "10","5:31am" in Regular Weekly Hours Tab
    Then HS I remove the Time Slot recently created with "10","5:51am" in Regular Weekly Hours Tab
    And HS I successfully sign out
