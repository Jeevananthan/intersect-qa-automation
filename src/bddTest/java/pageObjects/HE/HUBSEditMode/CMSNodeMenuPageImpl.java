package pageObjects.HE.HUBSEditMode;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import pageObjects.COMMON.PageObjectFacadeImpl;

public class CMSNodeMenuPageImpl extends PageObjectFacadeImpl {

    Logger logger = null;

    public CMSNodeMenuPageImpl() {
        logger = Logger.getLogger(CMSNodeMenuPageImpl.class);
    }

    public void clickModerateButton() {
        moderateButton().click();
        waitUntilPageFinishLoading();
    }

    public void selectOptionPublishDropdown(String option) {
        Select dropdown = new Select(publishDropdown());
        dropdown.selectByVisibleText(option);
    }

    public void clickApplyButton() {
        applyButton().click();
        waitUntilPageFinishLoading();
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
