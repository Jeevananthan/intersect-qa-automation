@HS
Feature: HS - RepVisits - CollegeFairs - As an HS user, I want to be able to use the RepVisits College Fair features

  #Test case is not running accorded to the expected, need to be fixed.
#  @MATCH-1617 @MATCH-1997
#  Scenario: As a high school community user, I want to be able to accept or deny a college that requests to attend my fair.
#            So that I can ensure the colleges attending are a good match for my students. 
#    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
#    Then HS I create a College Fair with the following data
#      | College Fair Name                                         | Fair QA Test#03         |
#      | Automatically Confirm Incoming Requestions From Colleges? | no                      |
#      | Cost                                                      | 10                      |
#      | Start Time                                                | 0800AM                  |
#      | Date                                                      | 5                       |
#      | RSVP Deadline                                             | 4                       |
#      | End Time                                                  | 0800PM                  |
#      | Max Number of Colleges                                    | 10                      |
#      | Number of Students Expected                               | 10                      |
#      | Instructions for College Representatives                  | Submit request by Email |
#      | Email Message to Colleges After Confirmation              | why not                 |
#    And HS I successfully sign out
#
#    # Log into HE app to request attendance to college fair created in HS app above
#    Given HE I am logged in to Intersect HE as user type "administrator"
#    Then HE I request an appointment with "Int QA High School 4" for College Fair "Fair QA Test#03"
#    And HE I successfully sign out
#    Given HE I am logged in to Intersect HE as user type "publishing"
#    Then HE I request an appointment with "Int QA High School 4" for College Fair "Fair QA Test#03"
#    And HE I successfully sign out
#
#    # Log back into the HS app to accept and decline the attendance requests from above
#    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
#    Then HS I make sure the "Confirm" button works properly for college fair attendee requests for "Fair QA Test#03"
#    Then HS I make sure the "Decline" button works properly for college fair attendee requests for "Fair QA Test#03"
#    Then HS I cancel the "Fair QA Test#03" College Fair
#    And HS I successfully sign out

  @MATCH-1598
  Scenario Outline: As a HS RepVisits user I want to be able to access the College Fair overview page
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I set the data to create the College Fair "<CollegeFairName>","<Date>","<StartTime>","<EndTime>","<RSVPDate>","<Cost>","<MaxNumberofColleges>","<NumberofStudentsExpected>","<ButtonToClick>"
    Then HS I verify the Success Message for the College Fair "<CollegeFairName>"
    Then HS I Click on the "Close" button in the success page of the college fair
    Then HS I verify the data for the fair present on the College Fair Overview page "PreviouslySetFair","<date>","<CollegesRegistered>","<RSVPBy>","<Time>","<ViewDetails>"

    Examples:
      |CollegeFairName      |Date            |StartTime|EndTime|RSVPDate        |Cost  |MaxNumberofColleges|NumberofStudentsExpected|ButtonToClick|date        |CollegesRegistered   |RSVPBy	    |Time             |ViewDetails|
      |QA Test Fair Overview|3               |1012AM   |1112AM |2               |$25   |25                 |100                     |Save         |3           |0 of 25 spots filled |2             |10:12am - 11:12am|Yes        |


  @HS @MATCH-2728
  Scenario: Clickable fair summaries open in a drawer but are not editable. This ticket adds an "edit" link,
  and once that's clicked the tray opens fields for data entry and adds a "save" button.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I create a dynamic College Fair with the following data
      | College Fair Name                                         | MATCH-2082 Fair         |
      | Automatically Confirm Incoming Requestions From Colleges? | no                      |
      | Cost                                                      | 10                      |
      | Start Time                                                | 0810AM                  |
      | Date                                                      | 3                       |
      | RSVP Deadline                                             | 2                       |
      | End Time                                                  | 0820AM                  |
      | Max Number of Colleges                                    | 10                      |
      | Number of Students Expected                               | 10                      |
      | Instructions for College Representatives                  | Submit request by Email |
      | Email Message to Colleges After Confirmation              | why not                 |
    And HS I successfully sign out
  # Log into HE app and verify that the fair is visible
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify that the previously created fair appears for "Int QA High School 4"
  # Log into HS app and unpublish the previous College Fair
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I Click the View Details button for the College Fair Event for "PreviouslySetFair"
    #And HS I click on Edit button to edit fair
    Then HS I edit a dynamic College Fair with the following data
      | College Fair Name                                         | MATCH-2082 Fair Edited  |
      | Automatically Confirm Incoming Requestions From Colleges? | no                      |
      | Cost                                                      | 11                     |
      | Start Time                                                | 0815AM                  |
      | Date                                                      |90                      |
      | RSVP Deadline                                             |3                      |
      | End Time                                                  | 0825AM                  |
      | Max Number of Colleges                                    | 11                     |
      | Number of Students Expected                               | 11                      |
      | Instructions for College Representatives                  | Submit request by Email |
      | Email Message to Colleges After Confirmation              | why not                 |
    Then HS I verify edit a dynamic College Fair with the following data
      | College Fair Name                                         | MATCH-2082 Fair Edited  |
      | Automatically Confirm Incoming Requestions From Colleges? | no                      |
      | Cost                                                      | 11                     |
      | Start Time                                                | 0815AM                  |
      | Date                                                      |90                       |
      | RSVP Deadline                                             |3                       |
      | End Time                                                  | 0825AM                  |
      | Max Number of Colleges                                    | 11                     |
      | Number of Students Expected                               | 11                      |
      | Instructions for College Representatives                  | Submit request by Email |
      | Email Message to Colleges After Confirmation              | why not                 |
    Then HS I cancel college fair created "MATCH-2082 Fair Edited"


  @MATCH-1937
  Scenario: As a high school RepVisits user, I want to be able to specify who is notified of changes to college fairs,
            So that I can be sure the right staff members are informed.
    #BLUE4HS has an issue with duplicated Primary contacts, so using standalone 1 instead.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I verify configuration and staff notifications for "District Manager" and "NidhuHS User"
    Then HS I set the data to create the College Fair "QA Test Fair New/Edit","3","0900AM","1000AM","2","$25","25","100","Save"
    Then HS I add the following attendees to the College Fair
      |Andrew Lane|
    Then HS I verify that the user receives an activity notification with "PreviouslySetFair" and "Andrew Lane"
    Then HS I verify non community members to be notified with "frank.sejas@gmail.com" and "incorrectemail.com" email