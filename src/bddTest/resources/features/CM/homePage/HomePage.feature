@CM
Feature: As a Freemium or Legacy Hubs HE user I want to see an additional Community Home page widget
         about all the neato features I am missing out on in the Community so I feel inclined to
         upgrade my HE institution's account to a premium one.

  @MATCH-1550
  Scenario: As a Hubs Premium user I want to see an additional Community Home page widget
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    And SP I set the "Legacy: Hub Community" module to "inactive" in the institution page
    And SP I Click the Save Changes button
    Then SP I successfully sign out
    When HE I want to login to the HE app using "purpleheautomation+limited@gmail.com" as username and "Password!1" as password
    Then CM I verify that the upgrade widget is "visible" for "Hubs Premium" users
    And HE I successfully sign out

  Scenario: As a Community Premium user I should not see upgrade widget
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    And SP I set the "Legacy: Community " module to "<Status>" in the institution page
    And SP I Click the Save Changes button
    Then SP I successfully sign out
    When HE I want to login to the HE app using "purpleheautomation+limited@gmail.com" as username and "Password!1" as password
    Then CM I verify that the upgrade widget is "not visible" for "Community Premium" users
    And HE I successfully sign out

  Scenario: As a Awareness Premium user I should not see upgrade widget
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    And SP I set the "Intersect Awareness Subscription" module to "<Status>" in the institution page
    And SP I Click the Save Changes button
    Then SP I successfully sign out
    When HE I want to login to the HE app using "purpleheautomation+limited@gmail.com" as username and "Password!1" as password
    Then CM I verify that the upgrade widget is "not visible" for "Awareness Premium" users
    And HE I successfully sign out

  Scenario: As a Presence Premium user I should not see upgrade widget
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    And SP I set the "Intersect Presence Subscription" module to "<Status>" in the institution page
    And SP I Click the Save Changes button
    Then SP I successfully sign out
    When HE I want to login to the HE app using "purpleheautomation+limited@gmail.com" as username and "Password!1" as password
    Then CM I verify that the upgrade widget is "not visible" for "Presence Premium " users
    And HE I successfully sign out
      Examples:
      |UserType            |Visibility    |Subscription                         |Status |
      |Hubs Premium        |visible       |Legacy: Hub page management          |active |
      |Community Premium   |invisible     |Legacy: Community                    |active |
      |Awareness Premium   |invisible     |Intersect Awareness Subscription     |active |
      |Presence Premium    |invisible     |Intersect Presence Subscription      |active |