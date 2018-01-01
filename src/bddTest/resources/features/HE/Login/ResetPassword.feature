@HE
Feature: HE - Login - ResetPassword - As an HE Admissions User I need the ability to trigger a Reset Password email to myself
  when I am unable to remember my login credentials or have locked my account so I can regain access to the system

  @MATCH-182
  Scenario: As an HE Admissions user I can take a "Reset Password" action from the Purple login screen
    When HE I begin the reset password process for user type "marketing"
    Then HE I receive the below reset password email and reset the password for user type "marketing"
      |Subject                                      |To                                     |Messages |
      |Intersect Forgot Password Verification Email |purpleheautomation+marketing@gmail.com |1        |
    Then HE I am logged in to Intersect HE as user type "marketing"
    Then HE I successfully sign out

  @ignore
  Scenario: As an HE Admissions user I must provide a valid username or email address when attempting to reset my password

  @ignore
  Scenario: As an HE Admissions user, if I provide an invalid username or password, I am presented with an error message that informs me the information I provided does not match an existing user account

  @MATCH-167
  Scenario: As a System I need a platform-level password policy for all institutional accounts and their users in order
            to secure the data within the Purple platform.

    When HE I begin the reset password process for user type "reset"
    Then HE I receive the below reset password email and reset the password for user type "reset"
      |Subject                                     |To                                         |Messages |
      |Intersect Forgot Password Verification Email|purpleheautomation+resetpassword@gmail.com |1        |