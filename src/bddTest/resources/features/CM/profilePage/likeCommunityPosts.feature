@CM @MATCH-1805
Feature: Community User - Like Community Posts
  As a Community user I want to be able to 'like' posts I find within the Community so other Community users know when I value their post and its content.


  Scenario: Community user can like and unlike any post/comment they have access to see by clicking on the heart icon
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then I go to the home page
    And I check if Hobsons institution post is visible
    Then I like the Hobsons post
    And I check if the Hobsons post is liked
    Then I unlike the Hobsons post
    And I check if Hobsons post is unliked
    And HE I successfully sign out


  Scenario: Community user can like and unlike their own post/comment
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then I go to user profile page
    And I check if Profile posts are visible
    Then I like my own post
    And I check if the my own post is liked
    Then I unlike the my own post
    And I check if my own post is unliked
    And HE I successfully sign out


  Scenario: If another Community user likes one of my posts/comments, a Community notification is created
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then I go to user profile page
    And I create new user post with text "This post is generated by TestAutomation USER!"
    And HE I successfully sign out
    Then SP I am logged in to the Admin page as an Admin user
    And I search for "PurpleHE Automation" and open profile page of this user
    Then I like post "This post is generated by TestAutomation USER!"
    And SP I successfully sign out
    Then HE I am logged in to Intersect HE as user type "administrator"
    Then I go to Counselor Community page
    And I open Notifications list
    And I check if user has new notification for post like
    And HE I successfully sign out


  Scenario: If another Community user unlikes one of my posts/comments, do NOT create a Community notification
    Then SP I am logged in to the Admin page as an Admin user
    And I search for "PurpleHE Automation" and open profile page of this user
    Then I unlike post "This post is generated by TestAutomation USER!"
    And SP I successfully sign out
    Then HE I am logged in to Intersect HE as user type "administrator"
    Then I go to Counselor Community page
    And I open Notifications list
    And I check if new notification for post like is not raised
    And HE I successfully sign out