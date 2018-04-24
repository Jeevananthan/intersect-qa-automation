@SP
Feature:  SP - Global Search - GlobalSearchInstitutionalSearch - Hobsons Staff User - Search for Institutional Accounts
  As a Hobsons staff user I want to be able to search for institutional accounts in the global search bar
  so I can quickly find and take action on a particular account such as viewing or updating product
  subscription information.

  Scenario: As a Hobsons staff user I can start to type the name of an institutional account in the global search bar and results are returned real time
    Given SP I am logged in to the Admin page as an Admin user
    And SP I search for "University" as a HE Institution in the global search box
    Then SP I am presented with all institutional accounts that contain "university" in their names below the global search box
    And SP I successfully sign out
