package pageObjects.HE.HUBSEditMode;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.COMMON.PageObjectFacadeImpl;

import static org.junit.Assert.assertTrue;

public class EditMenuPageImpl extends PageObjectFacadeImpl {

    Logger logger = null;

    public EditMenuPageImpl() {
        logger = Logger.getLogger(EditMenuPageImpl.class);
    }

    public void clickEditMenuButton(String label) {
        new WebDriverWait(getDriver(), 60).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='editor-status ng-scope']")));
        studiesButton().click();
    }

    //Locators
    private WebElement studiesButton() {
        return getDriver().findElement(By.xpath("//li[text()='Studies']"));
    }
}
