@HUBS
Feature: HUBS - ActiveMatch Next Gen - Import the AM NextGen Component

  @MATCH-5446
  Scenario: Hubs page loads AM NextGen component when clicking "Connect", when AM Next Gen is enabled (MATCH-5565)
    Given HUBS I am logged to Naviance Student as user "linussupermatch", with password "Hobsons!23" from school "blue1combo"
    When HUBS I search for the college "The University of Alabama"
    And HUBS I open the HUBS page for "The University of Alabama"
    And HUBS I enable AM Next Gen using the URL part "?amn=true"
    And HUBS I add the college to the I'm thinking about list, if it is not already added
    And HUBS I verify that the AM Next Gen component is loaded when the user clicks the Connect button

  @MATCH-5446
  Scenario:Hubs page loads Legacy AM component when clicking "Connect", when AM Next Gen is disabled (MATCH-5565)
    Given HUBS I am logged to Naviance Student as user "linussupermatch", with password "Hobsons!23" from school "blue1combo"
    When HUBS I search for the college "The University of Alabama"
    And HUBS I open the HUBS page for "The University of Alabama"
    And HUBS I add the college to the I'm thinking about list, if it is not already added
    And HUBS I verify that the Legacy AM component is loaded when the user clicks the Connect button

  @MATCH-4463
  Scenario:As a user of the Naviance Student College Profile Pages, I would like to see the new footer that is being
  used in the new SuperMatch so that I can have access to more information quickly and easily from the footer area and
  drive traffic to the College Match page.
    Given HUBS I am logged to Naviance Student as user "linussupermatch", with password "Hobsons!23" from school "blue1combo"
    When HUBS I search for the college "The University of Alabama"
    And HUBS I open the HUBS page for "The University of Alabama"
    Then HUBS I verify the dark blue footer
    Then HUBS I verify the "Upcoming Visits" link in the SuperMatch Footer
    And HUBS I verify the "Events" link in the SuperMatch Footer
