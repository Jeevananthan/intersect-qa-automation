@HE
Feature: HE - RepVisits - Calendar - As an HE user I want to use the RepVisits Calendar features.

  @MATCH-4222
  Scenario: As an RepVisits HE admin premium/paid Presence subscription user, I want the ability to see and select which
  visits and college fair registrations I'd like to transfer at the "Re-assign appointments" modal for the selected HE rep,
  so that I can support appropriate appointment transfer as necessary at a single appointment level or in bulk from one
  user to another user.
    Given HE I want to login to the HE app using "purpleheautomation+administrator@gmail.com" as username and "Password!1" as password
    When HS I go to re assign appointments
    Then HS I verify UI components with the option "Publishing, PurpleHE" in the drop down action
    Then HS I verify UI components with the option "Community, PurpleHE" in the drop down action
    And HE I successfully sign out