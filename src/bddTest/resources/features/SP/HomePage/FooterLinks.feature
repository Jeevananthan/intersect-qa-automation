@SP
Feature: SP - HomePage - FooterLinks - Ability to access Privacy Policy and Terms of Use

  @MATCH-1430
  Scenario: As an Intersect system, I need to have a Privacy Policy page, a Terms of Use page
            available for users to read and understand how they are supposed to used the system and how the system uses their information.
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I verify "Terms of Service" is present in the footer
    Then SP I verify "Privacy Policy" is present in the footer
    Then SP I navigate to each page and verify the unique URL is present in the "Terms of Service" page
    Then SP I navigate to each page and verify the unique URL is present in the "Privacy Policy" page
    Then SP I successfully sign out

  @MATCH-3563
  Scenario:As a Support user, I verify the Copyright information
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I verify the current year is displayed at the bottom of the window in the Home Page
    Then SP I successfully sign out

