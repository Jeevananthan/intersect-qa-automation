@HE
Feature: HE- RepVisits - RepVisitsTravelPlan - As an HE user, I want to be able to access the Travel plan in order to see
  the list of schools I am interested

@MATCH-3573
Scenario: As a HE premium user, I want the ability to remove any institution I add to my Travel Plan in RepVisits
  Given HE I am logged in to Intersect HE as user type "administrator"
  When HE I add "Westlake High School" high school with location "Austin" to the Travel Plan
  Then HE I verify the "To do" label is displayed for "Westlake High School" high school
  And HE I verify the "This school isnt using RepVisits yet" label is displayed for "Westlake High School" high school
  And HE I verify the trash icon for "Westlake High School" high school
  When HE I cancel removing "Westlake High School" high school from the travel plan
  Then HE I verify "Westlake High School" is displayed in the Travel Plan list
  When HE I remove "Westlake High School" high school from the travel plan
  Then HE I verify "Westlake High School" is not displayed in the Travel Plan list
  Then HE I verify the "Scheduled" label is displayed for "Standalone High School 3" high school
  And HE I successfully sign out
  Given HE I am logged in to Intersect HE as user type "limited"
  Then HE I verify travel plan is locked for non premium users
  And HE I successfully sign out

  @MATCH-4016
  Scenario: As a HE premium user (any role), I want the ability to continue to manage my Travel Plan regardless of SY,
  so that I have as single travel plan I update based upon needs and can see past appts and continue to schedule new appts.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify the "View Availability" button "is displayed" for schools with "Past Appointments"
    And HE I verify the "View Availability" button "is not displayed" for schools with "Upcoming Appointments"
    And HE I verify the "View Availability" button "is displayed" for schools with "Nothing scheduled yet"
    And HE I verify the "past" appointments for schools in travel plan
    And HE I verify the "upcoming" appointments for schools in travel plan
    And HE I successfully sign out
