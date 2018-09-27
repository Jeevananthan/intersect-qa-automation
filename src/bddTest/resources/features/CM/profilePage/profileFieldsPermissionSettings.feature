@CM @MATCH-493
Feature: Community User - Profile Fields Permission Settings
  As a Community user I want each user profile field to have 'public', 'hidden', and 'connection only' permission
  levels available so I can control what information displays about me within the Community.

  @MATCH-494
  Scenario: As a Community user I want profile fields I mark as 'visible to all users' to display to all Community users when they view my profile irregardless if I am connected to them.
    Given HE I am logged in to Intersect HE as user type "administrator"
    And I am not connected to "PurpleHS User" user
    Then I open my profile tab
    And I click on Edit profile button
    And I set work email privacy to 'visible to all users', office phone privacy to 'connections only' and personal email privacy to 'visible to only me'
    And I Save changes
    Then HE I successfully sign out
    And HS I am logged in to Intersect HS as user type "default"
    And I search for "PurpleHE Automation" and open profile page of this user
    Then I check if I see fields with privacy set to 'visible to all users'



#  #This feature cannot be done because on 3rd step we cannot access Profile page (because of session issue - when HS ans HE users are logged in in the same browser). Browser redirects to homepage instead of Profile page.
#  @MATCH-495
#  Scenario: As a Community user I want profile fields I mark as 'visible to only me' to not display whatsoever to other Community users even if I am connected to them.
#    Given HE I am logged in to Intersect HE as user type "administrator"
#    And I am connected to HS user
#    Then I go to user profile page
#    And I click on Edit profile button
#    And I set work email privacy to 'visible to all users', office phone privacy to 'connections only' and personal email privacy to 'visible to only me'
#    And I Save changes
#    Then HE I successfully sign out
#    And HS I am logged in to Intersect HS as user type "default"
#    And I search for "PurpleHE Automation" and open profile page of this user
#    Then I check if I see fields with privacy set to 'visible to only me'
#    And HS I successfully sign out



# #This feature cannot be done because on 3rd step we cannot access Profile page (because of session issue - when HS ans HE users are logged in in the same browser). Browser redirects to homepage instead of Profile page.
#  @MATCH-496
#  Scenario: As a Community user I want profile fields I mark as 'connections only' to display to only the Community users I am connected to in the Community.
#    Given HE I am logged in to Intersect HE as user type "administrator"
#    And I am connected to HS user
#    Then I go to user profile page
#    And I click on Edit profile button
#    And I set work email privacy to 'visible to all users', office phone privacy to 'connections only' and personal email privacy to 'visible to only me'
#    And I Save changes
#    Then HE I successfully sign out
#    And HS I am logged in to Intersect HS as user type "default"
#    And I search for "PurpleHE Automation" and open profile page of this user
#    Then I check if I see fields with privacy set to 'connections only'
#    And HS I successfully sign out