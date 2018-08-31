@SP
Feature: SP - Community - Community - Verify access to Community and HUBS view mode

  @MATCH-1907
  Scenario: Support user cannot access Hubs View mode
    Given SP I am logged in to the Admin page as a Support user
    When SP I search for "Bowling Green State University-Main Campus" as an Institution in the global search box
    And SP I select "Bowling Green State University-Main Campus" from the global search results
    Then SP I verify Hubs view mode for "Bowling Green State University-Main Campus"
    And SP I successfully sign out

  @MATCH-3007
  Scenario: As a Support user with the 'Super Administrator' role I need to be able to access Community so I can create my profile and network with other users.
    Given SP I am logged in to the Admin page as a Super Admin user
    Then SP I verify the user can successfully access Counselor Community from within the Support App
    Then SP I verify the user can access the following sub tabs in the Counselor Community
      |Home|Profile|Institution|Connections|Groups|
    Then SP I verify the user tied to the Hobsons institution
    And SP I successfully sign out

    @MATCH-4371
    Scenario: As a Support user, I need the ability to add a state subscription so that I can configure the correct subscription for the HE client.
      Given SP I am logged in to the Admin page as a Super Admin user
      When SP I select "The University of Alabama" from the institution dashboard


      Then HE I click the link "Connection"
#      Here I need a step to get current number of records
      Then SM I press button "ADD NEW SUBSCRIPTION"
      Then SM I press button "State"
      Then SM I press button "Next"
      Then I check if I can see "Add New Subscription" on the page
      Then I check if I can see " - The University of Alabama" on the page
      Then I check if I can see "Choose State(s)" on the page
      Then I check if I can see "Choose Diversity Filter" on the page
      Then I check if I can see "Choose Naviance Engagement" on the page
      Then I check if I can see "Select Competitors" on the page
      Then I check if I can see "Start and End Dates" on the page
      Then SM I verify that checkBox with text "Majors" can be checked|unchecked
      Then SM I verify that checkBox with text "Connection" can be checked|unchecked
      Then SM I pick "Female" from the dropdown ".custom-rounded-dropdown"
      Then SM I pick "Arizona" from the dropdown ".custom-dropdown"
      Then SM I press button "Select date"
      Then SM I pick the date "01/01/18" from the date picker
      Then SM I press button "Finish"
#      Here I need step to verify that one more record was added


      And SP I successfully sign out
#        | University                                 | HubsModule                  | CommunityModule   | IntersectAwarenessModule         | IntersectPresenceModule         | AdvancedAwarenessModule | ConnectionModule | StartDate | EndDate | Inactive | Active |
#        | Bowling Green State University-Main Campus | Legacy: Hub page management | Legacy: Community | Intersect Awareness Subscription | Intersect Presence Subscription | Advanced Awareness      | Connection       | 0         | 35      | inactive | active |