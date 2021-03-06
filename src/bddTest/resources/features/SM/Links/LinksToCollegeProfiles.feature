@SM
Feature: SM - LinksToCollegeProfiles - LinksToCollegeProfiles - As a SuperMatch tool, I need to update the embedded version
         of the UI to link to the colleges' profile pages in all the areas we had to skip this requirement when using
         the standalone version.

  @MATCH-4331
  Scenario: As a HS student I want to see the college's profile page after clicking the proper links
    Given SM I am logged in to SuperMatch through Family Connection
    And I clear the onboarding popups if present
    And SM I start the search over
    When I select the following data from the Admission Fit Criteria
      | GPA (4.0 scale) | 4 |
      | SAT Composite   | 400 |
      | ACT Composite   | 3   |
      | Acceptance Rate | 25% or Lower |
    Then SM I select the following data from the Location Fit Criteria
      |State or Province  |
      |Maine              |
    #The following step is needed to avoid MATCH-4830
    #And SM I reload the page
    Then SM I verify the College Profile page for "Bates College" in the following sections:
      | Search results         |
      | Why? drawer            |
      | Academic Match section |

  @MATCH-4331
  Scenario: As a HS student I want to see the college's profile Housing Information section after clicking the proper links
    Given SM I am logged in to SuperMatch through Family Connection
    And I clear the onboarding popups if present
    And SM I start the search over
    And SM I clear pinned schools list
    When I select the following data from the Admission Fit Criteria
      | GPA (4.0 scale) | 4 |
      | SAT Composite   | 400 |
      | ACT Composite   | 3   |
      | Acceptance Rate | 25% or Lower |
    Then SM I select the following data from the Location Fit Criteria
      |State or Province  |
      |California         |
    #The following step is needed to avoid MATCH-4830
    #And SM I reload the page
    And SM I pin "Pomona College"
    And SM I open the Pinned Schools Compare screen
    Then SM I verify that the appropriate section in the college's profile is displayed after clicking the following links:
      | # of 4 year majors      |
      | Varsity sports          |
      | Male varsity sports     |
      | Female varsity sports   |
      | Clubs and organizations |
    And SM I unpin "Pomona College" from the Schools Compare screen
