@HE @Events
Feature: HE - Active Match Events - As a HE Intersect Administrator/Publishing user with Intersect Presence or Legacy ActiveMatch Events subscriptions,
  I should be able to access the ActiveMatch Events product

  @MATCH-2918
  Scenario: As a HE User with Administrator role with active Presence & Events subscription, I can access Events module ( Open issue with logout MATCH-3109)
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I verify the "Events" nav link is displaying for this user
    And HE I open the Events section
    Then HE The Events page is displayed
    And HE I successfully sign out
  Scenario: As a HE User with Publishing role with active Presence & Events subscription, I can access Events module ( Open issue with logout MATCH-3109)
    Given HE I am logged in to Intersect HE as user type "publishing"
    And HE I open the Events section
    Then HE The Events page is displayed
    And HE I successfully sign out
  Scenario: As a HE User with Community role with active Presence & Events subscription, I can not access Events module
    Given HE I am logged in to Intersect HE as user type "community"
    Then HE I verify the "Events" nav link is not displaying for this user
    And HE I successfully sign out

  @MATCH-2918
  Scenario: As a HE User with Administrator role with no Presence/Events subscription, I can not access Events module
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    And SP I set the "Legacy: ActiveMatch Events" module to "inactive" in the institution page
    And SP I set the "Intersect Presence Subscription" module to "inactive" in the institution page
    And SP I Click the Save Changes button
    Then SP I successfully sign out
    Given HE I am logged in to Intersect HE as user type "limited"
    Then HE I verify the "Events" nav link is not displaying for this user
    And HE I successfully sign out
  Scenario: As a HE User with Administrator role with only Legacy Events subscription, I can access Events module
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    And SP I set the "Legacy: ActiveMatch Events" module to "active" in the institution page
    And SP I set the "Intersect Presence Subscription" module to "inactive" in the institution page
    And SP I Click the Save Changes button
    Then SP I successfully sign out
    Given HE I am logged in to Intersect HE as user type "limited"
    And HE I verify the "Events" nav link is displaying for this user
    And HE I open the Events section
    Then HE The Events page is displayed
    And HE I successfully sign out
  Scenario: As a HE User with Administrator role with only Presence subscription, I can access Events module
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    And SP I set the "Legacy: ActiveMatch Events" module to "inactive" in the institution page
    And SP I set the "Intersect Presence Subscription" module to "active" in the institution page
    And SP I Click the Save Changes button
    Then SP I successfully sign out
    Given HE I am logged in to Intersect HE as user type "limited"
    And HE I verify the "Events" nav link is displaying for this user
    And HE I open the Events section
    Then HE The Events page is displayed
    And HE I successfully sign out
    #cleanup
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    And SP I set the "Legacy: ActiveMatch Events" module to "inactive" in the institution page
    And SP I set the "Intersect Presence Subscription" module to "inactive" in the institution page
    And SP I Click the Save Changes button

  @MATCH-2913
  Scenario: An Event can be created/edited/saved/published/deleted
    Given HE I am logged in to Intersect HE as user type "administrator"
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
    Then HE I should see the event of name "TestEvent" present in the events list as Draft event
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
    And HE I click on Save button
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

  @MATCH-2913
  Scenario: As a HE User, verify Create Event Page Validations
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events section
    And HE I click Publish Now button  on events page leaving blank all the fields
    And HE I Verify  required fields error message
    And HE I verify Save and Publish buttons are disabled by default
    And HE I successfully sign out

  @MATCH-2913
  Scenario: As a HE User, Verify Create Event Page Validation for Field
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events section
    And HE I click on Create Event button
    And HE I enter event name as "Event name test maximum two hundred and fifty characters and hit button Publish Now
        This should give an error message if characters are more than 120. Life is beautiful full of surprises.
        Enjoy every moment and give your best and then forgot about it. I am looking forward to seeing a valid error
        message added by Rob Kalmar"
    And HE I verify Event Name error message
    And HE I Enter Event Location as "aaa"
    And HE I verify message "No results found"
    And HE I Enter Event Audience field as "8787"
    And HE I verify message "No results found"





    



