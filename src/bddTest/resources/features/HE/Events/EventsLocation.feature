@HE @EventsLocation @MATCH-2914
Feature: HE - Events - EventsLocation - As an HE Events user, I can manage event locations

  @MATCH-2914
  Scenario: A location can be created/edited/saved/deleted
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I open the Create Event screen
    And HE I create and save a new location with the following details:
    | Location Name | RandomLocation   |
    | Street        | Test Street # 777 |
    | City          | Gotham      |
    | State         | WA          |
    | Postal Code   | 12345-4561  |
    Then HE I should see the location of name "RandomLocation" present in the locations list

    When HE I edit the location currently selected in the event with the following details:
      | Location Name | RandomLocation    |
      | Street        | Test Street # 888 |
      | City          | East Northumberland |
      | State         | VA |
      | Postal Code   | 12345  |
    And HE I take note of the data in the Location
    And HE I save the location
    Then HE The location currently selected should be updated

    When HE I delete the open location
    Then HE The deleted location of name "RandomLocation" should not be present in the locations list

  @MATCH-2914
  Scenario: As a HE User, verify Create Location Validations
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I open the Create Event screen
    And HE I open the Create Location dialog
    And HE I edit the new location with the following details:
      | State         | XX |
      | Postal Code   | ABCDE  |
    And HE I save the current location
    And HE I verify required fields error messages

  @MATCH-2914
  Scenario: As a HE user, verify the message when deleting a location associated with a unpublished event
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I open the Create Event screen
    And HE I create and save a new location with the following details:
      | Location Name | LocationToDeleteUnpublished3254 |
    When HE I open the Events list
    And HE I create and save a new event "3" minutes ahead from now with the following details:
      | Event Name     | TestEventUnpublished444 |
      | Max Attendees  | 30        |
      | EVENT LOCATION | LocationToDeleteUnpublished3254 |
      | EVENT PRIMARY CONTACT BY POSITION | 1 |
    And HE I open the Create Event screen
    Then HE I verify the error message when deleting the location of name "LocationToDeleteUnpublished" associated to a "unpublished" event
    And HE I open the Events list
    And HE I delete the event of name "TestEventUnpublished444"

  @MATCH-2914
  Scenario: As a HE user, verify the message when deleting a location associated with a published event
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I open the Create Event screen
    And HE I create and save a new location with the following details:
      | Location Name | LocationToDeletePublished |
    When HE I open the Events list
    And HE I create and publish a new event "3" minutes ahead from now with the following details:
      | Event Name     | TestEventPublished |
      | Max Attendees  | 30        |
      | EVENT LOCATION | LocationToDeletePublished |
      | EVENT PRIMARY CONTACT BY POSITION | 1 |
    And HE I open the Create Event screen
    Then HE I verify the error message when deleting the location of name "LocationToDeletePublished" associated to a "published" event
    And HE I open the Events list
    And HE I unpublish the event of name "TestEventPublished"
    And HE I delete the event of name "TestEventPublished"
    And HE I open the Create Event screen
    And HE I delete the location of name "LocationToDeletePublished"
    And HE I open the Events list

  @MATCH-2914
  Scenario: As a HE user, verify the message when deleting a location associated with an expired event
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    When HE I open the Create Event screen
    Then HE I verify the error message when deleting the location of name "ExpiredLocationForAutomation" associated to a "expired" event
    When HE I open the Events list




    



