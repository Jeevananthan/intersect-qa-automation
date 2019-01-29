package pageObjects.SP.subscriptionsPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SubscriptionsPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public SubscriptionsPageImpl() {
        logger = Logger.getLogger(SubscriptionsPageImpl.class);
    }

    public void verifyBackButtonFunctionality() {
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector(backButton), 1));
        driver.findElement(By.cssSelector(backButton)).click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("The Back button is not working as expected",
                driver.findElement(By.xpath(subscriptionTypeRadioButtonLocator("State"))).isDisplayed());
    }

    public void fillNewSubscriptionForm(DataTable dataTable) {
        Actions actions = new Actions(getDriver());
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
                    waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath(competitorsOption(row.get(1))), 1));
                    driver.findElement(By.xpath(competitorsOption(row.get(1)))).click();
                    break;
                case "Majors" :
                    majorsCheckbox().click();
                    break;
                case "Connection" :
                    connectionCheckbox().click();
                    break;
                case "Start date" :
                    startDate().click();
                    String futureDate = row.get(1).split(" ")[0];
                    setStartDate(futureDate);
                    startDate().click();
                    break;
                case "End date" :
                    endDate().click();
                    String futureEndDate = row.get(1).split(" ")[0];
                    setEndDate(futureEndDate);
                    endDate().click();
                    break;
                case "Zips" :
                    if (!row.get(1).equals("None")) {
                        chooseZipsField().clear();
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

    public void setStartDate(String delta){
        Calendar cal = getDeltaDate(Integer.parseInt(delta));

        String month = getMonth(cal);
        String dateNo = getDay(cal);
        if (dateNo.startsWith("0"))
            dateNo = dateNo.substring(1);
        String year = getYear(cal);
        Select selectYear = new Select(driver.findElement(By.id("year-select")));
        selectYear.selectByVisibleText(year);

        Select selectMonth = new Select(driver.findElement(By.id("month-select")));
        selectMonth.selectByVisibleText(month);

        WebElement dateTemp = getCalender().findElement(By.xpath("//div[text()='"+dateNo+"']"));
        dateTemp.click();
    }

    public void setEndDate(String delta){
        Calendar cal = getDeltaDate(Integer.parseInt(delta));

        String month = getMonth(cal);
        String dateNo = getDay(cal);
        if (dateNo.startsWith("0"))
            dateNo = dateNo.substring(1);
        String year = getYear(cal);

        Select selectYear = new Select(driver.findElement(By.id("year-select")));
        selectYear.selectByVisibleText(year);

        Select selectMonth = new Select(driver.findElement(By.id("month-select")));
        selectMonth.selectByVisibleText(month);

        WebElement dateTemp = getCalender().findElement(By.xpath("//div[text()='"+dateNo+"']"));
        dateTemp.click();
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
        if (getDriver().findElements(By.cssSelector("div.ui.error.message")).size() == 0)
            waitUntil(ExpectedConditions.numberOfElementsToBe(By.id("subscription-modal"),0));
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
        Assert.assertTrue("The subscription was not added", driver.findElement(By.xpath(subscriptionRemoveButton(diversity, startDate))).isDisplayed());
    }

    public void clickNextButton() {
        driver.findElement(By.cssSelector(nextButton)).click();
    }

    public void selectRadioButton(String buttonLabel) {
        driver.findElement(By.xpath(subscriptionTypeRadioButtonLocator(buttonLabel))).click();
    }

    public void deleteSubscription(DataTable dataTable) {
        waitUntilPageFinishLoading();
        List<List<String>> details = dataTable.asLists(String.class);
        String location = "";
        String diversity = "";
        String startDate = "";

        List<WebElement> subList = driver.findElements(By.xpath(subscriptionRemoveButton(diversity, startDate)));
        int sizeOfSubList = subList.size();
        for (List<String> row : details) {
            switch (row.get(0)) {
                case "Diversity":
                    diversity = row.get(1);
                    break;
                case "Start Date":
                    Calendar futureDate = getDeltaDate(Integer.parseInt(row.get(1).split(" ")[0]));
                    startDate = getMonth(futureDate).substring(0, 3) + " " + getDay(futureDate) + ", " + getYear(futureDate);
                    break;

            }
        }
            waitUntilPageFinishLoading();
            driver.findElement(By.xpath(subscriptionRemoveButton(diversity, startDate))).click();
            waitUntilPageFinishLoading();
            deleteButton().click();
    }

    public void verifyValueRadiusFromZips(String expectedValue) {
        Assert.assertTrue("The value in Radius From Zips field is not correct", radiusFromZipsField().getAttribute("value").equals(expectedValue));
    }

    public void deleteMultipleSubscriptions(){
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/h2[text()='Advanced Awareness and Connections Subscriptions']")));
        List<WebElement> buttonList = driver.findElements(By.cssSelector(removeButtonListLocator));
        for (WebElement removeButton : buttonList){
            waitUntilPageFinishLoading();
            removeButton.click();
            waitUntilPageFinishLoading();
            deleteButton().click();
            waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/h2[text()='Advanced Awareness and Connections Subscriptions']")));
        }
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector(removeButtonListLocator),0 ));




    }

    public void openSubscription(String subscriptionName){
        driver.navigate().refresh();
        waitUntilPageFinishLoading();
        clickSubscriptionName(subscriptionName).click();

    }

    /**
     * verify Major checkbox can be checked
     */
    public void verifyMajorsCheckboxCanBeChecked(){
        boolean check = getCheckBoxLabelByText("Majors").isSelected();
        if(check==false){
            getCheckBoxElementByText("Majors").click();
        }
        moveToElement(getCheckBoxLabelByText("Majors"));
        Assert.assertTrue("CheckBox 'Majors' is not checked", getCheckBoxLabelByText("Majors").isSelected());
    }
    /**
     * verify Major checkbox can be Unchecked
     */
    public void verifyMajorsCheckboxCanBeUnchecked(){
        boolean check = getCheckBoxLabelByText("Majors").isSelected();
        if(check==true){
            getCheckBoxElementByText("Majors").click();
        }
        moveToElement(getCheckBoxLabelByText("Majors"));
        Assert.assertFalse("CheckBox 'Majors' is checked", getCheckBoxLabelByText("Majors").isSelected());
    }
    /**
     * verify Connection checkbox can be checked
     */
    public void verifyConnectionCheckboxCanBeChecked(){
        boolean check = getCheckBoxLabelByText("Connection").isSelected();
        if(check==false){
            getCheckBoxElementByText("Connection").click();
        }
        moveToElement(getCheckBoxLabelByText("Connection"));
        Assert.assertTrue("CheckBox 'Connection' is not checked", getCheckBoxLabelByText("Connection").isSelected());
    }
    /**
     * verify Connection checkbox can be Unchecked
     */
    public void verifyConnectionCheckboxCanBeUnchecked(){
        boolean check = getCheckBoxLabelByText("Connection").isSelected();
        if(check==true){
            getCheckBoxElementByText("Connection").click();
        }
        moveToElement(getCheckBoxLabelByText("Connection"));
        Assert.assertFalse("CheckBox 'Connection' is checked", getCheckBoxLabelByText("Connection").isSelected());
    }
    //Locators

    private String subscriptionTypeRadioButtonLocator(String subscriptionType) { return "//label[text() = '" + subscriptionType + "']"; }

    private String nextButton = "button.ui.primary.button";

    private String backButton = "button#action-back";

    private WebElement chooseStateDropdown() { return driver.findElement(By.cssSelector("div#field13")); }

    private WebElement stateDropdownOption(String optionName) { return driver.findElement(By.xpath("//div/span[text() = '" + optionName + "']")); }

    private WebElement chooseStateLabel() { return driver.findElement(By.cssSelector("label[for='field13']")); }

    private WebElement startDate() { return driver.findElement(By.cssSelector("button#field18")); }

    private WebElement endDate() { return driver.findElement(By.cssSelector("button#field19")); }

    private WebElement finishButton() { return driver.findElement(By.cssSelector("button#action-submit")); }

    private WebElement dateInCalendar(String dateString) { return driver.findElement(By.xpath("//div[@class='DayPicker-Day' or @class='DayPicker-Day DayPicker-Day--today'" +
            "or @class='DayPicker-Day DayPicker-Day--selected' or @class = 'DayPicker-Day DayPicker-Day--selected " +
            "DayPicker-Day--today'][text()='" + dateString + "']")); }

    private WebElement diversityFilterDropdown() { return driver.findElement(By.cssSelector("div#field14")); }

    private WebElement diversityFilterDropdownOption(String optionName) { return driver.findElement(By.xpath("//div[@class = 'visible menu transition']/div[@role = 'option']/span[text() = '" + optionName + "']")); }

    private WebElement competitorsField() { return driver.findElement(By.cssSelector("input#field15")); }

    private String competitorsOption(String optionName) { return "//div[contains(@class, 'content')][contains(text(), '" + optionName + "')]"; }

    private WebElement majorsCheckbox() { return driver.findElement(By.cssSelector("input#field16")); }

    private WebElement connectionCheckbox() { return driver.findElement(By.cssSelector("input#field17")); }

    private WebElement chooseZipsField() { return driver.findElement(By.cssSelector("input#field21")); }

    private WebElement radiusFromZipsField() { return driver.findElement(By.cssSelector("input#field22")); }

    private WebElement formHeaderText() { return driver.findElement(By.cssSelector("h1.header span")); }

    private WebElement countiesField() { return driver.findElement(By.cssSelector("div[id *= 'field20']")); }

    private WebElement countiesOption(String optionName) { return driver.findElement(By.xpath("//span[text() = '" + optionName + "']")); }

    private String subscriptionRemoveButton(String diversity, String startDate) {
        return String.format("//td[text()='%s']/preceding-sibling::td/following-sibling::td/span[text()='%s']/ancestor::tr/td/button",diversity,startDate);
    }

    private String removeButtonListLocator = "button.ui:not(.icon)";

    private WebElement deleteButton() {
        return driver.findElement(By.cssSelector("button[class *= 'ui teal basic button']"));

    }

    private WebElement clickSubscriptionName(String subName){
        return  driver.findElement(By.xpath("//a[text()='" + subName + "']"));
    }
    private WebElement getCalender(){ return driver.findElement(By.xpath("//div[@role='application']")); }
    /**
     *
     * @param checkboxText : element to verify(ex: Majors )
     * @return : checkbox element
     */
    private WebElement getCheckBoxLabelByText(String checkboxText) {
        return getDriver().findElement(By.xpath("//label[text()='" + checkboxText + "']/../input"));
    }

    /**
     *
     * @param checkboxText : element to check(ex: Majors )
     * @return : checkbox element
     */
    private WebElement getCheckBoxElementByText(String checkboxText) {
        return getDriver().findElement(By.xpath("//label[text()='" + checkboxText + "']/.."));
    }

    /**
     * Move to the given element using javascript
     * @param element : get the element to move
     */
    public void moveToElement(WebElement element) {
        Actions builder = new Actions(driver);
        builder.moveToElement(element).build().perform();
    }
}



