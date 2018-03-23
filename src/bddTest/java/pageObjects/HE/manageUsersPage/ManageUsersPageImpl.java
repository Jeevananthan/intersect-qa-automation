package pageObjects.HE.manageUsersPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.COMMON.PageObjectFacadeImpl;
import pageObjects.HE.accountSettingsPage.AccountSettingsPageImpl;
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
        waitUntilElementExists( button("Yes"));
        Actions action = new Actions(getDriver());
        jsClick(getDriver().findElement(By.xpath(".//span[text()='Yes']")));
        waitForUITransition();
        try{
            (new WebDriverWait(getDriver(),10)).until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath(".//span[text()='Yes']")));
            waitForUITransition();
            action.sendKeys(Keys.TAB).sendKeys(Keys.TAB).sendKeys(Keys.ENTER).build().perform();
        }catch (Exception e){}
        waitUntil(ExpectedConditions.invisibilityOfElementLocated(By.xpath(".//div[text()='Inactivate this user']")));
        waitForUITransition();
    }

    public void activateUser(String accountName) {
        takeUserAction(accountName,"Activate");
        waitUntilElementExists( button("Yes"));
        Actions action = new Actions(getDriver());
        action.moveToElement(getDriver().findElement(By.xpath(".//span[text()='Yes']"))).click().build().perform();
        try{
            (new WebDriverWait(getDriver(),10)).until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath(".//span[text()='Yes']")));
            waitForUITransition();
            action.sendKeys(Keys.TAB).sendKeys(Keys.TAB).sendKeys(Keys.ENTER).build().perform();
        }catch (Exception e){}
        waitUntil(ExpectedConditions.invisibilityOfElementLocated(By.xpath(".//div[text()='Activate this user']")));
        waitForUITransition();
    }

    public void unlockUser(String accountName) {
        takeUserAction(accountName,"Unlock");
        waitUntil(ExpectedConditions.presenceOfElementLocated(By.xpath(".//div[text()='Unlock this user']")));
        jsClick(getDriver().findElement(By.cssSelector("button[class='ui positive button']")));
        waitForUITransition();
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


    public void verifyUserRoles(DataTable table) {
        navBar.goToUsers();
        button("ADD NEW USER").click();
        List<String> li = table.transpose().asList(String.class);
        for (int i=1;i<li.size();i++){
            String role = li.get(i);
            Assert.assertTrue("Expected to find role " + role + ", but it was not found.", driver.findElement(By.xpath("//label[@class='lESlXEQvUQGVcUPjUzRud']/span[text()='"+role+"']")).isDisplayed());
        }
    }

    public void verifyUserData(DataTable data) {
        navBar.goToUsers();
        List<Map<String,String>> entities = data.asMaps(String.class,String.class);
        for (Map<String,String> entity : entities) {
            WebElement row = getDriver().findElement(By.xpath(String.format(".//div[text()='%s']/parent::td/parent::tr",
                    entity.get("Email"))));
           for (String key : entity.keySet()) {
               logger.info("User Data Row: "+ row.getText());
               Assert.assertTrue("Expected to find " + entity.get(key) + "in row for " + entity.get("Email") + ", but it was not found.",row.getText().contains(entity.get(key)));
           }
        }
    }

    private void takeUserAction(String accountName, String action) {
        AccountSettingsPageImpl accountSettings = new AccountSettingsPageImpl();
        accountSettings.accessUsersPage("Account Settings","Users");
        WebElement userAccountRow = getDriver().findElement(By.xpath(String.format(".//div[text()='%s']/parent::td/parent::tr",accountName)));
        WebElement actionsButton = userAccountRow.findElement(By.cssSelector("div[aria-label='Actions']"));
        WebElement button = actionsButton.findElement(By.xpath("div/div/span[contains(text(),'"+action+"')]"));
        jsClick(button);
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
        String username = GetProperties.get("he." + usertype + ".username");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        LocalDate localDate = LocalDate.now();
        String currentDate = dtf.format(localDate);
        WebElement userRow = driver.findElement(By.xpath(String.format(
                "//div[contains(text(),'%s')]/parent::*/parent::*",username)));
        Assert.assertTrue(String.format("Last login is not correct for user: %s",username),userRow.
                findElement(By.xpath(String.format(".//td/span[text()='%s']", currentDate))).isDisplayed());
    }

    private GmailAPI getGmailApi() throws Exception { return new GmailAPI(); }

}
