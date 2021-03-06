@HS @HS2
Feature:  HS - Community - EditInstitutionProfile - As an HS user, I should be able to modify my enrollment data via the Community.

  @MATCH-811 @MATCH-814 @MATCH-815 @MATCH-1561
  Scenario: As an authorized HS User, I need to be able to update my contact information in the purple community.
  So I should be able to change my address, phone, fax, and website URL.
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone7"
    Then HS I access the INSTITUTION page
    And HS I access the EDIT PROFILE page by clicking edit button
    Then HS I make sure the CONTACT INFORMATION FIELDS exist
      | Address | City | State | Zip | County | Country | Phone | Fax | Website URL |
    And HS I enter the following CONTACT data on the Institution Profile page and click "Save"
      | Address     | 3480 DIXIE HWY    |
      | City        | Erlanger          |
      | State       | Kentucky          |
      | Zip         | 41018             |
      | County      | Kenton            |
      | Country     | United States     |
      | Phone       | 859.432.3333      |
      | Fax         | 859.432.4899      |
      | Website URL | www.DIXIEHIGH.com |
    Then HS I access the INSTITUTION page
    And HS I access the EDIT PROFILE page by clicking edit button
    And HS I verify updated data entered has successfully been saved
      | Address     | 3480 DIXIE HWY    |
      | City        | Erlanger          |
      | State       | Kentucky          |
      | Zip         | 41018             |
      | County      | Kenton            |
      | Country     | United States     |
      | Phone       | 859.432.3333      |
      | Fax         | 859.432.4899      |
      | Website URL | www.DIXIEHIGH.com |
    Then HS I re-enter original data and click "Save"
      | Address     | 2621 McMenemy St |
      | City        | Little Canada    |
      | State       | Minnesota        |
      | Zip         | 55117            |
      | County      | Ramsey           |
      | Country     | United States    |
      | Phone       | 360.555.1212     |
      | Fax         | 360.123.4567     |
      | Website URL | www.hobsons.com  |



  @MATCH-811 @MATCH-814 @MATCH-815
  Scenario: As an authorized HS User, I need to be able to update my enrollment data in the purple community.
  So I should be able to change my address, phone, fax, and website URL.
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone7"
    Then HS I access the INSTITUTION page
    And HS I access the EDIT PROFILE page by clicking edit button
    Then HS I make sure the ENROLLMENT INFORMATION FIELDS exist
      | Male | Female | Nonbinary | Asian or Asian/Pacific Islander | American Indian/Alaska Native | Hawaiian Native/Pacific Islander | Hispanic or Latino | Black/African American | White | Two or More Races | Prekindergarten Students | Kindergarten Students | Grade 1 | Grade 2 | Grade 3 | Grade 4 | Grade 5 | Grade 6 | Grade 7 | Grade 8 | Grade 9 | Grade 10 | Grade 11 | Grade 12 | Title I Status |
    And HS I enter the following ENROLLMENT data on the Institution Profile page and click "Save"
      | Male                             | 499                                 |
      | Female                           | 600                                 |
      | Nonbinary                        | 600                                 |
      | Asian or Asian/Pacific Islander  | 600                                 |
      | American Indian/Alaska Native    | 600                                 |
      | Hawaiian Native/Pacific Islander | 600                                 |
      | Hispanic or Latino               | 600                                 |
      | Black/African American           | 600                                 |
      | White                            | 600                                 |
      | Two or More Races                | 600                                 |
      | Prekindergarten Students         | 600                                 |
      | Kindergarten Students            | 600                                 |
      | Grade 1                          | 600                                 |
      | Grade 2                          | 600                                 |
      | Grade 3                          | 600                                 |
      | Grade 4                          | 600                                 |
      | Grade 5                          | 600                                 |
      | Grade 6                          | 600                                 |
      | Grade 7                          | 600                                 |
      | Grade 8                          | 600                                 |
      | Grade 9                          | 600                                 |
      | Grade 10                         | 600                                 |
      | Grade 11                         | 600                                 |
      | Grade 12                         | 600                                 |
      | Title I Eligible                 | Yes                                 |
      | Title I Status                   | Not eligible for either TAS or SWP. |
    Then HS I access the INSTITUTION page
    And HS I access the EDIT PROFILE page by clicking edit button
    And HS I verify updated data entered has successfully been saved
      | Male                             | 499                                 |
      | Female                           | 600                                 |
      | Nonbinary                        | 600                                 |
      | Asian or Asian/Pacific Islander  | 600                                 |
      | American Indian/Alaska Native    | 600                                 |
      | Hawaiian Native/Pacific Islander | 600                                 |
      | Hispanic or Latino               | 600                                 |
      | Black/African American           | 600                                 |
      | White                            | 600                                 |
      | Two or More Races                | 600                                 |
      | Prekindergarten Students         | 600                                 |
      | Kindergarten Students            | 600                                 |
      | Grade 1                          | 600                                 |
      | Grade 2                          | 600                                 |
      | Grade 3                          | 600                                 |
      | Grade 4                          | 600                                 |
      | Grade 5                          | 600                                 |
      | Grade 6                          | 600                                 |
      | Grade 7                          | 600                                 |
      | Grade 8                          | 600                                 |
      | Grade 9                          | 600                                 |
      | Grade 10                         | 600                                 |
      | Grade 11                         | 600                                 |
      | Grade 12                         | 600                                 |
      | Title I Eligible                 | Yes                                 |
      | Title I Status                   | Not eligible for either TAS or SWP. |
    Then HS I re-enter original data and click "Save"
      | Male                             | 499     |
      | Female                           | 501     |
      | Nonbinary                        | 60      |
      | Asian or Asian/Pacific Islander  | 110     |
      | American Indian/Alaska Native    | 120     |
      | Hawaiian Native/Pacific Islander | 130     |
      | Hispanic or Latino               | 250     |
      | Black/African American           | 150     |
      | White                            | 160     |
      | Two or More Races                | 140     |
      | Prekindergarten Students         | 499     |
      | Kindergarten Students            | 501     |
      | Grade 1                          | 0       |
      | Grade 2                          | 0       |
      | Grade 3                          | 0       |
      | Grade 4                          | 0       |
      | Grade 5                          | 0       |
      | Grade 6                          | 0       |
      | Grade 7                          | 0       |
      | Grade 8                          | 0       |
      | Grade 9                          | 250     |
      | Grade 10                         | 260     |
      | Grade 11                         | 270     |
      | Grade 12                         | 280     |
      | Title I Eligible                 | Unknown |
      | Title I Status                   | Eligible for Title I SWP provides TAS program. |



  @MATCH-811 @MATCH-814 @MATCH-815
  Scenario: As an authorized HS User, I need to be able to update my academic data fields in the purple community.
  So I should be able to change any academic data attributes.
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone7"
    Then HS I access the INSTITUTION page
    And HS I access the EDIT PROFILE page by clicking edit button
    Then HS I make sure the ACADEMIC DATA FIELDS exist
      |Charter School |Coeducational |College-Going Rate |Student/Teacher ratio |Full Time Teachers |Highest Grade |Lowest Grade |School Level |School Type Options |School Year of Last Reported Data|
    And HS I enter the following ACADEMIC data on the Institution Profile page and click "Save"
      | Charter School                     | No                                          |
      | Coeducational                      | All-male (school only has all-male students) |
      # The College-Going Rate field will not save, manually or by test
      #| College-Going Rate                 | 80                                           |
      | Student/Teacher ratio              | 20                                           |
      | Full Time Teachers                 | 200                                          |
      | Highest Grade                      | Grade 11                                     |
      | Lowest Grade                       | Grade 6                                      |
      | School Level                       | Combined (k-12)                              |
      | School Type Options                | Special education school                     |
      | School Year of Last Reported Data  | 2014-2016                                    |
    Then HS I access the INSTITUTION page
    And HS I access the EDIT PROFILE page by clicking edit button
    And HS I verify updated data entered has successfully been saved
      | Charter School                     | No                                          |
      | Coeducational                      | All-male (school only has all-male students) |
      #| College-Going Rate                 | 80                                           |
      | Student/Teacher ratio              | 20                                           |
      | Full Time Teachers                 | 200                                          |
      | Highest Grade                      | Grade 11                                     |
      | Lowest Grade                       | Grade 6                                      |
      | School Level                       | Combined (k-12)                               |
      | School Type Options                | Special education school                     |
      | School Year of Last Reported Data  | 2014-2016                                    |
    Then HS I re-enter original data and click "Save"
      | Charter School                     | No                                         |
      | Coeducational                      | Coed (school has male and female students) |
      #| College-Going Rate                 | 67                                         |
      | Student/Teacher ratio              | 12                                         |
      | Full Time Teachers                 | 150                                        |
      | Highest Grade                      | Grade 12                                   |
      | Lowest Grade                       | Grade 7                                    |
      | School Level                       | Secondary (7-12)                           |
      | School Type Options                | Regular school                             |
      | School Year of Last Reported Data  | 2015-2017                                  |



  @MATCH-1245
  Scenario: As a HS user with the appropriate permissions enabled in Naviance I need to be able to edit my HS institution's profile data that displays in Community
  So that data is always fresh and current.
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone7"
    Then HS I access the INSTITUTION page
    And HS I access the EDIT PROFILE page by clicking edit button
    Then HS I make sure the ACADEMIC DATA FIELDS exist
      | Charter School | Coeducational |
    And HS I enter the following ACADEMIC data on the Institution Profile page and click "Cancel"
      | Charter School                     | No                                         |
      | Coeducational                      | Coed (school has male and female students) |
    And HS I successfully sign out
    #Checking to make sure non_admin users do not have access to the edit profile button
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone7"
    Then HS I access the INSTITUTION page
    And HS I verify that I do not have access to the institution profile edit button



  @MATCH-1568
  Scenario: As a HS Intersect User on my HS Institution Edit page I want to see the right suffixes on grade levels in drop downs
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone7"
    Then HS I access the INSTITUTION page
    And HS I access the EDIT PROFILE page by clicking edit button
    Then HS I make sure the LOWEST and HIGHEST GRADE LEVEL FIELDS exist
      | Highest Grade | Lowest Grade |
    Then HS I make sure the LOWEST GRADE LEVEL dropdown is complete and sorted correctly and "lowestGrade"
      | Prekindergarten Students | Kindergarten Students | Grade 1 | Grade 2 | Grade 3 | Grade 4 | Grade 5 | Grade 6 | Grade 7 | Grade 8 | Grade 9 | Grade 10 | Grade 11 | Grade 12 | Not specified |
    Then HS I make sure the HIGHEST GRADE LEVEL dropdown is complete and sorted correctly and "highestGrade"
      | Prekindergarten Students | Kindergarten Students | Grade 1 | Grade 2 | Grade 3 | Grade 4 | Grade 5 | Grade 6 | Grade 7 | Grade 8 | Grade 9 | Grade 10 | Grade 11 | Grade 12 | Not specified |


  @MATCH-1563
  Scenario: As a HS User on my HS Institution Edit page I would like to see a header above the Title I data entry boxes
  To reduce the risk of confusion.
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone7"
    Then HS I access the INSTITUTION page
    And HS I access the EDIT PROFILE page by clicking edit button
    Then HS I verify the header exist above Title I data entry boxes "Title I Information"

  @MATCH-1562
  Scenario: As an authenticated HS user on the HS Institution Edit Page I don't want to have a "total" box in demographic data
  So data entry is less confusing.
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone7"
    Then HS I access the INSTITUTION page
    And HS I access the EDIT PROFILE page by clicking edit button
    Then HS I make sure that no total fields exist
      | Total |

  @MATCH-1564
  Scenario: As an HS Intersect user viewing my HS Institution Edit page I want dropdowns that don't have too many options
  So I'm not confused during editing.
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone7"
    Then HS I access the INSTITUTION page
    And HS I access the EDIT PROFILE page by clicking edit button
    Then HS I make sure the Title I Eligibility dropdown only displays appropriate options "titleEligible"
      | Yes | No | Unknown |
    Then HS I make sure the Title I Status dropdown only displays appropriate options "titleStatus"
      | Eligible for Title I TAS provides no program. | Eligible for Title I TAS provides TAS program. | Eligible for Title I SWP provides TAS program. | Eligible for Title I SWP provides no program. | Eligible for Title I SWP provides SWP program. | Not eligible for either TAS or SWP. | Unknown |
    Then HS I make sure the Charter School dropdown only displays appropriate options "charterSchool"
      | Yes | No | Unknown |
    Then HS I make sure the Coeducational dropdown only displays appropriate options "coeducational"
      | Coed (school has male and female students) | All-male (school only has all-male students) | All-female (school only has all-female students) | Unknown |



  @MATCH-1849
  Scenario: As a HS user, I need to be able to request a user account by providing the necessary information about myself.
  So Support can provision my user account.

    Given HS I navigate to Registration Intersect url
    When HS I click on HIGHER EDUCATION STAFF MEMBER
    And HS I search for "Dr. A.P.J. Abdul Kalam Technical University" in the registration page
    And HE I click the link "please complete this form."
    Then HS I verify all field type in request user page
      |firstName |lastName |email |verifyEmail |jobTitle |
      |text      |text     |email |email       |text     |
    And HS I enter the following data in request user page
      |firstName |lastName |email                 |verifyEmail           |jobTitle |
      |mahi      |qateam   |kpmahi93+12@gmail.com |kpmahi93+12@gmail.com |test role|

  @MATCH-1565
  Scenario: As a HS Intersect User on the HS Institution edit page I want to see placeholder text when fields are blank
  So there is less confusion on what to put in the field.
    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone7"
    Then HS I access the INSTITUTION page
    And HS I access the EDIT PROFILE page by clicking edit button
    And HS I want to ensure placeholders exist for the appropriate fields click "Cancel"
      | phone       | Ex: (555) 555-5555     |
      | fax         | Ex: (555) 555-5555     |
      | websiteUrl  | Ex: www.yourschool.edu |
      | collegeRate | Ex: 78%                |
      | ratio       | Ex: 12 (for 12:1)      |

  @MATCH-2052
  Scenario: As a HS user, I need to be able to request a new user account AND a new high school institution during the registration process.
  So I can still complete the registration workflow process when I am unable to successfully search and locate my high school.
    Given HS I navigate to Registration Intersect url
    And HS I search for "Request new institution" in the registration page
    Then HS I verify the Request New User page
      | firstName      | email  | verifyEmail  | institutionName  |  lastName  | jobTitle |


  @MATCH-1781
  Scenario: As a HS user, I need to be able to search for high schools during the registration process.
  so I can associate myself with the high school I work at
    Given HS I am navigating to Intersect HS through Non naviance Url
    Then HS I click the new user link in the login page
    And HS I search for "Int Qa High School 4" in "High school" and verify the results

  @MATCH-5276
  Scenario: As a HS user, I want to see if high schools are displayed in the search results when SEARCH button is pressed
    Given HS I navigate to Registration Intersect url
    And HS I search for "Lakota" in High School Staff Member registration page
    Then HS I verify the search results on the registration page contain "Lakota"

