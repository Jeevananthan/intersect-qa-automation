@SP
Feature: As a super admin and admin role in the Support app of Intersect, I want the ability to manage Intersect
  In-product notifications in the support app.

  @MATCH-3905 @MATCH-3908
  Scenario: As a super admin and admin role in the Support app of Intersect, I want the ability to add/edit and see
            current published/unpublished Intersect in-product notifications in the support app. So that management is
            centralized for me at the Admin Dashboard in the support app.

    Given SP I am logged in to the Admin page as an Admin user
    And SP I un-publish all the published announcements
    When SP I add a new product announcement with title "AutomationAnnouncement" content "" audience "" and status "Published"
    #Verifying labels and tooltips
    Then SP I verify the label with text "The title field is formatted in bold and shows before the content field." is displayed
    Then SP I verify the label with text "30 character limit" is displayed
    Then SP I verify that field "Content" has a tooltip that says "Field is required"
    And SP I verify that field "Audience" has a tooltip that says "Field is required"
    And SP I verify that the error message that says "Something went wrong" "The fields highlighted below need your attention" is displayed
    #Adding a published announcement
    When SP I add a new product announcement with title "AutomationAnnouncementWith30Ch" content "ContentTest" audience "HE,HS - Naviance,HS - Non Naviance" and status "Published"
    Then SP I verify the toast with the message "New announcement added" is displayed
    And SP I verify the product announcement with title "AutomationAnnouncementWith30Ch" content "ContentTest" visibility "HE, HS Naviance, HS Non Naviance" date "today" user "Match Support UI QA4" and status "Published" in the list
    #Adding a published announcement for the same users
    When SP I add a new product announcement with title "AutomationAnnouncement2" content "ContentTest" audience "HE" and status "Published"
    Then SP I verify that the error message that says "Something went wrong" "The fields highlighted below need your attention" is displayed
    And SP I verify that field "Status" has a tooltip that says "You can only have 1 announcement per audience published at a time. Save as unpublished to edit later."
    #Adding unpublished announcement
    When SP I add a new product announcement with title "AutomationAnnouncement2" content "ContentTest" audience "HE" and status "Unpublished"
    Then SP I verify the toast with the message "New announcement added" is displayed
    And SP I verify the product announcement with title "AutomationAnnouncement2" content "ContentTest" visibility "HE" date "today" user "Match Support UI QA4" and status "Unpublished" in the list
    #Editing a product announcement to published to get error message
    When SP I edit the product announcement "AutomationAnnouncement2" with title "AutomationEdited" content "ContentEdited" audience "" and status "Published"
    Then SP I verify that the error message that says "Something went wrong" "The fields highlighted below need your attention" is displayed
    And SP I verify that field "Status" has a tooltip that says "You can only have 1 announcement per audience published at a time. Save as unpublished to edit later."
    #Editing to unpublished and save announcements
    When SP I edit the product announcement "AutomationAnnouncementWith30Ch" with title "AutomationEdited" content "ContentEdited" audience "" and status "Unpublished"
    Then SP I verify the product announcement with title "AutomationEdited" content "ContentEdited" visibility "HE, HS Naviance, HS Non Naviance" date "today" user "Match Support UI QA4" and status "Unpublished" in the list
    Then SP I verify the toast with the message "Changes saved" is displayed
    #Editing to published and save announcements
    When SP I edit the product announcement "AutomationAnnouncement2" with title "AutomationEdited2" content "ContentEdited2" audience "HS - Naviance" and status "Published"
    Then SP I verify the product announcement with title "AutomationEdited2" content "ContentEdited2" visibility "HE, HS Naviance" date "today" user "Match Support UI QA4" and status "Published" in the list
    Then SP I verify the toast with the message "Changes saved" is displayed
    #Unpublishing announcements
    And SP I edit the product announcement "AutomationEdited2" with title "AutomationEdited2" content "ContentEdited2" audience "" and status "Unpublished"
    And SP I successfully sign out

