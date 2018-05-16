@HE
Feature: As a HE user, I want to be able to access Footer Links

  @MATCH-1430
  Scenario: As an Intersect system, I need to have a Privacy Policy page, a Terms of Use page
            available for users to read and understand how they are supposed to used the system and how the system uses their information.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify "Terms of Service" is present in the footer
    Then HE I verify "Privacy Policy" is present in the footer
    Then HE I navigate to each page and verify the unique URL is present in the "Terms of Service" page
    Then HE I navigate to each page and verify the unique URL is present in the "Privacy Policy" page
    And HE I successfully sign out