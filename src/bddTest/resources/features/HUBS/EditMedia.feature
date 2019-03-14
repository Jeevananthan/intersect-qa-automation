@HUBS
Feature: HUBS - Edit Media - HS - Edit Media - As an HS user, I should be able to Edit User Profile
  Background:
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HUBS I access HUBS Edit Mode
    Then HUBS I open the "Overview" tab in the preview

  @HUBS-5176
  Scenario: As an HE user with either the Publishing or Administrator role tied to an HE account with the Awareness
  subscription activated, I want to be able to manage my institution's links so I do not have to rely on
  Intersect Support to do this for me.
    When HUBS I click on "LINKS & PROFILES" tab
    Then HUBS I expanded the "Links" link
    Then HUBS verify contents "COMMUNICATE" into "Links" section
    Then HUBS verify contents "APPLY ONLINE" into "Links" section
    Then HUBS verify contents "LEARN MORE" into "Links" section

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
    Then HUBS I verify contents "Photos and Videos" and "Add up to 31 photos and videos." in Media tab
    Then HUBS I verify Arrows and Slots in the Photos and Videos panel

  @HUBS-5791
  Scenario: As an HE user with either the Publishing or Administrator role tied to an HE account with the Awareness
            subscription activated, I want to be able to manage my institution's college profile (about us area) so
            I do not have to rely on Intersect Support to do this for me.
    Given HE I am logged in to Intersect HE as user type "HEMadministrator"
    And HUBS I access HUBS Edit Mode
    When HUBS I click on "MEDIA" tab
    And HUBS I click on "INTRO" tab
    Then HUBS I verify contents "About Us" in Intro tab

    Given HE I am logged in to Intersect HE as user type "HEMadmin"
    And HUBS I access HUBS Edit Mode
    When HUBS I click on "MEDIA" tab
    And HUBS I click on "INTRO" tab
    Then HUBS I verify "Premium Features" Lock
    Then HUBS I verify the Upgrade Modal

  @HUBS-5758
  Scenario: As an HE user with either the Publishing or Administrator role, I want to see my existing 'Links & Profiles'
            premium HEM content in the right area of the React app so I can see what currently exists on my College's
            Naviance profile.
    When HUBS I click on "LINKS & PROFILES" tab
    Then HUBS I expanded the "Links" link
    Then HUBS verify title "Links & Profiles" into "Title Links" section
    Then HUBS verify title "Links" into "Title Links" section
    Then HUBS verify contents "COMMUNICATE" into "Links" section
    Then HUBS verify contents "APPLY ONLINE" into "Links" section
    Then HUBS verify contents "LEARN MORE" into "Links" section
    Then HUBS verify contents "Request Information" in the dropdown "COMMUNICATE"
    Then HUBS verify contents "lttest.tt" in the dropdown "LEARN MORE"

  @HUBS-5177
  Scenario Outline: As an HE user with either the Publishing or Administrator role tied to an HE account with the Intersect Awareness
           Subscription activated, I want to be able to manage my institution's Request Information & Apply Online links so I
           do not have to rely on Intersect Support to do this for me.
    When HUBS I click on "LINKS & PROFILES" tab
    Then HUBS I expanded the "Links" link
    Then HUBS I click on PUBLISH MY LINKS & PROFILES CHANGES editing  "<URL>" new URL
    Then HUBS I click on Submit Changes
    Then HUBS I click on Continue editing link
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HUBS I access HUBS Edit Mode
    When HUBS I click on "LINKS & PROFILES" tab
    Then HUBS I expanded the "Links" link
    Then HUBS I verify "Request Information" input box with URL "http://gobama.ua.edu/contact/"
    Then HUBS I validate URL for "Request Information"
    Examples:
      | URL|
      | https://gobama.ua.edu/contact/|
      | www.gobama.ua.edu/contact/|
      | http://gobama.ua.edu/contact/|
      | ://gobama|

  @HUBS-5233
  Scenario: As an HE user with either the Publishing or Administrator role tied to an HE account with the Intersect
  Awareness Subscription activated, I want to see my institution's current profiles in HEM so I can determine if I want
  to update them or not. & Profiles'  premium HEM content in the right area of the React app so I can see what currently
  exists on my College's  Naviance profile.
    When HUBS I click on "LINKS & PROFILES" tab
    Then HUBS I expanded the "Profiles" link
    Then HUBS I verify "Student Profile" profile


  @HUBS-5232
  Scenario: As an HE user with either the Publishing or Administrator role tied to an HE account with the Awareness
  subscription activated, I want to be able to remove links within Premium HEM so I do not have to rely on Intersect
  Support to do this for me.
    When HUBS I click on "LINKS & PROFILES" tab
    Then HUBS I expanded the "Links" link
    Then HUBS I verify "Student Profile" profile
    Then HUBS I create a new "http://test.org" link with "Test" title
    Then HUBS I verify Remove link

  @HUBS-5759
  Scenario: As an HE user with either the Publishing or Administrator role, I want to see my existing Profiles premium
  HEM content in the right area of the 'Links & Profiles' tab so I can see what currently exists on my College's
  Naviance profile.
    When HUBS I click on "LINKS & PROFILES" tab
    Then HUBS I expanded the "Profiles" link
    Then HUBS I verify "Student Profile" profile
    Then HUBS I verify the "READ MORE" button
    Given HE I am logged in to Intersect HE as user type "HEMadmin"
    And HUBS I access HUBS Edit Mode
    When HUBS I click on "LINKS & PROFILES" tab
    Then HUBS I verify "Premium Features" Lock
    Given HE I am logged in to Intersect HE as user type "HEMnoprofile"
    And HUBS I access HUBS Edit Mode
    When HUBS I click on "LINKS & PROFILES" tab
    Then HUBS I verify no Profiles "No profiles created" in Premium

