@HE
Feature: HE- RepVisits - RepVisitsVisitFeedback - As an HE admin user, I want to be view feedback from HS on my institution's school visits


  @MATCH-2403
  Scenario: As a HE Administrator, I should see a message informing me that no feedback has been submitted by any high schools yet.
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    And SP I set the "Intersect Presence Subscription" module to "active" in the institution page
    And SP I Click the Save Changes button
    Then SP I successfully sign out
    Given HE I am logged in to Intersect HE as user type "limited"
    Then HE I navigate to the "Visit Feedback" page in RepVisits
    Then HE I verify the formatting of the Visit Feedback page
    Then HE I successfully sign out
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    And SP I set the "Intersect Presence Subscription" module to "inactive" in the institution page
    And SP I Click the Save Changes button
    Then SP I successfully sign out
