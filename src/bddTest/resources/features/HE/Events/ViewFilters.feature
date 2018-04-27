@HE @EventFilters
Feature: HE - Events - As a Premium HE Intersect user, I need the ability to view my AM Events filters so I can manage my filters quickly and easily.

  @MATCH-2903
  Scenario: A list of all the AM Events filters is displayed
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events section
    And HE I open the "Filters" tab in the Events section
    Then HE I should be able to see a list of all the AM Events filters
    And HE I successfully sign out

  @MATCH-2903
  Scenario: Filter Name is editable
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events section
    And HE I open the "Filters" tab in the Events section
    And HE I open the Create Filter screen
    When HE I create a new filter based on the following details:
      | Gender | Male |
      | Location | 50 miles;12345 |
      | Race and Ethnicity | White |
      | Grade Level        | Freshman |
      | GPA                | B     |
      | Filter Name        | ViewFilterTest6674 |
    And HE I edit the Event Filter's name "ViewFilterTest6674" to "ViewFilterTest6674Edited"
    Then HE A filter of name "ViewFilterTest6674Edited" is displayed in the filters list
    And HE I delete the filter of name "ViewFilterTest6674Edited"
    And HE I successfully sign out

  @MATCH-2903
  Scenario: A deleted filter is removed from any applied events
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events section
    And HE I open the "Filters" tab in the Events section
    And HE I open the Create Filter screen
    When HE I create a new filter based on the following details:
      | Gender | Male |
      | Location | 50 miles;12345 |
      | Race and Ethnicity | White |
      | Grade Level        | Freshman |
      | GPA                | B     |
      | Filter Name        | FilterToBeRemoved776 |
    When HE I open the Events section
    And HE I open the "Events" tab in the Events section
    When HE I create and save a new event with the following details:
      | Event Name | TestEventDeletedFilter33 |
      | Event Start | 12-21-2018;10:00AM |
      | Timezone    | Eastern Time (i.e. America/New_York) |
      | Description | Test              |
      | Max Attendees | 30 |
      | RSVP Deadline | 12-15-2018;10:00AM |
      | EVENT LOCATION BY POSITION | 1  |
      | EVENT PRIMARY CONTACT BY POSITION | 1  |
      | EVENT AUDIENCE        | FilterToBeRemoved776     |
    And HE I open the "Filters" tab in the Events section
    And HE I delete the filter of name "FilterToBeRemoved776"
    And HE I open the "Events" tab in the Events section
    And HE I open the "Unpublished" tab in the Events section
    And HE I open the event of name "TestEventDeletedFilter33"
    Then HE The filter of name "FilterToBeRemoved776" should not be present in the Event Audience list
    When HE I open the Events section
    And HE I open the "Events" tab in the Events section
    And HE I delete the event of name "TestEventDeletedFilter33"
    And HE I successfully sign out

  @MATCH-2903
  Scenario: The number of published events that a specific filter is assigned to is displayed
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events section
    And HE I open the "Filters" tab in the Events section
    And HE I open the Create Filter screen
    When HE I create a new filter based on the following details:
      | Gender | Male |
      | Location | 50 miles;12345 |
      | Race and Ethnicity | White |
      | Grade Level        | Freshman |
      | GPA                | B     |
      | Filter Name        | AssignedFilter88663 |
    When HE I open the Events section
    And HE I open the "Events" tab in the Events section
    When HE I create and publish a new event "30" minutes ahead from now with the following details:
      | Event Name | AsignedEvent17863 |
      | Timezone    | Eastern Time (i.e. America/New_York) |
      | Description | Test              |
      | Max Attendees | 30 |
      | EVENT LOCATION BY POSITION | 1  |
      | EVENT PRIMARY CONTACT BY POSITION | 1  |
      | EVENT AUDIENCE        | AssignedFilter88663     |
    When HE I open the Events section
    And HE I open the "Filters" tab in the Events section
    Then HE I verify that the filter of name "AssignedFilter88663" is assigned to "1" events
    And HE I delete the filter of name "AssignedFilter88663"
    And HE I open the "Events" tab in the Events section
    And HE I unpublish the event of name "AsignedEvent17863"
    And HE I delete the event of name "AsignedEvent17863"
    And HE I successfully sign out