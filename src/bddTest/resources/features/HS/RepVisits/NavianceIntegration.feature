@HS @HS2
Feature: HS - RepVisits - NavianceIntegration -  As an HS user, I should be able to setup and sync visits to Naviance

  @MATCH-1625 @MATCH-1958 @MATCH-1943
  Scenario: As a high school counselor using Naviance and RepVisits,  I want to integrate my RepVisits account with Naviance college visits so that I do not have to manually enter appointments.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I verify the Naviance Settings section of the Availability & Settings tab of the RepVisits page
    Then HS I verify the success message after save the changes

  @MATCH-3771
  Scenario: As a HS admin user that also has Naviance, I want to see the proper product name for Naviance Student (formerly Family Connection) on the Naviance Settings and Naviance Sync wizard pages in RepVisits.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I complete the setup wizard
    Then HS I navigate to the Naviance Settings page through the setup Wizard
    And HS I verify that the connecting naviance and repvisits page contains the text: "If a visit is rescheduled or cancelled in RepVisits, it will automatically update in Naviance College Visits and Naviance Student."
    And HS I verify that the naviance sync settings page contains the text: "To be displayed in Naviance Student when browsing and signing up."