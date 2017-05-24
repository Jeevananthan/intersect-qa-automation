package stepDefinitions.HE;

import cucumber.api.java8.En;
import pageObjects.HE.HUBSEditMode.HEMPreviewPageImpl;
import pageObjects.HE.HUBSEditMode.NavianceCollegeProfilePageImpl;
import pageObjects.HE.HUBSEditMode.StudiesPageImpl;

public class HUBSEditModeStepDefs implements En{

    public HUBSEditModeStepDefs() {
        NavianceCollegeProfilePageImpl navianceCollegeProfilePage = new NavianceCollegeProfilePageImpl();

        Then("^HUBS I access HUBS Edit Mode$", navianceCollegeProfilePage::openHUBSEditorMode);
    }

}
