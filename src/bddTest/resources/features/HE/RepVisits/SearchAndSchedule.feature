@HE
Feature: HE- RepVisits - RepVisitsAccess - As an HE user, I want to be able to access the Search and Schedule in the RepVisits features based on my role/subscription

  @MATCH-3856 @MATCH-4273
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

  @MATCH-3858 @MATCH-3864 @MATCH-3863 @MATCH-3860 @MATCH-3862 @MATCH-4277
  Scenario Outline: As an freemium or premium/paid user (any role) searching for a school in RV on the Search and Schedule
                    view, I want to be able to Search By a country from the dropdown and have the results reflect such accordingly
                    so that the results I see accurately reflect the field value I wanted my search value to search against.
    Given HE I am logged in to Intersect HE as user type "<userType>"
    And HE I search a school by "<filter>" using "<fewCharacters>"
    Then HE I verify "Please try a search term of at least 2 characters" is displayed in the search tooltip
    And HE I search a school by "<filter>" using "<toSearch>"
    Then HE I verify the search results have "<searchResult>" in the "<field>" field
    And HE I search a school by "<filter>" using "<invalid>"
    Then HE I verify "No results found." is displayed in the search results
    And HE I successfully sign out

    Examples:
      |userType          |filter         |toSearch                    |searchResult                |field      |invalid       |fewCharacters|
      |administrator     |Name           |Int QA High School 3        |Int QA High School 3        |Name       |InvalidName   |a            |
      |publishing        |Name           |International QA High School|International QA High School|Name       |Null          |b            |
      |community         |Name           |Standalone High School 7    |Standalone High School 7    |Name       |Null          |c            |
      |administrator     |Country        |Canada                      |Canada                      |Country    |Null          |d            |
      |publishing        |Country        |United States               |United States               |Country    |InvalidCountry|e            |
      |community         |Country        |United States               |United States               |Country    |Null          |a            |
      |administrator     |U.S. County    |Butler County               |Butler County               |U.S. County|Test County   |b            |
      |publishing        |U.S. County    |Butler County               |Butler County               |U.S. County|Null          |c            |
      |community         |U.S. County    |Butler County               |Butler County               |U.S. County|Null          |d            |
      |administrator     |U.S. State     |Texas                       |Tx                          |U.S. State |Ontario       |e            |
      |publishing        |U.S. State     |Tx                          |Tx                          |U.S. State |Null          |a            |
      |community         |U.S. State     |Texas                       |Tx                          |U.S. State |Null          |b            |
      |administrator     |U.S. Zip Code  |92831                       |Troy High School            |Name       |12345678      |c            |
      |publishing        |U.S. Zip Code  |92831-1233                  |Troy High School            |Name       |Null          |d            |
      |community         |U.S. Zip Code  |33195                       |Schoolhouse Academy         |Name       |33344455      |e            |
#freemium
      |limited           |Name           |Int QA High School 3        |Int QA High School 3        |Name       |InvalidName   |a            |
      |limitedPublishing |Name           |International QA High School|International QA High School|Name       |Null          |b            |
      |limitedCommunity  |Name           |Standalone High School 7    |Standalone High School 7    |Name       |Null          |c            |
      |limited           |U.S. State     |Texas                       |Tx                          |U.S. State |Ontario       |d            |
      |limitedPublishing |U.S. State     |Tx                          |Tx                          |U.S. State |Null          |e            |
      |limitedCommunity  |U.S. State     |Texas                       |Tx                          |U.S. State |Null          |f            |

  @MATCH-3859
  Scenario Outline: As an HE freemium or premium/paid user (any role) searching for a school in RVs on the Search and Schedule view,
                    I want to be able to Search By a school's city from the drop down and have the results reflect such accordingly
                    so that the results I see accurately reflect the field value I wanted my search value to search against.

    Then HE I am logged in to Intersect HE as user type "<userType>"
    And HE I search a school by "<filter>" using "<invalid>"
    Then HE I verify "No results found." is displayed in the search results
    And HE I search a school by "<filter>" using "<fewCharacters>"
    Then HE I verify "Please try a search term of at least 2 characters" is displayed in the search tooltip
    And HE I search a school by "<filter>" using ""
    Then HE I verify "Please try a search term of at least 2 characters" is displayed in the search tooltip
    And HE I search a school by "<filter>" using "<CityForNon-Us>"
    Then HE I verify the search results have "<searchResultForNon-US>" in the "<field>" field
    And HE I search a school by "<filter>" using "<CityForUS>"
    Then HE I verify the search results have "<searchResultForUS>" in the "<field>" field
    Then HE I successfully sign out

    Examples:
      |userType          |filter         |CityForUS             |CityForNon-Us|searchResultForUS|searchResultForNon-US|field      |invalid       |fewCharacters|
      |administrator     |City           |Liberty Township      |Toronto      |Toronto          |iberty Township      |City       |InvalidName   |a            |
      |publishing        |City           |Liberty Township      |Toronto      |Toronto          |iberty Township      |City       |InvalidName   |a            |
      |community         |City           |Liberty Township      |Toronto      |Toronto          |iberty Township      |City       |InvalidName   |a            |
      |limited           |City           |Liberty Township      |Toronto      |Toronto          |iberty Township      |City       |InvalidName   |a            |
      |limitedPublishing |City           |Liberty Township      |Toronto      |Toronto          |iberty Township      |City       |InvalidName   |a            |
      |limitedCommunity  |City           |Liberty Township      |Toronto      |Toronto          |iberty Township      |City       |InvalidName   |a            |



  @MATCH-3865 @MATCH-4489
  Scenario Outline: As an HE premium/paid user (any role) searching for a school in RVs on the Search and Schedule view,
                    I want to be able to see all high schools outside of the U.S.
                    so that I don't have to do a country by country search to see ALL international high schools

    Then HE I am logged in to Intersect HE as user type "<userType>"
    And HE I search a school by "<filter>" using "<Non-USState>"
    Then HE I click the link "See all high schools outside of the U.S." in search and schedule page
    Then HE I verify "International Schools" results is not displayed in search and schedule page after move out from International Schools results view
    And HE I search a school by "<filter>" using "<Non-USState>"
    Then HE I verify the Header "Results" is displayed in search results Page
    Then HE I verify the search results have "<Non-USState>" in the "<field>" field
    Then HE I click the link "See all high schools outside of the U.S." in search and schedule page
    Then HE I verify the Header name is changed to "International Schools" in search result Page
    Then HE I verify the button "More Results" is displaying for more than 25 results
    Then HE I verify the school is displayed in schedule page after click the school link using "<school>" and "<Non-USState>"
    And HE I search a school by "<filter>" using "<Non-USState>"
    Then HE I click the link "See all high schools outside of the U.S." in search and schedule page
    Then HE I get the URL of the current page
    Then HE I successfully sign out

#The International Schools list view does not load for HE RV freemium users
    Then HE I am logged in to Intersect HE as user type "<limitedUser>"
    Then HE I verify the International Schools list view does not load for freemium users in search and schedule page
    Then HE I successfully sign out

    Examples:
      |userType          |filter         |field      |Non-USState|school                      |limitedUser      |
      |administrator     |Country        |Country    |Canada     |International QA High School|limited         |
      |publishing        |Country        |Country    |Canada     |International QA High School|limitedPublishing|
      |community         |Country        |Country    |Canada     |International QA High School|limitedCommunity |