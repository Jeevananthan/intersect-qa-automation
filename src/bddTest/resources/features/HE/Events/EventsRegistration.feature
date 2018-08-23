@HE @Events
Feature: HE - Events - As a HE Events user, I can view and download my event attendees

  @MATCH-3312
  Scenario: Verify access to the Attendees list by clicking on the "attendee status bar / students" area of Published Events
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
  Scenario: Verify access to the Attendees list by clicking on the ellipse of a Published Event
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
  Scenario: Verify access to the Attendees list by clicking on the ellipse of a Cancelled and Expired  Event
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I open the "Cancelled" tab in Events
    Then HE I verify that the Attendees tab in the event of name "AutomationCancelledEvent" is opened by clicking the Attendees option in the edit menu
    When HE I open the Events list
    And HE I open the "Expired" tab in Events
    Then HE I verify that the Attendees tab in the event of name "ExpiredEventForAutomation" is opened by clicking the Attendees option in the edit menu
    And HE I successfully sign out

  @MATCH-3312
  Scenario: Verify access to the Attendees list by clicking on the "attendee status bar / students" area of Cancelled/Expired Events
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I open the "Cancelled" tab in Events
    Then HE I verify that the Attendees tab in the event of name "AutomationCancelledEvent" is opened by clicking the attendee status bar/students area
    When HE I open the Events list
    And HE I open the "Expired" tab in Events
    Then HE I verify that the Attendees tab in the event of name "ExpiredEventForAutomation" is opened by clicking the attendee status bar/students area
    And HE I successfully sign out

  @MATCH-4361
  Scenario: Verify Event information from Naviance Student
    Given  I log in to Family Connection with the following user details:
      | rtsa       | benhubs | Hobsons!23  |
    And I Navigate to old Colleges tab
    And I open link Upcoming college events
    And I click on icon next to College Events Header
    And I verify Events Information and Welcome message