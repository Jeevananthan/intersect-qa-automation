@SP
Feature: SP - AdminDashboard - Configuration page operations

  @MATCH-1556
  Scenario: As a Support user with the Support role I want to be able to activate COmmunity users who have
  inactivated their account
    Given HE I am logged in to Intersect HE as user type "inactivate"
    Then HE I verify the Manage Users screen contains the following user
      | First Name         | Last Name  | Email                                   | Role                       |
      | purpleheautomation | inactivate | purpleheautomation+inactivate@gmail.com | Administrator (All access) |
    Then HE I inactivate the user account for "purpleheautomation+inactivate@gmail.com"
    And HE I successfully sign out
    Given SP I am logged in to the Admin page as an Admin user
    And SP I go to "manageBlockedAccounts" page
    And SP I unblock or activate the account for "purpleheautomation inactivate"
