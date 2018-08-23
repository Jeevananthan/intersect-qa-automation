@HS
Feature: HS - RepVisits - Overview - As an HS user, I should be able to see an overview of my upcoming visit and fair appointments

  @MATCH-2833
  Scenario: As an HS RepVisits user I want to see a message on the RepVisits Overview page that informs me I have no
  upcoming appointments (visits OR fairs) for the next week so I can quickly know I don't have any colleges
  visiting my high school over the next 7 days.
    Given HS I want to login to the HS app using "purpleheautomation+hstest@gmail.com" as username and "Password!1" as password
    Then HS I navigate to the "Calendar" page in RepVisits
    Then HS I cancel all events for the next 7 days
    Then HS I navigate to the "Overview" page in RepVisits
    Then HS I verify the RepVisits Overview page when no events are scheduled for the next 7 days
    Then HS I successfully sign out
