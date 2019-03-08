@SP
Feature: SP - AdminDashboard - ProductAnnouncementsPartII - As a super admin and admin  I want the ability to manage Intersect
         In-product notifications in the support app.

  @MATCH-4138
  Scenario: As a super admin and admin role in the Support app of Intersect
            I want the ability to add and see current published/unpublished Intersect in-product notifications in the support app,
            So that management is centralized for me at the Admin Dashboard in the support app.
    Given SP I am logged in to the Admin page as an Admin user
    And SP I un-publish all the published announcements
    #create announcement with title
    When SP I add a new product announcement with title "Intersect" content "ThisisgoingtobeMorethan140characterslimit.ThisisgoingtobeMorethan140characterslimit.ThisisgoingtobeMorethan140characterslimit.ThisisgoingtobeMorethan140characterslimit." audience "HE,HS - Naviance,HS - Non Naviance" and status "Published"
    Then SP I verify the toast with the message "New announcement added" is displayed
    #verify content with 140 characters
    Then SP I verify the product announcement with title "Intersect" content "ThisisgoingtobeMorethan140characterslimit.ThisisgoingtobeMorethan140characterslimit.ThisisgoingtobeMorethan140characterslimit.Thisisgoing..." visibility "HE, HS Naviance, HS Non Naviance" date "today" user "Match Support UI QA4" and status "Published" in the list
    #create announcement with no title
    When SP I add a new product announcement with title "" content "Thisisgoingtobe140characterslimit.Thisisgoingtobe140characterslimit.Thisisgoingtobe140characterslimit.Thisisgoingto be 140 characters limit." audience "HE" and status "Unpublished"
    Then SP I verify the toast with the message "New announcement added" is displayed
    #verify content with 140 characters
    Then SP I verify the product announcement with title "Untitled" content "Thisisgoingtobe140characterslimit.Thisisgoingtobe140characterslimit.Thisisgoingtobe140characterslimit.Thisisgoingto be 140 characters limit." visibility "HE" date "today" user "Match Support UI QA4" and status "Unpublished" in the list
    Then SP I verify "Show More" button for more than 25 notifications in the Product Announcements page
    And SP I un-publish all the published announcements

  @MATCH-3907
  Scenario Outline: As a Intersect HE, HS (naviance), and HS (non-naviance) user (any role),
  I want the ability to view the full content of an in product notification when it doesn't all fit in the notification bar,
  So that I'm able access the content of longer in product notifications.
    Given SP I am logged in to the Admin page as an Admin user
    And SP I un-publish all the published announcements
#create announcement with title
    When SP I add a new product announcement with title "Intersect" content "<content>" audience "HE,HS - Naviance,HS - Non Naviance" and status "Published"
    Then SP I verify the toast with the message "New announcement added" is displayed
#Verify announcement in HE
    Then HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify the header 'Product Announcement' is displaying in the product announcements 'Read More' drawer
    Then HE I verify the close button is displaying in the product announcements 'Read More' drawer
    Then HE I verify the title "Intersect" is displaying in the product announcements 'Read More' drawer
    Then HE I verify the date is displaying next to the title "Intersect" with "MMM dd" format in the product announcements 'Read More' drawer
    Then HE I verify the content "<content>" is displaying in the product announcements 'Read More' drawer
    Then HE I click close button in the product announcements 'Read More' drawer
#Verify announcement in HS-Naviance
    Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
    Then HS I verify the header 'Product Announcement' is displaying in the product announcements 'Read More' drawer
    Then HS I verify the close button is displaying in the product announcements 'Read More' drawer
    Then HS I verify the title "Intersect" is displaying in the product announcements 'Read More' drawer
    Then HS I verify the date is displaying next to the title "Intersect" with "MMM dd" format in the product announcements 'Read More' drawer
    Then HS I verify the content "<content>" is displaying in the product announcements 'Read More' drawer
    Then HS I click close button in the product announcements 'Read More' drawer
    And HS I successfully sign out
#Verify announcement in HS-Non-Naviance
    Given HS I am logged in to Intersect HS as user type "administrator"
    Then HS I verify the header 'Product Announcement' is displaying in the product announcements 'Read More' drawer
    Then HS I verify the close button is displaying in the product announcements 'Read More' drawer
    Then HS I verify the title "Intersect" is displaying in the product announcements 'Read More' drawer
    Then HS I verify the date is displaying next to the title "Intersect" with "MMM dd" format in the product announcements 'Read More' drawer
    Then HS I verify the content "<content>" is displaying in the product announcements 'Read More' drawer
    Then HE I click close button in the product announcements 'Read More' drawer
    And HS I successfully sign out
    Examples:
    |content                                                                                                                                                                                                                                                                                                                                         |
    |ThisisgoingtobeMorethan140characterslimit.ThisisgoingtobeMorethan140characterslimit.ThisisgoingtobeMorethan140characterslimit.ThisisgoingtobeMorethan140characterslimit.ThisisgoingtobeMorethan140characterslimit.ThisisgoingtobeMorethan140characterslimit.ThisisgoingtobeMorethan140characterslimit.ThisisgoingtobeMorethan140characterslimit.|

  @MATCH-4144
  Scenario: As a Intersect HS (naviance) user (any role),
  I want the ability to view any in-product notifications associated with me and as published by Hobsons Intersect support admins and super admins,
  So that I'm able to view the content they've intended for me to see within the Intersect product.
#verify announcement details in HS-Naviance
    Given SP I am logged in to the Admin page as an Admin user
    And SP I un-publish all the published announcements
#create announcement with title and with more than 140 characters
    When SP I add a new product announcement with title "Intersect" content "ThisisgoingtobeMorethan140characterslimit.ThisisgoingtobeMorethan140characterslimit.ThisisgoingtobeMorethan140characterslimit.ThisisgoingtobeMorethan140characterslimit." audience "HS - Naviance" and status "Published"
    Then SP I verify the toast with the message "New announcement added" is displayed

    Given HS I am logged in to Intersect HS through Naviance with user type "navAdminStandalone6"
    Then HS I verify the announcement title "Intersect" in the announcement details
    Then HS I verify the 'Read More' button is displaying for the content with more than 140 character limit
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
#verify announcement details with success message
    Then HS I verify the announcement details after the success toast message using "Intersect"
    Then HS I verify the dismiss button is displaying in the announcement details
    Then HS I click the dismiss button
    Then HS I verify the announcement is not displaying after clicking dismiss button
    And HS I successfully sign out

  @MATCH-4144
  Scenario Outline: As a Intersect HE, HS (non-naviance) user (any role),
  I want the ability to view any in-product notifications associated with me and as published by Hobsons Intersect support admins and super admins,
  So that I'm able to view the content they've intended for me to see within the Intersect product.
#verify announcement details in HS-NonNaviance
    Given SP I am logged in to the Admin page as an Admin user
    And SP I un-publish all the published announcements
#create announcement with title and with more than 140 characters
    When SP I add a new product announcement with title "Intersect" content "ThisisgoingtobeMorethan140characterslimit.ThisisgoingtobeMorethan140characterslimit.ThisisgoingtobeMorethan140characterslimit.ThisisgoingtobeMorethan140characterslimit." audience "HS - Non Naviance" and status "Published"
    Then SP I verify the toast with the message "New announcement added" is displayed

    Given HS I am logged in to Intersect HS as user type "admin"
    Then HS I verify the announcement title "Intersect" in the announcement details
    Then HS I verify the 'Read More' button is displaying for the content with more than 140 character limit
#create Visit
    Then HS I set the date using "<StartDate>" and "<EndDate>"
    Then HS I add the new time slot with "<Day>","<StartTime>","<EndTime>" and "<NumVisits>" with "<option>"
    Then HS I set the RepVisits Visits Confirmations option to "<Option>"
    Then HS I set the Prevent colleges scheduling new visits option of RepVisits Visit Scheduling to "1"
    Then HS I set the Prevent colleges cancelling or rescheduling option of RepVisits Visit Scheduling to "1"
    And HS I set the Accept option of RepVisits Visit Scheduling to "visits until I am fully booked."
#verify announcement details with success message
    Then HS I verify the announcement details after the success toast message using "Intersect"
    Then HS I verify the dismiss button is displaying in the announcement details
    Then HS I click the dismiss button
    Then HS I verify the announcement is not displaying after clicking dismiss button
    And HS I successfully sign out

#verify announcement details in HE
    Given SP I am logged in to the Admin page as an Admin user
    And SP I un-publish all the published announcements
#create announcement with title and with more than 140 characters
    When SP I add a new product announcement with title "Intersect" content "ThisisgoingtobeMorethan140characterslimit.ThisisgoingtobeMorethan140characterslimit.ThisisgoingtobeMorethan140characterslimit.ThisisgoingtobeMorethan140characterslimit." audience "HE" and status "Published"
    Then SP I verify the toast with the message "New announcement added" is displayed

    Then HE I am logged in to Intersect HE as user type "administrator"
    Then HE I verify the announcement title "Intersect" in the announcement details
    Then HE I verify the 'Read More' button is displaying for the content with more than 140 character limit
    And HE I search for "<Non-NavSchool>" in RepVisits page
    Then HE I select Visits to schedule the appointment for "<Non-NavSchool>" using "<Date>" and "<heStartTime>"
    And HE I verify the schedule pop_up for "<Non-NavSchool>" using "<heTime>" and "<hsEndTime>"
    Then HE I verify the announcement details after the success toast message using "Intersect"
    Then HE I verify the dismiss button is displaying in the announcement details
    Then HE I click the dismiss button
    Then HE I verify the announcement is not displaying after clicking dismiss button


    Examples:
      |StartTime|EndTime |NumVisits|Option                            |hsEndTime |heStartTime   |heTime   |Day|Date|StartDate|EndDate|option|Non-NavSchool |
      |11:34am  |12:59pm |3        |Yes, accept all incoming requests.|12:59pm   |11:34am       |11:34am  |14 |14  |14       |35     |1     |Homeconnection|
