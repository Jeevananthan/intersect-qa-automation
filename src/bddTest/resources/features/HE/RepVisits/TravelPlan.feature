@HE
Feature: HE- RepVisits - RepVisitsTravelPlan - As an HE user, I want to be able to access the Travel plan in order to see
  the list of schools I am interested

@MATCH-3573
Scenario: As a HE premium user, I want the ability to remove any institution I add to my Travel Plan in RepVisits
  Given HE I am logged in to Intersect HE as user type "administrator"
  When HE I add "Westlake H S" high school with location "Austin" to the Travel Plan
  Then HE I verify the "To do" label is displayed for "Westlake H S" high school
  And HE I verify the trash icon for "Westlake H S" high school
  When I remove "Westlake H S" high school from the travel plan
  Then I verify "Westlake H S" is not displayed in the Travel Plan list
