package pageObjects.SP.accountPages;

import cucumber.api.DataTable;
import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;
import pageObjects.HE.accountSettingsPage.AccountSettingsPageImpl;
import utilities.GetProperties;
import utilities.Gmail.Email;
import utilities.Gmail.GmailAPI;

import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserListPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public UserListPageImpl(){
        logger = Logger.getLogger(UserListPageImpl.class);
    }

    public void setUserStatus(String activeOrInactiveorUnlock, String userName) {
        waitUntil(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(userListTable), 0));
        if (activeOrInactiveorUnlock.equals("activate") || activeOrInactiveorUnlock.equals("inactivate") || activeOrInactiveorUnlock.equals("unlock") ) {
            takeUserAction(userName, WordUtils.capitalize(activeOrInactiveorUnlock));
            waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a/span[text()='Create New User']")));
            waitForUITransition();
        } else if (activeOrInactiveorUnlock.equals("Login As")|| activeOrInactiveorUnlock.equals("re-invite") ){
            takeUserAction(userName, WordUtils.capitalize(activeOrInactiveorUnlock));
        } else {
            Assert.fail("Valid user actions are \"activate\",\"inactivate\",\"unlock\",\"re-invite\" and \"Login As\".");
        }
        try {
            driver.wait(2000);
        } catch (Exception e) {}
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
                saveButtonInCreateUser().click();
            } else if (buttonToClick.equalsIgnoreCase("Cancel")) {
                CancelButtonInCreateUser().click();
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
    }

    public void verifyUserIsPrimary(String userName) {
        verifyStatusIcon(userName,"primary");
    }

    public void verifyEditPrimaryUserDetails(){
        Boolean canEdit = false;
        button(By.xpath("//span[text()='Primary User Details']/../button")).click();
        Assert.assertTrue("The edit primary user page was not displayed", text("Update Primary User for").isDisplayed());
    }

    public void verifyUserIsNotPrimary(String userName) {
        verifyStatusIcon(userName,"nonprimary");
    }

    private void takeUserAction(String userName, String action) {
        waitUntilElementExists(driver.findElement(By.xpath("//a[text()='"+userName+"']")));
        WebElement element = driver.findElement(By.xpath("//a[text()='"+userName+"']"));
        moveToElement(element);
        WebElement actionsButton = driver.findElement(By.xpath("//a[text()='"+userName+"']/parent::td/following-sibling::td/div[@aria-label='Actions']"));
        //WebElement actionsButton = getParent(getParent(link(userName))).findElement(By.cssSelector("[aria-label=Actions]"));
        waitUntilElementExists(actionsButton);
        actionsButton.click();
        List<WebElement> button = driver.findElements(By.xpath("//a[text()='"+userName+"']/parent::td/following-sibling::td/div[@aria-label='Actions']/div/div/span[contains(text(),'"+action+"')]"));
        if(button.size()==1) {
            WebElement buttonToClick = driver.findElement(By.xpath("//a[text()='" + userName + "']/parent::td/following-sibling::td/div[@aria-label='Actions']/div/div/span[contains(text(),'" + action + "')]"));
            jsClick(buttonToClick);
            waitForUITransition();
            button("YES").click();
            waitUntilPageFinishLoading();
            waitForUITransition();
        }
    }

    private void verifyStatusIcon(String userName, String status) {
        String className = "";
        switch (status) {
            case "primary":
                //yellow
                className = "yellow star icon";
                break;
            case "nonprimary":
                //empty
                className = "outline star icon";
                break;
            case "active":
                //empty
                className = "outline star icon";
                break;
            case "inactive":
                //ban
                className = "ban icon";
                break;
        }
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='"+userName+"']/parent::td/parent::tr/td/i[@class='"+className+"']")));
        Assert.assertTrue("Expected user status icon was not found.  Expected " + status, driver.findElement(By.xpath("//a[text()='"+userName+"']/parent::td/parent::tr/td/i[@class='"+className+"']")).isDisplayed());
    }

    public void verifyCreateUserButton() {
        Assert.assertTrue("See All Users is not displayed",driver.findElement(By.xpath("//span[text()='See All Users']")).isDisplayed());
        driver.findElement(By.xpath("//span[text()='See All Users']")).click();
        Assert.assertTrue("Create New User button is not displayed",driver.findElement(By.xpath("//span[text()='Create New User']")).isDisplayed());
        driver.findElement(By.xpath("//span[text()='Create New User']")).click();
    }

    public void moveToElement(WebElement element) {
        Actions builder = new Actions(driver);
        builder.moveToElement(element).build().perform();
    }

    public void verifyVisitDetailsInLogHistory(String supportUserName,String user,String option,String date){
        driver.findElement(By.xpath("//div[@class='ui compact selection dropdown _3SCFtXPZGOBhlAsaQm037_']//i[@class='dropdown icon']")).click();
        driver.findElement(By.xpath("//span [text()='"+option+"']")).click();
        waitUntilPageFinishLoading();
        String visitDate = getSpecificDateForVisit(date);
        String currentDate = getDate("0");
        String value =supportUserName+" logged in as "+ user+" RSVP'd RepVisitsDay "+visitDate+"";
        String originalDate = "";
        String originalValue = "";
        int rowData = 1;
        List rowCount = driver.findElements(By.xpath("//table/tbody/tr"));
        for (int row= 1;row<=rowCount.size();row++){
            originalValue = driver.findElement(By.xpath("//table/tbody/tr[" + row + "]/td[2]")).getText();
            if (value.equals(originalValue)) {
                break;
            }rowData = rowData+1;
        }
        originalDate = driver.findElement(By.xpath("//table/tbody/tr[" + rowData + "]/td[1]/span")).getText();
        Assert.assertTrue("Current date is not displayed",originalDate.equals(currentDate));
        Assert.assertTrue("Visit details is not displayed",originalValue.equals(value));
    }

    public void verifyFairDetailsInLogHistory(String supportUserName,String user,String option){
        String fairName=pageObjects.HS.repVisitsPage.RepVisitsPageImpl.FairName;
        driver.findElement(By.xpath("//div[@class='ui compact selection dropdown _3SCFtXPZGOBhlAsaQm037_']//i[@class='dropdown icon']")).click();
        driver.findElement(By.xpath("//span [text()='"+option+"']")).click();
        waitUntilPageFinishLoading();
        String date = getDate("0");
        String value = supportUserName+" logged in as "+user+" RSVP'd "+fairName;
        String originalDate = "";
        String originalValue = "";
        int rowData = 1;
        List rowCount = driver.findElements(By.xpath("//table/tbody/tr"));
        for (int row= 1;row<=rowCount.size();row++){
            originalValue = driver.findElement(By.xpath("//table/tbody/tr[" + row + "]/td[2]")).getText();
            if (value.equals(originalValue)) {
                break;
            }rowData = rowData+1;
        }
        originalDate = driver.findElement(By.xpath("//table/tbody/tr[" + rowData + "]/td[1]/span")).getText();
        Assert.assertTrue("Current date is not displayed",originalDate.equals(date));
        Assert.assertTrue("Fair details is not displayed",originalValue.equals(value));
    }

    public void verifyLoggedInDetailsInLogHistory(String supportUserName,String user,String option){
        driver.findElement(By.xpath("//div[@class='ui compact selection dropdown _3SCFtXPZGOBhlAsaQm037_']//i[@class='dropdown icon']")).click();
        driver.findElement(By.xpath("//span [text()='"+option+"']")).click();
        waitUntilPageFinishLoading();
        String date = getDate("0");
        String value = supportUserName+" Logged in as "+user;
        String originalDate = "";
        String originalValue = "";
        int rowData = 1;
        List rowCount = driver.findElements(By.xpath("//table/tbody/tr"));
        for (int row= 1;row<=rowCount.size();row++){
            originalValue = driver.findElement(By.xpath("//table/tbody/tr[" + row + "]/td[2]")).getText();
            if (value.equals(originalValue)) {
                break;
            }rowData = rowData+1;
        }
        originalDate = driver.findElement(By.xpath("//table/tbody/tr[" + rowData + "]/td[1]/span")).getText();
        Assert.assertTrue("Current date is not displayed",originalDate.equals(date));
        Assert.assertTrue("Logged in details not displayed",originalValue.equals(value));
    }

    public void verifyCreatedPostDetailsInLogHistory(String user,String option){
        driver.findElement(By.xpath("//div[@class='ui compact selection dropdown _3SCFtXPZGOBhlAsaQm037_']//i[@class='dropdown icon']")).click();
        driver.findElement(By.xpath("//span [text()='"+option+"']")).click();
        waitUntilPageFinishLoading();
        String date = getDate("0");
        String value = user+" created post";
        String originalDate = "";
        String originalValue = "";
        int rowData = 1;
        List rowCount = driver.findElements(By.xpath("//table/tbody/tr"));
        for (int row= 1;row<=rowCount.size();row++){
            originalValue = driver.findElement(By.xpath("//table/tbody/tr[" + row + "]/td[2]")).getText();
            if (value.equals(originalValue)) {
                break;
            }rowData = rowData+1;
        }
        originalDate = driver.findElement(By.xpath("//table/tbody/tr[" + rowData + "]/td[1]/span")).getText();
        Assert.assertTrue("Current date is not displayed",originalDate.equals(date));
        Assert.assertTrue("Created post value is not displayed",originalValue.equals(value));
    }

    public String getDate(String addDays){
        String DATE_FORMAT_NOW = "M/d/yyyy";
        Calendar cal = Calendar.getInstance();
        int days=Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }
    public String getSpecificDateForVisit(String addDays){
        String DATE_FORMAT_NOW = "yyyy-MM-dd";
        Calendar cal = Calendar.getInstance();
        int days=Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }

    public void verifyUserUpdateInLogHistory(String user,String option) {
        timeDropdown().click();
        timeDropdownOption(option).click();
        waitUntilPageFinishLoading();
        String date = getSpecificDate(0,"M/d/yyyy");
        String firstNameValue = AccountSettingsPageImpl.generatedFirstName;
        softly().assertThat(ExpectedConditions.visibilityOf(firstNameValue(date, firstNameValue))).as("The user account was not successfully updated.");
    }

    public String getSpecificDate(int addDays, String format) {
        String DATE_FORMAT_NOW = "MMMM d, yyyy";

        if(format != null)
            DATE_FORMAT_NOW = format;

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, addDays);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }

    public void loginAs(String user) {
        takeUserAction(user, "Login As");
        String originalWindow = driver.getWindowHandle();
        String newWindow = null;
        Set<String> windows = driver.getWindowHandles();
        for(String thisWindow : windows){
            if(!thisWindow.equals(originalWindow)){
                newWindow = thisWindow;
            }
        }
        driver.close();
        driver.switchTo().window(newWindow);
        waitUntilPageFinishLoading();
    }

    public void verifyLoginMessageInHomPage(String message){
        waitUntil(ExpectedConditions.numberOfWindowsToBe(2));
        waitUntilPageFinishLoading();
        String supportWindow = driver.getWindowHandle();
        String HEWindow = null;
        Set<String> windows = driver.getWindowHandles();
        for(String thisWindow : windows){
            if(!thisWindow.equals(supportWindow)){
                HEWindow = thisWindow;
            }
        }
        driver.switchTo().window(HEWindow);
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='ui small icon info message toast persistent wGfRWJCMN3CEBD7NJI-dc']/div/span")));
        String originalMessage = getLoginMessageInHomePage().getText();
        softly().assertThat(originalMessage).as("Impersonation Banner Message").isEqualTo(message);
    }

    public void verifyUpgradeButtonInHomPage(String message){
        waitUntil(ExpectedConditions.numberOfWindowsToBe(2));
        waitUntilPageFinishLoading();
        String supportWindow = driver.getWindowHandle();
        String HEWindow = null;
        Set<String> windows = driver.getWindowHandles();
        for(String thisWindow : windows){
            if(!thisWindow.equals(supportWindow)){
                HEWindow = thisWindow;
            }
        }
        driver.switchTo().window(HEWindow);
        waitUntil(ExpectedConditions.visibilityOfElementLocated(getUpgrateButtonLocator()));
        softly().assertThat(getUpgradeButton().isDisplayed());
        softly().assertThat(getUpgradeButton().getText().contains(message));
    }

    public void verifyInviteEmail(DataTable dataTable) {
        waitForUITransition();
        String emailBody = "";
        GetProperties.setGmailAPIWait(120);     //Time unit is in seconds
        try {
            waitForUITransition();
            List<Email> emails = getGmailApi().getMessages(dataTable);
            for (Email email : emails) {
                System.out.print(email.toString());
                emailBody = email.getBody();
            }
        } catch (Exception e) {
            logger.info("Exception while retrieving Gmail messages: " + e.getMessage());
            e.printStackTrace();
            fail("There was an error retrieving the invitation email from Gmail.");
        }
        softly().assertThat(emailBody).as("Main invite message").contains("Welcome to Intersect by Hobsons! A user account has been created for you so that you may access the Counselor Community and manage your college profile in Naviance. To setup your account, go to:");
        softly().assertThat(emailBody).as("Seven day expiration reminder").contains("Note, this password will expire in seven days. Contact your Intersect Administrator or");
    }

    public void checkImpersonatedWindow(String expWinCount){
        int winCount = driver.getWindowHandles().size();
        softly().assertThat(Integer.parseInt(expWinCount)).as("TotalWindowCount").isEqualTo(winCount);
    }

    public void checkImpersonationAccountSettings(String option){
        Set<String> winHandles = driver.getWindowHandles();
        String firstWinHandle = driver.getWindowHandle();
        winHandles.remove(firstWinHandle);
        String secondWinHandle=winHandles.iterator().next();
        driver.switchTo().window(firstWinHandle);
        driver.switchTo().window(secondWinHandle);
        softly().assertThat(accountSettingOptionInHomePageForNonNaviance().size()).as("Account Setting Option is visible in Home Page").isEqualTo(0);
        userDropDown().click();
        softly().assertThat(accountSettingOptionInUserDD(option).size()).as("Account Setting Option is visible in User DropDown").isEqualTo(0);
    }

    public void loginAsWithNonNavianceUser(String feature, String user){
        String username = GetProperties.get("hs."+ user + ".username");
        setUserStatus(feature, username);
    }


    //Locators

    private List accountSettingOptionInHomePageForNonNaviance(){return driver.findElements(By.xpath("//h2[text()='Your settings']"));}
    private WebElement userDropDown(){ return driver.findElement(By.id("user-dropdown"));}
    private List accountSettingOptionInUserDD(String option){return driver.findElements(By.xpath("//span[text()='"+option+"']"));}
    private WebElement saveButtonInCreateUser(){
        return getDriver().findElement(By.xpath("//button/span[text()='Save']"));
    }
    private WebElement CancelButtonInCreateUser(){
        return getDriver().findElement(By.xpath("//button/span[text()='Cancel']"));
    }
    private WebElement timeDropdown() { return driver.findElement(By.xpath("//div[@class='ui compact selection dropdown _3SCFtXPZGOBhlAsaQm037_']//i[@class='dropdown icon']")); }

    private WebElement getUpgradeButton() { return driver.findElement(getUpgrateButtonLocator()); }

    private By getUpgrateButtonLocator(){
        return By.cssSelector("button[class='ui secondary button _2gMolwx9EDCmPkEu5Kjlaw']");
    }


    private WebElement timeDropdownOption(String option) { return driver.findElement(By.xpath("//span [text()='"+option+"']")); }
    /**
     * Gets the login message in home page
     * @return
     */
    private WebElement getLoginMessageInHomePage(){
        return driver.findElement(By.xpath(
                "//div[@class='ui small icon info message toast persistent wGfRWJCMN3CEBD7NJI-dc']/div/span"));
    }

    private GmailAPI getGmailApi() throws Exception { return new GmailAPI(); }
    private String backButton = "i[class='angle left icon']";
    private String userListTable = "//table/caption/span[contains(text(),'User List')]";
    private WebElement firstNameValue(String date, String newName) {
        return driver.findElement(By.xpath("//td/span[text()='" + date + "']/../following-sibling::td/following-sibling::" +
                "td//span[@class = 'json-markup-key']/following-sibling::span[text() = '\""+ newName + "\"']"));
    }
}
