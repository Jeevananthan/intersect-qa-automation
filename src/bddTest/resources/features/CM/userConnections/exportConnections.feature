@CM
Feature: Community User - Automate Bulk Export of Connections
  As a Community user I want to automate the exporting of my Community connections en masse so I can take this
  information and add it to my CRM for more advanced communication capabilities.

  @MATCH-842
  Scenario: As a Community user I want to automate the exporting of my Community connections.
    Given I am logged in to Purple Community through the HE App
    And I go to connections page
    And I am sure that user has at least one connection
    Then I export my connections
    And I check if connections CSV file "my_connections_" is exported successfully to location "/Users/bojan/Downloads"