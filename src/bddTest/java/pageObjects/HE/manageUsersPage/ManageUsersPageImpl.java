package pageObjects.HE.manageUsersPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import pageObjects.COMMON.PageObjectFacadeImpl;
import utilities.GetProperties;
import utilities.Gmail.Email;
import utilities.Gmail.GmailAPI;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.fail;

public class ManageUsersPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public ManageUsersPageImpl() {
        logger = Logger.getLogger(ManageUsersPageImpl.class);
    }

    public void inactivateUser(String accountName) {
        takeUserAction(accountName,"Inactivate");
        button("Yes").click();
        waitForStatusChangeModalToClear();
    }

    public void activateUser(String accountName) {
        takeUserAction(accountName,"Activate");
        button("Yes").click();
        waitForStatusChangeModalToClear();
    }

    public void unlockUser(String accountName) {
        takeUserAction(accountName,"Unlock");
        button("Yes").click();
        waitUntilPageFinishLoading();
    }

    public void editUser(String accountName,DataTable dataTable) {
        takeUserAction(accountName,"Edit");
        Map<String,String> entity = dataTable.asMap(String.class,String.class);
        for (String field : entity.keySet()) {
            if (textbox(field).isDisplayed()) {
                textbox(field).sendKeys(entity.get(field));
            } else {
                driver.findElement(By.cssSelector("input[value='"+entity.get(field).toLowerCase()+"']")).click();
            }
        }
        button("SAVE").click();
    }

    public void verifyUserRoles() {
        navBar.goToUsers();
        button("ADD NEW USER").click();
        String[] roles = {"administrator", "publishing", "community"};
        for (String role : roles) {
            Assert.assertTrue("Expected to find role " + role + ", but it was not found.", (driver.findElement(By.cssSelector("input[value='"+role.toLowerCase()+"']")).getLocation().getX()) > 0);
        }
    }

    public void verifyUserData(DataTable data) {
        navBar.goToUsers();
        Assert.assertTrue("Expected message \"Click the arrow to the right of any existing user to manage their settings and permissions.\" was not found!"
                ,text("Click the arrow to the right of any existing user to manage their settings and permissions.").isDisplayed());
        List<Map<String,String>> entities = data.asMaps(String.class,String.class);
        for (Map<String,String> entity : entities) {
           WebElement row = getParent(text(entity.get("Email")));
           for (String key : entity.keySet()) {
               logger.info("User Data Row: "+ row.getText());
               Assert.assertTrue("Expected to find " + entity.get(key) + "in row for " + entity.get("Email") + ", but it was not found.",row.getText().contains(entity.get(key)));
           }
        }
    }

    private void takeUserAction(String accountName, String action) {
        navBar.goToUsers();
        WebElement actionsButton = getParent(text(accountName)).findElement(By.cssSelector("[aria-label=Actions]"));
        WebElement button = actionsButton.findElement(By.xpath("./div/div/span[contains(text(),'"+action+"')]"));
        actionsButton.click();
        jsClick(button);
        waitUntilPageFinishLoading();
    }

    public void verifyEmailChangedNotification(DataTable data) {
        GetProperties.setGmailAPIWait(60);     //Time unit is in seconds
        try {
            List<Email> emails = getGmailApi().getMessages(data);

            for (Email email : emails) {
                System.out.print(email.toString());
            }
        } catch (Exception e) {
            logger.info("Exception while retrieving Gmail messages: " + e.getMessage());
            e.printStackTrace();
            fail("There was an error retrieving the email changed notification from Gmail.");
        }
    }

    //The below method is to validate the Last Login Date for Administrator (MATCH-192)
    public void verifyLastLoginData(String usertype) {
        navBar.goToUsers();
        String username = GetProperties.get("he."+ usertype + ".username");
        Assert.assertTrue("Expected message \"Click the arrow to the right of any existing user to manage their settings and permissions.\" was not found!"
                ,text("Click the arrow to the right of any existing user to manage their settings and permissions.").isDisplayed());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        LocalDate localDate = LocalDate.now();
        String currentDate = dtf.format(localDate);

        Assert.assertTrue("Last login date is not correct for "+username, getDriver().findElement(By.xpath("//td[contains(text(),'"+username+"')]/following::span[contains(text(),'"+currentDate+"')]")).isDisplayed());
    }


    public void createHigherEducationUser(String firstName, String lastName,String Email,String role){

        navBar.goToUsers();
        button("Add New User").click();

        textbox("First Name").sendKeys(firstName);
        textbox("Last Name").sendKeys(lastName);
        textbox("Email").sendKeys(Email);
        driver.findElement(By.xpath("//input[@aria-label='"+role+"']")).click();


//

        button("SAVE").click();
    }

    // This is necessary because Selenium doesn't think that the action options are visible (even though they are),
    // so we interact with them directly through JS.
    private void jsClick(WebElement element) {
        driver.executeScript("arguments[0].click();",element);
    }

    // These actions can be very slow in the UI and eat further script clicks.  Wait up to 20 seconds for them to disappear.
    private void waitForStatusChangeModalToClear() {
        long now = System.currentTimeMillis();
        long timeout = now + 20000;
        try {
            do {
                driver.findElement(By.id("manage-user-status-change-modal")).isDisplayed();
            } while (timeout > System.currentTimeMillis());
            Assert.fail("Waited 20 seconds for Modal to clear and it did not.");
        } catch (Exception e) {
            System.out.println("\nWaited " + (System.currentTimeMillis() - now) + " ms for Modal.\n");
        }
    }

    private GmailAPI getGmailApi() throws Exception { return new GmailAPI(); }

}
