@CM
Feature: As a Freemium or Legacy Hubs HE user I want to see an additional Community Home page widget
         about all the neato features I am missing out on in the Community so I feel inclined to
         upgrade my HE institution's account to a premium one.

  @MATCH-1550

  Scenario: As a Freemium user I want to see an additional Community Home page widget
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    And SP I set the "Legacy: Hub page management" module to "inactive" in the institution page
    And SP I set the "Legacy: Community" module to "inactive" in the institution page
    And SP I set the "Intersect Awareness Subscription" module to "inactive" in the institution page
    And SP I set the "Intersect Presence Subscription" module to "inactive" in the institution page
    And SP I Click the Save Changes button
    Then SP I successfully sign out
    Given HE I am logged in to Intersect HE as user type "limited"
    Then CM I verify that the upgrade widget is "visible" for "Freemium" users
    And HE I successfully sign out

  Scenario Outline: As a freemium Community user I want to see upgrade Community message and as a premium Community user I should not see the Upgrade Community message
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    And SP I set the "<Subscription>" module to "active" in the institution page
    And SP I Click the Save Changes button
    Then SP I successfully sign out
    Given HE I am logged in to Intersect HE as user type "limited"
    Then CM I verify that the upgrade widget is "<Visibility>" for "<UserType>" users
    And HE I successfully sign out
    #Cleanup
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    And SP I set the "<Subscription>" module to "inactive" in the institution page
    And SP I Click the Save Changes button
    Then SP I successfully sign out

  Examples:
  |UserType            |Visibility    |Subscription                         |Status |
  |Hubs Premium        |visible       |Legacy: Hub page management          |active |
  |Community Premium   |not visible   |Legacy: Community                    |active |
  |Awareness Premium   |not visible    |Intersect Awareness Subscription   |active |
  |Presence Premium    |not visible    |Intersect Presence Subscription    |active |





