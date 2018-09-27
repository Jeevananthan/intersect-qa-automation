package pageObjects.COMMON;

import cucumber.api.DataTable;
import junit.framework.AssertionFailedError;
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
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.id("js-main-nav-rep-visits-menu-link"), 1));
        if (!isLinkActive(getRepVisitsBtn())) {
            getRepVisitsBtn().click();
            getRepVisitsBtn().click();
        }
        waitUntilPageFinishLoading();
        waitUntilElementExists(link(By.id("js-main-nav-rep-visits-menu-link")));
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

    public void addPost(String msg)
    {
        Assert.assertTrue("Counselor Community is not displayed",getCommunityBtn().isDisplayed());
        getCommunityBtn().click();
        WebElement element=getDriver().findElement(By.xpath("//iframe[@class='_2ROBZ2Dk5vz-sbMhTR-LJ']"));
        getDriver().switchTo().frame(element);
        getDriver().findElement(By.xpath("//textarea[@class='form-textarea']")).sendKeys(msg);
        getDriver().findElement(By.xpath("//input[@id='edit-save']")).click();
        getDriver().switchTo().defaultContent();
    }

    public void verifyNotificationIconInHomePage(){
        String notificationCount;
        Assert.assertTrue("Notification Icon is not visible",notificationIcon().isDisplayed());
        try{if(getDriver().findElement(By.xpath("//span[@class='_1LESaFFfI5r0qGbmkZ5l2I']")).isDisplayed())
        {
            notificationCount=getDriver().findElement(By.xpath("//span[@class='_1LESaFFfI5r0qGbmkZ5l2I']")).getText();
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
            case "ActiveMatch":
                Assert.assertFalse("ActiveMatch link is not visible",getActiveMatchButton().isDisplayed());
                break;
        }
    }

    //The below method is to verify the Breadcrumbs along with corresponding heading.
    public void verifyLeftNavAndBreadcrumbs(DataTable dataTable){
        Map<String, String> map = dataTable.asMap(String.class, String.class);
        for (Map.Entry pair : map.entrySet()){
            String heading = pair.getKey().toString();
            String[] content = pair.getValue().toString().split(",");
            WebElement headerWebElement;
            //Checking heading
            try{
                headerWebElement= new WebDriverWait(getDriver(),10).
                        until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(
                                "//nav[contains(@class,'hidden-mobile hidden-tablet')]/div/dl//dt/span[text()='%s']"
                                ,heading))));
            } catch (Exception e){throw new AssertionFailedError(String.format("The header: %s is not visible",
                    heading));}
            //Checking sub menues
            for (String subMenu : content) {
                subMenu = subMenu.trim();
                try{
                    WebElement subMenuElement = (new WebDriverWait(getDriver(),10)).until
                            (ExpectedConditions.elementToBeClickable(headerWebElement.findElement(By.xpath(String.format(
                                    "parent::dt/parent::dl/dt/a/span[text()='%s']",subMenu)))));
                    subMenuElement.click();
                    waitUntilPageFinishLoading();
                } catch (Exception e){throw new AssertionFailedError(String.format("The submenu: %s is not visible"
                        ,subMenu));}
                String actualHeadingBreadcrumText = getHeadingBreadcrumbs().getText().toLowerCase();
                String actualSubmenuBreadcrumText = getSubMenuBreadcrumbs().getText().toLowerCase();
                Assert.assertEquals(String.format("The Heading breadcrum text is incorrect, actual: %s, expected %s"
                        ,actualHeadingBreadcrumText,heading.toLowerCase()),heading.toLowerCase(),actualHeadingBreadcrumText);
                Assert.assertEquals(String.format("The Submenu breadcrum text is incorrect, actual: %s, expected %s"
                        ,actualSubmenuBreadcrumText,subMenu.toLowerCase()),subMenu.toLowerCase(),actualSubmenuBreadcrumText);

            }
        }
    }


    private boolean isLinkActive(WebElement link) {
        //_28hxQ33nAx_7ae3SZ4XGnj is the class that is added to indicate css active
        return link.getAttribute("class").contains("_28hxQ33nAx_7ae3SZ4XGnj");
    }

    //Getters
    private WebElement notificationIcon()
    {WebElement element=getDriver().findElement(By.xpath("//div[@id='notifications']"));
    return  element;}
    private WebElement verifyGlobeIcon(){
        WebElement element=getDriver().findElement(By.xpath("//div[@id='notifications']//div[@class='menu transition visible']"));
        waitUntilElementExists(element);
        return  element;
    }
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

    public WebElement getHeadingBreadcrumbs(){
        List<WebElement> items = getDriver().findElements(By.className("_2QGqPPgUAifsnRhFCwxMD7"));
        for (WebElement item : items) {
            if (item.getText().length() > 0)
                return item;
        }
        return null;
    }

    public WebElement getSubMenuBreadcrumbs() {
        List<WebElement> items = getDriver().findElements(By.className("UDWEBAWmyRe5Hb8kD2Yoc"));
        for (WebElement item : items) {
            if (item.getText().length() > 0)
                return item;
        }
        return null;
    }

    /**
     * Goes to the Admin Dashboard menu
     */
    public void goToAdminDashboard(){
        getAdminDashboardLink().click();
        waitUntil(ExpectedConditions.visibilityOf(getAdminDashboardLabel()));
    }

    /**
     * Gets the Admin Dashboard link
     * @return WebElement
     */
    private WebElement getAdminDashboardLink(){
        return getDriver().findElement(By.id("js-main-nav-admin-menu-link"));
    }

    /**
     * Gets the Admin Dashboard label
     * @return WebElement
     */
    private WebElement getAdminDashboardLabel(){
        return getDriver().findElement(By.cssSelector("h1._2uZ_hMKXaU0AzfCZMfjh1t"));
    }
}
