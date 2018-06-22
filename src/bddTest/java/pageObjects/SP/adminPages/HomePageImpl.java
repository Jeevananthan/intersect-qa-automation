package pageObjects.SP.adminPages;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.GlobalSearch;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.List;

public class HomePageImpl extends PageObjectFacadeImpl {

    private Logger logger;
    private GlobalSearch search = new GlobalSearch();

    public HomePageImpl() {
        logger = Logger.getLogger(HomePageImpl.class);
    }

    public void verifyUserIsLoggedInForNoRole() {
        //Check the signout button is present for No Role
        Assert.assertTrue("User did not signed in",signoutButtonForNoRole().isDisplayed());
        logger.info("Logged in successfully");
    }

    public void verifyUserIsLoggedIn() {
        //Check if user element is present
        Assert.assertTrue("User did not signed in",link(By.id("user-dropdown")).isDisplayed());
        logger.info("Logged in successfully");
    }

    public void verifySecurityMessage(String expectedMsg) {
        Assert.assertTrue(text(expectedMsg).isDisplayed());
        logger.info("Security message match the expected message");
    }

    public void logout() {
        try{
            List<WebElement> signoutButton = driver.findElements(By.cssSelector("button[class='ui mini button']"));
            if (signoutButton.size()==1){
                signoutButtonForNoRole().click();
                waitUntilPageFinishLoading();
                Assert.assertTrue(getDriver().getCurrentUrl().contains("login"));
                driver.manage().deleteAllCookies();
            }
        }catch(Exception e){
            link(By.id("user-dropdown")).click();
            driver.findElement(By.cssSelector("div[id='user-dropdown-signout']")).click();
            waitUntilPageFinishLoading();
            Assert.assertTrue(getDriver().getCurrentUrl().contains("login"));
            driver.manage().deleteAllCookies();
        }
    }

    public String selectTheFistInstitutionOnTheList() {
        return table("Higher Ed Account Dashboard").clickOnTheFirstElementOfAColumn("Name");
    }

    public void goToInstitution(String institutionName) {
        navBar.goToHome();
        globalSearch.setSearchCategory("All");
        globalSearch.searchForHEInstitutions(institutionName);
        globalSearch.selectResult(institutionName);
        /*while (button("More Higher Ed Accounts").isDisplayed()) {
            button("More Higher Ed Accounts").click();
            waitUntilPageFinishLoading();
        }
        table(By.id("he-account-dashboard")).findElement(By.cssSelector("[aria-label=\"" + institutionName + "\"]")).click();*/
    }

    public void goToUsersList(String institutionName) {
        goToInstitution(institutionName);
        link("See All Users").click();
        waitUntilPageFinishLoading();
        waitUntil(ExpectedConditions.visibilityOf(userListTable()));
    }

    public void goToCreateUser(String institutionName) {
        goToInstitution(institutionName);
        link("Create User").click();
        waitUntilPageFinishLoading();
    }

    public void navigateToCreateUser(){
        link("Create User").click();
        waitUntilPageFinishLoading();

    }

    public void goToLogHistory(String institutionName) {
        goToInstitution(institutionName);
        link("View Log History").click();
        Assert.assertTrue(textbox("Search...").isDisplayed());
    }

    public void goToUsersListUsingSearch(String institutionName, String searchString) {
        navBar.goToHome();
        search.searchForAll(searchString);
        search.selectResult(institutionName);
        waitUntilPageFinishLoading();
        link("See All Users").click();
        try {
            driver.wait(2000);
        } catch (Exception e){}
        waitUntilPageFinishLoading();
    }

    private WebElement userDropdown() {
        return button(By.id("user-dropdown"));
    }
    private WebElement userListTable() {
        return button(By.cssSelector("[class='ui table']"));
    }
    private WebElement signoutButtonForNoRole(){return driver.findElement(By.cssSelector("button[class='ui mini button']")); }
}
