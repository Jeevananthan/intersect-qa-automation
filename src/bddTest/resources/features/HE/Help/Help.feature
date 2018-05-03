@HE
Feature: As a HE user, I want to be able to access secure help link

  @MATCH-2057 @MATCH-2195
  Scenario: As a HE user, I want to access to secure help links to learn about my features.
            So non-clients cannot access our help content and learn about our product.
    Given HE I want to login to the HE app using "purpleheautomation+HEAlmaCollege@gmail.com" as username and "Password!1" as password
    Then HE I verify that the help content is secure and matches the correct URL for "HE Users"
    And HE I successfully sign out

  @MATCH-1430
  Scenario: As an Intersect system, I need to have a Community Guidelines page available
            for users to read and understand how they are supposed to used the system and how the system uses their information.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I navigate to each page and verify the unique URL is present in the "Counselor Community Guidelines" page in Help Center
    Then HE I successfully sign out

