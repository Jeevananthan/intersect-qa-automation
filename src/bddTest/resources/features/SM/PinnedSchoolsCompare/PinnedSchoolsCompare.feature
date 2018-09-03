@SM
Feature: SM - PinnedSchoolsCompare - PinnedScoolsCompare - Compare Pinned Schools

  @MATCH-3450
  Scenario: As a HS student that is comparing my pinned schools, I want to expand and collapse buckets of data about my
  pinned colleges so I can control how much data is showing on my screen at a time. (MATCH-4787)
    Given SM I am logged in to SuperMatch through Family Connection
    And I clear the onboarding popups if present
    And SM I start the search over
    When I select the following data from the Admission Fit Criteria
      | GPA (4.0 scale) | 4 |
      | SAT Composite   | 400 |
      | ACT Composite   | 3   |
      | Acceptance Rate | 25% or Lower |
    #The following step is needed to avoid MATCH-4830
    And SM I reload the page
    And SM I pin "Colorado College" if it is not pinned already
    And SM I open the Pinned Schools Compare screen
    Then SM I verify that "10" drawers are displayed
    Then SM I verify that all drawers are expanded by default
    Then SM I verify that the "expanded" drawer in position "1" display an arrow facing "down"
    And SM I collapse all the drawers using the Collapse All button
    Then SM I verify that the Collapse All button changes to "Expand All" after it has been used
    Then SM I verify that the Collapse All button collapses all the drawers
    Then SM I verify that the "collapsed" drawer in position "1" display an arrow facing "right"
    And SM I expand the drawer in position "1"
    Then SM I verify that Expand All button changes to "Collapse All" when at least "1" drawer is expanded

  @MATCH-3523
  Scenario: As a HS student that is comparing my pinned schools, I want to export those schools and their data so I can
  view this information outside of SuperMatch.
    Given SM I am logged in to SuperMatch through Family Connection
    And I clear the onboarding popups if present
    And SM I clear pinned schools list
    And SM I start the search over
    When I select the following data from the Admission Fit Criteria
      | GPA (4.0 scale) | 4 |
      | SAT Composite   | 400 |
      | ACT Composite   | 3   |
      | Acceptance Rate | 25% or Lower |
    #The following step is needed to avoid MATCH-4830
    And SM I reload the page
    And SM I pin "Colorado College" if it is not pinned already
    And SM I open the Pinned Schools Compare screen
    And SM I export the data in the Pinned Schools Compare screen
    Then HE I verify the downloaded ActiveMatch Cvs file "PinnedColleges.csv" contains the following headers
    #The first empty space in the data table is for the first empty cell in the exported document
      | ﻿  |Colorado College|
    And HE I delete the downloaded ActiveMatch Cvs file "PinnedColleges.csv"


    @MATCH-3712
    Scenario: As a HS student that is comparing my pinned schools, I want to see cost details about each college
    side by side so I can determine which pinned college is a best fit for me based on their cost.
      Given SM I am logged in to SuperMatch through Family Connection
      And SM I skip the onboarding modals
      And SM I clear all pills from Must have  and Nice to have boxes
      And SM I clear pinned schools list
      And SM I click "Admission" filter criteria tab
      And SM I clean GPA/SAT/ACT scores
      And HS I Click on close button
      And SM I select the "Small City" checkbox from "Location" fit criteria
      And SM I select the "Historically Black Institutions" checkbox from "Diversity" fit criteria
      And SM I pin "Bennett College" if it is not pinned already
      And SM I select the "Meets 100% of Need" checkbox from "Cost" fit criteria
      And SM I pin "Smith College" if it is not pinned already
      Then SM I open the Pinned Schools Compare screen
      Then SM I verify that in the "Cost" criteria table "Meets 100% of need" criteria for the 1 college is "Unknown"
      Then SM I verify that in the "Cost" criteria table "Meets 100% of need" criteria for the 2 college is "Yes"
