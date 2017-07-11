@HS
Feature: As a HS user, I want to be able to access secure help link

  @MATCH-2057 @MATCH-2195
  Scenario: As a HS user, I want to access to secure help links to learn about my features.
            So non-clients cannot access our help content and learn about our product.
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I verify that the help content is not available for "Naviance HS Users"
    And HS I successfully sign out

    Given HS I want to login to the HS app using "purpleheautomation+HSSolidRock@gmail.com" as username and "Password!1" as password
    Then HS I verify that the help content is secure and matches the correct URL for "Non-Naviance HS Users"
    And HS I successfully sign out