package stepDefinitions.SM;

import cucumber.api.java8.En;
import pageObjects.SM.pinnedSchoolsComparePage.PinnedSchoolsComparePageImpl;

public class PinnedSchoolsComparePageStepDefs implements En {

    public PinnedSchoolsComparePageStepDefs() {

        PinnedSchoolsComparePageImpl pinnedSchoolsComparePage = new PinnedSchoolsComparePageImpl();

        Then("^SM I verify that \"([^\"]*)\" drawers are displayed$", pinnedSchoolsComparePage::verifyNumberOfDrawersDisplayed);

        Then("^SM I verify that all drawers are expanded by default$", pinnedSchoolsComparePage::verifyAllDrawersExpanded);

        Then("^SM I verify that the \"([^\"]*)\" drawer in position \"([^\"]*)\" display an arrow facing \"([^\"]*)\"$", pinnedSchoolsComparePage::verifyDrawerArrowDirection);

        Then("^SM I verify that the Collapse All button collapses all the drawers$", pinnedSchoolsComparePage::verifyCollapseAllButtonCollapsesDrawers);

        Then("^SM I verify that the Collapse All button changes to \"([^\"]*)\" after it has been used$", pinnedSchoolsComparePage::verifyCollapseExpandButtonText);

        And("^SM I expand the drawer in position \"([^\"]*)\"$", pinnedSchoolsComparePage::expandDrawerInPosition);

        Then("^SM I verify that Expand All button changes to \"([^\"]*)\" when at least \"([^\"]*)\" drawer is expanded$", pinnedSchoolsComparePage::verifyCollapseExpandButtonTextWithExpandedDrawers);

        And("^SM I collapse all the drawers using the Collapse All button$", pinnedSchoolsComparePage::clickCollapseExpandButton);

        And("^SM I export the data in the Pinned Schools Compare screen$", pinnedSchoolsComparePage::clickExportButton);

        And("^SM I verify that in the \"([^\"]*)\" criteria table \"([^\"]*)\" criteria for the (\\d+) college is \"([^\"]*)\"$", pinnedSchoolsComparePage::verifyTextDataForCriteria);

        And("^SM I verify all the below options available in Resources fit criteria in Resources expandable drawer$", pinnedSchoolsComparePage::verifyResourcesExpandableDrawerOptions);

        And("^SM I clear all the pinned college$", pinnedSchoolsComparePage::clearPinnedColleges);

        And("^SM I verify all the below options available in Student Life fit criteria in Student Life expandable drawer$", pinnedSchoolsComparePage::verifyStudentLifeExpandableDrawerOptions);

        And("^SM I verify all the below options available in Diversity fit criteria in Diversity expandable drawer$", pinnedSchoolsComparePage::verifyDiversityExpandableDrawerOptions);

        And("^SM I verify all the below options available in Academics fit criteria in Academics expandable drawer$", pinnedSchoolsComparePage::verifyAcademicsExpandableDrawerOptions);

        And("^SM I verify all the below options available in Athletics fit criteria in Athletics expandable drawer$", pinnedSchoolsComparePage::verifyAthleticsExpandableDrawerOptions);

        And("^SM I verify all the below options available in IC fit criteria in IC expandable drawer$", pinnedSchoolsComparePage::verifyICDrawerOptions);

        Then("^SM I pin \"([^\"]*)\" from the search box$", pinnedSchoolsComparePage::pinCollegeFromBottomSearchResult);

        Then("^SM I verify that the tooltips are displayed in the Schools Compare screen \"([^\"]*)\"$", pinnedSchoolsComparePage::verifyTooltipInComparePage);

        Then("^SM I verify that the appropriate section in the college's profile is displayed after clicking the following links:$", pinnedSchoolsComparePage::verifyHousingInfoIsDisplayedAfterClickingSections);

        And("^SM I unpin \"([^\"]*)\" from the Schools Compare screen$", pinnedSchoolsComparePage::unpinSchoolFromCompareSchools);

        And("^SM I search for \"([^\"]*)\" college in search bar$", pinnedSchoolsComparePage::pinCollegeFromBottomSearchResult);

        And("^SM I verify all the below options available in Admission fit criteria in Admission Info expandable drawer$", pinnedSchoolsComparePage::verifyAdmissionInfoDrawerOptions);

        And("^SM I verify all the below options available in Institution Highlights expandable drawer$", pinnedSchoolsComparePage::verifyInstitutionHighlightsDrawerOptionsMaps);

        And("^SM I verify all the below options available in Location fit criteria in Location expandable drawer$", pinnedSchoolsComparePage::verifyLocationExpandableDrawerOptions);

        And("^SM I verify the following things for pinned college$", pinnedSchoolsComparePage::verifyPinnedCollege);

        And("^SM I verify the displaying more then four colleges in compare pinned college page$", pinnedSchoolsComparePage::verifyPinnedCollegesFunctionality);

        And("^SM I favorite the school \"([^\"]*)\" from the Pinned Colleges screen$", pinnedSchoolsComparePage::favSchoolFromPinnedColleges);

        And("^SM I un-favorite the school \"([^\"]*)\" from the Pinned Colleges screen$", pinnedSchoolsComparePage::unfavSchoolFromPinnedColleges);

        And("^SM I verify that college name should be hyperlink and open in new tab$", pinnedSchoolsComparePage::verifyCollegeHyperlink);

    }
}
