@SP
Feature: SP - Subscriptions - Verify the Subscriptions functionality

  @MATCH-4369
  Scenario: As a Support user, I verify that the Next and Back button are working properly when adding a new subscription
    Given SP I am logged in to the Admin page as a Support user
    When SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    And SP I open the module link of name "Advanced Awareness"
    And SP I click the button "Add New Subscription"
    And SP I select the radio button "State" in Add new Subscription modal
    And SP I click the Next button
    Then SP I verify the functionality of the Back button

  @MATCH-4369 @MATCH-4368
  Scenario Outline: As a Support user, I need the ability to add an Advanced Awareness or Connection subscription so
  that the client's services can be provisioned.
    Given SP I am logged in to the Admin page as a Support user
    When SP I select "Bowling Green State University-Main Campus" from the institution dashboard
    And SP I click in "Advanced Awareness" link
    And SP I click the button "Add New Subscription"
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


