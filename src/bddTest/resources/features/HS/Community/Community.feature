@HS
Feature: As a HS user, I want to be able to access HUBS Edit Mode

  @MATCH-1904
  Scenario: HS user cannot access Hubs View mode (bug)
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    When SP I search for "The University of Alabama" as an Institution in the global search box
    And SP I select "The University of Alabama" from the global search results
    Then SP I verify Hubs view mode for "The University of Alabama"
    And HS I successfully sign out

  @MATCH-1658
  Scenario Outline: HS Community User - Add redirect rule for high school users that view college hub pages
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    When SP I search for "<institution>" as an Institution in the global search box
    And SP I select "<institution>" from the global search results
    Then HS I verify the URL "<beforeClickingBackToIntersectLink>" of "Additional info" page before clicking "Back to Intersect" link using "<SCID>","<institution>","/info" in the college profile page
    Then HS I verify the URL "<afterClickingBackToIntersectLink>" after clicking "Back to Intersect" link using "<institutionID>","/info" in the college profile page
    And HS I successfully sign out

    Examples:
      |SCID |institution              |institutionID|beforeClickingBackToIntersectLink                          |afterClickingBackToIntersectLink                             |
      |636  |The University of Alabama|2400006      |https://qa-hs.intersect.hobsons.com/community/institution/ |https://qa-hs.intersect.hobsons.com/community/institution-id/|
#      |705  |Alpena Community College |3300669      |https://qa-hs.intersect.hobsons.com/community/institution/ |https://qa-hs.intersect.hobsons.com/community/institution-id/|