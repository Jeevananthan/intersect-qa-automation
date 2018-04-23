@HE
Feature: As a HE user, I want to be able to access secure help link

  @MATCH-2057 @MATCH-2195
  Scenario: As a HE user, I want to access to secure help links to learn about my features.
            So non-clients cannot access our help content and learn about our product.
    Given HE I want to login to the HE app using "purpleheautomation+HEAlmaCollege@gmail.com" as username and "Password!1" as password
    Then HE I verify that the help content is secure and matches the correct URL for "HE Users"
    And HE I successfully sign out
