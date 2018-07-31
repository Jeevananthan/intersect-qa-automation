@HS
Feature:  As an HS user, I want to be able to access the features of the Search features.

  @MATCH-1881 @MATCH-1872
  Scenario: As an HS user I need be able to search through my RepVisits Contacts
  so I can quickly find the contact I am looking to view.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    And HS I validating the pagination of 25 contacts in Contacts Page
    And HS I verify the contacts list is sorted or not
    And HS I verify empty contacts page in Contacts
    And HS I verify full contacts page in Contacts
    And HS I verify the contacts page is full or empty
#    And HS I verify contacts details  in Contacts
#      |Overview |Calendar |Availability & Settings |College Fairs |Contacts |Notifications & Tasks|
    And HS I search for "The University of Alabama" in Contacts
    And HS I search for invalid data of "invalid data" in Contacts
    #Page layout is the same for HE/HS, so use the existing HE code for this.
    And HE I search for partial data of "The Univer" in Contacts
    And HS I successfully sign out


