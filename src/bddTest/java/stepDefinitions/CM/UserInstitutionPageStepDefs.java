package stepDefinitions.CM;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.CM.userInstitutionPage.UserInstitutionPageImpl;

/**
 * Created by bojan on 6/1/17.
 */
public class UserInstitutionPageStepDefs implements En {

    public UserInstitutionPageStepDefs() {


        UserInstitutionPageImpl institutionPage = new UserInstitutionPageImpl();

        And("^I go to institution page$", institutionPage::goToUserInstitutionPage);
        And("^I create new institution post with text \"([^\"]*)\"$", institutionPage::createNewInstitutionPost);
        Then("^I check if institution post is created with text \"([^\"]*)\"$", institutionPage::checkIfInstitutionPostIsCreated);
        And("^I go to Hobsons institution page$", institutionPage::goToHobsonsInstitutionPage);
        Then("^I check if institution banner exists$", institutionPage::checkIfInstituionBannerExists);
        Then("^I check if Institution posts are visible$", institutionPage::checkIfInstitutionPostsAreVisible);
        Then("^I check if Institution staff members are visible and I can connect with them$", institutionPage::checkIfStaffMembersExistsAndCanConenct);
        Then("^I check if I am following Hobsons instituion$", institutionPage::checkIfHobsonsInstituionIsDisplayed);
        Then("^I check if I can unfollow the Hobsons institution$", institutionPage::makeSureICannotUnfollowHobsons);
        And("^I check if my institution info is displayed$", institutionPage::checkMyInstitutionPageOpened);
        And("^I check if I am able to edit my institution info$", institutionPage::checkIfEditInstitutionBtnDisplayed);
        And("^I go to institution page with institution id \"([^\"]*)\"$", institutionPage::goToInstitutionPageById);
        And("^I check if correct error message is displayed$", institutionPage::checkIfCorrectErrorMessageDisplayed);
        Then("^I open institution tab$", institutionPage::clickInstitutionTab);
        And("^I am not following institution with id \"([^\"]*)\"$", institutionPage::notFollowingInstitution);
        And("^I am following institution with id \"([^\"]*)\"$", institutionPage::followingInstitution);
        And("^I check if follow institution button is visible$", institutionPage::checkFollowInstitutionBtnVisible);
        And("^I click on Follow institution button$", institutionPage::clickFollowInstitution);
        And("^I click on Unfollow institution button$", institutionPage::clickUnfollowInstitution);
        Then("^I check if institution with id \"([^\"]*)\" is in the list$", institutionPage::checkInstitutionListed);
        Then("^I unfollow institution with id \"([^\"]*)\"$", institutionPage::unfollowInstitutionById);
        And("^I check if institutions list is displayed$", institutionPage::checkInstitutionsListDisplayed);


    }


}
