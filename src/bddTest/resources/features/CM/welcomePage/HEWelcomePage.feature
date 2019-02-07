@HE
Feature: HE - Community - Welcome - As an HE User, Prepopulate Community User Profile Fields Upon Activation

  @MATCH-1799 @MATCH-4609
  Scenario: As a Hobsons staff wanting to ensure data security compliance for Intersect users, I want to ensure that all
            users coming through intersect including all subscription levels and HE user is required to complete the Counselor Community profile page,
            So that I can ensure that all users coming into Intersect have consented to terms, account creation, and specified whether they're an EU citizen.
    #Cleanup steps
    Then HE I am logged in to Intersect HE as user type "resetAccount"
    And HE I go to the Counselor Community
    And HE I clear the account to get the community welcome page again
    Then HE I successfully sign out
    # Testcase
    Then HE I am logged in to Intersect HE as user type "resetAccount"
    When HE I verify that I am redirected to the Community activate profile page when accessing RepVisits
    Then HE I verify the new user required to complete the Counselor Community profile form before they can access the following fields
      |Counselor Community|RepVisits|ActiveMatch|
    When HE I go to the Welcome Counselor Community poage
    And HE I verify the Welcome page has a header that says: "Please take a moment to complete the form below to activate your profile. This is the first step in utilizing Intersect solutions, including updating your Naviance college profile and accessing RepVisits. Based on your institution's partnership with Intersect, you may also have access to Counselor Community, Naviance Events, and Active Match. Once this step is complete you will be ready to start using Intersect."
    Then HE I verify the following fields are required fields in the Counselor Community profile form
      |Job Title field is required.|Office Phone field is required.|Privacy Policy|Are you an EU citizen? field is required.|Terms of Use|Community Guidelines|
    And HE I activate my community profile by providing OfficePhone as "1234567892" JobTitle as "Counselor" and EU citizen as "Yes"
    And HE I verify clicking on RepVisits will redirect to Search and Schedule tab of RepVisits
    Then HE I verify the user can access the following fields
      |Counselor Community|RepVisits|ActiveMatch|
    And HE I clear the account to get the community welcome page again