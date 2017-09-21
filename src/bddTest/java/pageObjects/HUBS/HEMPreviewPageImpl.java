package pageObjects.HUBS;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;

public class HEMPreviewPageImpl extends PageObjectFacadeImpl {

    Logger logger = null;

    public HEMPreviewPageImpl() {
        logger = Logger.getLogger(HEMPreviewPageImpl.class);
    }

    public void clickMenuButton(String buttonLabel) {
        waitUntil(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), \"Studies\")]")));
        WebElement button = null;
        switch (buttonLabel) {
            case "Studies" : button = studiesButton();
            break;
            case "Student Life" : button = studentLifeButton();
            break;
            case "Overview" : button = overviewButton();
            break;
        }
        waitUntil(ExpectedConditions.elementToBeClickable(button));
        button.click();
        waitUntilPageFinishLoading();
        logger.info(buttonLabel + " button clicked");
    }

    //Locators
    private WebElement studiesButton() {
        return getDriver().findElement(By.xpath("//span[contains(text(), \"Studies\")]"));
    }
    private WebElement studentLifeButton() {
        return getDriver().findElement(By.xpath("//span[contains(text(), \"Student Life\")]"));
    }
    private WebElement overviewButton() {
        return getDriver().findElement(By.xpath("//span[contains(text(), \"Overview\")]"));
    }
}
