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

public class RepVisitsPageImpl extends PageObjectFacadeImpl {

    public void checkRepVisitsSubTabs(DataTable dataTable){
        navBar.goToRepVisits();
        List<String> list = dataTable.asList(String.class);
        for (String repVisitsSubItem : list) {
            Assert.assertTrue(repVisitsSubItem + " is not showing.",link(repVisitsSubItem).isDisplayed());
        }
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

    public void searchInSearchAndSchedulePage(String searchString){
        getSearchInSearchAndSchedule().clear();
        getSearchInSearchAndSchedule().sendKeys(searchString);
        getSearchInSearchAndScheduleButton().click();
        waitUntilPageFinishLoading();
    }

    public void verifySearchResultOfSearchAndSchedule(DataTable dataTable){
        DataTable transposedTable = dataTable.transpose();
        Map<String,String> searchMap = transposedTable.asMap(String.class, String.class);
        Set<String> searchCategory = searchMap.keySet();
        for (String category : searchCategory ) {
            searchInSearchAndSchedulePage(searchMap.get(category));
            try {
                Assert.assertTrue("HS data is showing while searching through "+category+" in Search And Schedule page", driver.findElement(By.xpath("//table[@class='ui very basic table']")).isDisplayed());
            }catch (NoSuchElementException nsee){
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
    //private WebElement getSearchInSearchAndSchedule(){ return getDriver().findElement(By.className("ui huge fluid action input")); }
    private WebElement getSearchInSearchAndSchedule(){ return getDriver().findElement(By.xpath("//div[@class='ui huge fluid action input']/input")); }
    //private WebElement getSearchInSearchAndScheduleButton(){ return getDriver().findElement(By.xpath("//div[@class='ui huge fluid action input']"));}
    private WebElement getSearchInSearchAndScheduleButton(){ return getDriver().findElement(By.xpath("//button[@class='ui button button']"));}


}


