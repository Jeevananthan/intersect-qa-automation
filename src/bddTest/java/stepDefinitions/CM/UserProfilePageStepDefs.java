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
        And("^I create new user post$", userProfile::createNewUserPost);
        Then("^I check if user post is created$", userProfile::checkIfUserPostIsCreated);
        And("^I check if Profile posts are visible$", userProfile::checkIfProfilePostsAreVisible);

    }
}
