@HS @MATCH-1811
Feature: HS - LoginLogout - LogoutPage - As an HS user, I can logout to end my session

  @QASmokeTest
  Scenario: As an non Naviance HS user, I want to verify that the HS login page is displayed after logging out from Community
    Given HS I want to login to the HS app using "purpleheautomation+hstest@gmail.com" as username and "Password!1" as password
    And HS I successfully sign out
    Then HS I verify that the HS login page is displayed

  @QASmokeTest
  Scenario: As a Naviance HS user, I want to verify that the HS logout page is displayed after logging out from Community
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    And HS I successfully sign out
    Then HS I verify that the HS login page is displayed