@HE @Events
Feature: HE - Active Match Events - As an HE Intersect User, I need the ability to create/edit/save/publish/delete events in AM Events so that I
  can attract Naviance students to attend my events.

  @MATCH-2913
  Scenario: An Event can be created/edited/saved/published/deleted
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I create and save a new event with the following details:
    | Event Name | TestEvent |
    | Event Start | 12-21-2017;10:00AM |
    | Timezone    | America/Monterrey |
    | Description | Test                       |
    | Max Attendees | 30 |
    | RSVP Deadline | 12-15-2017 |
#    Select Location, Contact and Filter (audience) by position (starting in 1).
#    This is because currently we can create locations, contacts and filters with
#    the same name.
    | EVENT LOCATION        | 1 |
    | EVENT PRIMARY CONTACT | 1 |
    | EVENT AUDIENCE        | 1 |
    Then HE I should see the event of name "TestEvent" present in the unpublished events list as Draft event

    When HE I edit the event of name "TestEvent" with the following details:
    | Event Name | TestEventEdited |
    | Event Start | 12-23-2017;11:00AM |
    | Timezone    | America/Montevideo |
    | Description | TestEdited         |
    | Max Attendees | 40               |
    | RSVP Deadline | 12-22-2017 |
    | EVENT LOCATION | 2 |
    | EVENT PRIMARY CONTACT | 2 |
    | EVENT AUDIENCE        | 2 |
    And HE I take note of the data in the Event
    And HE I save the draft
    Then HE The event of name "TestEventEdited" should be updated

    When HE I publish the current event
    Then HE I should see the event of name "TestEventEdited" present in the events list as a published event

    When HE I cancel the event of name "TestEventEdited"
    Then HE The canceled event of name "TestEventEdited" should be displayed in the canceled events list

    When HE I delete the event of name "TestEventEdited"
    #Then HE The deleted event of name "TestEventEdited"
    And HE I successfully sign out

  @MATCH-2913
  Scenario: As a HE User, verify Create Event Page Validations
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    #The following step is needed because of an issue in MATCH-2913 (DEFECT 15)
    And HE I create and save a new event with the following details:
      | Event Start | 12-21-2017;10:00AM |

    And HE I publish the current event
    And HE I verify required fields error messages
    And HE I successfully sign out





    



