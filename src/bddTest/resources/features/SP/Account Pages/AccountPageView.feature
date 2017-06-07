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
