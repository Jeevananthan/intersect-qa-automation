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