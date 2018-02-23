
  @MATCH-1881 @MATCH-1872
  Scenario: As an HE premium user or HS user I need be able to search through my RepVisits Contacts
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
    And HE I search for partial data in Contacts using "HOMECONNECTION","HOMECON"
    And HE I successfully sign out

    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    And HS I validating the pagination of 25 contacts in Contacts Page
    And HS I verify the contacts list is sorted or not
    And HS I verify empty contacts page in Contacts
    And HS I verify full contacts page in Contacts
    And HS I verify the contacts page is full or empty
    And HS I verify contacts details  in Contacts
      |Overview |Calendar |Availability & Settings |College Fairs |Contacts |Notifications & Tasks|
    And HS I search for "Alma College" in Contacts
    And HS I search for invalid data of "invalid data" in Contacts
    And HE I search for partial data in Contacts using "Alma College","Alma"
    And HS I successfully sign out