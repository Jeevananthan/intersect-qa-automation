@HE
Feature: HE- RepVisits - RepVisitsAccess - As an HE user, I want to be able to access the RepVisits features  based on my role/subscription

  @MATCH-1697
  Scenario: As an HE user I want to be able to access RepVisit functionality within Intersect so I can find value from this new module and its features
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify the following tabs exist on the RepVisits page
      |Overview |Search and Schedule |Calendar |Travel Plan |Contacts |Recommendations |Notifications|
    And HE I successfully sign out

  @MATCH-1667
  Scenario: As an HE user, I should be able to see Check RepVisits Availability button and Availablity sidebar from HS instituion profile
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "Int QA High School 4" in "Institutions"
    And HE I select "Int QA High School 4" from the results
    Then HE I verify the Check RepVisits Availability button
    And HE I successfully sign out

  @MATCH-1610
  Scenario: As an HE Community member,I need to view a calendar of my appointments
            so that I can easily see what my day/week/month schedule looks like.
    Given HE I am logged in to Intersect HE as user type "community"
    And HE I verify the calendar view in repvisits
    And HE I successfully sign out

  @MATCH-1935 @MATCH-1934 @MATCH-1936 @MATCH-2274
  Scenario: As an HE user tied to an HE account that DOES NOT have the Intersect Presence Subscription activated,
  I need to see an upgrade message on the Contacts,Recommendations,Travel Plan and Visit Feedback tabs.
  #logging to support app to do pre-requisites that is inactive the ''Intersect Presence Subscription'' module
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    And SP I set the "Intersect Presence Subscription" module to "inactive" in the institution page
    And SP I Click the Save Changes button
    Then SP I successfully sign out
  #logging to HE app to verify the upgrade message for contacts, recommendations,Travel Plan and Visit Feedback tabs
    Given HE I am logged in to Intersect HE as user type "limited"
    Then HE I verify the following tabs exist on the RepVisits page
      |Overview |Search and Schedule |Calendar |Travel Plan |Contacts |Recommendations |Notifications| Visit Feedback|
    Then HE I verify the upgrade messaging on the Contacts page in RepVisits
    Then HE I verify the upgrade messaging on the Recommendations page in RepVisits
    Then HE I verify the upgrade messaging on the Travel Plan page in RepVisits
    And HE I navigate to the "Visit Feedback" page in RepVisits
    Then HE I verify the freemium messaging on the Visits Feedback page
    Then HE I successfully sign out

  @MATCH-2274
  Scenario: As a HE non-Administrator user, I want to ensure the no access messaging on the Visit Feedback section of the RepVisits page.
    Given HE I am logged in to Intersect HE as user type "community"
    Then HE I navigate to the "Visit Feedback" page in RepVisits
    And HE I verify the non-administrator messaging on the Visits Feedback page
    And HE I successfully sign out
