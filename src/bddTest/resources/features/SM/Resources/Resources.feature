@SM
Feature: SM - Resources - Resources - As a HS student, I need to be able to search for colleges based on the 'Resources' fit criteria

  @MATCH-3290 @MATCH-3359 @MATCH-3361 @MATCH-3360 @MATCH-3362 @MATCH-3363 @MATCH-3365 @MATCH-3366 @MATCH-3367 @MATCH-3369 @MATCH-3368
  Scenario Outline: As a HS student, I want to filter colleges I am searching for by Resources category so I can see relevant colleges that match my requirements.
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I clear all pills from Must have  and Nice to have boxes
    Then SM I select the "<ResourcesCheckboxOption>" checkbox from the Resources fit criteria
    And SM I verify that the Must Have box contains "<ResourcesCheckboxOption>"
    Then SM I unselect the "<ResourcesCheckboxOption>" checkbox from the Resources fit criteria
    And SM I verify that the Must Have box does not contain "<ResourcesCheckboxOption>"
    Then SM I select the "<ResourcesCheckboxOption>" checkbox from the Resources fit criteria
    And SM I move "<ResourcesCheckboxOption>" from the Must Have box to the Nice to Have box
    Then SM I verify that the Nice to Have box contains "<ResourcesCheckboxOption>"
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

  @MATCH-3950
  Scenario: As a HS student that is comparing my pinned schools, I want to see Resources details about each college
  side by side so I can determine which pinned college is a best fit for me based on their Resources.
    Given SM I am logged in to SuperMatch through Family Connection
    And I clear the onboarding popups if present
    And SM I clear all the pinned college
    And SM I search for "College of the North Atlantic" college in search bar
    And SM I open the Pinned Schools Compare screen
    And SM I verify all the below options available in Resources fit criteria in Resources expandable drawer
      |Learning Differences Support |Academic/Career Counseling| Counseling Services |Tutoring Services|Remedial Services |ESL/ELL Services |Physical Accessibility |Services for the Blind or Visually Impaired |Services for the Deaf and Hard of Hearing|Asperger's/Autism Support |Day Care Services|
      |Unknown |No| Unknown| No |Yes |No| No| No| No| No| Yes|

