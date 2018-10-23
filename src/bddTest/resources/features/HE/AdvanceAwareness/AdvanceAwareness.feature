@HE
Feature: HE - Upgrade - Upgrade - As an HE user in Intersect, I need to be engaged to perform actions on my subscription Advanced Awareness
         so that I can set Diversity , Configure Audience  etc etc


  @MATCH-4629
  Scenario: As an HE user, I want to configure Advance Awareness
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I click on button Configure for subscription "Advanced Awareness"
    And HE I click on Advance Awareness menu option "Diversity"
    And HE I select following Diversity Settings
    | Asian |
    | Hispanic/Latino of any race |
    And HE I click on Advance Awareness menu option "Competitors"
    And SM I press button "SAVE"
    And HE I click on Advance Awareness menu option "Diversity"
    And HE I verify following options are checked
      | Asian |
      | Hispanic/Latino of any race |
    And HE I select following Diversity Settings
      | Multiracial |
    And HE I click on Advance Awareness menu option "Competitors"
    And SM I press button "Don't save changes"
    And HE I click on Advance Awareness menu option "Diversity"
    And HE I verify following options are unchecked
      | Multiracial |
    #Following will un-check previously checked values
    And HE I select following Diversity Settings
      | Asian |
      | Hispanic/Latino of any race |
    And HE I click on Advance Awareness menu option "Competitors"
    And SM I press button "SAVE"

  @MATCH-4399
  Scenario: One Academic Threshold value must be entered if "Use Default Threshold" is selected for a subscription.
  GPA, SAT, ACT are not required
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I navigate to the "advanced-awareness/threshold" url
    Then HE I check "Enabled" checkbox for the first row on the Threshold Page
    Then HE I check "Use Default Filter Values" checkbox for the first row on the Threshold Page
    Then HE I set default filter value "3" for "gpa" on the Threshold Page
    Then HE I clear default filter value for "gpa" on the Threshold Page
    Then HE I clear default filter value for "sat" on the Threshold Page
    Then HE I clear default filter value for "act" on the Threshold Page
    Then SM I press button "Save"
    Then I check if I can see "Invalid Default Filter Values" on the page
    Then I check if I can see "Please review your default filter values to ensure they are in the correct format." on the page
    Then HE I set default filter value "3" for "gpa" on the Threshold Page
    Then HE I check "Use Default Filter Values" checkbox for the first row on the Threshold Page
    Then SM I press button "Save"
    Then I check there is no "Invalid Default Filter Values" text on the page
    Then HE I clear default filter value for "gpa" on the Threshold Page
    Then HE I set default filter value "1400" for "sat" on the Threshold Page
    Then SM I press button "Save"
    Then I check there is no "Invalid Default Filter Values" text on the page
    Then HE I clear default filter value for "sat" on the Threshold Page
    Then HE I set default filter value "30" for "act" on the Threshold Page
    Then SM I press button "Save"
    Then I check there is no "Invalid Default Filter Values" text on the page

  @MATCH-4399
  Scenario Outline: Academic Threshold min and max values for default GPA,SAT,ACT
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I navigate to the "advanced-awareness/threshold" url
    Then HE I clear default filter value for "<defaultValue>" on the Threshold Page
    Then HE I set default filter value "<minValue>" for "<defaultValue>" on the Threshold Page
    Then I check there is no "<message>" text on the page
    Then HE I clear default filter value for "<defaultValue>" on the Threshold Page
    Then HE I set default filter value "<maxValue>" for "<defaultValue>" on the Threshold Page
    Then I check there is no "<message>" text on the page
    Then HE I clear default filter value for "<defaultValue>" on the Threshold Page
    Then HE I set default filter value "<lessThanMin>" for "<defaultValue>" on the Threshold Page
    Then I check if I can see "<message>" on the page
    Then HE I clear default filter value for "<defaultValue>" on the Threshold Page
    Then HE I set default filter value "<moreThanMax>" for "<defaultValue>" on the Threshold Page
    Then I check if I can see "<message>" on the page
    Examples:
      | defaultValue | minValue | maxValue | lessThanMin | moreThanMax | message                          |
      | gpa          | 0.1      | 4.0      | 0.02        | 4.1         | Value must be between 0.1 - 4    |
      | sat          | 400      | 1600     | 399         | 1601        | Value must be between 400 - 1600 |
      | act          | 1        | 36       | 0           | 37          | Value must be between 1 - 36     |

  @MATCH-4399
  Scenario Outline: Academic Threshold min and max values for GPA,SAT,ACT
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I navigate to the "advanced-awareness/threshold" url
    Then HE I check "Enabled" checkbox for the first row on the Threshold Page
    Then HE I uncheck "Use Default Filter Values" checkbox for the first row on the Threshold Page
    Then HE I clear filter value for "<valueName>" on the Threshold Page
    Then HE I set filter value "<minValue>" for "<valueName>" on the Threshold Page
    Then I check there is no "<message>" text on the page
    Then HE I clear filter value for "<valueName>" on the Threshold Page
    Then HE I set filter value "<maxValue>" for "<valueName>" on the Threshold Page
    Then I check there is no "<message>" text on the page
    Then HE I clear filter value for "<valueName>" on the Threshold Page
    Then HE I set filter value "<lessThanMin>" for "<valueName>" on the Threshold Page
    Then I check if I can see "<message>" on the page
    Then HE I clear filter value for "<valueName>" on the Threshold Page
    Then HE I set filter value "<moreThanMax>" for "<valueName>" on the Threshold Page
    Then I check if I can see "<message>" on the page
    Examples:
      | valueName | minValue | maxValue | lessThanMin | moreThanMax | message                          |
      | gpa          | 0.1      | 4.0      | 0.02        | 4.1         | Value must be between 0.1 - 4    |
      | sat          | 400      | 1600     | 399         | 1601        | Value must be between 400 - 1600 |
      | act          | 1        | 36       | 0           | 37          | Value must be between 1 - 36     |



  @MATCH-4399
  Scenario Outline: Academic Threshold GPA, SAT, ACT default values are applied
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I navigate to the "advanced-awareness/threshold" url
    Then HE I clear default filter value for "<filterValue>" on the Threshold Page
    Then HE I set default filter value "<value>" for "<filterValue>" on the Threshold Page
    Then HE I check "Enabled" checkbox for the first row on the Threshold Page
    Then HE I check "Use Default Filter Values" checkbox for the first row on the Threshold Page
    Then HE I verify "<filterValue>" value for the first row is "<value>" on the Threshold Page
    Examples:
      | filterValue | value |
      | gpa         | 0.8   |
      | sat         | 1250  |
      | act         | 13    |

  @MATCH-4399
  Scenario: Academic Threshold check all fields
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I navigate to the "advanced-awareness/threshold" url
    Then HE I check "Enabled" checkbox for the first row on the Threshold Page
    Then HE I uncheck "Use Default Filter Values" checkbox for the first row on the Threshold Page
    Then HE I set filter value "0.8" for "gpa" on the Threshold Page
    Then HE I set filter value "1250" for "sat" on the Threshold Page
    Then HE I set filter value "13" for "act" on the Threshold Page
    Then SM I press button "Save"
    When HE I navigate to the "connection/threshold" url
    Then HE I verify "gpa" value for the first row is "0.8" on the Threshold Page
    Then HE I verify "sat" value for the first row is "1250" on the Threshold Page
    Then HE I verify "act" value for the first row is "13" on the Threshold Page

  @MATCH-5138
  Scenario:  As an Intersect User with Advanced Awareness provisioned,  I need to have Advanced Awareness displayed
  as navigational items so that I can access those subscriptions easily.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then SM I press button "Home"
    Then HE I pick the "Advanced Awareness" from the menu items
    Then I check if I can see "Your Advanced Awareness and Connection Subscriptions" on the page
    Then HE I navigate to the "home" url
    And SM I press button "Configure"
    Then I check if I can see "Your Advanced Awareness and Connection Subscriptions" on the page




