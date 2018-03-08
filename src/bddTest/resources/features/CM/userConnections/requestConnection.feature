@CM
Feature: Community User - Request to Connect to Another User
  As a Community user I need to be able to request to 'connect' (friend) to another community user so I can build
  out my personal network within the Community.

  @MATCH-400
  Scenario: As a Community user I can take a 'connect' action when viewing another community user's profile
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then I search for "MatchSupportUIQA4" and open profile page of this user
    And I check if I can connect to the user
    And HE I successfully sign out


  @MATCH-402
  Scenario: As a Community user, after taking the 'connect' action, I see a confirmation message that confirms I want to request a connection with that user.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then I search for "MatchSupportUIQA4" and open profile page of this user
    And I click on connect button
    And I check if confirmation message is displayed
    And HE I successfully sign out


  @MATCH-403
  Scenario: As a Community user, after taking the 'connect' action, the confirmation message also includes a message text box where I can type a personal message to the user I am requesting to connect with.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then I search for "MatchSupportUIQA4" and open profile page of this user
    And I click on connect button
    Then I check if confirmation message includes message box and Send Invite button
    And I write "Please confirm my connection request!" to the box
    And HE I successfully sign out


