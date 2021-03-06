@SP
Feature: SP - HomePage - Help - Ability to access Help content

  @MATCH-2057 @MATCH-2195
  Scenario: As a SP user, I want to access to secure help links to learn about my features.
            So non-clients cannot access our help content and learn about our product.
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I verify that the help content is secure and matches the correct URL for "SP Users"
