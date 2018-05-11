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
    static String originalHandle;

    public SurveyPageImpl() {
        logger = Logger.getLogger(SurveyPageImpl.class);
    }

    public void verifySurveyURL(String URL) {
        Assert.assertTrue("The URL of the survey page is incorrect: " + driver.getCurrentUrl(), driver.getCurrentUrl().equals(URL));
    }

    public void verifySurvey() {
        originalHandle = openSurvey();
        Assert.assertTrue("The survey is not displayed", surveySubtitle().isDisplayed());
        closeSurvey();
    }

    public String openSurvey() {
        waitForUITransition();
        waitForUITransition();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector(smSurveyButtonLocator), 1));
        smSurveyButton().click();
        String winHandleBefore = driver.getWindowHandle();
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }
        originalHandle = winHandleBefore;
        return winHandleBefore;
    }

    public void closeSurvey() {
        if (driver.getWindowHandles().size() > 1) {
            driver.close();
        }
        driver.switchTo().window(originalHandle);
    }

    // Locators Below
    public WebElement surveySubtitle() {
        return driver.findElement(By.cssSelector("div.newSurveyTitle.customSurveyTitle"));
    }
    public WebElement smSurveyButton() {
        return driver.findElement(By.cssSelector(smSurveyButtonLocator));
    }
    private String smSurveyButtonLocator = "span[class=\"button\"]";
}
