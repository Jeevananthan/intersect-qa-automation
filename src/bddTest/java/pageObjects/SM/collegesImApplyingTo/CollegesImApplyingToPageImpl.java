package pageObjects.SM.collegesImApplyingTo;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;

public class CollegesImApplyingToPageImpl extends PageObjectFacadeImpl {

    public CollegesImApplyingToPageImpl() {
        logger = Logger.getLogger(CollegesImApplyingToPageImpl.class);
    }

    private Logger logger;

    public void addCollegeToImApplyingTo(String collegeName) {

        waitUntilPageFinishLoading();
        getCollegeDropDown().click();
        getCollegeDropDown().clear();
        getCollegeDropDown().sendKeys(collegeName);
        try {
            button(collegeName).click();
            button("Add Application").click();
        }
        catch (Exception e)
        {
            logger.info("The " + collegeName + " is already added");
        }
    }

    public void removeCollegeFromList(String college) {
        if (driver.findElements(By.xpath(collegeCheckboxLocator(college))).size() > 0) {
            collegeCheckbox(college).click();
            removeButton().click();
            dialogRemoveButton().click();
            waitUntil(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(confirmationBannerLocator), 0));
        }
    }

    //Locators

    private WebElement getCollegeDropDown() { return getDriver().findElement(By.xpath("//div/input")); }
    private WebElement collegeCheckbox(String college) { return driver.findElement(By.xpath(collegeCheckboxLocator(college))); }
    private String collegeCheckboxLocator(String college) {return "//a[text() = '" + college + "']/../../../td[1]//figure";}
    private WebElement removeButton() { return driver.findElement(By.xpath("//button[contains(text(), 'REMOVE')]")); }
    private WebElement dialogRemoveButton() { return driver.findElement(By.xpath("//button[text() = 'REMOVE']")); }
    private String confirmationBannerLocator = "//strong[text() = 'Confirmation']";
}

