package pageObjects.HUBS;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;

public class NavianceCollegeProfilePageImpl extends PageObjectFacadeImpl{

    Logger logger = null;

    public NavianceCollegeProfilePageImpl() {
        logger = Logger.getLogger(NavianceCollegeProfilePageImpl.class);
    }

    public void openHUBSEditorMode() {
        navBar.goToCollegeProfile();
        verifyVieworEditYourCollegeProfileinNaviance();
        getStartedButton().click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.tagName("iframe")));
        driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
        verifyInstitutionalProfilePage();
        waitUntilPageFinishLoading();
        logger.info("HUBS Editor Mode opened");
    }

    public void verifyVieworEditYourCollegeProfileinNaviance(){
        Assert.assertTrue("Page Title 'View or Edit Your College Profile in Naviance' is not dsiplayed",text("View or Edit Your College Profile in Naviance").isDisplayed());
        Assert.assertTrue("Welcome Text 1 is not displayed",driver.findElement(By.xpath("//p/span[text()='In Intersect, higher education users can manage, and add to, the information provided on their college profile in Family Connection.']")).isDisplayed());
        Assert.assertTrue("Welcome Text 2 is not displayed",driver.findElement(By.xpath("//p/span[text()='Family Connection is the student and parent portal that is part of the larger Naviance system - a comprehensive K-12 college and career readiness solution that helps districts and schools align student strengths and interests to postsecondary goals.']")).isDisplayed());
        Assert.assertTrue("Welcome Text 3 is not displayed",driver.findElement(By.xpath("//p/span[text()='College profiles can be displayed to students and parents in Family Connection as early as 6th grade, and are used through 12th grade to manage the college research and application process.']")).isDisplayed());
        Assert.assertTrue("'Get Started' button is not displayed",getStartedButton().isDisplayed());
    }

    public void verifyInstitutionalProfilePage(){
        Assert.assertTrue("'Edit your college profile' page title is not displayed",driver.findElement(By.xpath("//h3[text()='Edit your college profile']")).isDisplayed());
        Assert.assertTrue("Text 'To get started – choose a category.'",driver.findElement(By.xpath("//div[text()='To get started – choose a category.']")).isDisplayed());
        Assert.assertTrue("Text 'This College Profile Page is using mock student data to replicate a student experience.' is not displayed",driver.findElement(By.xpath("//div/span[text()='This College Profile Page is using mock student data to replicate a student experience.']")).isDisplayed());
        Assert.assertTrue("Button 'Publish' is not displayed",button("Publish").isDisplayed());
    }
    //Locators
    private WebElement getStartedButton() {
        return button("Get Started");
    }

}
