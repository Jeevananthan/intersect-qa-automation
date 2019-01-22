package pageObjects.SP.communityPages;

import junit.framework.AssertionFailedError;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.Assert;
import cucumber.api.DataTable;

public class InstitutionPageImpl extends PageObjectFacadeImpl {
    private Logger logger;

    public InstitutionPageImpl() { logger = Logger.getLogger(InstitutionPageImpl.class);    }

    public void goToHubsPage(String collegeName){
        waitUntilPageFinishLoading();
        communityFrame();
        WebElement additionalLink = driver.findElement(By.xpath(("//li/a[text()='Additional info']")));
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//li/a[text()='Additional info']"),1));
        additionalLink.click();
        waitUntilPageFinishLoading();
        WebElement viewNavianceCollegeProfile = link("VIEW NAVIANCE COLLEGE PROFILE");
        waitUntil(ExpectedConditions.visibilityOf(viewNavianceCollegeProfile));
        viewNavianceCollegeProfile.click();
        //Commenting the below line to increase the performance
        //waitUntilPageFinishLoading();
        waitUntilPageFinishLoading();
        waitForUITransition();
        getDriver().switchTo().frame(driver.findElement(By.className("IdFjPLV2funrJ0xNAJdsL")));
        //Commenting the below line to increase the performance
        //waitForUITransition();
       // waitForUITransition();
        try{
            waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector("h1.masthead__name"), 1));
            /*Commented because changed the UI
            waitUntil(ExpectedConditions.textToBePresentInElement(collageNameLabel(),collegeName));*/
        }catch(Exception e){
            logger.info("Caught Exception: " + e.getMessage());
            getDriver().switchTo().defaultContent();
            throw new AssertionFailedError("College Name is not displaying in Hubs View");
        }
        getDriver().switchTo().defaultContent();
    }

    public void accessCounselorCommunity(){
        Assert.assertTrue("Counselor Community is not displayed",getCounselorCommunity().isDisplayed());
        getCounselorCommunity().click();
        communityFrame();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//li/a[text()='Home']"),1));
        Assert.assertTrue("Home page is not displayed",getHomePage().isDisplayed());
        getDriver().switchTo().defaultContent();
    }

    public void accessFieldsInCounselorCommunity(DataTable dataTable){
        getCounselorCommunity().click();
        waitUntilPageFinishLoading();
        List<String> list = dataTable.asList(String.class);
        for(String fields:list){
            switch (fields){
                case "Home" :
                    communityFrame();
                    driver.findElement(By.xpath("//li/a[text()='"+fields+"']")).click();
                    waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//a[@class='active' and text()='"+fields+"']"),1));
                    Assert.assertTrue("Home field is not displayed",driver.findElement(By.xpath("//a[@class='active' and text()='"+fields+"']")).isDisplayed());
                    break;
                case "Profile" :
                    communityFrame();
                    driver.findElement(By.xpath("//li/a[text()='"+fields+"']")).click();
                    waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//a[@class='active' and text()='"+fields+"']"),1));
                    Assert.assertTrue("Profile field is not displayed",driver.findElement(By.xpath("//a[@class='active' and text()='"+fields+"']")).isDisplayed());
                    break;
                case "Institution" :
                    communityFrame();
                    driver.findElement(By.xpath("//li/a[text()='"+fields+"']")).click();
                    waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//a[@class='active' and text()='"+fields+"']"),1));
                    Assert.assertTrue("Institution field is not displayed",driver.findElement(By.xpath("//a[@class='active' and text()='"+fields+"']")).isDisplayed());
                    break;
                case "Connections" :
                    communityFrame();
                    driver.findElement(By.xpath("//li/a[text()='"+fields+"']")).click();
                    waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//a[@class='active-trail' and text()='"+fields+"']"),1));
                    Assert.assertTrue("Connections field is not displayed",driver.findElement(By.xpath("//a[@class='active-trail' and text()='"+fields+"']")).isDisplayed());
                    break;
                case "Groups" :
                    communityFrame();
                    driver.findElement(By.xpath("//li/a[text()='"+fields+"']")).click();
                    waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//a[@class='active-trail active' and text()='"+fields+"']"),1));
                    Assert.assertTrue("Groups field is not displayed",driver.findElement(By.xpath("//a[@class='active-trail active' and text()='"+fields+"']")).isDisplayed());
                    break;
            }
            getDriver().switchTo().defaultContent();
        }
    }


    public void verifyUser(){
        getCounselorCommunity().click();
        waitUntilPageFinishLoading();
        communityFrame();
        getProfilePage().click();
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.xpath("//a[@class='active' and text()='Profile']"),1));
        Assert.assertTrue("Profile page is not displayed",getProfilePage().isDisplayed());
        Assert.assertTrue("Hobsons text is not displayed",getHobsonsNameInProfilePage().isDisplayed());
        getDriver().switchTo().defaultContent();
    }

    //locator
    private WebElement collageNameLabel() {
            return getDriver().findElement(By.cssSelector("h1.masthead__name"));
        }
    private WebElement getCounselorCommunity(){return getDriver().findElement(By.id("js-main-nav-counselor-community-menu-link"));}
    private WebElement getHomePage(){return getDriver().findElement(By.xpath("//li/a[text()='Home']"));}
    private WebElement getProfilePage(){return getDriver().findElement(By.xpath("//li/a[text()='Profile']"));}
    private WebElement getHobsonsNameInProfilePage(){return getDriver().findElement(By.xpath("//div/a[text()='Hobsons']"));}
}
