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

  @MATCH-3076
  Scenario: As a HE user I do not want to see the word 'rating' or 'rate' when interacting with the RV Feedback functionality so I dont feel depressed if feedback isn't always positive from HSs.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I navigate to the "Visit Feedback" page in RepVisits
    Then HE I verify that rate or rating text is not present on Visit Feedback Overview page
    Then HE I verify the text displaying while viewing individual staff member feedback
    Then HE I successfully sign out