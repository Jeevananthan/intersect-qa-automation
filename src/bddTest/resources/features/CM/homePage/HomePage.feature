@CM
Feature: As a Freemium or Legacy Hubs HE user I want to see an additional Community Home page widget
         about all the neato features I am missing out on in the Community so I feel inclined to
         upgrade my HE institution's account to a premium one.

#  @MATCH-1550
# #  to be covered by MATCH-4631 and MATCH-4919
#  Scenario: As a Freemium user I want to see an additional Community Home page widget
#    Given SP I am logged in to the Admin page as an Admin user
#    Then SP I select "Bowling Green State University-Main Campus" from the institution dashboard
#    And SP I set the "Legacy: Hub page management" module to "inactive" in the institution page
#    And SP I set the "Legacy: Community" module to "inactive" in the institution page
#    And SP I set the "Intersect Awareness Subscription" module to "inactive" in the institution page
#    And SP I set the "Intersect Presence Subscription" module to "inactive" in the institution page
#    And SP I Click the Save Changes button
#    Then SP I successfully sign out
#    Given HE I am logged in to Intersect HE as user type "limited"
#    Then CM I verify that the upgrade widget is "visible" for "Freemium" users
#    And HE I successfully sign out
#
#  Scenario Outline: As a freemium Community user I want to see upgrade Community message and as a premium Community user I should not see the Upgrade Community message
#    Given SP I am logged in to the Admin page as an Admin user
#    Then SP I select "Bowling Green State University-Main Campus" from the institution dashboard
#    And SP I set the "<Subscription>" module to "active" in the institution page
#    And SP I Click the Save Changes button
#    Then SP I successfully sign out
#    Given HE I am logged in to Intersect HE as user type "limited"
#    Then CM I verify that the upgrade widget is "<Visibility>" for "<UserType>" users
#    And HE I successfully sign out
#    Cleanup
#    Given SP I am logged in to the Admin page as an Admin user
#    Then SP I select "Bowling Green State University-Main Campus" from the institution dashboard
#    And SP I set the "<Subscription>" module to "inactive" in the institution page
#    And SP I Click the Save Changes button
#    Then SP I successfully sign out
#
#  Examples:
#  |UserType            |Visibility    |Subscription                         |Status |
#  |Hubs Premium        |visible       |Legacy: Hub page management          |active |
#  |Community Premium   |not visible   |Legacy: Community                    |active |
#  |Awareness Premium   |not visible    |Intersect Awareness Subscription   |active |
#  |Presence Premium    |not visible    |Intersect Presence Subscription    |active |


  @MATCH-4894
  Scenario: As a Community user looking to make a post, I want to see instructions and details on exactly where I should
  be creating my post so it doesn't end up in the wrong place.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then I navigate to Counselor Community page
    Then I verify the instruction text in the post box
    Then I go to user profile page
    Then I verify the instruction text in the post box

  @MATCH-5471 @MATCH-5125
  Scenario: The h tags used in Community are not following appropriate accessibility standards and need re-updated.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then I navigate to Counselor Community page
    Then I verify that the "Community Home" header in "Home" tab is "h1"
    Then I verify that the "Your Profile" header in "Profile" tab is "h1"
    Then I verify that the "Population Served (Territory)" header in "Profile" tab is "h2"
    Then I verify that the "Contact" header in "Profile" tab is "h2"
    Then I verify that the "Your Institution Profile" header in "Institution" tab is "h1"
    Then I verify that the "Degrees Offered" header in "Institution" tab is "h3"
    Then I verify that the "CEEB" header in "Institution" tab is "h3"
    Then I verify that the "Connections" header in "Connections" tab is "h1"
    Then I verify that the "Connections" header in "Connections > My Connections" tab is "h2"
    Then I verify that the "Pending Connections" header in "Connections > Pending Requests" tab is "h2"
    Then I verify that the "Invited Connections" header in "Connections > Invited Connections" tab is "h2"
    Then I verify that the "Institutions" header in "Connections > Institutions I'm Following" tab is "h2"
    Then I verify that the "Your Groups" header in "Groups" tab is "h1"