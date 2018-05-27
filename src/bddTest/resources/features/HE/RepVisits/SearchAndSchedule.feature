@HE
Feature: HE- RepVisits - RepVisitsAccess - As an HE user, I want to be able to access the Search and Schedule in the RepVisits features based on my role/subscription

  @MATCH-3856
  Scenario Outline: As an HE freemium user (any role) searching for a school in RVs on the Search and Schedule view,
                    I want to see a Search By drop-down clearly indicating what's available to me and what what requires an upgrade
                    so that I won't be confused as to what I can search against as a free user.

    Given HE I am logged in to Intersect HE as user type "<userType>"
    Then HE I verify the dropdown named "Search by" in search and schedule page
    And HE I verify the text "Search for a school..." present in the text box
    And HE I verify the following fields are displayed after click Search by drop-down
      |Name|City|U.S. State|U.S. County|U.S. Zip Code|Country|
    And HE I verify "Search by,Name" is a default option in the Search by drop-down for the following fields
      |Name|City|U.S. State|
    Then HE I verify the fields are displaying box after selecting the following fields in the dropdown
      |Name|City|U.S. State|
    And HE I verify the "Premium Search" text present with lock icon in the search by drop-down
    Then HE I verify the Background color "<freemium-color>" for the following fields to represent that fields are Freemium
      |Name|City|U.S. State|
    Then HE I verify the Background color "<premium-color>" for the following fields to represent that fields are Premium
      |U.S. County|U.S. Zip Code|Country|
    Then HE I verify the "Upgrade" pop-up page, when selecting the premium options from the search by dropdown
      |U.S. County|U.S. Zip Code|Country|
    Then HE I select the following fields will not submit the search on the page search by "Name","Int Qa High School 4"
      |Name|City|U.S. State|
    And HE I successfully sign out


    Examples:
    |freemium-color    |premium-color           |userType          |
    |rgba(0, 0, 0, 0)  |rgba(255, 242, 248, 1)  |limited           |
    |rgba(0, 0, 0, 0)  |rgba(255, 242, 248, 1)  |limitedPublishing |
    |rgba(0, 0, 0, 0)  |rgba(255, 242, 248, 1)  |limitedCommunity  |

  @MATCH-3857
  Scenario Outline: As an HE premium user (any role) searching for a school in RVs on the Search and Schedule view,
  I want to see a Search By drop-down clearly indicating what's available to me
  so that I won't be confused as to what I can search against as a premium user.
#Premium
    Given HE I am logged in to Intersect HE as user type "<userType>"
    Then HE I verify the dropdown named "Search by" in search and schedule page
    And HE I verify the following fields after click Search by drop-down
      |Name|City|U.S. State|U.S. County|U.S. Zip Code|Country|
    Then HE I verify "Search by,Name" is a default option in the Search by drop-down for the following fields
      |Name|City|U.S. State|U.S. County|U.S. Zip Code|Country|
    Then HE I verify the fields are displaying box after selecting the following fields in the dropdown
      |Name|City|U.S. State|U.S. County|U.S. Zip Code|Country|
    Then HE I verify the text "Search for a school..." present in the text box
    And HE I successfully sign out
    Examples:
      |userType      |
      |administrator |
      |publishing    |
      |community     |

  @MATCH-3858 @MATCH-3864 @MATCH-3863 @MATCH-3860 @MATCH-3862
  Scenario Outline: As an HE premium/paid user (any roles) searching for a school in RV on the Search and Schedule
  view, I want to be able to Search By a country from the dropdown and have the results reflect such accordingly
  so that the results I see accurately reflect the field value I wanted my search value to search against.
    Given HE I am logged in to Intersect HE as user type "<userType>"
    And HE I search a school by "<filter>" using "<toSearch>"
    Then HE I verify the search results have "<searchResult>" in the "<field>" field
    And HE I search a school by "<filter>" using "Null"
    Then HE I verify "No results found." is displayed in the search results
    And HE I successfully sign out
    Examples:
      |userType     |filter         |toSearch            |searchResult        |field      |
      |administrator|Name           |Int QA High School 3|Int QA High School 3|Name       |
      |publishing   |Name           |Int QA High School 3|Int QA High School 3|Name       |
      |community    |Name           |Int QA High School 3|Int QA High School 3|Name       |
      |administrator|Country        |United States       |United States       |Country    |
      |publishing   |Country        |United States       |United States       |Country    |
      |community    |Country        |United States       |United States       |Country    |
      |administrator|U.S. County    |Butler County       |Butler County       |U.S. County|
      |publishing   |U.S. County    |Butler County       |Butler County       |U.S. County|
      |community    |U.S. County    |Butler County       |Butler County       |U.S. County|
      |administrator|U.S. State     |Texas               |Tx                  |U.S. State |
      |publishing   |U.S. State     |Tx                  |Tx                  |U.S. State |
      |community    |U.S. State     |Texas               |Tx                  |U.S. State |
      |administrator|U.S. Zip Code  |92831               |Troy High School    |Name       |
      |publishing   |U.S. Zip Code  |92831-1233          |Troy High School    |Name       |
      |community    |U.S. Zip Code  |33195               |Schoolhouse Academy |Name       |

