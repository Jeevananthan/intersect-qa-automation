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

  @MATCH-1989
  Scenario: As an HE user tied to an HE account that has not paid for the Intersect Presence Subscription.
          I want to be presented with a popup/form that allows me to inquire about upgrading my HE account

   #logging to support app to do pre-requisites that is inactive the ''Intersect Presence Subscription'' module

    Given SP I am logged in to the Admin page as an Admin user
    Then SP I select "The University of Alabama" from the institution dashboard
    And SP I set the "Legacy: Hub page management" module to "active" in the institution page
    And SP I set the "Legacy: Community" module to "active" in the institution page
    And SP I set the "Intersect Awareness Subscription" module to "inactive" in the institution page
    And SP I set the "Intersect Presence Subscription" module to "inactive" in the institution page
    And SP I Click the Save Changes button
    Then SP I successfully sign out
   #logging to HE app to verify the upgrade message for contacts and recommendations tabs
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify the upgrade messaging on the Contacts page in RepVisits
    And HE I click the upgrade button
    Then HE I verify the Upgrade popup and the details displayed in the popup
      |First Name |Last Name  |Work Email Address           |
      |PurpleHE   |Automation |purpleheautomation@gmail.com |
    Then HE I verify the upgrade messaging on the Recommendations page in RepVisits
    And HE I click the upgrade button
    Then HE I verify the Upgrade popup and the details displayed in the popup
      |First Name |Last Name  |Work Email Address           |
      |PurpleHE   |Automation |purpleheautomation@gmail.com |
    Then HE I verify the upsell messaging on the Travel Plan page in RepVisits
    And HE I click the upgrade button
    Then HE I verify the Upgrade popup and the details displayed in the popup
      |First Name |Last Name  |Work Email Address           |
      |PurpleHE   |Automation |purpleheautomation@gmail.com |
    Then HE I successfully sign out
   #logging to support app to active the ''Intersect Presence Subscription'' module
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I select "The University of Alabama" from the institution dashboard
    And SP I set the "Legacy: Hub page management" module to "active" in the institution page
    And SP I set the "Legacy: Community" module to "active" in the institution page
    And SP I set the "Intersect Awareness Subscription" module to "active" in the institution page
    And SP I set the "Intersect Presence Subscription" module to "active" in the institution page
    And SP I Click the Save Changes button
    Then SP I successfully sign out