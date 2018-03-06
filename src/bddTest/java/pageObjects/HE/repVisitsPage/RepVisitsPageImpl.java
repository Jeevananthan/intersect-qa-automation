package pageObjects.HE.repVisitsPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import cucumber.api.java.gl.E;
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
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import utilities.GetProperties;

public class RepVisitsPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

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
        driver.findElement(By.xpath("//i[@class='teal search large link icon _3pWea2IV4hoAzTQ12mEux-']")).click();
        text(highSchool).click();
        text("Fairs").click();
        if (text(fairTitle).isDisplayed()){
            driver.findElement(By.xpath("//span[contains(text(), '"+fairTitle+"')]/../following-sibling::div/button/span[contains(text(),'Register')]")).click();
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
                        String header = driver.findElement(By.className("_2sidGZdt-6WEIYD6BrBvZ")).getText();
                        Assert.assertTrue("School name was not found in header.", header.contains(school.get(key)));
                        break;
                    case "High School Contact:":
                        String contactLink = driver.findElement(By.className("_16fRAQOOUtmia2wftYHDhf")).getText();
                        Assert.assertTrue("Contact name was not found in header.",contactLink.contains(school.get(key)));
                        break;
                    default:
                        WebElement label = driver.findElement(By.xpath("//div[contains(text(),'" + key + "')]"));
                        WebElement item = getParent(label);
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
        driver.findElement(By.xpath("//button/span[text()='Cancel This Fair']")).click();
        waitForUITransition();
        driver.findElement(By.id("cancel-message")).click();
        driver.findElement(By.id("cancel-message")).sendKeys(Keys.PAGE_DOWN);
        driver.findElement(By.id("cancel-message")).sendKeys("by QA");
        button("Yes, Cancel Fair").click();
        waitUntilPageFinishLoading();
        waitForUITransition();
    }


    private void validateInfolink(){
        Assert.assertTrue("Text 'For more information' is not displayed", text("For more information:").isDisplayed());
        WebElement item = getParent(text("For more information:"));
        item.findElement(By.tagName("a")).click();
        Assert.assertTrue("Did not end up in Community!", driver.getCurrentUrl().contains("counselor-community/institution"));
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
        List<WebElement> searchedValueOfinstitutionName = driver.findElements(By.className("_2ZIfaO8qcJzzQzgSfH1Z8h"));
        for(int i=0;i<searchedValueOfinstitutionName.size();i++){
            String value = searchedValueOfinstitutionName.get(i).getText();
            Assert.assertTrue("Partial matching on institution name is not available",value.toLowerCase().contains(institutionName.toLowerCase()));
        }
    }
    public void selectHighSchoolFromIntermediateSearchResults(String schoolName, String location) {
        WebElement schoolLocation = text(location);
        getParent(schoolLocation).findElement(By.tagName("a")).click();
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
        driver.switchTo().frame(0);
        Assert.assertTrue("Check RepVisits Availability Button is not present", getCheckRepVisitsAvailabilityButton().isDisplayed());
        getCheckRepVisitsAvailabilityButton().click();
        driver.switchTo().defaultContent();
        Assert.assertTrue("RepVisits Availability Sidebar is not displaying.", getRepVisitsAvailabilitySidebar().isDisplayed());
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

    public void verifyFairInCalendar(String date) {
        boolean result = false;
        waitUntil(ExpectedConditions.elementToBeClickable(getCalendarBtn()));
        getCalendarBtn().click();
        waitUntil(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.ui.medium.inverted.loader")));
        waitUntil(ExpectedConditions.elementToBeClickable(rightCalendarHeaderDate()));
        if (!rightCalendarHeaderDate().getText().equals(date.split(" ")[0])) {
            pressCalendarArrowUntil("right", date.split(" ")[0], 10);
        }
        try {
            if (showMoreLink().isDisplayed()) {
                showMoreLink().click();
                for (WebElement overlayEvent : overlayEventsList()) {
                    if (overlayEvent.findElement(By.cssSelector("span.rbc-event-time")).getText().equals(date.split(" ")[2])) {
                        result = true;
                        break;
                    }
                }
            }
        } catch (NoSuchElementException e) {
            for (int i = 1; i < 3; i++) {
                if (getDateCell(date.split(" ")[1], date.split(" ")[2], i).isDisplayed()) {
                    result = true;
                    break;
                }
            }
        }
        Assert.assertTrue("The fair is not displayed in the calendar", result);
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

    public void verifyFairInQuickView(String schoolName, String date) {
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
        waitUntilPageFinishLoading();
    }

    public void clickUpgradeButton(){
        driver.findElement(By.xpath("//div[@class='seven wide column _2I5Wf1vjM_1kmY7BHT_G9k']//div/button/span[text()='UPGRADE']")).click();
        waitUntilPageFinishLoading();
    }

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

    public void verifyUpgradeMessageInTravelPlanInRepVisits(){
        navBar.goToRepVisits();
        waitUntilElementExists(getTravelPlanBtn());
        getTravelPlanBtn().click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("'Premium Feature' text is not displayed",text("Premium Feature").isDisplayed());
        Assert.assertTrue("'UPGRADE' text is not displayed",text("UPGRADE").isDisplayed());
        Assert.assertTrue("'Lock' Icon is not displayed",driver.findElement(By.cssSelector(" i[class='icons']")).isDisplayed());
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


    public void searchSchool(String school){
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        searchTextBox().sendKeys(school);
        waitUntilElementExists(search());
        searchButton().click();
        waitUntilElementExists(schoolInSearchAndSchedule(school));
        Assert.assertTrue("school is not displayed",schoolInSearchAndSchedule(school).isDisplayed());
        schoolInSearchAndSchedule(school).click();
        waitUntilPageFinishLoading();
    }

    public void visitsSchedule(String school,String startDate,String time){
        visit().click();
        waitUntilElementExists(schoolInVisits(school));
        Assert.assertTrue("school is not displayed",schoolInVisits(school).isDisplayed());
        waitUntilElementExists(goToDate());
        String gotoDate = getSpecificDate(startDate);
        setDate(gotoDate, "Go To Date");
        String visitTime = pageObjects.HS.repVisitsPage.RepVisitsPageImpl.StartTime;
        String visitDate=getMonthandDate(startDate);
        Assert.assertTrue("Availability is not displayed",availabilityButton(visitDate,visitTime).isDisplayed());
        availabilityButton(visitDate,visitTime).click();
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
        String visitTime=pageObjects.HS.repVisitsPage.RepVisitsPageImpl.StartTime;
        Assert.assertTrue("SchedulePopup is not displayed",driver.findElement(By.xpath("//div[contains(text(),'Ready to Schedule?')]")).isDisplayed());
        Assert.assertTrue("school is not displayed",driver.findElement(By.xpath("//div[contains(text(),'Do you want to schedule a visit with "+school+" from')]")).isDisplayed());
        Assert.assertTrue("time is not displayed",driver.findElement(By.xpath("//div[contains(text(),'Do you want to schedule a visit with "+school+" from')]/b[contains(text(),'"+visitTime+"-"+endTime+"')]")).isDisplayed());
        visitRequestButton().click();
        waitUntilElementExists(goToDate());
        navBar.goToRepVisits();
        waitForUITransition();
        waitUntilPageFinishLoading();
    }

    public void visitFairsToRegister(String fairName,String schoolName){
        String Fair=pageObjects.HS.repVisitsPage.RepVisitsPageImpl.FairName;
        waitUntilElementExists(fairs());
        fairs().click();
        waitUntilElementExists(register());
        waitUntilElementExists(fairName(schoolName,Fair));
        Assert.assertTrue("fair is not displayed",fairName(schoolName,Fair).isDisplayed());
        Assert.assertTrue("schoolName is not displayed",schoolName(schoolName).isDisplayed());
        registerButton(Fair) .click();
        Assert.assertTrue("submit page is not displayed",text("Yes, Submit Request").isDisplayed());
        submitButton().click();
        waitForUITransition();
        navBar.goToRepVisits();
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

    public void clickOnDay(String date) {
        try{
            driver.findElement(By.cssSelector("div[class='DayPicker-Day']")).findElement(By.xpath("//div[text()="+date+"]")).click();}
        catch (Exception e){logger.info("Invalid date");}
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

    private WebElement upgradeButton(){
        WebElement button=button("UPGRADE");
        return button;
   }

    private WebElement getOverviewBtn() {
        return link("Overview");
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

    private WebElement getSearchBox() { return textbox("Search by school name or location...");}
    private WebElement getVisitsFeedbackBtn() {return link("Visit Feedback"); }
    private WebElement getSearchAndScheduleSearchBox(){ return textbox("Search by school name or location..."); }
    //private WebElement getSearchBox() { return textbox("Enter a school name or location");}
    private WebElement getSearchBoxforContact() { return driver.findElement(By.name("contacts-search"));}
    private WebElement getSearchButton() { return driver.findElement(By.className("_3pWea2IV4hoAzTQ12mEux-"));}
    private WebElement getMapButton() { return driver.findElement(By.cssSelector("[class='map outline icon']"));}
    private WebElement getComingSoonMessageInOverviewPage(){ return driver.findElement(By.className("_9SnX9M6C12WsFrvkMMEZR")); }
    private WebElement getCheckRepVisitsAvailabilityButton(){ return driver.findElement(By.xpath("//a[text() = 'Check RepVisits Availability']")); }
    private WebElement getRepVisitsAvailabilitySidebar(){ return driver.findElement(By.className("_36B3QS_3-4bR8tfro5jydy")); }
    private WebElement getRegistrationButton(String fairName) { return getDriver().findElement(By.xpath("//span[text()='" + fairName + "']/../../div/button/span")); }
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
    public WebElement miniCalendarDayCell(String day) { return getDriver().findElement(By.xpath("//div[@class='DayPicker-Week']/div[text()='" + day + "']")); }
    public WebElement showMoreLink() { return getDriver().findElement(By.cssSelector("a.rbc-show-more")); }
    private List<WebElement> overlayEventsList() { return getDriver().findElements(By.cssSelector("div.rbc-overlay div.rbc-event")); }
    private WebElement visitsTab() { return getDriver().findElement(By.cssSelector("div.ui.left.attached.button")); }
    private WebElement goToDateButton() { return getDriver().findElement(By.cssSelector("button.ui.right.labeled.small.basic i")); }
    private WebElement searchAndScheduleCalendarIcon() { return getDriver().findElement(By.cssSelector("button.ui.right.labeled.tiny.icon.button i")); }
    private List<WebElement> headerWeekDays() { return getDriver().findElements(By.cssSelector("table.ui.fixed.unstackable.basic.center thead th div span")); }
    private List<WebElement> visitsRows() { return getDriver().findElements(By.cssSelector("table.ui.fixed.unstackable.basic.center tbody tr")); }
    private List<WebElement> eventsRows(String day) { return getDriver().findElements(By.xpath("//div[not(contains(@class, 'rbc-off-range'))][contains(@class,'rbc-date-cell')]/a[text()='" + day + "']/../../following-sibling::div")); }
    private WebElement getVerticalStaffMembersMenu() {
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

    private WebElement availabilityButton(String date,String time) {
        WebElement button= driver.findElement(By.xpath("//span[text()='"+date+"']/parent::th/ancestor::thead/following-sibling::tbody/tr//td//div/button[text()='"+time+"']"));
        return button;
    }
    private WebElement fairs() {
        WebElement fair=button("Fairs");
        return  fair;
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
    private WebElement search(){
        WebElement search=driver.findElement(By.xpath("//button[@class='ui button']"));
        waitUntilElementExists(search);
        return  search;
    }
    private WebElement goToDate() {
        WebElement goToDate=driver.findElement(By.xpath("//button[text()='Go To Date']"));
        waitUntilElementExists(goToDate);
        return  goToDate;
    }
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
    private WebElement textBoxInViewDetails() {
        WebElement text= driver.findElement(By.xpath("//input[@aria-label='Internal Notes']"));
        return text;
    }
    private WebElement showMoreButton(String option){
        WebElement text=driver.findElement(By.xpath("//span[text()='"+option+"']"));
        return text;
    }
    private WebElement calendar()
    {
        WebElement navbar=link("Calendar");
        return navbar;
    }
    private WebElement collegeFairTextBoxInCalendarPage()
    {
        WebElement check= driver.findElement(By.xpath("//div/label[text()='College Fair - Confirmed']"));
        return check;
    }
    private WebElement visitCheckBoxInCalendarPage()
    {
        WebElement check=driver.findElement(By.xpath("//div/label[text()='Visits - Confirmed']"));
        return check;
    }
    private WebElement currentMonthInCalendarPage()
    {
        WebElement month=driver.findElement(By.xpath("//div[@class='three wide column ZfUaDp3-V60qJ8_BTeIi']/div[@class='ui medium header _1ucD2vjQuS9iWHF9uzN__M']"));
        return month;
    }
    private WebElement nextMonthButton()
    {
        WebElement button=driver.findElement(By.xpath("//button[@class='ui teal basic icon button _38R7SJgG4fJ86m-eLYYZJw']"));
        return button;
    }
    private String month(String date)
    {
        String month=getSpecificDate(date);
        String selectMonth[]=month.split(" ");
        String currentMonth=selectMonth[0];
        return  currentMonth;
    }
}


