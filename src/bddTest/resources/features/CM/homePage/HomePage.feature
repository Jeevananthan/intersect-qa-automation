@CM
Feature: As a Freemium or Legacy Hubs HE user I want to see an additional Community Home page widget
         about all the neato features I am missing out on in the Community so I feel inclined to
         upgrade my HE institution's account to a premium one.

  @MATCH-1550
  Scenario Outline: As a Freemium or Legacy Hubs HE user I want to see an additional Community Home page widget
            and for Premium HE Legacy Community Hubs/Premium HE Intersect Awareness HE user the new
            widget will not show
    When HE I want to login to the HE app using "<Username>" as username and "<Password>" as password
    Then CM I verify that the upgrade widget is "<Visibility>" for "<UserType>" users
    Examples:
      |UserType            |Username                              |Password   |Visibility  |
      |Freemium            |purpleheautomation+limited@gmail.com  |Password!1 |visible     |
      |Legacy Community    |floresu.test+xavierhe@gmail.com       |Xavier18!  |not visible |
