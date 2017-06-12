package pageObjects.COMMON;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import selenium.SeleniumBase;

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
                Assert.assertTrue(heading+ " is not correct in Breadcrumbs", heading.equalsIgnoreCase(getHeadingBreadcrumbs().getText()));
                Assert.assertTrue(subMenu+ " is not correct in Breadcrumbs", subMenu.equals(getSubMeunBreadcrumbs().getText()));
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
    private WebElement getHeadingBreadcrumbs(){ return driver.findElement(By.className("_2QGqPPgUAifsnRhFCwxMD7")); }
    private WebElement getSubMeunBreadcrumbs(){ return driver.findElement(By.className("UDWEBAWmyRe5Hb8kD2Yoc")); }
}
