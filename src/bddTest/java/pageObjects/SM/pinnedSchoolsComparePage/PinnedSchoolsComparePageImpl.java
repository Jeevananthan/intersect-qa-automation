package pageObjects.SM.pinnedSchoolsComparePage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.COMMON.PageObjectFacadeImpl;
import pageObjects.HS.repVisitsPage.RepVisitsPageImpl;
import pageObjects.SM.searchPage.SearchPageImpl;
import pageObjects.SM.surveyPage.SurveyPageImpl;
import utilities.HUBSEditMode.Navigation;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class PinnedSchoolsComparePageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    private Navigation navigation = new Navigation();

    public PinnedSchoolsComparePageImpl() {
        logger = Logger.getLogger(PinnedSchoolsComparePageImpl.class);
    }

    public void verifyHousingInfoIsDisplayedAfterClickingSections(DataTable dataTable) {
        List<String> details = dataTable.asList(String.class);
        for(String element : details) {
            switch (element) {
                case "# of 4 year majors" :
                    number4YearMajorsLink().sendKeys(Keys.RETURN);
                    verifyStudiesTab();
                    break;
                case "Varsity sports" :
                    varsitySportsLink().sendKeys(Keys.RETURN);
                    verifyHousingInfoPage();
                    break;
                case "Male varsity sports" :
                    maleSportsLink().click();
                    verifyHousingInfoPage();
                    break;
                case "Female varsity sports" :
                    femaleSportsLink().click();
                    verifyHousingInfoPage();
                    break;
                case "Clubs and organizations" :
                    clubsAndOrganizationsLink().sendKeys(Keys.RETURN);
                    verifyHousingInfoPage();
                    break;
            }
        }
    }

    public void verifyHousingInfoPage() {
        String originalHandle = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        Assert.assertTrue("The Housing Information section was not displayed", housingInfoSection().isDisplayed());
        navigation.closeNewTabAndSwitchToOriginal(originalHandle);
    }

    public void verifyStudiesTab() {
        String originalHandle = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        waitUntilElementExists(majorsHeaderInStudiesTab());
        Assert.assertTrue("The Studies tab was not displayed", majorsHeaderInStudiesTab().isDisplayed());
        navigation.closeNewTabAndSwitchToOriginal(originalHandle);
    }

    private WebElement number4YearMajorsLink() { return driver.findElement(By.cssSelector("td[aria-label=\"Academics # of 4 Year Majors\"] + td a")); }
    private WebElement majorsHeaderInStudiesTab() { return driver.findElement(By.cssSelector("h3.ng-binding")); }
    private WebElement varsitySportsLink() { return driver.findElement(By.cssSelector("td[aria-label=\"Athletics Levels Available\"] + td > a")); }
    private WebElement housingInfoSection() { return driver.findElement(By.cssSelector("div.student-life-housing-information__header.fc-grid__col.fc-grid__col--xs-12.fc-grid__col--sm-7.fc-grid__col--md-12 h2")); }
    private WebElement maleSportsLink() { return driver.findElement(By.cssSelector("td[aria-label=\"Athletics Levels Available\"] + td ul li:nth-of-type(1)")); }
    private WebElement femaleSportsLink() { return driver.findElement(By.cssSelector("td[aria-label=\"Athletics Levels Available\"] + td ul li:nth-of-type(2)")); }
    private WebElement clubsAndOrganizationsLink() { return driver.findElement(By.cssSelector("td[aria-label=\"Student Life Clubs & Organizations\"] + td a")); }
}
