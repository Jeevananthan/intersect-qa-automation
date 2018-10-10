package stepDefinitions.SM;

import cucumber.api.java8.En;
import pageObjects.SM.footerPage.FooterPageImpl;

public class FooterStepDefs implements En {

    FooterPageImpl footerPageOgj = new FooterPageImpl();

    public FooterStepDefs() {
        And("^SM I check pinned colleges count$", footerPageOgj::verifyPinnedCollegeCountInFooter);
    }
}

