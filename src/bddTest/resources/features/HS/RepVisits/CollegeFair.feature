@HS @HS1
Feature: HS - RepVisits - CollegeFair - As an HS user, I should be able to manage my College Fairs availability and appointments

  @MATCH-1962
  Scenario: As a HIGH School User, I want to verify College Fair Blank DashBoard
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone10"
    And HS I Navigate to College Fairs tab of the Repvisits Page
    And HS I verify the College Fair Blank DashBoard Message
    And HS I successfully sign out

  @MATCH-1776
  Scenario Outline: As a HS RepVisits user I want to able to create a new fair in the college fair
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
    Then HS I verify the Success Message for the College Fair "<College Fair Name>"
    Then HS I Click on the "Close" button in the success page of the college fair
    Then HS I Click the View Details button for the College Fair Event for "PreviouslySetFair"
    Then HS I Click on the "Edit" button in the College Fair Details Page
    Then HS I set the data to the Edit a college Fair "<College Fair Name>","<VerifyDateEdit>","<Cost>","<NumberofStudentsExpected>","<MaxNumberofColleges>","<verifyStartTime>","<verifyEndTime>","<VerifyRSVPDateEdit>","<ButtonToClick>"
    Then HS I Click on the "Close" button in the success page of the college fair
    Then HS I Click on the View Details button for the College Fair Event "<College Fair Name>"
    Then HS I select Edit button to cancel the college Fair "<College Fair Name>"
    And HS I successfully sign out
    Examples:
      |College Fair Name    |Date            |Start Time|End Time|RSVP Deadline    |Cost|Max Number of Colleges|Number of Students Expected| ButtonToClick |Cost|MaxNumberofColleges|NumberofStudentsExpected|ButtonToClick|VerifyDateEdit       |VerifyRSVPDateEdit     |verifyStartTime|verifyEndTime|
      |QA Test Fair New/Edit|35              |0900AM    |1000AM  |7                |$25 |25                    |100                        | Save          |$25 |25                 |100                     |Save         |Tuesday, Dec 12, 2018|Wednesday, Nov 15, 2018|09:00          |10:00        |

  @MATCH-1464
  Scenario: As a HS Repvisit user send Mass email to college fair attendees
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
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
    Then HS I Click on the "Close" button in the success page of the college fair
    Then HS I Click on the View Details button for the College Fair Event "Automation Fair for Mass Email"
    And HS I Click on the "Add Attendee" button in the College Fair Details Page
    And HS I Add the following Attendee "purple HEadmin" from the results in the Add Attendee pop-up page
    Then HS I Click on the "No, I'm Done" button in the success page of the Add Attendees page
    Then  HS I click the Message Colleges button
    Then  HS I Enter Message as "Mass email to attendees to verify automation is sending mass email to attendees"
    Then  HS I click on Send Message
    Then  HS I verify confirmation message
    Then HS I Click on the "Close" button in the success page of the college fair
    Then HS I Click on the View Details button for the College Fair Event "Automation Fair for Mass Email"
    Then HS I select Edit button to cancel the college Fair "Automation Fair for Mass Email"
    And HS I successfully sign out

  @MATCH-1462
  Scenario: As a HS Repvisit user manually add college fair attendees
    Given HS I am logged in to Intersect HS as user type "HSadmin1"
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
    Then HS I Click on the "Close" button in the success page of the college fair
    And HS I Click the View Details button for the College Fair Event for "Automation Fair Add Attendee"
    And HS I Click on the "Add Attendee" button in the College Fair Details Page
    And HS I Add the following Attendee "purple HE" from the results in the Add Attendee pop-up page
    Then HS I Click on the "No, I'm Done" button in the success page of the Add Attendees page
    Then HS I Click on the View Details button for the College Fair Event "Automation Fair Add Attendee"
    Then HS I select Edit button to cancel the college Fair "Automation Fair Add Attendee"
    And HS I successfully sign out

  @MATCH-1462
  Scenario: As a HS Repvisit user manually add college fair attendees and save it
    Given HS I am logged in to Intersect HS as user type "HSadmin1"
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
    Then HS I Click on the "Close" button in the success page of the college fair
    And HS I Click the View Details button for the College Fair Event for "Automation Fair Add Manual Attendee"
    And HS I Click on the "Add Attendee" button in the College Fair Details Page
    Then HS I click on link Add School User Manually
    Then HS I Enter Following Data to Add a School User Manually
      |First Name| AlmauserFirstName|
      |Last Name |AlmaUserLastName  |
      |E-mail    |almauser@E-mail.com|
      |Phone     |12345              |
      |Position  |Alma Tester        |
      |Institution|Alma College    |
    Then HS I click on button Add attendees
    Then HS I Click on the "No, I'm Done" button in the success page of the Add Attendees page
    Then HS I Click on the View Details button for the College Fair Event "Automation Fair Add Manual Attendee"
    Then HS I select Edit button to cancel the college Fair "Automation Fair Add Manual Attendee"
    And HS I successfully sign out


  @MATCH-1631 @MATCH-1463
  Scenario Outline: As a high school community member, I want to be able to view a list colleges that have requested to attend my college fair,
  so I can keep track of who is attending.
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
    Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
    Then HS I Click on the "Close" button in the success page of the college fair
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
    Then HS I Click on the "Close" button in the success page of the college fair
    Then HS I verify the list of registered college fair attendees for the "<Name>","<Contact>","<Notes>","<StatusCanceled>","<ActionCanceled>"
    Then HS I cancel the fair of name "PreviouslySetFair" with the reason "TestCase Cleanup"
    And HS I successfully sign out

    Examples:
      |College Fair Name           |Date            |RSVP Deadline   |Start Time |End Time |Cost|Max Number of Colleges|Number of Students Expected|ButtonToClick|Attendees          |VerifyDate       |instructionsforCollegeRepresentatives|Name                     |Contact                      |Notes|Status   |Action|cancellationMessage             |buttonToClickNo,go back|buttonToClickYes, cancel visit|StatusCanceled  |ActionCanceled |
      |QA Fair Cancel Fair Attendee|2               |1               |0500AM     |0600AM   |$25 |25                    |100                        |Save         |PurpleHE Automation|1                |                                     |PurpleHE Automation      |PurpleHE Automation          |     |Attending|yes   |QA Test for canceling Attendees |No, go back            |Yes, cancel visit             |Canceled        |               |

  @MATCH-1775
  Scenario: As a High School Community user, I want to be able to cancel my college fair and notify attendees
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
    Then HS I Click on the "Close" button in the success page of the college fair
    And HS I Click the View Details button for the College Fair Event for "Cancel This Fair"
    And HS I click on button Add attendees for fair
    And HS I Add the following Attendee "Franky2" from the results in the Add Attendee pop-up page
    Then HS I Click on the "No, I'm Done" button in the success page of the Add Attendees page
    Then HS I Click on the View Details button for the College Fair Event "Cancel This Fair"
    Then HS I select Edit button to cancel the college Fair "Cancel This Fair"
    And HS I successfully sign out


  @MATCH-1598
  Scenario Outline: As a HS RepVisits user I want to be able to access the College Fair overview page
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone2"
    Then HS I set the data to create the College Fair "<CollegeFairName>","<Date>","<StartTime>","<EndTime>","<RSVPDate>","<Cost>","<MaxNumberofColleges>","<NumberofStudentsExpected>","<ButtonToClick>"
    Then HS I verify the Success Message for the College Fair "<CollegeFairName>"
    Then HS I Click on the "Close" button in the success page of the college fair
    Then HS I verify the data for the fair present on the College Fair Overview page "PreviouslySetFair","<date>","<CollegesRegistered>","<RSVPBy>","<Time>","<ViewDetails>"
    Then HS I Click on the View Details button for the College Fair Event "<CollegeFairName>"
    Then HS I select Edit button to cancel the college Fair "<CollegeFairName>"
    And HS I successfully sign out

  Examples:
    |CollegeFairName      |Date            |StartTime|EndTime|RSVPDate        |Cost  |MaxNumberofColleges|NumberofStudentsExpected|ButtonToClick|date        |CollegesRegistered   |RSVPBy	    |Time             |ViewDetails|
    |QA Test Fair Overview|3               |1012AM   |1112AM |2               |$25   |25                 |100                     |Save         |2           |0 of 25 spots filled |2             |10:12am - 11:12am|Yes        |

  @MATCH-1937
  Scenario: As a high school RepVisits user, I want to be able to specify who is notified of changes to college fairs,
            So that I can be sure the right staff members are informed.
    #BLUE4HS has an issue with duplicated Primary contacts, so using standalone 1 instead.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I verify configuration and staff notifications for "District Manager" and "NidhuHS User"
    And HS I clean the college fairs created
    Then HS I set the data to create the College Fair "QA Test Fair New/Edit","3","0900AM","1000AM","2","$25","25","100","Save"
    Then HS I add the following attendees to the College Fair
      |PurpleHE Automation|
    Then HS I Click on the "Close" button in the success page of the college fair
    Then HS I verify that the user receives an activity notification with "PreviouslySetFair" and "PurpleHE Automation"
    Then HS I verify non community members to be notified with "frank.sejas@gmail.com" and "incorrectemail.com" email
    Then HS I Click on the View Details button for the College Fair Event "QA Test Fair New/Edit"
    Then HS I select Edit button to cancel the college Fair "QA Test Fair New/Edit"
    And HS I successfully sign out

  @MATCH-2382
  Scenario: As a HS user, I should see a green confirmation message when I save College Fair settings
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I go to the College Fair Settings page
    Then HS I click on the Save Settings button in College Fairs tab
    Then HS I verify that a banner appears letting me know that College Fair settings were saved
    And HS I successfully sign out

  @MATCH-2202 @MATCH-2372
  Scenario Outline: As a HS RepVisits user who has canceled an HE attendee at a college fair
  I want to be able to re-add that attendee to the fair
  So that I can optimize fair attendance.
#create fair
    Given HS I am logged in to Intersect HS as user type "CollegeFairs"
    Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
#add attendee
    Then HS I add the following attendees to the College Fair
      |<Attendee>|
    Then HS I Click on the "Close" button in the success page of the college fair
#cancel attendee
    Then HS I Click on the View Details button for the College Fair "<College Fair Name>"
    Then HS I Click the "Cancel" button for the attendee "<Attendee>"
    Then HS I set the following data in the confirm cancel pop-up "<cancellationMessage>","<buttonToClickYes, cancel visit>"
    And HS I successfully sign out
#register the fair from the cancelled attendee
    Then HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "<School>" in RepVisits page
    Then HE I register for the "<College Fair Name>" college fair at "<School>"
#decline attendee
    Given HS I am logged in to Intersect HS as user type "CollegeFairs"
    Then HS I Click on the View Details button for the College Fair "<College Fair Name>"
    Then HS I verify the "DECLINE","CONFIRM" buttons are present in the Fairs tab
    Then HS I select "DECLINE" option for "<College Fair Name>"
    Then HS I verify the DECLINE pop-up for "<institution>","<cancellationMessage>" in Fairs
    And HS I successfully sign out
#register the fair from the declined attendee
    Then HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "<School>" in RepVisits page
    Then HE I register for the "<College Fair Name>" college fair at "<School>"
#confirm attendee
    Given HS I am logged in to Intersect HS as user type "CollegeFairs"
    Then HS I Click on the View Details button for the College Fair "<College Fair Name>"
    Then HS I verify the "DECLINE","CONFIRM" buttons are present in the Fairs tab
    Then HS I select "CONFIRM" option for "<College Fair Name>"
#cancel the college Fair
    Then HS I Click on the View Details button for the College Fair Event "<College Fair Name>"
    Then HS I select Edit button to cancel the college Fair "<College Fair Name>"
    And HS I successfully sign out
    Examples:
      |College Fair Name                 |Date |Start Time|End Time|RSVP Deadline    |Cost|Max Number of Colleges|Number of Students Expected|ButtonToClick|School             |Attendees          |buttonToClickAdd Attendees|cancellationMessage          |buttonToClickYes, cancel visit|institution               |Attendee              |
      |qa Fairs for cancel28 decline     |3    |0800AM    |1000AM  |1                |$25 |25                    |100                        |Save         |Cinema School (the)|PurpleHE Automation|Add Attendees             |Qa test for cancel Attendee  |Yes, cancel visit             |The University of Alabama |PurpleHE Automation   |

  @MATCH-2080 @MATCH-2217
  Scenario: As a HS RepVisits user,
  I need to be able to access all college fairs from the dashboard
  So i know what's upcoming and can see previous fairs' data.
    Given HS I am logged in to Intersect HS as user type "HSadmin"
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
    Then HS I Click on the "Close" button in the success page of the college fair
    Then HS I Click on the View Details button for the College Fair Event "MATCH-2080 Fair"
    Then HS I select Edit button to cancel the college Fair "MATCH-2080 Fair"
    And HS I verify the Canceled events for "MATCH-2080 Fair"

  @MATCH-2444
  Scenario Outline: Verify that email is sent to HS users after cancelling a fair as an HE user
    Given HS I am logged in to Intersect HS as user type "administrator"
    Then HS I add the email "<EMail>" in the primary contact in Notifications & Primary Contact page
    And HS I clean the college fairs created
    Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
    And HS I successfully sign out

    Then HE I am logged in to Intersect HE as user type "alpenaAdmin"
    And HE I search for "<School>" in RepVisits page
    Then HE I register for the "<College Fair Name>" college fair at "<School>"
    Then HE I verify the calendar page using "<School>","<heCT>","<Date>" for Fairs
    Then HE I remove the appointment from the calendar for fairs
    Then HE I verify the Email Notification Message for "<School>" using "<Date>","<EmailTimeForFair>"
      |Subject                                                             |To       |Messages |
      |College fair registration cancelled for <School for Notification>   |<EMail>  |1        |

    Given HS I am logged in to Intersect HS as user type "administrator"
    Then HS I Click on the View Details button for the College Fair Event "<College Fair Name>"
    Then HS I select Edit button to cancel the college Fair "<College Fair Name>"
    And HS I successfully sign out

    Examples:
      |School for Notification|School          |EMail                           |College Fair Name     |Date|Start Time|End Time|RSVP Deadline|Cost|Max Number of Colleges|Number of Students Expected| ButtonToClick |heCT   |EmailTimeForFair|
      |Mays High School (GA)  |Mays High School|purpleheautomation@gmail.com    |QAs Fairs tests       |4   |900AM     |1100AM  |2            |$25 |25                    |100                        | Save          |9AM    |9:00am          |
    
  @MATCH-2381
  Scenario Outline: As a HS RepVisits user verify note to let users know their contact info will be visible
    Given HS I am logged in to Intersect HS as user type "HSadmin1"
    And HS I Navigate to College Fairs tab of the Repvisits Page
    And HS I Click button Add a College Fair to Add a fair
    And HS I verify Note on Add Edit Fair screen "Please note: Your high school name, address, email, and primary contact phone number will be displayed to admission representatives."
    And HS I click on close icon on Add Edit College Fair pop-up
    And HS I clean the college fairs created
    Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
    Then HS I Click on the "Close" button in the success page of the college fair
    Then HS I Click on the View Details button for the College Fair Event "<College Fair Name>"
    And HS I click on Edit button to edit fair
    And HS I verify Note on Add Edit Fair screen "Please note: Your high school name, address, email, and primary contact phone number will be displayed to admission representatives."
    And HS I click on close icon on Add Edit College Fair pop-up
  #cancel the college Fair
    Then HS I Click on the View Details button for the College Fair Event "<College Fair Name>"
    Then HS I select Edit button to cancel the college Fair "<College Fair Name>"
    And HS I successfully sign out

  Examples:
  |College Fair Name |Date |Start Time|End Time|RSVP Deadline    |Cost|Max Number of Colleges|Number of Students Expected|ButtonToClick|
  |qa Fairs          |3    |0800AM    |1000AM  |1                |$25 |25                    |100                        |Save         |
 
 @MATCH-2381
  Scenario Outline: As a Non Naviance HS RepVisits user verify note to let users know their contact info will be visible
    Given HS I am logged in to Intersect HS as user type "administrator"
    And HS I Navigate to College Fairs tab of the Repvisits Page
    And HS I Click button Add a College Fair to Add a fair
    And HS I verify Note on Add Edit Fair screen "Please note: Your high school name, address, email, and primary contact phone number will be displayed to admission representatives."
    And HS I click on close icon on Add Edit College Fair pop-up
    Then HS I set the following data to On the College Fair page "<College Fair Name>", "<Date>", "<Start Time>", "<End Time>", "<RSVP Deadline>", "<Cost>", "<Max Number of Colleges>", "<Number of Students Expected>", "<ButtonToClick>"
    Then HS I Click on the "Close" button in the success page of the college fair
    Then HS I Click on the View Details button for the College Fair Event "<College Fair Name>"
    And HS I click on Edit button to edit fair
    And HS I verify Note on Add Edit Fair screen "Please note: Your high school name, address, email, and primary contact phone number will be displayed to admission representatives."
    And HS I click on close icon on Add Edit College Fair pop-up
 #cancel the college Fair
    Then HS I Click on the View Details button for the College Fair Event "<College Fair Name>"
    Then HS I select Edit button to cancel the college Fair "<College Fair Name>"
    And HS I successfully sign out
    
  Examples:
  |College Fair Name |Date |Start Time|End Time|RSVP Deadline    |Cost|Max Number of Colleges|Number of Students Expected|ButtonToClick|
  |qa Fairs          |3    |0800AM    |1000AM  |1                |$25 |25                    |100                        |Save         |

  @MATCH-2438
  Scenario: As an HS User who entered only a first name and Institution to attend a fair, I want no last name displayed (instead of "undefined")
            So that my display is not cluttered with info that is not applicable.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I set the following data to On the College Fair page "qa Fairs", "3", "0800AM", "1000AM", "1", "$25", "25", "100", "Save"
    Then HS I manually add the attendee for created college fair without last name "PurpleHE","The University of Alabama"
    Then HS I Click on the "No, I'm Done" button in the success page of the Add Attendees page
    Then HS I verify "PurpleHE Undefined" text is not displaying in the attendee page
    #cancel the college Fair
    Then HS I Click on the View Details button for the College Fair Event "qa Fairs"
    Then HS I select Edit button to cancel the college Fair "qa Fairs"
    And HS I successfully sign out

  @MATCH-3924
  Scenario: As a high school admin user that has previously scheduling College Fairs in RepVisits, I want to be able to schedule a college fair up and through the end of SY
  (i.e. July 14 date of the year through which the Regular Weekly Hours end date picker can be supported through),
  so that my end date for Regular Weekly hours doesn't limit when I can schedule a College Fair.

    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I verify the date "July 14 2021" is not enabled in college fair page
# verify earlier than current date is not enabled in college fair
    Then HS I verify the date "-7" is not enabled in college fair page
    Then HS I set the following data to On the College Fair page "qa Fairs", "3", "0800AM", "1000AM", "1", "$25", "25", "100", "Save"
    Then HS I Click on the "Close" button in the success page of the college fair
    Then HS I Click on the View Details button for the College Fair Event "qa Fairs"
    Then HS I verify the date "July 14 2021" is not enabled in edit college fair page
# verify earlier than current date is not enabled in edit college fair
    Then HS I verify the date "-7" is not enabled in edit college fair page
    Then HS I select Edit button to cancel the college Fair "qa Fairs"
    And HS I successfully sign out
