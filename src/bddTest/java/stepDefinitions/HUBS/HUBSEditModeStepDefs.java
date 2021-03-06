package stepDefinitions.HUBS;

import cucumber.api.java8.En;
import pageObjects.HUBS.HEMPreviewPageImpl;
import pageObjects.HUBS.NavianceCollegeProfilePageImpl;
import pageObjects.HUBS.StudiesPageImpl;
import pageObjects.SP.communityPages.InstitutionPageImpl;

public class HUBSEditModeStepDefs implements En{

    public HUBSEditModeStepDefs() {
        NavianceCollegeProfilePageImpl navianceCollegeProfilePage = new NavianceCollegeProfilePageImpl();
        HEMPreviewPageImpl hemPreviewPage = new HEMPreviewPageImpl();
        StudiesPageImpl studiesPage = new StudiesPageImpl();

        Then("^HUBS I access HUBS Edit Mode$", navianceCollegeProfilePage::openHUBSEditorMode);

        And("^HUBS I open the \"([^\"]*)\" tab in the preview$", hemPreviewPage::clickMenuButton);

        Then("^HUBS All the elements of the studies tab should be displayed$", studiesPage::verifyAllElementsDisplayed);

        And("^HE I access the INSTITUTION page$",navianceCollegeProfilePage::navigateToInstitutionProfile);

        Then("^HUBS I verify that HEM loads$", navianceCollegeProfilePage::verifyHEMLoads);

    }

}
