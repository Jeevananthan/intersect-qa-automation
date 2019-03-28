@SM
Feature: SM - Cost - As a HS student, I need to be able to search for colleges based on the 'Admission' fit criteria

  @MATCH-3391
  Scenario: As a HS student, I want to filter colleges I am searching for by 'Meets 100% of Need' within the Cost
  category so I can see relevant colleges that match my Meets 100% of Need requirement.
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I select the "Meets 100% of Need" checkbox from the Cost fit criteria
    Then SM I verify "Meets 100% of Need" checkbox in Cost fit criteria
    And SM I verify that the Must Have box contains "Meets 100% of Need"
    Then SM I unselect the "Meets 100% of Need" checkbox from the "Cost" fit criteria
    And SM I verify that the Must Have box does not contain "Meets 100% of Need"
    Then SM I select the "Meets 100% of Need" checkbox from the Cost fit criteria
    And SM I move "Meets 100% of Need" from the Must Have box to the Nice to Have box
    Then SM I unselect the "Meets 100% of Need" checkbox from the "Cost" fit criteria
    And SM I verify that the Must Have box does not contain "Meets 100% of Need"
    And SM I verify that Nice to Have box does not contain "Meets 100% of Need"
    Then SM I select the "Meets 100% of Need" checkbox from the Cost fit criteria
    And SM I verify that the Must Have box contains "Meets 100% of Need"

  @MATCH-4194
  Scenario: We need to incorporate the word 'annual' or the words 'per year' into the dropdown UI
    Given SM I am logged in to SuperMatch through Family Connection
    When SM I open the "Cost" tab
    Then SM I verify that the appropriate wording is used for dropdowns of the following options:
    | Maximum Tuition and Fees                         | Select Max | per year |
    | Maximum Total Cost (Tuition, Fees, Room & Board) | Select Max | per year |

  @MATCH-4247 @concurrency
  Scenario: 00 As a HS student viewing the Why drawer of a particular college in my search results, I want to see
  the actual cost data for the college so I can clearly see what matched/did not match/partially matched
  Maximum Tuition & Fees
    Given SM I am logged in to SuperMatch through Family Connection as user type "4247-00"
    And SM I skip the onboarding modals
    And SM I clear all pills from Must have  and Nice to have boxes
    And SM I clear pinned schools list
    And SM I click "Admission" filter criteria tab
    And SM I clean GPA/SAT/ACT scores
    And SM I select the "Small City" checkbox from "Location" fit criteria
    And SM I select the "Historically Black Institutions" checkbox from "Diversity" fit criteria
    And SM I pin "Bennett College" if it is not pinned already
    And SM I clear all pills from Must have  and Nice to have boxes
    And SM I select the "Historically Black Institutions" checkbox from "Diversity" fit criteria
    Then SM I click "Cost" filter criteria tab
    And SM I pick "$5,000" from the dropdown "cost-maximum"
    And HS I Click on close button
    Then SM I press Why button for "Bennett College" college
    Then I check if I can see "Cost < $5000" on the page
    Then I check if I can see "$15,964 out-of-state tuition & fees for one year" on the page
    Then SM I press Why button for "Southern University and A & M College" college
    Then I check if I can see "Cost < $5000" on the page
    Then I check if I can see "$4,973 out-of-state tuition & fees for one year" on the page
    Then SM I press Why button for "Grambling State University" college
    Then I check if I can see "Cost < $5000" on the page
    Then I check if I can see "$5,140 out-of-state tuition & fees for one year" on the page

  @MATCH-4247 @concurrency
  Scenario: 01 As a HS student viewing the Why drawer of a particular college in my search results, I want to see
  the actual cost data for the college so I can clearly see what matched/did not match/partially matched
  Maximum Tuition & Fees for Home state
    Given SM I am logged in to SuperMatch through Family Connection as user type "4247-01"
    And SM I skip the onboarding modals
    And SM I clear all pills from Must have  and Nice to have boxes
    And SM I clear pinned schools list
    And SM I click "Admission" filter criteria tab
    And SM I clean GPA/SAT/ACT scores
    And SM I select the "Small City" checkbox from "Location" fit criteria
    And SM I select the "Historically Black Institutions" checkbox from "Diversity" fit criteria
    And SM I pin "Bennett College" if it is not pinned already
    And SM I clear all pills from Must have  and Nice to have boxes
    And SM I select the "Historically Black Institutions" checkbox from "Diversity" fit criteria
    Then SM I click "Cost" filter criteria tab
    And SM I pick "$5,000" from the dropdown "cost-maximum"
    And SM I pick "North Carolina" from the dropdown ".ui.fluid.search.selection.scrolling.dropdown"
    And HS I Click on close button
    Then SM I press Why button for "Bennett College" college
    Then I check if I can see "Cost < $5000" on the page
    Then I check if I can see "$15,964 in-state (NC) tuition & fees for one year" on the page
    Then SM I press Why button for "Southern University and A & M College" college
    Then I check if I can see "Cost < $5000" on the page
    Then I check if I can see "$4,973 out-of-state tuition & fees for one year" on the page
    Then SM I press Why button for "Grambling State University" college
    Then I check if I can see "Cost < $5000" on the page
    Then I check if I can see "$5,140 out-of-state tuition & fees for one year" on the page

  @MATCH-4247 @concurrency
  Scenario: 02 As a HS student viewing the Why drawer of a particular college in my search results, I want to see
  the actual cost data for the college so I can clearly see what matched/did not match/partially matched
  Maximum Total Cost
    Given SM I am logged in to SuperMatch through Family Connection as user type "4247-02"
    And SM I skip the onboarding modals
    And SM I clear all pills from Must have  and Nice to have boxes
    And SM I clear pinned schools list
    And SM I click "Admission" filter criteria tab
    And SM I clean GPA/SAT/ACT scores
    And SM I select the "Small City" checkbox from "Location" fit criteria
    And SM I select the "Historically Black Institutions" checkbox from "Diversity" fit criteria
    And SM I pin "Bennett College" if it is not pinned already
    And SM I clear all pills from Must have  and Nice to have boxes
    And SM I select the "Historically Black Institutions" checkbox from "Diversity" fit criteria
    And SM I select the "Maximum Total Cost (Tuition, Fees, Room & Board)" checkbox from the "Cost" fit criteria
    Then SM I click "Cost" filter criteria tab
    And SM I pick "$5,000" from the dropdown "cost-maximum"
    And HS I Click on close button
    Then SM I press Why button for "Bennett College" college
    Then I check if I can see "Cost < $5000" on the page
    Then I check if I can see "$24,078 maximum total cost for one year" on the page
    Then SM I press Why button for "Coahoma Community College" college
    Then I check if I can see "Cost < $5000" on the page
    Then I check if I can see "$7,070 out-of-state maximum total cost for one year" on the page

  @MATCH-4247 @concurrency
  Scenario: 03 As a HS student viewing the Why drawer of a particular college in my search results, I want to see
  the actual cost data for the college so I can clearly see what matched/did not match/partially matched
  Maximum Total Cost for home state
    Given SM I am logged in to SuperMatch through Family Connection as user type "4247-03"
    And SM I skip the onboarding modals
    And SM I clear all pills from Must have  and Nice to have boxes
    And SM I clear pinned schools list
    And SM I click "Admission" filter criteria tab
    And SM I clean GPA/SAT/ACT scores
    And SM I select the "Small City" checkbox from "Location" fit criteria
    And SM I select the "Historically Black Institutions" checkbox from "Diversity" fit criteria
    And SM I pin "Bennett College" if it is not pinned already
    And SM I clear all pills from Must have  and Nice to have boxes
    And SM I select the "Historically Black Institutions" checkbox from "Diversity" fit criteria
    And SM I select the "Maximum Total Cost (Tuition, Fees, Room & Board)" checkbox from the "Cost" fit criteria
    Then SM I click "Cost" filter criteria tab
    And SM I pick "$5,000" from the dropdown "cost-maximum"
    And SM I pick "North Carolina" from the dropdown ".ui.fluid.search.selection.scrolling.dropdown"
    And HS I Click on close button
    Then SM I press Why button for "Coahoma Community College" college
    Then I check if I can see "Cost < $5000" on the page
    Then I check if I can see "$7,070 out-of-state maximum total cost for one year" on the page
    And SM I click "Location" filter criteria tab
    And SM I pick "North Carolina" from the dropdown ".ui.fluid.multiple.search.selection.dropdown"
    And HS I Click on close button
    Then SM I press Why button for "Bennett College" college
    Then I check if I can see "Cost < $5000" on the page
    Then I check if I can see "$24,078 maximum total cost for one year" on the page
    Then SM I press Why button for "Elizabeth City State University" college
    Then I check if I can see "Cost < $5000" on the page
    Then I check if I can see "$10,482 in-state (NC) maximum total cost for one year" on the page