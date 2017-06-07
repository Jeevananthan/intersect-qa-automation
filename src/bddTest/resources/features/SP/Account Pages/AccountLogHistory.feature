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
    And SP I go to the Log History for "University of Montevallo" from the institution dashboard
    Then SP I Select the Log history Filter option as "Today"
    And SP I verify the Log history filter option is selected as "Today"
    And SP I verify the Log history results for the option "Today"
    Then SP I Select the Log history Filter option as "Yesterday"
    And SP I verify the Log history filter option is selected as "Yesterday"
    And SP I verify the Log history results for the option "Yesterday"
    Then SP I Select the Log history Filter option as "Last 7 days"
    And SP I verify the Log history filter option is selected as "Last 7 days"
    And SP I verify the Log history results for the option "Last 7 days"
    Then SP I Select the Log history Filter option as "Last 30 days"
    And SP I verify the Log history filter option is selected as "Last 30 days"
    And SP I verify the Log history results for the option "Last 30 days"
    Then SP I Select the Log history Filter option as "Custom"
    And SP I verify the Log history filter option is selected as "Custom"
    And SP I verify the Start and End Date is displayed after choosing custom option
    Then SP I Select the Log history Filter option as "Last Week"
    And SP I verify the Log history filter option is selected as "Last Week"
    Then SP I Select the Log history Filter option as "Last Month"
    And SP I verify the Log history filter option is selected as "Last Month"
    And SP I successfully sign out
