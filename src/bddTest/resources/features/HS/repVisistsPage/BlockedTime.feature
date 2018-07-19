@HS
Feature:  As an HS user, I want to be able to access the features of the Bblocked Time features.


  @MATCH-2692
  Scenario: As a high school staff member, I want to be able to toggle blocking of specific availabilities in RepVisits,
  so that I can effectively close a time slot for further visits and re-open it later, if I choose.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    And HS I set a date using "0" and "90"
    And HS I add new time slot with "Friday", "2", "3", "15", "15", "AM", "AM" and "2"
    And HS I schedule a new visit with day "Fri" time "2:15am" representative name "Test Person name" representative last name "Test Last N" representative institution "RepresentativeTest" location "Cbba" NumberOfStudents "7" registrationWillClose "7 days"
    Then HS I verify that Block this time slot button is displayed for time slot with day "Fri" and time "2:15am"
    And HS I verify that Block this time slot ToolTip is displayed for time slot with day "Fri" and time "2:15am"
    When HS I block the time slot with day "Fri" and time "2:15am"
    Then HS I verify that Unblock this time slot button is displayed for time slot with day "Fri" and time "2:15am"
    And HS I verify that Block this time slot ToolTip is displayed for time slot with day "Fri" and time "2:15am"
    And HS I verify that Blocked label is displayed in the slot time with day "Fri" and time "2:15am"
    And HS I verify that a new visit with day "Fri" and time "2:15am" cannot be set
    When HS I unblock the time slot with day "Fri" and time "2:15am"
    Then HS I verify that the blocked label is not displayed for the time slot with day "Fri" and time "2:15am"
    And HS I verify that the number of visits for the time slot with day "Fri" and time "2:15am" is "2"
    And HS I schedule a new visit with day "Fri" time "2:15am" representative name "Test Person name" representative last name "Test Last N" representative institution "RepresentativeTest2" location "Cbba" NumberOfStudents "7" registrationWillClose "7 days"
    And HS I cancel a visit with time "2:15AM" college "RepresentativeTest" and note "Cancel"
    And HS I cancel a visit with time "2:15AM" college "RepresentativeTest2" and note "Cancel"
    And HS I remove the time slot with day "Fri" and time "2:15am"
    And HS I successfully sign out
