@SM
Feature: SM - Footer - FooterUI - As a SuperMatch user, I want to be able to access and use the SuperMatch footer

  @MATCH-4743
  Scenario: Ensure that the 'More' button is working as expected for SuperMatch Student
    Given SM I am logged in to SuperMatch through Family Connection as user type "student" 
    Then SM I verify the "Upcoming Visits" link in the SuperMatch Footer
    And SM I verify the "Events" link in the SuperMatch Footer

  @MATCH-4683
  Scenario: The 'Clear Pinned Schools' action is taken, the count in the footer in embedded SuperMatch
  no longer changes to zero.
    When SM I am logged in to SuperMatch through Family Connection
    And I clear the onboarding popups if present
    And SM I check pinned colleges count

  @MATCH-4833
  Scenario: As a HS student that just pinned a college from the "Search by College Name" search box in the footer,
  I want the main "Your Results" to refresh to show that pinned college at the top of the list.
    When SM I am logged in to SuperMatch through Family Connection as user type "4833"
    And I clear the onboarding popups if present
    And SM I clear all pills from Must have  and Nice to have boxes
    And SM I clear all the pinned college
    When I select the following data from the Admission Fit Criteria
      | Acceptance Rate | 25% or Lower |
    And SM I pin "The University of Alabama" from the search box
    Then SM I verify that the college "The University of Alabama" pinned from the search box is displayed at the top of the list
