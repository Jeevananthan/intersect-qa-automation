package pageObjects.HS.RescheduleVisitForm;

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

public class RescheduleVisitFormImpl extends PageObjectFacadeImpl {
    private Logger logger;

    public RescheduleVisitFormImpl() {
        logger = Logger.getLogger(pageObjects.HE.loginPage.LoginPageImpl.class);
    }

    public void verifyValidationsInRescheduleVisitForm(DataTable dataTable) {
        List<List<String>> details = dataTable.asLists(String.class);
        customTimeLink().click();
        Calendar calendarDate = getDeltaDate(1);
        selectDateButton().click();
        pickDateInDatePicker(calendarDate);

        for (List<String> row : details) {
            switch (row.get(0)) {
                case "Start Time" :
                    manualStartTimeField().sendKeys(row.get(1));
                    break;
                case "End Time" :
                    manualEndTimeField().sendKeys(row.get(1));
                    break;
                case "Reschedule Message" :
                    rescheduleMessageField().sendKeys(row.get(1));
                    break;
            }
        }
        softly().assertThat(ExpectedConditions.visibilityOf(disabledRescheduleButton()))
                .as("The 'Reschedule Visit' button is not disabled when wrong data is entered for Start and End Time");
    }

    public void closeRescheduleVisitForm() {
        closeButton().sendKeys(Keys.RETURN);
    }

    //Locators
    public WebElement customTimeLink() { return driver.findElement(By.xpath("//span[text() = 'Want a custom time? Add it manually']")); }
    private WebElement selectDateButton() { return driver.findElement(By.cssSelector("button.ui.small.fluid.button")); }
    private WebElement rescheduleMessageField() { return driver.findElement(By.cssSelector("textarea#rescheduleMessage")); }
    private WebElement manualStartTimeField() { return driver.findElement(By.cssSelector("input#manualStartTime")); }
    private WebElement manualEndTimeField() { return driver.findElement(By.cssSelector("input#manualEndTime")); }
    private WebElement disabledRescheduleButton() { return driver.findElement(By.cssSelector("button[type='submit'].ui.teal.disabled.right.floated.button")); }
    private WebElement closeButton() { return driver.findElement(By.cssSelector("div[class *= 'ui overlay right very wide visible sidebar'] button[aria-label='Close']")); }
}
