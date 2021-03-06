package pageObjects.COMMON;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.SeleniumBase;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;


public class NavigationBarImpl extends SeleniumBase {
    private Logger logger;
    //Navigation controls
    //@FindBy(xpath = "//i[@class='sort tiny icon _1oGgpE4PWdo7s3TzYBzNk']|" +
    //        "//span[contains(@class,'hidden-m')]/i[@class='sort tiny icon _3N6bahErAyB0Hx1zmV_fkt']")
    @FindBy(xpath = "//a[@name='mainmenu']")
    private WebElement navigationDropDown;

    @FindBy(id="js-main-nav-home-menu-link")
    private  WebElement homeMenuLink;

    @FindBy(id="js-main-nav-home-menu-link")
    private  WebElement homeMenuLinkInHs;

    @FindBy(id="js-main-nav-counselor-community-menu-link")
    private WebElement counselorCommunityMenuLink;

    @FindBy(id="js-main-nav-naviance-college-profile-menu-link")
    public WebElement navianceCollegeProfileMenuLink;

    @FindBy(id="js-main-nav-graphiql-menu-link")
    private WebElement graphiqlMenuLink;

    @FindBy(id="js-main-nav-rep-visits-menu-link")
    private WebElement repVisitsMenuLink;

    @FindBy(id="js-main-nav-am-events-menu-link")
    private WebElement eventsMenuLink;

    @FindBy(id="js-main-nav-am-plus-menu-link")
    private WebElement activeMatchMenuLink;

    @FindBy(css = "a[name='mainmenu'] div.hidden-mobile")
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

    @FindBy(xpath = "//i[@class[contains(.,'globe big icon')]]")
    private WebElement notificationsDropdown;

    @FindBy(xpath = "//div[@class[contains(.,'menu') and contains(.,'transition') and contains(.,'visible')]]")
    private WebElement notificationList;

    @FindBy(css = "i[class='user circle big icon _2zVyfrnly39K0rqwywYZo8']")
    private WebElement userDropdown;

    @FindBy(xpath = "//i[@class[contains(.,'pink') and contains(.,'circle')]]")
    private WebElement notificationsCounter;

    public NavigationBarImpl(){

        logger = Logger.getLogger(NavBarImpl.class);
        PageFactory.initElements(getDriver(), this);
    }

    public void goToHome() {
        waitUntilElementExists(navigationDropDown);
        navigationDropDown.click();
        waitUntilPageFinishLoading();
        homeMenuLink.click();
        waitUntilElementExists(selectedNavigationMenu);
        Assert.assertTrue("The Home menu was not selected",
                selectedNavigationMenu.getText().contains("Home"));
    }

    public void goToCommunity() {
        waitForElementSetMaxTimeout();
        waitUntilElementExists(navigationDropDown);
        navigationDropDown.click();
        waitUntil(ExpectedConditions.visibilityOf(counselorCommunityMenuLink));
        counselorCommunityMenuLink.click();
        waitUntil(ExpectedConditions.visibilityOf(selectedNavigationMenu));
        Assert.assertTrue("The Counselor Community menu was not selected: ",
                selectedNavigationMenu.getAttribute("innerText").contains("Counselor Community"));
    }

    public void goToCommunityInHS() {
        waitForElementSetMaxTimeout();
        waitUntilElementExists(navigationDropDown);
        navigationDropDown.sendKeys(Keys.ENTER);
        waitUntil(ExpectedConditions.visibilityOf(homeMenuLinkInHs));
        homeMenuLinkInHs.click();
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
                selectedNavigationMenu.getAttribute("innerText").contains("College Profile"));
    }

    public void goToRepVisits() {
        getDriver().manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        FluentWait<WebDriver> wait = new WebDriverWait(getDriver(), 5);
        wait.until(presenceOfElementLocated(By.className("_3ygB2WO7tlKf42qb0NrjA3")));
        waitUntilElementExists(navigationDropDown);
        waitUntilPageFinishLoading();
        navigationDropDown.sendKeys(Keys.ENTER);
        waitUntilElementExists(repVisitsMenuLink);
        repVisitsMenuLink.sendKeys(Keys.ENTER);
        waitUntil(ExpectedConditions.visibilityOf(selectedNavigationMenu));
    }

    public void goToEvents() {
        waitUntilPageFinishLoading();
        navigationDropDown.click();
        waitUntil(ExpectedConditions.visibilityOf(eventsMenuLink));
        eventsMenuLink.click();
        waitUntil(ExpectedConditions.visibilityOf(selectedNavigationMenu));
        Assert.assertTrue("The Events menu was not selected",
                selectedNavigationMenu.getText().contains("Events"));
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
        getDriver().findElement(By.xpath(String.format("//span[text()='%s']",filter))).click();
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
        getDriver().findElement(By.xpath(String.format("//span[text()='%s']", helpOption))).click();
        waitUntilPageFinishLoading();
    }

    /**
     * Clicks on the notification dropdown
     */
    public void clickNotificationsDropdown(){
        waitUntil(ExpectedConditions.visibilityOf(notificationsDropdown));
        try {
            notificationsDropdown.click();
        } catch (Exception e) {
            if (notificationsCounter.isDisplayed()) {
                notificationsCounter.click();
            } else {
                throw e;
            }
        }
        waitUntil(ExpectedConditions.visibilityOf(notificationList));
    }

    /**
     * Selects the given option in the user dropdown
     * @param option to be selected
     */
    public void selectUserOption(String option){
        waitUntil(ExpectedConditions.visibilityOf(this.userDropdown));
        this.userDropdown.click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format("//span[text()='%s']", option))));
        getDriver().findElement(By.xpath(String.format("//span[text()='%s']", option))).click();
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
        getDriver().findElement(By.xpath(String.format("//span[text()='%s']", menu))).click();
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
                getDriver().findElement(By.xpath(String.format("//span[text()='%s']",submenu))).isDisplayed());
        navigationDropDown.click();
    }
    public void verifySubMenuIsNotVisible(String submenu){
        waitUntil(ExpectedConditions.visibilityOf(navigationDropDown));
        navigationDropDown.click();
        Assert.assertTrue(String.format("The sub menu: %s is displayed",submenu),
                getDriver().findElements(By.xpath(String.format("//span[text()='%s']",submenu))).size()==0);
        navigationDropDown.click();
    }

    /**
     *Selects the logout option in the user menu
     */
    public void logout(){
        getDriver().switchTo().defaultContent();
        goToHome();
        selectUserOption("Sign Out");
        waitUntilPageFinishLoading();
    }

    /**
     * Verifies the breadcrumbs with its respective header
     * @param dataTable
     */
    public void verifyLeftNavAndBreadcrumbs(DataTable dataTable){
        waitUntilElementExists(getDriver().findElement(By.cssSelector("div[class='_3xvPKh2BtfX3PytW8GQpO3']")));
        waitUntilPageFinishLoading();
        navigationDropDown.click();
        List<List<String>> data = dataTable.raw();
        for(List<String> row : data){
            WebElement menu = getDriver().findElement(By.xpath(String.format(
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
        try{if(getDriver().findElement(By.xpath("_2F8dw7IguhNBAlCKQDVrcl")).isDisplayed())
        {
            notificationCount=getDriver().findElement(By.xpath("_2F8dw7IguhNBAlCKQDVrcl")).getText();
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

    /**
     * Verifies the breadcrumbs with its respective header
     * @param dataTable
     */
    public void verifyLeftNavAndBreadcrumbsForHS(DataTable dataTable){
        waitUntilPageFinishLoading();
        getDriver().manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        FluentWait<WebDriver> wait = new WebDriverWait(getDriver(), 10);
        wait.until(presenceOfElementLocated(By.className("_3ygB2WO7tlKf42qb0NrjA3")));
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[name='mainmenu']")));
        mainMenu().click();
        waitUntil(ExpectedConditions.visibilityOfElementLocated(By.id("js-main-nav-rep-visits-menu-link")));
        List<List<String>> data = dataTable.raw();
        for(List<String> row : data){
            WebElement menu = getDriver().findElement(By.xpath(String.format(
                    "//dt[@class='header _3zoxpD-z3dk4-NIOb73TRl _384bOUKpp0wQluMQzJZp0P']/span[text()='%s']", row.get(0).trim())));
            String[] subMenusText = row.get(1).split(",");
            Assert.assertTrue(String.format("The menu: %s is not displayed",row.get(0).trim()),menu.isDisplayed());
            for(String subMenuText : subMenusText){
                WebElement subMenu = menu.findElement(By.xpath(String.format(
                        "parent::dt/parent::dl/dt/a/span[text()='%s']"
                        ,subMenuText.trim())));
                Assert.assertTrue(String.format("The submenu: %s is not displayed",subMenuText.trim()),subMenu.isDisplayed());
            }
        }
        getDriver().navigate().refresh();
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

    //That set is just to put a limit in the wait until element exists, not is a hardcoded time.
    //Read more information here: https://imalittletester.com/2016/05/11/selenium-how-to-wait-for-an-element-to-be-displayed-not-displayed/
    private void waitForElementSetMaxTimeout() {
        getDriver().manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
    }
    private WebElement mainMenu(){
        return getDriver().findElement(By.cssSelector("a[name='mainmenu']"));
    }
}
