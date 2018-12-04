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