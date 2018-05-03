@HE
Feature: As an HE user, I want to be able to handle fairs

  @MATCH-1825
  Scenario: As an HE user I want to be able to access the College Fair Details
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
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
    And HS I successfully sign out
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I search for "Int Qa High School 4" in RepVisits
    And HE I select "Int Qa High School 4" from the RepVisits search result
    And HE I open the fairs tab
    Then HE I register to the "TestFair--x" fair from Search and Schedule screen
    When HE I open the Calendar tab in RepVisits
    And HE I open the new fair details in the generated date
    Then HE I verify that the following details are present in the fair details in the generated date:
    | College Fair Name | TestFair--x |
    | High School name  | Int Qa High School 4 |
    | High School address | 6840 LAKOTA LN Liberty Township, OH 45044 |
    | Contact name      | IAM Purple              |
    | Contact email     | naviance-email@mock.com |
    | Contact phone     | 1234567890              |
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
    And HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    And HS I cancel the fair of name "TestFair--x" with the reason "test"
    And HS I successfully sign out


