@SP
Feature: SP - Subscriptions - Verify the Subscriptions functionality

  @MATCH-4369
  Scenario: As a Support user, I verify that the Next and Back button are working properly when adding a new subscription
    Given SP I am logged in to the Admin page as a Support user
    When SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    Then SP I set the "Advanced Awareness" module to "active" with the start date "0" and end date "35" in the institution page
    And SP I Click the Save Changes button
    And SP I open the module link of name "Advanced Awareness"
    And SM I press button "ADD NEW SUBSCRIPTION"
    And SP I select the radio button "State" in Add new Subscription modal
    And SP I click the Next button
    Then SP I verify the functionality of the Back button

  @MATCH-4369 @MATCH-4368
  Scenario Outline: As a Support user, I need the ability to add an Advanced Awareness or Connection subscription so
                    that the client's services can be provisioned.
    Given SP I am logged in to the Admin page as a Support user
    When SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    Then SP I set the "Advanced Awareness" module to "active" with the start date "0" and end date "35" in the institution page
    And SP I Click the Save Changes button
    And HE I click the link "Advanced Awareness"
    And SM I press button "ADD NEW SUBSCRIPTION"
    And SP I select the radio button "<Subscription type>" in Add new Subscription modal
    And SP I click the Next button
    And SP I fill the new subscription with the following data:
    | State                | <State>            |
    | Counties             | <Counties>         |
    | Diversity Filter     | <Diversity Filter> |
    | Competitors          | <Competitors>      |
    | Majors               | <Majors>           |
    | Connection           | <Connection>       |
    | Start date           | <Start date>       |
    | End date             | <End date>         |
    | Zips                 | <Zips>             |
    | Radius from zips     | <Radius from zips> |
    And SP I save the new subscription
    Then SP I verify that a new subscription was added to the subscription table with the following data:
    | Location  | <State>              |
    | Diversity | <Diversity Filter>   |
    | Start Date | <Start date>        |
    And SP I delete the subscriptions with the following data:
      | Diversity | <Diversity Filter>   |
      | Start Date | <Start date>        |

  Examples:
      | Subscription type | State   | Counties                       | Diversity Filter         | Competitors                   | Majors | Connection | Start date      | End date        | Zips  | Radius from zips |
      | State             | Alabama | None                           | Female                   | Auburn University Main Campus | yes    | no         | 2 days from now | 3 days from now | None  | None             |
      | County            | Alaska  | Aleutians East Borough County  | Male                     | Auburn University Main Campus | no     | yes        | 2 days from now | 3 days from now | None  | None             |
      | Zip               | Arizona | None                           | Racial & Ethnic Minority | Auburn University Main Campus | yes    | no         | 2 days from now | 3 days from now | 76001 | 15               |

  @MATCH-5073
  Scenario: As a Support person provisioning an AM NxtGen zip code subscription, I want to have an open text box for the
  zip code radius that has a 100 mile limit, so that clients can be more specific with their radiuses.
    Given SP I am logged in to the Admin page as a Support user
    When SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    Then SP I set the "Advanced Awareness" module to "active" with the start date "0" and end date "35" in the institution page
    And SP I Click the Save Changes button
    And SP I open the module link of name "Advanced Awareness"
    And SM I press button "ADD NEW SUBSCRIPTION"
    And SP I select the radio button "Zip" in Add new Subscription modal
    And SP I click the Next button
    And SP I fill the new subscription with the following data:
      | Radius from zips | 100 |
    Then SP I verify that the value in the Radius From Zips field is "100"
    And SP I fill the new subscription with the following data:
      | Radius from zips | 101 |
    Then SP I verify that the value in the Radius From Zips field is "10"

  @MATCH-5249
  Scenario: As a Support person I need to be able to generate a bulk subscription transaction that results in multiple subscriptions being displayed on UI
  Given SP I am logged in to the Admin page as a Support user
    When SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SM I press button "ADD NEW SUBSCRIPTION"
    And SP I select the radio button "State" in Add new Subscription modal
    And SP I click the Next button
    And SP I fill the new subscription with the following data:
      | State                | Virginia |
      | State                | Ohio |
      | Diversity Filter     | Female |
      | Competitors          | Auburn University Main Campus (2400026)      |
      | Majors               | yes           |
      | Connection           | yes |
      | Start date           | 2 days from now      |
      | End date             | 3 days from now      |
    And SP I save the new subscription
    Then SP I verify that a new subscription was added to the subscription table with the following data:
      | Location | Ohio |
      | Location | Virginia |
      | Diversity | Female |
      | Start Date | 2 days from now |
    And SP I delete all the subscriptions for school








  @MATCH-4374
  Scenario: As a Support person provisioning AM NextGen, I want to Edit/Delete State subscriptions
    Given SP I am logged in to the Admin page as a Support user
    When SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SM I press button "ADD NEW SUBSCRIPTION"
    And SP I select the radio button "State" in Add new Subscription modal
    And SP I click the Next button
    And SP I fill the new subscription with the following data:
      | State                | Virginia |
      | Diversity Filter     | Female |
      | Competitors          | Auburn University Main Campus (2400026)      |
      | Majors               | yes           |
      | Connection           | yes |
      | Start date           | 2 days from now      |
      | End date             | 3 days from now      |
    And SP I save the new subscription
    And SP I press on the name of the Subscription "Virginia"
    And SP I fill the new subscription with the following data:
      | State                | Indiana |
      | Diversity Filter     | Female |
      | Competitors          | Auburn University Main Campus (2400026)      |
      | Majors               | yes           |
      | Connection           | yes |
      | Start date           | 2 days from now      |
      | End date             | 3 days from now      |
    And SP I save the new subscription
    And SP I delete the subscriptions with the following data:
      | Diversity | Female |
      | Start Date | 2 days from now |

    @MATCH-4374
    Scenario: As a Support person provisioning AM NextGen, I want to Edit/Delete County subscriptions
      Given SP I am logged in to the Admin page as a Support user
      When SP I select "Bowling Green State University-Main Campus" from the institution dashboard
      And HE I click the link "Advanced Awareness"
      And SM I press button "ADD NEW SUBSCRIPTION"
      And SP I select the radio button "County" in Add new Subscription modal
      And SP I click the Next button
      And SP I fill the new subscription with the following data:
        | State                | Virginia |
        | Counties             | Accomack County |
        | Diversity Filter     | Female |
        | Competitors          | Auburn University Main Campus (2400026)      |
        | Majors               | yes           |
        | Connection           | yes |
        | Start date           | 2 days from now      |
        | End date             | 3 days from now      |
      And SP I save the new subscription
      And SP I press on the name of the Subscription "Accomack County, Virginia"
      And SP I fill the new subscription with the following data:
        | Counties             | Albemarle County |
      And SP I save the new subscription
      And SP I delete the subscriptions with the following data:
        | Diversity | Female |
        | Start Date | 2 days from now |

  @MATCH-4374
  Scenario: As a Support person provisioning AM NextGen, I want to Edit/Delete Zip subscriptions
    Given SP I am logged in to the Admin page as a Support user
    When SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    And HE I click the link "Advanced Awareness"
    And SM I press button "ADD NEW SUBSCRIPTION"
    And SP I select the radio button "Zip" in Add new Subscription modal
    And SP I click the Next button
    And SP I fill the new subscription with the following data:
      | Diversity Filter     | Female |
      | Competitors          | Auburn University Main Campus (2400026)      |
      | Majors               | yes           |
      | Connection           | yes |
      | Start date           | 2 days from now      |
      | End date             | 3 days from now      |
      | Zips                 | 45040 |
      | Radius from zips     | 15 |
    And SP I save the new subscription
    And SP I press on the name of the Subscription "45040"
    And SP I fill the new subscription with the following data:
      | Zips                 | 45241 |
      | Radius from zips     | 25 |
    And SP I save the new subscription
    And SP I delete the subscriptions with the following data:
      | Diversity | Female |
      | Start Date | 2 days from now |
