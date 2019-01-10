@HE
Feature: HE - Events Contact  - Verify Events functionality - Contacts


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
    And HE Enter Following Data to create Contact
    |Contact Name| AdelphiContactUpdated1|
    |Contact Email| contactupdated@adelphi.com|
    |Contact NUmber|111-222-3333              |
    And HE I select save button
    And HE I click on box Enter Primary Contact and enter text "AdelphiContactUpdated1"
    And HE I click on Edit against contact
    And HE I click on Delete button
    And HE I click on YES on Confirmation message
    And HE I open the Events list
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

  @MATCH-2912
  Scenario: As a HE User, I want to verify delete contact warning message for unpublished/published and Expired Event
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I open the Events section
    And HE The Events page is displayed
    And HE I click on Create Event button
    And HE I click on box Enter Primary Contact and enter text "ContactForExpiredEvent"
    And HE I click on Edit against contact
    And HE I click on Delete button
    And HE I verify warning messages
    And HE I open the Events list
    And HE I successfully sign out

  @MATCH-2912
  Scenario: As a HE User, I want to verify delete contact message for Unpublished Event
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events section
    And HE The Events page is displayed
    And HE I click on Create Event button
    And HE I click on box Enter Primary Contact and enter text "New Contact"
    And HE I click on New Contact link
    And HE Enter Following Data to create Contact
      |Contact Name| AdelphiContactUnpublished|
    And HE I select save button
    And HE I open the Events list
    And HE I create and save a new event "3" minutes ahead from now with the following details:
      | Event Name     | TestEventUnpublished |
      | Max Attendees  | 30        |
      | EVENT LOCATION | Location2 |
      | EVENT PRIMARY CONTACT |AdelphiContactUnpublished|
    And HE I click on Create Event button
    And HE I click on box Enter Primary Contact and enter text "AdelphiContactUnpublished"
    And HE I click on Edit against contact
    And HE I click on Delete button
    Then HE I verify the error message when deleting the Contact associated to a unpublished event
    And HE I select No for delete Confirmation
    And HE I open the Events list
    And HE I delete the event of name "TestEventUnpublished"
    And HE I open the Create Event screen
    And HE I click on box Enter Primary Contact and enter text "AdelphiContactunpublished"
    And HE I click on Edit against contact
    And HE I click on Delete button
    And HE I click on YES on Confirmation message
    And HE I open the Events list
    And HE I successfully sign out

  @MATCH-2912
  Scenario: As a HE User, I want to verify delete contact message for Published Event
    Given HE I am logged in to Intersect HE as user type "administrator"
    When HE I open the Events section
    And HE The Events page is displayed
    And HE I click on Create Event button
    And HE I click on box Enter Primary Contact and enter text "New Contact"
    And HE I click on New Contact link
    And HE Enter Following Data to create Contact
      |Contact Name| AdelphiContactPublished|
    And HE I select save button
    And HE I open the Events list
    And HE I create and publish a new event "3" minutes ahead from now with the following details:
      | Event Name     | TestEventPublished |
      | Max Attendees  | 30        |
      | EVENT LOCATION | Location2 |
      | EVENT PRIMARY CONTACT |AdelphiContactPublished|
    And HE I click on Create Event button
    And HE I click on box Enter Primary Contact and enter text "AdelphiContactPublished"
    And HE I click on Edit against contact
    And HE I click on Delete button
    Then HE I verify the error message when deleting the Contact associated to a published event
    And HE I open the Events list
    And HE I unpublish the event of name "TestEventPublished"
    And HE I delete the event of name "TestEventPublished"
    And HE I open the Create Event screen
    And HE I click on box Enter Primary Contact and enter text "AdelphiContactPublished"
    And HE I click on Edit against contact
    And HE I click on Delete button
    And HE I click on YES on Confirmation message
    And HE I open the Events list
    And HE I successfully sign out
