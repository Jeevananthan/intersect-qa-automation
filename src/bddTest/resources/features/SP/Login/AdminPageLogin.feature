@SP
Feature: Hobsons Staff - Access Admin page via Network Credentials
  As a Hobsons Staff user I want to access the Purple admin page with my network credentials that
  already exist so I don't have to create a new set of login credentials.

  @SPTest
  Scenario: As a Hobsons Admin user I am able to login to the admin page with my Hobsons network username and password.
    Given SP I want to login to the admin page using "MatchSupportUIQA4@hobsons.com" as username and "Password!1" as password
    Then SP I am able to successfully login
    And SP I successfully sign out

  Scenario: As a Hobsons View Only user I am able to login to the admin page with my Hobsons network username and password.
    Given SP I want to login to the admin page using "MatchSupportUIQA1@hobsons.com" as username and "Password!1" as password
    Then SP I am able to successfully login
    And SP I successfully sign out

  Scenario: As a Hobsons Sales Ops user I am able to login to the admin page with my Hobsons network username and password.
    Given SP I want to login to the admin page using "MatchSupportUIQA2@hobsons.com" as username and "Password!1" as password
    Then SP I am able to successfully login
    And SP I successfully sign out

  Scenario: As a Hobsons QA user I am able to login to the admin page with my Hobsons network username and password.
    Given SP I want to login to the admin page using "MatchSupportUIQA3@hobsons.com" as username and "Password!1" as password
    Then SP I am able to successfully login
    And SP I successfully sign out

  Scenario: As a Hobsons staff user I am unable to login to the admin page with credentials that do not exist in Active Directory
    Given SP I want to login to the admin page using "nonexistinguser@hobsons.com" as username and "password" as password
    Then SP I am unable to login and I see the following error message "We don't recognize this user ID or password Be sure to type the password for your work or school account. Forgot your password?"

  Scenario: As a Hobsons staff user I don't have access to the admin page with my Hobsons network credentials if I am not assigned to a Matching Active Directory group
    Given SP I want to login to the admin page using "NoMatchSupportUIQA5@hobsons.com" as username and "Password!1" as password
    And SP I am able to successfully login
    Then SP I see the following security message "You are not authorized to view the content on this page"
    And SP I successfully sign out
