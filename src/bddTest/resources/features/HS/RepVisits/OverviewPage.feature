@HS @HS1
Feature: HS - RepVisits - OverviewPage - As an HS user, I should be able to see an overview of my upcoming visit and fair appointments

  @MATCH-2833
  Scenario: As an HS RepVisits user I want to see a message on the RepVisits Overview page that informs me I have no
  upcoming appointments (visits OR fairs) for the next week so I can quickly know I don't have any colleges
  visiting my high school over the next 7 days.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I navigate to the "Calendar" page in RepVisits
    Then HS I cancel all events for the next 7 days
    Then HS I navigate to the "Overview" page in RepVisits
    Then HS I verify the RepVisits Overview page when no events are scheduled for the next 7 days


