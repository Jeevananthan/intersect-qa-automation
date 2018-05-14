@SM
Feature: As a HS student accessing SuperMatch through Family Connection I need to be able to search college based on
  certain fit criteria

  @MATCH-3592
  Scenario: As a HS student accessing SuperMatch through Family Connection I need to be presented with an Student Body
            Size fit criteria
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I verify the Student Body UI in Resources Dropdown

  @MATCH-3246
  Scenario: As a HS student accessing College Search through Family Connection I need to be presented with an
            'empty state' page (no filters selected yet) so I can perform a search when ready.
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I verify if dark blue header is present
    Then SM I verify if Your Fit Criteria text is present
    Then SM I verify the Choose Fit Criteria bar
    Then SM I verify Select Criteria to Start button and instructional text
    Then SM I verify Must Have and Nice to Have boxes
    Then SM I verify the empty results table
    Then SM I verify the dark blue footer

  @MATCH-3208
  Scenario: As a HS student accessing SuperMatch through Family Connection I need to be able to
            add or remove filter criteria from the Must Have box
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I select the "Counseling Services" checkbox from the Resources fit criteria
    Then SM I remove the "Counseling Services" fit criteria from the Must Have box or Nice to Have box
    And SM I verify that the Must Have box does not contain "Counseling Services"
    And SM I verify that the "Counseling Services" checkbox from the Resources fit criteria is "unchecked"

  @MATCH-3911
  Scenario: As a HS student who is interacting with the 'Academics' fit category dropdown, selected Bachelor's for the
            degree fit criteria, and has searched and selected at least one Major and one Minor, I want SuperMatch to
            treat those two entities as separate fit criteria so I can moved Majors and Minors around independently
            between the Must Have and Nice to Have boxes.
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I select the "Bachelor's" radio button from the Academics fit criteria
    Then SM I select the following majors in the SEARCH MAJORS multi-select combobox for Bachelor's degree type
    |Accounting|
    And SM I verify that the Must Have box contains "Major [1]"
    Then SM I select the following minors in the SEARCH MINORS multi-select combobox for Bachelor's degree type
    |Drawing|
    And SM I verify that the Must Have box contains "Minor [1]"
    Then SM I move "Minor [1]" from the Must Have box to the Nice to Have box
    And SM I verify that the Must Have box contains "Major [1]"
    And SM I verify that the Nice to Have box contains "Minor [1]"
    Then SM I move "Major [1]" from the Must Have box to the Nice to Have box
    And SM I verify that the Nice to Have box contains "Major [1]"
    And SM I verify that the Nice to Have box contains "Minor [1]"
    Then SM I unselect the following majors in the SEARCH MAJORS multi-select combobox for Bachelor's degree type
    |Accounting|
    Then SM I unselect the following minors in the SEARCH MINORS multi-select combobox for Bachelor's degree type
    |Drawing|
    And SM I verify that the Must Have box does not contain "Major [1]"
    And SM I verify that the Must Have box does not contain "Minor [1]"
    And SM I verify that Nice to Have box does not contain "Major [1]"
    And SM I verify that Nice to Have box does not contain "Minor [1]"
    Then SM I select the following majors in the SEARCH MAJORS multi-select combobox for Bachelor's degree type
    |Accounting|
    Then SM I select the following minors in the SEARCH MINORS multi-select combobox for Bachelor's degree type
    |Drawing|
    And SM I verify that the Must Have box contains "Major [1]"
    And SM I verify that the Must Have box contains "Minor [1]"
    And SM I verify that Nice to Have box does not contain "Major [1]"
    And SM I verify that Nice to Have box does not contain "Minor [1]"
    Then SM I select the "Associate's" radio button from the Academics fit criteria
    And SM I verify that the Must Have box does not contain "Major [1]"
    And SM I verify that the Must Have box does not contain "Minor [1]"

  @MATCH-3432
  Scenario: As a HS student reviewing results in SuperMatch, I want to be able to select what details I see on each
            college in my search results so the information I care most about is visible to review.
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I select the "Counseling Services" checkbox from the Resources fit criteria
    Then SM I verify the default column headers displayed in the results table
    |Admission Info   |
    |Financial Aid    |
    |Pick what to show|
    Then SM I verify if the option selected or defaulted in column header can be changed to "Athletics"
