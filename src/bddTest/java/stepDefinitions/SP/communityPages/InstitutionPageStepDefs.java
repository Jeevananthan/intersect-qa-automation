package stepDefinitions.SP.communityPages;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.SP.communityPages.InstitutionPageImpl;

public class InstitutionPageStepDefs implements En {
    InstitutionPageImpl collProfile = new InstitutionPageImpl();

    public InstitutionPageStepDefs() {

        Then("^SP I verify Hubs view mode for \"([^\"]*)\"$", collProfile::goToHubsPage);
        And("^SP I un-publish all the published announcements$",collProfile::unpublishAllAnnouncements);
        Given("^SP I add a new product announcement with title \"([^\"]*)\" content \"([^\"]*)\" audience \"([^\"]*)\" and status \"([^\"]*)\"$",collProfile::addNewProductAnnouncement );
        Then("^SP I verify the toast with the message \"([^\"]*)\" is displayed$",collProfile::verifySaveUpdateConfirmationToast );
        Then("^SP I verify title \"([^\"]*)\" is displayed in the Product Announcements page$",collProfile::verifyTitleInProductAnnouncements);
        Then("^SP I verify \"([^\"]*)\" characters are displayed in the Product Announcements page$",collProfile::verifyCharCountsInProductAnnouncements);
        Then("^SP I verify the visibility for the following details \"([^\"]*)\" in the Product Announcements page using \"([^\"]*)\"$",collProfile::verifyVisibilityInProductAnnouncements);
        Then("^SP I verify the date format \"([^\"]*)\" in the Product Announcements page using \"([^\"]*)\" for the user \"([^\"]*)\"$",collProfile::verifyDateFormatInProductAnnouncements);
        Then("^SP I verify the status \"([^\"]*)\" is displayed in the Product Announcements page using \"([^\"]*)\"$",collProfile::verifyStatusInProductAnnouncements);
    }

}