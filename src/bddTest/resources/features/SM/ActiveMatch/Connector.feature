@SM
Feature: AMNG - AM NextGen Connector

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
