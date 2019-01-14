package stepDefinitions.SM;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.SM.collegesLookingForStudentsLikeYou.CollegesLookingForStudentsLikeYouPageImpl;

/**
 * Created by mbhangu on 9/28/2018.
 */
public class CollegesLookingForStudentsLikeYouStepDefs implements En {
    public CollegesLookingForStudentsLikeYouStepDefs() {
        CollegesLookingForStudentsLikeYouPageImpl collegesLookingForStudentsLikeYouPage = new CollegesLookingForStudentsLikeYouPageImpl();

        And("^SM I add the college \"([^\"]*)\" to the I'm thinking about list using the heart icon in the match card$", collegesLookingForStudentsLikeYouPage::clickHeartIcon);

        Then("^SM I verify all the steps of the Visual Step Progress Indicator with the following data:$", collegesLookingForStudentsLikeYouPage::verifyVisualStepProgressIndicator);

        Then("^SM I click the button \"([^\"]*)\" in the connection dialog$", collegesLookingForStudentsLikeYouPage::clickButtonInConnectDialog);

        Then("^SM I verify a matching card is \"([^\"]*)\" for \"([^\"]*)\"$", collegesLookingForStudentsLikeYouPage::verifyIfMatchingCardIsDisplayed);

    }}
