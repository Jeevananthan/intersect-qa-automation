@SM
Feature: AMNG - AM NextGen Connector

  @MATCH-5151 @MATCH-5455 @MATCH-5488 @ignore
  Scenario: As a student using Naviance Student, when I match with an AM NextGen Connection client, I would like to see
  a connector form that would allow me to connect with that college so that I can send my information to them.

    #Clean existing subscriptions and create a new one
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    And SP I navigate to the GraphiQL page
    And SP I create a new subscription via GraphiQL with the data in "match-5151SubscriptionData.json" and the following settings:
      | startDate | 2 days before now |
      | endDate   | 2 days after now  |
    And SP I successfully sign out

    #Make the connector verifications
    Given SM I am logged in to SuperMatch through Family Connection as user "davidsupermatch" with password "Hobsons!23" from school "blue4hs"
    And SM I remove "The University of Alabama" from the I'm thinking about list if it is added in the list
    When SM I add "Babson College" to the Colleges I'm thinking about list if it is not already there
    And SM I go to Colleges Looking for Students Like You list
    And SM I add the college "The University of Alabama" to the I'm thinking about list using the heart icon in the match card
    Then SM I click the button "Yes, I do" in the connection dialog
    Then SM I verify that all the checkboxes are "checked" when "Share all" is "checked"
    Then SM I verify that "Share all" is unselected when any data checkbox is unselected, for example "Email"
    Then SM I verify that the birthday format is: Month(abbreviated) DD, YYYY
    Then SM I verify that at least one of the following fields is required for submitting the form: Email, Phone, Address
    Then SM I verify that, when "1234567891011121314" is used as value for Phone, an error message is displayed
    Then SM I verify that the following connector fields are editable:
    | First Name * |
    | Last Name *  |
    | Email      |
    | Phone      |
    | Street     |
    | City       |
    | State      |
    | Zip Code   |
    Then SM I verify that the following connector fields are not editable:
    | Gender    |
    | Birthday  |
    | Your GPA  |
    | Ethnicity |
    And HE I click the button "Submit" in the connector dialog
    And HE I click the button "Close" in the connector dialog

    #Cleanup
    And SM I remove "The University of Alabama" from the I'm thinking about list if it is added in the list
    And SM I remove "Babson College" from the I'm thinking about list if it is added in the list
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school

  @MATCH-5545 @MATCH-5745 @ignore
  Scenario: As a student using Naviance Student, when I match with an AM NextGen Connection client, I would like to see
  a Visual Step Progress Indicator on a connector form that would allow me to connect with that college so that I can send my information to them.

    #Clean existing subscriptions and create a new one
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    And SP I navigate to the GraphiQL page
    And SP I create a new subscription via GraphiQL with the data in "match-5545SubscriptionData.json" and the following settings:
    | startDate | 2 days before now |
    | endDate   | 2 days after now  |
    And SP I successfully sign out

    #Make the connector verifications
    Given SM I am logged in to SuperMatch through Family Connection as user "linussupermatch" with password "Hobsons!23" from school "blue1combo"
    And SM I remove "The University of Alabama" from the I'm thinking about list if it is added in the list
    When SM I add "Babson College" to the Colleges I'm thinking about list if it is not already there
    And SM I go to Colleges Looking for Students Like You list
    And SM I add the college "The University of Alabama" to the I'm thinking about list using the heart icon in the match card
    Then SM I verify all the steps of the Visual Step Progress Indicator with the following data:
    | Step 1 title | Want to connect?                |
    | Step 2 title | Verify your contact information |
    | Step 3 title | Successfully Submitted!         |
    And SM I remove "The University of Alabama" from the I'm thinking about list if it is added in the list
    And SM I remove "Babson College" from the I'm thinking about list if it is added in the list

    #Clean subscriptions
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school

  @MATCH-5615 @ignore
  Scenario: The Birthday field should not be required

  #Clean existing subscriptions and create a new one
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    And SP I navigate to the GraphiQL page
    And SP I create a new subscription via GraphiQL with the data in "match-5545SubscriptionData.json" and the following settings:
      | startDate | 2 days before now |
      | endDate   | 2 days after now  |
    And SP I successfully sign out

  #Make the connector verifications
    Given SM I am logged in to SuperMatch through Family Connection as user "linussupermatch" with password "Hobsons!23" from school "blue1combo"
    And SM I remove "The University of Alabama" from the I'm thinking about list if it is added in the list
    When SM I add "Babson College" to the Colleges I'm thinking about list if it is not already there
    And SM I go to Colleges Looking for Students Like You list
    And SM I add the college "The University of Alabama" to the I'm thinking about list using the heart icon in the match card
    Then SM I verify that it is possible to submit the form with "N/A" as Birthday value
    And SM I remove "The University of Alabama" from the I'm thinking about list if it is added in the list
    And SM I remove "Babson College" from the I'm thinking about list if it is added in the list

  #Clean subscriptions
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school

  @MATCH-5488 @ignore
  Scenario: The workflow is changed to display the single Connector on the College Match Colleges Looking For Students
  Like You page as soon as the student click on the heart icon and NOT redirect the student to Colleges I'm Thinking About page.

   #Clean existing subscriptions and create a new one
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    And SP I navigate to the GraphiQL page
    And SP I create a new subscription via GraphiQL with the data in "match-5545SubscriptionData.json" and the following settings:
      | startDate | 2 days before now |
      | endDate   | 2 days after now  |
    And SP I successfully sign out

   #Make the connector verifications
    Given SM I am logged in to SuperMatch through Family Connection as user "linussupermatch" with password "Hobsons!23" from school "blue1combo"
    And SM I remove "The University of Alabama" from the I'm thinking about list if it is added in the list
    When SM I add "Babson College" to the Colleges I'm thinking about list if it is not already there
    And SM I go to Colleges Looking for Students Like You list
    And SM I add the college "The University of Alabama" to the I'm thinking about list using the heart icon in the match card
    Then SM I verify that the connector dialog is displayed in the page of URL "https://student-match-qa.mango.naviance.com/colleges/match/activematch-next"
    And HE I click the button "No, Thanks" in the connector dialog
    And SM I remove "The University of Alabama" from the I'm thinking about list if it is added in the list
    And SM I remove "Babson College" from the I'm thinking about list if it is added in the list

   #Clean subscriptions
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school

  @MATCH-5455 @ignore
  Scenario: As a Naviance Student student user, I would like to be able to connect with an HE Connection client when
  I have met the match criteria, elected to connect with the college and visit the Colleges Other Students Like page,
  so that I can send my information to the college. (MATCH-5856)

    #Clean existing subscriptions and create a new one
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    And SP I navigate to the GraphiQL page
    And SP I create a new subscription via GraphiQL with the data in "match-5151SubscriptionData.json" and the following settings:
      | startDate | 2 days before now |
      | endDate   | 2 days after now  |
    And SP I successfully sign out

    #Make the connector verifications
    Given SM I am logged in to SuperMatch through Family Connection as user "davidsupermatch" with password "Hobsons!23" from school "blue4hs"
    When SM I remove "The University of Alabama" from the I'm thinking about list if it is added in the list
    And SM I add "Babson College" to the Colleges I'm thinking about list if it is not already there
    And SP I successfully sign out
    Given SM I am logged in to SuperMatch through Family Connection as user "davidparentsupermatch" with password "Hobsons!23" from school "blue4hs"
    And SM I go to Colleges Looking for Students Like You list
    And SM I add the college "The University of Alabama" to the I'm thinking about list using the heart icon in the match card
    Then SM I verify that the connector dialog is NOT displayed
    And HE I click the button "No, Thanks" in the connector dialog

    #Clean subscriptions
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school

  @MATCH-5324 @MATCH-5944
  Scenario: As a student using Naviance Student, when I match with more than one AM NextGen Connection HE client,
  I would like to see a connector form that would allow me to connect with more than one college so that I can send my information to them at once.

    #Clean existing connections, subscriptions and create new ones

    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    And SP I navigate to the GraphiQL page
    And SP I create a new subscription via GraphiQL with the data in "match-5545SubscriptionData.json" and the following settings:
      | startDate | 2 days before now |
      | endDate   | 2 days after now  |
    When SP I select "Auburn University" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    And SP I navigate to the GraphiQL page
    And SP I create a new subscription via GraphiQL with the data in "match-5324SubscriptionData.json" and the following settings:
      | startDate | 2 days before now |
      | endDate   | 2 days after now  |
    When I remove all connections for the user id "402714135"
    And SP I successfully sign out

    #Create Majors Messages
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I navigate to the "advanced-awareness/majors" url
    And HE I set messages for the following majors:
      | African-American/Black Studies              | Message 1 |
      | American/United States Studies/Civilization | Message 2 |
    And HE I click the advanced awareness save button

    #Make the connector verifications
    Given SM I am logged in to SuperMatch through Family Connection as user "jaredsupermatch" with password "Hobsons!23" from school "blue1combo"
    And SM I clear all pills from Must have  and Nice to have boxes
    When SM I select the following majors in the SEARCH MAJORS multi-select combobox for Bachelor's degree type
      | African-American/Black Studies              |
      | American/United States Studies/Civilization |
    And SM I navigate to page via URL path "colleges/applying-to"
    And SM I remove "The University of Alabama" if it is in the Colleges I'm applying to list
    When SM I add "Babson College" to the Colleges I'm thinking about list if it is not already there
    And SM I add "The University of Alabama" to the Colleges I'm thinking about list if it is not already there
    And SM I add "Auburn University" to the Colleges I'm thinking about list if it is not already there
    And SM I navigate to the Colleges I'm thinking about list
    Then SM I verify that the following text is present in the connector dialog:
    | Multiple institutions you're interested in would like to communicate with you! |
    | Select those in which you would like to connect                                |
    Then SM I verify that all the connection checkboxes are "checked" by default
    Then SM I verify that the Next button is disabled when all competitors are unchecked
    And HE I click the button "Next" in the connector dialog
    Then SM I verify that all the checkboxes are "checked" when "Share all" is "checked"
    Then SM I verify that "Share all" is unselected when any data checkbox is unselected, for example "Email"
    Then SM I verify that at least one of the following fields is required for submitting the form: Email, Phone, Address
    Then SM I verify that the following connector fields are editable:
      | Email      |
      | Phone      |
      | Street     |
      | City       |
      | State      |
      | Zip Code   |
    Then SM I verify that the following connector fields are not editable:
      | Gender    |
      | Birthday  |
      | Your GPA  |
      | Ethnicity |
    Then SM I verify that it is possible to select the value "African-American/Black Studies" in the Majors dropdown
    Then SM I verify that it is possible to select the value "American/United States Studies/Civilization" in the Majors dropdown
    Then SM I verify that it is possible to select the value "Accounting" in the Majors dropdown
    And HE I click the button "Submit" in the connector dialog
    Then SM I verify that the Successfully Submitted! screen is displayed
    And HE I click the button "Close" in the connector dialog

    #Clean subscriptions
    Given SP I am logged in to the Admin page as an Admin user
    When I remove all connections for the user id "402714135"
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    When SP I select "Auburn University" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school

  @MATCH-5929 @MATCH-5154 @concurrency
  Scenario: HS student's in Naviance student are seeing the connector for the same institution automagically popup for
  them more than once, which is against the rules.

    #Add and remove proper college from CITA
    Given SM I am logged in to SuperMatch through Family Connection as user "linussupermatch" with password "Hobsons!23" from school "blue1combo"
    When SM I add "Babson College" to the Colleges I'm thinking about list if it is not already there
    And SM I add "The University of Alabama" to the Colleges I'm thinking about list if it is not already there
    And SM I add "Auburn University" to the Colleges I'm thinking about list if it is not already there

    #Clean existing connections, subscriptions and create new ones
    Given SP I am logged in to the Admin page as an Admin user
    When I remove all connections for the user id "402661187"
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    And SP I navigate to the GraphiQL page
    And SP I create a new subscription via GraphiQL with the data in "match-5545SubscriptionData.json" and the following settings:
      | startDate | 2 days before now |
      | endDate   | 2 days after now  |
    When SP I select "Auburn University" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    And SP I navigate to the GraphiQL page
    And SP I create a new subscription via GraphiQL with the data in "match-5324SubscriptionData.json" and the following settings:
      | startDate | 2 days before now |
      | endDate   | 2 days after now  |
    And SP I successfully sign out

    #Create the connection for one of the colleges
    Given SM I am logged in to SuperMatch through Family Connection as user "linussupermatch" with password "Hobsons!23" from school "blue1combo"
    And SM I navigate to the Colleges I'm thinking about list
    Then SM I "uncheck" the checkbox for "Auburn University" in the connector dialog
    And HE I click the button "Next" in the connector dialog
    And HE I click the button "Submit" in the connector dialog
    And HE I click the button "Close" in the connector dialog

    #Verify that the connector is not displayed again
    Given SM I reload the page
    Then SM I verify that no connector dialog is displayed

    #Clean subscriptions
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    When SP I select "Auburn University" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    When I remove all connections for the user id "402661187"

  @MATCH-5153 @MATCH-5154
  Scenario: As a Naviance Student student user, I would like to be able to connect with an HE Connection client within
  SuperMatch when I have met the AM NextGen match criteria, have added to Colleges I'm Thinking About list (favorited)
  so that I can send my information to the college.

    #Add and remove proper college from CITA
    Given SM I am logged in to SuperMatch through Family Connection as user "linussupermatch" with password "Hobsons!23" from school "blue1combo"
    And SM I remove "The University of Alabama" from the Colleges I'm thinking about list via the Look Up page
    When SM I add "Babson College" to the Colleges I'm thinking about list if it is not already there

    #Clean existing connections, subscriptions and create new ones
    Given SP I am logged in to the Admin page as an Admin user
    When I remove all connections for the user id "402661187"
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    And SP I navigate to the GraphiQL page
    And SP I create a new subscription via GraphiQL with the data in "match-5545SubscriptionData.json" and the following settings:
      | startDate | 2 days before now |
      | endDate   | 2 days after now  |
    And SP I successfully sign out

    #Verifications MATCH-5154: 'No, Thanks' button
    Given SM I am logged in to SuperMatch through Family Connection as user "linussupermatch" with password "Hobsons!23" from school "blue1combo"
    And SM I navigate to page via URL path "colleges/match/looking-for-you"
    And SM I add the college "The University of Alabama" to the I'm thinking about list using the heart icon in the match card
    And HE I click the button "No, Thanks" in the connector dialog
    Then SM I verify that the URL of the current page contains "colleges/match/looking-for-you"
    And SM I remove "The University of Alabama" from the Colleges I'm thinking about list via the Look Up page

    #Clean existing connections
    Given SP I am logged in to the Admin page as an Admin user
    When I remove all connections for the user id "402661187"

     #Verifications MATCH-5154: close icon
    Given SM I am logged in to SuperMatch through Family Connection as user "linussupermatch" with password "Hobsons!23" from school "blue1combo"
    And SM I navigate to page via URL path "colleges/match/looking-for-you"
    And SM I add the college "The University of Alabama" to the I'm thinking about list using the heart icon in the match card
    And SM I close the connector with the close icon
    Then SM I verify that the URL of the current page contains "colleges/match/looking-for-you"
    And SM I remove "The University of Alabama" from the Colleges I'm thinking about list via the Look Up page

    #Clean existing connections
    Given SP I am logged in to the Admin page as an Admin user
    When I remove all connections for the user id "402661187"

    #Verifications MATCH-5154: Submit
    Given SM I am logged in to SuperMatch through Family Connection as user "linussupermatch" with password "Hobsons!23" from school "blue1combo"
    And SM I navigate to page via URL path "colleges/match/looking-for-you"
    And SM I add the college "The University of Alabama" to the I'm thinking about list using the heart icon in the match card
    And HE I click the button "Yes, I do" in the connector dialog
    And HE I click the button "Submit" in the connector dialog
    And HE I click the button "Close" in the connector dialog
    Then SM I verify that the URL of the current page contains "colleges/match/looking-for-you"
    And SM I remove "The University of Alabama" from the Colleges I'm thinking about list via the Look Up page

    #Clean existing connections
    Given SP I am logged in to the Admin page as an Admin user
    Then SM I reload the page
    When I remove all connections for the user id "402661187"

    #Verifications MATCH-5153: Results
    Given SM I am logged in to SuperMatch through Family Connection as user "linussupermatch" with password "Hobsons!23" from school "blue1combo"
    And SM I clear all pills from Must have  and Nice to have boxes
    And SM I clear pinned schools list
    And SM I pin "The University of Alabama" from the search box
    When SM I select the following majors in the SEARCH MAJORS multi-select combobox for Bachelor's degree type
      | African-American/Black Studies              |
      | American/United States Studies/Civilization |
    And SM I favorite the school "The University of Alabama"
    Then SM I verify that the connector banner is displayed
    And HE I click the button "Connect?" in the connector dialog
    Then SM I verify that it is possible to select the value "African-American/Black Studies" in the Majors dropdown
    And HE I click the button "Submit" in the connector dialog
    And HE I click the button "Close" in the connector dialog
    And SM I reload the page
    And SM I remove "The University of Alabama" from the Colleges I'm thinking about list via the Look Up page

#    #Clean existing connections
    Given SP I am logged in to the Admin page as an Admin user
    Then SM I reload the page
    When I remove all connections for the user id "402661187"

    #Verifications MATCH-5153: Why Drawer
    Given SM I am logged in to SuperMatch through Family Connection as user "linussupermatch" with password "Hobsons!23" from school "blue1combo"
    And SM I press Why button for "The University of Alabama" college
    And SM I favorite the school "The University of Alabama" from the why drawer
    Then SM I verify that the connector banner is displayed
    And HE I click the button "Connect?" in the connector dialog
    Then SM I verify that it is possible to select the value "African-American/Black Studies" in the Majors dropdown
    And HE I click the button "Submit" in the connector dialog
    And HE I click the button "Close" in the connector dialog
    And SM I remove "The University of Alabama" from the Colleges I'm thinking about list via the Look Up page

    #Clean existing connections
    Given SP I am logged in to the Admin page as an Admin user
    Then SM I reload the page
    When I remove all connections for the user id "402661187"

    #Verifications MATCH-5153: Pinned Schools
    Given SM I am logged in to SuperMatch through Family Connection as user "linussupermatch" with password "Hobsons!23" from school "blue1combo"
    And SM I open the Pinned Schools Compare screen
    And SM I favorite the school "The University of Alabama" from the Pinned Colleges screen
    Then SM I verify that the connector banner is displayed
    And HE I click the button "Connect?" in the connector dialog
    Then SM I verify that it is possible to select the value "African-American/Black Studies" in the Majors dropdown
    And HE I click the button "Submit" in the connector dialog
    And HE I click the button "Close" in the connector dialog
    And SM I remove "The University of Alabama" from the Colleges I'm thinking about list via the Look Up page

    #Clean subscriptions
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    When I remove all connections for the user id "402661187"

  @MATCH-5332
  Scenario: As a Naviance Student student user, I would like to be able to connect with one or more HE Connection client(s)
  when I have met the match criteria, elected to connect with the college(s) and visit the Colleges I'm Applying To page,
  so that I can send my information to the college(s).

    #Add and remove proper college from CITA
    Given SM I am logged in to SuperMatch through Family Connection as user "linussupermatch" with password "Hobsons!23" from school "blue1combo"
    When SM I add "Babson College" to the Colleges I'm thinking about list if it is not already there
    And SM I navigate to page via URL path "colleges/applying-to"
    And SM I remove "Adrian College" if it is in the Colleges I'm applying to list
    And SM I add "Adrian College" to the Colleges I'm thinking about list if it is not already there
    And  SM I add "Adrian College" to the Colleges I'm applying to list from the CITA page

    #Clean existing connections, subscriptions and create new ones
    Given SP I am logged in to the Admin page as an Admin user
    When I remove all connections for the user id "402661187"
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "Adrian College" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    And SP I navigate to the GraphiQL page
    And SP I create a new subscription via GraphiQL with the data in "match-5332SubscriptionData.json" and the following settings:
      | startDate | 2 days before now |
      | endDate   | 2 days after now  |
    And SP I successfully sign out

    #Verifications
    Given SM I am logged in to SuperMatch through Family Connection as user "linussupermatch" with password "Hobsons!23" from school "blue1combo"
    And SM I navigate to page via URL path "colleges/applying-to"
    Then SM I verify that the connector dialog is displayed in the page of URL "https://family-connection-ui-qa.mango.naviance.com/colleges/applying-to"
    And HE I click the button "No, Thanks" in the connector dialog
    And SM I click "CONNECT" from the More menu for "Adrian College"
    Then SM I verify that the connector dialog is displayed in the page of URL "https://family-connection-ui-qa.mango.naviance.com/colleges/applying-to"
    And SM I click the button "Yes, I do" in the connection dialog
    And HE I click the button "Submit" in the connector dialog
    And HE I click the button "Close" in the connector dialog
    Then SM I verify that "DISCONNECT" is displayed in the More menu for "Adrian College"
    And SM I click "DISCONNECT" from the More menu for "Adrian College"
    Then SM I click the button "Yes, I'm sure" in the disconnect dialog
    Then SM I verify that "CONNECT" is displayed in the More menu for "Adrian College"

    #Clean subscriptions
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "Adrian College" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    When I remove all connections for the user id "402661187"