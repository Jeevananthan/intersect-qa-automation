@HE
Feature: As an HE user, I want to be able to access the features of the main Intersect UI.

  @MATCH-1350
  Scenario: As an HE user, I want to access the Intersect Help page
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify I have access the Intersect Help page

  @MATCH-1549 @QASmokeTest
  Scenario: As a Freemium user, users should see Additional Intersect Home Page Widget.
    Given HE I want to login to the HE app using "mandeep.bhangu@hobsons.com" as username and "Hobsons!234" as password
    Then HE I verify the upgrade message on the Community widget

  @MATCH-1266
  Scenario: As an HE user I want a home page that familiarizes me with the Purple product and its features.
            so I am clear on what I can do within the app.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then I verify that the "The Hobsons Counselor Community" widget is displayed
    Then I verify that the "Manage and update your institution's profile" widget is displayed
    Then I verify that the "Configure your Account" widget is displayed
    And HE I successfully sign out
    When HE I am logged in to Intersect HE as user type "publishing"
    Then I verify that the "The Hobsons Counselor Community" widget is displayed
    Then I verify that the "Manage and update your institution's profile" widget is displayed
    Then I verify that the "Configure your Account" widget is not displayed
    And HE I successfully sign out
    When HE I am logged in to Intersect HE as user type "community"
    Then I verify that the "The Hobsons Counselor Community" widget is displayed
    Then I verify that the "Manage and update your institution's profile" widget is not displayed
    Then I verify that the "Configure your Account" widget is not displayed
    And HE I successfully sign out
  @MATCH-1799
  Scenario: Force new user to Activate Community Profile first before accessing RepVisits
    Given HE I want to login to the HE app using "yadav.arun24+auto_1799_1@gmail.com" as username and "Hobsons@2017" as password
    When HE I verify that I am sent to the Community activate profile page when accessing RepVisits
    Then HE I successfully sign out

  @MATCH-1732
  Scenario: As an support user I want the Intersect left navigation bar to be better organized and labeled.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify the left navigation bar and section breadcrumbs are as follows
      | Awareness | Counselor Community,Naviance College Profile |
      | Presence  | RepVisits                                    |
      | Settings  | Users                                        |
    And HE I successfully sign out

  @MATCH-1344
  Scenario: As a HE Users, I want to make sure that appropriate menu's are displaying for HE premium user and HE community user
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I verify the "Home" nav link is displaying for this user
    And HE I verify the "Counselor Community" nav link is displaying for this user
    And HE I verify the "Naviance College Profile" nav link is displaying for this user
    And HE I verify the "RepVisits" nav link is displaying for this user
    And HE I verify the "Users" nav link is displaying for this user
    Then HE I successfully sign out
    When HE I am logged in to Intersect HE as user type "community"
    And HE I verify the "Home" nav link is displaying for this user
    And HE I verify the "Counselor Community" nav link is displaying for this user
    And HE I verify the "RepVisits" nav link is displaying for this user
    And HE I verify the "Naviance college profile" nav link is not displaying for this user
    And HE I verify the "Users" nav link is not displaying for this user
    Then HE I successfully sign out


  @MATCH-1496
  Scenario: As an HE user I want the Intersect left navigation bar to be better organized and labeled.
    Given  HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify the left navigation bar and section breadcrumbs are as follows
      | Awareness | Counselor Community |
      | Awareness | Naviance College Profile |
    And HE I verify the left navigation bar and section breadcrumbs are as follows
      |Presence |RepVisits |
    Then HE I successfully sign out

  @MATCH-1548
  Scenario: As an HE Freemium user, I want to be able to access Experience the full benefits of the Counselor Community page.
    Given HE I am logged in to Intersect HE as user type "limited"
    Then HE I click on Learn More button on Upgrade message on the Community Widget
    Then HE I verify the benefits of the Counselor Community popup and the details of the following freemium user
    |First Name |Last Name      |Work Email Address                  |School / Institution Name|
    |IntersectHE|Limited        |purpleheautomation+limited@gmail.com|Bowling Green State University-Main Campus|
    And HE I click on Request Information button Counselor Community popup
    Then HE I verify the Confirmation message for Request Information



  @MATCH-1387
  Scenario: If only ''HUBS Management module'' is active in support app, then the access to Community is limited
            and ''User'' nav link is not available for HE publishing user in the HE app
      Given SP I am logged in to the Admin page as an Admin user
      Then SP I select "Alma College" from the institution dashboard
      And SP I set the "Legacy: Hub page management" module to "active" in the institution page
      And SP I set the "Legacy: Community" module to "inactive" in the institution page
      And SP I set the "Intersect Awareness Subscription" module to "inactive" in the institution page
      And SP I set the "Intersect Presence Subscription" module to "inactive" in the institution page
      And SP I Click the Save Changes button
      Then SP I successfully sign out

      Given HE I want to login to the HE app using "purpleheautomation+123@gmail.com" as username and "Password!1" as password
      Then HE I verify the upgrade message on the Community widget
      And HE I verify the "Users" nav link is not displaying for this user
      Then HE I successfully sign out

      Given SP I am logged in to the Admin page as an Admin user
      Then SP I select "Alma College" from the institution dashboard
      And SP I set the "Legacy: Hub page management" module to "inactive" in the institution page
      And SP I set the "Legacy: Community" module to "inactive" in the institution page
      And SP I set the "Intersect Awareness Subscription" module to "inactive" in the institution page
      And SP I set the "Intersect Presence Subscription" module to "inactive" in the institution page
      And SP I Click the Save Changes button
      Then SP I successfully sign out


  @MATCH-1365
  Scenario: As an HE, HS, or Hobsons user,I need to be presented with the notifications globe from Community in the Intersect banner
  so I can still see when I have notifications from Community.
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I add post in the Homepage "test100"
    And SP I successfully sign out

    Then HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify the navigation globe is displayed for this user
    And HE I click the naviagtion globe for viewing the recent notifications
    Then HE I successfully sign out

    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I verify the navigation globe is displayed for this user
    And HS I click the naviagtion globe for viewing the recent notifications
    Then HS I successfully sign out
