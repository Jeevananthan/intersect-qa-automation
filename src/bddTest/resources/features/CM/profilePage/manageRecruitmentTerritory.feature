@CM
Feature: HE Community User - Manage Recruitment Territory within User Profile
  As an HE Community user I need to manage my specific recruitment territory so the correct HS Community users can
  find, connect with me, and see an accurate territory for me at all times.

  @MATCH-918
  Scenario: As an HE Community user I can manage my specific recruitment territory
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then I go to user profile page
    And I click on Edit profile button
    Then I select "California" state
    And I Save changes
    Then I check if "California" state is saved