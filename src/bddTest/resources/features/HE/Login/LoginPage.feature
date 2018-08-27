@HE
Feature: HE - Login - LoginPage - As an HE user with appropriate access, I should be able to login

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

  @MATCH-1846
  Scenario: As a potential Intersect user, I need to go down the appropriate HE or HS path in the registration url.
            So I can request the correct user account.
    Given HE I navigate to the Intersect Registration app
    Then HE I select "Higher Education Staff Member" and verify that the appropriate text is displayed
    Then HE I select "High School Staff Member" and verify that the appropriate text is displayed


