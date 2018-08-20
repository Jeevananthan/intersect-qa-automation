@HS
Feature: HE - Home - HomePage - As an HE user, I want to be able to access the features of the Home page

  @MATCH-4661 @MATCH-4664 @MATCH-4658
  Scenario Outline: As an HE user in Intersect, I need to see the Intersect Connection subscription module
  so that I can access the configure my Intersect Connection settings and access my connections effectively.
    Given HS I am logged in to Intersect HS as user type "<user>"
    Then HS I verify that the text in the button for "<module>" is "<button>"
    Then HS I verify that "<pageURL>" is opened from the "<module>" module
    And HS I successfully sign out
       Examples:
      | user          | module              | button      | pageURL                 |
      | administrator | RepVisits           | SCHEDULE    | rep-visits/calendar     |
      | member        | RepVisits           | SCHEDULE    | rep-visits/calendar     |
#  skipped due to MATCH-4997
#      | administrator | Your settings       | CONFIGURE   | settings/change-profile |
#      | member        | Your settings       | CONFIGURE   | settings/change-profile |
      | administrator | Counselor Community | PARTICIPATE | community/              |
      | member        | Counselor Community | PARTICIPATE | community/              |
