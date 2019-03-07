@SP
Feature: SP - AdminDashboard - Configuration page operations

  @MATCH-1556
  Scenario: As a Support user with the Support role I want to be able to activate Community users who have
  inactivated their account
    Given SP I am logged in to the Admin page as a Support user
    And SP I go to "manageBlockedAccounts" page
    And SP I unblock or activate the account for "purpleheautomation inactivate"
    And SP I successfully sign out

    Given HE I am logged in to Intersect HE as user type "inactivate"
    And HE I go to the Counselor Community
    When I go to user profile page
    Then I click on Edit profile button
    And I click on Inactivate Account link
    And HE I successfully sign out

    Given SP I am logged in to the Admin page as a Support user
    And SP I go to "manageBlockedAccounts" page
    And SP I unblock or activate the account for "purpleheautomation inactivate"
    And SP I successfully sign out

    Given HE I am logged in to Intersect HE as user type "inactivate"
    And HE I go to the Counselor Community
    When I go to user profile page
    And HE I successfully sign out

    Given HE I am logged in to Intersect HE as user type "inactivate"
    And HE I go to the Counselor Community
    When I go to user profile page
    Then I click on Edit profile button
    And I click on Inactivate Account link
    And HE I successfully sign out