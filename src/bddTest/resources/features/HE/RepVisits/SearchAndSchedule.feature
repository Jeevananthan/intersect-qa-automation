@HE
Feature: HE - RepVisits - SearchAndSchedule - As an HE user, I want to be able to access the Search and Schedule in the RepVisits features based on my role,subscription

  @MATCH-3856 @MATCH-4273 @MATCH-4378 @MATCH-3861 @MATCH-4209
  Scenario Outline: As an HE freemium user (any role) searching for a school in RVs on the Search and Schedule view,
                    I want to see a Search By drop-down clearly indicating what's available to me and what what requires an upgrade
                    so that I won't be confused as to what I can search against as a free user.

    Given HE I am logged in to Intersect HE as user type "<userType>"
    Then HE I verify the dropdown named "Search by" in search and schedule page
    And HE I verify the text "Search for a school..." present in the text box
    And HE I verify the following fields are displayed after click Search by drop-down
      |Name|City|U.S. State|U.S. County|U.S. Zip Code|Country|CEEB Code|
    And HE I verify "Search by,Name" is a default option in the Search by drop-down for the following fields
      |Name|City|U.S. State|
    Then HE I verify the fields are displaying box after selecting the following fields in the dropdown
      |Name|City|U.S. State|
    And HE I verify the "Premium Search" text present with lock icon in the search by drop-down
    Then HE I verify the Background color "<freemium-color>" for the following fields to represent that fields are Freemium
      |Name|City|U.S. State|
    Then HE I verify the Background color "<premium-color>" for the following fields to represent that fields are Premium
      |U.S. County|U.S. Zip Code|Country|CEEB Code|
    Then HE I verify the "Upgrade" pop-up page, when selecting the premium options from the search by dropdown
      |U.S. County|U.S. Zip Code|Country|CEEB Code|
    Then HE I select the following fields will not submit the search on the page search by "Name","Int Qa High School 4"
      |Name|City|U.S. State|

    Examples:
    |freemium-color    |premium-color           |userType          |
    |rgba(0, 0, 0, 0)  |rgba(255, 242, 248, 1)  |limited           |
    |rgba(0, 0, 0, 0)  |rgba(255, 242, 248, 1)  |limitedPublishing |
    |rgba(0, 0, 0, 0)  |rgba(255, 242, 248, 1)  |limitedCommunity  |

  @MATCH-3857 @MATCH-4378 @MATCH-3861
  Scenario Outline: As an HE premium user (any role) searching for a school in RVs on the Search and Schedule view,
                    I want to see a Search By drop-down clearly indicating what's available to me
                    so that I won't be confused as to what I can search against as a premium user.
#Premium
    Given HE I am logged in to Intersect HE as user type "<userType>"
    Then HE I verify the dropdown named "Search by" in search and schedule page
    And HE I verify the following fields after click Search by drop-down
      |Name|City|U.S. State|U.S. County|U.S. Zip Code|Country|CEEB Code|
    Then HE I verify "Search by,Name" is a default option in the Search by drop-down for the following fields
      |Name|City|U.S. State|U.S. County|U.S. Zip Code|Country|CEEB Code|
    Then HE I verify the fields are displaying box after selecting the following fields in the dropdown
      |Name|City|U.S. State|U.S. County|U.S. Zip Code|Country|CEEB Code|
    Then HE I verify the text "Search for a school..." present in the text box

    Examples:
      |userType      |
      |administrator |
      |publishing    |
      |community     |

  @MATCH-3858 @MATCH-3864 @MATCH-3863 @MATCH-3860 @MATCH-3862 @MATCH-4277 @MATCH-4378 @MATCH-3861
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
      |administrator     |CEEB Code      |ABC123                      |Standalone High School 6    |CEEB Code  |12345678      |c            |
      |publishing        |CEEB Code      |ABC123                      |Standalone High School 6    |CEEB Code  |Null          |d            |
      |community         |CEEB Code      |ABC123                      |Standalone High School 6    |CEEB Code  |33344455      |e            |
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

    Examples:
      |userType          |filter         |CityForUS             |CityForNon-Us|searchResultForUS|searchResultForNon-US|field      |invalid       |fewCharacters|
      |administrator     |City           |Liberty Township      |Toronto      |Toronto          |iberty Township      |City       |InvalidName   |a            |
      |publishing        |City           |Liberty Township      |Toronto      |Toronto          |iberty Township      |City       |InvalidName   |a            |
      |community         |City           |Liberty Township      |Toronto      |Toronto          |iberty Township      |City       |InvalidName   |a            |
      |limited           |City           |Liberty Township      |Toronto      |Toronto          |iberty Township      |City       |InvalidName   |a            |
      |limitedPublishing |City           |Liberty Township      |Toronto      |Toronto          |iberty Township      |City       |InvalidName   |a            |
      |limitedCommunity  |City           |Liberty Township      |Toronto      |Toronto          |iberty Township      |City       |InvalidName   |a            |



  @MATCH-3865 @MATCH-4489 @MATCH-4209
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

    Examples:
      |userType          |filter         |field      |Non-USState|school                      |limitedUser      |
      |administrator     |Country        |Country    |Canada     |International QA High School|limited          |
      |publishing        |Country        |Country    |Canada     |International QA High School|limitedPublishing|
      |community         |Country        |Country    |Canada     |International QA High School|limitedCommunity |

  @MATCH-3779 @MATCH-4209
  Scenario Outline: As an HE user searching for a school in RVs on the Search and Schedule view,
                    I want to see how many search results were returned and what portion of the results I'm viewing (count)
                    so that I can get a sense of how many hits I have based upon the search criteria I specified.

    Given HE I am logged in to Intersect HE as user type "<userType>"
    And HE I search a school by "<filter>" using "<value>"
    Then HE I verify the results count by "<filter>" using "<value>" in search results page

    Examples:
      |userType          |filter      |value    |
      |administrator     |Name        |school   |
      |publishing        |Name        |school   |
      |community         |Name        |school   |
      |limited           |Name        |school   |
      |limitedPublishing |Name        |school   |
      |limitedCommunity  |Name        |school   |

  @MATCH-1730
  Scenario: As an HE user I want to see a 'quickview' of my high school visit schedule/calendar on the RepVisits Search and Schedule subtab so I can quickly see what appointments I have already made with high schools.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    Then HS I set the RepVisits Visits Confirmations option to "Yes, accept all incoming requests."

    Then HS I set the date using "14" and "28"
    And HS I verify the update button appears and I click update button
    Then HS I clear the time slot for the particular day "14" in Regular Weekly Hours Tab
    Then HS I add the new time slot with "14","10:28am","11:28am" and "5" with "1"
    And HS I successfully sign out

    Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "Int Qa High School 4" in RepVisits page
    Then HE I select Visits to schedule the appointment for "Int Qa High School 4" using "14" and "10:28am"
    And HE I verify the schedule pop_up for "Int Qa High School 4" using "10:28am" and "11:28am"

    And HE I search for "Int Qa High School 4" in RepVisits page
    Then HE I verify "Your Schedule" text is displaying in search and schedule tab
    Then HE I verify the month and dates displayed in the title bar of the your schedule content always matches what is selected for the search bar "14"
    Then HE I verify the view details hyperlink for each high school visit already scheduled by that user opens a popup "Int Qa High School 4"
    Then HE I verify the popup has an 'X' icon that would close the popup if clicked
    Then HE I verify the view details hyperlink for each high school visit already scheduled by that user opens a popup "Int Qa High School 4"
    Then HE I verify the school details are present in the your schedule popup "Int Qa High School 4"
    |6840 LAKOTA LN Liberty Township, Ohio 45044|360.555.1212   |PUBLIC  |280    |null%  |
    Then HE I verify the link navigate to the Counselor Community institution profile page "Int Qa High School 4"
    And HE I successfully sign out
