@HE
  Feature: As a RepVisits HE User, I need to be able to configure my RepVisits notifications

    @MATCH-4207
    Scenario: As a RepVisits HE User, I need to be able to configure my RepVisits notifications, So I can see what
    I need without having a cluttered inbox.
      Given HE I am logged in to Intersect HE as user type "administrator"
      When HE I go to Your Notifications
      Then HE I verify that "Your Notifications" title is displayed
      And HE I verify that "REPVISITS" sub title is displayed
      And HE I verify that "Choose what you'd like to receive email notifications about." description is displayed
      And HE I verify that Alert me when high schools become available in RepVisits checkbox is displayed
      When HE I save your notifications
      Then HE I verify the toast that says "Your notifications settings were updated." is displayed