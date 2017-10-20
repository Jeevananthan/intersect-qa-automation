@HE
Feature: As a potential HE User, I want to be able to request a new user account for a new/existing institution

  @MATCH-202
  Scenario: As a new freemium user, I want to utilize the Request User page to request a new user account when a Primary User already exists for my institution
            As a tester, I want to verify the fields and messaging on the Request User Page
    Given HE I want to request for a New User account via the Request User Account page
    Then HE I use the New User Search to find "Florida Southern College" and request a new freemium user
    Then HE I verify the fields and error messaging on the Request User page using the following info
      |First Name   |NewUserTest            |
      |Last Name    |LNameTest              |
      |Email        |n.l@notarealdomain.com |
      |Verify Email |                       |
    Then HE I submit the Request User form using the following info
      |First Name   |NewUserTest                     |
      |Last Name    |LNameTest                       |
      |Email        |Success@simulator.amazonses.com |
      |Verify Email |Success@simulator.amazonses.com |
      |Job Title    |The boss                        |


  @MATCH-203 @MATCH-1514 @MATCH-1856 @MATCH-1857
  Scenario: As a new freemium user, I want to utilize the Request User page to request a new user account when a Primary User does not exist for my institution
            As a tester, I want to verify the fields and messaging on the Request User Page
    Given HE I want to request for a New User account via the Request User Account page
    Then HE I use the New User Search to find "Bowling Green State University-Main Campus" and request a new freemium user
    Then HE I submit the Request User form using the following info
      |First Name                                                          |NewUserTest                     |
      |Last Name                                                           |LNameTest                       |
      |Email                                                               |Success@simulator.amazonses.com |
      |Verify Email                                                        |Success@simulator.amazonses.com |
      |Job Title                                                           |The boss                        |
      |Are you authorized to post public information about your institution?| No |
      |Do you schedule visits to high schools?                              | Yes |


  @MATCH-204
  Scenario: As a new freemium user, I want to utilize the Request New User page to request a new user user account for an institution that is not found
  As a tester, I want to verify the fields and messaging on the Request Institution Page
    Given HE I want to request for a New User account via the Request User Account page
    Then HE I use the New User Search to find "Not A Real Institution" and request a new institution
    Then HE I verify the fields and error messaging on the Request User page using the following info
      |First Name           |NewUserTest                |
      |Last Name            |LNameTest                  |
      |Email                |brian.bartizek@hobsons.com |
      |Verify Email         |                           |
      |Institution Name     |                           |
      |Job Title            |The boss                   |
      |Website              |http://www.google.com      |
    Then HE I submit the Request User form using the following info
      |First Name           |NewUserTest                     |
      |Last Name            |LNameTest                       |
      |Email                |Success@simulator.amazonses.com |
      |Verify Email         |Success@simulator.amazonses.com |
      |Institution Name     |This is a new Institution       |
      |Website              |https://www.tiani.edu           |
      |Job Title            |The boss                        |
      |Are you authorized to post public information about your institution?| Yes |
      |Do you schedule visits to high schools?                              | No |
