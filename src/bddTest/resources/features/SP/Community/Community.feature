@SP
Feature: SP - Community - Community - Verify access to Community and HUBS view mode

  @MATCH-1907
  Scenario: Support user cannot access Hubs View mode
    Given SP I am logged in to the Admin page as a Support user
    When SP I search for "Bowling Green State University-Main Campus" as an Institution in the global search box
    And SP I select "Bowling Green State University-Main Campus" from the global search results
    Then SP I verify Hubs view mode for "Bowling Green State University-Main Campus"

  @MATCH-3007
  Scenario: As a Support user with the 'Super Administrator' role I need to be able to access Community so I can create my profile and network with other users.
    Given SP I am logged in to the Admin page as a Super Admin user
    Then SP I verify the user can successfully access Counselor Community from within the Support App
    Then SP I verify the user can access the following sub tabs in the Counselor Community
      |Home|Profile|Institution|Connections|Groups|
    Then SP I verify the user tied to the Hobsons institution

    @MATCH-4371
    Scenario: As a Support user, I need the ability to add a state subscription so that I can configure the correct subscription for the HE client.
      Given SP I am logged in to the Admin page as a Super Admin user
      When SP I select "The University of Alabama" from the institution dashboard
      Then HE I click the link "Connection"
      Then I check number of records in the ".subscriptions-table" table
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
      Then SM I pick the date "01/01/19" from the date picker
      Then SM I press button "Finish"
      Then I check that table ".subscriptions-table"  has one more row

  @MATCH-4372
  Scenario: As a Support user, I need the ability to add a county subscription so that I can configure the correct subscription for the HE client.
    Given SP I am logged in to the Admin page as a Super Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    Then HE I click the link "Connection"
    Then I check number of records in the ".subscriptions-table" table
    Then SM I press button "ADD NEW SUBSCRIPTION"
    Then SM I press button "County"
    Then SM I press button "Next"
    Then I check if I can see "Add New Subscription" on the page
    Then I check if I can see " - The University of Alabama" on the page
    Then I check if I can see "Choose State(s)" on the page
    Then I check if I can see "Choose Diversity Filter" on the page
    Then I check if I can see "Choose Naviance Engagement" on the page
    Then I check if I can see "Select Competitors" on the page
    Then I check if I can see "Start and End Dates" on the page
    Then SM I pick "Male" from the dropdown ".custom-rounded-dropdown"
    Then SM I pick "Arizona" from the dropdown ".custom-dropdown"
    Then SM I press button "Select date"
    Then SM I pick the date "01/01/19" from the date picker
    Then SM I verify that checkBox with text "Majors" can be checked|unchecked
    Then SM I verify that checkBox with text "Connection" can be checked|unchecked
    Then SM I pick "Coconino County" from the dropdown "[name='counties.Arizona']"
    Then SM I press button "Finish"
    Then I check that table ".subscriptions-table"  has one more row

  @MATCH-4373
  Scenario: As a Support user, I need the ability to add a zip subscription so that I can configure the correct subscription for the HE client.
    Given SP I am logged in to the Admin page as a Super Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    Then HE I click the link "Connection"
    Then I check number of records in the ".subscriptions-table" table
    Then SM I press button "ADD NEW SUBSCRIPTION"
    Then SM I press button "Zip"
    Then SM I press button "Next"
    Then I check if I can see "Add New Subscription" on the page
    Then I check if I can see " - The University of Alabama" on the page
    Then I check if I can see "Choose Zip(s)" on the page
    Then I check if I can see "Radius from Zip(s)" on the page
    Then I check if I can see "Choose Naviance Engagement" on the page
    Then I check if I can see "Select Competitors" on the page
    Then I check if I can see "Start and End Dates" on the page
    Then I send text "40001" to the field "field21"
    Then I send text "80" to the field "field22"
    Then SM I verify that checkBox with text "Majors" can be checked|unchecked
    Then SM I verify that checkBox with text "Connection" can be checked|unchecked
    Then SM I press button "Select date"
    Then SM I pick the date "01/01/19" from the date picker
    Then SM I press button "Finish"
    Then I check that table ".subscriptions-table"  has one more row

  @MATCH-4371 @MATCH-4372 @MATCH-4373
  Scenario Outline: As a Support user, I need to see error messages when required fields are not set while adding subscription.
    Given SP I am logged in to the Admin page as a Super Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    Then HE I click the link "Connection"
    Then I check number of records in the ".subscriptions-table" table
    Then SM I press button "ADD NEW SUBSCRIPTION"
    Then SM I press button "<subscription>"
    Then SM I press button "Next"
    Then SM I press button "Finish"
    Then I check there are <numberOfErrorMessages> icons ".ui.red.pointing.label" are displayed
    Examples:
    |subscription|numberOfErrorMessages|
    |State       |3                    |
    |County      |3                    |
    |Zip         |4                    |