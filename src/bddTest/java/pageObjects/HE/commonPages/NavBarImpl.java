package pageObjects.HE.commonPages;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import selenium.SeleniumBase;

public class NavBarImpl extends SeleniumBase {

    private Logger logger;

    public NavBarImpl() {
        logger = Logger.getLogger(NavBarImpl.class);
    }

    public void goToHome() {
        getHomeBtn().click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("Unable to navigate to Home tab", isLinkActive(getHomeBtn()));
    }

    public void goToCommunity() {
        getCommunityBtn().click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("Unable to navigate to Community", isLinkActive(getCommunityBtn()));
    }

    public void goToCollegeProfile() {
        getCollegeProfileBtn().click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("Unable to navigate to Community", isLinkActive(getCollegeProfileBtn()));
    }

    public void goToRepVisits() {
        getRepVisitsBtn().click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("Unable to navigate to Community", isLinkActive(getRepVisitsBtn()));
    }

    public void goToUsers() {
        getUsersBtn().click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("Unable to navigate to Community", isLinkActive(getUsersBtn()));
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
        return link(By.id("js-main-nav-counselor-community-menu-link"));
    }
}
