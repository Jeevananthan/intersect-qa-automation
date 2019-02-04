@HS @HS2
Feature: HS - RepVisits - SetupWizard - As an HS user, I should be able to set up Visit availability using the setup wizard

  @MATCH-2436
  Scenario: As a HS user, I want to be taken to the Calendar page (month view) after my school's RepVisits' wizard has been completed
  so I don't see the Overview page as my first experience with RepVisits.
   #Naviance
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
#Pre condition
    Then HS I complete the setup wizard
    And HS I navigate to the college visits page
    Then HS I verify the default calendar page present after the Wizard completion
    And HS I successfully sign out
   #Non-Naviance
    Given HS I am logged in to Intersect HS as user type "admin"
    Then HS I complete the setup wizard
    And HS I navigate to the college visits page
    Then HS I verify the default calendar page present after the Wizard completion

  @MATCH-1945
  Scenario: As a new RepVisits user,I want the setup wizard to confirm my school's timezone
            So that I can be sure my appointments will be scheduled at the right time.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
#Pre condition
    Then HS I complete the setup wizard
    And HS I verify the repvisit setup wizard displayed for high school information
    Then HS I check the time zone is selected as "America/Mexico_City" and change it to "America/New_York"
    And HS I navigate to college fairs,visits through availability option
    And HS I successfully sign out

  @MATCH-1944
  Scenario: As a new RepVisits user,I want a setup wizard with an introduction that describes what the system does
            so that I can be encouraged to set up my RepVisits account.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
#Pre condition
    Then HS I complete the setup wizard
    Then HS I am verifying the welcome milestone in setup wizard
    And HS I click the Get Started button in the welcome milestone page
    And HS I navigate to college fairs,visits through availability option
    And HS I successfully sign out

  @MATCH-1949
  Scenario: As a new RepVisits user,I want the setup wizard to help me configure my calendars
            so that I can see all my calendar information in one place.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
#Pre condition
    Then HS I complete the setup wizard
    And HS I verify the Calendar Sync Milestone in the setup wizard of repvisits
    And HS I successfully sign out

  @MATCH-1948
  Scenario: As a new RepVisits user,I want the setup wizard to help me configure my school's contacts.
            so that I can be sure internal notifications will be routed to the people who need the information.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
#Pre condition
    Then HS I complete the setup wizard
    And HS I change the primary contact from "IAM Purple" to "Jennifer TestAdmin" and verify that the save option is working
    And HS I successfully sign out

  @MATCH-1946
  Scenario Outline: As a new RepVisits user,I want the setup wizard to walk me through my availability settings
                    so that I can be sure my RepVisits account is properly set up.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
#Pre condition
    Then HS I complete the setup wizard
    Then HS I go to welcome wizard of the repvisits
    And HS I navigate to "Availability" wizard in repvisits
    Then HS I add the time slot in "Monday" with start time as "5:00am" and end time as "2:00pm" and "5" visits with "1"
    And HS I navigate to sub tab "Blocked Days" in availability wizard
    Then HS I select "LABOR_DAY" in blocked days tab and verify saving option works successfully
    And HS I navigate to sub tab "Exceptions" in availability wizard
    Then HS I change to "next week" in exception and verify saving option works successfully
    And HS I navigate to sub tab "Availability Settings" in availability wizard
    Then HS I set the RepVisits Visits Confirmations option to "<Visits Confirmation>","<Prevent colleges scheduling new visits>","<Prevent colleges cancelling or rescheduling>"

    Examples:
      |Visits Confirmation                                 |Prevent colleges scheduling new visits|Prevent colleges cancelling or rescheduling|
      |No, I want to manually review all incoming requests.|5                                     |5                                          |

  @MATCH-1950
  Scenario: As a new RepVisits user,
            I want the setup wizard to guide me through final steps in the new user experience
            so that I can decide on my appointments' visibility and then continue into the system.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
#Pre condition
    Then HS I complete the setup wizard
    #FAIRS
    Then HS I select the "Fairs" option on the welcome page in the RepVisits setup wizard
    Then HS I select the "Only Me" option for Visit Availability on the 'One Last Step' page
    Then HS I verify the 'You're All Set' page is correct when Visit Availability is set to "Only Me"
    Then HS I select the "All RepVisits Users" option for Visit Availability on the 'One Last Step' page
    Then HS I verify the 'You're All Set' page is correct when Visit Availability is set to "All RepVisits Users"
    #VISITS
    Then HS I select the "Visits" option on the welcome page in the RepVisits setup wizard
    Then HS I select the "Only Me" option for Visit Availability on the 'One Last Step' page
    Then HS I verify the 'You're All Set' page is correct when Visit Availability is set to "Only Me"
    Then HS I select the "All RepVisits Users" option for Visit Availability on the 'One Last Step' page
    Then HS I verify the 'You're All Set' page is correct when Visit Availability is set to "All RepVisits Users"
    #VISITS AND FAIRS
    Then HS I select the "Visits and Fairs" option on the welcome page in the RepVisits setup wizard
    Then HS I select the "Only Me" option for Visit Availability on the 'One Last Step' page
    Then HS I verify the 'You're All Set' page is correct when Visit Availability is set to "Only Me"
    Then HS I select the "All RepVisits Users" option for Visit Availability on the 'One Last Step' page
    Then HS I verify the 'You're All Set' page is correct when Visit Availability is set to "All RepVisits Users"


  @MATCH-1947 @ignore
  Scenario Outline: As a HS RepVisits user I want to be able to use the rep-visits setup page to set the Messaging options
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
#Pre condition
    Then HS I complete the setup wizard
    Then HS I verify the Messaging Options Page in the repvists setup wizard "<verify Confirmation Message>","<verify Special Instruction for RepVisits>"
    Then HS I enter the messages in the Message Option page for Repvists Setup wizard "<Confirmation Message_Next>","<Special Instruction for RepVisits_Next>","<Button to Click_Next>"
    Then HS I verify the Primary Contact for Visits page and then click the "Back" button
    Then HS I verify the Messaging Options Page in the repvists setup wizard "<verify Confirmation Message_Next>","<verify Special Instruction for RepVisits_Next>"
    Then HS I enter the messages in the Message Option page for Repvists Setup wizard "<Confirmation Message_Back>","<Special Instruction for RepVisits_Back>","<Button to Click_Back>"
    Then HS I verify the Availability Settings page and then click the "Next" button
    Then HS I verify the Messaging Options Page in the repvists setup wizard "<verify Confirmation Message_Next>","<verify Special Instruction for RepVisits_Next>"

    Examples:
      |verify Confirmation Message|verify Special Instruction for RepVisits|Confirmation Message_Next                |Special Instruction for RepVisits_Next     |Button to Click_Next|verify Confirmation Message_Next         |verify Special Instruction for RepVisits_Next|Confirmation Message_Back                     |Special Instruction for RepVisits_Back          |Button to Click_Back|
      |                           |                                        |Confirmation Message for visits and Fairs|Special Instructions for the repvisits User|Next                |Confirmation Message for visits and Fairs|Special Instructions for the repvisits User  |Confirmation Message for visits and Fairs Back|Special Instructions for the repvisits User Back|Back                |



  @MATCH-2294
  Scenario: As a Non-Naviance HS, I want the RepVisit setup wizard to not have Naviance Settings options.
            So that I can update RepVisit college fairs and visits
    Given HS I want to login to the HS app using "purplehsautomation+HSSolidRock@gmail.com" as username and "Password!1" as password
    Then HS I complete the setup wizard
    Then HS I verify I can make it through the RepVisits wizard as a non-Naviance HS

  @MATCH-3462
  Scenario: As a RepVisits HS user that is interested in opting in to connect events with Naviance, I want the copy on
            the screen to clearly provide me with information on my ability to opt in/out of the publish connection,
            so that I know what the implications are for connecting and whether I can disconnect the sync.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
#Pre condition
    Then HS I complete the setup wizard
    Then HS I navigate to the Naviance Settings page through the setup Wizard
    And HS I verify the UI of the Naviance Settings Page in setup wizard