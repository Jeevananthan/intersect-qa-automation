@HUBS
Feature: As a community user viewing College Hubs, I want to be able to view Hubs Tab content so I can
  understand what Hubs offers students.

  @HUBSStudies
  Scenario: All the elements of the page are displayed for HE users in Studies
    Given HE I want to login to the HE app using "jorgetesthobsons@gmail.com" as username and "Hobsons2016!" as password
    And HUBS I access HUBS Edit Mode
    Then HUBS I open the "Studies" tab in the preview
    Then HUBS All the elements of the studies tab should be displayed
    And HE I successfully sign out