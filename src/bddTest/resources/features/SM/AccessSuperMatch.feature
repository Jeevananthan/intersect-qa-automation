@SM
Feature: SM - AccessSuperMatch - As a SuperMatch user, I want to be able to access the SuperMatch UI

  #This is only an example scenario for testing features, so it's tagged with NotInQA to stay out of Maven executions
  @NotInQA
  Scenario: As a SuperMatch user, I want to be able to access the SuperMatch UI and search for colleges.
    Given SM I am logged in to SuperMatch through Family Connection as user "benhubs" with password "Hobsons!23" from school "rtsa"
    # This doesn't actually go through Family Connection since it's not hooked up yet, it just goes to the public URL
    Given SM I am logged in to SuperMatch through Family Connection
    Then I select the following data from the Location Fit Criteria
      |Search type                |State or Province  |Quick Selection: US Regions & Others |Campus Surroundings     |
      |Search by state or region  |Ohio, Indiana      |Northeast, Canada                    |Large City, Small City  |

  @SMINTEGRATIONTEST
  Scenario: As a HS student accessing SuperMatch through Family Connection I need to be able to
            add or remove filter criteria from the Must Have box
    Given SM I am logged in to SuperMatch through Family Connection
    Then SM I select the "Counseling Services" checkbox from the Resources fit criteria
    Then SM I verify that the Must Have box contains "Counseling Services"
    Then SM I remove the "Counseling Services" fit criteria from the Must Have box or Nice to Have box
    And SM I verify that the Must Have box does not contain "Counseling Services"
    And SM I verify that the "Counseling Services" checkbox from the Resources fit criteria is "unchecked"
