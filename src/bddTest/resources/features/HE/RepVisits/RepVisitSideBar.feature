@HE
  Feature:HE User - Check Availability Sidebar Page for Individual HS


  @MATCH-1596
  Scenario:As an HE user, I want to be able to view each high school's availability from the HS profile page in Community, so that I can see what times are available to visit.

    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I navigate to the "Travel Plan" page in RepVisits
    Then HE I verify the school "Int Qa High School 4" and delete if it is added to travel plan
    And  HE I search for "Int Qa High School 4" in "Institutions"
    And  HE I select "Int Qa High School 4" from the results
    When HE I select Check RepVisits Availability in the community page
    Then HE I verify the side bar popup has "Int Qa High School 4" as school Name on the header
    Then HE I verify default sidebar has visits tab and toggle between tabs
    Then HE I verify the Start and End Dates displayed on the side bar
    And  HE I verify the Add to Travel Plan on the "Visits" toggle
    And  HE I click on add to travel plan on footer
    When HE I click on the side bar 'Fairs' toggle bar
    And  HE I verify the Add to Travel Plan on the "Fairs" toggle
    And  HE I click on add to travel plan on footer
    When HE I click on outside of the Repvisits side bar
    Then HE I verify the Repvisits side bar closed


    @MATCH-2081
    Scenario Outline:As an HE user, I want the current high school availability sidebar, when the fair toggle,is enabled to only display fairs that have been published by the high school so I cannot see a fair that the high school has not yet published.

      #verifying a published fair
      Given HS I am logged in to Intersect HS as user type "HSadmin1"
      Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
      Then HS I verify the Success Message for the College Fair "<College Fair Name>"
      And HS  I Store the college Fair Name created
      And HS I Click on the "Close" button in the success page of the college fair
      And  HS I successfully sign out

      Given HE I am logged in to Intersect HE as user type "administrator"
      When  I navigate to Counselor Community page
      And  HE I search for "Fabens High School" in "Institutions"
      And  HE I select "Fabens High School" from the results
      When HE I select Check RepVisits Availability in the community page
      Then HE I verify the side bar popup has "Fabens High School" as school Name on the header
      When HE I click on the side bar 'Fairs' toggle bar
      Then HE I verify the recently "Published" Fair on the fairs toggle
      And  HE I successfully sign out

       #verifying a unpublished fair
      Given HS I am logged in to Intersect HS as user type "HSadmin1"
      Then HS I Click on the View Details button for the College Fair Event "<College Fair Name>"
      And HS I click on Edit button to edit fair
      And HS I click on Unpublish option on repvisits sidebar
      Then HS I verify Fair unpublished message on the repvisits sidebar
      And HS I Click on the "Close" button in the success page of the college fair
      And HS I cancel the fair of name "PreviouslySetFair" with the reason "test"
      And  HS I successfully sign out

      Given HE I am logged in to Intersect HE as user type "administrator"
      When  I navigate to Counselor Community page
      And  HE I search for "Fabens High School" in "Institutions"
      And  HE I select "Fabens High School" from the results
      When HE I select Check RepVisits Availability in the community page
      Then HE I verify the side bar popup has "Fabens High School" as school Name on the header
      When HE I click on the side bar 'Fairs' toggle bar
      Then HE I verify the recently "UnPublished" Fair on the fairs toggle

      Examples:
        |College Fair Name                 |Date|Start Time|End Time|RSVP Deadline|Cost|Max Number of Colleges|Number of Students Expected| ButtonToClick |Cost|
        |Publish/UnpublishWhenToggleON/OFF|35  |0900AM    |1000AM  |7            |$25 |25                    |100                        | Save          |$25 |


