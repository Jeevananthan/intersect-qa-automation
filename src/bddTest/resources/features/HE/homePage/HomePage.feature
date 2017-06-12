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

  @MATCH-1496
  Scenario: As an HE user I want the Intersect left navigation bar to be better organized and labeled.
    Given  HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify the left navigation bar and section breadcrumbs are as follows
      | Awareness | Counselor Community |
      | Awareness | Naviance College Profile |
    And HE I verify the left navigation bar and section breadcrumbs are as follows
      |Presence |RepVisits |
    Then HE I successfully sign out
