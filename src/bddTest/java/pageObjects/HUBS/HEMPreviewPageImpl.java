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
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), '" + buttonLabel + "')]")));
        WebElement button = null;
        switch (buttonLabel) {
            case "Studies" : button = studiesButton();
                break;
            case "Student Life" : button = studentLifeButton();
                break;
            case "Overview" : button = overviewButton();
                break;
            case "International" : button = internationalButton();
                break;
            case "Costs" : button = costsButton();
                break;
        }
        waitUntil(ExpectedConditions.elementToBeClickable(button));
        button.click();
        waitUntilPageFinishLoading();
        if (!button.getText().equals(buttonLabel)) {
            button.click();
        }
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
    private WebElement internationalButton() {
        return getDriver().findElement(By.xpath("//span[contains(text(), \"International\")]"));
    }
    private WebElement costsButton() {
        return getDriver().findElement(By.xpath("//span[contains(text(), \"Costs\")]"));
    }
}
