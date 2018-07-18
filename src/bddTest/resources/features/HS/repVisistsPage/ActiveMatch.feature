@HS
Feature:  As an HS user, I want to be able to access the features of the Active Match features.


  @MATCH-3257 @MATCH-3603
Scenario Outline: As a HS admin user,I want the ability to specify my school's "regular weekly hours"
for the upcoming school year (e.g. 2018-2019), so that I can begin allowing reps to start
scheduling visits accordingly for the new school year.
Given HS I am logged in to Intersect HS through Naviance with user type "navianceAdmin"
Then HS I verify that availability dates are from "<StartDate>" to "<EndDate>" for visits the days "<days>" in the calendar

Examples:
| StartDate      | EndDate    | days |
| April 2018     | July 2019  |   1  |
| April 2018     | July 2019  |   14 |
| April 2018     | July 2019  |   30 |