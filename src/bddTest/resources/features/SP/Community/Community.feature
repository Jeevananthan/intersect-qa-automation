@SP
Feature: As a Support User, I want to be able to access HUBS View Mode

  @MATCH-1907
  Scenario: Support user cannot access Hubs View mode
    Given SP I am logged in to the Admin page as a Support user
    When SP I search for "Bowling Green State University-Main Campus" as an Institution in the global search box
    And SP I select "Bowling Green State University-Main Campus" from the global search results
    Then SP I verify Hubs view mode for "Bowling Green State University-Main Campus"
    And SP I successfully sign out

  @MATCH-3904
  Scenario: As a super admin and admin role in the Support app of Intersect,
            I want the ability to have a location within the support app that allows me to create Intersect in-product notifications,
            So that when there are any important notifications (e.g. maintenance, product survey's, release notifications, etc.) related to the Intersect product line,
            I have the ability to directly notify users within the application and manage such from the Support app.
#Verify admin dashboard is displayed
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I verify that Admin dashboard is displayed in Homepage
    Then SP I verify the header is changed from "Intersect" to "Admin Dashboard" in the Admin dashboard page
    Then SP I verify "Product Announcements" stub menu is displayed in the Admin dashboard page
    And SP I successfully sign out
#Verify admin dashboard is not displayed
    When SP I am logged in to the Admin page as a Community Manager user
    And SP I verify that Admin dashboard is not displayed
    And SP I successfully sign out
    When SP I am logged in to the Admin page as a Community user
    And SP I verify that Admin dashboard is not displayed
    And SP I successfully sign out
    When SP I am logged in to the Admin page as a Sales Ops user
    And SP I verify that Admin dashboard is not displayed
    And SP I successfully sign out
    When SP I am logged in to the Admin page as a Support user
    And SP I verify that Admin dashboard is not displayed
    And SP I successfully sign out

