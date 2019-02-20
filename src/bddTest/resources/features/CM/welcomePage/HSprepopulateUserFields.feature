@CM
Feature: HS Community User - Prepopulate Community User Profile Fields Upon Activation

  As an HS Community user I want my Naviance Succeed user account data to prepopulate my Community user profile upon
  initial activation so I don't have to re-enter information Naviance already knows about me.

  @MATCH-486 @ignore
  Scenario: As an HS Community user I want my Naviance Succeed user account details to autopopulate my Community user profile when initially activated.
    Given HS I am logged in to Intersect HS as user type "default"
    And I am sure that HS user will be logged in for the first time and HS Welcome page will be opened
    And I go to HS Counselor Community page
    #Then I upload Profile and Banner pictures
    And I populate all the fields on Welcome page
    Then I set work email and office phone privacy to 'Connections Only'
    And I set personal email and mobile phone privacy to 'Visible to Only Me'
    And I set the EU citizen to "Yes"
    And I accept Terms and conditions
    And I consent to create and maintain my Intersect account
    And I Save changes
    Then I click on Edit profile button
    And I check if privacy settings are saved properly

  @MATCH-1799 @MATCH-4609 @MATCH-4468
  Scenario: As a Hobsons staff wanting to ensure data security compliance for Intersect users,
  I want to ensure that all users coming through intersect including all subscription levels and HS user is required to complete the Counselor Community profile page,
  So that I can ensure that all users coming into Intersect have consented to terms, account creation, and specified whether they're an EU citizen.
#Non-Naviance
#Cleanup steps
    Given HS I am logged in to Intersect HS as user type "resetAccount"
    And HS I go to the Counselor Community
    And I clear the account to get the community welcome page again
    Then HS I successfully sign out

    Given HS I am logged in to Intersect HS as user type "resetAccount"
    When I verify that I am redirected to the Community activate profile page when accessing RepVisits
    Then I verify the new user required to complete the Counselor Community profile form before they can access the following fields
      |Counselor Community|RepVisits|
    And I go to the Welcome Counselor Community page
    And HE I verify the Welcome page has a header that says: "Please take a moment to complete the form below to activate your profile. This is the first step in utilizing Intersect solutions, including updating your Naviance college profile and accessing RepVisits. Based on your institution's partnership with Intersect, you may also have access to Counselor Community, Naviance Events, and Active Match. Once this step is complete you will be ready to start using Intersect."
    And HE I verify the agreements label that says: "Please confirm your agreement to the following:"
    And HE I verify the edit privacy info label that says: "By submitting this form, you acknowledge that an account will be created and maintained for you on Intersect. The account includes periodic communications from Intersect regarding product-related updates, services and surveys."
    And HE I verify the edit privacy info label that says: "You have the option to withdraw your consent at any time by contacting us"
    Then I verify the following fields are required fields in the Counselor Community profile form
      |Job Title field is required.|Office Phone field is required.|Privacy Policy|Are you an EU citizen? field is required.|Terms of Use|Community Guidelines|
    And I activate my community profile by providing OfficePhone as "1234567892" JobTitle as "Counselor" and EU citizen as "Yes"
    Then I verify the user can access the following fields
      |Counselor Community|RepVisits|
    And HS I successfully sign out
#Naviance
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
    And HS I go to the Counselor Community
    And I clear the account to get the community welcome page again
    Then HS I successfully sign out

    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
    When I verify that I am redirected to the Community activate profile page when accessing RepVisits
    Then I verify the new user required to complete the Counselor Community profile form before they can access the following fields
      |Counselor Community|RepVisits|
    And I go to the Welcome Counselor Community page
    And HE I verify the Welcome page has a header that says: "Please take a moment to complete the form below to activate your profile. This is the first step in utilizing Intersect solutions, including updating your Naviance college profile and accessing RepVisits. Based on your institution's partnership with Intersect, you may also have access to Counselor Community, Naviance Events, and Active Match. Once this step is complete you will be ready to start using Intersect."
    And HE I verify the agreements label that says: "Please confirm your agreement to the following:"
    And HE I verify the edit privacy info label that says: "By submitting this form, you acknowledge that an account will be created and maintained for you on Intersect. The account includes periodic communications from Intersect regarding product-related updates, services and surveys."
    And HE I verify the edit privacy info label that says: "You have the option to withdraw your consent at any time by contacting us"
    Then I verify the following fields are required fields in the Counselor Community profile form
      |Job Title field is required.|Office Phone field is required.|Privacy Policy|Are you an EU citizen? field is required.|Terms of Use|Community Guidelines|
    And I activate my community profile by providing OfficePhone as "5137462317" JobTitle as "Counselor" and EU citizen as "Yes"
    Then I verify the user can access the following fields
      |Counselor Community|RepVisits|
