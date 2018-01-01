@MATCH-1804
  Feature: As a Community user I want the hyperlink and image icons/actions available to me when
    replying to an already created post so I can include rich media content in my reply too.

  Scenario: As a Community user I want the hyperlink and image icons/actions available to me when replying to an already created post
    Given I am logged in to Purple Community through the HE App
    When I go to the home page
    Then I click on comment icon
    And I check if I see hyperlink and image icons/actions when replying to an already created post
    And I sign out from the HE app


  Scenario: As a Community user I want the hyperlink and image icons/actions available to me when user is creating a new post
    Given I am logged in to Purple Community through the HE App
    When I go to user profile page
    Then I check if I see hyperlink and image icons/actions when user is creating a new post
    And I sign out from the HE app
