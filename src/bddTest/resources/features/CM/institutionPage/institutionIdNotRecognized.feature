@CM @MATCH-1406
Feature: Community - Display Error Page When Institution ID is Not Recognized

  Scenario: Display Error Page When Institution ID is Not Recognized
    Given HE I am logged in to Intersect HE as user type "administrator"
    And I go to institution page with institution id "345678945678"
    And I check if correct error message is displayed
    Then HE I successfully sign out