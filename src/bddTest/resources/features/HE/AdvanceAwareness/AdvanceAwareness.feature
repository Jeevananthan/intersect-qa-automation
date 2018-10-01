@HE
Feature: HE - Upgrade - Upgrade - As an HE user in Intersect, I need to be engaged to perform actions on my subscription Advanced Awareness
         so that I can set Diversity , Configure Audience  etc etc


  @MATCH-4919
  Scenario: As an HE user, I want to configure Advance Awareness
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I click on button Configure for subscription "Advanced Awareness"
    And HE I click on Advance Awareness menu option "Diversity"
    And HE I select following Diversity Settings
    | Asian |
    | Hispanic/Latino of any race |
    And HE I click on Advance Awareness menu option "Competitors"
    And SM I press button "SAVE"
    And HE I click on Advance Awareness menu option "Diversity"
    And HE I verify following options are checked
      | Asian |
      | Hispanic/Latino of any race |
    And HE I select following Diversity Settings
      | Multiracial |
    And HE I click on Advance Awareness menu option "Competitors"
    And SM I press button "Don't save changes"
    And HE I click on Advance Awareness menu option "Diversity"
    And HE I verify following options are unchecked
      | Multiracial |
    #Following will un-check previously checked values
    And HE I select following Diversity Settings
      | Asian |
      | Hispanic/Latino of any race |
    And HE I click on Advance Awareness menu option "Competitors"
    And SM I press button "SAVE"







