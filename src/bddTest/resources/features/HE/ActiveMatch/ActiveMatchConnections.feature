@HE @ActiveMatch
Feature: HE - ActiveMatch - ActiveMatchConnections - As an HE Admin user with access, I can view and download my connections

@MATCH-3016
Scenario:  As an HE intersect user, I need the ability to view my student connections from AM so I can see who has made a connection
Given HE I am logged in to Intersect HE as user type "administrator"
And HE I open the Active Match section
And HE I verify the ActiveMatch page
Then HE I verify the Headers are present in the Active Match Connections
|Name|Gender / Ethnicity|Birth Date|Grad Year / GPA|School Name / CEEB|Contact|Address|Created / Modified|
And HE I verify the following details are present under the new header of "Historical" in the ActiveMatch export connections dropdown Menu
|Last 7 days|Last 14 days|Last 30 days|Last 60 days|Last 90 days|
And HE I verify the following details are present under the header of "By School Year" in the ActiveMatch export connections dropdown Menu
|2017-2018|2016-2017|2015-2016|2014-2015|
And HE I verify the following headers are present in the ActiveMatch export connections dropdown Menu in the following order
|Since Last Export|Historical|By School Year|
And HE I verify the Default drop-down Menu selection to remain "Since Last Export" after all connections are modified
|Last 7 days|Last 14 days|Last 30 days|Last 60 days|Last 90 days|
And HE I successfully sign out


@MATCH-3012 @ignore
Scenario: As an HE intersect user, I need the ability to export my student connections from AM so I can import them
into my own system.
Given HE I am logged in to Intersect HE as user type "administrator"
When HE I navigate to the ActiveMatch Tab
Then HE I verify the following details are present under the new header of "Historical" in the ActiveMatch export connections dropdown Menu
|Last 7 days|Last 14 days|Last 30 days|Last 60 days|Last 90 days|
And HE I verify the following details are present under the new header of "By School Year" in the ActiveMatch export connections dropdown Menu
|2017-2018|2016-2017|2015-2016|2014-2015|
And HE I verify the following details are present under the new header of "Since Last Export" in the ActiveMatch export connections dropdown Menu
|connections since last export on|
When HE I export the ActiveMatchConnections for the current year
Then HE I verify the downloaded ActiveMatch Cvs file "student-connections.csv" contains the following headers
|First Name|Last Name|Email|Phone|Address|City|State|Zip|Ethnicity|Gender|BirthDate|SchoolName|Ceeb|Graduation Year|Gpa|Created/Modified Date|Connection Status|
Then HE I delete the downloaded ActiveMatch Cvs file "student-connections.csv"
Then HE I successfully sign out
