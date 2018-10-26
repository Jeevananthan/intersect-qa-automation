package pageObjects.HS.ScheduleNewVisitForm;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.io.File;
import java.util.Calendar;
import java.util.List;

public class ScheduleNewVisitFormImpl extends PageObjectFacadeImpl {
    private Logger logger;
    private static String fs = File.separator;
    private static String propertiesFilePath = String.format(".%ssrc%sbddTest%sresources%sScheduleNewVisitErrorMessages%sScheduleNewVisitErrorMessages.properties",fs ,fs ,fs ,fs ,fs);

    public ScheduleNewVisitFormImpl() {
        logger = Logger.getLogger(pageObjects.HE.loginPage.LoginPageImpl.class);
    }

    public void verifyInputValidationsInScheduleNewVisit(DataTable dataTable) {
        List<List<String>> details = dataTable.asLists(String.class);
        for(List<String> row : details) {
            switch (row.get(0)) {
                case "Student Registration Deadline" :
                    if(Integer.parseInt(row.get(2)) == 0) {
                        setRegDeadline(row.get(1), Integer.parseInt(row.get(2)));
                        waitUntilPageFinishLoading();
                        Assert.assertTrue("The number 0 didn't turn into 1 in Student Registration Deadline",
                                regDeadlineTextBox().getAttribute("value").equals("1"));
                    } else if(Integer.parseInt(row.get(2)) > 255) {
                        setRegDeadline(row.get(1), Integer.parseInt(row.get(2)));
                        waitUntilPageFinishLoading();
                        Assert.assertTrue("The big number didn't turn into 255 in Student Registration Deadline",
                                regDeadlineTextBox().getAttribute("value").equals("255"));
                    }
                    break;
                case "Custom Time" :
                    customTimeLink().click();
                    Calendar calendarDate = getDeltaDate(1);
                    selectDateButton().click();
                    pickDateInDatePicker(calendarDate);
                    findRepresentativesTextBox().clear();
                    findRepresentativesTextBox().sendKeys("a");
                    waitUntilPageFinishLoading();
                    List<WebElement> representativesList = driver.findElements(By.cssSelector(representativesListLocator));
                    waitUntilPageFinishLoading();
                    representativesList.get(0).click();
                    getNameTextBox(row.get(1).split("/")[0]).sendKeys(row.get(2));
                    getNameTextBox(row.get(1).split("/")[1]).sendKeys(row.get(2));
                    addVisitButton().click();
                    Assert.assertTrue("The Start Time error message is not displayed",
                            startTimeErrorMessage().getText().equals(getStringFromPropFile(propertiesFilePath, "start.time.error.message")));
                    Assert.assertTrue("The End Time error message is not displayed",
                            endTimeErrorMessage().getText().equals(getStringFromPropFile(propertiesFilePath, "end.time.error.message")));
                    break;
                case "Representative" :

                    if(row.get(1).equals("First Name/Last Name")) {
                        addRepresentativeManually().click();
                        firstNameTextBox().sendKeys(getStringFromPropFile(propertiesFilePath, row.get(2)));
                        lastNameTextBox().sendKeys(getStringFromPropFile(propertiesFilePath, row.get(2)));
                        institutionTextBox().sendKeys(getStringFromPropFile(propertiesFilePath, row.get(2)));
                        lastNameTextBox().sendKeys(Keys.PAGE_DOWN);
                        lastNameTextBox().sendKeys(Keys.PAGE_DOWN);
                        addVisitButton().click();
                        Assert.assertTrue("The error message for First Name is not displayed",
                                firstNameErrorMessage().getText().equals(getStringFromPropFile(propertiesFilePath, "first.name.error.message")));
                        Assert.assertTrue("The error message for Last Name is not displayed",
                                lastNameErrorMessage().getText().equals(getStringFromPropFile(propertiesFilePath, "last.name.error.message")));
                    } else if(row.get(1).equals("Institution")) {
                        institutionTextBox().sendKeys(getStringFromPropFile(propertiesFilePath, row.get(2)));
                        institutionTextBox().sendKeys(Keys.PAGE_DOWN);
                        addVisitButton().click();
                    }
                    break;
            }
        }
    }

    private void setRegDeadline(String hoursOrDays, int numberToSelect) {
        waitUntil(ExpectedConditions.elementToBeClickable(regDeadlineHoursOrDaysButton()));
        if(!regDeadlineHoursOrDaysButton().getText().equals(hoursOrDays)) {
            regDeadlineHoursOrDaysButton().click();
            regDeadlineOption(hoursOrDays).click();
        }
        regDeadlineTextBox().clear();
        regDeadlineTextBox().sendKeys(Integer.toString(numberToSelect));
    }

    public void openRescheduleVisitForm() {
        waitUntil(ExpectedConditions.elementToBeClickable(rescheduleButton()));
        rescheduleButton().click();
    }

    //Locators

    private WebElement regDeadlineHoursOrDaysButton() {
        return getDriver().findElement(By.cssSelector("div[aria-label='Deadline type'] div[role='alert']"));
    }
    private WebElement regDeadlineOption(String optionName) {
        return driver.findElement(By.xpath("//span[text() = '" + optionName + "']"));
    }
    private WebElement regDeadlineTextBox() { return driver.findElement(By.cssSelector("input[aria-label='Student Registration Deadline']")); }
    private WebElement getNameTextBox(String textBoxLabel) { return driver.findElement(By.xpath("//span[text() = '" + textBoxLabel + "']/../../div/div/input")); }
    private WebElement findRepresentativesTextBox() { return driver.findElement(By.cssSelector("input#calendar-search-reps")); }
    private String representativesListLocator = "div.title";
    private WebElement addVisitButton() { return driver.findElement(By.cssSelector("button.ui.teal.right.floated.button")); }
    private WebElement startTimeErrorMessage() { return driver.findElement(By.cssSelector("label[for='manualStartTime'] + div + div span")); }
    private WebElement endTimeErrorMessage() { return driver.findElement(By.cssSelector("label[for='manualEndTime'] + div + div span")); }
    private WebElement firstNameTextBox() { return driver.findElement(By.cssSelector("input#add-rep-first-name")); }
    private WebElement lastNameTextBox() { return driver.findElement(By.cssSelector("input#add-rep-last-name")); }
    private WebElement firstNameErrorMessage() { return driver.findElement(By.cssSelector("label[for='add-rep-first-name'] + div + div span")); }
    private WebElement lastNameErrorMessage() { return driver.findElement(By.cssSelector("label[for='add-rep-last-name'] + div + div span")); }
    private WebElement institutionTextBox() { return driver.findElement(By.cssSelector("input#add-rep-institution")); }
    private WebElement customTimeLink() { return driver.findElement(By.xpath("//span[text() = 'Want a custom time? Add it manually']")); }
    private WebElement addRepresentativeManually() { return driver.findElement(By.xpath("//span[text() = 'Not in the list? Add them manually']")); }
    private WebElement selectDateButton() { return driver.findElement(By.cssSelector("button.ui.small.fluid.button")); }
    private WebElement rescheduleButton() { return driver.findElement(By.cssSelector("button[class *= 'ui button _3vZDfsYYTrAHlC39ZjUDHc']")); }
}
