package pageObjects.HUBS;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;
import utilities.GetProperties;

import java.util.List;

public class HUBSLoginPageImpl extends PageObjectFacadeImpl {

    Logger logger = null;

    public HUBSLoginPageImpl() {
        logger = Logger.getLogger(HUBSLoginPageImpl.class);
    }

    public void login(final String username, final String password) {
        open();
        usernameField().sendKeys(username);
        passwordField().sendKeys(password);
        loginButton().click();
    }

    public void loginInternational(final String username, final String password) {
        openInternational();
        usernameField().sendKeys(username);
        passwordField().sendKeys(password);
        loginButton().click();
    }

    public void defaultLogIn(List<String> creds) {
        String username = creds.get(0);
        String password = creds.get(1);
        login(username,password);
        waitUntilPageFinishLoading();
    }

    public void internationalLogIn(List<String> creds) {
        String username = creds.get(0);
        String password = creds.get(1);
        loginInternational(username,password);
        waitUntilPageFinishLoading();
    }

    private void open(){
        driver.close();
        load(GetProperties.get("hubs.app.url"));
    }

    private void openInternational() { load(GetProperties.get("hubs.app.int.url")); }

    private void openNavianceStudent(String school) {
        load(GetProperties.get("fc.app.url") + school);
    }

    public void loginNavianceStudent(String username, String password, String school) {
        openNavianceStudent(school);
        usernameField().sendKeys(username);
        passwordField().sendKeys(password);
        button("Login").click();
        waitUntil(ExpectedConditions.visibilityOf(link("/colleges")));
    }

    public void searchCollege(String collegeName) {
        waitUntilElementExists(searchCollegeField());
        searchCollegeField().sendKeys(collegeName);
        searchButton().click();
        //The following is a workaround due to MATCH-5565
        waitUntilElementExists(button("Go"));
        if (driver.findElements(By.xpath(noResultsFoundTextLocator)).size() > 0) {
            button("Go").click();
        }
    }

    public void openHUBSPage(String collegeName) {
        waitUntil(ExpectedConditions.elementToBeClickable(collegeInResults(collegeName)));
        collegeInResults(collegeName).sendKeys(Keys.RETURN);
    }

    public void enableAMNextGenByURL(String urlPart) {
        String newURL = driver.getCurrentUrl().replace("index-qa.html", "index-qa.html" + urlPart);
        driver.get(newURL);
    }


    //Locators

    private WebElement usernameField() {
        return textbox(By.name("username"));
    }
    private WebElement passwordField() {
        return textbox(By.name("password"));
    }
    private WebElement loginButton() {
        return button(By.cssSelector("input.yellow-button"));
    }
    private WebElement searchCollegeField() { return driver.findElement(By.cssSelector("input[title='Search']")); }
    private WebElement searchButton() { return driver.findElement(By.cssSelector("span.nophone")); }
    private WebElement collegeInResults(String collegeName) { return driver.findElement(By.xpath("//tbody//a[text() = '" + collegeName + "']")); }
    private String noResultsFoundTextLocator = "//div[contains(text(), 'No results found')]";
}
