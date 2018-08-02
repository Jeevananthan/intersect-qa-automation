@HS
Feature:  As an HS user, I want to be able to access the features of the Account Settings page.

  @MATCH-3060 @MATCH-3061
  Scenario Outline: As a HS Naviance RepVisits user,I want one page to go to to manage all my settings
                    To simplify my RepVisits configuration experience.

    Given HS I am logged in to Intersect HS through Naviance with account "<navianceUserType>"
    And HS I verify the items in the user dropdown for a Naviance user
    Then HS I verify the items are navigate to the respective page in the user dropdown for a Naviance user
    Then HS I verify the user is "<user>" or not
    And HS I successfully sign out
  Examples:
    |navianceUserType|user      |
    |navianceAdmin   |ADMIN     |
    |navianceMember  |NON-ADMIN |

  @MATCH-3060 @MATCH-3061
  Scenario Outline: As a HS Non-Naviance RepVisits user,I want one page to go to to manage all my settings
                    To simplify my RepVisits configuration experience.

    Given HS I want to login to the HS app using "<usertype>" as username and "<password>" as password
    And HS I verify the items in the user dropdown for a Non-Naviance user
    Then HS I verify the items are navigate to the respective page in the user dropdown for a Non-Naviance user
    Then HS I verify the user is "<user>" or not
    And HS I verify the items are present in the help center dropdown for a Non-Naviance user
    And HS I successfully sign out
  Examples:
    |usertype                                  |password  |user      |
    |purpleheautomation+administrator@gmail.com|Password!1|ADMIN     |
    |purpleheautomation+member@gmail.com       |Password#1|NON-ADMIN |

  @MATCH-3715
   Scenario: As a HS admin user of RepVisits that is syncing appointments (visits and college fairs) with Naviance,
                     I want the header and lead in copy on the Naviance Settings wizard page (pre-sync)
                     and on the RepVisits>Availability & Settings>Naviance Settings page "Naviance College Visits Default Settings" section to clearly indicate
                     how those values will be applied to visits when syncing and thereafter should I change them
                     So that I know exactly what to expect as to how those are applied to my visits.
    Given HS I am logged in to Intersect HS through Naviance with account "navianceAdmin"
    Then HS I complete the set up wizard that not yet completed by selecting "All RepVisits Users" option on the 'One Last Step' page
    Then HS I go to welcome wizard of the repvisits
    And HS I navigate to "Naviance Settings" wizard in repvisits page
    Then HS I verify the following details are displaying in Naviance Settings page
      |Naviance Sync Settings|Default Visit Details for College Visits|You can set default values to save time in scheduling future visits, but you can also edit details for individual visits when needed.|The following default visit details will be applied to visits scheduled moving forward.|
    Then HS I complete the set up wizard page by selecting "All RepVisits Users" option on the 'One Last Step' page
    Then HS I navigate to naviance settings page
    Then HS I verify the following details are displaying in Naviance Settings page
      |Naviance Sync Settings|Default Visit Details for College Visits|You can set default values to save time in scheduling future visits, but you can also edit details for individual visits when needed.|The following default visit details will be applied to visits scheduled moving forward.|
    And HS I successfully sign out
