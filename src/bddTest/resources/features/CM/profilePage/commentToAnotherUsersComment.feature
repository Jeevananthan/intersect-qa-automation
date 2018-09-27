@MATCH-1809
Feature: As a Community user I want to comment directly to another Community user's comment to another user's post
  so I don't have to comment to the original post and can comment directly to the user who made a comment.

  Scenario: Community user sees a comment icon/action below any reply
    Given HE I am logged in to Intersect HE as user type "administrator"
    When I go to the home page
    Then I create new user post with text "Test post is created by test automation script"
    And I write a comment with text "New comment to this post" on the post "Test post is created by test automation script"
    Then I check if I see a comment icon/action below reply "New comment to this post"
    Then I go to user profile page
    And I delete created post



  Scenario: Error messaging and required inputs work the same for commenting on a comment as they do for commenting on a post
    Given HE I am logged in to Intersect HE as user type "administrator"
    When I go to the home page
    Then I create new user post with text "Test post is created by test automation script"
    And I write a comment with text "New comment to this post" on the post "Test post is created by test automation script"
    And I go to the home page
    And I click on new comment icon to the post "Test post is created by test automation script" and reply "New comment to this post"
    Then click on the Post button
    And I check if I can see "Comment text is required." as a placeholder
    Then I go to user profile page
    And I delete created post



  Scenario: Notification is generated to the user that wrote the comment that was commented on
    Given HE I am logged in to Intersect HE as user type "administrator"
    When I am connected to HS user
    And I go to the home page
    Then I create new user post with text "Test post is created by test automation script"
    And I write a comment with text "New comment to this post" on the post "Test post is created by test automation script"
    Then I clear all the notifications
    And HE I successfully sign out
    When HS I am logged in to Intersect HS as user type "default"
    And I go to the HS home page
    And I add comment to the post "Test post is created by test automation script" and reply "New comment to this post" with text "This is a comment on the post reply"
    And HS I successfully sign out
    Then HE I am logged in to Intersect HE as user type "administrator"
    And I open Notifications list
    And I check if user has new notification
    Then I go to user profile page
    And I delete created post

