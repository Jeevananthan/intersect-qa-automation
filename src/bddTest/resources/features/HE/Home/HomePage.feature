@HE
Feature: HE - Home - HomePage - As an HE user, I want to be able to access the features of the Home page

  @MATCH-1350
  Scenario: As an HE user, I want to access the Intersect Help page
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify I have access the Intersect Help page

#  @MATCH-1549 @QASmokeTest
#   covered by MATCH-4660-MATCH-4664
#  Scenario: As a HE Freemium Community user, I should see the Upgrade to Premium message on the Home page
#    Given HE I am logged in to Intersect HE as user type "limited"
#    Then HE I verify the upgrade message on the Community widget

#  @MATCH-1266
#  @skip covered by MATCH-4657 - MATCH-4664
#  Scenario: As an HE user I want a home page that familiarizes me with the Intersect product and its features.
#            so I am clear on what I can do within the app.
#    Given SP I am logged in to the Admin page as a Support user
#    When SP I search for "2400006" in "HE Accounts"
#    And SP I select "The University of Alabama" from the global search results
#    And SP I set the "Legacy: Hub page management" module to "inactive" in the institution page
#    And SP I Click the Save Changes button
#    Then SP I successfully sign out
#    Given HE I am logged in to Intersect HE as user type "administrator"
#    Then I verify that the "The Hobsons Counselor Community" widget is displayed
#    Then I verify that the "Manage and update your institution's profile" widget is displayed
#    Then I verify that the "Configure your Account" widget is displayed
#    And HE I successfully sign out
#    When HE I am logged in to Intersect HE as user type "publishing"
#    Then I verify that the "The Hobsons Counselor Community" widget is displayed
#    Then I verify that the "Manage and update your institution's profile" widget is displayed
#    Then I verify that the "Configure your Account" widget is not displayed
#    And HE I successfully sign out
#    When HE I am logged in to Intersect HE as user type "community"
#    Then I verify that the "The Hobsons Counselor Community" widget is displayed
#    Then I verify that the "Manage and update your institution's profile" widget is not displayed
#    Then I verify that the "Configure your Account" widget is not displayed
#    And HE I successfully sign out

  @MATCH-1799 @MATCH-4609
  Scenario: As a Hobsons staff wanting to ensure data security compliance for Intersect users,
            I want to ensure that all users coming through intersect including all subscription levels and HE user is required to complete the Counselor Community profile page,
            So that I can ensure that all users coming into Intersect have consented to terms, account creation, and specified whether they're an EU citizen.
    #Cleanup steps
    Given HE I want to login to the HE app using "purpleheautomation+admin_match_1799@gmail.com" as username and "Password!1" as password
    And HE I go to the Counselor Community
    And HE I clear the account to get the community welcome page again
    Then HE I successfully sign out
    # Testcase
    Given HE I want to login to the HE app using "purpleheautomation+admin_match_1799@gmail.com" as username and "Password!1" as password
    When HE I verify that I am redirected to the Community activate profile page when accessing RepVisits
    Then HE I verify the new user required to complete the Counselor Community profile form before they can access the following fields
      |Counselor Community|RepVisits|Events|ActiveMatch|
    Then HE I verify the following fields are required fields in the Counselor Community profile form
      |Job Title field is required.|Office Phone field is required.|Privacy Policy|Are you an EU citizen? field is required.|Terms of Use|Community Guidelines|
    And HE I activate my community profile by providing OfficePhone as "1234567892" JobTitle as "Counselor" and EU citizen as "Yes"
    And HE I verify clicking on RepVisits will redirect to Search and Schedule tab of RepVisits
    Then HE I verify the user can access the following fields
      |Counselor Community|RepVisits|Events|ActiveMatch|
    And HE I clear the account to get the community welcome page again
    Then HE I successfully sign out

  @MATCH-1732 @MATCH-1496
  Scenario: As an HE user I want the Intersect left navigation bar to be better organized and labeled.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify the left navigation bar and section breadcrumbs are as follows
      | Awareness | Counselor Community, Naviance College Profile |
      | Presence  | RepVisits                                     |
    And HE I successfully sign out

  @MATCH-1344
  Scenario: As a HE premium user with Administrator role, I want to verify I have the appropriate access to various modules
        Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I verify the "Home" nav link is displaying for this user
    And HE I verify the "Counselor Community" nav link is displaying for this user
    And HE I verify the "Naviance College Profile" nav link is displaying for this user
    And HE I verify the "RepVisits" nav link is displaying for this user
    Then HE I successfully sign out

  @MATCH-1344
  Scenario: As a HE Premium user with Community role, I want to verify I have the appropriate access to various modules
    When HE I am logged in to Intersect HE as user type "community"
    And HE I verify the "Home" nav link is displaying for this user
    And HE I verify the "Counselor Community" nav link is displaying for this user
    And HE I verify the "RepVisits" nav link is displaying for this user
    And HE I verify the "Naviance college profile" nav link is not displaying for this user
    And HE I verify the "Users" nav link is not displaying for this user
    Then HE I successfully sign out
#
#  @MATCH-1548 @skip
##  to be covered by MATCH-4631
#  Scenario: As an HE Freemium user, I want to be able to access 'Experience the full benefits of the Counselor Community' page.
#    Given HE I am logged in to Intersect HE as user type "limited"
#    Then HE I click on Learn More button on Upgrade message on the Community Widget
#    Then HE I verify the benefits of the Counselor Community popup and the details of the following freemium user
#    |First Name |Last Name      |Work Email Address                  |School / Institution Name|
#    |PurpleHE   |Limited        |purpleheautomation+limited@gmail.com|Bowling Green State University-Main Campus|
#    And HE I click on Request Information button Counselor Community popup
#    Then HE I verify the Confirmation message for Request Information
#
#  @MATCH-1387 @skip
    ##  to be covered by MATCH-4631 and MATCH-4919
#  Scenario: As a HE Publishing user with only 'Legacy HUBS' subscription active in Support app, I should have limited access to Community and no access to User Management
#      Given SP I am logged in to the Admin page as an Admin user
#      Then SP I select "Alma College" from the institution dashboard
#      And SP I set the "Legacy: Hub page management" module to "active" in the institution page
#      And SP I set the "Legacy: Community" module to "inactive" in the institution page
#      And SP I set the "Intersect Awareness Subscription" module to "inactive" in the institution page
#      And SP I set the "Intersect Presence Subscription" module to "inactive" in the institution page
#      And SP I Click the Save Changes button
#      Then SP I successfully sign out
#
#      Given HE I want to login to the HE app using "purpleheautomation+123@gmail.com" as username and "Password!1" as password
#      Then HE I verify the upgrade message on the Community widget
#      And HE I verify the "Users" nav link is not displaying for this user
#      Then HE I successfully sign out
#
#      Given SP I am logged in to the Admin page as an Admin user
#      Then SP I select "Alma College" from the institution dashboard
#      And SP I set the "Legacy: Hub page management" module to "inactive" in the institution page
#      And SP I set the "Legacy: Community" module to "inactive" in the institution page
#      And SP I set the "Intersect Awareness Subscription" module to "inactive" in the institution page
#      And SP I set the "Intersect Presence Subscription" module to "inactive" in the institution page
#      And SP I Click the Save Changes button
#      Then SP I successfully sign out

  @MATCH-1365
  Scenario: As an HE, HS, or Hobsons user,I need to be presented with the notifications globe from Community in the Intersect banner
  so I can still see when I have notifications from Community.
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I add post in the Homepage "test1921"
    And SP I successfully sign out

    Then HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify the navigation globe is displayed for this user
    And HE I click the navigation globe for viewing the recent notifications
    Then HE I successfully sign out

    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I verify the navigation globe is displayed for this user
    And HS I click the navigation globe for viewing the recent notifications
    Then HS I successfully sign out

  @MATCH-4657 @MATCH-4660 @MATCH-4661 @MATCH-4662 @MATCH-4664 @MATCH-4658  @MATCH-1266
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
      | limited       | Counselor Community      | PARTICIPATE | counselor-community/            |

  @MATCH-1430
  Scenario: As a HE user, I should be able to access Privacy Policy and Terms of Use pages
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify "Terms of Service" is present in the footer
    Then HE I verify "Privacy Policy" is present in the footer
    Then HE I navigate to each page and verify the unique URL is present in the "Terms of Service" page
    Then HE I navigate to each page and verify the unique URL is present in the "Privacy Policy" page
    And HE I successfully sign out

  @MATCH-2057 @MATCH-2195
  Scenario: As a HE user, I want to access to secure help links to learn about my features.
  So non-clients cannot access our help content and learn about our product.
    Given HE I want to login to the HE app using "purpleheautomation+HEAlmaCollege@gmail.com" as username and "Password!1" as password
    Then HE I verify that the help content is secure and matches the correct URL for "HE Users"
    And HE I successfully sign out

  @MATCH-1430
  Scenario: As a HE user, I can access Community Guidelines page
  to read and understand how they are supposed to used the system and how the system uses their information.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I navigate to each page and verify the unique URL is present in the "Counselor Community Guidelines" page in Help Center
    Then HE I successfully sign out

  @MATCH-3563
  Scenario:As a HE, I verify the Copyright information
    Given HE I verify the current year is displayed at the bottom of the window in the login page
    Then  HE I verify the current year is displayed at the bottom of the window in the Registration page

    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify the current year is displayed at the bottom of the window in the Home Page
    And HE I verify the items are present in the help center dropdown
    Then HE I successfully sign out