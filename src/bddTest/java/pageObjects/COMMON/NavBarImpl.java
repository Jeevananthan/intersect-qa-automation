package pageObjects.COMMON;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

    public void goToEvents() {
        if (!isLinkActive(getEventsBtn()))
            getEventsBtn().click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("Unable to navigate to Events", isLinkActive(getEventsBtn()));
    }

    public void goToUsers() {
        if(!isLinkActive(getUsersBtn()))
            getUsersBtn().click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("Unable to navigate to User List", isLinkActive(getUsersBtn()));
    }

    public void goToActiveMatch() {
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector("a#js-main-nav-am-plus-menu-link span"), 1));
        getActiveMatchButton().click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("Unable to navigate to ActiveMatch page", isLinkActive(getActiveMatchButton()));
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
            case "ActiveMatch":
                Assert.assertTrue("ActiveMatch link is not visible",getActiveMatchButton().isDisplayed());
                break;
        }
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
            case "ActiveMatch":
                Assert.assertFalse("ActiveMatch link is not visible",getActiveMatchButton().isDisplayed());
                break;
        }
    }

    //The below method is to verify the Breadcrumbs along with corresponding heading.
    public void verifyLeftNavAndBreadcrumbs(DataTable dataTable){
        WebElement container;
        Map<String, String> map = dataTable.asMap(String.class, String.class);
        for (Map.Entry pair : map.entrySet()){
            String heading = pair.getKey().toString();
            String[] content = pair.getValue().toString().split(",");
            for (String subMenu : content) {
                subMenu = subMenu.trim();
                WebElement itemLink = driver.findElement(By.xpath("(//span[contains(text(),'"+subMenu+"')])[2]"));
                // Check Heading
                WebElement section = getParent(getParent(getParent(getParent(itemLink))));
                String text = section.getText();
                if (heading.equalsIgnoreCase("Presence")) {
                    container = section.findElement(By.xpath("(//dt[@class='header _3zoxpD-z3dk4-NIOb73TRl'])[4]"));
                    //container = section.findElement(By.className("_3zoxpD-z3dk4-NIOb73TRl"));
                }else {

                    container = section.findElement(By.className("_3zoxpD-z3dk4-NIOb73TRl"));
                }
                WebElement headerSpan = container.findElement(By.tagName("span"));
                String containerText =  headerSpan.getText();
                Assert.assertTrue("Nav Bar header for "+subMenu+" is incorrect, expected \"" + heading + "\"",headerSpan.getText().toLowerCase().contains(heading.toLowerCase()));
                waitUntilPageFinishLoading();
                itemLink.click();
                waitUntilPageFinishLoading();
                // This doesn't work for some reason, but the following steps will sometimes fail due to timing issues with User List page loading.
                //waitUntilElementExists(driver.findElement(By.className("_2QGqPPgUAifsnRhFCwxMD7")));
                //Check Breadcrumbs
                // Now the UI it's displaying AWARENESS Community
                Assert.assertTrue(heading+ " is not correct in Breadcrumbs, actual value is: " + getHeadingBreadcrumbs(heading).getText(), heading.equalsIgnoreCase(getHeadingBreadcrumbs(heading).getText()));
                Assert.assertTrue(subMenu+ " is not correct in Breadcrumbs, actual value is: " + getSubMeunBreadcrumbs(heading).getText(), subMenu.equals(getSubMeunBreadcrumbs(heading).getText()));
                logger.info("Verified " + subMenu + " is under " + heading + " as expected.");
            }
        }
    }

    private boolean isLinkActive(WebElement link) {
        //_28hxQ33nAx_7ae3SZ4XGnj is the class that is added to indicate css active
        return link.getAttribute("class").contains("_28hxQ33nAx_7ae3SZ4XGnj");
    }

    //Getters
    private WebElement getHomeBtn() {
        return link(By.id("js-main-nav-home-menu-link"));
    }
    private WebElement getCommunityBtn() {
        try {
            if (link(By.id("js-main-nav-counselor-community-menu-link")).isDisplayed()) {
                return link(By.id("js-main-nav-counselor-community-menu-link"));
            }
            else
                return link(By.id("js-main-nav-home-menu-link"));
        } catch (Exception e) {
            return link(By.id("js-main-nav-home-menu-link"));
        }
    }
    private WebElement getCollegeProfileBtn() {
        return link(By.id("js-main-nav-naviance-college-profile-menu-link"));
    }
    private WebElement getRepVisitsBtn() {
        return link(By.id("js-main-nav-rep-visits-menu-link"));
    }
    private WebElement getEventsBtn() { return link(By.id("js-main-nav-am-events-menu-link")); }
    private WebElement getUsersBtn() {
        return link(By.id("js-main-nav-manage-users-menu-link"));
    }
    private WebElement getActiveMatchButton() { return link(By.id("js-main-nav-am-plus-menu-link")); }

    private WebElement getHeadingBreadcrumbs(String heading){
        if (heading.equalsIgnoreCase("Presence")) {
            List<WebElement> items = driver.findElements(By.xpath("(//dt[@class='header _3zoxpD-z3dk4-NIOb73TRl'])[4]"));
            for (WebElement item : items) {
                if (item.getText().length() > 0)
                    return item;
            }
        }else {

            List<WebElement> items = driver.findElements(By.cssSelector("dt[class='header _3zoxpD-z3dk4-NIOb73TRl']"));
            for (WebElement item : items) {
                if (item.getText().length() > 0)
                    return item;
            }
        }

        return null;
    }
    private WebElement getSubMeunBreadcrumbs(String heading) {

        if (heading.equalsIgnoreCase("Presence")) {
            List<WebElement> items = driver.findElements(By.xpath("(//a[@class='_2PQVKVsDhRwSQYR3V28Dnw _28hxQ33nAx_7ae3SZ4XGnj'])[2]"));
            for (WebElement item : items) {
                if (item.getText().length() > 0)
                    return item;
            }
        }else {

            List<WebElement> items = driver.findElements(By.className("_2PQVKVsDhRwSQYR3V28Dnw"));
            for (WebElement item : items) {
                if (item.getText().length() > 0)
                    return item;
            }
        }
        return null;
    }
}
