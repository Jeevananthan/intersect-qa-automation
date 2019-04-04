package stepDefinitions.CM;

import cucumber.api.java8.En;
import pageObjects.CM.userProfilePage.UserProfilePageImpl;

/**
 * Created by bojan on 6/1/17.
 */
public class UserProfilePageStepDefs implements En {

    public UserProfilePageStepDefs() {


        UserProfilePageImpl userProfile = new UserProfilePageImpl();

        Then("^I go to user profile page$", userProfile::goToUserProfilePage);
        Then("^I check if user post is created with text \"([^\"]*)\"$", userProfile::checkIfUserPostIsCreated);
        And("^I check if Profile posts are visible$", userProfile::checkIfProfilePostsAreVisible);
        Then("^I like my own post$", userProfile::makeSureMyOwnPostIsLiked);
        And("^I check if the my own post is liked$", userProfile::assertMyOwnPostLiked);
        Then("^I unlike the my own post$", userProfile::makeSureMyOwnPostIsNotLiked);
        And("^I check if my own post is unliked$", userProfile::assertMyOwnPostNotLiked);
        And("^I click on Edit profile button$", userProfile::clickOnEditProfileButton);
        Then("^I select \"([^\"]*)\" state$", userProfile::selectState01);
        Then("^I check if \"([^\"]*)\" state is saved$", userProfile::checkIfStateSaved);
        Then("^I fill in \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\" fields$", userProfile::editProfileInfo);
        And("^I check if user info is saved with \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$", userProfile::checkProfileInfoSaved);
        Then("^I check if office phone and job title are required fields$", userProfile::checkRequiredFields);
        And("^I add first state \"([^\"]*)\" with counties \"([^\"]*)\" and \"([^\"]*)\"$", userProfile::selectFirstStateWithCounties);
        And("^I add second state \"([^\"]*)\" with counties \"([^\"]*)\" and \"([^\"]*)\"$", userProfile::selectSecondStateWithCounties);
        And("^I check if first state \"([^\"]*)\" is saved with counties \"([^\"]*)\" and \"([^\"]*)\"$", userProfile::checkFirstStateWithCounties);
        And("^I check if second state \"([^\"]*)\" is saved with counties \"([^\"]*)\" and \"([^\"]*)\"$", userProfile::checkSecondStateWithCounties);
        Then("^I like post \"([^\"]*)\"$", userProfile::likePost);
        And("^I open Notifications list$", userProfile::openNotifications);
        And("^I open Notifications Support list$", userProfile::openNotificationsSupport);
        And("^I check if user has new notification for post like$", userProfile::checkNewNotificationRaised);
        Then("^I unlike post \"([^\"]*)\"$", userProfile::unlikePost);
        And("^I check if new notification for post like is not raised$", userProfile::checkNewNotificationNotRaised);
        And("^I check if my user profile page is opened$", userProfile::checkIfMyProfilePageIsOpened);
        And("^I check if user has new notification that my request to join private group is approved$", userProfile::checkNewGroupApprovementNotificationRaised);
        And("^I set work email privacy to 'visible to all users', office phone privacy to 'connections only' and personal email privacy to 'visible to only me'$", userProfile::setPrivacyForFields);
        Then("^I check if I see fields with privacy set to 'visible to all users'$", userProfile::checkPublicFieldsVisibility);
        Then("^I open my profile tab$", userProfile::clickProfileTab);
        Then("^I check if I see fields with privacy set to 'connections only'$", userProfile::checkConnectionsOnlyFieldsVisibility);
        Then("^I check if I see fields with privacy set to 'visible to only me'$", userProfile::checkVisibleToOnlyMeFieldsVisibility);
        And("^I create new user post with text \"([^\"]*)\"$", userProfile::createNewUserPost);
        Then("^I write a comment with text \"([^\"]*)\" on the post \"([^\"]*)\"$", userProfile::writeCommentOnProfilePost);
        And("^I check if comment \"([^\"]*)\" is posted$", userProfile::checkIfCommentIsPosted);
        And("^I create new user post with text \"([^\"]*)\" and hyperlink \"([^\"]*)\"$", userProfile::createNewRichContentPostWithHyperlink);
        Then("^I check if user post is created with text \"([^\"]*)\" and hyperlink \"([^\"]*)\"$", userProfile::checkIfRichContentPostWithHyperlinkIsCreated);
        Then("^I check if user post is created with text \"([^\"]*)\" and embedded video$", userProfile::checkIfRichContentPostWithVideoIsCreated);
        And("^I clear all the notifications$", userProfile::clearAllNotifications);
        And("^I clear all the notifications from support$", userProfile::clearAllNotificationsSupport);
        Then("^I check if user has new notification$", userProfile::checkNewNotificationRaised);
        Then("^I edit post with text \"([^\"]*)\"$", userProfile::editCreatedPost);
        And("^I check if the post is created with text \"([^\"]*)\"$", userProfile::checkIfUserPostIsCreatedNoDelete);
        Then("^I delete created post$", userProfile::deleteCreatedPost);
        And("^I check if post with text \"([^\"]*)\" is deleted$", userProfile::checkIfUserPostIsDeleted);
        And("^I set all fields permissions to \"([^\"]*)\"$", userProfile::setPermissionsToAllFields);
        And("^I check if permissions settings are saved to \"([^\"]*)\"$", userProfile::checkPermissionsSettingsSavedForAllFields);
        And("^I check if there are no privacy settings for following fields first name, last name, institution name, bio, headline, territory served, and job title$", userProfile::checkNoPrivacySettingsForFields);
        When("^I go to HS user profile page$", userProfile::goToHSUserProfilePage);
        Then("^I check if office phone is required field$", userProfile::checkOfficePhoneRequiredField);
        Then("^I click on comment icon$", userProfile::clickCommentIcon);
        And("^I check if I see hyperlink and image icons/actions when replying to an already created post$", userProfile::checkIfHyperlinkAndImageIconsVisibleForPostReply);
        Then("^I check if I see hyperlink and image icons/actions when user is creating a new post$", userProfile::checkIfHyperlinkAndImageIconsVisibleForNewUserPost);
        Then("^I check if I see a comment icon/action below reply \"([^\"]*)\"$", userProfile::checkIfSeeCommentIconBelowAnyReply);
        And("^I add comment to the post \"([^\"]*)\" and reply \"([^\"]*)\" with text \"([^\"]*)\"$", userProfile::addCommentToThePostReply);
        And("^I click on new comment icon to the post \"([^\"]*)\" and reply \"([^\"]*)\"$", userProfile::clickOnCommentIconToTheReply);
        Then("^click on the Post button$", userProfile::clickOnThePostBtn);
        And("^I check if I can see \"([^\"]*)\" as a placeholder$", userProfile::checkPlaceholderVisible);
        And("^I click on delete button$", userProfile::clickOnDeleteButton);
        And("^I click cancel deletion button$", userProfile::clickCancelButton);
        And("^I click on Inactivate Account link$", userProfile::clickOnInactivateAccount);

    }

}
