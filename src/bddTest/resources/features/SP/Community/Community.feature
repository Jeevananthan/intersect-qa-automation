@SP
Feature: As a Support User, I want to be able to access HUBS View Mode

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