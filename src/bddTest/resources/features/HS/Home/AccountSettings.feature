@HS @HS2
Feature: HS - Home - AccountSettings - As an HS user, I can manage my account settings

  @MATCH-3715
  Scenario: As a HS admin user of RepVisits that is syncing appointments (visits and college fairs) with Naviance,
  I want the header and lead in copy on the Naviance Settings wizard page (pre-sync)
  and on the RepVisits>Availability & Settings>Naviance Settings page "Naviance College Visits Default Settings" section to clearly indicate
  how those values will be applied to visits when syncing and thereafter should I change them
  So that I know exactly what to expect as to how those are applied to my visits.
    Given HS I am logged in to Intersect HS through Naviance with account "navianceAdmin"
#    Then HS I complete the set up wizard that not yet completed by selecting "All RepVisits Users" option on the 'One Last Step' page
    Then HS I go to welcome wizard of the repvisits
    And HS I navigate to "Naviance Settings" wizard in repvisits page
    Then HS I verify the following details are displaying in Naviance Settings page
      |Naviance Sync Settings|Default Visit Details for College Visits|You can set default values to save time in scheduling future visits, but you can also edit details for individual visits when needed.|The following default visit details will be applied to visits scheduled moving forward.|
    Then HS I complete the set up wizard page by selecting "All RepVisits Users" option on the 'One Last Step' page
    Then HS I navigate to naviance settings page
    Then HS I verify the following details are displaying in Naviance Settings page
      |Naviance Sync Settings|Default Visit Details for College Visits|You can set default values to save time in scheduling future visits, but you can also edit details for individual visits when needed.|The following default visit details will be applied to visits scheduled moving forward.|


  @MATCH-3060 @MATCH-3061
  Scenario Outline: As a HS Naviance RepVisits user,I want one page to go to to manage all my settings
                    To simplify my RepVisits configuration experience.

    Given HS I am logged in to Intersect HS through Naviance with user type "<UserType>"
    And HS I verify the items in the user dropdown for a Naviance user
    Then HS I verify the items are navigate to the respective page in the user dropdown for a Naviance user
    Then HS I verify the user is "<Role>" or not
  Examples:
    |UserType        |Role      |
    |navianceAdmin   |ADMIN     |
    |navianceMember  |NON-ADMIN |

  @MATCH-3060 @MATCH-3061
  Scenario Outline: As a HS Non-Naviance RepVisits user,I want one page to go to to manage all my settings
                    To simplify my RepVisits configuration experience.

    Given HS I am logged in to Intersect HS as user type "<UserType>"
    And HS I verify the items in the user dropdown for a Non-Naviance user
    Then HS I verify the items are navigate to the respective page in the user dropdown for a Non-Naviance user
    Then HS I verify the user is "<Role>" or not
    And HS I verify the items are present in the help center dropdown for a Non-Naviance user
  Examples:
    |UserType     |Role      |
    |administrator|ADMIN     |
    |member       |NON-ADMIN |

  #Due to the throttle error in qa , we could not be able to reset the password more than twice a day. So we could not be able to run the test case more than twice.
  @MATCH-3062 @Unstable
  Scenario Outline: As a HS RepVisits User,I need to be able to update my contact information and reset my password
                    So I can effectively manage my RepVisits Account.

    Given HS I want to login to the HS app using "<usertype>" as username and "<oldPassword>" as password
    Then HS I navigate to the "Account Settings" Page
    Then HS I reset the password for "<oldPassword>","<newPassword>"
    And HS I successfully sign out

    Given HS I want to login to the HS app using "<usertype>" as username and "<newPassword>" as password
    Then HS I navigate to the "Account Settings" Page
    And HS I verify the left-sub menu "Account Information" is present in the Account Settings page
    And HS I verify the non-password fields "Account Information,Your Name,First Name,Last Name,Contact Information,Email,Change Password,Current Password,New Password,Confirm New Password" are pre-populated with current data "<FirstName>","<LastName>","<Email>"
      |contain a lowercase letter|contain an uppercase letter|contain a number|
    And HS I validate the password field "<oldPassword>","<newPassword>","<minimum8character>","<lowercaseletter>","<uppercaseletter>","<withoutNumber>","<withoutspecialcharacter>"
    And HS I successfully sign out
  Examples:
   |usertype                                                 |oldPassword|newPassword|minimum8character|lowercaseletter|uppercaseletter|withoutNumber|withoutspecialcharacter |FirstName|LastName      |Email                                                    |
   |purplehsautomations+AdministratorPasswordPolicy@gmail.com|Password!1 |Password#1 |word!1           |password#1     |PASSWORD#1     |Password#*   |Password1               |PurpleHS |PasswordPolicy|purplehsautomations+AdministratorPasswordPolicy@gmail.com|
   |purplehsautomations+MemberPasswordPolicy@gmail.com       |Password!1 |Password#1 |word!1           |password#1     |PASSWORD#1     |Password#*   |Password1               |PurpleHS |PasswordPolicy|purplehsautomations+MemberPasswordPolicy@gmail.com       |
