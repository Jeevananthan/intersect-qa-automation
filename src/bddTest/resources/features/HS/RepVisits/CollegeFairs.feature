@HS
Feature: HS - RepVisits - CollegeFairs - As an HS user, I want to be able to use the RepVisits College Fair features

  @MATCH-1617 @MATCH-1997
  Scenario: As a high school community user, I want to be able to accept or deny a college that requests to attend my fair.
            So that I can ensure the colleges attending are a good match for my students. 
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I create a College Fair with the following data
      | College Fair Name                                         | Fair QA Test#03         |
      | Automatically Confirm Incoming Requestions From Colleges? | no                      |
      | Cost                                                      | 10                      |
      | Start Time                                                | 0800AM                  |
      | Date                                                      | 5                       |
      | RSVP Deadline                                             | 4                       |
      | End Time                                                  | 0800PM                  |
      | Max Number of Colleges                                    | 10                      |
      | Number of Students Expected                               | 10                      |
      | Instructions for College Representatives                  | Submit request by Email |
      | Email Message to Colleges After Confirmation              | why not                 |
    And HS I successfully sign out

    # Log into HE app to request attendance to college fair created in HS app above
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I request an appointment with "Int QA High School 4" for College Fair "Fair QA Test#03"
    And HE I successfully sign out
    Given HE I am logged in to Intersect HE as user type "publishing"
    Then HE I request an appointment with "Int QA High School 4" for College Fair "Fair QA Test#03"
    And HE I successfully sign out

    # Log back into the HS app to accept and decline the attendance requests from above
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I make sure the "Confirm" button works properly for college fair attendee requests for "Fair QA Test#03"
    Then HS I make sure the "Decline" button works properly for college fair attendee requests for "Fair QA Test#03"
    Then HS I cancel the "Fair QA Test#03" College Fair
    And HS I successfully sign out