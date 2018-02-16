Feature: As a HS student accessing SuperMatch through Family Connection I need to be able to search college based on
  certain fit criteria

  @MATCH-3592
  Scenario: As a HS student accessing SuperMatch through Family Connection I need to be presented with an Student Body
            Size fit criteria
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I verify the Student Body UI in Resources Dropdown

  @MATCH-3367
  Scenario: As a HS student, I want to filter colleges I am searching for by Services for the Deaf and Hard of
  Hearing within the Resources category so I can see relevant colleges that match my Services for the Deaf and Hard
  of Hearing requirement.
    Given SM I am logged in to SuperMatch through Family Connection
    Then I click on Resources fit criteria
    Then I check the selection and deselection and Must Have box functionality for Services for the Deaf and Hard of Hearing checkbox
    Then I check when Services for the Deaf and Hard of Hearing is selected, moved to Nice To Have, unselected, and then selected again it should be defaulted back to the Must Have box
