@HS @HS2
Feature: HS - RepVisits - NavianceIntegration -  As an HS user, I should be able to setup and sync visits to Naviance

  @MATCH-1625 @MATCH-1958 @MATCH-1943
  Scenario: As a high school counselor using Naviance and RepVisits,  I want to integrate my RepVisits account with Naviance college visits so that I do not have to manually enter appointments.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I verify the Naviance Settings section of the Availability & Settings tab of the RepVisits page
    Then HS I verify the success message after save the changes
    And HS I successfully sign out
