@HS @MATCH-1811
Feature: As a HS user I need to logout from Intersect

  @QASmokeTest
  Scenario: As an non Naviance HS user, I want to verify that the HS login page is displayed after logging out from Community
    Given HS I want to login to the HS app using "hobsonstest11@mailinator.com" as username and "Control!23" as password
    And HS I successfully sign out
    Then HS I verify that the HS login page is displayed

  @QASmokeTest
  Scenario: As a Naviance HS user, I want to verify that the HS logout page is displayed after logging out from Community
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    And HS I successfully sign out
    Then HS I verify that the HS login page is displayed