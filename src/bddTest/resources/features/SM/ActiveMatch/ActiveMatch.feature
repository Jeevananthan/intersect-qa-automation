@SM
Feature: SM - ActiveMatch Next Gen

  @MATCH-5033
  Scenario: As a student viewing Colleges Looking For Students Like You in Naviance Student CollegeMatch,
  I would like a linked modal to understand what I am looking at in more detail so I can understand how to use this tool.
    When SM I am logged in to SuperMatch through Family Connection as user "linussupermatch" with password "Hobsons!23" from school "blue1combo"
    Then SM I navigate to page via URL path "colleges/match/activematch-next"
    Then HE I click the link "Why are these colleges listed?"
    Then I check if I can see "Why are these colleges listed?" on the page
    Then I check if I can see "You match the profile of students that this college is looking for, and" on the page
    Then I check if I can see "Please note, there may be other colleges that are also a good fit for you. Use" on the page
    Then I click on close icon
    Then HE I click the link "Why are these colleges listed?"

  @MATCH-5031
  Scenario: As a student in Naviance viewing Colleges Looking for Students Like You page in CollegeMatch,
  I would like to be able to understand better why this HE school is interested in me so that I can make a good decision about connecting.
    When SM I am logged in to SuperMatch through Family Connection as user "linussupermatch" with password "Hobsons!23" from school "blue1combo"
    And I clear the onboarding popups if present
    And SM I clear all pills from Must have  and Nice to have boxes
    Then SM I select the "Bachelor's" radio button from the Academics fit criteria
    And SM I select the following majors in the SEARCH MAJORS multi-select combobox for Bachelor's degree type
    |Creative Writing|
    And SM I select the following majors in the SEARCH MAJORS multi-select combobox for Bachelor's degree type
    |Philosophy|
    And SM I navigate to page via URL path "colleges/match/activematch-next"
    Then SM I click on the "Creative Writing " for the "The University of Alabama" card
    Then I check if I can see "The University of Alabama" on the page
    Then I check if I can see "Tuscaloosa, AL" on the page
    Then I check if I can see "Learn how they fit your interests:" on the page
    Then I check if I can see "English Language and Literature/Letters" on the page
    Then I check if I can see "Creative Writing" on the page
    Then I check if I can see "Show different messaging per major Creative Writing" on the page
    Then I check if I can see "Favorite" on the page
    Then I check if I can see "Not Interested" on the page
    Then I click on close icon
    Then SM I click on the "Philosophy " for the "The University of Alabama" card
    Then I check if I can see "Philosophy and Religious Studies" on the page
    Then I check if I can see "Philosophy" on the page
    Then I check if I can see "Show same message for all majors in a category Philosophy" on the page

  @MATCH-5507
  Scenario Outline: As a Naviance Student student user, I want to only be matched against Audience Profiles from active subscriptions.
  Active subscriptions are subscriptions where the start date is equal to or before the current date, and the end date is
  equal to or after the current date.
    #Clean existing subscriptions
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school

    #Create a subscription with today as end date.
    And SP I navigate to the GraphiQL page
    And SP I create a new subscription via GraphiQL with the data in "match-5507SubscriptionData.json" and the following settings:
      | startDate | <startDate> |
      | endDate   | <endDate>   |
    And SP I successfully sign out

    #Verify match or not match
    Given SM I am logged in to SuperMatch through Family Connection as user "linussupermatch" with password "Hobsons!23" from school "blue1combo"
    And SM I remove "The University of Alabama" from the I'm thinking about list if it is added in the list
    When SM I add "Babson College" to the Colleges I'm thinking about list if it is not already there
    And SM I go to Colleges Looking for Students Like You list
    Then SM I verify a matching card is "<cardStatus>" for "The University of Alabama"

    #Clean existing subscriptions
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school

    Examples:
    | startDate         | endDate           | cardStatus |
    | 0 days before now | 2 days after now  | displayed  |
    | 2 days after now  | 3 days after now  | not displayed |


