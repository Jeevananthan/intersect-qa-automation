@HS
Feature:  As an HS user, I want to be able to access the features of the Sync Opt in features.


  @MATCH-3462
  Scenario: As a RepVisits HS user that is interested in opting in to connect events with Naviance, I want the copy on
  the screen to clearly provide me with information on my ability to opt in/out of the publish connection,
  so that I know what the implications are for connecting and whether I can disconnect the sync.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I navigate to the Naviance Settings page through the setup Wizard
    And HS I verify the UI of the Naviance Settings Page in setup wizard
    And HS I successfully sign out