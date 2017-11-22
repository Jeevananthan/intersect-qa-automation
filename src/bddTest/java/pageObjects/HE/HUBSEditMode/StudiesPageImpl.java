package pageObjects.HE.HUBSEditMode;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;

import static org.junit.Assert.assertTrue;

public class StudiesPageImpl extends PageObjectFacadeImpl {

    Logger logger = null;

    public StudiesPageImpl() {
        logger = Logger.getLogger(StudiesPageImpl.class);
    }

    public void verifyAllElementsDisplayed() {
       // new WebDriverWait(getDriver(), 30).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.tagName("iframe")));
        boolean result = false;
        if (studentFacultyRatioText().isDisplayed()
            && studentRetentionText().isDisplayed()
            && graduationRateText().isDisplayed()
            && degreesOfferedSection().isDisplayed()
            && topAreasOfStudySection().isDisplayed()
            && majorsOfferedSection().isDisplayed()
            && studyOptionsSection().isDisplayed()) {
            result = true;
        }
        assertTrue("One or more elements are not displayed in Studies tab", result);
        getDriver().switchTo().defaultContent();
    }

    //Locators

    private WebElement studentFacultyRatioText() {
        return (text("Student Faculty Ratio"));
    }
    private WebElement studentRetentionText() {
        return text("Student Retention");
    }
    private WebElement graduationRateText() {
        return text("Graduation Rate");
    }
    private WebElement degreesOfferedSection() {
        return getDriver().findElement(By.xpath("//div[@ng-if='vm.profile.friendlyDegrees.length > 0']"));
    }
    private WebElement topAreasOfStudySection() {
        return getDriver().findElement(By.xpath("//div[@class='hubs-section__content fc-grid__row studies-popular']"));
    }
    private WebElement majorsOfferedSection() {
        return getDriver().findElement(By.xpath("//div[@class='studies-programs__content']"));
    }
    private WebElement studyOptionsSection() {
        return getDriver().findElement(By.xpath("//div[@class='study-options fc-grid__row fc-grid__row--xs-center ng-scope']"));
    }
}
