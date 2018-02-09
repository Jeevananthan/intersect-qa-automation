Feature: As a HS student accessing SuperMatch through Family Connection I need to be able to search college based on
  certain fit criteria

  @MATCH-3667
  Scenario: Verify that the box containing instructional text has a width of 25% and the Must Have
    and Nice to Have boxes split the rest
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I verify the widths of the three boxes
