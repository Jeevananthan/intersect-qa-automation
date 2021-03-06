@HS @HS1
Feature: HS - Home - HomePage - As an HS user, I want to be able to access the features of the Home page

  @MATCH-3563
  Scenario:As a HS user, I verify the Copyright information
    Given HS I am logged in to Intersect HS as user type "administrator"
    Then HS I verify the current year is displayed at the bottom of the window in the Home Page
    And HS I verify the items are present in the help center dropdown
    And HS I successfully sign out

    Given HS I verify the current year is displayed at the bottom of the window in the login page
    Then HS I verify the current year is displayed at the bottom of the window in the Registration page
#Naviance HS
    Given HS I verify the current year is displayed at the bottom of the window in the login page for Naviance
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I verify the current year is displayed at the bottom of the window in the RepVisits Page

  @MATCH-4661 @MATCH-4664 @MATCH-4658
  Scenario Outline: As an HS user in Intersect, I need to see the Intersect Connection subscription module
  so that I can access the configure my Intersect Connection settings and access my connections effectively.
    Given HS I am logged in to Intersect HS as user type "<user>"
    Then HS I verify that the text in the button for "<module>" is "<button>"
    Then HS I verify that "<pageURL>" is opened from the "<module>" module
    Examples:
      | user          | module              | button      | pageURL                 |
      | administrator | RepVisits           | SCHEDULE    | rep-visits/calendar     |
#  skipped because not found the UI and will be reported as issue
#      | member        | RepVisits           | SCHEDULE    | rep-visits/calendar     |
#  skipped due to MATCH-4997
#      | administrator | Your settings       | CONFIGURE   | settings/change-profile |
#      | member        | Your settings       | CONFIGURE   | settings/change-profile |
      | administrator | Counselor Community | PARTICIPATE | community/              |
#  skipped because not found the UI and will be reported as issue
#      | member        | Counselor Community | PARTICIPATE | community/              |



  @MATCH-1430
  Scenario: As a HS user, I can view Privacy Policy and Terms of Use pages
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I verify "Terms of Service" is present in the footer
    Then HS I verify "Privacy Policy" is present in the footer
    Then HS I navigate to each page and verify the unique URL is present in the "Terms of Service" page
    Then HS I navigate to each page and verify the unique URL is present in the "Privacy Policy" page
    And HS I successfully sign out

    Given HS I am logged in to Intersect HS as user type "admin"
    Then HS I verify "Terms of Service" is present in the footer
    Then HS I verify "Privacy Policy" is present in the footer
    Then HS I navigate to each page and verify the unique URL is present in the "Terms of Service" page
    Then HS I navigate to each page and verify the unique URL is present in the "Privacy Policy" page

  @MATCH-2057 @MATCH-2195
  Scenario: As a HS user, I want to access to secure help links to learn about my features.
  So non-clients cannot access our help content and learn about our product.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I verify that the help content is not available for "Naviance HS Users"

  @MATCH-2057 @MATCH-2195
  Scenario: As a HS user, I want to access to secure help links to learn about my features.
    Given HS I am logged in to Intersect HS as user type "admin"
    Then HS I verify that the help content is secure and matches the correct URL for "Non-Naviance HS Users"

  @MATCH-1430
  Scenario: As an Intersect system, I need to have a Community Guidelines page available
  for users to read and understand how they are supposed to used the system and how the system uses their information.
    Given HS I am logged in to Intersect HS as user type "admin"
    Then HS I navigate to each page and verify the unique URL is present in the "Counselor Community Guidelines" page in Help Center
    And HS I successfully sign out

  @MATCH-1496
  Scenario: As an HS user I want the Intersect left navigation bar to be better organized and labeled.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I verify the left navigation bar and section breadcrumbs are as follows
      | Awareness | Counselor Community |
    Then HS I verify the left navigation bar and section breadcrumbs are as follows
      |Presence |RepVisits |


