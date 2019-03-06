@MATCH-704
Feature: Community User - View Public Group Aggregate Feed Content
  As a Community user or institution I want to view an aggregate feed of recent content and activity within a
  Public group so I can quickly determine what has happened since the last time I viewed the group.


  @MATCH-706
  Scenario: As a community user I can view an aggregate feed of recent content and activity of a Public group I have joined.
    Given HE I am logged in to Intersect HE as user type "administrator"
    And I search for "**Test Automation** HE Community PUBLIC Group" group
    And I am sure that user is a member of the public group
    And I search for "**Test Automation** HE Community PUBLIC Group" group
    Then I check if I can see post with text "**Test Automation** HE Community PUBLIC Group"


  @MATCH-708
  Scenario: As a community user I can view an aggregate feed of recent content and activity of a Public group I have not joined.
    Given HE I am logged in to Intersect HE as user type "administrator"
    And I search for "**Test Automation** HE Community PUBLIC Group" group
    And I am sure that user is not a member of the group
    Then I check if I can see post with text "**Test Automation** HE Community PUBLIC Group"
