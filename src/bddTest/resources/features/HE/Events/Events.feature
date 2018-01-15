@HE @Events
Feature: HE - Active Match Events - As an HE Intersect User, I need the ability to create/edit/save/publish/delete events in AM Events so that I
  can attract Naviance students to attend my events.

  @MATCH-2913 @MATCH-3219
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
    | EVENT LOCATION        | 1 |
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
    | EVENT LOCATION | 2 |
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

  @MATCH-2913
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
      | EVENT LOCATION        | 1 |
      | EVENT PRIMARY CONTACT | 1 |
    When HE I cancel the created event
    Then HE The cancelled event should be displayed in the canceled events list

  @MATCH-2913
  Scenario: As a HE User, verify Create Event Page Validations
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I open the Create Event screen
    And HE I publish the current event
    Then HE I verify required fields error messages
    And HE I successfully sign out





    



