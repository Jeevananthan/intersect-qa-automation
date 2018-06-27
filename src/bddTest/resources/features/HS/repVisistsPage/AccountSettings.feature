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
