@MATCH-2918
Feature: As a HE Intersect ADMIN or PUBLISHER with Intersect Presence or Legacy ActiveMatch Events product, I need the
  ability to access the ActiveMatch Events product in Intersect.

  Scenario: Events section is displayed for Admin users in Intersect HE (MATCH-3109)
    Given HE I want to login to the HE app using "jorgetesthobsons@gmail.com" as username and "Hobsons2016!" as password
    And HE I open the Events section
    Then HE The Events page is displayed
    And HE I successfully sign out

  Scenario: Events section is displayed for Publishing users in Intersect HE (MATCH-3109)
    Given HE I want to login to the HE app using "testhobsons1145@mailinator.com" as username and "Hobsons!23" as password
    And HE I open the Events section
    Then HE The Events page is displayed
    And HE I successfully sign out

  Scenario: Events section is not displayed for Community users in Intersect HE
    Given HE I want to login to the HE app using "testhobsonscomm@mailinator.com" as username and "Hobsons!23" as password
    Then HE Events section is not displayed for Community users
    And HE I successfully sign out

  Scenario: An Event can be created/edited/saved/published/deleted
    Given HE I want to login to the HE app using "jorgetesthobsons@gmail.com" as username and "Hobsons2016!" as password
    When HE I open the Events section
    And HE I create and save a new event with the following details:
    | Event Name | TestEvent |
    | Event Start | Dec 21;10:00 AM |
    | Event End   | Dec 22;11:00 AM |
    | Timezone    | Easter Standard Time (EST) |
    | Description | Test                       |
    | Max Attendees | 30                        |
    | RSVP Deadline | Nov 21;10:00 AM          |
    | EVENT LOCATION | Columbus Hall Library   |
    | EVENT PRIMARY CONTACT | Frank Turner     |
    | EVENT AUDIENCE        | Filter 1         |
    Then HE I should see the event of name "TestEvent" present in the events list as an unpublished event
    When HE I edit the event of name "TestEvent" with the following details:
    | Event Name | TestEventEdited |
    | Event Start | Dec 23;10:00 AM |
    | Event End   | Dec 24;11:00 AM |
    | Timezone    | Western Standard Time (WST)|
    | Description | TestEdited                 |
    | Max Attendees | 40                        |
    | RSVP Deadline | Nov 10;10:00 AM          |
    | EVENT LOCATION | Columbus Hall LibraryAnother   |
    | EVENT PRIMARY CONTACT | Frank TurnerAnother     |
    | EVENT AUDIENCE        | Filter 1Another         |
    Then HE The event of name "TestEventEdited" should have the following data:
    | Event Name | TestEventEdited |
    | Event Start | Dec 23;10:00 AM |
    | Event End   | Dec 24;11:00 AM |
    | Timezone    | Western Standard Time (WST)|
    | Description | TestEdited                 |
    | Max Attendees | 40                        |
    | RSVP Deadline | Nov 10;10:00 AM          |
    | EVENT LOCATION | Columbus Hall LibraryAnother   |
    | EVENT PRIMARY CONTACT | Frank TurnerAnother     |
    | EVENT AUDIENCE        | Filter 1Another         |
    When HE I publish the event with name "TestEventEdited"
    Then HE I should see the event of name "TestEventEdited" present in the events list as a published event
    When HE I delete the event of name "TestEventEdited"
    Then HE The deleted event should not be displayed in the events list
    



