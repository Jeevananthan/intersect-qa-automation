@HE
Feature: As an HE administrator I want to manage users at my institution

  @MATCH-1100 @MATCH-1258
  Scenario: As an HE administrator, I am able to deactivate and reactivate other users' accounts in my institution.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify the Manage Users screen contains the following user
      |First Name |Last Name  |Email                        |Role                       |
      |PurpleHE   |Automation |purpleheautomation@gmail.com |Administrator (All access) |
    Then HE I inactivate the user account for "purpleheautomation+recruiter@gmail.com"
    And HE I successfully sign out
    Then HE I am prevented from logging in as user type "recruiter"
    Then HE I am logged in to Intersect HE as user type "administrator"
    And HE I activate the user account for "purpleheautomation+recruiter@gmail.com"
    And HE I successfully sign out
    Then HE I am logged in to Intersect HE as user type "recruiter"
    And HE I am able to successfully login
    Then HE I successfully sign out


  @MATCH-133
  Scenario: As an HE administrator, I am able to manage other users' roles in my institution.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I edit the user account for "purpleheautomation+recruiter@gmail.com" with the following info
      |Role  |administrator |
    And HE I successfully sign out
    Then HE I am logged in to Intersect HE as user type "recruiter"
    Then HE I edit the user account for "purpleheautomation+recruiter@gmail.com" with the following info
      |Role  |publishing |
    And HE I successfully sign out


  @MATCH-175
  Scenario: As an HE administrator, I am able to verify the roles for users in my institution.
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I verify the user roles available in my institution
    Then HE I successfully sign out


  @MATCH-1127
  Scenario: As an HE user, I want to be notified at my old email address when my account email is changed by my administrator.
    Given HE I am logged in to Intersect HE as user type "coordinator"
    Then HE I edit the user account for "purpleheautomation+coordinator@gmail.com" with the following info
      |Email  |purpleheautomation+coordinator_updated@gmail.com |
    And HE I receive the "Matching Account has been Updated" email below
      |Subject                             |To                                        |Messages |Body                                                                                                                               |
      |Intersect Account has been Updated  |purpleheautomation+coordinator@gmail.com  |1        |The email address of your Intersect user account was recently updated. If you or your institution's administrator did not initiate this change, please contact  |
    Then HE I successfully sign out
    Then HE I am logged in to Intersect HE as user type "administrator"
    Then HE I edit the user account for "purpleheautomation+coordinator_updated@gmail.com" with the following info
      |Email  |purpleheautomation+coordinator@gmail.com |
    And HE I receive the "Matching Account has been Updated" email below
      |Subject                             |To                                                |Messages |Body                                                                                                                               |
      |Intersect Account has been Updated  |purpleheautomation+coordinator_updated@gmail.com  |1        |The email address of your Intersect user account was recently updated. If you or your institution's administrator did not initiate this change, please contact  |
    Then HE I successfully sign out


  @MATCH-192
  Scenario: As an HE Admissions Administrator I can see the last login date for each user in Purple
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I can see the last login date for user type "administrator"
    Then HE I successfully sign out


  @MATCH-1416 @NotInQA
  Scenario: As an Intersect system I want HE user accounts that have been inactivated to also have their
            corresponding Community user accounts inactivated.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I inactivate the user account for "yadav.arun24+qa1416@gmail.com"
    And HE I search for "Arun Match1416" in "People"
    Then HE I verify there are no search results returned
    And HE I activate the user account for "yadav.arun24+qa1416@gmail.com"
    Then HE I successfully sign out



  @MATCH-130
  Scenario Outline: As a HE user with administrator role,I can able to create, edit, active and inactive users in HE app.
    Given HE I am logged in to Intersect HE as user type "administrator"
    # This is not repeatedly testable, as you cannot delete acocunts from the UI.
    # And HE I can create a user in HE app "<First Name>" and "<Last Name>" and "<Email>" and "<role>"
    Then HE I inactivate the user account for "<Email>"
    And HE I activate the user account for "<Email>"
    Then HE I edit the user account for "purpleheautomation+match-130@gmail.com" with the following info
      |Email  |purpleheautomation+match-130-updated@gmail.com |
    Then HE I edit the user account for "purpleheautomation+match-130-updated@gmail.com" with the following info
      |Email  |purpleheautomation+match-130@gmail.com |
    Then HE I successfully sign out
    Examples:
      |First Name |Last Name     |Email                                   | role      |
      |testing03  |automation02  |purpleheautomation+match-130@gmail.com  |publishing |







