@SP
Feature: SP - Account Pages - AccountPageView - Manage Institution account and subscriptions
         As a Hobsons staff user I need to view individual institutional accounts.

  @MATCH-1670
  Scenario: As a Support User I can access Hubs view mode
    Given SP I am logged in to the Admin page as an Admin user
    When SP I search for "Bowling Green State University-Main Campus" in "Institutions"
    And SP I select the following institution "Bowling Green State University-Main Campus" from the results
    Then SP I verify Hubs view mode for "Bowling Green State University-Main Campus"
    And SP I successfully sign out

  @MATCH-264
  Scenario: As a Hobsons Sales Ops user I can view an institutional account
    Given SP I am logged in to the Admin page as a Sales Ops user
    When SP I select "The University of Alabama" from the institution dashboard
    Then SP I am able to view the individual account page
    Then SP I do not have access to "Community" sub menu in left navigation
    And SP I successfully sign out

  @MATCH-3007
  Scenario: As a Hobsons Super Admin user I can view an institutional account
    Given SP I am logged in to the Admin page as a Super Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    Then SP I am able to view the individual account page
    Then SP I do have access to "Community" sub menu in left navigation
    And SP I successfully sign out

  @MATCH-264
  Scenario: As a Hobsons Admin user I can view an institutional account
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    Then SP I am able to view the individual account page
    Then SP I do have access to "Community" sub menu in left navigation
    And SP I successfully sign out

  @MATCH-264
  Scenario: As a Hobsons Support user I can view an institutional account
    Given SP I am logged in to the Admin page as a Support user
    When SP I select "The University of Alabama" from the institution dashboard
    Then SP I am able to view the individual account page
    And SP I successfully sign out

  @MATCH-958
  Scenario: As a Hobsons Support user I should not activate any module where the end date is earlier than the start date and vice-versa.
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "The University of Alabama" from the institution dashboard
    Then SP I am able to view the individual account page
    Then SP I set the "Legacy: Hub page management" module to "active" in the institution page
    Then SP I verify subscription end date restrictions
    Then SP I verify subscription start date restrictions
    And SP I successfully sign out

  @MATCH-264
  Scenario: As a Hobsons Community user I cannot view an institutional accounts but have access to Community
    Given SP I am logged in to the Admin page as a Community user
    Then SP I do not have access to "Home" sub menu in left navigation
    Then SP I do have access to "Community" sub menu in left navigation
    And SP I successfully sign out

  @MATCH-264
  Scenario: As a Hobsons Community Manager user I cannot view an institutional accounts but have access to Community
    Given SP I am logged in to the Admin page as a Community Manager user
    Then SP I do not have access to "Home" sub menu in left navigation
    Then SP I do have access to "Community" sub menu in left navigation
    And SP I successfully sign out

  @MATCH-650
  Scenario: As a Hobsons staff user I can see the institutional account address, city, state/province, postal code, SCID
    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "Docufide Institute of Technology (not a real school)" from the institution dashboard
    Then SP I should see Additional Contact Details on Institutional Account Page
    And SP I successfully sign out

  @MATCH-1895  @MATCH-1496
  Scenario: As an support user I want the Intersect left navigation bar to be better organized and labeled.
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I verify the left navigation bar and section breadcrumbs are as follows
      |Awareness|Counselor Community|
    And SP I successfully sign out


   @MATCH-128 @MATCH-4366 @MATCH-4367
    Scenario Outline: As a Support user with admin privilege or support privilege or sales ops privilege is able to edit
              Activate/Inactivate modules/products within the institutional accounts

      Given SP I am logged in to the Admin page as an Admin user
      When SP I select "<University>" from the institution dashboard
      Then SP I set the "<HubsModule>" module to "<Active>" with the start date "<StartDate>" and end date "<EndDate>" in the institution page
      Then SP I set the "<CommunityModule>" module to "<Active>" with the start date "<StartDate>" and end date "<EndDate>" in the institution page
      Then SP I set the "<IntersectAwarenessModule>" module to "<Active>" with the start date "<StartDate>" and end date "<EndDate>" in the institution page
      Then SP I set the "<IntersectPresenceModule>" module to "<Active>" with the start date "<StartDate>" and end date "<EndDate>" in the institution page
      Then SP I set the "<Legacy: ActiveMatch Events>" module to "<Active>" with the start date "<StartDate>" and end date "<EndDate>" in the institution page
      Then SP I set the "<ActiveMatch Plus>" module to "<Active>" with the start date "<StartDate>" and end date "<EndDate>" in the institution page
      Then SP I set the "<AdvancedAwarenessModule>" module to "<Active>" with the start date "<StartDate>" and end date "<EndDate>" in the institution page
      Then SP I set the "<ConnectionModule>" module to "<Active>" with the start date "<StartDate>" and end date "<EndDate>" in the institution page
      And SP I Click the Save Changes button
      When SP I select "<University>" from the institution dashboard
      Then SP I verify the status "<Active>" with the start date "<StartDate>" and end date "<EndDate>" for the module "<HubsModule>"
      Then SP I verify the status "<Active>" with the start date "<StartDate>" and end date "<EndDate>" for the module "<CommunityModule>"
      Then SP I verify the status "<Active>" with the start date "<StartDate>" and end date "<EndDate>" for the module "<IntersectAwarenessModule>"
      Then SP I verify the status "<Active>" with the start date "<StartDate>" and end date "<EndDate>" for the module "<IntersectPresenceModule>"
      Then SP I verify the status "<Active>" with the start date "<StartDate>" and end date "<EndDate>" for the module "<Legacy: ActiveMatch Events>"
      Then SP I verify the status "<Active>" with the start date "<StartDate>" and end date "<EndDate>" for the module "<ActiveMatch Plus>"
      Then SP I verify the status "<Active>" with the start date "<StartDate>" and end date "<EndDate>" for the module "<AdvancedAwarenessModule>"
      Then SP I verify the status "<Active>" with the start date "<StartDate>" and end date "<EndDate>" for the module "<ConnectionModule>"
      When SP I select "<University>" from the institution dashboard
      Then SP I set the "<HubsModule>" module to "<Inactive>" in the institution page
      Then SP I set the "<CommunityModule>" module to "<Inactive>" in the institution page
      Then SP I set the "<IntersectAwarenessModule>" module to "<Inactive>" in the institution page
      Then SP I set the "<IntersectPresenceModule>" module to "<Inactive>" in the institution page
      Then SP I set the "<Legacy: ActiveMatch Events>" module to "<Inactive>" in the institution page
      Then SP I set the "<ActiveMatch Plus>" module to "<Inactive>" in the institution page
      Then SP I set the "<AdvancedAwarenessModule>" module to "<Inactive>" in the institution page
      Then SP I set the "<ConnectionModule>" module to "<Inactive>" in the institution page
      And SP I Click the Save Changes button
      When SP I select "<University>" from the institution dashboard
      Then SP I verify the status "<Inactive>" with the start date "" and end date "" for the module "<HubsModule>"
      Then SP I verify the status "<Inactive>" with the start date "" and end date "" for the module "<CommunityModule>"
      Then SP I verify the status "<Inactive>" with the start date "" and end date "" for the module "<IntersectAwarenessModule>"
      Then SP I verify the status "<Inactive>" with the start date "" and end date "" for the module "<IntersectPresenceModule>"
      Then SP I verify the status "<Inactive>" with the start date "" and end date "" for the module "<Legacy: ActiveMatch Events>"
      Then SP I verify the status "<Inactive>" with the start date "" and end date "" for the module "<ActiveMatch Plus>"
      Then SP I verify the status "<Inactive>" with the start date "" and end date "" for the module "<AdvancedAwarenessModule>"
      Then SP I verify the status "<Inactive>" with the start date "" and end date "" for the module "<ConnectionModule>"
      And SP I successfully sign out

      Examples:
      |University                                |HubsModule                 |CommunityModule  |IntersectAwarenessModule        |IntersectPresenceModule        |Legacy: ActiveMatch Events|ActiveMatch Plus|AdvancedAwarenessModule|ConnectionModule|StartDate|EndDate|Inactive|Active|
      |Bowling Green State University-Main Campus|Legacy: Hub page management|Legacy: Community|Intersect Awareness Subscription|Intersect Presence Subscription|Legacy: ActiveMatch Events|ActiveMatch Plus|Advanced Awareness     |Connection      |0        |35     |inactive|active|

  @MATCH-3748
  Scenario Outline: As a Support user, I want the ability to specify a module subscription start and end date for paid HE institutions,
                    So that I can provision and activate multi-year subscriptions without issues.

    Given SP I am logged in to the Admin page as an Admin user
    When SP I select "<University>" from the institution dashboard
#Hubs Module
    Then SP I set the "<HubsModule>" module to "<Active>" in the institution page
    Then SP I verify the rolling update behaviour changed to "-1" and "+12" for "<HubsModule>" in institution calendar page
    Then SP I verify the list of years present in the subscription modules start date and end date calendar for "<HubsModule>"
    Then SP I verify the user can able to select a "<year>" from the year list in calendar using "<HubsModule>","<startDate>","<endDate>"
    Then SP I verify the "<color>" is present in the selected date "<startDate>","<endDate>" in calendar of the institution page for "<HubsModule>"
#Community Module
    Then SP I set the "<CommunityModule>" module to "<Active>" in the institution page
    Then SP I verify the rolling update behaviour changed to "-1" and "+12" for "<CommunityModule>" in institution calendar page
    Then SP I verify the list of years present in the subscription modules start date and end date calendar for "<CommunityModule>"
    Then SP I verify the user can able to select a "<year>" from the year list in calendar using "<CommunityModule>","<startDate>","<endDate>"
    Then SP I verify the "<color>" is present in the selected date "<startDate>","<endDate>" in calendar of the institution page for "<CommunityModule>"
#Intersect Awareness Module
    Then SP I set the "<IntersectAwarenessModule>" module to "<Active>" in the institution page
    Then SP I verify the rolling update behaviour changed to "-1" and "+12" for "<IntersectAwarenessModule>" in institution calendar page
    Then SP I verify the list of years present in the subscription modules start date and end date calendar for "<IntersectAwarenessModule>"
    Then SP I verify the user can able to select a "<year>" from the year list in calendar using "<IntersectAwarenessModule>","<startDate>","<endDate>"
    Then SP I verify the "<color>" is present in the selected date "<startDate>","<endDate>" in calendar of the institution page for "<IntersectAwarenessModule>"
#Intersect Presence Module
    Then SP I set the "<IntersectPresenceModule>" module to "<Active>" in the institution page
    Then SP I verify the rolling update behaviour changed to "-1" and "+12" for "<IntersectPresenceModule>" in institution calendar page
    Then SP I verify the list of years present in the subscription modules start date and end date calendar for "<IntersectPresenceModule>"
    Then SP I verify the user can able to select a "<year>" from the year list in calendar using "<IntersectPresenceModule>","<startDate>","<endDate>"
    Then SP I verify the "<color>" is present in the selected date "<startDate>","<endDate>" in calendar of the institution page for "<IntersectPresenceModule>"
#Active Match Events Module
    Then SP I set the "<ActiveMatchEventsModule>" module to "<Active>" in the institution page
    Then SP I verify the rolling update behaviour changed to "-1" and "+12" for "<ActiveMatchEventsModule>" in institution calendar page
    Then SP I verify the list of years present in the subscription modules start date and end date calendar for "<ActiveMatchEventsModule>"
    Then SP I verify the user can able to select a "<year>" from the year list in calendar using "<ActiveMatchEventsModule>","<startDate>","<endDate>"
    Then SP I verify the "<color>" is present in the selected date "<startDate>","<endDate>" in calendar of the institution page for "<ActiveMatchEventsModule>"
#Active Match Plus Module
    Then SP I set the "<ActiveMatchPlusModule>" module to "<Active>" in the institution page
    Then SP I verify the rolling update behaviour changed to "-1" and "+12" for "<ActiveMatchPlusModule>" in institution calendar page
    Then SP I verify the list of years present in the subscription modules start date and end date calendar for "<ActiveMatchPlusModule>"
    Then SP I verify the user can able to select a "<year>" from the year list in calendar using "<ActiveMatchPlusModule>","<startDate>","<endDate>"
    Then SP I verify the "<color>" is present in the selected date "<startDate>","<endDate>" in calendar of the institution page for "<ActiveMatchPlusModule>"
#Advanced Awareness Module
    Then SP I set the "<AdvancedAwarenessModule>" module to "<Active>" in the institution page
    Then SP I verify the rolling update behaviour changed to "-1" and "+12" for "<AdvancedAwarenessModule>" in institution calendar page
    Then SP I verify the list of years present in the subscription modules start date and end date calendar for "<AdvancedAwarenessModule>"
    Then SP I verify the user can able to select a "<year>" from the year list in calendar using "<AdvancedAwarenessModule>","<startDate>","<endDate>"
    Then SP I verify the "<color>" is present in the selected date "<startDate>","<endDate>" in calendar of the institution page for "<AdvancedAwarenessModule>"
#Connection Module
    Then SP I set the "<ConnectionModule>" module to "<Active>" in the institution page
    Then SP I verify the rolling update behaviour changed to "-1" and "+12" for "<ConnectionModule>" in institution calendar page
    Then SP I verify the list of years present in the subscription modules start date and end date calendar for "<ConnectionModule>"
    Then SP I verify the user can able to select a "<year>" from the year list in calendar using "<ConnectionModule>","<startDate>","<endDate>"
    Then SP I verify the "<color>" is present in the selected date "<startDate>","<endDate>" in calendar of the institution page for "<ConnectionModule>"

    When SP I select "<University>" from the institution dashboard
    Then SP I verify the status "<Inactive>" with the start date "" and end date "" for the module "<HubsModule>"
    Then SP I verify the status "<Inactive>" with the start date "" and end date "" for the module "<CommunityModule>"
    Then SP I verify the status "<Inactive>" with the start date "" and end date "" for the module "<IntersectAwarenessModule>"
    Then SP I verify the status "<Inactive>" with the start date "" and end date "" for the module "<IntersectPresenceModule>"
    Then SP I verify the status "<Inactive>" with the start date "" and end date "" for the module "<ActiveMatchEventsModule>"
    Then SP I verify the status "<Inactive>" with the start date "" and end date "" for the module "<ActiveMatchPlusModule>"
    Then SP I verify the status "<Inactive>" with the start date "" and end date "" for the module "<AdvancedAwarenessModule>"
    Then SP I verify the status "<Inactive>" with the start date "" and end date "" for the module "<ConnectionModule>"
    And SP I successfully sign out
    And SP I successfully sign out

  Examples:
  |University                                |HubsModule                 |CommunityModule  |IntersectAwarenessModule        |IntersectPresenceModule        |ActiveMatchEventsModule   |ActiveMatchPlusModule|AdvancedAwarenessModule|ConnectionModule|startDate|endDate|Active|Inactive|year|color              |
  |Bowling Green State University-Main Campus|Legacy: Hub page management|Legacy: Community|Intersect Awareness Subscription|Intersect Presence Subscription|Legacy: ActiveMatch Events|ActiveMatch Plus     |Advanced Awareness     |Connection	  |15       |20     |active|inactive|2030|rgba(210, 0, 97, 1)|










