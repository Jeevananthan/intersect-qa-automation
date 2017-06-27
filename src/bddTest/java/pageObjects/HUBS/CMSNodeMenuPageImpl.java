package pageObjects.HUBS;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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
        List<String> details = CMSDetails.asList(String.class);
        cmsLogin.defaultLogIn(details);
        chooseInstitution.searchInstitution(details.get(2));
        String originalWindow = navigation.getWindowHandle();
        if (details.get(4).contains(";")) {
            String[] sectionsToApprove = details.get(4).split(";");
            for (String section : sectionsToApprove) {
                approveChangesInSection(section, details.get(3), originalWindow);
            }
        } else {
            approveChangesInSection(details.get(4), details.get(3), originalWindow);
        }
        logger.info("Changes were approved in CMS");
    }

    private void approveChangesInSection(String section, String publishOption, String originalWindowHandle) {
        boolean dropdownPresenceChecker = true;
        chooseInstitution.clickSingleResult();
        switch (section) {
            case "Student Body" : institutionPage.openStudentBodyNode();
                break;
            case "Undergraduate Admissions" : institutionPage.openUndergradAdmissionsNode();
                break;
            case "Undergraduate Financial Aid" : institutionPage.openUndergradFinancialAidNode();
                break;
        }
        clickModerateButton();
        while (dropdownPresenceChecker) {
            try {
                selectOptionPublishDropdown(publishOption);
                dropdownPresenceChecker = false;
            } catch (NoSuchElementException e) {
                nextPageButton().click();
                waitUntilPageFinishLoading();
            }
        }
        applyButton().click();
        waitUntilPageFinishLoading();
        navigation.closeNewTabAndSwitchToOriginal(originalWindowHandle);
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
    private WebElement nextPageButton() {
        return getDriver().findElement(By.cssSelector("a[title=\"Go to next page\"]"));
    }
}
