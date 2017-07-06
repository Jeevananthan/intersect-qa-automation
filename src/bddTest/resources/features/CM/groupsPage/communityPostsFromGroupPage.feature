Feature: Community User - Create Community Post from Group Page
  As a Community user or institution I want to create posts from a Group page so other group members can read
  about the content I felt was relevant for the group.

  @MATCH-723
  Scenario: As a Community user I am able to create a post on a Public group page that I have joined.
    Given I am logged in to Purple Community through the HE App
    And I search for "**Test Automation** HE Community PUBLIC Group" group
    And I am sure that user is a member of the group
    And I create new user post
    Then I check if user post is created
    And I sign out from the HE app

  @MATCH-725
  Scenario: As a Community user I am unable to create a post on a Public group page that I have not joined.
    Given I am logged in to Purple Community through the HE App
    And I search for "**Test Automation** HE Community PUBLIC Group" group
    And I am sure that user is not a member of the group
    Then I check if user is unable to create a post
    And I sign out from the HE app

#  @MATCH-727
#  Scenario: As a Community user I am able to create a post on a Private group page that I am a member of.
#    Given I am logged in to Purple Community through the HE App
#    And I search for "**Test Automation** HE Community PRIVATE Group" group
#    And I am sure that user is a member of the private group
#    And I create new user post
#    Then I check if user post is created
#    And I sign out from the HE app

  @MATCH-729
  Scenario: As a Community user I am unable to create a post on a Private group page that I am NOT a member of.
    Given I am logged in to Purple Community through the HE App
    And I search for "**Test Automation** HE Community PRIVATE Group" group
    And I am sure that user is not a member of the group
    Then I check if user is unable to create a post
    And I sign out from the HE app