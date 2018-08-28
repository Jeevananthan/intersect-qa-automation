@HS
Feature: HS - Home - HomePage - As an HS user, I want to be able to access the features of the Home page

  @MATCH-1496
  Scenario: As an HS user I want the Intersect left navigation bar to be better organized and labeled.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I verify the left navigation bar and section breadcrumbs are as follows
      | Awareness | Counselor Community |
    Then HS I verify the left navigation bar and section breadcrumbs are as follows
      |Presence |RepVisits |
    And HS I successfully sign out

  @MATCH-4661 @MATCH-4664 @MATCH-4658
  Scenario Outline: As an HS user in Intersect, I need to see the Intersect Connection subscription module
  so that I can access the configure my Intersect Connection settings and access my connections effectively.
    Given HS I am logged in to Intersect HS as user type "<user>"
    Then HS I verify that the text in the button for "<module>" is "<button>"
    Then HS I verify that "<pageURL>" is opened from the "<module>" module
    And HS I successfully sign out
       Examples:
      | user          | module              | button      | pageURL                 |
      | administrator | RepVisits           | SCHEDULE    | rep-visits/calendar     |
      | member        | RepVisits           | SCHEDULE    | rep-visits/calendar     |
#  skipped due to MATCH-4997
#      | administrator | Your settings       | CONFIGURE   | settings/change-profile |
#      | member        | Your settings       | CONFIGURE   | settings/change-profile |
      | administrator | Counselor Community | PARTICIPATE | community/              |
      | member        | Counselor Community | PARTICIPATE | community/              |


  @MATCH-1430
  Scenario: As a HS user, I can view Privacy Policy and Terms of Use pages
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I verify "Terms of Service" is present in the footer
    Then HS I verify "Privacy Policy" is present in the footer
    Then HS I navigate to each page and verify the unique URL is present in the "Terms of Service" page
    Then HS I navigate to each page and verify the unique URL is present in the "Privacy Policy" page
    And HS I successfully sign out

    Given HS I want to login to the HS app using "purpleheautomation+admin@gmail.com" as username and "Password!1" as password
    Then HS I verify "Terms of Service" is present in the footer
    Then HS I verify "Privacy Policy" is present in the footer
    Then HS I navigate to each page and verify the unique URL is present in the "Terms of Service" page
    Then HS I navigate to each page and verify the unique URL is present in the "Privacy Policy" page
    And HS I successfully sign out

  @MATCH-2057 @MATCH-2195
  Scenario: As a HS user, I want to access to secure help links to learn about my features.
  So non-clients cannot access our help content and learn about our product.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I verify that the help content is not available for "Naviance HS Users"
    And HS I successfully sign out

    Given HS I want to login to the HS app using "hobsonstest15@mailinator.com" as username and "boGusPassw0rd@" as password
    Then HS I verify that the help content is secure and matches the correct URL for "Non-Naviance HS Users"
    And HS I successfully sign out

  @MATCH-1430
  Scenario: As an Intersect system, I need to have a Community Guidelines page available
  for users to read and understand how they are supposed to used the system and how the system uses their information.
    Given HS I want to login to the HS app using "purpleheautomation+admin@gmail.com" as username and "Password!1" as password
    Then HS I navigate to each page and verify the unique URL is present in the "Counselor Community Guidelines" page in Help Center
    Then HS I successfully sign out