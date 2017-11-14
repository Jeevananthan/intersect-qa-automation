@HE
Feature: HE- RepVisits - RepVisitsVisitFeedback - As an HE admin user, I want to be view feedback from HS on my institution's school visits


  @MATCH-2403
  Scenario: As a HE Administrator, I should see a message informing me that no feedback has been submitted by any high schools yet.
    Given HE I want to login to the HE app using "roshnag.v5+HE_71@gmail.com" as username and "H@llo123" as password
    Then HE I navigate to the "Visit Feedback" page in RepVisits
    Then HE I verify the Visit Feedback heading
    Then HE I verify staff are listed down the left hand side of the page in ABC order by last name
    Then HE I verify that staff listed down on the left hand side of the page display a Community avatar to the left of their name
    Then HE I verify that a message informing me that no feedback has been submitted by any high schools yet is displayed
