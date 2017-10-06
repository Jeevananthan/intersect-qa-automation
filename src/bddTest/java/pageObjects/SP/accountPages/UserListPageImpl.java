package pageObjects.SP.accountPages;

import cucumber.api.DataTable;
import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;
import static org.junit.Assert.fail;
import java.util.Map;

public class UserListPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public UserListPageImpl(){
        logger = Logger.getLogger(UserListPageImpl.class);
    }

    public void setUserStatus(String activeOrInactiveorUnlock, String userName) {
        if (activeOrInactiveorUnlock.equals("activate") || activeOrInactiveorUnlock.equals("inactivate") || activeOrInactiveorUnlock.equals("unlock") || activeOrInactiveorUnlock.equals("re-invite") ) {
            takeUserAction(userName, WordUtils.capitalize(activeOrInactiveorUnlock));
        } else {
            Assert.fail("Valid user actions are \"activate\",\"inactivate\" and \"unlock\".");
        }
        button("YES").click();
        try {
            driver.wait(2000);
        } catch (Exception e) {}
        waitUntilPageFinishLoading();
    }

    public void verifyUserStatus(String userName, String activeOrInactive) {
        if (activeOrInactive.equals("active") || activeOrInactive.equals("inactive")) {
            verifyStatusIcon(userName,activeOrInactive);
        } else {
            Assert.fail("Valid user actions are \"active\" and \"inactive\".");
        }
    }

    public void fillFormInCreateUser(String firstName, String lastName, String email, String verifyEmail, String role, String buttonToClick) {
        if (!firstName.equals("")) {
            Assert.assertTrue("First Name TextBox is not displayed", textbox(By.id("create-primary-user-first-name")).isDisplayed());
            textbox(By.id("create-primary-user-first-name")).sendKeys(firstName);
        }
        if (!lastName.equals("")) {
            Assert.assertTrue("Last Name TextBox is not displayed", textbox(By.id("create-primary-user-last-name")).isDisplayed());
            textbox(By.id("create-primary-user-last-name")).sendKeys(lastName);
        }
        if (!email.equals("")) {
            Assert.assertTrue("Email TextBox is not displayed", textbox(By.id("create-primary-user-email")).isDisplayed());
            textbox(By.id("create-primary-user-email")).sendKeys(email);
        }
        if (!verifyEmail.equals("")) {
            Assert.assertTrue("Verify Email TextBox is not displayed", textbox(By.id("create-primary-user-verify-email")).isDisplayed());
            textbox(By.id("create-primary-user-verify-email")).sendKeys(verifyEmail);
        }
        if (!role.equals("")) {
            if(role.equalsIgnoreCase("Administrator")) {
                if (driver.findElement(By.id("role_administrator")).isDisplayed()) {
                    driver.findElement(By.id("role_administrator")).click();
                } else if (driver.findElement(By.id("role_admin")).isDisplayed()) {
                    driver.findElement(By.id("role_administrator")).click();
                }
            }else if(role.equalsIgnoreCase("Publishing")) {
                driver.findElement(By.id("role_publishing")).click();
            }else if(role.equalsIgnoreCase("Community")) {
                driver.findElement(By.id("role_community")).click();
            }else if(role.equalsIgnoreCase("Member")) {
                driver.findElement(By.id("role_member")).click();
            }else{
                fail("The option for the Role is not a valid one");
            }
        }
        if (!buttonToClick.equals("")) {
            if (buttonToClick.equalsIgnoreCase("Save")) {
                button("Save").click();
            } else if (buttonToClick.equalsIgnoreCase("Cancel")) {
                button("Cancel").click();
            }else{
              fail("The option for button to click is not a valid one");
            }
        }
    }

    public void verifyErrorMessageinCreateUser(){
        Assert.assertTrue("\"Please enter a value\" message is not displayed",text("Please enter a value").isDisplayed());
    }

    public void verifyNoErrorMessageinCreateUser(){
        Assert.assertFalse("\"Please enter a value\" message is not displayed",text("Please enter a value").isDisplayed());
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

    // This is necessary because Selenium doesn't think that the action options are visible (even though they are),
    // so we interact with them directly through JS.
    private void jsClick(WebElement element) {
        driver.executeScript("arguments[0].click();",element);
    }
}
