Feature: As a HS student accessing SuperMatch through Family Connection I need to be able to search college based on
  certain fit criteria

  @MATCH-3359 @MATCH-3361 @MATCH-3362 @MATCH-3363 @MATCH-3365 @MATCH-3366 @MATCH-3367 @MATCH-3369
  Scenario Outline: As a HS student, I want to filter colleges I am searching for by Resources category so I can see relevant colleges that match my requirements.
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I click on "Resources" fit criteria
    Then SM I check the selection and deselection and Must Have box functionality for "<ResourcesCheckbox>" checkbox
    Then SM I check when "<ResourcesCheckbox>" is selected, moved to Nice To Have, unselected, and then selected again it should be defaulted back to the Must Have box
    Examples: SM checking each checkbox under Resources
      | ResourcesCheckbox                           |
      | Academic/Career Counseling                  |
      | Tutoring Services                           |
      | Remedial Services                           |
      | ESL/ELL Services                            |
      | Physical Accessibility                      |
      | Services for the Blind or Visually Impaired |
      | Services for the Deaf and Hard of Hearing   |
      | Day Care Services                           |






