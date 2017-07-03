package pageObjects.SP.accountPages;

import cucumber.api.DataTable;
import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.Map;

public class UserListPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public UserListPageImpl(){
        logger = Logger.getLogger(UserListPageImpl.class);
    }

    public void setUserStatus(String activeOrInactiveorUnlock, String userName) {
        if (activeOrInactiveorUnlock.equals("activate") || activeOrInactiveorUnlock.equals("inactivate") || activeOrInactiveorUnlock.equals("unlock")) {
            takeUserAction(userName, WordUtils.capitalize(activeOrInactiveorUnlock));
        } else {
            Assert.fail("Valid user actions are \"activate\",\"inactivate\" and \"unlock\".");
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

    public void fillFormInCreateUserAndVerifyMessaging(DataTable dataTable){
        Map<String,String> data = dataTable.asMap(String.class,String.class);
        for (String field : data.keySet()){
            if(field.equalsIgnoreCase("role")){
                driver.findElement(By.xpath("//input[@name='role' and @value='"+data.get(field)+"']")).click();
            }
                else{
                textbox(field).sendKeys(data.get(field));
            }
        }
        button("Save").click();
        Assert.assertTrue("\"Please enter a value\" message is not displayed",text("Please enter a value").isDisplayed());

    }

    public void createUserAndSubmit(DataTable dataTable){
        Map<String,String> data = dataTable.asMap(String.class,String.class);
        for (String field : data.keySet()){
            if(field.equalsIgnoreCase("role")){
                driver.findElement(By.xpath("//input[@name='role' and @value='"+data.get(field)+"']")).click();
            }
            else{
                textbox(field).sendKeys(data.get(field));
            }
        }
        button("Save").click();
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
