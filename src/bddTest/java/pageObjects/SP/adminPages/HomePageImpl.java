package pageObjects.SP.adminPages;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.GlobalSearch;
import pageObjects.COMMON.PageObjectFacadeImpl;

public class HomePageImpl extends PageObjectFacadeImpl {

    private Logger logger;
    private GlobalSearch search = new GlobalSearch();

    public HomePageImpl() {
        logger = Logger.getLogger(HomePageImpl.class);
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
        link(By.id("user-dropdown")).click();
        button(By.id("user-dropdown-signout")).click();
        Assert.assertTrue(getDriver().getCurrentUrl().contains("login"));
        driver.manage().deleteAllCookies();
    }

    public String selectTheFistInstitutionOnTheList() {
        return table("Higher Ed Account Dashboard").clickOnTheFirstElementOfAColumn("Name");
    }

    public void verifyInstitutionExist(String institutionName) {
        navBar.goToHome();
        while (button("More Higher Ed Accounts").isDisplayed()) {
            button("More Higher Ed Accounts").click();
            waitUntilPageFinishLoading();
        }

        table(By.id("he-account-dashboard")).verifyValueIsOnTheTable(institutionName);
    }

    public void verifyInstitutionDoesNotExist(String institutionName) {
        navBar.goToHome();
        while (button("More Higher Ed Accounts").isDisplayed()) {
            button("More Higher Ed Accounts").click();
            waitUntilPageFinishLoading();
        }

        table(By.id("he-account-dashboard")).verifyValueIsNotOnTheTable(institutionName);
    }

    public void goToInstitution(String institutionName) {
        navBar.goToHome();
        while (button("More Higher Ed Accounts").isDisplayed()) {
            button("More Higher Ed Accounts").click();
            waitUntilPageFinishLoading();
        }
        table(By.id("he-account-dashboard")).findElement(By.cssSelector("[aria-label=\"" + institutionName + "\"]")).click();
    }

    public void goToUsersList(String institutionName) {
        goToInstitution(institutionName);
        link("See All Users").click();
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
        waitUntilPageFinishLoading();
    }

    private WebElement userDropdown() {
        return button(By.id("user-dropdown"));
    }
}
