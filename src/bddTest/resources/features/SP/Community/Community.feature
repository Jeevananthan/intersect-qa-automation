@SP
Feature: As a Support User, I want to be able to access HUBS View Mode

  @MATCH-1907
  Scenario: Support user cannot access Hubs View mode
    Given SP I am logged in to the Admin page as a Support user
    When SP I search for "Bowling Green State University-Main Campus" as an Institution in the global search box
    And SP I select "Bowling Green State University-Main Campus" from the global search results
    Then SP I verify Hubs view mode for "Bowling Green State University-Main Campus"
    And SP I successfully sign out

  @MATCH-4138
  Scenario: As a super admin and admin role in the Support app of Intersect,
            I want the ability to add and see current published/unpublished Intersect in-product notifications in the support app,
            So that management is centralized for me at the Admin Dashboard in the support app.
    Given SP I am logged in to the Admin page as an Admin user
    And SP I un-publish all the published announcements
    When SP I add a new product announcement with title "" content "ContentTest" audience "HE" and status "Unpublished"
    Then SP I verify the toast with the message "New announcement added" is displayed
    Then SP I verify title "Untitled" is displayed in the Product Announcements page
    Then SP I verify "140" characters are displayed in the Product Announcements page
    Then SP I verify the visibility for the following details "HE" in the Product Announcements page using "14"
    Then SP I verify the date format "mmm d" in the Product Announcements page using "14" for the user "Match Support UI QA4"
    And SP I verify the status "" is displayed in the Product Announcements page using "14"
    Then SP I verify "" button for more than 25 notifications
    And SP I successfully sign out