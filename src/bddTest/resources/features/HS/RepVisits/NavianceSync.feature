@HS
Feature:  As an HS user, I want to be able to access the features of the Sync Opt in features.

@MATCH-3237
Scenario: As an RepVisits HS user that had previously connected my RepVisits with my Naviance account to publish
events into Naviance, I want the ability to opt out/disconnect my RepVisits events from publishing to Naviance,
so that I can manage events separately in Naviance and RepVisits.
Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
Then HS I navigate to naviance settings page
And HS I click on Disconnect RepVisits from Naviance button
And HS I verify the Cancel on the disconnect confirmation popup
And HS I click on Disconnect RepVisits from Naviance button
And HS I verify the Yes on the disconnect confirmation popup with "7","6:30am","7:00am","2","PurpleHE Automation" and "Cbba"
And HS I cancel a visit with time "6:30AM" college "Bowling Green State University-Main Campus" and note "Cancel"
Then HS I remove the Time Slot recently created with "7","6:30am" in Regular Weekly Hours Tab
And HS I successfully sign out