@HS @HS1
Feature: HS - RepVisits - CollegeFair - As an HS user, I should be able to manange my College Fairs availability and appointments

  @MATCH-1962
  Scenario: As a HIGH School User, I want to verify College Fair Blank DashBoard
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone10"
    And HS I Navigate to College Fairs tab of the Repvisits Page
    And HS I verify the College Fair Blank DashBoard Message


  @MATCH-1776
  Scenario Outline: As a HS RepVisits user I want to able to create a new fair in the college fair
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
    Then HS I verify the Success Message for the College Fair "<College Fair Name>"
    Then HS I Click on the "Close" button in the success page of the college fair
    Then HS I Click the View Details button for the College Fair Event for "PreviouslySetFair"
    Then HS I Click on the "Edit" button in the College Fair Details Page
    Then HS I set the data to the Edit a college Fair "<College Fair Name>","<VerifyDateEdit>","<Cost>","<NumberofStudentsExpected>","<MaxNumberofColleges>","<verifyStartTime>","<verifyEndTime>","<VerifyRSVPDateEdit>","<ButtonToClick>"
    Examples:
      |College Fair Name    |Date            |Start Time|End Time|RSVP Deadline    |Cost|Max Number of Colleges|Number of Students Expected| ButtonToClick |Cost|MaxNumberofColleges|NumberofStudentsExpected|ButtonToClick|VerifyDateEdit       |VerifyRSVPDateEdit     |verifyStartTime|verifyEndTime|
      |QA Test Fair New/Edit|35              |0900AM    |1000AM  |7                |$25 |25                    |100                        | Save          |$25 |25                 |100                     |Save         |Tuesday, Dec 12, 2018|Wednesday, Nov 15, 2018|09:00          |10:00        |

  @MATCH-1464
  Scenario: As a HS Repvisit user send Mass email to college fair attendees
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    And HS I Navigate to College Fairs tab of the Repvisits Page
    And HS I create a College Fair with the following data
      | College Fair Name                                         | Automation Fair for Mass Email  |
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
    And HS I Click the View Details button for the College Fair Event for "Automation Fair for Mass Email"
    And HS I Click on the "Add Attendee" button in the College Fair Details Page
    And HS I Add the following Attendee "purple HEadmin" from the results in the Add Attendee pop-up page
    And HS I Click on the "No, I'm Done" button in the success page of the Add Attendees page
    Then  HS I click the Message Colleges button
    Then  HS I Enter Message as "Mass email to attendees to verify automation is sending mass email to attendees"
    Then  HS I click on Send Message
    Then  HS I verify confirmation message

  @MATCH-1462
  Scenario: As a HS Repvisit user manually add college fair attendees
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    And HS I Navigate to College Fairs tab of the Repvisits Page
    And HS I create a College Fair with the following data
      | College Fair Name                                         | Automation Fair Add Attendee      |
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

    And HS I Click the View Details button for the College Fair Event for "Automation Fair Add Attendee"
    And HS I Click on the "Add Attendee" button in the College Fair Details Page
    And HS I Add the following Attendee "purple HE" from the results in the Add Attendee pop-up page
    And HS I Click on the "No, I'm Done" button in the success page of the Add Attendees page


  @MATCH-1462
  Scenario: As a HS Repvisit user manually add college fair attendees and save it
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    And HS I Navigate to College Fairs tab of the Repvisits Page
    And HS I create a College Fair with the following data
      | College Fair Name                                         | Automation Fair Add Manual Attendee       |
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

    And HS I Click the View Details button for the College Fair Event for "Automation Fair Add Manual Attendee"
    And HS I Click on the "Add Attendee" button in the College Fair Details Page
    Then HS I click on link Add School User Manually
    Then HS I Enter Folliwng Data to Add a School User Manually
      |First Name| AlmauserFirstName|
      |Last Name |AlmaUserLastName  |
      |E-mail    |almauser@E-mail.com|
      |Phone     |12345              |
      |Position  |Alma Tester        |
      |Institution|Alma College    |
    Then HS I click on button Add attendees
    Then HS I Click on the "No, I'm Done" button in the success page of the Add Attendees page


  @MATCH-1631 @MATCH-1463 @skip
  Scenario Outline: As a high school community member, I want to be able to view a list colleges that have requested to attend my college fair,
  so I can keep track of who is attending.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
    Then HS I Click the View Details button for the College Fair Event for "PreviouslySetFair"
    Then HS I verify the Fair Details Page "PreviouslySetFair","<VerifyDate>","<instructionsforCollegeRepresentatives>"
    Then HS I Click on the "Add Attendee" button in the College Fair Details Page
    Then HS I Add the following Attendee "<Attendees>" from the results in the Add Attendee pop-up page
    Then HS I Click on the "No, I'm Done" button in the success page of the Add Attendees page
    Then HS I verify the list of registered college fair attendees for the "<Name>","<Contact>","<Notes>","<Status>","<Action>"
    Then HS I Click the "Cancel" button for the attendee named "<Name>"
    Then HS I set the following data in the confirm cancel pop-up "<cancellationMessage>","<buttonToClickNo,go back>"
    Then HS I verify the list of registered college fair attendees for the "<Name>","<Contact>","<Notes>","<Status>","<Action>"
    Then HS I Click the "Cancel" button for the attendee named "<Name>"
    Then HS I set the following data in the confirm cancel pop-up "<cancellationMessage>","<buttonToClickYes, cancel visit>"
    Then HS I verify the list of registered college fair attendees for the "<Name>","<Contact>","<Notes>","<StatusCanceled>","<ActionCanceled>"
    Then HS I cancel the fair of name "PreviouslySetFair" with the reason "TestCase Cleanup"


    Examples:
      |College Fair Name           |Date            |RSVP Deadline   |Start Time |End Time |Cost|Max Number of Colleges|Number of Students Expected|ButtonToClick|Attendees          |VerifyDate       |instructionsforCollegeRepresentatives|Name                     |Contact                               |Notes|Status   |Action|cancellationMessage             |buttonToClickNo,go back|buttonToClickYes, cancel visit|StatusCanceled  |ActionCanceled |
      |QA Fair Cancel Fair Attendee|3               |2               |0500AM     |0600AM   |$25 |25                    |100                        |Save         |PurpleHE Automation|3                |                                     |PurpleHE Automation      |PurpleHE Automation undefined         |     |Attending|yes   |QA Test for canceling Attendees |No, go back            |Yes, cancel visit             |Canceled        |               |

  @MATCH-1775
  Scenario: As a High School Community user, I wan tto be able to cancel my college fair and notify attendees
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    And HS I Navigate to College Fairs tab of the Repvisits Page
    And HS I create a College Fair with the following data
      | College Fair Name                                         | Cancel This Fair        |
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
    And HS I Click the View Details button for the College Fair Event for "Cancel This Fair"
    And HS I click on button Add attendees for fair
    And HS I Add the following Attendee "Franky2" from the results in the Add Attendee pop-up page
    And HS I Click on the "No, I'm Done" button in the success page of the Add Attendees page
    And HS I click on Edit button to navigate to Edit College Fair
    And HS I cancel the "Cancel This Fair" College Fair



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

  @MATCH-1937
  Scenario: As a high school RepVisits user, I want to be able to specify who is notified of changes to college fairs,
            So that I can be sure the right staff members are informed.
    #BLUE4HS has an issue with duplicated Primary contacts, so using standalone 1 instead.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I verify configuration and staff notifications for "District Manager" and "NidhuHS User"
    Then HS I set the data to create the College Fair "QA Test Fair New/Edit","3","0900AM","1000AM","2","$25","25","100","Save"
    Then HS I add the following attendees to the College Fair
      |Jeeva C|
    Then HS I verify that the user receives an activity notification with "PreviouslySetFair" and "Jeeva C"
    Then HS I verify non community members to be notified with "frank.sejas@gmail.com" and "incorrectemail.com" email

  @MATCH-2382
  Scenario: As a HS user, I should see a green confirmation message when I save College Fair settings
    Given HS I am logged in to Intersect HS through Naviance with account "navianceAdmin"
    Then HS I go to the College Fair Settings page
    Then HS I click on the Save Settings button in College Fairs tab
    Then HS I verify that a banner appears letting me know that College Fair settings were saved

  @MATCH-2202
  Scenario Outline: As a HS RepVisits user who has canceled an HE attendee at a college fair
  I want to be able to re-add that attendee to the fair
  So that I can optimize fair attendance.
#create fair
    Given HS I am logged in to Intersect HS through Naviance with account "navianceAdmin"
    Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
#add attendee
    Then HS I add the following attendees to the College Fair
      |<Attendee>|
    Then HS I Click on the "No, I'm Done" button in the success page of the Add Attendees page
#cancel attendee
    Then HS I Click on the View Details button for the College Fair "<College Fair Name>"
    Then HS I Click the "Cancel" button for the attendee "<Attendee>"
    Then HS I set the following data in the confirm cancel pop-up "<cancellationMessage>","<buttonToClickYes, cancel visit>"
    And HS I successfully sign out
#register the fair from the cancelled attendee
    Then HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "<School>" in RepVisits page
    Then HE I register for the "<College Fair Name>" college fair at "<School>"
    And HE I successfully sign out
#decline attendee
    Given HS I am logged in to Intersect HS through Naviance with account "navianceAdmin"
    Then HS I Click on the View Details button for the College Fair "<College Fair Name>"
    Then HS I verify the "DECLINE","CONFIRM" buttons are present in the Fairs tab
    Then HS I select "DECLINE" option for "<College Fair Name>"
    Then HS I verify the DECLINE pop-up for "<institution>","<cancellationMessage>" in Fairs
    And HS I successfully sign out
#register the fair from the declined attendee
    Then HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "<School>" in RepVisits page
    Then HE I register for the "<College Fair Name>" college fair at "<School>"
    And HE I successfully sign out
#confirm attendee
    Given HS I am logged in to Intersect HS through Naviance with account "navianceAdmin"
    Then HS I Click on the View Details button for the College Fair "<College Fair Name>"
    Then HS I verify the "DECLINE","CONFIRM" buttons are present in the Fairs tab
    Then HS I select "CONFIRM" option for "<College Fair Name>"
#cancel the college Fair
    Then HS I Click on the View Details button for the College Fair Event "<College Fair Name>"
    Then HS I select Edit button to cancel the college Fair "<College Fair Name>"
    Examples:
      |College Fair Name                 |Date |Start Time|End Time|RSVP Deadline    |Cost|Max Number of Colleges|Number of Students Expected|ButtonToClick|School              |Attendees          |buttonToClickAdd Attendees|cancellationMessage          |buttonToClickYes, cancel visit|institution               |Attendee              |
      |qa Fairs for cancel28 decline     |3    |0800AM    |1000AM  |1                |$25 |25                    |100                        |Save         |Int Qa High School 4|PurpleHE Automation|Add Attendees             |Qa test for cancel Attendee  |Yes, cancel visit             |The University of Alabama |PurpleHE Automation   |

  @MATCH-2080 @MATCH-2217
  Scenario: As a HS RepVisits user,
  I need to be able to access all college fairs from the dashboard
  So i know what's upcoming and can see previous fairs' data.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I create a dynamic College Fair with the following data
      | College Fair Name                                         | MATCH-2080 Fair         |
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
    And HS I verify the Dashboard Upcoming events for "MATCH-2080 Fair " and "2"
    Then HS I cancel new event created for "MATCH-2080 Fair"
    And HS I verify the Canceled events for "MATCH-2080 Fair"