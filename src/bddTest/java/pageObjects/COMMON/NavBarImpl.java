package pageObjects.COMMON;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.SeleniumBase;

import java.util.List;
import java.util.Map;

public class NavBarImpl extends SeleniumBase {

    private Logger logger;

    public NavBarImpl() {
        logger = Logger.getLogger(NavBarImpl.class);
    }

    public void goToHome() {
        if(!isLinkActive(getHomeBtn()))
            getHomeBtn().click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("Unable to navigate to Home tab", isLinkActive(getHomeBtn()));
    }

    public void goToCommunity() {
        if(!isLinkActive(getCommunityBtn()))
            getCommunityBtn().click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("Unable to navigate to Community", isLinkActive(getCommunityBtn()));
    }

    public void goToCollegeProfile() {
        if (!isLinkActive(getCollegeProfileBtn()))
            getCollegeProfileBtn().click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("Unable to navigate to Naviance College Profile", isLinkActive(getCollegeProfileBtn()));
    }

    public void goToRepVisits() {
        if (!isLinkActive(getRepVisitsBtn()))
            getRepVisitsBtn().click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("Unable to navigate to RepVisits", isLinkActive(getRepVisitsBtn()));
    }

    public void goToUsers() {
        if(!isLinkActive(getUsersBtn()))
            getUsersBtn().click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("Unable to navigate to User List", isLinkActive(getUsersBtn()));
    }

    public void verifySubMenuIsVisible(String tabName) {
        switch (tabName) {
            case "Home":
                Assert.assertTrue("Home link is not visible",getHomeBtn().isDisplayed());
                break;
            case "Counselor Community":
                Assert.assertTrue("Counselor Community tab is not visible",getCommunityBtn().isDisplayed());
                break;
            case "Naviance College Profile":
                Assert.assertTrue("Naviance College Profile link is not visible",getCollegeProfileBtn().isDisplayed());
                break;
            case "RepVisits":
                Assert.assertTrue("RepVisits link is not visible",getRepVisitsBtn().isDisplayed());
                break;
            case "Users":
                Assert.assertTrue("Users link is not visible",getUsersBtn().isDisplayed());
                break;
        }
    }

    public void addPost(String msg)
    {
        Assert.assertTrue("Counselor Community is not displayed",getCommunityBtn().isDisplayed());
        getCommunityBtn().click();
        WebElement element=driver.findElement(By.xpath("//iframe[@class='_2ROBZ2Dk5vz-sbMhTR-LJ']"));
        driver.switchTo().frame(element);
        driver.findElement(By.xpath("//textarea[@class='form-textarea']")).sendKeys(msg);
        driver.findElement(By.xpath("//input[@id='edit-save']")).click();
        driver.switchTo().defaultContent();
    }

    public void verifyNotificationIconInHomePage(){
        String notificationCount;
        Assert.assertTrue("Notification Icon is not visible",notificationIcon().isDisplayed());
        try{if(driver.findElement(By.xpath("//span[@class='_1LESaFFfI5r0qGbmkZ5l2I']")).isDisplayed())
        {
            notificationCount=driver.findElement(By.xpath("//span[@class='_1LESaFFfI5r0qGbmkZ5l2I']")).getText();
            if(notificationCount.equals(""))
            {
                logger.info("There is no notification");
            }else
            {
                logger.info(notificationCount+"noticications are displayed");
            }
        }else
        {
            logger.info("There is no notification");
        }}catch(Exception e){}
    }


    public void clickNavigationGlobeIcon(){
        notificationIcon().click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("GlobeIcon is not displayed",verifyGlobeIcon().isDisplayed());
    }


    public void verifySubMenuIsNotVisible(String tabName) {
        switch (tabName) {
            case "Home":
                Assert.assertFalse("Home link is visible",getHomeBtn().isDisplayed());
                break;
            case "Counselor Community":
                Assert.assertFalse("Counselor Community link is visible",getCommunityBtn().isDisplayed());
                break;
            case "Naviance College Profile":
                Assert.assertFalse("Naviance College Profile link is not visible",getCollegeProfileBtn().isDisplayed());
                break;
            case "RepVisits":
                Assert.assertFalse("RepVisits link is not visible",getRepVisitsBtn().isDisplayed());
                break;
            case "Users":
                Assert.assertFalse("Users link is not visible",getUsersBtn().isDisplayed());
                break;
        }
    }

    //The below method is to verify the Breadcrumbs along with corresponding heading.
    public void verifyLeftNavAndBreadcrumbs(DataTable dataTable){
        Map<String, String> map = dataTable.asMap(String.class, String.class);
        for (Map.Entry pair : map.entrySet()){
            String heading = pair.getKey().toString();
            String[] content = pair.getValue().toString().split(",");
            for (String subMenu : content) {
                WebElement itemLink = driver.findElement(By.xpath("(//span[contains(text(),'"+subMenu+"')])[2]"));
                // Check Heading
                WebElement section = getParent(getParent(getParent(itemLink)));
                WebElement container = section.findElement(By.className("_3zoxpD-z3dk4-NIOb73TRl"));
                WebElement headerSpan = container.findElement(By.tagName("span"));
                Assert.assertTrue("Nav Bar header for "+subMenu+" is incorrect, expected \"" + heading + "\"",headerSpan.getText().toLowerCase().contains(heading.toLowerCase()));
                itemLink.click();
                waitUntilPageFinishLoading();
                //Check Breadcrumbs
                Assert.assertTrue(heading+ " is not correct in Breadcrumbs, actual value is: " + getHeadingBreadcrumbs().getText(), heading.equalsIgnoreCase(getHeadingBreadcrumbs().getText()));
                Assert.assertTrue(subMenu+ " is not correct in Breadcrumbs, actual value is: " + getSubMeunBreadcrumbs().getText(), subMenu.equals(getSubMeunBreadcrumbs().getText()));


            }
        }
    }


    private boolean isLinkActive(WebElement link) {
        //_28hxQ33nAx_7ae3SZ4XGnj is the class that is added to indicate css active
        return link.getAttribute("class").contains("_28hxQ33nAx_7ae3SZ4XGnj");
    }

    //Getters
    private WebElement notificationIcon()
    {WebElement element=driver.findElement(By.xpath("//div[@id='notifications']"));
    return  element;}
    private WebElement verifyGlobeIcon(){
        WebElement element=driver.findElement(By.xpath("//div[@id='notifications']//div[@class='menu transition visible']"));
        waitUntilElementExists(element);
        return  element;
    }
    private WebElement getHomeBtn() {
        return link(By.id("js-main-nav-home-menu-link"));
    }
    private WebElement getCommunityBtn() {
        return link(By.id("js-main-nav-counselor-community-menu-link"));
    }
    private WebElement getCollegeProfileBtn() {
        return link(By.id("js-main-nav-naviance-college-profile-menu-link"));
    }
    private WebElement getRepVisitsBtn() {
        return link(By.id("js-main-nav-rep-visits-menu-link"));
    }
    private WebElement getUsersBtn() {
        return link(By.id("js-main-nav-manage-users-menu-link"));
    }
    private WebElement getHeadingBreadcrumbs(){
        List<WebElement> items = driver.findElements(By.className("_2QGqPPgUAifsnRhFCwxMD7"));
        for (WebElement item : items) {
            if (item.getText().length() > 0)
                return item;
        }
        return null;
    }
    private WebElement getSubMeunBreadcrumbs() {
        List<WebElement> items = driver.findElements(By.className("UDWEBAWmyRe5Hb8kD2Yoc"));
        for (WebElement item : items) {
            if (item.getText().length() > 0)
                return item;
        }
        return null;
    }
}
