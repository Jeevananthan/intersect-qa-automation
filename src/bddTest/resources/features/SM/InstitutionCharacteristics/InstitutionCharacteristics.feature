@SM
Feature: SM - Institution Characteristics - As a HS student, I need to be able to search for colleges based on the 'Institution Characteristics' fit criteria

  @MATCH-3747
  Scenario Outline: As a HS student, I want to filter colleges I am searching for by High Job Placement Rate category checkbox so I can see relevant colleges that match my requirements.
    Given SM I am logged in to SuperMatch through Family Connection


    sddfdd
    jhkf

    Then SM I select the "<ResourcesCheckboxOption>" checkbox from the Resources fit criteria
    And SM I verify that the Must Have box contains "<ResourcesCheckboxOption>"
    Then SM I unselect the "<ResourcesCheckboxOption>" checkbox from the Resources fit criteria
    And SM I verify that the Must Have box does not contain "<ResourcesCheckboxOption>"
    Then SM I select the "<ResourcesCheckboxOption>" checkbox from the Resources fit criteria
    And SM I move "<ResourcesCheckboxOption>" from the Must Have box to the Nice to Have box
    Then SM I unselect the "<ResourcesCheckboxOption>" checkbox from the Resources fit criteria
    Then SM I select the "<ResourcesCheckboxOption>" checkbox from the Resources fit criteria
    And SM I verify that the Must Have box contains "<ResourcesCheckboxOption>"
    Examples: Each of the available options for the Resources fit criteria
      | ResourcesCheckboxOption                     |
      | Learning Differences Support                |
      | Academic/Career Counseling                  |
      | Counseling Services                         |
      | Tutoring Services                           |
      | Remedial Services                           |
      | ESL/ELL Services                            |
      | Physical Accessibility                      |
      | Services for the Blind or Visually Impaired |
      | Services for the Deaf and Hard of Hearing   |
      | Asperger's/Autism Support                   |
      | Day Care Services                           |
