package stepDefinitions.SM;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.SM.pinnedSchoolsComparePage.PinnedSchoolsComparePageImpl;
import pageObjects.SM.searchPage.SearchPageImpl;

public class PinnedSchoolsCompareStepDefs implements En {

    public PinnedSchoolsCompareStepDefs() {

        PinnedSchoolsComparePageImpl pinnedSchoolsComparePage = new PinnedSchoolsComparePageImpl();

        Then("^SM I verify that the tooltips are displayed in the Schools Compare screen \"([^\"]*)\"$", pinnedSchoolsComparePage::verifyTooltipInComparePage);

        Then("^SM I verify that the appropriate section in the college's profile is displayed after clicking the following links:$", pinnedSchoolsComparePage::verifyHousingInfoIsDisplayedAfterClickingSections);

        And("^SM I unpin \"([^\"]*)\" from the Schools Compare screen$", pinnedSchoolsComparePage::unpinSchool);

    }
}
