@SP
Feature: Hobsons Staff - Access Admin page via Network Credentials
         As a Hobsons Staff user I want to access the Intersect admin page with my network credentials that
         already exist so I don't have to create a new set of login credentials.

  Scenario: As a Hobsons Sales Ops user I am able to login to the admin page with my Hobsons network username and password.
    Given SP I am logged in to the Admin page as a Sales Ops user
    Then SP I am able to successfully login
    And SP I successfully sign out

  Scenario: As a Hobsons Support user I am able to login to the admin page with my Hobsons network username and password.
    Given SP I am logged in to the Admin page as a Support user
    Then SP I am able to successfully login
    And SP I successfully sign out

  Scenario: As a Hobsons Support Admin user I am able to login to the admin page with my Hobsons network username and password.
    Given SP I am logged in to the Admin page as an Admin user
    Then SP I am able to successfully login
    And SP I successfully sign out

  Scenario: As a Hobsons Community user I am able to login to the admin page with my Hobsons network username and password.
    Given SP I am logged in to the Admin page as a Community user
    Then SP I am able to successfully login
    And SP I successfully sign out

  Scenario: As a Hobsons Community Manager user I am able to login to the admin page with my Hobsons network username and password.
    Given SP I am logged in to the Admin page as a Community Manager user
    Then SP I am able to successfully login
    And SP I successfully sign out

  Scenario: As a Hobsons staff user I don't have access to the admin page with my Hobsons network credentials if I am not assigned to a Matching Active Directory group
    Given SP I am logged in to the Admin page as a user with no Intersect access
    Then SP I see the following security message "You are not authorized to view the content on this page"
    And SP I successfully sign out

  Scenario: As a Hobsons staff user I am unable to login to the admin page with credentials that do not exist in Active Directory
    Given SP I want to login to the admin page using "nonexistinguser@hobsons.com" as username and "password" as password
    Then SP I am unable to login and I see the following error message "Your account or password is incorrect. If you don't remember your password, reset it now."

