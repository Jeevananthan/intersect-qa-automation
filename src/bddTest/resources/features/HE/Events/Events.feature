@HE
Feature: HE - Events - Verify Events functionality


  @MATCH-2912
  Scenario: As a HE User, I want to Add a Contact so that to create Event I select added contact
    Given HE I am logged in to Intersect HE as user type "Administrator"
    And HE I open the Events section
    And HE The Events page is displayed
    And HE I click on Create Event button
    And HE I click on box Enter Primary Contact
    And HE I click on New Contact link
    And HE Enter Following Data to create Contact
    | AdelphiContact |
    | contact@adelphi.com |
    | 513-746-2317 |
    And HE I select check box "Save Contact"
    And HE I click on saveupdate button
    And HE I enter "AdelphiContact" in the Event Primary Contact field
    And HE I verify "AdephiContact" present  in the contact list
    And HE I successfully sign out

  @MATCH-2912
  Scenario: As a HE User, I want to test validations for Add a Contact form
    Given HE I am logged in to Intersect HE as user type "Administrator"
    And HE I open the Events section
    And HE The Events page is displayed
    And HE I click on Create Event button
    And HE I click on box Enter Primary Contact
    And HE I click on New Contact link
    And HE I click on save button
    And HE I verify required field warning messages
    And HE I successfully sign out

  @MATCH-2912
  Scenario: As a HE User, I want to delete a Contact that is associated with Unpublished event
    Given HE I am logged in to Intersect HE as user type "Administrator"
    And HE I open the Events section
    And HE The Events page is displayed
    And HE I Navigate to Draft Tab
    And HE I click on Edit against event "Event2DeleteContact"
    And HE I Enter Primary Contact "AbbyforAdelphi" and I click on Edit
    And HE I click on Delete button
    And HE I click on Confirmation Message Cancel
    And HE I verify user returned to previous screen
    And HE I click on Delete button
    And HE I click on delete again on Confirmation message
    And HE I Verify that Contact is deleted
    And HE I successfully sign out

  @MATCH-2912
  Scenario: As a HE User, I want to delete a Contact that is associated with Published event
    Given HE I am logged in to Intersect HE as user type "Administrator"
    And HE I open the Events section
    And HE The Events page is displayed
    And HE I Navigate to Published Tab
    And HE I click on Edit against event "EventDeleteContact"
    And HE I Enter Primary Contact "AbbyforAdelphi" and I click on Edit
    And HE I click on Delete button
    And HE I verify information message "Actual Message"
    And HE I successfully sign out

  @MATCH-2912
  Scenario: As a HE User, I want to delete a Contact that is associated with Expired event
    Given HE I am logged in to Intersect HE as user type "Administrator"
    And HE I open the Events section
    And HE The Events page is displayed
    And HE I Navigate to Expired Tab
    And HE I click on Edit against event "ExpiredEvent"
    And HE I Enter Primary Contact "BenforAdelphi" and I click on Edit
    And HE I click on Delete button
    And HE I click on delete again on Confirmation message
    And HE I Verify that Contact is deleted
    And HE I Verify Contact is assigned to Expired Event
    And HE I Enter "BenforAdelphi"
    And HE I verify "No results found"
    And HE I successfully sign out
