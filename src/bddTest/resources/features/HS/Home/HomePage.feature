@HE
Feature: HE - Home - HomePage - As an HE user, I want to be able to access the features of the Home page

  @MATCH-4657 @MATCH-4660 @MATCH-4661 @MATCH-4662 @MATCH-4664 @MATCH-4658
  Scenario Outline: As an HE user in Intersect, I need to see the Intersect Connection subscription module
  so that I can access the configure my Intersect Connection settings and access my connections effectively.
    Given HE I am logged in to Intersect HE as user type "<user>"
    Then HE I verify that the text in the button for "<module>" is "<button>"
    Then HE I verify that "<pageURL>" is opened from the "<module>" module
    Examples:
      | user          | module                   | button      | pageURL                         |
      | administrator | Naviance College Profile | UPDATE      | naviance-college-profile/edit   |
      | publishing    | Naviance College Profile | UPDATE      | naviance-college-profile/edit   |
      | administrator | Connection               | MANAGE      | am-plus/view-connections        |
      | administrator | RepVisits                | SCHEDULE    | rep-visits/search               |
      | publishing    | RepVisits                | SCHEDULE    | rep-visits/search               |
      | community     | RepVisits                | SCHEDULE    | rep-visits/search               |
      | administrator | Events                   | PUBLISH     | am-events/view-events/published |
      | publishing    | Events                   | PUBLISH     | am-events/view-events/published |
      | administrator | Account Settings         | CONFIGURE   | settings/change-profile         |
      | publishing    | Account Settings         | CONFIGURE   | settings/change-profile         |
      | community     | Account Settings         | CONFIGURE   | settings/change-profile         |
      | administrator | Counselor Community      | PARTICIPATE | counselor-community/            |
      | publishing    | Counselor Community      | PARTICIPATE | counselor-community/            |
      | community     | Counselor Community      | PARTICIPATE | counselor-community/            |
