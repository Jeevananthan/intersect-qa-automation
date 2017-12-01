@HE
Feature: HE- RepVisits - RepVisitsScheduleVisits - As an HE user, I want to be able to search and schedule visits with HS

  @MATCH-1602 @MATCH-1958
  Scenario: As an HE user I want to be able to use the Search and Schedule tab of RepVisits to browse HS availability.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify the Search and Schedule tab of the RepVisits page
    And HE I verify the Coming Soon message on the RepVisits Overview page
    And HE I successfully sign out

  @MATCH-1476 @MATCH-1902 @MATCH-1903 @MATCH-1774
  Scenario: As an HE user with the Intersect Presence Subscription active I want to see
  10 high schools returned in the scheduling results on a map so I can plan my high school visits
  travel efficiently by location.
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "Lebanon High School" in RepVisits
    Then HE I select "Lebanon High School" in "Lebanon, OH" from the RepVisits intermediate search results
    Then HE I view the map plugin on RepVisits Search & Schedule subtab
    And HE I select "Lebanon High School" from the RepVisists map plugin
    Then HE I verify the high school information popup contains the following data
      |School Name          |High School Contact:   |Address                           |Phone          |District      |Type   |Senior Class Size |College Going Rate |
      |Lebanon High School  |PurpleHS User          |1916 Drake Rd Lebanon, Ohio 45036 |(513) 934-5105 |Lebanon City  |PUBLIC |335               |65                 |
