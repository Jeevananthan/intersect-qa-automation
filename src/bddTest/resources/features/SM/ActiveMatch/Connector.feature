@SM
Feature: AMNG - AM NextGen Connector

  @MATCH-5151 @ignore
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