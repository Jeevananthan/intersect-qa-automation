package pageObjects.SP.accountPages;

import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;

public class UserListPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public UserListPageImpl(){
        logger = Logger.getLogger(UserListPageImpl.class);
    }

    public void setUserStatus(String activeOrInactive, String userName) {
        if (activeOrInactive.equals("activate") || activeOrInactive.equals("inactivate")) {
            takeUserAction(userName, WordUtils.capitalize(activeOrInactive));
        } else {
            Assert.fail("Valid user actions are \"activate\" and \"inactivate\".");
        }
        button("YES").click();
    }

    public void verifyUserStatus(String userName, String activeOrInactive) {
        if (activeOrInactive.equals("active") || activeOrInactive.equals("inactive")) {
            verifyStatusIcon(userName,activeOrInactive);
        } else {
            Assert.fail("Valid user actions are \"active\" and \"inactive\".");
        }
    }

    public void setPrimaryUser(String userName) {
        takeUserAction(userName,"Assign as Primary");
        button("YES").click();
    }

    public void verifyUserIsPrimary(String userName) {
        verifyStatusIcon(userName,"primary");
    }

    public void verifyUserIsNotPrimary(String userName) {
        verifyStatusIcon(userName,"nonprimary");
    }

    private void takeUserAction(String userName, String action) {
        WebElement actionsButton = getParent(getParent(link(userName))).findElement(By.cssSelector("[aria-label=Actions]"));
        WebElement button = actionsButton.findElement(By.xpath("./div/div/span[contains(text(),'"+action+"')]"));
        actionsButton.click();
        jsClick(button);
        waitUntilPageFinishLoading();
    }

    private void verifyStatusIcon(String userName, String status) {
        String className = "";
        switch (status) {
            case "primary":
                //yellow star icon
                className = "yellow";
                break;
            case "nonprimary":
                //empty star icon
                className = "empty";
                break;
            case "active":
                //empty star icon
                className = "empty";
                break;
            case "inactive":
                //ban icon
                className = "ban";
                break;
        }
        Assert.assertTrue("Expected user status icon was not found.  Expected " + status, getParent(getParent(link(userName))).findElement(By.className(className)).isDisplayed());
    }

    private WebElement getParent(WebElement childElement) {
        return childElement.findElement(By.xpath("./.."));
    }

    // This is necessary because Selenium doesn't think that the action options are visible (even though they are),
    // so we interact with them directly through JS.
    private void jsClick(WebElement element) {
        driver.executeScript("arguments[0].click();",element);
    }
}
