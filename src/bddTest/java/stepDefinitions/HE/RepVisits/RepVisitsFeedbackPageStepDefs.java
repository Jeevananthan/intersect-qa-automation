package stepDefinitions.HE.RepVisits;

import cucumber.api.java8.En;
import pageObjects.HE.repVisitsPage.RepVisitsFeedbackPageImpl;

public class RepVisitsFeedbackPageStepDefs implements En {

    RepVisitsFeedbackPageImpl repVisitsFeedback = new RepVisitsFeedbackPageImpl();

    public RepVisitsFeedbackPageStepDefs() {

        And("^HE I verify the Visit Feedback heading$", repVisitsFeedback::verifyVisitFeedbackHeading);

        And("^HE I verify staff are listed down the left hand side of the page in ABC order by last name$", repVisitsFeedback::verifyStaffMembersAreDisplayedInAscendingOrderByLastName);

        And("^HE I verify that staff listed down on the left hand side of the page display a Community avatar to the left of their name$", repVisitsFeedback::verifyCommunityAvatarIsDisplayedNextToStaffMemberName);

        And("^HE I verify that staff members with no ratings submitted on them for the current school do not display the average rating and star icon to the right of their name$", repVisitsFeedback::verifyIndividualAvgRatingIsNotDisplayedIfStaffMemberHasNoRatings);

        And("^HE I verify that staff members with one or more ratings submitted on them for the current school year display an average rating to the right of their name and a star icon to the right of the average rating$", repVisitsFeedback::verifyIndividualAvgRatingIsDisplayedIfStaffMemberHasRatings);

        And("^HE I verify that average of all ratings submitted for the HE account for the current school year is displayed as a statistic on Overview area$", repVisitsFeedback::verifyIfAvgOfAllStaffMemberRatingsIsDisplayedInOverviewArea);

        And("^HE I verify that number of active staff members for the HE account is displayed as a statistic on Overview area$", repVisitsFeedback::verifyTotalActiveStaffMembersIsDisplayedInOverviewArea);

        And("^HE I verify that total number of ratings submitted for the HE account for the current school year is displayed as a statistic on Overview area$", repVisitsFeedback::verifyTotalNumberOfRatingsIsDisplayedInOverviewArea);

        And("HE I verify that number of comments submitted for the HE account for the current school year is displayed as a statistic on Overview area", repVisitsFeedback::verifyTotalNumberOfCommentsIsDisplayedInOverviewArea);

        And("^HE I verify the Feedback Breakdown for the HE account on Overview area$", repVisitsFeedback::verifyFeedbackBreakdownInOverviewArea);

        And("^HE I verify Top Areas To Improve Percentage Breakdown for the HE account on Overview area$", repVisitsFeedback::verifyTopAreasToImproveBreakdownInOverviewArea);

        Then("^HE I verify HS user's name be an active hyperlink to the HE user's Community profile in visit feedback subtab$", repVisitsFeedback::verifyHSUsersNameLink);

        Then("^HE I select \"([^\"]*)\" from the RepVisits Feedback user list$", repVisitsFeedback::selectFeedbackUser);

        And("^HE I verify the format of the user feedback page for user \"([^\"]*)\"$", repVisitsFeedback::verifyUserFeedbackPage);
    }
}
