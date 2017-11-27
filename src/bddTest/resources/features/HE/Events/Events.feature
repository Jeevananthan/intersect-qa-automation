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
    And HE I successfully sign out

#  @MATCH-2913
#  Scenario: As a HE User, verify Create Event Page Validations
#    Given HE I am logged in to Intersect HE as user type "administrator"
#    When HE I open the Events section
#    And HE I click Publish Now button  on events page leaving blank all the fields
#    And HE I Verify  required fields error message
#    And HE I verify Save and Publish buttons are disabled by default
#    And HE I successfully sign out

#  @MATCH-2913
#  Scenario: As a HE User, Verify Create Event Page Validation for Field
#    Given HE I am logged in to Intersect HE as user type "administrator"
#    When HE I open the Events section
#    And HE I click on Create Event button
#    And HE I enter event name as "Event name test maximum two hundred and fifty characters and hit button Publish Now
#        This should give an error message if characters are more than 120. Life is beautiful full of surprises.
#        Enjoy every moment and give your best and then forgot about it. I am looking forward to seeing a valid error
#        message added by Rob Kalmar"
#    And HE I verify Event Name error message
#    And HE I Enter Event Location as "aaa"
#    And HE I verify message "No results found"
#    And HE I Enter Event Audience field as "8787"
#    And HE I verify message "No results found"





    



