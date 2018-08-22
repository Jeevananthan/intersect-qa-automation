@SM
Feature: SM - SuperMatchSearch - As a HS student accessing SuperMatch through Family Connection I need to be able to search college based on
  certain fit criteria

  @MATCH-3592
  Scenario: As a HS student accessing SuperMatch through Family Connection I need to be presented with an Student Body
            Size fit criteria
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I verify the Student Body UI in Resources Dropdown

  @MATCH-3246 @MATCH-3471
  Scenario: As a HS student accessing College Search through Family Connection I need to be presented with an
            empty state page no filters selected yet so I can perform a search when ready.
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I verify if dark blue header is present
    Then SM I verify if Your Fit Criteria text is present
    Then SM I verify the Choose Fit Criteria bar
    Then SM I verify Select Criteria to Start button and instructional text
    Then SM I verify Must Have and Nice to Have boxes
    Then SM I verify the empty results table
    Then SM I verify the dark blue footer

  @MATCH-3208
  Scenario: As a HS student accessing SuperMatch through Family Connection I need to be able to
            add or remove filter criteria from the Must Have box
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I select the "Counseling Services" checkbox from the Resources fit criteria
    Then SM I remove the "Counseling Services" fit criteria from the Must Have box or Nice to Have box
    And SM I verify that the Must Have box does not contain "Counseling Services"
    And SM I verify that the "Counseling Services" checkbox from the Resources fit criteria is "unchecked"

  @MATCH-3911
  Scenario: As a HS student who is interacting with the 'Academics' fit category dropdown, selected Bachelor's for the
            degree fit criteria, and has searched and selected at least one Major and one Minor, I want SuperMatch to
            treat those two entities as separate fit criteria so I can moved Majors and Minors around independently
            between the Must Have and Nice to Have boxes.
    Given SM I am logged in to SuperMatch through Family Connection
    And I clear the onboarding popups if present
    Then SM I select the "Bachelor's" radio button from the Academics fit criteria
    Then SM I select the following majors in the SEARCH MAJORS multi-select combobox for Bachelor's degree type
    |Accounting|
    And SM I verify that the Must Have box contains "Major [1]"
    Then SM I select the following minors in the SEARCH MINORS multi-select combobox for Bachelor's degree type
    |Drawing|
    And SM I verify that the Must Have box contains "Minor [1]"
    Then SM I move "Minor [1]" from the Must Have box to the Nice to Have box
    And SM I verify that the Must Have box contains "Major [1]"
    And SM I verify that the Nice to Have box contains "Minor [1]"
    Then SM I move "Major [1]" from the Must Have box to the Nice to Have box
    And SM I verify that the Nice to Have box contains "Major [1]"
    And SM I verify that the Nice to Have box contains "Minor [1]"
    Then SM I unselect the following majors in the SEARCH MAJORS multi-select combobox for Bachelor's degree type
    |Accounting|
    Then SM I unselect the following minors in the SEARCH MINORS multi-select combobox for Bachelor's degree type
    |Drawing|
    And SM I verify that the Must Have box does not contain "Major [1]"
    And SM I verify that the Must Have box does not contain "Minor [1]"
    And SM I verify that Nice to Have box does not contain "Major [1]"
    And SM I verify that Nice to Have box does not contain "Minor [1]"
    Then SM I select the following majors in the SEARCH MAJORS multi-select combobox for Bachelor's degree type
    |Accounting|
    Then SM I select the following minors in the SEARCH MINORS multi-select combobox for Bachelor's degree type
    |Drawing|
    And SM I verify that the Must Have box contains "Major [1]"
    And SM I verify that the Must Have box contains "Minor [1]"
    And SM I verify that Nice to Have box does not contain "Major [1]"
    And SM I verify that Nice to Have box does not contain "Minor [1]"
    Then SM I select the "Associate's" radio button from the Academics fit criteria
    And SM I verify that the Must Have box does not contain "Major [1]"
    And SM I verify that the Must Have box does not contain "Minor [1]"

  @MATCH-3667
  Scenario: Verify that the box containing instructional text has a width of 25% and the Must Have
  and Nice to Have boxes split the rest
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I verify the widths of the three boxes

  @MATCH-3339
  Scenario: As a HS student looking to search for colleges, I want each fit category that I see in the 'Choose Fit Criteria'
  header bar to be clickable so I can select specific fit criteria.
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I verify each fit category in the Choose Fit Criteria header bar is clickable and match the color
    Then SM I verify clicking outside of the box will also close the box
    And SM I check both Select Criteria To Start buttons take the user to the Location dropdown

  @MATCH-3432 @MATCH-4317
  Scenario: As a HS student reviewing results in SuperMatch, I want to be able to select what details I see on each
  college in my search results so the information I care most about is visible to review.
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I select the "Counseling Services" checkbox from the Resources fit criteria
    Then SM I verify the default column headers displayed in the results table
      | Highlights        |
      | Cost              |
      | Pick what to show |
    Then SM I verify if the option selected or defaulted in column header can be changed to "Athletics"

  @MATCH-3506
  Scenario: As a HS student, I want to be able to close the Save Search popup
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I verify that the Save Search button is disabled
    And I clear the onboarding popups if present
    When I select the following data from the Admission Fit Criteria
      | GPA (4.0 scale) | 4 |
      | SAT Composite   | 400 |
      | ACT Composite   | 3   |
      | Acceptance Rate | 25% or Lower |
    And SM I open the Save Search popup
    Then SM I verify that the Save Search popup is closed when I use the Cancel action

  @MATCH-3506
  Scenario: As a HS student, I want to verify that all the text displayed in the Save Search popup is correct
    Given SM I am logged in to SuperMatch through Family Connection
    And I clear the onboarding popups if present
    When I select the following data from the Admission Fit Criteria
      | GPA (4.0 scale) | 4 |
      | SAT Composite   | 400 |
      | ACT Composite   | 3   |
      | Acceptance Rate | 25% or Lower |
    And SM I open the Save Search popup
    Then SM I verify that the text inside the Save Search popup is correct

  @MATCH-3506
  Scenario: As a HS student, I want to verify the error messages in the Save Search popup
    Given SM I am logged in to SuperMatch through Family Connection
    And I clear the onboarding popups if present
    When I select the following data from the Admission Fit Criteria
      | GPA (4.0 scale) | 4 |
      | SAT Composite   | 400 |
      | ACT Composite   | 3   |
      | Acceptance Rate | 25% or Lower |
    And SM I open the Save Search popup
    Then SM I verify the error message for more than "50" characters
    And SM I verify the error message for less than "3" characters

  @MATCH-3506 @MATCH-3508
  Scenario: As a HS student, I want to verify that the save/load search functionality (MATCH-4703)
    Given SM I am logged in to SuperMatch through Family Connection
    And I clear the onboarding popups if present
    And SM I delete all the saved searches
    And SM I start the search over
    When I select the following data from the Admission Fit Criteria
      | GPA (4.0 scale) | 4 |
      | SAT Composite   | 400 |
      | ACT Composite   | 3   |
      | Acceptance Rate | 25% or Lower |
    And SM I reload the page
    And SM I open the Save Search popup
    And SM I save the search with the name "SavedTestSearch"
    Then SM I verify the confirmation message
    Then SM I verify the saved search of name "SavedTestSearch" is displayed in the Saved Searches dropdown
    And SM I select "SavedTestSearch" in the Saved Searches dropdown
    Then SM I verify that "SavedTestSearch" is displayed as selected option in the Saved Searches dropdown
    And SM I delete the save search "SavedTestSearch" and verify it

  @MATCH-3212
  Scenario: As a HS student I want a way to clear all my fit criteria I have currently selected so I can quickly start my search over again.
    Given SM I am logged in to SuperMatch through Family Connection
    And I clear the onboarding popups if present
    And SM I start the search over
    Then SM I verify that the Start Over button is disabled
    When I select the following data from the Admission Fit Criteria
      | GPA (4.0 scale) | 4 |
      | SAT Composite   | 400 |
      | ACT Composite   | 3   |
      | Acceptance Rate | 25% or Lower |
    #The following step is needed to avoid MATCH-4830
    And SM I reload the page
    Then SM I verify the content of the popup that is opened by the Start Over button
    Then SM I verify that the search results remain after clicking the No, Cancel button
    And SM I open the Start Over popup
    Then SM I verify that the fit criteria is removed after clicking the Yes, Start Over button

  @MATCH-4160
  Scenario: Currently, the financial aid results column of the results table does not display a tuition value of $0 in the UI. This needs updated.
    Given SM I am logged in to SuperMatch through Family Connection
    And I clear the onboarding popups if present
    And SM I start the search over
    When I select the following data from the Admission Fit Criteria
      | GPA (4.0 scale) | 3  |
      | SAT Composite   | 1000 |
      | ACT Composite   | 26   |
    And SM I select the "$5,000" option from the "Maximum Tuition and Fees" dropdown in Cost
    #The following step is needed to avoid MATCH-4830
    And SM I reload the page
    Then SM I verify that "Sistema Universitario Ana G Mendez" displays "$0" in the Cost column

  @MATCH-4276
  Scenario: As a HS student, I want to see specific footnotes when SuperMatch does not know my GPA and does not know my test scores
    Given SM I am logged in to SuperMatch through Family Connection
    And I clear the onboarding popups if present
    And SM I start the search over
    When I select the following data from the Admission Fit Criteria
      | Acceptance Rate | 25% or Lower |
    Then SM I verify the footnote for no GPA and no other scores, with the text:
      | To determine if you're an academic match for this institution, enter your GPA and/or standardized test scores. |

  @MATCH-4276
  Scenario: As a HS student, I want to see specific footnotes when SuperMatch does know my GPA, but not my test scores
    Given SM I am logged in to SuperMatch through Family Connection
    And I clear the onboarding popups if present
    And SM I start the search over
    When I select the following data from the Admission Fit Criteria
      | GPA (4.0 scale) | 4 |
      | Acceptance Rate | 25% or Lower |
    Then SM I verify the footnote for known GPA but unknown test scores for "Pomona College", with the text:
    | To best determine if you're an academic match for this institution, enter both your GPA and standardized test scores. |

   @MATCH-4276
   Scenario: As a HS student, I want to see specific footnotes when SuperMatch does know my test scores, but not my GPA
     Given SM I am logged in to SuperMatch through Family Connection
     And I clear the onboarding popups if present
     And SM I start the search over
     When I select the following data from the Admission Fit Criteria
       | SAT Composite   | 1500 |
       | ACT Composite   | 30   |
       | Acceptance Rate | 76% or more |
     And SM I select the "Coed" checkbox from "Diversity" fit criteria
     Then SM I verify the footnote for known GPA but unknown test scores for "Utica College", with the text:
       | To best determine if you're an academic match for this institution, enter both your GPA and standardized test scores. |

    @MATCH-4406
    Scenario: As a HS student, I want to be able to save my searches for colleges in SuperMatch so I can quickly
              return to the results to continue my college research.
      Given SM I am logged in to SuperMatch through Family Connection
      And I clear the onboarding popups if present
      Then SM I create till fifteen different save search from Resources tab
      When I select the following data from the Admission Fit Criteria
        | Acceptance Rate | 25% or Lower |
      And SM I open the Save Search popup
      And SM I save the search with the name "Search16"
      And SM I validate the error message "You have reached your maximum of 15 saved searches. Remove a saved search to add a new one"

  @MATCH-3511
  Scenario: As a HS student, I want to delete my saved searches so that list can contain only the saved
  searches I need presently
    Given SM I am logged in to SuperMatch through Family Connection
    And I clear the onboarding popups if present
    Then SM I create a save search "Search1" by selecting "Learning Differences Support" from Resources tab
    Then SM I check the delete icon in save search "Search1"
    Then SM After clicking "Search1" delete icon I check the confirmation popup message
    And SM I check clicking outside will close the "Search1" popup message
    And SM I delete the save search "Search1" and verify it

  @MATCH-3628
  Scenario: As a HS student reviewing results from my search, I want to have an action available to jump back to the top of the SuperMatch page
    Given SM I am logged in to SuperMatch through Family Connection
    And I clear the onboarding popups if present
    When I select the following data from the Admission Fit Criteria
      | GPA (4.0 scale) | 4 |
      | Acceptance Rate | 25% or Lower |
    Then SM I verify that screen jumps to the top of the page after clicking the Back to top button


  @MATCH-3471
  Scenario: As a HS student I want to search for a specific college by name, so I do not have to pick fit criteria
            in order to see that college as result.
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I should see at the bottom the search by college name text box with default text "Search by College Name"
    When SM I search by college name using "Art"
    Then SM I see a message at the top of the results box that says "Search for Art"
    And SM I verify "10" results were displayed when searching by college name
    When SM I search by college name using "NonExistent"
    Then SM I see a message in the search by college name text box that says "No results found for NonExistent"

  @MATCH-3933
  Scenario: Based on the generic data available for online learning opportunities in CMS per college, we need to add a
            tooltip next to the 'Include online learning opportunities' fit criteria in the Academic fit category
            dropdown so students can understand how we return search results that include this fit criteria.
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I verify that tooltip icon is added to the include online learning opportunities fit criteria

  @MATCH-3767
  Scenario: As a HS student accessing SuperMatch through Family Connection I need to be presented with % MALE VS. FEMALE
  in Diversity dropdown
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I verify the text displayed in the % Male vs. Female Fit Criteria
    Then SM I verify the placeholders displayed in the Select % dropdown and Select gender dropdown
      |Select %     |
      |Select gender|
    Then SM I verify the options displayed in the Select % dropdown
      |Select %|
      |10%     |
      |20%     |
      |30%     |
      |40%     |
      |50%     |
      |60%     |
      |70%     |
    Then SM I verify the options displayed in the Select Gender dropdown
      |Select gender|
      |Male         |
      |Female       |


  @MATCH-4350
  Scenario: As a HS student, if I pin the 26th school from the Why? drawer, an error message should be displayed
    Given SM I am logged in to SuperMatch through Family Connection
    And I clear the onboarding popups if present
    Then SM I clear pinned schools list
    Then SM I select the "Learning Differences Support" checkbox from the Resources fit criteria
    Then SM I pin "26" colleges
    Then SM I verify the error message displayed on pinning the 26th college

  @MATCH-4727
  Scenario: Load saved search which includes the NET family income
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I select the following data in the Cost Fit Criteria
      |Radio           |Maximum Total Cost (Tuition, Fees, Room & Board)|
      |Maximum Cost    |$10,000                                         |
      |Home State      |Ohio                                            |
      |Family Income   |$75,001 - $110,000                              |
    Then SM I open the Save Search popup
    Then SM I save the search with the name "SS123"
    Then SM I remove the "Cost < $10000" fit criteria from the Must Have box or Nice to Have box
    Then SM I select "SS123" in the Saved Searches dropdown
    Then SM I verify the following data in the Cost Fit Criteria
      |Family Income|$75,001 - $110,000|

  @MATCH-3589
  Scenario: Verify the checkboxes in the College Type fit criteria
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I verify that "Public" checkbox is "selected" in "Institution Characteristics" fit criteria
    Then SM I verify that "Private" checkbox is "selected" in "Institution Characteristics" fit criteria
    Then SM I verify that "Show only non-profit" checkbox is "selected" in "Institution Characteristics" fit criteria
    Then SM I unselect the "Public" checkbox from the "Institution Characteristics" fit criteria
    Then SM I verify that "Public" checkbox is "unselected" in "Institution Characteristics" fit criteria
    Then SM I select the "Public" checkbox from "Institution Characteristics" fit criteria
    Then SM I verify that "Public" checkbox is "selected" in "Institution Characteristics" fit criteria
    Then SM I unselect the "Private" checkbox from the "Institution Characteristics" fit criteria
    Then SM I verify that "Private" checkbox is "unselected" in "Institution Characteristics" fit criteria
    Then SM I verify that "Show only non-profit" checkbox is "unselected" in "Institution Characteristics" fit criteria
    Then SM I select the "Private" checkbox from "Institution Characteristics" fit criteria
    Then SM I verify that "Private" checkbox is "selected" in "Institution Characteristics" fit criteria
    Then SM I verify that "Show only non-profit" checkbox is "selected" in "Institution Characteristics" fit criteria
    Then SM I unselect the "Show only non-profit" checkbox from the "Institution Characteristics" fit criteria
    Then SM I verify that "Private" checkbox is "selected" in "Institution Characteristics" fit criteria
    Then SM I verify that "Show only non-profit" checkbox is "unselected" in "Institution Characteristics" fit criteria
    Then SM I select the "Show only non-profit" checkbox from "Institution Characteristics" fit criteria
    Then SM I verify that "Show only non-profit" checkbox is "selected" in "Institution Characteristics" fit criteria

  @MATCH-4682
  Scenario: The Compare seems to load and focus itself in the same general area you were on the main page of SuperMatch.
  Verify that this doesn't happen.
    Given SM I am logged in to SuperMatch through Family Connection
    And I clear the onboarding popups if present
    Then SM I select the "Learning Differences Support" checkbox from the Resources fit criteria
    Then SM I pin "2" colleges
    Then SM I scroll to the middle of the main page
    And SM I open the Pinned Schools Compare screen
    Then SM I verify scrollbar is positioned at the top of the Pinned Schools Compare page

  @MATCH-3522
  Scenario: As a HS student using SuperMatch I want to clear all of my currently pinned schools so I can quickly wipe
  that list and start over.
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I select the "Learning Differences Support" checkbox from the Resources fit criteria
    Then SM I pin "1" colleges
    Then SM I verify that CLEAR PINNED LIST option is clickable
    Then SM I verify the CLEAR PINNED LIST confirmation modal
    Then SM I verify that the pinned colleges are not cleared when the NO CANCEL button is clicked in the modal
    Then SM I verify that the pinned colleges are cleared when the the YES, CLEAR MY LIST button is clicked in the modal
    Then SM I verify that the CLEAR PINNED LIST option is disabled

  @MATCH-4133
  Scenario: Verify the validation message displayed in the Zip Code field
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I verify if the validation message displayed for Zip Code field is user friendly
    |Location Search Type|Select Miles    |Zip Code|Verify Error Message Displayed |Verify Pill Displayed In Must Have Box|Verify Pill Not Displayed In Must Have Box|
    |Search by distance  |Within 500 miles|        |                               |                                      |Within 500 miles                          |
    |Search by distance  |Select Miles    |90001   |                               |                                      |90001                                     |
    |Search by distance  |Within 25 miles |90001   |                               |Within 25 miles of 90001              |                                          |
    |Search by distance  |Within 25 miles |9000    |Enter a valid, 5 digit zip code|                                      |                                          |
    |Search by distance  |Within 25 miles |90001   |                               |                                      |                                          |

  @MATCH-4654
  Scenario: As a HS student using SuperMatch I want to see colleges interested in me so I can make more connections with colleges I am interested in
    Given SM I am logged in to SuperMatch through Family Connection
    Then I check if I can see "Interested In You" on the page
    Then SM I press button "Interested In You"
    Then I check if I can see "Colleges Looking For Students Like You" on the page

  @MATCH-4654
  Scenario Outline: As a HS student using SuperMatch I cannot see colleges interested in me because there are 0 or I have no permissions
    Given SM I am logged in to SuperMatch through Family Connection as user "<user>" with password "<pass>" from school "<school>"
    Then I verify that there is not text "Interested In You" on the page
    Examples:
      | user         | pass     | school     |
      | talka10grade | password | blue1combo |
      | uat_user6    | password | blue1combo |
