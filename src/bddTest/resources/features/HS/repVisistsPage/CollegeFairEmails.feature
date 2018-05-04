Feature: As an HE and HS user of RepVisits I want to receive email notifications when certain activities happen that
  relate to me or my school, so I can be made aware of things happening even when I am not logged into RepVisits

  @MATCH-1792
  Scenario: HE User submits Visit Request - HS needs to approve
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
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
    And HE I want to login to the HE app using "purpleheautomation@gmail.com" as username and "Password!1" as password
    And HE I request an appointment with "Int Qa High School 4" for College Fair "Email Scenario 1"
    And HE I successfully sign out
    Then HE I verify that the Email Notification Message says: "(.*)Your request to attend a college fair at Int Qa High School 4 in(.*)at 8:00am has been submitted. You will be notified by email once the high school has confirmed your request(.*)"
      |Subject                                         |To                            |Messages |
      |College fair scheduled for Int Qa High School 4 |purpleheautomation@gmail.com  |1        |
    And HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    And HS I cancel the fair of name "Email Scenario 1" with the reason "Test Finished"
    And HS I successfully sign out


  @MATCH-1792
  Scenario: HE User submits Visit Request - auto approved
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    When HS I create a College Fair with the following data
      | College Fair Name                                         | Email Scenario 2        |
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
    And HE I want to login to the HE app using "purpleheautomation@gmail.com" as username and "Password!1" as password
    And HE I request an appointment with "Int Qa High School 4" for College Fair "Email Scenario 2"
    And HE I successfully sign out
    Then HE I verify that the Email Notification Message says: "(.*)Your request to attend a college fair at Int Qa High School 4 in(.*)at 8:00am has been submitted. You will be notified by email once the high school has confirmed your request(.*)"
      |Subject                                         |To                            |Messages |
      |College fair scheduled for Int Qa High School 4 |purpleheautomation@gmail.com  |1        |
    And HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    And HS I cancel the fair of name "Email Scenario 2" with the reason "Test Finished"
    And HS I successfully sign out