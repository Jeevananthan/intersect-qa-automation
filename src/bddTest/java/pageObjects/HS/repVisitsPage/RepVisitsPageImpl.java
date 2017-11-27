package pageObjects.HS.repVisitsPage;

import cucumber.api.DataTable;
import javafx.scene.input.DataFormat;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.Assert.fail;
import java.text.DateFormat;

public class RepVisitsPageImpl extends PageObjectFacadeImpl {

    private Logger logger;
    //Creating instance of HE.RepVisitsPageImpl
    pageObjects.HE.repVisitsPage.RepVisitsPageImpl repVisitsPageHEObj = new pageObjects.HE.repVisitsPage.RepVisitsPageImpl();

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

    public void confirmDeclineCollegeAttendanceRequest(String requestOption, String fairName){
        logger.info("Validating the functionality of the " + requestOption + " button for College Fair attendance request.");
        navBar.goToRepVisits();
        link("College Fairs").click();
        button(By.xpath("//a[@aria-label='"+fairName+"']")).click();
        boolean expectedResult = false;
        switch(requestOption) {
            case "Decline":
                button("DECLINE").click();
                if(button(By.xpath("//span[contains(text(), 'No, go back')]")).isDisplayed() && getDriver().findElement(By.xpath("//button[@class = 'ui red small disabled right floated button']")).isDisplayed()) {
                    getDriver().findElement(By.xpath("//textarea[@id='attendee-decline-message']")).sendKeys("Attendance Denied.");
                    if(button("No, go back").isDisplayed() && button("Yes, decline visit").isDisplayed()) {
                        button(By.xpath("//span[contains(text(), 'No, go back')]")).click();
                        button("DECLINE").click();
                        button("Yes, decline visit").click();
                        button("Close").click();
                        expectedResult = true;
                    }
                }
                break;
            case "Confirm":
                button(By.xpath("//span[contains(text(), 'CONFIRM')]")).click();
                waitUntilPageFinishLoading();
                if(getDriver().findElement(By.xpath("//tr/td[contains(text(), 'Attending')]")).isDisplayed() && button(By.xpath("//span[contains(text(), 'CANCEL')]")).isDisplayed()) {
                    button("CANCEL").click();
                    getDriver().findElement(By.xpath("//textarea[@id='attendee-cancellation-message']")).sendKeys("Attendance Cancelled.");
                    button("Yes, cancel attendee").click();//-------------need to update this script here--------------
                    button("Close").click();
                    expectedResult = true;
                }

                break;
            case "default":
                expectedResult = false;
                break;
        }
        Assert.assertTrue(requestOption + " button is not working properly, or you did not choose to decline or confirm request.", expectedResult);

    }

    public void verifyAvailabilityAndSettingsPage(){
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        List<String> tabs = new ArrayList<>();
        //Left Menu
        tabs.add("Availability");
        tabs.add("Messaging Options");
        tabs.add("Time Zone");
        tabs.add("Notifications & Primary Contact");
        tabs.add("Calendar Sync");
        tabs.add("Naviance Settings");
        //Top menu
        tabs.add("Regular Weekly Hours");
        tabs.add("Holidays");
        tabs.add("Exceptions");
        tabs.add("Availability Settings");
        for (String tab : tabs) {
            Assert.assertTrue("Tab " + tab + " is not displaying as expected!",link(tab).isDisplayed());
        }
    }

    public void verifyAvailabilitySettings(DataTable dataTable){
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability Settings").click();
        Assert.assertTrue("Title 'Visit Scheduling' is not displayed",text("Visit Scheduling").isDisplayed());
        Assert.assertTrue("Text 'Accept' is not displayed",text("Accept").isDisplayed());
        Assert.assertTrue("Listbox is not displayed",driver.findElement(By.cssSelector("div[class='ui selection dropdown'][role='listbox']")).isDisplayed());
        Assert.assertTrue("'Save Changes' button is not displayed", driver.findElement(By.cssSelector("button[class='ui primary button']")).isDisplayed());
        Assert.assertTrue("'Visit Confirmations' header is not displayed as expected!", text("Visits Confirmations").isDisplayed());
        //'Visit Availability' Content
        Assert.assertTrue("contents 'Automatically confirm all visit requests?' is not displaying as expected!", text("Automatically confirm all visit requests?").isDisplayed());
        //verify the Radio button and Label
        Assert.assertTrue("radiobutton 'Yes, accept all incoming requests.' is not displaying as expected!", driver.findElement(By.xpath("//div[@class='field']//label[text()='Yes, accept all incoming requests.']/input[@type='radio']")).isDisplayed());
        Assert.assertTrue("radiobutton 'No, I want to manually review all incoming requests.' is not displaying as expected!", driver.findElement(By.xpath("//div[@class='field']//label[text()='No, I want to manually review all incoming requests.']/input[@type='radio']")).isDisplayed());

        List<Map<String,String>> entities = dataTable.asMaps(String.class,String.class);
        for (Map<String,String> availabilityData : entities ){
            for (String key : availabilityData.keySet()){
                switch (key){
                    // This is where we verify the actual editable values on the screen.
                    case "Accept":
                        String currentOption = driver.findElement(By.cssSelector("div[class='ui selection dropdown']>div[class='text']")).getText();
                        Assert.assertTrue("'Accept' value was not as expected.",currentOption.contains(availabilityData.get(key)));
                        break;
                    case "visits per day":
                        String actualVisitPerDay = driver.findElement(By.cssSelector("input[name='maxDailyColleges'][min='1'][max='99']")).getAttribute("value");
                        Assert.assertTrue("Visits per day were not set as expected.",actualVisitPerDay.equals(availabilityData.get(key)));
                        break;
                    case "Who can see your availability?":
                        if (availabilityData.get(key).contains("All Repvisits Users")){
                            Assert.assertTrue("Availability option is not set as expected",driver.findElement(By.cssSelector("[name=availabilityVisible][value=true]")).isSelected());
                        } else if (availabilityData.get(key).contains("Only Me")) {
                            Assert.assertTrue("Availability option is not set as expected", driver.findElement(By.cssSelector("[name=availabilityVisible][value=false]")).isSelected());
                        } else {
                            Assert.fail("\"" + availabilityData.get(key) + "\" is not a valid input for visit availability.");
                        }
                        break;
                    case "Automatically confirm all visit requests?":
                        if (availabilityData.get(key).contains("Yes, accept all incoming requests.")){
                            Assert.assertTrue("Confirmation option is not set as expected",driver.findElement(By.cssSelector("[name=autoConfirmVisit][value=true]")).isSelected());
                        } else if (availabilityData.get(key).contains("No, I want to manually review all incoming requests.")) {
                            Assert.assertTrue("Confirmation option is not set as expected", driver.findElement(By.cssSelector("[name=autoConfirmVisit][value=false]")).isSelected());
                        } else {
                            Assert.fail("\"" + availabilityData.get(key) + "\" is not a valid input for visit request confirmations.");
                        }
                        break;
                    case "Prevent colleges from scheduling new visits less than":
                        String rsvpDeadline = driver.findElement(By.cssSelector("input[name='rsvpDeadlineDays'][min='1'][max='99']")).getAttribute("value");
                        Assert.assertTrue("Visits per day were not set as expected.",rsvpDeadline.equals(availabilityData.get(key)));
                        break;
                    case "Prevent colleges from cancelling or rescheduling less than":
                        String modifyDeadline = driver.findElement(By.cssSelector("input[name='modifyDeadlineDays'][min='1'][max='99']")).getAttribute("value");
                        Assert.assertTrue("Visits per day were not set as expected.",modifyDeadline.equals(availabilityData.get(key)));
                        break;
                }
            }
        }

    }

    public void setPreventCollegesSchedulingNewVisits(String Numberofdays){
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability").click();
        link("Availability Settings").click();
        WebElement visitBox = getDriver().findElement(By.cssSelector("input[title='Days in advance to prevent colleges from scheduling new visits.'][min='1'][max='99']"));
        visitBox.clear();
        visitBox.sendKeys(Numberofdays);
        button("Save Changes").click();
    }

    public void setPreventCollegesCancellingorRescheduling(String DaysInAdvance){
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability").click();
        link("Availability Settings").click();
        WebElement visitBox = getDriver().findElement(By.cssSelector("input[title='Days in advance to prevent colleges from cancelling or rescheduling visits.'][min='1'][max='99']"));
        visitBox.clear();
        visitBox.sendKeys(DaysInAdvance);
        button("Save Changes").click();
    }

    public void setAcceptInVisitSchedulingToFullyBooked(String accept){
        setAcceptinAvailabilitySettings(accept, "1");
    }
    public void setAcceptinAvailabilitySettings(String accept, String visitsPerDay){
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability").click();
        link("Availability Settings").click();
        WebElement selectAccept = getDriver().findElement(By.cssSelector("div[class='ui selection dropdown']>div"));
        selectAccept.click();
        getDriver().findElement(By.xpath("//span[text()='" + accept + "']")).click();
        if(accept.equals("a maximum of...")) {
            WebElement visitsBox = getDriver().findElement(By.cssSelector("input[name='maxDailyColleges'][min='1'][max='99']"));
            visitsBox.clear();
            visitsBox.sendKeys(visitsPerDay);
        }
        button("Save Changes").click();
    }

    public void setVisitsConfirmations(String option){
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability").click();
        link("Availability Settings").click();
        WebElement options = getParent(getParent(getParent(driver.findElement(By.cssSelector("[name=autoConfirmVisit]")))));
        options.findElement(By.xpath("div/label[text()[contains(., '"+ option +"')]]")).click();
        button("Save Changes").click();
    }

    public void verifyContentsOfNavianceSettings() {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Naviance Settings").click();
        waitUntilPageFinishLoading();

        String publishVisitsToNavianceText = getDriver().findElement(By.id("form-naviance-settings")).getText();
        String likeToPublishAutomaticallyOptionsText = "Automatically publish confirmed visits.";
        String likeToPublishManuallyOptionsText = "Manually choose which visits to publish. (If any)";
        String optionalNotificationText = "When publishing to Naviance, do you want to notify students who have expressed interest in that institution?";
        String displayDeadLineAndStatus = "Do you want to display a deadline and status to students?";

        Assert.assertTrue(likeToPublishAutomaticallyOptionsText + " Text is not displayed",
                publishVisitsToNavianceText.contains(likeToPublishAutomaticallyOptionsText));
        Assert.assertTrue(likeToPublishManuallyOptionsText + " Text is not displayed",
                publishVisitsToNavianceText.contains(likeToPublishManuallyOptionsText));
        Assert.assertTrue(optionalNotificationText + " Text is not displayed",
                publishVisitsToNavianceText.contains(optionalNotificationText));
        Assert.assertTrue("Max number of students is not showing.", textbox(By.id("maxNumStudents")).isDisplayed());
        Assert.assertTrue("Location is not showing is not showing.", textbox(By.id("locationWithinSchool")).isDisplayed());
        Assert.assertTrue("Registration will close is not showing.", textbox(By.id("rsvpNumber")).isDisplayed());
        Assert.assertTrue(displayDeadLineAndStatus + " Text is not displayed",
                publishVisitsToNavianceText.contains(displayDeadLineAndStatus));
        Assert.assertTrue("Button Save Changes is not showing.", button(By.cssSelector("button[class='ui primary button']")).isDisplayed());
    }

    public void verifyContentsOfRegularWeeklyHours() {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability").click();
        link("Regular Weekly Hours").click();
        waitUntilPageFinishLoading();

        String startEndDatesForVisitsText = getDriver().findElement(By.cssSelector("div[class='availability _17ehxT_27Cme444W6HfdTN']")).getText();
        String dayTableText = getDriver().findElement(By.cssSelector("div[class='_10Tg7oamBO_AGbl5OgX9ba']")).getText();
        String displayStartEndForVisitsText = "2017-2018 Start and End Dates For Visits";
        Assert.assertTrue(dayTableText + " Text is not displayed",
                startEndDatesForVisitsText.contains("MON TUE WED THU FRI"));
        Assert.assertTrue(displayStartEndForVisitsText + " Text is not displayed",
                startEndDatesForVisitsText.contains(displayStartEndForVisitsText));
        Assert.assertTrue("Button Start Date is not showing.",
                button(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']")).isDisplayed());
        Assert.assertTrue("Button End Date is not showing.",
                button(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']:nth-child(3)")).isDisplayed());
        Assert.assertTrue("Button Add Time Slot is not showing.",
                button(By.cssSelector("button[class='ui primary button _3uyuuaqFiFahXZJ-zOb0-w']")).isDisplayed());
    }

    public void setStartAndEndDates(String startDate, String endDate) {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability").click();
        link("Regular Weekly Hours").click();
        waitUntilPageFinishLoading();
        button(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']")).click();
        setDate(startDate);
        button(By.cssSelector("div[style='display: inline-block;'] :nth-child(3)")).click();
        setDate(endDate);
    }

    public void setDate(String inputDate) {

        String[] parts = inputDate.split(" ");
        String calendarHeading = parts[0] + " " + parts[2];

        findMonth(calendarHeading);
        clickOnDay(parts[1]);
        waitUntilPageFinishLoading();
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
            fail("The Date selected it's out of RANGE.");
        }
    }

    public void clickOnDay(String date) {

        driver.findElement(By.cssSelector("div[class='DayPicker-Day']")).findElement(By.xpath("//div[contains(text(), "+date+")]")).click();
    }

    public void verifyStartAndEndDates(String startDate, String endDate){
        String[] partsStartDate = startDate.split(" ");
        String[] partsEndDate = endDate.split(" ");
        String startMonth = partsStartDate[0].substring(0, 3);
        String startDay = partsStartDate[1];
        String startYear = partsStartDate [2];
        String endMonth = partsEndDate[0].substring(0, 3);
        String endDay = partsEndDate[1];
        String endYear = partsEndDate [2];

        try {

            Assert.assertTrue("Button Start Date is not showing.",
                    driver.findElement(By.cssSelector("button[class='ui button _1RspRuP-VqMAKdEts1TBAC']")).
                            findElement(By.xpath("//span[contains(text(), '" + startMonth + " " + startDay + ", " + startYear + "')]")).isDisplayed());

            Assert.assertTrue("Button End Date is not showing.",
                    driver.findElement(By.cssSelector("div[style='display: inline-block;'] :nth-child(3)")).
                            findElement(By.xpath("//span[contains(text(), '" + endMonth + " " + endDay + ", " + endYear + "')]")).isDisplayed());

        } catch (Exception e) {
            fail("The Date selected it's out of RANGE.");
        }

    }

    public void verifyTimeZonePage(String ValueTZ){
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Time Zone").click();
        Assert.assertTrue("Tell us about your high school text is not present",getDriver().findElement(By.cssSelector(".ui.header")).isDisplayed());
        Assert.assertTrue("Please specify your high school's time zone text is not present",getDriver().findElement(By.cssSelector(".field label")).isDisplayed());
        WebElement TZDropDown = getDriver().findElement(By.cssSelector("[class='ui search selection dropdown']"));
        Assert.assertTrue("Timezone was not set as expected.",TZDropDown.findElement(By.className("text")).getText().contains(ValueTZ));
        Assert.assertTrue("TZ selected does not match",getDriver().findElement(By.cssSelector(".search[name=\"-search\"] + div")).isDisplayed());
        Assert.assertTrue("Update Time Zone button is not displayed",getDriver().findElement(By.cssSelector(".button[class='ui primary button']")).isDisplayed());
    }

    public void setTimeZone(String timeZone){
        if (!isLinkActive(link("Time Zone"))) {
            navBar.goToRepVisits();
            link("Availability & Settings").click();
            link("Time Zone").click();
        }
        setTimeZoneValue(timeZone);
        button("Update time zone").click();
    }

    public void clickLinkAvailability() {
        if (!isLinkActive(link("Availability"))) {
            navBar.goToRepVisits();
            link("Availability & Settings").click();
            link("Availability").click();
        }
    }

    private void setTimeZoneValue(String timeZone) {
        WebElement EntertimeZone = getDriver().findElement(By.cssSelector(".search[name=\"-search\"] + div"));
        EntertimeZone.click();
        getDriver().findElement(By.xpath("//span[text()='"+ timeZone +"']")).click();
    }

    public void verifyOverviewPage(){
        //Since the code is already implemented for HE, calling the method of HE RepVisitsPageImpl class.
        repVisitsPageHEObj.verifyOverviewPage();
    }

    public void cancelCollegeFair(String fairName) {
        logger.info("Cancelling Job Fair " + fairName + " under RepVisits.");
        navBar.goToRepVisits();
        link("College Fairs").click();
        button(By.xpath("//a[@aria-label='"+fairName+"']")).click();
        button("Edit").click();
        button("Cancel This College Fair").click();
        if (getDriver().findElements(By.xpath("//span[contains(text(), 'Yes, Cancel this fair')]")).size() >= 1) {
            button("yes, cancel this fair").click();
        }
        else if (getDriver().findElements(By.xpath("//span[contains(text(), 'Cancel fair and notify colleges')]")).size() >= 1) {
            textbox(By.name("cancellationMessage")).sendKeys("College fair has been cancelled.");
            button("cancel fair and notify colleges").click();
            button("Close").click();
        }
        else{
            Assert.assertTrue("There were no job fairs registered for this high school.", false);
        }
        Assert.assertFalse("College fair was not canceled.", getDriver().findElements(By.xpath("//span[contains(text(), 'Upcoming Events')]/../../following-sibling::table/tbody/tr/td[contains(text(), '"+fairName+"')]")).size() >= 1);
    }

    public void createJobFair(DataTable dataTable) {
        logger.info("Creating a Job Fair under RepVisits.");
        navBar.goToRepVisits();
        waitUntilPageFinishLoading();
        link("College Fairs").click();
        waitUntilPageFinishLoading();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(text(By.id("add-college"))));
        //wait.until(ExpectedConditions.visibilityOfElementLocated(text(By.id("add-college"))));
        button("ADD A COLLEGE FAIR").click();
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        for (String key : data.keySet()) {
            if(key.equals("Date")){
                String fairDateToFill = createFutureDateForFair(Integer.parseInt(data.get(key)));
                createCollegeFair(key, fairDateToFill);
            }
            else if (key.equals("RSVP Deadline")){
                String fairRSVPDateToFill = createFutureDateForFair(Integer.parseInt(data.get(key)));
                createCollegeFair(key, fairRSVPDateToFill);
            }else
                createCollegeFair(key, data.get(key));

        }
        scrollDown(driver.findElement(By.xpath("//button[@class='ui primary right floated button']")));
        button("Save").click();
    }

    public String createFutureDateForFair(int days) {
        DateFormat dateFormat_3 = DateFormat.getDateInstance(DateFormat.LONG);
        Calendar cal_1 = Calendar.getInstance();
        cal_1.setTime(new Date());
        cal_1.add(Calendar.DATE, days);
        String fixDate = dateFormat_3.format(cal_1.getTime());
        return fixDate;
    }

    public void scrollDown(WebElement element){
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(0,250)", "");
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    public void createCollegeFair(String field, String data){
        switch (field) {
            case "College Fair Name":
                textbox(By.id("college-fair-name")).sendKeys(data);
                break;
            case "Date":
                getDriver().findElement(By.xpath("//div[@class='ui fluid input']/input[@id = 'college-fair-date']/../i")).click();
                // Only goes out 12 months
                for(int i = 0; i <= 12; i++) {
                    List<WebElement> dayCalander = getDriver().findElements(By.xpath("//div[@class='DayPicker-Body']//div[@aria-label]"));
                    for (WebElement day : dayCalander) {
                        String dayResult = day.getAttribute("aria-label");
                        String dayAvailable = day.getAttribute("aria-disabled");
                        if (dayResult.equalsIgnoreCase(data) &&
                                dayAvailable.equalsIgnoreCase("false")) {
                            day.click();
                            i = 13;
                            break;
                        }
                    }
                    if(i<12){
                        getDriver().findElement(By.xpath("//span[@class = 'DayPicker-NavButton DayPicker-NavButton--next']")).click();
                    }
                    else if(i==12) {
                        logger.info("The date entered is in the past, unavailable, or invalid. \nPlease enter date as \"January 1, 2017\". \nDates can only be chosen 12 months in advance");
                    }
                }
                break;
            case "Start Time":
                WebElement startTime = getDriver().findElement(By.xpath("//input[@id='college-fair-start-time']"));
                startTime.click();
                startTime.sendKeys(data);
                break;
            case "End Time":
                WebElement endTime = getDriver().findElement(By.xpath("//input[@id='college-fair-end-time']"));
                endTime.click();
                endTime.sendKeys(data);
                break;
            case "RSVP Deadline":
                getDriver().findElement(By.xpath("//div[@class='ui input']/input[@id = 'college-fair-rsvp-deadline']/../i")).click();
                // Only goes out 12 months
                for(int i = 0; i <= 12; i++) {
                    List<WebElement> RSVPCalander = getDriver().findElements(By.xpath("//div[@class='DayPicker-Body']//div[@aria-label]"));
                    for (WebElement day : RSVPCalander) {
                        String dayResult = day.getAttribute("aria-label");
                        String dayAvailable = day.getAttribute("aria-disabled");
                        if (dayResult.equalsIgnoreCase(data) &&
                                dayAvailable.equalsIgnoreCase("false")) {
                            day.click();
                            i = 13;
                            break;
                        }
                    }
                    if(i<12){
                        getDriver().findElement(By.xpath("//span[@class = 'DayPicker-NavButton DayPicker-NavButton--next']")).click();
                    }
                    else if(i==12) {
                        logger.info("The date entered is in the past, unavailable, or invalid. \nPlease enter date as \"January 1, 2017\". \nDates can only be chosen 12 months in advance");
                    }
                }
                break;
            case "Cost":
                fairCost().sendKeys(data);
                break;
            case "Max Number of Colleges":
                textbox(By.id("college-fair-max-number-colleges")).sendKeys(data);
                break;
            case "Number of Students Expected":
                textbox(By.id("college-fair-number-expected-students")).sendKeys(data);
                break;
            case "Instructions for College Representatives":
                textbox(By.id("college-fair-instructions")).sendKeys(data);
                break;
            case "Automatically Confirm Incoming Requestions From Colleges?":
                scrollDown(driver.findElement(By.id("college-fair-automatic-request-confirmation-yes")));
                if(data.equalsIgnoreCase("yes")) {
                    radioButton(By.id("college-fair-automatic-request-confirmation-yes")).click();
                }
                else {
                    radioButton(By.id("college-fair-automatic-request-confirmation-no")).click();
                }
                break;
            case "Email Message to Colleges After Confirmation":
                scrollDown(driver.findElement(By.id("college-fair-email-message-to-colleges")));
                textbox(By.id("college-fair-email-message-to-colleges")).sendKeys(data);
                break;
            default:
                logger.info("Something went wrong during filling data for creatinf Fair.....");
        }
    }


    /*public void fillAddCollegeFairFields(String field, String data) {
        switch (field) {
            case "Automatically Confirm Incoming Requestions From Colleges?":
                // Scroll to the end of the form
                JavascriptExecutor jse = (JavascriptExecutor)driver;
                jse.executeScript("window.scrollBy(0,250)", "");
                Actions actions = new Actions(driver);
                actions.moveToElement(driver.findElement(By.id("college-fair-automatic-request-confirmation-yes"))).perform();
                if(data.equalsIgnoreCase("yes")) {
                    radioButton(By.id("college-fair-automatic-request-confirmation-yes")).click();
                }
                else {
                    radioButton(By.id("college-fair-automatic-request-confirmation-no")).click();
                }
                break;
            case "College Fair Name":
                textbox(By.id("college-fair-name")).sendKeys(data);
                break;
            case "Date":
                getDriver().findElement(By.xpath("//div[@class='ui fluid input']/input[@id = 'college-fair-date']/../i")).click();
                // Only goes out 12 months
                for(int i = 0; i <= 12; i++) {
                    List<WebElement> dayCalander = getDriver().findElements(By.xpath("//div[@class='DayPicker-Body']//div[@aria-label]"));
                    for (WebElement day : dayCalander) {
                        String dayResult = day.getAttribute("aria-label");
                        String dayAvailable = day.getAttribute("aria-disabled");
                        if (dayResult.equalsIgnoreCase(data) &&
                                dayAvailable.equalsIgnoreCase("false")) {
                            day.click();
                            i = 13;
                            break;
                        }
                    }
                    if(i<12){
                        getDriver().findElement(By.xpath("//span[@class = 'DayPicker-NavButton DayPicker-NavButton--next']")).click();
                    }
                    else if(i==12) {
                        logger.info("The date entered is in the past, unavailable, or invalid. \nPlease enter date as \"January 1, 2017\". \nDates can only be chosen 12 months in advance");
                    }
                }
                break;
            case "Email Message to Colleges After Confirmation":
                textbox(By.name("emailInstructions")).sendKeys(data);
                break;
            case "End Time":
                WebElement endTime = getDriver().findElement(By.xpath("//input[@id='college-fair-end-time']"));
                endTime.click();
                endTime.sendKeys(data);
                break;
            case "Instructions for College Representatives":
                textbox(By.name("webInstructions")).sendKeys(data);
                break;
            case "Max Number of Colleges":
                textbox(By.name("maxColleges")).sendKeys(data);
                break;
            case "Number of Students Expected":
                textbox(By.name("numberOfExpectedStudents")).sendKeys(data);
                break;
            case "RSVP Deadline":
                getDriver().findElement(By.xpath("//div[@class='ui input']/input[@id = 'college-fair-rsvp-deadline']/../i")).click();
                // Only goes out 12 months
                for(int i = 0; i <= 12; i++) {
                    List<WebElement> RSVPCalander = getDriver().findElements(By.xpath("//div[@class='DayPicker-Body']//div[@aria-label]"));
                    for (WebElement day : RSVPCalander) {
                        String dayResult = day.getAttribute("aria-label");
                        String dayAvailable = day.getAttribute("aria-disabled");
                        if (dayResult.equalsIgnoreCase(data) &&
                                dayAvailable.equalsIgnoreCase("false")) {
                            day.click();
                            i = 13;
                            break;
                        }
                    }
                    if(i<12){
                        getDriver().findElement(By.xpath("//span[@class = 'DayPicker-NavButton DayPicker-NavButton--next']")).click();
                    }
                    else if(i==12) {
                        logger.info("The date entered is in the past, unavailable, or invalid. \nPlease enter date as \"January 1, 2017\". \nDates can only be chosen 12 months in advance");
                    }
                }
                break;
            case "Start Time":
                WebElement startTime = getDriver().findElement(By.xpath("//input[@id='college-fair-start-time']"));
                startTime.click();
                startTime.sendKeys(data);
                break;
            case "Cost":
                fairCost().sendKeys(data);
                break;
            default:
                textbox(By.name(field.toLowerCase())).sendKeys(data);
                break;
        }
    }*/


    //locators
    private boolean isLinkActive(WebElement link) {
        return link.getAttribute("class").contains("active");
    }
    private WebElement fairCost() { return getDriver().findElement(By.id("college-fair-cost")); }
}




