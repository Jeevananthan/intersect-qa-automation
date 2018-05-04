package stepDefinitions.HE;

import cucumber.api.java8.En;
import pageObjects.HE.FooterLinks.FooterLinksImpl;

public class FooterLinksStepDefs implements En {
    public FooterLinksStepDefs() {
        FooterLinksImpl footerLinksObj = new FooterLinksImpl();

        And("^HE I verify \"([^\"]*)\" is present in the footer$",footerLinksObj::verifyLinksInFooter);

        And("^HE I navigate to each page and verify the unique URL is present in the \"([^\"]*)\" page$",footerLinksObj::switchToWindowAndVerifyURL);

    }
}
