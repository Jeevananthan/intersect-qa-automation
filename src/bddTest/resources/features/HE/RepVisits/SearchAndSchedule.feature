@HE
Feature: HE- RepVisits - RepVisitsAccess - As an HE user, I want to be able to access the Search and Schedule in the RepVisits features based on my role/subscription

  @MATCH-3856
  Scenario Outline: As an HE freemium user (any role) searching for a school in RVs on the Search and Schedule view,
                    I want to see a Search By drop-down clearly indicating what's available to me and what what requires an upgrade
                    so that I won't be confused as to what I can search against as a free user.
 #Administrator
    Given HE I am logged in to Intersect HE as user type "limited"
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

 #Publishing
    Given HE I am logged in to Intersect HE as user type "limitedPublishing"
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
      |City|U.S. State|
    And HE I successfully sign out

 #Community
    Given HE I am logged in to Intersect HE as user type "limitedCommunity"
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
      |City|U.S. State|
    And HE I successfully sign out

    Examples:
    |freemium-color    |premium-color           |
    |rgba(0, 0, 0, 0)  |rgba(255, 242, 248, 1)  |

