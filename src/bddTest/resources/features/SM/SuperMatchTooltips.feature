@SM
Feature: SM - SuperMatchSearch - As a HS student who is interacting with SuperMatch, I want to see tooltips so I can
  learn how to use the tool and what certain feature are/mean.

  Background:
    Given SM I am logged in to SuperMatch through Family Connection

  @MATCH-3526
  Scenario Outline: As a HS student accessing SuperMatch through Family Connection I need to be verify that the tooltips are displayed/closed
    Then SM I verify that the tooltips are displayed in the tab "<tabLabel>"
    Examples:
    | tabLabel |
    | Location |
    | Academics |
    | Admission |
    | Diversity |
    | Institution Characteristics |
    | Cost                        |
    | Student Life                |
    | Athletics:Aerobics          |
    | Resources                   |
