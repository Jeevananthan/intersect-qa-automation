@HE @Events @EventsFilters @Filters
Feature: HE - Events - As a HE Intersect user with AM Events, I need the ability to create a filter so that I can have
  my event be recommended to targeted students.

  @MATCH-2895
  Scenario: As a HE Intersect user, I can create a filter in AM Events
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I open the Events section
    And HE I open the "Filters" tab in the Events section
    When HE I create a new filter based on the following details:
    | Gender | Male |
    #Location is expressed in 'miles;zip'. Example: 50 miles outside of the postal code 12345: 50;12345
    | Location | 50;12345 |
    | Race and Ethnicity | White |
    | Grade Level        | 12    |
    | GPA                | 4     |
    | Filter Name        | FilterTest123 |
    Then HE A filter of name "TestEventFilterEdited" is displayed in the filters list
    And HE I delete the event of name "TestEvent33"
    And HE I successfully sign out

  Scenario: As a HE User, verify Create Filter Page Validations
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events section
    And HE I open the "Filters" tab in the Events section
    And HE I open the page for filter creation
    And HE I save the filter leaving all the fields blank
    Then HE I verify the error messages for the required fields
    And HE I successfully sign out