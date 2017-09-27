package pageObjects.HUBS;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.COMMON.PageObjectFacadeImpl;

public class EditMenuPageImpl extends PageObjectFacadeImpl {

    Logger logger = null;

    public EditMenuPageImpl() {
        logger = Logger.getLogger(EditMenuPageImpl.class);
    }

    public void clickEditMenuButton(String label) {
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//div[@class='editor__page ng-scope']"), 1));
        switch (label) {
            case "Studies" : studiesButton().click();
                break;
            case "Student Life" : studentLifeButton().click();
                break;
            case "Overview" : overviewButton().click();
                break;
            case "International" : internationalButton().click();
                break;
        }
        logger.info(label + " button was clicked");
    }

    //Locators
    private WebElement studiesButton() {
        return getDriver().findElement(By.xpath("//li[text()='Studies']"));
    }
    private WebElement studentLifeButton() { return getDriver().findElement(By.xpath("//li[text()='Student Life']")); }
    private WebElement overviewButton() { return getDriver().findElement(By.xpath("//li[text()='Overview']")); }
    private WebElement internationalButton() { return getDriver().findElement(By.xpath("//li[text()='International']")); }
}
