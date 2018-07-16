@HS
Feature: As a HS user, I want to be able to access secure help link

  @MATCH-2057 @MATCH-2195
  Scenario: As a HS user, I want to access to secure help links to learn about my features.
            So non-clients cannot access our help content and learn about our product.
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I verify that the help content is not available for "Naviance HS Users"
    And HS I successfully sign out

    Given HS I want to login to the HS app using "hobsonstest11@mailinator.com" as username and "Control!23" as password
    Then HS I verify that the help content is secure and matches the correct URL for "Non-Naviance HS Users"
    And HS I successfully sign out

  @MATCH-1430
  Scenario: As an Intersect system, I need to have a Community Guidelines page available
  for users to read and understand how they are supposed to used the system and how the system uses their information.
    Given HS I want to login to the HS app using "purpleheautomation+admin@gmail.com" as username and "Password!1" as password
    Then HS I navigate to each page and verify the unique URL is present in the "Counselor Community Guidelines" page in Help Center
    Then HS I successfully sign out