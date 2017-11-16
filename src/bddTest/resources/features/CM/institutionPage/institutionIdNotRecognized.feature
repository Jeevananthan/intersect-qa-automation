@CM @MATCH-1406
Feature: Community - Display Error Page When Institution ID is Not Recognized

  Scenario: Display Error Page When Institution ID is Not Recognized
    Given I am logged in to Purple Community through the HE App
    And I go to institution page with institution id "345678945678"
    And I check if correct error message is displayed
    Then I sign out from the HE app