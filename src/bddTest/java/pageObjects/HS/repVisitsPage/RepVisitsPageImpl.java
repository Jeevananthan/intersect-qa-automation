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

    public void verifyContentsOfNavianceSettings() {
        navBar.goToRepVisits();
        link("Availability & Settings").click();
        link("Naviance Settings").click();

        String publishVisitsToNavianceText = getDriver().findElement(By.id("form-naviance-settings")).getText();
        String likeToPublishAutomaticallyOptionsText = "Automatically publish confirmed visits.";
        String likeToPublishManuallyOptionsText = "Manually choose which visits to publish. (If any)";
        String optionalNotificationText = "When publishing to Naviance, do you want to notify students who have expressed interest in that institution?";
        String displayDeadLineAndStatus = "Do you want to display a deadline and status to students?";
        Assert.assertTrue(likeToPublishAutomaticallyOptionsText + " Text is not displayed",
                publishVisitsToNavianceText.contains(likeToPublishAutomaticallyOptionsText));
        Assert.assertTrue(likeToPublishManuallyOptionsText + " Text is not displayed",
                publishVisitsToNavianceText.contains(likeToPublishManuallyOptionsText));
        Assert.assertTrue(optionalNotificationText + " Text is not displayed",
                publishVisitsToNavianceText.contains(optionalNotificationText));
        Assert.assertTrue("Max number of students is not showing.", textbox(By.id("maxNumStudents")).isDisplayed());
        Assert.assertTrue("Location is not showing is not showing.", textbox(By.id("locationWithinSchool")).isDisplayed());
        Assert.assertTrue("Registration will close is not showing.", textbox(By.id("rsvpNumber")).isDisplayed());
        Assert.assertTrue(displayDeadLineAndStatus + " Text is not displayed",
                publishVisitsToNavianceText.contains(displayDeadLineAndStatus));
        Assert.assertTrue("Button Save Changes is not showing.", button(By.cssSelector("button[class='ui primary button']")).isDisplayed());
    }
}
