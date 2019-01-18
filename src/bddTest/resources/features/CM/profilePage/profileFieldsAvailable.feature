@CM @MATCH-873
Feature: HE Community User - Profile Fields Available
  As a HE Community user I want to build out my profile with additional information so other Community users
  can learn more about me and network with me.

  @MATCH-874
  Scenario Outline: As a HE Community user I want to build out my user profile with additional information.
    Given HE I am logged in to Intersect HE as user type "administrator"
    When I go to user profile page
    And I click on Edit profile button
    Then I fill in "<Personal Email>", "<Office Phone>", "<Mobile Phone>", "<Title>", "<Population(s) Served/Territory>", "<Bio>", "<Headline>" and "<Alma Meter>" fields
    And I Save changes
    And I check if user info is saved with "<Personal Email>", "<Office Phone>", "<Mobile Phone>", "<Title>", "<Population(s) Served/Territory>", "<Bio>", "<Headline>" and "<Alma Meter>"
    Examples:
      | Personal Email                     | Office Phone |Mobile Phone|Title    |Population(s) Served/Territory|Bio                                                                                                                            |Headline  |Alma Meter|
      | johntesting_hobsons_pers@gmail.com | 51312341234  |5137772345  |Recruiter|California                    |I am a graduate of Miami University. I currently work in the admissions office and love my job. More great stuff about me. Blah|Deep stuff|UNC CH    |


  @MATCH-875
  Scenario: As a HE Community user the job title and office phone fields are required for my Community user profile.
    Given HE I am logged in to Intersect HE as user type "administrator"
    When I go to user profile page
    And I click on Edit profile button
    Then I check if office phone and job title are required fields