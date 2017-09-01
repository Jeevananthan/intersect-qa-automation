package stepDefinitions.CM;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.CM.homePage.HomePageImpl;

public class HomePageStepDefs implements En {

    public HomePageStepDefs() {

        HomePageImpl homePage = new HomePageImpl();

        Then("^CM I verify that the upgrade widget is \"([^\"]*)\" for \"([^\"]*)\" users$", homePage::verifyUpgradeWidget);

        Then("^I am able to successfully login$", homePage::verifyUserIsLoggedIn);

        And("^I successfully sign out from the HE app$", homePage::logoutHE);

        And("^I successfully sign out from the Support app$", homePage::logoutSupport);

        And("^I go to Counselor Community page$", homePage::accessCounselorCommunityPage);

        And("^I go to HS Counselor Community page$", homePage::accessHSCounselorCommunityPage);

        And("^I sign out from the HE app$", homePage::logoutHEDefault);

        And("^I sign out from the HS app$", homePage::logoutHSDefault);

        And("^I sign out from the Support app$", homePage::logoutSupport);

        Then("^I check if Home posts are visible$", homePage::checkIfHomePostsAreVisible);

        Then("^I go to the home page$", homePage::goToHomePage);

        And("^I check if Hobsons institution post is visible$", homePage::checkIfHobsonsPostIsVisible);
        Then("^I like the Hobsons post$", homePage::makeSureHobsonsPostIsLiked);
        And("^I check if the Hobsons post is liked$", homePage::assertHobsonsPostLiked);
        Then("^I unlike the Hobsons post$", homePage::makeSureHobsonsPostNotLiked);
        And("^I check if Hobsons post is unliked$", homePage::assertHobsonsPostNotLiked);
        Then("^I open home tab$", homePage::clickOnHomeTab);
        Then("^I write a comment with text \"([^\"]*)\" on the Hobsons post$", homePage::writeCommentOnHobsonsPost);
        And("^I check if comment \"([^\"]*)\" is posted on the Hobsons post$", homePage::checkIfHobsonsPostCommentIsPosted);

    }
}
