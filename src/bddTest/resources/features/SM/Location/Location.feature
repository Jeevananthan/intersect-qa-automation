@SM
Feature: SM - Feature - As a HS student, I need to be able to search for colleges based on the 'Locale' fit criteria

  @MATCH-3848
  Scenario: As a HS student I want to see validation message when only the 'Select Miles' input has been answered, but not the postal code input
    Given SM I am logged in to SuperMatch through Family Connection
    When I select the following data from the Location Fit Criteria
      | Search Type        | Zip Code |
      | Search by distance | 91203    |
    Then SM I see validation message "Select miles to finish adding this criteria"

  @MATCH-3848
  Scenario: As a HS student I want to see validation message when only the 'Zip Code' input has been answered, but not the miles input
    Given SM I am logged in to SuperMatch through Family Connection
    When I select the following data from the Location Fit Criteria
      | Search Type        | Select Miles    |
      | Search by distance | Within 25 miles |
   Then SM I see validation message "Enter postal code to finish adding this criteria"

  @MATCH-3848
  Scenario: As a HS student I want to see validation message when enter incorrect 'Zip Code' value
    Given SM I am logged in to SuperMatch through Family Connection
    When I select the following data from the Location Fit Criteria
      | Search Type        | Select Miles    | Zip Code |
      | Search by distance | Within 25 miles | 333      |
    Then SM I see validation message "Enter a valid, 5 digit zip code"

