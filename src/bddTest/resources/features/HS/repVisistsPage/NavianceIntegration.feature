@HS
Feature:  As an HS user, I want to be able to access the features of the Naviance Integration features.

  @MATCH-1625 @MATCH-1958 @MATCH-1943
  Scenario: As a high school counselor using Naviance and RepVisits,
  I want to integrate my RepVisits account with Naviance college visits
  So that I do not have to manually enter appointments.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I verify the Naviance Settings section of the Availability & Settings tab of the RepVisits page
    Then HS I verify the success message after save the changes
#Comming soon message is removed
#   And HS I verify the Coming Soon message on the RepVisits Overview page
    And HS I successfully sign out
