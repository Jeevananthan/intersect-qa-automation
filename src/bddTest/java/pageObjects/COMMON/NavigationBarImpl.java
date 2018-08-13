package pageObjects.COMMON;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import selenium.SeleniumBase;

import java.util.List;



public class NavigationBarImpl extends SeleniumBase {
    private Logger logger;
    //Navigation controls
    //@FindBy(xpath = "//i[@class='sort tiny icon _1oGgpE4PWdo7s3TzYBzNk']|" +
    //        "//span[contains(@class,'hidden-m')]/i[@class='sort tiny icon _3N6bahErAyB0Hx1zmV_fkt']")
    @FindBy(xpath = "//a[@name='mainmenu']")
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

        logger = Logger.getLogger(NavBarImpl.class);
        PageFactory.initElements(driver, this);
    }

    public void goToHome() {
        waitUntil(ExpectedConditions.visibilityOf(navigationDropDown));
        navigationDropDown.click();
        waitUntil(ExpectedConditions.visibilityOf(homeMenuLink));
        homeMenuLink.click();
        waitUntil(ExpectedConditions.visibilityOf(selectedNavigationMenu));
        Assert.assertTrue("The Home menu was not selected",
                selectedNavigationMenu.getText().contains("Home"));
    }

    public void goToCommunity() {
        waitUntil(ExpectedConditions.visibilityOf(navigationDropDown));
        navigationDropDown.click();
        waitUntil(ExpectedConditions.visibilityOf(counselorCommunityMenuLink));
        counselorCommunityMenuLink.click();
        waitUntil(ExpectedConditions.visibilityOf(selectedNavigationMenu));
        Assert.assertTrue("The Counselor Community menu was not selected: ",
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
//        Assert.assertTrue("The RepVisits menu was not selected",
//                selectedNavigationMenu.getAttribute("innerText").contains("RepVisits"));
//        Assert.assertTrue("The RepVisits menu was not selected",
//                selectedNavigationMenu.getAttribute("innerText").contains("RepVisits"));
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

    /**
     * Verifies the breadcrumbs with its respective header
     * @param dataTable
     */
    public void verifyLeftNavAndBreadcrumbs(DataTable dataTable){
        navigationDropDown.click();
        List<List<String>> data = dataTable.raw();
        for(List<String> row : data){
            WebElement menu = driver.findElement(By.xpath(String.format(
                    "//dt[@class='header _1ojTdlgPNhtH4N-__uiqvu']/span[text()='%s']", row.get(0).trim())));
            String[] subMenusText = row.get(1).split(",");
            Assert.assertTrue(String.format("The menu: %s is not displayed",row.get(0).trim()),menu.isDisplayed());
            for(String subMenuText : subMenusText){
                WebElement subMenu = menu.findElement(By.xpath(String.format(
                        "ancestor::dl[@class='ui huge inverted vertical _3oMJTHrebN5xpDMwkfhCJw menu']/dt/a/span[text()='%s']"
                        ,subMenuText.trim())));
                Assert.assertTrue(String.format("The submenu: %s is not displayed",subMenuText.trim()),subMenu.isDisplayed());
            }
        }
        navigationDropDown.click();
    }

    /**
     * Verifies the notifications in the header
     */
    public void verifyNotificationIconInHomePage(){
        String notificationCount;
        Assert.assertTrue("Notification Icon is not visible",notificationsDropdown.isDisplayed());
        try{if(driver.findElement(By.xpath("_2F8dw7IguhNBAlCKQDVrcl")).isDisplayed())
        {
            notificationCount=driver.findElement(By.xpath("_2F8dw7IguhNBAlCKQDVrcl")).getText();
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

}
