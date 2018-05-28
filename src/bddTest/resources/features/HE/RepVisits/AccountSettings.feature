@HE
Feature: As an HE user, I want to be able to access the features of the Account Settings page.

  @MATCH-3060 @MATCH-3061
  Scenario Outline: As a HE RepVisits user,I want one page to go to to manage all my settings
                    To simplify my RepVisits configuration experience.

    Given HE I am logged in to Intersect HE as user type "<usertype>"
    And HE I verify the items in the user dropdown for a HE user
    Then HE I verify the items are navigate to the respective page in the user dropdown for a HE user
    Then HE I verify the user is "<user>" or not
    And HE I verify the items are present in the help center dropdown for a HE user
    And HE I successfully sign out
  Examples:
    |usertype   |user     |
    |publishing |NON-ADMIN|
    |limited    |ADMIN    |


  @MATCH-3062
  Scenario Outline: As a HE RepVisits User,I need to be able to update my contact information and reset my password
                    So I can effectively manage my RepVisits Account.

    When HE I want to login to the HE app using "<usertype>" as username and "<oldPassword>" as password
    Then HE I navigate to the "Account Settings" Page
    Then HE I reset the password for "<oldPassword>","<newPassword>"
    And HE I verify the success message "Success! You've updated your account information." in Account settings page
    And HE I successfully sign out

    When HE I want to login to the HE app using "<usertype>" as username and "<newPassword>" as password
    Then HE I navigate to the "Account Settings" Page
    And HE I verify the left-sub menu "Account Information,Users" are present in the Account Settings page
    And HE I verify the non-password fields "Account Information,Your Name,First Name,Last Name,Contact Information,Email,Change Password,Current Password,New Password,Confirm New Password" are pre-populated with current data "<FirstName>","<LastName>","<Email>"
      |contain a lowercase letter|contain an uppercase letter|contain a number|
    And HE I validate the password field "<user>","<newPassword>","<minimum8character>","<lowercaseletter>","<uppercaseletter>","<withoutNumber>","<withoutspecialcharacter>"
    And HE I verify the success message "Success! You've updated your account information." in Account settings page
    And HE I successfully sign out
 Examples:
  |usertype                               |oldPassword|newPassword|minimum8character|lowercaseletter|uppercaseletter|withoutNumber|withoutspecialcharacter|user      |FirstName|LastName  |Email                                  |
  |purpleheautomation+limited@gmail.com   |Password!1 |Password#1 |word!1           |password#1     |PASSWORD#1     |Password#*   |Password1              |limited   |PurpleHE |Limited   |purpleheautomation+limited@gmail.com   |
  |purpleheautomation+publishing@gmail.com|Password!1 |Password#1 |word!1           |password#1     |PASSWORD#1     |Password#*   |Password1              |publishing|PurpleHE |Publishing|purpleheautomation+publishing@gmail.com|


