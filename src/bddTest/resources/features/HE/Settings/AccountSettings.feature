@HE
Feature:  HE - Settings - AccountSettings - As an HE user, I want to be able to manage my user profile details.

  @MATCH-1172 @MATCH-1155
  Scenario: As an HE user, I can enter the Account Settings screen and then cancel my
            changes without updating my profile.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I access the Account Settings page
    And HE I verify the Password Requirements are displayed
    And HE I enter the following data on the Account Settings page and click "Cancel"
      |Current Password     |O7#c9CWJAkXY |
      |New Password         |BadPassword1!|
      |Confirm New Password |BadPassword1!|
    Then HE I successfully sign out
    # This step will fail if the password was changed when clicking "Cancel"
    And HE I am logged in to Intersect HE as user type "administrator"
    And HE I am able to successfully login
    Then HE I successfully sign out


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
    Then HE I successfully sign out


  @MATCH-1134
  Scenario: As an HE user, I can update my Account Settings.
    Given HE I am logged in to Intersect HE as user type "updates"
    Then HE I access the Account Settings page
    And HE I enter the following data on the Account Settings page and click "Save"
      |First Name           |PurpleHE_Updated |
      |Last Name            |Updates_Updated  |
      |Current Password     |8fDxP@AZT485     |
      |New Password         |8fDxP@AZT485     |
      |Confirm New Password |8fDxP@AZT485     |
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
    Then HE I successfully sign out
