package pageObjects.COMMON;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.Set;

public class HelpImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public HelpImpl() {
        logger = Logger.getLogger(HelpImpl.class);
    }

    public void verifyHelpExistAndSecure(String highSchool){
        logger.info("I verify that the help content is secure and matches the correct URL.");
        ArrayList<String> windows = null;
        String url;
        if(!(highSchool.equalsIgnoreCase("Naviance HS Users")) && !(highSchool.equalsIgnoreCase("SP Users"))) {
            selectHelpOption("Help Center");
            windows = new ArrayList<>(driver.getWindowHandles());
        }
        switch (highSchool){
            case "Naviance HS Users":
                Assert.assertFalse("The Help link is available for Naviance user, but shouldn't be.", button(By.id("js-main-nav-help-menu-link")).isDisplayed());
                break;
            case "Non-Naviance HS Users":
                driver.switchTo().window(windows.get(1));
                waitForUITransition();
                url = driver.getCurrentUrl();
                driver.switchTo().window(windows.get(0));
                waitForUITransition();
                waitForUITransition();
                driver.switchTo().window(windows.get(1));
                url = driver.getCurrentUrl();
                Assert.assertEquals("The Help link is not secure or is not the correct web address.","https://helpsite.hobsons.com/RepVisits/Content/Getting%20Started%20HS.htm", url);
                driver.switchTo().window(windows.get(0));
                break;
            case "HE Users":
                driver.switchTo().window(windows.get(1));
                waitForUITransition();
                url = driver.getCurrentUrl();
                waitUntilElementExists(link("Getting Started"));
                waitUntilPageFinishLoading();
                Assert.assertEquals("The Help link is not secure or is not the correct web address.", "https://helpsite.hobsons.com/Intersect/Content/Getting%20Started%20HE.htm", url);
                driver.close();
                driver.switchTo().window(windows.get(0));
                break;
            case "SP Users":
                getHelpLink().click();
                waitUntil(ExpectedConditions.numberOfWindowsToBe(2));
                windows = new ArrayList<>(driver.getWindowHandles());
                driver.switchTo().window(windows.get(1));
                waitUntilPageFinishLoading();
                waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector("div[class='navigation-wrapper']"), 1));
                url = driver.getCurrentUrl();
                driver.close();
                driver.switchTo().window(windows.get(0));
                Assert.assertEquals("The Help link is not secure or is not the correct web address.", "https://helpsite.hobsons.com/Intersect/Content/Getting%20Started%20HE.htm", url);
                break;
        }
    }

    public void verifyURLinHECounselorCommunityGuidelines(String navigateToPage){
        waitUntilPageFinishLoading();
        waitForUITransition();
        String currentWindow = driver.getWindowHandle();
        String communityGuidelinesWindow = null;
        if(navigateToPage.equals("Counselor Community Guidelines")){
            driver.findElement(By.id("helpNav-dropdown")).click();
            Assert.assertTrue("Help center is not displayed",driver.findElement(By.id("js-helpNavMenu-help-menu-link")).isDisplayed());
            driver.findElement(By.id("js-helpNavMenu-help-menu-link")).click();
            waitUntilPageFinishLoading();
            waitForUITransition();
            waitForUITransition();
            Set<String> windows = driver.getWindowHandles();
            for(String thiswindow:windows){
                if(!thiswindow.equals(currentWindow)){
                    communityGuidelinesWindow = thiswindow;
                }
            }
            driver.switchTo().window(communityGuidelinesWindow);
            waitForUITransition();
            driver.findElement(By.xpath("//span/a/b[text()='Hobsons Counselor Community']")).click();
            String communityGuidelinesURL = link("Counselor Community Guidelines").getUrl();
            link("Counselor Community Guidelines").click();
            waitUntilPageFinishLoading();
            String getCurrentPageUrl = driver.getCurrentUrl();
            Assert.assertTrue("Counselor Community Guidelines page is not displayed",driver.findElement(By.xpath("//span[normalize-space(text())='To be part of the Hobsons Counselor Community, you must be 18 or older and be authorized by your school, district or higher education institution to participate.']")).isDisplayed());
            Assert.assertTrue("Given URL is not present in the page",getCurrentPageUrl.equals(communityGuidelinesURL));
            driver.close();
            driver.switchTo().window(currentWindow);
            waitUntilPageFinishLoading();
        }else {
            Assert.fail("Invalid option");
        }

    }

    public void verifyURLinHSCounselorCommunityGuidelines(String navigateToPage){
        waitUntilPageFinishLoading();
        waitForUITransition();
        String currentWindow = driver.getWindowHandle();
        String communityGuidelinesWindow = null;
        if(navigateToPage.equals("Counselor Community Guidelines")){
            driver.findElement(By.id("helpNav-dropdown")).click();
            Assert.assertTrue("Help center is not displayed",driver.findElement(By.id("js-helpNavMenu-help-menu-link")).isDisplayed());
            driver.findElement(By.id("js-helpNavMenu-help-menu-link")).click();
            waitUntilPageFinishLoading();
            waitForUITransition();
            waitForUITransition();
            Set<String> windows = driver.getWindowHandles();
            for(String thiswindow:windows){
                if(!thiswindow.equals(currentWindow)){
                    communityGuidelinesWindow = thiswindow;
                }
            }
            driver.switchTo().window(communityGuidelinesWindow);
            waitForUITransition();
            driver.findElement(By.xpath("//span/a[text()='Hobsons Counselor Community']")).click();
            String communityGuidelinesURL = link("Counselor Community Guidelines").getUrl();
            link("Counselor Community Guidelines").click();
            waitUntilPageFinishLoading();
            String getCurrentPageUrl = driver.getCurrentUrl();
            Assert.assertTrue("Counselor Community Guidelines page is not displayed",driver.findElement(By.xpath("//span[normalize-space(text())='To be part of the Hobsons Counselor Community, you must be 18 or older and be authorized by your school, district or higher education institution to participate.']")).isDisplayed());
            Assert.assertTrue("Given URL is not present in the page",getCurrentPageUrl.equals(communityGuidelinesURL));
            driver.close();
            driver.switchTo().window(currentWindow);
            waitUntilPageFinishLoading();
        }else {
            Assert.fail("Invalid option");
        }

    }

    /**
     * Selects the gien option in the Help Header menu
     * @param option to be selcted
     */
    public void selectHelpOption(String option){
        getHelpButton().click();
        WebElement helpOptionButton = driver.findElement(By.xpath(String.format("//span[text()='%s']",option)));
        waitUntil(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class='menu transition visible']")));
        helpOptionButton.click();
    }

    //Locators

    /**
     * Gets the help button of the header
     * @return the help button of the header
     */
    public WebElement getHelpButton(){
        return button(By.id("helpNav-dropdown"));
    }

    /**
     * Gets the help link of the navigation bar
     * @return the help link of the navigation bar
     */
    public WebElement getHelpLink(){
        return button(By.id("js-main-nav-help-menu-link"));
    }

}
