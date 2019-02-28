package pageObjects.HUBS;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageObjects.COMMON.PageObjectFacadeImpl;
import pageObjects.SM.searchPage.SearchPageImpl;
import utilities.GetProperties;

import java.util.List;

public class HUBSLoginPageImpl extends PageObjectFacadeImpl {

    private SearchPageImpl searchPage = new SearchPageImpl();

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

    public void verifyDarkBlueFooter() {
        Assert.assertTrue("College Search footer is not displayed", getFooter().isDisplayed());

        // placeholder changed in MATCH-3471 from Search... to Search by College Name
        Assert.assertTrue("Search box in College Search footer is not displayed", getFooter().findElement(By.xpath(".//input[@placeholder='Search by College Name']"))
                .isDisplayed());

        Assert.assertTrue("'THINKING ABOUT' menu is not displayed", getFooter().findElement(By.xpath("//a[contains(text(), 'Thinking About')]"))
                .isDisplayed());

        Assert.assertTrue("'APPLYING TO' menu is not displayed", getFooter().findElement(By.xpath("//a[contains(text(), 'Applying To')]"))
                .isDisplayed());

        Assert.assertTrue("'MORE' menu is not displayed", getFooter().findElement(By.xpath("//a[contains(text(), 'More')]"))
                .isDisplayed());
    }

    /**
     * Verifies the text of a link in the "More" link inside the SuperMatch footer, as well as the location the user is sent to,
     * @param link Name of the link to navigate to in the "More" menu in the SM footer.
     */
    public void verifyFooterLink(String link) {
            waitUntilPageFinishLoading();
        switch(link) {
            case "Upcoming Visits":
                footerMoreButton().sendKeys(Keys.RETURN);
                footerUpcomingVisitsLink().click();
                waitUntilPageFinishLoading();
                softly().assertThat(getDriver().getCurrentUrl()).contains("/colleges/visits");
                getDriver().executeScript("window.history.go(-1)");
                waitUntilPageFinishLoading();
                break;
            case "Events":
                footerMoreButton().sendKeys(Keys.RETURN);
                footerEventsAppLink().click();
                waitUntilPageFinishLoading();
                softly().assertThat(getDriver().getCurrentUrl()).contains("college-events");
                getDriver().executeScript("window.history.go(-1)");
                waitUntilPageFinishLoading();
                break;
        }
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
    private WebElement getFooter() {return driver.findElement(By.xpath("//sticky-bar"));}
    private WebElement footerMoreButton() {return getDriver().findElement(By.xpath("//sticky-bar//*[contains(text(), 'More')]"));}
    private WebElement footerUpcomingVisitsLink() {return getDriver().findElement(By.xpath("//a[contains(text(), 'Upcoming Visits')]"));}
    private WebElement footerEventsAppLink() {return getDriver().findElement(By.xpath("//a[contains(text(), 'Events')]"));}
}
