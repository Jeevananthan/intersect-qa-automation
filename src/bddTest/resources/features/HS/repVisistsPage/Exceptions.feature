@HS @MATCH-1582
Feature: As a high school user, I need to be able to add or delete appointment windows on a day by day basis
  so that I can indicate when we will be offering appointments that differ from our typical recurring schedule.

  Scenario: As an HS user, I want to be able to add/remove time slots
    Given HS I want to login to the HS app using "hobsonstest11@mailinator.com" as username and "Control!23" as password
    When HS I open the Exceptions page
    And HS I select the date "December,Monday 18,7:03AM"
    And HS I add a new time slot with the following data:
    | Start time | 07:03 am |
    | End time   | 08:00 am |
    | Visits     | 3        |
    Then HS I verify that the time slot was added in date "12/18/17", with the start time "7:03am"
    And HS I delete the time slot in date "12/18/17", with start time "7:03am"
    And HS I verify that the time slot was removed from date "12/18/17", with the start time "7:03am"
    And HS I successfully sign out

  Scenario: As an HS User, I want to be able to clear a day
    Given HS I want to login to the HS app using "hobsonstest11@mailinator.com" as username and "Control!23" as password
    When HS I open the Exceptions page
    And HS I select the date "December,Friday 22,7:04AM"
    And HS I add a new time slot with the following data:
      | Start time | 07:04 am |
      | End time   | 08:00 am |
      | Max Visits | 3        |
    And HS I select the date "December,Friday 22,7:04AM"
    Then HS I clear the day
    And HS I verify that the time slot was removed from date "12/22/17", with the start time "7:04am"
    And HS I successfully sign out