@HS @MATCH-1784 @HS2
Feature: HS - LoginLogout - LoginPage - As an HS user I can login with appropriate access

  @QASmokeTest
  Scenario: As an non Naviance HS user, I want to verify the content of the HS Login Page
    Given HS I verify the HS login screen

  @QASmokeTest
  Scenario: As an non Naviance HS user, I want to verify that the links in the HS Login page are working properly
    Given HS I verify that the following links are working as expected:
    | New User? | https://qa-reg.intersect.hobsons.com/hs |
    | Mock Naviance User? | https://qa-hs.intersect.hobsons.com/mock-naviance-login |
    | Forgot Password | https://qa-hs.intersect.hobsons.com/forgot-password |

  Scenario: As an non Naviance HS user, I want to verify the error messages in the HS login page
    Given HS I verify the following types of error messages in the HS login page:
#    | Improper email address           | This is commented because the capabilities to detect this error message are not
#    implemented in the framework yet.
    | Email or password not provided   |
    | User not found                   |

  Scenario: As a non Naviance HS user, I want to verify the locking rules in the HS login page
    When HS I will block HS app using "hobsonstest15@mailinator.com" as username and "boGusPassw0rd" as password
#    And HS I want to login to the HS app using "hobsonstest11@mailinator.com" as username and "boGusPassw0rd" as password
#    And HS I want to login to the HS app using "hobsonstest11@mailinator.com" as username and "boGusPassw0rd" as password
#    And HS I want to login to the HS app using "hobsonstest11@mailinator.com" as username and "boGusPassw0rd" as password
#    And HS I want to login to the HS app using "hobsonstest11@mailinator.com" as username and "boGusPassw0rd" as password
    Then HS I am locked out from logging in as user type "locked"
    Given SP I am logged in to the Admin page as a Support user
    Then SP I go to the users list for "THE FULTON SCHOOL" with NCES_ID "A0771765" from the institution dashboard using the search
    And SP I "unlock" the user account for "hobsonstest15@mailinator.com"
    And SP I successfully sign out
    And HS I want to login to the HS app using "hobsonstest15@mailinator.com" as username and "boGusPassw0rd@" as password
    And HS I successfully sign out

 @MATCH-2062
  Scenario: As a HS user,I want to see the right logo, So that I know I'm in the counselor community (by Hobsons).
    When HS I verify the Intersect Logo present in the Login Page
    When HS I want to login to the HS app using "purpleheautomation+admin@gmail.com" as username and "Password!1" as password
    And HS I verify the Intersect Logo present in the Home Page
    And HS I successfully sign out