@HE @Events
Feature: HE - Active Match Events - As an HE Intersect User, I need the ability to create/edit/save/publish/delete events in AM Events so that I
  can attract Naviance students to attend my events.

  @MATCH-2913 @MATCH-3219 @MATCH-2902
  Scenario: An Event can be created/edited/saved/published/deleted
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I create and save a new event with the following details:
    | Event Name | TestEvent7777 |
    | Event Start | 12-21-2018;10:00AM |
    | Timezone    | Eastern Time (i.e. America/New_York) |
    | Description | Test              |
    | Max Attendees | 30 |
    | RSVP Deadline | 12-15-2018;10:00AM |
#    Select Location, Contact and Filter (audience) by position (starting in 1).
#    This is because currently we can create locations, contacts and filters with
#    the same name.
    | EVENT LOCATION BY POSITION  | 1 |
    | EVENT PRIMARY CONTACT | 1 |
    | EVENT AUDIENCE        | 1 |
    Then HE I should see the event of name "TestEvent7777" present in the unpublished events list as Draft event

    When HE I edit the event of name "TestEvent7777" with the following details:
    | Event Name | TestEvent7777Edited |
    | Event Start | 12-23-2018;11:00AM |
    | Timezone    | Atlantic Time (i.e. America/Puerto_Rico) |
    | Description | TestEdited         |
    | Max Attendees | 40               |
    | RSVP Deadline | 12-22-2018;11:00AM |
    | EVENT LOCATION BY POSITION | 2 |
    | EVENT PRIMARY CONTACT | 2 |
    | EVENT AUDIENCE        | 2 |
    And HE I take note of the data in the Event
    And HE I save the draft
    Then HE The event of name "TestEvent7777Edited" should be updated

    When HE I publish the current event
    Then HE I should see the event of name "TestEvent7777Edited" present in the events list as a published event

    When HE I unpublish the event of name "TestEvent7777Edited"
    And HE I delete the event of name "TestEvent7777Edited"
    Then HE The deleted event of name "TestEvent7777Edited" should not be displayed in the unpublished events list
    And HE I successfully sign out

  @MATCH-2913 @MATCH-2902
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
      | EVENT LOCATION BY POSITION | 1 |
      | EVENT PRIMARY CONTACT | 1 |
    When HE I cancel the created event
    Then HE The cancelled event should be displayed in the canceled events list

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
      | EVENT LOCATION BY POSITION | 1 |
      | EVENT PRIMARY CONTACT | 1 |
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
      | EVENT PRIMARY CONTACT | 1 |
    And HE I cancel the created event
    When I log in to Family Connection with the following user details:
      | rtsa       | benhubs | Hobsons!23  |
    And I Navigate to old Colleges tab
    And I open link Upcoming college events
    And I look for the host "The University of Alabama"
    Then I verify the cancelation message for the generated event
    And HUBS I successfully sign out
