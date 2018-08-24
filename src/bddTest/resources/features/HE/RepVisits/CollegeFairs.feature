@HE
Feature: HE - RepVisits - CollegeFairs - As an HE user, I should be able to sign up for College fairs and manage my fair appointments

  @MATCH-1771
  Scenario: As a HE user, on the "Community availability sidebar", each college fair has a Register button that presents
            the HE user with a fair request confirmation popup
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    And HS I create a College Fair with the following data
      | College Fair Name                                         | Fair-MATCH1771          |
      | Automatically Confirm Incoming Requestions From Colleges? | no                      |
      | Cost                                                      | 10                      |
      | Start Time                                                | 1800AM                  |
      | Date                                                      | 7                       |
      | RSVP Deadline                                             | 1                       |
      | End Time                                                  | 1900PM                  |
      | Max Number of Colleges                                    | 10                      |
      | Number of Students Expected                               | 10                      |
      | Instructions for College Representatives                  | Submit request by Email |
      | Email Message to Colleges After Confirmation              | why not                 |
    And HS I successfully sign out
    When HE I am logged in to Intersect HE as user type "administrator"
    And HE I open the institution of ID "04d3b77e-18f5-43cd-a585-101268b1178b"
    And HE I open the Fairs tab in Check RepVisits Availability sidebar
    Then HE I should be able to open the registration popup for the fair "Fair-MATCH1771" in Check RepVisits Availability sidebar
    Then HE I verify that the fair request confirmation popup contains all the required fields, including high school name "Int Qa High School 4"
    And HE I close the fair request popup
    And HE I successfully sign out
    And HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    And HS I cancel the fair of name "Fair-MATCH1771" with the reason "test"
    And HS I successfully sign out

  @MATCH-1771
  Scenario: As a HE user, on the scheduling results page, each college fair listed has a "register" button that presents
            the user with a fair request confirmation popup when clicked
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    And HS I create a College Fair with the following data
      | College Fair Name                                         | Fair-MATCH1771               |
      | Automatically Confirm Incoming Requestions From Colleges? | yes                     |
      | Cost                                                      | 10                      |
      | Start Time                                                | 1800AM                  |
      | Date                                                      | 2                       |
      | RSVP Deadline                                             | 1                       |
      | End Time                                                  | 1900PM                  |
      | Max Number of Colleges                                    | 10                      |
      | Number of Students Expected                               | 10                      |
      | Instructions for College Representatives                  | Submit request by Email |
      | Email Message to Colleges After Confirmation              | why not                 |
    And HS I successfully sign out
    When HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "Int Qa High School 4" in RepVisits
    And HE I select "Int Qa High School 4" from the RepVisits search result
    And HE I open the fairs tab
    Then HE I should be able to open the registration popup for the fair "Fair-MATCH1771" in Search and Schedule
    Then HE I verify that the fair request confirmation popup contains all the required fields, including high school name "Int Qa High School 4"
    And HE I close the fair request popup
    And HE I successfully sign out
    And HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    And HS I cancel the fair of name "Fair-MATCH1771" with the reason "test"
    And HS I successfully sign out

  @MATCH-1771
  Scenario: As a HE user, I am able to submit a fair request with auto approvals enabled
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    And HS I create a College Fair with the following data
      | College Fair Name                                         | Fair-1771               |
      | Automatically Confirm Incoming Requestions From Colleges? | yes                     |
      | Cost                                                      | 10                      |
      | Start Time                                                | 0600AM                  |
      | Date                                                      | 2                       |
      | RSVP Deadline                                             | 1                       |
      | End Time                                                  | 0800AM                  |
      | Max Number of Colleges                                    | 10                      |
      | Number of Students Expected                               | 10                      |
      | Instructions for College Representatives                  | Submit request by Email |
      | Email Message to Colleges After Confirmation              | why not                 |
    And HS I successfully sign out
    When HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "Int Qa High School 4" in RepVisits
    And HE I select "Int Qa High School 4" from the RepVisits search result
    And HE I open the fairs tab
    Then HE I register to the "Fair-MATCH1771" fair from Search and Schedule screen
    Then HE I verify that the message for registered fairs with auto approval is displayed
    Then HE I verify that the registered fair "Fair-MATCH1771" is displayed in the calendar for the date "In 2 days" and time "6:00AM"
    Then HE I verify that the registered fair is displayed for "Int Qa High School 4" in the Search and Schedule quickview in the date "In 2 days" and time "6:00AM"
    And HE I successfully sign out
    When HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    And HS I cancel the fair of name "Fair-MATCH1771" with the reason "test"
    And HS I successfully sign out

  # The following scenario is failing because of MATCH-3704.
#  @MATCH-1771
#  Scenario: As a HE user, I am able to submit a fair request without auto approvals enabled (MATCH-3704)
#    Given HE I am logged in to Intersect HE as user type "administrator"
#    When HE I search for "Int Qa High School 4" in RepVisits
#    And HE I select "Int Qa High School 4" from the RepVisits search result
#    And HE I open the fairs tab
#    Then HE I register to the "NoAutoApprovals" fair from Search and Schedule screen
#    Then HE I verify that the message for registered fairs without auto approval is displayed
#    Then HE I verify that the registered fair is displayed in the calendar for the date "November 17 8:34AM"
#    Then HE I verify that the registered fair is displayed for "Int Qa High School 4" in the Search and Schedule quickview in the date "November 17 8:34AM"
#    And HE I successfully sign out
#    When HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
#    And HS I cancel the fair of name "NoAutoApprovals" with the reason "test"
#    And HS I successfully sign out

  @MATCH-2082
  Scenario: As an HE user, I want to only see college fairs that have been published by a high school.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I create a dynamic College Fair with the following data
      | College Fair Name                                         | MATCH-2082 Fair         |
      | Automatically Confirm Incoming Requestions From Colleges? | no                      |
      | Cost                                                      | 10                      |
      | Start Time                                                | 0810AM                  |
      | Date                                                      | 3                       |
      | RSVP Deadline                                             | 2                       |
      | End Time                                                  | 0820AM                  |
      | Max Number of Colleges                                    | 10                      |
      | Number of Students Expected                               | 10                      |
      | Instructions for College Representatives                  | Submit request by Email |
      | Email Message to Colleges After Confirmation              | why not                 |
    And HS I successfully sign out
  # Log into HE app and verify that the fair is visible
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify that the previously created fair appears for "Int QA High School 4"
  # Log into HS app and unpublish the previous College Fair
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I Click the View Details button for the College Fair Event for "PreviouslySetFair"
    And HS I click on Edit button to edit fair
    Then HS I unpublish the College Fair
  # Log into HE app and verify that the fair is not visible
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify that the previously created fair does not appear for "Int QA High School 4"
    And HE I successfully sign out
    And HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    And HS I cancel the fair of name "PreviouslySetFair" with the reason "test"
    And HS I successfully sign out

  @manual @NotInQA
  Scenario: As a HE user, I need to verify that the error message when the fair is not available anymore
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I search for "Int Qa High School 4" in RepVisits
    And HE I select "Int Qa High School 4" from the RepVisits search result
    And HE I open the fairs tab
    And HE I open another browser
    Given HE I am logged in to Intersect HE as another user of type "administrator"
    When HE I search for "Int Qa High School 4" in RepVisits
    And HE I select "Int Qa High School 4" from the RepVisits search result
    And HE I open the fairs tab
    And HE I register to the "TestFair" fair from Search and Schedule screen
    And HE I navigate back to first browser
    And HE I register to the "TestFair" fair from Search and Schedule screen
    Then HE I should see a red upper bar with the text: "Sorry, this fair is no longer available. Please select another fair:"

  @MATCH-1825 @MATCH-1750
  Scenario: As an HE user I want to be able to access the College Fair Details
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    When HS I Navigate to College Fairs tab of the Repvisits Page
    Then HS I create a new college fair "10" days ahead of today and the following details:
      | Name | TestFair--x |
      | Start time | 07:04AM |
      | End time   | 08:00AM |
      | Cost          | 123        |
      | Max colleges  | 5          |
      | Max students  | 5          |
      | Instructions  | Test instruction |
      | Auto Approvals | Yes       |
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I search for "Int Qa High School 4" in RepVisits
    And HE I select "Int Qa High School 4" from the RepVisits search result
    And HE I open the fairs tab
    And HE I verify the data for the following fair
      | College Fair Name   | TestFair--x                                 |
      | High School name    | Int Qa High School 4                        |
      #| Date                | 10                                          |
      | Start time          | 7:04am                                      |
      | Contact phone       | 360-555-1212                                |
      | Contact email       | naviance-email@mock.com                     |
      | Address             | 6840 LAKOTA LN                              |
      | City                | Liberty Township                            |
      | State               | Ohio                                        |
      #| Number of students  | 5                                           |
      | Cost                | 123                                         |
      | Instructions        | Test instruction                            |
    Then HE I register to the "TestFair--x" fair from Search and Schedule screen
    When HE I open the Calendar tab in RepVisits
    And HE I open the new fair details in the generated date
    Then HE I verify that the following details are present in the fair details in the generated date:
      | College Fair Name | TestFair--x |
      | High School name  | Int Qa High School 4 |
      | High School address | 6840 LAKOTA LN Liberty Township, OH 45044 |
      | Contact name      | IAM Purple              |
      | Contact email     | naviance-email@mock.com |
      | Contact phone     | 9876543210              |
      | Start time        | 7:04 AM                 |
      | End time          | 8:00 AM                 |
      | Time zone         | (EDT)                   |
      | Cost              | 123                     |
      | Number of students | 5                      |
      | Instructions       | Test instruction       |
      | Cancellation text  | This school allows you to cancel your fair registration until |
      | Cancel link        | Present                                                       |
      | Internal notes     | Test text                                                     |
    And HE I successfully sign out
    And HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    And HS I cancel the fair of name "TestFair--x" with the reason "test"
    And HS I successfully sign out

  @MATCH-2309
  Scenario: As a HE user with the community role, I am able to submit a fair request
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    And HS I create a dynamic College Fair with the following data
      | College Fair Name                                         | Fair-2309               |
      | Automatically Confirm Incoming Requestions From Colleges? | yes                     |
      | Cost                                                      | 10                      |
      | Start Time                                                | 0610AM                  |
      | Date                                                      | 2                       |
      | RSVP Deadline                                             | 1                       |
      | End Time                                                  | 0810AM                  |
      | Max Number of Colleges                                    | 10                      |
      | Number of Students Expected                               | 10                      |
      | Instructions for College Representatives                  | Submit request by Email |
      | Email Message to Colleges After Confirmation              | why not                 |
    And HS I successfully sign out
    When HE I am logged in to Intersect HE as user type "community"
    And HE I search for "Int Qa High School 4" in RepVisits
    And HE I select "Int Qa High School 4" from the RepVisits search result
    And HE I open the fairs tab
    Then HE I register to the "PreviouslySetFair" fair from Search and Schedule screen
    Then HE I verify that the message for registered fairs with auto approval is displayed
    When HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    And HS I cancel the fair of name "PreviouslySetFair" with the reason "test"
    And HS I successfully sign out





