package stepDefinitions.SP.AdminDashboard;

import cucumber.api.java8.En;
import pageObjects.SP.AdminDashboard.ProductAnnouncementsImpl;

public class ProductAnnouncementsStepDef implements En {
    public ProductAnnouncementsStepDef(){

        ProductAnnouncementsImpl productAnnouncements = new ProductAnnouncementsImpl();

        Given("^SP I add a new product announcement with title \"([^\"]*)\" content \"([^\"]*)\" audience \"([^\"]*)\" and status \"([^\"]*)\"$",productAnnouncements::addNewProductAnnouncement );
        Then("^SP I verify that field \"([^\"]*)\" has a tooltip that says \"([^\"]*)\"$",productAnnouncements::verifyErrorFieldTooltipForProductAnnouncementField );
        Then("^SP I verify the product announcement with title \"([^\"]*)\" content \"([^\"]*)\" visibility \"([^\"]*)\" date \"([^\"]*)\" user \"([^\"]*)\" and status \"([^\"]*)\" in the list$",productAnnouncements::verifyProductAnnouncementInList );
        Then("^SP I verify that the error message that says \"([^\"]*)\" \"([^\"]*)\" is displayed$",productAnnouncements::verifyProductAnnouncementErrorMessage );
        Then("^SP I verify the toast with the message \"([^\"]*)\" is displayed$",productAnnouncements::verifySaveUpdateConfirmationToast );
        Then("^SP I verify the label with text \"([^\"]*)\" is displayed$",productAnnouncements::verifyLabel);
        When("^SP I edit the product announcement \"([^\"]*)\" with title \"([^\"]*)\" content \"([^\"]*)\" audience \"([^\"]*)\" and status \"([^\"]*)\"$",productAnnouncements::editProductAnnouncement );
        Then("^SP I verify that Admin dashboard is not displayed$",productAnnouncements::verifyAdminDashboardIsNotDisplayed);
    }
}
