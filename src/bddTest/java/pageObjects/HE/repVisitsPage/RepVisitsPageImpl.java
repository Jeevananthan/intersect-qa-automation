package pageObjects.HE.repVisitsPage;

import cucumber.api.DataTable;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;

public class RepVisitsPageImpl extends PageObjectFacadeImpl {

    private Logger logger;

    public void checkRepVisitsSubTabs(DataTable dataTable){
        navBar.goToRepVisits();
        List<String> list = dataTable.asList(String.class);
        for (String repVisitsSubItem : list) {
            Assert.assertTrue(repVisitsSubItem + " is not showing.",link(repVisitsSubItem).isDisplayed());
        }
    }
    public void checkHighSchoolPopUp(DataTable dataTable){
        List<Map<String, String>> entities = dataTable.asMaps(String.class, String.class);
        for (Map<String,String> school : entities){
            for (String key : school.keySet()) {
                switch (key) {
                    case "School Name":
                        String header = driver.findElement(By.className("_2XeJUEZaR8a0YG2vHm_ZA5")).getText();
                        Assert.assertTrue("School name was not found in header.", header.contains(school.get(key)));
                        break;
                    case "High School Contact:":
                        String contactLink = driver.findElement(By.className("_2-cmgeOoondfKX-iEm8xCP")).getText();
                        Assert.assertTrue("Contact name was not found in header.",contactLink.contains(school.get(key)));
                        break;
                    default:
                        WebElement label = driver.findElement(By.xpath("//div[contains(text(),'" + key + "')]"));
                        WebElement item = getParent(label);
                        String content = item.findElement(By.className("_2WVz9-VTbgicuNzZo9Vc_o")).getText();
                        Assert.assertTrue(key + " was not found in the popup",content.contains(school.get(key)));
                }
            }
        }
        validateInfolink();
    }

    private void validateInfolink(){
        link("For more information:").click();
        Assert.assertTrue("Did not end up on Community URL!", driver.getCurrentUrl().contains("counselor-community/institution"));
    }
    public void verifySearchAndSchedulePage() {
        navBar.goToRepVisits();
        getSearchAndScheduleBtn().click();
        WebElement dateBar = driver.findElement(By.className("_2Y4XoXCJpDOFoe0UYkEn-I"));
        Assert.assertTrue("Previous Week button is not present!",dateBar.findElement(By.cssSelector("[aria-label='Previous week']")).isDisplayed());
        Assert.assertTrue("Next Week button is not present!",dateBar.findElement(By.cssSelector("[aria-label='Next week']")).isDisplayed());
        Assert.assertTrue("Calendar button is not present!",dateBar.findElement(By.className("calendar")).isDisplayed());
        Assert.assertTrue("Placeholder text for search box was not present!", textbox("Enter a school name or location").isDisplayed());
    }

    public void searchforHighSchool(String schoolName) {
        navBar.goToRepVisits();
        getSearchAndScheduleBtn().click();
        getSearchBox().sendKeys(schoolName);
        getSearchButton().click();
        waitUntilPageFinishLoading();
    }

    public void selectHighSchoolFromIntermediateSearchResults(String schoolName, String location) {
        WebElement schoolLocation = text(location);
        getParent(schoolLocation).findElement(By.tagName("a")).click();
        waitUntilPageFinishLoading();
    }

    public void viewMapPlugin() {
        getMapButton().click();
        waitUntilPageFinishLoading();
    }

    public void verifyOverviewPage(){
        navBar.goToRepVisits();
        getOverviewBtn().click();
        Assert.assertTrue("Coming Soon message is not displaying",
                getComingSoonMessageInOverviewPage().getText().equals("The Overview Dashboard is coming soon. It will provide a quick view of your upcoming appointments and most recent notifications."));
    }

    public void selectSchoolFromMap(String schoolName) {
        button(schoolName).click();
        waitUntilPageFinishLoading();
    }

    public void verifySearchResultOfSearchAndSchedule(DataTable dataTable){
        DataTable transposedTable = dataTable.transpose();
        Map<String,String> searchMap = transposedTable.asMap(String.class, String.class);
        Set<String> searchCategory = searchMap.keySet();
        for (String category : searchCategory ) {
            searchforHighSchool(searchMap.get(category));
            try {
                Assert.assertTrue("HS data is showing while searching through "+category+" in Search And Schedule page", driver.findElement(By.xpath("//table[@class='ui very basic table']")).isDisplayed());
            } catch (NoSuchElementException nsee){
                Assert.assertTrue("HS data is not showing while searching through "+category+" in Search And Schedule page", driver.findElement(By.xpath("//span[contains(text(),'No results found')]")).isDisplayed());
                Assert.assertTrue("HS data is not showing while searching through "+category+" in Search And Schedule page", false);
            }
        }
    }

    private WebElement getOverviewBtn() {
        return link("Overview");
    }
    private WebElement getSearchAndScheduleBtn() {
        return link("Search and Schedule");
    }
    private WebElement getCalendarBtn() {
        return link("Calendar");
    }
    private WebElement getTravelPlanBtn() {
        return link("Travel Plan");
    }
    private WebElement getContactsBtn() {
        return link("Contacts");
    }
    private WebElement getRecommendationsBtn() {
        return link("Recommendations");
    }
    private WebElement getNotificationsBtn() {
        return link("Notifications");
    }
    private WebElement getSearchBox() { return textbox("Enter a school name or location");}
    private WebElement getSearchButton() { return driver.findElement(By.className("_3pWea2IV4hoAzTQ12mEux-"));}
    private WebElement getMapButton() { return driver.findElement(By.cssSelector("[class='map outline big icon']"));}
    private WebElement getComingSoonMessageInOverviewPage(){ return driver.findElement(By.className("_9SnX9M6C12WsFrvkMMEZR")); }
}


