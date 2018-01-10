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
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//div[@class='editor ng-scope']"), 1));
        switch (label) {
            case "Studies" : studiesButton().click();
                break;
            case "Costs" : costsButton().click();
                break;
        }
        logger.info(label + " button was clicked");
    }

    //Locators
    private WebElement studiesButton() {
        return getDriver().findElement(By.xpath("//li[text()='Studies']"));
    }
    private WebElement costsButton() {
        return getDriver().findElement(By.xpath("//li[text()='Costs']"));
    }
}
