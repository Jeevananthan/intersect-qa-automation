package pageObjects.SM.surveyPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.List;
import java.util.Map;

public class SurveyPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public SurveyPageImpl() {
        logger = Logger.getLogger(SurveyPageImpl.class);
    }

    // Locators Below
    public WebElement surveySubtitle() {
        return driver.findElement(By.cssSelector("div.newSurveyTitle.customSurveyTitle"));
    }
}
