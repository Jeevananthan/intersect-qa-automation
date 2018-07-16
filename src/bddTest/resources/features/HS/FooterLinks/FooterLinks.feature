@HS
Feature: As a HS user, I want to be able to access Footer Links

  @MATCH-1430
  Scenario: As an Intersect system, I need to have a Privacy Policy page, a Terms of Use page
            available for users to read and understand how they are supposed to used the system and how the system uses their information.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I verify "Terms of Service" is present in the footer
    Then HS I verify "Privacy Policy" is present in the footer
    Then HS I navigate to each page and verify the unique URL is present in the "Terms of Service" page
    Then HS I navigate to each page and verify the unique URL is present in the "Privacy Policy" page
    And HS I successfully sign out

    Given HS I want to login to the HS app using "purpleheautomation+admin@gmail.com" as username and "Password!1" as password
    Then HS I verify "Terms of Service" is present in the footer
    Then HS I verify "Privacy Policy" is present in the footer
    Then HS I navigate to each page and verify the unique URL is present in the "Terms of Service" page
    Then HS I navigate to each page and verify the unique URL is present in the "Privacy Policy" page
    And HS I successfully sign out