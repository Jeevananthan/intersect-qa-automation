@HE @ActiveMatch
Feature: As an HE user, I need to understand my Connection and Advansed Awareness subscription status so that I can configure or manage that subscription effectively

  @MATCH-4427 @MATCH-4397
  Scenario Outline:As an HE user, I need to understand my subscriptions that have been provisioned so that I can configure or manage that subscription effectively
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I navigate to the "<URL>" url
    Then I check if I can see "Understanding Your Subscriptions" on the page
    Then I check if I can see "Your Advanced Awareness and Connection Subscriptions" on the page
    Then I verify that the column headers in the "connections-table" table are the following:
      | Audience           |
      | Competitors        |
      | Majors             |
      | Connection         |
      | Academic Threshold |
    Examples:
      | URL                         |
      | connection/overview         |
      | advanced-awareness/overview |

  @MATCH-4395
  Scenario: As an HE user who is setting up my Advanced Awareness filters, I need a menu to help me navigate through the configuration pages
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I navigate to the "advanced-awareness/overview" url
    Then HE I click menu tab "Overview"
    Then I check if I can see "Your Advanced Awareness and Connection Subscriptions" on the page
    Then HE I click menu tab "Academic Threshold"
    Then I check if I can see "Default Filter Values" on the page
    Then HE I click menu tab "Diversity"
    Then I check if I can see "Diversity Settings" on the page
    Then HE I click menu tab "Competitors"
    Then HE I click menu tab "Majors"
    Then HE I click menu link "Understanding Your Subscriptions"
    Then I check if I can see "Your Advanced Awareness and Connection Subscriptions" on the page
    Then HE I click menu link "Academic Threshold"
    Then I check if I can see "Default Filter Values" on the page
    Then HE I click menu link "Diversity"
    Then I check if I can see "Diversity Settings" on the page
    Then HE I click menu link "Competitors"
    Then HE I click menu link "Majors"

  @MATCH-5097
  Scenario: As an HE user who is setting up my Connection filters, I need a menu to help me navigate through the configuration pages
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I navigate to the "connection/overview" url
    Then HE I click menu tab "Overview"
    Then I check if I can see "Configure Your Audience" on the page
    Then I check if I can see "Your Advanced Awareness and Connection Subscriptions" on the page
    Then HE I click menu tab "Academic Threshold"
    Then I check if I can see "Default Filter Values" on the page
    Then HE I click menu tab "Diversity"
    Then I check if I can see "Diversity Settings" on the page
    Then HE I click menu link "Understanding Your Subscriptions"
    Then I check if I can see "Your Advanced Awareness and Connection Subscriptions" on the page
    Then HE I click menu link "Academic Threshold"
    Then I check if I can see "Default Filter Values" on the page
    Then HE I click menu link "Diversity"
    Then I check if I can see "Diversity Settings" on the page
