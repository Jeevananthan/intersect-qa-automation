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
        And("^SP I un-publish all the published announcements$",productAnnouncements::unpublishAllAnnouncements);
        Then("^SP I verify the product announcement with title \"([^\"]*)\" content \"([^\"]*)\" audience \"([^\"]*)\" and status \"([^\"]*)\" in the the edit form",productAnnouncements::verifyProductAnnouncementInEditMode );
        Then("^SP I verify that Admin dashboard is displayed in Homepage$",productAnnouncements::verifyAdminDashboardIsDisplayed);
        Then("^SP I verify the header is changed from \"([^\"]*)\" to \"([^\"]*)\" in the Admin dashboard page$",productAnnouncements::verifyHeaderInAdminDashboard);
        Then("^SP I verify \"([^\"]*)\" stub menu is displayed in the Admin dashboard page$",productAnnouncements::verifyProductAnnouncementsStubMenu);
        Then("^SP I verify that Admin dashboard is not displayed$",productAnnouncements::verifyAdminDashboardIsNotDisplayed);
        Then("^SP I verify \"([^\"]*)\" button for more than 25 notifications in the Product Announcements page$",productAnnouncements::verifyShowMoreButtonInProductAnnouncements);
    }
}
