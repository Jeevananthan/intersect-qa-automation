package stepDefinitions.HE.HUBSEditMode;

import cucumber.api.DataTable;
import cucumber.api.java8.En;
import pageObjects.HE.HUBSEditMode.*;

import java.util.HashMap;
import java.util.List;

public class HUBSLoginStepDefs implements En{

    public HUBSLoginStepDefs() {

        HUBSLoginPageImpl hubsLogin = new HUBSLoginPageImpl();
        FCMainPageImpl fcMain = new FCMainPageImpl();
        FCCollegesPageImpl collegesPage = new FCCollegesPageImpl();
        HUBSMainMenuPageImpl hubsMainMenu = new HUBSMainMenuPageImpl();
        StudiesPageImpl studies = new StudiesPageImpl();

        Then("^HUBS I should be able to verify the changes published in HUBS, with the following credentials:$", (DataTable stringsDataTable) -> {
            List<String> creds = stringsDataTable.asList(String.class);
            hubsLogin.defaultLogIn(creds);
            fcMain.clickCollegesTab();
            collegesPage.enterSearchString(creds.get(2));
            collegesPage.clickGoButton();
            collegesPage.clickSingleResult();
            hubsMainMenu.clickStudiesTab();
            studies.verifyGeneratedValues(creds.get(3), StudiesPageImpl.generatedValues);
        });
    }

}
