Feature: HE Community User - Document Recruitment Territory within User Profile
  As an HE Community user I need to enter in specific combinations states and counties that represent my
  recruitment territory so the correct HS Community users can find and connect with me.

  Scenario: As an HE Community userquisc I can enter in specific combinations states and counties
    Given I am logged in to Purple Community through the HE App
    When I go to user profile page
    Then I click on Edit profile button
    And I add first state "Pennsylvania" with counties "Philadelphia" and "Northumberland"
    And I add second state "New York" with counties "Delaware" and "Broome"
    Then I Save changes
    And I check if first state "Pennsylvania" is saved with counties "Philadelphia" and "Northumberland"
    And I check if second state "New York" is saved with counties "Delaware" and "Broome"