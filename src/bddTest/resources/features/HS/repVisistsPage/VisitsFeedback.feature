@HS
Feature:  As an HS user, I want to be able to access the features of the Visit Feedback features.

  @MATCH-3101
  Scenario: As a HS Repvisits user, I should be able to see the text '#HE User# has asked for feedback on their recent visit.' in every entry present in
  Visit Feedback Pending tab
    Given HS I want to login to the HS app using "purpleheautomation+hstest@gmail.com" as username and "Password!1" as password
    Then HS I Navigate to Notifications & Tasks tab of the Repvisits Page
    Then HS I click the Visit Feedback sub tab
    Then HS I should be able to see the text - #HE User# has asked for feedback on their recent visit.- in every entry present in Visit Feedback Pending tab

