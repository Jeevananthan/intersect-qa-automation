@SP
Feature: Hobsons Support - View HE Institution Accounts
         As a Hobsons staff user I need to view individual institutional accounts.

  @MATCH-264
  Scenario: As a Hobsons Sales Ops user I can view an institutional account
    Given SP I am logged in to the Admin page as a Sales Ops user
    When SP I click on an institution name
    Then SP I am able to view the individual account page
    Then SP I do not have access to "Community" sub menu in left navigation
    And SP I successfully sign out

  @MATCH-264
  Scenario: As a Hobsons Admin user I can view an institutional account
    Given SP I am logged in to the Admin page as an Admin user
    When SP I click on an institution name
    Then SP I am able to view the individual account page
    And SP I successfully sign out

  @MATCH-264
  Scenario: As a Hobsons Support user I can view an institutional account
    Given SP I am logged in to the Admin page as a Support user
    When SP I click on an institution name
    Then SP I am able to view the individual account page
    And SP I successfully sign out

  @MATCH-958
  Scenario: As a Hobsons Support user I should not activate any module where the end date is earlier than the start date and vice-versa.
    Given SP I am logged in to the Admin page as an Admin user
    When SP I click on an institution name
    Then SP I am able to view the individual account page
    Then SP I verify subscription end date restrictions
    Then SP I verify subscription start date restrictions
    And SP I successfully sign out

  @MATCH-264
  Scenario: As a Hobsons Community user I cannot view an institutional accounts but have access to Community
    Given SP I am logged in to the Admin page as a Community user
    Then SP I do not have access to "Home" sub menu in left navigation
    Then SP I do have access to "Community" sub menu in left navigation
    And SP I successfully sign out

  @MATCH-264
  Scenario: As a Hobsons Community Manager user I cannot view an institutional accounts but have access to Community
    Given SP I am logged in to the Admin page as a Community Manager user
    Then SP I do not have access to "Home" sub menu in left navigation
    Then SP I do have access to "Community" sub menu in left navigation
    And SP I successfully sign out

  @MATCH-650
  Scenario: As a Hobsons staff user I can see the institutional account address, city, state/province, postal code, SCID
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "Docufide Institute of Technology (not a real school)" from the institution dashboard
    Then SP I should see Additional Contact Details on Institutional Account Page
    And SP I successfully sign out

  @MATCH-1670
  Scenario: As a Support Users I can access Hubs view mode
    Given SP I am logged in to the Admin page as an Admin user
    When SP I search for "Bowling Green State University-Main Campus" in "Institutions"
    And SP I select the following institution "Bowling Green State University-Main Campus" from the results
    Then SP I verify Hubs view mode for "Bowling Green State University-Main Campus"
    And SP I successfully sign out

  @MATCH-1895
  Scenario: As an support user I want the Intersect left navigation bar to be better organized and labeled.
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I verify the left navigation bar and section breadcrumbs are as follows
      | Awareness | Counselor Community |
    And SP I successfully sign out


    @MATCH-128
    Scenario: As a Support user with admin privilege or support privilege or sales ops privilege is able to edit
              Activate/Inactivate modules/products within the institutional accounts
      Given SP I am logged in to the Admin page as an Admin user
      Then SP I select "Bowling Green State University-Main Campus" from the institution dashboard
      And SP I set the "Legacy: Hub page management" module to "inactive" in the institution page
      And SP I set the Start Date as "June 13 2017"
      And SP I set the End Date as "June 15 2017"
      And SP I Click the Save Changes button
      Then SP I verify the "status" should be "inactive" for "Legacy: Hub page management"
      Then SP I verify the "Start Date" should be "Jun 13, 2017" for "Legacy: Hub page management"
      Then SP I verify the "End Date" should be "Jun 15, 2017" for "Legacy: Hub page management"
      And SP I set the "Legacy: Hub page management" module to "active" in the institution page
      And SP I set the Start Date as "June 13 2017"
      And SP I set the End Date as "June 13 2017"
      And SP I Click the Save Changes button
      Then SP I verify the "status" should be "inactive" for "Legacy: Hub page management"
      And SP I successfully sign out

