@SM
Feature: SM - Location - Location - As a HS student, I need to be able to search for colleges based on the 'Locale' fit criteria

  @MATCH-3848 @ignore @unnecessary
  Scenario: As a HS student I want to see validation message when only the 'Select Miles' input has been answered, but not the postal code input
    Given SM I am logged in to SuperMatch through Family Connection
    When SM I select the following data from the Location Fit Criteria
      | Search Type        | Zip Code |
      | Search by distance | 91203    |
    Then SM I see validation message "Select miles to finish adding this criteria"

  @MATCH-3848 @ignore @unnecessary
  Scenario: As a HS student I want to see validation message when only the 'Zip Code' input has been answered, but not the miles input
    Given SM I am logged in to SuperMatch through Family Connection
    When SM I select the following data from the Location Fit Criteria
      | Search Type        | Select Miles    |
      | Search by distance | Within 25 miles |
   Then SM I see validation message "Enter postal code to finish adding this criteria"

  @MATCH-3848 @MATCH-4104 @ignore @unnecessary
  Scenario: As a HS student I want to see validation message when enter incorrect 'Zip Code' value
    Given SM I am logged in to SuperMatch through Family Connection
    When SM I select the following data from the Location Fit Criteria
      | Search Type        | Select Miles    | Zip Code |
      | Search by distance | Within 25 miles | 333      |
    Then SM I see validation message "Enter a valid, 5 digit zip code"

  @MATCH-4236
  Scenario: 00 As a HS student viewing the Why drawer of a particular college in my search results,
  I want to see the actual location data for the college so I can clearly see what
  matched/did not match/partially matched Search by state region
    Given SM I am logged in to SuperMatch through Family Connection
    And SM I skip the onboarding modals
    And SM I clear all pills from Must have  and Nice to have boxes
    And SM I clear pinned schools list
    And SM I click "Admission" filter criteria tab
    And SM I clean GPA/SAT/ACT scores
    And HS I Click on close button
    And SM I select the "Small City" checkbox from "Location" fit criteria
    And SM I select the "Historically Black Institutions" checkbox from "Diversity" fit criteria
    And SM I pin "Bennett College" if it is not pinned already
    And SM I pin "Florida Agricultural and Mechanical University" if it is not pinned already
    When SM I click "Location" filter criteria tab
    Then SM I pick "Alabama" from the dropdown "div-state-or-region-search"
    Then HS I Click on close button
    Then SM I press Why button for "Florida Agricultural and Mechanical University" college
    Then I check if I can see "Location [1]" on the page
    Then I check if I can see "Located in Florida (neighboring state)" on the page
    Then SM I press Why button for "Bennett College" college
    Then I check if I can see "Location [1]" on the page
    Then I check if I can see "Located in North Carolina" on the page
    Then SM I press Why button for the first college in results with score 100%
    Then I check if I can see "Location [1]" on the page
    Then I check if I can see "Located in Alabama" on the page

  @MATCH-4236
  Scenario: 01 As a HS student viewing the Why drawer of a particular college in my search results,
  I want to see the actual location data for the college so I can clearly see what
  matched/did not match/partially matched Search by distance
    Given SM I am logged in to SuperMatch through Family Connection
    And SM I skip the onboarding modals
    And SM I clear all pills from Must have  and Nice to have boxes
    And SM I clear pinned schools list
    And SM I click "Admission" filter criteria tab
    And SM I clean GPA/SAT/ACT scores
    And HS I Click on close button
    And SM I select the "Small City" checkbox from "Location" fit criteria
    And SM I select the "Historically Black Institutions" checkbox from "Diversity" fit criteria
    And SM I pin "Florida Agricultural and Mechanical University" if it is not pinned already
    When SM I click "Location" filter criteria tab
    Then SM I select the "Search by distance" from the "Location" fit criteria not closing the tab
    Then SM I pick "Within 250 miles" from the dropdown "supermatch-location-miles-dropdown"
    Then SM I send text "10001" to the Zip Code field
    Then HS I Click on close button
    Then SM I press Why button for "Florida Agricultural and Mechanical University" college
    Then I check if I can see "Within 250 miles of 10001" on the page
    Then I check if I can see "917 miles from 10001" on the page
    Then SM I press Why button for "Delaware State University" college
    Then I check if I can see "Within 250 miles of 10001" on the page
    Then I check if I can see "135 miles from 10001" on the page
    Then SM I press Why button for "Virginia Union University" college
    Then I check if I can see "Within 250 miles of 10001" on the page
    Then I check if I can see "288 miles from 10001" on the page

  @MATCH-4236
  Scenario: 02 As a HS student viewing the Why drawer of a particular college in my search results,
  I want to see the actual location data for the college so I can clearly see what
  matched/did not match/partially matched Campus surrounding
    Given SM I am logged in to SuperMatch through Family Connection
    And SM I skip the onboarding modals
    And SM I clear all pills from Must have  and Nice to have boxes
    And SM I clear pinned schools list
    And SM I click "Admission" filter criteria tab
    And SM I clean GPA/SAT/ACT scores
    And HS I Click on close button
    And SM I select the "Small City" checkbox from "Location" fit criteria
    And SM I select the "Historically Black Institutions" checkbox from "Diversity" fit criteria
    And SM I pin "Florida Agricultural and Mechanical University" if it is not pinned already
    And SM I clear all pills from Must have  and Nice to have boxes
    And SM I select the "Large City" checkbox from "Location" fit criteria
    Then SM I press Why button for "Florida Agricultural and Mechanical University" college
    Then I check if I can see "Campus Surroundings [1]" on the page
    Then I check if I can see "Small City" on the page
    Then SM I press Why button for the first college in results with score 100%
    Then I check if I can see "Campus Surroundings [1]" on the page
    Then I check if I can see "Large City" on the page

  @MATCH-3199
  Scenario: As a HS student, I want to filter colleges I am searching for by state or region so I can see relevant
  colleges that match my location requirements.
    Given SM I am logged in to SuperMatch through Family Connection
    And SM I skip the onboarding modals
    And SM I clear all pills from Must have  and Nice to have boxes
    Then SM I verify that "Search by state or region" checkbox is "selected" in "Location" fit criteria
    And SM I select the "Location" fit criteria
    And I click the dropdown "div[role='combobox']"
    Then I verify that the default text in "div.default.text" is "Type US State or Canadian Province..."
    And I click the dropdown "div[role='combobox']"
    And I select the option "Alberta" from the list "span.text"
    Then SM I verify that the option "Alberta" was added to the dropdown field
    And I click the dropdown "div[role='combobox']"
    And I select the option "Arizona" from the list "span.text"
    Then SM I verify that 2 items are displayed in the dropdown field
    And SM I remove the option "Alberta" from the dropdown field
    Then SM I verify that 1 items are displayed in the dropdown field
    Then I check if I can see "Quick Selection: US Regions & Others" on the page
    And I click the dropdown "div[role='combobox'] i.teal"
    And SM I remove the option "Arizona" from the dropdown field
    And SM I select the "Central" checkbox from "Location" fit criteria
    Then SM I verify that the following options were added to the dropdown field:
    | Kansas |
    | Missouri |
    | Nebraska |
    | North Dakota |
    | Oklahoma     |
    | South Dakota |
    | Texas        |
    And SM I unselect the "Central" checkbox from the "Location" fit criteria
    Then SM I verify that 0 items are displayed in the dropdown field

  @MATCH-3199
  Scenario: As a HS student, I want to filter colleges I am searching for by distance so I can see relevant
  colleges that match my location requirements.
    Given SM I am logged in to SuperMatch through Family Connection
    And SM I skip the onboarding modals
    And SM I clear all pills from Must have  and Nice to have boxes
    And SM I open the "Location" tab
    And I select the radio button "Search by distance"
    And I click the dropdown "div.default"
    Then I verify that the options list "span.text" matches the list in "select.miles.options.list"
    And I click the dropdown "div.default"
    Then I check if I can see "Campus Surroundings" on the page
    Then SM I select the "Large City" checkbox from "Location" fit criteria
    Then SM I select the "Small City" checkbox from "Location" fit criteria
    And SM I unselect the "Large City" checkbox from the "Location" fit criteria
    And SM I unselect the "Small City" checkbox from the "Location" fit criteria

  @MATCH-3708
  Scenario: As a HS student that is comparing my pinned schools, I want to see Location details about each college
  side by side so I can determine which pinned college is a best fit for me based on their Location.
    Given SM I am logged in to SuperMatch through Family Connection
    And I clear the onboarding popups if present
    And SM I clear all the pinned college
    And SM I search for "The University of Alabama" college in search bar
    And SM I open the Pinned Schools Compare screen
    And SM I verify all the below options available in Location fit criteria in Location expandable drawer
      | Location | Region    | Campus Surroundings |
      | Alabama  | Southeast | Small City          |
