@SP
Feature: Hobsons Support - View Institution Log History
         As a Hobsons Staff Administrator or Support user I need to be able to view and filter by date an audit log of all other Hobsons Staff
         activity per institutional account in the admin page for system security, auditing, and troubleshooting.

  Scenario: As a Hobsons Sales Ops user I cannot view an Institution's Log History
    Given SP I am logged in to the Admin page as a Sales Ops user
    When SP I select "The University of Alabama" from the institution dashboard
    And SP I am able to view the individual account page
    Then SP I do not have access to View Log History
    And SP I successfully sign out

  Scenario: As a Hobsons Support user I can view an Institution's Log History
    Given SP I am logged in to the Admin page as a Support user
    When SP I select "The University of Alabama" from the institution dashboard
    And SP I am able to view the individual account page
    Then SP I do have access to View Log History
    And SP I successfully sign out

  Scenario: As a Hobsons Admin user I can view an Institution's Log History
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I go to the Log History for "Bowling Green State University-Main Campus" from the institution dashboard
    And SP I successfully sign out