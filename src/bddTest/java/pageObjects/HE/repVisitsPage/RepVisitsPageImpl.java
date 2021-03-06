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
import org.openqa.selenium.interactions.Actions;
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
import static pageObjects.HS.repVisitsPage.RepVisitsPageImpl.FairName;
import static pageObjects.HS.repVisitsPage.RepVisitsPageImpl.StartTime;

public class RepVisitsPageImpl extends PageObjectFacadeImpl {

    protected Logger logger;
    public static String formattedDate;
    public static String currentURL;

    public RepVisitsPageImpl() {
        logger = Logger.getLogger(RepVisitsPageImpl.class);
    }

    public void checkRepVisitsSubTabs(DataTable dataTable){
        getNavigationBar().goToRepVisits();
        List<String> list = dataTable.asList(String.class);
        for (String repVisitsSubItem : list) {
            Assert.assertTrue(repVisitsSubItem + " is not showing.",link(repVisitsSubItem)!=null);
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
        getDriver().switchTo().defaultContent();
            getDriver().switchTo().frame(0);

        communityFrame();
        /*    WebDriverWait wait = new WebDriverWait(driver, 40);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), 'Check RepVisits Availability')]"))).click();
        *///wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(), 'Check RepVisits Availability')]"))).click();
        getDriver().findElement(By.xpath("//a[contains(text(), 'Check RepVisits Availability')]")).click();

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
        getNavigationBar().goToRepVisits();
        navigateToRepVisitsSection("Search and Schedule");
        //text("Search for a school...").sendKeys(highSchool);
        getSearchBox().sendKeys(highSchool);
        getSearchButton().click();
        text(highSchool).click();
        text("Fairs").click();
        if (text(fairTitle).isDisplayed()){
            waitUntilPageFinishLoading();

            getDriver().findElement(By.xpath("//span[contains(text(), '"+FairName+"')]/../following-sibling::div/button[contains(text(),'Register')]")).click();
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
                        String header = getDriver().findElement(By.xpath(".//h2[@class='ui header _1Lpvqe0tqVOX5RBm2576B2']")).getText();
                        String a = school.get(key);
                        Assert.assertTrue("School name was not found in header.", header.contains(school.get(key)));
                        break;
                    case "High School Contact:":
                        String contactLink = getDriver().findElement(By.className("_16fRAQOOUtmia2wftYHDhf")).getText();
                        Assert.assertTrue("Contact name was not found in header.",contactLink.contains(school.get(key)));
                        break;
                    default:
                        WebElement label = getDriver().findElement(By.xpath("//div[contains(text(),'" + key + "')]"));
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
        getNavigationBar().goToRepVisits();
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
        WebElement appointmentSlot = getDriver().findElement(By.xpath("//span[text()='"+school+"']/following-sibling::span[text()='"+time+"']"));
        Assert.assertTrue("Appointment Slot time and university is not displayed",appointmentSlot.isDisplayed());
        jsClick(appointmentSlot);
        waitUntilPageFinishLoading();
    }

    public void cancelFairAppointmentfromCalendar(){
        jsClick(getDriver().findElement(By.xpath("//input[@aria-label='Internal Notes']")));
        getDriver().findElement(By.xpath("//input[@aria-label='Internal Notes']")).sendKeys(Keys.PAGE_DOWN);
        Assert.assertTrue("Cancel This Visit is not displayed",getDriver().findElement(By.xpath("//button/span[text()='Cancel This Fair']")).isDisplayed());
        button("Cancel This Fair").click();
        waitUntilPageFinishLoading();
        getDriver().findElement(By.id("cancel-message")).click();
        waitUntilPageFinishLoading();
        getDriver().findElement(By.id("cancel-message")).sendKeys(Keys.PAGE_DOWN);
        getDriver().findElement(By.id("cancel-message")).sendKeys("by QA");
        button("Yes, Cancel Fair").click();
        waitUntilPageFinishLoading();
    }

    public void verifyExportButtonInCalendar(){
        waitUntilPageFinishLoading();
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        link("Calendar").click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(calendarDayButton()));
        Assert.assertTrue("Export button is Enabled in Calendar page",getDriver().findElement(By.xpath("//button[@class='ui teal basic disabled button _1I0GHfcjpniiDr2MOWxpxw _3Rc-fBQEQJr4FpMhLBYL0m']")).isDisplayed());
    }

    public void verifyExportButtonisEnabledInCalendar(){
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        link("Calendar").click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(calendarDayButton()));
        Assert.assertTrue("Export button is not enabled",exportButton().isEnabled());
    }

    public void exportAppointmentsInCalendarPage(String startDate,String endDate){
        exportButton().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/span[text()='Export Settings']")));
        Assert.assertTrue("Export Settings text is not displayed in the Export button",getDriver().findElement(By.xpath("//div/span[text()='Export Settings']")).isDisplayed());
        Assert.assertTrue("From Text is not displayed in the Export button",getDriver().findElement(By.xpath("//span[text()='From']")).isDisplayed());
        Assert.assertTrue("To text is not displayed in the Export button",getDriver().findElement(By.xpath("//span[text()='To']")).isDisplayed());
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
        WebElement schoolName=getDriver().findElement(By.xpath("//h3/a[text()='"+school+"']"));
        waitUntilElementExists(schoolName);
        Assert.assertTrue("school is not displayed",schoolName.isDisplayed());
        waitUntilElementExists(goToDate());
        String gotoDate = getSpecificDate(startDate);
        setDate(gotoDate, "Go To Date");
        String visitTime = pageObjects.HS.repVisitsPage.RepVisitsPageImpl.StartTime;
        String visitDate=getMonthandDate(startDate);
        WebElement button= getDriver().findElement(By.xpath("//span[text()='"+visitDate+"']/parent::th/ancestor::thead/following-sibling::tbody/tr//td//div/button[text()='"+visitTime+"']"));
        Assert.assertTrue("Availability is not displayed",button.isDisplayed());
    }

    public void verifyPillsIsNotPresent(String school,String startDate,String time){
        visit().click();
        waitUntilPageFinishLoading();
        WebElement schoolName = getDriver().findElement(By.xpath("//h3/a[text()='"+school+"']"));
        waitUntilElementExists(schoolName);
        Assert.assertTrue("school is not displayed",schoolName.isDisplayed());
        waitUntilElementExists(goToDate());
        String gotoDate = getSpecificDate(startDate);
        setDate(gotoDate, "Go To Date");
        String visitTime = pageObjects.HS.repVisitsPage.RepVisitsPageImpl.StartTime;
        String visitDate = getMonthandDate(startDate);
        List<WebElement> button = getDriver().findElements(By.xpath("//span[text()='"+visitDate+"']/parent::th/ancestor::thead/following-sibling::tbody/tr//td//div/button[text()='"+visitTime+"']"));
        if(button.size()==0) {
            logger.info("appointment is not displayed");
        }else{
            logger.info("appointment is displayed");}
    }

    private void validateInfolink(){
        Assert.assertTrue("Text 'For more information' is not displayed", text("For more information:").isDisplayed());
        WebElement item = getParent(text("For more information:"));
        item.findElement(By.tagName("a")).click();
        Assert.assertTrue("Did not end up in Community!", getDriver().getCurrentUrl().contains("counselor-community/institution"));
    }

    public String selectdateforExportAppointmentsIncalendar(String addDays) {
        String DATE_FORMAT_NOW = "MMMM d yyyy";
        Calendar cal = Calendar.getInstance();
        int days=Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }

    public void switchToSupportApp(){
        String HEWindow = getDriver().getWindowHandle();
        String supportWindow = null;
        Set<String> windows = getDriver().getWindowHandles();
        for(String thisWindow : windows){
            if(!thisWindow.equals(HEWindow)){
                supportWindow = thisWindow;
            }
        }
        getDriver().switchTo().window(supportWindow);
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("js-main-nav-admin-menu-link")));
        waitUntilPageFinishLoading();
    }

    public void postMessageInHomePage(String message){
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[name='mainmenu']")));
        getNavigationBar().goToCommunity();
        waitUntilPageFinishLoading();
        WebElement element=getDriver().findElement(By.xpath("//iframe[@class='_2ROBZ2Dk5vz-sbMhTR-LJ']"));
        getDriver().switchTo().frame(element);
        waitForUITransition();
        Assert.assertTrue("Message text box is not displayed",getDriver().findElement(By.id("edit-body")).isDisplayed());
        getDriver().findElement(By.id("edit-body")).click();
        getDriver().findElement(By.id("edit-body")).sendKeys(message);
        Assert.assertTrue("Message text box is not displayed",getDriver().findElement(By.id("edit-save")).isDisplayed());
        getDriver().findElement(By.id("edit-save")).click();
        waitUntilPageFinishLoading();
        getDriver().switchTo().defaultContent();
    }

    public void verifySearchAndSchedulePage() {
        navigateToRepVisitsSection("Search and Schedule");
        WebElement dateBar = getDriver().findElement(By.className("_2Y4XoXCJpDOFoe0UYkEn-I"));
        // These calendar controls have been moved to only appear after a search, this is covered by MATCH-2133.
        // Move these validations into that ticket when automated.
//        Assert.assertTrue("Previous Week button is not present!",dateBar.findElement(By.cssSelector("[aria-label='Previous week']")).isDisplayed());
//        Assert.assertTrue("Next Week button is not present!",dateBar.findElement(By.cssSelector("[aria-label='Next week']")).isDisplayed());
//        Assert.assertTrue("Calendar button is not present!",dateBar.findElement(By.className("calendar")).isDisplayed());
//        Assert.assertTrue("Placeholder text for search box was not present!", textbox("Enter a school name or location").isDisplayed());
    }

    public void searchforHighSchool(String schoolName) {
        getNavigationBar().goToRepVisits();
        navigateToRepVisitsSection("Search and Schedule");
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
        getNavigationBar().goToRepVisits();
        getContactsBtn().click();
        Assert.assertTrue("Contact Header is not displayed",text("").isDisplayed());
        Assert.assertTrue("Instruction text is not displayed",text("").isDisplayed());

    }
    public void verifyFullContactPage(){
        getNavigationBar().goToRepVisits();
        getContactsBtn().click();
        Assert.assertTrue("Contact Header is not displayed",getDriver().findElement(By.xpath("//h1[@class='ui header _2GIsNevIB_s082IZwcYen3']")).isDisplayed());
        Assert.assertTrue("Instruction text is not displayed",getDriver().findElement(By.xpath("//div[@class='sub header _240ldPuujUDvP5vNIGw15H']")).isDisplayed());
        List<WebElement> searchedValueOfName = getDriver().findElements(By.className("_1ijSBYwG-OqiUP1_S7yMUN"));
        int size = searchedValueOfName.size();
        Assert.assertTrue("RepVisits contact are not displayed",size>0);
    }
    public void verifyContactDetails(DataTable dataTable){
        getNavigationBar().goToRepVisits();
        getContactsBtn().click();
        List<String> list = dataTable.asList(String.class);
        for (String repVisitsSubItem : list) {
            Assert.assertTrue(repVisitsSubItem + " is not showing.",getDriver().findElement(
                    By.xpath(String.format("//span[text()='%s']",repVisitsSubItem))).isDisplayed());
        }
    }
    public void verifyFullorEmpty(){
    try{ if(text("Welcome to Contacts").isDisplayed())
    {
        logger.info("you have no Contacts");
    }
        else if(getDriver().findElement(By.xpath("//span[text()='Show More']")).isDisplayed()) {
        while (getDriver().findElement(By.xpath("//span[text()='Show More']")).isDisplayed()){
            getDriver().findElement(By.xpath("//span[text()='Show More']")).click();
        }}else{}}
        catch(Exception e){}}
    public void verifyinvalidcontact(String invalidData){
        getSearchBoxforContact().clear();
        getSearchBoxforContact().sendKeys(invalidData);
        Assert.assertTrue("the message of 'Your search did not return any contacts.' is not displayed",text("Your search did not return any contacts.").isDisplayed());
    }

    public void sortingContacts()
    {
        getNavigationBar().goToRepVisits();
        getContactsBtn().click();
        getDriver().findElement(By.xpath("//input[@name='contacts-search']")).clear();
        ArrayList<String> original=new ArrayList<>();
        List<WebElement> elements=getDriver().findElements(By.xpath("//tr[@class='_1ijSBYwG-OqiUP1_S7yMUN']/td[2]/div[1]"));
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
        getNavigationBar().goToRepVisits();
        getContactsBtn().click();
        waitUntil(ExpectedConditions.visibilityOf(button("EXPORT CONTACTS")));
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[@class='_1ijSBYwG-OqiUP1_S7yMUN']")));
        Assert.assertTrue("Contacts is not displayed",getDriver().findElement(By.xpath("//tr[@class='_1ijSBYwG-OqiUP1_S7yMUN']")).isDisplayed());
        count=getDriver().findElements(By.xpath("//tr[@class='_1ijSBYwG-OqiUP1_S7yMUN']")).size();
        Assert.assertTrue("The pagination has more than 25 contacts",count<=25);

    }

    public void searchforContact(String institutionName){
        getNavigationBar().goToRepVisits();
        getContactsBtn().click();
        getSearchBoxforContact().sendKeys(institutionName);
        waitForUITransition();
        //_1ijSBYwG-OqiUP1_S7yMUN is the class for the rows or the results table.
        String schoolName = getDriver().findElement(By.className("_1ijSBYwG-OqiUP1_S7yMUN")).findElement(By.xpath(".//div[@class='_2ZIfaO8qcJzzQzgSfH1Z8h']")).getText();
        Assert.assertTrue("The specified school name is not displayed.  Expected: " + institutionName + ", Actual: " + schoolName,schoolName.equalsIgnoreCase(institutionName));
    }

    public void partialsearchforContact(String institutionName){
        getNavigationBar().goToRepVisits();
        getContactsBtn().click();
        getSearchBoxforContact().sendKeys(institutionName);
        waitUntilPageFinishLoading();
        List<WebElement> searchedValueOfinstitutionName = getDriver().findElements(By.cssSelector("div[class='_2ZIfaO8qcJzzQzgSfH1Z8h']"));
        for(int i=0;i<searchedValueOfinstitutionName.size();i++){
            String value = searchedValueOfinstitutionName.get(i).getText();
            Assert.assertTrue("Partial matching on institution name is not available",value.toLowerCase().contains(institutionName.toLowerCase()));
        }
    }
    public void selectHighSchoolFromIntermediateSearchResults(String schoolName, String location) {
        getDriver().findElement(By.xpath(String.format(".//td//a[text()='%s']",schoolName))).click();
        waitUntilPageFinishLoading();
    }

    public void selectHighSchoolFromResults(String schoolName) {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td/h3/a[text()='"+schoolName+"']")));
        getDriver().findElement(By.xpath("//td/h3/a[text()='"+schoolName+"']")).click();
        waitUntilPageFinishLoading();
    }

    public void viewMapPlugin() {
        getMapButton().click();
        waitUntilPageFinishLoading();
    }

    public void verifyOverviewPage(){
        getNavigationBar().goToRepVisits();
        getOverviewBtn().click();
        waitUntilElementExists(getComingSoonMessageInOverviewPage());
        Assert.assertTrue("Coming Soon message is not displaying",
                getComingSoonMessageInOverviewPage().getText().equals("The Overview Dashboard is coming soon. It will provide a quick view of your upcoming appointments and most recent notifications."));
    }

    public void verifyCheckRepVisitsAvailabilityButton(){
        waitForUITransition();
        waitUntil(ExpectedConditions.frameToBeAvailableAndSwitchToIt(0));
        waitUntil(ExpectedConditions.visibilityOfElementLocated(checkVisitAvailabilityButtonLocator()));
        Assert.assertTrue("Check RepVisits Availability Button is not present", getCheckRepVisitsAvailabilityButton().isDisplayed());
        waitUntil(ExpectedConditions.visibilityOf(getCheckRepVisitsAvailabilityButton()));
        JavascriptExecutor executor = getDriver();
        executor.executeScript("arguments[0].click();",getCheckRepVisitsAvailabilityButton());
        //getCheckRepVisitsAvailabilityButton().click();
        getDriver().switchTo().defaultContent();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(getRepVisitsAvailabilityLabeLocator()));
        Assert.assertTrue("RepVisits Availability Sidebar is not displaying.", getRepVisitsAvailabilitySidebar().isDisplayed());
    }

    public void navigatetoRepVisits()
    {
        getNavigationBar().goToRepVisits();
        navigateToRepVisitsSection("Availability & Settings");
    }

    public void selectAllRepVisitsUser(String option){
        getDriver().findElement(By.xpath("//ul[@class='ui pointing secondary fourth menu']//h2/span[text()='Availability Settings']")).click();
        waitUntilElementExists(saveChanges());
        getDriver().findElement(By.xpath("//label[text()='"+option+"']")).click();
        button("Save Changes").click();
    }

    public void clickRegistrationButton(String fairName) {
        waitUntilPageFinishLoading();
        if(fairName.equalsIgnoreCase("PreviouslySetFair")){
            fairName = FairName;
        }
        getRegistrationButton(fairName).click();
    }

    public void registerFair(String fairName) {
        if (fairName.equals("PreviouslySetFair")) {
            fairName = pageObjects.HS.repVisitsPage.RepVisitsPageImpl.FairName;
        }
        clickRegistrationButton(fairName);
        submitRequestButton().click();
    }

    public void verifyConfirmationPopup(String highSchoolName) {
        Assert.assertTrue("The fair date is not displayed", getFairDate().isDisplayed());
        String startDate = getStartEndTimeAndTimeZone().getText().split("-")[0];
        String endDate = getStartEndTimeAndTimeZone().getText().split("-")[1].split(" ")[0];
        String timeZone = getStartEndTimeAndTimeZone().getText().split("-")[1].split(" ")[1];
        softly().assertThat(startDate).as("The Start time does not have a correct format: " + startDate).matches("([0-9]):([0-9])?([0-9])([ap])([m])");
        softly().assertThat(endDate).as("The End time does not have a correct format: " + endDate).matches("([0-9]):([0-9])?([0-9])([ap])([m])");
        softly().assertThat(timeZone).as("The time zone does not have a correct format: " + timeZone).matches("([ABCDEFGHIJKLMNOPQRSTUVWXYZ])([ABCDEFGHIJKLMNOPQRSTUVWXYZ])([ABCDEFGHIJKLMNOPQRSTUVWXYZ])");
        softly().assertThat(requestText().getText()).as("The High School name is not displayed").contains(highSchoolName);
        softly().assertThat(submitRequestButton().getText()).as("The confirmation button's text is not correct: " + submitRequestButton().getText()).isEqualToIgnoringCase("Yes, Submit Request");
        softly().assertThat(cancelButton().getText()).as("The cancel button's text is not correct").isEqualToIgnoringCase("cancel");
    }

    public void closeFairRequestPopup() {
        cancelButton().click();
    }

    public void clickFairsTab() {
        waitUntilPageFinishLoading();
        fairsTab().click();
        waitUntilPageFinishLoading();
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
        waitUntil(ExpectedConditions.visibilityOf(upperMessage()));
        String a = upperMessage().getText();
        Assert.assertTrue("The sucess message for fairs with Auto Approval enabled is not displayed",
                upperMessage().getText().trim().equalsIgnoreCase("Fair registration confirmed! Your request has been automatically confirmed by the high school."));
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
            if(fairName.equalsIgnoreCase("PreviouslySetFair")){
                fairName = FairName;
            }
            getDriver().findElements(By.xpath(String.format(".//span[text()='%s']",fairName)));
            getDriver().findElements(By.xpath(String.format(".//span[text()='%s']/preceding-sibling::span[text()='%s']",
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
        waitUntil(ExpectedConditions.visibilityOf(rightCalendarRightButton()));
        for (int i = 0; i < tries; i++) {
            if (!rightCalendarHeaderDate().getText().trim().split(" ")[0].equals(month)) {
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
        String gotoDate="",Date="";
        navigateToRepVisitsSection("Search and Schedule");
        getSearchBox().sendKeys(schoolName);
        getSearchButton().click();
        selectHighSchoolFromResults(schoolName);
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(dateButton()));
        getDriver().findElement(dateButton()).click();
        if(date.contains("In") && date.contains("day")){
            int relativeDays =  Integer.parseInt(date.replaceAll("[^0-9]",""));
            date = getRelativeDate(relativeDays).split(" ")[1]+" "+
                    getRelativeDate(relativeDays).split(" ")[0]+" "+hour;
            Date = date.split(" ")[1];
            int currentDate = Integer.parseInt(Date);
            gotoDate = getSpecificDate(currentDate,"MMMM d yyyy");
        }else if (date.length()>2){
            gotoDate = date.split(" ")[0]+" "+date.split(" ")[1]+" "+date.split(" ")[2];
        }
        boolean result = false;
        setDateFixed(gotoDate);
        waitUntil(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.ui.medium.inverted.loader")));
        for (WebElement fairElement : quickViewFairsList()) {
            if (fairElement.getText().contains(schoolName) && fairElement.getText().contains(date.split(" ")[2].toLowerCase())) {
                result = true;
            }
        }
        softly().assertThat(result).as("The fair is not displayed in the Quick View").isTrue();
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
        getNavigationBar().goToCommunity();
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
        navigateToRepVisitsSection("Search and Schedule");
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
        Assert.assertTrue("High School specila instructions are not matching/available",getParent(getDriver().findElement(By.className("kuh1rp3g-UeGhggKqCdPA"))).findElement(By.cssSelector("div:nth-child(4)")).getText().contains(instructions));
    }
    public void selectSchoolFromMap(String schoolName) {
        button(schoolName).click();
        waitForUITransition();
        waitUntilPageFinishLoading();
    }

    public void clickUpgradeButton(){
        getUpgradeButton().click();
        waitUntilPageFinishLoading();
    }
    public void searchSchoolinRepVisits(String school)
    {
        getNavigationBar().goToRepVisits();
        getSearchBox().sendKeys(school);
        waitUntilElementExists(search());
        getSearchButton().click();
        WebElement schoolName=getDriver().findElement(By.xpath("//td//a[contains(text(),'"+school+"')]"));
        waitUntilElementExists(schoolName);
        Assert.assertTrue("school is not displayed",getDriver().findElement(By.xpath("//a[contains(text(),'"+school+"')]")).isDisplayed());
        getDriver().findElement(By.xpath("//a[contains(text(),'"+school+"')]")).click();
    }

    public void verifyBlockedAvailability(String school,String Date,String time) {
        visit().click();
        waitUntilElementExists(getDriver().findElement(By.xpath("//div/h3/a[text()='"+school+"']")));
        Assert.assertTrue("school is not displayed",getDriver().findElement(By.xpath("//div/h3/a[text()='"+school+"']")).isDisplayed());
        waitUntilElementExists(goToDate());
        String gotoDate = getSpecificDate(Date);
        setDate(gotoDate, "Go To Date");
        String date=getMonthandDate(Date);
        String Time=pageObjects.HS.repVisitsPage.RepVisitsPageImpl.StartTime;
        List<WebElement> slot = getDriver().findElements(By.xpath("//span[text()='"+date+"']/parent::th/ancestor::thead/following-sibling::tbody/tr//td//div/button[text()='"+Time+"']"));
        if(slot.size()==0) {
            logger.info("Slot is not displayed");
        }
    }

/*    public void visitsSchedule(String school,String date,String time)
    {
        getDriver().findElement(By.xpath("//span[text()='Visits']")).click();
        WebElement schoolName=getDriver().findElement(By.xpath("//a[text()='"+school+"']"));
        waitUntilElementExists(schoolName);
        Assert.assertTrue("school is not displayed",getDriver().findElement(By.xpath("//div/a[text()='"+school+"']")).isDisplayed());
        waitUntilElementExists(goToDate());
        getDriver().findElement(By.xpath("//button[text()='Go To Date']")).click();
        setSpecificDate(7);
        getDriver().findElement(By.xpath("//div/div/button[text()='"+time+"']")).click();
    }*/

    public void verifyUpgradePopupAndInformations(DataTable dataTable){
        List<Map<String,String>> entities = dataTable.asMaps(String.class,String.class);
        for (Map<String,String> UpgradeInformationPopup : entities ) {
            for (String key : UpgradeInformationPopup.keySet()) {
                switch (key) {
                    case "First Name":
                        String actualFirstName = getDriver().findElement(By.id("field13")).getAttribute("value");
                        Assert.assertTrue("First Name was not as expected.", actualFirstName.contains(UpgradeInformationPopup.get(key)));
                        break;
                    case "Last Name":
                        String actualLastName = getDriver().findElement(By.id("field14")).getAttribute("value");
                        Assert.assertTrue("Last Name was not as expected.", actualLastName.equals(UpgradeInformationPopup.get(key)));
                        break;
                    case "Work Email Address":
                        String actualEmailAddress = getDriver().findElement(By.id("field12")).getAttribute("value");
                        Assert.assertTrue("Work Email Address was not as expected.", actualEmailAddress.equals(UpgradeInformationPopup.get(key)));
                        break;
                    case "Phone":
                        String actualPhone = getDriver().findElement(By.id("field15")).getAttribute("value");
                        Assert.assertTrue("Phone was not as expected.", actualPhone.equals(UpgradeInformationPopup.get(key)));
                        break;
                    case "School / Institution Name":
                        String actualSchoolInstitutionName = getDriver().findElement(By.id("field16")).getAttribute("value");
                        Assert.assertTrue("School / Institution Name was not as expected.", actualSchoolInstitutionName.equals(UpgradeInformationPopup.get(key)));
                        break;
                    case "Message":
                        String actualMessage = getDriver().findElement(By.id("field18")).getText();
                        Assert.assertTrue("Messages was not as expected.", actualMessage.equals(UpgradeInformationPopup.get(key)));
                        break;
                }
            }
            button("Request Information").click();
            Assert.assertTrue("success message is not displayed",getDriver().findElement(By.xpath("//span[text()='Thanks!']")).isDisplayed());
            getDriver().findElement(By.xpath("//div[@id='upgrade-form']/i[@class='close icon']")).click();
        }

    }

    public void verifyUpgradeMessageInRecommendationspage()
      {
          navigateToRepVisitsSection("Recommendations");
          waitUntilPageFinishLoading();
          Assert.assertTrue("'Premium Feature' text is not displayed",text("Premium Feature").isDisplayed());
          Assert.assertTrue("'UPGRADE' button is not displayed",getUpgradeButton().isDisplayed());
          Assert.assertTrue("'Lock' Icon is not displayed",getDriver().findElement(By.xpath("//img[@alt='locked']")).isDisplayed());
      }


   public void verifyUpgradeMessageInContactspage()
   {
       navigateToRepVisitsSection("Contacts");
       waitUntilPageFinishLoading();
       Assert.assertTrue("'Premium Feature' text is not displayed",text("Premium Feature").isDisplayed());
       Assert.assertTrue(getUpgradeButton().isDisplayed());
       Assert.assertTrue("'Lock' Icon is not displayed",getDriver().findElement(By.xpath("//img[@alt='locked']")).isDisplayed());
   }
    public void verifySearchResultOfSearchAndSchedule(DataTable dataTable){
        DataTable transposedTable = dataTable.transpose();
        Map<String,String> searchMap = transposedTable.asMap(String.class, String.class);
        Set<String> searchCategory = searchMap.keySet();
        for (String category : searchCategory ) {
            searchforHighSchool(searchMap.get(category));
            try {
                Assert.assertTrue("HS data is showing while searching through "+category+" in Search And Schedule page", getDriver().findElement(By.xpath("//table[@class='ui very basic table']")).isDisplayed());
            } catch (NoSuchElementException nsee){
                Assert.assertTrue("HS data is not showing while searching through "+category+" in Search And Schedule page", getDriver().findElement(By.xpath("//span[contains(text(),'No results found')]")).isDisplayed());
            }
        }
    }
    public void selectDate(String date)
    {
        String[] value=date.split(" ");
        getDriver().findElement(By.xpath("//button[text()='Go To Date']")).click();
        String yearandmonth=value[0]+" "+value[2];
        String element=getDriver().findElement(By.xpath("//div[@class='DayPicker-Month']/div")).getText();
        if(yearandmonth.equals(element))
        {
            getDriver().findElement(By.xpath("//div[text()='"+value[1]+"']")).click();
        }else{
            while(!yearandmonth.equals(element))
            {
                getDriver().findElement(By.xpath("//div[@class='DayPicker-NavBar']/span[2]")).click();
                element=getDriver().findElement(By.xpath("//div[@class='DayPicker-Month']/div")).getText();
            }
            getDriver().findElement(By.xpath("//div[text()='"+value[1]+"']")).click();
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
        getDriver().findElement(By.cssSelector("div[class='DayPicker-Body']")).findElement(By.xpath("//div[contains(@class,'DayPicker-Day') and @aria-disabled='false' and text()='"+date+"']")).click();
            }

    public void clickDisabledDate(String date) {
        getDriver().findElement(By.cssSelector("div[class='DayPicker-Body']")).findElement(By.xpath("//div[contains(@class,'DayPicker-Day DayPicker-Day--disabled') and @aria-disabled='true' and text()='"+date+"']")).click();
    }

    public void findMonth(String month) {

        String DayPickerCaption = getDriver().findElement(By.cssSelector("div[class='DayPicker-Caption']")).getText();

        try{
            while (!DayPickerCaption.contains(month)) {
                getDriver().findElement(By.cssSelector("span[class='DayPicker-NavButton DayPicker-NavButton--next']")).click();
                DayPickerCaption = getDriver().findElement(By.cssSelector("div[class='DayPicker-Caption']")).getText();
            }
        }
        catch (Exception e) {
            Assert.fail("The Date selected it's out of RANGE.");
        }
    }


    public void  verifyPills(String time,String school)
    {
        try {
            getDriver().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            if(getDriver().findElement(By.xpath("//h3[text()='"+school+"']/../../../../following-sibling::td//div/button[text()='"+time+"']")).isDisplayed()) {
                Assert.fail("Time slot is displayed");
                getDriver().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            }
        } catch (Exception e)
        {
            getDriver().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        }
    }
    public void verifyUpgradeMessageInTravelPlanInRepVisits(){
        navigateToRepVisitsSection("Travel Plan");
        waitUntilPageFinishLoading();
        Assert.assertTrue("'Premium Feature' text is not displayed",text("Premium Feature").isDisplayed());
        Assert.assertTrue("'UPGRADE' button is not displayed",getUpgradeButton().isDisplayed());
        Assert.assertTrue("'Lock' Icon is not displayed",getDriver().findElement(By.cssSelector(" img[alt='locked']")).isDisplayed());
    }

    public void verifySeeDetailsLinkInRepVisits(){

        navigateToRepVisitsSection("Travel Plan");
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//h1/span[text()='Travel Plan']")));
        Assert.assertTrue("'See Details' text is not displayed",text("See details").isDisplayed());
        travelPlanSeeDetailsLink().click();
        Assert.assertTrue("Fairs Tab it' active",text("Fairs").isDisplayed());
        Assert.assertTrue("'Fairs'  button it's not activated.",getDriver().findElement(By.cssSelector("button[class='ui button _3uhLnGGw9ic0jbBIDirRkC']")).isDisplayed());
        Assert.assertTrue("'All Fairs' does not not displayed.",getDriver().findElement(By.cssSelector("div[class='_135QG0V-mOkCAZD0s14PUf']")).isDisplayed());
        Assert.assertTrue("Show All Fais does not displayed",text("Showing All Scheduled Fairs").isDisplayed());
    }


    public void searchforPartialdata(String institutionName,String partial)
    {
    getNavigationBar().goToRepVisits();
    getContactsBtn().click();
    getSearchBoxforContact().clear();
    getSearchBoxforContact().sendKeys(partial);
    Assert.assertTrue("the specified schoolname is not displayed",getDriver().findElement(By.xpath("//tr[@class='_1ijSBYwG-OqiUP1_S7yMUN']/td[@class='five wide hidden-mobile']/div[contains(text(),'"+institutionName+"')]")).isDisplayed());
    }

    public void verifyUpgradeMessageInContactsInRepVisits(){

        getNavigationBar().goToRepVisits();
        getContactsBtn().click();
        Assert.assertTrue("'Premium Feature' text is not displayed",text("Premium Feature").isDisplayed());
        Assert.assertTrue("'UPGRADE' text is not displayed",text("UPGRADE").isDisplayed());
        Assert.assertTrue("'Lock' Icon is not displayed",getDriver().findElement(By.xpath("//img[@alt='locked']")).isDisplayed());
    }

    public void verifyUpgradeMessageInRecommendationsInRepVisits(){

        getNavigationBar().goToRepVisits();
        getRecommendationsBtn().click();
        Assert.assertTrue("'Premium Feature' text is not displayed",text("Premium Feature").isDisplayed());
        Assert.assertTrue("'UPGRADE' text is not displayed",text("UPGRADE").isDisplayed());
        Assert.assertTrue("'Lock' Icon is not displayed",getDriver().findElement(By.xpath("//img[@alt='locked']")).isDisplayed());
    }

    public void verifyCalendarViewOnRepVisits() {
        getNavigationBar().goToRepVisits();
        link("Calendar").click();

        //Verify Small Calendar
        Assert.assertTrue("Small Calendar is not displayed",getDriver().findElement(By.cssSelector("div[role='application']")).isDisplayed());
        Assert.assertTrue("small calendar next button is not displayed",getDriver().findElement(By.cssSelector("button[title='right']>i")).isDisplayed());
        Assert.assertTrue("small calendar previous button is not displayed",getDriver().findElement(By.cssSelector("button[title='left']>i")).isDisplayed());

        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement mainCalendarNextBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[title='Forwards']>i")));

        //Verify Main Calendar
        Assert.assertTrue("Main Calendar is not displayed",getDriver().findElement(By.cssSelector("div[class='rbc-calendar rep-visits-rbc-calendar ']")).isDisplayed());
        Assert.assertTrue("Main calendar next button is not displayed",getDriver().findElement(By.cssSelector("button[title='Forwards']>i")).isDisplayed());
        Assert.assertTrue("Main calendar previous button is not displayed",getDriver().findElement(By.cssSelector("button[title='Backwards']>i")).isDisplayed());

        //verify day view
        Assert.assertTrue(" Day view button is not displayed",button("Day").isDisplayed());
        getDriver().findElement(By.cssSelector("button[title='Day']")).click();
        Assert.assertTrue("Day view is not displayed",getDriver().findElement(By.cssSelector("div[class='rbc-time-view']")).isDisplayed());

        //verify week view
        Assert.assertTrue(" Week view button is not displayed",button("Week").isDisplayed());
        getDriver().findElement(By.cssSelector("button[title='Week']")).click();
        Assert.assertTrue("Week view is not displayed",getDriver().findElement(By.cssSelector("div[class='rbc-time-view']")).isDisplayed());

        //verify month view
        Assert.assertTrue(" Month view button is not displayed",button("Month").isDisplayed());
        getDriver().findElement(By.cssSelector("button[title='Month']")).click();
        Assert.assertTrue("Month view is not displayed",getDriver().findElement(By.cssSelector("div[class='rbc-month-view']")).isDisplayed());

        // Appointments are clickable
        getDriver().findElement(By.cssSelector("div[class='rbc-event']")).click();
        waitForUITransition();
        Assert.assertTrue("The Wizard contains the appointment details are not displayed",getDriver().findElement(By.cssSelector("div[class='ui overlay right very wide visible sidebar _1bTs4IjZQSsADQ671qHLL3']")).isDisplayed());
        getDriver().findElement(By.xpath("//button[@aria-label='Close']")).click();

        //verify Appointment Keys
        //Visits
        Assert.assertTrue(" visit confirmed option is not displayed",text("Visits - Confirmed").isDisplayed());
        Assert.assertTrue("Visit confirmed Checkbox is not displayed",getDriver().findElement(By.xpath("//input[@id='visit' and @type='checkbox']/following::label")).isDisplayed());
        String visitColor = "rgba(0, 0, 0, 1)";
        String actualVisitColor = getDriver().findElement(By.xpath("//input[@id='visit']")).getCssValue("color");
        Assert.assertTrue("Background Color for the Visit checkbox are not displayed",actualVisitColor.equals(visitColor));
        getDriver().findElement(By.xpath("//input[@id='visit']/following::label")).click();

        //Fairs
        Assert.assertTrue(" Fair confirmed option is not displayed",text("College Fair - Confirmed").isDisplayed());
        Assert.assertTrue("Fair confirmed Checkbox is not displayed",getDriver().findElement(By.xpath("//input[@id='fair'and @type='checkbox']/following::label")).isDisplayed());
        String fairColor = "rgba(0, 0, 0, 1)";
        String actualFairColor = getDriver().findElement(By.xpath("//input[@id='fair']")).getCssValue("color");
        Assert.assertTrue("Background Color for the fair checkbox are not displayed",actualFairColor.equals(fairColor));
        getDriver().findElement(By.xpath("//input[@id='fair']/following::label")).click();

        //Pending
        Assert.assertTrue("Pending option is not displayed",text("Pending").isDisplayed());
        Assert.assertTrue("Pending Checkbox is not displayed",getDriver().findElement(By.xpath("//input[@id='pending'and @type='checkbox']/following::label")).isDisplayed());
        String pendingColor = "rgba(0, 0, 0, 1)";
        String actualPendingColor = getDriver().findElement(By.xpath("//input[@id='pending']")).getCssValue("color");
        getDriver().findElement(By.xpath("//input[@id='pending']/following::label")).click();
        Assert.assertTrue("Background Color for the Pending checkbox are not displayed",actualPendingColor.equals(pendingColor));

    }

    public void navigateToRepVisitsSection(String pageName) {
        getNavigationBar().goToRepVisits();
        getDriver().findElement(By.xpath("(//a/span[text()='"+pageName+"'])[2]")).click();
        waitUntilPageFinishLoading();
    }

    /**
     * verifying your schedule text in search and schedule page
     * @param yourScheduleText
     */
    public void verifyYourScheduleTextInSearchAndSchedule(String yourScheduleText){
        waitUntil(ExpectedConditions.visibilityOfElementLocated(yourSchedule()));
        String actualValue = getDriver().findElement(yourSchedule()).getText();
        softly().assertThat(actualValue).as("Your schedule Text in search and schedule page").isEqualTo(yourScheduleText);
    }

    /**
     * verifying date in search and schedule page
     * @param startDate
     */
    public void verifyCurrentVisitDateInSearchAndSchedule(String startDate){
        String gotoDate;
        int date = Integer.parseInt(startDate);
        gotoDate = getSpecificDate(date,"MMMM d yyyy");
        setDate(gotoDate, "Go To Date");
        String startdate = getStartDate().getText();
        String enddate = getEndDate().getText();
        String actualValue = startdate+"-"+enddate;
        String yourScheduleStartDate = getStartDateFromYourSchedule().getText();
        String yourScheduleEndDate = getEndDateFromYourSchedule().getText();
        String expectedValue = yourScheduleStartDate+"-"+yourScheduleEndDate;
        softly().assertThat(actualValue).as("Date is not equal in Your schedule page").isEqualTo(expectedValue);
    }

    /**
     * verifying hyperlink in yourSchedule page
     * @param school
     */
    public void verifyHyperLinkInYourSchedulePage(String school){
        String visitTime = StartTime;
        String actualValue = getSchoolFromYourSchedule(visitTime).getText();
        softly().assertThat(actualValue).as("School is not displayed with visit time").isEqualTo(school);
        Assert.assertTrue("Visit details Hyper link is not displayed in your schedule page",selectHyperLinkInYourSchedule(school,visitTime).isDisplayed());
        selectHyperLinkInYourSchedule(school,visitTime).click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(schoolInYourSchedule(school)));
    }

    /**
     * verifying school details in your schedule popup
     * @param school
     * @param dataTable
     */
    public void verifySchoolDetailsInYourSchedulePopup(String school,DataTable dataTable){
        Assert.assertTrue("Email icon is not displaying in your schedule page",emailIconInYourSchedulePage().isDisplayed());
        List<String> values = dataTable.asList(String.class);
        for(String schoolDetails:values){
            Assert.assertTrue(schoolDetails+" are not displayed",schoolDetailsInYourSchedulePopup(schoolDetails).isDisplayed());
        }
    }

    /**
     * verify Link navigation in your schedule page
     * @param school
     */
    public void verifyLinkNavigationInYourSchedulePopup(String school){
        schoolHyperLink(school).click();
        communityFrame();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(repvisitsAvailability()));
        Assert.assertTrue("School hyper link is not navigated to counselor community profile",getDriver().findElement(repvisitsAvailability()).isDisplayed());
        getDriver().switchTo().defaultContent();
    }

    /**
     * verifying close icon in your schedule popup
     */
    public void verifyCloseIconInYourSchedulePopup(){
        moveToElement(closeIconInYourSchedulePopup());
        Assert.assertTrue("close icon is not displayed",closeIconInYourSchedulePopup().isDisplayed());
        closeIconInYourSchedulePopup().click();
        waitUntil(ExpectedConditions.visibilityOf(search()));
        Assert.assertTrue("Close icon is not clicked",search().isDisplayed());
    }

    public void verifyUserDropdownforHE(){
        getNavigationBar().goToRepVisits();
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
        // Temporary fix for the 'Your Profile' option, sometimes it does not navigate to the respective page, the issue exists only in automation
        for(int i=0;i<2;i++){
            try{
                getDriver().switchTo().defaultContent();
                waitUntilPageFinishLoading();
                userDropdown().click();
                getYourProfileBtn().click();
                waitUntilPageFinishLoading();
                waitForUITransition();
                communityFrame();
                List<WebElement> button = getDriver().findElements(By.xpath("//a[@class='active' and text()='Profile']"));
                if(button.size()==1){
                  Assert.assertTrue("'User Profile' is not displayed",userProfilePage().isDisplayed());
                  getDriver().switchTo().defaultContent();
                  waitUntilPageFinishLoading();
                  break;
                }
                getDriver().switchTo().defaultContent();
                waitUntilPageFinishLoading();
            } catch (Exception e){}
        }
        userDropdown().click();
        getInstitutionProfileBtn().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        communityFrame();
        Assert.assertTrue("'Institution Profile' is not displayed",institutionProfilePage().isDisplayed());
        getDriver().switchTo().defaultContent();
        waitUntilPageFinishLoading();
        getNavigationBar().goToCommunity();
        waitUntilPageFinishLoading();
    }

    public void verifyNavigationUserDropdownforHS()  {
        getAccoutSettingsBtn().click();
        waitUntilPageFinishLoading();
        // Temporary fix for the 'Your Profile' option, sometimes it does not navigate to the respective page, the issue exists only in automation
        for(int i=0;i<2;i++){
            try{
                getDriver().switchTo().defaultContent();
                waitUntilPageFinishLoading();
                userDropdown().click();
                getYourProfileBtn().click();
                waitUntilPageFinishLoading();
                waitForUITransition();
                communityFrame();
                List<WebElement> button = getDriver().findElements(By.xpath("//a[@class='active' and text()='Profile']"));
                if(button.size()==1){
                    Assert.assertTrue("'User Profile' is not displayed",userProfilePage().isDisplayed());
                    getDriver().switchTo().defaultContent();
                    waitUntilPageFinishLoading();
                    break;
                }
                getDriver().switchTo().defaultContent();
                waitUntilPageFinishLoading();
            } catch (Exception e){}
        }
        userDropdown().click();
        getInstitutionProfileBtn().click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        communityFrame();
        Assert.assertTrue("'Institution Profile' is not displayed",institutionProfilePage().isDisplayed());
        getDriver().switchTo().defaultContent();
        waitUntilPageFinishLoading();
        getNavigationBar().goToCommunityInHS();
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
        getNavigationBar().goToRepVisits();
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
        String navianceWindow = getDriver().getWindowHandle();
        String intersectWindow = null;
        Set<String> windows = getDriver().getWindowHandles();
        for (String thisWindow : windows) {
            if (!thisWindow.equals(navianceWindow)){
                intersectWindow = thisWindow;
            }
        }
        getDriver().switchTo().window(intersectWindow);
        waitUntilPageFinishLoading();
        Assert.assertTrue("hobsons logo is not displayed",logo().isDisplayed());
        getDriver().switchTo().window(navianceWindow);
        waitUntilPageFinishLoading();
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
    }

    public void verifytSearchHeadingInSearchAndScheduleTab(){
        waitUntilPageFinishLoading();
        navigateToRepVisitsSection("Search and Schedule");
        waitUntilPageFinishLoading();
        Assert.assertTrue("Search text box is not displayed",getSearchBox().isDisplayed());
        Assert.assertTrue("Search heading is not over the search bar",getDriver().findElement(By.xpath("//input[@placeholder='Search for a school...']/ancestor::form/preceding-sibling::h1/span[text()='Search']")).isDisplayed());
    }

    public void verifyScheduleHeadingOverAvailabilityBlockInSearchAndSchedule(){
        waitUntilPageFinishLoading();
        waitForUITransition();
        Assert.assertTrue("Schedule text is not over the Availability block",getDriver().findElement(By.xpath("//button/span[text()='Visits']/ancestor::div/preceding-sibling::h2/span[text()='Schedule']")).isDisplayed());
    }

    public void verifyCalendarIconNextToDateInSearchAndSchedule(){
        Assert.assertTrue("Go To Date button is not displayed",getDriver().findElement(By.xpath("//button[text()='Go To Date']")).isDisplayed());
        Assert.assertTrue("Calendar icon is not present next to date",getDriver().findElement(By.xpath("//button[text()='Go To Date']/ancestor::div/button/i[@class='calendar outline icon']")).isDisplayed());
    }

    public void verifyDateAndCalenderIconOverAvailabilityTable(){
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//thead/tr[@class='_27onojIbT_EM64h57k6Mee']/ancestor::table/ancestor::div/div/button[text()='Go To Date']")));
        Assert.assertTrue("Calendar icon is not displayed over the Availability Table",getDriver().findElement(By.xpath("//thead/tr[@class='_27onojIbT_EM64h57k6Mee']/ancestor::table/ancestor::div/div/button[text()='Go To Date']")).isDisplayed());
    }

    public void verifyNextPrevButtonInSearchAndSchedule(){
        Assert.assertTrue("Previous Week button is not displayed",getDriver().findElement(By.xpath("//button[text()='Go To Date']/preceding-sibling::span/button[@aria-label='Previous week']")).isDisplayed());
        Assert.assertTrue("Next week button is not displayed",getDriver().findElement(By.xpath("//button[text()='Go To Date']/preceding-sibling::span/button[@aria-label='Next week']")).isDisplayed());
    }

    public void verifyViewTypeButtonInsearchAndSchedule(){
        Assert.assertTrue("View Type button is not displayed",getDriver().findElement(By.xpath("//span/button[@aria-label='Previous week']/parent::span/following-sibling::div/button[@aria-label='List']")).isDisplayed());
    }

    public void verifyColorofViewTypeButton(){
        String originalValue = "rgba(210, 0, 97, 1)";
        String currentValue = getDriver().findElement(By.xpath("//button[@aria-label='List']")).getCssValue("color");
        logger.info(currentValue+"currentValue");
        Assert.assertTrue("",currentValue.equals(originalValue));
    }

    public void verifyMapInSearchAndScheduleTab(){
        waitUntilPageFinishLoading();
        waitForUITransition();
        Assert.assertTrue("Map icon is not displayed",getDriver().findElement(By.xpath("//button/span[text()='Map']")).isDisplayed());
        getDriver().findElement(By.xpath("//button/span[text()='Map']")).click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        Assert.assertTrue("Current school is not displayed in the Map",getDriver().findElement(By.xpath("//div[@class='_2WDkiBOfzASeH5aeq7kJXv']")).isDisplayed());
    }

    public void verifyTextInFairsTabInSearchAndScheduleTab(String showingScheduledFairs){
        waitUntilPageFinishLoading();
        getDriver().findElement(By.xpath("//span[text()='Fairs']")).click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        Assert.assertTrue("Showing All Scheduled Fairs text is not displayed in the Fair Tab",getDriver().findElement(By.xpath("//span[text()='"+showingScheduledFairs+"']")).isDisplayed());
    }

    public void verifyYourScheduleTextInSearchAndScheduleTab(){
        Assert.assertTrue("Your Schedule text is not displayed",getDriver().findElement(By.xpath("//span[text()='Your Schedule']")).isDisplayed());
    }

    public void verifytSearchHeadingInSearchAndScheduleTabAfterSchoolSearch(){
        waitUntilPageFinishLoading();
        Assert.assertTrue("Search text box is not displayed",getSearchBox().isDisplayed());
        Assert.assertTrue("Search heading is not over the search bar",getDriver().findElement(By.xpath("//input[@placeholder='Search for a school...']/ancestor::form/preceding-sibling::h1/span[text()='Search']")).isDisplayed());
    }

    public void verifyVisitsFeedbackNonAdminMessaging() {
        Assert.assertTrue("Non-administrator message was not displayed",text("Visit Feedback is only available to users with the Administrator role.").isDisplayed());
        Assert.assertTrue("Locked banner was not displayed",getDriver().findElement(By.xpath("//div[@class='centered one column row']")).findElement(By.cssSelector("[alt=locked]")).isDisplayed());
    }

    public void verifyVisitsFeedbackFreemiumMessaging() {
        Assert.assertTrue("Feature description was not displayed",text(" you get access to information on the effectiveness of your college visits. See what's working and what could be improved.").isDisplayed());
        Assert.assertTrue("Upgrade button was not displayed",getDriver().findElement(By.xpath("//button/span")).getText().equalsIgnoreCase("upgrade"));
        Assert.assertTrue("Locked banner was not displayed",getDriver().findElement(By.xpath("//div[@class='centered one column row']")).findElement(By.cssSelector("[alt=locked]")).isDisplayed());
    }

    public void verifyVisitFeedbackPage(){
        verifyVisitFeedbackHeading();
        verifyStaffMembersAreDisplayedInAscendingOrderByLastName();
        verifyCommunityAvatarIsDisplayedToTheLeftOfStaffMemberName();
        verifyNoFeedbackSubmittedYetMessageIsDisplayed();
    }
    public void navaigateToAccountSettings(String accountSettings) {
        getNavigationBar().goToRepVisits();
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
                Assert.assertTrue("Visits toggle is not Enabled",isButtonEnabledInSearchandScheduleTab(getDriver().findElement(By.xpath("//span[text()='Visits']/parent::button[@role='button']"))));
            }else if(enabledOrDisabled.equals("Disabled")){
                Assert.assertTrue("Visits toggle is not Disabled",isButtonDisabledInSearchandScheduleTab(getDriver().findElement(By.xpath("//span[text()='Visits']/parent::button[@role='button']"))));
            }else {
             logger.info("Invalid option");
            }
        }else if(visitOrFairs.equals("Fairs")) {
            if(enabledOrDisabled.equals("Enabled")) {
                Assert.assertTrue("Fairs toggle is not Enabled",isButtonEnabledInSearchandScheduleTab(getDriver().findElement(By.xpath("//span[text()='Fairs']/parent::button[@role='button']"))));
            }else if(enabledOrDisabled.equals("Disabled")) {
                Assert.assertTrue("Fairs toggle is not Disabled",isButtonDisabledInSearchandScheduleTab(getDriver().findElement(By.xpath("//span[text()='Fairs']/parent::button[@role='button']"))));
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
        WebElement schoolName=getDriver().findElement(By.xpath("//td//a[text()='"+school+"']"));
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
        WebElement button= getDriver().findElement(By.xpath("//span[text()='"+visitDate+"']/parent::th/ancestor::thead/following-sibling::tbody/tr//td//div/button[text()='"+visitTime+"']"));
       //verify availability
        Assert.assertTrue("Availability slot is not displayed",button.isDisplayed());
    }

    public void verifyAvailabilitySlotIsNotDisplayingInSearchAndSchedule(String time,String startDate,String school){
        waitUntilPageFinishLoading();
        visit().click();
        waitForUITransition();
        WebElement schoolName=getDriver().findElement(By.xpath("//h3/a[text()='"+school+"']"));
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
        List<WebElement> button= getDriver().findElements(By.xpath("//span[text()='"+visitDate+"']/parent::th/ancestor::thead/following-sibling::tbody/tr//td//div/button[text()='"+visitTime+"']"));
        Assert.assertTrue("Availability slot is displayed",button.size()==0);
    }

    public void searchSchoolbyLocation(String school,String location){
        waitUntilPageFinishLoading();
        navigateToRepVisitsSection("Search and Schedule");
        waitUntilPageFinishLoading();
        searchTextBox().clear();
        searchTextBox().sendKeys(location);
        waitUntilElementExists(search());
        searchButton().click();
        waitForUITransition();
        WebElement schoolName=getDriver().findElement(By.xpath("//td//a[contains(text(),'"+school+"')]"));
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

        Assert.assertTrue("'Overview' header is not displayed", getDriver().findElement(By.xpath("//div[@class='_2A5hjEmVP0BipO_WsFslGI']/span[text()='Overview']")).isDisplayed());
        Assert.assertTrue("'Premium Feature' subtext is not displayed",  getDriver().findElement(By.xpath("//span[text()='Premium Feature']")).isDisplayed());
        Assert.assertTrue("'Locked' icon is not displayed", getDriver().findElement(By.xpath("//img[@alt='locked']")).isDisplayed());
        Assert.assertTrue("'Unlock Overview' text is not displayed",  getDriver().findElement(By.xpath("//span[text()='Unlock Overview']")).isDisplayed());
        Assert.assertTrue("'You'll get right to work faster' text is not displayed",  getDriver().findElement(By.xpath("//span[contains(text(),\"You'll get right to work faster\")]")).isDisplayed());
        Assert.assertTrue("'The Overview provides a summary of your scheduled visits and fairs for the week with easy access to appointment details—all in one quick view.' text is not displayed",
                getDriver().findElement(By.className("yA0LbT1wFzAHyLC-oUZgc")).getText().equals("The Overview provides a summary of your scheduled visits and fairs for the week with easy access to appointment details—all in one quick view."));
        Assert.assertTrue("'UPGRADE' button is not displayed", getUpgradeButton().isDisplayed());

        getUpgradeButton().click();
        Assert.assertTrue("'Eloqua sales lead form' is not displayed", getDriver().findElement(By.xpath("//*[@class='ui header yPrXuXWe8f9WYWmKjbRiU _2_NgiA2zhtvtK1J2NNgPGn']")).getText().equals("Upgrade to Intersect Presence and get the most out of RepVisits"));

        getDriver().findElement(By.xpath("//div[@id='upgrade-form']/i[@class='close icon']")).click();

    }

    private void verifyVisitFeedbackHeading() {
        waitUntilPageFinishLoading();
        Assert.assertTrue("Visit Feedback heading not displayed", getDriver().findElement(By.xpath("//h1[contains(@class, 'ui header _26ekcAlhCmjadW7ShhS7aj')]")).getText().equals("Visit Feedback"));
    }

    private void verifyStaffMembersAreDisplayedInAscendingOrderByLastName() {
        waitUntilPageFinishLoading();

        List<WebElement> itemsInStaffMemberMenu = getVerticalStaffMembersMenu().findElements(By.xpath(".//li[contains(@class,'item')]"));

        ArrayList<String> listContainingFullNamesOfStaffMembers = new ArrayList<String>();

        for(int i=0; i < itemsInStaffMemberMenu.size(); i++)
        {
            String fullNameOfStaffMember = itemsInStaffMemberMenu.get(i).findElement(By.xpath(".//h2")).getText();
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
                        findElement(By.xpath(".//h2/../img | .//h2/../i[@class[contains(.,'circular')]]")).isDisplayed();
            } catch(Exception ex) {
                isCommunityAvatarDisplayed = false;
            }

            String nameOfStaffMember = itemsInStaffMemberMenu.get(i).findElement(By.xpath(".//h2")).getText();
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
        Assert.assertTrue(getDriver().findElement(By.xpath("//span[text()='submitted visit feedback of']")).isDisplayed() || getDriver().findElement(By.xpath("//span[text()='Anonymous']")).isDisplayed());

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
        navigateToRepVisitsSection("Search and Schedule");
        waitUntilPageFinishLoading();
        searchTextBox().clear();
        searchTextBox().sendKeys(school);
        waitUntil(ExpectedConditions.visibilityOf(search()));
        searchButton().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(school(school)));
        Assert.assertTrue("school is not displayed",verifySchool(school).isDisplayed());
        verifySchool(school).click();
        waitUntilPageFinishLoading();
    }
    public void visitsSchedule(String school,String startDate,String time){
        if(getDay(startDate).equalsIgnoreCase("Sat")){
            startDate = Integer.toString(Integer.parseInt(startDate)+2);
        } else{
            if(getDay(startDate).equalsIgnoreCase("Sun")) {
                startDate =  Integer.toString(Integer.parseInt(startDate)+1);
            }
        }
        waitUntil(ExpectedConditions.visibilityOf(visit()));
        visit().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(schoolInVisitsLocator(school)));
        Assert.assertTrue("school is not displayed",schoolInVisits(school).isDisplayed());
        waitUntil(ExpectedConditions.visibilityOf(goToDate()));
        String gotoDate;
        String visitDate;
        if (startDate.length() < 3) {
            int date = Integer.parseInt(startDate);
            gotoDate = getSpecificDate(date,"MMMM d yyyy");
            visitDate = getMonthandDate(startDate);
        }
        else {
            gotoDate = startDate;
            String[] dateParts = startDate.split(" ");
            visitDate = getShortMonth(dateParts[0]) + " " + dateParts[1];
        }
        waitUntil(ExpectedConditions.visibilityOfElementLocated(dateButton()));
        getDriver().findElement(dateButton()).click();
        setDateFixed(gotoDate);
        time = pageObjects.HS.repVisitsPage.RepVisitsPageImpl.StartTime==null?time:pageObjects.HS.repVisitsPage.RepVisitsPageImpl.StartTime;
        waitUntil(ExpectedConditions.visibilityOfElementLocated(availabilityButtonLocator(visitDate,time)));
        Assert.assertTrue("Availability is not displayed",avialabilityButton(visitDate,time).isDisplayed());
        avialabilityButton(visitDate,time).click();
        waitUntilPageFinishLoading();
    }

    public void verifySchedulePopup(String school, String startTime, String endTime) {
        waitUntilPageFinishLoading();
        startTime = pageObjects.HS.repVisitsPage.RepVisitsPageImpl.StartTime;
        Assert.assertTrue("SchedulePopup is not displayed", getDriver().findElement(By.xpath("//div[contains(text(),'Ready to Schedule?')]")).isDisplayed());
        Assert.assertTrue("school is not displayed", getDriver().findElement(By.xpath("//div[contains(text(),'Do you want to schedule a visit with " + school + " from')]")).isDisplayed());
//        Assert.assertTrue("time is not displayed",getDriver().findElement(By.xpath("//div[contains(text(),'Do you want to schedule a visit with "+school+" from')]/b[contains(text(),'"+startTime+"-"+endTime+"')]")).isDisplayed());
        visitRequestButton().click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(visitSuccessMessage(),1));
        waitUntil(ExpectedConditions.numberOfElementsToBe(visitSuccessMessage(),0));
        waitUntil(ExpectedConditions.visibilityOf(goToDate()));
    }

    public void setDateFixed(String inputDate) {
        String[] parts = inputDate.split(" ");
        String calendarHeading = parts[0] + " " + parts[2];
        findMonthInCalendar(calendarHeading);
        clickOnDay(parts[1]);
        waitUntilPageFinishLoading();
    }

    public void findMonthInCalendar(String month) {
        waitUntilPageFinishLoading();
        boolean monthStatus = compareDate(month);

        String DayPickerCaption = driver.findElement(By.cssSelector("div[class='DayPicker-Caption']")).getText();

        try {
            while (!DayPickerCaption.contains(month)) {

                if (monthStatus) {
                    driver.findElement(By.cssSelector("span[class='DayPicker-NavButton DayPicker-NavButton--next']")).click();
                    DayPickerCaption = driver.findElement(By.cssSelector("div[class='DayPicker-Caption']")).getText();
                } else {
                    driver.findElement(By.cssSelector("span[class='DayPicker-NavButton DayPicker-NavButton--prev']")).click();
                    DayPickerCaption = driver.findElement(By.cssSelector("div[class='DayPicker-Caption']")).getText();
                }
            }

        } catch (Exception e) {
            Assert.fail("The Date selected it's out of RANGE.");
        }
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

    public String getDay(String addDays){
        String DATE_FORMAT_NOW = "EE";
        Calendar cal = Calendar.getInstance();
        int days=Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDay = sdf.format(cal.getTime());
        return currentDay;
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

    public void clickRequestButton(){
        waitUntilPageFinishLoading();
        visitRequestButton().click();
        waitUntil(ExpectedConditions.visibilityOf(goToDate()));
    }

    public void visitFairsToRegister(String fairName,String schoolName){
        waitUntilPageFinishLoading();
        String Fair=pageObjects.HS.repVisitsPage.RepVisitsPageImpl.FairName;
        waitUntil(ExpectedConditions.visibilityOfElementLocated(fairsButton()));
        getFairsButton().click();
        waitUntilElementExists(register());
        waitUntilElementExists(fairname(schoolName,Fair));
        Assert.assertTrue("fair is not displayed",fairname(schoolName,Fair).isDisplayed());
        Assert.assertTrue("schoolName is not displayed",schoolName(schoolName).isDisplayed());
        registerFairs(Fair).click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("submit page is not displayed",submitRequestText().isDisplayed());
        submitButton().click();
        waitUntil(ExpectedConditions.visibilityOf(goToDate()));
    }

    public void verifyNotification(String school,String date,String time) {
        Assert.assertTrue("Requests is not displayed",requestsubtab().isDisplayed());
        requestsubtab().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='"+school+"']")));
        Assert.assertTrue("school is not displayed",verifySchoolInNotificationTab(school).isDisplayed());
        String Date = selectdate(date);
        clickShowMoreButton();
        String visitTime = pageObjects.HS.repVisitsPage.RepVisitsPageImpl.StartTime;
        Assert.assertTrue("date and time are not displayed",verifyDateAndTimeInNotificationTab(school,Date,visitTime).isDisplayed());
    }

    public void verifyNotificationforFairs(String school,String date,String time) {
        getNavigationBar().goToRepVisits();
        getNotificationsBtn().click();
        waitUntilElementExists(requestsubtab());
        Assert.assertTrue("Requests is not displayed",requestsubtab().isDisplayed());
        requestsubtab().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='"+school+"']")));
        Assert.assertTrue("school is not displayed",verifySchoolInNotificationTab(school).isDisplayed());
        clickShowMoreButton();
        String Date = selectdate(date);
        Assert.assertTrue("date and time is not displayed",verifyDateAndTimeInNotificationTab(school,Date,time).isDisplayed());
    }

    public  void verify25Entries(String option) {
        int count=getTotalCountInNotification().size();
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
        Assert.assertTrue("View full details option is not displayed",viewFullDetails(school,Date,visitTime).isDisplayed());
        viewFullDetails(school,Date,visitTime).click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(internalNodeTextBox()));
        textBoxInViewDetails().sendKeys(Keys.PAGE_DOWN);
        Assert.assertTrue("save button is not displayed",viewDetailsSaveButton().isDisplayed());
        viewDetailsSaveButton().click();
    }

    public void clickOnSeeAllUsersLink(String link) {
        seeAllUsersLink(link).click();
    }

    public void reInviteSendEmail(String action, String loginName){

        waitUntil(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(loginRowLocator(loginName)), 0));
        WebElement login = getDriver().findElement(By.xpath(loginRowLocator(loginName)));

        WebElement Actions = getParent(getParent(login)).findElement(By.cssSelector("[aria-label='Actions']"));
        Actions.click();

        WebElement selectsReInviteDropDown = getDriver().findElement(By.xpath(actionMenuElementLocator(action)));
        getDriver().executeScript("arguments[0].click();",selectsReInviteDropDown);
        yesButton().click();
    }

    public void selectViewDetailsforFairs(String school,String date,String time) {
        String Date = selectdate(date);
        Assert.assertTrue("View full details option is not displayed",viewFullDetails(school,Date,time).isDisplayed());
        viewFullDetails(school,Date,time).click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(internalNodeTextBox()));
        waitUntilElementExists(textBoxInViewDetails());
        textBoxInViewDetails().sendKeys(Keys.PAGE_DOWN);
        Assert.assertTrue("save button is not displayed",viewDetailsSaveButton().isDisplayed());
        viewDetailsSaveButton().click();
    }

    public void verifynoNotificationMessage(String message) {
        getNavigationBar().goToRepVisits();
        getNotificationsBtn().click();
        waitUntilElementExists(requestsubtab());
        try{
            if(text(message).isDisplayed()) {
                logger.info("Notification is not displayed");
            } else{
                logger.info("Notification is displayed");
            }}
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

    public void setDate(String inputDate, String startOrEndDate) {

        String[] parts = inputDate.split(" ");
        String calendarHeading = parts[0] + " " + parts[2];

        if (startOrEndDate.contains("Start")) {
            button(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']:nth-child(1)")).click();
            findMonth(calendarHeading, startOrEndDate);
        } else if(startOrEndDate.contains("End")) {
            button(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']:nth-child(3)")).click();
            findMonth(calendarHeading, startOrEndDate);
        } else if(startOrEndDate.contains("FirstDate")) {
            getDriver().findElement(By.xpath("//div[@class='_20rE_mlucRFZ--zviGCI2N']/b/preceding-sibling::button")).click();
            findMonth(calendarHeading, startOrEndDate);
        } else if(startOrEndDate.contains("LastDate")||startOrEndDate.equals("DisabledDate")) {
            getDriver().findElement(By.xpath("//div[@class='_20rE_mlucRFZ--zviGCI2N']/b/following-sibling::button")).click();
            findMonth(calendarHeading, startOrEndDate);
        } else if(startOrEndDate.contains("other")) {
            button(By.cssSelector("button[class='ui small button _2D2Na6uaWaEMu9Nqe1UnST']")).click();
            findMonth(calendarHeading);
        } else if(startOrEndDate.equals("Go to date")){
            waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[class='ui small basic icon right labeled button']")));
            getDriver().findElement(By.cssSelector("button[class='ui small basic icon right labeled button']")).click();
            findMonth(calendarHeading);
        } else {
            button(By.cssSelector("button[class='ui tiny icon right floated right labeled button _1alys3gHE0t2ksYSNzWGgY']")).click();
            findMonth(calendarHeading);
        }
        if(parts[1].startsWith("0")) {
            parts[1]=parts[1].replace("0","");
            clickOnDay(parts[1]);
        }else if(startOrEndDate.equals("DisabledDate")) {
            clickDisabledDate(parts[1]);
        }else {
            clickOnDay(parts[1]);
            waitUntilPageFinishLoading();
        }
    }

    public void findMonth(String month, String startOrEndDate) {
        waitUntilPageFinishLoading();
        boolean monthStatus = compareDate(month, startOrEndDate);

        String DayPickerCaption = getDriver().findElement(By.cssSelector("div[class='DayPicker-Caption']")).getText();

        try{
            while (!DayPickerCaption.contains(month)) {
                if (monthStatus){
                    getDriver().findElement(By.cssSelector("span[class='DayPicker-NavButton DayPicker-NavButton--next']")).click();
                    DayPickerCaption = getDriver().findElement(By.cssSelector("div[class='DayPicker-Caption']")).getText();
                }
                else {
                    getDriver().findElement(By.cssSelector("span[class='DayPicker-NavButton DayPicker-NavButton--prev']")).click();
                    DayPickerCaption = getDriver().findElement(By.cssSelector("div[class='DayPicker-Caption']")).getText();
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
            dateCaption = getDriver().findElement(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']")).getText();
        } else if(startOrEndDate.contains("end")){
            dateCaption = button(By.cssSelector("div[style='display: inline-block;'] :nth-child(3)")).getText();
        }else if(startOrEndDate.contains("FirstDate")){
            dateCaption = getDriver().findElement(By.xpath("//div[@class='_20rE_mlucRFZ--zviGCI2N']/b/preceding-sibling::button")).getText();
        } else if(startOrEndDate.contains("LastDate")||startOrEndDate.equals("DisabledDate")){
            dateCaption = getDriver().findElement(By.xpath("//div[@class='_20rE_mlucRFZ--zviGCI2N']/b/following-sibling::button")).getText();
        } else{
            dateCaption = button(By.cssSelector("button[class='ui tiny icon right floated right labeled button _1alys3gHE0t2ksYSNzWGgY']")).getText();
        }


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

    public Boolean compareDate(String month) {

        String dateCaption = null;
        DateFormat format = new SimpleDateFormat("MMM yyyy");
        DateFormat formatDate = new SimpleDateFormat("MMM yyyy");
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='DayPicker-Caption']")));
        dateCaption = driver.findElement(By.cssSelector("div[class='DayPicker-Caption']")).getText();

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
        return before;

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
        button(By.xpath(String.format(".//div/div/h3[text()='%s']/ancestor::div[@class='item']//span[text()='Remove']",school))).click();
        Assert.assertTrue("The Remove from Travel Plan text is not displayed", text("Remove from Travel Plan?").isDisplayed());
        Assert.assertTrue("The remove from travel plan confirmation message is not displayed",text(String.format("Are you sure you want to remove %s from your travel plan?", school)).isDisplayed());
        button("YES, REMOVE").click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Succesfully removed']")));
        waitUntil(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//span[text()='Succesfully removed']")));
    }

    public void verifySchoolDetailsInTravelPlan(String school,String address,String collegeGoingRate,String seniorclassSize,String primaryPoc,String stateName){
        String addressdetails[] = address.split(",");
        String city = addressdetails[0];
        String state = addressdetails[1];
        String county = addressdetails[2];
        String zip = addressdetails[3];
        List<WebElement> countOfHighSchoolInSameState = getDriver().findElements(By.xpath("//h2[contains(text(),'"+stateName+"')]"));
        Assert.assertTrue("HighSchool count is not displayed",countOfHighSchoolInSameState.size()==1);
        Assert.assertTrue("HighSchool avatar image is not displayed",getDriver().findElement(By.xpath("//div/img[@class='ui centered image yUiNH8XB_uHGXhISF0aaL']")).isDisplayed());
        Assert.assertTrue("School is not displayed",getDriver().findElement(By.xpath("//h3[text()='"+school+"']")).isDisplayed());
        Assert.assertTrue("Address is not displayed",getDriver().findElement(By.xpath("//div[normalize-space(text())='"+city+","+state+","+county+","+zip+"']")).isDisplayed());
        Assert.assertTrue("CollegeGoingRate is not displayed",getDriver().findElement(By.xpath("//h3[text()='" + school + "']/../../..//*[text() = '" + collegeGoingRate +"']/../../div")).isDisplayed());
        Assert.assertTrue("Senior class size is not displayed",getDriver().findElement(By.xpath("//h3[text()='" + school + "']/../../..//*[text() = '" + seniorclassSize + "']/../../div")).isDisplayed());
        Assert.assertTrue("primary Poc is not displayed",getDriver().findElement(By.xpath("//h3[text()='" + school + "']/../../..//*[text() = '" + primaryPoc + "']/../../div")).isDisplayed());
    }

    public void verifyLinkInTravelPlanPage(){
        navigateToRepVisitsSection("Travel Plan");
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//h1/span[text()='Travel Plan']")));
        try{
            Assert.assertTrue("Instruction is not displayed",getDriver().findElement(By.xpath("//span[text()='The Travel Plan lists high schools you plan to visit or will be visiting. You can add additional schools by selecting \"Add to my travel plan\" from the ']")).isDisplayed());
            Assert.assertTrue("Recommendations link is not displayed",getDriver().findElement(By.xpath("//a[@class='_3tCrfAwfbPaYbACR-fQgum']/span[text()='Recommendations']")).isDisplayed());
            navigateToRepVisitsSection("Recommendations");
            Assert.assertTrue("Navigated to Recommendations Page",getDriver().findElement(By.xpath("//div/h1/span[text()='Recommendations']")).isDisplayed());
        }catch(Exception e){
            throw new AssertionFailedError("The label for the 'Recommendation' is not displayed in the 'Travel Plan' tab");
        }
    }

    public void verifysortingStatesInTravelPlan(){
        navigateToRepVisitsSection("Travel Plan");
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//h1/span[text()='Travel Plan']")));
        List<WebElement> elements = getDriver().findElements(By.xpath("//div[@class='ui stackable grid f4StyMpAtcTrzK5AccX8f']/div[1]/div/span"));
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
        if(getDay(date).equalsIgnoreCase("Sat")){
            date = Integer.toString(Integer.parseInt(date)+2);
        } else{
            if(getDay(date).equalsIgnoreCase("Sun")) {
                date =  Integer.toString(Integer.parseInt(date)+1);
            }
        }
        navigateToRepVisitsSection("Travel Plan");
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//h1/span[text()='Travel Plan']")));
        String visitDate = getSpecificDateforTravelPlan(date);
        WebElement visitDetails = getDriver().findElement(By.xpath("//h3[text()='"+school+"']/ancestor::div[@class='row _2Hy63yUH9iXIx771rmjucr']//div[@class='content _1SfpP2fklL5GVWMyfqjq4q']//a/span/span[text()='Visit,']/parent::span/following-sibling::span[text()='"+visitDate+"']"));
        Assert.assertTrue("Visit Details is not displayed",visitDetails.isDisplayed());
        visitDetails.click();
        waitForUITransition();
        Assert.assertTrue("Internal Notes text box is displayed",getDriver().findElement(By.xpath("//input[@aria-label='Internal Notes']")).isDisplayed());
        getDriver().findElement(By.xpath("//input[@aria-label='Internal Notes']")).sendKeys(Keys.PAGE_DOWN);
        Assert.assertTrue("Cancel This Visit button is not displayed",button("Cancel This Visit").isDisplayed());
        button("Cancel This Visit").click();
        getDriver().findElement(By.id("cancel-message")).sendKeys("by QA");
        Assert.assertTrue("Yes, Cancel Visit button is not Enabled",button("Yes, Cancel Visit").isEnabled());
        button("Yes, Cancel Visit").click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        waitForUITransition();
    }

    public void verifyFairDetailsInTravelPlan(String school,String date){
        navigateToRepVisitsSection("Travel Plan");
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//h1/span[text()='Travel Plan']")));
        String fairDate = getSpecificDateforTravelPlan(date);
        WebElement fairDetails = getDriver().findElement(By.xpath("//h3[text()='"+school+"']/ancestor::div[@class='row _2Hy63yUH9iXIx771rmjucr']//div[@class='content _1SfpP2fklL5GVWMyfqjq4q']//a/span/span[text()='College Fair,']/../../span/following-sibling::span[text()='"+fairDate+"']"));
        Assert.assertTrue("FairDetails is not displayed",fairDetails.isDisplayed());
        fairDetails.click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        waitUntil(ExpectedConditions.visibilityOf(getDriver().findElement(By.xpath("//input[@aria-label='Internal Notes']"))));
        Assert.assertTrue("Internal Notes text box is displayed",getDriver().findElement(By.xpath("//input[@aria-label='Internal Notes']")).isDisplayed());
        getDriver().findElement(By.xpath("//input[@aria-label='Internal Notes']")).sendKeys(Keys.PAGE_DOWN);
        Assert.assertTrue("Cancel This Fair is not Displayed",button("Cancel This Fair").isDisplayed());
        button("Cancel This Fair").click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        getDriver().findElement(By.id("cancel-message")).sendKeys("by QA");
        Assert.assertTrue("Yes, Cancel Fair button is not Enabled",button("Yes, Cancel Fair").isEnabled());
        button("Yes, Cancel Fair").click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        waitForUITransition();
        waitForUITransition();
    }

    public void verifyUpcommingAppointmentTextInTravelPlan(String upcomingAppointmentsText,String school){
        Assert.assertTrue("'Upcoming Appointments' text is not displayed for the school '"+school+"' in Travel Plan",getDriver().findElement(By.xpath("//h3[text()='"+school+"']/ancestor::div/following-sibling::div/div/div/div/span/span[text()='"+upcomingAppointmentsText+"']")).isDisplayed());
    }

    public void verifyScheduledTextInTravelPlan(String scheduled,String school){
        Assert.assertTrue("'Scheduled' Label text is not displayed for the school '"+school+"' in the Travel Plan" ,getDriver().findElement(By.xpath("//h2[text()='"+school+"']/following-sibling::div/div/span[text()='"+scheduled+"']")).isDisplayed());
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
        Assert.assertTrue("'"+text+"' Text is not displayed for the school '"+school+"' in the Travel Plan",getDriver().findElement(By.xpath("//h3[text()='"+school+"']/ancestor::div/following-sibling::div//span[text()='"+text+"']")).isDisplayed());
    }

    public void verifyViewAvailabilityButtonInTravelPlan(String viewAvailabilityButton,String school){
        Assert.assertTrue("'View Availability' is not displayed for the school '"+school+"' in the Travel Plan",getDriver().findElement(By.xpath("//h3[text()='"+school+"']/ancestor::div/following-sibling::div//span[text()='"+viewAvailabilityButton+"']")).isDisplayed());
    }

    public void selectViewAvailabilityButtonInTravelPlan(String viewAvailabilityButton,String school){
       getDriver().findElement(By.xpath("//h3[text()='"+school+"']/ancestor::div/following-sibling::div//span[text()='"+viewAvailabilityButton+"']")).click();
       waitForUITransition();
       waitUntilPageFinishLoading();
       Assert.assertTrue("Search text box is not displayed in the 'Search and Schedule' Page",getSearchBox().isDisplayed());
       Assert.assertTrue("visit button is not displayed in the 'Search and Schedule' Page",getDriver().findElement(By.xpath("//button/span[text()='Visits']")).isDisplayed());
    }

    public void verifyRemoveButtonInTravelPlan(String removeButton,String school){
        waitForUITransition();
        Assert.assertTrue("Remove button is not displayed",getDriver().findElement(By.xpath("//h3[text()='"+school+"']/ancestor::div/preceding-sibling::div/div/button/span[text()='"+removeButton+"']")).isDisplayed());
    }

    public void verifyUpcomingFairMessageInTravelPlan(String school){
        Assert.assertTrue("'Upcoming Fair' Highlighted Text is not displayed for the school '"+school+"' in the Travel Page",getDriver().findElement(By.xpath("//h3[text()='"+school+"']/ancestor::div/following-sibling::div/div/div/div/div/span[text()='This school has an upcoming College Fair on']/parent::div/a/span[text()='See details »']")).isDisplayed());
        WebElement seeDetailsLink = getDriver().findElement(By.xpath("//h3[text()='"+school+"']/ancestor::div/following-sibling::div/div/div/div/div/span[text()='This school has an upcoming College Fair on']/parent::div/a/span[text()='See details »']"));
        seeDetailsLink.click();
        waitUntilPageFinishLoading();
        waitForUITransition();
        Assert.assertTrue("Search text box is not displayed in the 'Search and Schedule' page",getSearchBox().isDisplayed());
        Assert.assertTrue("visit button is not displayed in the 'Search and Schedule' page",getDriver().findElement(By.xpath("//button/span[text()='Visits']")).isDisplayed());
    }

    public void verifyToDoTextInTravelPlan(String toDoText,String school){
        waitForUITransition();
        Assert.assertTrue("'To Do' Label is not displayed for the school '"+school+"' in the Travel Plan",getDriver().findElement(By.xpath("//h3[text()='"+school+"']/following-sibling::div/div/span[text()='"+toDoText+"']")).isDisplayed());
    }

    //The below method will verify the message which will display when there is no visit/fair for the next week.
    public void verifyDefaultMessageOverviewPage(){
        if(text("You don't have any visits or fairs for the next week.").isDisplayed()){
            Assert.assertTrue("You don't have any visits or fairs for the next week. text message is not displaying.", text("You don't have any visits or fairs for the next week.").isDisplayed());
            Assert.assertTrue("You can always make appointments by using the text is not displaying.", text("You can always make appointments by using the").isDisplayed());
            navigateToRepVisitsSection("Search and Schedule");
            String actURL = getDriver().getCurrentUrl();
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
            getDriver().findElement(By.xpath(String.format(".//td/a[text()='%s']/ancestor::tr/td/div[@class='_1az38UH6Zn-lk--8jTDv2w']/span[text()='Added To Travel Plan']"
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
                    ".//div/div/h3[text()='%s']/ancestor::div[@class='item']/div/div/button/i[@class='trash alternate icon _22IhW8lEh2abuRIROnZXJx']"
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
        button(By.xpath(String.format(".//div/div/h3[text()='%s']/ancestor::div[@class='item']//span[text()='Remove']"
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
            getDriver().findElement(By.xpath(String.format(".//div[@class='content']/div/div/h3[text()='%s']", school)));
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
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='_2A5hjEmVP0BipO_WsFslGI']/span[text()='Travel Plan']")));
        Assert.assertTrue("Travel Plan is not locked",text("Unlock Travel Plan").isDisplayed());
    }
     public void navigateToInstitutionNotificationPage(){
        getNavigationBar().goToRepVisits();
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
                waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='Something unexpected happened. Please, try again.']")),10);
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
        Assert.assertTrue("Add people to counselor community label is not displayed", addPeopleOutsideCounselorCommunityLabel().isDisplayed());
    }

    public void validateEmailInInstitutionNotificationPage(String Email,String InvalidEmail,String ValidEmail){
        emailTextBox().clear();
        emailTextBox().sendKeys(Email);
        emailTextBox().sendKeys(InvalidEmail);
        saveButton().click();
        Assert.assertTrue("Error message is not displayed",getDriver().findElement(By.xpath("//span[text()='Emails must be valid and separated by comma.']")).isDisplayed());
        emailTextBox().clear();
        emailTextBox().sendKeys(Email);
        emailTextBox().sendKeys(ValidEmail);
        saveButton().click();
        waitUntilPageFinishLoading();
        String ExactMessage=getDriver().findElement(By.cssSelector("div[class='content']")).getText();
        String SuccessMessage="Success! You've updated your notifications settings.";
        Assert.assertTrue("Success Message is not displayed", ExactMessage.equals(SuccessMessage));
        setImplicitWaitTimeout(7);
        waitUntil(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[class='content']")));
        resetImplicitWaitTimeout();
    }

    public void validateCheckboxInInstitutionNotificationPage(String checkboxValue){
        checkBoxInAccountSettingsNotification(checkboxValue).click();
        checkBoxInAccountSettingsNotification(checkboxValue).click();
    }

    public void verifyNotificationTabinNonAdmin(){
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        userDropDown().click();
        accountSettings().click();
        waitUntilPageFinishLoading();
        List<WebElement> list = getDriver().findElements(By.xpath("//span[text()='Institution Notifications']"));
        Assert.assertTrue("'Institution Notifications' is displayed in Account settings page",list.size()==0);
   }

    public void verifyNavigationInNonAdminByURl(){
        load(GetProperties.get("he.accountsettings.url"));
        waitUntilPageFinishLoading();
        Assert.assertTrue("Authorization message is not displayed in Account settings page",getDriver().findElement(By.xpath("//div/h1[text()='You are not authorized to view the content on this page']")).isDisplayed());
    }

    private WebElement upgradeButton(){
        WebElement button = getDriver().findElement(By.cssSelector("button[class='ui button _3A-KkdzsiqhORmN0RiEGSO']"));
        return button;
   }

    private WebElement getRepVisitsBtn() {
        return link(By.id("js-main-sidebar-nav-home-menu-link"));
    }
    public void verifyAccountsettings(String leftSubMenuInaccountSettings) {
        String subMenu[] = leftSubMenuInaccountSettings.split(",");
        for (int i=0;i<subMenu.length-1;i++) {
            Assert.assertTrue("Tab " + subMenu[i] + " is not displaying as expected!",leftSubMenu(subMenu,i).isDisplayed());
        } }

    public void verifyPasswordFields(String AccountInformationPage,String firstName,String lastName,String eMail,DataTable dataTable) {
        String details[] = AccountInformationPage.split(",");
        for(int i=0;i<details.length-1;i++) {
            Assert.assertTrue(details[i] + " is not showing.",accountSettingsNonPasswordFields(details,i)
                    .isDisplayed());}
        currentPasswordInput().sendKeys(Keys.PAGE_DOWN);

        List<String> list = dataTable.asList(String.class);
        for (String passwordCriteria : list) {
            Assert.assertTrue(passwordCriteria + " is not showing.",text(passwordCriteria).isDisplayed());
        }
        waitUntil(ExpectedConditions.visibilityOf(firstNameTextbox()));
        waitUntil(ExpectedConditions.visibilityOf(lastNameTextbox()));
        waitUntil(ExpectedConditions.visibilityOf(emailTextBox()));
        String firstname=firstNameTextbox().getAttribute("value");
        Assert.assertTrue("FirstName is not displayed",firstname.equals(firstName));
        String lastname=lastNameTextbox().getAttribute("value");
        Assert.assertTrue("LastName is not displayed",lastname.equals(lastName));
        String email=eMailTextbox().getAttribute("value");
        Assert.assertTrue("Email is not displayed",email.equals(eMail));
    }

    public void validatePassword(String oldPassword,String newPassword,String minimum8character,String lowercaseletter,String uppercaseletter,String withoutNumber,String withoutspecialcharacter) {
        currentPasswordInput().clear();
        currentPasswordInput().sendKeys(newPassword);
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
        newPasswordInput().sendKeys(oldPassword);
        confirmPasswordInput().sendKeys(oldPassword);
        saveButton().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(successMessage()));
        if(negativeMessage().size()==1) {
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
            Assert.assertTrue("Tab " + tab + " is not displaying as expected!",getDriver().findElement(By.xpath("//a/span[text()='"+tab+"']")).isDisplayed());
        } }

    public void verifyDetailsInaccountSettingsforNonAdmin(String leftSubMenuInaccountSettings) {
        Assert.assertTrue("Tab " + leftSubMenuInaccountSettings + " is not displaying as expected!",getDriver().findElement(By.xpath("//a/span[text()='"+leftSubMenuInaccountSettings+"']")).isDisplayed());
    }


    public void resetPassword(String oldPassword,String newPassword) {
        currentPasswordInput().clear();
        currentPasswordInput().sendKeys(oldPassword);
        newPasswordInput().sendKeys(newPassword);
        confirmPasswordInput().sendKeys(newPassword);
        saveButton().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(successMessage()));
        if(negativeMessage().size()==1) {
            Assert.fail("Error Message is displayed");
        }
    }

    public void verifySuccessMessageinAccountSettingsPage(String message){
        String SuccessMessage = getSuccessMessage().getText();
        Assert.assertTrue("Success message is not displayed",message.equals(SuccessMessage));
    }

    public void selectCalendar() {
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        getCalendarBtn().click();
        waitUntilPageFinishLoading();
    }
    public void verifyCalendarPage(String school,String time,String date) {
        getNavigationBar().goToRepVisits();
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
        if (calendarAppointments(startTime,school).size() == 1) {
            selectAppointmentInCalendar(startTime, school);
        } else if (calendarAppointments(startTime,school).size() == 0) {
            int appointment = getAppointmentFromCalendar(startTime, school);
            if (appointment == 0) {
                startTime = getCalendarStartTime();
                if (calendarAppointments(startTime,school).size() == 1) {
                    selectAppointmentInCalendar(startTime, school);
                } else if (calendarAppointments(startTime,school).size() == 0) {
                    appointment = getAppointmentFromCalendar(startTime, school);
                    if(appointment==1){
                        selectAppointmentInCalendar(startTime, school);
                    }else {
                        Assert.fail("Appointment is not displayed");
                    }
                }
            }else if(appointment == 1){
                selectAppointmentInCalendar(startTime, school);
            }
        }else {
            Assert.fail("Appointment is not displayed");
        }
    }

    public int getAppointmentFromCalendar(String startTime,String school){
        if(getMonthRow().size()>0){
            outerloop:
            for(int i=1;i<=getMonthRow().size();i++){
                for (int j = 1; j <= 7; j++) {
                    int rowCount =  getShowMoreRowCount(i,j);
                    if(rowCount>0) {
                        jsClick(selectShowMoreButton(i, j, rowCount));
                        waitUntilPageFinishLoading();
                        if (calendarAppointments(startTime, school).size() == 1) {
                            break outerloop;
                        } else {
                            jsClick(selectShowMoreButton(i, j, rowCount));
                            waitUntilPageFinishLoading();
                        }
                    }
                }
            }
        }
        return calendarAppointments(startTime,school).size();
    }

    public void selectAppointmentInCalendar(String startTime,String school){
        Assert.assertTrue("Appointment is not displayed", calendarAppointments(startTime,school).size() == 1);
        jsClick(calendarAppointment(startTime,school));
        waitUntilPageFinishLoading();
    }

    private int getShowMoreRowCount(int firstIndex,int secondIndex){
        int count = 0,i;
        for(i=3;i<=7;i++){
            if(showMoreButton(firstIndex,secondIndex,i).size()==1){
                count = i;
                break;
            }
        }
        return count;
    }

    public void verifyCalendarPopup(String school,String date,String startTime,String endTime,String hsAddress,String contactPhoneNo,String user,String eMail) {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(visitDetailsText()));
        Assert.assertTrue("Visit Details is not displayed",visitDetailsLabel().isDisplayed());
        Assert.assertTrue("Contact is not displayed",contactLabel().isDisplayed());
        Assert.assertTrue("user is not displayed",userText(user).isDisplayed());
        Assert.assertTrue("email id is not displayed",eMail(eMail).isDisplayed());
        Assert.assertTrue("contact no is not displayed",!getContactNo(eMail).getText().equals(""));
        Assert.assertTrue("school name is not displayed",schoolNameText(school).isDisplayed());
        Assert.assertTrue("address is not displayed",address(school,hsAddress).isDisplayed());
        String Date = getSpecificDateforCalendar(date);
        Assert.assertTrue("date is not displayed",date(Date).isDisplayed());
        startTime = getCalendarPopupVisitTime().toUpperCase();
        Assert.assertTrue("Time is not displayed",time(startTime, endTime).isDisplayed());
        Assert.assertTrue("Instructions from High School option is not displayed",instructions().isDisplayed());

        try{
            Assert.assertTrue("Reschedule and cancel option is not displayed",rescheduleVisit().isDisplayed()&&cancelVisit().isDisplayed());
        }catch (Exception e){}
        saveButtonInCalendarPopup().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(successMessage()));
    }
    public void verifyCalendarPopupforfreemium$(String school,String date,String startTime,String endTime,String hsAddress,String contactPhoneNo,String user,String eMail) {
        waitForUITransition();
        Assert.assertTrue("Visit Details is not displayed",visitDetailsLabel().isDisplayed());
        Assert.assertTrue("Contact is not displayed",contactLabel().isDisplayed());
        Assert.assertTrue("user is not displayed",userText(user).isDisplayed());
        Assert.assertTrue("email id is not displayed",eMail(eMail).isDisplayed());
        Assert.assertTrue("contact no is not displayed",!getContactNo(eMail).getText().equals(""));
        Assert.assertTrue("school name is not displayed",schoolNameText(school).isDisplayed());
        Assert.assertTrue("address is not displayed",address(school,hsAddress).isDisplayed());
        String Date=getSpecificDateforCalendar(date);
        Assert.assertTrue("date is not displayed",date(Date).isDisplayed());
        startTime=getCalendarPopupVisitTime().toUpperCase();
        Assert.assertTrue("Time is not displayed",time(startTime, endTime).isDisplayed());
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
        jsClick(circularIconCloseButton());
        waitUntil(ExpectedConditions.visibilityOf(currentMonthInCalendarPage()));
    }

    public void visitsScheduleInFreemium(String school,String startDate,String time){
        visit().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(schoolInFreemium(school)));
        Assert.assertTrue("school is not displayed",verifySchoolInFreemium(school).isDisplayed());
        waitUntil(ExpectedConditions.visibilityOf(goToDate()));
        String gotoDate = getSpecificDate(startDate);
        setDate(gotoDate, "Go To Date");
        String date=getMonthandDate(startDate);
        String visitTime = pageObjects.HS.repVisitsPage.RepVisitsPageImpl.StartTime;
        Assert.assertTrue("Availability is not displayed",availabilityButton(date,visitTime).isDisplayed());
        availabilityButton(date,visitTime).click();
        waitUntilPageFinishLoading();
    }

    public void removeAppointmentfromCalendar(){
        waitUntil(ExpectedConditions.visibilityOfElementLocated(cancelVisitButton()));
        moveToElement(cancelThisVisitButton());
        Assert.assertTrue("Cancel This Visit is not displayed",cancelThisVisitButton().isDisplayed());
        cancelThisVisitButton().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(cancelMessageText()));
        cancelMessageTextBox().click();
        cancelMessageTextBox().sendKeys(Keys.PAGE_DOWN);
        cancelMessageTextBox().sendKeys("by QA");
        visitCancelButton().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(successmessage()));
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
            getDriver().findElement(By.cssSelector("button[class='ui button _1wqaQnL4wzTmKK7w_sXTwT']")).click();
        }else if (fromOrTo.equals("To")){
            getDriver().findElement(By.cssSelector("button[class='ui button _2pj6YkRYwl9szEe_wh7dxF']")).click();
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
        navigateToRepVisitsSection("Search and Schedule");
        waitUntilPageFinishLoading();
        getSearchBox().sendKeys(schoolName);
        getSearchButton().click();
        waitUntil(ExpectedConditions.visibilityOf(link(schoolName)));
        link(schoolName).click();
        waitForUITransition();
        waitUntilElementExists(getFairsButton());
        getFairsButton().click();
        waitForUITransition();
        waitUntilElementExists(getDriver().findElement(By.cssSelector("div[id='search-container']")));
        Assert.assertTrue("College Fair: " + pageObjects.HS.repVisitsPage.RepVisitsPageImpl.FairName + " was not displayed in upcoming fairs list",getDriver().findElement(By.xpath("//span[text()='" + pageObjects.HS.repVisitsPage.RepVisitsPageImpl.FairName + "']")).isDisplayed());
    }

    /**
     * Verifies that the college fair stored in HS.repVisitsPage.FairName is not visible in the fairs list for the given school
     * @param schoolName - Name of the school to look for the fair under.
     */
    public void verifyCollegeFairNotVisible(String schoolName) {
        navigateToRepVisitsSection("Search and Schedule");
        waitUntilPageFinishLoading();
        getSearchBox().sendKeys(schoolName);
        getSearchButton().click();
        link(schoolName).click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(getFairsButtonLocator()));
        getFairsButton().click();
        waitUntilPageFinishLoading();
        getDriver().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        try {
            Assert.assertFalse("College Fair: " + pageObjects.HS.repVisitsPage.RepVisitsPageImpl.FairName + " was displayed in upcoming fairs list",getDriver().findElement(By.xpath("//span[text()='" + pageObjects.HS.repVisitsPage.RepVisitsPageImpl.FairName + "']")).isDisplayed());
            getDriver().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        } catch (Exception e) {
            getDriver().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        }
    }

    public void openCalendar() {
        getNavigationBar().goToRepVisits();
        getCalendarBtn().click();
        waitForUITransition();
    }

    public void openFairDetailsWithGeneratedDate() {
        formattedDate = pageObjects.HS.repVisitsPage.RepVisitsPageImpl.generatedDate + ","
                + pageObjects.HS.repVisitsPage.RepVisitsPageImpl.generatedDateDayOfWeek + ","
                + pageObjects.HS.repVisitsPage.RepVisitsPageImpl.time.replaceFirst(" ", "");
        openFairDetails(formattedDate);
    }

    public void openFairDetails(String date) {
        pressCalendarArrowUntil("right", date.split(",")[0], 10);
        jsClick(getDateCell(date.split(",")[1].split(" ")[1], date.split(",")[2], 1));
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
                case "College Fair Name" :
                    String fairName = fairDataElement.get(1);
                    if(fairName.equalsIgnoreCase("PreviouslySetFair")){
                        fairName =FairName;
                    }
                    softly().assertThat(fairNameHeader().getText()).as("The College Fair name is incorrect").isEqualTo(fairName);
                    break;
                case "High School name" :
                    softly().assertThat(fairHSName().getText()).as(fairDataElement.get(0) + " is incorrect").isEqualTo(fairDataElement.get(1));
                    break;
                case "High School address" :
                    softly().assertThat(fairHSAddress().getText()).as(fairDataElement.get(0) + " is incorrect").isEqualTo(fairDataElement.get(1));
                    break;
                case "Contact name" :
                    softly().assertThat(fairContactName().getText()).as(fairDataElement.get(0) + " is incorrect").isEqualTo(fairDataElement.get(1));
                    break;
                case "Contact title" :
                    softly().assertThat(fairContactTitle().getText()).as(fairDataElement.get(0) + " is incorrect").isEqualTo(fairDataElement.get(1));
                    break;
                case "Contact email" :
                    softly().assertThat(fairContactEmail().getText()).as(fairDataElement.get(0) + " is incorrect").isEqualTo(fairDataElement.get(1));
                    break;
                case "Contact phone" :
                    softly().assertThat(fairContactPhone().getText()).as(fairDataElement.get(0) + " is incorrect").isEqualTo(fairDataElement.get(1));
                    break;
                case "Date" :
                    softly().assertThat(fairDate().getText()).as(fairDataElement.get(0) + " is incorrect").isEqualTo(fairDataElement.get(1));
                    break;
                case "Start time" :
                    softly().assertThat(fairStartTime().getText()).as(fairDataElement.get(0) + " is incorrect").isEqualTo(fairDataElement.get(1));
                    break;
                case "End time" :
                    softly().assertThat(fairEndTime().getText()).as(fairDataElement.get(0) + " is incorrect").isEqualTo(fairDataElement.get(1));
                    break;
                case "Time zone" :
                    softly().assertThat(fairTimeZone().getText()).as(fairDataElement.get(0) + " is incorrect").isEqualTo(fairDataElement.get(1));
                    break;
                case "Cost" :
                    softly().assertThat(fairCost().getText()).as(fairDataElement.get(0) + " is incorrect").isEqualTo(fairDataElement.get(1));
                    break;
                case "Number of students" :
                    softly().assertThat(fairExpectedStudents().getText()).as(fairDataElement.get(0) + " is incorrect").isEqualTo(fairDataElement.get(1));
                    break;
                case "Instructions" :
                    softly().assertThat(fairInstructions().getText()).as(fairDataElement.get(0) + " is incorrect").isEqualTo(fairDataElement.get(1));
                    break;
                case "Cancellation text" :
                    softly().assertThat(fairCancellationInstructions().getText()).as(fairDataElement.get(0) + " is incorrect").contains(fairDataElement.get(1));
                    break;
                case "Cancel link" :
                    if (fairDataElement.get(1).equals("Present")) {
                        softly().assertThat(ExpectedConditions.visibilityOf(fairCancelLink())).as(fairDataElement.get(0) + " is incorrect");
                    } else if (fairDataElement.get(1).equals("Not Present")) {
                        softly().assertThat(ExpectedConditions.visibilityOf(fairCancelLink())).as(fairDataElement.get(0) + " is incorrect");
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
        Assert.assertTrue("'Visits' tab is not displayed as Default tab",getDriver().findElement(By.xpath("//span[text()='Visits']")).isDisplayed());
        getDriver().findElement(By.xpath("//span[text()='Fairs']")).click();
        Assert.assertTrue("Unable to Toggle 'Fairs' tab",getDriver().findElement(By.xpath("//span[text()='Fairs']")).isEnabled());
        getDriver().findElement(By.xpath("//span[text()='Visits']")).click();
        Assert.assertTrue("Unable to Toggle 'Visits' tab",getDriver().findElement(By.xpath("//span[text()='Visits']")).isEnabled());
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
        Assert.assertTrue("Searched school is not displayed",getDriver().findElement(By.xpath("//div/h3/a[text()='"+schoolName+"']")).isDisplayed());
        Assert.assertTrue("High school name scheduling results is a not a hyperlink",link(schoolName).isEnabled());
        link(schoolName).click();
    }

    public void verifyHSpopup(DataTable dataTable) {
        List<String> list = dataTable.asList(String.class);
        for (String schoolDetails : list) {
            Assert.assertTrue(schoolDetails + " is not showing.",text(schoolDetails).isDisplayed());}
        getDriver().findElement(By.xpath("//div[@class='ui page modals dimmer transition visible active']/div/i")).click();
    }

    public void verifyHSBlockedText(String school) {
        List<WebElement> noAppointments = getDriver().findElements(By.xpath("//a[contains(text(),'" + school + "')]/../ancestor::div[@class='ui items']/../following-sibling::td/span[contains(text(),'No Appointments Available')]"));
        List<WebElement> blockedText = getDriver().findElements(By.xpath("//a[contains(text(),'" + school + "')]/../ancestor::div[@class='ui items']/../following-sibling::td/h1/span[contains(text(),'Blocked')]"));
        if(noAppointments.size()==1) {
            Assert.assertTrue("No Appointments Available text is not displayed", noAppointments.size() == 1);
        }else {
            logger.info("No Appointments Available text is not displayed");
        }
        if(blockedText.size()==1) {
            Assert.assertTrue("Blocked text is not displayed", blockedText.size() == 1);
        }else {
            logger.info("Blocked text is not displayed");
        }
    }

    public void selectHSLink(String schoolName) {
        link(schoolName).click();
        getDriver().findElement(By.xpath("//div[@class='column _3dlZYPxGjtMbv6sS-JsWeA']/a[contains(text(),'"+schoolName+"')]")).click();
    }

    public void verifyInActiveSubscription(String schoolName){
        waitForUITransition();
        waitUntilElementExists(goToDate());
        Assert.assertTrue("Searched school is not displayed",getDriver().findElement(By.xpath("//a[text()='"+schoolName+"'] | //div[text()='"+schoolName+"']")).isDisplayed());
        try {
            Assert.assertTrue("High school name scheduling results is not a hyperlink", link(schoolName).isEnabled());
        }
        catch(Exception e)
        {
        }
    }

    public void verifyCityAndStateInRequestNotificationsubTab(String cityAndState,String school){
        String visitTime = pageObjects.HS.repVisitsPage.RepVisitsPageImpl.StartTime;
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        getNotificationsBtn().click();
        String value[] = cityAndState.split(",");
        String city = value[0];
        String state = value[1];
        verifyAndClickShowMoreButton("Request",visitTime,city,state,school);
        waitUntil(ExpectedConditions.visibilityOfElementLocated(cityAndStateInRequest(visitTime,city,state,school)));
        Assert.assertTrue("City and state are not displayed",verifyCityAndStateInRequest(visitTime,city,state,school).isDisplayed());
    }

    public void verifyCityAndStateInActivitysubTab(String cityAndState,String school){
        String visitTime = pageObjects.HS.repVisitsPage.RepVisitsPageImpl.StartTime;
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        getNotificationsBtn().click();
        getActivityTab().click();
        String value[] = cityAndState.split(",");
        String city = value[0];
        String state = value[1];
        verifyAndClickShowMoreButton("Activity",visitTime,city,state,school);
        waitUntil(ExpectedConditions.visibilityOfElementLocated(cityAndStateInActivity(visitTime,city,state,school)));
        Assert.assertTrue("City and state are not displayed",verifyCityAndStateInActivity(visitTime,city,state,school).isDisplayed());
    }

    public void verifyCityAndStateInRequestNotificationsubTabforFairs(String cityAndState,String school){
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        getNotificationsBtn().click();
        String value[] = cityAndState.split(",");
        String city = value[0];
        String state = value[1];
        verifyAndClickShowMoreButtonforFairs("Request",city,state,school);
        waitUntil(ExpectedConditions.visibilityOfElementLocated(cityAndStateInRequestforFairs(city,state,school)));
        Assert.assertTrue("City and state are not displayed",verifyCityAndStateInRequestforFairs(city,state,school).isDisplayed());
    }

    public void verifyCityAndStateInActivitysubTabforFairs(String cityAndState,String school){
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        getNotificationsBtn().click();
        getActivityTab().click();
        String value[] = cityAndState.split(",");
        String city = value[0];
        String state = value[1];
        verifyAndClickShowMoreButtonforFairs("Activity",city,state,school);
        waitUntil(ExpectedConditions.visibilityOfElementLocated(cityAndStateInActivityforFairs(city,state,school)));
        Assert.assertTrue("City and state are not displayed",verifyCityAndStateInActivityforFairs(city,state,school).isDisplayed());
    }

    private void verifyAndClickShowMoreButton(String option,String time,String city,String state,String school){
        try{
            if(option.equals("Activity")){
                waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='_3d9aL7nr2h8RrqrSAC7mMk _1JTfN0oSkdjnUDHbAdVRl9']")));
                if(cityAndStateInActivityTab(time, city, state, school).size()==0) {
                    if (showMore().size() == 1) {
                        while (cityAndStateInActivityTab(time, city, state, school).size() == 1) {
                            if (showMore().size() == 1) {
                                jsClick(showMoreButton());
                                waitUntilPageFinishLoading();
                                waitForUITransition();
                            }
                        }
                    }
                }
            }else if(option.equals("Request")){
                waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='View Full Details']")));
                if(cityAndStateInRequestTab(time, city, state, school).size()==0){
                    if (showMore().size() == 1) {
                        while (cityAndStateInRequestTab(time, city, state, school).size() == 0) { if (showMore().size() == 1) {
                            jsClick(showMoreButton());
                            waitUntilPageFinishLoading();
                            waitForUITransition();
                        }
                        }
                    }
                }
            }
        }catch(Exception e) {
            Assert.fail("Notification is not displayed");
        }
    }


    private void verifyAndClickShowMoreButtonforFairs(String option,String city,String state,String school) {
        try {
            if (option.equals("Request")) {
                waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='View Full Details']")));
                if (cityAndStateInRequestTabforFairs(city, state, school).size() == 0) {
                    if (showMore().size() == 1) {
                        while (cityAndStateInRequestTabforFairs(city, state, school).size() == 0) {
                            if (showMore().size() == 1) {
                                jsClick(showMoreButton());
                                waitUntilPageFinishLoading();
                                waitForUITransition();
                            }
                        }
                    }
                }
            } else if (option.equals("Activity")) {
                waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='_3d9aL7nr2h8RrqrSAC7mMk _1JTfN0oSkdjnUDHbAdVRl9']")));
                if (cityAndStateInActivityTabforFairs(city, state, school).size() == 0) {
                    if (showMore().size() == 1) {
                        while (cityAndStateInRequestTabforFairs(city, state, school).size() == 0) {
                            if (showMore().size() == 1) {
                                jsClick(showMoreButton());
                                waitUntilPageFinishLoading();
                                waitForUITransition();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            Assert.fail("Notification is not displayed");
        }
    }

    public void verifyDropdownInSearchAndSchedulePage(String dropdown){
        navigateToRepVisitsSection("Search and Schedule");
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//div/label[text()='"+dropdown+"']"),1));
        Assert.assertTrue("Search by text is displayed in the drop-down",getDriver().findElement(By.xpath("//div/label[text()='"+dropdown+"']")).isDisplayed());
    }

    public void verifyDropdownFieldsInSearchAndSchedule(DataTable dataTable){
        List<String> list = dataTable.asList(String.class);
        dropdownInSearchAndSchedule().click();
        for(String fields:list){
            Assert.assertTrue(fields+" is not displayed in the dropdown",getDriver().findElement(By.xpath("//div/span[text()='"+fields+"']")).isDisplayed());
        }
    }

    public void verifyBackgroundColorforFreemiumorPremium(String color,DataTable dataTable){
        navigateToRepVisitsSection("Search and Schedule");
        waitUntilPageFinishLoading();
        List<String> list = dataTable.asList(String.class);
        dropdownInSearchAndSchedule().click();
        for(String fields:list) {
            String displayingColor = getDriver().findElement(By.xpath("//div/span[text()='"+fields+"']/parent::div")).getCssValue("background-color");
            Assert.assertTrue("Color is not equal",displayingColor.equals(color));
        }
    }

    public void verifyPremiumSearchInSearchByDropdown(String premiumText){
        dropdownInSearchAndSchedule().click();
        Assert.assertTrue("Premium Search text with lock icon is not displayed",getDriver().findElement(By.xpath("//span[text()='"+premiumText+"']/following-sibling::i[@class='lock icon right floated']")).isDisplayed());
    }

    public void verifyDefaultOptionInSearchByDropdown(String defaultOption,DataTable dataTable){
        waitUntilPageFinishLoading();
        String defaultValue[] = defaultOption.split(",");
        List<String> list = dataTable.asList(String.class);
        for(String fields:list){
            dropdownInSearchAndSchedule().click();
            waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/span[text()='"+fields+"']")));
            Assert.assertTrue(fields+" is not displayed in the dropdown",getDriver().findElement(By.xpath("//div/span[text()='"+fields+"']")).isDisplayed());
            getDriver().findElement(By.xpath("//div/span[text()='"+fields+"']")).click();
            Assert.assertTrue(fields+" is not displayed",getDriver().findElement(By.xpath("//div/label[text()='"+defaultValue[0]+"']/parent::div/div/span[text()='"+fields+"']")).isDisplayed());
            calendar().click();
            waitUntilPageFinishLoading();
            navigateToRepVisitsSection("Search and Schedule");
            Assert.assertTrue(defaultOption+" is not displayed",getDriver().findElement(By.xpath("//div/label[text()='"+defaultValue[0]+"']/parent::div/div/span[text()='"+defaultValue[1]+"']")).isDisplayed());
        }
    }

    public void verifyUpgradeNotificationPage(String upgrade,DataTable dataTable){
        navigateToRepVisitsSection("Search and Schedule");
        waitUntilPageFinishLoading();
        List<String> list = dataTable.asList(String.class);
        for(String fields:list){
            dropdownInSearchAndSchedule().click();
            getDriver().findElement(By.xpath("//div/span[text()='"+fields+"']")).click();
            waitUntilPageFinishLoading();
            waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//span[text()='Upgrade']"),1));
            Assert.assertTrue("Upgrade popup is not displayed",getDriver().findElement(By.xpath("//span[text()='"+upgrade+"']")).isDisplayed());
            upgradePopupCloseButtonInSearchAndScheduleDropdown().click();
            waitUntilPageFinishLoading();
        }
    }

    public void verifySearchByOptionAfterSelectFields(DataTable dataTable){
        navigateToRepVisitsSection("Search and Schedule");
        waitUntilPageFinishLoading();
        List<String> list = dataTable.asList(String.class);
        for(String fields:list) {
            dropdownInSearchAndSchedule().click();
            getDriver().findElement(By.xpath("//div/span[text()='" + fields + "']")).click();
            waitUntilPageFinishLoading();
            Assert.assertTrue(fields + " is not present in the Textbox", getDriver().findElement(By.xpath("//div/label[text()='Search by']/parent::div/div/span[text()='" + fields + "']")).isDisplayed());
        }
    }

    public void verifyTextInSearchAndScheduleTextBox(String text){
        navigateToRepVisitsSection("Search and Schedule");
        waitUntilPageFinishLoading();
        Assert.assertTrue(text+" is not present in the textbox",getDriver().findElement(By.xpath("//input[@placeholder='"+text+"']")).isDisplayed());
    }

    public void selectFieldswillnotSubmitSearch(String defaultOption,String school,DataTable dataTable){
        navigateToRepVisitsSection("Search and Schedule");
        waitUntilPageFinishLoading();
        List<String> list = dataTable.asList(String.class);
        getSearchBox().sendKeys(school);
        dropdownInSearchAndSchedule().click();
        getDriver().findElement(By.xpath("//div/span[text()='" + defaultOption + "']")).click();
        getSearchButton().click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("Result is not displayed",getDriver().findElement(By.xpath("//td/h3/a[text()='"+school+"']")).isDisplayed());
        for(String fields:list){
            dropdownInSearchAndSchedule().click();
            waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//div[@role='option']/span[text()='" + fields + "']"),1));
            getDriver().findElement(By.xpath("//div/span[text()='" + fields + "']")).click();
            waitUntilPageFinishLoading();
            Assert.assertTrue("Result is not displayed",getDriver().findElement(By.xpath("//td/h3/a[text()='"+school+"']")).isDisplayed());
        }
        getSearchButton().click();
        waitUntilPageFinishLoading();
        waitUntilElementExists(noResultsMessageInSearchAndSchedule());
        Assert.assertTrue("Result is displayed",noResultsMessageInSearchAndSchedule().isDisplayed());
    }
  
    /**
     * Searchs for schools given a text to search and the criteria
     * @param criteria to perform the search
     * @param parameter to find the schools
     */
    public void searchBySchool(String criteria, String parameter){
        getNavigationBar().goToRepVisits();
        navigateToRepVisitsSection("Search and Schedule");
        selectSearchCriteria(criteria);
        searchTextBox().click();
        searchTextBox().clear();
        searchTextBox().sendKeys(parameter);
        searchButton().click();
        waitUntil(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='ui medium inverted loader']")));
    }

    /***
     * Sets the search criteria, for the search and schedule page
     * @param criteria to be selected
     */
    private void selectSearchCriteria(String criteria){
        dropdownInSearchAndSchedule().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(
                "//div/span[text()='%s']",criteria))));
        getDriver().findElement(By.xpath(String.format("//div/span[text()='%s']",criteria))).click();
    }

    /**
     * Verifies that the search result contains a given value, in a given field.
     * @param field, to verify the given value
     * @param value to ve verified
     */
    public void verifyFilteredSearchResults(String field, String value){
        while(getMoreResultsButton().isDisplayed()){
            getMoreResultsButton().click();
        }
        switch(field){
            case "Name":
                verifyNameFieldForSearchResults(value);
                break;
            case "City":
                verifyCityFieldForSearchResults(value);
                break;
            case "U.S. State":
                verifyStateFieldForSearchResults(value);
                break;
            case "U.S. County":
                verifyCountyFieldForSearchResults(value);
                break;
            case "Country":
                verifyCountryFieldForSearchResults(value);
                break;
        }
    }

    /**
     * Verifies that the given value is present in the name field of the result search
     * @param value to be verified
     */
    private void verifyNameFieldForSearchResults(String value){
        List<WebElement> rows = getDriver().findElements(By.xpath("//tr"));
        for(WebElement row : rows){
            String school = row.findElement(By.cssSelector("td._2i9Ix-ZCUb0uO32jR3hE3x")).getAttribute("innerText");
            Assert.assertTrue(String.format("The school name value is not correct, actual: %s, expected: %s",
                    school,value), school.equalsIgnoreCase(value));
        }
    }

    /**
     * Verifies that the given value is present in the city field of the result search
     * @param value to be verified
     */
    private void verifyCityFieldForSearchResults(String value){
        List<WebElement> rows = getDriver().findElements(By.xpath("//tr"));
        for(WebElement row : rows){
            String school = row.findElement(By.cssSelector("td._2i9Ix-ZCUb0uO32jR3hE3x")).getAttribute("innerText");
            String schoolCity = row.findElement(By.cssSelector("td._2153pfSQVTks2SHnwPyg-v")).
                    getAttribute("innerText").split(",")[0];
            Assert.assertTrue(String.format("The City value is not correct for the school: %s, actual: %s, expected: %s",
                    school, schoolCity,value), schoolCity.equalsIgnoreCase(value));
        }
    }

    /**
     * Verifies that the given value is present in the state field of the result search
     * @param value to be verified
     */
    public void verifyStateFieldForSearchResults(String value){
        List<WebElement> rows = getDriver().findElements(By.xpath("//tr"));
        for(WebElement row : rows){
            String school = row.findElement(By.cssSelector("td._2i9Ix-ZCUb0uO32jR3hE3x")).getAttribute("innerText");
            String schoolState = row.findElement(By.cssSelector("td._2153pfSQVTks2SHnwPyg-v")).
                    getAttribute("innerText").split(",")[1];
            Assert.assertTrue(String.format("The State value is not correct for the school: %s, actual: %s, expected: %s",
                    school, schoolState,value), schoolState.equalsIgnoreCase(value));
        }
    }

    /**
     * Verifies that the given value is present in the county field of the result search
     * @param value to be verified
     */
    public void verifyCountyFieldForSearchResults(String value){
        List<WebElement> rows = getDriver().findElements(By.xpath("//tr"));
        for(WebElement row : rows){
            String school = row.findElement(By.cssSelector("td._2i9Ix-ZCUb0uO32jR3hE3x")).getAttribute("innerText");
            String schoolCounty = row.findElement(By.cssSelector("p._1R_ntcOgVThs8R9j3y8dTW")).
                    getAttribute("innerText");
            Assert.assertTrue(String.format("The County value is not correct for the school: %s, actual: %s, expected: %s",
                    school, schoolCounty,value), schoolCounty.equalsIgnoreCase(value));
        }
    }

    /**
     * Verifies that the given value is present in the country field of the result search
     * @param value to be verified
     */
    private void verifyCountryFieldForSearchResults(String value){
        List<WebElement> rows = getDriver().findElements(By.xpath("//tr"));
        for(WebElement row : rows){
            String school = row.findElement(By.cssSelector("td._2i9Ix-ZCUb0uO32jR3hE3x")).getAttribute("innerText");
            String schoolCountry = row.findElement(By.xpath("//td/td")).
                    getAttribute("innerText");
            Assert.assertTrue(String.format("The Country value is not correct for the school: %s, actual: %s, expected: %s",
                    school, schoolCountry,value), schoolCountry.equalsIgnoreCase(value));
        }
    }

    /**
     * Verifies that a given text is displayed in the search results
     * @param text
     */
    public void verifyLabelInSearchResult(String text){
        Assert.assertTrue(String.format("The text: %s is not displayed in the search results",text),
                getSearchResultContainer().findElement(By.xpath(String.format("//span[text()='%s']",text))).isDisplayed());
    }

    /**
     * Verifies a given text in the search school tooltip
     * @param text
     */
    public void verifySearchToolTip(String text){
        Assert.assertTrue(String.format("The text: %s is not displayed in the search results tooltip",text),
                getDriver().findElement(By.xpath(String.format(
                        "//div[@class='ui pointing basic label _2rnRYFCNwUMXpFryzESst_']/span[text()='%s']",text)))
                        .isDisplayed());
    }

    public void selectLinkInsearchAndSchedule(String seeAllHighSchool){
        Assert.assertTrue("See All HighSchool link is not displayed",link(seeAllHighSchool).isDisplayed());
        link(seeAllHighSchool).click();
        waitUntilPageFinishLoading();
    }

    public void verifyTextInSearchResultPage(String text){
        Assert.assertTrue(text+" is not displayed",getDriver().findElement(By.xpath("//div/h2[text()='"+text+"']")).isDisplayed());
    }

    public void verifySchoolInSchedulePage(String school,String value){
        link(school).click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("School is not displayed",link(school).isDisplayed());
        Assert.assertTrue(value+" is not displayed",text(value).isDisplayed());
    }

    public void verifyMoreResultButtonInSearchAndSchedulePage(String moreResults){
        List<WebElement> schoolCount = getDriver().findElements(searchResult());
        if(schoolCount.size()>25){
            Assert.assertTrue("More results button is displayed",button(moreResults).isDisplayed());
        }else {
            logger.info("More results button is not displayed");
        }
    }

    public void verifySearchAndSchedulePageAfterMovedOut(String internationalSchools){
        getCalendarBtn().click();
        waitUntilPageFinishLoading();
        navigateToRepVisitsSection("Search and Schedule");
        List<WebElement> text = getDriver().findElements(By.xpath("//h3[text()='"+internationalSchools+"']"));
        Assert.assertTrue("International Schools result is displayed",text.size()==0);
    }

    public void verifyInternationalSchoolsListIsNotDisplayedforFreemium(){
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("user-dropdown")));
        getDriver().get(currentURL);
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("user-dropdown")));
        waitUntil(ExpectedConditions.numberOfElementsToBe(searchResult(),0));
        List<WebElement> results = getDriver().findElements(searchResult());
        Assert.assertTrue("International school list is displayed",results.size()==0);
    }

    public void getCurrentPageURL(){
        currentURL = getDriver().getCurrentUrl();
    }

    public void verifyResultsCountInSchedulePage(String criteria, String parameter){
        selectMoreResultsInSearchAndSchedule();
        int finalSchoolCount = searchResultsSize();
        searchBySchool(criteria,parameter);
        int count = searchResultsSize();
        logger.info("Result count:"+count);
        if(count==25){
            Assert.assertTrue("Result count is not displayed",getDriver().findElement(By.xpath("//p/span/span[text()='Showing 1-"+count+" of "+finalSchoolCount+"']")).isDisplayed());
            while(getMoreResultsButton().isDisplayed()) {
                getMoreResultsButton().click();
                waitUntilPageFinishLoading();
                count = searchResultsSize();
                String currentValue = Integer.toString(count);
                logger.info("Result count:"+count);
                String displayingCountText = resultCountInSearchResultsPage().getText();
                String value[] = displayingCountText.split("-");
                String countValue[] = value[1].split(" of");
                String displayingValue = countValue[0];
                Assert.assertTrue("Result count is not equal",currentValue.equals(displayingValue));
            }
        }
    }

    /**
     * Verifies the data for a given fair from the College Fair list
     * @param dataTable - Contains the data to be verified in the College Fair List
     */
    public void verifyCollegeFairOnList(DataTable dataTable) {
        Map<String,String> items = dataTable.asMap(String.class,String.class);
        String fairName = items.get("College Fair Name");
        if(fairName.equalsIgnoreCase("PreviouslySetFair")){
            fairName = FairName;
        }
        WebElement firstResultName = getDriver().findElement(By.xpath("//span[text()='" + fairName + "']"));
        WebElement resultFair = getParent(getParent(getParent(firstResultName)));
        for (String key : items.keySet()){
            String value;
            value = items.get(key);
            if(key.equalsIgnoreCase("College Fair Name")){
                value = fairName;
            }
            Assert.assertTrue("Expected to find \""+ value +"\" in Fair entry, but it was not found.",resultFair.findElement(By.xpath("//*[text()[contains(.,'"+ value +"')]]")).isDisplayed());
        }
    }
  
    public void verifyYourNotificationTabforPremium(String yourNotification){
        waitUntilPageFinishLoading();
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        userDropDown().click();
        accountSettings().click();
        waitUntilPageFinishLoading();
        List<WebElement> yourNotificationTab = getDriver().findElements(By.xpath("//a/span[text()='"+yourNotification+"']"));
        Assert.assertTrue("Your Notification tab is not displayed",yourNotificationTab.size()==1);
    }

    public void verifyYourNotificationTabforfreemium(String yourNotification){
        waitUntilPageFinishLoading();
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        userDropDown().click();
        accountSettings().click();
        waitUntilPageFinishLoading();
        List<WebElement> yourNotificationTab = getDriver().findElements(By.xpath("//a/span[text()='"+yourNotification+"']"));
        Assert.assertTrue("Your Notification tab is displayed",yourNotificationTab.size()==0);
    }

    public void verifyYourNotificationTab(DataTable dataTable){
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        userDropDown().click();
        accountSettings().click();
        waitUntilPageFinishLoading();
        yourNotification().click();
        List<String> text = dataTable.asList(String.class);
        for(String displayingValue:text){
            waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button/span[text()='Save']"),1));
            Assert.assertTrue("Given text is not displayed",getDriver().findElement(By.xpath("//span[text()='"+displayingValue+"']")).isDisplayed());
        }
    }

    public void verifySuccessMessageInYourNotification(String successMessage){
        checkBoxInYourNotification().click();
        saveButtonInYourNotification().click();
        Assert.assertTrue("Success message is not displayed", getDriver().findElement(By.xpath("//span[text()='" + successMessage + "']")).isDisplayed());
    }

    public void verifySavedChangesInYourNotification(){
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        userDropDown().click();
        accountSettings().click();
        waitUntilPageFinishLoading();
        yourNotification().click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button/span[text()='Save']"),1));
        Assert.assertTrue("Checkbox is enabled",!checkBoxInYourNotification().isSelected());
        checkBoxInYourNotification().click();
        saveButtonInYourNotification().click();
    }

    public void selectAlertBoxInYourNotification(){
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        userDropDown().click();
        accountSettings().click();
        waitUntilPageFinishLoading();
        yourNotification().click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button/span[text()='Save']"),1));
        if(!checkBoxInYourNotification().isSelected())
            checkBoxInYourNotification().click();
        saveButtonInYourNotification().click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(notificationsSettingsWereUpdatedToastLocator(),1));
        waitUntil(ExpectedConditions.numberOfElementsToBe(notificationsSettingsWereUpdatedToastLocator(),0));
    }

    public void accessAgendaView(String agenda){
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        getCalendarBtn().click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button[text()='"+agenda+"']"),1));
        Assert.assertTrue("Agenda button is not displayed",agendaButton().isDisplayed());
        jsClick(agendaButton());
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//h2[text()='Agenda']"),1));
        Assert.assertTrue("Agenda page is not displayed in the calendar",getStartDateInAgenda().isDisplayed());
    }

    public void verifyAgendaPageForFreemium(String message,String agenda){
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        getCalendarBtn().click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button[text()='"+agenda+"']"),1));
        Assert.assertTrue("Agenda button is not displayed",agendaButton().isDisplayed());
        jsClick(agendaButton());
        Assert.assertTrue("Message is not displayed",getDriver().findElement(By.xpath("//span[text()='"+message+"']")).isDisplayed());
    }

    public void verifyUpgradeButtonInAgendaPage(String upgradeButton,String agenda){
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        getCalendarBtn().click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button[text()='"+agenda+"']"),1));
        Assert.assertTrue("Agenda button is not displayed",agendaButton().isDisplayed());
        jsClick(agendaButton());
        Assert.assertTrue("Upgrade button is not displayed",getUpgradeButton().isDisplayed());
    }

    public void verifyUpgradeModelPage(){
        getUpgradeButton().click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//span[text()='Request Information']"),1));
        Assert.assertTrue("Request information button is not displayed",requestInformationButton().isDisplayed());
        Assert.assertTrue("Upgrade message is not displayed",upgradeMessage().isDisplayed());
        closeButtonInAgendaUpgradePage().click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//span[text()='UPGRADE']"),1));
    }

    public void verifyCollegeFairInHECalendar(String option,String school,String time,String date){
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        calendar().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/label[text()='College Fair - Confirmed']")));
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
            waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@title='Day']")));
            currentMonth = currentMonthInCalendarPage().getText();
            selectMonth = currentMonth.split(" ");
            Month = selectMonth[0];
        }
        if(option.equals("present")){
            Assert.assertTrue("Appointment Slot time and university is not displayed", getDriver().findElement(By.xpath("//span[text()='"+time+"']/following-sibling::span[text()='"+school+"']")).isDisplayed());
        }else if(option.equals("not present")){
            List<WebElement> appointment = getDriver().findElements(By.xpath("//span[text()='"+time+"']/following-sibling::span[text()='"+school+"']"));
            Assert.assertTrue("Appointment Slot time and university is displayed",appointment.size()==0);
        }else {
            Assert.fail("Invalid option");
        }
    }

    public void verifyRequestsSubTabIsEnabled(){
        getNavigationBar().goToRepVisits();
        getNotificationsBtn().click();
        waitUntilElementExists(requestsubtab());
        Assert.assertTrue("Request subtab is not enabled",requestsubtab().isEnabled());
    }

    public void verifySortingNotificationEntries() {
        ArrayList<Date> date=new ArrayList<>();
        Date currentDate=null;
        ArrayList<Date> sortedDate = new ArrayList<>();
        SimpleDateFormat format=new SimpleDateFormat("MMMM d, yyyy");
        for(WebElement dateValue:getDateInRequestTab()){
            String sortingDate = dateValue.getText();
            String Date[] = sortingDate.split(", ");
            String dateWithoutDay = Date[1]+", "+Date[2];
            try {
                currentDate = format.parse(dateWithoutDay);
            }catch(ParseException e){}
            sortedDate.add(currentDate);
        }
        Collections.sort(sortedDate);
        for(WebElement dateValue:getDateInRequestTab()){
            String sortingDate = dateValue.getText();
            String Date[] = sortingDate.split(", ");
            String dateWithoutDay = Date[1]+", "+Date[2];
            try{
                currentDate = format.parse(dateWithoutDay);
            }catch(ParseException e){}
            date.add(currentDate);
        }
        Assert.assertTrue("Date is not sorted",sortedDate.equals(date));
    }

    public void removeFairsAppointmentFromCalendar(){
        waitUntil(ExpectedConditions.visibilityOfElementLocated(internalNotes()));
        jsClick(internalNotesTextBox());
        internalNotesTextBox().sendKeys(Keys.PAGE_DOWN);
        waitUntil(ExpectedConditions.visibilityOfElementLocated(cancelThisFair()));
        Assert.assertTrue("Cancel This Visit is not displayed",cancelThisFairButton().isDisplayed());
        cancelFairAppointmentfromCalendar();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(todayButtonInCalendar()));
    }

    public void setDateInCalendarAgenda(String startDate,String endDate,String agenda){
        getNavigationBar().goToRepVisits();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.linkText("Calendar"),1));
        calendar().click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button[text()='"+agenda+"']"),1));
        jsClick(agendaButton());
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//div[@class='_20rE_mlucRFZ--zviGCI2N']/b/preceding-sibling::button"),1));
        String EndDate = getSpecificDateForCalendar(endDate);
        setDate(EndDate, "LastDate");
        String StartDate = getSpecificDateForCalendar(startDate);
        setDate(StartDate, "FirstDate");
    }

    public void verifyVisitDetailsInYourSchedule(String startTime,String school,String startDate){
        getDriver().navigate().refresh();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(schoolInVisitsLocator(school)));
        waitUntil(ExpectedConditions.visibilityOf(goToDate()));
        String gotoDate;
        int date = Integer.parseInt(startDate);
        gotoDate = getSpecificDate(date,"MMMM d yyyy");
        setDate(gotoDate, "Go To Date");
        waitUntil(ExpectedConditions.visibilityOfElementLocated(visitDetails(school)));
        Assert.assertTrue("Visit details is not displayed in Your schedule page",verifyVisitDetails(school).isDisplayed());
    }

    public void verifyVisitDetailsInCalendar(String school,String date,String startTime){
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        getCalendarBtn().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(calendarDayButton()));
        collegeFairTextBoxInCalendarPage().click();
        String month = month(date);
        String currentMonth = currentMonthInCalendarPage().getText();
        String selectMonth[] = currentMonth.split(" ");
        String Month = selectMonth[0];
        while (!month.equals(Month)) {
            nextMonthButton().click();
            waitForUITransition();
            waitUntilPageFinishLoading();
            waitUntil(ExpectedConditions.visibilityOf(currentMonthInCalendarPage()));
            currentMonth = currentMonthInCalendarPage().getText();
            selectMonth = currentMonth.split(" ");
            Month = selectMonth[0];
        }
        startTime=getCalendarVisitTime().toUpperCase();
        if (calendarAppointments(startTime,school).size() == 1) {
            verifyAppointmentInCalendar(startTime, school);
        } else if (calendarAppointments(startTime,school).size() == 0) {
            int appointment = getAppointmentFromCalendar(startTime, school);
            if (appointment == 0) {
                startTime = getCalendarStartTime();
                if (calendarAppointments(startTime,school).size() == 1) {
                    verifyAppointmentInCalendar(startTime, school);
                } else if (calendarAppointments(startTime,school).size() == 0) {
                    appointment = getAppointmentFromCalendar(startTime, school);
                    if(appointment==1){
                        verifyAppointmentInCalendar(startTime, school);
                    }else {
                        Assert.fail("Appointment is not displayed");
                    }
                }
            }else if(appointment == 1){
                verifyAppointmentInCalendar(startTime, school);
            }
        }else {
            Assert.fail("Appointment is not displayed");
        }
    }

    public void verifyAppointmentInCalendar(String startTime,String school){
        Assert.assertTrue("Appointment is not displayed", calendarAppointments(startTime,school).size()==1);
    }

    /**
     * Select Visit in HE
     * @param highSchool high School to select
     * @param date select date to add Visit
     * @param time the time for the visit selected
     */
    public void selectVisitForHE(String highSchool, String date, String time){
        getNavigationBar().goToRepVisits();
        getSearchBox().sendKeys(highSchool);
        getSearchButton().click();
        waitUntilElementExists(findHESchool(highSchool));
        findHESchool(highSchool).click();
        clickOnButton("Visits").click();
        waitUntilElementExists(goToDate());
        String gotoDate = getSpecificDate(date);
        setDate(gotoDate, "Go To Date");
        String dateSelected = getMonthandDate(date);
        List<WebElement> slot = getDriver().findElements(By.xpath("//span[text()='"+dateSelected+"']/parent::th/ancestor::thead/following-sibling::tbody/tr//td//div/button[text()='"+time+"']"));
        if(slot.size()==0) {
            logger.info("Slot is not displayed");
        }
        findHEButton(time).click();
        findHEButton("Yes, Request this time").click();
    }

    public void verifyAnnouncementTitleIsDisplaying(String announcementTitle){
        waitUntil(ExpectedConditions.visibilityOfElementLocated(announcementTitle(announcementTitle)));
        Assert.assertTrue("Announcement title is not displayed",verifyAnnouncementTitle(announcementTitle).isDisplayed());
    }

    public void verifyReadMoreButtonIsDisplaying(){
        Assert.assertTrue("Read more button is not displayed",readMoreButton().isDisplayed());
    }

    public void verifyAnnouncementDetailsAfterSuccessMessage(String announcement){
        waitUntil(ExpectedConditions.visibilityOfElementLocated(announcementTitle(announcement)));
        Assert.assertTrue("Announcement is not displayed",verifyAnnouncementTitle(announcement).isDisplayed());
    }

    public void verifyDismissButtonInAnnouncementDetails(){
        Assert.assertTrue("Dismiss button is not displayed",announcementDismissButton().isDisplayed());
    }

    public void clickDismissButton(){
        waitUntil(ExpectedConditions.visibilityOfElementLocated(dismissButton()));
        waitUntil(ExpectedConditions.elementToBeClickable(dismissButton()));
        jsClick(announcementDismissButton());
    }

    public void verifyAnnouncementIsNotDisplaying(){
        waitForUITransition();
        Assert.assertTrue("Announcement is displayed",announcementsDismissButton().size()==0);
    }

    public void verifyProductAnnouncementContent(String content){
        Assert.assertTrue("Content is not displaying",announcementContent(content).isDisplayed());
    }

    public void verifyReadMoreButtonIsNotDisplaying(){
        Assert.assertTrue("Read more button is displayed",readMorebutton().size()==0);
    }

    /**
     * Find the Button to perform action over there.
     * @param button name of the Button to click
     * @return webelement
     */
    private WebElement findHEButton(String button){
        return  getDriver().findElement(By.xpath("//button[contains(text(),'"+button+"')]"));
    }

    /**
     * Gets the Button to perform action over there.
     * @param button name of the Button to click
     * @return webelement
     */
    private WebElement clickOnButton(String button){
        return  getDriver().findElement(By.xpath("//span[contains(text(),'"+button+"')]"));
    }

    /**
     * Gets the High School found.
     * @param highSchool the School name
     * @return webelement
     */
    private WebElement findHESchool(String highSchool){
        return getDriver().findElement(By.xpath("//a[contains(text(),'"+highSchool+"')]"));
    }

    public void verifyDisabledDateIsNotClickableInEndDate(String disabledDate){
        String DisabledDate = getSpecificDateForCalendar(disabledDate);
        setDate(DisabledDate, "DisabledDate");
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//div[@class='_20rE_mlucRFZ--zviGCI2N']/b/preceding-sibling::button"),1));
        //verify the dates are not equal
        String date = getStartDateInAgenda().getText();
        String displayingDate = getDisplayingDateInCalendarAgenda(disabledDate);
        Assert.assertTrue("Selected date is not displayed",!date.equals(displayingDate));
        getNavigationBar().goToCommunity();
        waitUntilPageFinishLoading();
    }

    public String getSpecificDateForCalendar(String addDays) {
        String DATE_FORMAT_NOW = "MMMM d yyyy";
        Calendar cal = Calendar.getInstance();
        int days = Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }
    public String getDisplayingDateInCalendarAgenda(String addDays) {
        String DATE_FORMAT_NOW = "MM/d/yyyy";
        Calendar cal = Calendar.getInstance();
        int days = Integer.parseInt(addDays);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        String currentDate = sdf.format(cal.getTime());
        return currentDate;
    }

    /**
     *
     * navigate to recommendations page
     */
    public void verifyRecommendatiosPage(){
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        Assert.assertTrue("",getRecommendationsBtn().isDisplayed());
        getRecommendationsBtn().click();
    }

    /**
     * search school in recommendation page
     * @param County : ex - West
     */
    public void searchforRecommendationsPage(String County) {
        getNavigationBar().goToRepVisits();
        getRecommendationsBtn().click();
        getRecommendationsSearchbox().sendKeys(County);
    }

    /**
     *
     * verifying school details in recommendation tab
     * @param schoolName : passing school name ex: Lakota West High School
     * @param dataTable : dataTable values : High School Name, Recommendation Score, High School Type, College Going Rate, Senior Class Size
     */
    public void verifyDetailsofRecommendation(String schoolName,DataTable dataTable) {
        List<Map<String, String>> entities = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> school : entities) {
            for (String key : school.keySet()) {
                switch (key) {
                    case "High School Name":
                        String header = getDriver().findElement(By.xpath("//a[contains(text(),'" + school.get(key) + "')]//ancestor::tr//td[@class='four wide']//a")).getText();
                        Assert.assertTrue("High School name was not found in header.", header.contains(school.get(key)));
                        break;
                    case "Recommendation Score":
                        String recommendation_score = getDriver().findElement(By.xpath("//td/a[text()='" + schoolName + "']/ancestor::tr/td//div[@class='_8vbyq71MPONij-xyBJp-w']/span")).getText();
                        Assert.assertTrue("Recommendation Score was not found in header.", recommendation_score.contains(school.get(key)));
                        break;
                    case "High School Type":
                        String school_type = getDriver().findElement(By.xpath("//td/a[text()='" + schoolName + "']/ancestor::tr/td[@class='one wide']/div")).getText();
                        Assert.assertTrue("High School Type was not found in header.", school_type.contains(school.get(key)));
                        break;
                    case "College Going Rate":
                        String college_rate = getDriver().findElement(By.xpath("//td/a[text()='" + schoolName + "']/ancestor::tr/td[@class='three wide']/div")).getText();
                        Assert.assertTrue("College Going Rate was not found in header.", college_rate.contains(school.get(key)));
                        break;
                    case "Senior Class Size":
                        String class_size = getDriver().findElement(By.xpath("//td/a[text()='" + schoolName + "']/ancestor::tr/td[5]/div")).getText();
                        Assert.assertTrue("Senior Class Size was not found in header.", class_size.contains(school.get(key)));
                        break;
                }
            }
        }
    }

    /**
     *
     * @param option : ex - Add To Travel Plan
     * @param school : ex - Lakota West High School
     */
    public void verifyAvailability(String option,String school) {
        Assert.assertTrue("school is not displayed",driver.findElement(By.xpath("//a[text()='Lakota West High School']")).isDisplayed());
        if(option.equals("Add To Travel Plan")) {
                List<WebElement> addToTravelPlan = driver.findElements(By.xpath("//a[text()='"+school+"']/../following-sibling::td/div/button/span[text()='Add To Travel Plan']"));
                if(addToTravelPlan.size()==1) {
                    driver.findElement(By.xpath("//a[text()='"+school+"']/../following-sibling::td/div/button/span[text()='Add To Travel Plan']")).click();
                }else {
                    Assert.assertTrue("School is not added with Travel plan",addToTravelPlan.size()==0);
                }
        }else if(option.equals("This school isnt using RepVisits yet")) {
            List<WebElement> repVistsAccess = driver.findElements(By.xpath("//a[text()='"+school+"']//../following-sibling::td/div/span[text()='This school isnt using RepVisits yet']"));
            if(repVistsAccess.size()==1)
                Assert.assertTrue("'This school isnt using RepVisits yet' text is not displayed", driver.findElement(By.xpath("//a[text()='"+school+"']//../following-sibling::td/div/span[text()='This school isnt using RepVisits yet']")).isDisplayed());
        }else if(option.equals("View Availability")) {
            List<WebElement> viewAvailability = driver.findElements(By.xpath("//a[text()='"+school+"']/../following-sibling::td/div/button/span[text()='View Availability']"));
            Assert.assertTrue("View Availability button is not displayed",viewAvailability.size()==1);
        }
    }

    /**
     * verifying view availability close button
     * @param school : school name (ex: Lakota West High School)
     */
    public void verifyViewAvailabilityCloseButton(String school){
        driver.findElement(By.xpath("//a[text()='"+school+"']/../following-sibling::td/div/button/span[text()='View Availability']")).click();
        Assert.assertTrue("Repvisits Availability popup is not displayed",viewAvailabilityPopUp().isDisplayed());
        closeButtonRepvisistsAvailability().click();
    }

    private void selectMoreResultsInSearchAndSchedule(){
        while(getMoreResultsButton().isDisplayed()){
            getMoreResultsButton().click();
            waitUntilPageFinishLoading();
        }
        waitUntilPageFinishLoading();
    }
    /*
       verifying a travel plan with the School Name and deleting it
     */
    public void verifyTravelPlanAndDelete(String school){
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//h1/span[text()='Travel Plan']")));
        List<WebElement> ele=allSchools(school);
        if(!ele.isEmpty()){
            button(By.xpath(String.format(".//div/div/h3[text()='%s']/ancestor::div[@class='item']//span[text()='Remove']"
                    ,school))).click();
            button("YES, REMOVE").click();
            waitUntil(ExpectedConditions.numberOfElementsToBe(successMessage(),1));
            waitUntil(ExpectedConditions.numberOfElementsToBe(successMessage(),0));
        }
        waitForUITransition();
        waitForUITransition();

    }

    public void accessViewDetailsPageforFair(String fairNametoClickViewDetails){
        getNavigationBar().goToRepVisits();
//        waitUntilPageFinishLoading();
        link("College Fairs").click();
        waitUntilPageFinishLoading();
        while (link("View More Upcoming Events").isDisplayed()){
            link("View More Upcoming Events").click();
//            waitUntilPageFinishLoading();
        }
        getDriver().findElement(By.xpath("//table[@class='ui unstackable table']//tbody//tr/td[text()='"+FairName+"']/parent::tr/td/a[span='View Details']")).click();

    }

    /**
     * Vrifies if the given button is displayed for the travel plan schools
     * @param buttonText
     * @param status
     * @param appointmentsLabel
     */
    public void verifyViewButtonForTravelPlanSchools(String buttonText, String status, String appointmentsLabel){
        navigateToRepVisitsSection("Travel Plan");
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//h1/span[text()='Travel Plan']")));
        List<WebElement> schoolRow = getDriver().findElements(By.xpath(String.format(
                "//span[text()='%s']/ancestor::div[@class='row _2Hy63yUH9iXIx771rmjucr']",appointmentsLabel)));
        for(WebElement school:schoolRow){
            if(status.equalsIgnoreCase("is displayed")){
                Assert.assertTrue(String.format("The %s button is not displayed for an school with %s",
                        buttonText,appointmentsLabel),school.findElement(By.xpath(String.format(
                        "descendant::span[text()='%s']",buttonText))).isDisplayed());

            } else{
                if(status.equalsIgnoreCase("is not displayed")){
                    setImplicitWaitTimeout(2);
                    softly().assertThat(school.findElements(By.xpath(String.format("descendant::span[text()='%s']",buttonText))).size()).as("Add to Travel Plan button (should not display)").isEqualTo(0);
                    //Assert.assertTrue(String.format("The %s button is displayed for an school with %s", buttonText,appointmentsLabel),school.findElements(By.xpath(String.format("descendant::span[text()='%s']",buttonText))).size()==0);
                    resetImplicitWaitTimeout();
                } else{
                    Assert.fail("The status of the button is not correct");
                }
            }
        }
    }

    /**
     * Verifies the appointments in travel plan page
     * @param type
     */
    public void verifySchoolAppointmentsInTravelPlan(String type){
        List<WebElement> appointmentLabels = null;
        switch (type.toLowerCase()){
            case "past":
                appointmentLabels = getPastAppointmentsLabelsInTravelPlan();
                break;
            case "upcoming":
                appointmentLabels = getUpcomingAppointmentsLabelsInTravelPlan();
        }
        for(WebElement appointmentLabel : appointmentLabels){
            List<WebElement> appointments = appointmentLabel.findElements(
                    By.xpath("//ancestor::div[@role='listitem']/following-sibling::div[@role='listitem']/div"));
            Assert.assertTrue("There is not any appoiment for past/upcoming appointments",
                    appointmentLabels.size()>0);
        }
    }

    /**
     * Verifies if the share claendar link is displayed
     */
    public void verifyShareCalendarsLinkIsDisplayed(){
        navigateToRepVisitsSection("Calendar");
        Assert.assertTrue("The share clanedars link is nor displayed", getShareCalendarsLink().isDisplayed());
    }

    /**
     * Verifies if share your calendar modal is displayed
     */
    public void verifyShareYourCalendarModalIsDisplayed(){
        navigateToRepVisitsSection("Calendar");
        getShareCalendarsLink().click();
        waitUntil(ExpectedConditions.visibilityOf(getShareYourCalendarLabel()));
        Assert.assertTrue("Share your calendar modal was not displayed", getShareYourCalendarLabel()
                .isDisplayed());
        getCloseShareYourCalendarButton().click();
    }

    public WebElement schoolInVisits(String school) {
        return getDriver().findElement(schoolInVisitsLocator(school));
    }

    private By schoolInVisitsLocator(String school){
        return By.xpath("//div/h3/a[text()='"+school+"']");
    }

    public By availabilityButtonLocator(String visitDate, String time){
        return By.xpath("//span[text()='"+visitDate+"']/parent::th/ancestor::thead/following-sibling::tbody/tr[1]//td//div/button[text()='"+time+"']");
    }

    public WebElement avialabilityButton(String visitDate,String time){
        return getDriver().findElement(availabilityButtonLocator(visitDate, time));
    }
  
    public void verifyAndSelectAppointmentInCalendarPage(String school,String time,String date,String option){
        String startTime = "";
        if(option.equals("Scheduled")) {
            startTime = getVisitStartTimeInCalendar();
        }else if(option.equals("ReScheduled")){
            startTime = getRescheduledVisitStartTimeInCalendar();
        }
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        calendar().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.visibilityOf(currentMonthInCalendarPage()),10);
        collegeFairTextBoxInCalendarPage().click();
        String month = month(date);
        String currentMonth = currentMonthInCalendarPage().getText();
        String selectMonth[] = currentMonth.split(" ");
        String Month = selectMonth[0];
        while (!month.equals(Month)) {
            nextMonthButton().click();
            waitUntilPageFinishLoading();
            waitUntil(ExpectedConditions.visibilityOf(currentMonthInCalendarPage()),10);
            currentMonth = currentMonthInCalendarPage().getText();
            selectMonth = currentMonth.split(" ");
            Month = selectMonth[0];
        }
        if (calendarAppointments(startTime,school).size() == 1) {
            selectAppointmentInCalendar(startTime, school);
        } else if (calendarAppointments(startTime,school).size() == 0) {
            int appointment = getAppointmentFromCalendar(startTime, school);
            if (appointment == 0) {
                startTime = getCalendarStartTime();
                if (calendarAppointments(startTime,school).size() == 1) {
                    selectAppointmentInCalendar(startTime, school);
                } else if (calendarAppointments(startTime,school).size() == 0) {
                    appointment = getAppointmentFromCalendar(startTime, school);
                    if(appointment==1){
                        selectAppointmentInCalendar(startTime, school);
                    }else {
                        Assert.fail("Appointment is not displayed");
                    }
                }
            }else if(appointment == 1){
                selectAppointmentInCalendar(startTime, school);
            }
        }else {
            Assert.fail("Appointment is not displayed");
        }
    }

    public void verifyAvailabilitySlotInCommunitySideBar(String startDate){
        int date = Integer.parseInt(startDate);
        String gotoDate = getSpecificDate(date,"MMMM d yyyy");
        setDate(gotoDate, "Go to date");
        waitUntil(ExpectedConditions.visibilityOfElementLocated(availabilityButtonInCommunity(StartTime)));
        Assert.assertTrue("Availability slot is not displayed",getDriver().findElement(availabilityButtonInCommunity(StartTime)).isDisplayed());
    }

    public void verifyAvailabilitySlotIsClickable(){
        getDriver().findElement(availabilityButtonInCommunity(StartTime)).click();
        Assert.assertTrue("Availability is not clicked",visitRequestButton().isDisplayed());
    }

    public void verifyAppointmentDateIsDisplayingInSchedulePopup(String startDate){
        int date = Integer.parseInt(startDate);
        String getDate = getSpecificDate(date,"EEEE, MMMM d, yyyy");
        waitUntil(ExpectedConditions.visibilityOfElementLocated(dateInVisitSchedulePopup(getDate)));
        Assert.assertTrue("Date is not displayed in visit schedule popup", getDriver().findElement(dateInVisitSchedulePopup(getDate)).isDisplayed());
    }

    public void verifyStartAndEndTimeInSearchAndSchedulePopup(String startTime,String endTime){
        Assert.assertTrue("Start and end time is not displayed",startAndEndTimeInSchedulePopup(StartTime,endTime).isDisplayed());
    }

    public void verifyTimeZoneInSearchAndSchedulePopup(){
        String timeZone = getTimeZone().getText();
        Assert.assertTrue("Time zone is not displayed",timeZone.contains("EST")||timeZone.contains("EDT"));
    }

    public void verifyHSNameInVisitSchedulePopup(String school){
        Assert.assertTrue("School name is not displayed",schoolInSchedulePopup(school).size()==1);
    }

    public void verifySubmitButtonInVisitSchedulePopup(String submit){
        Assert.assertTrue("Submit button is not displayed",requestButton(submit).isDisplayed());
    }

    public void closeSearchAndSchedulePopup(String cancel){
        Assert.assertTrue("Cancel button is not displayed",requestButton(cancel).isDisplayed());
        requestButton(cancel).click();
        waitUntil(ExpectedConditions.visibilityOf(goToDate()));
        Assert.assertTrue("Cancel button is not clicked",goToDate().isDisplayed());
    }

    public void selectSubmitButtonInSearchAndSchedulePopup(String submit){
        requestButton(submit).click();
    }

    public void verifySuccessMessageInSearchAndSchedulePage(String successMessage){
        waitUntil(ExpectedConditions.visibilityOfElementLocated(successMessage()));
        String getMessage[] = successMessage.split("!");
        String message = getMessage().getText();
        String actualValue[] = message.split("!");
        softly().assertThat(getMessage[0]).as("Message is not equal").isEqualTo(actualValue[0]);
        softly().assertThat(getMessage[1]).as("Message is not equal").isEqualTo(actualValue[1]);
        waitUntil(ExpectedConditions.numberOfElementsToBe(successMessage(),0));
    }

    public void loggingAnotherAccount(String usertype){
        getDriver().findElement(By.linkText("Terms of Service")).click();
        waitUntilPageFinishLoading();
        String currentWindow = getDriver().getWindowHandle();
        String newWindow = null;
        Set<String> windows = getDriver().getWindowHandles();
        if(windows.size()>1){
            for (String thisWindow : windows) {
                if (!thisWindow.equals(currentWindow)){
                    newWindow = thisWindow;
                }
            }
            waitForUITransition();
            getDriver().switchTo().window(newWindow);
        }
        String username = GetProperties.get("he."+ usertype + ".username");
        String password = GetProperties.get("he."+ usertype + ".password");
        login(username, password);
    }

    public void switchToAnotherHEuserTab(){
        String currentWindow = getDriver().getWindowHandle();
        String newWindow = null;
        Set<String> windows = getDriver().getWindowHandles();
        if(windows.size()>1){
            for (String thisWindow : windows) {
                if (!thisWindow.equals(currentWindow)){
                    newWindow = thisWindow;
                }
            }
            waitForUITransition();
            getDriver().close();
            getDriver().switchTo().window(newWindow);
        }
    }

    public void verifyNegativeMessageIsDisplaying(String negativeMessage){
        waitUntil(ExpectedConditions.visibilityOfElementLocated(negativeMessage(negativeMessage)));
        Assert.assertTrue("Negative message is not displaying",getDriver().findElement(negativeMessage(negativeMessage)).isDisplayed());
    }

    public void verifyPendingVisitInCalendar(String university, String date, String option){
        String startTime = "";
        if (option.equals("Scheduled")) {
            startTime = getVisitStartTimeInCalendar();
        } else if (option.equals("ReScheduled")) {
            startTime = getRescheduledVisitStartTimeInCalendar();
        }
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        calendar().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button[@title='Day']"), 1));
//Pending check box is not checked
//visit confirmed check box is checked
        visitCheckBoxInCalendarPage().click();
        collegeFairTextBoxInCalendarPage().click();
        String month = month(date);
        String currentMonth = currentMonthInCalendarPage().getText();
        String selectMonth[] = currentMonth.split(" ");
        String Month = selectMonth[0];
        while (!month.equals(Month)) {
            nextMonthButton().click();
            waitUntilPageFinishLoading();
            waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button[@title='Day']"), 1));
            currentMonth = currentMonthInCalendarPage().getText();
            selectMonth = currentMonth.split(" ");
            Month = selectMonth[0];
        }
        if (calendarAppointments(startTime, university).size() == 1) {
            verifyAppointmentInCalendar(startTime, university);
        } else if (calendarAppointments(startTime, university).size() == 0) {
            int appointment = getAppointmentFromCalendar(startTime, university);
            if (appointment == 0) {
                startTime = getCalendarStartTime();
                if (calendarAppointments(startTime, university).size() == 1) {
                    verifyAppointmentInCalendar(startTime, university);
                } else if (calendarAppointments(startTime, university).size() == 0) {
                    appointment = getAppointmentFromCalendar(startTime, university);
                    if (appointment == 1) {
                        verifyAppointmentInCalendar(startTime, university);
                    } else {
                        Assert.fail("Appointment is not displayed");
                    }
                }
            } else if (appointment == 1) {
                verifyAppointmentInCalendar(startTime, university);
            }
        } else {
            Assert.fail("Appointment is not displayed");
        }
    }

    public void login(String username, String password) {
        openLoginPage(GetProperties.get("he.app.url"));
        loginActions(username, password);
    }

    private void loginActions(String username, String password){
        logger.info("Login into the HE app");
        usernameTextbox().sendKeys(username);
        logger.info("Using " + username + " as username");
        passwordTextbox().sendKeys(password);
        logger.info("Using " + password + " as password");
        loginButton().click();
        logger.info("Clicked the login button");
        //Commenting the below line to increase the performance
        //waitUntilPageFinishLoading();
        waitForUITransition();
        List<WebElement> errorMessage = driver.findElements(By.cssSelector("div[class='ui negative message']"));
        if (errorMessage.size()==1){
            logger.info("Login failed. Invalid user or password.");
        }else {
            waitUntilElementExists(link(By.id("user-dropdown")));
            //Commenting the below line to increase the performance
            waitForUITransition();
        }
    }

    public void verifyScheduledVisitInCalendar(String university, String date, String option){
        String startTime = "";
        if (option.equals("Scheduled")) {
            startTime = getVisitStartTimeInCalendar();
        } else if (option.equals("ReScheduled")) {
            startTime = getRescheduledVisitStartTimeInCalendar();
        }
        getNavigationBar().goToRepVisits();
        waitUntilPageFinishLoading();
        calendar().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button[@title='Day']"), 1));
//Pending check box is checked
//Visits Confirmed check box is not checked
        pendingCheckBoxInCalendarPage().click();
        collegeFairTextBoxInCalendarPage().click();
        String month = month(date);
        String currentMonth = currentMonthInCalendarPage().getText();
        String selectMonth[] = currentMonth.split(" ");
        String Month = selectMonth[0];
        while (!month.equals(Month)) {
            nextMonthButton().click();
            waitUntilPageFinishLoading();
            waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//button[@title='Day']"), 1));
            currentMonth = currentMonthInCalendarPage().getText();
            selectMonth = currentMonth.split(" ");
            Month = selectMonth[0];
        }
        if (calendarAppointments(startTime, university).size() == 1) {
            verifyAppointmentInCalendar(startTime, university);
        } else if (calendarAppointments(startTime, university).size() == 0) {
            int appointment = getAppointmentFromCalendar(startTime, university);
            if (appointment == 0) {
                startTime = getCalendarStartTime();
                if (calendarAppointments(startTime, university).size() == 1) {
                    verifyAppointmentInCalendar(startTime, university);
                } else if (calendarAppointments(startTime, university).size() == 0) {
                    appointment = getAppointmentFromCalendar(startTime, university);
                    if (appointment == 1) {
                        verifyAppointmentInCalendar(startTime, university);
                    } else {
                        Assert.fail("Appointment is not displayed");
                    }
                }
            } else if (appointment == 1) {
                verifyAppointmentInCalendar(startTime, university);
            }
        } else {
            Assert.fail("Appointment is not displayed");
        }
    }

    public void clickCancelButtonInCommunityAvailabilityPopup(String cancelButon){
        Assert.assertTrue("Cancel button is not displayed",requestButton(cancelButon).isDisplayed());
        requestButton(cancelButon).click();
    }

    public void closeCommunityAvailability(){
        availabilityCloseButton().click();
        waitUntilPageFinishLoading();
    }

    private void openLoginPage(String url) {
        load(url);
        // If a previous test fails, we'll still have an open session.  Log out first.
        if (button(By.id("user-dropdown")).isDisplayed()) {
            button(By.id("user-dropdown")).click();
            button(By.id("user-dropdown-signout")).click();
            waitUntilPageFinishLoading();
        }
        //No longer needed
        //link("Go To Authorized Page").click();
    }

    private void clickShowMoreButton(){
        if(showMore().size()==1){
            showMoreButton().click();
            waitForUITransition();
        }
    }
    //Locators
    private WebElement staffForReassign(){
        waitUntilPageFinishLoading();
        return  getDriver().findElement(By.cssSelector("div[role='alert']"));
    }

    private WebElement agendaIsDisplayed(){
        return getDriver().findElement(By.cssSelector("div[class='_2gJHeLgeouIqly4xt-Bv2C']"));
    }

    private WebElement accountSettings(String accountSettings)
    {
        WebElement label= getDriver().findElement(By.xpath("//span[text()='"+accountSettings+"']"));
        return  label;
    }
    private WebElement search(){
        WebElement search=getDriver().findElement(By.cssSelector("button[class='ui icon button _3pWea2IV4hoAzTQ12mEux-']"));
        waitUntilElementExists(search);
        return  search;
    }
    private WebElement goToDate()
    {
        WebElement goToDate=getDriver().findElement(By.xpath("//button[text()='Go To Date']"));
        waitUntilElementExists(goToDate);
        return  goToDate;
    }
    private WebElement getOverviewBtn() {
        return getDriver().findElement(By.xpath("//a[contains(@class, 'menu-link')]/span[text()='Overview']"));
    }
    private WebElement travelPlanSeeDetailsLink() {
        return link("See details »");
    }
    private WebElement getCalendarBtn() {
        return link("Calendar");
    }

    private WebElement getContactsBtn() {
        return getDriver().findElement(By.xpath("//div[contains(@class,'pusher dimmed-opacity')]//span[text()='Contacts']"));
    }

    /**
     *
     * @return : recommendation button in repvisits page
     */
    private WebElement getRecommendationsBtn() {return driver.findElement(By.xpath("//ul[@class='ui huge pointing secondary stackable hidden-mobile hidden-tablet _3k81ACwPvWfJIsP_32h5Yh menu']/li//span[text()='Recommendations']"));}
    private WebElement getNotificationsBtn() {
        return link("Notifications");
    }

    private WebElement getSearchBox() { return textbox("Search for a school...");}
    protected WebElement getVisitsFeedbackBtn() {return getDriver().findElement(By.xpath("//a[@class='_3tCrfAwfbPaYbACR-fQgum']/span[text()='Visit Feedback']")); }
    private WebElement getSearchAndScheduleSearchBox(){ return textbox("Search for a school..."); }
    //private WebElement getSearchBox() { return textbox("Enter a school name or location");}
    private WebElement getSearchBoxforContact() { return getDriver().findElement(By.name("contacts-search"));}
    private WebElement getSearchButton() { return getDriver().findElement(By.xpath("//button[@class='ui icon button _3pWea2IV4hoAzTQ12mEux-']"));}
    private WebElement getMapButton() { return getDriver().findElement(By.cssSelector("[class='map outline icon']"));}
    private WebElement getComingSoonMessageInOverviewPage(){ return getDriver().findElement(By.className("_9SnX9M6C12WsFrvkMMEZR")); }
    private By checkVisitAvailabilityButtonLocator(){return By.xpath("//a[text() = 'Check RepVisits Availability']");}
    private WebElement getCheckRepVisitsAvailabilityButton(){ return getDriver().findElement(checkVisitAvailabilityButtonLocator()); }
    private WebElement getRepVisitsAvailabilitySidebar(){ return getDriver().findElement(By.className("_36B3QS_3-4bR8tfro5jydy")); }
    private By getRepVisitsAvailabilityLabeLocator(){
        return By.xpath("//span[text()='Repvisits Availability']");
    }
    private WebElement saveChanges(){WebElement button=button("Save Changes"); return  button; }
    private WebElement userDropdown() {
        WebElement dropdown=getDriver().findElement(By.id("user-dropdown"));
        return  dropdown;
    }
    private WebElement getAccoutSettingsBtn() { return getDriver().findElement(By.xpath("//span[text()='Account Settings']")); }
    private WebElement getInstitutionProfileBtn() { return getDriver().findElement(By.xpath("//span[text()='Institution Profile']")); }
    private WebElement getYourProfileBtn() { return getDriver().findElement(By.xpath("//span[text()='Your Profile']")); }
    private WebElement signOut() {
        WebElement signOut=getDriver().findElement(By.xpath("//span[text()='Sign Out']"));
        return  signOut;
    }
    private WebElement loggedInText() {
        WebElement text=getDriver().findElement(By.xpath("//span[contains(text(),'Logged in as')]"));
        return  text;
    }
    private WebElement userProfilePage() {
        WebElement profile=getDriver().findElement(By.xpath("//a[@class='active' and text()='Profile']"));
        return  profile;
    }
    private WebElement institutionProfilePage() {
        WebElement institution=getDriver().findElement(By.xpath("//a[@class='active' and text()='Institution']"));
        return  institution;
    }
    private WebElement adminLogin() {
        WebElement admin=getDriver().findElement(By.xpath("//span[contains(text(),'Logged in as')]/span[text()='ADMIN']"));
        return admin;
    }
    private WebElement nonAdminLogin() {
        WebElement nonAdmin=getDriver().findElement(By.xpath("//span[contains(text(),'Logged in as')]"));
        return nonAdmin;
    }
    private WebElement helpNavDropdown() {
        WebElement help=getDriver().findElement(By.id("helpNav-dropdown"));
        return  help;
    }
    private  WebElement logo() {
        WebElement Logo=getDriver().findElement(By.xpath("//div/a[@class='logo']"));
        return Logo;
    }
    private WebElement notificationIconInHelpCentre() {
        WebElement notificationIcon=getDriver().findElement(By.id("notificationsNav"));
        return notificationIcon;
    }
    private WebElement helpCentre()  {
        WebElement helpCentre=getDriver().findElement(By.xpath("//span[text()='Help Center']"));
        return  helpCentre;
    }
    private WebElement contactSupport()  {
        WebElement contactSupport= text("Contact Support");
        return  contactSupport;
    }
    private WebElement getRegistrationButton(String fairName) { return getDriver().findElement(By.xpath("//span[text()='" + fairName + "']/../../div/button")); }
    private WebElement getFairDate() { return getDriver().findElement(By.cssSelector("div.content span")); }
    private WebElement getStartEndTimeAndTimeZone() { return getDriver().findElement(By.xpath("//div[@class='dJgg9PNwgMdtXUrtb_czW content']/div/b[text()]")); }
    private WebElement requestText() { return getDriver().findElement(By.cssSelector("div.ui.modal.transition.visible.active div.content div")); }
    private WebElement submitRequestButton() { return getDriver().findElement(By.xpath("//button[text()='Yes, Submit Request']")); }
    private WebElement cancelButton() { return getDriver().findElement(By.cssSelector("div.actions button.ui.button:not(.primary)")); }
    private WebElement fairsTab() { return getDriver().findElement(By.cssSelector("div.ui.fluid.buttons button:nth-of-type(2)")); }
    private WebElement upperMessage() { return getDriver().findElement(By.cssSelector("div[class='ui small icon success message toast _2Z22tp5KKn_l5Zn5sV3zxY']")); }
    private WebElement rightCalendarRightButton() { return getDriver().findElement(By.cssSelector("button._2UEGkUTszONN0hK0CeHMm0")); }
    private WebElement rightCalendarHeaderDate() { return getDriver().findElement(By.cssSelector("h2.ui.medium.header")); }
    private WebElement rightCalendarLeftButton() { return getDriver().findElement(By.cssSelector("button[title=\"Backwards\"]")); }
    private WebElement getDateCell(String day, String time, int row) { return getDriver().findElement(By.xpath("//h3/a[text()='" + day + "']/../../../following-sibling::div[" + row + "]/div/div[@class='rbc-event']/div/button/h4/span[text()='" + time + "']")); }
    private List<WebElement> quickViewCalendarHeaderDates() { return getDriver().findElements(By.cssSelector("h1.ui.header + div span span span")); }
    private WebElement quickViewRightButton() { return getDriver().findElement(By.cssSelector("button[aria-label=\"Next week\"]")); }
    private List<WebElement> quickViewEventsList() { return getDriver().findElements(By.cssSelector("ul.ui.huge.pointing.secondary + div div._2qvF1GJtxr-YZYY8wYagxl + div div.ui.stackable.grid")); }
    private List<WebElement> quickViewFairsList() { return getDriver().findElements(By.cssSelector("ul.ui.huge.pointing.secondary + div div._2qvF1GJtxr-YZYY8wYagxl + div div.ui.stackable.grid")); }
    private WebElement calendarIcon() { return getDriver().findElement(By.cssSelector("button.ui.tiny.icon.right.floated.right.labeled.button._1alys3gHE0t2ksYSNzWGgY")); }
    private WebElement miniCalendarHeader() { return getDriver().findElement(By.cssSelector("div.DayPicker-Caption")); }
    private WebElement miniCalendarRightButton() { return getDriver().findElement(By.cssSelector("span[aria-label=\"Next Month\"]")); }
    private WebElement miniCalendarLeftButton() { return getDriver().findElement(By.cssSelector("span[aria-label=\"Previous Month\"]")); }
    public WebElement miniCalendarDayCell(String day) { return getDriver().findElement(By.xpath("//div[@class='DayPicker-Week']/div[text()='" + day + "' and @class='DayPicker-Day']")); }
    public String showMoreLink = "a.rbc-show-more";
    private List<WebElement> overlayEventsList() { return getDriver().findElements(By.cssSelector("div.rbc-overlay div.rbc-event")); }
    private WebElement visitsTab() { return getDriver().findElement(By.cssSelector("div.ui.left.attached.button")); }
    private WebElement goToDateButton() { return getDriver().findElement(By.cssSelector("button.ui.right.labeled.small.basic i")); }
    private WebElement searchAndScheduleCalendarIcon() { return getDriver().findElement(By.cssSelector("button.ui.right.labeled.tiny.icon.button i")); }
    private List<WebElement> headerWeekDays() { return getDriver().findElements(By.cssSelector("table.ui.fixed.unstackable.basic.center thead th div span")); }
    private List<WebElement> visitsRows() { return getDriver().findElements(By.cssSelector("table.ui.fixed.unstackable.basic.center tbody tr")); }
    private List<WebElement> eventsRows(String day) { return getDriver().findElements(By.xpath("//div[not(contains(@class, 'rbc-off-range'))][contains(@class,'rbc-date-cell')]/a[text()='" + day + "']/../../following-sibling::div")); }
    protected WebElement getVerticalStaffMembersMenu() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//nav[@label='Representatives']")));
    }
    private WebElement searchTextBox() {
        WebElement textBox= getDriver().findElement(By.cssSelector("input[placeholder='Search for a school...']"));
        return textBox;
    }
    private WebElement searchButton() {
        WebElement button=getDriver().findElement(By.cssSelector("button[class='ui icon button _3pWea2IV4hoAzTQ12mEux-']"));
        return  button;
    }
    private WebElement visit() {
        WebElement visit=getDriver().findElement(By.xpath("//span[text()='Visits']"));
        return  visit;
    }
    private WebElement visitRequestButton() {
        WebElement button=getDriver().findElement(By.xpath("//button[contains(text(),'Yes, Request this time')]"));
        return button;
    }
    private WebElement schoolInSearchAndSchedule(String school)
    {
        WebElement schoolName=getDriver().findElement(By.xpath("//td/a[contains(text(),'"+school+"')]"));
        return schoolName;
    }
    private WebElement currentPasswordInput() {
        WebElement currentPassword=getDriver().findElement(By.id("current-password-input"));
        return  currentPassword;
    }
    private WebElement newPasswordInput() {
        WebElement newPassword=getDriver().findElement(By.id("new-password-input"));
        return  newPassword;
    }
    private WebElement confirmPasswordInput() {
        WebElement confirmPassword=getDriver().findElement(By.id("confirm-password-input"));
        return  confirmPassword;}
    private WebElement passwordErrorMessage() {
        WebElement msg=getDriver().findElement(By.xpath("//span[contains(text(),'The new password failed to satisfy security requirements')]"));
        return  msg;
    }
    private WebElement firstNameTextbox() {
        WebElement text=getDriver().findElement(By.id("user-form-first-name"));
        return  text;
    }
    private WebElement lastNameTextbox() {
        WebElement text=getDriver().findElement(By.id("user-form-last-name"));
        return  text;
    }
    private WebElement eMailTextbox() {
        WebElement text=getDriver().findElement(By.id("user-form-email"));
        return  text;
    }
    private WebElement availabilityButton(String date,String time) {
        WebElement button= getDriver().findElement(By.xpath("//span[text()='"+date+"']/parent::th/ancestor::thead/following-sibling::tbody/tr//td//div/button[text()='"+time+"']"));
        return button;
    }
    public WebElement getFairsButton() {
        return button("Fairs");
    }
    public By getFairsButtonLocator(){
        return By.cssSelector("button[class='ui button lM1ka_IX-p7Hiuh9URqAJ']");
    }
    private WebElement fairName(String  school,String fairName) {
        WebElement fairname=getDriver().findElement(By.xpath("//a/h3[text()='"+school+"']/parent::a/following-sibling::span[text()='"+fairName+"']"));
        return  fairname;
    }
    private WebElement schoolName(String schoolName)
    {
        WebElement schoolDetails = getDriver().findElement(By.xpath("//a/h3[text()='"+schoolName+"']"));
        return  schoolDetails;
    }
    private WebElement registerButton(String fair) {
        WebElement button=  getDriver().findElement(By.xpath("//span[text()='"+fair+"']/parent::div/following-sibling::div/button[text()='Register']"));
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
        WebElement search=getDriver().findElement(By.xpath("//button[@class='ui button']"));
        waitUntilElementExists(search);
        return  search;
    }
    private WebElement goToDate() {
        WebElement goToDate=getDriver().findElement(By.xpath("//button[text()='Go To Date']"));
        waitUntilElementExists(goToDate);
        return  goToDate;
    }*/
    private WebElement requestsubtab() {
        WebElement request=link("Requests");
        return  request;
    }
    private WebElement schoolInRequest(String schoolName) {
        WebElement school=getDriver().findElement(By.xpath("//b[text()='"+schoolName+"']"));
        return school;
    }
    private WebElement saveButton() {
        WebElement button=getDriver().findElement(By.xpath("//button/span[text()='Save']"));
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
        WebElement text= getDriver().findElement(By.cssSelector("input[aria-label='Internal Notes']"));
        return text;
    }
    private WebElement calendar() {
        WebElement navbar=link("Calendar");
        return navbar;
    }
    private WebElement collegeFairTextBoxInCalendarPage() {
        WebElement check= getDriver().findElement(By.xpath("//div/label[text()='College Fair - Confirmed']"));
        return check;
    }
    private WebElement visitCheckBoxInCalendarPage() {
        WebElement check=getDriver().findElement(By.xpath("//div/label[text()='Visits - Confirmed']"));
        return check;
    }
    private WebElement currentMonthInCalendarPage() {
        return getDriver().findElement(By.cssSelector("div[class='three wide column ZfUaDp3-V60qJ8_BTeIi']>h2[class='ui medium header _1ucD2vjQuS9iWHF9uzN__M']"));
    }
    private WebElement nextMonthButton() {
        return getDriver().findElement(By.cssSelector("button[class='ui icon button _38R7SJgG4fJ86m-eLYYZJw']"));
    }
    private String month(String date) {
        String month=getSpecificDate(date);
        String selectMonth[]=month.split(" ");
        String currentMonth=selectMonth[0];
        return  currentMonth;
    }
    private WebElement verifyInternalNotes() {
        WebElement message=getDriver().findElement(By.cssSelector("input[placeholder='Upgrade your account to add custom notes to this visit.']"));
        return message;
    }
    private WebElement visitDetailsLabel() {
        WebElement label=getDriver().findElement(By.xpath("//span[text()='Visit Details']"));
        return label;
    }
    private WebElement contactLabel() {
        WebElement contact=getDriver().findElement(By.xpath("//span[text()='Contact']"));
        return  contact;
    }
    private WebElement instructions() {
        WebElement text=getDriver().findElement(By.xpath("//span[contains(text(),'Instructions from High School')]"));
        return text;
    }
    private WebElement rescheduleVisit() {
        WebElement reschedule=getDriver().findElement(By.cssSelector("a[class='_2SlRxBknbetA9qpiUnR4Pb']"));
        return reschedule;
    }
    private WebElement cancelVisit() {
        WebElement cancelisit=getDriver().findElement(By.xpath("//span[contains(text(),'Cancel This Visit')]"));
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

    public void verifyAddToTravelPlanButtonInHSPage(String option){
        communityFrame();
        Assert.assertTrue(option+" is not displaying.", getAddToTravelPlanButtonInHSPage().isDisplayed());
    }

    /**
     * /**
     * verifying premium feature Header is displaying for limited account in search and schedule page
     * @param premiumText
     */
    public void verifyPremiumFeatureHeaderIsDisplayingInSearchAndSchedule(String premiumText){
        String actualValue = premiumFeatureText().getText();
        softly().assertThat(actualValue).as("Premium text").isEqualTo(premiumText);
    }

    /**
     * verifying lock icon is displaying for limited account in search and schedule page
     * @param lockIcon
     */
    public void verifyLockIconIsDisplayingInSearchAndSchedule(String lockIcon){
        String actualValue = lockIcon().getAttribute("alt");
        softly().assertThat(actualValue).as("Lock icon").isEqualTo(lockIcon);
    }

    /**
     * verifying learn more link is displaying for limited account in search and schedule page
     * @param learnMore
     */
    public void verifyLearnMoreHyperLinkIsDisplayingInSearchAndSchedulePage(String learnMore){
        String actualValue = learnMoreLink().getText();
        softly().assertThat(actualValue).as("Learn more link").isEqualTo(learnMore);
    }

    /**
     * verifying premium feature Header is not displaying for premium account in search and schedule page
     */
    public void verifyPremiumFeatureHeaderIsNotDisplayingInSearchAndSchedule(){
        Assert.assertTrue("Learn more hyper link is displayed",premiumFeature().size()==0);
    }

    /**
     * verifying lock icon is not displaying for premium account in search and schedule page
     */
    public void verifyLockIconIsNotDisplayingInSearchAndSchedule(){
        Assert.assertTrue("Learn more hyper link is displayed",lockicon().size()==0);
    }

    /**
     * verifying learn more link is not displaying for premium account in search and schedule page
     */
    public void verifyLearnMoreHyperLinkIsNotDisplayingInSearchAndSchedulePage(){
        Assert.assertTrue("Learn more hyper link is displayed",learnMore().size()==0);
    }

    /**
     * Gets the searchByLocation textbox in the repvisits>recommendations page
     * @return WebElement
     */
    private WebElement getAddToTravelPlanButtonInHSPage(){ return driver.findElement(By.className("travel-plan-link"));}
    private WebElement getSearchByLocationTextBox(){
        return textbox("Search by location...");
    }
    private WebElement userDropDown(){
        WebElement button=getDriver().findElement(By.id("user-dropdown"));
        return button;
    }
    private WebElement accountSettings(){
        WebElement link=getDriver().findElement(By.xpath("//span[text()='Account Settings']"));
        return link;
    }
    private WebElement institutionNotification(){
        WebElement link=getDriver().findElement(By.xpath("//span[text()='Institution Notifications']"));
        return link;
    }
    private WebElement institutionNotificationText(){
        WebElement text=getDriver().findElement(By.xpath("//span[text()='Institution Notifications']"));
        return text;
    }
    private WebElement navianceActiveMatchText(){
        WebElement text=getDriver().findElement(By.xpath("//span[text()='Naviance ActiveMatch']"));
        return text;
    }
    private WebElement emailTextBox(){
        WebElement textBox;
        try {
            setImplicitWaitTimeout(2);
            textBox=getDriver().findElement(By.id("am_notification_contacts_additional_emails"));
            resetImplicitWaitTimeout();
        } catch (Exception e) {
            resetImplicitWaitTimeout();
            textBox=getDriver().findElement(By.id("user-form-email"));
        }
        return textBox;
    }

    private WebElement checkBoxInAccountSettingsNotification(String value) {
        WebElement checkbox = getDriver().findElement(By.xpath("//label[text()='" + value + "']"));
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
     * Gets the add people outside counselor community label
     * @return
     */
    private WebElement addPeopleOutsideCounselorCommunityLabel(){
        return getDriver().findElement(By.xpath("//label[text()='Add people outside of the Counselor Community.']"));
    }

    /**
     * Gets the web element container of the RepVisits branding header
     * @return Webelement
     */
    private WebElement getRepVisitsBrandingHeaderContainer(){
        WebElement headerContainer = getDriver().findElement(By.cssSelector(
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

    /**
     * Gets the search results container element in the search and schedule page
     * @return
     */
    private WebElement getSearchResultContainer(){
        return getDriver().findElement(By.xpath("//div[@class='ui segment _2ayJrz3b13vtCc7KUcHHte']"));
    }

    /**
     * Gets the More results button in the search and schedule page
     * @return
     */
    private WebElement getMoreResultsButton(){
        return button("More Results");
    }

    private WebElement fairNameHeader() { return getDriver().findElement(By.cssSelector("h1.ui.header")); }
    private WebElement fairHSName() { return getDriver().findElement(By.cssSelector("div._3RqdVnu3tniPVn-91Bd61M")); }
    private WebElement fairHSAddress() { return getDriver().findElement(By.cssSelector("div.rvsP8MMShznvvZWePlkUO")); }
    private WebElement fairContactName() { return getDriver().findElement(By.cssSelector("div._3gQNLVW3B3CfdELmCdwdfG")); }
    private WebElement fairContactTitle() { return getDriver().findElement(By.cssSelector("div._25t7gNvT8MVtZfRUq4WSK3")); }
    private WebElement fairContactEmail() { return getDriver().findElement(By.cssSelector("div._38U_qKPRgGTogiYwQidWQu")); }
    private WebElement fairContactPhone() { return getDriver().findElement(By.cssSelector("div._2Yft-cZFY8BFL0J6NPSWna")); }
    private WebElement fairDate() { return getDriver().findElement(By.xpath("//div[@class='_3dJVJHDv8f4yi7W71sBMV7']/div[@class='ui grid']/div[3]/div[1]/div/span")); }
    private WebElement fairStartTime() { return getDriver().findElement(By.xpath("//span[text() = 'Time']/../following-sibling::div/span[1]")); }
    private WebElement fairEndTime() { return getDriver().findElement(By.xpath("//span[text() = 'Time']/../following-sibling::div/span[2]")); }
    private WebElement fairTimeZone() { return getDriver().findElement(By.xpath("//span[text() = 'Time']/../following-sibling::div/span[3]")); }
    private WebElement fairCost() { return getDriver().findElement(By.xpath("//span[text() = 'Cost']/following-sibling::div")); }
    private WebElement fairExpectedStudents() { return getDriver().findElement(By.xpath("//div[@class='_3dJVJHDv8f4yi7W71sBMV7']/div[@class='ui grid']/div[4]/div[2]/div")); }
    private WebElement fairInstructions() { return getDriver().findElement(By.xpath("//div[@class='_3dJVJHDv8f4yi7W71sBMV7']/div[@class='ui grid']/div[5]/div/div[2]")); }
    private WebElement fairCancellationInstructions() { return getDriver().findElement(By.xpath("//div[@class='_3JCQh0qrOlZKBKE8m4D5Iz']/span/span")); }
    private WebElement fairCancelLink() { return getDriver().findElement(By.cssSelector("div._3JCQh0qrOlZKBKE8m4D5Iz button span")); }
    private WebElement fairInternalNotesTextBox() { return getDriver().findElement(By.cssSelector("input[aria-label=\"Internal Notes\"]")); }
    private WebElement fairSaveButton() { return getDriver().findElement(By.cssSelector("button.ui.primary.right.floated.button span")); }
    private WebElement fairSavedConfirmationMessage() { return getDriver().findElement(By.cssSelector("div.content span span")); }
    private WebElement dropdownInSearchAndSchedule(){
        WebElement dropdown = getDriver().findElement(By.xpath("//i[@class='teal caret down small icon']"));
        return dropdown;
    }
    private WebElement upgradePopupCloseButtonInSearchAndScheduleDropdown(){
        return getDriver().findElement(By.xpath("//div[@role='dialog']/i[@class='close icon']"));
    }
    private WebElement noResultsMessageInSearchAndSchedule() {
        return getDriver().findElement(By.xpath("//span[text()='No results found.']"));
    }
    private int searchResultsSize(){
       int size = getDriver().findElements(By.xpath("//tr/td[@class='_2i9Ix-ZCUb0uO32jR3hE3x']")).size();
       return size;
    }
    private WebElement resultCountInSearchResultsPage(){
        return getDriver().findElement(By.xpath("//p[@class='WWSRdogYvrcJkqEg52pv3']//span"));
    }
    /**
     * Gets the upgrade button
     * @return webelement
     */
    private WebElement getUpgradeButton(){
        return getDriver().findElement(By.cssSelector("button[class='ui button _3A-KkdzsiqhORmN0RiEGSO']"));
    }
    private WebElement agendaButton(){
        return getDriver().findElement(By.xpath("//button[@title='Agenda']"));
    }
    private WebElement getStartDateInAgenda(){
        return getDriver().findElement(By.xpath("//button[@class='ui tiny button bne-HEiKl3BvzkB-LIC8M'][1]/b/span"));
    }
    private WebElement requestInformationButton(){
        return getDriver().findElement(By.xpath("//span[text()='Request Information']"));
    }
    private WebElement upgradeMessage(){
        return getDriver().findElement(By.xpath("//span[text()='Unlock powerful features']"));
    }
    private WebElement closeButtonInAgendaUpgradePage(){
        return getDriver().findElement(By.xpath("//div/i[@class='close icon']"));
    }
      private WebElement checkBoxInYourNotification(){
        return getDriver().findElement(By.id("opt_out_availability_emails"));
    }
    private WebElement saveButtonInYourNotification(){
        return getDriver().findElement(By.xpath("//button/span[text()='Save']"));
    }
    private WebElement yourNotification(){
        return getDriver().findElement(By.xpath("//a/span[text()='Your Notifications']"));
    }
    /**
     * Gets the past appointments label in travel plan page
     * @return webelement
     */
    private List<WebElement> getPastAppointmentsLabelsInTravelPlan(){
        return getDriver().findElements(By.xpath("//span[contains(text(),'Past Appointments')]"));
    }

    /**
     * Gets the upcoming appointments label in travel plan page
     * @return webelement
     */
    private List<WebElement> getUpcomingAppointmentsLabelsInTravelPlan(){
        return getDriver().findElements(By.xpath("//span[contains(text(),'Upcoming Appointments')]"));
    }

    /**
     * Gets the share calendars link
     * @return
     */
    private WebElement getShareCalendarsLink(){
        return link("Share calendars");
    }

    /**
     * Gets the share your calendar label
     * @return
     */
    private WebElement getShareYourCalendarLabel(){
        return getDriver().findElement(By.xpath("//div[@class='ui small modal transition visible " +
                "active _56z_iePncfEtW1YuLtMg4']/div/span[text()='Share Your Calendar']"));
    }

    /**
     * Gets the close button to close the share your calendar modal
     * @return
     */
    public WebElement getCloseShareYourCalendarButton(){
        return getDriver().findElement(By.cssSelector("div>i[class='close icon']"));
    }

    /**
     * Get the re assign link
     * @return
     */
    private List<WebElement> getReAssignLink(){
        return getDriver().findElements(By.xpath("//span[text()='Re-assign appointments']"));
    }
      private WebElement newAssigneeButton(){
        return getDriver().findElement(By.xpath("//div[text()='Select new assignee']"));
    }
    private WebElement disabledNewAssigneeDropdown(){
        return getDriver().findElement(By.xpath("//div[@class='ui disabled selection dropdown staffSelect _1fyAdfnHhLDFoE1OCXnbCC' and @aria-disabled='true']"));
    }
    public List<WebElement> getUsers() {
       return getDriver().findElements(By.xpath("//div[@class='menu transition visible']/div"));
    }
     private WebElement selectStaffMemberButton(){
        return getDriver().findElement(By.xpath("//div[text()='Select staff member']"));
    }
    private WebElement showMoreButtonInReassignAppointments(){
        return button("Show More");
    }
    private WebElement submitRequestText(){return text("Yes, Submit Request");}
    private WebElement schedulePopupTextInVisitSchedule(){
        return getDriver().findElement(By.xpath("//div[contains(text(),'Ready to Schedule?')]"));
    }
    private List<WebElement> getTotalCountInNotification(){
        return getDriver().findElements(By.cssSelector("div[class='_12QfCShNjFFA8a-x4K3-yn']>div>div"));
    }
      public WebElement fairname(String schoolName,String Fair){
        return getDriver().findElement(By.xpath("//a/h3[text()='"+schoolName+"']/parent::a/following-sibling::span[text()='"+Fair+"']"));
    }

    public WebElement registerFairs(String fairName){
        return getDriver().findElement(By.xpath("//span[text()='"+fairName+"']/parent::div/following-sibling::div/button[text()='Register']"));
    }

    public WebElement viewFullDetails(String school,String date,String time){
        return driver.findElement(By.xpath("//span[text()='"+school+"']/parent::div/following-sibling::div/span[text()='"+date+"']/parent::div[text()='"+time+"']/following-sibling::div/button/span[text()='View Full Details']"));
    }

    public By internalNodeTextBox(){
        return By.cssSelector("input[aria-label='Internal Notes']");
    }

    public WebElement verifySchoolInNotificationTab(String school){
        return getDriver().findElement(By.xpath("//span[text()='"+school+"']"));
    }

    public WebElement verifyDateAndTimeInNotificationTab(String school,String date,String time){
        return getDriver().findElement(By.xpath("//span[text()='"+school+"']/parent::div/following-sibling::div/span[text()='"+date+"']/parent::div[text()='"+time+"']"));
    }

    public WebElement schoolInVisitSchedule(String school){
        return getDriver().findElement(By.xpath("//div[contains(text(),'Do you want to schedule a visit with "+school+" from')]"));
    }

    public WebElement timeInVisitSchedule(String school,String startTime,String endTime){
        return getDriver().findElement(By.xpath("//div[contains(text(),'Do you want to schedule a visit with "+school+" from')]/b[contains(text(),'"+startTime+"-"+endTime+"')]"));
    }

    public WebElement availabilityButton(String visitDate){
        return getDriver().findElement(By.xpath("(//span[text()='"+visitDate+"']/parent::th/ancestor::thead/following-sibling::tbody/tr/td)[3]/div/div/button[text()='"+pageObjects.HS.repVisitsPage.RepVisitsPageImpl.StartTime+"']"));
    }

    public By school(String school){
        return By.xpath("//td/h3/a[contains(text(),'"+school+"')]");
    }

    public WebElement verifySchool(String school){
        return getDriver().findElement(By.xpath("//td/h3/a[contains(text(),'"+school+"')]"));
    }

    public WebElement showMoreButton(String option){
        WebElement text=getDriver().findElement(By.xpath("//span[text()='"+option+"']"));
        return text;
    }
    private List<WebElement> getDateInRequestTab(){return driver.findElements(By.cssSelector("div[class='row _7a-AX8OE6ILreCgE8P27C']+div>div>div+div>span"));}
  
    private String getCalendarStartTime(){
        String startTime = "";
        String[] time=pageObjects.HS.repVisitsPage.RepVisitsPageImpl.StartTime.split("am");
        String hour[]=time[0].split(":");
        if(hour[0].equals("12"))
            startTime = "00"+":"+hour[1];
        return startTime;
    }
    private By cancelVisitButton(){return By.xpath("//button/span[text()='Cancel This Visit']");}
    private WebElement cancelThisVisitButton(){return getDriver().findElement( By.xpath("//button/span[text()='Cancel This Visit']"));}
    private WebElement visitCancelButton(){return button("Yes, Cancel Visit");}
    private WebElement cancelMessageTextBox(){return getDriver().findElement(By.id("cancel-message"));}
    private By cancelMessageText(){return By.id("cancel-message");}
    private WebElement saveButtonInCalendarPopup(){return getDriver().findElement(By.xpath("//button/span[text()='Save']"));}
    public List<WebElement> calendarAppointments(String startTime,String school){return getDriver().findElements(By.xpath("//span[text()='" + school + "']/following-sibling::span[text()='" + startTime + "']"));}
    public WebElement calendarAppointment(String startTime,String school){return getDriver().findElement(By.xpath("//span[text()='" + school + "']/following-sibling::span[text()='" + startTime + "']"));}
    public By visitDetailsText(){return By.xpath("//span[text()='Visit Details']");}
    public WebElement userText(String user){return getDriver().findElement(By.xpath("//div[text()='"+user+"']"));}
    public WebElement eMail(String eMail){return getDriver().findElement(By.xpath("//div[text()='"+eMail+"']"));}
    public WebElement contactNo(String contactNo){return getDriver().findElement(By.xpath("//div[text()='"+contactNo+"']"));}
    public WebElement schoolNameText(String school){return getDriver().findElement(By.xpath("//div[contains(text(),'"+school+"')]"));}
    public WebElement address(String school,String hsAddress){return getDriver().findElement(By.xpath("//div[contains(text(),'"+school+"')]/following-sibling::div[contains(text(),'"+hsAddress+"')]"));}
    public WebElement date(String date){return getDriver().findElement(By.xpath("//span[text()='"+date+"']"));}
    public WebElement time(String startTime,String endTime){return getDriver().findElement(By.xpath("//div/span[contains(text(),'"+startTime+"')]/following-sibling::span[contains(text(),'"+endTime+"')]"));}
    private By schoolNameTextInSchedule(String school){
        return By.xpath("//td/a[contains(text(),'"+school+"')]");
    }
    private WebElement readyToScheduleText(){
        return getDriver().findElement(By.xpath("//div[contains(text(),'Ready to Schedule?')]"));
    }
    private WebElement scheduleTextWithSchool(String school){
        return getDriver().findElement(By.xpath("//div[contains(text(),'Do you want to schedule a visit with "+school+" from')]"));
    }
    private WebElement scheduleTextWithTime(String school,String startTime,String endTime){
        return getDriver().findElement(By.xpath("//div[contains(text(),'Do you want to schedule a visit with "+school+" from')]/b[contains(text(),'"+startTime+"-"+endTime+"')]"));
    }
    private List<WebElement> getMonthRow(){return getDriver().findElements(By.cssSelector("div.rbc-month-row"));}
    private List<WebElement> showMore(int index){return getDriver().findElements(By.cssSelector("div[class='rbc-month-row']:nth-of-type("+index+")>div[class='rbc-row-content']>div[class='rbc-row']:nth-of-type(4)>div>a"));}
    private List<WebElement> showMoreButton(int firstIndex,int secondIndex){return getDriver().findElements(By.cssSelector("div[class='rbc-month-row']:nth-of-type("+firstIndex+")>div[class='rbc-row-content']>div[class='rbc-row']:nth-of-type(4)>div:nth-of-type("+secondIndex+")>a"));}
    private WebElement selectShowMoreButton(int firstIndex,int secondIndex) {return getDriver().findElement(By.cssSelector("div[class='rbc-month-row']:nth-of-type("+firstIndex+")>div[class='rbc-row-content']>div[class='rbc-row']:nth-of-type(4)>div:nth-of-type("+secondIndex+")>a"));}

    private WebElement getActivityTab(){return link("Activity");}

    private By cityAndStateInRequest(String visitTime,String city,String state,String school){return By.xpath("//div[text()='"+visitTime+"']/preceding-sibling::div[normalize-space(text())='"+city+","+state+"']/preceding-sibling::div/span[text()='"+school+"']");}

    private By cityAndStateInActivity(String visitTime,String city,String state,String school){
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='_3d9aL7nr2h8RrqrSAC7mMk _1JTfN0oSkdjnUDHbAdVRl9']")));
        By element = null;
        if(getDriver().findElements(By.xpath("//div/span[contains(text(),'"+visitTime+"')]/parent::div/preceding-sibling::div[contains(text(),'"+city+"')]/self::div[contains(text(),'"+state+"')]/preceding-sibling::div/b[text()='"+school+"']")).size()>0){
            element = By.xpath("//div/span[contains(text(),'"+visitTime+"')]/parent::div/preceding-sibling::div[contains(text(),'"+city+"')]/self::div[contains(text(),'"+state+"')]/preceding-sibling::div/b[text()='"+school+"']");
        }else if(getDriver().findElements(By.xpath("//div/span[contains(text(),'"+visitTime+"')]/parent::div/preceding-sibling::div[contains(text(),'"+city+"')]/self::div[contains(text(),'"+state+"')]/preceding-sibling::div/span[text()='"+school+"']")).size()>0){
            element = By.xpath("//div/span[contains(text(),'"+visitTime+"')]/parent::div/preceding-sibling::div[contains(text(),'"+city+"')]/self::div[contains(text(),'"+state+"')]/preceding-sibling::div/span[text()='"+school+"']");
        }
        return element;
    }

    private WebElement verifyCityAndStateInRequest(String visitTime,String city,String state,String school){return getDriver().findElement(By.xpath("//div[text()='"+visitTime+"']/preceding-sibling::div[normalize-space(text())='"+city+","+state+"']/preceding-sibling::div/span[text()='"+school+"']"));}

    private WebElement verifyCityAndStateInActivity(String visitTime,String city,String state,String school){
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='_3d9aL7nr2h8RrqrSAC7mMk _1JTfN0oSkdjnUDHbAdVRl9']")));
        WebElement element = null;
        if(getDriver().findElements(By.xpath("//div/span[contains(text(),'"+visitTime+"')]/parent::div/preceding-sibling::div[contains(text(),'"+city+"')]/self::div[contains(text(),'"+state+"')]/preceding-sibling::div/b[text()='"+school+"']")).size()>0){
            element = getDriver().findElement(By.xpath("//div/span[contains(text(),'"+visitTime+"')]/parent::div/preceding-sibling::div[contains(text(),'"+city+"')]/self::div[contains(text(),'"+state+"')]/preceding-sibling::div/b[text()='"+school+"']"));
        }else if(getDriver().findElements(By.xpath("//div/span[contains(text(),'"+visitTime+"')]/parent::div/preceding-sibling::div[contains(text(),'"+city+"')]/self::div[contains(text(),'"+state+"')]/preceding-sibling::div/span[text()='"+school+"']")).size()>0){
            element = getDriver().findElement(By.xpath("//div/span[contains(text(),'"+visitTime+"')]/parent::div/preceding-sibling::div[contains(text(),'"+city+"')]/self::div[contains(text(),'"+state+"')]/preceding-sibling::div/span[text()='"+school+"']"));
        }
        return element;
    }

    private By cityAndStateInRequestforFairs(String city,String state,String school){return By.xpath("//div[normalize-space(text())='"+city+","+state+"']/preceding-sibling::div/span[text()='"+school+"']");}

    private By cityAndStateInActivityforFairs(String city,String state,String school){
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='_3d9aL7nr2h8RrqrSAC7mMk _1JTfN0oSkdjnUDHbAdVRl9']")));
        By element = null;
        if(getDriver().findElements(By.xpath("//div[contains(text(),'"+city+"')]/self::div[contains(text(),'"+state+"')]/preceding-sibling::div/span[text()='"+school+"']")).size()>0){
            element = By.xpath("//div[contains(text(),'"+city+"')]/self::div[contains(text(),'"+state+"')]/preceding-sibling::div/span[text()='"+school+"']");
        }else if(getDriver().findElements(By.xpath("//div[contains(text(),'"+city+"')]/self::div[contains(text(),'"+state+"')]/preceding-sibling::div/b[text()='"+school+"']")).size()>0){
            element = By.xpath("//div[contains(text(),'"+city+"')]/self::div[contains(text(),'"+state+"')]/preceding-sibling::div/b[text()='"+school+"']");
        }
        return element;
    }

    private WebElement verifyCityAndStateInRequestforFairs(String city,String state,String school){return getDriver().findElement(By.xpath("//div[normalize-space(text())='"+city+","+state+"']/preceding-sibling::div/span[text()='"+school+"']"));}

    private WebElement verifyCityAndStateInActivityforFairs(String city,String state,String school){
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='_3d9aL7nr2h8RrqrSAC7mMk _1JTfN0oSkdjnUDHbAdVRl9']")));
        WebElement element = null;
        if(getDriver().findElements(By.xpath("//div[contains(text(),'"+city+"')]/self::div[contains(text(),'"+state+"')]/preceding-sibling::div/b[text()='"+school+"']")).size()>0){
            element = getDriver().findElement(By.xpath("//div[contains(text(),'"+city+"')]/self::div[contains(text(),'"+state+"')]/preceding-sibling::div/b[text()='"+school+"']"));
        }else if(getDriver().findElements(By.xpath("//div[contains(text(),'"+city+"')]/self::div[contains(text(),'"+state+"')]/preceding-sibling::div/span[text()='"+school+"']")).size()>0){
            element = getDriver().findElement(By.xpath("//div[contains(text(),'"+city+"')]/self::div[contains(text(),'"+state+"')]/preceding-sibling::div/span[text()='"+school+"']"));
        }
        return element;
    }

    private WebElement viewDetailsSaveButton(){return getDriver().findElement(By.xpath("//button/span[text()='Save']"));}

    /**
     * Gets the notifications settings updated toast locator
     * @return
     */
    private By notificationsSettingsWereUpdatedToastLocator(){
        return By.xpath("//div/span[@class='LkKQEXqh0w8bxd1kyg0Mq']");
    }
    private By internalNotes(){return By.cssSelector("input[aria-label = 'Internal Notes']");}
    private WebElement internalNotesTextBox(){return driver.findElement(By.cssSelector("input[aria-label = 'Internal Notes']"));}
    private By cancelThisFair(){return By.xpath("//button/span[text()='Cancel This Fair']");}
    private WebElement cancelThisFairButton(){return getDriver().findElement(By.xpath("//button/span[text()='Cancel This Fair']"));}
    private WebElement cancelFairButton(){return button("Yes, Cancel Fair");}
    private By todayButtonInCalendar(){return By.cssSelector("button[title='Today']");}

    private WebElement appointmentSlot(String time,String school){return getDriver().findElement(By.xpath("//span[text()='"+time+"']/preceding-sibling::span[text()='"+school+"']"));}

    private By successMessage(){
        return By.cssSelector("span[class='LkKQEXqh0w8bxd1kyg0Mq']");
    }
    private List<WebElement> negativeMessage(){
        return getDriver().findElements(By.cssSelector("div[class='ui negative message']>div>span"));
    }
    private WebElement getSuccessMessage(){return getDriver().findElement(By.cssSelector("span[class='LkKQEXqh0w8bxd1kyg0Mq']>span"));}
    private WebElement leftSubMenu(String subMenu[],int i){return getDriver().findElement(By.xpath("//a/span[text()='"+subMenu[i]+"']"));}
    private WebElement accountSettingsNonPasswordFields(String fieldDetails[],int i){return getDriver().findElement(By.xpath("//span[text()='"+fieldDetails[i]+"']"));}
    private String loginRowLocator(String loginName) {
        return "//table[@class='ui table']//tbody//tr//td[6]/a[text()='" + loginName + "']";
    }
    private String actionMenuElementLocator (String action) {
        return "//div[@class='visible menu transition']//span[text()='"+action+"']";
    }
    private By calendarDayButton(){return By.cssSelector("button[title='Day']");}

    private WebElement exportButton(){return getDriver().findElement(By.cssSelector("button[title='Export']"));}

    private List<WebElement> cityAndStateInRequestTabforFairs(String city,String state,String school){return getDriver().findElements(By.xpath("//div[normalize-space(text())='"+city+","+state+"']/preceding-sibling::div/span[text()='"+school+"']"));}

    private List<WebElement> cityAndStateInActivityTabforFairs(String city,String state,String school){return getDriver().findElements(By.xpath("//div[normalize-space(text())='"+city+","+state+"']/preceding-sibling::div/span[text()='"+school+"']"));}

    private WebElement showMoreButton(){
        return getDriver().findElement(By.xpath("//span[text()='Show More']"));
    }

    private List<WebElement> showMore(){
        return getDriver().findElements(By.xpath("//span[text()='Show More']"));
    }

    private List<WebElement> cityAndStateInActivityTab(String visitTime,String city,String state,String school){return getDriver().findElements(By.xpath("//div/span[contains(text(),'"+visitTime+"')]/parent::div/preceding-sibling::div[normalize-space(text())='"+city+","+state+"']/preceding-sibling::div/span[text()='"+school+"']"));}

    private List<WebElement> cityAndStateInRequestTab(String visitTime,String city,String state,String school){return getDriver().findElements(By.xpath("//div[text()='"+visitTime+"']/preceding-sibling::div[normalize-space(text())='"+city+","+state+"']/preceding-sibling::div/span[text()='"+school+"']"));}

    private By fairsButton(){return By.xpath("//button/span[text()='Fairs']");}

    private WebElement selectShowMoreButton(int firstIndex,int secondIndex,int rowCount) {return getDriver().findElement(By.cssSelector("div[class='rbc-month-row']:nth-of-type("+firstIndex+")>div[class='rbc-row-content']>div[class='rbc-row']:nth-of-type("+rowCount+")>div:nth-of-type("+secondIndex+")>a.rbc-show-more"));}

    private List<WebElement> showMoreButton(int firstIndex,int secondIndex,int rowCount){return getDriver().findElements(By.cssSelector("div[class='rbc-month-row']:nth-of-type("+firstIndex+")>div[class='rbc-row-content']>div[class='rbc-row']:nth-of-type("+rowCount+")>div:nth-of-type("+secondIndex+")>a.rbc-show-more"));}

    private WebElement verifyVisitDetails(String school){return getDriver().findElement(By.xpath("//div/span[text()='"+StartTime+"']/following-sibling::h3/a[text()='"+school+"']"));}

    private By visitDetails(String school){return By.xpath("//div/span[text()='"+StartTime+"']/following-sibling::h3/a[text()='"+school+"']");}

    private String getVisitStartTimeInCalendar(){
        String[] time=pageObjects.HS.repVisitsPage.RepVisitsPageImpl.StartTime.split("am");
        String startTime=time[0]+"AM";
        return startTime;
    }
    private String getRescheduledVisitStartTimeInCalendar(){
        String[] time=pageObjects.HS.repVisitsPage.RepVisitsPageImpl.RescheduleStartTimeforNewVisit.split("am");
        String startTime=time[0]+"AM";
        return startTime;
    }

    private By schoolInFreemium(String school){return By.xpath("//div[text()='"+school+"']");}

    private WebElement verifySchoolInFreemium(String school){return getDriver().findElement(By.xpath("//div[text()='"+school+"']"));}

    private WebElement circularIconCloseButton(){  return getDriver().findElement(By.xpath("//button[@class='ui circular icon button _1zaSIpaNy8bj4C9yOAOsXw']")); }

    private By successmessage(){return By.xpath("//span[@class='LkKQEXqh0w8bxd1kyg0Mq']/parent::div"); }
  
    private WebElement verifyAnnouncementTitle(String announcementTitle){return getDriver().findElement(By.xpath("//div/b[text()='"+announcementTitle+"']"));}

    private By announcementTitle(String announcementTitle){return By.xpath("//div/b[text()='"+announcementTitle+"']");}

    private WebElement readMoreButton(){return button("Read More");}

    private WebElement announcementDismissButton(){return getDriver().findElement(By.cssSelector("i[class='close icon _3AcltzPxtgX0rUCbxyMhN_']"));}

    private WebElement announcementContent(String content){return getDriver().findElement(By.xpath("//span[text()='"+content+"']"));}

    private List<WebElement> announcementsDismissButton(){return getDriver().findElements(By.cssSelector("i[class='close icon _3AcltzPxtgX0rUCbxyMhN_']"));}

    private List<WebElement> readMorebutton(){return getDriver().findElements(By.xpath("//button[text()='Read More']"));}

    private By dismissButton(){return By.cssSelector("i[class='close icon _3AcltzPxtgX0rUCbxyMhN_']");}
    public List<WebElement> allSchools(String schoolName){
        return driver.findElements(By.xpath("//h3[contains(text(),'"+schoolName+"')]"));
    }

    /**
     * returning premium feature text
     * @return : Web element
     */
    private WebElement premiumFeatureText(){
        return getDriver().findElement(By.xpath("//span[text()='Your Schedule']/following-sibling::span/span"));
    }

    /**
     * returning lock icon text
     * @return : Web element
     */
    private WebElement lockIcon(){
        return getDriver().findElement(By.xpath("//span[text()='Your Schedule']/following-sibling::icon/img"));
    }

    /**
     * returning learn more hyper link
     * @return : Web element
     */
    private WebElement learnMoreLink(){
        return getDriver().findElement(By.cssSelector("button[class='ui button _2rXWwF_Uy39x5eO1MMJqOu']>span>span"));
    }
    /**
     * returning premium feature text
     * @return : Web element
     */
    private List<WebElement> premiumFeature(){
        return getDriver().findElements(By.xpath("//span[text()='Your Schedule']/following-sibling::span/span"));
    }

    /**
     * returning lock icon text
     * @return : Web element
     */
    private List<WebElement> lockicon(){
        return getDriver().findElements(By.xpath("//span[text()='Your Schedule']/following-sibling::icon/img"));
    }

    /**
     * returning learn more hyper link
     * @return : Web element
     */
    private List<WebElement> learnMore(){
        return getDriver().findElements(By.cssSelector("button[class='ui button _2rXWwF_Uy39x5eO1MMJqOu']>span>span"));
    }

    /**
     * return your schedule text locator
     * @return
     */
    private By yourSchedule(){return By.cssSelector("h2[class='ui header _10d-iKN-H5vknCTxDrfCk8']>span");}

    /**
     * return locator to get start date value
     * @return
     */
    private WebElement getStartDate(){
        return getDriver().findElement(By.xpath("//span[text()='Showing Week:']/parent::span/following-sibling::span/span/span[1]"));
    }

    /**
     * return locator to get end date value
     * @return
     */
    private WebElement getEndDate(){
        return getDriver().findElement(By.xpath("//span[text()='Showing Week:']/parent::span/following-sibling::span/span/span[2]"));
    }

    /**
     * return locator to get start date value from your schedule
     * @return
     */
    private WebElement getStartDateFromYourSchedule(){
        return getDriver().findElement(By.xpath("//span[text()='Your Schedule']/parent::h2/following-sibling::p/span/span[1]"));
    }

    /**
     * return locator to get end date value from your schedule
     * @return
     */
    private WebElement getEndDateFromYourSchedule(){
        return getDriver().findElement(By.xpath("//span[text()='Your Schedule']/parent::h2/following-sibling::p/span/span[2]"));
    }

    /**
     * return school name from your schedule page
     * @param startTime
     * @return
     */
    private WebElement getSchoolFromYourSchedule(String startTime){
        return getDriver().findElement(By.xpath("//span[text()='"+startTime+"']/following-sibling::h3/a"));
    }

    /**
     * return hyperlink of the school visit
     * @param school
     * @param startTime
     * @return
     */
    private WebElement selectHyperLinkInYourSchedule(String school,String startTime){
        return getDriver().findElement(By.xpath("//span[text()='"+startTime+"']/following-sibling::h3/a[text()='"+school+"']"));
    }

    /**
     * return locator for school from your schedule popup
     * @param school
     * @return
     */
    private By schoolInYourSchedule(String school){
        return By.xpath("//h2[text()='"+school+"']");
    }

    /**
     * return locator for email icon from your schedule popup
     * @return
     */
    private WebElement emailIconInYourSchedulePage(){
        return getDriver().findElement(By.cssSelector("i[class='mail outline icon']"));
    }

    /**
     * return locator for school details from your schedule popup
     * @return
     */
    private WebElement schoolDetailsInYourSchedulePopup(String schoolDetails){
        return getDriver().findElement(By.xpath("//div[normalize-space(text())='"+schoolDetails+"']"));
    }

    /**
     * return locator for school hyperlink
     * @param school
     * @return
     */
    private WebElement schoolHyperLink(String school){
        return getDriver().findElement(By.xpath("//a[text()='"+school+"']/span"));
    }

    /**
     * return locator for repvisits availability
     * @return
     */
    private By repvisitsAvailability(){
        return By.cssSelector("a[class='check-repvisits-link']");
    }

    /**
     * return close icon locator in your schedule popup
     * @return
     */
    private WebElement closeIconInYourSchedulePopup(){
        return getDriver().findElement(By.cssSelector("div[class='ui modal transition visible active _2Y9RSczSrF9nmSWpzkmx21']>i[class='close icon']"));
    }

    public void moveToElement(WebElement element) {
        Actions builder = new Actions(driver);
        builder.moveToElement(element).build().perform();
    }
   private By availabilityButtonInCommunity(String time){
        return By.xpath("//button[text()='"+time+"']");
    }

    private By dateInVisitSchedulePopup(String date){
        return By.xpath("//span[text()='"+date+"']");
    }

    private WebElement startAndEndTimeInSchedulePopup(String startTime,String endTime){
        return getDriver().findElement(By.xpath("//b[contains(text(),'"+startTime+"-"+endTime+"')]"));
    }

    private WebElement getTimeZone(){
        return getDriver().findElement(By.cssSelector("div[class='dJgg9PNwgMdtXUrtb_czW content']>div>b:nth-of-type(1)"));
    }

    private List<WebElement> schoolInSchedulePopup(String school){
        return getDriver().findElements(By.xpath("//div[@class='ui modal transition visible active _147-j4YP4EpdIh1rkNHy-W']//div[contains(text(),'"+school+"')]"));
    }

    private WebElement requestButton(String submitButton){
        return getDriver().findElement(By.xpath("//button[text()='"+submitButton+"']"));
    }

    private WebElement getMessage(){
        return getDriver().findElement(By.xpath("//span[@class='LkKQEXqh0w8bxd1kyg0Mq']/parent::div[@class='content']"));
    }

    private WebElement usernameTextbox() {
        return textbox("E-mail Address");
    }

    private WebElement passwordTextbox() {
        return textbox("Password");
    }

    private WebElement loginButton() {
        return button("Login");
    }

    private By negativeMessage(String negativeMessage){
        return By.xpath("//span[text()='"+negativeMessage+"']");
    }

    private WebElement pendingCheckBoxInCalendarPage() {
        WebElement check=getDriver().findElement(By.xpath("//div/label[text()='Pending']"));
        return check;
    }

    private WebElement availabilityCloseButton(){
        return getDriver().findElement(By.cssSelector("i[class='close large circular fitted link icon']"));
    }

    private By dateButton(){
        return By.cssSelector("button[class='ui tiny icon right floated right labeled button _1alys3gHE0t2ksYSNzWGgY']");
    }

    private By searchResult(){
      return By.xpath("//td[@class='_2i9Ix-ZCUb0uO32jR3hE3x']");
    }
    /**
     *
     * @return : view availability popup class name
     */

    private WebElement viewAvailabilityPopUp(){
        return driver.findElement(By.xpath("//aside[@class='ui overlay right very wide visible sidebar _4OZUXsPPiZy6Cfo7xMfVx']"));
    }

    /**
     *
     * @return close button in view availability popup
     */
    private WebElement closeButtonRepvisistsAvailability(){
        return driver.findElement(By.xpath("//div[@class='ui padded grid _3emfXzaryr93-lK5HisTL2']/div/div/i[@class='close large circular fitted link icon']"));
    }

    /**
     *
     * @return recommendation search text box text
     */
    private WebElement getRecommendationsSearchbox(){
        return  textbox("Search by location...");
    }
    private WebElement getContactNo(String eMail){
        return getDriver().findElement(By.xpath("//div[text()='"+eMail+"']/following-sibling::div"));
    }
    private By visitSuccessMessage(){return By.cssSelector("span[class='LkKQEXqh0w8bxd1kyg0Mq']");}
}


