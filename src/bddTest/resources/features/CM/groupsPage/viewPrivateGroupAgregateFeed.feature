@CM @MATCH-712
Feature: Community User - View Private Group Aggregate Feed Content
  As a Community user I want to view an aggregate feed of recent content and activity within a
  Private group so I can quickly determine what has happened since the last time I viewed the group.

  @MATCH-713
  Scenario: As a Community user I am unable to see a Private groups aggregate feed if I am not a member of the group.
    Given HE I am logged in to Intersect HE as user type "administrator"
    And I search for "**Test Automation** HE Community PRIVATE Group" group
    And I am sure that user is not a member of the group
    Then I check if I cannot see post with text "**Test Automation** HE Community PRIVATE Group post."

  #This scenario may fail on ste when approving request to join the private group due to session issue (not possible to go to /groups page)
  @MATCH-715
  Scenario: As a Community user I am able to see a Private groups aggregate feed if I am a member of the group.
    Given HE I am logged in to Intersect HE as user type "administrator"
    And I search for "**Test Automation** HE Community PRIVATE Group" group
    And I am sure that user is a member of the private group
    And I search for "**Test Automation** HE Community PRIVATE Group" group
    Then I check if I can see post with text "**Test Automation** HE Community PRIVATE Group post."

  @MATCH-717
  Scenario: As a Community user viewing a group that is private and I am not a member of I am presented with a message that this group is private and their content only displays to group members.
    Given HE I am logged in to Intersect HE as user type "administrator"
    # Community does not behave well when switching between apps and then searching, so you have to search twice.
    And I search for "**Test Automation** HE Community PRIVATE Group" group
    And I search for "**Test Automation** HE Community PRIVATE Group" group
    And I am sure that user is not a member of the group
    Then I check if the massage is displayed "You must be a member to see group content."
