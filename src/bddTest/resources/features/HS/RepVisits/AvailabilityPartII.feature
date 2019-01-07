@HS @HS2
Feature: HS - RepVisits - AvailabilityPartII - As an HS user, I should be able to setup my visit configurations

  @MATCH-1580
  Scenario: As a HS Repvisit user Set Repvist availability Messages Instructions for HE on scheduling page
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then  HS I verify the Availability & Settings tab of the RepVisits page
    And HS I verify the UI of the Messaging Options Page
    And HS I set the Special Instructions Text as "AUTOMATION Welcome message. This message is to test the maximum limit of characters in messages. As a HS Repvisits user We will add this message. Ans same message will be displayed in HE for Repvisits to schedule their visits. Maximum characters allowed are 250 . This text contains more than 250 characters";
    And HS I verify the Special Instructions are "AUTOMATION Welcome message. This message is to test the maximum limit of characters in messages. As a HS Repvisits user We will add this message. Ans same message will be displayed in HE for Repvisits to schedule their visits. Maximum characters allo"
    And HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "Int Qa High School 4" in RepVisits
    And HE I select "Int Qa High School 4" in "Liberty Township, OH" from the RepVisits intermediate search results
    And HE I verify Repvisits Special Instructions for School are "AUTOMATION Welcome message. This message is to test the maximum limit of characters in messages. As a HS Repvisits user We will add this message. Ans same message will be displayed in HE for Repvisits to schedule their visits. Maximum characters allo"

  @MATCH-1576
  Scenario Outline: As a HS RepVisits user, I want to be able to Block specific days and date ranges in the Holidays tab of the Availability and Settings page
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I set the Blocked date as "<BlockedDate>" and select the reason as "<Reason>" in the Holiday tab
    Then HS I go to the Counselor Community
    Then HS I verify the "<StartDate>" and "<EndDate>" date with "<Reason>" was present in the Holidays tab in the Availability & Settings page in RepVisits
    Then HS I click the Remove option for the "<StartDate>" and "<EndDate>" in the Holiday tab
    Then HS I go to the Counselor Community
    Then HS I verify the "<StartDate>" and "<EndDate>" date with "<Reason>" was not present in the Holidays tab in the Availability & Settings page in RepVisits
    Examples:
      |BlockedDate          |Reason       |StartDate  | EndDate   |
      |13                   |No School    |13         | 13        |


  @MATCH-2171 @MATCH-2124
  Scenario Outline: when we initially created the first and last days for availability, they were not developed to persist.
  Instead they're changed / set each time that availability is set. This ticket is to persist the first and last dates
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I set the visit availability dates to "<StartDate>" through "<EndDate>"
    Then HS I verify the success Message "Great!You've updated your settings." in Availability Settings page
    Then HS I go to the Counselor Community
    Then HS I verify the StartDate is set to "<verifyStartDate>" and EndDate is set to "<verifyEndDate>"

    Examples:
      |StartDate     |EndDate        |verifyStartDate  |verifyEndDate   |
      |June 14 2019  |July 14 2019   |06/14/2019       |07/14/2019      |

  @MATCH-1584
  Scenario Outline: As a high school user, when I confirm an appointment I need to email colleges with specific details
  about visiting my high school  so that they are prepared to visit my high school.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I set the RepVisits Confirmation message to "<Message>"
    And HS I verify the messaging updated confirmation toast message
    And HS I verify the RepVisits Confirmation message is set to "<Message>"

    Examples:
      |Message                 |
      |Test update New Message |

  @MATCH-2111 @MATCH-2124
  Scenario: As a RepVisits High School user who works in multiple schools,
            I want to be able to enter a primary contact number for my school,
            So that Colleges trying to schedule visits have the correct contact number for me.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    And HS I go to the Availability & Settings
    Then HS I verify the Primary Contact Phone Number is required in Availability & Settings
    Then HS I verify the success Message "Great!You've updated your settings." in Availability Settings page
    And HS I go to the College Fair Settings page
    Then HS I verify the Primary Contact Phone Number is required in College Fair Settings
    Then HS I verify the success Message "Great!You've updated College Fair settings." in Fair Settings page
    And HS I go to the Notifications & Primary Contact Tab in HS Setup Wizard page
    Then HS I verify the Primary Contact Phone Number is required in the Visits and Fairs setup wizard

  @MATCH-2691
  Scenario Outline: As a High School RepVisits User who is viewing my exceptions (/rep-visits/settings/availability/exceptions)
  I want to see availability pills during times when appointments are scheduled
  So that I can edit remaining availabilities.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I verify in exceptions the appointments color and status for "<AppointmentStatus>" with color "<Color>"
    Examples:
      |AppointmentStatus       |Color                   |
      |Max visits met          | rgba(233, 233, 245, 1) |
      |Fully booked            | rgba(233, 238, 245, 1) |
      |Appointment scheduled   | rgba(233, 238, 245, 1) |

  @MATCH-1812 @MATCH-2124
  Scenario Outline: As a RepVisits product I want to limit the high schools returned in RepVisits searches to only include those high schools
                    who have made their RepVisits availability publicly available
                    so HE users are not presented with high schools in the search results that don't use RepVisits.
#Pre-condition
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    And HS I set the Visit Availability of RepVisits Availability Settings to "All RepVisits Users"
    Then HS I verify the success Message "Great!You've updated your settings." in Availability Settings page
    Then HS I set the RepVisits Visits Confirmations option to "<Option>"
    Then HS I verify the success Message "Great!You've updated your settings." in Availability Settings page
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
    Then HS I verify the success Message "Great!You've updated your settings." in Availability Settings page
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    Then HS I verify the success Message "Great!You've updated your settings." in Availability Settings page
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
    Then HS I verify the success Message "Great!You've updated your settings." in Availability Settings page
    Then HS I clear the time slot for the particular day "<StartDate>" in Regular Weekly Hours Tab
    Then HS I set the date using "<StartDate>" and "<EndDate>"
    Then HS I verify the success Message "Great!You've updated your settings." in Availability Settings page
    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>" with "<option>"
    And HS I successfully sign out

    Then HE I am logged in to Intersect HE as user type "administrator"
#by SchoolLocation
    And HE I search a school by "City" using "<location>"
    Then HE I select "<School>" from the RepVisits search result
    Then HE I verify the default toggle "Visits" is "Enabled" in search and schedule Tab
    Then HE I verify the default toggle "Fairs" is "Disabled" in search and schedule Tab
    Then HE I verify the Availability slot "<heStartTime>" is displaying in the visit toggle "<Date>","<School>" in search and schedule Tab
    Then HE I successfully sign out
  #by SchoolName
    Then HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "<School>" in RepVisits page
    Then HE I verify the default toggle "Visits" is "Enabled" in search and schedule Tab
    Then HE I verify the default toggle "Fairs" is "Disabled" in search and schedule Tab
    Then HE I verify the Availability slot "<heStartTime>" is displaying in the visit toggle "<Date>","<School>" in search and schedule Tab
    Then HE I successfully sign out

    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    And HS I set the Visit Availability of RepVisits Availability Settings to "Only Me"
    Then HS I verify the success Message "Great!You've updated your settings." in Availability Settings page
    And HS I successfully sign out

    Then HE I am logged in to Intersect HE as user type "administrator"
#by SchoolLocation
    And HE I search a school by "City" using "<location>"
    Then HE I select "<School>" from the RepVisits search result
    Then HE I verify the default toggle "Visits" is "Disabled" in search and schedule Tab
    Then HE I verify the default toggle "Fairs" is "Enabled" in search and schedule Tab
    Then HE I verify the Availability slot "<heStartTime>" is not displaying in the visit toggle "<Date>","<School>" in search and schedule Tab
    Then HE I successfully sign out
 #by SchoolName
    Then HE I am logged in to Intersect HE as user type "administrator"
    And HE I search for "<School>" in RepVisits page
    Then HE I verify the default toggle "Visits" is "Disabled" in search and schedule Tab
    Then HE I verify the default toggle "Fairs" is "Enabled" in search and schedule Tab
    Then HE I verify the Availability slot "<heStartTime>" is not displaying in the visit toggle "<Date>","<School>" in search and schedule Tab
    Then HE I successfully sign out
#Post-Condition
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    And HS I set the Visit Availability of RepVisits Availability Settings to "All RepVisits Users"
    Then HS I verify the success Message "Great!You've updated your settings." in Availability Settings page
    And HS I successfully sign out

  Examples:
   |Day |StartTime|EndTime |NumVisits|StartDate|EndDate |Option                                               |School                  |heStartTime |Date|location               |option |
   |14  |10:32am  |11:25pm |3        |14       |42      |No, I want to manually review all incoming requests. |Int Qa High School 4    |10:32am     |14  |Liberty Township       |1      |


  @MATCH-1583
  Scenario: As an HS User I want to be able to use the Notifications and Primary Contact tab of RepVisits to Set Primay Contact
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    And HS I Set the Primary Contact for Visits for my  school with phone"444-444-4444" and Email "mbhangu@hobsons.com"
    And HS I Save the Primary Contacts for visits for my school

  @MATCH-2381 @test
  Scenario: As a HS RepVisits user verify note to let users know their contact info will be visible
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    And HS I Navigate to College Fairs tab of the Repvisits Page
    And HS I Click button Add a College Fair to Add a fair
    And HS I verify Note on Add Edit Fair screen "Please note: Your high school name, address, email, and primary contact phone number will be displayed to admission representatives."
    And HS I click on close icon on Add Edit College Fair pop-up
    And HS I click View Details against fair
    And HS I click on Edit button to edit fair
    And HS I verify Note on Add Edit Fair screen "Please note: Your high school name, address, email, and primary contact phone number will be displayed to admission representatives."

  @MATCH-3257 @MATCH-3603
  Scenario Outline: As a HS admin user,I want the ability to specify my school's "regular weekly hours"
  for the upcoming school year (e.g. 2018-2019), so that I can begin allowing reps to start
  scheduling visits accordingly for the new school year.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I verify that availability dates are from "<StartDate>" to "<EndDate>" for visits the days "<startDay>","<endDay>" in the calendar

    Examples:
      | StartDate      | EndDate    | startDay |endDay|
      | July 2018      | July 2019  |  15      | 14   |

