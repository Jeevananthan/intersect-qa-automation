@MATCH-1706
Feature: As a Community user I want to be able to see subtab/category called "Pending Requests" and subtab/category called "Invited Connections".
  For Pending Requests, subtab/category is displays a red dot indicator with the appropriate number in the dot for the number of pending connections requests the user needs to respond to


  Scenario: As a Community user I want to be able to see subtab/category called "Pending Requests" and subtab/category called "Invited Connections"
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then I go to connections page
    And I check if subtabs/categories called "Pending Requests" and "Invited Connections" are displayed



  Scenario: The Pending Requests subtab/category displays a red dot indicator with the appropriate number in the dot for the number of pending connections requests the user needs to respond to
    Given HE I am logged in to Intersect HE as user type "administrator"
    When I am not connected to "PurpleHS User" user
    Then I search for "PurpleHS User" and open profile page of this user
    And I click on connect button
    And I send the connection invitation
    Then HE I successfully sign out
    When HS I am logged in to Intersect HS as user type "default"
    And I go to HS user profile page
    And I go to HS connections page
    Then I check if I see a red indicator which indicates pending connections
    And I click on Pending requests link
    And I click Approve button for "PurpleHE Automation" user