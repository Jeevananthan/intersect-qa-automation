@CM
Feature: As an HE or Support user I want to login to the Community

  Scenario: As an HE user I'm able to log in to the Community
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then I am able to successfully login

  @MATCH-1179
  Scenario: As a Support user I'm able to log in to the Community
    Given SP I am logged in to the Admin page as an Admin user
    Then I am able to successfully login as a support user