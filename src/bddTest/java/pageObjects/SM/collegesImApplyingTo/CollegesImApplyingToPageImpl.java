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

        //Locators

        private WebElement getCollegeDropDown() { return getDriver().findElement(By.xpath("//div/input")); }

    }

