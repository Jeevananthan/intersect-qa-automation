@HE
Feature: HE - RepVisits - RepVisitsVisitFeedback - As an HE admin user, I want to be view feedback from HS on my institution's school visits


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

  @MATCH-3076
  Scenario: As a HE user I do not want to see the word 'rating' or 'rate' when interacting with the RV Feedback functionality so I dont feel depressed if feedback isn't always positive from HSs.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I navigate to the "Visit Feedback" page in RepVisits
    Then HE I verify that rate or rating text is not present on Visit Feedback Overview page
    Then HE I verify text displayed while viewing individual staff member feedback

  @MATCH-2404
  Scenario: As an HE user with the Administrator role I want to be presented with a Staff Ratings page
            so I can view information about how my staff is doing when visiting high schools.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I navigate to the "Visit Feedback" page in RepVisits
    Then HE I verify the Visit Feedback heading
    Then HE I verify staff are listed down the left hand side of the page in ABC order by last name
    Then HE I verify that staff listed down on the left hand side of the page display a Community avatar to the left of their name
    Then HE I verify that staff members with no ratings submitted on them for the current school do not display the average rating and star icon to the right of their name
    Then HE I verify that staff members with one or more ratings submitted on them for the current school year display an average rating to the right of their name and a star icon to the right of the average rating
    Then HE I verify that average of all ratings submitted for the HE account for the current school year is displayed as a statistic on Overview area
    Then HE I verify that number of active staff members for the HE account is displayed as a statistic on Overview area
    Then HE I verify that total number of ratings submitted for the HE account for the current school year is displayed as a statistic on Overview area
    Then HE I verify that number of comments submitted for the HE account for the current school year is displayed as a statistic on Overview area
    Then HE I verify the Feedback Breakdown for the HE account on Overview area
    Then HE I verify Top Areas To Improve Percentage Breakdown for the HE account on Overview area

  @MATCH-2962
  Scenario: As an HE user, I want to be able to opt out of RV Ratings so HS users don't spend their time submitting
            ratings on my HE staff that I don't care about.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I navigate to the "Visit Feedback" page in RepVisits
    Then HE I "disable" RepVisits Feedback
    And HE I verify that the Visit Feedback Toggle link displays as "Turn On?"
    Then HE I "enable" RepVisits Feedback
    And HE I verify that the Visit Feedback Toggle link displays as "Turn Off?"

  @MATCH-2405
  Scenario: As an HE user with the Administrator role I want to be presented with a Staff Ratings page so I can view information
            about how an individual staff member is doing when visiting high schools.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I navigate to the "Visit Feedback" page in RepVisits
    Then HE I select "Rosh Nag_HE01" from the RepVisits Feedback user list
    And HE I verify the format of the user feedback page for user "Rosh Nag_HE01"

  @MATCH-4014
  Scenario: HE admins that have enabled Visit Feedback and are reviewing visit-specific feedback for a rep, clicking on the institution associated with the visit returns a "The requested page "/institution/undefined" could not be found error in Counselor Community.
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I navigate to the "Visit Feedback" page in RepVisits
    And HE I select "Rosh Nag_HE01" from the RepVisits Feedback user list
    Then HE I verify that clicking on  " Ritzville High School" link, school profile is loaded

