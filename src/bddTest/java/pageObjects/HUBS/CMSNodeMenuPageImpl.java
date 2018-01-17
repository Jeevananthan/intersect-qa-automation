package pageObjects.HUBS;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

    public void approveChangesInCMS(String userMail, DataTable CMSDetails) {
        List<String> details = CMSDetails.asList(String.class);
        cmsLogin.defaultLogIn(details);
        workflowOverviewButton().click();
        userEmailTextBox().sendKeys(userMail);
        waitUntilPageFinishLoading();
        int numberOfRows = workflowRows().size();
        waitUntilPageFinishLoading();
        waitUntilPageFinishLoading();
        waitForUITransition();
        userEmailTextBox().clear();
        for (int i = 0; i < numberOfRows; i++) {
            userEmailTextBox().sendKeys(userMail);
            waitUntilPageFinishLoading();
            WebElement workFlowRow = getDriver().findElements(By.cssSelector("table.views-table.sticky-enabled tbody tr")).get(workflowRows().size() - 1);
            WebElement institutionCell = workFlowRow.findElement(By.cssSelector("td.views-field.views-field-institution"));
            if (institutionCell.getText().equals(details.get(2))) {
                workFlowRow.findElement(By.cssSelector("td.views-field.views-field-approve-action a")).click();
                approveButton().click();
                waitUntil(ExpectedConditions.elementToBeClickable(confirmationMessage()));
                workflowOverviewButton().click();
            }
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
    private WebElement workflowOverviewButton() { return getDriver().findElement(By.xpath("//a[text()='Workflow']")); }
    private List<WebElement> workflowRows() { return getDriver().findElements(By.cssSelector("table.views-table.sticky-enabled tbody tr")); }
    private WebElement approveButton() { return getDriver().findElement(By.cssSelector("input#edit-submit")); }
    private WebElement confirmationMessage() { return getDriver().findElement(By.cssSelector("div.messages.status")); }
    private WebElement userEmailTextBox() {
        try{
            return getDriver().findElement(By.cssSelector("input#edit-apiuseremail"));
        } catch (NoSuchElementException e) {
            return getDriver().findElement(By.cssSelector("input#edit-api-user-email"));
        }
    }
}
