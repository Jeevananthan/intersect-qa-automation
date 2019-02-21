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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        waitForUITransition();
        cmsLogin.defaultLogIn(details);
        workflowOverviewButton().click();
        userEmailTextBox().clear();
        userEmailTextBox().sendKeys(userMail);
        schoolNameField().clear();
        schoolNameField().sendKeys(details.get(2));
        submitButton().click();
        waitUntilPageFinishLoading();
        int numberOfRows = workflowRows().size();
        waitUntilPageFinishLoading();
        waitForUITransition();
        for (int i = 0; i < numberOfRows; i++) {
            userEmailTextBox().clear();
            userEmailTextBox().sendKeys(userMail);
            schoolNameField().clear();
            schoolNameField().sendKeys(details.get(2));
            submitButton().click();
            waitUntilPageFinishLoading();
            String originalHandle = driver.getWindowHandle();
            actionsLink(details.get(2), "Approve").click();
            for (String handle : driver.getWindowHandles()) {
                driver.switchTo().window(handle);
            }
            waitUntilElementExists(approveButton());
            approveButton().click();
            waitUntil(ExpectedConditions.elementToBeClickable(confirmationMessage()));
            navigation.closeNewTabAndSwitchToOriginal(originalHandle);
            workflowOverviewButton().click();
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

    public void clickLogout() {
        logOutButton().click();
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
    private WebElement workflowOverviewButton() { return getDriver().findElement(By.xpath("//div[@id='admin-menu-wrapper']/ul[2]/li[1]")); }
    private List<WebElement> workflowRows() { return getDriver().findElements(By.cssSelector("table.sticky-enabled.tableheader-processed.sticky-table tbody tr")); }
    private WebElement approveButton() { return getDriver().findElement(By.cssSelector("input#edit-submit")); }
    private WebElement confirmationMessage() { return getDriver().findElement(By.cssSelector("div.messages.status")); }
    private WebElement userEmailTextBox() { return getDriver().findElement(By.cssSelector("input#edit-apiuseremail")); }
    private WebElement nextPageButton() {
        return getDriver().findElement(By.cssSelector("a[title=\"Go to next page\"]"));
    }
    private WebElement submitButton() { return driver.findElement(By.cssSelector("input#edit-submit")); }
    private WebElement logOutButton() { return driver.findElement(By.xpath("//a[text()='Log out']")); }
    private WebElement schoolNameField() { return driver.findElement(By.cssSelector("input#edit-school-name")); }
    private WebElement actionsLink(String collegeName, String action) { return driver.findElement(By.xpath("//tr/td[2]/a[text() = '" + collegeName + "']/../../td[9]/a[text() = '" + action + "']")); }
}
