package pageObjects.HE.repVisitsPage;

import cucumber.api.DataTable;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.GlobalSearch;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import utilities.GetProperties;

public class RepVisitsPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public void checkRepVisitsSubTabs(DataTable dataTable){
        navBar.goToRepVisits();
        List<String> list = dataTable.asList(String.class);
        for (String repVisitsSubItem : list) {
            Assert.assertTrue(repVisitsSubItem + " is not showing.",link(repVisitsSubItem).isDisplayed());
        }
    }
    public void checkHighSchoolPopUp(DataTable dataTable){
        List<Map<String, String>> entities = dataTable.asMaps(String.class, String.class);
        for (Map<String,String> school : entities){
            for (String key : school.keySet()) {
                switch (key) {
                    case "School Name":
                        String header = driver.findElement(By.className("_2XeJUEZaR8a0YG2vHm_ZA5")).getText();
                        Assert.assertTrue("School name was not found in header.", header.contains(school.get(key)));
                        break;
                    case "High School Contact:":
                        String contactLink = driver.findElement(By.className("_2-cmgeOoondfKX-iEm8xCP")).getText();
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

    private void validateInfolink(){
        link("For more information:").click();
        Assert.assertTrue("Did not end up on Community URL!", driver.getCurrentUrl().contains("counselor-community/institution"));
    }
    public void verifySearchAndSchedulePage() {
        navBar.goToRepVisits();
        getSearchAndScheduleBtn().click();
        WebElement dateBar = driver.findElement(By.className("_2Y4XoXCJpDOFoe0UYkEn-I"));
        Assert.assertTrue("Previous Week button is not present!",dateBar.findElement(By.cssSelector("[aria-label='Previous week']")).isDisplayed());
        Assert.assertTrue("Next Week button is not present!",dateBar.findElement(By.cssSelector("[aria-label='Next week']")).isDisplayed());
        Assert.assertTrue("Calendar button is not present!",dateBar.findElement(By.className("calendar")).isDisplayed());
        Assert.assertTrue("Placeholder text for search box was not present!", textbox("Enter a school name or location").isDisplayed());
    }

    public void searchforHighSchool(String schoolName) {
        navBar.goToRepVisits();
        getSearchAndScheduleBtn().click();
        getSearchBox().sendKeys(schoolName);
        getSearchButton().click();
    }

    public void selectHighSchoolFromIntermediateSearchResults(String schoolName, String location) {
        WebElement schoolLocation = text(location);
        getParent(schoolLocation).findElement(By.tagName("a")).click();
        waitUntilPageFinishLoading();
    }

    public void selectHighSchoolFromResults(String schoolName) {
        waitUntilPageFinishLoading();
        getDriver().findElement(By.xpath("//a[text()='" + schoolName + "']")).click();
        waitUntilPageFinishLoading();
    }

    public void viewMapPlugin() {
        getMapButton().click();
        waitUntilPageFinishLoading();
    }

    public void verifyOverviewPage(){
        navBar.goToRepVisits();
        getOverviewBtn().click();
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
        if (startDate.contains("am") || startDate.contains("pm")) {
            Assert.assertTrue("The Start time does not have a correct format: " + startDate, startDate.matches("([0-9])?([0-9]):([0-9])?([0-9])([ap])([m])"));
        } else {
            Assert.assertTrue("The Start time does not have a correct format: " + startDate, startDate.matches("([0-9])?([0-9]):([0-9])?([0-9])"));
        }
        Assert.assertTrue("The End time does not have a correct format: " + endDate, endDate.matches("([0-9])?([0-9]):([0-9])?([0-9])([ap])([m])"));
        Assert.assertTrue("The time zone does not have a correct format: " + timeZone, timeZone.matches("([ABCDEFGHIJKLMNOPQRSTUVWXYZ])([ABCDEFGHIJKLMNOPQRSTUVWXYZ])([ABCDEFGHIJKLMNOPQRSTUVWXYZ])"));
        Assert.assertTrue("The High School name is not displayed", requestText().getText().contains(highSchoolName));
        Assert.assertTrue("The confirmation button's text is not correct: " + submitRequestButton().getText(), submitRequestButton().getText().equals("YES, REQUEST THIS TIME"));
        Assert.assertTrue("The cancel button's text is not correct", cancelButton().getText().equals("CANCEL"));
    }

    public void closeFairRequestPopup() {
        cancelButton().click();
    }

    public void clickFairsTab() {
        fairsTab().click();
    }

    public void openFairsInChckRepVisitsAv() {
        waitUntil(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.tagName("iframe")));
        waitUntil(ExpectedConditions.elementToBeClickable(getCheckRepVisitsAvailabilityButton()));
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
        Assert.assertTrue("", upperMessage().getText().trim().equals("Fair registration requested! You will " +
                "receive an email notification when your request has been confirmed."));
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
        if (showMoreLink().isDisplayed()) {
            showMoreLink().click();
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
        calendarIcon().click();
        pressMiniCalendarArrowUntil("right", date.split(",")[0], 10);
        miniCalendarDayCell(date.split(" ")[1]).click();
        waitUntil(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.ui.medium.inverted.loader")));
        for (WebElement fairElement : quickViewEventsList()) {
            if (fairElement.getText().contains(schoolName) && fairElement.getText().contains(date.split(" ")[2].toLowerCase())) {
                result = true;
            }
        }
        Assert.assertTrue("The fair is not displayed in the Quick View", result);
    }

    public void pressMiniCalendarArrowUntil(String direction, String month, int tries) {
        for (int i = 0; i < tries; i++) {
            if (!miniCalendarHeader().getText().split(" ")[0].contains(month)) {
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
        getDriver().get(GetProperties.get("he.app.url") + "counselor-community/institution/" + URLPartID);
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
    public void selectSchoolFromMap(String schoolName) {
        button(schoolName).click();
        waitUntilPageFinishLoading();
    }




    public void verifyUpgradeMessageInTravelPlanInRepVisits(){

        navBar.goToRepVisits();
        getTravelPlanBtn().click();
        Assert.assertTrue("'Premium Feature' text is not displayed",text("Premium Feature").isDisplayed());
        Assert.assertTrue("'UPGRADE' text is not displayed",text("UPGRADE").isDisplayed());
        Assert.assertTrue("'Lock' Icon is not displayed",driver.findElement(By.cssSelector(" i[class='icons']")).isDisplayed());
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
    private WebElement getRecommendationsBtn() {
        return link("Recommendations");
    }
    private WebElement getNotificationsBtn() {
        return link("Notifications");
    }
    private WebElement getSearchBox() { return textbox("Search by school name or location...");}
    private WebElement getSearchButton() { return driver.findElement(By.className("_3pWea2IV4hoAzTQ12mEux-"));}
    private WebElement getMapButton() { return driver.findElement(By.cssSelector("[class='map outline big icon']"));}
    private WebElement getComingSoonMessageInOverviewPage(){ return driver.findElement(By.className("_9SnX9M6C12WsFrvkMMEZR")); }
    private WebElement getCheckRepVisitsAvailabilityButton(){ return driver.findElement(By.cssSelector("a.check-repvisits-link")); }
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
    private WebElement calendarIcon() { return getDriver().findElement(By.cssSelector("i.teal.calendar.large.link.icon")); }
    private WebElement miniCalendarHeader() { return getDriver().findElement(By.cssSelector("div.DayPicker-Caption")); }
    private WebElement miniCalendarRightButton() { return getDriver().findElement(By.cssSelector("span[aria-label=\"Next Month\"]")); }
    private WebElement miniCalendarLeftButton() { return getDriver().findElement(By.cssSelector("span[aria-label=\"Previous Month\"]")); }
    public WebElement miniCalendarDayCell(String day) { return getDriver().findElement(By.xpath("//div[@class='DayPicker-Body']/div/div[not(@class='DayPicker-Day DayPicker-Day--outside')][text()='" + day + "']")); }
    public WebElement showMoreLink() { return getDriver().findElement(By.cssSelector("a.rbc-show-more")); }
    private List<WebElement> overlayEventsList() { return getDriver().findElements(By.cssSelector("div.rbc-overlay div.rbc-event")); }
    private WebElement visitsTab() { return getDriver().findElement(By.cssSelector("div.ui.left.attached.button")); }
    private WebElement goToDateButton() { return getDriver().findElement(By.cssSelector("button.ui.right.labeled.small.basic i")); }
    private WebElement searchAndScheduleCalendarIcon() { return getDriver().findElement(By.cssSelector("button.ui.right.labeled.tiny.icon.button i")); }
    private List<WebElement> headerWeekDays() { return getDriver().findElements(By.cssSelector("table.ui.fixed.unstackable.basic.center thead th div span")); }
    private List<WebElement> visitsRows() { return getDriver().findElements(By.cssSelector("table.ui.fixed.unstackable.basic.center tbody tr")); }
    private List<WebElement> eventsRows(String day) { return getDriver().findElements(By.xpath("//div[not(contains(@class, 'rbc-off-range'))][contains(@class,'rbc-date-cell')]/a[text()='" + day + "']/../../following-sibling::div")); }
}


