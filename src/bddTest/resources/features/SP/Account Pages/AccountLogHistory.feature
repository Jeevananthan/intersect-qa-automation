@SP
Feature: Hobsons Support - View Institution Log History
         As a Hobsons Staff Administrator or Support user I need to be able to view and filter by date an audit log of all other Hobsons Staff
         activity per institutional account in the admin page for system security, auditing, and troubleshooting.

  Scenario: As a Hobsons Sales Ops user I cannot view an Institution's Log History
    Given SP I am logged in to the Admin page as a Sales Ops user
    When SP I click on an institution name
    And SP I am able to view the individual account page
    Then SP I do not have access to View Log History
    And SP I successfully sign out

  Scenario: As a Hobsons Support user I can view an Institution's Log History
    Given SP I am logged in to the Admin page as a Support user
    When SP I click on an institution name
    And SP I am able to view the individual account page
    Then SP I do have access to View Log History
    And SP I successfully sign out

  Scenario: As a Hobsons Admin user I can view an Institution's Log History
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I go to the Log History for "The University of Alabama" from the institution dashboard
    And SP I successfully sign out

  @MATCH-191
  Scenario: As a Support user - I need to be able to view and filter by date an audit log
    Given SP I am logged in to the Admin page as a Support user
    When SP I click on an institution name
    And SP I go to the Log History for "Bowling Green State University-Main Campus" from the institution dashboard
    Then SP I select "Today" from the Log History filter
    And SP I verify the Log History correctly shows records from "Today"
    Then SP I select "Yesterday" from the Log History filter
    And SP I verify the Log History correctly shows records from "Yesterday"
    Then SP I select "Last 7 days" from the Log History filter
    And SP I verify the Log History correctly shows records from "Last 7 days"
    Then SP I select "Last 30 days" from the Log History filter
    And SP I verify the Log History correctly shows records from "Last 30 days"
    Then SP I select "Custom" from the Log History filter
    And SP I verify the Log History correctly shows records from "Custom"
    And SP I verify that Start and End Date are displayed in the Log History filter after choosing 'Custom'
    Then SP I select "Last Week" from the Log History filter
    And SP I verify the Log History correctly shows records from "Last Week"
    Then SP I select "Last Month" from the Log History filter
    And SP I verify the Log History correctly shows records from "Last Month"
    And SP I successfully sign out
