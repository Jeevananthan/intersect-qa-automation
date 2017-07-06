package stepDefinitions.CM;

import cucumber.api.java8.En;
import pageObjects.CM.userInstitutionPage.UserInstitutionPageImpl;

/**
 * Created by bojan on 6/1/17.
 */
public class UserInstitutionPageStepDefs implements En {

    public UserInstitutionPageStepDefs() {


        UserInstitutionPageImpl institutionPage = new UserInstitutionPageImpl();

        And("^I go to institution page$", institutionPage::goToUserInstitutionPage);
        And("^I create new institution post$", institutionPage::createNewInstitutionPost);
        Then("^I check if institution post is created$", institutionPage::checkIfInstitutionPostIsCreated);
        And("^I go to Hobsons institution page$", institutionPage::goToHobsonsInstitutionPage);
        Then("^I check if institution banner exists$", institutionPage::checkIfInstituionBannerExists);
        Then("^I check if Institution posts are visible$", institutionPage::checkIfInstitutionPostsAreVisible);
        Then("^I check if Institution staff members are visible and I can connect with them$", institutionPage::checkIfStaffMembersExistsAndCanConenct);
        Then("^I check if I am following Hobsons instituion$", institutionPage::checkIfHobsonsInstituionIsDisplayed);
        Then("^I check if I can unfollow the Hobsons institution$", institutionPage::makeSureICannotUnfollowHobsons);


    }


}
