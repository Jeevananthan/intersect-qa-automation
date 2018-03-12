@SM
Feature: As a HS student accessing SuperMatch through Family Connection I need to be able to search college based on
  certain fit criteria

  @MATCH-3592
  Scenario: As a HS student accessing SuperMatch through Family Connection I need to be presented with an Student Body
            Size fit criteria
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I verify the Student Body UI in Resources Dropdown

  @MATCH-3933
  Scenario: Based on the generic data available for online learning opportunities in CMS per college, we need to add a
            tooltip next to the 'Include online learning opportunities' fit criteria in the Academic fit category
            dropdown so students can understand how we return search results that include this fit criteria.
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I verify that tooltip icon is added to the include online learning opportunities fit criteria