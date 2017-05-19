@HS
Feature:  As an HS user, I want to be able to access the features of the RepVisits module.


  @MATCH-1779
  Scenario: As a HS RepVisits user I need to be able to navigate to a page for availability settings
            So that I can manage my college visits within the times that I am typically available.
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I verify the following tabs exist on the RepVisits page
      |Overview |Calendar |Availability & Settings |College Fairs |Contacts |Notifications |
    And HS I verify the Availability & Settings tab of the RepVisits page
    And HS I successfully sign out