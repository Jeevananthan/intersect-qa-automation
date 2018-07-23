@HS
Feature:  As an HS user, I want to be able to access the features of the Visits features.


  @MATCH-2391
  Scenario: As a RepVisits user,I cannot able to add the visits for the past days
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS verify pills are not available for the past dates in schedule new visit page
    Then HS verify the past dates are disabled in the select custom date section
    Then HS verify pills are not available for the past dates in Re-schedule visit page
    Then HS verify the past dates are disabled in the select custom date section for Re-schedule visit page
    And HS I successfully sign out
