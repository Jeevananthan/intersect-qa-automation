@HS @HS2
Feature: HS - Community - Community - As an HS user, I can view HE institution data

  @MATCH-1904
  Scenario: HS user - Verify access to Hubs View mode
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    When SP I search for "The University of Alabama" as an Institution in the global search box
    And SP I select "The University of Alabama" from the global search results
    Then SP I verify Hubs view mode for "The University of Alabama"
    And HS I successfully sign out

  @MATCH-1658
  Scenario Outline: HS Community User - Add redirect rule for high school users that view college hub pages
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    When SP I search for "<institution>" as an Institution in the global search box
    And SP I select "<institution>" from the global search results
    Then HS I verify the URL "<beforeClickingBackToIntersectLink>" of "Additional info" page before clicking "Back to Intersect" link using "<SCID>","<institution>","/info" in the college profile page
    Then HS I verify the URL "<afterClickingBackToIntersectLink>" after clicking "Back to Intersect" link using "<institutionID>","/info" in the college profile page
    And HS I successfully sign out

    Examples:
      |SCID |institution              |institutionID|beforeClickingBackToIntersectLink                          |afterClickingBackToIntersectLink                             |
      |636  |The University of Alabama|636      |https://qa-hs.intersect.hobsons.com/community/institution/ |https://qa-hs.intersect.hobsons.com/community/institution/|
#      |705  |Alpena Community College |3300669      |https://qa-hs.intersect.hobsons.com/community/institution/ |https://qa-hs.intersect.hobsons.com/community/institution-id/|institution-id/|

  @MATCH-2652
  Scenario: As a nonNaviance HS, Freemium HE, or Premium HE user who is receiving their 'Welcome to Counselor Community/Intersect'
  email with their user credentials to access the system for the first time I want to see more accurate information in
  the email so I can appropriately contact Support, if needed, and, ideally, log in on my own.
    Then SP I am logged in to Support for Intersect
    Then SP I search for "Alabama" in Support
    Then SP I click in "See All Users" link
    And  SP I "Re-invite" to "purpleheautomation+marketing543210@gmail.com"
    Then HE I verify that the Email Notification Message says: "(.*)Intersect by Hobsons(.*)purpleheautomation(.*)marketing543210@gmail.com(.*)Hobsons Support(.*)support@purpledev.hobsonspobox.net(.*)"
      |Subject                                                        |To                            |Messages |
      |Your Intersect Invitation | purpleheautomation+marketing543210@gmail.com|1        |
