@HE
Feature: HE - RepVisits - RepVisitsNavigation - As an HE user, I should be able to access the submenus
  @MATCH-3869
  Scenario: As a HE user with active presence subscription I want to navigate the submenus in RepVisits and see
  the Presence RepVisits branding in the header
    #Precondition
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I select "The University of Alabama" from the institution dashboard
    And SP I set the "Intersect Presence Subscription" module to "active" in the institution page
    And SP I Click the Save Changes button
    Then SP I successfully sign out
    #Test
    Given HE I am logged in to Intersect HE as user type "administrator"
    When  HE I navigate to the "Visit Feedback" page in RepVisits
    Then HE I verify the title "Presence RepVisits" in RepVisits branding header
    When  HE I navigate to the "Notifications" page in RepVisits
    Then HE I verify the title "Presence RepVisits" in RepVisits branding header
    When  HE I navigate to the "Recommendations" page in RepVisits
    Then HE I verify the title "Presence RepVisits" in RepVisits branding header
    When  HE I navigate to the "Contacts" page in RepVisits
    Then HE I verify the title "Presence RepVisits" in RepVisits branding header
    When  HE I navigate to the "Travel Plan" page in RepVisits
    Then HE I verify the title "Presence RepVisits" in RepVisits branding header
    When  HE I navigate to the "Calendar" page in RepVisits
    Then HE I verify the title "Presence RepVisits" in RepVisits branding header
    When  HE I navigate to the "Search and Schedule" page in RepVisits
    Then HE I verify the title "Presence RepVisits" in RepVisits branding header
    When  HE I navigate to the "Overview" page in RepVisits
    Then HE I verify the title "Presence RepVisits" in RepVisits branding header