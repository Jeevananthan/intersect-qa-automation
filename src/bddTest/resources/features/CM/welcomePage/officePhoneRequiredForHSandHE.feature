@MATCH-1796
Feature: As an HE or HS user I want my office phone number to be required so that information can be used for additional RepVisit features.

@ignore
Scenario: The HS user profile activation page requires Office Phone
  Given HS I am logged in to Intersect HS as user type "default"
  When I am sure that HS user will be logged in for the first time and HS Welcome page will be opened
  And I go to HS Counselor Community page
  Then I check if office phone is required field
  #Then I upload Profile and Banner pictures
  And I populate all the fields on Welcome page
  And I set the EU citizen to "Yes"
  And I accept Terms and conditions
  And I consent to create and maintain my Intersect account
  Then I Save changes


@ignore
Scenario: The HE user profile activation page requires Office Phone
  Given HE I am logged in to Intersect HE as user type "administrator"
  When I am sure that HE user will be logged in for the first time and Welcome page will be opened
  And I go to Counselor Community page
  Then I check if office phone is required field
  #Then I upload Profile and Banner pictures
  And I populate all the fields on Welcome page
  And I set the EU citizen to "Yes"
  And I accept Terms and conditions
  And I consent to create and maintain my Intersect account
  Then I Save changes



Scenario: The HS update user profile page requires Office Phone
  Given HS I am logged in to Intersect HS as user type "default"
  When I go to HS user profile page
  And I click on Edit profile button
  Then I check if office phone is required field


# This scenario is covered by MATCH-873 (Scenario MATCH-875)
#Scenario: The HE update user profile page requires Office Phone



# This scenario is covered by MATCH-873 (Scenario MATCH-875) for HE user and by "Scenario: The HS update user profile page requires Office Phone" for HS user
#Scenario: Currently HE and HS community users who have already activated their profiles are required to provide a Office Phone, if not already provided, the next time they update their profile