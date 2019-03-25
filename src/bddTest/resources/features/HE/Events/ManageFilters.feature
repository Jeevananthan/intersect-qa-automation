@HE @Events @EventsFilters
Feature: HE - Events - ManageFilters - As an HE Events user, I can create filters to recommend my event to targeted students

  @MATCH-2895
  Scenario: As a HE Intersect user, I can create a filter in AM Events from the Create Filter Screen
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I open the Events section
    And HE I open the "Filters" tab in the Events section
    And HE I delete the filter of name "FilterTest123"
    And HE I open the Create Filter screen
    When HE I create a new filter based on the following details:
    | Gender | Male |
    #Location is expressed in 'miles;zip'. Example: 50 miles outside of the postal code 12345: 50;12345
    | Location | 50 miles;12345 |
    | Race and Ethnicity | White |
    | Grade Level        | Freshman |
    | GPA                | B     |
    | Filter Name        | FilterTest123 |
    Then HE A filter of name "FilterTest123" is displayed in the filters list
    And HE I delete the filter of name "FilterTest123"

  @MATCH-2895
  Scenario: As a HE Intersect user, I can create a filter in AM Events from the Create Event Screen (MATCH-5414 - fixed)
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I open the Events section
    And HE I open the "Filters" tab in the Events section
    And HE I delete the filter of name "FilterTestxx937"
    And HE I open the "Events" tab in the Events section
    And HE I open the Create Event screen
    And HE I open the Create Filter dialog from the Event Audience field
    When HE I create a new filter based on the following details:
      | Gender | Male |
      #Location is expressed in 'miles;zip'. Example: 50 miles outside of the postal code 12345: 50;12345
      | Location | 50 miles;12345 |
      | Race and Ethnicity | White |
      | Grade Level        | Freshman |
      | GPA                | B     |
      | Filter Name        | FilterTestxx937 |
    And HE I open the Events section
    And HE I open the "Events" tab in the Events section
    And HE I open the Create Event screen
    Then HE A filter of name "FilterTestxx937" is displayed in the Event Audience list
    And HE I open the Events section
    And HE I open the "Filters" tab in the Events section
    And HE I delete the filter of name "FilterTestxx937"

  @MATCH-2895 @MATCH-3509
  Scenario: As a HE User, verify Create Filter Page Validations
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events section
    And HE I open the "Filters" tab in the Events section
    And HE I open the page for filter creation
    And HE I save the filter leaving all the fields blank
    Then HE I verify the error messages for the required fields:
    | Location | Location and Postal Code are required |
    | Filter Name | Filter Name is required            |

  @MATCH-3512
  Scenario: When a user creates a filter from the Create/Edit Event screen, the newly created Filter/Contact/Location
  should be selected by default in the Event (MATCH-5503)
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I open the Events section
    And HE I open the "Events" tab in the Events section
    And HE I open the Create Event screen
    And HE I open the Create Filter dialog from the Event Audience field
    When HE I create a new filter with a unique name based on the following data
      #Location is expressed in 'miles;zip'. Example: 50 miles outside of the postal code 12345: 50;12345
  | Location | 50 miles;12345 |
  | Filter Name        | FilterTestYY498 |
    Then HE I verify that the filter of generated name is displayed by default in the Event Audience field
    And HE I open the Events section
    And HE I open the "Filters" tab in the Events section
    And HE I delete the filter of generated name

  @MATCH-3338
  Scenario: As a user, view a filter to verify which events are assigned to particular filter
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events section
    And HE I open the "Filters" tab in the Events section
    And HE I delete the filter of name "FilterMATCH3338"
    And HE I open the Create Filter screen
    When HE I create a new filter based on the following details:
      | Gender | Male |
      | Location | 50 miles;12345 |
      | Race and Ethnicity | White |
      | Grade Level        | Freshman |
      | GPA                | B     |
      | Filter Name        | FilterMATCH3338  |
    When HE I open the Events section
    And HE I open the "Events" tab in the Events section
    When HE I create and publish a new event "90" minutes ahead from now with the following details:
      | Event Name |  EventForFilterMATCH3338  |
      | Timezone    | Eastern Time (i.e. America/New_York) |
      | Description | Test              |
      | Max Attendees | 30 |
      | EVENT LOCATION BY POSITION | 1  |
      | EVENT PRIMARY CONTACT BY POSITION | 1  |
      | EVENT AUDIENCE        | FilterMATCH3338 |
    When HE I open the Events section
    And HE I open the "Filters" tab in the Events section
    Then HE I verify that the filter of name "FilterMATCH3338" is assigned to "1" events
    And HE I verify that the name of the Event is "EventForFilterMATCH3338" is assigned to filter "FilterMATCH3338"
    And HE I delete the filter of name "FilterMATCH3338"
    And HE I open the "Events" tab in the Events section
    And HE I unpublish the event of name "EventForFilterMATCH3338"
    And HE I delete the event of name "EventForFilterMATCH3338"

  @MATCH-2898
  Scenario: Calculate potential matches for Filter is greater than zero
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I open the Events section
    And HE I open the "Filters" tab in the Events section
    And HE I open the Create Filter screen
    When HE I enter data to create a new filter based on the following details:
      | Gender | Female |
      #Location is expressed in 'miles;zip'. Example: 50 miles outside of the postal code 12345: 250;45040
      | Location | 250 miles;45040|
    And HE I verify Filter Summary value is greater than zero
