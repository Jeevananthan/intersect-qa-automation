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
    When SP I add a new product announcement with title "AutomationAnnouncementWith30Ch+1" content "ContentTest" audience "HE,HS - Naviance,HS - Non Naviance" and status "Published"
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
    And SP I verify the product announcement with title "AutomationAnnouncement2" content "ContentTest" audience "HE" and status "Unpublished" in the the edit form
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

  @MATCH-3904
  Scenario: As a super admin and admin role in the Support app of Intersect,
  I want the ability to have a location within the support app that allows me to create Intersect in-product notifications,
  So that when there are any important notifications (e.g. maintenance, product survey's, release notifications, etc.) related to the Intersect product line,
  I have the ability to directly notify users within the application and manage such from the Support app.
#Verify admin dashboard is displayed
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I verify that Admin dashboard is displayed in Homepage
    Then SP I verify the header is changed from "Intersect" to "Admin Dashboard" in the Admin dashboard page
    Then SP I verify "Product Announcements" stub menu is displayed in the Admin dashboard page
    And SP I successfully sign out
#Verify admin dashboard is not displayed
    When SP I am logged in to the Admin page as a Community Manager user
    And SP I verify that Admin dashboard is not displayed
    And SP I successfully sign out
    When SP I am logged in to the Admin page as a Community user
    And SP I verify that Admin dashboard is not displayed
    And SP I successfully sign out
    When SP I am logged in to the Admin page as a Sales Ops user
    And SP I verify that Admin dashboard is not displayed
    And SP I successfully sign out
    When SP I am logged in to the Admin page as a Support user
    And SP I verify that Admin dashboard is not displayed
    And SP I successfully sign out
    
  @MATCH-4138
  Scenario: As a super admin and admin role in the Support app of Intersect,
  I want the ability to add and see current published/unpublished Intersect in-product notifications in the support app,
  So that management is centralized for me at the Admin Dashboard in the support app.
    Given SP I am logged in to the Admin page as an Admin user
    And SP I un-publish all the published announcements
    #create announcement with title and more than 140 characters
    When SP I add a new product announcement with title "Intersect" content "ThisisgoingtobeMorethan140characterslimit.ThisisgoingtobeMorethan140characterslimit.ThisisgoingtobeMorethan140characterslimit.ThisisgoingtobeMorethan140characterslimit." audience "HE,HS - Naviance,HS - Non Naviance" and status "Published"
    Then SP I verify the toast with the message "New announcement added" is displayed
    Then SP I verify title "Intersect" is displayed in the Product Announcements page
    #verify content with 140 characters
    Then SP I verify "140" characters are displayed in the Product Announcements page
    Then SP I verify the visibility for the following details "HE, HS Naviance, HS Non Naviance" in the Product Announcements page using "0"
    Then SP I verify the date format "MMM d" in the Product Announcements page using "0" for the user "Match Support UI QA4"
    And SP I verify the status "Published" is displayed in the Product Announcements page using title "Intersect"
    #create announcement with no title and 140 characters
    When SP I add a new product announcement with title "" content "Thisisgoingtobe140characterslimit.Thisisgoingtobe140characterslimit.Thisisgoingtobe140characterslimit.Thisisgoingto be 140 characters limit." audience "HE" and status "Unpublished"
    Then SP I verify the toast with the message "New announcement added" is displayed
    Then SP I verify title "Untitled" is displayed in the Product Announcements page
    #verify content with 140 characters
    Then SP I verify "140" characters are displayed in the Product Announcements page
    Then SP I verify the visibility for the following details "HE" in the Product Announcements page using "0"
    Then SP I verify the date format "MMM d" in the Product Announcements page using "0" for the user "Match Support UI QA4"
    And SP I verify the status "Unpublished" is displayed in the Product Announcements page using title "Untitled"
    Then SP I verify "Show More" button for more than 25 notifications in the Product Announcements page
    And SP I un-publish all the published announcements
    And SP I successfully sign out
