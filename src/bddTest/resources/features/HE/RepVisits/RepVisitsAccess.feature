@HE
Feature: HE- RepVisits - RepVisitsAccess - As an HE user, I want to be able to access the RepVisits features based on my role/subscription

  @MATCH-1697
  Scenario: As an HE user I want to be able to access RepVisit functionality within Intersect so I can find value from this new module and its features
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify the following tabs exist on the RepVisits page
      |Overview |Search and Schedule |Calendar |Travel Plan |Contacts |Recommendations |Notifications|
    And HE I successfully sign out

  @MATCH-1667
  Scenario: As an HE user, I should be able to see Check RepVisits Availability button and Availablity sidebar from HS instituion profile
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "Int QA High School 4" in "Institutions"
    And HE I select "Int QA High School 4" from the results
    Then HE I verify the Check RepVisits Availability button
    And HE I successfully sign out

  @MATCH-1610
  Scenario: As an HE Community member,I need to view a calendar of my appointments
            so that I can easily see what my day/week/month schedule looks like.
    Given HE I am logged in to Intersect HE as user type "community"
    And HE I verify the calendar view in repvisits
    And HE I successfully sign out

  @MATCH-1935 @MATCH-1934 @MATCH-1936 @MATCH-2274
  Scenario: As an HE user tied to an HE account that DOES NOT have the Intersect Presence Subscription activated,
  I need to see an upgrade message on the Contacts,Recommendations,Travel Plan and Visit Feedback tabs.
  #logging to support app to do pre-requisites that is inactive the ''Intersect Presence Subscription'' module
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    And SP I set the "Intersect Presence Subscription" module to "inactive" in the institution page
    And SP I Click the Save Changes button
    Then SP I successfully sign out
  #logging to HE app to verify the upgrade message for contacts, recommendations,Travel Plan and Visit Feedback tabs
    Given HE I am logged in to Intersect HE as user type "limited"
    Then HE I verify the following tabs exist on the RepVisits page
      |Overview |Search and Schedule |Calendar |Travel Plan |Contacts |Recommendations |Notifications| Visit Feedback|
    Then HE I verify the upgrade messaging on the Contacts page in RepVisits
    Then HE I verify the upgrade messaging on the Recommendations page in RepVisits
    Then HE I verify the upgrade messaging on the Travel Plan page in RepVisits
    And HE I navigate to the "Visit Feedback" page in RepVisits
    Then HE I verify the freemium messaging on the Visits Feedback page
    Then HE I successfully sign out

  @MATCH-2274
  Scenario: As a HE non-Administrator user, I want to ensure the no access messaging on the Visit Feedback section of the RepVisits page.
    Given HE I am logged in to Intersect HE as user type "community"
    Then HE I navigate to the "Visit Feedback" page in RepVisits
    And HE I verify the non-administrator messaging on the Visits Feedback page
    And HE I successfully sign out

  @MATCH-1989
  Scenario: As an HE user tied to an HE account that has not paid for the Intersect Presence Subscription.
  I want to be presented with a popup/form that allows me to inquire about upgrading my HE account

    #logging to support app to do pre-requisites that is inactive the ''Intersect Presence Subscription'' module
    Given SP I am logged in to the Admin page as an Admin user
    When SP I search for "2100209"
    And SP I select the following institution "Bowling Green State University-Main Campus" from the results
    And SP I set the "Legacy: Hub page management" module to "inactive" in the institution page
    And SP I set the "Legacy: Community" module to "inactive" in the institution page
    And SP I set the "Intersect Awareness Subscription" module to "inactive" in the institution page
    And SP I set the "Intersect Presence Subscription" module to "inactive" in the institution page
    And SP I Click the Save Changes button
    Then SP I successfully sign out

    #logging to HE app to verify the upgrade message for contacts and recommendations tabs
    Given HE I am logged in to Intersect HE as user type "limited"
    Then HE I verify the upgrade messaging on the Contacts page in RepVisits
    And HE I click the upgrade button
    Then HE I verify the Upgrade popup and the details displayed in the popup
      |First Name |Last Name  |Work Email Address                       |
      |PurpleHE   |Limited    |purpleheautomation+limited@gmail.com     |
    Then HE I verify the upgrade messaging on the Recommendations page in RepVisits
    And HE I click the upgrade button
    Then HE I verify the Upgrade popup and the details displayed in the popup
      |First Name |Last Name  |Work Email Address                   |
      |PurpleHE   |Limited    |purpleheautomation+limited@gmail.com |
    Then HE I verify the upgrade messaging on the Travel Plan page in RepVisits
    And HE I click the upgrade button
    Then HE I verify the Upgrade popup and the details displayed in the popup
      |First Name |Last Name  |Work Email Address                   |
      |PurpleHE   |Limited    |purpleheautomation+limited@gmail.com |
    Then HE I successfully sign out

  @MATCH-2133
  Scenario: As an HE User, I want to be able to view the weekly recurring time slots and able to view the UI for the "Search and Schedule" Page
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify the Search heading over the search bar in Search and Schedule Tab
    And HE I search for "Int Qa High School 4" in RepVisits page
    Then HE I verify the Search heading over the search bar after search the school in Search and Schedule Tab
    Then HE I verify the Schedule heading over the availability block
    Then HE I verify the calender icon is present next to date
    Then HE I verify the date and calendar icon present over the availability table
    Then HE I verify the next and previous buttons at the top, far right of the availability table
    Then HE I verify the view type button to the left of the next/previous buttons
    Then HE I verify the color of the active view type button
    Then HE I verify "Showing All Scheduled Fairs" Text in Fairs Tab in Search and Schedule Tab
    Then HE I verify the Your Schedule Text in Search and Schedule Page
    And HE I search for "Mays High School" in RepVisits page
    Then HE I verify the Map in SearchAndSchedule Page
    Then HE I successfully sign out

  @MATCH-2485
  Scenario: Issue: For HE users viewing their travel plan, the "see details" link for college fairs
  opens the HS in the visits view.
    Given SP I am logged in to the Admin page as an Admin user
    When SP I search for "2400006"
    And SP I select "The University of Alabama" from the global search results
    Then SP I set the "Intersect Presence Subscription" module to "active" in the institution page
    And SP I successfully sign out

    Given HS I want to login to the HS app using "purpleheautomation+hstest@gmail.com" as username and "Password!1" as password
    Then HS I go to the repvisits page
    Then HS I select "All RepVisits Users" to show view availability
    And HS I successfully sign out

    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify the see details link in RepVisits
    And HE I successfully sign out
    
  @MATCH-3065
  Scenario: As a RepVisits Admin User
  I want to be able to configure email forwarding of my ActiveMatch and ActiveMatch Events Reports
  So that I can keep non RV Using members of my school staff informed
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I navigate to the Institution Notification page
    Then HE I verify the Institution Notification page
    Then HE I validate the Checkbox in the Institution Notification page using "purple HEadmin"
    Then HE I validate the Email in the Institution Notification page using "purpleheautomation@gmail.com","purpleheautomation+admin@gmail.com",",purpleheautomation+admin@gmail.com"
    Then HE I successfully sign out

    Given HE I am logged in to Intersect HE as user type "community"
    Then HE I verify the Non-admins do not have the tab in navigation
    Then HE I verify the Non-admins cannot reach the page directly by URL
    Then HE I successfully sign out

  @MATCH-2238
  Scenario: Verify Overview page when HE user DOES NOT have Intersect subscription activated
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I select "The University of Alabama" from the institution dashboard
    And SP I set the "Intersect Presence Subscription" module to "inactive" in the institution page
    And SP I Click the Save Changes button
    Then SP I successfully sign out
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I navigate to the "Overview" page in RepVisits
    Then HE I verify the Repvisits Overview Upgrade Subscription page
    Then HE I successfully sign out
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I select "The University of Alabama" from the institution dashboard
    And SP I set the "Intersect Presence Subscription" module to "active" in the institution page
    And SP I Click the Save Changes button
    Then SP I successfully sign out