package pageObjects.HS.repVisitsPage;

import cucumber.api.DataTable;
import org.apache.tools.ant.taskdefs.WaitFor;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import pageObjects.COMMON.PageObjectFacadeImpl;

import java.util.ArrayList;
import java.util.List;

public class RepVisitsPageImpl extends PageObjectFacadeImpl {
    //RepVisitsPageImpl repVisits = new RepVisitsPageImpl();

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
    public void verifyTimeZonePage(String ValueTZ){
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Time Zone").click();
        Assert.assertTrue("Tell us about your high school text is not present",getDriver().findElement(By.cssSelector(".ui.header")).isDisplayed());
        Assert.assertTrue("Please specify your high school's time zone text is not present",getDriver().findElement(By.cssSelector(".field label")).isDisplayed());
        WebElement TZDropDown = getDriver().findElement(By.cssSelector("[class='ui search selection dropdown']"));
        Assert.assertTrue("Timezone was not set as expected.",TZDropDown.findElement(By.className("text")).getText().contains(ValueTZ));
        Assert.assertTrue("TZ selected does not match",getDriver().findElement(By.cssSelector(".search[name=\"-search\"] + div")).isDisplayed());
        Assert.assertTrue("Update Time Zone button is not displayed",getDriver().findElement(By.cssSelector(".button[class='ui primary button']")).isDisplayed());


        //ClickandSelectTZValue(ValueTZ);
        //ClickUpdateTimeZone();
        //ClickTimeZoneButtonToSave();

    }
    public void setTimeZone(String timeZone){
        if (!isRVLinkActive(link("Time Zone"))) {
            navBar.goToRepVisits();
            link("Availability & Settings").click();
            link("Time Zone").click();
        }

        ClickandSelectTZValue(timeZone);
        //ClickUpdateTimeZone();
        ClickTimeZoneButtonToSave();



    }

    private void ClickTimeZoneButtonToSave() {

        button("Update time zone").click();

    }

    private void ClickUpdateTimeZone() {
        getDriver().findElement(By.cssSelector(".search[name=\"-search\"] + div")).click();
    }

    private void ClickandSelectTZValue(String timeZone) {
        WebElement EntertimeZone = getDriver().findElement(By.cssSelector(".search[name=\"-search\"] + div"));
        EntertimeZone.click();
        getDriver().findElement(By.xpath("//span[text()='"+ timeZone +"']")).click();
    }
    public void clickLinkAvailability() {
        if (!isRVLinkActive(link("Availability"))) {
            navBar.goToRepVisits();
            link("Availability & Settings").click();
            link("Availability").click();

        }
    }

    //locators for Time Zone Page
    private WebElement timeZone()
    {
        return link("Time Zone");
    }
    private WebElement highSchoolText()
    {
        return getDriver().findElement(By.cssSelector(".ui.header"));
    }
    private WebElement highSchoolTZ()
    {
        return getDriver().findElement(By.cssSelector(".field label"));
    }
    private WebElement dropdownTZ()
    {
        return getDriver().findElement(By.cssSelector(".search[name=\"-search\"] + div"));
    }
    private WebElement buttonTZ()
    {
        return getDriver().findElement(By.cssSelector(".button[class='ui primary button']"));
    }

    private boolean isRVLinkActive(WebElement link) {
        return link.getAttribute("class").contains("active");
    }
}




