package stepDefinitions.HE;

import cucumber.api.java8.En;
import pageObjects.HE.HUBSEditMode.HEMPreviewPageImpl;
import pageObjects.HE.HUBSEditMode.NavianceCollegeProfilePageImpl;
import pageObjects.HE.HUBSEditMode.StudiesPageImpl;

public class HUBSEditModeStepDefs implements En{

    public HUBSEditModeStepDefs() {
        NavianceCollegeProfilePageImpl navianceCollegeProfilePage = new NavianceCollegeProfilePageImpl();
        HEMPreviewPageImpl hemPreviewPage = new HEMPreviewPageImpl();
        StudiesPageImpl studiesPage = new StudiesPageImpl();

//        Then("^HUBS I access HUBS Edit Mode$", navianceCollegeProfilePage::openHUBSEditorMode);

       // And("^HUBS I open the \"([^\"]*)\" tab in the preview$", hemPreviewPage::clickMenuButton);

    //    Then("^HUBS All the elements of the studies tab should be displayed$", studiesPage::verifyAllElementsDisplayed);

    }

}
