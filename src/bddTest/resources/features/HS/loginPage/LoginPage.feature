@HS @MATCH-1784
Feature: As a HS user I need to login to Intersect

  @QASmokeTest
  Scenario: As an non Naviance HS user, I want to verify the content of the HS Login Page
    Given HS I verify the HS login screen

  @QASmokeTest
  Scenario: As an non Naviance HS user, I want to verify that the links in the HS Login page are working properly
    Given HS I verify that the following links are working as expected:
    | New User? | https://qa-reg.intersect.hobsons.com/hs |
    | Naviance User? | https://tf-succeed-56-qa.mango.naviance.com/auth/signin |
    | Forgot Password | https://qa-hs.intersect.hobsons.com/forgot-password |

  Scenario: As an non Naviance HS user, I want to verify the error messages in the HS login page
    Given HS I verify the following types of error messages in the HS login page:
#    | Improper email address           | This is commented because the capabilities to detect this error message are not
#    implemented in the framework yet.
    | Email or password not provided   |
    | User not found                   |

  Scenario: As a non Naviance HS user, I want to verify the locking rules in the HS login page
    When HS I want to login to the HS app using "hobsonstest11@mailinator.com" as username and "boGusPassw0rd" as password
    And HS I want to login to the HS app using "hobsonstest11@mailinator.com" as username and "boGusPassw0rd" as password
    And HS I want to login to the HS app using "hobsonstest11@mailinator.com" as username and "boGusPassw0rd" as password
    And HS I want to login to the HS app using "hobsonstest11@mailinator.com" as username and "boGusPassw0rd" as password
    And HS I want to login to the HS app using "hobsonstest11@mailinator.com" as username and "boGusPassw0rd" as password
    Then HS I am locked out from logging in as user type "locked"
    Given SP I am logged in to the Admin page as a Support user
    Then SP I go to the users list for "THE FULTON SCHOOL" with NCES_ID "A0771765" from the institution dashboard using the search
    And SP I "unlock" the user account for "hobsonstest11@mailinator.com"
    And SP I successfully sign out
    And HS I want to login to the HS app using "hobsonstest11@mailinator.com" as username and "Control!23" as password
    And HS I successfully sign out