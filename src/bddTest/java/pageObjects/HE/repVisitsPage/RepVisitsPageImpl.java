package pageObjects.HE.repVisitsPage;

import cucumber.api.DataTable;
import cucumber.api.java.cs.A;
import cucumber.api.java.gl.E;
import junit.framework.AssertionFailedError;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.COMMON.PageObjectFacadeImpl;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import utilities.File.CsvFileUtility;
import utilities.File.FileManager;
import utilities.GetProperties;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.fail;

public class RepVisitsPageImpl extends PageObjectFacadeImpl {

    private Logger logger;
    public static String formattedDate;

    public RepVisitsPageImpl() {
        logger = Logger.getLogger(RepVisitsPageImpl.class);
    }

    public void checkRepVisitsSubTabs(DataTable dataTable){
        navBar.goToRepVisits();
        List<String> list = dataTable.asList(String.class);
        for (String repVisitsSubItem : list) {
            Assert.assertTrue(repVisitsSubItem + " is not showing.",link(repVisitsSubItem).isDisplayed());
        }
    }
    public void checkHighSchoolJobFairAvailability(String highSchool, String fairName){
        waitUntilPageFinishLoading();
        //do{
            globalSearch.searchForInstitutions(highSchool);
            waitUntilPageFinishLoading();
            globalSearch.selectResult(highSchool);
            //communityFrame();
        /*try {
            Thread.sleep(4000);
        }catch (Exception ex){

        }*/
        driver.switchTo().defaultContent();
            driver.switchTo().frame(0);

        communityFrame();
        /*    WebDriverWait wait = new WebDriverWait(driver, 40);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), 'Check RepVisits Availability')]"))).click();
        *///wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(), 'Check RepVisits Availability')]"))).click();
        driver.findElement(By.xpath("//a[contains(text(), 'Check RepVisits Availability')]")).click();

            //link("Check Repvisits Availability").click();
            waitUntilPageFinishLoading();
        //}while (!link("Check Repvisits Availability").isDisplayed());

        getDriver().switchTo().defaultContent().findElement(By.xpath("//span[contains(text(), 'Fair')]")).click();
        if(getDriver().findElements(By.xpath("//span[contains(text(), '"+fairName+"')]/../following-sibling::div/button/span[contains(text(), 'Registered')]")).size()>=1) {

        }
        else{
            button(By.xpath("//span[contains(text(), '"+fairName+"')]/../following-sibling::div/button/span[contains(text(), 'Register')]")).click();
            button(By.xpath("//button[contains(text(), 'Yes, Submit Request')]")).click();
            waitUntilPageFinishLoading();
        }
    }

    public void selectFairForHE(String highSchool, String fairTitle){
        navBar.goToRepVisits();
        link("Search and Schedule").click();
        //text("Search by school name or location...").sendKeys(highSchool);
        driver.findElement(By.xpath("//input[@placeholder='Search by school name or location...']")).sendKeys(highSchool);
        driver.findElement(By.xpath("//i[@class='teal search large link icon Umyjf8WyIatPr6Rajw7y6']")).click();
        text(highSchool).click();
        text("Fairs").click();
        if (text(fairTitle).isDisplayed()){
            waitUntilPageFinishLoading();
            driver.findElement(By.xpath("//span[contains(text(), '"+fairTitle+"')]/../following-sibling::div/button[contains(text(),'Register')]")).click();
        }else
            Assert.assertFalse("Fair = "+fairTitle+" is not exist.", text(fairTitle).isDisplayed());

        button(By.xpath("//button[contains(text(), 'Yes, Submit Request')]")).click();
        waitUntilPageFinishLoading();
        // The confirmation toast blocks some upper screen controls, so we wait for it to clear.
        waitForUITransition();
        waitForUITransition();
    }

    public void checkHighSchoolPopUp(DataTable dataTable){
        List<Map<String, String>> entities = dataTable.asMaps(String.class, String.class);
        for (Map<String,String> school : entities){
            for (String key : school.keySet()) {
                switch (key) {
                    case "School Name":
                        String header = driver.findElement(By.xpath(".//h2[@class='ui header _1Lpvqe0tqVOX5RBm2576B2']")).getText();
                        String a = school.get(key);
                        Assert.assertTrue("School name was not found in header.", header.contains(school.get(key)));
                        break;
                    case "High School Contact:":
                        String contactLink = driver.findElement(By.className("_16fRAQOOUtmia2wftYHDhf")).getText();
                        Assert.assertTrue("Contact name was not found in header.",contactLink.contains(school.get(key)));
                        break;
                    default:
                        WebElement label = driver.findElement(By.xpath("//div[contains(text(),'" + key + "')]"));
                        WebElement item = getParent(label);
                        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.className("_2WVz9-VTbgicuNzZo9Vc_o")));
                        String content = item.findElement(By.className("_2WVz9-VTbgicuNzZo9Vc_o")).getText();
                        Assert.assertTrue(key + " was not found in the popup",content.contains(school.get(key)));
                }
            }
        }
        validateInfolink();
    }

    public void verifyCalendarPageforFairs(String school,String time,String date) {
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        calendar().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        collegeFairTextBoxInCalendarPage().click();
        waitUntilPageFinishLoading();
        visitCheckBoxInCalendarPage().click();
        waitUntilPageFinishLoading();
        String month = month(date);
        String currentMonth = currentMonthInCalendarPage().getText();
        String selectMonth[] = currentMonth.split(" ");
        String Month = selectMonth[0];
        while (!month.equals(Month)) {
            nextMonthButton().click();
            waitForUITransition();
            currentMonth = currentMonthInCalendarPage().getText();
            selectMonth = currentMonth.split(" ");
            Month = selectMonth[0];
        }
        WebElement appointmentSlot = getDriver().findElement(By.xpath("//span[text()='"+time+"']/following-sibling::span[text()='"+school+"']"));
        Assert.assertTrue("Appointment Slot time and university is not displayed",appointmentSlot.isDisplayed());
        jsClick(appointmentSlot);
        waitUntilPageFinishLoading();
    }

    public void removeFairAppointmentfromCalendar(){
        waitForUITransition();
        jsClick(driver.findElement(By.xpath("//input[@aria-label='Internal Notes']")));
        driver.findElement(By.xpath("//input[@aria-label='Internal Notes']")).sendKeys(Keys.PAGE_DOWN);
        Assert.assertTrue("Cancel This Visit is not displayed",driver.findElement(By.xpath("//button/span[text()='Cancel This Fair']")).isDisplayed());
        button("Cancel This Fair").click();
        waitUntilPageFinishLoading();
        driver.findElement(By.id("cancel-message")).click();
        waitUntilPageFinishLoading();
        driver.findElement(By.id("cancel-message")).sendKeys(Keys.PAGE_DOWN);
        driver.findElement(By.id("cancel-message")).sendKeys("by QA");
        button("Yes, Cancel Fair").click();
        waitUntilPageFinishLoading();
        // This wait is necessary for the toast to disappear after canceling the fair.
        waitForUITransition();
        waitForUITransition();
    }

    public void verifyExportButtonInCalendar(){
        waitUntilPageFinishLoading();
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        link("Calendar").click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        Assert.assertTrue("Export button is Enabled in Calendar page",driver.findElement(By.xpath("//button[@class='ui teal basic disabled button _1I0GHfcjpniiDr2MOWxpxw _3Rc-fBQEQJr4FpMhLBYL0m']")).isDisplayed());
    }

    public void verifyExportButtonisEnabledInCalendar(){
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        link("Calendar").click();
        waitForUITransition();
        waitUntilPageFinishLoading();
        Assert.assertTrue("Export button is not enabled",button("Export").isEnabled());
    }

    public void exportAppointmentsInCalendarPage(String startDate,String endDate){
        button("Export").click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("Export Settings text is not displayed in the Export button",driver.findElement(By.xpath("//div/span[text()='Export Settings']")).isDisplayed());
        Assert.assertTrue("From Text is not displayed in the Export button",driver.findElement(By.xpath("//span[text()='From']")).isDisplayed());
        Assert.assertTrue("To text is not displayed in the Export button",driver.findElement(By.xpath("//span[text()='To']")).isDisplayed());
        String From = selectdateforExportAppointmentsIncalendar(startDate);
        setDateforCalendarPage(From, "From");
        String To = selectdateforExportAppointmentsIncalendar(endDate);
        setDateforCalendarPage(To, "To");
        Assert.assertTrue("Download button is not displayed",button("Download").isDisplayed());
        button("Download").click();
        waitForUITransition();
        waitUntilPageFinishLoading();
    }

    public void verifyDownloadedCsvFileInCalendar(String csvFile,DataTable headers){
        List<String> expectedHeaderValues=headers.asList(String.class);
        String home = System.getProperty("user.home");
        String[] actualHeaderValues = CsvFileUtility.getCsvHeaders(String.format("%s/Downloads/%s",home,csvFile));
        for(int i=0; i<expectedHeaderValues.size(); i++){
            Assert.assertTrue(String.format("The Csv header is not correct, actual: %s, expected: %s ",
                    actualHeaderValues[i], expectedHeaderValues.get(i)),
                    actualHeaderValues[i].contains(expectedHeaderValues.get(i)));
        }
    }

    public void deleteDownloadedFileInCalendar(String filePath){
        String home = System.getProperty("user.home");
        FileManager.deleteFile(String.format("%s/Downloads/%s",home,filePath));
    }
  
   public  void verifyPillsIsPresent(String school,String startDate,String time){
        visit().click();
        waitForUITransition();
        WebElement schoolName=driver.findElement(By.xpath("//div/a[text()='"+school+"']"));
        waitUntilElementExists(schoolName);
        Assert.assertTrue("school is not displayed",schoolName.isDisplayed());
        waitUntilElementExists(goToDate());
        String gotoDate = getSpecificDate(startDate);
        setDate(gotoDate, "other");
        String visitTime = pageObjects.HS.repVisitsPage.RepVisitsPageImpl.StartTime;
        String visitDate=getMonthandDate(startDate);
        WebElement button= driver.findElement(By.xpath("//span[text()='"+visitDate+"']/parent::th/ancestor::thead/following-sibling::tbody/tr//td//div/button[text()='"+visitTime+"']"));
        Assert.assertTrue("Availability is not displayed",button.isDisplayed());
    }

    public void verifyPillsIsNotPresent(String school,String startDate,String time){
        visit().click();
        waitUntilPageFinishLoading();
        WebElement schoolName=driver.findElement(By.xpath("//div/a[text()='"+school+"']"));
        waitUntilElementExists(schoolName);
        Assert.assertTrue("school is not displayed",schoolName.isDisplayed());
        waitUntilElementExists(goToDate());
        String gotoDate = getSpecificDate(startDate);
        setDate(gotoDate, "other");
        String visitTime = pageObjects.HS.repVisitsPage.RepVisitsPageImpl.StartTime;
        String visitDate=getMonthandDate(startDate);
        List<WebElement> button= driver.findElements(By.xpath("//span[text()='"+visitDate+"']/parent::th/ancestor::thead/following-sibling::tbody/tr//td//div/button[text()='"+visitTime+"']"));
        if(button.size()==0)
        {
            logger.info("appointment is not displayed");
        }else{logger.info("appointment is displayed");}
    }

    private void validateInfolink(){
        Assert.assertTrue("Text 'For more information' is not displayed", text("For more information:").isDisplayed());
        WebElement item = getParent(text("For more information:"));
        item.findElement(By.tagName("a")).click();
        Assert.assertTrue("Did not end up in Community!", driver.getCurrentUrl().contains("counselor-community/institution"));
    }

    public String selectdateforExportAppointmentsIncalendar(String addDays)
    {
        String DATE_FORMAT_NOW = "MMMM d yyyy";
        Calendar cal = Calendar.getInstance();
        int days=Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }

    public void switchToSupportApp(){
        String HEWindow = driver.getWindowHandle();
        String supportWindow = null;
        Set<String> windows = driver.getWindowHandles();
        for(String thisWindow : windows){
            if(!thisWindow.equals(HEWindow)){
                supportWindow = thisWindow;
            }
        }
        driver.close();
        driver.switchTo().window(supportWindow);
        waitUntilPageFinishLoading();
    }

    public void verifyLoginMessageInHomPage(String message){
        waitForUITransition();
        waitForUITransition();
        waitForUITransition();
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
        String originalMessage = driver.findElement(By.xpath("//div[@class='_1iOWqkacLvWSlz2RWS4WYl']/span")).getText();
        Assert.assertTrue("Logged in message is not displayed",originalMessage.equals(message));
    }

    public void postMessageInHomePage(String message){
        waitUntilPageFinishLoading();
        driver.findElement(By.id("js-main-nav-counselor-community-menu-link")).click();
        waitUntilPageFinishLoading();
        WebElement element=driver.findElement(By.xpath("//iframe[@class='_2ROBZ2Dk5vz-sbMhTR-LJ']"));
        driver.switchTo().frame(element);
        waitForUITransition();
        Assert.assertTrue("Message text box is not displayed",driver.findElement(By.id("edit-body")).isDisplayed());
        driver.findElement(By.id("edit-body")).click();
        driver.findElement(By.id("edit-body")).sendKeys(message);
        Assert.assertTrue("Message text box is not displayed",driver.findElement(By.id("edit-save")).isDisplayed());
        driver.findElement(By.id("edit-save")).click();
        waitUntilPageFinishLoading();
        driver.switchTo().defaultContent();
    }

    public void verifySearchAndSchedulePage() {
        navBar.goToRepVisits();
        getSearchAndScheduleBtn().click();
        WebElement dateBar = driver.findElement(By.className("_2Y4XoXCJpDOFoe0UYkEn-I"));
        // These calendar controls have been moved to only appear after a search, this is covered by MATCH-2133.
        // Move these validations into that ticket when automated.
//        Assert.assertTrue("Previous Week button is not present!",dateBar.findElement(By.cssSelector("[aria-label='Previous week']")).isDisplayed());
//        Assert.assertTrue("Next Week button is not present!",dateBar.findElement(By.cssSelector("[aria-label='Next week']")).isDisplayed());
//        Assert.assertTrue("Calendar button is not present!",dateBar.findElement(By.className("calendar")).isDisplayed());
//        Assert.assertTrue("Placeholder text for search box was not present!", textbox("Enter a school name or location").isDisplayed());
    }

    public void searchforHighSchool(String schoolName) {
        navBar.goToRepVisits();
        getSearchAndScheduleBtn().click();
        getSearchBox().sendKeys(schoolName);
        getSearchButton().click();
    }

    public void searchSchoolForSupport(String schoolName){
        waitUntilPageFinishLoading();
        getSchoolFindOutInTheSearch().clear();
        getSchoolFindOutInTheSearch().sendKeys(schoolName);
        waitUntilElementExists(link(By.id("global-search-box-item-0")));
        link(By.id("global-search-box-item-0")).click();
        waitUntilPageFinishLoading();
    }

    public void verifyEmptyContactPage(){
        navBar.goToRepVisits();
        getContactsBtn().click();
        Assert.assertTrue("Contact Header is not displayed",text("").isDisplayed());
        Assert.assertTrue("Instruction text is not displayed",text("").isDisplayed());

    }
    public void verifyFullContactPage(){
        navBar.goToRepVisits();
        getContactsBtn().click();
        Assert.assertTrue("Contact Header is not displayed",driver.findElement(By.xpath("//h1[@class='ui header _2GIsNevIB_s082IZwcYen3']")).isDisplayed());
        Assert.assertTrue("Instruction text is not displayed",driver.findElement(By.xpath("//div[@class='sub header _240ldPuujUDvP5vNIGw15H']")).isDisplayed());
        List<WebElement> searchedValueOfName = driver.findElements(By.className("_1ijSBYwG-OqiUP1_S7yMUN"));
        int size = searchedValueOfName.size();
        Assert.assertTrue("RepVisits contact are not displayed",size>0);
    }
    public void verifyContactDetails(DataTable dataTable){
        navBar.goToRepVisits();
        getContactsBtn().click();
        List<String> list = dataTable.asList(String.class);
        for (String repVisitsSubItem : list) {
            Assert.assertTrue(repVisitsSubItem + " is not showing.",text(repVisitsSubItem).isDisplayed());
        }
    }
    public void verifyFullorEmpty(){
    try{ if(text("Welcome to Contacts").isDisplayed())
    {
        logger.info("you have no Contacts");
    }
        else if(driver.findElement(By.xpath("//span[text()='Show More']")).isDisplayed()) {
        while (driver.findElement(By.xpath("//span[text()='Show More']")).isDisplayed()){
            driver.findElement(By.xpath("//span[text()='Show More']")).click();
        }}else{}}
        catch(Exception e){}}
    public void verifyinvalidcontact(String invalidData){
        getSearchBoxforContact().clear();
        getSearchBoxforContact().sendKeys(invalidData);
        Assert.assertTrue("the message of 'Your search did not return any contacts.' is not displayed",text("Your search did not return any contacts.").isDisplayed());
    }

    public void sortingContacts()
    {
        navBar.goToRepVisits();
        getContactsBtn().click();
        driver.findElement(By.xpath("//input[@name='contacts-search']")).clear();
        ArrayList<String> original=new ArrayList<>();
        List<WebElement> elements=driver.findElements(By.xpath("//tr[@class='_1ijSBYwG-OqiUP1_S7yMUN']/td[2]/div[1]"));
        for(WebElement we:elements)
        {
            original.add(we.getText());
        }
        ArrayList<String> sortedList=new ArrayList<>();
        for(String s:original)
        {
            sortedList.add(s);
        }
        Collections.sort(sortedList);
        Assert.assertTrue("Contact are not ABC order by Institution Name and then Contact Last Name",sortedList.equals(original));
    }

    public void validatingthePaginationof25Contacts()
    {
        int count;
        navBar.goToRepVisits();
        getContactsBtn().click();
        waitUntil(ExpectedConditions.visibilityOf(button("EXPORT CONTACTS")));
        Assert.assertTrue("Contacts is not displayed",driver.findElement(By.xpath("//tr[@class='_1ijSBYwG-OqiUP1_S7yMUN']")).isDisplayed());
         count=driver.findElements(By.xpath("//tr[@class='_1ijSBYwG-OqiUP1_S7yMUN']")).size();
       try{ logger.info(count);}catch(Exception e){}
      while(count>=25)
       {
           if(driver.findElement(By.xpath("//span[text()='Show More']")).isDisplayed())
           {
               driver.findElement(By.xpath("//span[text()='Show More']")).click();
           }validatingthePaginationof25Contacts();
       }
    }

    public void searchforContact(String institutionName){
        navBar.goToRepVisits();
        getContactsBtn().click();
        getSearchBoxforContact().sendKeys(institutionName);
        waitForUITransition();
        //_1ijSBYwG-OqiUP1_S7yMUN is the class for the rows or the results table.
        String schoolName = driver.findElement(By.className("_1ijSBYwG-OqiUP1_S7yMUN")).findElement(By.xpath(".//div[@class='_2ZIfaO8qcJzzQzgSfH1Z8h']")).getText();
        Assert.assertTrue("The specified school name is not displayed.  Expected: " + institutionName + ", Actual: " + schoolName,schoolName.equalsIgnoreCase(institutionName));
    }

    public void partialsearchforContact(String institutionName){
        navBar.goToRepVisits();
        getContactsBtn().click();
        getSearchBoxforContact().sendKeys(institutionName);
        waitUntilPageFinishLoading();
        List<WebElement> searchedValueOfinstitutionName = driver.findElements(By.cssSelector("div[class='_2ZIfaO8qcJzzQzgSfH1Z8h']"));
        for(int i=0;i<searchedValueOfinstitutionName.size();i++){
            String value = searchedValueOfinstitutionName.get(i).getText();
            Assert.assertTrue("Partial matching on institution name is not available",value.toLowerCase().contains(institutionName.toLowerCase()));
        }
    }
    public void selectHighSchoolFromIntermediateSearchResults(String schoolName, String location) {
        driver.findElement(By.xpath(String.format(".//td[text()='%s']/preceding::td/a[text()='%s']"
                ,location, schoolName))).click();
        waitUntilPageFinishLoading();
    }

    public void selectHighSchoolFromResults(String schoolName) {
        waitUntilPageFinishLoading();
        getDriver().findElement(By.xpath("//td[@class='D8iaokkmOTXAhIkOIzngL']/a[text()='" + schoolName + "']")).click();
        waitUntilPageFinishLoading();
    }

    public void viewMapPlugin() {
        getMapButton().click();
        waitUntilPageFinishLoading();
    }

    public void verifyOverviewPage(){
        navBar.goToRepVisits();
        getOverviewBtn().click();
        waitUntilElementExists(getComingSoonMessageInOverviewPage());
        Assert.assertTrue("Coming Soon message is not displaying",
                getComingSoonMessageInOverviewPage().getText().equals("The Overview Dashboard is coming soon. It will provide a quick view of your upcoming appointments and most recent notifications."));
    }

    public void verifyCheckRepVisitsAvailabilityButton(){
        waitUntil(ExpectedConditions.frameToBeAvailableAndSwitchToIt(0));
        waitForUITransition();
        Assert.assertTrue("Check RepVisits Availability Button is not present", getCheckRepVisitsAvailabilityButton().isDisplayed());
        waitUntil(ExpectedConditions.visibilityOf(getCheckRepVisitsAvailabilityButton()));
        JavascriptExecutor executor = getDriver();
        executor.executeScript("arguments[0].click();",getCheckRepVisitsAvailabilityButton());
        //getCheckRepVisitsAvailabilityButton().click();
        driver.switchTo().defaultContent();
        waitUntil(ExpectedConditions.visibilityOf(getRepVisitsAvailabilitySidebar()));
        Assert.assertTrue("RepVisits Availability Sidebar is not displaying.", getRepVisitsAvailabilitySidebar().isDisplayed());
    }

    public void navigatetoRepVisits()
    {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
    }

    public void selectAllRepVisitsUser(String option){
        driver.findElement(By.xpath("//ul[@class='ui pointing secondary fourth menu']//a/span[text()='Availability Settings']")).click();
        waitUntilElementExists(saveChanges());
        driver.findElement(By.xpath("//label[text()='"+option+"']")).click();
        button("Save Changes").click();
    }

    public void clickRegistrationButton(String fairName) {
        getRegistrationButton(fairName).click();
    }

    public void registerFair(String fairName) {
        clickRegistrationButton(fairName);
        submitRequestButton().click();
    }

    public void verifyConfirmationPopup(String highSchoolName) {
        Assert.assertTrue("The fair date is not displayed", getFairDate().isDisplayed());
        String startDate = getStartEndTimeAndTimeZone().getText().split("-")[0];
        String endDate = getStartEndTimeAndTimeZone().getText().split("-")[1].split(" ")[0];
        String timeZone = getStartEndTimeAndTimeZone().getText().split("-")[1].split(" ")[1];
        Assert.assertTrue("The Start time does not have a correct format: " + startDate, startDate.matches("([0-9])?([0-9]):([0-9])?([0-9])"));
        Assert.assertTrue("The End time does not have a correct format: " + endDate, endDate.matches("([0-9])?([0-9]):([0-9])?([0-9])([ap])([m])"));
        Assert.assertTrue("The time zone does not have a correct format: " + timeZone, timeZone.matches("([ABCDEFGHIJKLMNOPQRSTUVWXYZ])([ABCDEFGHIJKLMNOPQRSTUVWXYZ])([ABCDEFGHIJKLMNOPQRSTUVWXYZ])"));
        Assert.assertTrue("The High School name is not displayed", requestText().getText().contains(highSchoolName));
        Assert.assertTrue("The confirmation button's text is not correct: " + submitRequestButton().getText(), submitRequestButton().getText().equals("YES, SUBMIT REQUEST"));
        Assert.assertTrue("The cancel button's text is not correct", cancelButton().getText().equals("CANCEL"));
    }

    public void closeFairRequestPopup() {
        cancelButton().click();
    }

    public void clickFairsTab() {
        fairsTab().click();
    }

    public void openFairsInChckRepVisitsAv() {
        waitUntil(ExpectedConditions.urlContains("/counselor-community/institution/"));
        waitUntil(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.tagName("iframe")));
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//a[text() = 'Check RepVisits Availability']"), 1));
        getCheckRepVisitsAvailabilityButton().click();
        getDriver().switchTo().defaultContent();
        fairsTab().click();
        waitUntil(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.ui.medium.inverted.loader")));
    }

    public void verifySuccessMessageWithAutoApprovals() {
        Assert.assertTrue("The sucess message for fairs with Auto Approval enabled is not displayed",
                upperMessage().getText().trim().equals("Fair registration confirmed! Your request has been automatically " +
                        "confirmed by the high school."));
    }

    public void verifySuccessMessageWithoutAutoApprovals() {
        Assert.assertTrue("", (upperMessage().getText().trim().equals("Fair registration requested! You will " +
                "receive an email notification when your request has been confirmed.")) || (upperMessage().getText().
                trim().equals("Fair registration confirmed! Your request has been automatically confirmed by the high school.")));
    }

    public void verifyFairInCalendar(String fairName, String date, String time) {
        if(date.contains("In") && date.contains("day")){
            int relativeDays =  Integer.parseInt(date.replaceAll("[^0-9]",""));
            date = getRelativeDate(relativeDays).split(" ")[1]+" "+
                    getRelativeDate(relativeDays).split(" ")[0];
        }
        waitUntil(ExpectedConditions.elementToBeClickable(getCalendarBtn()));
        getCalendarBtn().click();
        waitUntil(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.ui.medium.inverted.loader")));
        waitUntil(ExpectedConditions.elementToBeClickable(rightCalendarHeaderDate()));
        if (!rightCalendarHeaderDate().getText().equals(date.split(" ")[0])) {
            pressCalendarArrowUntil("right", date.split(" ")[0], 10);
        }
        try{
            driver.findElements(By.xpath(String.format(".//span[text()='%s']",fairName)));
            driver.findElements(By.xpath(String.format(".//span[text()='%s']/preceding-sibling::span[text()='%s']",
                    fairName,time)));
        } catch(Exception e){
            throw new AssertionError(String.format("The fair with name: %s and time: %s is not displayed in the calendar" +
                            ", error: %s"
                    ,fairName,time, e.toString()));
        }
    }

    private String getRelativeDate(int addDays){
        String day;
        Calendar relativeDate = getDeltaDate(addDays);
        day =getDay(relativeDate);
        if(day.startsWith("0")){
            day = day.replace("0","");
        }
        return day+" "+getMonth(relativeDate)+" "+getYear(relativeDate);
    }

    public void pressCalendarArrowUntil(String side, String month, int tries) {
        for (int i = 0; i < tries; i++) {
            if (!rightCalendarHeaderDate().getText().split(" ")[0].equals(month)) {
                if (side.equals("right")) {
                    rightCalendarRightButton().click();
                    waitUntil(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.ui.medium.inverted.loader")));
                } else if (side.equals("left")) {
                    rightCalendarLeftButton().click();
                    waitUntil(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.ui.medium.inverted.loader")));
                }
            }
        }
    }

    public void verifyFairInQuickView(String schoolName, String date, String hour) {
        if(date.contains("In") && date.contains("day")){
            int relativeDays =  Integer.parseInt(date.replaceAll("[^0-9]",""));
            date = getRelativeDate(relativeDays).split(" ")[1]+" "+
                    getRelativeDate(relativeDays).split(" ")[0]+" "+hour;
        }
        boolean result = false;
        getSearchAndScheduleBtn().click();
        getSearchBox().sendKeys(schoolName);
        getSearchButton().click();
        selectHighSchoolFromResults(schoolName);
        waitUntilPageFinishLoading();
        calendarIcon().click();
        pressMiniCalendarArrowUntil("right", date.split(" ")[0], 10);
        miniCalendarDayCell(date.split(" ")[1]).click();
        waitUntil(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.ui.medium.inverted.loader")));
        for (WebElement fairElement : quickViewFairsList()) {
            if (fairElement.getText().contains(schoolName) && fairElement.getText().contains(date.split(" ")[2].toLowerCase())) {
                result = true;
            }
        }
        Assert.assertTrue("The fair is not displayed in the Quick View", result);
    }

    public void pressMiniCalendarArrowUntil(String direction, String month, int tries) {
        for (int i = 0; i < tries; i++) {
            if (!miniCalendarHeader().getText().split(" ")[0].equals(month)) {
                if (direction.equals("right")) {
                    getDriver().findElement(By.cssSelector("span[aria-label=\"Next Month\"]")).click();
                } else if (direction.equals("left")) {
                    getDriver().findElement(By.cssSelector("span[aria-label=\"Previous Month\"]")).click();
                }
            }
        }
    }

    public void openInstitutionByURLPartID(String URLPartID) {
        waitUntilPageFinishLoading();
        navBar.goToCommunity();
        getDriver().get(GetProperties.get("he.app.url") + "counselor-community/institution-hs-id/" + URLPartID);
        waitUntilPageFinishLoading();
    }

    public void openVisitsInChckRepVisitsAv() {
        waitUntil(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.tagName("iframe")));
        waitUntil(ExpectedConditions.elementToBeClickable(getCheckRepVisitsAvailabilityButton()));
        getCheckRepVisitsAvailabilityButton().click();
        getDriver().switchTo().defaultContent();
        visitsTab().click();
        waitUntil(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.ui.medium.inverted.loader")));
        waitUntilPageFinishLoading();
    }

    public void clickVisitsTab() {
        visitsTab().click();
    }

    public void clickVisitPill(String date) {
        int visitPosition = 0;
        goToDateButton().click();
        pressMiniCalendarArrowUntil("right", date.split(" ")[0], 10);
        miniCalendarDayCell(date.split(" ")[1]).click();
        for (int i = 0; i < 7; i++) {
            if (headerWeekDays().get(i).getText().contains(date.split(" ")[1])) {
                visitPosition = i + 1;
                break;
            }
        }
        for (WebElement visitRow : visitsRows()) {
            if (visitRow.findElement(By.cssSelector("td:nth-of-type(" + visitPosition + ") button")).getText().equals(date.split(" ")[2].toLowerCase())) {
                visitRow.findElement(By.cssSelector("td:nth-of-type(" + visitPosition + ") button")).click();
                break;
            }
        }
    }

    public void clickVisitPillInSearchAndSchedule(String date) {
        int visitPosition = 0;
        searchAndScheduleCalendarIcon().click();
        pressMiniCalendarArrowUntil("right", date.split(" ")[0].split(",")[0], 10);
        miniCalendarDayCell(date.split(",")[1].split(" ")[1]).click();
        String month = date.split(",")[1].split(" ")[0];
        switch (month) {
            case "Monday" : visitPosition = 2;
                break;
            case "Tuesday" : visitPosition = 3;
                break;
            case "Wednesday" : visitPosition = 4;
                break;
            case "Thursday" : visitPosition = 5;
                break;
            case "Friday" : visitPosition = 6;
                break;
        }
        WebElement dayColumn = getDriver().findElement(By.cssSelector("table.ui.celled.padded.table tbody td.center:nth-of-type(" + visitPosition + ")"));
        List<WebElement> dayElements = dayColumn.findElements(By.cssSelector("div.item button"));
        for (WebElement visitItem : dayElements) {
            if (visitItem.getText().equals(date.split(",")[2])) {
                visitItem.click();
            }
        }
    }

    public void registerVisit(String date) {
        clickVisitPillInSearchAndSchedule(date);
        submitRequestButton().click();
    }

    public void verifyVisitSuccessMessageWithAutoApprovals() {
        Assert.assertTrue("The success message for visits with auto approvals is not displayed", upperMessage().getText().equals("Visit confirmed! Your request has been automatically confirmed by the high school."));
    }

    public void verifyVisitInCalendar(String date) {
        boolean result = false;
        waitUntil(ExpectedConditions.elementToBeClickable(getCalendarBtn()));
        getCalendarBtn().click();
        waitUntil(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.ui.medium.inverted.loader")));
        waitUntil(ExpectedConditions.elementToBeClickable(rightCalendarHeaderDate()));
        if (!rightCalendarHeaderDate().getText().equals(date.split(" ")[0])) {
            pressCalendarArrowUntil("right", date.split(" ")[0], 10);
        }

        for (WebElement eventRow : eventsRows(date.split(" ")[1])) {
            if (eventRow.findElements(By.xpath("/div/a")).size() > 0) {
                eventRow.findElements(By.xpath("/div/a")).get(0).click();
                for (WebElement overlayEvent : overlayEventsList()) {
                    if (overlayEvent.findElement(By.cssSelector("span.rbc-event-time")).getText().equals(date.split(" ")[2])) {
                        result = true;
                        break;
                    }
                }
            } else {
                for (int i = 1; i < 3; i++) {
                    if (getDateCell(date.split(" ")[1], date.split(" ")[2], i).isDisplayed()) {
                        result = true;
                        break;
                    }
                }
            }
        }

//        if (showMoreLink().isDisplayed()) {
//            showMoreLink().click();
//
//        }
        Assert.assertTrue("The visit is not displayed in the calendar", result);
    }

    public void verifyVisitInQuickView(String schoolName, String date) {
        boolean result = false;
        searchforHighSchool(schoolName);
        selectHighSchoolFromResults(schoolName);
        getSearchAndScheduleBtn().click();
        waitUntil(ExpectedConditions.elementToBeClickable(searchAndScheduleCalendarIcon()));
        searchAndScheduleCalendarIcon().click();
        pressMiniCalendarArrowUntil("right", date.split(" ")[0].split(",")[0], 10);
        miniCalendarDayCell(date.split(" ")[1]).click();
        waitUntil(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.ui.medium.inverted.loader")));
        for (WebElement visitElement : quickViewEventsList()) {
            if (visitElement.getText().contains(schoolName) && visitElement.getText().contains(date.split(" ")[2].toLowerCase())) {
                result = true;
            }
        }
        Assert.assertTrue("The visit is not displayed in the Quick View", result);
    }

    //locators
    public void verifyHSSpecialInstructions(String instructions)
    {
        Assert.assertTrue("High School specila instructions are not matching/available",getParent(driver.findElement(By.className("kuh1rp3g-UeGhggKqCdPA"))).findElement(By.cssSelector("div:nth-child(4)")).getText().contains(instructions));
    }
    public void selectSchoolFromMap(String schoolName) {
        button(schoolName).click();
        waitForUITransition();
        waitUntilPageFinishLoading();
    }

    public void clickUpgradeButton(){
        driver.findElement(By.xpath("//div[@class='seven wide column _2I5Wf1vjM_1kmY7BHT_G9k']//div/button/span[text()='UPGRADE']")).click();
        waitUntilPageFinishLoading();
    }
    public void searchSchoolinRepVisits(String school)
    {
        navBar.goToRepVisits();
        driver.findElement(By.xpath("//input[@placeholder='Search by school name or location...']")).sendKeys(school);
        waitUntilElementExists(search());
        driver.findElement(By.xpath("//button[@class='ui button']")).click();
        WebElement schoolName=driver.findElement(By.xpath("//td/a[contains(text(),'"+school+"')]"));
        waitUntilElementExists(schoolName);
        Assert.assertTrue("school is not displayed",driver.findElement(By.xpath("//a[contains(text(),'"+school+"')]")).isDisplayed());
        driver.findElement(By.xpath("//a[contains(text(),'"+school+"')]")).click();
    }


/*    public void visitsSchedule(String school,String date,String time)
    {
        driver.findElement(By.xpath("//span[text()='Visits']")).click();
        WebElement schoolName=driver.findElement(By.xpath("//a[text()='"+school+"']"));
        waitUntilElementExists(schoolName);
        Assert.assertTrue("school is not displayed",driver.findElement(By.xpath("//div/a[text()='"+school+"']")).isDisplayed());
        waitUntilElementExists(goToDate());
        driver.findElement(By.xpath("//button[text()='Go To Date']")).click();
        setSpecificDate(7);
        driver.findElement(By.xpath("//div/div/button[text()='"+time+"']")).click();
    }*/

    public void verifyUpgradePopupAndInformations(DataTable dataTable){
        List<Map<String,String>> entities = dataTable.asMaps(String.class,String.class);
        for (Map<String,String> UpgradeInformationPopup : entities ) {
            for (String key : UpgradeInformationPopup.keySet()) {
                switch (key) {
                    case "First Name":
                        String actualFirstName = driver.findElement(By.id("field13")).getAttribute("value");
                        Assert.assertTrue("First Name was not as expected.", actualFirstName.contains(UpgradeInformationPopup.get(key)));
                        break;
                    case "Last Name":
                        String actualLastName = driver.findElement(By.id("field14")).getAttribute("value");
                        Assert.assertTrue("Last Name was not as expected.", actualLastName.equals(UpgradeInformationPopup.get(key)));
                        break;
                    case "Work Email Address":
                        String actualEmailAddress = driver.findElement(By.id("field12")).getAttribute("value");
                        Assert.assertTrue("Work Email Address was not as expected.", actualEmailAddress.equals(UpgradeInformationPopup.get(key)));
                        break;
                    case "Phone":
                        String actualPhone = driver.findElement(By.id("field15")).getAttribute("value");
                        Assert.assertTrue("Phone was not as expected.", actualPhone.equals(UpgradeInformationPopup.get(key)));
                        break;
                    case "School / Institution Name":
                        String actualSchoolInstitutionName = driver.findElement(By.id("field16")).getAttribute("value");
                        Assert.assertTrue("School / Institution Name was not as expected.", actualSchoolInstitutionName.equals(UpgradeInformationPopup.get(key)));
                        break;
                    case "Message":
                        String actualMessage = driver.findElement(By.id("field18")).getText();
                        Assert.assertTrue("Messages was not as expected.", actualMessage.equals(UpgradeInformationPopup.get(key)));
                        break;
                }
            }
            button("Request Information").click();
            Assert.assertTrue("success message is not displayed",driver.findElement(By.xpath("//span[text()='Thanks!']")).isDisplayed());
            driver.findElement(By.xpath("//div[@id='upgrade-form']/i[@class='close icon']")).click();
        }

    }

    public void verifyUpgradeMessageInRecommendationspage()
      {
          navBar.goToRepVisits();
          waitUntilElementExists(getRecommendationsBtn());
          getRecommendationsBtn().click();
          waitUntilPageFinishLoading();
          Assert.assertTrue("'Premium Feature' text is not displayed",text("Premium Feature").isDisplayed());
          Assert.assertTrue("'UPGRADE' button is not displayed",button("UPGRADE").isDisplayed());
          Assert.assertTrue("'Lock' Icon is not displayed",driver.findElement(By.xpath("//img[@alt='locked']")).isDisplayed());
      }


   public void verifyUpgradeMessageInContactspage()
   {
       navBar.goToRepVisits();
       waitUntilElementExists(getContactsBtn());
       getContactsBtn().click();
       waitUntilPageFinishLoading();
       Assert.assertTrue("'Premium Feature' text is not displayed",text("Premium Feature").isDisplayed());
       Assert.assertTrue("'UPGRADE' button is not displayed",button("UPGRADE").isDisplayed());
       Assert.assertTrue("'Lock' Icon is not displayed",driver.findElement(By.xpath("//img[@alt='locked']")).isDisplayed());
   }
    public void verifySearchResultOfSearchAndSchedule(DataTable dataTable){
        DataTable transposedTable = dataTable.transpose();
        Map<String,String> searchMap = transposedTable.asMap(String.class, String.class);
        Set<String> searchCategory = searchMap.keySet();
        for (String category : searchCategory ) {
            searchforHighSchool(searchMap.get(category));
            try {
                Assert.assertTrue("HS data is showing while searching through "+category+" in Search And Schedule page", driver.findElement(By.xpath("//table[@class='ui very basic table']")).isDisplayed());
            } catch (NoSuchElementException nsee){
                Assert.assertTrue("HS data is not showing while searching through "+category+" in Search And Schedule page", driver.findElement(By.xpath("//span[contains(text(),'No results found')]")).isDisplayed());
            }
        }
    }
    public void selectDate(String date)
    {
        String[] value=date.split(" ");
        driver.findElement(By.xpath("//button[text()='Go To Date']")).click();
        String yearandmonth=value[0]+" "+value[2];
        String element=driver.findElement(By.xpath("//div[@class='DayPicker-Month']/div")).getText();
        if(yearandmonth.equals(element))
        {
            driver.findElement(By.xpath("//div[text()='"+value[1]+"']")).click();
        }else{
            while(!yearandmonth.equals(element))
            {
                driver.findElement(By.xpath("//div[@class='DayPicker-NavBar']/span[2]")).click();
                element=driver.findElement(By.xpath("//div[@class='DayPicker-Month']/div")).getText();
            }
            driver.findElement(By.xpath("//div[text()='"+value[1]+"']")).click();
        }}

    public void setDate(String inputDate) {

                        String[] parts = inputDate.split(" ");
        String calendarHeading = parts[0] + " " + parts[2];

        findMonth(calendarHeading);
        clickOnDay(parts[1]);
       waitUntilPageFinishLoading();
            }


    public void setSpecificDate(int addDays) {
        String DATE_FORMAT_NOW = "MMMM dd yyyy";
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, addDays);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        String[] parts = currentDate.split(" ");
        String calendarHeading = parts[0] + " " + parts[2];
        findMonth(calendarHeading);
        clickOnDay(parts[1]);
        waitUntilPageFinishLoading();
    }

    public void clickOnDay(String date) {
        driver.findElement(By.cssSelector("div[class='DayPicker-Body']")).findElement(By.xpath("//div[contains(@class,'DayPicker-Day') and @aria-disabled='false' and text()='"+date+"']")).click();
            }

    public void findMonth(String month) {

        String DayPickerCaption = driver.findElement(By.cssSelector("div[class='DayPicker-Caption']")).getText();

        try{
            while (!DayPickerCaption.contains(month)) {
                driver.findElement(By.cssSelector("span[class='DayPicker-NavButton DayPicker-NavButton--next']")).click();
                DayPickerCaption = driver.findElement(By.cssSelector("div[class='DayPicker-Caption']")).getText();
            }
        }
        catch (Exception e) {
            Assert.fail("The Date selected it's out of RANGE.");
        }
    }


    public void  verifyPills(String time,String school)
    {
        try {
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            if(driver.findElement(By.xpath("//div[text()='"+school+"']/../../../../following-sibling::td//div/button[text()='"+time+"']")).isDisplayed()) {
                Assert.fail("Time slot is displayed");
                driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            }
        } catch (Exception e)
        {
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        }
    }
    public void verifyUpgradeMessageInTravelPlanInRepVisits(){
        navBar.goToRepVisits();
        waitUntilElementExists(getTravelPlanBtn());
        getTravelPlanBtn().click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("'Premium Feature' text is not displayed",text("Premium Feature").isDisplayed());
        Assert.assertTrue("'UPGRADE' text is not displayed",text("UPGRADE").isDisplayed());
        Assert.assertTrue("'Lock' Icon is not displayed",driver.findElement(By.cssSelector(" img[alt='locked']")).isDisplayed());
    }

    public void verifySeeDetailsLinkInRepVisits(){

        navBar.goToRepVisits();
        getTravelPlanBtn().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//h1/span[text()='Travel Plan']")));
        Assert.assertTrue("'See Details' text is not displayed",text("See details").isDisplayed());
        travelPlanSeeDetailsLink().click();
        Assert.assertTrue("Fairs Tab it' active",text("Fairs").isDisplayed());
        Assert.assertTrue("'Fairs'  button it's not activated.",driver.findElement(By.cssSelector("div[class='ui right attached button _3uhLnGGw9ic0jbBIDirRkC']")).isDisplayed());
        Assert.assertTrue("'All Fairs' does not not displayed.",driver.findElement(By.cssSelector("div[class='_135QG0V-mOkCAZD0s14PUf']")).isDisplayed());
        Assert.assertTrue("Show All Fais does not displayed",text("Showing All Scheduled Fairs").isDisplayed());
    }


    public void searchforPartialdata(String institutionName,String partial)
    {
    navBar.goToRepVisits();
    getContactsBtn().click();
    getSearchBoxforContact().clear();
    getSearchBoxforContact().sendKeys(partial);
    Assert.assertTrue("the specified schoolname is not displayed",driver.findElement(By.xpath("//tr[@class='_1ijSBYwG-OqiUP1_S7yMUN']/td[@class='five wide hidden-mobile']/div[contains(text(),'"+institutionName+"')]")).isDisplayed());
    }

    public void verifyUpgradeMessageInContactsInRepVisits(){

        navBar.goToRepVisits();
        getContactsBtn().click();
        Assert.assertTrue("'Premium Feature' text is not displayed",text("Premium Feature").isDisplayed());
        Assert.assertTrue("'UPGRADE' text is not displayed",text("UPGRADE").isDisplayed());
        Assert.assertTrue("'Lock' Icon is not displayed",driver.findElement(By.xpath("//img[@alt='locked']")).isDisplayed());
    }

    public void verifyUpgradeMessageInRecommendationsInRepVisits(){

        navBar.goToRepVisits();
        getRecommendationsBtn().click();
        Assert.assertTrue("'Premium Feature' text is not displayed",text("Premium Feature").isDisplayed());
        Assert.assertTrue("'UPGRADE' text is not displayed",text("UPGRADE").isDisplayed());
        Assert.assertTrue("'Lock' Icon is not displayed",driver.findElement(By.xpath("//img[@alt='locked']")).isDisplayed());
    }

    public void verifyCalendarViewOnRepVisits() {
        navBar.goToRepVisits();
        link("Calendar").click();

        //Verify Small Calendar
        Assert.assertTrue("Small Calendar is not displayed",driver.findElement(By.cssSelector("div[role='application']")).isDisplayed());
        Assert.assertTrue("small calendar next button is not displayed",driver.findElement(By.cssSelector("button[title='right']>i")).isDisplayed());
        Assert.assertTrue("small calendar previous button is not displayed",driver.findElement(By.cssSelector("button[title='left']>i")).isDisplayed());

        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement mainCalendarNextBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[title='Forwards']>i")));

        //Verify Main Calendar
        Assert.assertTrue("Main Calendar is not displayed",driver.findElement(By.cssSelector("div[class='rbc-calendar rep-visits-rbc-calendar ']")).isDisplayed());
        Assert.assertTrue("Main calendar next button is not displayed",driver.findElement(By.cssSelector("button[title='Forwards']>i")).isDisplayed());
        Assert.assertTrue("Main calendar previous button is not displayed",driver.findElement(By.cssSelector("button[title='Backwards']>i")).isDisplayed());

        //verify day view
        Assert.assertTrue(" Day view button is not displayed",button("Day").isDisplayed());
        driver.findElement(By.cssSelector("button[title='Day']")).click();
        Assert.assertTrue("Day view is not displayed",driver.findElement(By.cssSelector("div[class='rbc-time-view']")).isDisplayed());

        //verify week view
        Assert.assertTrue(" Week view button is not displayed",button("Week").isDisplayed());
        driver.findElement(By.cssSelector("button[title='Week']")).click();
        Assert.assertTrue("Week view is not displayed",driver.findElement(By.cssSelector("div[class='rbc-time-view']")).isDisplayed());

        //verify month view
        Assert.assertTrue(" Month view button is not displayed",button("Month").isDisplayed());
        driver.findElement(By.cssSelector("button[title='Month']")).click();
        Assert.assertTrue("Month view is not displayed",driver.findElement(By.cssSelector("div[class='rbc-month-view']")).isDisplayed());

        // Appointments are clickable
        driver.findElement(By.cssSelector("div[class='_2_SLvlPA02MerU8g5DX1vz _3rlrDh7zu7nSf8Azwwi_pa']")).click();
        waitForUITransition();
        Assert.assertTrue("The Wizard contains the appointment details are not displayed",driver.findElement(By.cssSelector("div[class='ui overlay right very wide visible sidebar _1bTs4IjZQSsADQ671qHLL3']")).isDisplayed());
        driver.findElement(By.xpath("//button[@aria-label='Close']")).click();

        //verify Appointment Keys
        //Visits
        Assert.assertTrue(" visit confirmed option is not displayed",text("Visits - Confirmed").isDisplayed());
        Assert.assertTrue("Visit confirmed Checkbox is not displayed",driver.findElement(By.xpath("//input[@id='visit' and @type='checkbox']/following::label")).isDisplayed());
        String visitColor = "rgba(0, 0, 0, 0.87)";
        String actualVisitColor = driver.findElement(By.xpath("//input[@id='visit']")).getCssValue("color");
        Assert.assertTrue("Background Color for the Visit checkbox are not displayed",actualVisitColor.equals(visitColor));
        driver.findElement(By.xpath("//input[@id='visit']/following::label")).click();

        //Fairs
        Assert.assertTrue(" Fair confirmed option is not displayed",text("College Fair - Confirmed").isDisplayed());
        Assert.assertTrue("Fair confirmed Checkbox is not displayed",driver.findElement(By.xpath("//input[@id='fair'and @type='checkbox']/following::label")).isDisplayed());
        String fairColor = "rgba(0, 0, 0, 0.87)";
        String actualFairColor = driver.findElement(By.xpath("//input[@id='fair']")).getCssValue("color");
        Assert.assertTrue("Background Color for the fair checkbox are not displayed",actualFairColor.equals(fairColor));
        driver.findElement(By.xpath("//input[@id='fair']/following::label")).click();

        //Pending
        Assert.assertTrue("Pending option is not displayed",text("Pending").isDisplayed());
        Assert.assertTrue("Pending Checkbox is not displayed",driver.findElement(By.xpath("//input[@id='pending'and @type='checkbox']/following::label")).isDisplayed());
        String pendingColor = "rgba(0, 0, 0, 0.87)";
        String actualPendingColor = driver.findElement(By.xpath("//input[@id='pending']")).getCssValue("color");
        driver.findElement(By.xpath("//input[@id='pending']/following::label")).click();
        Assert.assertTrue("Background Color for the Pending checkbox are not displayed",actualPendingColor.equals(pendingColor));

    }

    public void navigateToRepVisitsSection(String pageName) {
        navBar.goToRepVisits();
        if (pageName.equalsIgnoreCase("visit feedback")) {
            getVisitsFeedbackBtn().click();
        } else {
            link(pageName).click();
        }
        waitUntilPageFinishLoading();
    }

    public void verifyUserDropdownforHE(){
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        userDropdown().click();
        Assert.assertTrue("'Account settings' option is not displayed",getAccoutSettingsBtn().isDisplayed());
        Assert.assertTrue("'Your Profile' option is not displayed",getYourProfileBtn().isDisplayed());
        Assert.assertTrue("'Institution Profile' option is not displayed",getInstitutionProfileBtn().isDisplayed());
        Assert.assertTrue("'Logged In As' Text is not displayed",loggedInText().isDisplayed());
        Assert.assertTrue("'Sign Out' option is not displayed",signOut().isDisplayed());
    }

    public void verifyNavigationUserDropdownforHE()  {
        getAccoutSettingsBtn().click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("Settings is not displayed", navBar.getSubMeunBreadcrumbs().getText().contains("Settings"));
        userDropdown().click();
        getYourProfileBtn().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        communityFrame();
        Assert.assertTrue("'User Profile' is not displayed",userProfilePage().isDisplayed());
        driver.switchTo().defaultContent();
        waitUntilPageFinishLoading();
        userDropdown().click();
        getInstitutionProfileBtn().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        communityFrame();
        Assert.assertTrue("'Institution Profile' is not displayed",institutionProfilePage().isDisplayed());
        driver.switchTo().defaultContent();
        waitUntilPageFinishLoading();
    }

    public void verifyUserAdminorNot(String option){
        userDropdown().click();
        if (!option.equals("")) {
            if (option.equalsIgnoreCase("ADMIN")) {
                Assert.assertTrue(option + "is not displayed", adminLogin().isDisplayed());
            } else if (option.equalsIgnoreCase("NON-ADMIN")) {
                Assert.assertTrue("'Logged In As' Text is not displayed",nonAdminLogin().isDisplayed());
            } else {
                Assert.fail("The given option" + option + " is a invalid one");
            }
        }
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
    }

    public void verifyHelpCentreforHE() {
        userDropdown().click();
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
        Assert.assertTrue("hobsons logo is not displayed",logo().isDisplayed());
        driver.close();
        driver.switchTo().window(navianceWindow);
        waitUntilPageFinishLoading();
        waitUntilElementExists(getRepVisitsBtn());
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
    }

    public void verifytSearchHeadingInSearchAndScheduleTab(){
        waitUntilPageFinishLoading();
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        getSearchAndScheduleBtn().click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("Search text box is not displayed",driver.findElement(By.xpath("//input[@placeholder='Search by school name or location...']")).isDisplayed());
        Assert.assertTrue("Search heading is not over the search bar",driver.findElement(By.xpath("//input[@placeholder='Search by school name or location...']/ancestor::form/preceding-sibling::h1/span[text()='Search']")).isDisplayed());
    }

    public void verifyScheduleHeadingOverAvailabilityBlockInSearchAndSchedule(){
        waitUntilPageFinishLoading();
        waitForUITransition();
        Assert.assertTrue("Schedule text is not over the Availability block",driver.findElement(By.xpath("//div/span[text()='Visits']/ancestor::div/preceding-sibling::h1/span[text()='Schedule']")).isDisplayed());
    }

    public void verifyCalendarIconNextToDateInSearchAndSchedule(){
        Assert.assertTrue("Go To Date button is not displayed",driver.findElement(By.xpath("//button[text()='Go To Date']")).isDisplayed());
        Assert.assertTrue("Calendar icon is not present next to date",driver.findElement(By.xpath("//button[text()='Go To Date']/ancestor::div/button/i[@class='calendar icon']")).isDisplayed());
    }

    public void verifyDateAndCalenderIconOverAvailabilityTable(){
        Assert.assertTrue("Calendar icon is not displayed over the Availability Table",driver.findElement(By.xpath("//thead/tr[@class='_27onojIbT_EM64h57k6Mee']/ancestor::table/ancestor::div/div/button[text()='Go To Date']")).isDisplayed());
    }

    public void verifyNextPrevButtonInSearchAndSchedule(){
        Assert.assertTrue("Previous Week button is not displayed",driver.findElement(By.xpath("//button[text()='Go To Date']/preceding-sibling::span/button[@aria-label='Previous week']")).isDisplayed());
        Assert.assertTrue("Next week button is not displayed",driver.findElement(By.xpath("//button[text()='Go To Date']/preceding-sibling::span/button[@aria-label='Next week']")).isDisplayed());
    }

    public void verifyViewTypeButtonInsearchAndSchedule(){
        Assert.assertTrue("View Type button is not displayed",driver.findElement(By.xpath("//span/button[@aria-label='Previous week']/parent::span/following-sibling::div/button[@aria-label='List']")).isDisplayed());
    }

    public void verifyColorofViewTypeButton(){
        String originalValue = "rgba(210, 0, 97, 1)";
        String currentValue = driver.findElement(By.xpath("//button[@aria-label='List']")).getCssValue("color");
        logger.info(currentValue+"currentValue");
        Assert.assertTrue("",currentValue.equals(originalValue));
    }

    public void verifyMapInSearchAndScheduleTab(){
        waitUntilPageFinishLoading();
        waitForUITransition();
        Assert.assertTrue("Map icon is not displayed",driver.findElement(By.xpath("//button/span[text()='Map']")).isDisplayed());
        driver.findElement(By.xpath("//button/span[text()='Map']")).click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        Assert.assertTrue("Current school is not displayed in the Map",driver.findElement(By.xpath("//div[@class='_2WDkiBOfzASeH5aeq7kJXv']")).isDisplayed());
    }

    public void verifyTextInFairsTabInSearchAndScheduleTab(String showingScheduledFairs){
        waitUntilPageFinishLoading();
        driver.findElement(By.xpath("//div/span[text()='Fairs']")).click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        Assert.assertTrue("Showing All Scheduled Fairs text is not displayed in the Fair Tab",driver.findElement(By.xpath("//span[text()='"+showingScheduledFairs+"']")).isDisplayed());
    }

    public void verifyYourScheduleTextInSearchAndScheduleTab(){
        Assert.assertTrue("Your Schedule text is not displayed",driver.findElement(By.xpath("//span[text()='Your Schedule']")).isDisplayed());
    }

    public void verifytSearchHeadingInSearchAndScheduleTabAfterSchoolSearch(){
        waitUntilPageFinishLoading();
        Assert.assertTrue("Search text box is not displayed",driver.findElement(By.xpath("//input[@placeholder='Search by school name or location...']")).isDisplayed());
        Assert.assertTrue("Search heading is not over the search bar",driver.findElement(By.xpath("//input[@placeholder='Search by school name or location...']/ancestor::form/preceding-sibling::h1/span[text()='Search']")).isDisplayed());
    }

    public void verifyVisitsFeedbackNonAdminMessaging() {
        Assert.assertTrue("Non-administrator message was not displayed",text("Visit Feedback is only available to users with the Administrator role.").isDisplayed());
        Assert.assertTrue("Locked banner was not displayed",driver.findElement(By.xpath("//div[@class='centered one column row']")).findElement(By.cssSelector("[alt=locked]")).isDisplayed());
    }

    public void verifyVisitsFeedbackFreemiumMessaging() {
        Assert.assertTrue("Feature description was not displayed",text(" you get access to information on the effectiveness of your college visits. See what's working and what could be improved.").isDisplayed());
        Assert.assertTrue("Upgrade button was not displayed",driver.findElement(By.xpath("//button/span")).getText().equalsIgnoreCase("upgrade"));
        Assert.assertTrue("Locked banner was not displayed",driver.findElement(By.xpath("//div[@class='centered one column row']")).findElement(By.cssSelector("[alt=locked]")).isDisplayed());
    }

    public void verifyVisitFeedbackPage(){
        verifyVisitFeedbackHeading();
        verifyStaffMembersAreDisplayedInAscendingOrderByLastName();
        verifyCommunityAvatarIsDisplayedToTheLeftOfStaffMemberName();
        verifyNoFeedbackSubmittedYetMessageIsDisplayed();
    }
    public void navaigateToAccountSettings(String accountSettings)
    {
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        userDropdown().click();
        waitUntilElementExists(accountSettings(accountSettings));
        Assert.assertTrue("AccountSettings is not displayed",accountSettings(accountSettings).isDisplayed());
        accountSettings(accountSettings).click();
        waitUntilPageFinishLoading();
    }

    public void verifyDefaultToggleinSearchAndSchedule(String visitOrFairs,String enabledOrDisabled){
        if(visitOrFairs.equals("Visits")) {
            if(enabledOrDisabled.equals("Enabled")) {
                Assert.assertTrue("Visits toggle is not Enabled",isButtonEnabledInSearchandScheduleTab(driver.findElement(By.xpath("//span[text()='Visits']/parent::div[@role='button']"))));
            }else if(enabledOrDisabled.equals("Disabled")){
                Assert.assertTrue("Visits is not Disabled",isButtonDisabledInSearchandScheduleTab(driver.findElement(By.xpath("//span[text()='Visits']/parent::div[@role='button']"))));
            }else {
             logger.info("Invalid option");
            }
        }else if(visitOrFairs.equals("Fairs")) {
            if(enabledOrDisabled.equals("Enabled")) {
                Assert.assertTrue("Fairs toggle is not Enabled",isButtonEnabledInSearchandScheduleTab(driver.findElement(By.xpath("//span[text()='Fairs']/parent::div[@role='button']"))));
            }else if(enabledOrDisabled.equals("Disabled")) {
                Assert.assertTrue("Fairs toggle is not Disabled",isButtonDisabledInSearchandScheduleTab(driver.findElement(By.xpath("//span[text()='Fairs']/parent::div[@role='button']"))));
            }else {
                logger.info("Invalid option");
            }
        }else {
            logger.info("Invalid option");
        }
    }

    public void verifyAvailabilitySlotInSearchAndSchedule(String time,String startDate,String school){
        waitUntilPageFinishLoading();
        waitForUITransition();
        WebElement schoolName=driver.findElement(By.xpath("//div/a[text()='"+school+"']"));
        waitUntilElementExists(schoolName);
        Assert.assertTrue("school is not displayed",schoolName.isDisplayed());
        waitUntilElementExists(goToDate());
        String gotoDate = getSpecificDate(startDate);
        setDate(gotoDate, "Go To Date");
        waitForUITransition();
        WebElement appointmentSlot = getDriver().findElement(By.cssSelector("table[class='ui celled padded table _2eF3VXyCbtuvPYc_1_GyO_']"));
        waitUntil(ExpectedConditions.visibilityOf(appointmentSlot));
        String visitTime = pageObjects.HS.repVisitsPage.RepVisitsPageImpl.StartTime;
        String visitDate=getMonthandDate(startDate);
        WebElement button= driver.findElement(By.xpath("//span[text()='"+visitDate+"']/parent::th/ancestor::thead/following-sibling::tbody/tr//td//div/button[text()='"+visitTime+"']"));
       //verify availability
        Assert.assertTrue("Availability slot is not displayed",button.isDisplayed());
    }

    public void verifyAvailabilitySlotIsNotDisplayingInSearchAndSchedule(String time,String startDate,String school){
        waitUntilPageFinishLoading();
        visit().click();
        waitForUITransition();
        WebElement schoolName=driver.findElement(By.xpath("//div/a[text()='"+school+"']"));
        waitUntilElementExists(schoolName);
        Assert.assertTrue("school is not displayed",schoolName.isDisplayed());
        waitUntilElementExists(goToDate());
        String gotoDate = getSpecificDate(startDate);
        setDate(gotoDate, "Go To Date");
        waitForUITransition();
        WebElement appointmentSlot = getDriver().findElement(By.cssSelector("table[class='ui celled padded table _2eF3VXyCbtuvPYc_1_GyO_']"));
        waitUntil(ExpectedConditions.visibilityOf(appointmentSlot));
        String visitTime = pageObjects.HS.repVisitsPage.RepVisitsPageImpl.StartTime;
        String visitDate=getMonthandDate(startDate);
        List<WebElement> button= driver.findElements(By.xpath("//span[text()='"+visitDate+"']/parent::th/ancestor::thead/following-sibling::tbody/tr//td//div/button[text()='"+visitTime+"']"));
        Assert.assertTrue("Availability slot is displayed",button.size()==0);
    }

    public void searchSchoolbyLocation(String school,String location){
        waitUntilPageFinishLoading();
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        getSearchAndScheduleBtn().click();
        waitUntilPageFinishLoading();
        searchTextBox().clear();
        searchTextBox().sendKeys(location);
        waitUntilElementExists(search());
        searchButton().click();
        waitForUITransition();
        WebElement schoolName=driver.findElement(By.xpath("//td/a[contains(text(),'"+school+"')]"));
        waitUntil(ExpectedConditions.visibilityOf(schoolName));
        Assert.assertTrue("school is not displayed",schoolName.isDisplayed());
        schoolName.click();
        waitUntilPageFinishLoading();
    }

    public void verifyRepvisitsOverviewUpgradeSubscriptionPage() {

        if(text("Welcome to Overview").isDisplayed()) {
            logger.error("RepVisits Overview Upgrade Subscription page is not displayed");
            return;
        }

        if(text("Your Upcoming Visits & Fairs").isDisplayed()) {
            logger.error("RepVisits Overview Upgrade Subscription page is not displayed");
            return;
        }

        Assert.assertTrue("'Overview' header is not displayed", text("Overview").isDisplayed());
        Assert.assertTrue("'Premium Feature' subtext is not displayed", text("Premium Feature").isDisplayed());
        Assert.assertTrue("'Locked' icon is not displayed", driver.findElement(By.xpath("//img[@alt='locked']")).isDisplayed());
        Assert.assertTrue("'Unlock Overview' text is not displayed", text("Unlock Overview").isDisplayed());
        Assert.assertTrue("'You'll get right to work faster' text is not displayed", text("You'll get right to work faster").isDisplayed());
        Assert.assertTrue("'The Overview provides a summary of your scheduled visits and fairs for the week with easy access to appointment details—all in one quick view.' text is not displayed",
                driver.findElement(By.className("yA0LbT1wFzAHyLC-oUZgc")).getText().equals("The Overview provides a summary of your scheduled visits and fairs for the week with easy access to appointment details—all in one quick view."));
        Assert.assertTrue("'UPGRADE' button is not displayed", button("UPGRADE").isDisplayed());

        button("UPGRADE").click();
        Assert.assertTrue("'Eloqua sales lead form' is not displayed", driver.findElement(By.xpath("//*[@class='ui header yPrXuXWe8f9WYWmKjbRiU _2_NgiA2zhtvtK1J2NNgPGn']")).getText().equals("Upgrade to Intersect Presence and get the most out of RepVisits"));

        driver.findElement(By.xpath("//i[@class='close icon']")).click();

    }

    private void verifyVisitFeedbackHeading() {
        waitUntilPageFinishLoading();
        Assert.assertTrue("Visit Feedback heading not displayed", driver.findElement(By.xpath("//h1[contains(@class, 'ui header _26ekcAlhCmjadW7ShhS7aj')]")).getText().equals("Visit Feedback"));
    }

    private void verifyStaffMembersAreDisplayedInAscendingOrderByLastName() {
        waitUntilPageFinishLoading();

        List<WebElement> itemsInStaffMemberMenu = getVerticalStaffMembersMenu().findElements(By.xpath(".//li[contains(@class,'item')]"));

        ArrayList<String> listContainingFullNamesOfStaffMembers = new ArrayList<String>();

        for(int i=0; i < itemsInStaffMemberMenu.size(); i++)
        {
            String fullNameOfStaffMember = itemsInStaffMemberMenu.get(i).findElement(By.xpath(".//div[contains(@class, 'middle aligned twelve wide column p2KoskEYI_DvLmnUMMQ2V')]")).getText();
            listContainingFullNamesOfStaffMembers.add(fullNameOfStaffMember);
        }

        List<String> listContainingStaffMembersSortedByLastName = sortByLastName(listContainingFullNamesOfStaffMembers);
        Assert.assertTrue("Staff members are NOT displayed in ascending order by last name", listContainingFullNamesOfStaffMembers.equals(listContainingStaffMembersSortedByLastName));
    }

    private void verifyCommunityAvatarIsDisplayedToTheLeftOfStaffMemberName() {
        waitUntilPageFinishLoading();
        boolean isCommunityAvatarDisplayed = false;

        List<WebElement> itemsInStaffMemberMenu = getVerticalStaffMembersMenu().findElements(By.xpath(".//li[contains(@class,'item')]"));

        for( int i=0; i<itemsInStaffMemberMenu.size(); i++)
        {
            try {
                isCommunityAvatarDisplayed = itemsInStaffMemberMenu.get(i).
                        findElement(By.xpath(".//div[contains(@class, 'middle aligned twelve wide column p2KoskEYI_DvLmnUMMQ2V')]/img | .//div[contains(@class, 'middle aligned twelve wide column p2KoskEYI_DvLmnUMMQ2V')]/i")).isDisplayed();
            } catch(Exception ex) {
                isCommunityAvatarDisplayed = false;
            }

            String nameOfStaffMember = itemsInStaffMemberMenu.get(i).findElement(By.xpath(".//div[contains(@class, 'middle aligned twelve wide column p2KoskEYI_DvLmnUMMQ2V')]")).getText();
            Assert.assertTrue("Community avatar for staff member - " + nameOfStaffMember + " - is not displayed", isCommunityAvatarDisplayed);
        }
    }

    private void verifyNoFeedbackSubmittedYetMessageIsDisplayed() {
        if(text("Insights into your team's reputation will appear here as staff members get feedback from high schools they visit.").isDisplayed()) {
            logger.info("'Insights into your team's reputation will appear here as staff members get feedback from high schools they visit.' message is displayed");
        }
        else {
            Assert.fail("'Insights into your team's reputation will appear here as staff members get feedback from high schools they visit.' message is not displayed!");
        }
    }

    private ArrayList sortByLastName(ArrayList<String> al) {
        Collections.sort(al, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String[] split1 = o1.split(" ");
                String[] split2 = o2.split(" ");
                String lastName1 = split1[1];
                String lastName2 = split2[1];
                if (lastName1.compareTo(lastName2) > 0) {
                    return 1;
                } else {

                    return -1;
                }
            }
        });

        return al;
    }

    public void verifyRateTextIsPresentInVFOverviewPage() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        List<WebElement> overviewPageElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='rep-visits-ratings']//*")));
        for(WebElement overviewPageElement : overviewPageElements)
        {
            Assert.assertTrue("Rate or Rating text is present in Visit Feedback Overview page", !overviewPageElement.getText().toLowerCase().contains("rate") && !overviewPageElement.getText().toLowerCase().contains("rating"));
        }
    }

    public void verifyTextDisplayedOnViewingStaffFeedback() {

        List<WebElement> itemsInStaffMemberMenuHavingOneOrMoreFeedbacks = getVerticalStaffMembersMenu().findElements(By.xpath(".//li[@class='item']"));
        itemsInStaffMemberMenuHavingOneOrMoreFeedbacks.get(0).click();

        Assert.assertTrue(itemsInStaffMemberMenuHavingOneOrMoreFeedbacks.get(0).findElement(By.xpath("./div")).getCssValue("background-color").equals("rgba(245, 234, 239, 1)"));

        WebDriverWait wait = new WebDriverWait(driver, 10);
        List<WebElement> elementsInRepVisitDetails = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='rating-details']//*")));
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()='submitted visit feedback of']")).isDisplayed() || driver.findElement(By.xpath("//span[text()='Anonymous']")).isDisplayed());

        for(WebElement elementInRepVisitDetails: elementsInRepVisitDetails) {
            if(elementInRepVisitDetails.getText().equals("Go Back to Visit Feedback"))
            {
                Assert.assertTrue(elementInRepVisitDetails.isDisplayed());
                elementInRepVisitDetails.click();
                Assert.assertTrue(itemsInStaffMemberMenuHavingOneOrMoreFeedbacks.get(0).findElement(By.xpath("./div")).getCssValue("background-color").equals("rgba(0, 0, 0, 0)"));
                break;
            }
        }
    }


    public void searchSchool(String school){
        waitUntilPageFinishLoading();
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        getSearchAndScheduleBtn().click();
        waitUntilPageFinishLoading();
        searchTextBox().clear();
        searchTextBox().sendKeys(school);
        waitUntil(ExpectedConditions.visibilityOf(search()),10);
        searchButton().click();
        waitForUITransition();
        //waitUntil(ExpectedConditions.visibilityOf(schoolName),10);
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td/a[contains(text(),'"+school+"')]")));
        WebElement schoolName=driver.findElement(By.xpath("//td/a[contains(text(),'"+school+"')]"));

        Assert.assertTrue("school is not displayed",schoolName.isDisplayed());
        schoolName.click();
        waitUntilPageFinishLoading();
        waitForUITransition();
    }


    public void visitsSchedule(String school,String startDate,String time){
        waitUntilPageFinishLoading();
        visit().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        WebElement schoolInVisits = driver.findElement(By.xpath("//div/a[text()='"+school+"']"));
        waitUntil(ExpectedConditions.visibilityOf(schoolInVisits),10);
        Assert.assertTrue("school is not displayed",schoolInVisits.isDisplayed());
        waitUntil(ExpectedConditions.visibilityOf(goToDate()),10);
        String gotoDate;
        String visitDate;
        if (startDate.length() < 3) {
            gotoDate = getSpecificDate(startDate);
            visitDate = getMonthandDate(startDate);
        }
        else {
            gotoDate = startDate;
            String[] dateParts = startDate.split(" ");
            visitDate = getShortMonth(dateParts[0]) + " " + dateParts[1];
        }
        setDate(gotoDate, "Go To Date");
        waitForUITransition();
        if (time.length() < 5)
            time = pageObjects.HS.repVisitsPage.RepVisitsPageImpl.StartTime;
        waitUntilElementExists(driver.findElement(By.xpath("//span[text()='"+visitDate+"']/parent::th/ancestor::thead/following-sibling::tbody/tr//td//div/button[text()='"+time+"']")));
        WebElement availabilityButton = driver.findElement(By.xpath("//span[text()='"+visitDate+"']/parent::th/ancestor::thead/following-sibling::tbody/tr//td//div/button[text()='"+time+"']"));
        Assert.assertTrue("Availability is not displayed",availabilityButton.isDisplayed());
        availabilityButton.click();
        waitUntilPageFinishLoading();
    }

    public String getShortMonth(String month) {
        switch(month) {
            case "January":
                return "Jan";
            case "February":
                return "Feb";
            case "March":
                return "Mar";
            case "April":
                return "Apr";
            case "May":
                return "May";
            case "June":
                return "Jun";
            case "July":
                return "Jul";
            case "August":
                return "Aug";
            case "September":
                return "Sep";
            case "October":
                return "Oct";
            case "November":
                return "Nov";
            case "December":
                return "Dec";
            default:
                return "ERROR: " + month + " is not a valid month.";
        }
    }

    public String getSpecificDate(String addDays) {
        String DATE_FORMAT_NOW = "MMMM dd yyyy";
        Calendar cal = Calendar.getInstance();
        int days=Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }

    public String getMonthandDate(String addDays)
    {
        String DATE_FORMAT_NOW = "MMM d";
        Calendar cal = Calendar.getInstance();
        int days=Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }

    public void verifySchedulePopup(String school,String startTime,String endTime){
        waitUntilPageFinishLoading();
        if(startTime.length() < 5)
            startTime = pageObjects.HS.repVisitsPage.RepVisitsPageImpl.StartTime;
        Assert.assertTrue("SchedulePopup is not displayed",driver.findElement(By.xpath("//div[contains(text(),'Ready to Schedule?')]")).isDisplayed());
        Assert.assertTrue("school is not displayed",driver.findElement(By.xpath("//div[contains(text(),'Do you want to schedule a visit with "+school+" from')]")).isDisplayed());
        Assert.assertTrue("time is not displayed",driver.findElement(By.xpath("//div[contains(text(),'Do you want to schedule a visit with "+school+" from')]/b[contains(text(),'"+startTime+"-"+endTime+"')]")).isDisplayed());
        visitRequestButton().click();
        waitUntilPageFinishLoading();
        waitUntilElementExists(goToDate());
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        waitForUITransition();
    }


    public void visitFairsToRegister(String fairName,String schoolName){
        waitUntilPageFinishLoading();
        String Fair=pageObjects.HS.repVisitsPage.RepVisitsPageImpl.FairName;
        waitUntilElementExists(getFairsButton());
        getFairsButton().click();
        waitUntilElementExists(register());
        WebElement fairname=driver.findElement(By.xpath("//a/h3[text()='"+schoolName+"']/parent::a/following-sibling::span[text()='"+Fair+"']"));
        waitUntilElementExists(fairname);
        Assert.assertTrue("fair is not displayed",fairname.isDisplayed());
        WebElement schoolDetails = driver.findElement(By.xpath("//a/h3[text()='"+schoolName+"']"));
        Assert.assertTrue("schoolName is not displayed",schoolDetails.isDisplayed());
        WebElement button=  driver.findElement(By.xpath("//span[text()='"+Fair+"']/parent::div/following-sibling::div/button[text()='Register']"));
        button.click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("submit page is not displayed",text("Yes, Submit Request").isDisplayed());
        submitButton().click();
        waitForUITransition();
        navBar.goToRepVisits();
        waitForUITransition();
        waitUntilPageFinishLoading();
    }

    public void verifyNotification(String school,String date,String time) {
        Assert.assertTrue("Requests is not displayed",requestsubtab().isDisplayed());
        requestsubtab().click();
        Assert.assertTrue("school is not displayed",schoolInRequest(school).isDisplayed());
        String Date = selectdate(date);
        String visitTime = pageObjects.HS.repVisitsPage.RepVisitsPageImpl.StartTime;
        Assert.assertTrue("date and time are not displayed",driver.findElement(By.xpath("//b[text()='"+school+"']/parent::div/following-sibling::div/span[text()='"+Date+"']/parent::div[text()='"+visitTime+"']")).isDisplayed());
    }

    public void verifyNotificationforFairs(String school,String date,String time) {
        navBar.goToRepVisits();
        link("Notifications").click();
        waitUntilElementExists(requestsubtab());
        Assert.assertTrue("Requests is not displayed",requestsubtab().isDisplayed());
        requestsubtab().click();
        Assert.assertTrue("school is not displayed",schoolInRequest(school).isDisplayed());
        String Date = selectdate(date);
        Assert.assertTrue("date and time is not displayed",driver.findElement(By.xpath("//b[text()='"+school+"']/parent::div/following-sibling::div/span[text()='"+Date+"']/parent::div[text()='"+time+"']")).isDisplayed());
    }

    public  void verify25Entries(String option) {
        int count=driver.findElements(By.xpath("//div[@class='_12QfCShNjFFA8a-x4K3-yn']/div/div")).size();
        try{
             if (count>25) {
                 while(showMoreButton(option).isDisplayed()){
                   logger.info("Show More option is displayed");
                     showMoreButton(option).click();
                 }
             }else {
                 logger.info("Show More option is not diplayed");
             }
        } catch (Exception e){}
    }

    public void selectViewDetails(String school,String date,String visitTime) {
        visitTime = pageObjects.HS.repVisitsPage.RepVisitsPageImpl.StartTime;
        String Date = selectdate(date);
        Assert.assertTrue("View full details option is not displayed",driver.findElement(By.xpath("//b[text()='"+school+"']/parent::div/following-sibling::div/span[text()='"+Date+"']/parent::div[text()='"+visitTime+"']/following-sibling::div/a/span[text()='View full details']")).isDisplayed());
        driver.findElement(By.xpath("//b[text()='"+school+"']/parent::div/following-sibling::div/span[text()='"+Date+"']/parent::div[text()='"+visitTime+"']/following-sibling::div/a/span[text()='View full details']")).click();
        waitForUITransition();
        waitUntilElementExists(textBoxInViewDetails());
        textBoxInViewDetails().sendKeys(Keys.PAGE_DOWN);
        Assert.assertTrue("save button is not displayed",saveButton().isDisplayed());
        saveButton().click();
        // The temporary notification that appears on save hides the user dropdown which can break subsequent steps.
        waitForUITransition();
        waitForUITransition();
    }

    public void clickOnSeeAllUsersLink(String link) {
        seeAllUsersLink(link).click();
    }

    public void reInviteSendEmail(String action, String loginName){

        waitForUITransition();
        waitUntilElementExists(driver.findElement(By.xpath("//table[@class='ui table']//tbody//tr//td[5]/a[text()='"+loginName+"']")));
        WebElement login = driver.findElement(By.xpath("//table[@class='ui table']//tbody//tr//td[5]/a[text()='"+loginName+"']"));

        WebElement Actions = getParent(getParent(login)).findElement(By.cssSelector("[aria-label='Actions']"));
        Actions.click();

        WebElement selectsReInviteDropDown = driver.findElement(By.xpath("//div[@class='menu transition visible']//span[text()='"+action+"']"));
        driver.executeScript("arguments[0].click();",selectsReInviteDropDown);
        yesButton().click();
    }

    public void selectViewDetailsforFairs(String school,String date,String time) {
        String Date = selectdate(date);
        Assert.assertTrue("View full details option is not displayed",driver.findElement(By.xpath("//b[text()='"+school+"']/parent::div/following-sibling::div/span[text()='"+Date+"']/parent::div[text()='"+time+"']/following-sibling::div/a/span[text()='View full details']")).isDisplayed());
        driver.findElement(By.xpath("//b[text()='"+school+"']/parent::div/following-sibling::div/span[text()='"+Date+"']/parent::div[text()='"+time+"']/following-sibling::div/a/span[text()='View full details']")).click();
        waitForUITransition();
        waitUntilElementExists(textBoxInViewDetails());
        textBoxInViewDetails().sendKeys(Keys.PAGE_DOWN);
        Assert.assertTrue("save button is not displayed",saveButton().isDisplayed());
        saveButton().click();
    }

    public void verifynoNotificationMessage(String message) {
        navBar.goToRepVisits();
        link("Notifications").click();
        waitUntilElementExists(requestsubtab());
        try{
        if(text(message).isDisplayed())
        {logger.info("Notification is not displayed");}
        else{logger.info("Notification is displayed");}}
    catch(Exception e){}
    }

    public String selectdate(String addDays)
    {
        String DATE_FORMAT_NOW = "EEEE, MMMM d, yyyy";
        Calendar cal = Calendar.getInstance();
        int days=Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }

    public void setDate(String inputDate, String startOrEndDate){

        String[] parts = inputDate.split(" ");
        String calendarHeading = parts[0] + " " + parts[2];

        if (startOrEndDate.contains("Start")) {
            button(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']")).click();
            findMonth(calendarHeading, startOrEndDate);
        } else if(startOrEndDate.contains("end")){
            button(By.cssSelector("div[style='display: inline-block;'] :nth-child(3)")).click();
            findMonth(calendarHeading, startOrEndDate);
        }else{button(By.cssSelector("button[class='ui tiny icon right floated right labeled button _1alys3gHE0t2ksYSNzWGgY']")).click();
            findMonth(calendarHeading);}
        if(parts[1].startsWith("0")) {
            parts[1]=parts[1].replace("0","");
        }
        clickOnDay(parts[1]);
        waitUntilPageFinishLoading();
    }

    public void findMonth(String month, String startOrEndDate) {
        waitUntilPageFinishLoading();
        boolean monthStatus = compareDate(month, startOrEndDate);

        String DayPickerCaption = driver.findElement(By.cssSelector("div[class='DayPicker-Caption']")).getText();

        try{
            while (!DayPickerCaption.contains(month)) {
                if (monthStatus){
                    driver.findElement(By.cssSelector("span[class='DayPicker-NavButton DayPicker-NavButton--next']")).click();
                    DayPickerCaption = driver.findElement(By.cssSelector("div[class='DayPicker-Caption']")).getText();
                }
                else {
                    driver.findElement(By.cssSelector("span[class='DayPicker-NavButton DayPicker-NavButton--prev']")).click();
                    DayPickerCaption = driver.findElement(By.cssSelector("div[class='DayPicker-Caption']")).getText();
                }
            }
        }
        catch (Exception e) {
            Assert.fail("The Date selected it's out of RANGE.");
        }
    }

    public Boolean compareDate(String month, String startOrEndDate)  {

        String dateCaption = null;
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        DateFormat formatDate = new SimpleDateFormat("MMM yyyy");
        if (startOrEndDate.contains("Start")) {
            dateCaption = driver.findElement(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']")).getText();
        } else if(startOrEndDate.contains("end")){
            button(By.cssSelector("div[style='display: inline-block;'] :nth-child(3)")).click();
        }else{button(By.cssSelector("button[class='ui tiny icon right floated right labeled button _1alys3gHE0t2ksYSNzWGgY']")).click();}


        //Logic to compare dates before? or not
        Date first = null;
        try {
            first = format.parse(dateCaption);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date second = null;
        try {
            second = formatDate.parse(month);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        boolean before = (first.before(second));
        return  before;

    }

    public void addHighSchoolToRepVisitsTravelPlan(String school, String location){
        navigateToRepVisitsSection("Recommendations");
        searchHighSchoolByLocationInRecommendationsPage(location);
        addHighSchoolToTravelPlan(school);
    }
    private void searchHighSchoolByLocationInRecommendationsPage(String location){
        getSearchByLocationTextBox().sendKeys(location);
    }

    /**
     +     * Removes a given high school from the travel plan page
     +     * @param school to be removed
     +     */
    public void removeHighSchoolFromTravelPlan(String school){
        navigateToRepVisitsSection("Travel Plan");
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//h1/span[text()='Travel Plan']")));
        button(By.xpath(String.format(".//div/div/div[text()='%s']/ancestor::div[@class='item']//span[text()='Remove']",school))).click();
        Assert.assertTrue("The Remove from Travel Plan text is not displayed", text("Remove from Travel Plan?").isDisplayed());
        Assert.assertTrue("The remove from travel plan confirmation message is not displayed",text(String.format("Are you sure you want to remove %s from your travel plan?", school)).isDisplayed());
        button("YES, REMOVE").click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Succesfully removed']")));
        waitUntil(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//span[text()='Succesfully removed']")));
    }

    public void verifySchoolDetailsInTravelPlan(String school,String address,String collegeGoingRate,String seniorclassSize,String primaryPoc,String numberofHighSchool,String stateName){
        String addressdetails[] = address.split(",");
        String city = addressdetails[0];
        String state = addressdetails[1];
        String county = addressdetails[2];
        String zip = addressdetails[3];
        Assert.assertTrue("HighSchool count is not displayed",driver.findElement(By.xpath("//span[text()='"+stateName+"']/parent::div/span[text()='"+numberofHighSchool+"']")).isDisplayed());
        Assert.assertTrue("HighSchool avatar image is not displayed",driver.findElement(By.xpath("//div/img[@class='ui centered image yUiNH8XB_uHGXhISF0aaL']")).isDisplayed());
        Assert.assertTrue("School is not displayed",driver.findElement(By.xpath("//div[text()='"+school+"']")).isDisplayed());
        Assert.assertTrue("Address is not displayed",driver.findElement(By.xpath("//div[normalize-space(text())='"+city+","+state+","+county+","+zip+"']")).isDisplayed());
        Assert.assertTrue("CollegeGoingRate is not displayed",driver.findElement(By.xpath("//div[text()='"+collegeGoingRate+"']")).isDisplayed());
        Assert.assertTrue("Senior class size is not displayed",driver.findElement(By.xpath("//div[text()='"+seniorclassSize+"']")).isDisplayed());
        Assert.assertTrue("primary Poc is not displayed",driver.findElement(By.xpath("//div[text()='"+primaryPoc+"']")).isDisplayed());
    }

    public void verifyLinkInTravelPlanPage(){
        navigateToRepVisitsSection("Travel Plan");
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//h1/span[text()='Travel Plan']")));
        try{
            Assert.assertTrue("Instruction is not displayed",driver.findElement(By.xpath("//span[text()='The Travel Plan lists high schools you plan to visit or will be visiting. You can add additional schools by selecting \"Add to my travel plan\" from the ']")).isDisplayed());
            Assert.assertTrue("Recommendations link is not displayed",link("Recommendations").isDisplayed());
            link("Recommendations").click();
            waitUntilPageFinishLoading();
            Assert.assertTrue("Navigated to Recommendations Page",driver.findElement(By.xpath("//div/h1/span[text()='Recommendations']")).isDisplayed());
        }catch(Exception e){
            throw new AssertionFailedError("The label for the 'Recommendation' is not displayed in the 'Travel Plan' tab");
        }
    }

    public void verifysortingStatesInTravelPlan(){
        link("Travel Plan").click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        List<WebElement> elements = driver.findElements(By.xpath("//div[@class='ui stackable grid f4StyMpAtcTrzK5AccX8f']/div[1]/div/span"));
        List<String> original = new ArrayList<>();
        for(WebElement element:elements){
            original.add(element.getText());
        }
        List<String> sortedList = new ArrayList();
        for(String element:sortedList){
            sortedList.add(element);
        }
        int i = 0;
        for (String entry : sortedList) {
            Assert.assertTrue("Entry in sorted list doesn't match an entry in the original list. Sorted: " + entry + ", Original: " + original.get(i),entry.equalsIgnoreCase(original.get(i)));
            i++;
        }
    }

    public void verifyVisitDetailsInTravelPlan(String school,String date){
        navigateToRepVisitsSection("Travel Plan");
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//h1/span[text()='Travel Plan']")));
        String visitDate = getSpecificDateforTravelPlan(date);
        WebElement visitDetails = driver.findElement(By.xpath("//div[text()='"+school+"']/ancestor::div[@class='row _2Hy63yUH9iXIx771rmjucr']//div[@class='content _1SfpP2fklL5GVWMyfqjq4q']//button/span/span[text()='Visit,']/parent::span/following-sibling::span[text()='"+visitDate+"']"));
        Assert.assertTrue("Visit Details is not displayed",visitDetails.isDisplayed());
        visitDetails.click();
        waitForUITransition();
        Assert.assertTrue("Internal Notes text box is displayed",driver.findElement(By.xpath("//input[@aria-label='Internal Notes']")).isDisplayed());
        driver.findElement(By.xpath("//input[@aria-label='Internal Notes']")).sendKeys(Keys.PAGE_DOWN);
        Assert.assertTrue("Cancel This Visit button is not displayed",button("Cancel This Visit").isDisplayed());
        button("Cancel This Visit").click();
        driver.findElement(By.id("cancel-message")).sendKeys("by QA");
        Assert.assertTrue("Yes, Cancel Visit button is not Enabled",button("Yes, Cancel Visit").isEnabled());
        button("Yes, Cancel Visit").click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        waitForUITransition();
    }

    public void verifyFairDetailsInTravelPlan(String school,String date){
        navBar.goToCommunity();
        waitUntilPageFinishLoading();
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        link("Travel Plan").click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        String fairDate = getSpecificDateforTravelPlan(date);
        WebElement fairDetails = driver.findElement(By.xpath("//div[text()='"+school+"']/ancestor::div[@class='row _2Hy63yUH9iXIx771rmjucr']//div[@class='content _1SfpP2fklL5GVWMyfqjq4q']//button/span/span[text()='College Fair,']/parent::span/following-sibling::span[text()='"+fairDate+"']"));
        Assert.assertTrue("FairDetails is not displayed",fairDetails.isDisplayed());
        fairDetails.click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        waitUntil(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//input[@aria-label='Internal Notes']"))));
        Assert.assertTrue("Internal Notes text box is displayed",driver.findElement(By.xpath("//input[@aria-label='Internal Notes']")).isDisplayed());
        driver.findElement(By.xpath("//input[@aria-label='Internal Notes']")).sendKeys(Keys.PAGE_DOWN);
        Assert.assertTrue("Cancel This Fair is not Displayed",button("Cancel This Fair").isDisplayed());
        button("Cancel This Fair").click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        driver.findElement(By.id("cancel-message")).sendKeys("by QA");
        Assert.assertTrue("Yes, Cancel Fair button is not Enabled",button("Yes, Cancel Fair").isEnabled());
        button("Yes, Cancel Fair").click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        waitForUITransition();
        waitForUITransition();
    }

    public void verifyUpcommingAppointmentTextInTravelPlan(String upcomingAppointmentsText,String school){
        Assert.assertTrue("'Upcoming Appointments' text is not displayed for the school '"+school+"' in Travel Plan",driver.findElement(By.xpath("//div[text()='"+school+"']/ancestor::div/following-sibling::div/div/div/div/span/span[text()='"+upcomingAppointmentsText+"']")).isDisplayed());
    }

    public void verifyScheduledTextInTravelPlan(String scheduled,String school){
        Assert.assertTrue("'Scheduled' Label text is not displayed for the school '"+school+"' in the Travel Plan" ,driver.findElement(By.xpath("//div[text()='"+school+"']/following-sibling::div/div/span[text()='"+scheduled+"']")).isDisplayed());
    }


     /* Verifies that a given label is displayed for a given high school
     * @param school to verify the label
     * @param labelText to verify*/
    public void verifyLabelForTravelPlanHighSchool(String school, String labelText){
        navigateToRepVisitsSection("Travel Plan");
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//h1/span[text()='Travel Plan']")));
        try{
            getDriver().findElements(By.xpath(String.format(".//div/div/div[text()='%s']/ancestor::div[@class='item']//span[text()='%s']",school,labelText)));
        }catch(Exception e){
            throw new AssertionFailedError(String.format("The %s label is not displayed for high school: %s, error: %s",labelText,school,e.toString()));
        }
    }

    public void verifyPreviousAppointmentsTextInTravelPlan(String text,String school){
        Assert.assertTrue("'"+text+"' Text is not displayed for the school '"+school+"' in the Travel Plan",driver.findElement(By.xpath("//div[text()='"+school+"']/ancestor::div/following-sibling::div//span[text()='"+text+"']")).isDisplayed());
    }

    public void verifyViewAvailabilityButtonInTravelPlan(String viewAvailabilityButton,String school){
        Assert.assertTrue("'View Availability' is not displayed for the school '"+school+"' in the Travel Plan",driver.findElement(By.xpath("//div[text()='"+school+"']/ancestor::div/following-sibling::div//span[text()='"+viewAvailabilityButton+"']")).isDisplayed());
    }

    public void selectViewAvailabilityButtonInTravelPlan(String viewAvailabilityButton,String school){
       getDriver().findElement(By.xpath("//div[text()='"+school+"']/ancestor::div/following-sibling::div//span[text()='"+viewAvailabilityButton+"']")).click();
       waitForUITransition();
       waitUntilPageFinishLoading();
       Assert.assertTrue("Search text box is not displayed in the 'Search and Schedule' Page",driver.findElement(By.xpath("//input[@placeholder='Search by school name or location...']")).isDisplayed());
       Assert.assertTrue("visit button is not displayed in the 'Search and Schedule' Page",driver.findElement(By.xpath("//div/span[text()='Visits']")).isDisplayed());
    }

    public void verifyRemoveButtonInTravelPlan(String removeButton,String school){
        waitForUITransition();
        Assert.assertTrue("Remove button is not displayed",driver.findElement(By.xpath("//div[text()='"+school+"']/ancestor::div/preceding-sibling::div/div/button/span[text()='"+removeButton+"']")).isDisplayed());
    }

    public void verifyUpcomingFairMessageInTravelPlan(String school){
        Assert.assertTrue("'Upcoming Fair' Highlighted Text is not displayed for the school '"+school+"' in the Travel Page",driver.findElement(By.xpath("//div[text()='"+school+"']/ancestor::div/following-sibling::div/div/div/div/div/span[text()='This school has an upcoming College Fair on']/parent::div/a/span[text()='See details »']")).isDisplayed());
        WebElement seeDetailsLink = getDriver().findElement(By.xpath("//div[text()='"+school+"']/ancestor::div/following-sibling::div/div/div/div/div/span[text()='This school has an upcoming College Fair on']/parent::div/a/span[text()='See details »']"));
        seeDetailsLink.click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        Assert.assertTrue("Search text box is not displayed in the 'Search and Schedule' page",driver.findElement(By.xpath("//input[@placeholder='Search by school name or location...']")).isDisplayed());
        Assert.assertTrue("visit button is not displayed in the 'Search and Schedule' page",driver.findElement(By.xpath("//div/span[text()='Visits']")).isDisplayed());
    }

    public void verifyToDoTextInTravelPlan(String toDoText,String school){
        waitForUITransition();
        Assert.assertTrue("'To Do' Label is not displayed for the school '"+school+"' in the Travel Plan",driver.findElement(By.xpath("//div[text()='"+school+"']/following-sibling::div/div/span[text()='"+toDoText+"']")).isDisplayed());
    }

    //The below method will verify the message which will display when there is no visit/fair for the next week.
    public void verifyDefaultMessageOverviewPage(){
        if(text("You don't have any visits or fairs for the next week.").isDisplayed()){
            Assert.assertTrue("You don't have any visits or fairs for the next week. text message is not displaying.", text("You don't have any visits or fairs for the next week.").isDisplayed());
            Assert.assertTrue("You can always make appointments by using the text is not displaying.", text("You can always make appointments by using the").isDisplayed());
            Assert.assertTrue("Search and Schedule link is not displaying.", link("Search and Schedule").isDisplayed());
            link("Search and Schedule").click();
            waitUntilPageFinishLoading();
            String actURL = driver.getCurrentUrl();
            Assert.assertTrue("Search and Schedule page is not displaying.", actURL.contains("/search"));
        }else if (text("Your Upcoming Visits & Fairs").isDisplayed())
            logger.info("The HE User has upcoming visits/fair for the coming 7 days, so can't show the default text in Overview page...");
    }

    public String getSpecificDateforTravelPlan(String addDays){
        String DATE_FORMAT_NOW = "MMMM d, yyyy";
        Calendar cal = Calendar.getInstance();
        int days=Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }

    /**
     * Adds a given school to the travel plan in RepVisits>Travel Plan
     * @param school
     */
    private void addHighSchoolToTravelPlan(String school){
        try{
            driver.findElement(By.xpath(String.format(".//td/a[text()='%s']/ancestor::tr/td/div[@class='_1az38UH6Zn-lk--8jTDv2w']/span[text()='Added To Travel Plan']"
                    ,school)));
        } catch (Exception e){
            button(By.xpath(String.format(".//td/a[text()='%s']/ancestor::tr/td/div/button/span[text()='Add To Travel Plan']"
                    ,school))).click();
            Assert.assertTrue(String.format("The school: %s was not added to the travel plan",school),
                    text(By.xpath("//span[text()='School added to Travel Plan']")).isDisplayed());
        }
    }


    /**
     * Verifies if the trash icon is displayed for a given school in the Travel Plan page
     * @param school to verify the trash icon
     */
    public void verifyTrashIconForTravelPlanHighSchool(String school){
        navigateToRepVisitsSection("Travel Plan");
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//h1/span[text()='Travel Plan']")));
        try{
            getDriver().findElement(By.xpath(String.format(
                    ".//div/div/div[text()='%s']/ancestor::div[@class='item']/div/div/button/i[@class='trash icon _22IhW8lEh2abuRIROnZXJx']"
                    ,school)));
        }catch(Exception e){
            throw new AssertionFailedError(String.format("The trash icon is not displayed for school: %s, error: %s"
                    ,school,e.toString()));
        }
    }

    /**
     * Cancels removing a given high school from the travel plan page
     * @param school to be removed and canceled
     */
    public void cancelRemoveHighSchoolFromTravelPlan(String school){
        navigateToRepVisitsSection("Travel Plan");
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//h1/span[text()='Travel Plan']")));
        button(By.xpath(String.format(".//div/div/div[text()='%s']/ancestor::div[@class='item']//span[text()='Remove']"
                ,school))).click();
        Assert.assertTrue("The Remove from Travel Plan text is not displayed", text("Remove from Travel Plan?")
                .isDisplayed());
        Assert.assertTrue("The remove from travel plan confirmation message is not displayed",
                text(String.format("Are you sure you want to remove %s from your travel plan?", school)).isDisplayed());
        button("CANCEL").click();
    }

    /**
     * Verifies if a given high school was removed from the travel plan list
     * @param school to verify if was removed
     */
    public void verifyHighSchoolIsNotInTravelPlan(String school){
        try{
            getDriver().findElement(By.xpath(String.format(".//div[@class='content']/div/div/div[text()='%s']", school)));
            throw new AssertionFailedError(String.format("The high school: %s  is displayed in the travel plan page",
                    school));
        } catch(Exception e){}
    }

    /**
     * Verifies if a given high school was removed from the travel plan list
     * @param school to verify if was removed
     */
    public void verifyHighSchoolInTravelPlan(String school){
        try{
            getDriver().findElement(By.xpath(String.format(".//div[@class='content']/div/div/div[text()='%s']", school)));
        } catch(Exception e){
            throw new AssertionFailedError(String.format("The high school: %s is not displayed in the travel plan page",
                    school));
        }
    }

    /**
     * Verifies that travel plan is locked
     */
    public void verifyTravelPlanIsLocked(){
        navigateToRepVisitsSection("Travel Plan");
        Assert.assertTrue("Travel Plan is not locked",text("Unlock Travel Plan").isDisplayed());
    }
     public void navigateToInstitutionNotificationPage(){
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        userDropDown().click();
        accountSettings().click();
        waitUntilPageFinishLoading();
        institutionNotification().click();
    }

    public void verifyInstitutionNotificationPage(){
        // Temporary fix because an error is displayed due to amount of data to be processed
        for(int i=0; i<10;i++){
                        try{
                                waitUntil(ExpectedConditions.visibilityOfElementLocated(By.
                                                xpath("//h1[text()='Something unexpected happened. Please, try again.']")),10);
                                navigateToInstitutionNotificationPage();
                                waitUntilPageFinishLoading();
                            } catch (Exception e){
                                break;
                            }
                    }
        Assert.assertTrue("Institution Notifications is not displayed",institutionNotificationText().isDisplayed());
        Assert.assertTrue("Naviance ActiveMatch is not displayed",navianceActiveMatchText().isDisplayed());
        Assert.assertTrue("Email Textbox is not displayed",emailTextBox().isDisplayed());
        Assert.assertTrue("Save button is not displayed",saveButton().isDisplayed());
    }

    public void validateEmailInInstitutionNotificationPage(String Email,String InvalidEmail,String ValidEmail){
        emailTextBox().clear();
        emailTextBox().sendKeys(Email);
        emailTextBox().sendKeys(InvalidEmail);
        saveButton().click();
        Assert.assertTrue("Error message is not displayed",driver.findElement(By.xpath("//span[text()='Emails must be valid and separated by comma.']")).isDisplayed());
        emailTextBox().clear();
        emailTextBox().sendKeys(Email);
        emailTextBox().sendKeys(ValidEmail);
        saveButton().click();
        waitUntilPageFinishLoading();
        String ExactMessage=driver.findElement(By.xpath("//span[@class='LkKQEXqh0w8bxd1kyg0Mq']/span")).getText();
        String SuccessMessage="Success! You've updated your notifications settings.";
        Assert.assertTrue("Success Message is not displayed", ExactMessage.equals(SuccessMessage));
    }

    public void validateCheckboxInInstitutionNotificationPage(String checkboxValue){
        checkBoxInAccountSettingsNotification(checkboxValue).click();
        checkBoxInAccountSettingsNotification(checkboxValue).click();
    }

    public void verifyNotificationTabinNonAdmin(){
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        userDropDown().click();
        accountSettings().click();
        waitUntilPageFinishLoading();
        List<WebElement> list = driver.findElements(By.xpath("//span[text()='Institution Notifications']"));
        Assert.assertTrue("'Institution Notifications' is displayed in Account settings page",list.size()==0);
   }

    public void verifyNavigationInNonAdminByURl(){
        load(GetProperties.get("he.accountsettings.url"));
        waitUntilPageFinishLoading();
        Assert.assertTrue("Authorization message is not displayed in Account settings page",driver.findElement(By.xpath("//div/h1[text()='You are not authorized to view the content on this page']")).isDisplayed());
    }

    private WebElement upgradeButton(){
        WebElement button=button("UPGRADE");
        return button;
   }

    private WebElement getRepVisitsBtn() {
        return link(By.id("js-main-nav-rep-visits-menu-link"));
    }
    public void verifyAccountsettings(String leftSubMenuInaccountSettings) {
        String subMenu[] = leftSubMenuInaccountSettings.split(",");
        for (int i=0;i<subMenu.length-1;i++) {
            Assert.assertTrue("Tab " + subMenu[i] + " is not displaying as expected!",driver.findElement(By.xpath("//a/span[text()='"+subMenu[i]+"']")).isDisplayed());
        } }

    public void verifyPasswordFields(String AccountInformationPage,String firstName,String lastName,String eMail,DataTable dataTable) {
        String details[] = AccountInformationPage.split(",");
        for(int i=0;i<details.length-1;i++) {
            Assert.assertTrue(details[i] + " is not showing.", text(details[i]).isDisplayed());}
        currentPasswordInput().sendKeys(Keys.PAGE_DOWN);
        List<String> list = dataTable.asList(String.class);
        for (String passwordCriteria : list) {
            Assert.assertTrue(passwordCriteria + " is not showing.",text(passwordCriteria).isDisplayed());
        }
        String firstname=firstNameTextbox().getAttribute("value");
        Assert.assertTrue("FirstName is not displayed",firstname.equals(firstName));
        String lastname=lastNameTextbox().getAttribute("value");
        Assert.assertTrue("LastName is not displayed",lastname.equals(lastName));
        String email=eMailTextbox().getAttribute("value");
        Assert.assertTrue("Email is not displayed",email.equals(eMail));
    }

    public void validatePassword(String userType,String oldPassword,String minimum8character,String lowercaseletter,String uppercaseletter,String withoutNumber,String withoutspecialcharacter) {
        currentPasswordInput().clear();
        currentPasswordInput().sendKeys(oldPassword);
        //verify the password policy of minimum of 8 characters
        newPasswordInput().sendKeys(minimum8character);
        confirmPasswordInput().sendKeys(minimum8character);
        saveButton().click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("'Password Failed' warning message is not displayed ", passwordErrorMessage().isDisplayed());

        //verify the password policy of lowercase letter
        newPasswordInput().clear();
        confirmPasswordInput().clear();
        newPasswordInput().sendKeys(lowercaseletter);
        confirmPasswordInput().sendKeys(lowercaseletter);
        saveButton().click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("'Password Failed' warning message is not displayed ", passwordErrorMessage().isDisplayed());

        //verify the password policy of uppercase letter
        newPasswordInput().clear();
        confirmPasswordInput().clear();
        newPasswordInput().sendKeys(uppercaseletter);
        confirmPasswordInput().sendKeys(uppercaseletter);
        saveButton().click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("'Password Failed' warning message is not displayed ", passwordErrorMessage().isDisplayed());

        //verify the password policy of without the number
        newPasswordInput().clear();
        confirmPasswordInput().clear();
        newPasswordInput().sendKeys(withoutNumber);
        confirmPasswordInput().sendKeys(withoutNumber);
        saveButton().click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("'Password Failed' warning message is not displayed ", passwordErrorMessage().isDisplayed());

        //verify the password policy of without the Special Characters
        newPasswordInput().clear();
        confirmPasswordInput().clear();
        newPasswordInput().sendKeys(withoutspecialcharacter);
        confirmPasswordInput().sendKeys(withoutspecialcharacter);
        saveButton().click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("'Password Failed' warning message is not displayed ", passwordErrorMessage().isDisplayed());

        //verify the password accepted with the password policy
        newPasswordInput().clear();
        confirmPasswordInput().clear();
        newPasswordInput().sendKeys(GetProperties.get("he."+ userType + ".password"));
        confirmPasswordInput().sendKeys(GetProperties.get("he."+ userType + ".password"));
        saveButton().click();
        waitForUITransition();
        List<WebElement> list=driver.findElements(By.xpath("//div[@class='ui negative message']/div/span"));
        if(list.size()==1) {
            Assert.fail("Error Message is displayed");
        }
        waitUntilPageFinishLoading();
    }

    public void verifyDetailsInaccountSettings()
    {
        List<String> tabs = new ArrayList<>();
        //Left Menu
        tabs.add("Account Information");
        tabs.add("Your Notifications");
        for (String tab : tabs) {
            Assert.assertTrue("Tab " + tab + " is not displaying as expected!",driver.findElement(By.xpath("//a/span[text()='"+tab+"']")).isDisplayed());
        } }

    public void verifyDetailsInaccountSettingsforNonAdmin(String leftSubMenuInaccountSettings) {
        Assert.assertTrue("Tab " + leftSubMenuInaccountSettings + " is not displaying as expected!",driver.findElement(By.xpath("//a/span[text()='"+leftSubMenuInaccountSettings+"']")).isDisplayed());
    }


    public void resetPassword(String oldPassword,String newPassword)
    {
        currentPasswordInput().clear();
        currentPasswordInput().sendKeys(oldPassword);
        newPasswordInput().sendKeys(newPassword);
        confirmPasswordInput().sendKeys(newPassword);
        saveButton().click();
        waitForUITransition();
        List<WebElement> list=driver.findElements(By.xpath("//div[@class='ui negative message']/div/span"));
        if(list.size()==1) {
            logger.info("Error Message is displayed");
            Assert.fail();
        }
    }

    public void verifySuccessMessageinAccountSettingsPage(String message){
        String SuccessMessage = driver.findElement(By.xpath("//span[@class='LkKQEXqh0w8bxd1kyg0Mq']/span")).getText();
        Assert.assertTrue("Success message is not displayed",message.equals(SuccessMessage));
    }

    public void selectCalendar() {
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        getCalendarBtn().click();
        waitUntilPageFinishLoading();
    }
    public void verifyCalendarPage(String school,String time,String date) {
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        calendar().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        collegeFairTextBoxInCalendarPage().click();
        waitUntilPageFinishLoading();
        visitCheckBoxInCalendarPage().click();
        waitUntilPageFinishLoading();
        String month = month(date);
        String currentMonth = currentMonthInCalendarPage().getText();
        String selectMonth[] = currentMonth.split(" ");
        String Month = selectMonth[0];
        while (!month.equals(Month)) {
            nextMonthButton().click();
            waitForUITransition();
            waitUntilPageFinishLoading();
            waitUntil(ExpectedConditions.visibilityOf(currentMonthInCalendarPage()),10);
            currentMonth = currentMonthInCalendarPage().getText();
            selectMonth = currentMonth.split(" ");
            Month = selectMonth[0];
        }
        String startTime=getCalendarVisitTime().toUpperCase();
        WebElement appointmentSlot = getDriver().findElement(By.xpath("//span[text()='"+startTime+"']/following-sibling::span[text()='"+school+"']"));
        Assert.assertTrue("Appointment Slot time and university is not displayed",appointmentSlot.isDisplayed());
        jsClick(appointmentSlot);
        waitUntilPageFinishLoading();
    }

    public void verifyCalendarPopup(String school,String date,String startTime,String endTime,String hsAddress,String contactPhoneNo,String user,String eMail) {
        waitForUITransition();
        Assert.assertTrue("Visit Details is not displayed",visitDetailsLabel().isDisplayed());
        Assert.assertTrue("Contact is not displayed",contactLabel().isDisplayed());
        Assert.assertTrue("user is not displayed",getDriver().findElement(By.xpath("//div[text()='"+user+"']")).isDisplayed());
        Assert.assertTrue("email id is not displayed",getDriver().findElement(By.xpath("//div[text()='"+eMail+"']")).isDisplayed());
        Assert.assertTrue("contact no is not displayed",getDriver().findElement(By.xpath("//div[text()='"+contactPhoneNo+"']")).isDisplayed());
        Assert.assertTrue("school name is not displayed",getDriver().findElement(By.xpath("//div[contains(text(),'"+school+"')]")).isDisplayed());
        Assert.assertTrue("address is not displayed",getDriver().findElement(By.xpath("//div[contains(text(),'"+school+"')]/following-sibling::div[contains(text(),'"+hsAddress+"')]")).isDisplayed());
        String Date = getSpecificDateforCalendar(date);
        Assert.assertTrue("date is not displayed",getDriver().findElement(By.xpath("//span[text()='"+Date+"']")).isDisplayed());
        startTime = getCalendarPopupVisitTime().toUpperCase();
        Assert.assertTrue("Time is not displayed",getDriver().findElement(By.xpath("//div/span[contains(text(),'"+startTime+"')]/following-sibling::span[contains(text(),'"+endTime+"')]")).isDisplayed());
        Assert.assertTrue("Instructions from High School option is not displayed",instructions().isDisplayed());

        try{
            if(rescheduleVisit().isDisplayed()&&cancelVisit().isDisplayed()) {
                logger.info("Reschedule this visit and cancel visit option is displayed");
            }else{
                logger.info("Reschedule and cancel option is not displayed");
            }
        }catch (Exception e){}
        saveButton().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
    }
    public void verifyCalendarPopupforfreemium$(String school,String date,String startTime,String endTime,String hsAddress,String contactPhoneNo,String user,String eMail) {
        waitForUITransition();
        Assert.assertTrue("Visit Details is not displayed",visitDetailsLabel().isDisplayed());
        Assert.assertTrue("Contact is not displayed",contactLabel().isDisplayed());
        Assert.assertTrue("user is not displayed",driver.findElement(By.xpath("//div[text()='"+user+"']")).isDisplayed());
        Assert.assertTrue("email id is not displayed",driver.findElement(By.xpath("//div[text()='"+eMail+"']")).isDisplayed());
        Assert.assertTrue("contact no is not displayed",driver.findElement(By.xpath("//div[text()='"+contactPhoneNo+"']")).isDisplayed());
        Assert.assertTrue("school name is not displayed",driver.findElement(By.xpath("//div[contains(text(),'"+school+"')]")).isDisplayed());
        Assert.assertTrue("address is not displayed",driver.findElement(By.xpath("//div[contains(text(),'"+school+"')]/following-sibling::div[contains(text(),'"+hsAddress+"')]")).isDisplayed());
        String Date=getSpecificDateforCalendar(date);
        Assert.assertTrue("date is not displayed",driver.findElement(By.xpath("//span[text()='"+Date+"']")).isDisplayed());
        startTime=getCalendarPopupVisitTime().toUpperCase();
        Assert.assertTrue("Time is not displayed",driver.findElement(By.xpath("//div/span[contains(text(),'"+startTime+"')]/following-sibling::span[contains(text(),'"+endTime+"')]")).isDisplayed());
        Assert.assertTrue("Instructions from High School option is not displayed",instructions().isDisplayed());
        Assert.assertTrue("InternalNotes is enabled",verifyInternalNotes().isDisplayed());

        try{
            if(rescheduleVisit().isDisplayed()) {
                logger.info("Reschedule this visit option is displayed");
            }else if(cancelVisit().isDisplayed()) {
                logger.info("Cancel This Visit option is displayed");
            }else{
                logger.info("Reschedule and cancel option is not displayed");
            }
        }catch (Exception e){}
    }

    public void visitsScheduleInFreemium(String school,String startDate,String time){
        visit().click();
        WebElement schoolName=driver.findElement(By.xpath("//div[text()='"+school+"']"));
        waitUntil(ExpectedConditions.visibilityOf(schoolName),10);
        Assert.assertTrue("school is not displayed",schoolName.isDisplayed());
        waitUntil(ExpectedConditions.visibilityOf(goToDate()),10);
        String gotoDate = getSpecificDate(startDate);
        setDate(gotoDate, "Go To Date");
        String date=getMonthandDate(startDate);
        String visitTime = pageObjects.HS.repVisitsPage.RepVisitsPageImpl.StartTime;
        Assert.assertTrue("Availability is not displayed",availabilityButton(date,visitTime).isDisplayed());
        availabilityButton(date,visitTime).click();
        waitUntilPageFinishLoading();
    }

    public void removeAppointmentfromCalendar(){
        waitForUITransition();
        Assert.assertTrue("Cancel This Visit is not displayed",driver.findElement(By.xpath("//button/span[text()='Cancel This Visit']")).isDisplayed());
        driver.findElement(By.xpath("//button/span[text()='Cancel This Visit']")).click();
        waitForUITransition();
        driver.findElement(By.id("cancel-message")).click();
        driver.findElement(By.id("cancel-message")).sendKeys(Keys.PAGE_DOWN);
        driver.findElement(By.id("cancel-message")).sendKeys("by QA");
        button("Yes, Cancel Visit").click();
        waitUntilPageFinishLoading();
        waitForUITransition();
    }

    public String getSpecificDateforCalendar(String addDays) {
        String DATE_FORMAT_NOW = "EEEE, MMMM d, yyyy";
        Calendar cal = Calendar.getInstance();
        int days=Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }

    public void setDateforCalendarPage(String date,String fromOrTo){
        String[] parts = date.split(" ");
        String calendarHeading = parts[0] + " " + parts[2];
        if(fromOrTo.equals("From")){
            driver.findElement(By.xpath("//button[@class='ui basic button _1wqaQnL4wzTmKK7w_sXTwT']")).click();
        }else if (fromOrTo.equals("To")){
            driver.findElement(By.xpath("//button[@class='ui basic button _2pj6YkRYwl9szEe_wh7dxF']")).click();
        }else {
            logger.info("Invalid option");
        }
        findMonth(calendarHeading);
        clickOnDay(parts[1]);
        waitUntilPageFinishLoading();
    }

    /**
     * Verifies that the given title is present in RepVisits branding header
     * @param expectedHeaderTitle to verify
     */
    public void verifyRepVisitsBrandingHeader(String expectedHeaderTitle){
        List<WebElement> headerElements = getRepVisitsBrandingHeaderContainer().findElements(By.cssSelector("div"));
        String actualHeaderTitle = String.format("%s %s",headerElements.get(0).getAttribute("innerText"),
                headerElements.get(1).getAttribute("innerText"));
        Assert.assertTrue(String.format(" The branding header title is not correct: actual: %s, expected: %s",
                actualHeaderTitle,expectedHeaderTitle),expectedHeaderTitle.equalsIgnoreCase(actualHeaderTitle));

    }

    /**
     * Verifies that the college fair stored in HS.repVisitsPage.FairName is visible in the fairs list for the given school
     * @param schoolName - Name of the school to look for the fair under.
     */
    public void verifyCollegeFairVisible(String schoolName) {
        navBar.goToRepVisits();
        getSearchAndScheduleBtn().click();
        waitUntilPageFinishLoading();
        getSearchBox().sendKeys(schoolName);
        getSearchButton().click();
        link(schoolName).click();
        waitUntilPageFinishLoading();
        getFairsButton().click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("College Fair: " + pageObjects.HS.repVisitsPage.RepVisitsPageImpl.FairName + " was not displayed in upcoming fairs list",driver.findElement(By.xpath("//span[text()='" + pageObjects.HS.repVisitsPage.RepVisitsPageImpl.FairName + "']")).isDisplayed());
    }

    /**
     * Verifies that the college fair stored in HS.repVisitsPage.FairName is not visible in the fairs list for the given school
     * @param schoolName - Name of the school to look for the fair under.
     */
    public void verifyCollegeFairNotVisible(String schoolName) {
        navBar.goToRepVisits();
        getSearchAndScheduleBtn().click();
        waitUntilPageFinishLoading();
        getSearchBox().sendKeys(schoolName);
        getSearchButton().click();
        link(schoolName).click();
        waitUntilPageFinishLoading();
        waitUntilElementExists( getFairsButton());
        getFairsButton().click();
        waitUntilPageFinishLoading();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        try {
            Assert.assertFalse("College Fair: " + pageObjects.HS.repVisitsPage.RepVisitsPageImpl.FairName + " was displayed in upcoming fairs list",driver.findElement(By.xpath("//span[text()='" + pageObjects.HS.repVisitsPage.RepVisitsPageImpl.FairName + "']")).isDisplayed());
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        } catch (Exception e) {
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        }
    }

    public void openCalendar() {
        navBar.goToRepVisits();
        getCalendarBtn().click();
        waitUntilPageFinishLoading();
    }

    public void openFairDetailsWithGeneratedDate() {
        formattedDate = pageObjects.HS.repVisitsPage.RepVisitsPageImpl.generatedDate + ","
                + pageObjects.HS.repVisitsPage.RepVisitsPageImpl.generatedDateDayOfWeek + ","
                + pageObjects.HS.repVisitsPage.RepVisitsPageImpl.time.replaceFirst(" ", "");
        openFairDetails(formattedDate);
    }

    public void openFairDetails(String date) {
        pressCalendarArrowUntil("right", date.split(",")[0], 10);
        getDateCell(date.split(",")[1].split(" ")[1], date.split(",")[2], 1).click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector("h1.ui.header"), 1));
    }

    public void verifyFairDetailsWithGenDate(DataTable fairDetails) {
        verifyFairDetails(formattedDate, fairDetails);
    }

    public void verifyFairDetails(String date, DataTable fairDetails) {
        List<List<String>> fairDetailsList = fairDetails.asLists(String.class);
        String internalNotesText = "";
        for (List<String> fairDataElement : fairDetailsList) {
            switch (fairDataElement.get(0)) {
                case "College Fair Name" : Assert.assertTrue(fairNameHeader().getText().equals(fairDataElement.get(1)));
                    break;
                case "High School name" : Assert.assertTrue(fairHSName().getText().equals(fairDataElement.get(1)));
                    break;
                case "High School address" : Assert.assertTrue(fairHSAddress().getText().equals(fairDataElement.get(1)));
                    break;
                case "Contact name" : Assert.assertTrue(fairContactName().getText().equals(fairDataElement.get(1)));
                    break;
                case "Contact title" : Assert.assertTrue(fairContactTitle().getText().equals(fairDataElement.get(1)));
                    break;
                case "Contact email" : Assert.assertTrue(fairContactEmail().getText().equals(fairDataElement.get(1)));
                    break;
                case "Contact phone" : Assert.assertTrue("UI: " + fairContactPhone().getText(), fairContactPhone().getText().equals(fairDataElement.get(1)));
                    break;
                case "Date" : Assert.assertTrue(fairDate().getText().equals(fairDataElement.get(1)));
                    break;
                case "Start time" : Assert.assertTrue("UI: " + fairStartTime().getText(), fairStartTime().getText().equals(fairDataElement.get(1)));
                    break;
                case "End time" : Assert.assertTrue(fairEndTime().getText().equals(fairDataElement.get(1)));
                    break;
                case "Time zone" : Assert.assertTrue("UI: " + fairTimeZone().getText(), fairTimeZone().getText().equals(fairDataElement.get(1)));
                    break;
                case "Cost" : Assert.assertTrue(fairCost().getText().equals(fairDataElement.get(1)));
                    break;
                case "Number of students" : Assert.assertTrue(fairExpectedStudents().getText().equals(fairDataElement.get(1)));
                    break;
                case "Instructions" : Assert.assertTrue(fairInstructions().getText().equals(fairDataElement.get(1)));
                    break;
                case "Cancellation text" : Assert.assertTrue(fairCancellationInstructions().getText().contains(fairDataElement.get(1)));
                    break;
                case "Cancel link" :
                    if (fairDataElement.get(1).equals("Present")) {
                        Assert.assertTrue("The Cancel link is not present", fairCancelLink().isDisplayed());
                    } else if (fairDataElement.get(1).equals("Not Present")) {
                        Assert.assertFalse("The Cancel link is present, when it shouldn't", fairCancelLink().isDisplayed());
                    }
                    break;
                case "Internal notes" :
                    internalNotesText = fairDataElement.get(1);
                    break;
            }
        }
        fairInternalNotesTextBox().clear();
        fairInternalNotesTextBox().sendKeys(internalNotesText);
        fairSaveButton().click();
        Assert.assertTrue("The message of successful saving is not disaplyed", fairSavedConfirmationMessage().isDisplayed());
        openFairDetails(date);
        Assert.assertTrue("The Internal notes were not saved", fairInternalNotesTextBox().getAttribute("value").equals(internalNotesText));
    }

    public void verifyDefaultTabAndToggleTab(){
        Assert.assertTrue("'Visits' tab is not displayed as Default tab",driver.findElement(By.xpath("//span[text()='Visits']")).isDisplayed());
        driver.findElement(By.xpath("//span[text()='Fairs']")).click();
        Assert.assertTrue("Unable to Toggle 'Fairs' tab",driver.findElement(By.xpath("//span[text()='Fairs']")).isEnabled());
        driver.findElement(By.xpath("//span[text()='Visits']")).click();
        Assert.assertTrue("Unable to Toggle 'Visits' tab",driver.findElement(By.xpath("//span[text()='Visits']")).isEnabled());
        waitUntilPageFinishLoading();
    }

    public void checkHighSchoolDetails(DataTable dataTable){
        List<String> list = dataTable.asList(String.class);
        for (String schoolDetails : list) {
            WebElement details=text(schoolDetails);
            waitUntilElementExists(details);
            Assert.assertTrue(schoolDetails + " is not showing.",text(schoolDetails).isDisplayed());
        }
    }

    public void verifyActiveSubscription(String schoolName){
        Assert.assertTrue("Searched school is not displayed",driver.findElement(By.xpath("//div/a[text()='"+schoolName+"']")).isDisplayed());
        Assert.assertTrue("High school name scheduling results is a not a hyperlink",link(schoolName).isEnabled());
        link(schoolName).click();
    }

    public void verifyHSpopup(DataTable dataTable)
    {
        List<String> list = dataTable.asList(String.class);
        for (String schoolDetails : list) {
            Assert.assertTrue(schoolDetails + " is not showing.",text(schoolDetails).isDisplayed());}
        driver.findElement(By.xpath("//div[@class='ui page modals dimmer transition visible active']/div/i")).click();
    }

    public void verifyHSBlockedText(String school)
    {
        try {
            Assert.assertTrue("No Appointments Available text is not displayed", driver.findElement(By.xpath("//a[contains(text(),'" + school + "')]/../ancestor::div[@class='ui items']/../following-sibling::td/span[contains(text(),'No Appointments Available')]")).isDisplayed());
        }catch(Exception e) {}
        try {
            Assert.assertTrue("Blocked text is not displayed", driver.findElement(By.xpath("//a[contains(text(),'" + school + "')]/../ancestor::div[@class='ui items']/../following-sibling::td/h1/span[contains(text(),'Blocked')]")).isDisplayed());
        }catch(Exception e) {}
    }

    public void selectHSLink(String schoolName)
    {
        link(schoolName).click();
        driver.findElement(By.xpath("//div[@class='column _3dlZYPxGjtMbv6sS-JsWeA']/a[contains(text(),'"+schoolName+"')]")).click();
    }

    public void verifyInActiveSubscription(String schoolName){
        waitUntilElementExists(goToDate());
        Assert.assertTrue("Searched school is not displayed",driver.findElement(By.xpath("//div[text()='"+schoolName+"']")).isDisplayed());
        try {
            Assert.assertTrue("High school name scheduling results is not a hyperlink", link(schoolName).isEnabled());
        }
        catch(Exception e)
        {
        }
    }

    private WebElement accountSettings(String accountSettings)
    {
        WebElement label= driver.findElement(By.xpath("//span[text()='"+accountSettings+"']"));
        return  label;
    }
    private WebElement search(){
        WebElement search=driver.findElement(By.xpath("//button[@class='ui button']"));
        waitUntilElementExists(search);
        return  search;
    }
    private WebElement goToDate()
    {
        WebElement goToDate=driver.findElement(By.xpath("//button[text()='Go To Date']"));
        waitUntilElementExists(goToDate);
        return  goToDate;
    }
    private WebElement getOverviewBtn() {
        return link("Overview");
    }
    private WebElement travelPlanSeeDetailsLink() {
        return link("See details »");
    }
    private WebElement getSearchAndScheduleBtn() {
        return link("Search and Schedule");
    }
    private WebElement getCalendarBtn() {
        return link("Calendar");
    }
    private WebElement getTravelPlanBtn() {
        return link("Travel Plan");
    }
    private WebElement getContactsBtn() {
        return link("Contacts");
    }
    private WebElement getRecommendationsBtn() {return link("Recommendations");}
    private WebElement getNotificationsBtn() {
        return link("Notifications");
    }

    private WebElement getSearchBox() { return textbox("Search for a school...");}
    private WebElement getVisitsFeedbackBtn() {return link("Visit Feedback"); }
    private WebElement getSearchAndScheduleSearchBox(){ return textbox("Search by school name or location..."); }
    //private WebElement getSearchBox() { return textbox("Enter a school name or location");}
    private WebElement getSearchBoxforContact() { return driver.findElement(By.name("contacts-search"));}
    private WebElement getSearchButton() { return driver.findElement(By.xpath("//button[@class='ui icon button _3pWea2IV4hoAzTQ12mEux-']"));}
    private WebElement getMapButton() { return driver.findElement(By.cssSelector("[class='map outline icon']"));}
    private WebElement getComingSoonMessageInOverviewPage(){ return driver.findElement(By.className("_9SnX9M6C12WsFrvkMMEZR")); }
    private WebElement getCheckRepVisitsAvailabilityButton(){ return driver.findElement(By.xpath("//a[text() = 'Check RepVisits Availability']")); }
    private WebElement getRepVisitsAvailabilitySidebar(){ return driver.findElement(By.className("_36B3QS_3-4bR8tfro5jydy")); }
    private WebElement saveChanges(){WebElement button=button("Save Changes"); return  button; }
    private WebElement userDropdown() {
        WebElement dropdown=driver.findElement(By.id("user-dropdown"));
        return  dropdown;
    }
    private WebElement getAccoutSettingsBtn() { return driver.findElement(By.xpath("//span[text()='Account Settings']")); }
    private WebElement getInstitutionProfileBtn() { return driver.findElement(By.xpath("//span[text()='Institution Profile']")); }
    private WebElement getYourProfileBtn() { return driver.findElement(By.xpath("//span[text()='Your Profile']")); }
    private WebElement signOut() {
        WebElement signOut=driver.findElement(By.xpath("//span[text()='Sign Out']"));
        return  signOut;
    }
    private WebElement loggedInText() {
        WebElement text=driver.findElement(By.xpath("//span[contains(text(),'Logged in as')]"));
        return  text;
    }
    private WebElement userProfilePage() {
        WebElement profile=driver.findElement(By.xpath("//a[@class='active' and text()='Profile']"));
        return  profile;
    }
    private WebElement institutionProfilePage() {
        WebElement institution=driver.findElement(By.xpath("//a[@class='active' and text()='Institution']"));
        return  institution;
    }
    private WebElement adminLogin() {
        WebElement admin=driver.findElement(By.xpath("//span[contains(text(),'Logged in as')]/span[text()='ADMIN']"));
        return admin;
    }
    private WebElement nonAdminLogin() {
        WebElement nonAdmin=driver.findElement(By.xpath("//span[contains(text(),'Logged in as')]"));
        return nonAdmin;
    }
    private WebElement helpNavDropdown() {
        WebElement help=driver.findElement(By.id("helpNav-dropdown"));
        return  help;
    }
    private  WebElement logo() {
        WebElement Logo=driver.findElement(By.xpath("//div/a[@class='logo']"));
        return Logo;
    }
    private WebElement notificationIconInHelpCentre() {
        WebElement notificationIcon=driver.findElement(By.id("notifications"));
        return notificationIcon;
    }
    private WebElement helpCentre()  {
        WebElement helpCentre=driver.findElement(By.xpath("//span[text()='Help Center']"));
        return  helpCentre;
    }
    private WebElement contactSupport()  {
        WebElement contactSupport= text("Contact Support");
        return  contactSupport;
    }
    private WebElement getRegistrationButton(String fairName) { return getDriver().findElement(By.xpath("//span[text()='" + fairName + "']/../../div/button")); }
    private WebElement getFairDate() { return getDriver().findElement(By.cssSelector("div.content span")); }
    private WebElement getStartEndTimeAndTimeZone() { return getDriver().findElement(By.cssSelector("div.content b:nth-of-type(1)")); }
    private WebElement requestText() { return getDriver().findElement(By.cssSelector("div.ui.modal.transition.visible.active div.content div")); }
    private WebElement submitRequestButton() { return getDriver().findElement(By.cssSelector("button.ui.primary.button")); }
    private WebElement cancelButton() { return getDriver().findElement(By.cssSelector("div.actions button.ui.teal.basic.button")); }
    private WebElement fairsTab() { return getDriver().findElement(By.cssSelector("div.right.button")); }
    private WebElement upperMessage() { return getDriver().findElement(By.cssSelector("span.LkKQEXqh0w8bxd1kyg0Mq + span")); }
    private WebElement rightCalendarRightButton() { return getDriver().findElement(By.cssSelector("button._2UEGkUTszONN0hK0CeHMm0")); }
    private WebElement rightCalendarHeaderDate() { return getDriver().findElement(By.cssSelector("div.ui.medium.header")); }
    private WebElement rightCalendarLeftButton() { return getDriver().findElement(By.cssSelector("button[title=\"Backwards\"]")); }
    private WebElement getDateCell(String day, String time, int row) { return getDriver().findElement(By.xpath("//div[@class='rbc-date-cell']/a[text()='" + day + "']/../../following-sibling::div[" + row + "]/div/div[@class='rbc-event']/div/div/span[text()='" + time + "']")); }
    private List<WebElement> quickViewCalendarHeaderDates() { return getDriver().findElements(By.cssSelector("h1.ui.header + div span span span")); }
    private WebElement quickViewRightButton() { return getDriver().findElement(By.cssSelector("button[aria-label=\"Next week\"]")); }
    private List<WebElement> quickViewEventsList() { return getDriver().findElements(By.cssSelector("ul.ui.huge.pointing.secondary + div div._2qvF1GJtxr-YZYY8wYagxl + div div.ui.stackable.grid")); }
    private List<WebElement> quickViewFairsList() { return getDriver().findElements(By.cssSelector("ul.ui.huge.pointing.secondary + div div._2qvF1GJtxr-YZYY8wYagxl + div div.ui.stackable.grid")); }
    private WebElement calendarIcon() { return getDriver().findElement(By.cssSelector("button.ui.tiny.icon.right.floated.right.labeled.button._1alys3gHE0t2ksYSNzWGgY")); }
    private WebElement miniCalendarHeader() { return getDriver().findElement(By.cssSelector("div.DayPicker-Caption")); }
    private WebElement miniCalendarRightButton() { return getDriver().findElement(By.cssSelector("span[aria-label=\"Next Month\"]")); }
    private WebElement miniCalendarLeftButton() { return getDriver().findElement(By.cssSelector("span[aria-label=\"Previous Month\"]")); }
    public WebElement miniCalendarDayCell(String day) { return getDriver().findElement(By.xpath("//div[@class='DayPicker-Week']/div[text()='" + day + "' and @class='DayPicker-Day']")); }
    public WebElement showMoreLink() { return getDriver().findElement(By.cssSelector("a.rbc-show-more")); }
    private List<WebElement> overlayEventsList() { return getDriver().findElements(By.cssSelector("div.rbc-overlay div.rbc-event")); }
    private WebElement visitsTab() { return getDriver().findElement(By.cssSelector("div.ui.left.attached.button")); }
    private WebElement goToDateButton() { return getDriver().findElement(By.cssSelector("button.ui.right.labeled.small.basic i")); }
    private WebElement searchAndScheduleCalendarIcon() { return getDriver().findElement(By.cssSelector("button.ui.right.labeled.tiny.icon.button i")); }
    private List<WebElement> headerWeekDays() { return getDriver().findElements(By.cssSelector("table.ui.fixed.unstackable.basic.center thead th div span")); }
    private List<WebElement> visitsRows() { return getDriver().findElements(By.cssSelector("table.ui.fixed.unstackable.basic.center tbody tr")); }
    private List<WebElement> eventsRows(String day) { return getDriver().findElements(By.xpath("//div[not(contains(@class, 'rbc-off-range'))][contains(@class,'rbc-date-cell')]/a[text()='" + day + "']/../../following-sibling::div")); }
    protected WebElement getVerticalStaffMembersMenu() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[contains(@class, 'ui vertical third _345W6T1ug0RMtbb4Ez3uMz menu')]")));
    }
    private WebElement searchTextBox() {
        WebElement textBox= driver.findElement(By.cssSelector("input[placeholder='Search by school name or location...']"));
        return textBox;
    }
    private WebElement searchButton() {
        WebElement button=driver.findElement(By.cssSelector("button[class='ui button']"));
        return  button;
    }
    private WebElement visit() {
        WebElement visit=driver.findElement(By.xpath("//span[text()='Visits']"));
        return  visit;
    }
    private WebElement visitRequestButton() {
        WebElement button=driver.findElement(By.xpath("//button[contains(text(),'Yes, Request this time')]"));
        return button;
    }
    private WebElement schoolInSearchAndSchedule(String school)
    {
        WebElement schoolName=driver.findElement(By.xpath("//td/a[contains(text(),'"+school+"')]"));
        return schoolName;
    }
    private WebElement schoolInVisits(String school) {
        WebElement schoolName=driver.findElement(By.xpath("//div/a[text()='"+school+"']"));
        return  schoolName;
    }
    private WebElement currentPasswordInput() {
        WebElement currentPassword=driver.findElement(By.id("current-password-input"));
        return  currentPassword;
    }
    private WebElement newPasswordInput() {
        WebElement newPassword=driver.findElement(By.id("new-password-input"));
        return  newPassword;
    }
    private WebElement confirmPasswordInput() {
        WebElement confirmPassword=driver.findElement(By.id("confirm-password-input"));
        return  confirmPassword;}
    private WebElement passwordErrorMessage() {
        WebElement msg=driver.findElement(By.xpath("//span[contains(text(),'The new password failed to satisfy security requirements')]"));
        return  msg;
    }
    private WebElement firstNameTextbox() {
        WebElement text=driver.findElement(By.id("user-form-first-name"));
        return  text;
    }
    private WebElement lastNameTextbox() {
        WebElement text=driver.findElement(By.id("user-form-last-name"));
        return  text;
    }
    private WebElement eMailTextbox() {
        WebElement text=driver.findElement(By.id("user-form-email"));
        return  text;
    }
    private WebElement availabilityButton(String date,String time) {
        WebElement button= driver.findElement(By.xpath("//span[text()='"+date+"']/parent::th/ancestor::thead/following-sibling::tbody/tr//td//div/button[text()='"+time+"']"));
        return button;
    }
    public WebElement getFairsButton() {
        return button("Fairs");
    }
    private WebElement fairName(String  school,String fairName) {
        WebElement fairname=driver.findElement(By.xpath("//a/h3[text()='"+school+"']/parent::a/following-sibling::span[text()='"+fairName+"']"));
        return  fairname;
    }
    private WebElement schoolName(String schoolName)
    {
        WebElement schoolDetails = driver.findElement(By.xpath("//a/h3[text()='"+schoolName+"']"));
        return  schoolDetails;
    }
    private WebElement registerButton(String fair) {
        WebElement button=  driver.findElement(By.xpath("//span[text()='"+fair+"']/parent::div/following-sibling::div/button/span[text()='Register']"));
        return  button;
    }
    private WebElement submitButton() {
        WebElement button=button("Yes, Submit Request");
        return  button;
    }
    private WebElement register() {
        WebElement registerButton=button("Register");
        waitUntilElementExists(registerButton);
        return  registerButton;
    }
    /*private WebElement search(){
        WebElement search=driver.findElement(By.xpath("//button[@class='ui button']"));
        waitUntilElementExists(search);
        return  search;
    }
    private WebElement goToDate() {
        WebElement goToDate=driver.findElement(By.xpath("//button[text()='Go To Date']"));
        waitUntilElementExists(goToDate);
        return  goToDate;
    }*/
    private WebElement requestsubtab() {
        WebElement request=link("Requests");
        return  request;
    }
    private WebElement schoolInRequest(String schoolName) {
        WebElement school=driver.findElement(By.xpath("//b[text()='"+schoolName+"']"));
        return school;
    }
    private WebElement saveButton() {
        WebElement button=button("Save");
        return button;
    }
    private WebElement yesButton() {
        WebElement button = button("Yes");
        return button;
    }
    public WebElement seeAllUsersLink(String link) {
        WebElement seealluser = link(link);
        return seealluser;
    }
    private WebElement textBoxInViewDetails() {
        WebElement text= driver.findElement(By.xpath("//input[@aria-label='Internal Notes']"));
        return text;
    }
    private WebElement showMoreButton(String option){
        WebElement text=driver.findElement(By.xpath("//span[text()='"+option+"']"));
        return text;
    }
    private WebElement calendar() {
        WebElement navbar=link("Calendar");
        return navbar;
    }
    private WebElement collegeFairTextBoxInCalendarPage() {
        WebElement check= driver.findElement(By.xpath("//div/label[text()='College Fair - Confirmed']"));
        return check;
    }
    private WebElement visitCheckBoxInCalendarPage() {
        WebElement check=driver.findElement(By.xpath("//div/label[text()='Visits - Confirmed']"));
        return check;
    }
    private WebElement currentMonthInCalendarPage() {
        WebElement month=driver.findElement(By.xpath("//div[@class='three wide column ZfUaDp3-V60qJ8_BTeIi']/div[@class='ui medium header _1ucD2vjQuS9iWHF9uzN__M']"));
        return month;
    }
    private WebElement nextMonthButton() {
        WebElement button=driver.findElement(By.xpath("//button[@class='ui teal basic icon button _38R7SJgG4fJ86m-eLYYZJw']"));
        return button;
    }
    private String month(String date) {
        String month=getSpecificDate(date);
        String selectMonth[]=month.split(" ");
        String currentMonth=selectMonth[0];
        return  currentMonth;
    }
    private WebElement verifyInternalNotes() {
        WebElement message=driver.findElement(By.xpath("//input[@placeholder='Upgrade your account to add custom notes to this visit.']"));
        return message;
    }
    private WebElement visitDetailsLabel() {
        WebElement label=driver.findElement(By.xpath("//span[text()='Visit Details']"));
        return label;
    }
    private WebElement contactLabel() {
        WebElement contact=driver.findElement(By.xpath("//span[text()='Contact']"));
        return  contact;
    }
    private WebElement instructions() {
        WebElement text=driver.findElement(By.xpath("//span[contains(text(),'Instructions from High School')]"));
        return text;
    }
    private WebElement rescheduleVisit() {
        WebElement reschedule=driver.findElement(By.xpath("//a[@class='_2SlRxBknbetA9qpiUnR4Pb']"));
        return reschedule;
    }
    private WebElement cancelVisit() {
        WebElement cancelisit=driver.findElement(By.xpath("//span[contains(text(),'Cancel This Visit')]"));
        return  cancelisit;
    }

    public String getCalendarVisitTime(){
        String time="",option="";
        String visitTime=pageObjects.HS.repVisitsPage.RepVisitsPageImpl.StartTime;
        char value[]=visitTime.toCharArray();
        for(int i=0;i<=4;i++){
            time=time+value[i];
        }
        for(int i=5;i<=value.length-1;i++) {
            option=option+value[i];
        }
        String startTime=time+option;
        return startTime;
    }
    public String getCalendarPopupVisitTime(){
        String time="",option="";
        String visitTime=pageObjects.HS.repVisitsPage.RepVisitsPageImpl.StartTime;
        char value[]=visitTime.toCharArray();
        for(int i=0;i<=4;i++){
            time=time+value[i];
        }
        for(int i=5;i<=value.length-1;i++) {
            option=option+value[i];
        }
        String startTime=time+" "+option;
        return startTime;
    }

    /**
     * Gets the searchByLocation textbox in the repvisits>recommendations page
     * @return WebElement
     */
    private WebElement getSearchByLocationTextBox(){
        return textbox("Search by location...");
    }

    private WebElement userDropDown(){
        WebElement button=driver.findElement(By.id("user-dropdown"));
        return button;
    }
    private WebElement accountSettings(){
        WebElement link=driver.findElement(By.xpath("//span[text()='Account Settings']"));
        return link;
    }
    private WebElement institutionNotification(){
        WebElement link=driver.findElement(By.xpath("//span[text()='Institution Notifications']"));
        return link;
    }
    private WebElement institutionNotificationText(){
        WebElement text=driver.findElement(By.xpath("//span[text()='Institution Notifications']"));
        return text;
    }
    private WebElement navianceActiveMatchText(){
        WebElement text=driver.findElement(By.xpath("//span[text()='Naviance ActiveMatch']"));
        return text;
    }
    private WebElement emailTextBox(){
        WebElement text=driver.findElement(By.id("am_notification_contacts_additional_emails"));
        return text;
    }

    private WebElement checkBoxInAccountSettingsNotification(String value) {
        WebElement checkbox = driver.findElement(By.xpath("//label[text()='" + value + "']"));
        return checkbox;
    }

    private boolean isButtonEnabledInSearchandScheduleTab(WebElement link) {
        //_3uhLnGGw9ic0jbBIDirRkC is the class that is added to indicate css active
        return link.getAttribute("class").contains("_3uhLnGGw9ic0jbBIDirRkC");
    }
    private boolean isButtonDisabledInSearchandScheduleTab(WebElement link){
        return link.getAttribute("class").contains("lM1ka_IX-p7Hiuh9URqAJ");
    }

    /**
     * Gets the web element container of the RepVisits branding header
     * @return Webelement
     */
    private WebElement getRepVisitsBrandingHeaderContainer(){
        WebElement headerContainer = driver.findElement(By.cssSelector(
                "div[class='hidden-mobile hidden-tablet _3ExBVDvA2sy-YCcBYK00PU']"));
        return headerContainer;

    }

    /**
     * Gets the web element container of the text to search a school
     * @return Webelement
     */
    private WebElement getSchoolFindOutInTheSearch(){
        WebElement schoolFoundInSupport = textbox(By.id("global-search-box-input"));
        return schoolFoundInSupport;

    }

    private WebElement fairNameHeader() { return getDriver().findElement(By.cssSelector("h1.ui.header")); }
    private WebElement fairHSName() { return getDriver().findElement(By.cssSelector("div._3RqdVnu3tniPVn-91Bd61M")); }
    private WebElement fairHSAddress() { return getDriver().findElement(By.cssSelector("div.rvsP8MMShznvvZWePlkUO")); }
    private WebElement fairContactName() { return getDriver().findElement(By.cssSelector("div._3gQNLVW3B3CfdELmCdwdfG")); }
    private WebElement fairContactTitle() { return getDriver().findElement(By.cssSelector("div._25t7gNvT8MVtZfRUq4WSK3")); }
    private WebElement fairContactEmail() { return getDriver().findElement(By.cssSelector("div._38U_qKPRgGTogiYwQidWQu")); }
    private WebElement fairContactPhone() { return getDriver().findElement(By.cssSelector("div._2Yft-cZFY8BFL0J6NPSWna")); }
    private WebElement fairDate() { return getDriver().findElement(By.xpath("//div[@class='_3dJVJHDv8f4yi7W71sBMV7']/div[@class='ui grid']/div[3]/div[1]/div/span")); }
    private WebElement fairStartTime() { return getDriver().findElement(By.xpath("//div[@class='_3dJVJHDv8f4yi7W71sBMV7']/div[@class='ui grid']/div[3]/div[2]/div/span[1]")); }
    private WebElement fairEndTime() { return getDriver().findElement(By.xpath("//div[@class='_3dJVJHDv8f4yi7W71sBMV7']/div[@class='ui grid']/div[3]/div[2]/div/span[2]")); }
    private WebElement fairTimeZone() { return getDriver().findElement(By.xpath("//div[@class='_3dJVJHDv8f4yi7W71sBMV7']/div[@class='ui grid']/div[3]/div[2]/div/span[3]")); }
    private WebElement fairCost() { return getDriver().findElement(By.xpath("//div[@class='_3dJVJHDv8f4yi7W71sBMV7']/div[@class='ui grid']/div[4]/div[1]/div")); }
    private WebElement fairExpectedStudents() { return getDriver().findElement(By.xpath("//div[@class='_3dJVJHDv8f4yi7W71sBMV7']/div[@class='ui grid']/div[4]/div[2]/div")); }
    private WebElement fairInstructions() { return getDriver().findElement(By.xpath("//div[@class='_3dJVJHDv8f4yi7W71sBMV7']/div[@class='ui grid']/div[5]/div/div")); }
    private WebElement fairCancellationInstructions() { return getDriver().findElement(By.xpath("//div[@class='_3JCQh0qrOlZKBKE8m4D5Iz']/span/span")); }
    private WebElement fairCancelLink() { return getDriver().findElement(By.cssSelector("div._3JCQh0qrOlZKBKE8m4D5Iz button span")); }
    private WebElement fairInternalNotesTextBox() { return getDriver().findElement(By.cssSelector("input[aria-label=\"Internal Notes\"]")); }
    private WebElement fairSaveButton() { return getDriver().findElement(By.cssSelector("button.ui.teal.right.floated.button span")); }
    private WebElement fairSavedConfirmationMessage() { return getDriver().findElement(By.cssSelector("div.content span span")); }
}


