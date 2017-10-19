@MATCH-2918
Feature: As a HE Intersect ADMIN or PUBLISHER with Intersect Presence or Legacy ActiveMatch Events product, I need the
  ability to access the ActiveMatch Events product in Intersect.

  Scenario: Events section is displayed for Admin users in Intersect HE (MATCH-3109)
    Given HE I want to login to the HE app using "jorgetesthobsons@gmail.com" as username and "Hobsons2016!" as password
    And HE I open the Active Match section
    Then HE The Active Match page is displayed
    And HE I successfully sign out

  Scenario: Events section is not displayed for Publishing users in Intersect HE
    Given HE I want to login to the HE app using "testhobsons1145@mailinator.com" as username and "Hobsons!23" as password
    Then HE The Active Match page is not displayed
    And HE I successfully sign out

  Scenario: Events section is not displayed for Community users in Intersect HE
    Given HE I want to login to the HE app using "testhobsonscomm@mailinator.com" as username and "Hobsons!23" as password
    Then HE The Active Match page is not displayed
    And HE I successfully sign out


