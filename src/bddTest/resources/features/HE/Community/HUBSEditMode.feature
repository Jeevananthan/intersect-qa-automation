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

  @MATCH-4461
  Scenario: As an HE Intersect user editing my GPA fields in my College Profile (HEM), I need validations in place so
  that I do not put in incorrect values that don't make sense. (part 1)
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HUBS I access HUBS Edit Mode
    And HUBS I open "Overview" in the edit menu
    And HUBS I open "GPA" in the edit menu
    Then HUBS I verify the error message "Please use a GPA between 0.1 and 4.0" in the GPA textbox, with the following data:
    | 25th Percentile (visible only in SuperMatch) | 4.1 |
    | 25th Percentile (visible only in SuperMatch) | 0.0 |
    | Average | 4.1 |
    | Average | 0.0 |
    | 75th Percentile (visible only in SuperMatch) | 4.1 |
    | 75th Percentile (visible only in SuperMatch) | 0.0 |

  @MATCH-4461
  Scenario: As an HE Intersect user editing my GPA fields in my College Profile (HEM), I need validations in place so
  that I do not put in incorrect values that don't make sense. (part 2)
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HUBS I access HUBS Edit Mode
    And HUBS I open "Overview" in the edit menu
    And HUBS I open "GPA" in the edit menu
    Then HUBS I verify the error message "GPA must be in n.n format. (Example: 3.0)" in the GPA textbox, with the following data:
      | 25th Percentile (visible only in SuperMatch) | abc |
      | 25th Percentile (visible only in SuperMatch) | abc |
      | Average | abc |
      | Average | abc |
      | 75th Percentile (visible only in SuperMatch) | abc |
      | 75th Percentile (visible only in SuperMatch) | abc |

  @MATCH-4461
  Scenario: As an HE Intersect user editing my GPA fields in my College Profile (HEM), I need validations in place so
  that I do not put in incorrect values that don't make sense. (part 3)
    Given HE I am logged in to Intersect HE as user type "administrator"
    And HUBS I access HUBS Edit Mode
    And HUBS I open "Overview" in the edit menu
    And HUBS I open "GPA" in the edit menu
    And HUBS I set the GPA values as follows:
      | 25th Percentile (visible only in SuperMatch) | 3.5 |
      | Average                                      | 3.4 |
      | 75th Percentile (visible only in SuperMatch) | 3.3 |
    Then HUBS I verify that the following error messages:
      | 25th Percentile (visible only in SuperMatch) | This value must be less than or equal to Average. |
      | Average                                      | This value must be less than or equal to 75th Percentile. |
      | 75th Percentile (visible only in SuperMatch) | This value must be greater than or equal to Average.      |


