package pageObjects.HUBS;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
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
        cmsLogin.defaultLogIn(details);
        workflowOverviewButton().click();
        userEmailTextBox().sendKeys(userMail);
        waitUntilPageFinishLoading();
        List<String> rowsTitles = new ArrayList<>();
        for (WebElement rowTitle : workflowRowsTitles()) {
            rowsTitles.add(rowTitle.getText());
        }
        Set<String> uniqueRowTitles = new HashSet<>(rowsTitles);
        int numberOfRows = uniqueRowTitles.size();
        waitUntilPageFinishLoading();
        userEmailTextBox().clear();
        for (int i = 0; i < numberOfRows; i++) {
            userEmailTextBox().sendKeys(userMail);
            waitUntilPageFinishLoading();
            WebElement workFlowRow = getDriver().findElements(By.cssSelector("table.views-table.sticky-enabled tbody tr")).get(workflowRowsTitles().size() - 1);
            WebElement institutionCell = workFlowRow.findElement(By.cssSelector("td.views-field.views-field-institution a"));
            if (institutionCell.getText().equals(details.get(2))) {
                workFlowRow.findElement(By.cssSelector("td.views-field.views-field-approve-action a")).click();
                approveButton().click();
                waitUntil(ExpectedConditions.elementToBeClickable(confirmationMessage()));
                workflowOverviewButton().click();
            }
        }

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
    private WebElement workflowOverviewButton() { return getDriver().findElement(By.xpath("//div[@id='admin-menu-wrapper']/ul[2]/li[1]")); }
    private List<WebElement> workflowRowsTitles() { return getDriver().findElements(By.cssSelector("table.views-table.sticky-enabled tbody tr td.views-field-title a")); }
    private WebElement approveButton() { return getDriver().findElement(By.cssSelector("input#edit-submit")); }
    private WebElement confirmationMessage() { return getDriver().findElement(By.cssSelector("div.messages.status")); }
    private WebElement userEmailTextBox() { return getDriver().findElement(By.cssSelector("input#edit-api-user-email")); }
}
