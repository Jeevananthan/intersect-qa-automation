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

    @MATCH-5029
    Scenario: We need to put both the AM Legacy Matches and AM NextGen matches into the same component so that they
    can be displayed similarly on the Colleges Looking For Students Like You page in CollegeMatch of Naviance Student.
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

    #Make matches verifications
      Given SM I am logged in to SuperMatch through Family Connection as user "linussupermatch" with password "Hobsons!23" from school "blue1combo"
      And SM I remove "The University of Alabama" from the I'm thinking about list if it is added in the list
      When SM I add "Babson College" to the Colleges I'm thinking about list if it is not already there
      And SM I go to Colleges Looking for Students Like You list
      Then SM I check ActiveMatch NextGen matches are displayed
      Then SM I check Legacy AM matches are displayed
      Then SM I check Legacy AM match for "The University of Alabama" displayes:
        | 702 miles from your school |
        | Favorite                   |
        | Not Interested            |

    #Clean subscriptions
      Given SP I am logged in to the Admin page as an Admin user
      When SP I select "The University of Alabama" from the institution dashboard
      And HE I click the link "Advanced Awareness"
      And SP I delete all the subscriptions for school

  @MATCH-5004
  Scenario: As a Naviance Student student user, I'd like to see why I matched with an HE institution who has
  an Advanced Awareness subscription configuration, because I am interested in a particular major.
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    And SP I navigate to the GraphiQL page
    And SP I create a new subscription via GraphiQL with the data in "match-5507SubscriptionData.json" and the following settings:
      | startDate | 0 days before now |
      | endDate   | 2 days after now  |
    And SP I successfully sign out
    Given SM I am logged in to SuperMatch through Family Connection as user "linussupermatch" with password "Hobsons!23" from school "blue1combo"
    And I clear the onboarding popups if present
    And SM I clear all pills from Must have  and Nice to have boxes
    And SM I remove "The University of Alabama" from the I'm thinking about list if it is added in the list
    And SM I navigate to page via URL path "colleges/supermatch-next"
    Then SM I select the following majors in the SEARCH MAJORS multi-select combobox for Bachelor's degree type
      | Accounting       |
      | Philosophy       |
      | Geography        |
      | Physics, General |
      | Social Work      |
    And SM I go to Colleges Looking for Students Like You list
    Then SM I verify the card for "The University of Alabama" contains:
      | Accounting       |
      | Philosophy       |
      | Geography        |
      | Physics, General |
      | Social Work      |
    And SM I navigate to page via URL path "colleges/supermatch-next"
    And SM I remove the "Major [5]" fit criteria from the Must Have box or Nice to Have box
    Then SM I select the following majors in the SEARCH MAJORS multi-select combobox for Bachelor's degree type
      | Accounting       |
      | Philosophy       |
      | Geography        |
      | Physics, General |
      | Social Work      |
      | Anthropology |
    And SM I go to Colleges Looking for Students Like You list
    Then SM I verify the card for "The University of Alabama" contains:
      | and more |
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school

  @MATCH-5201 @MATCH-5813
  Scenario: As a Naviance Student student user, I'd like to see why I matched with an HE institution who has
  an Advanced Awareness subscription configuration, because I am interested in another school
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    And SP I navigate to the GraphiQL page
    And SP I create a new subscription via GraphiQL with the data in "match-5201SubscriptionData.json" and the following settings:
      | startDate | 0 days before now |
      | endDate   | 2 days after now  |
    And SP I successfully sign out
    Given SM I am logged in to SuperMatch through Family Connection as user "linussupermatch" with password "Hobsons!23" from school "blue1combo"
    And I clear the onboarding popups if present
    And SM I remove "The University of Alabama" from the I'm thinking about list if it is added in the list
    And SM I add "Babson College" to the Colleges I'm thinking about list if it is not already there
    And SM I add "Auburn University" to the Colleges I'm thinking about list if it is not already there
    And SM I add "Assumption College" to the Colleges I'm thinking about list if it is not already there
    And SM I add "Art Academy of Cincinnati" to the Colleges I'm thinking about list if it is not already there
    And SM I add "Anna Maria College" to the Colleges I'm thinking about list if it is not already there
    And SM I navigate to page via URL path "colleges/applying-to/add"
    And SM I add "Babson College" to I'm applying list
    And SM I go to Colleges Looking for Students Like You list
    Then SM I verify the card for "The University of Alabama" contains:
      | Babson College                |
      | Auburn University |
      | Assumption College            |
      | Art Academy of Cincinnati     |
      | Anna Maria College            |
    And SM I navigate to the Colleges I'm thinking about list
    And SM I add "American University" to the Colleges I'm thinking about list if it is not already there
    And SM I go to Colleges Looking for Students Like You list
    Then SM I verify the card for "The University of Alabama" contains:
      | & more... |
    Then SM I verify that the text '& more...' is a link in "The University of Alabama" match card
    Then SM I remove "Babson College" from the I'm thinking about list if it is added in the list
    Then SM I remove "Auburn University" from the I'm thinking about list if it is added in the list
    Then SM I remove "Assumption College" from the I'm thinking about list if it is added in the list
    Then SM I remove "Art Academy of Cincinnati" from the I'm thinking about list if it is added in the list
    Then SM I remove "Anna Maria College" from the I'm thinking about list if it is added in the list
    Then SM I remove "American University" from the I'm thinking about list if it is added in the list
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school

  @MATCH-5707
  Scenario: Naviance Student users are able to see more than 10 AMNG cards on the college match page
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    When SP I select "Auburn University" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    When SP I select "Assumption College" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    When SP I select "Art Academy of Cincinnati" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    When SP I select "Anna Maria College" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    When SP I select "American University" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    When SP I select "Boston University" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    When SP I select "University of Washington Seattle" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    When SP I select "University of Sydney" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    When SP I select "The University of Arizona" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    When SP I select "University of California-Berkeley" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    And SP I navigate to the GraphiQL page
    And SP I create a new subscription via GraphiQL with the data in "match-5707SubscriptionData1.json" and the following settings:
      | startDate | 0 days before now |
      | endDate   | 2 days after now  |
    And SP I create a new subscription via GraphiQL with the data in "match-5707SubscriptionData2.json" and the following settings:
      | startDate | 0 days before now |
      | endDate   | 2 days after now  |
    And SP I create a new subscription via GraphiQL with the data in "match-5707SubscriptionData3.json" and the following settings:
      | startDate | 0 days before now |
      | endDate   | 2 days after now  |
    And SP I create a new subscription via GraphiQL with the data in "match-5707SubscriptionData4.json" and the following settings:
      | startDate | 0 days before now |
      | endDate   | 2 days after now  |
    And SP I create a new subscription via GraphiQL with the data in "match-5707SubscriptionData5.json" and the following settings:
      | startDate | 0 days before now |
      | endDate   | 2 days after now  |
    And SP I create a new subscription via GraphiQL with the data in "match-5707SubscriptionData6.json" and the following settings:
      | startDate | 0 days before now |
      | endDate   | 2 days after now  |
    And SP I create a new subscription via GraphiQL with the data in "match-5707SubscriptionData7.json" and the following settings:
      | startDate | 0 days before now |
      | endDate   | 2 days after now  |
    And SP I create a new subscription via GraphiQL with the data in "match-5707SubscriptionData8.json" and the following settings:
      | startDate | 0 days before now |
      | endDate   | 2 days after now  |
    And SP I create a new subscription via GraphiQL with the data in "match-5707SubscriptionData9.json" and the following settings:
      | startDate | 0 days before now |
      | endDate   | 2 days after now  |
    And SP I create a new subscription via GraphiQL with the data in "match-5707SubscriptionData10.json" and the following settings:
      | startDate | 0 days before now |
      | endDate   | 2 days after now  |
    And SP I create a new subscription via GraphiQL with the data in "match-5707SubscriptionData11.json" and the following settings:
      | startDate | 0 days before now |
      | endDate   | 2 days after now  |
    And SP I successfully sign out
    Given SM I am logged in to SuperMatch through Family Connection as user "linussupermatch" with password "Hobsons!23" from school "blue1combo"
    And I clear the onboarding popups if present
    And SM I remove "The University of Alabama" from the I'm thinking about list if it is added in the list
    And SM I remove "Auburn University" from the I'm thinking about list if it is added in the list
    And SM I remove "Assumption College" from the I'm thinking about list if it is added in the list
    And SM I remove "Art Academy of Cincinnati" from the I'm thinking about list if it is added in the list
    And SM I remove "Anna Maria College" from the I'm thinking about list if it is added in the list
    And SM I remove "American University" from the I'm thinking about list if it is added in the list
    And SM I remove "Boston University" from the I'm thinking about list if it is added in the list
    And SM I remove "University of Washington" from the I'm thinking about list if it is added in the list
    And SM I remove "University of Sydney" from the I'm thinking about list if it is added in the list
    And SM I remove "The University of Arizona" from the I'm thinking about list if it is added in the list
    And SM I remove "University of California,Berkeley" from the I'm thinking about list if it is added in the list
    And SM I add "Babson College" to the Colleges I'm thinking about list if it is not already there
    And SM I go to Colleges Looking for Students Like You list
    Then SM I verify there are more than 10 AM cards
    Then SP I am logged in to the Admin page as an Admin user
    Then SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    When SP I select "Auburn University" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    When SP I select "Assumption College" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    When SP I select "Art Academy of Cincinnati" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    When SP I select "Anna Maria College" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    When SP I select "American University" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    When SP I select "Boston University" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    When SP I select "University of Washington Seattle" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    When SP I select "University of Sydney" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    When SP I select "The University of Arizona" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    When SP I select "University of California-Berkeley" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school


  @MATCH-3578
  Scenario: As a HS student who has favorited a college, I want to be presented with a new 'Connect' action/text+icon
  for that college when viewing search results or the SuperMatch 'Why?' drawer so I am always presented with the option
  to change my mind to 'Connect'.
#    Given SP I am logged in to the Admin page as an Admin user
#    When SP I select "The University of Alabama" from the institution dashboard
#    And HE I click the link "Advanced Awareness"
#    And SP I delete all the subscriptions for school
#    When SP I select "Auburn University" from the institution dashboard
#    And HE I click the link "Advanced Awareness"
#    And SP I delete all the subscriptions for school
#    When SP I select "Assumption College" from the institution dashboard
#    And HE I click the link "Advanced Awareness"
#    And SP I delete all the subscriptions for school
#    And SP I navigate to the GraphiQL page
#    And SP I create a new subscription via GraphiQL with the data in "match-5707SubscriptionData1.json" and the following settings:
#      | startDate | 0 days before now |
#      | endDate   | 2 days after now  |
#    And SP I create a new subscription via GraphiQL with the data in "match-5707SubscriptionData2.json" and the following settings:
#      | startDate | 0 days before now |
#      | endDate   | 2 days after now  |
#    And SP I create a new subscription via GraphiQL with the data in "match-5707SubscriptionData4.json" and the following settings:
#      | startDate | 0 days before now |
#      | endDate   | 2 days after now  |
#    And SP I successfully sign out
    Given SM I am logged in to SuperMatch through Family Connection as user "linussupermatch" with password "Hobsons!23" from school "blue1combo"
#    And I clear the onboarding popups if present
#    And SM I remove "The University of Alabama" from the I'm thinking about list if it is added in the list
#    And SM I remove "Auburn University" from the I'm thinking about list if it is added in the list
#    And SM I remove "Assumption College" from the I'm thinking about list if it is added in the list
#    And SM I add "Babson College" to the Colleges I'm thinking about list if it is not already there
#    And SM I navigate to page via URL path "colleges/supermatch-next"
    And SM I clear pinned schools list
#    Then SM I pin "Williams College" from the search box
#    Then SM I pin "Bennett College" from the search box
    And SM I pin "The University of Alabama" from the search box
    And SM I pin "Auburn University" from the search box
    And SM I pin "Assumption College" from the search box

    Then SM I favorite the school "The University of Alabama"
    Then I check if I can see "Great news!" on the page
    Then I check if I can see "would like to connect with you." on the page
    Then SM I press button "No, Thanks"
    Then SM I un-favorite the school "The University of Alabama"
    Then SM I favorite the school "The University of Alabama"
    Then I check there is no "Great news!" text on the page

    Then SM I favorite the school "Auburn University" from the why drawer
    Then I check if I can see "Great news!" on the page
    Then I check if I can see "would like to connect with you." on the page
    Then SM I press button "Connect"
    Then I check if I can see "Connect with Auburn University" on the page
    Then I click on close icon
    Then SM I un-favorite the school "Auburn University" from the why drawer
    Then SM I favorite the school "Auburn University" from the why drawer
    Then I check there is no "Great news!" text on the page

    Then SM I favorite the school "Assumption College"
    Then SM I press button "Connect"
    Then I check if I can see "Connect with Assumption College" on the page
    Then I click on close icon
    Then SM I un-favorite the school "Assumption College"
    Then SM I favorite the school "Assumption College"
    Then I check there is no "Great news!" text on the page


    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    When SP I select "Auburn University" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
    When SP I select "Assumption College" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
  @MATCH-5493
  Scenario: As a Naviance Student student user, I want to be matched based on my ethnicity when Audience Profiles have
  the diversity set to "Racial and ethnic minorities".
    #Clean existing subscriptions
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school

    #Create a subscription with today as end date.
    And SP I navigate to the GraphiQL page
    And SP I create a new subscription via GraphiQL with the data in "match-5493SubscriptionData.json" and the following settings:
      | startDate | 0 days before now |
      | endDate   | 2 days after now  |
    And SP I successfully sign out

    #Enable Diversity in HE
    When HE I am logged in to Intersect HE as user type "administrator"
    When HE I navigate to the "advanced-awareness/diversity" url
    And HE I select following Diversity Settings
    | Asian |
    And SM I press button "Save"

    #Verify match or not match
    Given SM I am logged in to SuperMatch through Family Connection as user "davidsupermatch" with password "Hobsons!23" from school "blue4hs"
    And SM I remove "The University of Alabama" from the I'm thinking about list if it is added in the list
    When SM I add "Babson College" to the Colleges I'm thinking about list if it is not already there
    And SM I go to Colleges Looking for Students Like You list
    Then SM I verify a matching card is "displayed" for "The University of Alabama"

    #Clean existing subscriptions
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SP I delete all the subscriptions for school
