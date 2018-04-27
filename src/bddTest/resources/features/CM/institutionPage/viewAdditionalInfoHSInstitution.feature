@MATCH-914
Feature: Community User - View Additional Information on HS Institutions
  As a Community user or institution while viewing an HS institution within the Community I want to see additional
  information and data on the institution so I can learn more about that insitution and their admissions profile.


  Scenario: As a Community user or institution while viewing an HS institution within the Community I want to see additional information.
    Given HS I am logged in to Intersect HS as user type "default"
    And I go to HS institution page
    And I click on Additional Info
    Then I check items on the Lebanon High School institution additional info
    And HE I successfully sign out
