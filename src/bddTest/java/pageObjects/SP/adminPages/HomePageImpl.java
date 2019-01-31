package pageObjects.SP.adminPages;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.GlobalSearch;
import pageObjects.COMMON.PageObjectFacadeImpl;
import utilities.GetProperties;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

public class HomePageImpl extends PageObjectFacadeImpl {

    private Logger logger;
    private GlobalSearch search = new GlobalSearch();

    public HomePageImpl() {
        logger = Logger.getLogger(HomePageImpl.class);
    }

    public void verifyUserIsLoggedInForNoRole() {
        //Check the signout button is present for No Role
        Assert.assertTrue("User did not signed in",signoutButtonForNoRole().isDisplayed());
        logger.info("Logged in successfully");
    }

    public void verifyUserIsLoggedIn() {
        //Check if user element is present
        Assert.assertTrue("User did not signed in",link(By.id("user-dropdown")).isDisplayed());
        logger.info("Logged in successfully");
    }

    public void verifySecurityMessage(String expectedMsg) {
        Assert.assertTrue(text(expectedMsg).isDisplayed());
        logger.info("Security message match the expected message");
    }

    public void logout() {
        try{
            List<WebElement> signoutButton = driver.findElements(By.cssSelector("button[class='ui mini button']"));
            if (signoutButton.size()==1){
                signoutButtonForNoRole().click();
                waitUntilPageFinishLoading();
                Assert.assertTrue(getDriver().getCurrentUrl().contains("login"));
                driver.manage().deleteAllCookies();
            }
        }catch(Exception e){
            link(By.id("user-dropdown")).click();
            driver.findElement(By.cssSelector("div[id='user-dropdown-signout']")).click();
            waitUntilPageFinishLoading();
            Assert.assertTrue(getDriver().getCurrentUrl().contains("login"));
            driver.manage().deleteAllCookies();
        }
    }

    public String selectTheFistInstitutionOnTheList() {
        return table("Higher Ed Account Dashboard").clickOnTheFirstElementOfAColumn("Name");
    }

    public void addPost(String msg)
    {
        driver.switchTo().frame("_2ROBZ2Dk5vz-sbMhTR-LJ");
        driver.findElement(By.xpath("//textarea[@class='form-textarea']")).sendKeys(msg);
        driver.findElement(By.xpath("//input[@id='edit-save']")).click();
        driver.switchTo().defaultContent();
    }

    public void goToInstitution(String institutionName) {
        navBar.goToHome();
        globalSearch.setSearchCategory("All");
        globalSearch.searchForHEInstitutions(institutionName);
        globalSearch.selectResult(institutionName);
        /*while (button("More Higher Ed Accounts").isDisplayed()) {
            button("More Higher Ed Accounts").click();
            waitUntilPageFinishLoading();
        }
        table(By.id("he-account-dashboard")).findElement(By.cssSelector("[aria-label=\"" + institutionName + "\"]")).click();*/
    }

    public void goToAccount(String accountType, String accountName) {
        navBar.goToHome();
        globalSearch.setSearchCategory("All");
        switch (accountType.toLowerCase()){
            case "he":
                globalSearch.searchForHEInstitutions(accountName);
                break;
            case "hs":
                globalSearch.searchHSAccounts(accountName);
                break;
                default: Assert.fail("The Account type to search is incorrect");
                    break;
        }
        globalSearch.selectResult(accountName);
    }

    public void verifyYearInLoginPage(){
        String currentYear = getCurrentYear();
        driver.manage().deleteAllCookies();
        load(GetProperties.get("he.app.url"));
        waitUntilPageFinishLoading();
        Assert.assertTrue("Current year is not displayed",driver.findElement(By.xpath("//div[text()='Copyright © ']/parent::div/div[text()='"+currentYear+"']/parent::div/div[text()=', Hobsons Inc.']")).isDisplayed());
    }

    public void verifyYearInRegistrationPage(){
        load(GetProperties.get("he.registration.url"));
        String currentYear = getCurrentYear();
        Assert.assertTrue("Registration page is not displayed",text("New User? Find Your Institution").isDisplayed());
        Assert.assertTrue("Current year is not displayed",driver.findElement(By.xpath("//div[text()='Copyright © ']/parent::div/div[text()='"+currentYear+"']/parent::div/div[text()=', Hobsons Inc.']")).isDisplayed());
    }
    public void verifyYearInHomePage(){
        String currentYear = getCurrentYear();
        Assert.assertTrue("Current year is not displayed",driver.findElement(By.xpath("//div[text()='Copyright © ']/parent::div/div[text()='"+currentYear+"']/parent::div/div[text()=', Hobsons Inc.']")).isDisplayed());
    }
    public void verifyHelpCentre() {
        Assert.assertTrue("notifications icon is not displayed",notificationIconInHelpCentre().isDisplayed());
        Assert.assertTrue("helpNav-dropdown icon is not displayed",helpNavDropdown().isDisplayed());
        helpNavDropdown().click();
        Assert.assertTrue("Help Center is not displayed",helpCentre().isDisplayed());
        Assert.assertTrue("Contact Support is not displayed",contactSupport().isDisplayed());
        helpCentre().click();
        waitUntilPageFinishLoading();
        String navianceWindow = driver.getWindowHandle();
        String intersectWindow = null;
        Set<String> windows = driver.getWindowHandles();
        for (String thisWindow : windows) {
            if (!thisWindow.equals(navianceWindow)){
                intersectWindow = thisWindow;
            }
        }
        driver.switchTo().window(intersectWindow);
        waitUntilPageFinishLoading();
        String currentYear=getCurrentYear();
        Assert.assertTrue("hobsons logo is not displayed",logo().isDisplayed());
        Assert.assertTrue("Current year is not displayed",driver.findElement(By.xpath("//span[text()='Copyright © "+currentYear+" ']/parent::span/following-sibling::span[text()='Hobsons']")).isDisplayed());
        driver.close();
        driver.switchTo().window(navianceWindow);
        waitUntilPageFinishLoading();
    }
    public String getCurrentYear(){
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String currentYear=Integer.toString(year);
        return currentYear;
    }

    public void goToUsersList(String institutionName) {
        goToInstitution(institutionName);
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.linkText("See All Users")));
        link("See All Users").click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.visibilityOf(userListTable()));
    }

    public void goToAccountUsersList(String accountType, String accountName) {
        goToAccount(accountType, accountName);
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.linkText("See All Users")));
        link("See All Users").click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.visibilityOf(userListTable()));
    }

    public void goToCreateUser(String institutionName) {
        goToInstitution(institutionName);
        link("Create User").click();
        waitUntilPageFinishLoading();
    }

    public void navigateToCreateUser(){
        link("Create User").click();
        waitUntilPageFinishLoading();

    }

    public void goToLogHistory(String institutionName) {
        goToInstitution(institutionName);
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.linkText("View Log History"),1));
        link("View Log History").click();
        Assert.assertTrue(textbox("Search...").isDisplayed());
    }

    public void goToUsersListUsingSearch(String institutionName, String searchString) {
        navBar.goToHome();
        search.searchForAll(searchString);
        search.selectResult(institutionName);
        waitUntilPageFinishLoading();
        link("See All Users").click();
        try {
            driver.wait(2000);
        } catch (Exception e){}
        waitUntilPageFinishLoading();
    }

    public void navigateToGraphiQL() {
        navBar.goToGraphiQL();
        waitUntilPageFinishLoading();
    }

    /**
     * verify log history details
     */
    public void verifyLogHistory(String option, String day,String user){
        selectDate().click();
        selectDay(day).click();
        String visitDate = getDate("0");
        switch (option){
           case "DECLINED":
               String id = getDriver().findElement(By.xpath("//td/span[text()='"+visitDate+"']/parent::td/following-sibling::td[contains(text(),'"+user+"')]/following-sibling::td/div/div/span[contains(text(),'id')]")).getText();
               String actualValue = getDriver().findElement(By.xpath("//td/span[text()='"+visitDate+"']/parent::td/following-sibling::td[contains(text(),'"+user+"')]/following-sibling::td/div/div/span[contains(text(),'DECLINED')]")).getText();
               String startTime  = getDriver().findElement(By.xpath("//td/span[text()='"+visitDate+"']/parent::td/following-sibling::td[contains(text(),'"+user+"')]/following-sibling::td/div/div/span[contains(text(),'startTime')]")).getText();
               String endTime = getDriver().findElement(By.xpath("//td/span[text()='"+visitDate+"']/parent::td/following-sibling::td[contains(text(),'"+user+"')]/following-sibling::td/div/div/span[contains(text(),'endTime')]")).getText();
               softly().assertThat(option).as("DECLINED").isEqualTo(actualValue);
               Assert.assertTrue("StartTime is not displayed",!startTime.equals(""));
               Assert.assertTrue("EndTime is not displayed",!endTime.equals(""));
               Assert.assertTrue("id is not displayed",!id.equals(""));
               break;
           case "APPROVED":
               id = getDriver().findElement(By.xpath("//td/span[text()='"+visitDate+"']/parent::td/following-sibling::td[contains(text(),'"+user+"')]/following-sibling::td/div/div/span[contains(text(),'id')]")).getText();
               actualValue = getDriver().findElement(By.xpath("//td/span[text()='"+visitDate+"']/parent::td/following-sibling::td[contains(text(),'"+user+"')]/following-sibling::td/div/div/span[contains(text(),'APPROVED')]")).getText();
               startTime  = getDriver().findElement(By.xpath("//td/span[text()='"+visitDate+"']/parent::td/following-sibling::td[contains(text(),'"+user+"')]/following-sibling::td/div/div/span[contains(text(),'startTime')]")).getText();
               endTime = getDriver().findElement(By.xpath("//td/span[text()='"+visitDate+"']/parent::td/following-sibling::td[contains(text(),'"+user+"')]/following-sibling::td/div/div/span[contains(text(),'endTime')]")).getText();
               softly().assertThat(option).as("APPROVED").isEqualTo(actualValue);
               Assert.assertTrue("StartTime is not displayed",!startTime.equals(""));
               Assert.assertTrue("EndTime is not displayed",!endTime.equals(""));
               Assert.assertTrue("id is not displayed",!id.equals(""));
               break;
           case "Cancel":
               Assert.assertTrue("Cancelled visit details is not displayed",getDriver().findElement(By.xpath("//td/span[text()='"+visitDate+"']/parent::td/following-sibling::td[contains(text(),'"+user+"')]/self::td[text()='cancelled RSVP']")).isDisplayed());
               id = getDriver().findElement(By.xpath("//td/span[text()='"+visitDate+"']/parent::td/following-sibling::td[contains(text(),'"+user+"')]/self::td[text()='cancelled RSVP']/following-sibling::td/div/div/span[contains(text(),'id')]")).getText();
               startTime  = getDriver().findElement(By.xpath("//td/span[text()='"+visitDate+"']/parent::td/following-sibling::td[contains(text(),'"+user+"')]/self::td[text()='cancelled RSVP']/following-sibling::td/div/div/span[contains(text(),'startTime')]")).getText();
               endTime = getDriver().findElement(By.xpath("//td/span[text()='"+visitDate+"']/parent::td/following-sibling::td[contains(text(),'"+user+"')]/self::td[text()='cancelled RSVP']/following-sibling::td/div/div/span[contains(text(),'endTime')]")).getText();
               Assert.assertTrue("StartTime is not displayed",!startTime.equals(""));
               Assert.assertTrue("EndTime is not displayed",!endTime.equals(""));
               Assert.assertTrue("id is not displayed",!id.equals(""));
               break;
           case "Reschedule":
               id = getDriver().findElement(By.xpath("//td/span[text()='"+visitDate+"']/parent::td/following-sibling::td[contains(text(),'"+user+"')]/self::td[text()='Reschedule RSVP']/following-sibling::td/div/div/span[contains(text(),'id')]")).getText();;
               Assert.assertTrue("Reschedule details is not displayed in log history",getDriver().findElement(By.xpath("//td/span[text()='"+visitDate+"']/parent::td/following-sibling::td[contains(text(),'"+user+"')]/self::td[text()='Reschedule RSVP']")).isDisplayed());
               Assert.assertTrue("id is not displayed",!id.equals(""));
               break;
           default:
               Assert.fail("Invalid option");
        }
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
    //Locators
    private WebElement userDropdownSingout() { return button(By.id("user-dropdown-signout"));}
    private WebElement userDropdown() {
        return button(By.id("user-dropdown"));
    }
    private WebElement userListTable() {
        return button(By.cssSelector("[class='ui table']"));
    }
    private WebElement signoutButtonForNoRole(){return driver.findElement(By.xpath("//button[text()='Sign Out']")); }
    private WebElement notificationIconInHelpCentre() {
        WebElement notificationIcon=driver.findElement(By.id("notificationsNav"));
        return notificationIcon;
    }
    private WebElement helpNavDropdown() {
        WebElement help=driver.findElement(By.id("helpNav-dropdown"));
        return  help;
    }
    private  WebElement logo() {
        WebElement Logo=driver.findElement(By.xpath("//div/a[@class='logo']"));
        return Logo;
    }
    private WebElement contactSupport()  {
        WebElement contactSupport= text("Contact Support");
        return  contactSupport;
    }
    private WebElement helpCentre()    {
        WebElement helpCentre=driver.findElement(By.xpath("//span[text()='Help Center']"));
        return  helpCentre;
    }

    /**
     *
     * @return Web element
     */
    private WebElement selectDate(){
        return getDriver().findElement(By.cssSelector("div[class='ui compact selection dropdown _3SCFtXPZGOBhlAsaQm037_']>i[class='dropdown icon']"));
    }

    /**
     * select day from the dropdown
     * @param day
     * @return
     */
    private WebElement selectDay(String day){
        return getDriver().findElement(By.xpath("//span [text()='"+day+"']"));
    }
}
