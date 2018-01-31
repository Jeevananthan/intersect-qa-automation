@HE
Feature: HE - Events - Verify Events functionality


  @MATCH-2912
  Scenario: As a HE User, I want to Add a Contact so that to create Event I select added contact
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I open the Events section
    And HE The Events page is displayed
    And HE I click on Create Event button
    And HE I click on box Enter Primary Contact and enter text "New Contact"
    And HE I click on New Contact link
    And HE Enter Following Data to create Contact
   |Contact Name| AdelphiContact111|
   |Contact Email| contact6@adelphi.com |
   |Contact Number| 513-746-2344 |
    And HE I select save button
    And HE I verify "AdelphiContact111" present  in the contact list
    And HE I click on box Enter Primary Contact and enter text "AdelphiContact111"
    And HE I click on Edit against contact
    And HE I click on Delete button
    And HE I click on YES on Confirmation message
    And HE I successfully sign out

  @MATCH-2912
  Scenario: As a HE User, I want to test validations for Add a Contact form
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I open the Events section
    And HE The Events page is displayed
    And HE I click on Create Event button
    And HE I click on box Enter Primary Contact and enter text "New Contact"
    And HE I click on New Contact link
    And HE I select save button
    And HE I verify required field warning messages
    And HE I successfully sign out

