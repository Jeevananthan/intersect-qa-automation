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

  @MATCH-3075 @MATCH-3098 @MATCH-2271 @MATCH-2269 @MATCH-2819 @MATCH-2268
  Scenario: As a HS user I do now work to see the word 'rating' or 'rate' when interacting with the RV Feedback functionality
            so I am more included to use the features to help HE users improve their visit skills.
    Given HS I am logged in to Intersect HS through Naviance with account "navianceAdmin"
    Then HS I verify visit feedback tab and its subtabs
    Then HS I verify the visit feedback tab showing after Naviance Sync tab
    And HS I verify the Pending subtab under Visit Feedback
    And HS I verify the lock icon for Anonymously feedback
    And HS I verify submitted subtab under Visit Feedback
    Then HS I successfully sign out

    Given HS I am logged in to Intersect HS as user type "administrator"
    #When HS I want to login to the HS app using "purpleheautomation+hstest@gmail.com" as username and "Password!1" as password
    Then HS I verify visit feedback tab and its subtabs
    Then HS I verify the visit feedback tab showing after Activity tab
    And HS I verify the Pending subtab under Visit Feedback
    And HS I verify the lock icon for Anonymously feedback
    And HS I verify submitted subtab under Visit Feedback