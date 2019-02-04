package stepDefinitions.HE.RepVisits;
import pageObjects.HE.repVisitsPage.RepvisitsSideBarPage;
import cucumber.api.java8.En;

public class RepvisitsSideBarStepDefs  implements En {

    RepvisitsSideBarPage  repvisitSidebar = new RepvisitsSideBarPage();
    public  RepvisitsSideBarStepDefs(){

        Then("^HE I verify the side bar popup has \"([^\"]*)\" as school Name on the header$",repvisitSidebar::assertRepvisitSidebar);
        And("^HE I verify default sidebar has visits tab and toggle between tabs",repvisitSidebar::assertDefaultTab);
        Then("^HE I verify the Start and End Dates displayed on the side bar$",repvisitSidebar::assertWeekDays);
        When("^HE I click on outside of the Repvisits side bar$",repvisitSidebar::clickOutsideRepvisitSidebar);
        Then("^HE I verify the Repvisits side bar closed$",repvisitSidebar::verifySideBarClose);
        Then("^HE I click on the side bar 'Fairs' toggle bar$",repvisitSidebar::chooseFairsToggle);
        Then("^HE I verify the recently \"([^\"]*)\" Fair on the fairs toggle$",repvisitSidebar::assertFairs);
        Then("^HE I verify the Add to Travel Plan on the \"([^\"]*)\" toggle$",repvisitSidebar::verifyAddToTravelPlan);
        Then("^HE I click on add to travel plan on footer$",repvisitSidebar::clickOnAddToTravelPlan);


    }

}
