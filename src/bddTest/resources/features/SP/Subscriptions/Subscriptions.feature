@SP
Feature: SP - Subscriptions - Verify the Subscriptions functionality

  @MATCH-4369
  Scenario: As a Support user, I verify that the Next and Back button are working properly when adding a new subscription
    Given SP I am logged in to the Admin page as a Support user
    When SP I select "The University of Alabama" from the institution dashboard
    And SP I open the module link of name "Advanced Awareness"
    And SP I add a new subscription of type "State"
    Then SP I verify the functionality of the Back button

  @MATCH-4369 @MATCH-4368
  Scenario Outline: As a Support user, I need the ability to add an Advanced Awareness or Connection subscription so
  that the client's services can be provisioned.
    Given SP I am logged in to the Admin page as a Support user
    When SP I select "The University of Alabama" from the institution dashboard
    And SP I open the module link of name "Advanced Awareness"
    And SP I add a new subscription of type "<Subscription type>"
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
    #Since it is not possible to remove Subscriptions yet, this verification will match many items, so it cannot be used yet.
#    Then SP I verify that a new subscription was added with the following data:
#    | Location  | <State>              |
#    | Diversity | <Diversity Filter>   |
#    | Start Date | <Start date>        |
    # The functionality to delete subscriptions is not implemented yet
#    And SP I delete the subscriptions with the following data:
#      | Location | <State> |
#      | Diversity | <Diversity Filter>   |
    Examples:
      | Subscription type | State   | Counties                       | Diversity Filter         | Competitors                   | Majors | Connection | Start date      | End date        | Zips  | Radius from zips |
      | State             | Alabama | None                           | Female                   | Auburn University Main Campus | yes    | no         | 2 days from now | 3 days from now | None  | None             |
      | County            | Alaska  | Aleutians East Borough County  | Male                     | Auburn University Main Campus | no     | yes        | 2 days from now | 3 days from now | None  | None             |
      | Zip               | Arizona | None                           | Racial & Ethnic Minority | Auburn University Main Campus | yes    | no         | 2 days from now | 3 days from now | 76001 | 15               |

  @MATCH-5073
  Scenario: As a Support person provisioning an AM NxtGen zip code subscription, I want to have an open text box for the
  zip code radius that has a 100 mile limit, so that clients can be more specific with their radiuses.
    Given SP I am logged in to the Admin page as a Support user
    When SP I select "The University of Alabama" from the institution dashboard
    And SP I open the module link of name "Advanced Awareness"
    And SP I add a new subscription of type "Zip"
    And SP I fill the new subscription with the following data:
      | Radius from zips | 100 |
    Then SP I verify that the value in the Radius From Zips field is "100"
    And SP I fill the new subscription with the following data:
      | Radius from zips | 101 |
    Then SP I verify that the value in the Radius From Zips field is "10"



