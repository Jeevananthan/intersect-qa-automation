@HE
Feature: HE - Upgrade - Upgrade - As an HE user in Intersect, I need to be engaged to perform actions on my subscription Advanced Awareness
         so that I can set Diversity , Configure Audience  etc etc

  @MATCH-4629
  Scenario: As an HE user, I want to configure Advance Awareness
    #Pre-condition
    Given SP I am logged in to the Admin page as a Support user
    When SP I select "Alpena Community College" from the institution dashboard
    Then SP I set the "Advanced Awareness" module to "active" with the start date "0" and end date "35" in the institution page
    And SP I Click the Save Changes button
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    And SP I click 'ADD NEW SUBSCRIPTION' button
    And SP I select the radio button "State" in Add new Subscription modal
    And SP I click the Next button
    And SP I fill the new subscription with the following data:
      | State                | Alabama                  |
      | Counties             | None                     |
      | Diversity Filter     | Racial & Ethnic Minority |
      | Competitors          | Alpena Community College |
      | Majors               | yes                      |
      | Connection           | yes                      |
      | Start date           | 2 days from now          |
      | End date             | 3 days from now          |
      | Zips                 | None                     |
      | Radius from zips     | None                     |
    And SP I save the new subscription
    Then SP I successfully sign out

    Given HE I am logged in to Intersect HE as user type "AdvancedAwareness"
    And HE I click on button Configure for subscription "Advanced Awareness"
    And HE I click on Advance Awareness menu option "Diversity"
    And HE I select following Diversity Settings
    | Asian |
    | Hispanic/Latino of any race |
    Then HE I click Diversity save button
    And HE I click on Advance Awareness menu option "Competitors"
    And HE I click the advanced awareness save button
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
    And HE I unselect following Diversity Settings
    #And HE I select following Diversity Settings
      | Asian |
      | Hispanic/Latino of any race |
    And HE I click on Advance Awareness menu option "Competitors"
    And HE I click the advanced awareness save button
    Then HE I successfully sign out

    Given SP I am logged in to the Admin page as a Support user
    When SP I select "Alpena Community College" from the institution dashboard
    Then SP I set the "Advanced Awareness" module to "active" with the start date "0" and end date "35" in the institution page
    And SP I Click the Save Changes button
    And HE I click the link "Advanced Awareness"
    And SP I delete the subscriptions with the following data:
      | Diversity  | Racial & Ethnic Minority   |
      | Start Date | 2 days from now            |
    Then SP I successfully sign out

  @MATCH-4399
  Scenario: One Academic Threshold value must be entered if "Use Default Threshold" is selected for a subscription.
  GPA, SAT, ACT are not required
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    And SP I navigate to the GraphiQL page
    And SP I create a new subscription via GraphiQL with the data in "match-5545SubscriptionData.json" and the following settings:
      | startDate | 2 days before now |
      | endDate   | 2 days after now  |
    And SP I successfully sign out

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
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    And SP I successfully sign out

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
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    And SP I navigate to the GraphiQL page
    And SP I create a new subscription via GraphiQL with the data in "match-5545SubscriptionData.json" and the following settings:
      | startDate | 2 days before now |
      | endDate   | 2 days after now  |
    And SP I successfully sign out
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
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    And SP I successfully sign out
    Examples:
      | valueName | minValue | maxValue | lessThanMin | moreThanMax | message                          |
      | gpa          | 0.1      | 4.0      | 0.02        | 4.1         | Value must be between 0.1 - 4    |
      | sat          | 400      | 1600     | 399         | 1601        | Value must be between 400 - 1600 |
      | act          | 1        | 36       | 0           | 37          | Value must be between 1 - 36     |



  @MATCH-4399
  Scenario Outline: Academic Threshold GPA, SAT, ACT default values are applied
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    And SP I navigate to the GraphiQL page
    And SP I create a new subscription via GraphiQL with the data in "match-5545SubscriptionData.json" and the following settings:
      | startDate | 2 days before now |
      | endDate   | 2 days after now  |
    And SP I successfully sign out
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I navigate to the "advanced-awareness/threshold" url
    Then HE I clear default filter value for "<filterValue>" on the Threshold Page
    Then HE I set default filter value "<value>" for "<filterValue>" on the Threshold Page
    Then HE I check "Enabled" checkbox for the first row on the Threshold Page
    Then HE I check "Use Default Filter Values" checkbox for the first row on the Threshold Page
    Then HE I verify "<filterValue>" value for the first row is "<value>" on the Threshold Page
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    And SP I successfully sign out
    Examples:
      | filterValue | value |
      | gpa         | 0.8   |
      | sat         | 1250  |
      | act         | 13    |

  @MATCH-4399
  Scenario: Academic Threshold check all fields
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    And SP I navigate to the GraphiQL page
    And SP I create a new subscription via GraphiQL with the data in "match-5545SubscriptionData.json" and the following settings:
      | startDate | 2 days before now |
      | endDate   | 2 days after now  |
    And SP I successfully sign out
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
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    And SP I successfully sign out

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


  @MATCH-4402 @MATCH-5367
  Scenario: As an HE user, I need to be be able to compose my AM Next Gen Competitors messaging so that I can  attract
  the students with interests in other schools.
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    And SP I navigate to the GraphiQL page
    And SP I create a new subscription via GraphiQL with the data in "match-5545SubscriptionData.json" and the following settings:
      | startDate | 2 days before now |
      | endDate   | 2 days after now  |
    And SP I successfully sign out
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I navigate to the "advanced-awareness/competitors" url
    Then HE I check competitors are displayed in alphabetical order
    Then HE I check each competitor contains option "Display without message"
    Then HE I check each competitor contains option "Show same message to all audiences"
    Then HE I check each competitor contains option "Differentiate message per audience"
    Then HE I select "Show same message to all audiences" option for Competitors
    Then HE I set a Competitor "Show same message to all audiences automation message" message
    Then HE I select "Differentiate message per audience" option for Competitors
    Then HE I set a Competitor "Differentiate message per audience automation message" message
    Then I submit button "Save"
    Then I  wait for the success message "Competitor messages have been successfully updated."
    Then HE I navigate to the "advanced-awareness/competitors" url
    Then HE I select "Show same message to all audiences" option for Competitors
    Then I check if I can see "Show same message to all audiences automation message" on the page
    Then HE I select "Differentiate message per audience" option for Competitors
    Then I check if I can see "Differentiate message per audience automation message" on the page
    Then HE I set a Competitor "" message
    Then HE I select "Differentiate message per audience" option for Competitors
    Then HE I set a Competitor "" message
    Then I submit button "Save"
    Then I  wait for the success message "Competitor messages have been successfully updated."
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    And SP I successfully sign out

  @MATCH-4403 @MATCH-5368
  Scenario: As an HE user, I need to be be able to compose my Majors messaging so that I can  attract the students with
  interests in specific majors and interest of study
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "Alpena Community College" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    When SP I select "Alpena Community College" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I click 'ADD NEW SUBSCRIPTION' button
    And SP I select the radio button "State" in Add new Subscription modal
    And SP I click the Next button
    And SP I fill the new subscription with the following data:
      | State                | Alabama                      |
      | Diversity Filter     | Racial & Ethnic Minority     |
      | Competitors          | The University of Alabama    |
      | Majors               | yes                          |
      | Connection           | yes                          |
      | Start date           | 2 days from now              |
      | End date             | 3 days from now              |
    And SP I save the new subscription
    And SP I navigate to the GraphiQL page
    And SP I create a new subscription via GraphiQL with the data in "match-5545SubscriptionData.json" and the following settings:
      | startDate | 2 days before now |
      | endDate   | 2 days after now  |
    And SP I successfully sign out
    Given HE I am logged in to Intersect HE as user type "AdvancedAwareness"
    When HE I navigate to the "advanced-awareness/majors" url
    Then HE I check competitors are displayed in alphabetical order
    Then HE I check each major contains option "Display without message"
    Then HE I check each major contains option "Show same message to all majors"
    Then HE I check each major contains option "Differentiate message per major"
    Then HE I select "Show same message to all majors" option for Majors
    Then HE I set a Competitor "Show same message to all majors automation message" message
    Then HE I select "Differentiate message per major" option for Majors
    Then HE I set a Competitor "Differentiate message per major automation message" message
    Then I submit button "Save"
    Then I  wait for the success message "Major messages have been successfully updated."
    Then HE I navigate to the "advanced-awareness/majors" url
    Then HE I select "Show same message to all majors" option for Majors
    Then I check if I can see "Show same message to all majors automation message" on the page
    Then HE I select "Differentiate message per major" option for Majors
    Then I check if I can see "Differentiate message per major automation message" on the page
    Then HE I select "Show same message to all majors" option for Majors
    Then HE I set a Competitor "" message
    Then HE I select "Differentiate message per major" option for Majors
    Then HE I set a Competitor "" message
    Then I submit button "Save"
    Then I  wait for the success message "Major messages have been successfully updated."
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    And SP I successfully sign out