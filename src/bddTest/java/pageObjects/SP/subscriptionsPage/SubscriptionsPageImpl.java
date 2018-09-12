package pageObjects.SP.subscriptionsPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.Calendar;
import java.util.List;

public class SubscriptionsPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public SubscriptionsPageImpl() {
        logger = Logger.getLogger(SubscriptionsPageImpl.class);
    }

    public void addNewSubscription(String type) {
        addNewSubscriptionButton().click();
        waitUntilPageFinishLoading();
        driver.findElement(By.xpath(subscriptionTypeRadioButtonLocator(type))).click();
        waitUntilPageFinishLoading();
        driver.findElement(By.cssSelector(nextButton)).click();
        waitUntilPageFinishLoading();
    }

    public void verifyBackButtonFunctionality() {
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector(backButton), 1));
        driver.findElement(By.cssSelector(backButton)).click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("The Back button is not working as expected",
                driver.findElement(By.xpath(subscriptionTypeRadioButtonLocator("State"))).isDisplayed());
    }

    public void fillNewSubscriptionForm(DataTable dataTable) {
        List<List<String>> details = dataTable.asLists(String.class);
        for (List<String> row : details) {
            switch(row.get(0)) {
                case "State" :
                    if (!formHeaderText().getText().equals("Add New Zip Subscription")) {
                        chooseStateDropdown().click();
                        waitUntilPageFinishLoading();
                        stateDropdownOption(row.get(1)).click();
                        chooseStateLabel().click();
                    }
                    break;
                case "Counties" :
                    if (!row.get(1).equals("None")) {
                        countiesField().click();
                        countiesOption(row.get(1)).click();
                        chooseStateLabel().click();
                    }
                    break;
                case "Diversity Filter" :
                    waitUntilPageFinishLoading();
                    diversityFilterDropdown().click();
                    waitUntilPageFinishLoading();
                    diversityFilterDropdownOption(row.get(1)).click();
                    break;
                case "Competitors" :
                    competitorsField().sendKeys(row.get(1));
                    waitUntilPageFinishLoading();
                    competitorsOption(row.get(1));
                    break;
                case "Majors" :
                    majorsCheckbox().click();
                    break;
                case "Connection" :
                    connectionCheckbox().click();
                    break;
                case "Start date" :
                    startDate().click();
                    Calendar futureDate = getDeltaDate(Integer.parseInt(row.get(1).split(" ")[0]));
                    pickDateInDatePicker(futureDate);
                    startDate().click();
                    break;
                case "End date" :
                    endDate().click();
                    Calendar futureEndDate = getDeltaDate(Integer.parseInt(row.get(1).split(" ")[0]));
                    pickDateInDatePicker(futureEndDate);
                    endDate().click();
                    break;
                case "Zips" :
                    if (!row.get(1).equals("None")) {
                        chooseZipsField().sendKeys(row.get(1));
                    }
                    break;
                case "Radius from zips" :
                    if (!row.get(1).equals("None")) {
                        radiusFromZipsField().clear();
                        radiusFromZipsField().sendKeys(row.get(1));
                    }
            }
        }
    }

    protected void pickDateInDatePicker(Calendar date) {
        Calendar todaysDate = Calendar.getInstance();

        String dateString = getDay(date);
        if (Character.valueOf(dateString.charAt(0)).equals('0')) {
            dateString = dateString.substring(1);
        }

        if (date.before(todaysDate)) {
            int monthDifference = Integer.parseInt(getMonthNumber(todaysDate)) - Integer.parseInt(getMonthNumber(date));
            for (int i = 0; i < monthDifference; i++) {
                datePickerPrevMonthButton().click();
            }
        } else if (date.after(todaysDate)) {
            int monthDifference = Integer.parseInt(getMonthNumber(date)) - Integer.parseInt(getMonthNumber(todaysDate));
            for (int i = 0; i < monthDifference; i++) {
                datePickerNextMonthButton().click();
            }
        }
        waitForUITransition();
        dateInCalendar(dateString).click();
    }

    public void clickFinish() {
        finishButton().click();
    }

    public void verifyNewSubscription(DataTable dataTable) {
        String location = "";
        String diversity = "";
        String startDate = "";
        List<List<String>> details = dataTable.asLists(String.class);
        for (List<String> row : details) {
            switch (row.get(0)) {
                case "Location" :
                    location = row.get(1);
                    break;
                case "Diversity" :
                    diversity = row.get(1);
                    break;
                case "Start Date" :
                    Calendar futureDate = getDeltaDate(Integer.parseInt(row.get(1).split(" ")[0]));
                    startDate = getMonth(futureDate).substring(0, 3) + " " + getDay(futureDate) + ", " + getYear(futureDate);
            }
        }
        Assert.assertTrue("The subscription was not added", subscriptionStartDateInTable(location, diversity, startDate).isDisplayed());
    }

    public void verifyValueRadiusFromZips(String expectedValue) {
        Assert.assertTrue("The value in Radius From Zips field is not correct", radiusFromZipsField().getAttribute("value").equals(expectedValue));
    }

    //Locators
    private WebElement addNewSubscriptionButton() { return driver.findElement(By.cssSelector("h2.light-title + div[class " +
            "*= 'he-account-links'] a[class *= 'ui teal basic button link-button']:nth-of-type(2)")); }

    private String subscriptionTypeRadioButtonLocator(String subscriptionType) { return "//label[text() = '" + subscriptionType + "']"; }

    private String nextButton = "button.ui.primary.button";

    private String backButton = "button#action-back";

    private WebElement chooseStateDropdown() { return driver.findElement(By.cssSelector("div#field13")); }

    private WebElement stateDropdownOption(String optionName) { return driver.findElement(By.xpath("//div/span[text() = '" + optionName + "']")); }

    private WebElement chooseStateLabel() { return driver.findElement(By.cssSelector("label[for='field13']")); }

    private WebElement startDate() { return driver.findElement(By.cssSelector("button#field18")); }

    private WebElement endDate() { return driver.findElement(By.cssSelector("button#field19")); }

    private WebElement finishButton() { return driver.findElement(By.cssSelector("button#action-submit")); }

    private WebElement calendarMonthText() { return driver.findElement(By.cssSelector("select#month-select")); }

    private WebElement calendarYearText() { return driver.findElement(By.cssSelector("select#year-select")); }

    private WebElement dateInCalendar(String dateString) { return driver.findElement(By.xpath("//div[@class='DayPicker-Day' or @class='DayPicker-Day DayPicker-Day--today'" +
            "or @class='DayPicker-Day DayPicker-Day--selected' or @class = 'DayPicker-Day DayPicker-Day--selected " +
            "DayPicker-Day--today'][text()='" + dateString + "']")); }

    private WebElement diversityFilterDropdown() { return driver.findElement(By.cssSelector("div#field14")); }

    private WebElement diversityFilterDropdownOption(String optionName) { return driver.findElement(By.xpath("//div[@class = 'menu transition visible']/div[@role = 'option']/span[text() = '" + optionName + "']")); }

    private WebElement competitorsField() { return driver.findElement(By.cssSelector("input#field15")); }

    private WebElement competitorsOption(String optionName) { return driver.findElement(By.xpath("//div[contains(@class, 'content')][contains(text(), '" + optionName + "')]")); }

    private WebElement majorsCheckbox() { return driver.findElement(By.cssSelector("input#field16")); }

    private WebElement connectionCheckbox() { return driver.findElement(By.cssSelector("input#field17")); }

    private WebElement chooseZipsField() { return driver.findElement(By.cssSelector("input#field21")); }

    private WebElement radiusFromZipsField() { return driver.findElement(By.cssSelector("input#field22")); }

    private WebElement formHeaderText() { return driver.findElement(By.cssSelector("h1.header span")); }

    private WebElement countiesField() { return driver.findElement(By.cssSelector("div[id *= 'field20']")); }

    private WebElement countiesOption(String optionName) { return driver.findElement(By.xpath("//span[text() = '" + optionName + "']")); }

    private WebElement subscriptionStartDateInTable(String location, String diversity, String startDate) {
        return driver.findElement(By.xpath("//tbody/tr[1]/td[1]/div[text() = '" + location + "']/../../td[text() = '" + diversity + "']/../td[6]/span[text() = '" + startDate + "']"));}
}


