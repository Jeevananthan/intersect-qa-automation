Feature: As a HS student accessing SuperMatch through Family Connection I need to be able to search college based on
  certain fit criteria

  @MATCH-3367
  Scenario: As a HS student, I want to filter colleges I am searching for by Services for the Deaf and Hard of
  Hearing within the Resources category so I can see relevant colleges that match my Services for the Deaf and Hard
  of Hearing requirement.
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I click on "Resources" fit criteria
    Then SM I check the selection and deselection and Must Have box functionality for Services for the Deaf and Hard of Hearing checkbox
    Then SM I check when Services for the Deaf and Hard of Hearing is selected, moved to Nice To Have, unselected, and then selected again it should be defaulted back to the Must Have box


