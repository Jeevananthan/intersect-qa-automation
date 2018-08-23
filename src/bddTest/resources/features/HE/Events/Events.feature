@HE @Events
Feature: HE - Active Match Events - As an HE Intersect User, I need the ability to create/edit/save/publish/delete events in AM Events so that I
  can attract Naviance students to attend my events.

  @MATCH-2913 @MATCH-3219 @MATCH-2902 @MATCH-2928
  Scenario: An Event can be created/edited/saved/published/deleted
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I create and save a new event with the following details:
    | Event Name | TestEvent9999 |
    | Event Start | 12-21-2018;10:00AM |
    | Timezone    | Eastern Time (i.e. America/New_York) |
    | Description | Test              |
    | Max Attendees | 30 |
    | RSVP Deadline | 12-15-2018;10:00AM |
#    Select Location, Contact and Filter (audience) by position (starting in 1).
#    This is because currently we can create locations, contacts and filters with
#    the same name.
    | EVENT LOCATION BY POSITION  | 1 |
    | EVENT PRIMARY CONTACT BY POSITION | 1 |
    | EVENT AUDIENCE BY POSITION       | 1 |
    Then HE I should see the event of name "TestEvent9999" present in the unpublished events list as Draft event
    Then HE I verify status "Draft" for the event of name "TestEvent9999"

    When HE I edit the event of name "TestEvent9999" with the following details:
    | Event Name | TestEvent9999Edited |
    | Event Start | 12-23-2018;11:00AM |
    | Timezone    | Atlantic Time (i.e. America/Puerto_Rico) |
    | Description | TestEdited         |
    | Max Attendees | 40               |
    | RSVP Deadline | 12-22-2018;11:00AM |
    | EVENT LOCATION BY POSITION | 2 |
    | EVENT PRIMARY CONTACT BY POSITION | 2 |
    | EVENT AUDIENCE BY POSITION       | 2 |
    And HE I take note of the data in the Event
    And HE I save the draft
    Then HE The event of name "TestEvent9999Edited" should be updated

    When HE I publish the current event
    Then HE I verify status "Published" for the event of name "TestEvent9999Edited"
    Then HE I should see the event of name "TestEvent9999Edited" present in the events list as a published event

    When HE I unpublish the event of name "TestEvent9999Edited"
    And HE I delete the event of name "TestEvent9999Edited"
    Then HE The deleted event of name "TestEvent9999Edited" should not be displayed in the unpublished events list
    And HE I successfully sign out

  @MATCH-2913 @MATCH-2902 @MATCH-2928
  Scenario: An event can be cancelled
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I create and save a new event with a unique name and the following details:
      | Event Name | TestEvent |
      | Event Start | 12-31-2018;10:00AM |
      | Max Attendees | 30 |
      | RSVP Deadline | 12-30-2018;10:00AM |
  #    Select Location, Contact and Filter (audience) by position (starting in 1).
  #    This is because currently we can create locations, contacts and filters with
  #    the same name.
      | EVENT LOCATION BY POSITION  | 1 |
      | EVENT PRIMARY CONTACT BY POSITION | 1 |
    When HE I cancel the created event
    And HE I open the "Cancelled" tab in the Events section
    Then HE I verify status "Cancelled" for the event of generated name
    Then HE The cancelled event should be displayed in the canceled events list

  @MATCH-2928
  Scenario: As a HE Intersect user, I need the ability to track the current event status so that I can understand more
  about the current state of my AM Events.
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I open the "Expired" tab in the Events section
    Then HE I verify status "Expired" for the event of name "ExpiredEventForAutomation"

  @MATCH-2913
  Scenario: As a HE User, verify Create Event Page Validations
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I open the Create Event screen
    And HE I publish the current event
    Then HE I verify required fields error messages for events
    And HE I successfully sign out

  @MATCH-3614
  Scenario: Unpublish Not Allowed for Registered Events
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I create and save a new event with a unique name and the following details:
      | Event Name | NewTestEvent |
      | Event Start | 12-21-2018;10:00AM |
      | Timezone    | Eastern Time (i.e. America/New_York) |
      | Description | Test              |
      | Max Attendees | 30 |
      | RSVP Deadline | 12-15-2018;10:00AM |
      | EVENT LOCATION BY POSITION  | 1 |
      | EVENT PRIMARY CONTACT BY POSITION | 1 |
    And HE I successfully sign out
    When I log in to Family Connection with the following user details:
      | rtsa       | benhubs | Hobsons!23  |
    And I Navigate to old Colleges tab
    And I open link Upcoming college events
    And I look for the host "The University of Alabama"
    Then I sign up for the event of generated name
    And HUBS I successfully sign out

    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I attempt to unpublish the event of generated name
    Then HE I verify the message that warns that an event with attendee cannot be unpublished
    And HE I cancel the created event
    And HE I successfully sign out

  @MATCH-3613
  Scenario: Bug: Cancelled Event Not Showing in Family Connection
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I create and save a new event with a unique name and the following details:
      | Event Name | EventForCancelTest |
      | Event Start | 12-21-2018;10:00AM |
      | Timezone    | Eastern Time (i.e. America/New_York) |
      | Description | Test              |
      | Max Attendees | 30 |
      | RSVP Deadline | 12-15-2018;10:00AM |
      | EVENT LOCATION BY POSITION  | 1 |
      | EVENT PRIMARY CONTACT BY POSITION | 1 |
    And HE I cancel the created event
    When I log in to Family Connection with the following user details:
      | rtsa       | benhubs | Hobsons!23  |
    And I Navigate to old Colleges tab
    And I open link Upcoming college events
    And I look for the host "The University of Alabama"
    Then I verify the cancelation message for the generated event
    And HUBS I successfully sign out

  @MATCH-3489
  Scenario: If a user attempts to publish an event and the event date is past, the system should treat that as an invalid date
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I create and save a new event "-10" minutes ahead from now with the following details:
      | Event Name | EventForCancelTest |
      | Timezone    | Eastern Time (i.e. America/New_York) |
      | Description | Test |
      | Max Attendees | 30 |
      | EVENT LOCATION BY POSITION  | 1 |
      | EVENT PRIMARY CONTACT BY POSITION | 1 |
    Then HE I verify that a warning message about the past date is displayed
    And HE I successfully sign out

  @MATCH-3312
  Scenario: As a HE User, I can access the Attendees bar by clicking the "attendee status bar / students" area of Published Events
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I create and publish a new event with the following details:
      | Event Name | TestEventAttendees65334 |
      | Event Start | 13-31-2018;10:00AM |
      | Max Attendees | 30 |
      | RSVP Deadline | 13-30-2018;10:00AM |
      | EVENT LOCATION BY POSITION  | 1 |
      | EVENT PRIMARY CONTACT BY POSITION | 1 |
    And HE I open the Events section
    And HE I verify that the Attendees tab in the event of name "TestEventAttendees65334" is opened by clicking the attendee status bar/students area
    And HE I open the Events list
    And HE I unpublish the event of name "TestEventAttendees65334"
    And HE I delete the event of name "TestEventAttendees65334"
    And HE I successfully sign out

  @MATCH-3312
  Scenario: As a HE User, I can access the Attendees bar by clicking the ellipse on the far right of a Published
  Event, we should provide an option to "View Attendees".
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I create and publish a new event with the following details:
      | Event Name | TestEventAttendees2 |
      | Event Start | 13-31-2018;10:00AM |
      | Max Attendees | 30 |
      | RSVP Deadline | 13-30-2018;10:00AM |
      | EVENT LOCATION BY POSITION       | 1 |
      | EVENT PRIMARY CONTACT BY POSITION | 1 |
    Then HE I verify that the Attendees tab in the event of name "TestEventAttendees2" is opened by clicking the Attendees option in the edit menu
    And HE I open the Events list
    And HE I unpublish the event of name "TestEventAttendees2"
    And HE I open the "Unpublished" tab in Events
    And HE I delete the event of name "TestEventAttendees2"
    And HE I successfully sign out

  @MATCH-3312
  Scenario: As a HE User, I can access the Attendees bar by clicking the ellipse on the far right of a Cancelled and Expired
  Event, we should provide an option to "View Attendees".
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I open the "Cancelled" tab in Events
    Then HE I verify that the Attendees tab in the event of name "AutomationCancelledEvent" is opened by clicking the Attendees option in the edit menu
    When HE I open the Events list
    And HE I open the "Expired" tab in Events
    Then HE I verify that the Attendees tab in the event of name "ExpiredEventForAutomation" is opened by clicking the Attendees option in the edit menu
    And HE I successfully sign out

  @MATCH-3312
  Scenario: As a HE User, I can access the Attendees bar by clicking the "attendee status bar / students" area of Cancelled/Expired Events
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I open the "Cancelled" tab in Events
    Then HE I verify that the Attendees tab in the event of name "AutomationCancelledEvent" is opened by clicking the attendee status bar/students area
    When HE I open the Events list
    And HE I open the "Expired" tab in Events
    Then HE I verify that the Attendees tab in the event of name "ExpiredEventForAutomation" is opened by clicking the attendee status bar/students area
    And HE I successfully sign out

    @MATCH-4101 @manual @ignore
  Scenario: As a HE User, I want to make sure no other user from different school can access my Events
      Given HE I am logged in to Intersect HE as user type "administrator"
      When HE I open the Events list
      When HE I copy the URL for Evets List screen
      And HE I successfully sign out
      Given HE I am logged in to Intersect HE as user type 'Administrator' For Elmira College
      When HE I open the Events List
      When HE I paste the URL on the screen
      And HE User received message "Access Restricted. This page can only be accessed by the institution who created the event"

  @MATCH-4361
  Scenario: As a Naviance Student , I want to verify College Events Mesages on events screen
    Given  I log in to Family Connection with the following user details:
      | rtsa       | benhubs | Hobsons!23  |
    And I Navigate to old Colleges tab
    And I open link Upcoming college events
    And I click on icon next to College Events Header
    And I verify Events Information and Welcome message

  @MATCH-3242
  Scenario: View Event Screen, Event name should be a clickable link.
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events section
    And HE I open the "Events" tab in the Events section
    Then HE I verify that the events' names are clickable and they open the Edit Event screen

  @MATCH-2897
  Scenario: Verify Attendee details on the Edit Event screen - Registered and Cancelled Status for Attendees
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I create and publish a new event "60" minutes ahead from now with the following details:
      | Event Name  | EventForMATCH2897D |
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
    Then I sign up for the event "EventForMATCH2897D"
    Then I register for the event
    And HUBS I successfully sign out
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I open the event of name "EventForMATCH2897D"
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
    Then I click on View/Update button on event name "EventForMATCH2897D"
    Then I Cancel for the Event I signed up for
    And HUBS I successfully sign out
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I open the event of name "EventForMATCH2897D"
    And HE I verify Attendee Data Details on Edit Events attendee screen
      | AttendeeFirstName | Amanda |
      | AttendeeLastName | Hubs |
      | AttendeeEmail | amandahubs@dev.naviance.com |
      | AttendeeStatus | Cancelled                 |
    And HE I open the Events list
    And HE I unpublish the event of name "EventForMATCH2897D"
    And HE I open the "Unpublished" tab in Events
    And HE I delete the event of name "EventForMATCH2897D"
    And HE I successfully sign out


