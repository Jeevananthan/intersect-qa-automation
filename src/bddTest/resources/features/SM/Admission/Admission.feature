@SM
Feature: SM - Feature - As a HS student, I need to be able to search for colleges based on the 'Admission' fit criteria

  @MATCH-3379
  Scenario: As a HS student, I want to filter colleges I am searching for by my specific GPA within the Admission
            category so I can see relevant colleges that accept students similar to me based on my GPA in my search
            results.
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I verify the system response when the GPA entered by the user is valid
    Then SM I verify the system response when the GPA entered by the user is invalid
    Then SM I verify that entered GPA data persists
    Then SM I verify that GPA doesn't become a fit criteria in the Must Have box

  @MATCH-3382
  Scenario: As a HS student, I want to filter colleges I am searching for by my specific ACT Scores within the Admission
            category so I can see relevant colleges that accept students similar to me based on my ACT Scores in my
            search results.
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I verify the system response when the ACT score entered by the user is valid
    Then SM I verify the system response when the ACT score entered by the user is invalid
    Then SM I verify that entered ACT score data persists
    Then SM I verify that ACT score doesn't become a fit criteria in the Must Have box