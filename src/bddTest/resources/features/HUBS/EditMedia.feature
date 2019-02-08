@HUBS
Feature: HUBS - Edit Media
  Background:
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HUBS I access HUBS Edit Mode
    Then HUBS I open the "Overview" tab in the preview

  @HUBS-5739
  Scenario: As an HE user with the Publishing or Administrator role I want to be able to publish my Premium HEM (React)
            updates so my Naviance College Profile can stay up-to-date.
    When HUBS I click on "MEDIA" tab
    And HUBS I click on "Publish my media changes" button
    Then HUBS I verify the Publishing Modal Confirmation with the "You’re almost done!" element
    Then HUBS I verify the Publishing Modal Confirmation with the "The approval process can take up to 24-48 hours." element
    Then HUBS I verify the Publishing Modal Confirmation with the "Please provide a brief explanation for the changes:" element
    Then HUBS I verify the Publishing Modal Confirmation with the "Cancel and continue editing" element
    Then HUBS I verify the Publishing Modal Confirmation with the "Submit changes" element
    Then HUBS I verify the Publishing Modal Confirmation with the "IE: We've moved the admissions office!" element
    Then HUBS I verify functionality of Publishing Modal Confirmation

  @HUBS-5787
  Scenario: Currently, an HE user can find an 'edit' button from their Hubs 'view' page that, when mashed, takes the HE
  user to our old version of HEM (feature flag not passed in URL).
    Given HE I am logged in to Intersect HE as user type "administrator"
    When I go to institution page
    And I click on Additional Info
    And I click on VIEW NAVIANCE COLLEGE PROFILE
    And HUBS I click on EDIT button
    Then HUBS I verify that HEM loads

  @HUBS-5755
  Scenario: As an HE user with either the Publishing or Administrator role, I want to see my existing 'Media' premium
            HEM content in the right area of the React app so I can see what currently exists on my College's Naviance profile.
    When HUBS I click on "MEDIA" tab
    Then HUBS I verify contents "hotos and Videos" and "Add up to 31 photos and videos." in Media tab
    Then HUBS I verify Arrows and Slots in the Photos and Videos panel