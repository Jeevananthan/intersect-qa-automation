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


  @MATCH-1602
  Scenario: As an HE user I want to be able to use the Search and Schedule tab of RepVisits to browse HS availability.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify the Search and Schedule tab of the RepVisits page
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

