@HS
Feature:  As an HS user, I want to be able to access the features of Community page.

  @MATCH-1496
  Scenario: As an HS user I want the Intersect left navigation bar to be better organized and labeled.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I verify the left navigation bar and section breadcrumbs are as follows
      | Awareness | Counselor Community |
    Then HS I verify the left navigation bar and section breadcrumbs are as follows
      |Presence |RepVisits |
    And HS I successfully sign out

  @MATCH-2652
  Scenario: As a nonNaviance HS, Freemium HE, or Premium HE user who is receiving their 'Welcome to Counselor Community/Intersect'
  email with their user credentials to access the system for the first time I want to see more accurate information in
  the email so I can appropriately contact Support, if needed, and, ideally, log in on my own.
    Then SP I am logged in to Support for Intersect
    Then SP I search for "Alabama" in Support
    Then SP I click in "See All Users" link
    And  SP I "Re-invite" to "purpleheautomation+Match2652@gmail.com"
    Then HE I verify that the Email Notification Message says: "(.*)https://counselorcommunity.com(.*)purpleheautomation(.*)Match2652@gmail.com(.*)Hobsons Support(.*)counselorcommunity@purpledev.hobsonspobox.net(.*)"
      |Subject                                                        |To                            |Messages |
      |Your Intersect Invitation | purpleheautomation+Match2652@gmail.com|1        |
