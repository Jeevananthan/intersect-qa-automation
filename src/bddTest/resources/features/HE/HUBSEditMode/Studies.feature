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

    @MATCH-1044
    Scenario: As a HE user with admin role or publishing role can able to access the HUB edit mode page in HE app.
              Other roles such as community role could not have access to the HUB edit mode page.
      Given HE I am logged in to Intersect HE as user type "administrator"
      And HUBS I access HUBS Edit Mode
      And HE I successfully sign out
      Given HE I am logged in to Intersect HE as user type "publishing"
      And HUBS I access HUBS Edit Mode
      And HE I successfully sign out
      Given HE I am logged in to Intersect HE as user type "community"
      And HE I verify the "Naviance college profile" nav link is not displaying for this user
      And HE I successfully sign out

