@HS
Feature:  HS - RepVisits - VisitsFeedback - As an HS user, I should be able to provide feedback on the visit to HE user

  @MATCH-3101
  Scenario: As a HS Repvisits user, I should be able to see the text '#HE User# has asked for feedback on their recent visit.' in every entry present in
  Visit Feedback Pending tab
    Given HS I want to login to the HS app using "purpleheautomation+hstest@gmail.com" as username and "Password!1" as password
    Then HS I Navigate to Notifications & Tasks tab of the Repvisits Page
    Then HS I click the Visit Feedback sub tab
    Then HS I should be able to see the text - #HE User# has asked for feedback on their recent visit.- in every entry present in Visit Feedback Pending tab

  @MATCH-2981
  Scenario: As an HE or HS user interacting with the Ratings/Staff Feedback features, I want all references to another user
  to be hyperlinked to their Community profile
    When HS I want to login to the HS app using "purpleheautomation+HSAuto_freshAccount@gmail.com" as username and "Password!1" as password
    Then HS I verify HE user's name be an active hyperlink to the HS user's Community profile in visit feedback subtab
    Given HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify HS user's name be an active hyperlink to the HE user's Community profile in visit feedback subtab