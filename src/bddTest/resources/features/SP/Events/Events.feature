@SP
Feature: SP - Events - SuperAdmin Role for ActiveMatch

  @MATCH-3518
  Scenario: As a Hobsons Support user ghosting in to an HE admin user account, I shouldn't have access to a client's
  ActiveMatch Connections unless I have the Super Admin role so student data is protected.
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events list
    And HE I create and save a new event with a unique name and the following details:
      | Event Name | AtendeesEvent |
      | Event Start | 12-31-2018;10:00AM |
      | Max Attendees | 30 |
      | RSVP Deadline | 12-30-2018;10:00AM |
      | EVENT LOCATION BY POSITION  | 1 |
      | EVENT PRIMARY CONTACT BY POSITION | 1 |
    And HE I successfully sign out

    When I log in to Family Connection with the following user details:
      | rtsa       | benhubs | Hobsons!23  |
    And I Navigate to old Colleges tab
    And I open link Upcoming college events
    And I look for the host "The University of Alabama"
    Then I sign up for the event of generated name
    And HUBS I successfully sign out

    When SP I am logged in to the Admin page as a Support user
    And SP I go to the users list for "The University of Alabama" from the institution dashboard
    And SP I Login as the user "purpleheautomation@gmail.com"

    When HE I open the Events list
    Then HE I verify that the following error message is displayed when the Attendees section for the generated event is open:
    | You do not have permission to access this data. |
    And HE I open the Events list
    And HE I cancel the created event

