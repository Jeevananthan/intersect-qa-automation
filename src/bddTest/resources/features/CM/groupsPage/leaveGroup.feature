@CM @MATCH-670
Feature: Community User - Leave Group
  As a Community user I want to leave Public groups that I've already joined in the Community so I
  no longer see them in my activity feed when I am no longer have interest in the group.

  @MATCH-671
  Scenario: As a Community user I am presented with a 'Leave' action when viewing an individual group's page I have previously have joined.
    Given HE I am logged in to Intersect HE as user type "administrator"
    And I search for "**Test Automation** HE Community PUBLIC Group" group
    And I am sure that user is a member of the group
    Then I see 'Leave' action on the page
    And HE I successfully sign out

  @MATCH-673
  Scenario: As a Community user I am presented with a 'Leave' action next to each group when viewing the 'My Groups' page.
    Given HE I am logged in to Intersect HE as user type "administrator"
    And I search for "**Test Automation** HE Community PUBLIC Group" group
    And I am sure that user is a member of the group
    Then I open my groups tab
    Then I see 'Leave' action on the page
    And HE I successfully sign out


  @MATCH-677
  Scenario: As a Community user when I perform the 'Leave' action that group's content no longer displays in my aggregate feed moving forward.
    Given HE I am logged in to Intersect HE as user type "administrator"
    And I search for "**Test Automation** HE Community PUBLIC Group" group
    And I am sure that user is a member of the group
    Then I open home tab
    And I check if I see posts from the group
    Then I search for "**Test Automation** HE Community PUBLIC Group" group
    And I am sure that user is not a member of the group
    Then I open home tab
    And I check if I do not see posts from the group
    And HE I successfully sign out



  @MATCH-679
  Scenario: As a Community user when I perform the 'Leave' action that group no longer displays on My Groups page.
    Given HE I am logged in to Intersect HE as user type "administrator"
    And I search for "**Test Automation** HE Community PUBLIC Group" group
    And I am sure that user is a member of the group
    Then I open my groups tab
    And I leave the "**Test Automation** HE Community PUBLIC Group" group
    And I check if "**Test Automation** HE Community PUBLIC Group" group" is no longer visible in my groups list
    And HE I successfully sign out
