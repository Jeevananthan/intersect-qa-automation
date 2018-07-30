package pageObjects.COMMON;

import cucumber.api.DataTable;
import junit.framework.AssertionFailedError;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.SeleniumBase;

import java.util.List;
import java.util.Map;


public class NavigationBarImpl extends SeleniumBase {
    //Navigation controls
    @FindBy(xpath = "//i[@class='sort tiny icon _1oGgpE4PWdo7s3TzYBzNk']|" +
            "//span[contains(@class,'hidden-m')]/i[@class='sort tiny icon _3N6bahErAyB0Hx1zmV_fkt']")
    private WebElement navigationDropDown;

    @FindBy(id="js-main-nav-home-menu-link")
    private  WebElement homeMenuLink;

    @FindBy(id="js-main-nav-counselor-community-menu-link")
    private WebElement counselorCommunityMenuLink;

    @FindBy(id="js-main-nav-naviance-college-profile-menu-link")
    private WebElement navianceCollegeProfileMenuLink;

    @FindBy(id="js-main-nav-graphiql-menu-link")
    private WebElement graphiqlMenuLink;

    @FindBy(id="js-main-nav-rep-visits-menu-link")
    private WebElement repVisitsMenuLink;

    @FindBy(id="js-main-nav-am-events-menu-link")
    private WebElement eventsMenuLink;

    @FindBy(id="js-main-nav-am-plus-menu-link")
    private WebElement activeMatchMenuLink;

    @FindBy(xpath = "//div[@class='hidden-mobile yBsrTFLn1W-1lYcbX39B6']|" +
            "//span[@class='hidden-mobile hidden-tablet _3zP6971BAXkchn_VGmJPZr VdkH6dltNSvmQo2ObYzwJ']")
    private  WebElement selectedNavigationMenu;

    //Header Bar Search Box Controls
    @FindBy(id = "global-search-box-input")
    private WebElement searchTextbox;

    @FindBy(css = "i[class='search link icon']")
    private  WebElement searchBoxGlassButton;

    @FindBy(id="global-search-box-filter")
    private WebElement searchFilterDropdown;

    @FindBy(xpath = "//a[text()='Advanced Search']")
    private WebElement advancedSearchLink;

    @FindBy(id = "global-search-box-results")
    private WebElement searchSesultsListBox;

    // Right side icons
    @FindBy(id="helpNav-dropdown")
    private WebElement helpDropdown;

    @FindBy(css = "i[class='globe big icon _2Mks8yaXgkTI1rvaRYv4jn _2wmRB8Z7aYw_Hx80n2l5nS']")
    private WebElement notificationsDropdown;

    @FindBy(css = "div[class='menu transition visible']")
    private WebElement notificationList;

    @FindBy(css = "i[class='user circle big icon _2zVyfrnly39K0rqwywYZo8']")
    private WebElement userDropdown;

    public NavigationBarImpl(){
        PageFactory.initElements(driver, this);
    }

    public void goToHome() {
        waitUntil(ExpectedConditions.visibilityOf(navigationDropDown));
        navigationDropDown.click();
        waitUntil(ExpectedConditions.visibilityOf(homeMenuLink));
        homeMenuLink.click();
        waitUntil(ExpectedConditions.visibilityOf(selectedNavigationMenu));
        Assert.assertTrue("The Home menu was not selected",
                selectedNavigationMenu.getAttribute("innerText").contains("Home"));
    }

    public void goToCommunity() {
        waitUntil(ExpectedConditions.visibilityOf(navigationDropDown));
        navigationDropDown.click();
        waitUntil(ExpectedConditions.visibilityOf(counselorCommunityMenuLink));
        counselorCommunityMenuLink.click();
        waitUntil(ExpectedConditions.visibilityOf(selectedNavigationMenu));
        Assert.assertTrue("The Counselor Community menu was not selected",
                selectedNavigationMenu.getAttribute("innerText").contains("Counselor Community"));
    }

    public void goToCollegeProfile() {
        waitUntil(ExpectedConditions.visibilityOf(navigationDropDown));
        navigationDropDown.click();
        waitUntil(ExpectedConditions.visibilityOf(navianceCollegeProfileMenuLink));
        navianceCollegeProfileMenuLink.click();
        waitUntil(ExpectedConditions.visibilityOf(selectedNavigationMenu));
        Assert.assertTrue("The Naviance College Profile menu was not selected",
                selectedNavigationMenu.getAttribute("innerText").contains("Naviance College Profile"));
    }

    public void goToRepVisits() {
        waitUntil(ExpectedConditions.visibilityOf(navigationDropDown));
        navigationDropDown.click();
        waitUntil(ExpectedConditions.visibilityOf(repVisitsMenuLink));
        repVisitsMenuLink.click();
        waitUntil(ExpectedConditions.visibilityOf(selectedNavigationMenu));
        Assert.assertTrue("The RepVisits menu was not selected",
                selectedNavigationMenu.getAttribute("innerText").contains("RepVisits"));
    }

    public void goToEvents() {
        waitUntil(ExpectedConditions.visibilityOf(navigationDropDown));
        navigationDropDown.click();
        waitUntil(ExpectedConditions.visibilityOf(eventsMenuLink));
        eventsMenuLink.click();
        waitUntil(ExpectedConditions.visibilityOf(selectedNavigationMenu));
        Assert.assertTrue("The Events menu was not selected",
                selectedNavigationMenu.getAttribute("innerText").contains("Events"));
    }

    public void goToActiveMatch() {
        waitUntil(ExpectedConditions.visibilityOf(navigationDropDown));
        navigationDropDown.click();
        waitUntil(ExpectedConditions.visibilityOf(activeMatchMenuLink));
        activeMatchMenuLink.click();
        waitUntil(ExpectedConditions.visibilityOf(selectedNavigationMenu));
        Assert.assertTrue("The Active Match menu was not selected",
                selectedNavigationMenu.getAttribute("innerText").contains("ActiveMatch"));
    }

    /**
     * Sets the search text box
     * @param text to be used to set the text box
     */
    public void setSearchTextbox(String text){
        waitUntil(ExpectedConditions.visibilityOf(searchTextbox));
        searchTextbox.clear();
        searchTextbox.sendKeys(text);
    }

    /**
     * Clicks the search glass button
     */
    public void clickSearchGlassButton(){
        waitUntil(ExpectedConditions.visibilityOf(searchBoxGlassButton));
        searchBoxGlassButton.click();
    }

    /**
     * Selects a filter in the filter search dropdown
     * @param filter to be used
     */
    public void filterSearch(String filter){
        waitUntil(ExpectedConditions.visibilityOf(searchFilterDropdown));
        searchFilterDropdown.click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format("//span[text()='%s']",filter))));
        driver.findElement(By.xpath(String.format("//span[text()='%s']",filter))).click();
        waitUntil(ExpectedConditions.visibilityOf(searchSesultsListBox));
    }

    /**
     * Clicks on the advanced search link
     */
    public void clickAdvancedSearchLink(){
        waitUntil(ExpectedConditions.visibilityOf(advancedSearchLink));
        advancedSearchLink.click();
        waitUntilPageFinishLoading();
    }

    /**
     * Selects an option in the help dropdown
     * @param helpOption to be selected
     */
    public void selectHelpOption(String helpOption) {
        waitUntil(ExpectedConditions.visibilityOf(helpDropdown));
        helpDropdown.click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format("//span[text()='%s']", helpOption))));
        driver.findElement(By.xpath(String.format("//span[text()='%s']", helpOption))).click();
        waitUntilPageFinishLoading();
    }

    /**
     * Clicks on the notification dwopdown
     */
    public void clickNotificationsDropdown(){
        waitUntil(ExpectedConditions.visibilityOf(notificationsDropdown));
        notificationsDropdown.click();
        waitUntil(ExpectedConditions.visibilityOf(notificationList));
    }

    /**
     * Selects the given option in the user dropdown
     * @param option to be selected
     */
    public void selectUserOption(String option){
        waitUntil(ExpectedConditions.visibilityOf(userDropdown));
        userDropdown.click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format("//span[text()='%s']", option))));
        driver.findElement(By.xpath(String.format("//span[text()='%s']", option))).click();
        waitUntilPageFinishLoading();
    }

    /**
     * Navigates to the different menus in the application
     * @param menu to navigate
     */
    public void navigateTo(String menu){
        waitUntil(ExpectedConditions.visibilityOf(navigationDropDown));
        navigationDropDown.click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format("//span[text()='%s']", menu))));
        driver.findElement(By.xpath(String.format("//span[text()='%s']", menu))).click();
        waitUntilPageFinishLoading();
    }

    /**
     * Verifies if a navigation submenue is displayed
     * @param submenu
     */
    public void verifySubMenuIsVisible(String submenu){
        waitUntil(ExpectedConditions.visibilityOf(navigationDropDown));
        navigationDropDown.click();
        Assert.assertTrue(String.format("The sub menu: %s is not displayed",submenu),
                driver.findElement(By.xpath(String.format("//span[text()='%s']",submenu))).isDisplayed());
        navigationDropDown.click();
    }
    public void verifySubMenuIsNotVisible(String submenu){
        waitUntil(ExpectedConditions.visibilityOf(navigationDropDown));
        navigationDropDown.click();
        Assert.assertTrue(String.format("The sub menu: %s is displayed",submenu),
                driver.findElements(By.xpath(String.format("//span[text()='%s']",submenu))).size()==0);
        navigationDropDown.click();
    }

    /**
     *Selects the logout option in the user menu
     */
    public void logout(){
        driver.switchTo().defaultContent();
        goToHome();
        selectUserOption("Sign Out");
        waitUntilPageFinishLoading();
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

    public WebElement getHeadingBreadcrumbs(){
        List<WebElement> items = driver.findElements(By.className("_2QGqPPgUAifsnRhFCwxMD7"));
        for (WebElement item : items) {
            if (item.getText().length() > 0)
                return item;
        }
        return null;
    }

    public WebElement getSubMenuBreadcrumbs() {
        List<WebElement> items = driver.findElements(By.className("UDWEBAWmyRe5Hb8kD2Yoc"));
        for (WebElement item : items) {
            if (item.getText().length() > 0)
                return item;
        }
        return null;
    }

    public void verifyNotificationIconInHomePage(){
        String notificationCount;
        Assert.assertTrue("Notification Icon is not visible",notificationIcon().isDisplayed());
        try{if(driver.findElement(By.xpath("//span[@class='_1LESaFFfI5r0qGbmkZ5l2I']")).isDisplayed())
        {
            notificationCount=driver.findElement(By.xpath("//span[@class='_1LESaFFfI5r0qGbmkZ5l2I']")).getText();
            if(notificationCount.equals(""))
            {
                System.out.println("There is no notification");
            }else
            {
                System.out.println(notificationCount+"noticications are displayed");
            }
        }else
        {
            System.out.println("There is no notification");
        }}catch(Exception e){}
    }

    public void clickNavigationGlobeIcon(){
        notificationIcon().click();
        waitUntilPageFinishLoading();
        Assert.assertTrue("GlobeIcon is not displayed",verifyGlobeIcon().isDisplayed());
    }

    private WebElement verifyGlobeIcon(){
        WebElement element=driver.findElement(By.xpath("//div[@id='notifications']//div[@class='menu transition visible']"));
        waitUntilElementExists(element);
        return  element;
    }

    //Getters
    private WebElement notificationIcon()
    {WebElement element=driver.findElement(By.xpath("//div[@id='notifications']"));
        return  element;}

}
