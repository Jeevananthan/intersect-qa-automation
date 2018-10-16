@MATCH-912
Feature: Community User - View Additional Information on HE Institutions
  As a Community user or institution while viewing an HE institution within the Community I want to see additional
  information and data on the institution so I can learn more about that insitution and their admissions profile.


  Scenario: As a Community user or institution while viewing an HE institution within the Community I want to see additional information.
    Given HE I am logged in to Intersect HE as user type "administrator"
    And I go to institution page
    And I click on Additional Info
    Then I check items on the Alabama's institution additional info