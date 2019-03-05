@HUBS
Feature: HUBS - Naviance College Profile Home page, which have mainly 3 tabs ie Information, Media and Links & Profiles.

  Background:
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HUBS I access HUBS Edit Mode

  @MATCH-5584 @MATCH-5585
  Scenario: As an HE user with the Administrator or Publishing role I want to see multiple 'tabs' in HEM that will
  separate the two kinds of HEM content (data vs premium content) so Premium HEM features can be built in React
  versus Angular
    Then HUBS all three tabs ie "BASIC INFO", "INTRO", "MEDIA" and "LINKS & PROFILES" should display
    Then HUBS I click on "MEDIA" tab in main menu
    And HUBS I check "MEDIA" tab functionality

  @MATCH-5587 @MATCH-5840
  Scenario: As an HE user with the Administrator or Publishing role I need to be able to navigate between different
  tabs of HEM seamlessly so I can manage my institution's profile content.
    Then HUBS I click on "LINKS & PROFILES" tab in main menu
    And HUBS I check "LINKS & PROFILES" tab functionality
    And HUBS I click on Links link
    And HUBS I update Reguest Information field
    And HUBS I click on Intersect
    And HUBS I verify the Publish model for "LINKS & PROFILES" tab

  @MATCH-5701
  Scenario: As an HE user with the Administrator or Publishing role that has made 'Media' or 'Links & Profiles'
  updates to my institution's profile in HEM (React) but has not published those updates from within either tab,
  I want to be notified that I need to publish my changes any time I leave HEM (React) to the 'Information' tab of
  HEM (Angular) or elsewhere in Intersect
    Then HUBS I click on "MEDIA" tab in main menu
    And HUBS I select an image for logo
    And HUBS I click on "BASIC INFO" tab in main menu
    And HUBS I verify the Publish model for "MEDIA" tab

  @MATCH-5897
  Scenario: As an HE user with the Administrator or Publishing role I want an 'Intro' tab in Premium HEM so I can
  manage my institution's 'college profile' without Support needing to get involved.
    Then HUBS I click on "MEDIA" tab in main menu
    Then HUBS I click on "INTRO" tab in main menu
    And HUBS I check "INTRO" tab functionality
