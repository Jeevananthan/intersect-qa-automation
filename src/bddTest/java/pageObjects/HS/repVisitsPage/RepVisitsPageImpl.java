package pageObjects.HS.repVisitsPage;

import cucumber.api.DataTable;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObjects.COMMON.PageObjectFacadeImpl;
import pageObjects.HE.loginPage.LoginPageImpl;

import java.util.ArrayList;
import java.util.List;

public class RepVisitsPageImpl extends PageObjectFacadeImpl {
    private Logger logger;
    public RepVisitsPageImpl() {
        logger = Logger.getLogger(LoginPageImpl.class);
    }

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

    public void verifyVisitScheduling(String visitPerDay){
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Availability Settings").click();
        Assert.assertTrue("Title 'Visit Scheduling' is not displayed",text("Visit Scheduling").isDisplayed());
        Assert.assertTrue("Text 'Accept' is not displayed",text("Accept").isDisplayed());
        Assert.assertTrue("Listbox is not displayed",driver.findElement(By.cssSelector("div[class='ui selection dropdown'][role='listbox']")).isDisplayed());
        String Accept = driver.findElement(By.cssSelector("div[class='ui selection dropdown']>div[class='text']")).getText();
        if(Accept.equals("a maximum of...")){
            Assert.assertTrue("minimum and maximum user number of visit is not displayed",driver.findElement(By.cssSelector("input[name='rsvpDeadlineDays'][min='1'][max='99']")).isDisplayed());
            Assert.assertTrue("Text 'visits per day.' is not displayed",driver.findElement(By.xpath("//div/span[text()='visits per day.']")).isDisplayed());
            String actualVisitPerDay = getDriver().findElement(By.cssSelector("input[name='rsvpDeadlineDays'][min='1'][max='99']")).getAttribute("value");
            Assert.assertTrue("Visits per day is incorrect",visitPerDay.equals(actualVisitPerDay));
        }else if(Accept.equals("visits until I am fully booked.")){
            Assert.assertTrue("Name 'a maximum of...' is not selected",driver.findElement(By.xpath("//div[text()='visits until I am fully booked.']")).isDisplayed());
        }
        //'Save Changes' button
        Assert.assertTrue("'Save Changes' button is not displayed", driver.findElement(By.cssSelector("button[class='ui primary button']")).isDisplayed());
    }

    public void setAcceptinAvailabilitySettings(String accept, String visitsPerDay){
            navBar.goToRepVisits();
            link("Availability & Settings").click();
            link("Availability").click();
            link("Availability Settings").click();
        WebElement selectAccept = getDriver().findElement(By.cssSelector("div[class='ui selection dropdown']>div"));
        selectAccept.click();
        getDriver().findElement(By.xpath("//span[text()='" + accept + "']")).click();
        if(accept.equals("a maximum of...")) {
           getDriver().findElement(By.cssSelector("input[name='rsvpDeadlineDays'][min='1'][max='99']")).sendKeys(visitsPerDay);
        }
        button("Save Changes").click();
    }

}

