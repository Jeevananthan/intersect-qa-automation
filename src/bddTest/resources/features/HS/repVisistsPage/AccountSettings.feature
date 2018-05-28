@HS
Feature:  As an HS user, I want to be able to access the features of the Account Settings page.

  @MATCH-3060 @MATCH-3061
  Scenario Outline: As a HS Naviance RepVisits user,I want one page to go to to manage all my settings
                    To simplify my RepVisits configuration experience.

    Given HS I am logged in to Intersect HS through Naviance with account "<navianceUserType>"
    And HS I verify the items in the user dropdown for a Naviance user
    Then HS I verify the items are navigate to the respective page in the user dropdown for a Naviance user
    Then HS I verify the user is "<user>" or not
    And HS I successfully sign out
  Examples:
    |navianceUserType|user      |
    |navianceAdmin   |ADMIN     |
    |navianceMember  |NON-ADMIN |

  @MATCH-3060 @MATCH-3061
  Scenario Outline: As a HS Non-Naviance RepVisits user,I want one page to go to to manage all my settings
                    To simplify my RepVisits configuration experience.

    Given HS I want to login to the HS app using "<usertype>" as username and "<password>" as password
    And HS I verify the items in the user dropdown for a Non-Naviance user
    Then HS I verify the items are navigate to the respective page in the user dropdown for a Non-Naviance user
    Then HS I verify the user is "<user>" or not
    And HS I verify the items are present in the help center dropdown for a Non-Naviance user
    And HS I successfully sign out
  Examples:
    |usertype                                  |password  |user      |
    |purpleheautomation+administrator@gmail.com|Password!1|ADMIN     |
    |purpleheautomation+member@gmail.com       |Password!1|NON-ADMIN |

  @MATCH-3062
  Scenario Outline: As a HS RepVisits User,I need to be able to update my contact information and reset my password
                    So I can effectively manage my RepVisits Account.

    Given HS I want to login to the HS app using "<usertype>" as username and "<oldPassword>" as password
    Then HS I navigate to the "Account Settings" Page
    Then HS I reset the password for "<oldPassword>","<newPassword>"
    And HS I verify the success message "Success! You've updated your account information." in Account settings page
    And HS I successfully sign out

    Given HS I want to login to the HS app using "<usertype>" as username and "<newPassword>" as password
    Then HS I navigate to the "Account Settings" Page
    And HS I verify the left-sub menu "Account Information" is present in the Account Settings page
    And HS I verify the non-password fields "Account Information,Your Name,First Name,Last Name,Contact Information,Email,Change Password,Current Password,New Password,Confirm New Password" are pre-populated with current data "<FirstName>","<LastName>","<Email>"
      |contain a lowercase letter|contain an uppercase letter|contain a number|
    And HS I validate the password field "<user>","<newPassword>","<minimum8character>","<lowercaseletter>","<uppercaseletter>","<withoutNumber>","<withoutspecialcharacter>"
    And HS I verify the success message "Success! You've updated your account information." in Account settings page
    And HS I successfully sign out
  Examples:
   |usertype                                  |oldPassword|newPassword|minimum8character|lowercaseletter|uppercaseletter|withoutNumber|withoutspecialcharacter|user          |FirstName|LastName|Email                                     |
   |purpleheautomation+administrator@gmail.com|Password!1 |Password#1 |word!1           |password#1     |PASSWORD#1     |Password#*   |Password1              |administrator |Test     |qa      |purpleheautomation+administrator@gmail.com|
   |purpleheautomation+member@gmail.com       |Password!1 |Password#1 |word!1           |password#1     |PASSWORD#1     |Password#*   |Password1              |member        |QA       |Test    |purpleheautomation+member@gmail.com       |
