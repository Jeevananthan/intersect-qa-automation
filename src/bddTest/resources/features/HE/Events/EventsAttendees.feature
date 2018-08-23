@HE @Events
Feature: HE - EventsAccess - As a HE Intersect Administrator/Publishing user with Intersect Presence or Legacy ActiveMatch Events subscriptions,
  I should be able to see the attendees of an event

  @MATCH-2906
  Scenario: As a HE Intersect user, I need the ability to export the list of attendees for a specific AM Events so that I can import the attendees for my AM Events into my CRM.
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I create and save a new event with a unique name and the following details:
      | Event Name | ExportAttendees |
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
    And HE I open the event of generated name
    And HE I open the "ATTENDEES" tab in the Edit Event screen
    Then HE I verify that the Export Attendees button exports a document of name "am-event-attendees.csv" with the following headers:
    | First Name |
    | Last Name  |
    | Email Address |
    | Date of Birth |
    | Registered Date |
    | Status          |
    And HE I open the Events list
    And HE I cancel the created event
