package stepDefinitions.SM;

import cucumber.api.PendingException;
import cucumber.api.java8.En;
import pageObjects.SM.pinnedSchoolsComparePage.PinnedSchoolsComparePageImpl;
import pageObjects.SM.searchPage.SearchPageImpl;

public class PinnedSchoolsCompareStepDefs implements En {

    public PinnedSchoolsCompareStepDefs() {

        PinnedSchoolsComparePageImpl pinnedSchoolsComparePage = new PinnedSchoolsComparePageImpl();

        Then("^SM I verify that the tooltips are displayed in the Schools Compare screen \"([^\"]*)\"$", pinnedSchoolsComparePage::verifyTooltipInComparePage);

    }
}
