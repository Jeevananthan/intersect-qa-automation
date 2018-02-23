Feature: As a HS student accessing SuperMatch through Family Connection I need to be able to search college based on certain options under
  Institution Characteristics fit criteria

  @MATCH-3343
  Scenario: As a HS student, I want to filter colleges I am searching for by Average Class Size within the Institution Characteristics category so I can see relevant colleges that match my Average Class Size requirements.
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I click on Institution Characteristics fit criteria
    Then SM I check the selection and deselection and Must Have box functionality for Average Class Size drop down list
    Then SM I check when Average Class Size filter is selected, moved to Nice To Have, unselected, and then selected again it should be defaulted back to the Must Have box





