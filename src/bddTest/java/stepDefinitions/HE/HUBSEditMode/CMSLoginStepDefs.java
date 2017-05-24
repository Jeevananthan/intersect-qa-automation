package stepDefinitions.HE.HUBSEditMode;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.HE.HUBSEditMode.*;
import utilities.HUBSEditMode.Navigation;

import java.util.HashMap;
import java.util.List;

public class CMSLoginStepDefs implements En{

    private HashMap<String, String> originalValues = new HashMap<>();
    private HashMap<String, String> newValues = new HashMap<>();

    public CMSLoginStepDefs() {
        CMSLoginPageImpl cmsLogin = new CMSLoginPageImpl();
        CMSChooseInstitutionPageImpl chooseInstitution = new CMSChooseInstitutionPageImpl();
        CMSInstitutionPageImpl institutionPage = new CMSInstitutionPageImpl();
        CMSNodeMenuPageImpl nodeMenu = new CMSNodeMenuPageImpl();
        Navigation navigation = new Navigation();

        And("^HUBS I approve the changes in CMS for Studies with the following details:$", (DataTable stringsDataTable) -> {
            List<String> creds = stringsDataTable.asList(String.class);
            cmsLogin.defaultLogIn(creds);
            chooseInstitution.enterSearchStringInTitle(creds.get(2));
            chooseInstitution.clickApplyButton();
            String originalWindow = navigation.getWindowHandle();
            chooseInstitution.clickSingleResult();
            institutionPage.clickUndergradAdmissionsButton();
            institutionPage.clickUndergradNode();
            nodeMenu.clickModerateButton();
            nodeMenu.selectOptionPublishDropdown("Published");
            nodeMenu.clickApplyButton();
            navigation.closeCurrentTabAndSwitchToAnother(originalWindow);
            chooseInstitution.clickSingleResult();
            institutionPage.clickStudentbodyButton();
            institutionPage.clickStudentbodyNode();
            nodeMenu.clickModerateButton();
            nodeMenu.selectOptionPublishDropdown("Published");
            nodeMenu.clickApplyButton();
            navigation.closeCurrentTabAndSwitchToAnother(originalWindow);
        });
    }

}
