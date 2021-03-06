@SP
Feature: SP - Account Pages - UserListPage - Manage User accounts

  @MATCH-129
  Scenario: As a Hobsons admin user, I can inactivate and activate HE user accounts.
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I go to the users list for "The University of Alabama" from the institution dashboard
    And SP I "inactivate" the user account for "purpleheautomation+recruiter@gmail.com"
    Then SP I verify that the user account for "purpleheautomation+recruiter@gmail.com" is "inactive"
    And SP I "activate" the user account for "purpleheautomation+recruiter@gmail.com"
    Then SP I verify that the user account for "purpleheautomation+recruiter@gmail.com" is "active"

  @MATCH-129
  Scenario: As a Hobsons admin user, I can change the primary user of a premium institution.
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I go to the users list for "The University of Alabama" from the institution dashboard
    And SP I "activate" the user account for "purpleheautomation+AssignasPrimary@gmail.com"
    And SP I set the user "purpleheautomation+AssignasPrimary@gmail.com" to be the new primary user
    Then SP I verify that the user account for "purpleheautomation+AssignasPrimary@gmail.com" is the primary user
    And SP I set the user "purpleheautomation@gmail.com" to be the new primary user
    Then SP I verify that the user account for "purpleheautomation@gmail.com" is the primary user
    Then SP I verify that the user account for "purpleheautomation+AssignasPrimary@gmail.com" is not the primary user

  @MATCH-1126
  Scenario: As a Hobsons support user, if I inactivate the primary account for a freemium institution,
            I can create a new primary user
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I go to the users list for "Bowling Green State University-Main Campus" from the institution dashboard
    And SP I "inactivate" the user account for "purpleheautomation+12103@gmail.com"
    Then SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    And SP I verify that I can create a new primary user

    Then SP I go to the users list for "Bowling Green State University-Main Campus" from the institution dashboard
    And SP I "activate" the user account for "purpleheautomation+12103@gmail.com"

    @MATCH-1783
    Scenario Outline: As a Support user with the Administrator or Support role I need to be able to create additional users
                      for HE and Non Naviance HS accounts beyond just the primary user so non premium accounts can now have more than one user tied to them.
      #HE create user
      Given SP I am logged in to the Admin page as an Admin user
      Then SP I go to the Create user for "Alma College" from the institution dashboard
      Then SP I fill the create user form "<heFirstNameVerify>","<heLastNameVerify>","<heEmailVerify>","<heVerifyEmailVerify>","<heRoleVerify>","<heButtontoClickVerify>"
      Then SP verify the error message in create user page
      Then SP I fill the create user form "<heFirstNameSubmit>","<heLastNameSubmit>","<heEmailSubmit>","<heVerifyEmailSubmit>","<heRoleSubmit>","<heButtontoClickSubmit>"
      Then SP verify there is no empty field error message in the create user page
      #HS create user
      When SP I search for "390495603634"
      Then SP I select "LAKOTA HIGH SCHOOL" from the global search results
      And SP I navigate to create user page
      Then SP I fill the create user form "<hsFirstNameVerify>","<hsLastNameVerify>","<hsEmailVerify>","<hsVerifyEmailVerify>","<hsRoleVerify>","<hsButtontoClickVerify>"
      Then SP verify the error message in create user page
      Then SP I fill the create user form "<hsFirstNameSubmit>","<hsLastNameSubmit>","<hsEmailSubmit>","<hsVerifyEmailSubmit>","<hsRoleSubmit>","<hsButtontoClickSubmit>"
      Then SP verify there is no empty field error message in the create user page

  Examples:
    |heFirstNameVerify |heLastNameVerify|heEmailVerify         |heVerifyEmailVerify|heRoleVerify |heButtontoClickVerify|heFirstNameSubmit|heLastNameSubmit|heEmailSubmit            |heVerifyEmailSubmit       |heRoleSubmit |heButtontoClickSubmit|hsFirstNameVerify|hsLastNameVerify|hsEmailVerify         |hsVerifyEmailVerify      |hsRoleVerify|hsButtontoClickVerify|hsFirstNameSubmit|hsLastNameSubmit|hsEmailSubmit            |hsVerifyEmailSubmit       |hsRoleSubmit|hsButtontoClickSubmit|
    |NewUserTest       |LNameTest       |n.l@notarealdomain.com|                   |publishing   |Save                 |NewUserTest01    |LNameTest01     |n.l+01@notarealdomain.com|n.l+01@notarealdomain.com |publishing   |Save                 |NewUserTest      |LNameTest       |n.l@notarealdomain.com|                         |member      |Save                 |NewUserTest01    |LNameTest01     |n.l+01@notarealdomain.com|n.l+01@notarealdomain.com |member      |Save                 |



#  @MATCH-1416 @NotInQA
#  Scenario: As an Support system I want HE user accounts that have been inactivated to also have their
#            corresponding Community user accounts inactivated.
#    Given SP I am logged in to the Admin page as an Admin user
#    Then SP I go to the users list for "The University of Alabama" from the institution dashboard
#    And SP I "inactivate" the user account for "yadav.arun24+qa1416@gmail.com"
#    And SP I search for "Arun Match1416" in "People"
#    Then SP I verify there are no search results returned
#    And SP I "activate" the user account for "yadav.arun24+qa1416@gmail.com"
#    Then SP I successfully sign out

  @MATCH-1683
  Scenario: As a Administrator and Support User, I want to unlock a HE user account which has been locked.
    When HE I want to login to the HE app using "purpleheautomation+locked@gmail.com" as username and "boGusPassw0rd" as password
    And HE I want to login to the HE app using "purpleheautomation+locked@gmail.com" as username and "boGusPassw0rd" as password
    And HE I want to login to the HE app using "purpleheautomation+locked@gmail.com" as username and "boGusPassw0rd" as password
    And HE I want to login to the HE app using "purpleheautomation+locked@gmail.com" as username and "boGusPassw0rd" as password
    And HE I want to login to the HE app using "purpleheautomation+locked@gmail.com" as username and "boGusPassw0rd" as password
    Then HE I am locked out from logging in as user type "locked"
    Given SP I am logged in to the Admin page as a Support user
    Then SP I go to the users list for "The University of Alabama" from the institution dashboard
    And SP I "unlock" the user account for "purpleheautomation+locked@gmail.com"
    And SP I successfully sign out
    Then HE I am logged in to Intersect HE as user type "locked"
    And HE I am able to successfully login

  @MATCH-1553 @MATCH-5502
  Scenario: As a Support user I can able to re-invite an user from the support app.
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I go to the users list for "The University of Alabama" from the institution dashboard
    And SP I "re-invite" the user account for "purpleheautomation+MATCH-1553@gmail.com"
    Then SP I receive the Intersect Invitation email with the following data:
      |Subject                    |To                                       |Messages |
      |Your Intersect Invitation  |purpleheautomation+MATCH-1553@gmail.com  |1        |

  @MATCH-1793
  Scenario Outline: As a Support user with the Administrator or Support role I need to be able to create a non primary HE or HS user
                    that does not yet have a primary user established so the primary user slot can remain available for a more appropriate
                    staff member at that institution.
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    And SP I verify that I can create a new primary user
    Then SP I add the user account "<First Name>","<Last Name>","<Email>","<Verify Email>" and set the user to be a new primary user "<Email>"
    Then SP I successfully sign out
    Given SP I am logged in to the Admin page as a Support user
    When SP I select "Alpena Community College" from the institution dashboard
    And SP I verify that I can create a new primary user using create new user button
    Then SP I add the user account "<First Name>","<Last Name>","<Email>","<Verify Email>" and set the user to be a new primary user "<Email>"

    Examples:
      |First Name|Last Name  |Email                         |Verify Email                       |
      |PurpleHE  |Automation |purpleheautomation@gmail.com  |purpleheautomation@gmail.com       |


  @MATCH-2378
  Scenario: As a Support user I want to be able to update the primary user info for a non-Naviance HS
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    Then SP I verify that I can edit the Primary User Details

  @MATCH-5014
  Scenario: As a Support User I should see the impersonator banner in HS project
    When SP I am logged in to the Admin page as a Support user
    And SP I go to the users list for "HS" user, institution "Int Qa High School 4" from the institution dashboard
    And SP I "Login As" the user account for "hobsons.rrt+other16@gmail.com"
    Then SP I verify the "You're currently logged in as IAM Purple from Int Qa High School 4. Changes you make will reflect in their account." message in the homepage

  @MATCH-2863
  Scenario: As a Hobsons Support user with the Administrator or Support role within the Support App I need to impersonate
  nonNaviance HS users in order to troubleshot technical issues and tickets submitted to Hobsons Support.
    When SP I am logged in to the Admin page as a Support user
    And SP I go to the users list for "HS" user, institution "Homeconnection" from the institution dashboard
    And SP I use "Login As" feature for login as "adminHS"
    Then SP I verify the "You're currently logged in as PurpleHS Automation from HOMECONNECTION. Changes you make will reflect in their account." message in the homepage
    And SP I check the "Account Settings" option will be hidden for impersonation