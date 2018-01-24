@SM
Feature: SM - AccessSuperMatch - As a SuperMatch user, I want to be able to access the SuperMatch UI

  Scenario: As a SuperMatch user, I want to be able to access the SuperMatch UI and search for colleges.
    Given SM I am logged in to SuperMatch through Family Connection as user "benhubs" with password "Hobsons!23" from school "rtsa"
    Then SM I sign out of SuperMatch through Family Connection
    # This doesn't actually go through Family Connection since it's not hooked up yet, it just goes to the public URL
    Given SM I am logged in to SuperMatch through Family Connection
    Then I select the following data from the Location Fit Criteria
      |Search type                |State or Province  |Quick Selection: US Regions & Others |Campus Surroundings     |
      |Search by state or region  |Ohio, Indiana      |Northeast, Canada                    |Large City, Small City  |
