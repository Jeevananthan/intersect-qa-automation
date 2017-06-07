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
    Scenario: As a HE Premium user,I can able to access all the modules. But as a HE community user, I can able to access only home page
              and Community page

