@CM
Feature: Community User - Default Profile Contact Field Settings to Public Upon Profile Activation
  As a Community user I want my Community user profile fields privacy settings to be defaulted to 'public' when
  my Community user profile is first activated so other Community users can see as much about me as possible.


  @MATCH-512
  Scenario: As a Community user I need my profile fields privacy settings to be defaulted to 'public' when my Community user profile is first activated.
    Given HE I am logged in to Intersect HE as user type "administrator"
    And I am sure that HE user will be logged in for the first time and Welcome page will be opened
    And I go to Counselor Community page
    Then I check if my profile fields are set to 'public' by default
    And HE I successfully sign out


  @MATCH-513
  Scenario: As a Community user I need the ability to change my profile fields privacy settings if I don't want them all to be public.
    Given HE I am logged in to Intersect HE as user type "administrator"
    And I am sure that HE user will be logged in for the first time and Welcome page will be opened
    And I go to Counselor Community page
    Then I upload Profile and Banner pictures
    And I populate all the fields on Welcome page
    Then I set work email and office phone privacy to 'Connections Only'
    And I set personal email and mobile phone privacy to 'Visible to Only Me'
    And I Save changes
    Then I click on Edit profile button
    And I check if privacy settings are saved properly
    And HE I successfully sign out