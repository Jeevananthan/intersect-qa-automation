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
    When HE I click on Repvisits link in left panel it will redirect to Community activate profile page

  @MATCH-1732
  Scenario: As an support user I want the Intersect left navigation bar to be better organized and labeled.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify left navigation bar headings are updated as per mockup
      | Awareness | Counselor Community,Naviance College Profile |
      | Presence  | RepVisits                                    |
      | Settings  | Users                                        |
    And HE I successfully sign out