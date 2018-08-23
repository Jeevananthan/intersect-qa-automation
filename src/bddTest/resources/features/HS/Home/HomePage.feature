@HS
Feature: HE - Home - HomePage - As an HE user, I want to be able to access the features of the Home page

  @MATCH-4661 @MATCH-4664 @MATCH-4658
  Scenario Outline: As an HE user in Intersect, I need to see the Intersect Connection subscription module
  so that I can access the configure my Intersect Connection settings and access my connections effectively.
    Given HS I am logged in to Intersect HS as user type "<user>"
    Then HS I verify that the text in the button for "<module>" is "<button>"
    Then HS I verify that "<pageURL>" is opened from the "<module>" module
    And HS I successfully sign out
       Examples:
      | user          | module              | button      | pageURL                 |
      | administrator | RepVisits           | SCHEDULE    | rep-visits/calendar     |
      | member        | RepVisits           | SCHEDULE    | rep-visits/calendar     |
#  skipped due to MATCH-4997
#      | administrator | Your settings       | CONFIGURE   | settings/change-profile |
#      | member        | Your settings       | CONFIGURE   | settings/change-profile |
      | administrator | Counselor Community | PARTICIPATE | community/              |
      | member        | Counselor Community | PARTICIPATE | community/              |

  @MATCH-1799 @MATCH-4609
  Scenario: As a Hobsons staff wanting to ensure data security compliance for Intersect users,
  I want to ensure that all users coming through intersect including all subscription levels and HE user is required to complete the Counselor Community profile page,
  So that I can ensure that all users coming into Intersect have consented to terms, account creation, and specified whether they're an EU citizen.
    #Cleanup steps
    Given HS I want to login to the HS app using "purpleheautomation+HSCCProfile@gmail.com" as username and "Password!1" as password
    And HS I go to the Counselor Community
    And HS I clear the account to get the community welcome page again
    Then HS I successfully sign out
    # Testcase
    Given HS I want to login to the HS app using "purpleheautomation+HSCCProfile@gmail.com" as username and "Password!1" as password
    When HS I verify that I am redirected to the Community activate profile page when accessing RepVisits
    Then HS I verify the new user required to complete the Counselor Community profile form before they can access the following fields
      |Counselor Community|RepVisits|
    Then HS I verify the following fields are required fields in the Counselor Community profile form
      |Job Title field is required.|Office Phone field is required.|Privacy Policy|Are you an EU citizen? field is required.|Terms of Use|Community Guidelines|
    And HS I activate my community profile by providing OfficePhone as "1234567892" JobTitle as "Counselor" and EU citizen as "Yes"
    Then HS I verify the user can access the following fields
      |Counselor Community|RepVisits|
    And HS I clear the account to get the community welcome page again
    And HS I successfully sign out