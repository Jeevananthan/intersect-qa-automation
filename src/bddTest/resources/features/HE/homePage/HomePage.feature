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
  Scenario: When only ''HUBS Management module'' is active in support app, then the access to Community is limited
  and ''User'' nav link is not available for HE publishing user in the HE app
      Given SP I am logged in to the Admin page as an Admin user
      Then SP I select "Bowling Green State University-Main Campus" from the institution dashboard
      And SP I "active" the module "Legacy: Hub page management" in the institution page
      And SP I "inactive" the module "Legacy: Community" in the institution page
      And SP I "inactive" the module "Intersect Awareness Subscription" in the institution page
      And SP I "inactive" the module "Intersect Presence Subscription" in the institution page
      And SP I Click the Save Changes button
      Then SP I successfully sign out

      Given HE I want to login to the HE app using "kpmahibalan93@gmail.com" as username and "P@ssw0rd" as password
      Then HE I verify the upgrade message on the Community widget
      And HE I verify the "Users" nav link is not displaying for this user
      Then HE I successfully sign out

      Given SP I am logged in to the Admin page as an Admin user
      Then SP I select "Bowling Green State University-Main Campus" from the institution dashboard
      And SP I "inactive" the module "Legacy: Hub page management" in the institution page
      And SP I "inactive" the module "Legacy: Community" in the institution page
      And SP I "inactive" the module "Intersect Awareness Subscription" in the institution page
      And SP I "inactive" the module "Intersect Presence Subscription" in the institution page
      And SP I Click the Save Changes button
      Then SP I successfully sign out

