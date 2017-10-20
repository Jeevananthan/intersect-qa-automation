@HE
Feature: As a potential freemium HE admissions user I need to be able to request to be the individual institution account
  owner so I can manage my institution's HUB profile within Purple.

  @MATCH-202
  Scenario: As a new freemium user, I want to utilize the Request Primary User page to request a new account
            As a tester, I want to verify the fields and messaging on the Request Primary User Page
    Given HE I want to create a New User via the Request Primary User page
    Then HE I use the New User Search to find "Florida Southern College" and request a new freemium user
    Then HE I verify the fields and error messaging on the Request Primary User page using the following info
      |First Name   |NewUserTest            |
      |Last Name    |LNameTest              |
      |Email        |n.l@notarealdomain.com |
      |Verify Email |                       |
    Then HE I submit the Request Primary User form using the following info
      |First Name   |NewUserTest                     |
      |Last Name    |LNameTest                       |
      |Email        |Success@simulator.amazonses.com |
      |Verify Email |Success@simulator.amazonses.com |
      |Job Title    |The boss                        |


  @MATCH-203 @MATCH-1514 @MATCH-1856
  Scenario: As a new freemium user, I want to utilize the Update Primary User page to request a new primary user account for an existing freemium institution
            As a tester, I want to verify the fields and messaging on the Request Primary User Page
    Given HE I want to create a New User via the Request Primary User page
    Then HE I use the New User Search to find "Bowling Green State University-Main Campus" and request an update to the primary freemium user
    Then HE I verify the fields and error messaging on the Request Primary User page using the following info
      |First Name   |NewUserTest              |
      |Last Name    |LNameTest                |
      |Email        |n.l@notarealdomain.com   |
      |Verify Email |                         |
    Then HE I submit the Request Primary User form using the following info
      |First Name   |NewUserTest                     |
      |Last Name    |LNameTest                       |
      |Email        |Success@simulator.amazonses.com |
      |Verify Email |Success@simulator.amazonses.com |
      |Job Title    |The boss                        |


  @MATCH-204
  Scenario: As a new freemium user, I want to utilize the Request New User page to request a new primary user account for an institution that is not found
  As a tester, I want to verify the fields and messaging on the Request Primary User Page
    Given HE I want to create a New User via the Request Primary User page
    Then HE I use the New User Search to find "Not A Real Institution" and request a new institution
    Then HE I verify the fields and error messaging on the Request Primary User page using the following info
      |First Name           |NewUserTest                |
      |Last Name            |LNameTest                  |
      |Email                |brian.bartizek@hobsons.com |
      |Verify Email         |                           |
      |Institution Name     |Institution                |
      |Website              |http://www.google.com      |
    Then HE I submit the Request Primary User form using the following info
      |First Name           |NewUserTest                     |
      |Last Name            |LNameTest                       |
      |Email                |Success@simulator.amazonses.com |
      |Verify Email         |Success@simulator.amazonses.com |
      |Institution Name     |This is a new Institution       |
      |Website              |https://www.tiani.edu           |

