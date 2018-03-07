@SM
Feature: As a HS student accessing SuperMatch through Family Connection I need to be able to search college based on
  certain fit criteria

  @MATCH-3592
  Scenario: As a HS student accessing SuperMatch through Family Connection I need to be presented with an Student Body
            Size fit criteria
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I verify the Student Body UI in Resources Dropdown



  @MATCH-3382
  Scenario: As a HS student, I want to filter colleges I am searching for by my specific ACT Scores within the Admission
            category so I can see relevant colleges that accept students similar to me based on my ACT Scores in my
             search results.
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I verify the system response when the ACT score entered by the user is valid
    Then SM I verify the system response when the ACT score entered by the user is invalid
    Then SM I verify that ACT score data is stored on our side
    Then SM I verify that ACT score doesn't become fit criteria in Must Have box