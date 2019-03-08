package pageObjects.HUBS;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.concurrent.TimeUnit;

public class HUBSHeaderPageImpl extends PageObjectFacadeImpl {

    Logger logger = null;

    public HUBSHeaderPageImpl() {
        logger = Logger.getLogger(HUBSHeaderPageImpl.class);
    }

    public void clickLogOut() {
        driver.manage().timeouts().implicitlyWait(70, TimeUnit.SECONDS);
        waitUntilElementExists(logOutButton());
        logOutButton().sendKeys(Keys.RETURN);
        waitUntilPageFinishLoading();
        logger.info("User signed out HUBS");
    }

    public void addColToImThinkingAboutListIfNotAlreadyThere() {
        if (heartIcon().getAttribute("class").equals("fc-icon")) {
            heartIcon().click();
            waitUntil(ExpectedConditions.attributeToBe(heartIcon(), "class", "fc-icon masthead__heart--full"));
        }
    }

    public void verifyAMNextGenIsLoadedWhenClickConnect() {
        connectButton().click();
        Assert.assertTrue("The Active Match Next Gen Connector dialog is not displayed", amNextGenConnectDialog().isDisplayed());
    }

    public void verifyLegacyAMNIsLoadedWhenClickConnect() {
        connectButton().click();
        Assert.assertTrue("Tyhe Legacy Active Match Connector dialog is not disaplyed", legacyAMConnectorDialog().isDisplayed());
    }

    //Locators

    public WebElement logOutButton() {
        return getDriver().findElement(By.xpath("//a/following-sibling::button[text() = 'LOG OUT']"));
    }

    private WebElement heartIcon() {
        return driver.findElement(By.cssSelector("div.masthead__heart.ng-scope svg"));
    }

    private WebElement connectButton() {
        return driver.findElement(By.cssSelector("a[ng-click='vm.connect()']"));
    }

    private WebElement amNextGenConnectDialog() {
        return driver.findElement(By.cssSelector("div.ui.modal.transition.visible.active.modalWrapper"));
    }

    private WebElement legacyAMConnectorDialog() {
        return driver.findElement(By.cssSelector("div.fc-grid__col.fc-grid__col--xs-12.fc-grid__col--sm-8.fc-grid__col--md-6.amlb.amlb__connect"));
    }
}
