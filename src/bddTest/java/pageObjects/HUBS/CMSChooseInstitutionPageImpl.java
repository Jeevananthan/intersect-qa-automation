package pageObjects.HUBS;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.ArrayList;

public class CMSChooseInstitutionPageImpl extends PageObjectFacadeImpl {

    Logger logger = null;

    public CMSChooseInstitutionPageImpl() {
        logger = Logger.getLogger(CMSChooseInstitutionPageImpl.class);
    }

    public void enterSearchStringInTitle(String searchString) {
        titleTextbox().sendKeys(searchString);
    }

    public void clickApplyButton() {
        applyButton().click();
        waitUntilPageFinishLoading();
    }

    public void clickSingleResult() {
        waitUntil(ExpectedConditions.elementToBeClickable(By.xpath("//tr[contains(@class, 'odd views-row-first')]/td[1]/a")));
        waitUntil(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//tr[contains(@class, 'odd views-row-first')]/td[2]/a")));
        singleResult().click();
        ArrayList<String> tabs = new ArrayList<String> (getDriver().getWindowHandles());
        getDriver().switchTo().window(tabs.get(tabs.size() - 1));
    }

    public void searchInstitution(String institutionName) {
        enterSearchStringInTitle(institutionName);
        clickApplyButton();
    }

    //Locators

    private WebElement titleTextbox() {
        return (textbox("title"));
    }
    private WebElement applyButton() {
        return getDriver().findElement(By.xpath("//input[@id='edit-submit-institution-overview']"));
    }
    private WebElement singleResult() {
        return getDriver().findElement(By.xpath("//tr[contains(@class, 'odd views-row-first')]/td[1]/a"));
    }
}
