package pageObjects.HE.HUBSEditMode;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;

public class HEMPreviewPageImpl extends PageObjectFacadeImpl {

    Logger logger = null;

    public HEMPreviewPageImpl() {
        logger = Logger.getLogger(HEMPreviewPageImpl.class);
    }

    public void clickMenuButton(String buttonLabel) {
        WebElement button = null;
        switch (buttonLabel) {
            case "Studies" : button = studiesButton();
                break;
        }
        button.click();
        waitUntilPageFinishLoading();
    }

    //Locators
    private WebElement studiesButton() {
        return driver.findElement(By.xpath("//span[contains(text(), \"Studies\")]"));
    }
}
