@HS
Feature:  As an HS user, I want to be able to access the features of the RepVisits module.


  @MATCH-1779 @MATCH-1735 @NotInQA
  Scenario: As a HS RepVisits user I need to be able to navigate to a page for availability settings
            So that I can manage my college visits within the times that I am typically available.
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I verify the following tabs exist on the RepVisits page
      |Overview |Calendar |Availability & Settings |College Fairs |Contacts |Notifications & Tasks |
    And HS I verify the Availability & Settings tab of the RepVisits page
    And HS I successfully sign out

  @MATCH-1586
  Scenario: As an HS User I want to be able to use the Availability and Settings tab of RepVisits to Set Time Zone
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    And HS I verify the Availability & Settings tab of the RepVisits page
    And HS I set the RepVisits Availability & Settings time zone to "US/Central"
    And HS I click on Availability on the Availability & Settings tab in RepVisits
    Then HS I verify the time zone in Repvisits Availability & Settings is "US/Central"
    And HS I set the RepVisits Availability & Settings time zone to "US/Eastern"
    And HS I successfully sign out

  @MATCH-1625
  Scenario: As a high school counselor using Naviance and RepVisits,
  I want to integrate my RepVisits account with Naviance college visits
  So that I do not have to manually enter appointments.
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I verify the Naviance Settings section of the Availability & Settings tab of the RepVisits page
    And HS I successfully sign out

  @MATCH-1577
  Scenario Outline: As a high school community member,
  I want to be able to indicate the date ranges for which I am available for college visits,
  so that colleges know when to visit my high school.
    Given HS I am logged in to Intersect HS through Naviance with account "blue4hs" and username "iam.purple" and password "password"
    Then HS I verify is able to pick specific date into date ranges calendar for college visits using "<StartDate>" and "<EndDate>"
    And HS I successfully sign out

    Examples:
      |StartDate            |EndDate           |
      |July 23 2017         |Jun 23 2018       |
      |August 15 2017       |September 23 2018 |