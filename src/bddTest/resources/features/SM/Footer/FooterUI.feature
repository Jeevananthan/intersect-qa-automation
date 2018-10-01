@SM
Feature: SM - Footer - FooterUI - As a SuperMatch user, I want to be able to access and use the SuperMatch footer

  @MATCH-4743
  Scenario: Ensure that the 'More' button is working as expected for SuperMatch Student
    Given SM I am logged in to SuperMatch through Family Connection as user type "student" 
    Then SM I verify the "Upcoming Visits" link in the SuperMatch Footer
    And SM I verify the "Events" link in the SuperMatch Footer