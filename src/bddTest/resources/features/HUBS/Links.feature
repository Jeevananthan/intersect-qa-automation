@HUBS @HUBS-1016
Feature: HUBS - Links - As a community user viewing College Hubs, I want to verify the functionality of the links in HEM

  Background:
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HUBS I access HUBS Edit Mode

  @HUBS-1016
  Scenario: The 'Terms of Service' link points to the Terms of Service page
    When HUBS I verify the Terms of Service page
    | Terms of Service | Privacy Policy |

