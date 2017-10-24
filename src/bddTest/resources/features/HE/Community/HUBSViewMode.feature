@HE @HUBS
Feature: HE- Community - HUBSViewMode - As a HE user, I want to be able to view HE Institution Data
  @MATCH-1815
  Scenario: As a HE user, I should be able to view my HUBS Institution data
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I go to the Counselor Community
    And HS I access the INSTITUTION page
    Then SP I verify Hubs view mode for "The University of Alabama"
    And HE I successfully sign out
