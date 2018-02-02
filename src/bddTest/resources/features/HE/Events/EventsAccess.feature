@HE @Events
Feature: HE - EventsAccess - As a HE Intersect Administrator/Publishing user with Intersect Presence or Legacy ActiveMatch Events subscriptions,
  I should be able to access the ActiveMatch Events product

  @MATCH-2918
  Scenario: As a HE User with Administrator role with active Presence & Events subscription, I can access Events module ( Open issue with logout MATCH-3109)
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I verify the "Events" nav link is displaying for this user
    And HE I open the Events section
    Then HE The Events page is displayed
    And HE I successfully sign out

  @MATCH-2918
  Scenario: As a HE User with Publishing role with active Presence & Events subscription, I can access Events module ( Open issue with logout MATCH-3109)
    Given HE I am logged in to Intersect HE as user type "publishing"
    And HE I open the Events section
    Then HE The Events page is displayed
    And HE I successfully sign out

  @MATCH-2918
  Scenario: As a HE User with Community role with active Presence & Events subscription, I can not access Events module
    Given HE I am logged in to Intersect HE as user type "community"
    Then HE I verify the "Events" nav link is not displaying for this user
    And HE I successfully sign out

  @MATCH-2918
  Scenario: As a HE User with Administrator role with no Presence/Events subscription, I can not access Events module
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    And SP I set the "Legacy: ActiveMatch Events" module to "inactive" in the institution page
    And SP I set the "Intersect Presence Subscription" module to "inactive" in the institution page
    And SP I Click the Save Changes button
    Then SP I successfully sign out
    Given HE I am logged in to Intersect HE as user type "limited"
    Then HE I verify the "Events" nav link is not displaying for this user
    And HE I successfully sign out

  @MATCH-2918
  Scenario: As a HE User with Administrator role with only Legacy Events subscription, I can access Events module
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    And SP I set the "Legacy: ActiveMatch Events" module to "active" in the institution page
    And SP I set the "Intersect Presence Subscription" module to "inactive" in the institution page
    And SP I Click the Save Changes button
    Then SP I successfully sign out
    Given HE I am logged in to Intersect HE as user type "limited"
    And HE I verify the "Events" nav link is displaying for this user
    And HE I open the Events section
    Then HE The Events page is displayed
    And HE I successfully sign out

  @MATCH-2918
  Scenario: As a HE User with Administrator role with only Presence subscription, I can access Events module
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    And SP I set the "Legacy: ActiveMatch Events" module to "inactive" in the institution page
    And SP I set the "Intersect Presence Subscription" module to "active" in the institution page
    And SP I Click the Save Changes button
    Then SP I successfully sign out
    Given HE I am logged in to Intersect HE as user type "limited"
    And HE I verify the "Events" nav link is displaying for this user
    And HE I open the Events section
    Then HE The Events page is displayed
    And HE I successfully sign out

  #cleanup - Needed because these TCs fail on logout for now
  Scenario: Deactivate subscriptions from Bowling Green
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    And SP I set the "Legacy: ActiveMatch Events" module to "inactive" in the institution page
    And SP I set the "Intersect Presence Subscription" module to "inactive" in the institution page
    And SP I Click the Save Changes button