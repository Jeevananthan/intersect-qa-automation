@HUBS-1016
Feature: As a community user viewing College Hubs, I want to verify the functionality of the links in HEM

  Background:
    Given HE I want to login to the HE app using "jorgetesthobsons@gmail.com" as username and "Hobsons2016!" as password
    And HUBS I access HUBS Edit Mode

  Scenario: The 'Terms of Service' link points to the Terms of Service page
    When HUBS I verify the Terms of Service page
    | Terms of Service | Privacy Policy |
    And HE I successfully sign out

