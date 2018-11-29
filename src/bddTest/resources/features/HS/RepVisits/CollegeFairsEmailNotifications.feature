@HS @HS2
Feature: HS - RepVisits - CollegeFairsEmailNotifications - As an HE/HS user of RepVisits I want to receive email notifications
         when there is new activity on the fairs.

  @MATCH-1792
  Scenario: Verify Fair request confirmation email sent to HE user when approval is set to manual
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    When HS I create a College Fair with the following data
      | College Fair Name                                         | Email Scenario 1        |
      | Automatically Confirm Incoming Requestions From Colleges? | no                      |
      | Cost                                                      | 10                      |
      | Start Time                                                | 0800AM                  |
      | Date                                                      | 2                       |
      | RSVP Deadline                                             | 1                       |
      | End Time                                                  | 0800PM                  |
      | Max Number of Colleges                                    | 10                      |
      | Number of Students Expected                               | 10                      |
      | Instructions for College Representatives                  | Submit request by Email |
      | Email Message to Colleges After Confirmation              | why not                 |
    And HS I successfully sign out
    Then HE I am logged in to Intersect HE as user type "administrator"
    And HE I request an appointment with "Int Qa High School 4" for College Fair "Email Scenario 1"
    And HE I successfully sign out
    Then HE I verify that the Email Notification Message says: "(.*)Your request to attend a college fair at Int Qa High School 4 in(.*)at 8:00am has been submitted. You will be notified by email once the high school has confirmed your request(.*)"
      |Subject                                         |To                            |Messages |
      |College fair scheduled for Int Qa High School 4 |purpleheautomation@gmail.com  |1        |
    And HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    And HS I cancel the fair of name "Email Scenario 1" with the reason "Test Finished"


  @MATCH-1792
  Scenario: Verify Fair request confirmation email sent to HE user when approval is set to auto-approval
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    When HS I create a College Fair with the following data
      | College Fair Name                                         | Email Scenario 2        |
      | Automatically Confirm Incoming Requestions From Colleges? | yes                     |
      | Cost                                                      | 10                      |
      | Start Time                                                | 0800AM                  |
      | Date                                                      | 2                       |
      | RSVP Deadline                                             | 1                       |
      | End Time                                                  | 0800PM                  |
      | Max Number of Colleges                                    | 10                      |
      | Number of Students Expected                               | 10                      |
      | Instructions for College Representatives                  | Submit request by Email |
      | Email Message to Colleges After Confirmation              | why not                 |
    And HS I successfully sign out
    Then HE I am logged in to Intersect HE as user type "administrator"
    And HE I request an appointment with "Int Qa High School 4" for College Fair "Email Scenario 2"
    And HE I successfully sign out
    Then HE I verify that the Email Notification Message says: "(.*)You are all set! Int Qa High School 4 in Erlanger, Kentucky has added you to their list of college fair registrants for the event on(.*)from 8:00am to 8:00pm.(.*)"
      |Subject                                                        |To                            |Messages |
      |College fair request confirmed for Int Qa High School 4 (KY)   |purpleheautomation@gmail.com  |1        |
    And HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    And HS I cancel the fair of name "Email Scenario 2" with the reason "Test Finished"

  @MATCH-1792
  Scenario: Verify Fair request email sent to HS user when approval is set to manual
    Given HS I want to login to the HS app using "purplehsautomations@gmail.com" as username and "Password!1" as password
    When HS I create a College Fair with the following data
      | College Fair Name                                         | Email Scenario 3        |
      | Automatically Confirm Incoming Requestions From Colleges? | no                      |
      | Cost                                                      | 10                      |
      | Start Time                                                | 0800AM                  |
      | Date                                                      | 2                       |
      | RSVP Deadline                                             | 1                       |
      | End Time                                                  | 0800PM                  |
      | Max Number of Colleges                                    | 10                      |
      | Number of Students Expected                               | 10                      |
      | Instructions for College Representatives                  | Submit request by Email |
      | Email Message to Colleges After Confirmation              | why not                 |
    And HS I successfully sign out
    Then HE I am logged in to Intersect HE as user type "administrator"
    And HE I request an appointment with "Homeconnection" for College Fair "Email Scenario 3"
    And HE I successfully sign out
    Then HE I verify that the Email Notification Message says: "(.*)The University of Alabama (AL) just registered for the Homeconnection college fair on(.*)The contact information for the representative who will be attending is below:(.*)"
      |Subject                                                        |To                            |Messages |
      |Request to attend college fair: The University of Alabama (AL) |	purplehsautomations@gmail.com|1        |
    And HS I want to login to the HS app using "purplehsautomations@gmail.com" as username and "Password!1" as password
    And HS I cancel the fair of name "Email Scenario 3" with the reason "Test Finished"