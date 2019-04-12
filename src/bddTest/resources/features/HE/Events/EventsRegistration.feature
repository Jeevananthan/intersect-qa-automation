@HE @Events
Feature: HE - Events - EventsRegistration - As an HE Events user, I can view and download my event attendees

  @MATCH-3312
  Scenario: Verify access to the Attendees list by clicking on the "attendee status bar / students" area of Published Events
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I create and publish a new event with the following details:
      | Event Name | RandomEventName |
      | Event Start | 14;10:00AM |
      | Max Attendees | 30 |
      | RSVP Deadline | 7;10:00AM |
      | EVENT LOCATION BY POSITION  | 1 |
      | EVENT PRIMARY CONTACT BY POSITION | 1 |
    And HE I verify that the Attendees tab in the event of name "RandomEventName" is opened by clicking the attendee status bar/students area
    And HE I open the Events list
    And HE I unpublish the event of name "RandomEventName"
    And HE I delete the event of name "RandomEventName"

  @MATCH-3312
  Scenario: Verify access to the Attendees list by clicking on the ellipse of a Published Event
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I create and publish a new event with the following details:
      | Event Name | RandomEventName |
      | Event Start | 14;10:00AM |
      | Max Attendees | 30 |
      | RSVP Deadline | 7;10:00AM |
      | EVENT LOCATION BY POSITION       | 1 |
      | EVENT PRIMARY CONTACT BY POSITION | 1 |
    Then HE I verify that the Attendees tab in the event of name "RandomEventName" is opened by clicking the Attendees option in the edit menu
    And HE I open the Events list
    And HE I unpublish the event of name "RandomEventName"
    And HE I open the "Unpublished" tab in Events
    And HE I delete the event of name "RandomEventName"

  @MATCH-3312
  Scenario: Verify access to the Attendees list by clicking on the ellipse of a Cancelled and Expired  Event
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I open the "Cancelled" tab in Events
    Then HE I verify that the Attendees tab in the event of name "AutomationCancelledEvent" is opened by clicking the Attendees option in the edit menu
    When HE I open the Events list
    And HE I open the "Expired" tab in Events
    Then HE I verify that the Attendees tab in the event of name "ExpiredEventForAutomation" is opened by clicking the Attendees option in the edit menu

  @MATCH-3312
  Scenario: Verify access to the Attendees list by clicking on the "attendee status bar / students" area of Cancelled/Expired Events
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I open the "Cancelled" tab in Events
    Then HE I verify that the Attendees tab in the event of name "AutomationCancelledEvent" is opened by clicking the attendee status bar/students area
    When HE I open the Events list
    And HE I open the "Expired" tab in Events
    Then HE I verify that the Attendees tab in the event of name "ExpiredEventForAutomation" is opened by clicking the attendee status bar/students area

  @MATCH-4361
  Scenario: Verify Event information from Naviance Student
    When SM I am logged in to SuperMatch through Family Connection as user "linussupermatch" with password "Hobsons!23" from school "blue1combo"
    And SM I go to College Events from the SuperMatch main menu
    And I click on icon next to College Events Header
    And I verify Events Information and Welcome message