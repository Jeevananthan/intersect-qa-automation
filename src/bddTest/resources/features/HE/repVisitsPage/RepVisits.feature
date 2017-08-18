@HE
Feature: As an HE user, I want to be able to access the features of RepVisits.

  @MATCH-1697
  Scenario: As an HE user I want to be able to access RepVisit functionality within Intersect so I can find value from this new module and its features
    Given HE I am logged in to Intersect HE as user type "administrator"
    #THE BELOW STEP CAN BE ADD IN MATCH#1732
    #And I verify "RepVisits" displays in left navigation bar under "Presence" heading
    Then HE I verify the following tabs exist on the RepVisits page
      |Overview |Search and Schedule |Calendar |Travel Plan |Contacts |Recommendations |Notifications|
    And HE I successfully sign out


  @MATCH-1602 @MATCH-1958
  Scenario: As an HE user I want to be able to use the Search and Schedule tab of RepVisits to browse HS availability.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify the Search and Schedule tab of the RepVisits page
    And HE I verify the Coming Soon message on the RepVisits Overview page
    And HE I successfully sign out


  @MATCH-1476 @MATCH-1902 @MATCH-1903 @MATCH-1774
  Scenario: As an HE user of an HE account with the Intersect Presence Subscription active I want to see the
            10 high schools returned in the scheduling results on a map so I can plan my high school visits
            travel efficiently by location.
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "Lebanon High School" in RepVisits
    Then HE I select "Lebanon High School" in "Lebanon, Ohio" from the RepVisits intermediate search results
    Then HE I view the map plugin on RepVisits Search & Schedule subtab
    And HE I select "Lebanon High School" from the RepVisists map plugin
    Then HE I verify the high school information popup contains the following data
      |School Name          |High School Contact: |Address                           |Phone          |District      |Type   |Senior Class Size |College Going Rate |
      |LEBANON HIGH SCHOOL  |No Contact           |1916 DRAKE RD LEBANON, Ohio 45036 |(513) 934-5105 |Lebanon City  |PUBLIC |335               |65                 |

  @MATCH-1936
  Scenario: As a HE user with Intersect Presence Subscription module Inactive.I should be able to see the
           upgrade message on Travel Plan sub menu in the repvisits page
  Given HE I want to login to the HE app using "mahibalan.k@indiumsoft.com" as username and "P@ssw0rd" as password
  Then HE I verify the upsell messaging on the Travel Plan page in RepVisits
  And HE I successfully sign out

  @MATCH-1667
  Scenario: As an HE user Check RepVisits Availability Button and Sidebar on HS Profiles
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "Int QA High School 4" in "Institutions"
    And HE I select "Int QA High School 4" from the results
    Then HE I verify the Check RepVisits Availability button
    And HE I successfully sign out

@MATCH-2169
Scenario Outline: HE Users - RepVisits - Availability Pills Updates
  Given HE I am logged in to Intersect HE as user type "administrator"
  And HE I search for school in RepVisits using "<SchoolName>"
  Then HE I select Visits to schedule the appointment for "<SchoolName>" using "<Date>" and "<heStartTime>"
  And HE I verify the schedule pop_up for "<SchoolName>" using "<heTime>" and "<hsEndTime>"
  And HE verify the Pills got disappear for "<heStartTime>"
  And HE I successfully sign out

  Examples:
    |hsEndTime     |Date|heStartTime |heTime|SchoolName              |
    |10:20pm       |25  |5:20pm      |5:20  |Int QA High School 4    |
