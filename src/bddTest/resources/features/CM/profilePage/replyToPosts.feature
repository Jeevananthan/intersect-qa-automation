@MATC-799
Feature: Community User - Reply to Post
  As a Community user I want to reply to posts in the community so I can continue the conversation
  about that topic.


  @MATCH-800
  Scenario: As a Community user I can reply to other Community user's post.
    Given I am logged in to Purple Community through the HE App
    And I go to the home page
    Then I write a comment with text "AutoComment on the Hobsons post!" on the Hobsons post
    And I check if comment "AutoComment on the Hobsons post!" is posted on the Hobsons post
    And I sign out from the HE app


  @MATCH-802
  Scenario: As a Community user I can reply to my own posts.
    Given I am logged in to Purple Community through the HE App
    Then I go to user profile page
    And I create new user post with text "Autogenerated test post for comments."
    Then I write a comment with text "AutoComment on the post!" on the post "Autogenerated test post for comments."
    And I check if comment "AutoComment on the post!" is posted
    And I sign out from the HE app
