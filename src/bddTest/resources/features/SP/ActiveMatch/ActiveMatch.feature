@SP
Feature: SP - Events - SuperAdmin Role for ActiveMatch

  @MATCH-3517
  Scenario: As a Hobsons Support user ghosting in to an HE admin user account, I shouldn't have access to a client's
  ActiveMatch Connections unless I have the Super Admin role so student data is protected.
    Given SP I am logged in to the Admin page as a Support user
    And SP I go to the users list for "The University of Alabama" from the institution dashboard
    And SP I Login as the user "purpleheautomation@gmail.com"

    When HE I open the Active Match section
    Then HE I verify the following message in the Connections screen:
    | You do not have permission to access this data. |
    Then HE I verify that the Download button is disabled

