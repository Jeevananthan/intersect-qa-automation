@SP
Feature: As a Hobsons staff user, I need to be able to manage HE user accounts.

  @MATCH-129
  Scenario: As a Hobsons admin user, I can inactivate and activate HE user accounts.
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I go to the users list for "The University of Alabama" from the institution dashboard
    And SP I "inactivate" the user account for "purpleheautomation+recruiter@gmail.com"
    Then SP I verify that the user account for "purpleheautomation+recruiter@gmail.com" is "inactive"
    And SP I "activate" the user account for "purpleheautomation+recruiter@gmail.com"
    Then SP I verify that the user account for "purpleheautomation+recruiter@gmail.com" is "active"
    Then SP I successfully sign out

  @MATCH-129
  Scenario: As a Hobsons admin user, I can change the primary user of a premium institution.
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I go to the users list for "The University of Alabama" from the institution dashboard
    And SP I set the user "purpleheautomation+coordinator@gmail.com" to be the new primary user
    Then SP I verify that the user account for "purpleheautomation+coordinator@gmail.com" is the primary user
    And SP I set the user "purpleheautomation@gmail.com" to be the new primary user
    Then SP I verify that the user account for "purpleheautomation@gmail.com" is the primary user
    Then SP I verify that the user account for "purpleheautomation+coordinator@gmail.com" is not the primary user
    Then SP I successfully sign out

  @MATCH-1126
  Scenario: As a Hobsons support user, if I inactivate the primary account for a freemium institution,
  I can create a new primary user
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I go to the users list for "Bowling Green State University-Main Campus" from the institution dashboard
    And SP I "inactivate" the user account for "brian.bartizek@hobsons.com"
    Then SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    And SP I verify that I can create a new primary user
    Then SP I go to the users list for "Bowling Green State University-Main Campus" from the institution dashboard
    And SP I "activate" the user account for "brian.bartizek@hobsons.com"
    Then SP I successfully sign out


  @MATCH-1416 @NotInQA
  Scenario: As an Support system I want HE user accounts that have been inactivated to also have their
            corresponding Community user accounts inactivated.
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I go to the users list for "The University of Alabama" from the institution dashboard
    And SP I "inactivate" the user account for "yadav.arun24+qa1416@gmail.com"
    And SP I search for "Arun Match1416" in "People"
    Then SP I verify there are no search results returned
    And SP I "activate" the user account for "yadav.arun24+qa1416@gmail.com"
    Then SP I successfully sign out
