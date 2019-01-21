@SM
Feature: AMNG - AM NextGen Connector

  @MATCH-5593
  Scenario: As a student using Naviance Student, when I match with an AM NextGen Connection client, I would like to see
  a Visual Step Progress Indicator on a connector form that would allow me to connect with that college so that I can send my information to them.

    #Create a new subscription
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    And SM I press button "ADD NEW SUBSCRIPTION"
    And SP I select the radio button "State" in Add new Subscription modal
    And SP I click the Next button
    And SP I fill the new subscription with the following data:
      | State                | Virginia           |
      | Diversity Filter     | All Students       |
      | Competitors          | Burlington College |
      | Majors               | yes                |
      | Connection           | yes                |
      | Start date           | 5 days from now    |
      | End date             | 6 days from now    |
    And SP I save the new subscription
    And SP I successfully sign out

    #Create Majors Messages
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I navigate to the "advanced-awareness/majors" url
    And HE I set messages for the following majors:
      | African-American/Black Studies              | Message 1 |
      | American/United States Studies/Civilization | Message 2 |
      | Asian Studies/Civilization                  | Message 3 |
      | Latin American Studies                      | Message 4 |
      | Womens Studies                              | Message 5 |
      | Biology/Biological Sciences, General        | Message 6 |
    And HE I click the advanced awareness save button

    #Verification in Naviance Student
    Given SM I am logged in to SuperMatch through Family Connection as user "linussupermatch" with password "Hobsons!23" from school "blue1combo"
    And SM I navigate to the Colleges I'm thinking about list
    And SM I remove "The University of Alabama" from the I'm thinking about list if it is added in the list
    And HE I click the link "SuperMatch®"
    And SM I clear all pills from Must have  and Nice to have boxes
    When SM I select the following majors in the SEARCH MAJORS multi-select combobox for Bachelor's degree type
      | African-American/Black Studies              |
      | American/United States Studies/Civilization |
      | Asian Studies/Civilization                  |
      | Latin American Studies                      |
      | Womens Studies                              |
      | Biology/Biological Sciences, General        |
    And SM I navigate to page via URL path "colleges/match/activematch-next"
    Then SM I verify that the string "and more" is present in the card for "The University of Alabama" college

    #Delete created subscription
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school

