@HE @Events
Feature: HE - Events - ManageEvents - As an HE Events user, I can manage and publish events to Naviance Student

  @MATCH-2913 @MATCH-3219 @MATCH-2902 @MATCH-2928 @MATCH-2893
  Scenario: Verify an event can be created/edited/saved/published/deleted
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I create and save a new event "60" minutes ahead from now with the following details:
      | Event Name  | RandomEventName |
      | Timezone    | Eastern Time (i.e. America/New_York) |
      | Description | Test |
      | Max Attendees | 30 |
      | EVENT LOCATION BY POSITION  | 1 |
      | EVENT PRIMARY CONTACT BY POSITION | 1 |

#    Select Location, Contact and Filter (audience) by position (starting in 1).
#    This is because currently we can create locations, contacts and filters with
#    the same name.
    | EVENT LOCATION BY POSITION  | 1 |
    | EVENT PRIMARY CONTACT BY POSITION | 1 |
    | EVENT AUDIENCE BY POSITION       | 1 |
    Then HE I should see the event of name "RandomEventName" present in the unpublished events list as Draft event
    Then HE I verify status "Draft" for the event of name "RandomEventName"
    And HE click on Event Name "RandomEventName" to edit
    And HE I edit and publish a new event "60" minutes ahead from now with the following details:
      | Event Name  | RandomEventName |
      | Timezone    | Eastern Time (i.e. America/New_York) |
      | Description | TestEdited |
      | Max Attendees | 30 |
      | EVENT LOCATION BY POSITION  | 1 |
      | EVENT PRIMARY CONTACT BY POSITION | 1 |
      | EVENT AUDIENCE BY POSITION       | 2 |
    Then HE I verify status "Published" for the event of name "RandomEventName"
    Then HE I should see the event of name "RandomEventName" present in the events list as a published event
    When HE I unpublish the event of name "RandomEventName"
    And HE I delete the event of name "RandomEventName"
    Then HE The deleted event of name "RandomEventName" should not be displayed in the unpublished events list

  @MATCH-2913 @MATCH-2902 @MATCH-2928
  Scenario: Verify an event can be cancelled
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I create and publish a new event "60" minutes ahead from now with the following details:
      | Event Name  | TestEvent |
      | Timezone    | Eastern Time (i.e. America/New_York) |
      | Description | TestEdited |
      | Max Attendees | 30 |
      | EVENT LOCATION BY POSITION  | 1 |
      | EVENT PRIMARY CONTACT BY POSITION | 1 |
      | EVENT AUDIENCE BY POSITION       | 2 |
    When HE I cancel the event of name "TestEvent"
    And HE I open the "Cancelled" tab in Events
    Then HE I verify status "Cancelled" for the event of name "TestEvent"
    Then HE The cancelled event should be displayed in the canceled events list

  @MATCH-2928
  Scenario: View Expired events
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I open the "Expired" tab in Events
    Then HE I verify status "Expired" for the event of name "ExpiredEventForAutomation"

  @MATCH-2913
  Scenario: Verify validations on create/edit events page
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I open the Create Event screen
    And HE I publish the current event
    Then HE I verify required fields error messages for events

  @MATCH-3614
  Scenario: Verify an event with registrations cannot be unpublished
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I create and save a new event "120" minutes ahead from now with a unique name and the following details:
      | Event Name | ABCDUniqueName |
      | Max Attendees | 30 |
      | EVENT LOCATION BY POSITION  | 1 |
      | EVENT PRIMARY CONTACT BY POSITION | 1 |
    And HE I successfully sign out
    When SM I am logged in to SuperMatch through Family Connection as user "linussupermatch" with password "Hobsons!23" from school "blue1combo"
    And SM I go to College Events from the SuperMatch main menu
    And I look for the host "The University of Alabama"
    Then I sign up for the event of generated name
    And HUBS I successfully sign out
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I attempt to unpublish the event of generated name
    Then HE I verify the message that warns that an event with attendee cannot be unpublished
    #Cleanup step
    And HE I cancel the created event

  @MATCH-3613
  Scenario: Verify cancelled events can still be viewed in Naviance Student
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I create and save a new event "120" minutes ahead from now with a unique name and the following details:
      | Event Name | EventForCancelTest |
      | Timezone    | Eastern Time (i.e. America/New_York) |
      | Description | Test              |
      | Max Attendees | 30 |
      | EVENT LOCATION BY POSITION  | 1 |
      | EVENT PRIMARY CONTACT BY POSITION | 1 |
    And HE I cancel the created event
    When SM I am logged in to SuperMatch through Family Connection as user "benhubs" with password "Hobsons!23" from school "rtsa"
    And SM I go to College Events from the SuperMatch main menu
    And I look for the host "The University of Alabama"
    Then I verify the cancelation message for the generated event

  @MATCH-3489
  Scenario: Verify a past event cannot be published
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I create and save a new event "-10" minutes ahead from now with the following details:
      | Event Name | EventForPastTest |
      | Timezone    | Eastern Time (i.e. America/New_York) |
      | Description | Test |
      | Max Attendees | 30 |
      | EVENT LOCATION BY POSITION  | 1 |
      | EVENT PRIMARY CONTACT BY POSITION | 1 |
    Then HE I verify that a warning message about the past date is displayed

    @MATCH-4101 @manual @ignore
  Scenario: Verify an event for one school cannot be accessed from another
      Given HE I am logged in to Intersect HE as user type "administrator"
      When HE I open the Events list
      When HE I copy the URL for Evets List screen
      And HE I successfully sign out
      Given HE I am logged in to Intersect HE as user type 'Administrator' For Elmira College
      When HE I open the Events List
      When HE I paste the URL on the screen
      And HE User received message "Access Restricted. This page can only be accessed by the institution who created the event"

  @MATCH-3242
  Scenario: Verify event name is clickable on the Manage Events page
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events section
    Then HE I verify that the events' names are clickable and they open the Edit Event screen

  @MATCH-2897
  Scenario: Verify Attendee details on the Edit Event screen - Registered and Cancelled Status for Attendees
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I create and publish a new event "60" minutes ahead from now with the following details:
      | Event Name  | EventForMATCH2897M |
      | Timezone    | Eastern Time (i.e. America/New_York) |
      | Description | Test |
      | Max Attendees | 30 |
      | EVENT LOCATION BY POSITION  | 1 |
      | EVENT PRIMARY CONTACT BY POSITION | 1 |
    When I log in to Family Connection with the following user details:
      | rtsa       | amandahubs | Hobsons!23  |
    And I Navigate to old Colleges tab
    And I open link Upcoming college events
    And I look for the host "The University of Alabama"
    Then I sign up for the event "EventForMATCH2897M"
    Then I register for the event
    And HUBS I successfully sign out
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I open the event of name "EventForMATCH2897M"
    And HE I verify Attendee Data Details on Edit Events attendee screen
      | AttendeeFirstName | Amanda |
      | AttendeeLastName | Hubs |
      | AttendeeEmail | amandahubs@dev.naviance.com |
      | AttendeeStatus | Registered                 |
    When I log in to Family Connection with the following user details:
      | rtsa       | amandahubs | Hobsons!23  |
    And I Navigate to old Colleges tab
    And I open link Upcoming college events
    And I look for the host "The University of Alabama"
    Then I click on View/Update button on event name "EventForMATCH2897M"
    Then I Cancel for the Event I signed up for
    And HUBS I successfully sign out
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I open the event of name "EventForMATCH2897M"
    And HE I verify Attendee Data Details on Edit Events attendee screen
      | AttendeeFirstName | Amanda |
      | AttendeeLastName | Hubs |
      | AttendeeEmail | amandahubs@dev.naviance.com |
      | AttendeeStatus | Cancelled                 |
    And HE I open the Events list
    And HE I unpublish the event of name "EventForMATCH2897M"
    And HE I open the "Unpublished" tab in Events
    And HE I delete the event of name "EventForMATCH2897M"
    And HE I successfully sign out
    # This test case will fail at step HE I unpublish the event of name "EventForMATCH2897M". There is an open issue "MATCH-5019"

