@SM

Feature: SM - Athletics - As a HS student, I need to be able to search for colleges based on the 'Athletics' fit criteria

  @MATCH-3711
  Scenario: As a HS student that is comparing my pinned schools, I want to see Athletics details about each college
  side by side so I can determine which pinned college is a best fit for me based on their Athletics.
    Given SM I am logged in to SuperMatch through Family Connection
    And I clear the onboarding popups if present
    And SM I clear all the pinned college
    And SM I search for "Bennett College" college in search bar
    And SM I open the Pinned Schools Compare screen
    And SM I verify all the below options available in Athletics fit criteria in Athletics expandable drawer
      |Associations & Divisions| Levels Available|
      |NCAA (Division III)| Varsity (1 sport)\n0\nFemale (1 sport)|