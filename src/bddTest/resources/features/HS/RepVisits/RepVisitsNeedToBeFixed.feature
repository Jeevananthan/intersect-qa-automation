@HS
Feature:  As an HS user, I want to be able to access the features of the RepVisits module.


 #  This ticket is commented by missing fix because is changing the password and not recovering the initial password, Jeeva
#  comment that are some issue related to this ticket
#  @MATCH-3062
#  Scenario Outline: As a HS RepVisits User,I need to be able to update my contact information and reset my password
#                    So I can effectively manage my RepVisits Account.
#
#    Given HS I want to login to the HS app using "<usertype>" as username and "<oldPassword>" as password
#    Then HS I navigate to the "Account Settings" Page
#    Then HS I reset the password for "<oldPassword>","<newPassword>"
#    And HS I verify the success message "Success! You've updated your account information." in Account settings page
#    And HS I successfully sign out
#
#    Given HS I want to login to the HS app using "<usertype>" as username and "<newPassword>" as password
#    Then HS I navigate to the "Account Settings" Page
#    And HS I verify the left-sub menu "Account Information" is present in the Account Settings page
#    And HS I verify the non-password fields "Account Information,Your Name,First Name,Last Name,Contact Information,Email,Change Password,Current Password,New Password,Confirm New Password" are pre-populated with current data "<FirstName>","<LastName>","<Email>"
#      |contain a lowercase letter|contain an uppercase letter|contain a number|
#    And HS I validate the password field "<user>","<newPassword>","<minimum8character>","<lowercaseletter>","<uppercaseletter>","<withoutNumber>","<withoutspecialcharacter>"
#    And HS I verify the success message "Success! You've updated your account information." in Account settings page
#    And HS I successfully sign out
#  Examples:
#   |usertype                                  |oldPassword|newPassword|minimum8character|lowercaseletter|uppercaseletter|withoutNumber|withoutspecialcharacter|user          |FirstName|LastName|Email                                     |
#   |purpleheautomation+administrator@gmail.com|Password!1 |Password#1 |word!1           |password#1     |PASSWORD#1     |Password#*   |Password1              |administrator |Test     |qa      |purpleheautomation+administrator@gmail.com|
#   |purpleheautomation+member@gmail.com       |Password!1 |Password#1 |word!1           |password#1     |PASSWORD#1     |Password#*   |Password1              |member        |QA       |Test    |purpleheautomation+member@gmail.com       |

      #Scenario is failing and need to be fixed
#  Scenario: As an HS user, I want to be able to add/remove time slots
#    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
#    When HS I open the Exceptions page
#    And HS I select a date "10" days ahead from now
#    And HS I add a new time slot with the following data:
#      | Start time | 07:03 am |
#      | End time   | 08:00 am |
#      | Visits     | 3        |
#    Then HS I verify that the time slot was added in a generated date, with the start time "7:03am"
#    And HS I delete the time slot in a generated date, with start time "7:03am"
#    And HS I verify that the time slot was removed from the generated date, with the start time "7:03am"
#    And HS I successfully sign out


#  Scenario: As an HS User, I want to be able to clear a day
#    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
#    When HS I open the Exceptions page
#    And HS I select a date "11" days ahead from now
#    And HS I add a new time slot with the following data:
#      | Start time | 07:04 am |
#      | End time   | 08:00 am |
#      | Max Visits | 3        |
#    And HS I select a date "11" days ahead from now
#    Then HS I clear the day
#    And HS I verify that the time slot was removed from the generated date, with the start time "7:04am"
#    And HS I successfully sign out

  #  @MATCH-1469
#  Scenario: As a HS user Manually Add a Contact to Appointment
#    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
#    And HS I am Navigating to Calendar Home Screen
#    And HS I click on button Add Visit
#    And HS I select custom time manually
#    And HS I select a date "12" days ahead from now
#    And HS I select Visit StartTime "9:40am" and End Time "10:00am"
#    And HS I click on link Add School User Manually
#    And HS I Enter Following Data to Add a School User Manually
#      |FirstName|Amanda|
#      |LastName |Hubs  |
#      |E-mail|amanda@hobsons.com  |
#      |Phone    |5137462317         |
#      |Position |QA Tester         |
#      |Institution|Alma College    |
#    And HS I Enter Internal Notes "Visit Notes Added for Automation Purpose"
#    And HS I click on Add Visit button
#    And HS I click on Agenda on Calendar
#    And Hs I open the date picker on Agenda View
#    And HS I select a date "12" days ahead from now from the standard date picker
#    And HS I click on Day on Calendar
#    And HS I click on Visit with "Alma College" from "9:40 AM" to "10:00 AM" on Day Calendar
#    And HS I verify Representative details on Visit Details screen "amanda@hobsons.com"
#    And HS I Cancel visit to create again add Notes to Cancel "canceled for automation"
#    And HS I successfully sign out
