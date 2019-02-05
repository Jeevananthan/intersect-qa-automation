@HE @ActiveMatch
Feature: As an HE user, I need to understand my Connection and Advansed Awareness subscription status so that I can configure or manage that subscription effectively

  @MATCH-4427 @MATCH-4397
  Scenario Outline:As an HE user, I need to understand my subscriptions that have been provisioned so that I can configure or manage that subscription effectively
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And SP I set the "Connection" module to "active" in the institution page
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I navigate to the "<URL>" url
    Then I check if I can see "Understanding Your Subscriptions" on the page
    Then I check if I can see "Your Advanced Awareness and Connection Subscriptions" on the page
    Then I verify that the column headers in the "connections-table" table are the following:
      | Audience           |
      | Competitors        |
      | Majors             |
      | Connection         |
      | Academic Threshold |
    Examples:
      | URL                         |
      | connection/overview         |
      | advanced-awareness/overview |

  @MATCH-4395
  Scenario: As an HE user who is setting up my Advanced Awareness filters, I need a menu to help me navigate through the configuration pages
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I navigate to the "advanced-awareness/overview" url
    Then HE I click menu tab "Overview"
    Then I check if I can see "Your Advanced Awareness and Connection Subscriptions" on the page
    Then HE I click menu tab "Academic Threshold"
    Then I check if I can see "Default Filter Values" on the page
    Then HE I click menu tab "Diversity"
    Then I check if I can see "Diversity Settings" on the page
    Then HE I click menu tab "Competitors"
    Then HE I click menu tab "Majors"
    Then HE I click menu link "Understanding Your Subscriptions"
    Then I check if I can see "Your Advanced Awareness and Connection Subscriptions" on the page
    Then HE I click menu link "Academic Threshold"
    Then I check if I can see "Default Filter Values" on the page
    Then HE I click menu link "Diversity"
    Then I check if I can see "Diversity Settings" on the page
    Then HE I click menu link "Competitors"
    Then HE I click menu link "Majors"

  @MATCH-5097
  Scenario: As an HE user who is setting up my Connection filters, I need a menu to help me navigate through the configuration pages
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I navigate to the "connection/overview" url
    Then HE I click menu tab "Overview"
    Then I check if I can see "Configure Your Audience" on the page
    Then I check if I can see "Your Advanced Awareness and Connection Subscriptions" on the page
    Then HE I click menu tab "Academic Threshold"
    Then I check if I can see "Default Filter Values" on the page
    Then HE I click menu tab "Diversity"
    Then I check if I can see "Diversity Settings" on the page
    Then HE I click menu link "Understanding Your Subscriptions"
    Then I check if I can see "Your Advanced Awareness and Connection Subscriptions" on the page
    Then HE I click menu link "Academic Threshold"
    Then I check if I can see "Default Filter Values" on the page
    Then HE I click menu link "Diversity"
    Then I check if I can see "Diversity Settings" on the page

  @MATCH-4429 @MATCH-4400
  Scenario: As an HE user, I need to be be able to manage my AM Next Gen Diversity Filter in Connection and Advanced awareness
    Given SP I am logged in to the Admin page as a Support user
    When SP I select "The University of Alabama" from the institution dashboard
    Then SP I set the "Connection" module to "active" in the institution page
    And SP I Click the Save Changes button
    Then SP I successfully sign out
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I navigate to the "connection/overview" url
    Then HE I click menu tab "Diversity"
    And HE I select following Diversity Settings
      | American Indian or Alaskan Native         |
      | Asian                                     |
      | Black or African American                 |
      | Filipino                                  |
      | Hispanic/Latino of any race               |
      | Native Hawaiian or Other Pacific Islander |
      | Multiracial                               |
      | All Other Racial/Ethnic Minority Groups   |
    Then HE I verify following options are checked
      | American Indian or Alaskan Native         |
      | Asian                                     |
      | Black or African American                 |
      | Filipino                                  |
      | Hispanic/Latino of any race               |
      | Native Hawaiian or Other Pacific Islander |
      | Multiracial                               |
      | All Other Racial/Ethnic Minority Groups   |
    And SM I press button "Save"
    Then HE I verify following options are checked
      | American Indian or Alaskan Native         |
      | Asian                                     |
      | Black or African American                 |
      | Filipino                                  |
      | Hispanic/Latino of any race               |
      | Native Hawaiian or Other Pacific Islander |
      | Multiracial                               |
      | All Other Racial/Ethnic Minority Groups   |
    Then HE I navigate to the "advanced-awareness/overview" url
    Then HE I click menu tab "Diversity"
    Then HE I verify following options are checked
      | American Indian or Alaskan Native         |
      | Asian                                     |
      | Black or African American                 |
      | Filipino                                  |
      | Hispanic/Latino of any race               |
      | Native Hawaiian or Other Pacific Islander |
      | Multiracial                               |
      | All Other Racial/Ethnic Minority Groups   |
    Then HE I unselect following Diversity Settings
      | American Indian or Alaskan Native         |
      | Asian                                     |
      | Black or African American                 |
      | Filipino                                  |
      | Hispanic/Latino of any race               |
      | Native Hawaiian or Other Pacific Islander |
      | Multiracial                               |
      | All Other Racial/Ethnic Minority Groups   |
    Then HE I verify following options are unchecked
      | American Indian or Alaskan Native         |
      | Asian                                     |
      | Black or African American                 |
      | Filipino                                  |
      | Hispanic/Latino of any race               |
      | Native Hawaiian or Other Pacific Islander |
      | Multiracial                               |
      | All Other Racial/Ethnic Minority Groups   |
    And SM I press button "Save"
    Then I check if I can see "Please select at least one value" on the page
    And HE I select following Diversity Settings
      | American Indian or Alaskan Native         |
    And SM I press button "Save"
    Then HE I verify following options are checked
      | American Indian or Alaskan Native         |
    Then HE I navigate to the "connection/overview" url
    Then HE I click menu tab "Diversity"
    Then HE I verify following options are checked
      | American Indian or Alaskan Native         |
    Then HE I verify following options are unchecked
      | Asian                                     |
      | Black or African American                 |
      | Filipino                                  |
      | Hispanic/Latino of any race               |
      | Native Hawaiian or Other Pacific Islander |
      | Multiracial                               |
      | All Other Racial/Ethnic Minority Groups   |

  @MATCH-4428
  Scenario: One Academic Threshold value must be entered if "Use Default Threshold" is selected for a subscription.
    GPA, SAT, ACT are not required
    Given SP I am logged in to the Admin page as a Support user
    When SP I select "The University of Alabama" from the institution dashboard
    Then SP I set the "Connection" module to "active" in the institution page
    Then SP I set the "Advanced Awareness" module to "active" in the institution page
    And SP I Click the Save Changes button
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    And SP I navigate to the GraphiQL page
    And SP I create a new subscription via GraphiQL with the data in "match-5151SubscriptionData.json" and the following settings:
      | startDate | 2 days before now |
      | endDate   | 2 days after now  |
    And SP I successfully sign out
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I navigate to the "connection/threshold" url
    Then HE I set default filter value "3" for "gpa" on the Threshold Page
    Then HE I check "Enabled" checkbox for the first row on the Threshold Page
    Then HE I check "Use Default Filter Values" checkbox for the first row on the Threshold Page
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

  @MATCH-4428
  Scenario Outline: Academic Threshold min and max values for default GPA,SAT,ACT
    Given SP I am logged in to the Admin page as a Support user
    When SP I select "The University of Alabama" from the institution dashboard
    Then SP I set the "Connection" module to "active" in the institution page
    Then SP I set the "Advanced Awareness" module to "active" in the institution page
    And SP I Click the Save Changes button
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    And SP I navigate to the GraphiQL page
    And SP I create a new subscription via GraphiQL with the data in "match-5151SubscriptionData.json" and the following settings:
      | startDate | 2 days before now |
      | endDate   | 2 days after now  |
    And SP I successfully sign out
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I navigate to the "connection/threshold" url
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
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    Examples:
      | defaultValue | minValue | maxValue | lessThanMin | moreThanMax | message                          |
      | gpa          | 0.1      | 4.0      | 0.02        | 4.1         | Value must be between 0.1 - 4    |
      | sat          | 400      | 1600     | 399         | 1601        | Value must be between 400 - 1600 |
      | act          | 1        | 36       | 0           | 37          | Value must be between 1 - 36     |


  @MATCH-4428
  Scenario Outline: Academic Threshold min and max values for GPA,SAT,ACT
    Given SP I am logged in to the Admin page as a Support user
    When SP I select "The University of Alabama" from the institution dashboard
    Then SP I set the "Connection" module to "active" in the institution page
    Then SP I set the "Advanced Awareness" module to "active" in the institution page
    And SP I Click the Save Changes button
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    And SP I navigate to the GraphiQL page
    And SP I create a new subscription via GraphiQL with the data in "match-5151SubscriptionData.json" and the following settings:
      | startDate | 2 days before now |
      | endDate   | 2 days after now  |
    And SP I successfully sign out
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I navigate to the "connection/threshold" url
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
    Examples:
      | valueName | minValue | maxValue | lessThanMin | moreThanMax | message                          |
      | gpa       | 0.1      | 4.0      | 0.02        | 4.1         | Value must be between 0.1 - 4    |
      | sat       | 400      | 1600     | 399         | 1601        | Value must be between 400 - 1600 |
      | act       | 1        | 36       | 0           | 37          | Value must be between 1 - 36     |



  @MATCH-4428
  Scenario Outline: Academic Threshold GPA, SAT, ACT default values are applied
    Given SP I am logged in to the Admin page as a Support user
    When SP I select "The University of Alabama" from the institution dashboard
    Then SP I set the "Connection" module to "active" in the institution page
    Then SP I set the "Advanced Awareness" module to "active" in the institution page
    And SP I Click the Save Changes button
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    And SP I navigate to the GraphiQL page
    And SP I create a new subscription via GraphiQL with the data in "match-5151SubscriptionData.json" and the following settings:
      | startDate | 2 days before now |
      | endDate   | 2 days after now  |
    And SP I successfully sign out
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I navigate to the "connection/threshold" url
    Then HE I clear default filter value for "<filterValue>" on the Threshold Page
    Then HE I set default filter value "<value>" for "<filterValue>" on the Threshold Page
    Then HE I check "Enabled" checkbox for the first row on the Threshold Page
    Then HE I check "Use Default Filter Values" checkbox for the first row on the Threshold Page
    Then HE I verify "<filterValue>" value for the first row is "<value>" on the Threshold Page
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    Examples:
      | filterValue | value |
      | gpa         | 0.8   |
      | sat         | 1250  |
      | act         | 13    |

  @MATCH-4428
  Scenario: Academic Threshold check all fields
    Given SP I am logged in to the Admin page as a Support user
    When SP I select "The University of Alabama" from the institution dashboard
    Then SP I set the "Connection" module to "active" in the institution page
    Then SP I set the "Advanced Awareness" module to "active" in the institution page
    And SP I Click the Save Changes button
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    And SP I navigate to the GraphiQL page
    And SP I create a new subscription via GraphiQL with the data in "match-5151SubscriptionData.json" and the following settings:
      | startDate | 2 days before now |
      | endDate   | 2 days after now  |
    And SP I successfully sign out
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I navigate to the "connection/threshold" url
    Then HE I check "Enabled" checkbox for the first row on the Threshold Page
    Then HE I uncheck "Use Default Filter Values" checkbox for the first row on the Threshold Page
    Then HE I set filter value "0.8" for "gpa" on the Threshold Page
    Then HE I set filter value "1250" for "sat" on the Threshold Page
    Then HE I set filter value "13" for "act" on the Threshold Page
    Then SM I press button "Save"
    When HE I navigate to the "advanced-awareness/threshold" url
    Then HE I verify "gpa" value for the first row is "0.8" on the Threshold Page
    Then HE I verify "sat" value for the first row is "1250" on the Threshold Page
    Then HE I verify "act" value for the first row is "13" on the Threshold Page
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school

  @MATCH-5138
  Scenario:  As an Intersect User with Connection provisioned,  I need to have Connection displayed as navigational
  items so that I can access those subscriptions easily.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then SM I press button "Home"
    Then HE I pick the "Connection" from the menu items
    Then I check if I can see "Intersect Connection" on the page
    Then HE I navigate to the "home" url
    And SM I press button "Manage"
    Then I check if I can see "Intersect Connection" on the page
