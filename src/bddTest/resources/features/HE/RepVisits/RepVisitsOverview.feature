@HE
Feature: HE- RepVisits - OverviewPage - As an HE user, I want to use the Overview page features.

  @MATCH-2241
  Scenario: As an HE RepVisits user I want to see a message on the RepVisits Overview page that informs me I have no
  upcoming appointments (visits OR fairs) for the next week.
    When HE I want to login to the HE app using "purpleheautomation+HEAuto_freshAccount@gmail.com" as username and "Password!1" as password
    Then HE I navigate to the "Overview" page in RepVisits
    Then HE I verify the RepVisits Overview page and Search and Schedule hyperlink when no events are scheduled for the next 7 days
    And HE I successfully sign out