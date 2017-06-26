package pageObjects.HUBS;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import pageObjects.COMMON.PageObjectFacadeImpl;
import utilities.HUBSEditMode.Navigation;

import java.util.List;

public class CMSNodeMenuPageImpl extends PageObjectFacadeImpl {

    private Logger logger = null;
    private CMSLoginPageImpl cmsLogin = new CMSLoginPageImpl();
    private CMSChooseInstitutionPageImpl chooseInstitution = new CMSChooseInstitutionPageImpl();
    private Navigation navigation = new Navigation();
    private CMSInstitutionPageImpl institutionPage = new CMSInstitutionPageImpl();

    public CMSNodeMenuPageImpl() {
        logger = Logger.getLogger(CMSNodeMenuPageImpl.class);
    }

    private void clickModerateButton() {
        moderateButton().click();
        waitUntilPageFinishLoading();
    }

    private void selectOptionPublishDropdown(String option) {
        Select dropdown = new Select(publishDropdown());
        dropdown.selectByVisibleText(option);
    }

    public void approveChangesInCMS(DataTable CMSDetails) {
        List<String> creds = CMSDetails.asList(String.class);
        cmsLogin.defaultLogIn(creds);
        chooseInstitution.searchInstitution(creds.get(2));
        String originalWindow = navigation.getWindowHandle();
        chooseInstitution.clickSingleResult();
        institutionPage.openUndergradAdmissionsNode();
        clickModerateButton();
        selectOptionPublishDropdown(creds.get(3));
        applyButton().click();
        waitUntilPageFinishLoading();
        navigation.closeNewTabAndSwitchToOriginal(originalWindow);
        chooseInstitution.clickSingleResult();
        institutionPage.openStudentBodyNode();
        clickModerateButton();
        selectOptionPublishDropdown(creds.get(3));
        applyButton().click();
        waitUntilPageFinishLoading();
        navigation.closeNewTabAndSwitchToOriginal(originalWindow);
        logger.info("Changes were approved in CMS");
    }

    //Locators

    private WebElement moderateButton() {
        return button("Moderate");
    }
    private WebElement publishDropdown() {
        return getDriver().findElement(By.xpath("//select[@id='edit-state']"));
    }
    private WebElement applyButton() {
        return getDriver().findElement(By.id("edit-submit"));
    }
}
