package pageObjects.HS.repVisitsPage;

import cucumber.api.DataTable;
import org.junit.Assert;
import org.openqa.selenium.By;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.ArrayList;
import java.util.List;

public class RepVisitsPageImpl extends PageObjectFacadeImpl {

    public void checkRepVisitsSubTabs(DataTable dataTable){
        navBar.goToRepVisits();
        List<String> list = dataTable.asList(String.class);
        for (String repVisitsSubItem : list) {
            Assert.assertTrue(repVisitsSubItem + " is not showing.",link(repVisitsSubItem).isDisplayed());
        }
    }

    public void verifyAvailabilityAndSettingsPage(){
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        List<String> tabs = new ArrayList<>();
        //Left Menu
        tabs.add("Availability");
        tabs.add("Messaging Options");
        tabs.add("Time Zone");
        tabs.add("Notifications & Primary Contact");
        tabs.add("Calendar Sync");
        tabs.add("Naviance Settings");
        //Top menu
        tabs.add("Regular Weekly Hours");
        tabs.add("Holidays");
        tabs.add("Exceptions");
        tabs.add("Availability Settings");
        for (String tab : tabs) {
            Assert.assertTrue("Tab " + tab + " is not displaying as expected!",link(tab).isDisplayed());
        }
    }
    public void verifyVisitScheduling(){
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability Settings").click();
        Assert.assertTrue("Title 'Visit Scheduling' is not displayed",driver.findElement(By.xpath("//div/span[text()='Visit Scheduling']")).isDisplayed());
        Assert.assertTrue("Text 'Accept' is not displayed",driver.findElement(By.xpath("//div/span[text()='Accept']")).isDisplayed());
        Assert.assertTrue("Listbox is not displayed",driver.findElement(By.xpath("//div[@class='ui selection dropdown' and @role='listbox']")).isDisplayed());
        try{
            Assert.assertTrue("Name 'a maximum of...' is not selected",driver.findElement(By.xpath("//div[text()='a maximum of...']")).isDisplayed());
            Assert.assertTrue("minimum and maximum user number of visit is not displayed",driver.findElement(By.xpath("//div/input[@name='maxDailyColleges' and @min='1' and @max='99']")).isDisplayed());
            Assert.assertTrue("Text 'visits per day.' is not displayed",driver.findElement(By.xpath("//div/span[text()='visits per day.']")).isDisplayed());
        }catch (Exception e) {
            Assert.assertTrue("Name 'a maximum of...' is not selected",driver.findElement(By.xpath("//div[text()='visits until I am fully booked.']")).isDisplayed());
        }
    }

}
