@SP
Feature: SP - GlobalSearch - GlobalSearchSCIDSearch - Ability to search for Institutional Accounts by SCID
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


  @MATCH-5074
  Scenario: As a Hobsons purple sales user I want to search for institutional accounts via the full SCID in the search box in the admin page.
    Given SP I am logged in to the Admin page as a Sales Ops user
    When SP I search for "2400006"
    Then SP I am able to see "The University of Alabama" institution in the results
    And SP I successfully sign out