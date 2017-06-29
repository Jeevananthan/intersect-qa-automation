@HE
Feature: As an HE user I want to login to Intersect

  @MATCH-1428 @QASmokeTest
  Scenario: As an HE user, I want to verify the content of the HE Login Page
    Given HE I verify the HE login screen

  @MATCH-1101
  Scenario: As a system, users should be locked out after failing 5 login attempts in 60 minutes.
            As an administrator, I can unlock a users' account which has been locked.
    When HE I want to login to the HE app using "purpleheautomation+locked@gmail.com" as username and "boGusPassw0rd" as password
    And HE I want to login to the HE app using "purpleheautomation+locked@gmail.com" as username and "boGusPassw0rd" as password
    And HE I want to login to the HE app using "purpleheautomation+locked@gmail.com" as username and "boGusPassw0rd" as password
    And HE I want to login to the HE app using "purpleheautomation+locked@gmail.com" as username and "boGusPassw0rd" as password
    And HE I want to login to the HE app using "purpleheautomation+locked@gmail.com" as username and "boGusPassw0rd" as password
    Then HE I am locked out from logging in as user type "locked"
    Then HE I am logged in to Intersect HE as user type "administrator"
    And HE I unlock the user account for "purpleheautomation+locked@gmail.com"
    And HE I successfully sign out
    Then HE I am logged in to Intersect HE as user type "locked"
    And HE I am able to successfully login
    Then HE I successfully sign out

  @MATCH-1857
  Scenario: As a HE user, I need to be able to request a user account by providing the necessary information about myself.
            So Support can provision my user account.

    Given HE I navigate to Registration Intersect url
    And HE I search for "The University of Alabama" in "Higher Education Staff Member" registeration page
    And HE I click the link "please complete this form."
    Then HE I verify all field type in request user page
      |firstName |lastName |email |verifyEmail |jobTitle |authorizedToPostPublicInformation |schedulesVisits |
      |text      |text     |email |email       |text     |checkbox                          |checkbox        |









