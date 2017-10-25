@HUBS @HEM @HE
Feature: HE- Community - HUBSEditMode - As a HE User, I want to be able to edit HE Institution Data

    @MATCH-1044
    Scenario: As a HE user with admin role or publishing role I can access the HUBS edit mode page in HE app.
              As a HE user with community role I do not have access to the HUBS edit mode page.
      Given HE I am logged in to Intersect HE as user type "administrator"
      And HUBS I access HUBS Edit Mode
      And HE I successfully sign out
      Given HE I am logged in to Intersect HE as user type "publishing"
      And HUBS I access HUBS Edit Mode
      And HE I successfully sign out
      Given HE I am logged in to Intersect HE as user type "community"
      And HE I verify the "Naviance college profile" nav link is not displaying for this user
      And HE I successfully sign out

  @HUBSStudies
  Scenario: As a HE user with access to HEM, I van view the Studies section
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HUBS I access HUBS Edit Mode
    Then HUBS I open the "Studies" tab in the preview
    Then HUBS All the elements of the studies tab should be displayed
    And HE I successfully sign out
