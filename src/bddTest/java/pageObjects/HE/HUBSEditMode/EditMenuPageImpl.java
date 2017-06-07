package pageObjects.HE.HUBSEditMode;

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
        new WebDriverWait(getDriver(), 60).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='editor-status ng-scope']")));
        studiesButton().click();
        logger.info(label + " button was clicked");
    }

    //Locators
    private WebElement studiesButton() {
        return getDriver().findElement(By.xpath("//li[text()='Studies']"));
    }
}
