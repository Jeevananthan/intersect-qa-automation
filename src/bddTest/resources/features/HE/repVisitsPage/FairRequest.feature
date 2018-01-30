@HE @MATCH-1771
Feature: As an HE user I want to submit a fair request to a high school for approval so I can build out my travel plan.

  Scenario: Create test fairs
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I create a new college fair with the following details:
      | Name | AutoApprovals |
      | Date | November 14   |
      | Start time | 07:34 AM |
      | End time   | 09:30 AM |
      | RSVP deadline | October 14 |
      | Cost          | 123        |
      | Max colleges  | 5          |
      | Max students  | 5          |
      | Auto Approvals | Yes       |
    Then HS I create a new college fair with the following details:
      | Name | NoAutoApprovals |
      | Date | November 17     |
      | Start time | 08:34 AM |
      | End time   | 09:30 AM |
      | RSVP deadline | October 17 |
      | Cost          | 123        |
      | Max colleges  | 5          |
      | Max students  | 5          |
      | Auto Approvals | No        |

  Scenario: As a HE user, on the "Community availability sidebar", each college fair has a Register button that presents the HE user with
  a fair request confirmation popup
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the institution of ID "04d3b77e-18f5-43cd-a585-101268b1178b"
    And HE I open the Fairs tab in Check RepVisits Availability sidebar
    Then HE I should be able to open the registration popup for the fair "AutoApprovals" in Check RepVisits Availability sidebar
    Then HE I verify that the fair request confirmation popup contains all the required fields, including high school name "Int Qa High School 4"
    And HE I close the fair request popup
    And HE I successfully sign out

  Scenario: As a HE user, on the scheduling results page, each college fair listed has a "register" button that presents the user with
  a fair request confirmation popup when clicked
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I search for "Int Qa High School 4" in RepVisits
    And HE I select "Int Qa High School 4" from the RepVisits search result
    And HE I open the fairs tab
    Then HE I should be able to open the registration popup for the fair "AutoApprovals" in Search and Schedule
    Then HE I verify that the fair request confirmation popup contains all the required fields, including high school name "Int Qa High School 4"
    And HE I close the fair request popup
    And HE I successfully sign out

  Scenario: As a HE user, I am able to submit a fair request with auto approvals enabled
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I search for "Int Qa High School 4" in RepVisits
    And HE I select "Int Qa High School 4" from the RepVisits search result
    And HE I open the fairs tab
    Then HE I register to the "AutoApprovals" fair from Search and Schedule screen
    Then HE I verify that the message for registered fairs with auto approval is displayed
    Then HE I verify that the registered fair is displayed in the calendar for the date "November 14 7:34AM"
    Then HE I verify that the registered fair is displayed for "Int Qa High School 4" in the Search and Schedule quickview in the date "November 14 7:34AM"
    And HE I successfully sign out
    When HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    And HS I cancel the fair of name "AutoApprovals" with the reason "test"
    And HS I successfully sign out

  # The following scenario is failing because of MATCH-3704.
  Scenario: As a HE user, I am able to submit a fair request without auto approvals enabled (MATCH-3704)
#    Given HE I am logged in to Intersect HE as user type "administrator"
#    When HE I search for "Int Qa High School 4" in RepVisits
#    And HE I select "Int Qa High School 4" from the RepVisits search result
#    And HE I open the fairs tab
#    Then HE I register to the "NoAutoApprovals" fair from Search and Schedule screen
#    Then HE I verify that the message for registered fairs without auto approval is displayed
#    Then HE I verify that the registered fair is displayed in the calendar for the date "November 17 8:34AM"
#    Then HE I verify that the registered fair is displayed for "Int Qa High School 4" in the Search and Schedule quickview in the date "November 17 8:34AM"
#    And HE I successfully sign out
    When HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    And HS I cancel the fair of name "NoAutoApprovals" with the reason "test"
    And HS I successfully sign out

  @manual
  Scenario: As a HE user, I need to verify that the error message when the fair is not available anymore
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I search for "Int Qa High School 4" in RepVisits
    And HE I select "Int Qa High School 4" from the RepVisits search result
    And HE I open the fairs tab
    And HE I register to the "TestFair" fair from Search and Schedule screen
    And HE I open another browser
    Given HE I am logged in to Intersect HE as another user of type "administrator"
    When HE I search for "Int Qa High School 4" in RepVisits
    And HE I select "Int Qa High School 4" from the RepVisits search result
    And HE I open the fairs tab
    And HE I register to the "TestFair" fair from Search and Schedule screen
    Then HE I should see a red upper bar with the text: "Sorry, this fair is no longer available. Please select another fair:"




