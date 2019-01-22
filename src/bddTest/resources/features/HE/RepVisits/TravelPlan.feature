@HE
Feature: HE - RepVisits - TravelPlan - As an HE user, I can utilize the Travel plan to plan my visits

@MATCH-3573
Scenario: As a HE premium user, I want the ability to remove any institution I add to my Travel Plan in RepVisits
  Given HE I am logged in to Intersect HE as user type "administrator"
  When HE I add "Westlake High School" high school with location "Austin" to the Travel Plan
  Then HE I verify the "To do" label is displayed for "Westlake High School" high school
  And HE I verify the "This school isnt using RepVisits yet" label is displayed for "Westlake High School" high school
  And HE I verify the trash icon for "Westlake High School" high school
  When HE I cancel removing "Westlake High School" high school from the travel plan
  Then HE I verify "Westlake High School" is displayed in the Travel Plan list
  When HE I remove "Westlake High School" high school from the travel plan
  Then HE I verify "Westlake High School" is not displayed in the Travel Plan list
  Then HE I verify the "Scheduled" label is displayed for "Standalone High School 3" high school
  And HE I successfully sign out
  Given HE I am logged in to Intersect HE as user type "limited"
  Then HE I verify travel plan is locked for non premium users

  @MATCH-4016
  Scenario: As a HE premium user (any role), I want the ability to continue to manage my Travel Plan regardless of SY,
  so that I have as single travel plan I update based upon needs and can see past appts and continue to schedule new appts.
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify the "View Availability" button "is displayed" for schools with "Past Appointments"
    And HE I verify the "View Availability" button "is not displayed" for schools with "Upcoming Appointments"
    And HE I verify the "View Availability" button "is displayed" for schools with "Nothing scheduled yet"
    And HE I verify the "past" appointments for schools in travel plan
    And HE I verify the "upcoming" appointments for schools in travel plan
    And HE I successfully sign out
    
  @MATCH-1620 @MATCH-1715 @MATCH-3021
  Scenario Outline: As an HE user of an HE institution with a Presence subscription active, I want to view recommendations for other high schools to potentially visit,
  so I can visit high schools I might not have otherwise considered.
    Given SP I am logged in to the Admin page as an Admin user
    When SP I search for "2400006"
    And SP I select "The University of Alabama" from the global search results
    Then SP I set the "Intersect Presence Subscription" module to "active" in the institution page
    And SP I Click the Save Changes button
    And SP I successfully sign out

    Given HS I want to login to the HS app using "purpleheautomation+lakotawest@gmail.com" as username and "Password!1" as password
    Then HS I go to the repvisits page
    Then HS I select "All RepVisits Users" to show view availability
    And HS I successfully sign out

    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify the Recommendation tab of the RepVisits page
    Then HE I search "<County>" in Recommendation tab of the RepVisits page
    Then HE I verify the details of Recommendation result for "<school>" using "<Recommendation Score>","<High School Type>","<College Going Rate>","<Senior Class Size>","<County>"
    Then HE I verify "Add To Travel Plan" for "<school>"
    Then HE I verify "This school isnt using RepVisits yet" for "<school>"
    Then HE I verify "View Availability" for "<school>"
    Then HE I click the View Availability for "<school>" and verify the close button
    And HE I successfully sign out

    Examples:
      |Recommendation Score |High School Type |College Going Rate |Senior Class Size |County   |school                    |
      |80                   |PUBLIC           |85%                |524               |West     |Lakota West High School   |
