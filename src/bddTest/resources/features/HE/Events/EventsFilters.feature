@HE @Events @EventsFilters
Feature: HE - Events - As a HE Intersect user with AM Events, I need the ability to create a filter so that I can have
  my event be recommended to targeted students.

  @MATCH-2895
  Scenario: As a HE Intersect user, I can create a filter in AM Events from the Create Filter Screen
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I open the Events section
    And HE I open the "Filters" tab in the Events section
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
    And HE I successfully sign out

  @MATCH-2895
  Scenario: As a HE Intersect user, I can create a filter in AM Events from the Create Event Screen
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I open the Events section
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
    And HE I open the "Events" tab in the Events section
    And HE I open the Create Event screen
    Then HE A filter of name "FilterTestxx937" is displayed in the Event Audience list
    And HE I open the "Filters" tab in the Events section
    And HE I delete the filter of name "FilterTestxx937"
    And HE I successfully sign out

  @MATCH-2895
  Scenario: As a HE User, verify Create Filter Page Validations
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events section
    And HE I open the "Filters" tab in the Events section
    And HE I open the page for filter creation
    And HE I save the filter leaving all the fields blank
    Then HE I verify the error messages for the required fields
    And HE I successfully sign out