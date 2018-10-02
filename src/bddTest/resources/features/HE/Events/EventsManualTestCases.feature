@HE
Feature: HE - Events - Verify Manual Test Cases

  @Manual @MATCH-3406
    Scenario: : This manual test case is to verify Filter count match
      Given HE I am logged in to Intersect HE as user type "administrator"
      When HE I open the Events section
      And HE The Events page is displayed
      And HE I open the Filter screen
      And HE I press button Create Filter
      And HE I added folowing data to Create Filter
      | Gender | Female |
      | Location WIthin | 25|
      | Location Outside of | 76001|
      | Filter Name | ManualFilter |
      And HE I Save Filter
      And HE I verify and document Recommended To count for filter "ManualFilter"
      And HE I successfully signout
      And HE I login to Succeed with account "rtd1" username "stan.smith" and password "stan01!"
      And HE I opem Student tab
      And HE I open Add a Student screen
      And HE I Add a student with all the required data and save student
      And HE I successfully sign out from succeed
      And HE I am logged in to Intersect HE as user type "administrator"
      And HE I open the Filter screen
      And HE I Verify and document Recommended To count for filter "ManualFilter"
      And HE I verify count increased by 1
      And HE I successfully sign out

    @Manual @MATCH-2898
  Scenario: : This manual test case is to verify Filter count match
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events section
    And HE The Events page is displayed
    And HE I open the Filter screen
    And HE I press button Create Filter
    And HE I added folowing data to Create Filter
      | Gender | Female |
      | Location WIthin | 250|
      | Location Outside of | 76001|
      | Filter Name | ManualFilter |
    And HE I Save Filter
    And HE I verify message "Large Audience: The audience for this filter is at least 10,000 student, we suggest reconfiguring to reduce the size of the audience"
    And HE I successfully sign out



