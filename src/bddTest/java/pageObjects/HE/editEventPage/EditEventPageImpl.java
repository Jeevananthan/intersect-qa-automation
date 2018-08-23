package pageObjects.HE.editEventPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;
import utilities.File.CsvFileUtility;

import javax.xml.crypto.Data;
import java.util.List;

public class EditEventPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public EditEventPageImpl() {
        logger = Logger.getLogger(EditEventPageImpl.class);
    }

    public void verifyExportAction(String fileName, DataTable dataTable) {
        waitUntil(ExpectedConditions.visibilityOf(exportAttendeesButton()));
        exportAttendeesButton().click();

        List<String> expectedHeaderValues= dataTable.asList(String.class);
        String home = System.getProperty("user.home");
        waitUntilFileExists(String.format("%s/Downloads/%s",home,fileName));
        String[] actualHeaderValues = CsvFileUtility.getCsvHeaders(String.format("%s/Downloads/%s",home,fileName));
        for(int i=0; i<expectedHeaderValues.size(); i++){
            Assert.assertTrue(String.format("The Csv header is not correct, actual: %s, expected: %s ",
                    actualHeaderValues[i], expectedHeaderValues.get(i)),
                    actualHeaderValues[i].contains(expectedHeaderValues.get(i)));
        }

    }

    //locators
    private WebElement exportAttendeesButton() { return driver.findElement(By.cssSelector("a[class *= 'ui primary button'] span")); }
}
