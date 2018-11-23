@HE
Feature: HE - Settings - AccountSettings - As an HE user, I should be able to manage my user settings

  @MATCH-1172 @MATCH-1155
  Scenario: As an HE user, I can enter the Account Settings screen and then cancel my
            changes without updating my profile.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I access the Account Settings page
    And HE I verify the Password Requirements are displayed
    And HE I enter the following data on the Account Settings page and click "Home"
      |Current Password     |Password!1   |
      |New Password         |BadPassword1!|
      |Confirm New Password |BadPassword1!|
    Then HE I successfully sign out
    # This step will fail if the password was changed when clicking "Cancel"
    And HE I am logged in to Intersect HE as user type "administrator"
    And HE I am able to successfully login

  @MATCH-1191
  Scenario: As an HE user, I want to be able to manage my Purple account and my Community account separately.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify the Account Settings page for the following user
      |E-mail Address |purpleheautomation@gmail.com |
    Then HE I verify the Update Profile page for the following user
      |E-mail Address   |purpleheautomation@gmail.com   |
      |First Name       |PurpleHE                       |
      |Last Name        |Automation                     |
      |Your institution |The University of Alabama (636)|


  @MATCH-1134
  Scenario: As an HE user, I can update my Account Settings.
    Given HE I am logged in to Intersect HE as user type "updates"
    Then HE I access the Account Settings page
    And HE I enter the following data on the Account Settings page and click "Save"
      |First Name           |PurpleHE_Updated |
      |Last Name            |Updates_Updated  |
      |Current Password     |Password!1       |
      |New Password         |Password!1       |
      |Confirm New Password |Password!1       |
  #  And I receive the "Matching Account has been Updated" email below
  #    |Subject                             |From                                 |Messages |Body                                                                                                                               |
  #    |Intersect Account has been Updated  |no-reply@purpledev.hobsonspobox.net  |1        |THIS TEXT IS UNKNOWN AS THE EMAIL WAS NOT WORKING |
    Then HE I access the Account Settings page
    And HE I enter the following data on the Account Settings page and click "Save"
      |First Name       |PurpleHE                                     |
      |Last Name        |Updates                                      |
      |Email            |purpleheautomation+updates_updated@gmail.com |
    And HE I receive the "Matching Account has been Updated" email below
      |Subject                             |To                                    |Messages |Body                                                                                                                                                          |
      |Intersect Account has been Updated  |purpleheautomation+updates@gmail.com  |1        |The email address of your Intersect user account was recently updated. If you or your institution's administrator did not initiate this change, please contact|
    Then HE I access the Account Settings page
    And HE I enter the following data on the Account Settings page and click "Save"
      |Email            |purpleheautomation+updates@gmail.com |
    And HE I receive the "Matching Account has been Updated" email below
      |Subject                             |To                                            |Messages |Body                                                                                                                                                          |
      |Intersect Account has been Updated  |purpleheautomation+updates_updated@gmail.com  |1        |The email address of your Intersect user account was recently updated. If you or your institution's administrator did not initiate this change, please contact|

# This test case isn't actually testing MATCH-1129, and since the AC for that ticket is basically a negative test with email, it will be hard to automate in the first place
# Given that, I've commented it out, but left it to be fixed at a later time if necessary.
#  @MATCH-1129
#  Scenario: As a non view only Hobsons staff member I want the admin page to NOT send the 'email updated verification code'
#            email to the primary owner each time I update the primary owner's email address after they have logged in to the Purple UI at least once
#            so the primary owner does not get confused when logging in with their new email address.
#    Given HE I want to login to the HE app using "purpleheautomation@gmail.com" as username and "Password!1" as password
#    Then HE I navigate to the "Account Settings" page to access the "Users" page
#    Then HE I select the "Edit" options from the actions dropdown for the HE primary account "purpleheautomation@gmail.com"
#    And HE I enter the following data on the Account Settings page and click "Save"
#      |First Name       |PurpleHE                                     |
#      |Last Name        |Updates                                      |
#      |Email            |purpleheautomation+Updates@gmail.com         |
#    And HE I receive the "Matching Account has been Updated" email below
#      |Subject                             |To                                    |Messages |Body                                                                                                                                                          |
#      |Intersect Account has been Updated  |purpleheautomation@gmail.com          |1        |The email address of your Intersect user account was recently updated. If you or your institution's administrator did not initiate this change, please contact|
#    Then HE I navigate to the "Account Settings" page to access the "Users" page
#    Then HE I select the "Edit" options from the actions dropdown for the HE primary account "purpleheautomation+Updates@gmail.com"
#    And HE I enter the following data on the Account Settings page and click "Save"
#      |First Name       |PurpleHE                       |
#      |Last Name        |Automation                     |
#      |Email            |purpleheautomation@gmail.com   |
#    And HE I receive the "Matching Account has been Updated" email below
#      |Subject                             |To                                            |Messages |Body                                                                                                                                                          |
#      |Intersect Account has been Updated  |purpleheautomation+Updates@gmail.com          |1        |The email address of your Intersect user account was recently updated. If you or your institution's administrator did not initiate this change, please contact|
#    Then HE I successfully sign out

  @MATCH-3060 @MATCH-3061
  Scenario Outline: As a HE RepVisits user,I want one page to go to to manage all my settings
                    To simplify my RepVisits configuration experience.

    Given HE I am logged in to Intersect HE as user type "<usertype>"
    And HE I verify the items in the user dropdown for a HE user
    Then HE I verify the items are navigate to the respective page in the user dropdown for a HE user
    Then HE I verify the user is "<user>" or not
    And HE I verify the items are present in the help center dropdown for a HE user
    Examples:
      |usertype   |user     |
      |publishing |NON-ADMIN|
      |limited    |ADMIN    |
      |community  |NON-ADMIN|


  @MATCH-3062 @Unstable
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
    And HE I validate the password field "<oldPassword>","<newPassword>","<minimum8character>","<lowercaseletter>","<uppercaseletter>","<withoutNumber>","<withoutspecialcharacter>"
    And HE I verify the success message "Success! You've updated your account information." in Account settings page
    Examples:
      |usertype                                             |oldPassword|newPassword|minimum8character|lowercaseletter|uppercaseletter|withoutNumber|withoutspecialcharacter|FirstName|LastName                |
      |purpleheautomation+LimitedPasswordPolicy@gmail.com   |Password!1 |Password#1 |word!1           |password#1     |PASSWORD#1     |Password#*   |Password1              |PurpleHE |LimitedPasswordPolicy   |
      |purpleheautomation+PublishingPasswordPolicy@gmail.com|Password!1 |Password#1 |word!1           |password#1     |PASSWORD#1     |Password#*   |Password1              |PurpleHE |PublishingPasswordPolicy|
      
  @MATCH-1785
  Scenario: As a RepVisits user I want to see the appropriate subtabs within the RepVisits Notifications page so I can easily find the notifications I need to review.

    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    Then SP I set the "Intersect Presence Subscription" module to "active" in the institution page

    Then HE I am logged in to Intersect HE as user type "administrator"
    Then I verify the following sub-tabs are displaying and "High Schools Now Available" is "displaying" in the notification tab for "premium" user
      |Requests|Activity|

    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    Then SP I set the "Intersect Presence Subscription" module to "inactive" in the institution page

    Then HE I am logged in to Intersect HE as user type "limited"
    Then I verify the following sub-tabs are displaying and "High Schools Now Available" is "not displaying" in the notification tab for "limited" user
      |Requests|Activity|
    And HE I successfully sign out

    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then I verify the following sub-tabs are displaying and "Naviance Sync" is "displaying" in the notification tab for "naviance" user
      |Requests|Activity|Visit Feedback|
    And HS I successfully sign out

    Given HS I am logged in to Intersect HS as user type "administrator"
    Then I verify the following sub-tabs are displaying and "Naviance Sync" is "not displaying" in the notification tab for "non-naviance" user
      |Requests|Activity|Visit Feedback|
