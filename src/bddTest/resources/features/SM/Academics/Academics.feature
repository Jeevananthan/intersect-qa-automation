@SM
Feature: SM - Academics - Academics - As a HS student, I need to be able to search for colleges based on the 'Academics' fit criteria

  Background:
    Given SM I am logged in to SuperMatch through Family Connection
    And I clear the onboarding popups if present
    And SM I clear all pills from Must have  and Nice to have boxes

  @MATCH-3554
  Scenario: As a HS student I want to see dynamically display the Institution Type for Certificate option
    When SM I select the "Certificate" radio button from the Academics fit criteria
    When SM I click "Academics" filter criteria tab
    Then SM I verify that checkBox with text "Search for institutions that have ALL of my selected programs" is displayed

  @MATCH-3554
  Scenario: As a HS student I want to see dynamically display the Institution Type checkbox is not checked for Certificate option
    When SM I select the "Certificate" radio button from the Academics fit criteria
    When SM I click "Academics" filter criteria tab
    Then SM I verify that checkBox with text "Search for institutions that have ALL of my selected programs" is not checked

  @MATCH-3554
  Scenario: As a HS student I want to be able to check/uncheck the Institution Type box for Certificate option
    When SM I select the "Certificate" radio button from the Academics fit criteria
    When SM I click "Academics" filter criteria tab
    Then SM I verify that checkBox with text "Search for institutions that have ALL of my selected programs" can be checked|unchecked

  @MATCH-3554
  Scenario: As a HS student I want to see dynamically display the Institution Type for Associate's option
    When SM I select the "Associate's" radio button from the Academics fit criteria
    When SM I click "Academics" filter criteria tab
    Then SM I verify that checkBox with text "Search for institutions that have ALL of my selected programs" is displayed

  @MATCH-3554
  Scenario: As a HS student I want to see dynamically display the Institution Type checkbox is not checked for Associate's option
    When SM I select the "Associate's" radio button from the Academics fit criteria
    When SM I click "Academics" filter criteria tab
    Then SM I verify that checkBox with text "Search for institutions that have ALL of my selected programs" is not checked

  @MATCH-3554
  Scenario: As a HS student I want to be able to check/uncheck the Institution Type box for Associate's option
    When SM I select the "Associate's" radio button from the Academics fit criteria
    When SM I click "Academics" filter criteria tab
    Then SM I verify that checkBox with text "Search for institutions that have ALL of my selected programs" can be checked|unchecked

  @MATCH-3554
  Scenario: As a HS student I want to sure filter pill for Associate's always appears in Must to have box
    When SM I select the "Associate's" radio button from the Academics fit criteria
    And SM I move "Associate's [Any]" from the Must Have box to the Nice to Have box
    And SM I remove the "Associate's [Any]" fit criteria from the Must Have box or Nice to Have box
    When SM I select the "Associate's" radio button from the Academics fit criteria
    Then SM I verify that the Must Have box contains "Associate's [Any]"

  @MATCH-3397
  Scenario Outline: As a HS student, I want to filter colleges I am searching for by Degree Type within the Academics category
  so I can see relevant colleges that match my Degree Type requirements.
    Given SM I click "Academics" filter criteria tab
    And SM I select the "<degreeType>" radio button from the Academics fit criteria
    Then SM I verify that "Include online learning opportunities" checkbox is "unselected" in "Academics" fit criteria
    And SM I select the "Include online learning opportunities" checkbox from "Academics" fit criteria
    And SM I click "Academics" filter criteria tab
    Then I verify that the default text in "div.ui.divider + div.row.supermatch-academics-checkbox-row + div.row div.default.text" is "Start typing..."
    And I scroll the dialog down, anchored in the element "//p[contains(text(), 'Degree')]"
    And SM I pick "<dropDownOption>" from the dropdown "input.search + span + div"
    Then SM I verify that the option "<dropDownOption>" was added to the dropdown field
    And SM I remove the option "<dropDownOption>" from the dropdown field
    And SM I select the "Search for institutions that have ALL of my selected programs" checkbox from "Academics" fit criteria
    Examples:
    | degreeType   | dropDownOption  |
    | Certificate  | Accounting      |
    | Associate's  | Accounting      |
    | Master's     | Accounting and Business/Management |
    | Doctorate    | Accounting and Business/Management |
    | Graduate Certificate | Aboriginal/Indigenous Studies |

