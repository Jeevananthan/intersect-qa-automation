package pageObjects.SM.connector;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;
import pageObjects.SM.collegesLookingForStudentsLikeYou.CollegesLookingForStudentsLikeYouPageImpl;

import java.util.List;

public class DisconnectorPageImpl extends PageObjectFacadeImpl {

    public DisconnectorPageImpl() {
        logger = Logger.getLogger(DisconnectorPageImpl.class);
    }

    private Logger logger;
    private CollegesLookingForStudentsLikeYouPageImpl collegesLookingForStudentsLikeYouPage = new CollegesLookingForStudentsLikeYouPageImpl();

    public void clickDialogButton(String buttonLabel) {
        waitUntilElementExists(yesButton());
        if (buttonLabel.equals("Yes, I'm sure")) {
            yesButton().click();
        } else {
            nevermindButton().click();
        }
    }



    //Locators
    private WebElement yesButton() { return driver.findElement(By.cssSelector("button#yesButton")); }
    private WebElement nevermindButton() { return driver.findElement(By.cssSelector("button#nevermindButton")); }
}
