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

  @MATCH-3563
  Scenario:As a HS user, I verify the Copyright information
    #Non-Naviance HS
    Given HS I verify the current year is displayed at the bottom of the window in the login page
    Then HS I verify the current year is displayed at the bottom of the window in the Registration page

    Given HS I am logged in to Intersect HS as user type "administrator"
    Then HS I verify the current year is displayed at the bottom of the window in the Home Page
    And HS I verify the items are present in the help center dropdown
    And HS I successfully sign out
#Naviance HS
    Given HS I verify the current year is displayed at the bottom of the window in the login page for Naviance
    Then HS I verify the current year is displayed at the bottom of the window in the Naviance page using "blue4hs","iam.purple","Password!1"
    Then HS I verify the current year is displayed at the bottom of the window in the RepVisits Page
    And HS I successfully sign out
    
  @MATCH-1799 @MATCH-4609
  Scenario: As a Hobsons staff wanting to ensure data security compliance for Intersect users,
            I want to ensure that all users coming through intersect including all subscription levels and HS user is required to complete the Counselor Community profile page,
            So that I can ensure that all users coming into Intersect have consented to terms, account creation, and specified whether they're an EU citizen.
#Non-Naviance
#Cleanup steps
    Given HS I want to login to the HS app using "purpleheautomation+HSCCProfile@gmail.com" as username and "Password!1" as password
    And HS I go to the Counselor Community
    And HS I clear the account to get the community welcome page again
    Then HS I successfully sign out

    Given HS I want to login to the HS app using "purpleheautomation+HSCCProfile@gmail.com" as username and "Password!1" as password
    When HS I verify that I am redirected to the Community activate profile page when accessing RepVisits
    Then HS I verify the new user required to complete the Counselor Community profile form before they can access the following fields
      |Counselor Community|RepVisits|
    Then HS I verify the following fields are required fields in the Counselor Community profile form
      |Job Title field is required.|Office Phone field is required.|Privacy Policy|Are you an EU citizen? field is required.|Terms of Use|Community Guidelines|
    And HS I activate my community profile by providing OfficePhone as "1234567892" JobTitle as "Counselor" and EU citizen as "Yes"
    Then HS I verify the user can access the following fields
      |Counselor Community|RepVisits|
    And HS I successfully sign out
#Naviance
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
    And HS I go to the Counselor Community
    And HS I clear the account to get the community welcome page again
    Then HS I successfully sign out

    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
    When HS I verify that I am redirected to the Community activate profile page when accessing RepVisits
    Then HS I verify the new user required to complete the Counselor Community profile form before they can access the following fields
      |Counselor Community|RepVisits|
    Then HS I verify the following fields are required fields in the Counselor Community profile form
      |Job Title field is required.|Office Phone field is required.|Privacy Policy|Are you an EU citizen? field is required.|Terms of Use|Community Guidelines|
    And HS I activate my community profile by providing OfficePhone as "5137462317" JobTitle as "Counselor" and EU citizen as "Yes"
    Then HS I verify the user can access the following fields
      |Counselor Community|RepVisits|
    And HS I successfully sign out
