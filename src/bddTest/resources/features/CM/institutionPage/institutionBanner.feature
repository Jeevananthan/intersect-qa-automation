Feature: Community User - View HE/HS Institutional Banner
  As a Community user or institution while viewing an HE/HS institution within the Community I want to see a banner
  that contains high level information on that institution so I can quickly learn about that insitution before
  navigating the rest of its profile.

  @MATCH-921
  Scenario: Check if institution banner exists on the institution page
    Given I am logged in to Purple Community through the HE App
    And I go to institution page
    And I go to Hobsons institution page
    Then I check if institution banner exists