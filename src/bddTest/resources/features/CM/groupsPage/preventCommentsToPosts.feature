@CM
Feature: Community Admin - No Commenting Setting for Groups
  As a Community Administrator I want to prevent comments to posts within certain groups in the Community so I can
  use a group as a push only communication tool for certain announcements.

  @MATCH-1177
  Scenario: As a Community Admin user I can manage whether each individual group in the Community allows comments to posts or not.
    Given I am logged in to Purple Community through the Support App
    Then I navigate to Groups page
    And I check if group with name "**AutoGenerated** HE Users Group - Comments Disabled" exists
    And I click on Add Group button
    Then I populate fields for new Group with name "**AutoGenerated** HE Users Group - Comments Disabled"
    And I select institution type "HE"
    And I disable comments on group posts
    And I click on Create button
    And I sign out from the Support app
    Then I am logged in to Purple Community through the HE App
    And I search for "***AutoGenerated** HE Users Group - Comments Disabled" group
    And I am sure that user is a member of the group
    And I create new user post with text "This post is generated by TestAutomation USER!"
    Then I check if comments are allowed to the post
    And I sign out from the HE app