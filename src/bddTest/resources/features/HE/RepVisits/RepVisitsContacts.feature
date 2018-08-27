@HE
Feature: HE- RepVisits - RepVisitsContacts - As an HE user, I should be able to view my HS contacts

  @MATCH-1881 @MATCH-1872
  Scenario: As an HE premium user, I need be able to search through my RepVisits Contacts
            so I can quickly find the contact I am looking to view.
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HE I validating the pagination of 25 contacts in Contacts Page
    And HE I verify the contacts list is sorted or not
    And HE I verify the contacts page in Contacts
    And HE I verify contacts details  in Contacts
      |Overview |Search and Schedule |Calendar |Travel Plan |Contacts |Recommendations|
    And HE I verify the contacts page is full or empty
    And HE I search for "HOMECONNECTION" in Contacts
    And HE I search for invalid data of "invalid data" in Contacts
    And HE I search for partial data of "HOMECON" in Contacts
    And HE I successfully sign out