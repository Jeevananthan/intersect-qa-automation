package stepDefinitions.HUBS.FamilyConnection;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pageObjects.HUBS.FamilyConnection.FCColleges.FCCollegeEvents;

/**
 * Created by mbhangu on 12/13/2016.
 */
public class FCHubsEvents_StepDef {
    FCCollegeEvents collegeEvents = new FCCollegeEvents();

    @When("^I Navigate to old Colleges tab$")
    public void i_Click_On_Old_CollegesTab() throws Throwable {
        collegeEvents.clickCollegesTabOld();
    }

    @When("^I open link Upcoming college events$")
    public void i_Click_On_Link_UpcomingCollegeEvents() throws Throwable {
        collegeEvents.clicklinkUpcomingCollegeEvents();

    }

    @And("^I look for the host \"([^\"]*)\"$")
    public void iLookForTheHost(String collegeName) throws Throwable {
        collegeEvents.searchHost(collegeName);
    }

    @Then("^I sign up for the event of generated name$")
    public void iSignUpForTheEventOfNameAt() throws Throwable {
        collegeEvents.signUpToEvent();
    }
}


