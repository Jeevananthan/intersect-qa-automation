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
      And HE I verify the "Home" menu is displaying for this user
      And HE I verify the "Counselor Community" menu is displaying for this user
      And HE I verify the "Naviance College Profile" menu is displaying for this user
      And HE I verify the "RepVisits" menu is displaying for this user
      And HE I verify the "Users" menu is displaying for this user
      Then HE I am logging out from HE interset app
      When HE I am logged in to Intersect HE as user type "community"
      And HE I verify the "Home" menu is displaying for this user
      And HE I verify the "Counselor Community" menu is displaying for this user
      And HE I verify the "RepVisits" menu is displaying for this user
      And HE I verify the "Naviance college profile" menu is not displaying for this user
      And HE I verify the "Users" menu is not displaying for this user
      Then HE I am logging out from HE interset app




