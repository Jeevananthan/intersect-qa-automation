@HE @MATCH-1771
Feature: As an HE user I want to submit a fair request to a high school for approval so I can build out my travel plan.

  Scenario: Create test fairs
    Given HS I am logged in to Intersect HS through Naviance with account "stndalonehs8" and username "school-user" and password "password"
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
    Given HE I want to login to the HE app using "jorgetesthobsons@gmail.com" as username and "Hobsons2016!" as password
    When HE I open the institution of ID "10584"
    And HE I open the Fairs tab in Check RepVisits Availability sidebar
    Then HE I should be able to open the registration popup for the fair "AutoApprovals" in Check RepVisits Availability sidebar
    Then HE I verify that the fair request confirmation popup contains all the required fields, including high school name "Standalone High School 8"
    And HE I close the fair request popup
    And HE I successfully sign out

  Scenario: As a HE user, on the scheduling results page, each college fair listed has a "register" button that presents the user with
  a fair request confirmation popup when clicked
    Given HE I want to login to the HE app using "jorgetesthobsons@gmail.com" as username and "Hobsons2016!" as password
    When HE I search for "Standalone High School 8" in RepVisits
    And HE I select "Standalone High School 8" from the RepVisits search result
    And HE I open the fairs tab
    Then HE I should be able to open the registration popup for the fair "AutoApprovals" in Search and Schedule
    Then HE I verify that the fair request confirmation popup contains all the required fields, including high school name "Standalone High School 8"
    And HE I close the fair request popup
    And HE I successfully sign out

  Scenario: As a HE user, I am able to submit a fair request with auto approvals enabled
    Given HE I want to login to the HE app using "jorgetesthobsons@gmail.com" as username and "Hobsons2016!" as password
    When HE I search for "Standalone High School 8" in RepVisits
    And HE I select "Standalone High School 8" from the RepVisits search result
    And HE I open the fairs tab
    Then HE I register to the "AutoApprovals" fair from Search and Schedule screen
    Then HE I verify that the message for registered fairs with auto approval is displayed
    Then HE I verify that the registered fair is displayed in the calendar for the date "November 14 7:34AM"
    Then HE I verify that the registered fair is displayed for "Standalone High School 8" in the Search and Schedule quickview in the date "November 14 7:34AM"
    And HE I successfully sign out
    When HS I am logged in to Intersect HS through Naviance with account "stndalonehs8" and username "school-user" and password "password"
    And HS I cancel the fair of name "AutoApprovals" with the reason "test"
    And HS I successfully sign out

  Scenario: As a HE user, I am able to submit a fair request without auto approvals enabled
    Given HE I want to login to the HE app using "jorgetesthobsons@gmail.com" as username and "Hobsons2016!" as password
    When HE I search for "Standalone High School 8" in RepVisits
    And HE I select "Standalone High School 8" from the RepVisits search result
    And HE I open the fairs tab
    Then HE I register to the "NoAutoApprovals" fair from Search and Schedule screen
    Then HE I verify that the message for registered fairs without auto approval is displayed
    Then HE I verify that the registered fair is displayed in the calendar for the date "November 17 6:34AM"
    Then HE I verify that the registered fair is displayed for "Standalone High School 8" in the Search and Schedule quickview in the date "November 17 6:34AM"
    And HE I successfully sign out
    When HS I am logged in to Intersect HS through Naviance with account "stndalonehs8" and username "school-user" and password "password"
    And HS I cancel the fair of name "test3" with the reason "test"
    And HS I successfully sign out



