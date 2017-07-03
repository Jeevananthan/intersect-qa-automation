package pageObjects.HS.repVisitsPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.fail;

public class RepVisitsPageImpl extends PageObjectFacadeImpl {

    private Logger logger;
    //Creating instance of HE.RepVisitsPageImpl
    pageObjects.HE.repVisitsPage.RepVisitsPageImpl repVisitsPageHEObj = new pageObjects.HE.repVisitsPage.RepVisitsPageImpl();

    public RepVisitsPageImpl() {
        logger = Logger.getLogger(RepVisitsPageImpl.class);
    }

    public void checkRepVisitsSubTabs(DataTable dataTable) {
        navBar.goToRepVisits();
        List<String> list = dataTable.asList(String.class);
        for (String repVisitsSubItem : list) {
            Assert.assertTrue(repVisitsSubItem + " is not showing.", link(repVisitsSubItem).isDisplayed());
        }
    }

    public void verifyAvailabilityAndSettingsPage() {
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
            Assert.assertTrue("Tab " + tab + " is not displaying as expected!", link(tab).isDisplayed());
        }
    }

    public void verifyAvailabilitySettings(DataTable dataTable) {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability Settings").click();
        Assert.assertTrue("Title 'Visit Scheduling' is not displayed", text("Visit Scheduling").isDisplayed());
        Assert.assertTrue("Text 'Accept' is not displayed", text("Accept").isDisplayed());
        Assert.assertTrue("Listbox is not displayed", driver.findElement(By.cssSelector("div[class='ui selection dropdown'][role='listbox']")).isDisplayed());
        Assert.assertTrue("'Save Changes' button is not displayed", driver.findElement(By.cssSelector("button[class='ui primary button']")).isDisplayed());
        Assert.assertTrue("'Visit Confirmations' header is not displayed as expected!", text("Visits Confirmations").isDisplayed());
        //'Visit Availability' Content
        Assert.assertTrue("contents 'Automatically confirm all visit requests?' is not displaying as expected!", text("Automatically confirm all visit requests?").isDisplayed());
        //verify the Radio button and Label
        Assert.assertTrue("radiobutton 'Yes, accept all incoming requests.' is not displaying as expected!", driver.findElement(By.xpath("//div[@class='field']//label[text()='Yes, accept all incoming requests.']/input[@type='radio']")).isDisplayed());
        Assert.assertTrue("radiobutton 'No, I want to manually review all incoming requests.' is not displaying as expected!", driver.findElement(By.xpath("//div[@class='field']//label[text()='No, I want to manually review all incoming requests.']/input[@type='radio']")).isDisplayed());

        List<Map<String, String>> entities = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> availabilityData : entities) {
            for (String key : availabilityData.keySet()) {
                switch (key) {
                    // This is where we verify the actual editable values on the screen.
                    case "Accept":
                        String currentOption = driver.findElement(By.cssSelector("div[class='ui selection dropdown']>div[class='text']")).getText();
                        Assert.assertTrue("'Accept' value was not as expected.", currentOption.contains(availabilityData.get(key)));
                        break;
                    case "visits per day":
                        String actualVisitPerDay = driver.findElement(By.cssSelector("input[name='maxDailyColleges'][min='1'][max='99']")).getAttribute("value");
                        Assert.assertTrue("Visits per day were not set as expected.", actualVisitPerDay.equals(availabilityData.get(key)));
                        break;
                    case "Who can see your availability?":
                        if (availabilityData.get(key).contains("All Repvisits Users")) {
                            Assert.assertTrue("Availability option is not set as expected", driver.findElement(By.cssSelector("[name=availabilityVisible][value=true]")).isSelected());
                        } else if (availabilityData.get(key).contains("Only Me")) {
                            Assert.assertTrue("Availability option is not set as expected", driver.findElement(By.cssSelector("[name=availabilityVisible][value=false]")).isSelected());
                        } else {
                            Assert.fail("\"" + availabilityData.get(key) + "\" is not a valid input for visit availability.");
                        }
                        break;
                    case "Automatically confirm all visit requests?":
                        if (availabilityData.get(key).contains("Yes, accept all incoming requests.")) {
                            Assert.assertTrue("Confirmation option is not set as expected", driver.findElement(By.cssSelector("[name=autoConfirmVisit][value=true]")).isSelected());
                        } else if (availabilityData.get(key).contains("No, I want to manually review all incoming requests.")) {
                            Assert.assertTrue("Confirmation option is not set as expected", driver.findElement(By.cssSelector("[name=autoConfirmVisit][value=false]")).isSelected());
                        } else {
                            Assert.fail("\"" + availabilityData.get(key) + "\" is not a valid input for visit request confirmations.");
                        }
                        break;
                    case "Prevent colleges from scheduling new visits less than":
                        String rsvpDeadline = driver.findElement(By.cssSelector("input[name='rsvpDeadlineDays'][min='1'][max='99']")).getAttribute("value");
                        Assert.assertTrue("Visits per day were not set as expected.", rsvpDeadline.equals(availabilityData.get(key)));
                        break;
                    case "Prevent colleges from cancelling or rescheduling less than":
                        String modifyDeadline = driver.findElement(By.cssSelector("input[name='modifyDeadlineDays'][min='1'][max='99']")).getAttribute("value");
                        Assert.assertTrue("Visits per day were not set as expected.", modifyDeadline.equals(availabilityData.get(key)));
                        break;
                }
            }
        }

    }

    public void setPreventCollegesSchedulingNewVisits(String Numberofdays) {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability").click();
        link("Availability Settings").click();
        WebElement visitBox = getDriver().findElement(By.cssSelector("input[title='Days in advance to prevent colleges from scheduling new visits.'][min='1'][max='99']"));
        visitBox.clear();
        visitBox.sendKeys(Numberofdays);
        button("Save Changes").click();
    }

    public void setPreventCollegesCancellingorRescheduling(String DaysInAdvance) {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability").click();
        link("Availability Settings").click();
        WebElement visitBox = getDriver().findElement(By.cssSelector("input[title='Days in advance to prevent colleges from cancelling or rescheduling visits.'][min='1'][max='99']"));
        visitBox.clear();
        visitBox.sendKeys(DaysInAdvance);
        button("Save Changes").click();
    }

    public void setAcceptInVisitSchedulingToFullyBooked(String accept) {
        setAcceptinAvailabilitySettings(accept, "1");
    }

    public void setAcceptinAvailabilitySettings(String accept, String visitsPerDay) {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability").click();
        link("Availability Settings").click();
        WebElement selectAccept = getDriver().findElement(By.cssSelector("div[class='ui selection dropdown']>div"));
        selectAccept.click();
        getDriver().findElement(By.xpath("//span[text()='" + accept + "']")).click();
        if (accept.equals("a maximum of...")) {
            WebElement visitsBox = getDriver().findElement(By.cssSelector("input[name='maxDailyColleges'][min='1'][max='99']"));
            visitsBox.clear();
            visitsBox.sendKeys(visitsPerDay);
        }
        button("Save Changes").click();
    }

    public void setVisitsConfirmations(String option) {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability").click();
        link("Availability Settings").click();
        WebElement options = getParent(getParent(getParent(driver.findElement(By.cssSelector("[name=autoConfirmVisit]")))));
        options.findElement(By.xpath("div/label[text()[contains(., '" + option + "')]]")).click();
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

        try {
            while (!DayPickerCaption.contains(month)) {
                driver.findElement(By.cssSelector("span[class='DayPicker-NavButton DayPicker-NavButton--next']")).click();
                DayPickerCaption = driver.findElement(By.cssSelector("div[class='DayPicker-Caption']")).getText();
            }
        } catch (Exception e) {
            fail("The Date selected it's out of RANGE.");
        }
    }

    public void clickOnDay(String date) {

        driver.findElement(By.cssSelector("div[class='DayPicker-Day']")).findElement(By.xpath("//div[contains(text(), " + date + ")]")).click();
    }

    public void verifyStartAndEndDates(String startDate, String endDate) {
        String[] partsStartDate = startDate.split(" ");
        String[] partsEndDate = endDate.split(" ");
        String startMonth = partsStartDate[0].substring(0, 3);
        String startDay = partsStartDate[1];
        String startYear = partsStartDate[2];
        String endMonth = partsEndDate[0].substring(0, 3);
        String endDay = partsEndDate[1];
        String endYear = partsEndDate[2];

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

    public void verifyTimeZonePage(String ValueTZ) {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Time Zone").click();
        Assert.assertTrue("Tell us about your high school text is not present", getDriver().findElement(By.cssSelector(".ui.header")).isDisplayed());
        Assert.assertTrue("Please specify your high school's time zone text is not present", getDriver().findElement(By.cssSelector(".field label")).isDisplayed());
        WebElement TZDropDown = getDriver().findElement(By.cssSelector("[class='ui search selection dropdown']"));
        Assert.assertTrue("Timezone was not set as expected.", TZDropDown.findElement(By.className("text")).getText().contains(ValueTZ));
        Assert.assertTrue("TZ selected does not match", getDriver().findElement(By.cssSelector(".search[name=\"-search\"] + div")).isDisplayed());
        Assert.assertTrue("Update Time Zone button is not displayed", getDriver().findElement(By.cssSelector(".button[class='ui primary button']")).isDisplayed());
    }

    public void setTimeZone(String timeZone) {
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
        getDriver().findElement(By.xpath("//span[text()='" + timeZone + "']")).click();
    }

    public void verifyOverviewPage() {
        //Since the code is already implemented for HE, calling the method of HE RepVisitsPageImpl class.
        repVisitsPageHEObj.verifyOverviewPage();
    }

    public void accessAddAttendeePopUp(String attendeeName) {
        getAddAttendeeSearchBox().clear();
        getAddAttendeeSearchBox().sendKeys(attendeeName);
        waitUntilPageFinishLoading();
        List<WebElement> categories = getDriver().findElement(By.id("undefined-results")).findElements(By.className("category"));
        boolean institutionsReturned = false;
        boolean institutionClickedOn = false;
        for (WebElement category : categories) {
            String sectionName = category.findElement(By.cssSelector("div.name div.name")).getText();
            if (sectionName.equalsIgnoreCase("Community")) {
                institutionsReturned = true;
                List<WebElement> options = category.findElements(By.className("result"));
                for (WebElement option : options) {
                    if (option.findElement(By.className("title")).getText().toLowerCase().equals(attendeeName.toLowerCase())) {
                        option.click();
                        institutionClickedOn = true;
                        waitUntilPageFinishLoading();
                    }
                }
            }
        }
    }

    public void accessSuccessMessageforAddAttendees(String buttonName) {
        if (buttonName.equals("No, I'm Done")) {
            driver.findElement(By.cssSelector("button[class='ui basic primary button']")).click();
        } else if (buttonName.equals("Yes, Add More")) {
            driver.findElement(By.cssSelector("button[id='next-action']")).click();
        } else {
            fail("The given option is not a valid one");
        }
    }

    public void accessCollegeFairOverviewPage(String fairName) {
        while (link("View More Upcoming Events").isDisplayed()) {
            link("View More Upcoming Events").click();
        }
        WebElement viewDetails = driver.findElement(By.xpath("//table[@class='ui table']//tbody//tr/td[text()='" + fairName + "']/parent::tr/td/a[span='View Details']"));
        viewDetails.click();
    }

    public void verifyCollegeFairDetailsPage(DataTable dataTable) {
        Assert.assertTrue("Edit Button is not Displayed", button(By.id("edit-college-fair")).isDisplayed());
        Assert.assertTrue("MESSAGE COLLEGES Button is not Displayed", button(By.id("message-colleges")).isDisplayed());
        Assert.assertTrue("ADD ATTENDEE Button is not Displayed", button(By.id("add-attendee")).isDisplayed());
        Assert.assertTrue("Colleges Attending Heading is not Displayed", driver.findElement(By.xpath(" //h2/span[text()='Colleges Attending']")).isDisplayed());
        Assert.assertTrue("ADD ATTENDEE Button is not Displayed", button(By.id("add-attendee")).isDisplayed());

        //Verify the Headers of the table
        List<String> columns = new ArrayList<>();
        columns.add("Name");
        columns.add("Contact");
        columns.add("Notes");
        columns.add("Status");

        for (String column : columns) {
            Assert.assertTrue("column Name " + column + " is not displaying as expected!", driver.findElement(By.xpath("//table[@id='he-account-dashboard']/thead/tr//th/span[text()='" + column + "']")).isDisplayed());
        }

        List<Map<String, String>> entities = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> collegeFairData : entities) {
            for (String key : collegeFairData.keySet())
                switch (key) {
                    // This is where we verify the values on the screen.
                    case "College Fair Name":
                        String actualCollegeFairName = driver.findElement(By.xpath("//h1[text()='" + collegeFairData.get(key) + "']")).getText();
                        Assert.assertTrue("College Fair Name is not Displayed", actualCollegeFairName.equals(collegeFairData.get(key)));
                        break;
                    case "Date":
                        String actualDate = driver.findElement(By.xpath("//div[@class='thirteen wide column']//p/b")).getText();
                        Assert.assertTrue("Date is not Displayed.", actualDate.equals(collegeFairData.get(key)));
                        break;
                    case "Instructions for College Representatives":
                        String actualInstructionsforCollegeRepresentatives = driver.findElement(By.xpath("//div[@class='column']//p")).getText();
                        Assert.assertTrue("Date is not Displayed.", actualInstructionsforCollegeRepresentatives.equals(collegeFairData.get(key)));
                        break;

                }
        }
    }

    public void verifyListofRegisteredAttendee(DataTable dataTable, String name) {
        int nameID = getColumnIdByFieldName( "//table[@id='he-account-dashboard']//thead/tr/th","Name");
        int rowID = getRowIdByColumnId("//table[@id='he-account-dashboard']//tbody/tr/td", nameID,name);

        List<Map<String, String>> entities = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> collegeFairData : entities) {
            for (String key : collegeFairData.keySet())
                switch (key) {
                    // This is where we verify the values on the screen.
                    case "Name":
                        String actualName = driver.findElement(By.xpath("//table[@id='he-account-dashboard']//tbody/tr["+rowID+"]/td["+nameID+"]")).getText();
                        Assert.assertTrue("College Attendee Name is not Displayed.", actualName.equals(collegeFairData.get(key)));
                        break;
                    case "Contact":
                        int contactID = getColumnIdByFieldName( "//table[@id='he-account-dashboard']//thead/tr/th","Contact");
                        String[] contact = collegeFairData.get(key).split(",");
                        String contactName = contact[0] + contact[1] + contact[2];
                        String actualContact = driver.findElement(By.xpath("//table[@id='he-account-dashboard']//tbody/tr["+rowID+"]/td["+contactID+"]")).getText();
                        Assert.assertTrue("College Contact Name is not Displayed", contact[0].contains(actualContact));
                        Assert.assertTrue("College Email is not Displayed", contact[1].contains(actualContact));
                        Assert.assertTrue("College Phone Number is not Displayed", contact[2].contains(actualContact));
                        break;

                    case "Notes":
                        int notesID = getColumnIdByFieldName( "//table[@id='he-account-dashboard']//thead/tr/th","Notes");
                        String actualNotes = driver.findElement(By.xpath("//table[@id='he-account-dashboard']//tbody/tr["+rowID+"]/td["+notesID+"]")).getText();
                        Assert.assertTrue("Notes is not Displayed.", actualNotes.equals(collegeFairData.get(key)));
                        break;

                    case "Status":
                        int statusID = getColumnIdByFieldName( "//table[@id='he-account-dashboard']//thead/tr/th","Notes");
                        String actualStatus = driver.findElement(By.xpath("//table[@id='he-account-dashboard']//tbody/tr["+rowID+"]/td["+statusID+"]")).getText();
                        Assert.assertTrue("Status is not Displayed.", actualStatus.equals(collegeFairData.get(key)));
                        break;
                   }
        }
    }


    //locators
    private WebElement getAddAttendeeSearchBox() {
        return getDriver().findElement(By.id("multiple-search"));
    }
    private boolean isLinkActive(WebElement link) {
        return link.getAttribute("class").contains("active");
    }
}




