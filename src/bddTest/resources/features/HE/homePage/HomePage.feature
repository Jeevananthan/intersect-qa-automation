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
    Then I verify that "The Hobsons Counselor Community" widget is displayed
    Then I verify that "Manage and update your institution's profile" widget is displayed
    Then I verify that "Configure your Account" widget is displayed
    And HE I successfully sign out
    When HE I am logged in to Intersect HE as user type "publishing"
    Then I verify that "The Hobsons Counselor Community" widget is displayed
    Then I verify that "Manage and update your institution's profile" widget is displayed
    And HE I successfully sign out
    When HE I am logged in to Intersect HE as user type "community"
    Then I verify that "The Hobsons Counselor Community" widget is displayed
    And HE I successfully sign out