@SP
Feature: Hobsons Staff - Search for Institutional Accounts via SCID
  As a Hobsons staff member I need to search for institutional accounts by their full SCID in the Purple Admin
  page so I know I am provisioning (for the first time) the correct account.

  Scenario: As a Hobsons purple admin user I want to search for institutional accounts via the full SCID in the search box in the admin page.
    Given SP I am logged in to the Admin page as an Admin user
    When SP I search for "2100209"
    Then SP I am able to see "Bowling Green State University-Main Campus" institution in the results
    And SP I successfully sign out

  Scenario: As a Hobsons purple support user I want to search for institutional accounts via the full SCID in the search box in the admin page.
    Given SP I am logged in to the Admin page as a Support user
    When SP I search for "1100522"
    Then SP I am able to see "Gettysburg College" institution in the results
    And SP I successfully sign out

  Scenario: As a Hobsons purple sales user I want to search for institutional accounts via the full SCID in the search box in the admin page.
    Given SP I am logged in to the Admin page as a Sales Ops user
    When SP I search for "1100545"
    Then SP I am able to see "Indiana University Bloomington" institution in the results
    And SP I successfully sign out

   Scenario: As a Hobsons purple admin user I want a purple account to be created when I click on a college core institution
     Given SP I am logged in to the Admin page as an Admin user
     When SP I search for "2100209"
     And SP I select the following institution "Bowling Green State University-Main Campus" from the results
     #Then SP I go to the institution dashboard and make sure "Bowling Green State University-Main Campus" is on the dashboard
     And SP I successfully sign out

  Scenario: As a Hobsons purple support user I want a purple account to be created when I click on a college core institution
    Given SP I am logged in to the Admin page as a Support user
    When SP I search for "1100522"
    And SP I select the following institution "Gettysburg College" from the results
    #Then SP I go to the institution dashboard and make sure "Gettysburg College" is on the dashboard
    And SP I successfully sign out

  Scenario: As a Hobsons purple sales user I want a purple account to be created when I click on a college core institution
    Given SP I am logged in to the Admin page as a Sales Ops user
    When SP I search for "1100545"
    And SP I select the following institution "Indiana University Bloomington" from the results
    #Then SP I go to the institution dashboard and make sure "Indiana University Bloomington" is on the dashboard
    And SP I successfully sign out
